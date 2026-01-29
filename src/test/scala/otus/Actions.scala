package otus

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object Actions {
  val webTours: HttpRequestBuilder = http("getMyPage")
    .get("")
}
