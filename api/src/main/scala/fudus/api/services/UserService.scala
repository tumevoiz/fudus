package fudus.api.services

import fudus.api.errors.{
  FudusError,
  FudusUserCreationError,
  FudusValidationError,
  ValidationMessages
}
import fudus.api.model.{Credentials, CredentialsUUID, User, UserRole, UserUUID}
import fudus.api.repository.{CredentialsRepository, UserRepository}
import zio._

import java.util.UUID

final case class UserService(
    credentialsRepository: CredentialsRepository,
    userRepository: UserRepository
) {
  def create(
      username: String,
      password: String,
      email: String,
      role: UserRole.Type
  ): IO[FudusError, Unit] =
    for {
      _ <- validateUsername(username)
      _ <- validatePassword(password)
      _ <- validateEmail(email)

      user = User(
        uuid = UserUUID(UUID.randomUUID().toString),
        email = email,
        address = "",
        city = "",
        role = role
      )

      credentials = Credentials(
        uuid = CredentialsUUID(UUID.randomUUID().toString),
        username = username,
        password = password,
        user = user.uuid
      )

      // TODO could be improved using STM or SAGA
      _ <- userRepository
        .save(user)
        .mapError(e => FudusUserCreationError(e.getMessage))

      _ <- credentialsRepository
        .save(credentials)
        .mapError(e => FudusUserCreationError(e.getMessage))
    } yield ()

  private[this] def validateUsername(username: String): IO[FudusError, Unit] =
    for {
      _ <- ZIO.when(username.length <= 4)(
        ZIO.fail(FudusValidationError(ValidationMessages.UsernameTooShort))
      )
      _ <- ZIO.when(username.length >= 30)(
        ZIO.fail(FudusValidationError(ValidationMessages.UsernameTooLong))
      )
    } yield ()

  private[this] def validatePassword(password: String): IO[FudusError, Unit] =
    for {
      _ <- ZIO.when(password.length <= 8)(
        ZIO.fail(FudusValidationError(ValidationMessages.PasswordTooShort))
      )
      _ <- ZIO.when(password.length >= 30)(
        ZIO.fail(FudusValidationError(ValidationMessages.PasswordTooLong))
      )
    } yield ()

  private[this] def validateEmail(email: String): IO[FudusError, Unit] =
    for {
      _ <- ZIO.when(email.length <= 6)(
        ZIO.fail(FudusValidationError(ValidationMessages.EmailTooShort))
      )
      _ <- ZIO.when(email.length >= 100)(
        ZIO.fail(FudusValidationError(ValidationMessages.EmailTooLong))
      )
    } yield ()
}

object UserService {
  val layer: ZLayer[CredentialsRepository with UserRepository, Throwable, UserService] =
    ZLayer.fromFunction(UserService.apply _)
}
