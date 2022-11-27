package fudus.api.repository

import fudus.api.model.{Restaurant, RestaurantUUID}
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

  def findByUUID(uuid: RestaurantUUID): Task[Option[Restaurant]] =
    run {
      quote {
        query[Restaurant].filter(_.uuid == lift(uuid))
      }
    }.mapAttempt(_.headOption)

  def save(restaurant: Restaurant): Task[Unit] =
    run {
      quote {
        query[Restaurant].insertValue(lift(restaurant))
      }
    }.mapAttempt(_ => {})

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

  def findBySlug(slug: String): ZIO[RestaurantRepository, Throwable, Restaurant] =
    ZIO.serviceWithZIO[RestaurantRepository](_.findBySlug(slug))
}
