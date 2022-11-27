package fudus.api

package object types {
  type SecureEndpoint[INPUT, ERROR_OUTPUT, OUTPUT, -R] =
    sttp.tapir.Endpoint[String, INPUT, ERROR_OUTPUT, OUTPUT, R]
}
