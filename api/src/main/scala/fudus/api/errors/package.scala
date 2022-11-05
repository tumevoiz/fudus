package fudus.api

import java.sql.SQLException

package object errors {
  sealed trait FudusError extends Throwable
  case class FudusDatabaseError(message: String) extends FudusError
  case class FudusDatabaseException(message: String) extends SQLException with FudusError
  case class FudusServerError(message: String) extends FudusError

  case class FudusApiError(message: String) extends SQLException
}
