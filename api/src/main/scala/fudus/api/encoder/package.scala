package fudus.api

import fudus.api.errors.{FudusApiError, FudusError}
import fudus.api.model._
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

package object encoder {
  // UUID decoders
  implicit val categoryUuidDecoder: JsonDecoder[CategoryUUID] =
    JsonDecoder[String].map(CategoryUUID)
  implicit val categoryUuidEncoder: JsonEncoder[CategoryUUID] =
    JsonEncoder[String].contramap(_.value)

  implicit val foodUuidDecoder: JsonDecoder[FoodUUID] =
    JsonDecoder[String].map(FoodUUID)
  implicit val foodUuidEncoder: JsonEncoder[FoodUUID] =
    JsonEncoder[String].contramap(_.value)

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

  implicit val foodZioEncoder: zio.json.JsonEncoder[Food] = DeriveJsonEncoder.gen[Food]
  implicit val foodZioDecoder: zio.json.JsonDecoder[Food] = DeriveJsonDecoder.gen[Food]

  implicit val restaurantZioEncoder: zio.json.JsonEncoder[Restaurant] =
    DeriveJsonEncoder.gen[Restaurant]
  implicit val restaurantZioDecoder: zio.json.JsonDecoder[Restaurant] =
    DeriveJsonDecoder.gen[Restaurant]
}
