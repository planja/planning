package controllers.businesspartners

import play.api.mvc.{Action, Controller}

/**
  * Created by ShchykalauM on 25.01.2017.
  */
object BusinessPartnersController  extends Controller{

  def businessPartners = Action{
    Ok(views.html.businesspartners.businesspartners())
  }

}
