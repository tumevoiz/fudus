package fudus.api.services

import fudus.api.model.{CategoryUUID, Food, Restaurant, RestaurantUUID}
import fudus.api.services.DatabaseService.QuillContext
import zio._

final case class FoodService(quillCtx: DatabaseService.QuillContext) {
  import quillCtx._

  // UUID database encoders
  implicit def arrayUUIDEncoder[Col <: Seq[CategoryUUID]]: Encoder[Col] =
    arrayRawEncoder[CategoryUUID, Col]("uuid")
  implicit def arrayUUIDDecoder[Col <: Seq[CategoryUUID]](implicit
      bf: CBF[CategoryUUID, Col]
  ): Decoder[Col] =
    arrayRawDecoder[CategoryUUID, Col]

  def listFoodByRestaurantUUID(restaurantUUID: RestaurantUUID): Task[List[Food]] =
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
}

object FoodService {
  def listFoodByRestaurantUUID(
      restaurantUUID: RestaurantUUID
  ): ZIO[FoodService, Throwable, List[Food]] =
    ZIO.serviceWithZIO[FoodService](_.listFoodByRestaurantUUID(restaurantUUID))

  def layer: ZLayer[QuillContext, Throwable, FoodService] =
    ZLayer.fromFunction(FoodService.apply _)
}
