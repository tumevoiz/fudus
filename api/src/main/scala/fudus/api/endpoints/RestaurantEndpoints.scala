package fudus.api.endpoints

import fudus.api.errors.{FudusApiError, FudusError}
import fudus.api.model.Restaurant
import sttp.tapir._
import sttp.tapir.generic.auto._
import sttp.tapir.json.zio._

object RestaurantEndpoints {
  import fudus.api.encoder._

  val listRestaurants: PublicEndpoint[Unit, FudusApiError, List[Restaurant], Any] =
    endpoint.get
      .in("restaurants")
      .out(jsonBody[List[Restaurant]])
//      .errorOut(stringBody)
      .errorOut(jsonBody[FudusApiError])
}
