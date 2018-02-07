name := "commentator"

version := "1.0"

scalaVersion := "2.12.4"

libraryDependencies ++= {
  Seq(
    "org.twitter4j" % "twitter4j-stream" % "4.0.4",
    "com.typesafe.akka" %% "akka-actor" % "2.4.19",
    "com.typesafe.akka" %% "akka-http" % "10.0.9",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.9",
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2",
    "com.typesafe" % "config" % "1.3.1",
    "com.github.etaty" %% "rediscala" % "1.8.0"
  )
}