package spatulapp

import spatulapp.CookingList.CookingListID

import scala.collection.mutable


class CookingList(var name: CookingListID) {

	val recipes = mutable.ListBuffer[Recipe]()

	def +=(r: Recipe) = {
		recipes += r
	}

}

object CookingList {

	type CookingListID = String

}