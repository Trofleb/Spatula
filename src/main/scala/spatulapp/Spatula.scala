package spatulapp

import scala.Seq
import scala.concurrent.Future
import scala.scalajs.js
import org.scalajs.dom.raw._
import org.scalajs.jquery.jQuery

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}

object Spatula extends js.JSApp {
  val sites : Seq[RecipeProvider] = Seq(ComRecipeProvider)

  var recipes : Seq[Recipe] = Seq()


  val $ = jQuery

  def main(): Unit = {

    //$("#lookup").on(events = "change", handler = "")

    val searchTerm = "curry"

    $("body").append("<p>[message]</p>")

    //log("meeeeh")
    val searches = sites.map(x => x.search(searchTerm)).foldLeft(Future(Seq.empty[Recipe])){
      case (lf1, lf2) => for(l1 <- lf1; l2 <- lf2) yield l1 ++ l2
    }

    searches onComplete {
      case Success(s) => recipes = s
      case Failure(_) => IOHandler.log("there is error(s?)")
    }
  }

}
