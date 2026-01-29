package otus

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object Actions {
  val webTours: HttpRequestBuilder = http("webTours")
    .get("webtours/")
    .check(status is 200)

  val loginPage: HttpRequestBuilder = http("login")
    .get("cgi-bin/nav.pl?in=home")
    .check(status is 200)

  val homePage: HttpRequestBuilder = http("homePage")
    .get("cgi-bin/login.pl?intro=true")
    .check(status is 200)

  val selectCity: HttpRequestBuilder = http("selectCity")
    .get("cgi-bin/welcome.pl?page=search")
    .check(status is 200)

  val selectFlight: HttpRequestBuilder = http("selectFlight")
    .get("cgi-bin/reservations.pl") //??????????????????????????????????
    .check(status is 200)

  val payment: HttpRequestBuilder = http("payment")  //??????????????????????????????????
    .get("cgi-bin/reservations.pl")
    .check(status is 200)
}
