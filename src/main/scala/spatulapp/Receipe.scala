package spatulapp

import scala.Seq

class Receipe(
	val title: String,
	val stars: Double,
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