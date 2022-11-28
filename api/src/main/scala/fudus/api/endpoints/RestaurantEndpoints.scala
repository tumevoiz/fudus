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

object RestaurantEndpoints {
  import fudus.api.encoder._

  val listRestaurants: ZServerEndpoint[FudusServerEnv, Any] =
    baseEndpoint.get
      .in("restaurants")
      .out(jsonBody[List[Restaurant]])
      .serverLogic(_ =>
        _ => RestaurantRepository.findAll.mapError(e => FudusApiError(e.getMessage))
      )

  val getRestaurantBySlug: ZServerEndpoint[FudusServerEnv, Any] =
    baseEndpoint.get
      .in("restaurant" / path[String]("slug"))
      .out(jsonBody[Restaurant])
      .serverLogic(_ =>
        slug =>
          RestaurantRepository.findBySlug(slug).mapBoth(e => FudusApiError(e.getMessage), _.get)
      )

  val getRestaurantBySlugFood: ZServerEndpoint[FudusServerEnv, Any] =
    baseEndpoint.get
      .in("restaurant" / path[String]("slug") / "food")
      .out(jsonBody[List[Food]])
      .serverLogic(_ =>
        slug =>
          RestaurantFoodService
            .fetchRestaurantFoodBySlug(slug)
            .mapError(e => {
              FudusApiError(e.getMessage)
            })
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
