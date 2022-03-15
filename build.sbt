name := "format_practice"

version := "0.1"
scalaVersion := "2.11.12"

val sparkVersion = "2.4.8"

libraryDependencies += "org.scala-sbt" %% "zinc" % "1.2.1"
libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-hive" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-avro" % sparkVersion
libraryDependencies += "io.delta" %% "delta-core" % "0.6.1"
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.28"
libraryDependencies += "com.lihaoyi" %% "os-lib" % "0.8.1"

