import sbt._

object Dependencies {

  val typesafeConfig = "com.typesafe" % "config" % "1.3.1"
  val playJson = "com.typesafe.play" %% "play-json" % "2.6.6"
  val lazyLogging = "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"


  val mockito = "org.mockito" % "mockito-core" % "2.8.9" % Test
  val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5" % Test

  object Spark {
    val version = "2.4.+"
    val sparkCore = "org.apache.spark" %% "spark-core" % version % Provided
    val sparkSql = "org.apache.spark" %% "spark-sql" % version % Provided
    val sparkTesting = "com.holdenkarau" %% "spark-testing-base" % "2.4.0_0.12.0" % Test
  }

}

