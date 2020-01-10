import sbt._

object Dependencies {

  val typesafeConfig = "com.typesafe" % "config" % "1.3.1"
  val playJson = "com.typesafe.play" %% "play-json" % "2.6.6"
  val lazyLogging = "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"


  val mockito = "org.mockito" % "mockito-core" % "2.8.9" % Test
  val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5" % Test

  object Spark {
    val version = "2.3.0"
    val sparkCore = "org.apache.spark" %% "spark-core" % version % Provided
    val sparkSql = "org.apache.spark" %% "spark-sql" % version % Provided

    val sparkTesting = "com.holdenkarau" %% "spark-testing-base" % "2.1.0_0.8.0" % Test
  }

  object Datastax {
    val version = "2.3.0"
    val sparkCassandraConnector = "com.datastax.spark" %% "spark-cassandra-connector" % version

    val sparkCassandraConnectorEmbedded = "com.datastax.spark" %% "spark-cassandra-connector-embedded" % version % Test exclude("org.slf4j", "log4j-over-slf4j")
    val sparkCassandraConnectorUnshaded = "com.datastax.spark" %% "spark-cassandra-connector-unshaded" % version
    val cassandraAllDependency = "org.apache.cassandra" % "cassandra-all" % "3.2" % Test exclude("org.slf4j", "log4j-over-slf4j")
  }


  object Jackson {
    val version = "2.8.7"
    val jacksonCore = "com.fasterxml.jackson.core" % "jackson-core" % version
    val jacksonModuleScala = "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % version
    val jacksonDatabind = "com.fasterxml.jackson.core" % "jackson-databind" % version
  }

}

