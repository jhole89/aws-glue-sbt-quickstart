package shared

import org.scalatest.{FlatSpec, Matchers}

class GlueUtilsTest extends FlatSpec with Matchers {
  "parseArgs" should "resolve system arguments from Glue to a map of key value pairs" in {
    val args: Array[String] = Array("JOB_NAME", "--myArg", "myArgValue")

    val parsedArgs: Map[String, String] = GlueUtils.parseArgs(args)

    parsedArgs("JOB_NAME") shouldBe null
    parsedArgs("myArg") shouldBe "myArgValue"
  }
}
