package spatulapp


trait UniqueID {

	private var count = -1

	def getID: Int = {
		count += 1
		count
	}

}
