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
//    CategoryEndpoints.listCategories.zServerLogic(_ =>
//      categoryService.findAll.mapError(e => FudusApiError(e.getMessage))
//    ),
      RootEndpoints.login,
      RestaurantEndpoints.listRestaurants,
      RestaurantEndpoints.getRestaurantBySlug,
      RestaurantEndpoints.getRestaurantBySlugFood,
      RestaurantEndpoints.createRestaurant,
      OrderingEndpoint.createOrdering
    )

  val docEndpoints: List[ZServerEndpoint[FudusServerEnv, Any]] =
    SwaggerInterpreter().fromServerEndpoints(apiEndpoints, "fudus-api", "1.0.0")

  val httpApp: HttpApp[FudusServerEnv, Throwable] =
    ZioHttpInterpreter().toHttp(apiEndpoints ++ docEndpoints)

  def start: ZIO[FudusEnv, Throwable, Unit] = (for {
    port <- System.envOrElse("PORT", "8080").map(_.toInt)
    _ <- ZIO.logInfo("Starting migrations") *> databaseService.migrate
    _ <- ZIO
      .logInfo("Starting HTTP server") *> Server
      .start(port, httpApp)
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

  type FudusServerEnv = RestaurantRepository
    with RestaurantFoodService
    with AuthenticationService
    with RestaurantService
    with FoodService
    with OrderingService

  val layer: ZLayer[FudusEnv, Throwable, FudusServer] =
    ZLayer.fromFunction(FudusServer.apply _)

}
