package otus

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class Debug extends Simulation{
    setUp(CommonScenario().inject(atOnceUsers(1))).protocols(otus.httpProtocol)
}
