package fudus.api.endpoints

import fudus.api.FudusServer.FudusServerEnv
import fudus.api.errors.FudusApiError
import fudus.api.services.{AuthenticationService, OrderingService}
import sttp.tapir.json.zio.jsonBody
import sttp.tapir.ztapir._
import sttp.tapir.generic.auto._

object OrderingEndpoint {
  import fudus.api.model.Requests._
  import fudus.api.model.Responses._
  import fudus.api.encoder._

  val createOrdering: ZServerEndpoint[FudusServerEnv, Any] =
    secureEndpoint.post
      .in("ordering")
      .in(jsonBody[OrderingCreateRequest])
      .out(stringBody)
      .serverLogic(customerUUID =>
        orderCreateRequest =>
          OrderingService
            .create(orderCreateRequest.basket, customerUUID)
            .mapBoth(e => FudusApiError(e.getMessage), _ => "OK")
      )
}
