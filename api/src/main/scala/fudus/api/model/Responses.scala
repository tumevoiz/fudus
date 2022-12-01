package fudus.api.model

import fudus.api.model.Domain.Basket
import fudus.api.model.Requests.LoginRequest
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder}

object Responses {
  case class LoginResponse(token: String)

  implicit val loginResponseEncoder: zio.json.JsonEncoder[LoginResponse] =
    DeriveJsonEncoder.gen[LoginResponse]
  implicit val loginResponseDecoder: zio.json.JsonDecoder[LoginResponse] =
    DeriveJsonDecoder.gen[LoginResponse]
}
