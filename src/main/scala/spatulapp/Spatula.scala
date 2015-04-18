package spatulapp


import org.scalajs.dom
import org.scalajs.dom.XMLHttpRequest
import org.scalajs.jquery.jQuery
import org.scalajs.dom.ext._


import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.scalajs.js
import scala.util.{Try, Success}

object Spatula extends js.JSApp {

  val $ = jQuery

  def main(): Unit = {

    $("body").append("<p>[message]</p>")

    //log("meeeeh")

    val request = get("http://google.com")

    request.onComplete((e: Try[XMLHttpRequest]) => e match {
      case Success(s) => println(s.responseText)
    })


  }

  def log(m: String): Unit =
    dom.console.log(m)

  def get(url: String): Future[XMLHttpRequest] = {
    val domain = url
    Ajax.get(url = url, headers = Map("Origin" -> domain, "Access-Control-Allow-Origin" -> domain))
  }


}
