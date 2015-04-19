package spatulapp

import org.scalajs.jquery.JQuery
import org.scalajs.jquery.{jQuery => $}
import spatulapp.CookingList.CookingListID
import spatulapp.Recipe_t.RecipeID

import scala.collection.mutable
import scala.concurrent.Future
import scala.scalajs.js

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}
import providers._
import display._

import be.doeraene.spickling._

object Spatula extends js.JSApp {
    PicklerRegistry.register[Recipe]

    val sites : Seq[RecipeProvider] = Seq(ComRecipeProvider, AllRecipeProvider, SimplyRecipesProvider)
    var recipes = Map.empty[RecipeID, Recipe]
    val searchQueue = mutable.ArrayBuffer[String]()

    val initialLists = List(
        new CookingList("French food"),
        new CookingList("English food")
    )

    val cookingList = mutable.Map.empty[CookingListID, CookingList]
    initialLists.foreach(e => cookingList += e.name -> e)

    def main: Unit = {

        // serialization : https://groups.google.com/forum/#!topic/scala-js/rY6Rz283W1A
        //dom.localStorage.setItem("recipes", recipes.seriz)

        //showRecipe(1)
        //showList("french")
        SideView.updateCookingList(cookingList.map(_._2).toSeq)
        SideView.updateSearchList()

        Events.click(".addtolist", Events.body)((e: JQuery) => {
            val id = e.attr("rel")
            cookingList(id) += recipes(RecipeView.container.attr("rel").toInt)
            SideView.updateCookingList(cookingList.map(_._2).toSeq)
            showList(id)
        })

        Events.on("click")("#addList a", Events.body)((e: JQuery) => {
            val sel = $("#addList ul")
            if (sel.css("display") == "none") sel.show()
            else sel.hide()
        })

        Events.on("change")("#lookup", Events.body)((e: JQuery) => {
            $("#loader").css("visibility", "visible")
            searchTerms(e.value.toString)
        })

        Events.click("a.recipe", Events.body)((e: JQuery) =>
            showRecipe(e.attr("rel").toInt)
        )

        Events.click("#back", Events.body)((e: JQuery) =>
            showSearch
        )

        Events.click("#new", SideView.container)((e: JQuery) => {
            val add = new CookingList("Rename me")
            cookingList += add.name -> add
            SideView.updateCookingList(cookingList.map(_._2).toSeq)
        })

    }

    def searchTerms(terms: String): Unit = {
        val searches = Future.sequence(sites.map(x => x.search(terms)))

        searches onComplete {
            case Success(s) => onSuccessSearch(s.flatten, terms)
            case Failure(_) => IOHandler.log("there is error(s?)")
        }
    }

    def onSuccessSearch(s: Seq[Recipe]){
      $("#loader").css("visibility", "hidden")
      recipes = s.map(e => e.id -> e).toMap
      Spatula.searchQueue -= t
      searchQueue.prepend(t)
      SideView.updateSearchList()
      showSearch
    }

    def showSearch: Unit = {
        SearchView.show
        RecipeView.hide
        CookingListView.hide
        SearchView(recipes.map(_._2).toSeq)
    }

    def showRecipe(id: RecipeID): Unit = {
        SearchView.hide
        RecipeView.show
        CookingListView.hide
        RecipeView(recipes(id))
    }

    def showList(id: CookingListID): Unit = {
        SearchView.hide
        RecipeView.hide
        CookingListView.show
        CookingListView(cookingList(id))
    }

}
