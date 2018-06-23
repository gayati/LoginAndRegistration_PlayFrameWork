package services

import model.User
import scala.concurrent.Future
import javax.inject.Inject
import dao.UserDao
import scala.concurrent.ExecutionContext
import javax.inject.Singleton

@Singleton
class UserServiceImpl @Inject() (userDao: UserDao)(implicit ec: ExecutionContext) extends UserService {

  override def addUser(user: User): Future[String] = {
    userDao.add(user)
  }

  

  override def getEmail(email: String,password:String): Future[Option[User]] = {

    userDao.getEmail(email,password)
  }

}