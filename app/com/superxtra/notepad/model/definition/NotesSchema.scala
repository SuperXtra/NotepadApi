package com.superxtra.notepad.model.definition

import com.superxtra.notepad.model.Note
import slick.lifted.TableQuery
import slick.jdbc.H2Profile.api._

object NotesSchema {
  class NotesTable(tag: Tag) extends Table[Note](tag, "NOTES") {
    def id = column[Long]("ID", O.AutoInc, O.PrimaryKey)
    def title  =column[String]("TITLE")
    def body = column[String]("BODY")
    def userId = column[Long]("USER_ID")
    def user = foreignKey("USER_FK", userId, UsersSchema.users)(_.id, onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)
    override def * = (id, title, body, userId) <> ((Note.apply _).tupled, Note.unapply)
  }
  val notes = TableQuery[NotesTable]
}

