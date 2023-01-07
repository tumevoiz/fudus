package fudus.api

import fudus.api.errors.{FudusApiError, FudusError}
import fudus.api.model.Domain._
import io.circe._
import sttp.tapir.Codec.PlainCodec
import sttp.tapir.{Codec, DecodeResult, Schema}
import sttp.tapir.generic.Derived
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

package object encoder {
  // UUID decoders
  implicit val categoryUuidDecoder: JsonDecoder[CategoryUUID] =
    JsonDecoder[String].map(CategoryUUID)
  implicit val categoryUuidEncoder: JsonEncoder[CategoryUUID] =
    JsonEncoder[String].contramap(_.value)

  implicit val credentialsUuidDecoder: JsonDecoder[CredentialsUUID] =
    JsonDecoder[String].map(CredentialsUUID)
  implicit val credentialsUuidEncoder: JsonEncoder[CredentialsUUID] =
    JsonEncoder[String].contramap(_.value)

  implicit val customerUuidDecoder: JsonDecoder[CustomerUUID] =
    JsonDecoder[String].map(CustomerUUID)
  implicit val customerUuidEncoder: JsonEncoder[CustomerUUID] =
    JsonEncoder[String].contramap(_.value)

  implicit val foodUuidDecoder: JsonDecoder[FoodUUID] =
    JsonDecoder[String].map(FoodUUID)
  implicit val foodUuidEncoder: JsonEncoder[FoodUUID] =
    JsonEncoder[String].contramap(_.value)

  implicit val orderingUuidDecoder: JsonDecoder[OrderingUUID] =
    JsonDecoder[String].map(OrderingUUID)
  implicit val orderingUuidEncoder: JsonEncoder[OrderingUUID] =
    JsonEncoder[String].contramap(_.value)

  implicit val foodUuidCodec: Encoder[FoodUUID] =
    Encoder.encodeString.contramap(s => s.value)

  implicit val restaurantUuidDecoder: JsonDecoder[RestaurantUUID] =
    JsonDecoder[String].map(RestaurantUUID)
  implicit val restaurantUuidEncoder: JsonEncoder[RestaurantUUID] =
    JsonEncoder[String].contramap(_.value)

  implicit val errorEncoder: zio.json.JsonEncoder[FudusApiError] =
    DeriveJsonEncoder.gen[FudusApiError]
  implicit val errorDecoder: zio.json.JsonDecoder[FudusApiError] =
    DeriveJsonDecoder.gen[FudusApiError]

  implicit val categoryZioEncoder: zio.json.JsonEncoder[Category] = DeriveJsonEncoder.gen[Category]
  implicit val categoryZioDecoder: zio.json.JsonDecoder[Category] = DeriveJsonDecoder.gen[Category]

  import fudus.api.model.Ops._
  implicit val customerRoleZioEncoder: JsonDecoder[CustomerRole.Type] =
    JsonDecoder[String].map(_.asUserRole)
  implicit val customerRoleZioDecoder: JsonEncoder[CustomerRole.Type] =
    JsonEncoder[String].contramap(_.asString)

  implicit val customerZioEncoder: zio.json.JsonEncoder[Customer] = DeriveJsonEncoder.gen[Customer]
  implicit val customerZioDecoder: zio.json.JsonDecoder[Customer] = DeriveJsonDecoder.gen[Customer]

  implicit val foodZioEncoder: zio.json.JsonEncoder[Food] = DeriveJsonEncoder.gen[Food]
  implicit val foodZioDecoder: zio.json.JsonDecoder[Food] = DeriveJsonDecoder.gen[Food]

  implicit val restaurantZioEncoder: zio.json.JsonEncoder[Restaurant] =
    DeriveJsonEncoder.gen[Restaurant]
  implicit val restaurantZioDecoder: zio.json.JsonDecoder[Restaurant] =
    DeriveJsonDecoder.gen[Restaurant]

  implicit val basketEncoder: zio.json.JsonEncoder[BasketEntry] =
    DeriveJsonEncoder.gen[BasketEntry]
  implicit val basketDecoder: zio.json.JsonDecoder[BasketEntry] =
    DeriveJsonDecoder.gen[BasketEntry]

  implicit val orderingZioEncoder: zio.json.JsonEncoder[Ordering] =
    DeriveJsonEncoder.gen[Ordering]
  implicit val orderingZioDecoder: zio.json.JsonDecoder[Ordering] =
    DeriveJsonDecoder.gen[Ordering]

  implicit val tokenContentEncoder: zio.json.JsonEncoder[TokenContent] =
    DeriveJsonEncoder.gen[TokenContent]
  implicit val tokenContentDecoder: zio.json.JsonDecoder[TokenContent] =
    DeriveJsonDecoder.gen[TokenContent]
}
