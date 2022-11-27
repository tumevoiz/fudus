package fudus.api.endpoints

import fudus.api.FudusServer.FudusServerEnv
import fudus.api.errors.FudusApiError
import fudus.api.model.{Category, Food, Restaurant}
import fudus.api.repository.RestaurantRepository
import fudus.api.services.{AuthenticationService, RestaurantFoodService, RestaurantService}
import fudus.api.types.SecureEndpoint
import sttp.tapir.ztapir._
import sttp.tapir.generic.auto._
import sttp.tapir.json.zio._
import sttp.tapir.ztapir.ZServerEndpoint

object RestaurantEndpoints {
  import fudus.api.encoder._

//  val listRestaurants: ZServerEndpoint[Unit, FudusApiError, List[Restaurant], Any] =
//    endpoint.get
//      .in("restaurants")
//      .out(jsonBody[List[Restaurant]])
//      .errorOut(jsonBody[FudusApiError])

//  val getRestaurantBySlug: ZServerEndpoint[FudusServerEnv, Any] =
//    endpoint.get
//      .in("restaurant" / path[String]("slug"))
//      .out(jsonBody[Restaurant])
//      .errorOut(jsonBody[FudusApiError])
//      .serverLogic(slug => RestaurantRepository.findBySlug(slug).mapAttempt(Right(_)))
//
  val getRestaurantBySlugFood: ZServerEndpoint[FudusServerEnv, Any] =
    baseEndpoint.get
      .in("restaurant" / path[String]("slug") / "food")
      .out(jsonBody[List[Food]])
      .serverLogic(slug =>
        RestaurantFoodService
          .fetchRestaurantFoodBySlug(slug)
          .mapAttempt(Right(_))
      )

  val createRestaurant: ZServerEndpoint[FudusServerEnv, Any] =
    secureEndpoint.post
      .in("restaurant")
      .in(jsonBody[Restaurant])
      .serverLogic(_ =>
        restaurant =>
          RestaurantService
            .create(
              restaurant.slug,
              restaurant.name,
              restaurant.description,
              restaurant.imageBase64
            )
            .mapError(e => FudusApiError(e.getMessage))
      )
}
