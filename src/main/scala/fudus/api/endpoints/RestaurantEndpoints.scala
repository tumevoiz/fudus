package fudus.api.endpoints

import fudus.api.errors.{FudusApiError, FudusDatabaseError, FudusError}
import fudus.api.model.Restaurant
import sttp.tapir.generic.auto._
import sttp.tapir.json.zio._
import sttp.tapir._

object RestaurantEndpoints {
  import fudus.api.encoder._

  val listRestaurants: PublicEndpoint[Unit, FudusError, List[Restaurant], Any] =
    endpoint.get
      .in("restaurants")
      .out(jsonBody[List[Restaurant]])
      .errorOut(jsonBody[FudusError])
}
