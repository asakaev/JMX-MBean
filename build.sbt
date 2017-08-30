lazy val root = (project in file("."))
  .settings(
    name := "JMX-MBean",
    scalaVersion := "2.12.3",
    version := "0.0.2-SNAPSHOT",
    libraryDependencies += "org.reactivestreams" % "reactive-streams" % "1.0.1"
  )