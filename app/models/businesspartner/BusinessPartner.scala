package models.businesspartner

import java.sql.Timestamp

import org.joda.time.DateTime
import common.{DataBaseOperations, IndexedTable, Unique}
import slick.lifted.Tag
import config.SlickDriver.driver.api._

import config.DateTimeConverter._

/**
  * Created by ShchykalauM on 27.01.2017.
  */
case class BusinessPartner(id: Option[Long],
                           shortName: String,
                           startDate: DateTime,
                           endDate: DateTime,
                           address: String,
                           email: String,
                           countryId: Long,
                           businessPartnerTypeId: Long) extends Unique

class BusinessPartnerTable(tag: Tag) extends IndexedTable[BusinessPartner](tag, "BUSINESS_PARTNER") {

  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

  def shortName = column[String]("SHORT_NAME")

  def startDate = column[DateTime]("START_DATE")

  def endDate = column[DateTime]("END_DATE")

  def address = column[String]("ADDRESS")

  def email = column[String]("EMAIL")

  def countryId = column[Long]("COUNTRY_ID")

  def businessPartnerTypeId = column[Long]("BUSINESS_PARTNER_TYPE_ID")

  def * = (id.?, shortName, startDate, endDate, address, email, countryId, businessPartnerTypeId) <> ((BusinessPartner.apply _).tupled, BusinessPartner.unapply)
}

object BusinessPartnerDataBaseOperations extends DataBaseOperations[BusinessPartnerTable, BusinessPartner] {

  override val query = TableQuery[BusinessPartnerTable]

}

