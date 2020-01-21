package com.superxtra.notepad.services

import com.superxtra.notepad.model.{Note, User, UserPassword}
import com.superxtra.notepad.repository.UserRepo
import javax.inject.Inject

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


class UsersService @Inject() (userRepository: UserRepo){
  def createNote(title: String, body: String, userId: Int): Future[Int] = userRepository.createNote(title, body, userId)


  def createUser(name: String, lastName: String, email: String, password: String): Future[Unit] = userRepository.create(name, lastName, email, password)
  def listAllUsers(): Future[List[User]] = userRepository.listAll()
  def getUserById(id: Int): Future[Option[User]] = userRepository.getUserById(id)
  def updatePassword(userId: Int, oldPassword: String, newPassword: String): Future[Any] = {
    val oldPasswordFromDB: Future[Option[UserPassword]] = userRepository.getPassword(userId)
    val oldPasswordAccordingToUser = oldPassword
    val newPasswordToBeUpdated = newPassword

    oldPasswordFromDB.map {
      case Some(value) if value.password == oldPasswordAccordingToUser  => userRepository.updatePassword(userId, newPasswordToBeUpdated)
      case None =>
    }
  }

  def getNotes(userId: Int): Future[List[Note]] = {
    userRepository.getNotes(userId)
  }
}
