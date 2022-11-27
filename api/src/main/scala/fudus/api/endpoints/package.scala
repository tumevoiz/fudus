package fudus.api

import fudus.api.errors.FudusApiError
import fudus.api.services.AuthenticationService
import sttp.tapir.generic.auto._
import sttp.tapir.json.zio._
import sttp.tapir.ztapir._
import sttp.tapir.ztapir.ZPartialServerEndpoint
import zio._

package object endpoints {
  import fudus.api.encoder._

  val secureEndpoint
      : ZPartialServerEndpoint[AuthenticationService, String, Any, Unit, FudusApiError, Unit, Any] =
    endpoint
      .securityIn(header[String]("X-AUTH-TOKEN"))
      .errorOut(jsonBody[FudusApiError])
      .zServerSecurityLogic(token =>
        AuthenticationService.authenticateByToken(token).mapError(e => FudusApiError(e.getMessage))
      )

  val baseEndpoint =
    endpoint.errorOut(jsonBody[FudusApiError])
}
