package com.superxtra.notepad.services

import com.superxtra.notepad.model.UserPassword
import com.superxtra.notepad.repository.PasswordDAO
import javax.inject.Inject

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class PasswordService @Inject() (passwordDAO: PasswordDAO) {

  def updatePassword(userId: Int, oldPassword: String, newPassword: String): Future[Any] = {
    val oldPasswordFromDB: Future[Option[UserPassword]] = passwordDAO.getPassword(userId)
    val oldPasswordAccordingToUser = oldPassword
    val newPasswordToBeUpdated = newPassword

    oldPasswordFromDB.map {
      case Some(value) if value.password == oldPasswordAccordingToUser  => passwordDAO.updatePassword(userId, newPasswordToBeUpdated)
      case None =>
    }
  }
}
