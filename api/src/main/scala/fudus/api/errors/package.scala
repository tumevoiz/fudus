package fudus.api

package object errors {
  trait FudusError extends Throwable {
    def message: String

    override def getMessage: String = message
  }

  case class FudusDatabaseError(message: String) extends FudusError
  case class FudusServerError(message: String) extends FudusError

  case class FudusPropagationError(message: String) extends FudusError
  case class FudusAuthenticationError(message: String) extends FudusError

  case class FudusUserCreationError(message: String) extends FudusError
  case class FudusRestaurantCreationError(message: String) extends FudusError
  case class FudusFoodCreationError(message: String) extends FudusError
  case class FudusValidationError(message: String) extends FudusError

  case class FudusApiError(message: String) {
    override def toString: String = message
  }

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

    val SlugTooLong = "Slug is too long"
    val SlugTooShort = "Slug is too short"
    val SlugInvalidCharacters = "Slug contains invalid characters"
    val SlugBigLetters = "Slug contains big letters"

    val RestaurantNameTooLong = "Restaurant name is too long"
    val RestaurantNameTooShort = "Restaurant name is too short"

    val CategoryDoesNotExists = "Category does not exists"
    val RestaurantDoesNotExists = "Restaurant does not exists"
  }
}
