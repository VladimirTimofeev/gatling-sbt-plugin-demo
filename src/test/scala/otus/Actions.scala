package otus

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import io.gatling.http.request.builder.{HttpRequestBuilder, resolveParamJList}

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

  val selectFlight: HttpRequestBuilder = http("postSelectFlight")
    .post("cgi-bin/reservations.pl") //??????????????????????????????????
    .check(status is 200)

  val payment: HttpRequestBuilder = http("postPayment")  //??????????????????????????????????
    .post("cgi-bin/reservations.pl")
    .check(status is 200)
}
