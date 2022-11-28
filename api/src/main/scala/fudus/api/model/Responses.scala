package fudus.api.model

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder}

object Responses {
  case class LoginResponse(token: String)

  implicit val loginRequestEncoder: zio.json.JsonEncoder[LoginResponse] =
    DeriveJsonEncoder.gen[LoginResponse]
  implicit val loginRequestDecoder: zio.json.JsonDecoder[LoginResponse] =
    DeriveJsonDecoder.gen[LoginResponse]
}
