package fudus.api.services

import fudus.api.errors.{FudusApiError, FudusDatabaseError, FudusDatabaseException, FudusError}
import fudus.api.model.Restaurant
import fudus.api.services.DatabaseService.QuillContext
import io.getquill.SnakeCase
import io.getquill.jdbczio.Quill
import zio._

import java.sql.SQLException

object RestaurantService {
  def getBySlug(slug: String): ZIO[RestaurantService, Throwable, List[Restaurant]] =
    ZIO.serviceWithZIO[RestaurantService](_.getBySlug(slug))

  def listRestaurants: ZIO[RestaurantService, FudusApiError, List[Restaurant]] =
    ZIO.serviceWithZIO[RestaurantService](_.listRestaurants)

  def save(restaurant: Restaurant): ZIO[RestaurantService, Throwable, Long] =
    ZIO.serviceWithZIO[RestaurantService](_.save(restaurant))

  def layer: ZLayer[QuillContext, Throwable, RestaurantService] =
    ZLayer.fromFunction(RestaurantService.apply _)
}

case class RestaurantService(quillCtx: Quill.Postgres[SnakeCase]) {
  import quillCtx._

  def getBySlug(slug: String): Task[List[Restaurant]] =
    run {
      quote {
        query[Restaurant].filter(_.slug == lift(slug))
      }
    }

  def save(restaurant: Restaurant): Task[Long] =
    run {
      quote {
        query[Restaurant].insertValue(lift(restaurant))
      }
    }

  def listRestaurants: IO[FudusApiError, List[Restaurant]] =
    run {
      quote {
        query[Restaurant]
      }
    }.orDie
}
