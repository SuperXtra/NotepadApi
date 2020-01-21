package com.superxtra.notepad.repository

import com.superxtra.notepad.model.{Note, UserPassword}
import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PasswordDAO @Inject()
(protected val dbConfigProvider: DatabaseConfigProvider)
                                    (implicit ec: ExecutionContext) {

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._
  import com.superxtra.notepad.model.definition.UsersPasswordSchema.usersPassword

  def getPassword(id: Long): Future[Option[UserPassword]] = db.run {
    usersPassword.filter(_.userId === id).result.headOption
  }

  def updatePassword(id: Long, password: String): Future[Int] = {
    val q = for { p <- usersPassword if p.userId === id} yield p.password
    db.run(q.update(password))
  }

}
