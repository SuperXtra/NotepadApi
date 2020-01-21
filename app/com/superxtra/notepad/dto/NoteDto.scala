package com.superxtra.notepad.dto

import play.api.libs.json.Json

case class NoteDto(title: String, body: String, userId: Int)

object NoteDto {
  implicit val userJsonFormat = Json.format[NoteDto]
  implicit val reads = Json.reads[NoteDto]
}
