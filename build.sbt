import com.lihaoyi.workbench.Plugin._

enablePlugins(ScalaJSPlugin)

workbenchSettings

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.8.0"

bootSnippet := "example.ScalaJSExample().main(document.getElementById('canvas'));"

updateBrowsers «= updateBrowsers.triggeredBy(fastOptJS in Compile)

name := "Spatula"

version := "1.0"

scalaVersion := "2.11.6"

