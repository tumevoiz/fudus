package fudus.api.repository

import fudus.api.model.Domain.{Category, Food, FoodUUID, Restaurant, RestaurantUUID}
import fudus.api.services.DatabaseService
import io.getquill.{Query, SnakeCase}
import io.getquill.jdbczio.Quill
import zio._

final case class RestaurantRepository(quillCtx: Quill.Postgres[SnakeCase]) {
  import quillCtx._

  import fudus.api.encoder.sql._
  import fudus.api.encoder._

  def findBySlug(slug: String): Task[Option[Restaurant]] =
    run {
      quote {
        query[Restaurant].filter(_.slug == lift(slug))
      }
    }.map(_.headOption)

  def findByUUID(uuid: RestaurantUUID): Task[Option[Restaurant]] =
    run {
      quote {
        query[Restaurant].filter(_.uuid == lift(uuid))
      }
    }.map(_.headOption)

  def findByFoodUUID(foodUUID: FoodUUID): Task[Option[Restaurant]] =
    run {
      quote {
        query[Restaurant]
          .join(query[Food])
          .on { case (restaurant, food) =>
            food.restaurant == restaurant.uuid
          }
          .filter { case (_, food) =>
            food.uuid == lift(foodUUID)
          }
          .map { case (restaurant, _) =>
            restaurant
          }
      }
    }.map(_.headOption)

  def save(restaurant: Restaurant): Task[Long] =
    run {
      quote {
        query[Restaurant].insertValue(lift(restaurant))
      }
    }

  def findAll: Task[List[Restaurant]] =
    run {
      quote {
        query[Restaurant]
      }
    }

  def likeName(name: String): Task[List[Restaurant]] = {
    val interpolatedName = s"%$name%"
    run {
      quote {
        query[Restaurant].filter(c => sql"name ilike ${lift(interpolatedName)}".asCondition)
      }
    }
  }
}

object RestaurantRepository {
  val layer: ZLayer[DatabaseService.QuillContext, Throwable, RestaurantRepository] =
    ZLayer.fromFunction(RestaurantRepository.apply _)

  def findBySlug(slug: String): ZIO[RestaurantRepository, Throwable, Option[Restaurant]] =
    ZIO.serviceWithZIO[RestaurantRepository](_.findBySlug(slug))

  def findAll: ZIO[RestaurantRepository, Throwable, List[Restaurant]] =
    ZIO.serviceWithZIO[RestaurantRepository](_.findAll)
}
