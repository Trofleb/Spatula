package spatulapp

import scala.util.matching

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

  def parseResults(document : String) : List[Recipe] = {

    val recipeLinks = findRecipesLinks(document).take(MAX_RECIPE)
    val recipes = recipeLinks map (IOHandler.get()(parseRecipePage))
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
    new Receipe("title", 1.0, /*pictures*/ Seq(), /*ingredients*/ Seq(),
      /*stepByStep*/ Seq(), "recipe.com", link)
  }
}