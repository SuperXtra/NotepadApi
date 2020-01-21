package com.superxtra.notepad.services

import com.superxtra.notepad.dto.{NoteDto, UpdatePasswordDto}
import com.superxtra.notepad.model.{Note, User, UserDto, UserPassword}
import com.superxtra.notepad.repository.UserRepo
import javax.inject.Inject
import slick.dbio.{Effect, NoStream}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class UsersService @Inject() (userRepository: UserRepo){
  def createNote(note: NoteDto): Future[Int] = userRepository.createNote(Note(0, note.title, note.body, note.userId))


  def createUser(userDto: UserDto): Future[Unit] = userRepository.create(userDto.name, userDto.lastName, userDto.email, userDto.password)
  def listAllUsers(): Future[List[User]] = userRepository.listAll()
  def getUserById(id: Int) = userRepository.getUserById(id)
  def updatePassword(updateData: UpdatePasswordDto): Future[Any] = {
    val oldPassword: Future[Option[UserPassword]] = userRepository.getPassword(updateData.userId)
    val userId = updateData.userId
    val oldPasswordAccordingToUser = updateData.oldPassword
    val newPasswordToBeUpdated = updateData.newPassword

    oldPassword.map {
      case Some(value) if value.password == oldPasswordAccordingToUser  => userRepository.updatePassword(userId, newPasswordToBeUpdated)
      case None =>
    }
  }

  def getNotes(userId: Int): Future[List[Note]] = {
    userRepository.getNotes(userId)
  }
}
