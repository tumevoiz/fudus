package fudus.api.repository

import fudus.api.model.Domain.{CategoryUUID, Food, FoodUUID, Restaurant, RestaurantUUID}
import fudus.api.services.DatabaseService
import zio._

final case class FoodRepository(quillCtx: DatabaseService.QuillContext) {
  import quillCtx._

  import fudus.api.encoder.sql._

  def findByUUID(uuid: FoodUUID): Task[Option[Food]] =
    run {
      quote {
        query[Food].filter(_.uuid == lift(uuid))
      }
    }.map(_.headOption)

  def findByRestaurantUUID(restaurantUUID: RestaurantUUID): Task[List[Food]] =
    run {
      quote {
        query[Food]
          .join(query[Restaurant])
          .on { case (food, restaurant) =>
            food.restaurant == restaurant.uuid
          }
          .filter { case (_, restaurant) =>
            restaurant.uuid == lift(restaurantUUID)
          }
          .map { case (food, _) =>
            food
          }
      }
    }

  def save(food: Food): Task[Unit] =
    run {
      quote {
        query[Food].insertValue(lift(food))
      }
    }.as[Unit]
}

object FoodRepository {
  val layer: ZLayer[DatabaseService.QuillContext, Throwable, FoodRepository] =
    ZLayer.fromFunction(FoodRepository.apply _)
}
