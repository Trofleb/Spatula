package spatulapp

import org.scalajs.dom
import org.scalajs.jquery.jQuery

import scala.io.Source
import scala.scalajs.js

object Spatula extends js.JSApp {

  val $ = jQuery

  def main(): Unit = {

    $("body").append("<p>[message]</p>")

    log("meeeeh")

    get("http://google.com")(log)

  }

  def log(m: String): Unit =
    dom.console.log(m)

  def get(url: String)(callback: String => Unit): Unit =
    $.get(url, {
      e: Any => e match {
        case s: String => callback(s)
        case _ => log("get: matcherror")
      }
    })


}
