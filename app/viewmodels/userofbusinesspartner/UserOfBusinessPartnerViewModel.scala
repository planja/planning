package viewmodels.userofbusinesspartner

import models.userofbusinesspartner.UserOfBusinessPartner
import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
  * Created by ShchykalauM on 31.01.2017.
  */
case class UserOfBusinessPartnerViewModel(var id: Option[Long], firstName: String, lastName: String, isCertificated: Boolean, countryId: Option[Long], businessPartnerId: Option[Long]) {

  def this(userOfBusinessPartner: UserOfBusinessPartner) {
    this(userOfBusinessPartner.id, userOfBusinessPartner.firstName, userOfBusinessPartner.lastName, userOfBusinessPartner.isCertificated,
      userOfBusinessPartner.countryId, userOfBusinessPartner.businessPartnerId)
  }

}

object UserOfBusinessPartnerViewModel {
  implicit val UserOfBusinessPartnerViewModelFormat = Json.format[UserOfBusinessPartnerViewModel]

  def toUserOfBusinessPartner(userOfBusinessPartnerViewModel: UserOfBusinessPartnerViewModel): UserOfBusinessPartner = {
    UserOfBusinessPartner(userOfBusinessPartnerViewModel.id, userOfBusinessPartnerViewModel.firstName, userOfBusinessPartnerViewModel.lastName,
      userOfBusinessPartnerViewModel.isCertificated, userOfBusinessPartnerViewModel.countryId, userOfBusinessPartnerViewModel.businessPartnerId)
  }
}
