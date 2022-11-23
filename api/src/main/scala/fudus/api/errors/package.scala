package fudus.api

package object errors {
  trait FudusError extends Throwable
  case class FudusDatabaseError(message: String) extends FudusError
  case class FudusServerError(message: String) extends FudusError

  case class FudusPropagationError(message: String) extends FudusError

  case class FudusApiError(message: String)
}
