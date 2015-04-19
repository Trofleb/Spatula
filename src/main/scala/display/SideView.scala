package display

import spatulapp.Spatula
import spatulapp.CookingList
import org.scalajs.jquery.{jQuery => $, _}

object SideView extends View {

  val container = $("#side")
  val cookingList = $("#cookingList", container)
  val lastSearch = $("#lastSearch", container)

  def updateCookingList(cs: Seq[CookingList]): Unit = {
    RecipeView.updateList(cs)
    $("li", cookingList).remove
    cs.foreach(c => cookingList.append(s"""<li><a rel="${c.name}">${c.name}</a></li>"""))

    Events.click("a", cookingList)(e => Spatula.showList(e.attr("rel")))
    Events.editable("a", cookingList)

  }

}