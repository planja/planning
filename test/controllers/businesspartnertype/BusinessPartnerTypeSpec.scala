package controllers.businesspartnertype

import common.SingleInstanceForTest
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.libs.json.{JsArray, JsObject, JsString, Json}
import play.api.test.Helpers.{contentType, _}
import play.api.test.{FakeApplication, FakeRequest, PlaySpecification, WithApplication}
import viewmodels.businesspartnertype.BusinessPartnerTypeViewModel

/**
  * Created by ShchykalauM on 08.02.2017.
  */

@RunWith(classOf[JUnitRunner])
class BusinessPartnerTypeSpec extends PlaySpecification with SingleInstanceForTest {

  "BusinessPartnerType" should {
    "send request to page" in {
      val page = route(FakeRequest(GET, routes.BusinessPartnerTypeController.businessPartnerType().url)).get
      status(page) must equalTo(OK)
      contentType(page) must beSome.which(_ == "text/html")
      contentAsString(page) must contain("Business Partner Type")
    }

    "get business partners type" in {
      val json = route(FakeRequest(GET, routes.BusinessPartnerTypeController.getBusinessPartnerType().url)).get
      status(json) must equalTo(OK)
      contentType(json) must beSome.which(_ == "application/json")
      contentAsJson(json).asInstanceOf[JsArray].value.length must beGreaterThan(0)
    }

    "get business partners type info" in {
      val json = route(FakeRequest(GET, routes.BusinessPartnerTypeController.getBusinessPartnerTypeInfo().url)).get
      status(json) must equalTo(OK)
      contentType(json) must beSome.which(_ == "application/json")
      contentAsJson(json).asInstanceOf[JsArray].value.length must beGreaterThan(0)
    }

    "save business partner type with delete and update" in {
      var read = route(FakeRequest(GET, routes.BusinessPartnerTypeController.getBusinessPartnerType().url)).get
      status(read) must equalTo(OK)
      contentType(read) must beSome.which(_ == "application/json")
      contentAsJson(read).asInstanceOf[JsArray].value.length must beGreaterThan(0)
      var length = contentAsJson(read).asInstanceOf[JsArray].value.length

      val businessPartnerTypeViewModel = BusinessPartnerTypeViewModel(None, name = "newname")
      val json = route(FakeRequest(POST, routes.BusinessPartnerTypeController.saveBusinessPartnerType().url)
        .withHeaders("Content-Type" -> "application/json")
        .withBody(Json.toJson(businessPartnerTypeViewModel).toString())).get
      status(json) must equalTo(OK)
      contentType(json) must beSome.which(_ == "application/json")

      read = route(FakeRequest(GET, routes.BusinessPartnerTypeController.getBusinessPartnerType().url)).get
      val newLength = contentAsJson(read).asInstanceOf[JsArray].value.length
      newLength must beGreaterThan(length)

      var saveViewModel = contentAsJson(json).asInstanceOf[JsObject].as[BusinessPartnerTypeViewModel]
      saveViewModel.name must equalTo("newname")
      saveViewModel.name = "updatedName"

      val update = route(FakeRequest(PUT, routes.BusinessPartnerTypeController.updateBusinessPartnerType().url)
        .withHeaders("Content-Type" -> "application/json")
        .withBody(Json.toJson(saveViewModel).toString())).get
      status(update) must equalTo(OK)
      contentType(update) must beSome.which(_ == "application/json")

      val updateViewModel = contentAsJson(update).asInstanceOf[JsObject].as[BusinessPartnerTypeViewModel]
      updateViewModel.name must equalTo(saveViewModel.name)
      updateViewModel.id must equalTo(saveViewModel.id)

      read = route(FakeRequest(GET, routes.BusinessPartnerTypeController.getBusinessPartnerType().url)).get
      contentAsJson(read).asInstanceOf[JsArray].value.length must equalTo(newLength)

      val delete = route(FakeRequest(DELETE, routes.BusinessPartnerTypeController.deleteBusinessPartnerType(saveViewModel.id.get).url)).get
      contentType(delete) must beSome.which(_ == "text/plain")
      contentAsString(delete) must equalTo("delete")

      read = route(FakeRequest(GET, routes.BusinessPartnerTypeController.getBusinessPartnerType().url)).get
      contentAsJson(read).asInstanceOf[JsArray].value.length must equalTo(length)
    }
  }
}
