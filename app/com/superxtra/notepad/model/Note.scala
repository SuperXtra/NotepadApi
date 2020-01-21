package com.superxtra.notepad.model

import play.api.libs.json.Json

case class Note(id: Int, title: String, body: String, userId: Int)

object Note {
  implicit val userJsonFormat = Json.format[Note]
  implicit val reads = Json.reads[Note]
}
