package fudus.api.repository

import fudus.api.model.Restaurant
import fudus.api.errors.{FudusDatabaseError, FudusError}
import zio.{ZIO, ZLayer}

trait RestaurantRepository {
  def getBySlug(slug: String): ZIO[Any, FudusDatabaseError, Restaurant]
  def listRestaurants: ZIO[Any, FudusDatabaseError, List[Restaurant]]
  def save(restaurant: Restaurant): ZIO[Any, FudusDatabaseError, String]
}

// TODO: use @accessible
object RestaurantRepository {
  def getBySlug(slug: String): ZIO[RestaurantRepository, FudusDatabaseError, Restaurant] =
    ZIO.serviceWithZIO[RestaurantRepository](_.getBySlug(slug))

  def listRestaurants: ZIO[RestaurantRepository, FudusDatabaseError, List[Restaurant]] =
    ZIO.serviceWithZIO[RestaurantRepository](_.listRestaurants)

  def save(restaurant: Restaurant): ZIO[RestaurantRepository, FudusDatabaseError, String] =
    ZIO.serviceWithZIO[RestaurantRepository](_.save(restaurant))
}

case class InmemoryRestaurantRepository(restaurants: List[Restaurant]) extends RestaurantRepository {
  override def getBySlug(slug: String): ZIO[Any, FudusDatabaseError, Restaurant] =
    ZIO.fromOption(restaurants.find(_.slug == slug))
      .orElseFail(FudusDatabaseError("restaurant not found"))

  override def save(restaurant: Restaurant): ZIO[Any, FudusDatabaseError, String] =
    ZIO.fail(FudusDatabaseError("database inmemory read-only")) // TODO make it mutable with ref

  override def listRestaurants: ZIO[Any, FudusDatabaseError, List[Restaurant]] =
    ZIO.succeed(restaurants)
}

object InmemoryRestaurantRepository {
  val layer: ZLayer[Any, FudusError, RestaurantRepository] =
    ZLayer.succeed(InmemoryRestaurantRepository(List.empty))

  val layerWithMockData: ZLayer[Any, FudusError, RestaurantRepository] =
    ZLayer.succeed {
      InmemoryRestaurantRepository((1 to 10).map(
        i => Restaurant(s"restaurant-$i", s"Restaurant #$i", s"Restaurant #$i", "NULL", 5)
      ).toList)
    }
}

object PersistentRestaurantRepository {
  // TODO implement persistent version with quill
}