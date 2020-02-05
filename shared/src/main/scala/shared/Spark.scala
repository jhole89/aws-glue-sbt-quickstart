package shared

import com.amazonaws.services.glue.GlueContext
import org.apache.spark.sql.SparkSession

object Spark {
  val sparkSession: SparkSession = SparkSession.builder().getOrCreate()
  val glueCtx: GlueContext = new GlueContext(sparkSession.sparkContext)
}
