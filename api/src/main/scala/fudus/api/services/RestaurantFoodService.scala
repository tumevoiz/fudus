package fudus.api.services

import fudus.api.errors.{FudusDatabaseError, FudusError}
import fudus.api.model.Food
import fudus.api.repository.{FoodRepository, RestaurantRepository}
import zio._

// TODO this isn't looks too good, move it
final case class RestaurantFoodService(
    foodRepository: FoodRepository,
    restaurantRepository: RestaurantRepository
) {
  def fetchRestaurantFoodBySlug(slug: String): IO[FudusError, List[Food]] =
    (for {
      restaurant <- restaurantRepository
        .findBySlug(slug)
        .someOrFail(FudusDatabaseError("Restaurant not found"))
      food <- foodRepository.findByRestaurantUUID(restaurant.uuid)
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
