package fudus.api.repository

import fudus.api.errors.{FudusDatabaseError, FudusError}
import fudus.api.model.Domain.{Category, CategoryUUID}
import fudus.api.services.DatabaseService
import zio._

final case class CategoryRepository(quillCtx: DatabaseService.QuillContext) {
  import quillCtx._

  import fudus.api.encoder.sql._

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

  def save(category: Category): Task[Unit] =
    run {
      quote {
        query[Category].insertValue(lift(category))
      }
    }.as[Unit]
}

object CategoryRepository {
  val layer: ZLayer[DatabaseService.QuillContext, Throwable, CategoryRepository] =
    ZLayer.fromFunction(CategoryRepository.apply _)
}
