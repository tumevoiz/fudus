package fudus.api.model

case class CategoryUUID(value: String) extends AnyVal
case class Category(uuid: CategoryUUID, name: String, icon: String)
