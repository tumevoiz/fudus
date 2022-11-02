package fudus.api

import fudus.api.repository.{InmemoryRestaurantRepository, RestaurantRepository}
import zio._

object FudusApplication extends ZIOAppDefault {
  override def run: Task[ExitCode] =
    ZIO.serviceWithZIO[FudusServer](_.start)
      .provide(
        FudusServer.layer,
        InmemoryRestaurantRepository.layerWithMockData
      ).exitCode
}
