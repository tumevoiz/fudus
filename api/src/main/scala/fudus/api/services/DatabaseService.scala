package fudus.api.services

import fudus.api.errors.FudusDatabaseError
import io.getquill._
import io.getquill.jdbczio.Quill
import org.flywaydb.core.Flyway
import zio._

case class DatabaseService(quillCtx: DatabaseService.QuillContext) {
  private[this] lazy val getFlyway: Task[Flyway] = ZIO
    .attempt {
      Flyway.configure().dataSource(quillCtx.ds).load()
    }

  def migrate: Task[Unit] = (for {
    flyway <- getFlyway
    _ <- ZIO.attempt(flyway.migrate())
  } yield ())
}

object DatabaseService {
  type QuillContext = Quill.Postgres[SnakeCase]

  def migrate: ZIO[DatabaseService, Throwable, Unit] =
    ZIO.serviceWithZIO[DatabaseService](_.migrate)

  val layer: ZLayer[QuillContext, Throwable, DatabaseService] =
    ZLayer.fromFunction(DatabaseService.apply _)
}
