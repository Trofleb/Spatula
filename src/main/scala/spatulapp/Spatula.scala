package spatulapp

import scala.scalajs.js
import org.scalajs.dom.raw._
import org.scalajs.jquery.jQuery
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object Spatula extends js.JSApp {

  val $ = jQuery

  def main(): Unit = {

    $("body").append("<p>[message]</p>")

    //log("meeeeh")

    val snif = new spatulapp.AllReceipeSniffer()

    snif.search("pesto")

  }

}
