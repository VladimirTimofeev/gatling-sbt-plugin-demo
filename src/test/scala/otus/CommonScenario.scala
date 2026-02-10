package otus

import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._

object CommonScenario {
  def apply(): ScenarioBuilder = new CommonScenario().scn
}

class CommonScenario{

  val auth: ChainBuilder = group("Auth")(
    feed(Feeders.user)
      exec(Actions.loginPage)
      exec(Actions.login)
  )

  val scn: ScenarioBuilder = scenario("Common scenario")
    .exec(auth)
    .exec(Actions.webTours)
    .exec(Actions.loginPage)
    .exec(Actions.login)
    .exec(Actions.payment)
    .exec(Actions.homePage)
    .exec(Actions.selectCity)
    .exec(Actions.selectFlight)
    .exec { session =>
      val userSessionVar = session("userSessionVar").asOption[String].getOrElse("отсутствует")
      println(s"userSessionVar => $userSessionVar")
      session
    }
    .exec(session => {println(session); session})
}