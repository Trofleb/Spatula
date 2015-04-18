package spatulapp

import org.scalajs.dom
import spatulapp.Recipe.{RecipeID, Ingredient}

import scala.collection.immutable.Seq
import scala.scalajs.js.Date

class Recipe(
	val title: String,
	val stars: Int,
	val pictures: Seq[String],
	val ingredients: Seq[Ingredient],
	val stepByStep: Seq[String],
	val website: String,
	val originUrl: String
	            ){

	val date = new Date
	val id: RecipeID = Recipe.getID

	def presentationPicture() = 
		if(pictures.isEmpty) 
			"" 
		else 
			pictures.head


}

object Recipe extends UniqueID {

	type Ingredient = String
	type RecipeID = Int


}