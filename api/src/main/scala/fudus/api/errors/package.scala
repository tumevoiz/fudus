package fudus.api

package object errors {
  trait FudusError extends Throwable
  case class FudusDatabaseError(message: String) extends FudusError
  case class FudusServerError(message: String) extends FudusError

  case class FudusPropagationError(message: String) extends FudusError
  case class FudusAuthenticationError(message: String) extends FudusError

  case class FudusUserCreationError(message: String) extends FudusError
  case class FudusValidationError(message: String) extends FudusError

  case class FudusApiError(message: String)

  object ErrorMessages {
    val BadPassword = "Bad password"
    val UserNotFound = "User not found"
  }

  object ValidationMessages {
    val UsernameTooShort = "Username is too short"
    val UsernameTooLong = "Username is too long"
    val PasswordTooShort = "Password is too short"
    val PasswordTooLong = "Password is too long"
    val EmailInvalidFormat = "Invalid format of email"
    val EmailTooShort = "Email is too short"
    val EmailTooLong = "Email is too long"
  }
}
