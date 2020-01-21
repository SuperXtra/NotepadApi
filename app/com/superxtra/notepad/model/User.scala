package com.superxtra.notepad.model

import play.api.libs.json._

case class User(id: Int, name: String, lastName: String, email: String)

object User {
  implicit val userJsonFormat: OFormat[User] = Json.format[User]
  implicit val reads: Reads[User] = Json.reads[User]
}