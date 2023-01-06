package fudus.api.services

import cats.data.OptionT
import cats.implicits._
import fudus.api.errors.{FudusError, FudusSearchError}
import fudus.api.model.Domain._
import fudus.api.repository._
import zio._

final case class SearchService(
    categoryRepository: CategoryRepository,
    foodRepository: FoodRepository,
    restaurantRepository: RestaurantRepository,
    restaurantService: RestaurantService
) {
  def search(query: String): IO[FudusError, List[Restaurant]] = for {
    restaurantsByName <- searchRestaurantByName(query)
    restaurantsByCategory <- searchRestaurantByCategory(query)
  } yield restaurantsByName.concat(restaurantsByCategory)

  def searchRestaurantByName(name: String): IO[FudusError, List[Restaurant]] = for {
    _ <- restaurantService.validateName(name)
    restaurants <- restaurantRepository.likeName(name).mapError(e => FudusSearchError(e.getMessage))
  } yield restaurants

  def searchRestaurantByCategory(categoryName: String): IO[FudusError, List[Restaurant]] = for {
    categories <- categoryRepository
      .likeName(categoryName)
      .mapError(e => FudusSearchError(e.getMessage))
    food <- ZIO
      .foreach(categories)(category => foodRepository.findByCategoryUUID(category.uuid))
      .mapError(e => FudusSearchError(e.getMessage))
    restaurants <- ZIO
      .foreach(food.flatten)(fd => restaurantRepository.findByUUID(fd.restaurant))
      .mapError(e => FudusSearchError(e.getMessage))
  } yield restaurants.traverse(identity).getOrElse(List.empty).distinct
}

object SearchService {
  val layer: ZLayer[
    CategoryRepository with FoodRepository with RestaurantRepository with RestaurantService,
    Throwable,
    SearchService
  ] =
    ZLayer.fromFunction(SearchService.apply _)

  def search(
      query: String
  ): ZIO[SearchService, FudusError, List[Restaurant]] =
    ZIO.serviceWithZIO[SearchService](_.search(query))

  def searchRestaurantByName(
      name: String
  ): ZIO[SearchService, FudusError, List[Restaurant]] =
    ZIO.serviceWithZIO[SearchService](_.searchRestaurantByName(name))

  def searchRestaurantByCategory(
      categoryName: String
  ): ZIO[SearchService, FudusError, List[Restaurant]] =
    ZIO.serviceWithZIO[SearchService](_.searchRestaurantByCategory(categoryName))
}
