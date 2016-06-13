package models

import play.api.libs.json.{Json, OFormat}

/**
  * Created by johnreed on 6/12/16.
  */
case class Person(name: String)

object Person {
  implicit val personFormat: OFormat[Person] = Json.format[Person]

}

object temp {
  case class User(name: String)

  implicit val userWrites = Json.format[User]
  // macro-compiler replaces Json.format[User] by injecting into compile chain
  // the exact code you would write yourself. This is strictly equivalent to:
  // implicit val userWrites = (
  //   (__ \ 'name).format[String]
  //   )(User.apply, unlift(User.unapply))
}