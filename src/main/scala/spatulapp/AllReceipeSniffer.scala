package spatulapp

import scala.util.{Try, Success, Failure}
import org.scalajs.dom.raw.Document
import org.scalajs.dom.raw.XMLHttpRequest
import scala.concurrent.ExecutionContext.Implicits.global
import org.scalajs.dom.raw.DOMParser

class AllReceipeSniffer extends Sniffer{

	def search(terms: String) = {
    val searchResult = getSearchResult(terms)

    searchResult onComplete {
      case Success(s: XMLHttpRequest) => println(parseHtml(s.responseText))
      case Failure(_) => 
    }

    Seq[spatulapp.Receipe]()
	}

	def parseTerms(terms: String) = terms.replaceAllLiterally(" ", "%20")

	def getSearchResult(terms: String) = {
		val searchQuery = parseTerms(terms)
		val  query = "http://allrecipes.com/search/default.aspx?qt=k&rt=r&pqt=k&ms=0&fo=0&wt=" + searchQuery
		Spatula.get(query)
	}

}