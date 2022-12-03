package fudus.api.endpoints

import fudus.api.FudusServer.FudusServerEnv
import fudus.api.errors.FudusApiError
import fudus.api.model.Domain.{Category, Restaurant}
import fudus.api.repository.CategoryRepository
import fudus.api.types.SecureEndpoint
import sttp.tapir.generic.auto._
import sttp.tapir.json.zio._
import sttp.tapir.ztapir._

object CategoryEndpoints {
  import fudus.api.encoder._

  val listCategories: ZServerEndpoint[FudusServerEnv, Any] =
    baseEndpoint.get
      .in("categories")
      .out(jsonBody[List[Category]])
      .serverLogic(_ => _ => CategoryRepository.findAll.mapError(e => FudusApiError(e.getMessage)))
}
