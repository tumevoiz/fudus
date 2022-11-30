package fudus.api.model

object CustomerRole {
  sealed trait Type
  case object Admin extends Type
  case object Client extends Type
}

case class CustomerUUID(value: String) extends AnyVal

case class Customer(
    uuid: CustomerUUID,
    email: String,
    address: String,
    city: String,
    role: CustomerRole.Type
)
