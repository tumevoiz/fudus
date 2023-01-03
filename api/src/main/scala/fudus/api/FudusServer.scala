package fudus.api

import fudus.api.FudusServer.{FudusEnv, FudusServerEnv}
import fudus.api.endpoints._
import fudus.api.errors.{FudusApiError, FudusError, FudusServerError}
import fudus.api.repository._
import fudus.api.services._
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
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
      SearchEndpoints.search
    )

  val docEndpoints: List[ZServerEndpoint[FudusServerEnv, Any]] =
    SwaggerInterpreter().fromServerEndpoints(apiEndpoints, "fudus-api", "1.0.0")

  val httpApp: HttpApp[FudusServerEnv, Throwable] =
    ZioHttpInterpreter().toHttp(apiEndpoints ++ docEndpoints)

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
