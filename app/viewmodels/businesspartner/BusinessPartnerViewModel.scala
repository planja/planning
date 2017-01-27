package viewmodels.businesspartner

import models.businesspartner.BusinessPartner
import org.joda.time.DateTime
import play.api.libs.json.Json

/**
  * Created by ShchykalauM on 27.01.2017.
  */
case class BusinessPartnerViewModel(var id: Option[Long],
                                    shortName: String,
                                    startDate: DateTime,
                                    endDate: DateTime,
                                    address: String,
                                    email: String,
                                    countryId: Long,
                                    businessPartnerTypeId: Long) {

  def this(businessPartner: BusinessPartner) {
    this(businessPartner.id, businessPartner.shortName, businessPartner.startDate,
      businessPartner.endDate, businessPartner.address, businessPartner.email, businessPartner.countryId, businessPartner.businessPartnerTypeId)
  }

}

object BusinessPartnerViewModel {

  implicit val BusinessPartnerViewModelFormat = Json.format[BusinessPartnerViewModel]

  def toBusinessPartner(businessPartnerViewModel: BusinessPartnerViewModel): BusinessPartner = {
    BusinessPartner(businessPartnerViewModel.id, businessPartnerViewModel.shortName, businessPartnerViewModel.startDate, businessPartnerViewModel.endDate,
      businessPartnerViewModel.address, businessPartnerViewModel.email, businessPartnerViewModel.countryId, businessPartnerViewModel.businessPartnerTypeId)
  }
}
