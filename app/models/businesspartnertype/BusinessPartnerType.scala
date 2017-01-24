package models.businesspartnertype

import common.{DataBaseOperations, IndexedTable, Unique}
import slick.lifted.Tag
import config.SlickDriver.driver.api._


/**
  * Created by ShchykalauM on 24.01.2017.
  */
case class BusinessPartnerType(id: Option[Long], name: String) extends Unique

class BusinessPartnerTypeTable(tag: Tag) extends IndexedTable[BusinessPartnerType](tag, "BUSINESS_PARTNER_TYPE") {

  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

  def name = column[String]("NAME")

  def * = (id.?, name) <> ((BusinessPartnerType.apply _).tupled, BusinessPartnerType.unapply)
}

object BusinessPartnerTypeDataBaseOperations extends DataBaseOperations[BusinessPartnerTypeTable, BusinessPartnerType] {

  def update(updatedBusinessPartnerType: BusinessPartnerType): BusinessPartnerType = {
    val action = query.filter(_.id === updatedBusinessPartnerType.id).map(businessPartnerStatus => businessPartnerStatus.name)
      .update(updatedBusinessPartnerType.name)
    val future = dbConfig.db.run(action)
    updatedBusinessPartnerType
  }


  override val query = TableQuery[BusinessPartnerTypeTable]

}
