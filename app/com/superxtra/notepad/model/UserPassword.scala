package com.superxtra.notepad.model

import play.api.libs.json._

case class UserPassword(id: Long, password: String, userId: Long)

object UserPassword {

  implicit val userPasswordJsonFormat: OFormat[UserPassword] = Json.format[UserPassword]
  implicit val readsPassword: Reads[UserPassword] = Json.reads[UserPassword]
}