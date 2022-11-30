package fudus.api.model

case class CredentialsUUID(value: String) extends AnyVal
case class Credentials(
    uuid: CredentialsUUID,
    username: String,
    password: String,
    customer: CustomerUUID
)
