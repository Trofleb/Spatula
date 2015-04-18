package spatulapp

import org.scalajs.dom
import org.scalajs.jquery.jQuery

import scala.scalajs.js

object Spatula extends js.JSApp {

  def main(): Unit = {

    jQuery("body").append("<p>[message]</p>")

    log("meeeeh")

  }

  def log(m: String): Unit =
    dom.console.log(m)

}
