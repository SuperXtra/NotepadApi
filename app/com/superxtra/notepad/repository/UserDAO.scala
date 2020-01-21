package com.superxtra.notepad.repository

import com.superxtra.notepad.model.{Note, User, UserPassword}
import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserDAO @Inject()
(protected val dbConfigProvider: DatabaseConfigProvider)
(implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._
  import com.superxtra.notepad.model.definition.UsersSchema.users
  import com.superxtra.notepad.model.definition.UsersPasswordSchema.usersPassword



  def getUserById(id: Long): Future[Option[User]] = db.run {
    users.filter(_.id === id).result.headOption
  }

  def create(name: String, lastName: String, email: String, password: String): Future[Unit] = {
    def insertUser() = users returning users.map(_.id)

    val tx = for {
      userId <- insertUser += User(0, name, lastName, email)
      _ <- usersPassword += UserPassword(0, password, userId)
    } yield ()

    tx.transactionally
    db.run(tx)
  }

  def listAll(): Future[List[User]] = db.run {
    users.to[List].result
  }
}
