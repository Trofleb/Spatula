package spatulapp

import scala.util.matching
import scala.concurrent._
import org.scalajs.jquery.{JQuery, jQuery}

import scala.concurrent.ExecutionContext.Implicits.global

object ComRecipeProvider extends RecipeProvider {

  val websiteName = "recipe.com"
  val notRecipes = Seq("how-to",
                       "tools",
                       "videos",
                       "popular",
                       "about-us", 
                       "recipes",
                       "recipecom",
                       "search",
                       "blogs",
                       "images",
                       "common",
                       "free-stuff")

  queryUrl = "http://www.recipe.com/search/?searchTerm="

  def parseResults(document : String) : Future[Seq[Recipe]] = {

    val recipeLinks = findRecipesLinks(document).take(MAX_RECIPE)
    val recipes = recipeLinks map {case x : String => IOHandler.get(x)(parseRecipePage(x))}

    recipes.foldLeft(Future(Seq.empty[Recipe])){
      case (list , recipe) => for(l <- list; r <- recipe) yield r +: l
    }
  }

  def findRecipesLinks(document : String) : Seq[String] = {
    val pattern = """http\:\/\/www\.recipe\.com\/[a-zA-Z\-]+\/""".r
    val links = pattern findAllIn(document)
    (links filterNot (link => notRecipes.exists(link.contains)) toList) distinct
  }

  def parseRecipePage(url : String)(page : String) : Recipe = {

    val patternTitle = """<meta property=\"og:title\" content=\"([a-zA-Z\ ]+)\" \/>""".r
    val title = (patternTitle findAllIn(page)).matchData.next group 1

    val patternScore = """<span class=\"hide\" itemprop=\"ratingValue\">(\d(\.\d)*)<\/span>""".r
    val score = ((patternScore findAllIn(page)).matchData.next group 1).toDouble / 5.0

    val patternPicture = """<img class=\"photo\" itemprop=\"image\" src=\"http\:\/\/www\.recipe\.com\/images\/([a-zA-Z\d\-]+)\.jpg\" """.r
    val picMatch = ((patternPicture findAllIn(page)).matchData.next) group 1
    val picture = Some("http://www.recipe.com/images/" + picMatch + ".jpg")

    val patternIngredients = """<div class=\"floatleft ingredient\">\n(\ )*([a-zA-Z\d\ ,\-]+)</div>""".r
    val ingredients = (((patternIngredients findAllIn(page)).matchData) map ( x => x group 2)) toList

    val rawSteps : Seq[String] = (parseHtml(page).find(".stepbystepInstruction") toArray) map (x => jQuery(x).html)
    val patternTrailingSpaces = """\n(\ )+""".r
    val withLinksSteps = rawSteps map (x => patternTrailingSpaces replaceFirstIn(x, ""))

    Recipe(title, score, /*picture*/ picture, /*ingredients*/ ingredients,
      /*stepByStep*/ withLinksSteps, websiteName, url)
  }
}