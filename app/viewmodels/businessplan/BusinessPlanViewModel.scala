package viewmodels.businessplan

import models.businessplan.BusinessPlan
import play.api.libs.json.Json

/**
  * Created by ShchykalauM on 07.02.2017.
  */
case class BusinessPlanViewModel(id: Option[Long], plan: Long, revenue: Long, businessPlanStatusId: Long, businessPartnerId: Long) {

  def this(businessPlan: BusinessPlan) {
    this(businessPlan.id, businessPlan.plan, businessPlan.revenue, businessPlan.businessPlanStatusId, businessPlan.businessPartnerId)
  }

}

object BusinessPlanViewModel{

  implicit val BusinessPlanViewModelFormat = Json.format[BusinessPlanViewModel]

  def toBusinessPlan(businessPlanViewModel: BusinessPlanViewModel): BusinessPlan = {
    BusinessPlan(businessPlanViewModel.id, businessPlanViewModel.plan, businessPlanViewModel.revenue, businessPlanViewModel.businessPlanStatusId, businessPlanViewModel.businessPartnerId)
  }
}
