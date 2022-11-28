package fudus.api.repository

import fudus.api.model.{Restaurant, RestaurantUUID}
import fudus.api.services.DatabaseService
import io.getquill.SnakeCase
import io.getquill.jdbczio.Quill
import zio._

final case class RestaurantRepository(quillCtx: Quill.Postgres[SnakeCase]) {
  import quillCtx._

  import fudus.api.encoder.sql._

  def findBySlug(slug: String): Task[Option[Restaurant]] =
    run {
      quote {
        query[Restaurant].filter(_.slug == lift(slug))
      }
    }.mapAttempt(_.headOption)

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

  def findBySlug(slug: String): ZIO[RestaurantRepository, Throwable, Option[Restaurant]] =
    ZIO.serviceWithZIO[RestaurantRepository](_.findBySlug(slug))

  def findAll: ZIO[RestaurantRepository, Throwable, List[Restaurant]] =
    ZIO.serviceWithZIO[RestaurantRepository](_.findAll)
}
