package controllers.businessplanstatus

import common.SingleInstanceForTest
import controllers.businesspartnertype.routes
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import play.api.libs.json.{JsArray, JsObject, Json}
import play.api.test.{FakeRequest, PlaySpecification}
import viewmodels.businessplanstatus.BusinessPlanStatusViewModel

/**
  * Created by ShchykalauM on 09.02.2017.
  */

@RunWith(classOf[JUnitRunner])
class BusinessPlanStatusSpec extends PlaySpecification with SingleInstanceForTest {

  "BusinessPlanStatus" should{
    "send request to page" in {
      val page = route(FakeRequest(GET, routes.BusinessPlanStatusController.businessPlanStatus().url)).get
      status(page) must equalTo(OK)
      contentType(page) must beSome.which(_ == "text/html")
      contentAsString(page) must contain("Business Plan Status")
    }

    "get business plan statuses" in {
      val json = route(FakeRequest(GET, routes.BusinessPlanStatusController.getBusinessPlanStatus().url)).get
      status(json) must equalTo(OK)
      contentType(json) must beSome.which(_ == "application/json")
      contentAsJson(json).asInstanceOf[JsArray].value.length must beGreaterThan(0)
    }

    "get business plan statuses info" in {
      val json = route(FakeRequest(GET, routes.BusinessPlanStatusController.getBusinessPlanStatusInfo().url)).get
      status(json) must equalTo(OK)
      contentType(json) must beSome.which(_ == "application/json")
      contentAsJson(json).asInstanceOf[JsArray].value.length must beGreaterThan(0)
    }

    "save business plan status with delete and update" in {
      var read = route(FakeRequest(GET, routes.BusinessPlanStatusController.getBusinessPlanStatus().url)).get
      status(read) must equalTo(OK)
      contentType(read) must beSome.which(_ == "application/json")
      contentAsJson(read).asInstanceOf[JsArray].value.length must beGreaterThan(0)
      var length = contentAsJson(read).asInstanceOf[JsArray].value.length

      val businessPlanStatusViewModel = BusinessPlanStatusViewModel(None, name = "newname")
      val json = route(FakeRequest(POST, routes.BusinessPlanStatusController.saveBusinessPlanStatus().url)
        .withHeaders("Content-Type" -> "application/json")
        .withBody(Json.toJson(businessPlanStatusViewModel).toString())).get
      status(json) must equalTo(OK)
      contentType(json) must beSome.which(_ == "application/json")

      read = route(FakeRequest(GET, routes.BusinessPlanStatusController.getBusinessPlanStatus().url)).get
      val newLength = contentAsJson(read).asInstanceOf[JsArray].value.length
      newLength must beGreaterThan(length)

      var saveViewModel = contentAsJson(json).asInstanceOf[JsObject].as[BusinessPlanStatusViewModel]
      saveViewModel.name must equalTo("newname")
      saveViewModel.name = "updatedName"

      val update = route(FakeRequest(PUT, routes.BusinessPlanStatusController.updateBusinessPlanStatus().url)
        .withHeaders("Content-Type" -> "application/json")
        .withBody(Json.toJson(saveViewModel).toString())).get
      status(update) must equalTo(OK)
      contentType(update) must beSome.which(_ == "application/json")

      val updateViewModel = contentAsJson(update).asInstanceOf[JsObject].as[BusinessPlanStatusViewModel]
      updateViewModel.name must equalTo(saveViewModel.name)
      updateViewModel.id must equalTo(saveViewModel.id)

      read = route(FakeRequest(GET, routes.BusinessPlanStatusController.getBusinessPlanStatus().url)).get
      contentAsJson(read).asInstanceOf[JsArray].value.length must equalTo(newLength)

      val delete = route(FakeRequest(DELETE, routes.BusinessPlanStatusController.deleteBusinessPlanStatus(saveViewModel.id.get).url)).get
      contentType(delete) must beSome.which(_ == "text/plain")
      contentAsString(delete) must equalTo("delete")

      read = route(FakeRequest(GET, routes.BusinessPlanStatusController.getBusinessPlanStatus().url)).get
      contentAsJson(read).asInstanceOf[JsArray].value.length must equalTo(length)

    }

  }


}
