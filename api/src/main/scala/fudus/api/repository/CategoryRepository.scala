package fudus.api.repository

import fudus.api.errors.{FudusDatabaseError, FudusError}
import fudus.api.model.{Category, CategoryUUID}
import fudus.api.services.DatabaseService
import zio._

final case class CategoryRepository(quillCtx: DatabaseService.QuillContext) {
  import quillCtx._

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
    }.mapAttempt(_.headOption)

  def save(category: Category): Task[Unit] =
    run {
      quote {
        query[Category].insertValue(lift(category))
      }
    }.mapAttempt(_ => {})
}

object CategoryRepository {
  val layer: ZLayer[DatabaseService.QuillContext, Throwable, CategoryRepository] =
    ZLayer.fromFunction(CategoryRepository.apply _)
}
