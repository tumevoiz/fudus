package fudus.api.repository

import fudus.api.model.Restaurant
import fudus.api.services.DatabaseService
import io.getquill.SnakeCase
import io.getquill.jdbczio.Quill
import zio._

final case class RestaurantRepository(quillCtx: Quill.Postgres[SnakeCase]) {
  import quillCtx._

  def findBySlug(slug: String): Task[Restaurant] =
    run {
      quote {
        query[Restaurant].filter(_.slug == lift(slug))
      }
    }.mapAttempt(_.head)

  def create(restaurant: Restaurant): Task[Long] =
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
}

object RestaurantRepository {
  val layer: ZLayer[DatabaseService.QuillContext, Throwable, RestaurantRepository] =
    ZLayer.fromFunction(RestaurantRepository.apply _)
}
