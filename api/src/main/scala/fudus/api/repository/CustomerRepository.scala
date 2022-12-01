package fudus.api.repository

import fudus.api.model.Domain.{Customer, CustomerRole, CustomerUUID}
import fudus.api.services.DatabaseService
import zio._

final case class CustomerRepository(quillCtx: DatabaseService.QuillContext) {
  import quillCtx._

  import fudus.api.encoder.sql._

  import fudus.api.model.Ops.{UserRoleOps0, StringOps0}

  implicit val encodeUserRole: MappedEncoding[CustomerRole.Type, String] =
    MappedEncoding[CustomerRole.Type, String](_.asString)
  implicit val decodeUserRole: MappedEncoding[String, CustomerRole.Type] =
    MappedEncoding[String, CustomerRole.Type](_.asUserRole)

  def findByUUID(uuid: CustomerUUID): Task[Option[Customer]] =
    run {
      quote {
        query[Customer].filter(_.uuid == lift(uuid))
      }
    }.map(_.headOption)

  def save(customer: Customer): Task[Unit] =
    run {
      quote {
        query[Customer].insertValue(lift(customer))
      }
    }.as[Unit]
}

object CustomerRepository {
  val layer: ZLayer[DatabaseService.QuillContext, Throwable, CustomerRepository] =
    ZLayer.fromFunction(CustomerRepository.apply _)
}
