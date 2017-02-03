package viewmodels.businesspartner

import models.businesspartner.BusinessPartner
import models.userofbusinesspartner.UserOfBusinessPartner
import org.joda.time.DateTime
import play.api.libs.json.Json

import scala.collection.mutable.ListBuffer

/**
  * Created by ShchykalauM on 02.02.2017.
  */
case class BusinessPartnerDetailsViewModel(var id: Long,
                                           shortName: String,
                                           startDate: DateTime,
                                           endDate: DateTime,
                                           address: String,
                                           email: String,
                                           countryId: Long,
                                           businessPartnerTypeId: Long, usersIdOfBusinessPartners: ListBuffer[Long]) {

  def this(businessPartner: BusinessPartner, usersOfBusinessPartner: ListBuffer[Long]) {
    this(businessPartner.id.get, businessPartner.shortName, businessPartner.startDate, businessPartner.endDate, businessPartner.address, businessPartner.email, businessPartner.countryId,
      businessPartner.businessPartnerTypeId, usersOfBusinessPartner)
  }

}

object BusinessPartnerDetailsViewModel {

  implicit val businessPartnerDetailsViewModeFormat = Json.format[BusinessPartnerDetailsViewModel]

  def getUsersIdOfBusinessPartner(usersOfBusinessPartners: Seq[UserOfBusinessPartner], businessPartnerId: Long): ListBuffer[Long] = {
    var usersIdOfBusinessPartner = new ListBuffer[Long]
    for (userOfBusinessPartner <- usersOfBusinessPartners) {
      if (userOfBusinessPartner.businessPartnerId.isDefined && userOfBusinessPartner.businessPartnerId.get == businessPartnerId)
        usersIdOfBusinessPartner += userOfBusinessPartner.id.get
    }
    usersIdOfBusinessPartner
  }
}
