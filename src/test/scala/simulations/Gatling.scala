package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.core.feeder.BatchableFeederBuilder
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class Gatling extends Simulation {
  val sec: Int = Integer.getInteger("sec", 5)
  val userAtOnce: Int = Integer.getInteger("usersAtOnce", 50)
  val constantUser: Int = Integer.getInteger("constantUsers", 20)
  val rampUser: Int = Integer.getInteger("rampUsers", 100)
  val user: BatchableFeederBuilder[String]#F = csv("src/test/resources/body/AddUser.csv").random
  val updateUser: BatchableFeederBuilder[String]#F = csv("src/test/resources/body/UpdateUser.csv").circular

  //http conf
  val httpConf: HttpProtocolBuilder = http.baseUrl("https://reqres.in")
    .header("content-type", "application/json")

  //Scenario
  val scn: ScenarioBuilder = scenario("Scenario 1")

    //Get Request
    .exec(http("Get user request").get("/api/users?page=2").check(status is 200))

    //Post Request
    .feed(user) //StringBody("""{"id1”:${id1}, "id2”:${id2}"”")
    .exec(http("Post request").post("/api/users").body(StringBody("""{"name":"${name}", "job":"${job}"}""")).asJson.check(status is 201))

    //Put Request
    .feed(updateUser)
    .exec(http("Put Request").put("/api/users/2").body(StringBody("""{"name":"${name}", "job":"${job}"}""")).asJson.check(status is 200))

  //Setup
  setUp(scn.inject(nothingFor(sec), atOnceUsers(userAtOnce), constantUsersPerSec(constantUser).during(15), rampUsers(rampUser).during(30))).maxDuration(60).protocols(httpConf).assertions(forAll.responseTime.max.lt(2500), forAll.successfulRequests.count.gte(0), global.failedRequests.count.is(0L))


}
