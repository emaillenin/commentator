name := "commentator"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies ++= {
  Seq(
    "org.twitter4j" % "twitter4j-stream" % "4.0.4",
    "com.typesafe.akka" %% "akka-actor" % "2.5.3",
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.7.1",
    "com.typesafe" % "config" % "1.3.1",
    "com.github.etaty" %% "rediscala" % "1.8.0"
  )
}