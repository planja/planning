package controllers.usersofbusinesspartner

import com.fasterxml.jackson.databind.ObjectMapper
import models.userofbusinesspartner.{UserOfBusinessPartner, UserOfBusinessPartnerDataBaseOperations}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import viewmodels.common.CommonInfoViewModel
import viewmodels.userofbusinesspartner.UserOfBusinessPartnerViewModel

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by ShchykalauM on 31.01.2017.
  */
object UsersOfBusinessPartnerController extends Controller {

  def usersOfBusinessPartner = Action {
    Ok(views.html.usersofbusinesspartner.usersofbusinesspartner())
  }

  def getUsersOfBusinessPartner = Action {
    val list = Await.ready(UserOfBusinessPartnerDataBaseOperations.listAll, Duration.Inf).value.get.get
    val result = list.map(o => new UserOfBusinessPartnerViewModel(o))
    Ok(Json.toJson(result))
  }

  def saveUserOfBusinessPartner() = Action { request =>
    val json = request.body.asJson.get
    val save = json.as[UserOfBusinessPartnerViewModel]
    val id = Await.ready(UserOfBusinessPartnerDataBaseOperations.insert(UserOfBusinessPartnerViewModel.toUserOfBusinessPartner(save)), Duration.Inf).value.get.get
    save.id = Option(id)
    Ok(Json.toJson(save))
  }

  def updateUserOfBusinessPartner() = Action { request =>
    val json = request.body.asJson.get
    val update = json.as[UserOfBusinessPartnerViewModel]
    UserOfBusinessPartnerDataBaseOperations.update(UserOfBusinessPartnerViewModel.toUserOfBusinessPartner(update))
    Ok(Json.toJson(update))
  }

  def getUsersOfBusinessPartnerInfo = Action {
    val list = Await.ready(UserOfBusinessPartnerDataBaseOperations.listAll, Duration.Inf).value.get.get
    val result = list.map(o => CommonInfoViewModel(o.id.get, o.firstName + " " + o.lastName))
    Ok(Json.toJson(result))
  }


  def deleteUserOfBusinessPartner(id: Long) = Action {
    Await.ready(UserOfBusinessPartnerDataBaseOperations.delete(id), Duration.Inf)
    Ok("delete")
  }

}
