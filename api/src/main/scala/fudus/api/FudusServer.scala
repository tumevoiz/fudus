package fudus.api

import fudus.api.endpoints._
import fudus.api.errors.{FudusApiError, FudusError, FudusServerError}
import fudus.api.repository.{CategoryRepository, FoodRepository, RestaurantRepository}
import fudus.api.services.{DatabaseService, RestaurantFoodService}
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import sttp.tapir.swagger.bundle.SwaggerInterpreter
import sttp.tapir.ztapir._
import zhttp.http.HttpApp
import zhttp.service.Server
import zio._

final case class FudusServer(
    databaseService: DatabaseService,
    categoryService: CategoryRepository,
    restaurantService: RestaurantRepository,
    restaurantFoodService: RestaurantFoodService
) {
  val apiEndpoints: List[ZServerEndpoint[Any, Any]] = List(
    CategoryEndpoints.listCategories.zServerLogic(_ =>
      categoryService.findAll.mapError(e => FudusApiError(e.getMessage))
    ),
    RestaurantEndpoints.listRestaurants.zServerLogic(_ =>
      restaurantService.findAll.mapError(e => FudusApiError(e.getMessage))
    ),
    RestaurantEndpoints.getRestaurantBySlug.zServerLogic(slug =>
      restaurantService.findBySlug(slug).mapError(e => FudusApiError(e.getMessage))
    ),
    RestaurantEndpoints.getRestaurantBySlugFood.zServerLogic(slug =>
      restaurantFoodService
        .fetchRestaurantFoodBySlug(slug)
        .mapError(e => FudusApiError(e.getMessage))
    )
  )

  val docEndpoints: List[ZServerEndpoint[Any, Any]] = SwaggerInterpreter()
    .fromServerEndpoints[Task](apiEndpoints, "fudus-api", "1.0.0")

  val all: List[ZServerEndpoint[Any, Any]] = apiEndpoints ++ docEndpoints

  val httpApp: HttpApp[Any, Throwable] = ZioHttpInterpreter()
    .toHttp(all)

  def start: ZIO[Any, Throwable, Unit] = (for {
    port <- System.envOrElse("PORT", "8080").map(_.toInt)
    _ <- ZIO.logInfo("Starting migrations") *> databaseService.migrate
    _ <- ZIO.logInfo("Starting HTTP server") *> Server.start(port, httpApp)
  } yield ())
}

object FudusServer {
  type FudusEnv = CategoryRepository
    with RestaurantRepository
    with DatabaseService
    with FoodRepository
    with RestaurantFoodService

  val layer: ZLayer[FudusEnv, Throwable, FudusServer] =
    ZLayer.fromFunction(FudusServer.apply _)
}
