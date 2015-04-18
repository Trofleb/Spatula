package spatulapp

abstract class Sniffer{
  val parser = new DOMParser()
  def parseHtml(text: String) = parser.parseFromString(text, "text/html")
	def search(terms: String): Seq[Receipe]
}