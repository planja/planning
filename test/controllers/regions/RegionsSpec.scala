package controllers.regions

import common.SingleInstanceForTest
import controllers.businessplanstatus.routes
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import play.api.libs.json.{JsArray, JsObject, Json}
import play.api.test.{FakeRequest, PlaySpecification}
import viewmodels.region.RegionViewModel

/**
  * Created by ShchykalauM on 09.02.2017.
  */
@RunWith(classOf[JUnitRunner])
class RegionsSpec extends PlaySpecification with SingleInstanceForTest {

  "Regions" should {
    "send request to page" in {
      val page = route(FakeRequest(GET, routes.RegionsController.regions().url)).get
      status(page) must equalTo(OK)
      contentType(page) must beSome.which(_ == "text/html")
      contentAsString(page) must contain("Regions")
    }

    "get regions" in {
      val json = route(FakeRequest(GET, routes.RegionsController.getRegions().url)).get
      status(json) must equalTo(OK)
      contentType(json) must beSome.which(_ == "application/json")
      contentAsJson(json).asInstanceOf[JsArray].value.length must beGreaterThan(0)
    }

    "get regions info" in {
      val json = route(FakeRequest(GET, routes.RegionsController.getRegionsInfo().url)).get
      status(json) must equalTo(OK)
      contentType(json) must beSome.which(_ == "application/json")
      contentAsJson(json).asInstanceOf[JsArray].value.length must beGreaterThan(0)
    }

    "save region with delete and update" in {
      var read = route(FakeRequest(GET, routes.RegionsController.getRegions().url)).get
      status(read) must equalTo(OK)
      contentType(read) must beSome.which(_ == "application/json")
      contentAsJson(read).asInstanceOf[JsArray].value.length must beGreaterThan(0)
      var length = contentAsJson(read).asInstanceOf[JsArray].value.length

      val regionViewModel = RegionViewModel(None, "AN", "ANFullName")
      val json = route(FakeRequest(POST, routes.RegionsController.saveRegion().url)
        .withHeaders("Content-Type" -> "application/json")
        .withBody(Json.toJson(regionViewModel).toString())).get
      status(json) must equalTo(OK)
      contentType(json) must beSome.which(_ == "application/json")

      read = route(FakeRequest(GET, routes.RegionsController.getRegions().url)).get
      val newLength = contentAsJson(read).asInstanceOf[JsArray].value.length
      newLength must beGreaterThan(length)


      var saveViewModel = contentAsJson(json).asInstanceOf[JsObject].as[RegionViewModel]
      saveViewModel.shortName must equalTo("AN")
      saveViewModel.fullName = "ANUpdatedFullName"

      val update = route(FakeRequest(PUT, routes.RegionsController.updateRegion().url)
        .withHeaders("Content-Type" -> "application/json")
        .withBody(Json.toJson(saveViewModel).toString())).get
      status(update) must equalTo(OK)
      contentType(update) must beSome.which(_ == "application/json")

      val updateViewModel = contentAsJson(update).asInstanceOf[JsObject].as[RegionViewModel]
      updateViewModel.fullName must equalTo(saveViewModel.fullName)
      updateViewModel.id must equalTo(saveViewModel.id)

      read = route(FakeRequest(GET, routes.RegionsController.getRegions().url)).get
      contentAsJson(read).asInstanceOf[JsArray].value.length must equalTo(newLength)

      val delete = route(FakeRequest(DELETE, routes.RegionsController.deleteRegion(saveViewModel.id.get).url)).get
      contentType(delete) must beSome.which(_ == "text/plain")
      contentAsString(delete) must equalTo("delete")

      read = route(FakeRequest(GET, routes.RegionsController.getRegions().url)).get
      contentAsJson(read).asInstanceOf[JsArray].value.length must equalTo(length)
    }


  }

}
