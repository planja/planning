package models.businessplanstatus

import common.{DataBaseOperations, IndexedTable, Unique}
import slick.lifted.Tag
import config.SlickDriver.driver.api._

/**
  * Created by ShchykalauM on 24.01.2017.
  */
case class BusinessPlanStatus(id: Option[Long], name: String) extends Unique

class BusinessPlanStatusTable(tag: Tag) extends IndexedTable[BusinessPlanStatus](tag, "BUSINESS_PLAN_STATUS") {

  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

  def name = column[String]("NAME")

  def * = (id.?, name) <> ((BusinessPlanStatus.apply _).tupled, BusinessPlanStatus.unapply)
}

object BusinessPlanStatusDataBaseOperations extends DataBaseOperations[BusinessPlanStatusTable, BusinessPlanStatus] {

  def update(updatedBusinessPlanStatus: BusinessPlanStatus): BusinessPlanStatus = {
    val action = query.filter(_.id === updatedBusinessPlanStatus.id).map(businessPlanStatus => businessPlanStatus.name)
      .update(updatedBusinessPlanStatus.name)
    val future = dbConfig.db.run(action)
    updatedBusinessPlanStatus
  }


  override val query = TableQuery[BusinessPlanStatusTable]

}