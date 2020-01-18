name := "sparktools"

version := "2.4.1"

description := "A collection of useful functions to extends the standard spark library."

scalaVersion := "2.11.8"

organization := "org.charik"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xfuture", "-Xlint")



/**
 * Dependencies
 */
resolvers += "jitpack" at "https://jitpack.io"

import Dependencies._
import Dependencies.Spark._
import Dependencies.Datastax._
import Dependencies.Jackson._

libraryDependencies ++= Seq(
  typesafeConfig,
  playJson,
  lazyLogging,
  sparkCore,
  sparkSql,
  sparkCassandraConnector,
  mockito,
  scalaTest,
  sparkTesting,
  sparkCassandraConnectorEmbedded,
  sparkCassandraConnectorUnshaded,
  cassandraAllDependency
)

dependencyOverrides += jacksonCore
dependencyOverrides += jacksonDatabind
dependencyOverrides += jacksonModuleScala

/**
 * Tests
 */

parallelExecution in test := false
//Forking is required for the Embedded Cassandra
fork in Test := true

/**
 * Assembly
 */

import Assembly._
import Assembly.ShadeRules._
import sbt.Keys.version


assemblyJarName in assembly := s"${name.value}-assembly-${version.value}.jar"
assemblyMergeStrategy in assembly := strategy
assemblyShadeRules in assembly := Seq(shapelessShading)

artifact in (Compile, assembly) := {
  val art = (artifact in (Compile, assembly)).value
  art.withClassifier(Some("assembly"))
}
addArtifact(artifact in (Compile, assembly), assembly)


import sbt.url

organizationName := "Charik"
organizationHomepage := Some(url("http://charik.org"))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/helkaroui/spark-tools"),
    "scm:git@github.com:helkaroui/spark-tools.git"
  )
)
developers := List(
  Developer(
    id    = "helkaroui",
    name  = "Hamza",
    email = "helkarou@gmail.com",
    url   = url("http://charik.org")
  )
)

description := "This is a collection of useful functions to extends the standard spark library.."
licenses := List("Apache 2" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt"))
homepage := Some(url("https://github.com/helkaroui/spark-tools"))

// Remove all additional repository other than Maven Central from POM
pomIncludeRepository := { _ => false }
publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
publishMavenStyle := true