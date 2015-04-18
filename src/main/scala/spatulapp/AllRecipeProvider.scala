package spatulapp

import scala.util.{Try, Success, Failure}
//import org.scalajs.dom.raw.Document
//import org.scalajs.dom.raw.XMLHttpRequest
import scala.concurrent.ExecutionContext.Implicits.global
import org.scalajs.jquery.{JQuery, jQuery}
import scala.concurrent.Future

object AllRecipeProvider extends RecipeProvider{

  queryUrl = "http://allrecipes.com/search/default.aspx?qt=k&rt=r&pqt=k&ms=0&fo=0&wt="

  def parseResults(html: String) = {
    findReceipes(parseHtml(html))
  }

  def findReceipes(parsed: JQuery): Future[Seq[Recipe]]= {
    val allRecipesInfo = find(parsed, ".recipe-info")
    val nbrElem = Math.min(allRecipesInfo.length, 3)
    if(nbrElem > 0){
      val recipeInfo = allRecipesInfo.slice(0, allRecipesInfo.length)

      Future.sequence(recipeInfo.toArray.map( e => {
        val title = find(jQuery(e), ".title").html
        val url = "http://allrecipes.com/Recipe/" + title
        IOHandler.get(url)(extract(url,title))
      }))
    }
    else
      Future(Seq())
  }

  def extract(url: String, title: String)(html: String): Recipe = {
    val picture = None
    val ingredients = Seq()
    val instructions = Seq()
    val website = "allrecipes"
    val originUrl = url

    val parsed = parseHtml(html)
    val stars = find(parsed, "meta[itemprop='ratingvalue']").attr("content").toDouble/5
    println(stars)


    Recipe(title,
            stars,
            picture,
            ingredients,
            instructions,
            website,
            originUrl)
  }

}