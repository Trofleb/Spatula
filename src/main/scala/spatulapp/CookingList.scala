package spatulapp

import spatulapp.CookingList.CookingListID

import scala.collection.mutable
import scala.scalajs.js

import be.doeraene.spickling.jsany._
import be.doeraene.spickling._

class CookingList(var name: CookingListID){

  def this(newName: CookingListID, json: String) = {
    this(newName)
    json.split("#").foreach(r => this += PicklerRegistry.unpickle(r.asInstanceOf[js.Any]).asInstanceOf[Recipe])
  }

  val recipes = mutable.ArrayBuffer[Recipe]()

  def forStorage = name -> recipes.map(r => PicklerRegistry.pickle(r).asInstanceOf[String]).mkString("#")
 
	def +=(r: Recipe) = {
    recipes += r
	}
}

object CookingList {

	type CookingListID = String

}

/*<!-- Single button -->
<div class="btn-group">
  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
    Action <span class="caret"></span>
  </button>
  <ul class="dropdown-menu" role="menu">
    <li><a rel="categorie" href="#" class="addrecipe">categorie</a></li>
    <li><a href="#">Another action</a></li>
    <li><a href="#">Something else here</a></li>
    <li class="divider"></li>
    <li><a href="#">Separated link</a></li>
  </ul>
</div>

Events.click(jQuery())*/