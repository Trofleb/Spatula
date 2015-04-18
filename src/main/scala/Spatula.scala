import scala.scalajs.js
import org.scalajs.jquery.jQuery
import org.scalajs.dom

object Spatula extends js.JSApp {

  def main(): Unit = {

    val paragraph = dom.document.createElement("p")
    paragraph.innerHTML = "<strong>It will work!</strong>"
    //dom.document.getElementById("search").appendChild(paragraph)
    jQuery("body").append("<p>[message]</p>")

  }

}
