package models.userofbusinesspartner

import common.{DataBaseOperations, IndexedTable, Unique}
import slick.lifted.Tag
import config.SlickDriver.driver.api._
import models.businesspartner.BusinessPartnerTable

import scala.concurrent.Future

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

  def country_fk = foreignKey("user_of_business_partner_country_fk", countryId, TableQuery[UserOfBusinessPartnerTable])(_.id, onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.NoAction)

  def businessPartner_fk = foreignKey("user_of_business_partner_business_partner_fk", businessPartnerId, TableQuery[BusinessPartnerTable])(_.id, onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.NoAction)


  def * = (id.?, firstName, lastName, isCertificated, countryId, businessPartnerId) <> ((UserOfBusinessPartner.apply _).tupled, UserOfBusinessPartner.unapply)
}


object UserOfBusinessPartnerDataBaseOperations extends DataBaseOperations[UserOfBusinessPartnerTable, UserOfBusinessPartner] {
  override val query = TableQuery[UserOfBusinessPartnerTable]

  def update(updatedUserOfBusinessPartner: UserOfBusinessPartner): UserOfBusinessPartner = {
    val action = query.filter(_.id === updatedUserOfBusinessPartner.id).map(userOfbusinessPartner => (userOfbusinessPartner.firstName, userOfbusinessPartner.lastName,
      userOfbusinessPartner.isCertificated, userOfbusinessPartner.countryId, userOfbusinessPartner.businessPartnerId))
      .update(updatedUserOfBusinessPartner.firstName, updatedUserOfBusinessPartner.lastName, updatedUserOfBusinessPartner.isCertificated,
        updatedUserOfBusinessPartner.countryId, updatedUserOfBusinessPartner.businessPartnerId)
    val future = dbConfig.db.run(action)


    updatedUserOfBusinessPartner
  }

  def updateBusinessPartner(filteredUsers: Seq[UserOfBusinessPartner], updatedIds: Seq[Long], users: Seq[UserOfBusinessPartner], businessPartnerId: Long): Unit = {
    for (user <- filteredUsers) {
      if (!updatedIds.contains(user.id.get)) {
        val updatedUser = user.copy(businessPartnerId = null)
        update(updatedUser)
      }
    }

    for (user <- users) {
      if (updatedIds.contains(user.id.get)) {
        val updatedUser = user.copy(businessPartnerId = Option(businessPartnerId))
        update(updatedUser)
      }
    }

  }


}
