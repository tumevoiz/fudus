package fudus.api

import fudus.api.errors.FudusError
import fudus.api.model.Restaurant
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder}

package object encoder {
  implicit val errorEncoder: zio.json.JsonEncoder[FudusError] = DeriveJsonEncoder.gen[FudusError]
  implicit val errorDecoder: zio.json.JsonDecoder[FudusError] = DeriveJsonDecoder.gen[FudusError]

  implicit val restaurantZioEncoder: zio.json.JsonEncoder[Restaurant] = DeriveJsonEncoder.gen[Restaurant]
  implicit val restaurantZioDecoder: zio.json.JsonDecoder[Restaurant] = DeriveJsonDecoder.gen[Restaurant]
}
