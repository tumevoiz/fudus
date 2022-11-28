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
      .securityIn(auth.bearer[String]())
      .errorOut(jsonBody[FudusApiError])
      .zServerSecurityLogic(token =>
        AuthenticationService.authenticateByToken(token).mapError(e => FudusApiError(e.getMessage))
      )

  val baseEndpoint: ZPartialServerEndpoint[Any, Unit, String, Unit, FudusApiError, Unit, Any] =
    endpoint.errorOut(jsonBody[FudusApiError]).zServerSecurityLogic(_ => ZIO.succeed("Authorized"))
}
