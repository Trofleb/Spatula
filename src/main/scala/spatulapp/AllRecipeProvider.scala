// package spatulapp

// import scala.util.{Try, Success, Failure}
// import org.scalajs.dom.raw.Document
// import org.scalajs.dom.raw.XMLHttpRequest
// import scala.concurrent.ExecutionContext.Implicits.global
// import org.scalajs.jquery.{JQuery, jQuery}

// object AllRecipeProvider extends RecipeProvider{

//   queryUrl = "http://allrecipes.com/search/default.aspx?qt=k&rt=r&pqt=k&ms=0&fo=0&wt="

//   def parseResults(html: String) = {
//     val result = Seq()
//     findReceipes(parseHtml(html))
//   }

//   def findReceipes(parsed: JQuery): Seq[Recipe]= {
//     val allRecipesInfo = find(parsed, ".recipe-info")
//     val nbrElem = Math.min(allRecipesInfo.length, 3)
//     if(nbrElem > 0){
//       val recipeInfo = allRecipesInfo.slice(0, allRecipesInfo.length)

//       for(e <- recipeInfo.toArray){
//         val elem = jQuery(e)
//         val star = find(elem, ".rating-stars-grad").width/82
//         val title = find(elem, ".title").html
//         //val (pic, ingredients, instructions, website, ) = spatula.get("http://allrecipes.com/Recipe/" + title, extract)
//         println(find(elem, ".rating-stars-grad").width)
//       }//yield(Receipe(title, star, pic, ingredients, instructions, url))
//       Seq()
//     }
//     else
//       Seq()
//   }

//   def extract() = {
//   }

// }