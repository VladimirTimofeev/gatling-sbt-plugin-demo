package otus

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object Actions {
  val getWebTours: HttpRequestBuilder = http("getWebTours")
    .get("webtours/")
    .check(status is 200)

  val getLoginPage: HttpRequestBuilder = http("getLoginPage")
    .get("cgi-bin/nav.pl")
    .queryParam("in", "home")
    .check(status is 200)
    .check(substring(userSession => "value").saveAs("userSession"))

  val postLogin: HttpRequestBuilder = http("postLogin")
    .post("cgi-bin/login.pl")

  val homePage: HttpRequestBuilder = http("homePage")
    .get("cgi-bin/login.pl")
    .queryParam("ntro", "true")
    .check(status is 200)

  val selectCity: HttpRequestBuilder = http("selectCity")
    .get("cgi-bin/welcome.pl")
    .queryParam("page", "search")
    .check(status is 200)

  val selectFlight: HttpRequestBuilder = http("selectFlight")
    .get("cgi-bin/reservations.pl") //??????????????????????????????????
    .check(status is 200)

  val payment: HttpRequestBuilder = http("payment")  //??????????????????????????????????
    .get("cgi-bin/reservations.pl")
    .check(status is 200)
}
