package providers

import scala.util.matching
import scala.concurrent._
import org.scalajs.jquery.{JQuery, jQuery}

import scala.concurrent.ExecutionContext.Implicits.global
import spatulapp._

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

    val flattenRecipes = recipes.foldLeft(Future(Seq.empty[Option[Recipe]])){
      case (list , recipe) => for(l <- list; r <- recipe) yield r +: l
    }
    flattenRecipes map( _ flatMap (x => x))
  }

  def findRecipesLinks(document : String) : Seq[String] = {
    val patternNoRes = """<p class=\"noResults\">""".r
    if(patternNoRes.findAllIn(document).isEmpty){
      val pattern = """http\:\/\/www\.recipe\.com\/[a-zA-Z\-]+\/""".r
      val links = pattern findAllIn(document)
      (links filterNot (link => notRecipes.exists(link.contains)) toList) distinct
    }
    else { Seq() }
  }

  def parseRecipePage(url : String)(page : String) : Option[Recipe] = {

    val patternTitle = """<meta property=\"og:title\" content=\"([a-zA-Z\ &amp;]+)\" \/>""".r
    val foundTit = ((patternTitle findAllIn(page)))
    val title : String = if (!foundTit.isEmpty) {
      foundTit.matchData.next group 1
    }
    else {
      "Every day is pie day"
    }


    val patternScore = """<span class=\"hide\" itemprop=\"ratingValue\">(\d(\.\d)?)<\/span>""".r
    val found = ((patternScore findAllIn(page)))
    val denominator : Double = if (!found.isEmpty) {
      (found.matchData.next group 1).toDouble
    }
    else {
      0
    }

    val score = denominator / 5.0

    
    //IOHandler.log(title)

    val patternPicture = """<img class=\"photo\" itemprop=\"image\" src=\"http\:\/\/www\.recipe\.com\/images\/([a-zA-Z\d\-]+)\.jpg\" """.r

    val foundPi = patternPicture findAllIn(page)
    val pictureURL = if (foundPi.isEmpty) {
      Seq()
    }
    else {
      (foundPi.matchData.next) group 1
    }

    if(pictureURL == Seq()) {
      None
    }
    else {

      val picture = Some("http://www.recipe.com/images/" + pictureURL + ".jpg")

      val patternIngredients = """<div class=\"floatleft ingredient\">\n(\ )*([a-zA-Z\d\ ,\-\/]+)</div>""".r
      val foundIn = patternIngredients findAllIn(page)
      val ingredients = if (foundIn.isEmpty) {
        Seq()
      }
      else {
        ((foundIn.matchData) map ( x => x group 2)) toList
      }

      if(ingredients == Seq()) {
        None
      }
      else {
        val rawSteps : Seq[String] = (parseHtml(page).find(".stepbystepInstruction") toArray) map (x => jQuery(x).html)
        val patternTrailingSpaces = """\n(\ )+""".r
        val withLinksSteps = rawSteps map (x => patternTrailingSpaces replaceFirstIn(x, ""))

        Some(Recipe(title, score, /*picture*/ picture, /*ingredients*/ ingredients,
          /*stepByStep*/ rawSteps, websiteName, url))
      }
    }
  }
}