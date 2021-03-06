package spatulapp

import org.scalajs.dom
import org.scalajs.dom.ext._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

object IOHandler {
  def log(m: String): Unit =
    dom.console.log(m)

  def get[T](url: String)(func : String => T): Future[T] = {
    val request = Ajax.get(url = url)
    request.map(x => func(x.responseText))
  }
}