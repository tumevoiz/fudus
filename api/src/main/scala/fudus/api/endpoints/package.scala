package fudus.api

import fudus.api.errors.FudusApiError
import fudus.api.model.Domain.CustomerUUID
import fudus.api.services.AuthenticationService
import sttp.tapir.generic.auto._
import sttp.tapir.json.zio._
import sttp.tapir.ztapir._
import sttp.tapir.ztapir.ZPartialServerEndpoint
import zio._

package object endpoints {
  import fudus.api.encoder._

  val secureEndpoint: ZPartialServerEndpoint[
    AuthenticationService,
    String,
    CustomerUUID,
    Unit,
    FudusApiError,
    Unit,
    Any
  ] =
    endpoint
      .securityIn(auth.bearer[String]())
      .errorOut(jsonBody[FudusApiError])
      .zServerSecurityLogic(token => {
        AuthenticationService
          .authenticateByToken(token)
          .mapBoth(e => FudusApiError(e.getMessage), customerUUID => customerUUID)
      })

  val baseEndpoint: ZPartialServerEndpoint[Any, Unit, String, Unit, FudusApiError, Unit, Any] =
    endpoint.errorOut(jsonBody[FudusApiError]).zServerSecurityLogic(_ => ZIO.succeed("anonymous"))
}
