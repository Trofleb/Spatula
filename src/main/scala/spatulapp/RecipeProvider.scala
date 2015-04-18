package spatulapp

import org.scalajs.jquery.{JQuery, jQuery}
import scala.concurrent._

abstract class RecipeProvider{
  val MAX_RECIPE = 3
//-----
  var queryUrl: String = ""
//-----
  def parseHtml(text: String) = jQuery(jQuery.parseHTML(text))
  def find(parsed: JQuery, what: String) = parsed.find(what)
//-----
	def parseResults(html: String): Seq[Future[Recipe]]
//-----
  def escape(terms: String) = terms.replaceAllLiterally(" ", "%20")
  def search(terms: String) = {
    val searchQuery = escape(terms)
    val query = queryUrl + searchQuery
    IOHandler.get(query)(parseResults)
  }
}