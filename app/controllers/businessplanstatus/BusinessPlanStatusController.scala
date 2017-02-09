package controllers.businessplanstatus


import models.businessplanstatus.BusinessPlanStatusDataBaseOperations
import play.api.libs.json.Json
import play.api.mvc._
import viewmodels.businessplanstatus.BusinessPlanStatusViewModel
import viewmodels.common.CommonInfoViewModel

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by ShchykalauM on 24.01.2017.
  */
object BusinessPlanStatusController extends Controller {

  def businessPlanStatus = Action {
    Ok(views.html.businessplanstatus.businessplanstatus())
  }

  def getBusinessPlanStatus = Action {
    val list = Await.ready(BusinessPlanStatusDataBaseOperations.listAll, Duration.Inf).value.get.get
    val result = list.map(o => new BusinessPlanStatusViewModel(o))
    Ok(Json.toJson(result))
  }

  def saveBusinessPlanStatus = Action { request =>
    val json = request.body.asJson.get
    val save = json.as[BusinessPlanStatusViewModel]
    val id = Await.ready(BusinessPlanStatusDataBaseOperations.insert(BusinessPlanStatusViewModel.toBusinessPlanStatus(save)), Duration.Inf).value.get.get
    save.id = Option(id)
    Ok(Json.toJson(save))
  }

  def deleteBusinessPlanStatus(id: Long) = Action {
    Await.ready(BusinessPlanStatusDataBaseOperations.delete(id), Duration.Inf)
    Ok("delete")
  }

  def updateBusinessPlanStatus() = Action { request =>
    val json = request.body.asJson.get
    val update = json.as[BusinessPlanStatusViewModel]
    BusinessPlanStatusDataBaseOperations.update(BusinessPlanStatusViewModel.toBusinessPlanStatus(update))
    Ok(Json.toJson(update))
  }

  def getBusinessPlanStatusInfo = Action {
    val list = Await.ready(BusinessPlanStatusDataBaseOperations.listAll, Duration.Inf).value.get.get
    val result = list.map(o => CommonInfoViewModel(o.id.get, o.name))
    Ok(Json.toJson(result))
  }


}
