package fudus.api.services

import fudus.api.errors.{
  FudusError,
  FudusUserCreationError,
  FudusValidationError,
  ValidationMessages
}
import fudus.api.model.Domain.{Credentials, CredentialsUUID, Customer, CustomerRole, CustomerUUID}
import fudus.api.repository.{CredentialsRepository, CustomerRepository}
import zio._

import java.util.UUID

final case class CustomerService(
    credentialsRepository: CredentialsRepository,
    customerRepository: CustomerRepository
) {
  def create(
      username: String,
      password: String,
      email: String,
      role: CustomerRole.Type
  ): IO[FudusError, Unit] =
    for {
      _ <- validateUsername(username)
      _ <- validatePassword(password)
      _ <- validateEmail(email)

      customer = Customer(
        uuid = CustomerUUID(UUID.randomUUID().toString),
        email = email,
        address = "",
        city = "",
        role = role
      )

      credentials = Credentials(
        uuid = CredentialsUUID(UUID.randomUUID().toString),
        username = username,
        password = password,
        customer = customer.uuid
      )

      // TODO could be improved using STM or SAGA
      _ <- customerRepository
        .save(customer)
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

object CustomerService {
  val layer: ZLayer[CredentialsRepository with CustomerRepository, Throwable, CustomerService] =
    ZLayer.fromFunction(CustomerService.apply _)
}
