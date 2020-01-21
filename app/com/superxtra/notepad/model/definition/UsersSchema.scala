package com.superxtra.notepad.model.definition

import com.superxtra.notepad.model.User
import slick.lifted.TableQuery
import slick.jdbc.H2Profile.api._

object UsersSchema {
  class UsersTable(tag: Tag) extends Table[User](tag, "USERS")  {
    def id = column[Long]("ID", O.AutoInc, O.PrimaryKey)
    def name = column[String]("NAME")
    def lastName = column[String]("LAST_NAME")
    def email = column[String]("EMAIL")
    def * = (id, name, lastName, email) <> ((User.apply _).tupled, User.unapply)
  }
  val users = TableQuery[UsersTable]
}

