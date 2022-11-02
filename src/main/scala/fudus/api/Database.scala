package fudus.api

import com.typesafe.config.ConfigFactory
import fudus.api.errors.FudusDatabaseError
import io.getquill.context.ZioJdbc.DataSourceLayer
import io.getquill.{PostgresZioJdbcContext, SnakeCase}
import org.flywaydb.core.Flyway
import zio._

import javax.sql.DataSource

case class Database(dataSource: DataSource) extends PostgresZioJdbcContext(SnakeCase) {
  private[this] lazy val getFlyway: IO[FudusDatabaseError, Flyway] = ZIO.attempt {
    Flyway.configure().dataSource(dataSource).load()
  }.refineToOrDie[FudusDatabaseError]

  def migrate: IO[FudusDatabaseError, Unit] = (for {
    flyway <- getFlyway
    _ <- ZIO.attempt(flyway.migrate())
  } yield ()).refineToOrDie[FudusDatabaseError]
}

object Database {
  import scala.jdk.CollectionConverters._

  val dataSourceLayer = ZLayer {
    for {
      envUrl <- System.env("DATABASE_URL").orDie
      dbConfig = Map(
        "dataSource.user" -> "postgres",
        "dataSource.password" -> "",
        "dataSource.url" -> "jdbc:postgresql://localhost:25576/postgres"
      )
      config = ConfigFactory.parseMap(
        dbConfig.updated("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource").asJava
      )
    } yield DataSourceLayer.fromConfig(config).orDie
  }

  val layer: ZLayer[DataSource, FudusDatabaseError, Database] =
    ZLayer.fromFunction(Database.apply _)
}
