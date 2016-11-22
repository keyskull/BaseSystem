name := "BaseSystem"

lazy val commonSettings = Seq(
  organization := "me.keyskull",
  scalaVersion := "2.11.8"
)

lazy val util = (project in file("util")).settings(commonSettings: _*).
  settings(libraryDependencies ++=
    "org.json" % "json" % "20160810"::
      Nil)
lazy val core = (project in file("core") dependsOn(util)).
  settings(commonSettings: _*).settings(libraryDependencies ++=
  "com.firebase" % "geofire" % "2.1.1"::
    "org.ethereum" % "ethereumj-core" % "1.3.6-RELEASE" ::
    "com.typesafe.akka" %% "akka-actor" % "2.4.8" ::
    "com.typesafe.akka" %% "akka-testkit" % "2.4.8" ::
    "com.typesafe.akka" %% "akka-slf4j" % "2.4.8" ::
    "com.typesafe.akka" %% "akka-cluster" % "2.4.8" ::
    "org.xerial" % "sqlite-jdbc" % "3.14.2.1" ::
    //    "org.scalacheck" %% "scalacheck" % "1.13.2" % "test"::
    "org.scalatest" %% "scalatest" % "3.0.0" % "test"
    :: Nil)
lazy val root = (project in file(".")) settings(commonSettings: _*)
lazy val mining = project in file("mining") dependsOn(core) settings(commonSettings: _*)
lazy val client = (project in file("client") dependsOn(core,mining)).settings(commonSettings: _*)
lazy val server = (project in file("server") dependsOn(core,mining)).settings(commonSettings: _*)
