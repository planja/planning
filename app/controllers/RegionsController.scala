package controllers

import play.api.mvc._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import models.region.RegionDataBaseOperations

/**
  * Created by ShchykalauM on 19.01.2017.
  */
object RegionsController extends Controller {

  def regions = Action {


    val list = Await.ready(RegionDataBaseOperations.listAll, Duration.Inf)

    Ok(views.html.regions.regions())
  }

}
