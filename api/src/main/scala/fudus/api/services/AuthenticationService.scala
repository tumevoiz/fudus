package fudus.api.services

import fudus.api.errors.{ErrorMessages, FudusApiError, FudusAuthenticationError, FudusError}
import fudus.api.model.Customer
import fudus.api.repository.{CredentialsRepository, CustomerRepository}
import pdi.jwt.{Jwt, JwtAlgorithm, JwtClaim}

import java.time.Clock
import zio._

final case class AuthenticationService(
    credentialsRepository: CredentialsRepository,
    customerRepository: CustomerRepository
) {
  implicit val clock: Clock = Clock.systemUTC

  private val secretKey = "fuduslove"
  private val jwtAlgorithm = JwtAlgorithm.HS512

  def authenticate(username: String, password: String): IO[FudusError, String] =
    (for {
      credentials <- credentialsRepository
        .findByUsername(username)
        .someOrFail(FudusAuthenticationError(ErrorMessages.UserNotFound))
      _ <- ZIO.when(credentials.password != password)(
        ZIO.fail(FudusAuthenticationError(ErrorMessages.BadPassword))
      )
      _ <- customerRepository
        .findByUUID(credentials.customer)
        .someOrFail(FudusAuthenticationError(ErrorMessages.UserNotFound))
    } yield jwtEncode(credentials.username)).refineToOrDie[FudusAuthenticationError]

  def authenticateByToken(token: String): IO[FudusError, Unit] =
    for {
      _ <- ZIO.when(jwtDecode(token).isEmpty)(ZIO.fail(FudusAuthenticationError("Invalid token")))
    } yield () // TODO implement me

  private def jwtEncode(username: String): String = {
    val json = s"""{"user":"${username}"}"""
    val claim = JwtClaim {
      json
    }.issuedNow.expiresIn(300)
    Jwt.encode(claim, secretKey, jwtAlgorithm)
  }

  def jwtDecode(token: String): Option[JwtClaim] =
    Jwt.decode(token, secretKey, Seq(jwtAlgorithm)).toOption
}

object AuthenticationService {
  val layer
      : ZLayer[CredentialsRepository with CustomerRepository, Throwable, AuthenticationService] =
    ZLayer.fromFunction(AuthenticationService.apply _)

  def authenticate(
      username: String,
      password: String
  ): ZIO[AuthenticationService, FudusError, String] =
    ZIO.serviceWithZIO[AuthenticationService](_.authenticate(username, password))

  def authenticateByToken(token: String): ZIO[AuthenticationService, FudusError, Unit] =
    ZIO.serviceWithZIO[AuthenticationService](_.authenticateByToken(token))
}
