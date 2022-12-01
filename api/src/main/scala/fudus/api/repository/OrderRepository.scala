package fudus.api.repository

import fudus.api.model.Domain.{Order, OrderUUID}
import fudus.api.services.DatabaseService
import zio._

final case class OrderRepository(quillCtx: DatabaseService.QuillContext) {
  import quillCtx._

  import fudus.api.encoder.sql._

  def findAll: Task[List[Order]] =
    run {
      quote {
        query[Order]
      }
    }

  def findByUUID(uuid: OrderUUID): Task[Option[Order]] =
    run {
      quote {
        query[Order].filter(_.uuid == lift(uuid))
      }
    }.map(_.headOption)

  def save(order: Order): Task[Unit] =
    run {
      quote {
        query[Order].insertValue(lift(order))
      }
    }.as[Unit]
}

object OrderRepository {
  val layer: ZLayer[DatabaseService.QuillContext, Throwable, OrderRepository] =
    ZLayer.fromFunction(OrderRepository.apply _)
}
