package models.userofbusinesspartner

import common.{DataBaseOperations, IndexedTable, Unique}
import slick.lifted.Tag
import config.SlickDriver.driver.api._

/**
  * Created by ShchykalauM on 31.01.2017.
  */
case class UserOfBusinessPartner(id: Option[Long], firstName: String, lastName: String, isCertificated: Boolean, countryId: Option[Long], businessPartnerId: Option[Long]) extends Unique


class UserOfBusinessPartnerTable(tag: Tag) extends IndexedTable[UserOfBusinessPartner](tag, "USER_OF_BUSINESS_PARTNER") {

  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

  def firstName = column[String]("FIRSTNAME")

  def lastName = column[String]("LASTNAME")

  def isCertificated = column[Boolean]("CERTIFICATED")

  def countryId = column[Option[Long]]("COUNTRY_ID")

  def businessPartnerId = column[Option[Long]]("BUSINESS_PARTNER_ID")

  def country_fk = foreignKey("user_of_business_partner_country_fk", countryId, TableQuery[UserOfBusinessPartnerTable])(_.id, onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.Cascade)

  def * = (id.?, firstName, lastName, isCertificated, countryId, businessPartnerId) <> ((UserOfBusinessPartner.apply _).tupled, UserOfBusinessPartner.unapply)
}


object UserOfBusinessPartnerDataBaseOperations extends DataBaseOperations[UserOfBusinessPartnerTable, UserOfBusinessPartner] {

  def update(updatedUserOfBusinessPartner: UserOfBusinessPartner): UserOfBusinessPartner = {
    val action = query.filter(_.id === updatedUserOfBusinessPartner.id).map(userOfbusinessPartner => (userOfbusinessPartner.firstName, userOfbusinessPartner.lastName,
      userOfbusinessPartner.isCertificated, userOfbusinessPartner.countryId, userOfbusinessPartner.businessPartnerId))
      .update(updatedUserOfBusinessPartner.firstName, updatedUserOfBusinessPartner.lastName, updatedUserOfBusinessPartner.isCertificated,
        updatedUserOfBusinessPartner.countryId, updatedUserOfBusinessPartner.businessPartnerId)
    val future = dbConfig.db.run(action)


    updatedUserOfBusinessPartner
  }


  override val query = TableQuery[UserOfBusinessPartnerTable]

}
