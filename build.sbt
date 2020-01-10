name := "spark-tools"

version := "1.0.0"

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

