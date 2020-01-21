package com.superxtra.notepad.model

import play.api.libs.json.{Json, OFormat, Reads}

case class Note(id: Int, title: String, body: String, userId: Int)

object Note {
  implicit val userJsonFormat: OFormat[Note] = Json.format[Note]
  implicit val reads: Reads[Note] = Json.reads[Note]
}
