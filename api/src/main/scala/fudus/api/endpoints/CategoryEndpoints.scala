package fudus.api.endpoints

import fudus.api.errors.FudusApiError
import fudus.api.model.{Category, Restaurant}
import fudus.api.types.SecureEndpoint
import sttp.tapir._
import sttp.tapir.generic.auto._
import sttp.tapir.json.zio._

object CategoryEndpoints {
  import fudus.api.encoder._

  val listCategories: PublicEndpoint[Unit, FudusApiError, List[Category], Any] =
    endpoint.get
      .in("categories")
      .out(jsonBody[List[Category]])
      .errorOut(jsonBody[FudusApiError])

  val addCategory: SecureEndpoint[Category, FudusApiError, Category, Any] =
    endpoint.post
      .securityIn(auth.bearer[String]())
      .in("categories")
      .in(jsonBody[Category])
      .out(jsonBody[Category])
      .errorOut(jsonBody[FudusApiError])
}
