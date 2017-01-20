package controllers

import play.api.mvc._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import models.region.RegionDataBaseOperations
import play.api.libs.json.Json
import viewmodels.region.RegionViewModel

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
    RegionDataBaseOperations.insert(RegionViewModel.toRegion(save))
    Ok("save")
  }

  def deleteRegion(id: Long) = Action {
    RegionDataBaseOperations.delete(id)
    Ok("delete")
  }

  def updateRegion() = Action { request =>
    val json = request.body.asJson.get
    val update = json.as[RegionViewModel]
    RegionDataBaseOperations.update(RegionViewModel.toRegion(update))
    Ok("update")
  }

}
