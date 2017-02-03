package controllers.businesspartners

import models.businesspartner.BusinessPartnerDataBaseOperations
import models.userofbusinesspartner.UserOfBusinessPartnerDataBaseOperations
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import viewmodels.businesspartner.{BusinessPartnerDetailsViewModel, BusinessPartnerViewModel}
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
    Ok(views.html.common.index())
  }

  def getBusinessPartnersInfo = Action {
    val list = Await.ready(BusinessPartnerDataBaseOperations.listAll, Duration.Inf).value.get.get
    val result = list.map(o => CommonInfoViewModel(o.id.get, o.shortName))
    Ok(Json.toJson(result))
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

}
