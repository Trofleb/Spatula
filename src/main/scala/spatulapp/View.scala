package spatulapp

import org.scalajs.dom
import org.scalajs.jquery.{jQuery => $, _}
import spatulapp.Recipe.Ingredient

trait View {

	val container: JQuery

	def hide: Unit = {
		container.hide()
	}

	def show: Unit = {
		container.show()
	}

}

object Events {

	val body = $("body")
	private var shift = false
	private var clicks = 0
	val click = on("click")_
	val dblclick = on("dblclick")_

	def on(event: String)(selector: String, parent: JQuery = body)(callback: JQuery => Unit): Unit = {

		if (event == "dblclick") {
			parent.on("click", selector, (e: JQueryEventObject) => {
				e.preventDefault
				clicks += 1
				if (clicks == 1) {
					schedule(250)({
						if (clicks > 1) {
							callback($(e.currentTarget))
						}
						clicks = 0
					})
				}
			})

		} else {
			parent.on(event, selector, (e: JQueryEventObject) => {
				e.preventDefault
				callback($(e.currentTarget))
			})
		}

	}

	def schedule(delay: Int)(action: => Unit): Unit = {
		dom.setTimeout(() => action, delay)
	}

	$("body").on("keydown", (e: JQueryEventObject) => {
		shift = e.which == 16
	})

	$("body").on("keyup", (e: JQueryEventObject) => {
		shift = e.which == 16
	})

	def editable(selector: String, parent: JQuery = body): Unit = {
		dblclick(selector, parent)(e => openEdit(e))
	}

	def openEdit(e: JQuery): Unit = {
		closeEdit($(".edit"))
		e.replaceWith( s"""<input class="edit" prev="${e.html}" rel="${e.attr("rel")}" value="${e.html}">""")
		$(".edit").focus
	}

	def closeEdit(e: JQuery): Unit = {
		if (e.length > 0) {
			e.replaceWith( s"""<a rel="${e.attr("rel")}">${e.value}</a>""")
		}
	}

	body.on("keypress", ".edit", (e: JQueryEventObject) =>
		if (e.which == 13) {
			closeEdit($(e.target))
		}
	)

}

final class Search(private val $: JQuery) {

}

object SearchView extends View {

	val container = $("search-mode")
	val result = List($("#result1", container), $("#result2", container), $("#result3", container))

	def addResult(id: Int, r: Recipe) = {
		result(id).append(
			s"""
			  |<a href="" class="recipe">
			  |		<img src="${r.picture.get}">
			  |		<div class="infos">
			  |		    <h3>${r.title}</h3><span class="score pull-right">${r.stars}</span>
			  |		</div>
			  |	</a>
			""".stripMargin)
	}

	def trashResult: Unit = {
		result.foreach($("a", _).remove)
	}

	def apply(terms: String): Unit = {

	}

}

object SideView extends View {

	val container = $("#side")
	val cookingList = $("#cookingList", container)
	val lastSearch = $("#lastSearch", container)

	def updateCookingList(cs: Seq[CookingList]): Unit = {
		$("li", cookingList).remove
		cs.foreach(c => cookingList.append(s"""<li><a rel="${c.name}">${c.name}</a></li>"""))

		Events.click("a", cookingList)(e => Spatula.showList(e.attr("rel")))
		Events.editable("a", cookingList)

	}

}

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
		table.append(s"""<tr rel="${r.id}"><td>${r.title}</td><td>${r.stars}</td><td>${r.website}</td><td>${r.date}</td></tr>""")
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