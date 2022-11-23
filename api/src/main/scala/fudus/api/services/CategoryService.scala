package fudus.api.services

import fudus.api.errors.{FudusDatabaseError, FudusError}
import fudus.api.model.Category
import fudus.api.services.DatabaseService.QuillContext
import io.getquill.SnakeCase
import io.getquill.jdbczio.Quill
import zio._

object CategoryService {
  def getAll(): ZIO[CategoryService, FudusError, List[Category]] =
    ZIO.serviceWithZIO[CategoryService](_.getAll())

  def layer: ZLayer[QuillContext, Throwable, CategoryService] =
    ZLayer.fromFunction(CategoryService.apply _)
}

final case class CategoryService(quillCtx: DatabaseService.QuillContext) {
  import quillCtx._

  def getAll(): IO[FudusError, List[Category]] =
    run {
      quote {
        query[Category]
      }
    }.mapError(e => FudusDatabaseError(e.getMessage))
}
