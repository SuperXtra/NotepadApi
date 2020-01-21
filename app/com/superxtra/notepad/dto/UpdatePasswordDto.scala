package com.superxtra.notepad.dto

import play.api.libs.json.Json

case class UpdatePasswordDto(userId: Int, oldPassword: String, newPassword: String)

object UpdatePasswordDto {
  implicit val userJsonFormat = Json.format[UpdatePasswordDto]
  implicit val reads = Json.reads[UpdatePasswordDto]
}
