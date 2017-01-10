name := "akka-reproducer"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.11",
  "com.typesafe.akka" %% "akka-stream" % "2.4.11",
  "com.typesafe.akka" %% "akka-http-core" % "2.4.11",
  "com.typesafe.akka" %% "akka-http-experimental" % "2.4.11",
  "com.typesafe.akka" %% "akka-slf4j" % "2.4.11",


  "com.typesafe.play" %% "play-json" % "2.5.9",
  "com.typesafe" % "config" % "1.3.1",
  "ch.qos.logback" % "logback-classic" % "1.1.7" % "runtime",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"
)