package display

import org.scalajs.dom
import org.scalajs.jquery.{jQuery => $, _}

trait View {

	val container: JQuery

	def hide: Unit = {
		container.hide()
	}

	def show: Unit = {
		container.show()
	}
}