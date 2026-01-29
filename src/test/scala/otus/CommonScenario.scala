package otus

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._

object CommonScenario {

  def apply(): ScenarioBuilder = new CommonScenario().scn

}

class CommonScenario{
  val scn = scenario("Common scenario")
    .exec(Actions.webTours)
}
