package controllers.businesspartners

import models.businesspartner.BusinessPartnerDataBaseOperations
import models.businessplan.BusinessPlanDataBaseOperations
import models.userofbusinesspartner.UserOfBusinessPartnerDataBaseOperations
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import viewmodels.businesspartner.{BusinessPartnerDetailsViewModel, BusinessPartnerViewModel}
import viewmodels.businessplan.BusinessPlanViewModel
import viewmodels.common.CommonInfoViewModel

import scala.collection.mutable.ListBuffer
import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by ShchykalauM on 25.01.2017.
  */
object BusinessPartnersController extends Controller {

  def businessPartners = Action {
    Ok(views.html.businesspartners.businesspartner())
  }

  def saveBusinessPartner = Action { request =>
    val json = request.body.asJson.get
    val save = json.as[BusinessPartnerViewModel]
    val result = Await.ready(BusinessPartnerDataBaseOperations.insert(BusinessPartnerViewModel.toBusinessPartner(save)), Duration.Inf)
    Ok("save")
  }

  def getBusinessPartnersInfo = Action {
    val list = Await.ready(BusinessPartnerDataBaseOperations.listAll, Duration.Inf).value.get.get
    val result = list.map(o => CommonInfoViewModel(o.id.get, o.shortName))
    Ok(Json.toJson(result))
  }

  def deleteBusinessPartner(id: Long) = Action {
    BusinessPartnerDataBaseOperations.delete(id)
    Ok("delete")
  }


  //For details
  def editBusinessPartner(id: Long) = Action {
    val item = Await.ready(BusinessPartnerDataBaseOperations.find(id), Duration.Inf)
    if (item.value.get.get.isDefined)
      Ok(views.html.businesspartners.businesspartnerdetails(id))
    else Ok(views.html.common.notfound())
  }

  def loadDataForBusinessPartner(id: Long) = Action {
    val businessPartner = Await.ready(BusinessPartnerDataBaseOperations.find(id), Duration.Inf).value.get.get.get
    val usersOfBusinessPartner = Await.ready(UserOfBusinessPartnerDataBaseOperations.listAll, Duration.Inf).value.get.get
    val usersIdOfBusinessPartner = BusinessPartnerDetailsViewModel.getUsersIdOfBusinessPartner(usersOfBusinessPartner, id)
    val businessPartnerDetailsViewModel = new BusinessPartnerDetailsViewModel(businessPartner, usersIdOfBusinessPartner)
    Ok(Json.toJson(businessPartnerDetailsViewModel))
  }

  def updateBusinessPartner() = Action { request =>
    val json = request.body.asJson.get
    val update = json.as[BusinessPartnerDetailsViewModel]
    BusinessPartnerDataBaseOperations.update(BusinessPartnerDetailsViewModel.toBusinessPartner(update))
    var users = Await.ready(UserOfBusinessPartnerDataBaseOperations.listAll, Duration.Inf).value.get.get
    val filteredUsers = users.filter(o => o.businessPartnerId == update.id)
    UserOfBusinessPartnerDataBaseOperations.updateBusinessPartner(filteredUsers, update.usersIdOfBusinessPartners, users, update.id.get)
    Ok("update")
  }

  def loadBusinessPlan(id: Long) = Action {
    var list = Await.ready(BusinessPlanDataBaseOperations.listAll, Duration.Inf).value.get.get
    list = list.filter(o => o.businessPartnerId == id)
    if (list.isEmpty)
      Ok("null")
    else Ok(Json.toJson(new BusinessPlanViewModel(list.head)))
  }

  def saveBusinessPlan = Action { request =>
    val json = request.body.asJson.get
    val save = json.as[BusinessPlanViewModel]
    if (save.id.isEmpty) {
      Await.ready(BusinessPlanDataBaseOperations.insert(BusinessPlanViewModel.toBusinessPlan(save)), Duration.Inf)
    } else {
      BusinessPlanDataBaseOperations.update(BusinessPlanViewModel.toBusinessPlan(save))
    }
    Ok("200")
  }

  def deleteBusinessPlan(id: Long) = Action {
    BusinessPlanDataBaseOperations.delete(id)
    Ok("delete")
  }

}
