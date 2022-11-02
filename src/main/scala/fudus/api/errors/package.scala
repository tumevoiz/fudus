package fudus.api

package object errors {
  sealed trait FudusError extends Throwable
  case class FudusDatabaseError(message: String) extends FudusError
  case class FudusServerError(message: String) extends FudusError

  case class FudusApiError(code: Int, message: String) extends FudusError
}
