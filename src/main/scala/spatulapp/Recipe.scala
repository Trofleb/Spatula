package spatulapp

import spatulapp.Recipe.{RecipeID, Ingredient}

import scala.scalajs.js.Date

case class Recipe(
  val title: String,
  val stars: Double,
  val picture: Option[String],
  val ingredients: Seq[Ingredient],
  val instructions: Seq[String],
  val website: String,
  val originUrl: String
              ){

  val date = new Date
  val id: RecipeID = Recipe.getID

}

object Recipe extends UniqueID {

  type Ingredient = String
  type RecipeID = Int


}