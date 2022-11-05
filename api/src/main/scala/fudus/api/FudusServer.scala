package fudus.api

import fudus.api.endpoints.RestaurantEndpoints
import fudus.api.errors.{FudusError, FudusServerError}
import fudus.api.services.{DatabaseService, RestaurantService}
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import sttp.tapir.swagger.bundle.SwaggerInterpreter
import sttp.tapir.ztapir._
import zhttp.http.HttpApp
import zhttp.service.Server
import zio._

final case class FudusServer(databaseService: DatabaseService, restaurantService: RestaurantService) {
  val apiEndpoints: List[ZServerEndpoint[Any, Any]] = List(
    RestaurantEndpoints.listRestaurants.zServerLogic(_ => restaurantService.listRestaurants)
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
  type FudusEnv = RestaurantService with DatabaseService

  val layer: ZLayer[FudusEnv, Throwable, FudusServer] =
    ZLayer.fromFunction(FudusServer.apply _)
}
