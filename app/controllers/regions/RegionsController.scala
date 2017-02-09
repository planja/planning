package controllers.regions

import models.region.RegionDataBaseOperations
import play.api.libs.json.Json
import play.api.mvc._
import viewmodels.common.CommonInfoViewModel
import viewmodels.region.RegionViewModel

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by ShchykalauM on 19.01.2017.
  */
object RegionsController extends Controller {

  def regions = Action {
    Ok(views.html.regions.regions())
  }

  def getRegions = Action {
    val list = Await.ready(RegionDataBaseOperations.listAll, Duration.Inf).value.get.get
    val result = list.map(o => new RegionViewModel(o))
    Ok(Json.toJson(result))
  }

  def saveRegion() = Action { request =>
    val json = request.body.asJson.get
    val save = json.as[RegionViewModel]
    val id = Await.ready(RegionDataBaseOperations.insert(RegionViewModel.toRegion(save)), Duration.Inf).value.get.get
    save.id = Option(id)
    Ok(Json.toJson(save))
  }

  def deleteRegion(id: Long) = Action {
    Await.ready(RegionDataBaseOperations.delete(id), Duration.Inf)
    Ok("delete")
  }

  def updateRegion() = Action { request =>
    val json = request.body.asJson.get
    val update = json.as[RegionViewModel]
    RegionDataBaseOperations.update(RegionViewModel.toRegion(update))
    Ok(Json.toJson(update))
  }

  def getRegionsInfo = Action {
    val list = Await.ready(RegionDataBaseOperations.listAll, Duration.Inf).value.get.get
    val result = list.map(o => CommonInfoViewModel(o.id.get, o.fullName))
    Ok(Json.toJson(result))
  }

}
