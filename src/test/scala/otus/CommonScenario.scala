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

    val city: ChainBuilder = group("Cities") (
      exec {session =>
      val cities = session("cities").as[Seq[String]]
      val random = new Random()
      val depart = cities(random.nextInt(cities.length))
      var arriva = cities(random.nextInt(cities.length))
      if (arriva == depart) {
        while (arriva == depart) {
          arriva = cities(random.nextInt(cities.length))
        }
      }
      println(s"depart => $depart")
      println(s"arriva => $arriva")
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
    .exec(city)
    .exec(Actions.selectFlight)
}