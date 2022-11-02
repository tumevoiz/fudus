package fudus.api

import fudus.api.repository.{InmemoryRestaurantRepository, RestaurantRepository}
import zio._

object FudusApplication extends ZIOAppDefault {
  override def run: Task[ExitCode] =
    ZIO.serviceWithZIO[FudusServer](_.start)
      .provide(
        FudusServer.layer,
//        Database.dataSourceLayer,
//        Database.layer,
        InmemoryRestaurantRepository.layerWithMockData
      ).exitCode
}
