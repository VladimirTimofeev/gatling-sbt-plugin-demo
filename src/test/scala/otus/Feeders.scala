package otus

import io.gatling.core.Predef._
import io.gatling.core.feeder.BatchableFeederBuilder
import io.gatling.http.Predef._

object Feeders {
  val user: BatchableFeederBuilder[String] = csv("users.csv").random
}
