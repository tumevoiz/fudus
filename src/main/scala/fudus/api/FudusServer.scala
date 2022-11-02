package fudus.api

import fudus.api.endpoints.RestaurantEndpoints
import fudus.api.errors.{FudusError, FudusServerError}
import fudus.api.repository.RestaurantRepository
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import sttp.tapir.swagger.bundle.SwaggerInterpreter
import sttp.tapir.ztapir._
import zhttp.http.HttpApp
import zhttp.service.Server
import zio._

final case class FudusServer(restaurantRepository: RestaurantRepository) {
  val apiEndpoints: List[ZServerEndpoint[Any, Any]] = List(
    RestaurantEndpoints.listRestaurants.zServerLogic(_ => restaurantRepository.listRestaurants)
  )

  val docEndpoints: List[ZServerEndpoint[Any, Any]] = SwaggerInterpreter()
    .fromServerEndpoints[Task](apiEndpoints, "fudus-api", "1.0.0")

  val all: List[ZServerEndpoint[Any, Any]] = apiEndpoints ++ docEndpoints

  val httpApp: HttpApp[Any, FudusError] = ZioHttpInterpreter().toHttp(all)
    .refineOrDie(t => FudusServerError(t.getMessage))

  def start: ZIO[Any, FudusServerError, Unit] = (for {
    port <- System.envOrElse("PORT", "8080").map(_.toInt)
    _ <- ZIO.logInfo("Server starting.")
    _ <- Server.start(port, httpApp)
  } yield ()).refineToOrDie[FudusServerError]
}

object FudusServer {
  val layer: ZLayer[RestaurantRepository, FudusServerError, FudusServer] =
    ZLayer.fromFunction(FudusServer.apply _)
}
