package spatulapp

import org.scalajs.dom
import org.scalajs.jquery.jQuery
import org.scalajs.dom.ext._

import scala.scalajs.js

object Spatula extends js.JSApp {

  val $ = jQuery

  def main(): Unit = {

    $("body").append("<p>[message]</p>")

    log("meeeeh")

    Ajax.get("http://google.com") // this is a future !

  }

  def log(m: String): Unit =
    dom.console.log(m)


}
