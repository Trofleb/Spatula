package spatulapp

import scala.util.matching
import scala.concurrent._

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

  def parseResults(document : String) : Seq[Future[Recipe]] = {

    val recipeLinks = findRecipesLinks(document).take(MAX_RECIPE)
    val recipes = recipeLinks map (IOHandler.get(_)(parseRecipePage))
    IOHandler.log(recipes.toList.map(_.toString) mkString(" "))

    recipes.toList
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