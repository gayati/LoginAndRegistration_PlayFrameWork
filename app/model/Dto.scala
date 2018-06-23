package model

import play.api.libs.json.Json

case class LoginDto(email:String,password:String) 

object LoginDto {
  implicit val LoginDto = Json.format[LoginDto]
}