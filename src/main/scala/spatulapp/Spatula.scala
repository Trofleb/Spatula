package spatulapp


import scala.scalajs.js
import org.scalajs.dom.raw._
import org.scalajs.jquery.jQuery
import org.scalajs.dom.ext._


import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object Spatula extends js.JSApp {

  val $ = jQuery

  def main(): Unit = {

    $("body").append("<p>[message]</p>")

    //log("meeeeh")

    val request = IOHandler.get("http://bigoven.com")

    request onComplete {
      case Success(s) => println(s.responseXML); IOHandler.log("hello")
      case Failure(_) => IOHandler.log("There was an error fetching the webpage")
    }


  }




}
