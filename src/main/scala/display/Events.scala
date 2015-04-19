package display

import org.scalajs.dom
import org.scalajs.jquery.{jQuery => $, _}
import spatulapp.Spatula

import scala.collection.mutable

object Events {

  val body = $("body")
  private var clicksCount = mutable.Map[String, Int]()
  val click = on("click")_
  val dblclick = on("dblclick")_

  def on(event: String)(selector: String, parent: JQuery = body)(callback: JQuery => Unit): Unit = {

    if (event == "dblclick") {
        parent.on("click", selector, (e: JQueryEventObject) => {
          e.preventDefault
          if (clicksCount.contains(selector)) {
            clicksCount(selector) += 1
          } else {
            clicksCount(selector) = 0
          }
          if (clicksCount(selector) == 3) {
            schedule(250)({
              if (clicksCount(selector) > 1) {
                callback($(e.currentTarget))
              }
              clicksCount(selector) = 0
              println("double")
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

  def editable(selector: String, parent: JQuery = body): Unit = {
    dblclick(selector, parent)(e => openEdit(e))
  }

  def openEdit(e: JQuery): Unit = {
    closeEdit($(".edit"))
    e.replaceWith( s"""<input class="edit" rel="${e.attr("rel")}" value="${e.html}">""")
    $(".edit").focus
  }

  def closeEdit(e: JQuery): Unit = {
    if (e.length > 0) {
      val id = e.attr("rel")
      val value = e.value.toString
      e.replaceWith( s"""<a rel="$id">$value</a>""")
      val old = Spatula.cookingList(id)
      old.name = value
      Spatula.cookingList += old.name -> old
      Spatula.showList(id)
    }
  }

  body.on("keypress", ".edit", (e: JQueryEventObject) =>
    if (e.which == 13) {
      closeEdit($(e.target))
    }
  )

}