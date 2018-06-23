package model
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._


case class User(id: Int, firstName: String, lastName: String, mobile: Long, email: String,password:String)

case class UserFormData(firstName : String, lastName : String , mobile : Long , email : String,password:String)

object UserForm {
  val form = Form(
    mapping(
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "mobile" -> longNumber,
      "email" -> nonEmptyText,
      "password" -> nonEmptyText
    )(UserFormData.apply)(UserFormData.unapply)
  )
}

case class LoginFormData(email:String,password:String)

object LoginForm {
  val loginForm = Form(
    mapping(   
      "email" -> email,
      "password" -> nonEmptyText
    )(LoginFormData.apply)(LoginFormData.unapply)
  )
}


