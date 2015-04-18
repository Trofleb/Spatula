package spatulapp

import spatulapp.CookingList.ListID

import scala.collection.mutable


class CookingList(val name: ListID) {

	val recipes = mutable.ListBuffer[Recipe]()

	def +=(r: Recipe) = {
		recipes += r
	}

}

object CookingList {

	type ListID = String

}