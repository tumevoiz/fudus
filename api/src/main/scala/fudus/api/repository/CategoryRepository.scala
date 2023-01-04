package fudus.api.repository

import fudus.api.errors.{FudusDatabaseError, FudusError}
import fudus.api.model.Domain.{Category, CategoryUUID, Restaurant}
import fudus.api.services.DatabaseService
import io.getquill.Query
import zio._

final case class CategoryRepository(quillCtx: DatabaseService.QuillContext) {
  import quillCtx._

  import fudus.api.encoder.sql._
  import fudus.api.encoder._

  def findAll: Task[List[Category]] =
    run {
      quote {
        query[Category]
      }
    }

  def findByUUID(uuid: CategoryUUID): Task[Option[Category]] =
    run {
      quote {
        query[Category].filter(_.uuid == lift(uuid))
      }
    }.map(_.headOption)

  def likeName(name: String): Task[List[Category]] = {
    val interpolatedName = s"%$name%"
    run {
      quote {
        query[Category].filter(c => sql"name ilike ${lift(interpolatedName)}".asCondition)
      }
    }
  }

  def save(category: Category): Task[Long] =
    run {
      quote {
        query[Category].insertValue(lift(category))
      }
    }

}

object CategoryRepository {
  val layer: ZLayer[DatabaseService.QuillContext, Throwable, CategoryRepository] =
    ZLayer.fromFunction(CategoryRepository.apply _)

  def findAll: ZIO[CategoryRepository, Throwable, List[Category]] =
    ZIO.serviceWithZIO[CategoryRepository](_.findAll)
}
