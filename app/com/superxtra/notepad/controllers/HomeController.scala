package com.superxtra.notepad.controllers

import com.superxtra.notepad.controllers.HomeController._
import com.superxtra.notepad.model.Note
import com.superxtra.notepad.services.UsersService
import javax.inject._
import play.api.mvc._
import play.api.libs.json.{JsError, Json, Reads}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class HomeController @Inject()(cc: ControllerComponents,
                               userService: UsersService)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def validateJson[A: Reads] = parse.json.validate(
    _.validate[A].asEither.left.map(e => BadRequest(JsError.toJson(e)))
  )

  def registerUser = Action(validateJson[RegisterUserRequest]) { implicit request =>
    val registerUser = request.body
    userService.createUser(registerUser.name, registerUser.lastName, registerUser.email, registerUser.password)
    Ok(Json.toJson("Created user: {}", registerUser.name))
  }

  def updatePassword = Action(validateJson[UpdatePasswordRequest]) { implicit request =>
    val req = request.body
    userService.updatePassword(req.userId, req.oldPassword, req.newPassword)
    Ok("Password has been updated")
  }

  def getUsers = Action.async { _ =>
    userService.listAllUsers().map { users =>
      Ok(Json.toJson(users))
    }
  }

  def retrieveUserNotes(userId: Int) = Action.async {
    val result: Future[List[Note]] = userService.getNotes(userId)
    result.map { result =>
      Ok(Json.toJson(result))
    }
  }

  def createNote = Action(validateJson[CreateNoteRequest]) { implicit request =>
    val note = request.body
    userService.createNote(note.title, note.body, note.userId)
    Ok(Json.obj("note" -> ("Title " + note.title + " added")))
  }

}

object HomeController {

  case class CreateNoteRequest(title: String, body: String, userId: Int)
  case class RegisterUserRequest(name: String, lastName: String, email: String, password: String)
  case class UpdatePasswordRequest(userId: Int, oldPassword: String, newPassword: String)

  implicit val readsCreateNoteRequest: Reads[CreateNoteRequest] = Json.reads[CreateNoteRequest]
  implicit val readsUserNoteRequest: Reads[RegisterUserRequest] = Json.reads[RegisterUserRequest]
  implicit val readsUpdatePasswordRequest: Reads[UpdatePasswordRequest] = Json.reads[UpdatePasswordRequest]

}


