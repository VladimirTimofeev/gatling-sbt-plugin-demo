package otus

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.{HttpRequestBuilder, resolveParamJList}

object Actions {
  val WebTours: HttpRequestBuilder = http("getWebTours")
    .get("webtours/")
    .check(status is 200)

  val LoginPage: HttpRequestBuilder = http("getLoginPage")
    .get("cgi-bin/nav.pl")
    .queryParam("in", "home")
    .check(status is 200)
    .check(
      regex("\"userSession\" value=\"(.*?)\"").find.saveAs("userSession")
    )


  val postLogin: HttpRequestBuilder = http("postLogin")
    .post("cgi-bin/login.pl")
//    .formParam("username", s"${username}")
//    .formParam("password", s"${password}")
    .formParam("login.x", "72")
    .formParam("login.y", "5")
    .formParam("FormSubmit", "off")

  val homePage: HttpRequestBuilder = http("homePage")
    .get("cgi-bin/login.pl")
    .queryParam("ntro", "true")
    .check(status is 200)

  val selectCity: HttpRequestBuilder = http("selectCity")
    .get("cgi-bin/reservations.pl")
    .queryParam("page", "welcome")
    .check(status is 200)
    //.check(...).saveAs()

  val selectFlight: HttpRequestBuilder = http("postSelectFlight")
    .post("cgi-bin/reservations.pl") //??????????????????????????????????
    .check(status is 200)

  val payment: HttpRequestBuilder = http("postPayment")  //??????????????????????????????????
    .post("cgi-bin/reservations.pl")
    .check(status is 200)
}
