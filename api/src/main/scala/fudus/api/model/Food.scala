package fudus.api.model

case class FoodUUID(value: String) extends AnyVal

case class Food(
    uuid: FoodUUID,
    name: String,
    categories: Seq[CategoryUUID],
    price: Float,
    restaurant: RestaurantUUID
)
