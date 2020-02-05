package shared

import com.amazonaws.services.glue.GlueContext
import com.amazonaws.services.glue.util.Job
import org.apache.spark.sql.SparkSession

import scala.collection.JavaConverters._

abstract class BaseGlueScript {
  implicit val sparkSession: SparkSession = Spark.sparkSession
  implicit val glueCtx: GlueContext = Spark.glueCtx

  protected def main(sysArgs: Array[String]): Unit = {
    val args: Map[String, String] = GlueUtils.parseArgs(sysArgs)

    Job.init(args("JOB_NAME"), glueCtx, args.asJava)
    run(args("myArg"))
    Job.commit()
  }

  protected def run(myArg: String): Unit
}
