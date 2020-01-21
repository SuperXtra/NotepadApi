package com.superxtra.notepad.repository

import com.superxtra.notepad.model.{Note, User, UserPassword}
import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserRepo @Inject()
(protected val dbConfigProvider: DatabaseConfigProvider)
(implicit ec: ExecutionContext) {



  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private val passwords = TableQuery[UsersPasswordTable]
  private val users = TableQuery[UsersTable]
  private val notes = TableQuery[NotesTable]

  private class UsersTable(tag: Tag) extends Table[User](tag, "USERS")  {
    def id = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    def name = column[String]("NAME")
    def lastName = column[String]("LAST_NAME")
    def email = column[String]("EMAIL")
    def * = (id, name, lastName, email) <> ((User.apply _).tupled, User.unapply)
  }

  private class UsersPasswordTable(tag: Tag) extends Table[UserPassword](tag, "USERS_PASSWORD") {
    def id = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    def password = column[String]("PASSWORD")
    def userId = column[Int]("USER_ID")
    def user = foreignKey("USER_FK", userId, users)(_.id, onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)
    override def * = (id, password, userId) <> ((UserPassword.apply _).tupled, UserPassword.unapply)
  }

  private class NotesTable(tag: Tag) extends Table[Note](tag, "NOTES") {
    def id = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    def title  =column[String]("TITLE")
    def body = column[String]("BODY")
    def userId = column[Int]("USER_ID")
    def user = foreignKey("USER_FK", userId, users)(_.id, onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)
    override def * = (id, title, body, userId) <> ((Note.apply _).tupled, Note.unapply)
  }

  def getUserById(id: Int): Future[Option[User]] = db.run {
    users.filter(_.id === id).result.headOption
  }

  def create(name: String, lastName: String, email: String, password: String): Future[Unit] = {
    def insertUser() = users returning users.map(_.id)

    val tx = for {
      userId <- insertUser += User(0, name, lastName, email)
      _ <- passwords += UserPassword(0, password, userId)
    } yield ()

    tx.transactionally
    db.run(tx)
  }

  def createNote(note: Note): Future[Int] = db.run {
      notes += note
  }

  def getNotes(id: Int): Future[List[Note]] = db.run {
    notes.filter(_.userId === id).to[List].result
  }

  def listAll(): Future[List[User]] = db.run {
    users.to[List].result
  }

  def getPassword(id: Int): Future[Option[UserPassword]] = db.run {
    passwords.filter(_.userId === id).result.headOption
  }

  def updatePassword(id: Int, password: String): Future[Int] = {
    val q = for { p <- passwords if p.userId === id} yield p.password
    db.run(q.update(password))
  }

}
