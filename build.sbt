ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.10"

val tapirVersion = "1.1.4"

lazy val api = (project in file("api"))
  .settings(
    name := "fudus-api",
    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding",
      "utf8",
      "-feature",
      "-language:existentials",
      "-language:higherKinds",
      "-language:implicitConversions",
      "-unchecked",
      "-Xfatal-warnings",
      "-Ymacro-annotations"
    ),
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.2",
      "io.d11" %% "zhttp" % "2.0.0-RC7",
      "io.getquill" %% "quill-jdbc-zio" % "4.6.0",
      "com.softwaremill.sttp.tapir" %% "tapir-zio" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-zio-http-server" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-prometheus-metrics" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-json-zio" % tapirVersion,
      "ch.qos.logback" % "logback-core" % "1.4.4",
      "org.flywaydb" % "flyway-core" % "9.6.0",
      "org.postgresql" % "postgresql" % "42.3.1",
      "org.slf4j" % "slf4j-api" % "2.0.3",
      "com.sksamuel.elastic4s" %% "elastic4s-core" % "8.4.4",
      "com.sksamuel.elastic4s" %% "elastic4s-effect-zio" % "8.4.4",

      // Test
      "com.softwaremill.sttp.tapir" %% "tapir-sttp-stub-server" % tapirVersion % Test,
      "dev.zio" %% "zio-test" % "2.0.0" % Test,
      "dev.zio" %% "zio-test-sbt" % "2.0.0" % Test,
      "com.softwaremill.sttp.client3" %% "zio-json" % "3.8.3" % Test
    )
  )
