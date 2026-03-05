package otus

import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._

import scala.util.Random

object CommonScenario {
  def apply(): ScenarioBuilder = new CommonScenario().scn
}

class CommonScenario{

  val auth: ChainBuilder = group("Auth")(
    feed(Feeders.user)
      exec(Actions.loginPage)
      exec(Actions.login)
      exec { session =>
        val userSession = session("userSessionVar").asOption[String].getOrElse("отсутствует")
        println(s"userSessionVar => $userSession")
        session
      }
  )


  val scn: ScenarioBuilder = scenario("Common scenario")
    .exec(auth)
    .pause(1, 3)
    .exec(Actions.webTours)
    .exec(Actions.payment)
    .exec(Actions.homePage)
    .exec(Actions.getCities)
    .exec(Actions.city)
    .exec(Actions.departDate)
    .exec(Actions.returnDate)
    .exec(Actions.selectFlight)
    .exec(Actions.flight)
}