package fudus.api.model

import fudus.api.model.Domain.{Basket, Customer}
import fudus.api.model.Requests.LoginRequest
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder}

object Responses {

  import fudus.api.encoder._
  case class LoginResponse(token: String, customer: Customer)

  implicit val loginResponseEncoder: zio.json.JsonEncoder[LoginResponse] =
    DeriveJsonEncoder.gen[LoginResponse]
  implicit val loginResponseDecoder: zio.json.JsonDecoder[LoginResponse] =
    DeriveJsonDecoder.gen[LoginResponse]
}
