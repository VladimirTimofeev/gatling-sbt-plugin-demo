package otus

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import io.gatling.http.request.builder.{HttpRequestBuilder, resolveParamJList}

import scala.util.Random

object Actions {
  val webTours: HttpRequestBuilder = http("getWebTours")
    .get("webtours/")
    .check(status is 200)

  val loginPage: HttpRequestBuilder = http("getLoginPage")
    .get("cgi-bin/nav.pl")
    .queryParam("in", "home")
    .check(
      regex("""<input type="hidden" name="userSession" value="([^"]+)"""").find.saveAs("userSessionVar")
    )
    .check(status is 200)


  val login: HttpRequestBuilder = http("postLogin")
    .post("cgi-bin/login.pl")
    .formParam("username", "#{username}")
    .formParam("password", "#{password}")
    .formParam("userSession", "#{userSessionVar}")
    .formParam("login.x", "72")
    .formParam("login.y", "5")
    .formParam("FormSubmit", "off")

  val homePage: HttpRequestBuilder = http("homePage")
    .get("cgi-bin/login.pl")
    .queryParam("ntro", "true")
    .formParam("userSession", "#{userSessionVar}")
    .check(status is 200)

  val getCities: HttpRequestBuilder = http("getCities")
    .get("cgi-bin/reservations.pl")
    .queryParam("page", "welcome")
    .check(status is 200)
    .check(
      regex("""<option value="([^"]+).*?>""").findAll.saveAs("cities")
    )
    .check(
      regex("""departDate" value="([^"]+).*?>""").find.saveAs("departDateVar")
    )
    .check(
      regex("""returnDate" value="([^"]+).*?>""").find.saveAs("returnDateVar")
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

  val departDate: ChainBuilder = {
    exec {session => val departDate = session("departDateVar").asOption[String].getOrElse("отсутствует")
    println(s"departDate => $departDate")
    session}
  }

  val returnDate: ChainBuilder = {
    exec {session => val returnDateVar = session("returnDateVar").asOption[String].getOrElse("отсутствует")
      println(s"returnDate => $returnDateVar")
      session}
  }

  val selectFlight: HttpRequestBuilder = http("postSelectFlight")
    .post("cgi-bin/reservations.pl")
    .formParam("advanceDiscount", "0")
    .formParam("depart", "#depart")
    .formParam("departDate", "#departDate")
    .formParam("arrive", "#arriva")
    .formParam("returnDate", "#returnDate")
    .formParam("numPassengers", "1")
    .formParam("seatPref", "None")
    .formParam("seatType", "Coach")
    .check(status is 200)
    .check(
      regex("""outboundFlight" value="([^"]+).*?>""").findAll.saveAs("flight")
    )


  val flight: ChainBuilder = {
    exec {session => val flight = session("flight").as[Seq[String]]
      val random = new Random()
      val fly = flight(random.nextInt(flight.length))
      println(s"fly => $fly")
      session}
  }

  val payment: HttpRequestBuilder = http("postPayment")  //??????????????????????????????????
    .post("cgi-bin/reservations.pl")
    .check(status is 200)
}
