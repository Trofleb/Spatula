package spatulapp

import org.scalajs.dom
import org.scalajs.jquery.JQuery
import spatulapp.CookingList.CookingListID
import spatulapp.Recipe.RecipeID

import scala.collection.mutable
import scala.concurrent.Future
import scala.scalajs.js

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}

object Spatula extends js.JSApp {
    val sites : Seq[RecipeProvider] = Seq(AllRecipeProvider, SimplyRecipesProvider)
    var recipes = Map.empty[RecipeID, Recipe]

    val testLists = List(
        new CookingList("french"),
        new CookingList("english")
    )

    val cookingList = mutable.Map.empty[CookingListID, CookingList]
    testLists.foreach(e => cookingList += e.name -> e)

    def main: Unit = {

        // serialization : https://groups.google.com/forum/#!topic/scala-js/rY6Rz283W1A
        //dom.localStorage.setItem("recipes", recipes.seriz)

        //showRecipe(1)
        //showList("french")
        SideView.updateCookingList(cookingList.map(_._2).toSeq)

        Events.on("change")("#lookup", Events.body)((e: JQuery) => {
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

                showSearch


                //showRecipe(recipes.head._1)
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
