package fudus.api.services

import fudus.api.errors.{FudusDatabaseError, FudusError}
import fudus.api.model.Domain.Food
import fudus.api.repository.{FoodRepository, RestaurantRepository}
import zio._

// TODO this isn't looks too good, move it
final case class RestaurantFoodService(
    foodRepository: FoodRepository,
    restaurantRepository: RestaurantRepository
) {
  def fetchRestaurantFoodBySlug(slug: String): IO[FudusError, List[Food]] =
    (for {
      _ <- ZIO.logInfo(s"Finding restaurant: $slug")
      restaurant <- restaurantRepository
        .findBySlug(slug)
        .someOrFail(FudusDatabaseError("Restaurant not found"))
      _ <- ZIO.logInfo(s"Found restaurant: ${restaurant.name}")
      food <- foodRepository.findByRestaurantUUID(restaurant.uuid)
      _ <- ZIO.logInfo(s"Found food: ${food.map(_.name).mkString(", ")}")
    } yield food).refineToOrDie[FudusDatabaseError]
}

object RestaurantFoodService {
  val layer: ZLayer[RestaurantRepository with FoodRepository, Throwable, RestaurantFoodService] =
    ZLayer {
      for {
        foodRepository <- ZIO.service[FoodRepository]
        restaurantRepository <- ZIO.service[RestaurantRepository]
      } yield RestaurantFoodService(foodRepository, restaurantRepository)
    }

  def fetchRestaurantFoodBySlug(slug: String): ZIO[RestaurantFoodService, FudusError, List[Food]] =
    ZIO.serviceWithZIO[RestaurantFoodService](_.fetchRestaurantFoodBySlug(slug))
}
