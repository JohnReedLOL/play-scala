package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import scala.trace.Pos

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() extends Controller {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok.apply(views.html.index("Your new application is ready."))
  }
  
  def foo = Action {
    Redirect(routes.HomeController.show( Pos() + "\n"))
  }

  def show(toPrint: String) = Action {
    Ok(toPrint /*+ Pos()*/).withCookies(
      Cookie("theme", "blue"))
  }
  
  import play.api.http.HttpEntity

    def dontCare = Action {
        {
            val ok = Ok("Hello world!")
            val notFound = NotFound
            val pageNotFound = NotFound(<h1>Page not found</h1>)
            // val badRequest = BadRequest(views.html.form(formWithErrors))
            val oops = InternalServerError("Oops")
            val anyStatus = Status(488)("Strange response type")
        }
      Result(
        header = ResponseHeader(200, Map.empty),
        body = HttpEntity.Strict(akka.util.ByteString( /*Pos()*/"Pos" ), Some("text/plain"))
      )
    }
    
    def foo2 = TODO

    def redirect = Action {
        Redirect("https://www.google.com")
    }
import play.api.data._
import play.api.data.Forms._

  case class UserData(name: String, age: Int)

  def form = Action { request =>
    val userForm: Form[UserData] = Form(
      mapping(
        "name" -> text,
        "age" -> number
      )(UserData.apply)(UserData.unapply)
    )
    val anyData = Map("name" -> "bob", "age" -> "21")
    val intermediate: Form[UserData] = userForm.bind(anyData)
    val userData: UserData = intermediate.get

    val intermediate2: Form[UserData] = userForm.bindFromRequest()(request)
    // val userData2: UserData = intermediate2.get // throws exception

    Ok("Hi " + userData.name + " " + userData.age /*+ Pos()*/)
  }

}
