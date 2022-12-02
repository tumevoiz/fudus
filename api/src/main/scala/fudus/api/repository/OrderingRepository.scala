package fudus.api.repository

import fudus.api.model.Domain.{Ordering, OrderingUUID}
import fudus.api.services.DatabaseService
import zio._

final case class OrderingRepository(quillCtx: DatabaseService.QuillContext) {
  import quillCtx._

  import fudus.api.encoder.sql._

  def findAll: Task[List[Ordering]] =
    run {
      quote {
        query[Ordering]
      }
    }

  def findByUUID(uuid: OrderingUUID): Task[Option[Ordering]] =
    run {
      quote {
        query[Ordering].filter(_.uuid == lift(uuid))
      }
    }.map(_.headOption)

  def save(order: Ordering): Task[Long] =
    run {
      quote {
        query[Ordering].insertValue(lift(order))
      }
    }
}

object OrderingRepository {
  val layer: ZLayer[DatabaseService.QuillContext, Throwable, OrderingRepository] =
    ZLayer.fromFunction(OrderingRepository.apply _)
}
