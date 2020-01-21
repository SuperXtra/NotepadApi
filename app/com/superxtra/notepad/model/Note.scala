package com.superxtra.notepad.model

import play.api.libs.json.{Json, OFormat, Reads}

case class Note(id: Long, title: String, body: String, userId: Long)


