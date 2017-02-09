package controllers.countries

import common.SingleInstanceForTest
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import play.api.libs.json.{JsArray, JsObject, Json}
import play.api.test.{FakeRequest, PlaySpecification}
import viewmodels.country.CountryViewModel

/**
  * Created by ShchykalauM on 09.02.2017.
  */

@RunWith(classOf[JUnitRunner])
class CountriesSpec extends PlaySpecification with SingleInstanceForTest {

  "Countries" should {
    "send request to page" in {
      val page = route(FakeRequest(GET, routes.CountriesController.countries().url)).get
      status(page) must equalTo(OK)
      contentType(page) must beSome.which(_ == "text/html")
      contentAsString(page) must contain("Countries")
    }

    "get countries" in {
      val json = route(FakeRequest(GET, routes.CountriesController.getCountries().url)).get
      status(json) must equalTo(OK)
      contentType(json) must beSome.which(_ == "application/json")
      contentAsJson(json).asInstanceOf[JsArray].value.length must beGreaterThan(0)
    }

    "get countries info" in {
      val json = route(FakeRequest(GET, routes.CountriesController.getCountriesInfo().url)).get
      status(json) must equalTo(OK)
      contentType(json) must beSome.which(_ == "application/json")
      contentAsJson(json).asInstanceOf[JsArray].value.length must beGreaterThan(0)
    }

    "save country with delete and update " in {
      var read = route(FakeRequest(GET, routes.CountriesController.getCountries().url)).get
      status(read) must equalTo(OK)
      contentType(read) must beSome.which(_ == "application/json")
      contentAsJson(read).asInstanceOf[JsArray].value.length must beGreaterThan(0)
      var length = contentAsJson(read).asInstanceOf[JsArray].value.length

      val countryViewModel = CountryViewModel(None, "DMK","Dominikana",8)
      val json = route(FakeRequest(POST, routes.CountriesController.saveCountry().url)
        .withHeaders("Content-Type" -> "application/json")
        .withBody(Json.toJson(countryViewModel).toString())).get
      status(json) must equalTo(OK)
      contentType(json) must beSome.which(_ == "application/json")

      read = route(FakeRequest(GET, routes.CountriesController.getCountries().url)).get
      val newLength = contentAsJson(read).asInstanceOf[JsArray].value.length
      newLength must beGreaterThan(length)

      var saveViewModel = contentAsJson(json).asInstanceOf[JsObject].as[CountryViewModel]
      saveViewModel.shortName must equalTo("DMK")
      saveViewModel.fullName = "DominikanaUpdatedName"

      val update = route(FakeRequest(PUT, routes.CountriesController.updateCountry().url)
        .withHeaders("Content-Type" -> "application/json")
        .withBody(Json.toJson(saveViewModel).toString())).get
      status(update) must equalTo(OK)
      contentType(update) must beSome.which(_ == "application/json")

      val updateViewModel = contentAsJson(update).asInstanceOf[JsObject].as[CountryViewModel]
      updateViewModel.fullName must equalTo(saveViewModel.fullName)
      updateViewModel.id must equalTo(saveViewModel.id)

      read = route(FakeRequest(GET, routes.CountriesController.getCountries().url)).get
      contentAsJson(read).asInstanceOf[JsArray].value.length must equalTo(newLength)

      val delete = route(FakeRequest(DELETE, routes.CountriesController.deleteCountry(saveViewModel.id.get).url)).get
      contentType(delete) must beSome.which(_ == "text/plain")
      contentAsString(delete) must equalTo("delete")

      read = route(FakeRequest(GET, routes.CountriesController.getCountries().url)).get
      contentAsJson(read).asInstanceOf[JsArray].value.length must equalTo(length)
    }

  }

}
