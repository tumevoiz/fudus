package fudus.api.endpoints

import fudus.api.FudusServer.FudusServerEnv
import fudus.api.errors.FudusApiError
import fudus.api.services.{AuthenticationService, OrderService}
import sttp.tapir.json.zio.jsonBody
import sttp.tapir.ztapir._

object OrderEndpoint {
  import fudus.api.model.Requests._
  import fudus.api.model.Responses._
  import fudus.api.encoder._

  val createOrder: ZServerEndpoint[FudusServerEnv, Any] =
    secureEndpoint.post
      .in("order")
      .in(jsonBody[OrderCreateRequest])
      .out(stringBody)
      .serverLogic(customerUUID =>
        orderCreateRequest =>
          OrderService
            .create(orderCreateRequest.basket, customerUUID)
            .mapBoth(e => FudusApiError(e.getMessage), _ => "OK")
      )
}
