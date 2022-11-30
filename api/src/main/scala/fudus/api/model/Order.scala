package fudus.api.model

import io.getquill.Embedded

case class OrderUUID(value: String) extends AnyVal

case class Order(
    uuid: OrderUUID,
    by: Customer,
    restaurantUUID: String,
    orderedFoods: Seq[OrderedFood],
    creationDate: String,
    hasPaid: Boolean
)

case class OrderedFood(food: FoodUUID, amount: Int, notes: String) extends Embedded
