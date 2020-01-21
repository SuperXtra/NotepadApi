package com.superxtra.notepad.model.controllers

import com.superxtra.notepad.model.{Note, UserDto}
import com.superxtra.notepad.model.dto.{NoteDto, UpdatePasswordDto}
import com.superxtra.notepad.model.services.UsersService
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

  def registerUser = Action(validateJson[UserDto]) { implicit request =>
    val userDto = request.body
    userService.createUser(userDto)
    Ok(Json.toJson("Created user: {}", userDto.name))
  }

  def updatePassword = Action(validateJson[UpdatePasswordDto]) { implicit request =>
    val data = request.body
    userService.updatePassword(data)
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

  def createNote = Action(validateJson[NoteDto]) { implicit request =>
    val note = request.body
    userService.createNote(note)
    Ok(Json.obj("note" -> ("Title " + note.title + " added")))
  }
}
