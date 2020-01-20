package model

import play.api.libs.json._
import slick.jdbc.H2Profile.api._
import slick.lifted.Tag

case class UserPassword(id: Int, password: String, userId: Int)

object UserPassword {

  implicit val userPasswordJsonFormat: OFormat[UserPassword] = Json.format[UserPassword]
  implicit val readsPassword: Reads[UserPassword] = Json.reads[UserPassword]
}