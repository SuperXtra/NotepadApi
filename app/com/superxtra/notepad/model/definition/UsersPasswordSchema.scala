package com.superxtra.notepad.model.definition

import com.superxtra.notepad.model.UserPassword
import slick.lifted.TableQuery
import slick.jdbc.H2Profile.api._

object UsersPasswordSchema{
  class UsersPasswordTable(tag: Tag) extends Table[UserPassword](tag, "USERS_PASSWORD") {
      def id = column[Int]("ID", O.AutoInc, O.PrimaryKey)
      def password = column[String]("PASSWORD")
      def userId = column[Int]("USER_ID")
      def user = foreignKey("USER_FK", userId, TableQuery[UsersSchema.UsersTable])(_.id, onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)
      override def * = (id, password, userId) <> ((UserPassword.apply _).tupled, UserPassword.unapply)
    }

  val usersPassword = TableQuery[UsersPasswordTable]

}


