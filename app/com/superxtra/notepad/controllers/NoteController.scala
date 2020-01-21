package com.superxtra.notepad.controllers

import com.superxtra.notepad.controllers.NoteController.CreateNoteRequest
import com.superxtra.notepad.model.Note
import com.superxtra.notepad.services.{NotesService, UsersService}
import javax.inject.{Inject, Singleton}
import play.api.libs.json.{JsError, Json, OFormat, Reads}
import play.api.mvc.{AbstractController, BodyParser, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class NoteController @Inject()(cc: ControllerComponents,
                               notesService: NotesService)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  import NoteController._

  private def validateJson[A: Reads]= parse.json.validate(
    _.validate[A].asEither.left.map(e => BadRequest(JsError.toJson(e)))
  )

  def retrieveUserNotes(userId: Int) = Action.async {
    val result: Future[List[Note]] = notesService.getNotes(userId)
    result.map { result =>
      Ok(Json.toJson(result))
    }
  }

  def createNote = Action(validateJson[CreateNoteRequest]) { implicit request =>
    val note = request.body
    notesService.createNote(note.title, note.body, note.userId)
    Ok(Json.obj("note" -> ("Title " + note.title + " added")))
  }
}

object NoteController {
  case class CreateNoteRequest(title: String, body: String, userId: Int)
  implicit val readsCreateNoteRequest: Reads[CreateNoteRequest] = Json.reads[CreateNoteRequest]
  implicit val userJsonFormat: OFormat[Note] = Json.format[Note]
}
