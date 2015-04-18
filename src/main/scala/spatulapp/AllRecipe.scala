package spatulapp

import scala.util.{Try, Success, Failure}
import org.scalajs.dom.raw.Document
import org.scalajs.dom.raw.XMLHttpRequest
import scala.concurrent.ExecutionContext.Implicits.global
import org.scalajs.jquery.{JQuery, jQuery}

object AllRecipe extends RecipeProvider{

	def search(terms: String) = {
    val searchResult = getSearchResult(terms)
    val result = Seq()

    searchResult onComplete {
      case Success(s: XMLHttpRequest) => {
        findReceipes(parseHtml(s.responseText))
      }
      case Failure(_) => Seq()
    }
	}

  /*def search(html: String) = {
    //val searchResult = getSearchResult(terms)
    val result = Seq()

    findReceipes(parseHtml(s.responseText))
  }*/

  def findReceipes(parsed: JQuery): Seq[Receipe]= {
    val allRecipesInfo = find(parsed, ".recipe-info")
    val nbrElem = Math.min(allRecipesInfo.length, 3)
    if(nbrElem > 0){
      val recipeInfo = allRecipesInfo.slice(0, allRecipesInfo.length)

      for(e <- recipeInfo.toArray){
        val elem = jQuery(e)
        val star = find(elem, ".rating-stars-grad").width/82
        val title = find(elem, ".title").html
        val () = extract("http://allrecipes.com/Recipe/" + title)
        println(find(elem, ".rating-stars-grad").width)
      }yield(Receipe(val title: String,
                      val stars: Double,
                      val pictures: Seq[String],
                      val ingredients: Seq[String],
                      val stepByStep: Seq[String],
                      val website: String,
                      val originUrl: String)
      )
    }
    else
      Seq()
  }

  def extract():  = {

  }

	def parseTerms(terms: String) = terms.replaceAllLiterally(" ", "%20")

	def getSearchResult(terms: String) = {
		val searchQuery = parseTerms(terms)
		val query = "http://allrecipes.com/search/default.aspx?qt=k&rt=r&pqt=k&ms=0&fo=0&wt=" + searchQuery
		IOHandler.get(query)
	}

}