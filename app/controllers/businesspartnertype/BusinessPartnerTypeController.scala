package controllers.businesspartnertype

import models.businesspartnertype.BusinessPartnerTypeDataBaseOperations
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import viewmodels.businesspartnertype.BusinessPartnerTypeViewModel
import viewmodels.common.CommonInfoViewModel

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by ShchykalauM on 24.01.2017.
  */
object BusinessPartnerTypeController extends Controller {

  def businessPartnerType = Action {
    Ok(views.html.businesspartnertype.businesspartnertype())
  }

  def getBusinessPartnerType = Action {
    val list = Await.ready(BusinessPartnerTypeDataBaseOperations.listAll, Duration.Inf).value.get.get
    val result = list.map(o => new BusinessPartnerTypeViewModel(o))
    Ok(Json.toJson(result))
  }

  def saveBusinessPartnerType = Action { request =>
    val json = request.body.asJson.get
    val save = json.as[BusinessPartnerTypeViewModel]
    val id = Await.ready(BusinessPartnerTypeDataBaseOperations.insert(BusinessPartnerTypeViewModel.toBusinessPartnerType(save)), Duration.Inf).value.get.get
    save.id = Option(id)
    Ok(Json.toJson(save))
  }

  def deleteBusinessPartnerType(id: Long) = Action {
    Await.ready(BusinessPartnerTypeDataBaseOperations.delete(id), Duration.Inf)
    Ok("delete")
  }

  def updateBusinessPartnerType() = Action { request =>
    val json = request.body.asJson.get
    val update = json.as[BusinessPartnerTypeViewModel]
    BusinessPartnerTypeDataBaseOperations.update(BusinessPartnerTypeViewModel.toBusinessPartnerType(update))
    Ok(Json.toJson(update))
  }

  def getBusinessPartnerTypeInfo = Action {
    val list = Await.ready(BusinessPartnerTypeDataBaseOperations.listAll, Duration.Inf).value.get.get
    val result = list.map(o => CommonInfoViewModel(o.id.get, o.name))
    Ok(Json.toJson(result))
  }


}