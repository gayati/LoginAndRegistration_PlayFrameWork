package dao

import com.google.inject.ImplementedBy
import scala.concurrent.Future
import model.User

@ImplementedBy(classOf[UserDaoImpl])
trait UserDao {

  def add(user: User): Future[String]
  def getEmail(email:String,password:String) :Future[Option[User]]
  }