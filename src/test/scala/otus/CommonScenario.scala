package otus

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._

object CommonScenario {
  def apply(): ScenarioBuilder = new CommonScenario().scn
}

class CommonScenario{
  val scn: ScenarioBuilder = scenario("Common scenario")
    .exec(Actions.WebTours)
    .exec(Actions.LoginPage)
    .exec(Actions.postLogin)
    .exec(Actions.payment)
    .exec(Actions.homePage)
    .exec(Actions.selectCity)
    .exec(Actions.selectFlight)
    .exec { session ⇒
      val userSession = session("userSession").asOption[String].getOrElse("отсутствует")
      println(s"userSession → $userSession")
      session
    }
}