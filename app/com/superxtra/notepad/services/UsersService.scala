package com.superxtra.notepad.services

import com.superxtra.notepad.model.{Note, User, UserPassword}
import com.superxtra.notepad.repository.UserDAO
import javax.inject.Inject

import scala.concurrent.Future

class UsersService @Inject() (userRepository: UserDAO){
  def createUser(name: String, lastName: String, email: String, password: String): Future[Unit] = userRepository.create(name, lastName, email, password)
  def listAllUsers(): Future[List[User]] = userRepository.listAll()
  def getUserById(id: Int): Future[Option[User]] = userRepository.getUserById(id)
}
