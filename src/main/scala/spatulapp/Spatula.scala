package spatulapp

import scala.Seq
import scala.scalajs.js
import org.scalajs.dom.raw._
import org.scalajs.jquery.jQuery

object Spatula extends js.JSApp {
  val sites : Seq[RecipeProvider] = Seq(AllRecipeProvider)

  val $ = jQuery

  def main(): Unit = {

    //$("#lookup").on(events = "change", handler = "")

    val searchTerm = "curry"

    $("body").append("<p>[message]</p>")

    //log("meeeeh")
    val recipes = sites flatMap (_.search(searchTerm))

  }

}
