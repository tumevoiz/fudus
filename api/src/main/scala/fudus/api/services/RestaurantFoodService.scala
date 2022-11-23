package fudus.api.services

import fudus.api.errors.{FudusDatabaseError, FudusError}
import fudus.api.model.Food
import zio._

final case class RestaurantFoodService(
    foodService: FoodService,
    restaurantService: RestaurantService
) {
  def getRestaurantFoodBySlug(slug: String): IO[FudusError, List[Food]] = (for {
    restaurant <- restaurantService.getBySlug(slug)
    food <- foodService.listFoodByRestaurantUUID(restaurant.uuid)
  } yield food).mapError(e => FudusDatabaseError(e.getMessage))
}

object RestaurantFoodService {
  def layer: ZLayer[RestaurantService with FoodService, Nothing, RestaurantFoodService] =
    ZLayer {
      for {
        foodService <- ZIO.service[FoodService]
        restaurantService <- ZIO.service[RestaurantService]
      } yield RestaurantFoodService(foodService, restaurantService)
    }

  def getRestaurantFoodBySlug(slug: String): ZIO[RestaurantFoodService, FudusError, List[Food]] =
    ZIO.serviceWithZIO[RestaurantFoodService](_.getRestaurantFoodBySlug(slug))
}
