package display

import org.scalajs.jquery.{jQuery => $, _}
import spatulapp._
import spatulapp.Recipe_t.Ingredient

object CookingListView extends View {

  val container = $("#cooklist-mode")
  val table = $("#all", container)
  val name = $("#name", container)
  val allIngredient = $("#allIngredients", container)

  def addIngredient(i: Ingredient): Unit = {
    allIngredient.append(s"<li>$i</li>")
  }

  def trashIngredients: Unit = {
    $("li", allIngredient).remove
  }

  def updateName(c: CookingList): Unit = {
    name.html(c.name)
  }

  def addRecipe(r: Recipe): Unit = {
    r.ingredients.foreach(addIngredient)
    table.append(s"""<tr rel="${r.id}"><td>${r.title}</td><td>${(r.stars * 100).toInt}</td><td>${r.website}</td><td>${r.date}</td></tr>""")
  }

  def trashRecipes: Unit = {
    $("tr", table).slice(1).remove
  }

  def apply(c: CookingList): Unit = {
    trashRecipes
    trashIngredients
    updateName(c)
    c.recipes.foreach(addRecipe)

    Events.click("td", table)(e => Spatula.showRecipe(e.parent("tr").attr("rel").toInt))

  }

}