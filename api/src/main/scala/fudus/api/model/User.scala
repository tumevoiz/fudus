package fudus.api.model

sealed trait UserRole
case object AdminRole extends UserRole
case object ClientRole extends UserRole

case class UserUUID(value: String) extends AnyVal

case class User(
    uuid: UserUUID,
    credentials: Credentials,
    email: String,
    address: String,
    city: String,
    role: UserRole
)
