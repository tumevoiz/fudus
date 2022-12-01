package fudus.api.services

import fudus.api.errors.{
  ErrorMessages,
  FudusDatabaseError,
  FudusError,
  FudusOrderingError,
  FudusValidationError,
  ValidationMessages
}
import fudus.api.model.Domain.{
  Basket,
  BasketEntry,
  CustomerUUID,
  FoodUUID,
  HasUUID,
  Order,
  OrderUUID,
  RestaurantUUID
}
import fudus.api.repository.{
  CustomerRepository,
  FoodRepository,
  OrderRepository,
  RestaurantRepository
}
import zio._

import java.util.UUID

final case class OrderService(
    customerRepository: CustomerRepository,
    foodRepository: FoodRepository,
    orderRepository: OrderRepository
) {
  def create(basket: Basket, orderedBy: CustomerUUID): IO[FudusError, Unit] =
    (for {
      _ <- ZIO.foreach(basket)(entry => validateUUID(entry.food))
      order = Order(
        uuid = OrderUUID(UUID.randomUUID().toString),
        by = orderedBy,
        basket = basket,
        creationDate = java.time.LocalDate.now().toString,
        hasPaid = false
      )
      _ <- orderRepository
        .save(order)
        .orElseFail(FudusOrderingError(ErrorMessages.GenericDbSaveError))
        .tapError(e => ZIO.logError(e.getMessage))
    } yield ()).refineToOrDie[FudusOrderingError]

  // TODO move it to domain
  private[this] def validateUUID[U <: HasUUID](uuid: U): IO[FudusError, Unit] =
    for {
      _ <- ZIO.when(uuid.value.isEmpty)(
        ZIO.fail(FudusValidationError(ValidationMessages.GenericUUIDEmpty))
      )
      _ <- ZIO.when(!uuid.value.contains("-"))(
        ZIO.fail(FudusValidationError(ValidationMessages.GenericUUIDInvalid))
      )
    } yield ()
}

object OrderService {
  val layer: ZLayer[
    CustomerRepository with FoodRepository with OrderRepository,
    Throwable,
    OrderService
  ] =
    ZLayer.fromFunction(OrderService.apply _)

  def create(
      orderedFood: Seq[BasketEntry],
      orderedBy: CustomerUUID
  ): ZIO[OrderService, FudusError, Unit] =
    ZIO.serviceWithZIO[OrderService](_.create(orderedFood, orderedBy))
}
