package display

import org.scalajs.jquery.{jQuery => $, _}
import spatulapp.Recipe
import spatulapp.Recipe.Ingredient


object RecipeView extends View {

  val container = $("#recipe-mode")
  val meta = $("#meta", container)
  val ingredient = $("#ingredients", container)
  val steps = $("#steps", container)

  def updateMeta(r: Recipe): Unit = {
    $("h3", meta).html(r.title)
    $("img", meta).attr("src", r.picture.get)
    $(".score", meta).html(r.stars.toString)
    val site = $(".site", meta)
    site.html(r.website)
    site.attr("href", r.website)
  }

  def addIngredient(is: Seq[Ingredient]): Unit = {
    is.foreach(i => ingredient.append(s"<li>$i</li>"))
  }

  def trashIngredients: Unit = {
    $("li", ingredient).remove
  }

  def addStep(ss: Seq[Ingredient]): Unit = {
    ss.foreach(s => steps.append(s"<li>$s</li>"))
  }

  def trashSteps: Unit = {
    $("li", steps).remove
  }

  def apply(r: Recipe): Unit = {
    trashIngredients
    trashSteps
    updateMeta(r)
    addIngredient(r.ingredients)
    addStep(r.instructions)
  }

}