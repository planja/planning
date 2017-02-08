package controllers.businesspartners

import common.SingleInstanceForTest
import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner._
import play.api.libs.json.{JsArray, JsNumber, JsObject}
import play.api.test.Helpers._
import play.api.test._


/**
  * Created by ShchykalauM on 08.02.2017.
  */

@RunWith(classOf[JUnitRunner])
class BusinessPartnersSpec extends Specification with SingleInstanceForTest {

  "BusinessPartners" should {

    "send request to page" in {
      val page = route(FakeRequest(GET, routes.BusinessPartnersController.businessPartners().url)).get
      status(page) must equalTo(OK)
      contentType(page) must beSome.which(_ == "text/html")
      contentAsString(page) must contain("Add Business Partner")
    }

    "get business partners info" in {
      val json = route(FakeRequest(GET, routes.BusinessPartnersController.getBusinessPartnersInfo().url)).get
      status(json) must equalTo(OK)
      contentType(json) must beSome.which(_ == "application/json")
      contentAsJson(json).asInstanceOf[JsArray].value.length must beGreaterThan(0)
    }

    "load data for business partner" in {
      val json = route(FakeRequest(GET, routes.BusinessPartnersController.loadDataForBusinessPartner(4).url)).get
      status(json) must equalTo(OK)
      contentType(json) must beSome.which(_ == "application/json")
      contentAsJson(json).asInstanceOf[JsObject].value("id") must equalTo(JsNumber(4))
    }

    "load business plan" in {
      val json = route(FakeRequest(GET, routes.BusinessPartnersController.loadBusinessPlan(4).url)).get
      status(json) must equalTo(OK)
      contentType(json) must beSome.which(_ == "application/json")
      contentAsJson(json).asInstanceOf[JsObject].value("id") must equalTo(JsNumber(3))
    }
  }

}
