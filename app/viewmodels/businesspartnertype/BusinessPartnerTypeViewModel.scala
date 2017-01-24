package viewmodels.businesspartnertype

import models.businesspartnertype.BusinessPartnerType
import play.api.libs.json.Json

/**
  * Created by ShchykalauM on 24.01.2017.
  */
case class BusinessPartnerTypeViewModel(var id: Option[Long], name: String) {

  def this(businessPartnerType: BusinessPartnerType) {
    this(businessPartnerType.id, businessPartnerType.name)
  }

}

object BusinessPartnerTypeViewModel {

  implicit val BusinessPartnerTypeViewModelFormat = Json.format[BusinessPartnerTypeViewModel]

  def toBusinessPartnerType(businessPartnerTypeViewModel: BusinessPartnerTypeViewModel): BusinessPartnerType = {
    BusinessPartnerType(businessPartnerTypeViewModel.id, businessPartnerTypeViewModel.name)
  }
}