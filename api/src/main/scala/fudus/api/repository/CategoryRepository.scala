package fudus.api.repository

import fudus.api.errors.{FudusDatabaseError, FudusError}
import fudus.api.model.Category
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
}

object CategoryRepository {
  val layer: ZLayer[DatabaseService.QuillContext, Throwable, CategoryRepository] =
    ZLayer.fromFunction(CategoryRepository.apply _)
}
