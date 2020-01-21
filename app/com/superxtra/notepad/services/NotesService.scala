package com.superxtra.notepad.services

import com.superxtra.notepad.model.Note
import com.superxtra.notepad.repository.NoteDAO
import javax.inject.Inject

import scala.concurrent.Future

class NotesService @Inject() (noteDAO: NoteDAO) {
  def createNote(title: String, body: String, userId: Int): Future[Int] = noteDAO.createNote(title, body, userId)
  def getNotes(userId: Int): Future[List[Note]] = {
    noteDAO.getNotes(userId)
  }
}
