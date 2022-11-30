package fudus.api

import fudus.api.errors.FudusError
import fudus.api.repository._
import fudus.api.services._
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
        // Repositories
        CategoryRepository.layer,
        CredentialsRepository.layer,
        RestaurantRepository.layer,
        FoodRepository.layer,
        CustomerRepository.layer,
        // Services
        AuthenticationService.layer,
        DatabaseService.layer,
        FoodService.layer,
        RestaurantFoodService.layer,
        RestaurantService.layer,
        CustomerService.layer
      )
      .debug
}
