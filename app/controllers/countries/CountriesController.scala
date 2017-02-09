package controllers.countries

import models.country.CountryDataBaseOperations
import play.api.libs.json.Json
import play.api.mvc._
import viewmodels.common.CommonInfoViewModel
import viewmodels.country.CountryViewModel

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by ShchykalauM on 23.01.2017.
  */
object CountriesController extends Controller {

  def countries = Action {
    Ok(views.html.countries.countries())
  }

  def getCountries = Action {
    val list = Await.ready(CountryDataBaseOperations.listAll, Duration.Inf).value.get.get
    val result = list.map(o => new CountryViewModel(o))
    Ok(Json.toJson(result))
  }

  def saveCountry = Action { request =>
    val json = request.body.asJson.get
    val save = json.as[CountryViewModel]
    val id = Await.ready(CountryDataBaseOperations.insert(CountryViewModel.toCountry(save)), Duration.Inf).value.get.get
    save.id = Option(id)
    Ok(Json.toJson(save))
  }

  def deleteCountry(id: Long) = Action {
    Await.ready(CountryDataBaseOperations.delete(id), Duration.Inf)
    Ok("delete")
  }

  def updateCountry() = Action { request =>
    val json = request.body.asJson.get
    val update = json.as[CountryViewModel]
    CountryDataBaseOperations.update(CountryViewModel.toCountry(update))
    Ok(Json.toJson(update))
  }

  def getCountriesInfo = Action {
    val list = Await.ready(CountryDataBaseOperations.listAll, Duration.Inf).value.get.get
    val result = list.map(o => CommonInfoViewModel(o.id.get, o.fullName))
    Ok(Json.toJson(result))
  }

}
