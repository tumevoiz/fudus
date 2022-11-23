package fudus.api.model

case class RestaurantUUID(value: String) extends AnyVal

case class Restaurant(
    uuid: RestaurantUUID,
    slug: String,
    name: String,
    description: String,
    imageBase64: String,
    rating: Float
)
