package fudus.api.model

object UserRole {
  sealed trait Type
  case object Admin extends Type
  case object Client extends Type
}

case class UserUUID(value: String) extends AnyVal

case class User(
    uuid: UserUUID,
    email: String,
    address: String,
    city: String,
    role: UserRole.Type
)
