package com.superxtra.notepad.controllers

import com.superxtra.notepad.controllers.PasswordController.UpdatePasswordRequest
import com.superxtra.notepad.services.PasswordService
import javax.inject.{Inject, Singleton}
import play.api.libs.json.{JsError, Json, Reads}
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

@Singleton
class PasswordController @Inject()(cc: ControllerComponents,
                                   passwordService: PasswordService)
                                  (implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  private def validateJson[A: Reads] = parse.json.validate(
    _.validate[A].asEither.left.map(e => BadRequest(JsError.toJson(e)))
  )

  def updatePassword = Action(validateJson[UpdatePasswordRequest]) { implicit request =>
    val req = request.body
    passwordService.updatePassword(req.userId, req.oldPassword, req.newPassword)
    Ok("Password has been updated")
  }

}

object PasswordController {
  case class UpdatePasswordRequest(userId: Int, oldPassword: String, newPassword: String)
  implicit val readsUpdatePasswordRequest: Reads[UpdatePasswordRequest] = Json.reads[UpdatePasswordRequest]
}
