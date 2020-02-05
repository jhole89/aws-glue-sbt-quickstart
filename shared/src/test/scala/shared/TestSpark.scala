package shared

import org.apache.spark.sql.SparkSession

object TestSpark {
  implicit val spark: SparkSession = SparkSession
    .builder()
    .appName("test")
    .master("local[2]")
    .config("spark.sql.shuffle.partitions", "1")
    .getOrCreate()
}
