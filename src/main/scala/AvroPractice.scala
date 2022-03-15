import org.apache.spark.sql.SparkSession
import os.Path

object AvroPractice extends App {
  System.setProperty("hadoop.home.dir", "C:\\Program Files\\hadoop\\hadoop-3.0.0")

  val spark = SparkSession.builder()
    .master("local[1]")
    .appName("AvroExamples")
    .enableHiveSupport()
    .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")

  val filePath = "src/main/resources"
  val path = Path(os.pwd + "/src/main/resources/out")

  // Remove "out" folder
  if (os.exists(path)) os.remove.all(path)

  // Reading json file to dataframe
  println("#1. Read json to dataframe")
  val df = spark.read.json(path = s"${filePath}/file2.json")

  df.show()
  df.printSchema()

  // Save to hive
  import spark.sql

  df.createOrReplaceTempView("file2Table")
  println("#2. Hive table:")
  sql("SELECT * FROM file2Table").show()

  // Converting JSON to Avro
  println("#3. Convert json to Avro")
  df.write.format("avro")
    .save(s"${filePath}/out/avro/file2")

  val users = spark
    .read
    .option("multiline", "true")
    .json(s"${filePath}/users.json")
  users.show()
  users.printSchema()

  // Converting json to avro with partition
  println("#4. Convert json to Avro with partition")
  users.write
    .partitionBy("city")
    .format("avro")
    .save(s"${filePath}/out/avro/user")

  // Converting avro to json
  println("#5. Convert avro to json")
  val userDf = spark.read.format("com.databricks.spark.avro")
    .option("header", "true")
    .load(s"${filePath}/out/avro/user")

  userDf.show()
  userDf.printSchema()

  userDf.write
    .json(s"${filePath}/out/userFromAvro")

  val file2Df = spark.read.format("com.databricks.spark.avro")
    .option("header", "true")
    .load(s"${filePath}/out/avro/file2")


  file2Df.show()
  file2Df.printSchema()

  file2Df.write
    .json(s"${filePath}/out/file2FromAvro")

  // Converting avro to parquet
  println("#6. Convert avro to parquet")
  file2Df.write
    .parquet(s"${filePath}/out/file2.parquet")

  val file2DfFromParquet = spark.read
    .parquet(s"${filePath}/out/file2.parquet")

  file2DfFromParquet.show()
  file2DfFromParquet.printSchema()

  spark.close()
}
