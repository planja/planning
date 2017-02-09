package controllers.usersofbusinesspartner

import common.SingleInstanceForTest
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import play.api.libs.json.{JsArray, JsObject, Json}
import play.api.test.{FakeRequest, PlaySpecification}
import viewmodels.userofbusinesspartner.UserOfBusinessPartnerViewModel

/**
  * Created by ShchykalauM on 09.02.2017.
  */

@RunWith(classOf[JUnitRunner])
class UsersOfBusinessPartnerSpec extends PlaySpecification with SingleInstanceForTest {

  "Users of business partner" should {
    "send request to page" in {
      val page = route(FakeRequest(GET, routes.UsersOfBusinessPartnerController.usersOfBusinessPartner().url)).get
      status(page) must equalTo(OK)
      contentType(page) must beSome.which(_ == "text/html")
      contentAsString(page) must contain("Users Of Business Partner")
    }

    "get users of business partner" in {
      val json = route(FakeRequest(GET, routes.UsersOfBusinessPartnerController.getUsersOfBusinessPartner().url)).get
      status(json) must equalTo(OK)
      contentType(json) must beSome.which(_ == "application/json")
      contentAsJson(json).asInstanceOf[JsArray].value.length must beGreaterThan(0)
    }

    "get users of business partner info" in {
      val json = route(FakeRequest(GET, routes.UsersOfBusinessPartnerController.getUsersOfBusinessPartnerInfo().url)).get
      status(json) must equalTo(OK)
      contentType(json) must beSome.which(_ == "application/json")
      contentAsJson(json).asInstanceOf[JsArray].value.length must beGreaterThan(0)
    }

    "save user of business partner with delete and update" in {
      var read = route(FakeRequest(GET, routes.UsersOfBusinessPartnerController.getUsersOfBusinessPartner().url)).get
      status(read) must equalTo(OK)
      contentType(read) must beSome.which(_ == "application/json")
      contentAsJson(read).asInstanceOf[JsArray].value.length must beGreaterThan(0)
      var length = contentAsJson(read).asInstanceOf[JsArray].value.length

      val userOfBusinessPartnerViewModel = UserOfBusinessPartnerViewModel(None, "firstname123", "lastname123", isCertificated = true, None, None)
      val json = route(FakeRequest(POST, routes.UsersOfBusinessPartnerController.saveUserOfBusinessPartner().url)
        .withHeaders("Content-Type" -> "application/json")
        .withBody(Json.toJson(userOfBusinessPartnerViewModel).toString())).get
      status(json) must equalTo(OK)
      contentType(json) must beSome.which(_ == "application/json")

      read = route(FakeRequest(GET, routes.UsersOfBusinessPartnerController.getUsersOfBusinessPartner().url)).get
      val newLength = contentAsJson(read).asInstanceOf[JsArray].value.length
      newLength must beGreaterThan(length)

      var saveViewModel = contentAsJson(json).asInstanceOf[JsObject].as[UserOfBusinessPartnerViewModel]
      saveViewModel.firstName must equalTo("firstname123")
      saveViewModel.lastName = "updatedLastname123"

      val update = route(FakeRequest(PUT, routes.UsersOfBusinessPartnerController.updateUserOfBusinessPartner().url)
        .withHeaders("Content-Type" -> "application/json")
        .withBody(Json.toJson(saveViewModel).toString())).get
      status(update) must equalTo(OK)
      contentType(update) must beSome.which(_ == "application/json")

      val updateViewModel = contentAsJson(update).asInstanceOf[JsObject].as[UserOfBusinessPartnerViewModel]
      updateViewModel.lastName must equalTo(saveViewModel.lastName)
      updateViewModel.id must equalTo(saveViewModel.id)

      read = route(FakeRequest(GET, routes.UsersOfBusinessPartnerController.getUsersOfBusinessPartner().url)).get
      contentAsJson(read).asInstanceOf[JsArray].value.length must equalTo(newLength)

      val delete = route(FakeRequest(DELETE, routes.UsersOfBusinessPartnerController.deleteUserOfBusinessPartner(saveViewModel.id.get).url)).get
      contentType(delete) must beSome.which(_ == "text/plain")
      contentAsString(delete) must equalTo("delete")

      read = route(FakeRequest(GET, routes.UsersOfBusinessPartnerController.getUsersOfBusinessPartner().url)).get
      contentAsJson(read).asInstanceOf[JsArray].value.length must equalTo(length)
    }

  }

}
