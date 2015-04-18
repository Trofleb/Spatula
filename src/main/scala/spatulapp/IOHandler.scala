package spatulapp


import org.scalajs.dom
import org.scalajs.dom.raw._
import org.scalajs.dom.ext._


import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object IOHandler {
  def log(m: String): Unit =
    dom.console.log(m)

  def get(url: String): Future[XMLHttpRequest] = {
    Ajax.get(url = url)
  }
}