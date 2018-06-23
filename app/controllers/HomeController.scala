package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.mvc.Session
import model.UserForm
import play.api.i18n.I18nSupport
import scala.concurrent.Future
import model.User
import services.UserService
import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global
import model.LoginForm
import play.api.data.Form
import dao.UserDao
import model.LoginFormData
import play.mvc.Http.Response

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() (cc: ControllerComponents, userService: UserService, userDao: UserDao)(ec: ExecutionContext) extends AbstractController(cc) with I18nSupport {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index(UserForm.form))
  }

  def addUser() = Action.async { implicit request =>
    UserForm.form.bindFromRequest.fold(
      errorForm => Future.successful(Ok("Please enter the valid input")),
      data => {
        val newUser = User(0, data.firstName, data.lastName, data.mobile, data.email, data.password)
        userService.addUser(newUser).map(res =>
          Redirect(routes.HomeController.success()))
      })
  }

  def success() = Action { implicit request: Request[AnyContent] =>
    Ok("Suuceesss...........")
  }

  def loginUser() = Action { implicit request =>
    Ok(views.html.login(LoginForm.loginForm))
  }

  def authenticate = Action.async { implicit request =>
    LoginForm.loginForm.bindFromRequest.fold(
      formWithErrors => {
        Future.successful(BadRequest(views.html.login(formWithErrors)))
      },
      data => {
        val newLoginUser = LoginFormData(data.email, data.password)
        userService.getEmail(newLoginUser.email, newLoginUser.password).map(loginFuture =>
          if (loginFuture.equals(None)) {
            Conflict("User does not exists........")
          } else {
            println(loginFuture.get.firstName)
            Ok(views.html.welcome()).withSession("firstName" -> loginFuture.get.firstName)
            //.withHeaders(CACHE_CONTROL -> "no-cache,no-store,must-revalidate",PRAGMA -> "no-cache",EXPIRES -> "0")
          })
      })
  }

  def logout = Action { implicit request =>
    Redirect(routes.HomeController.loginUser()).withNewSession
    //.withHeaders(CACHE_CONTROL -> "no-cache,no-store,must-revalidate",PRAGMA -> "no-cache",EXPIRES -> "0")
    Ok(views.html.login(LoginForm.loginForm)).withNewSession

  }
}
