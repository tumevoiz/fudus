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

  case class RegisterRequest(
      username: String,
      password: String,
      email: String,
      address: String,
      city: String
  )
  implicit val registerRequestEncoder: zio.json.JsonEncoder[RegisterRequest] =
    DeriveJsonEncoder.gen[RegisterRequest]
  implicit val registerRequestDecoder: zio.json.JsonDecoder[RegisterRequest] =
    DeriveJsonDecoder.gen[RegisterRequest]

  case class OrderingCreateRequest(basket: Basket)
  implicit val orderingCreateRequestEncoder: zio.json.JsonEncoder[OrderingCreateRequest] =
    DeriveJsonEncoder.gen[OrderingCreateRequest]
  implicit val orderingCreateRequestDecoder: zio.json.JsonDecoder[OrderingCreateRequest] =
    DeriveJsonDecoder.gen[OrderingCreateRequest]

  case class RestaurantSearchRequest(query: String)
  implicit val restaurantSearchRequestEncoder: zio.json.JsonEncoder[RestaurantSearchRequest] =
    DeriveJsonEncoder.gen[RestaurantSearchRequest]
  implicit val restaurantSearchRequestDecoder: zio.json.JsonDecoder[RestaurantSearchRequest] =
    DeriveJsonDecoder.gen[RestaurantSearchRequest]

}
