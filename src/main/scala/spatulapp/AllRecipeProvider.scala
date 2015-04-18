package spatulapp

import scala.util.{Try, Success, Failure}
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
      val recipeInfo = allRecipesInfo.slice(0, nbrElem)

      Future.sequence(
        recipeInfo.toArray.map( e => {
          val jTitle = jQuery(e).find(".title")
          val title = jTitle.html
          val url = "http://allrecipes.com/" + jTitle.attr("href")
          println("recipe : " + title)
          IOHandler.get(url)(extract(url,title))
        })
      )
    }
    else
      Future(Seq())
  }

  def extract(url: String, title: String)(html: String): Recipe = {

    val parsed = parseHtml(html)
    val stars = parsed.find(".rating-stars-img").find("meta").attr("content").toDouble/5
    val picture = parsed.find("#divHeroPhotoContainer").find("#imgPhoto").attr("src")
    val ingredients = parsed.find("#liIngredient").toArray.map(i => {
        val ing = jQuery(i)
        val amount = ing.find("#lblIngAmount")
        val ingr = ing.find("#lblIngName")
        if(amount.length > 0) amount.html + " " + ingr.html else "" + ingr.html
      })
    val instructions = parsed.find(".directLeft").find(".plaincharacterwrap").toArray.map(p => {
        jQuery(p).html
      })
    val website = "allrecipes"
    val originUrl = url

    Recipe(title,
            stars,
            if (picture == "http://images.media-allrecipes.com/images/44555.png") None else Some(picture),
            ingredients,
            instructions,
            website,
            originUrl)
  }

}
