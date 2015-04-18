package spatulapp

import org.scalajs.dom.raw.DOMParser
import org.scalajs.jquery.{JQuery, jQuery}

abstract class Sniffer{
  def parseHtml(text: String) = jQuery(jQuery.parseHTML(text))
  def find(parsed: JQuery)(what: String) = parsed.find(what)
	def search(terms: String): Seq[Receipe]
}