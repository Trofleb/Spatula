package spatulapp

import spatulapp.CookingList
import org.scalajs.dom.ext._
import org.scalajs.dom.ext.Storage


object RecipeDepot {
	val storage: Storage = LocalStorage

	def loadAll(): Seq[CookingList] = {
		val keys = (0 until storage.length) flatMap (index => storage.key(index))
		val r = (keys map (name => storage(name) match {
			case Some(json) => Some(new CookingList(name, json))
			case None => None
		})).flatten // unwrap Option (get rid of the None's)
		r
	}

	def save(cookList: CookingList): Unit = {
		val (key, value) = cookList.forStorage
		storage.update(key, value)
	}
}