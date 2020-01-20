package model

import play.api.libs.json._
import slick.lifted.{TableQuery, Tag}
import slick.jdbc.JdbcProfile
import slick.model.Table

case class User(id: Int, name: String, lastName: String, email: String)

case class UserDto(name: String, lastName: String, email: String, password: String)

object User {
  implicit val userJsonFormat: OFormat[User] = Json.format[User]
  implicit val reads: Reads[User] = Json.reads[User]
}

object UserDto {
  implicit val userJsonFormat: OFormat[UserDto] = Json.format[UserDto]
  implicit val reads: Reads[UserDto] = Json.reads[UserDto]
}