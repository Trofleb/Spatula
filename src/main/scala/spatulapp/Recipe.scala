package spatulapp

case class Recipe(
	val title: String,
	val stars: Double,
	val picture: Option[String],
	val ingredients: Seq[String],
	val instructions: Seq[String],
	val website: String,
	val originUrl: String )