package controllers.businesspartners

import models.businesspartner.BusinessPartnerDataBaseOperations
import play.api.mvc.{Action, Controller}
import viewmodels.businesspartner.BusinessPartnerViewModel

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

  def editBusinessPartner(id: Long) = Action {
    val item = Await.ready(BusinessPartnerDataBaseOperations.find(id), Duration.Inf)
    if (item.value.get.get.isDefined)
      Ok(views.html.businesspartners.businesspartnerdetails())
    else Ok(views.html.common.notfound())
  }


}
