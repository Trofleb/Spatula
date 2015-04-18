package spatulapp

class AllReceipeSniffer extends Sniffer{
	def search(terms: String) = {
    val searchResult = getSearchResult(terms)
    
	}

	def parseTerms(terms: String) = terms.replaceAllLiterally(" ", "%20")

	def getSearchResult(terms: String) = {
		val searchQuery = parseTerms(terms)
		val  query = "http://allrecipes.com/search/default.aspx?qt=k&rt=r&pqt=k&ms=0&fo=0&wt=" + searchQuery
		Spatula.get(query)
	}


}