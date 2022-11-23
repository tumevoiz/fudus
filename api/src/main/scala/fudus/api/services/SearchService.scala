package fudus.api.services

import com.sksamuel.elastic4s.ElasticClient
import fudus.api.errors.{FudusError, FudusPropagationError}
import zio._

final case class SearchService(client: ElasticClient) {
  import com.sksamuel.elastic4s.ElasticDsl._
  import com.sksamuel.elastic4s.zio.instances._

  def propagateRestaurants: IO[FudusError, Any] = client
    .execute {
      indexInto("")
    }
    .mapError(e => FudusPropagationError(e.getMessage))
}

object SearchService {
  def layer: URLayer[ElasticClient, SearchService] =
    ZLayer.fromFunction(SearchService.apply _)
}
