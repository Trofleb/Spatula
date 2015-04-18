package spatulapp

import org.scalajs.jquery.JQuery
import spatulapp.CookingList.ListID
import spatulapp.Recipe.RecipeID

import scala.concurrent.Future
import scala.scalajs.js

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}

object Spatula extends js.JSApp {
  val sites : Seq[RecipeProvider] = Seq(AllRecipeProvider)
  var recipes = Map.empty[RecipeID, Recipe]

  /*val testData = Vector(
    new Recipe(
      "Spaghetti",
      2,
      List("http://images.marmitoncdn.org/pixcontent/selections/3a130f2d-e240-451f-aef4-55cf1bd1a4ca/acc_selection_barbecue_200x300.jpg"),
      List("sucre", "beurre"),
      List("saucons", "sautons"),
      "Marmiton",
      "http://www.marmiton.org/recettes/selection_barbecue.aspx"
    ),
    new Recipe(
      "Poulet au lit",
      -4,
      List("http://images.marmitoncdn.org/recipephotos/multiphoto/00/0071c6d2-23ac-4f4c-aba7-19997e6c4aa0_normal.jpg"),
      List("poulet", "2x plouet", "pouet"),
      List("cuire", "amnger", "laver"),
      "Marmiton",
      "http://www.marmiton.org/recettes/recette_poulet-basquaise_16969.aspx"
    ),
    new Recipe(
      "dfsjjwieh",
      3243,
      List("http://images.marmitoncdn.org/recipephotos/multiphoto/f1/f1826b83-184b-467c-9a70-649497c01567_normal.jpg"),
      List("poireau"),
      List("1", "2", "3"),
      "Marmiton hihi",
      "http://www.marmiton.org/recettes/recette_fondue-de-poireaux_20348.aspx"
    )
  )*/

  val testLists = List(
    new CookingList("french"),
    new CookingList("english")
  )

  //val recipes = testData.map(e => e.id -> e).toMap
  val cookingList = testLists.map(e => e.name -> e).toMap


  def main: Unit = {

    //$("body").append("<p>[message]</p>")

    //$("#lookup").on(events = "change", handler = "")

    //val request = IOHandler.get("http://bigoven.com")

    /*request onComplete {
      case Success(s) => println(s.responseXML); IOHandler.log("hello")
      case Failure(_) => IOHandler.log("There was an error fetching the webpage")
    }*/

    //showRecipe(1)
    //showList("french")
    SideView.updateCookingList(cookingList.map(_._2).toSeq)

      Events.on("change")("#lookup", Events.body)((e: JQuery) => {
          searchTerms(e.value.toString)
      })

  }

    def searchTerms(terms: String): Unit = {
        val searches = sites.map(x => x.search(terms)).foldLeft(Future(Seq.empty[Recipe])){
            case (lf1, lf2) => for(l1 <- lf1; l2 <- lf2) yield l1 ++ l2
        }

        searches onComplete {
            case Success(s) =>
                s.foreach(r => {
                    println("-------------------------------")
                    println("Title : " + r.title)
                    println("Stars : " + r.stars)
                    println("Picture : " + r.picture)
                    println("Ingredients : \n" + r.ingredients.mkString("\n"))
                    println("Instructions : \n" + r.instructions.mkString("\n"))
                    println("Website : " + r.website)
                    println("Origin url : " + r.originUrl)
                })
                recipes = s.map(e => e.id -> e).toMap
                showRecipe(recipes.head._1)
                cookingList("french") += recipes(1)
                cookingList("french") += recipes(2)
                cookingList("english") += recipes(0)
            case Failure(_) => IOHandler.log("there is error(s?)")
        }
    }

  def showSearch: Unit = {
    SearchView.show
    RecipeView.hide
    CookingListView.hide

    SearchView("cookies !")
  }

  def showRecipe(id: RecipeID): Unit = {
    SearchView.hide
    RecipeView.show
    CookingListView.hide

    RecipeView(recipes(id))
  }

  def showList(id: ListID): Unit = {
    SearchView.hide
    RecipeView.hide
    CookingListView.show

    CookingListView(cookingList(id))
  }

}
