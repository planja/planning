package viewmodels

import models.businessplanstatus.BusinessPlanStatus
import play.api.libs.json.Json

/**
  * Created by ShchykalauM on 24.01.2017.
  */
case class BusinessPlanStatusViewModel(var id: Option[Long], name: String) {

  def this(businessPlanStatus: BusinessPlanStatus) {
    this(businessPlanStatus.id, businessPlanStatus.name)
  }

}

object BusinessPlanStatusViewModel {

  implicit val BusinessPlanStatusViewModelFormat = Json.format[BusinessPlanStatusViewModel]

  def toBusinessPlan(businessPlanStatusViewModel: BusinessPlanStatusViewModel): BusinessPlanStatus = {
    BusinessPlanStatus(businessPlanStatusViewModel.id, businessPlanStatusViewModel.name)
  }
}
