package spatulapp

import scala.concurrent.Future
import org.scalajs.jquery.jQuery

import scala.concurrent.ExecutionContext.Implicits.global

object SimplyRecipesProvider extends RecipeProvider {

  queryUrl = "http://www.simplyrecipes.com/?s="
  
  def parseRecipe(originUrl: String)(html: String): Option[Recipe] = {
		val $ = jQuery(jQuery.parseHTML(html))
		val postId = $.find("#content > article:first").prop("id").toString
		val picture = Some($.find(s"#$postId > div.featured-image > img:first").prop("src").toString)
		val title = $.find(s"#$postId > header > h1").html().toString
		val stars = scala.util.Random.nextInt(6)

		val pp = $.find(s"#$postId > div.entry-content > div.recipe-callout > div.entry-details.recipe-ingredients > ul > li")
		val ingredients = pp.toArray().map (x => jQuery(x).html().toString)

		val tt = $.find(s"#$postId > div.entry-content > div.recipe-callout > div.entry-details.recipe-method.instructions > div > p")
		val instructions = tt.toArray().map (x => jQuery(x).html())
		val website = "www.simplyrecipes.com"
		Some(Recipe(title, stars, picture, ingredients, instructions, website, originUrl))
  }

  def parseResults(html: String): Future[Seq[Recipe]] = {
  	val $ = jQuery(jQuery.parseHTML(html))
  	val entryList = $.find("#content > div.entry-list > ul > li")

		// skip non-recipe entries
		val recipesOnly = entryList.toArray() filter { x =>
			!jQuery(x).attr("class").toString.contains("category-how_to")
		}

		val hrefs = jQuery(recipesOnly).find("[href]").toArray()
		val links = hrefs.map(x => jQuery(x).attr("href")) take MAX_RECIPE

		val recipes = links.toSeq map (recipeUrl => IOHandler.get(recipeUrl)(parseRecipe(recipeUrl)))
  	val f = Future.sequence(recipes)
  	f map (_ flatMap  (x => x)) // unwrap the Option (remove None's)
  }
}