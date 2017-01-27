package controllers.application

import models.businesspartner.BusinessPartnerDataBaseOperations
import models.country.CountryDataBaseOperations
import models.region.RegionDataBaseOperations
import play.api.libs.json.Json
import play.api.mvc._
import viewmodels.treeview.TreeViewViewModel

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object ApplicationController extends Controller {

  def index = Action {
    Ok(views.html.common.index())
  }

  def loadDataForTreeView = Action { request =>
    val regions = Await.ready(RegionDataBaseOperations.listAll, Duration.Inf).value.get.get
    val countries = Await.ready(CountryDataBaseOperations.listAll, Duration.Inf).value.get.get
    val businessPartners = Await.ready(BusinessPartnerDataBaseOperations.listAll, Duration.Inf).value.get.get
    val result = TreeViewViewModel.mapToTreeViewViewModels(regions, countries, businessPartners)
    val json = Json.toJson(result)
    Ok(json)
  }

}