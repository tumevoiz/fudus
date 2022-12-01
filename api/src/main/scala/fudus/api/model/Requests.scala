package fudus.api.model

import fudus.api.model.Domain.{Basket}
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder}

object Requests {
  case class LoginRequest(username: String, password: String)

  implicit val loginRequestEncoder: zio.json.JsonEncoder[LoginRequest] =
    DeriveJsonEncoder.gen[LoginRequest]
  implicit val loginRequestDecoder: zio.json.JsonDecoder[LoginRequest] =
    DeriveJsonDecoder.gen[LoginRequest]

  case class OrderCreateRequest(basket: Basket)
  implicit val orderCreateRequestEncoder: zio.json.JsonEncoder[LoginRequest] =
    DeriveJsonEncoder.gen[LoginRequest]
  implicit val orderCreateRequestDecoder: zio.json.JsonDecoder[LoginRequest] =
    DeriveJsonDecoder.gen[LoginRequest]
}
