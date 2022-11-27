package fudus.api

import fudus.api.errors.FudusError
import fudus.api.repository.{CategoryRepository, FoodRepository, RestaurantRepository}
import fudus.api.services.{DatabaseService, RestaurantFoodService}
import io.getquill.SnakeCase
import io.getquill.jdbczio.Quill
import zio._

object FudusApplication extends ZIOAppDefault {
  override def run: IO[Throwable, Unit] =
    ZIO
      .serviceWithZIO[FudusServer](_.start)
      .provide(
        FudusServer.layer,
        Quill.DataSource.fromPrefix("db.default"),
        Quill.Postgres.fromNamingStrategy(SnakeCase),
        DatabaseService.layer,
        CategoryRepository.layer,
        RestaurantRepository.layer,
        CategoryRepository.layer,
        FoodRepository.layer,
        RestaurantFoodService.layer
      )
}
