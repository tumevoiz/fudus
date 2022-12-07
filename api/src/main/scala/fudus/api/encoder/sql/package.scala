package fudus.api.encoder

import fudus.api.model.Domain._
import io.getquill.MappedEncoding
import io.getquill._

package object sql {
  implicit val encodeCategoryUUID: MappedEncoding[String, CategoryUUID] =
    MappedEncoding(CategoryUUID)
  implicit val decodeCategoryUUID: MappedEncoding[CategoryUUID, String] =
    MappedEncoding(_.value)

  implicit val encodeCredentialsUUID: MappedEncoding[String, CredentialsUUID] =
    MappedEncoding(CredentialsUUID)
  implicit val decodeCredentialsUUID: MappedEncoding[CredentialsUUID, String] =
    MappedEncoding(_.value)

  implicit val encodeFoodUUID: MappedEncoding[String, FoodUUID] =
    MappedEncoding(FoodUUID)
  implicit val decodeFoodUUID: MappedEncoding[FoodUUID, String] =
    MappedEncoding(_.value)

  implicit val encodeRestaurantUUID: MappedEncoding[String, RestaurantUUID] =
    MappedEncoding(RestaurantUUID)
  implicit val decodeRestaurantUUID: MappedEncoding[RestaurantUUID, String] =
    MappedEncoding(_.value)

  implicit val encodeUserUUID: MappedEncoding[String, CustomerUUID] =
    MappedEncoding(CustomerUUID)
  implicit val decodeUserUUID: MappedEncoding[CustomerUUID, String] =
    MappedEncoding(_.value)

  import fudus.api.encoder.{basketEncoder, basketDecoder}
  import zio.json._

  implicit val basketSqlEncoder: MappedEncoding[String, BasketEntry] =
    MappedEncoding(
      _.fromJson[BasketEntry].getOrElse(throw new RuntimeException("sql encoder failure"))
    )
  implicit val basketSqlDecoder: MappedEncoding[BasketEntry, String] =
    MappedEncoding(_.toJson)

}
