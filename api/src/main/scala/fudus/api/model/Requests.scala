package fudus.api.model

import fudus.api.model.Domain.{Basket, BasketEntry}
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder}

object Requests {
  import fudus.api.encoder._
  case class LoginRequest(username: String, password: String)

  implicit val loginRequestEncoder: zio.json.JsonEncoder[LoginRequest] =
    DeriveJsonEncoder.gen[LoginRequest]
  implicit val loginRequestDecoder: zio.json.JsonDecoder[LoginRequest] =
    DeriveJsonDecoder.gen[LoginRequest]

  case class OrderingCreateRequest(basket: Basket)
  implicit val orderingCreateRequestEncoder: zio.json.JsonEncoder[OrderingCreateRequest] =
    DeriveJsonEncoder.gen[OrderingCreateRequest]
  implicit val orderingCreateRequestDecoder: zio.json.JsonDecoder[OrderingCreateRequest] =
    DeriveJsonDecoder.gen[OrderingCreateRequest]
}
