package fudus.api.repository

import fudus.api.model.{Credentials, CredentialsUUID}
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
    }.mapAttempt(_ => {})

  def findByUsername(username: String): Task[Credentials] =
    run {
      quote {
        query[Credentials].filter(_.username == lift(username))
      }
    }.mapAttempt(_.head)
}

object CredentialsRepository {
  val layer: ZLayer[DatabaseService.QuillContext, Throwable, CredentialsRepository] =
    ZLayer.fromFunction(CredentialsRepository.apply _)
}
