package spatulapp

import scala.collection.immutable.Seq

class Receipe(
	val title: String,
	val stars: Int,
	val pictures: Seq[String],
	val ingredients: Seq[String],
	val stepByStep: Seq[String],
	val website: String,
	val originUrl: String ){

	def presentationPicture() = 
		if(pictures.isEmpty) 
			"" 
		else 
			pictures.head


}