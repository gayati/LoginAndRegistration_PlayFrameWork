package dao

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import model.User
import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import javax.inject.Singleton

import play.api.Play
import scala.concurrent.Await
import scala.concurrent.duration.Duration

@Singleton
class UserDaoImpl @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends UserDao {

  val dbConfig = dbConfigProvider.get[JdbcProfile]


  import dbConfig._
  import profile.api._

  private class UserTable(tag: Tag) extends Table[User](tag, "User") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def firstName = column[String]("first_name")
  
    def lastName = column[String]("last_name")

    def mobile = column[Long]("mobile")

    def email = column[String]("email")
    
    def passWord = column[String]("password")
    
    

   override def * =
      (id, firstName,lastName,mobile,email,passWord) <> (User.tupled, User.unapply)
  }

  private val users = TableQuery[UserTable]

  override def add(user: User): Future[String] = {
  //  println(users.filter(_.email === user.email).result)
    db.run(users += user).map(res => "User successfully added in database")
  }
    
     override def getEmail(email: String,password:String):  Future[Option[User]] ={
       println(email)
       db.run(users.filter(_.email === email).result.headOption)
  }
    
}  






