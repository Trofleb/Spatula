package spatulapp


import org.scalajs.dom
import org.scalajs.dom.XMLHttpRequest
import org.scalajs.jquery.jQuery
import org.scalajs.dom.ext._


import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.scalajs.js
import scala.util.{Failure, Success}

object Spatula extends js.JSApp {

  val $ = jQuery

  def main(): Unit = {

    $("body").append("<p>[message]</p>")

    //log("meeeeh")

    val request = get("http://bigoven.com")

    request onComplete {
      case Success(s) => println(s.responseText)
      case Failure(_) => log("There was an error fetching the webpage")
    }


  }

  def log(m: String): Unit =
    dom.console.log(m)

  def get(url: String): Future[XMLHttpRequest] = {
    Ajax.get(url = "http://www.zifeo.com/hackathon.php?key=xi3nu2859323xu2&get="+url)
  }


}
