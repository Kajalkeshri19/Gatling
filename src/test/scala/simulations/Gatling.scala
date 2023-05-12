package simulations


import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._


class Gatling extends Simulation {

  val user = csv("src/test/resources/body/AddUser.csv").random
  val updateUser=csv("src/test/resources/body/UpdateUser.csv").circular
  //http conf
  val httpConf = http.baseUrl("https://reqres.in")
    .header("content-type", "application/json")


  //Scenario
  val scn = scenario("Scenario 1")

    //Get Request
    .exec(http("Get user request").get("/api/users?page=2").check(status is 200))

    //Post Request
    .feed(user) //StringBody("""{"id1”:${id1}, "id2”:${id2}"”")
    .exec(http("Post request").post("/api/users").body(StringBody("""{"name":"${name}", "job":"${job}"}""")).asJson.check(status is 201))

    //Put Request
    .feed(updateUser)
    .exec(http("Put Request").put("/api/users/2").body(StringBody("""{"name":"${name}", "job":"${job}"}""")).asJson.check(status is 200))

    //Setup
    setUp(scn.inject(nothingFor(5),atOnceUsers(50),constantUsersPerSec(20).during(15),rampUsers(100).during(30))).maxDuration(60).protocols(httpConf).assertions(forAll.responseTime.max.lt(2200), forAll.successfulRequests.count.lte(450),global.failedRequests.count.is(0L))


}
