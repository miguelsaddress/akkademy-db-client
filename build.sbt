name := """akkademy-db-scala-client"""

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.7"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"   
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"
libraryDependencies += "com.typesafe.akka" %% "akka-remote" % "2.3.6"

lazy val root = Project("root", file(".")) dependsOn(akkademyMessages)

lazy val akkademyMessages = ProjectRef(uri("https://github.com/miguelsaddress/akkademy-db-messages.git"), "akkademy-db-messages")
