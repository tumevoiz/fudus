package fudus.api.services

import fudus.api.errors.{ErrorMessages, FudusAuthenticationError, FudusError}
import fudus.api.model.User
import fudus.api.repository.{CredentialsRepository, UserRepository}
import zio._

final case class AuthenticationService(
    credentialsRepository: CredentialsRepository,
    userRepository: UserRepository
) {
  def authenticate(username: String, password: String): IO[FudusError, User] =
    (for {
      credentials <- credentialsRepository.findByUsername(username)
      _ <- ZIO.when(credentials.password != password)(
        ZIO.fail(FudusAuthenticationError(ErrorMessages.BadPassword))
      )
      user <- userRepository
        .findByUUID(credentials.user)
        .orElseFail(FudusAuthenticationError(ErrorMessages.UserNotFound))
    } yield user).refineToOrDie[FudusAuthenticationError]
}
