package fudus.api.repository

import fudus.api.model.{CategoryUUID, Food, Restaurant, RestaurantUUID}
import fudus.api.services.DatabaseService
import zio._

final case class FoodRepository(quillCtx: DatabaseService.QuillContext) {
  import quillCtx._

  // UUID database encoders
  implicit def arrayUUIDEncoder[Col <: Seq[CategoryUUID]]: Encoder[Col] =
    arrayRawEncoder[CategoryUUID, Col]("uuid")
  implicit def arrayUUIDDecoder[Col <: Seq[CategoryUUID]](implicit
      bf: CBF[CategoryUUID, Col]
  ): Decoder[Col] =
    arrayRawDecoder[CategoryUUID, Col]

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
}

object FoodRepository {
  val layer: ZLayer[DatabaseService.QuillContext, Throwable, FoodRepository] =
    ZLayer.fromFunction(FoodRepository.apply _)
}
