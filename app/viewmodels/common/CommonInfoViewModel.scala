package viewmodels.common

import play.api.libs.json.Json

/**
  * Created by ShchykalauM on 23.01.2017.
  */
case class CommonInfoViewModel(id: Long, text: String)

object CommonInfoViewModel {
  implicit val commonInfoViewModel = Json.format[CommonInfoViewModel]
}