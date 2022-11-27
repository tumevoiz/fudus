package fudus.api.services

import fudus.api.errors.{
  FudusDatabaseError,
  FudusError,
  FudusFoodCreationError,
  FudusValidationError,
  ValidationMessages
}
import fudus.api.model.{CategoryUUID, Food, FoodUUID, RestaurantUUID}
import fudus.api.repository.{CategoryRepository, FoodRepository, RestaurantRepository}
import zio._

import java.util.UUID

final case class FoodService(
    categoryRepository: CategoryRepository,
    foodRepository: FoodRepository,
    restaurantRepository: RestaurantRepository
) {
  def create(
      name: String,
      categories: Seq[CategoryUUID],
      price: Float,
      restaurant: RestaurantUUID
  ): IO[FudusError, Unit] =
    for {
      _ <- validateCategories(categories)
      _ <- validateRestaurant(restaurant)
      food = Food(
        uuid = FoodUUID(UUID.randomUUID().toString),
        name = name,
        categories = categories,
        price = price,
        restaurant = restaurant
      )
      _ <- foodRepository.save(food).mapError(e => FudusFoodCreationError(e.getMessage))
    } yield ()

  private[this] def validateCategories(categories: Seq[CategoryUUID]): IO[FudusError, Unit] =
    ZIO
      .foreach(categories) { categoryUUID =>
        for {
          category <- categoryRepository
            .findByUUID(categoryUUID)
            .mapError(e => FudusDatabaseError(e.getMessage))
          _ <- ZIO.when(category.isEmpty)(
            ZIO.fail(FudusValidationError(ValidationMessages.CategoryDoesNotExists))
          )
        } yield ()
      }
      .mapAttempt(_ => {})
      .mapError(e => FudusValidationError(e.getMessage)) // FIXME be more specific types on errors

  private[this] def validateRestaurant(restaurantUUID: RestaurantUUID): IO[FudusError, Unit] =
    for {
      restaurant <- restaurantRepository
        .findByUUID(restaurantUUID)
        .mapError(e => FudusDatabaseError(e.getMessage))
      _ <- ZIO.when(restaurant.isEmpty)(
        ZIO.fail(FudusValidationError(ValidationMessages.RestaurantDoesNotExists))
      )
    } yield ()
}

object FoodService {
  val layer: ZLayer[
    CategoryRepository with FoodRepository with RestaurantRepository,
    Throwable,
    FoodService
  ] =
    ZLayer.fromFunction(FoodService.apply _)
}
