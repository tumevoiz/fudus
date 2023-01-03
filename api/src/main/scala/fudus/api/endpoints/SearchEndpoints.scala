package fudus.api.endpoints

import fudus.api.FudusServer.FudusServerEnv
import fudus.api.errors.FudusApiError
import fudus.api.model.Domain.Restaurant
import fudus.api.model.Requests.RestaurantSearchRequest
import fudus.api.services.SearchService
import sttp.tapir.EndpointInput
import sttp.tapir.ztapir._
import sttp.tapir.generic.auto._
import sttp.tapir.json.zio._
import zio._

object SearchEndpoints {
  import fudus.api.model.Requests._
  import fudus.api.model.Responses._
  import fudus.api.encoder._

  val search: ZServerEndpoint[FudusServerEnv, Any] =
    baseEndpoint.post
      .in("search")
      .in("restaurant")
      .in(jsonBody[RestaurantSearchRequest])
      .out(jsonBody[List[Restaurant]])
      .serverLogic(_ =>
        searchRequest =>
          SearchService.search(searchRequest.query).mapError(e => FudusApiError(e.getMessage))
      )
}
