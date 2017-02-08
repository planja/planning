package controllers.application

import common.SingleInstanceForTest
import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner._
import play.api.libs.json.JsArray
import play.api.test.Helpers._
import play.api.test._

/**
  * Add your spec here.
  * You can mock out a whole application including requests, plugins etc.
  * For more information, consult the wiki.
  */
@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends PlaySpecification with SingleInstanceForTest {

  "Application" should {

    "send 404 on a bad request" in {
      val badRequest = route(FakeRequest(GET, "/badrequest")).get
      status(badRequest) must equalTo(NOT_FOUND)
    }

    "render the index page" in {
      val home = route(FakeRequest(GET, "/")).get
      status(home) must equalTo(OK)
      contentType(home) must beSome.which(_ == "text/html")
      contentAsString(home) must contain("Home")
    }

    "get treeview data" in {
      val loadDataForTreeView = route(FakeRequest(GET, routes.ApplicationController.loadDataForTreeView().url)).get
      status(loadDataForTreeView) must equalTo(OK)
      contentType(loadDataForTreeView) must beSome.which(_ == "application/json")
      contentAsJson(loadDataForTreeView).asInstanceOf[JsArray].value.length must beGreaterThan(0)
    }
  }
}
