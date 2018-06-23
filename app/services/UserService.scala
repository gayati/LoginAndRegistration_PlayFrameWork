package services

import model.User
import scala.concurrent.Future
import com.google.inject.ImplementedBy

@ImplementedBy(classOf[UserServiceImpl])
trait UserService {

  def addUser(user: User): Future[String]
  def getEmail(email: String,password:String): Future[Option[User]]
}