package fudus.api.endpoints

import fudus.api.FudusServer.FudusServerEnv
import fudus.api.errors.FudusApiError
import fudus.api.model.Food
import fudus.api.services.{AuthenticationService, RestaurantFoodService}
import sttp.tapir.ztapir._
import sttp.tapir.generic.auto._
import sttp.tapir.json.zio._

object RootEndpoints {
  import fudus.api.model.Requests._
  import fudus.api.model.Responses._
  import fudus.api.encoder._

  val login: ZServerEndpoint[FudusServerEnv, Any] =
    baseEndpoint.post
      .in("login")
      .in(jsonBody[LoginRequest])
      .out(jsonBody[LoginResponse])
      .serverLogic(_ =>
        loginRequest =>
          AuthenticationService
            .authenticate(loginRequest.username, loginRequest.password)
            .mapBoth(e => FudusApiError(e.getMessage), token => LoginResponse(token))
      )
}
