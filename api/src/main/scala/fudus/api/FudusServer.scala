package fudus.api

import fudus.api.FudusServer.{FudusEnv, FudusServerEnv}
import fudus.api.encoder._
import fudus.api.endpoints._
import fudus.api.errors.{FudusApiError, FudusError, FudusServerError}
import fudus.api.repository._
import fudus.api.services._
import sttp.model.StatusCode
import sttp.tapir.generic.auto.schemaForCaseClass
import sttp.tapir.json.zio.jsonBody
import sttp.tapir.server.interceptor.decodefailure.{
  DecodeFailureHandler,
  DefaultDecodeFailureHandler
}
import sttp.tapir.server.model.ValuedEndpointOutput
import sttp.tapir.server.ziohttp.{ZioHttpInterpreter, ZioHttpServerOptions}
import sttp.tapir.swagger.bundle.SwaggerInterpreter
import sttp.tapir.ztapir._
import zhttp.http._
import zhttp.http.middleware.Cors.CorsConfig
import zhttp.service.Server
import zio._

final case class FudusServer(
    authenticationService: AuthenticationService,
    databaseService: DatabaseService,
    categoryService: CategoryRepository,
    restaurantRepository: RestaurantRepository,
    restaurantService: RestaurantService,
    restaurantFoodService: RestaurantFoodService,
    orderingService: OrderingService
) {
  val apiEndpoints: List[ZServerEndpoint[FudusServerEnv, Any]] =
    List(
      CategoryEndpoints.listCategories,
      RootEndpoints.login,
      RootEndpoints.register,
      RestaurantEndpoints.listRestaurants,
      RestaurantEndpoints.getRestaurantBySlug,
      RestaurantEndpoints.getRestaurantBySlugFood,
      RestaurantEndpoints.createRestaurant,
      OrderingEndpoint.createOrdering,
      OrderingEndpoint.listOrdering,
      SearchEndpoints.search
    )

  val docEndpoints: List[ZServerEndpoint[FudusServerEnv, Any]] =
    SwaggerInterpreter().fromServerEndpoints(apiEndpoints, "fudus-api", "1.0.0")

  def myFailureDecodeHandler: DecodeFailureHandler =
    DefaultDecodeFailureHandler.default.response(myFailureResponse)

  def myFailureResponse(m: String): ValuedEndpointOutput[_] =
    ValuedEndpointOutput(jsonBody[FudusApiError], FudusApiError(m))

  val httpApp: HttpApp[FudusServerEnv, Throwable] = {
    ZioHttpInterpreter(
      ZioHttpServerOptions
        .customiseInterceptors[FudusServerEnv]
        .decodeFailureHandler(myFailureDecodeHandler)
        .options
    ).toHttp(apiEndpoints ++ docEndpoints)
  }

  val corsConfig: CorsConfig = CorsConfig(
    anyOrigin = true,
    anyMethod = true
  )

  val combined: Http[FudusServerEnv, Throwable, Request, Response] =
    httpApp @@ Middleware.cors(corsConfig)

  def start: ZIO[FudusEnv, Throwable, Unit] = (for {
    port <- System.envOrElse("PORT", "8080").map(_.toInt)
    _ <- ZIO.logInfo("Starting migrations") *> databaseService.migrate
    _ <- ZIO
      .logInfo("Starting HTTP server") *> Server
      .start(port, combined)
      .onError(e => ZIO.logError(e.prettyPrint))
  } yield ())
}

object FudusServer {
  type FudusEnv = AuthenticationService
    with CategoryRepository
    with CredentialsRepository
    with FoodRepository
    with RestaurantRepository
    with CustomerRepository
    with AuthenticationService
    with DatabaseService
    with FoodService
    with RestaurantFoodService
    with RestaurantService
    with CustomerService
    with OrderingRepository
    with OrderingService
    with SearchService

  type FudusServerEnv = CustomerService
    with CategoryRepository
    with OrderingRepository
    with RestaurantRepository
    with RestaurantFoodService
    with AuthenticationService
    with RestaurantService
    with FoodService
    with OrderingService
    with SearchService

  val layer: ZLayer[FudusEnv, Throwable, FudusServer] =
    ZLayer.fromFunction(FudusServer.apply _)

}
