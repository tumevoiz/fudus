package fudus.api.endpoints

import fudus.api.errors.FudusApiError
import fudus.api.model.{Food, Restaurant}
import sttp.tapir._
import sttp.tapir.generic.auto._
import sttp.tapir.json.zio._

object RestaurantEndpoints {
  import fudus.api.encoder._

  val listRestaurants: PublicEndpoint[Unit, FudusApiError, List[Restaurant], Any] =
    endpoint.get
      .in("restaurants")
      .out(jsonBody[List[Restaurant]])
      .errorOut(jsonBody[FudusApiError])

  val getRestaurantBySlug: PublicEndpoint[String, FudusApiError, Restaurant, Any] =
    endpoint.get
      .in("restaurant" / path[String]("slug"))
      .out(jsonBody[Restaurant])
      .errorOut(jsonBody[FudusApiError])

  val getRestaurantBySlugFood: PublicEndpoint[String, FudusApiError, List[Food], Any] =
    endpoint.get
      .in("restaurant" / path[String]("slug") / "food")
      .out(jsonBody[List[Food]])
      .errorOut(jsonBody[FudusApiError])
}
