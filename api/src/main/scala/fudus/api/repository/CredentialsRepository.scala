package fudus.api.repository

import fudus.api.model.Domain.{Credentials, CredentialsUUID}
import fudus.api.services.DatabaseService
import zio._

final case class CredentialsRepository(quillCtx: DatabaseService.QuillContext) {
  import quillCtx._

  import fudus.api.encoder.sql._

  def save(credentials: Credentials): Task[Unit] =
    run {
      quote {
        query[Credentials].insertValue(lift(credentials))
      }
    }.as[Unit]

  def findByUsername(username: String): Task[Option[Credentials]] =
    run {
      quote {
        query[Credentials].filter(_.username == lift(username))
      }
    }.map(_.headOption)
}

object CredentialsRepository {
  val layer: ZLayer[DatabaseService.QuillContext, Throwable, CredentialsRepository] =
    ZLayer.fromFunction(CredentialsRepository.apply _)
}
