package fudus.api.services

import fudus.api.errors.{
  FudusDatabaseError,
  FudusError,
  FudusRestaurantCreationError,
  FudusValidationError,
  ValidationMessages
}
import fudus.api.model.Domain.{Restaurant, RestaurantUUID}
import fudus.api.repository.RestaurantRepository
import zio._

import java.util.UUID

final case class RestaurantService(restaurantRepository: RestaurantRepository) {
  private val defaultRating = 0.0f

  def create(
      slug: String,
      name: String,
      description: String,
      imageBase64: String
  ): IO[FudusError, Unit] = for {
    _ <- validateSlug(slug)
    _ <- validateName(name)
    restaurant = Restaurant(
      uuid = RestaurantUUID(UUID.randomUUID().toString),
      slug = slug,
      name = name,
      description = description,
      imageBase64 = imageBase64,
      rating = defaultRating
    )
    _ <- restaurantRepository
      .save(restaurant)
      .mapError(e => FudusRestaurantCreationError(e.getMessage))
  } yield ()

  private def validateSlug(slug: String): IO[FudusError, Unit] =
    for {
      _ <- ZIO.when(slug.length < 4)(
        ZIO.fail(FudusValidationError(ValidationMessages.SlugTooShort))
      )
      _ <- ZIO.when(slug.length > 30)(
        ZIO.fail(FudusValidationError(ValidationMessages.SlugTooLong))
      )
      _ <- ZIO.when(slug.contains(" "))(
        ZIO.fail(FudusValidationError(ValidationMessages.SlugInvalidCharacters))
      )
    } yield ()

  private def validateName(name: String): IO[FudusError, Unit] =
    for {
      _ <- ZIO.when(name.length < 4)(
        ZIO.fail(FudusValidationError(ValidationMessages.RestaurantNameTooShort))
      )
      _ <- ZIO.when(name.length > 50)(
        ZIO.fail(FudusValidationError(ValidationMessages.RestaurantNameTooLong))
      )
    } yield ()
}

object RestaurantService {
  val layer: ZLayer[RestaurantRepository, Throwable, RestaurantService] =
    ZLayer.fromFunction(RestaurantService.apply _)

  def create(
      slug: String,
      name: String,
      description: String,
      imageBase64: String
  ): ZIO[RestaurantService, FudusError, Unit] =
    ZIO.serviceWithZIO[RestaurantService](_.create(slug, name, description, imageBase64))
}
