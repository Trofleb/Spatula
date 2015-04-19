enablePlugins(ScalaJSPlugin)

name := "spatula"

version := "0.1"

scalaVersion := "2.11.6"

persistLauncher in Compile := true

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.8.0"
libraryDependencies += "be.doeraene" %%% "scalajs-jquery" % "0.8.0"
libraryDependencies += "be.doeraene" %%% "scalajs-pickling" % "0.4.0"