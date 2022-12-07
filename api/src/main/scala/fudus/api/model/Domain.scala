package fudus.api.model

import io.getquill.Embedded
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder}

object Domain {
  sealed trait HasUUID extends Product with Serializable {
    def value: String
  }

  case class CategoryUUID(value: String) extends HasUUID
  case class Category(uuid: CategoryUUID, name: String, icon: String)

  case class CredentialsUUID(value: String) extends HasUUID
  case class Credentials(
      uuid: CredentialsUUID,
      username: String,
      password: String,
      customer: CustomerUUID
  )

  object CustomerRole {
    sealed trait Type
    case object Admin extends Type
    case object Client extends Type
  }

  case class CustomerUUID(value: String) extends HasUUID

  case class Customer(
      uuid: CustomerUUID,
      email: String,
      address: String,
      city: String,
      role: CustomerRole.Type
  )

  case class FoodUUID(value: String) extends HasUUID

  case class Food(
      uuid: FoodUUID,
      name: String,
      categories: Seq[CategoryUUID],
      price: Float,
      restaurant: RestaurantUUID
  )

  case class OrderingUUID(value: String) extends AnyVal

  case class Ordering(
      uuid: OrderingUUID,
      orderedBy: CustomerUUID,
      basket: Basket,
      creationDate: String,
      hasPaid: Boolean
  )

  case class BasketEntry(food: FoodUUID, amount: Int, notes: String)
  type Basket = Seq[BasketEntry]

  case class RestaurantUUID(value: String) extends HasUUID

  case class Restaurant(
      uuid: RestaurantUUID,
      slug: String,
      name: String,
      description: String,
      imageBase64: String,
      rating: Float
  )

  case class TokenContent(customer: CustomerUUID) extends AnyVal
}
