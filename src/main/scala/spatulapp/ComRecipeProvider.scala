package spatulapp

import scala.util.matching
import scala.concurrent._

import scala.concurrent.ExecutionContext.Implicits.global

object ComRecipeProvider extends RecipeProvider {

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
    val recipes = recipeLinks map {case x : String => IOHandler.get(x)(parseRecipePage)}
    IOHandler.log(recipes.toList.map(_.toString) mkString(" "))

    recipes.foldLeft(Future(Seq.empty[Recipe])){
      case (list , recipe) => for(l <- list; r <- recipe) yield r +: l
    }
  }

  def findRecipesLinks(document : String) : Iterator[String] = {
    val pattern = """http\:\/\/www\.recipe\.com\/[a-zA-Z\-]+\/""".r
    val links = pattern findAllIn(document)
    links filterNot (link => notRecipes.exists(link.contains))
  }

  def parseRecipePage(page : String) : Recipe = {
    IOHandler.log(page)
    val patternName = """""".r
    Recipe("title", 1.0, /*pictures*/ Option("img"), /*ingredients*/ Seq(),
      /*stepByStep*/ Seq(), "recipe.com", "hello")
  }
}