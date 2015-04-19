package display

import org.scalajs.jquery.{jQuery => $, _}
import spatulapp.Recipe

object SearchView extends View {

  val container = $("#search-mode")
  val result = List($("#result1", container), $("#result2", container), $("#result3", container))

  def addResult(id: Int, r: Recipe) = {
    result(id).append(
      s"""
        |<a rel="${r.id}" class="recipe">
        |   <img src="${r.picture.get}">
        |   <div class="infos">
        |       <h3>${r.title}</h3><span class="score pull-right">${(r.stars * 100).toInt}</span>
        |   </div>
        | </a>
      """.stripMargin)
  }

  def trashResult: Unit = {
    result.foreach($("a", _).remove)
  }

  def apply(recipes: Seq[Recipe]): Unit = {
    trashResult

    recipes.filter(_.website == "allrecipes").map(e => SearchView.addResult(0, e))
    recipes.filter(_.website == "www.simplyrecipes.com").map(e => SearchView.addResult(1, e))
    recipes.filter(_.website == "recipe.com").map(SearchView.addResult(2, _))
  }

}