enablePlugins(ScalaJSPlugin)

name := "spatula"

version := "0.0.0.0.0.0.0.0.1"

scalaOptions ++= Seq("-depecation", "-feature")

scalaVersion := "2.11.6"

persistLauncher in Compile := true

libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.8.0"
)