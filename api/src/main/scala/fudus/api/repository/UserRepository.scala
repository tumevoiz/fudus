package fudus.api.repository

import fudus.api.model.{User, UserUUID}
import fudus.api.services.DatabaseService
import zio._

final case class UserRepository(quillCtx: DatabaseService.QuillContext) {
  import quillCtx._

  def findByUUID(uuid: UserUUID): Task[User] =
    run {
      quote {
        query[User].filter(_.uuid == lift(uuid))
      }
    }.mapAttempt(_.head)

  def save(user: User): Task[Unit] =
    run {
      quote {
        query[User].insertValue(lift(user))
      }
    }.mapAttempt(_ => {})
}

object UserRepository {
  val layer: ZLayer[DatabaseService.QuillContext, Throwable, UserRepository] =
    ZLayer.fromFunction(UserRepository.apply _)
}
