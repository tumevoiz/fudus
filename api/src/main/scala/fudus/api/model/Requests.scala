package fudus.api.model

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder}

object Requests {
  case class LoginRequest(username: String, password: String)

  implicit val loginRequestEncoder: zio.json.JsonEncoder[LoginRequest] =
    DeriveJsonEncoder.gen[LoginRequest]
  implicit val loginRequestDecoder: zio.json.JsonDecoder[LoginRequest] =
    DeriveJsonDecoder.gen[LoginRequest]
}
