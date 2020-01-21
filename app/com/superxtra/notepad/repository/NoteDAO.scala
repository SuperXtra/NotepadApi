package com.superxtra.notepad.repository

import com.superxtra.notepad.model.Note
import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class NoteDAO @Inject()
(protected val dbConfigProvider: DatabaseConfigProvider)
(implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._
  import com.superxtra.notepad.model.definition.NotesSchema.notes

  def createNote(title: String, body: String, userId: Int): Future[Int] = db.run {
    notes += Note(0, title, body, userId)
  }

  def getNotes(id: Int): Future[List[Note]] = db.run {
    notes.filter(_.userId === id).to[List].result
  }
}
