package com.superxtra.notepad.controllers

import com.superxtra.notepad.controllers.UserController._
import com.superxtra.notepad.services.UsersService
import javax.inject._
import play.api.mvc._
import play.api.libs.json.{JsError, Json, Reads}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserController @Inject()(cc: ControllerComponents,
                               userService: UsersService)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  private def validateJson[A: Reads] = parse.json.validate(
    _.validate[A].asEither.left.map(e => BadRequest(JsError.toJson(e)))
  )

  def registerUser = Action(validateJson[RegisterUserRequest]) { implicit request =>
    val registerUser = request.body
    userService.createUser(registerUser.name, registerUser.lastName, registerUser.email, registerUser.password)
    Ok(Json.toJson("Created user: {}", registerUser.name))
  }

  def getUsers = Action.async { _ =>
    userService.listAllUsers().map { users =>
      Ok(Json.toJson(users))
    }
  }
}

object UserController {
  case class RegisterUserRequest(name: String, lastName: String, email: String, password: String)
  implicit val readsUserNoteRequest: Reads[RegisterUserRequest] = Json.reads[RegisterUserRequest]
}


