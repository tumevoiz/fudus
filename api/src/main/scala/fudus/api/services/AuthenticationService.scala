package fudus.api.services

import fudus.api.errors.{ErrorMessages, FudusApiError, FudusAuthenticationError, FudusError}
import fudus.api.model.Domain.{Customer, CustomerUUID, TokenContent}
import fudus.api.repository.{CredentialsRepository, CustomerRepository}
import pdi.jwt.{Jwt, JwtAlgorithm, JwtClaim}

import java.time.Clock
import zio._
import zio.json._

final case class AuthenticationService(
    credentialsRepository: CredentialsRepository,
    customerRepository: CustomerRepository
) {
  implicit val clock: Clock = Clock.systemUTC

  private val secretKey = "fuduslove"
  private val jwtAlgorithm = JwtAlgorithm.HS512

  import fudus.api.encoder._

  def authenticate(username: String, password: String): IO[FudusError, String] =
    (for {
      credentials <- credentialsRepository
        .findByUsername(username)
        .someOrFail(FudusAuthenticationError(ErrorMessages.UserNotFound))
      _ <- ZIO.when(credentials.password != password)(
        ZIO.fail(FudusAuthenticationError(ErrorMessages.BadPassword))
      )
      customer <- customerRepository
        .findByUUID(credentials.customer)
        .someOrFail(FudusAuthenticationError(ErrorMessages.UserNotFound))
    } yield jwtEncode(customer.uuid)).refineToOrDie[FudusAuthenticationError]

  def authenticateByToken(token: String): IO[FudusError, CustomerUUID] =
    (for {
      decodedToken <- ZIO
        .attempt(jwtDecode(token))
        .someOrFail(FudusAuthenticationError("Unauthorized token"))
    } yield decodedToken.content.fromJson[TokenContent] match {
      case Right(dt) => dt.customer
      case Left(_) => {
        ZIO.fail(FudusAuthenticationError("Malformed token"))
        CustomerUUID("invalid")
      }
    }).refineToOrDie[FudusAuthenticationError] // TODO implement me

  private def jwtEncode(customer: CustomerUUID): String = {
    val json = TokenContent(customer).toJson
    val claim = JwtClaim {
      json
    }.issuedNow.expiresIn(3600)
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

  def authenticateByToken(token: String): ZIO[AuthenticationService, FudusError, CustomerUUID] =
    ZIO.serviceWithZIO[AuthenticationService](_.authenticateByToken(token))
}
