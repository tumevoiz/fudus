package fudus.api

import fudus.api.errors.{FudusApiError, FudusError}
import fudus.api.model.Restaurant
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder}

package object encoder {
  implicit val errorEncoder: zio.json.JsonEncoder[FudusApiError] = DeriveJsonEncoder.gen[FudusApiError]
  implicit val errorDecoder: zio.json.JsonDecoder[FudusApiError] = DeriveJsonDecoder.gen[FudusApiError]

  implicit val restaurantZioEncoder: zio.json.JsonEncoder[Restaurant] = DeriveJsonEncoder.gen[Restaurant]
  implicit val restaurantZioDecoder: zio.json.JsonDecoder[Restaurant] = DeriveJsonDecoder.gen[Restaurant]
}
