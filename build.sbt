name := """akkademy-db-scala-client"""

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.7"

// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"
libraryDependencies += "com.akkademy-db" %% "akkademy-db-messages" % "0.0.1-SNAPSHOT"
    


libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"
libraryDependencies += "com.typesafe.akka" %% "akka-remote" % "2.3.6"

