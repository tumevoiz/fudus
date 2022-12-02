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
  Ordering,
  OrderingUUID,
  RestaurantUUID
}
import fudus.api.repository.{
  CustomerRepository,
  FoodRepository,
  OrderingRepository,
  RestaurantRepository
}
import zio._

import java.util.UUID

final case class OrderingService(
    customerRepository: CustomerRepository,
    foodRepository: FoodRepository,
    orderRepository: OrderingRepository
) {
  def create(basket: Basket, orderedBy: CustomerUUID): IO[FudusError, Unit] =
    (for {
      _ <- ZIO
        .foreach(basket)(entry => validateUUID(entry.food))
        .mapError(e => FudusOrderingError(e.getMessage))
      order = Ordering(
        uuid = OrderingUUID(UUID.randomUUID().toString),
        orderedBy = orderedBy,
        basket = basket,
        creationDate = java.time.LocalDate.now().toString,
        hasPaid = false
      )
      _ <- orderRepository
        .save(order)
        .orElseFail(FudusOrderingError(ErrorMessages.GenericDbSaveError))
        .tapError(e => ZIO.logError(s"Error cause: ${e.getMessage}"))
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

object OrderingService {
  val layer: ZLayer[
    CustomerRepository with FoodRepository with OrderingRepository,
    Throwable,
    OrderingService
  ] =
    ZLayer.fromFunction(OrderingService.apply _)

  def create(
      basket: Basket,
      orderedBy: CustomerUUID
  ): ZIO[OrderingService, FudusError, Unit] =
    ZIO.serviceWithZIO[OrderingService](_.create(basket, orderedBy))
}
