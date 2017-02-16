package models.businessplan

import common.Unique
import common.{DataBaseOperations, IndexedTable, Unique}
import slick.lifted.Tag
import config.SlickDriver.driver.api._
import models.businesspartner.BusinessPartnerTable
import models.businessplanstatus.BusinessPlanStatusTable


/**
  * Created by ShchykalauM on 07.02.2017.
  */
case class BusinessPlan(id: Option[Long], plan: Long, revenue: Long, businessPlanStatusId: Long, businessPartnerId: Long) extends Unique

class BusinessPlanTable(tag: Tag) extends IndexedTable[BusinessPlan](tag, "business_plan") {

  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

  def plan = column[Long]("PLAN_VALUE")

  def revenue = column[Long]("REVENUE_VALUE")

  def businessPlanStatusId = column[Long]("BUSINESS_PLAN_STATUS_ID")

  def businessPartnerId = column[Long]("BUSINESS_PARTNER_ID")

  def businessPlanStatusId_fk = foreignKey("business_plan_business_plan_status_fk", businessPlanStatusId, TableQuery[BusinessPlanStatusTable])(_.id, onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.SetNull)

  def businessPartnerId_fk = foreignKey("business_plan_business_partner_fk", businessPartnerId, TableQuery[BusinessPartnerTable])(_.id, onUpdate = ForeignKeyAction.NoAction, onDelete = ForeignKeyAction.Cascade)

  def * = (id.?, plan, revenue, businessPlanStatusId, businessPartnerId) <> ((BusinessPlan.apply _).tupled, BusinessPlan.unapply)
}

object BusinessPlanDataBaseOperations extends DataBaseOperations[BusinessPlanTable, BusinessPlan] {

  def update(updatedBusinessPlan: BusinessPlan): BusinessPlan = {
    val action = query.filter(_.id === updatedBusinessPlan.id).map(businessPlan => (businessPlan.plan, businessPlan.revenue, businessPlan.businessPlanStatusId,
      businessPlan.businessPartnerId))
      .update(updatedBusinessPlan.plan, updatedBusinessPlan.revenue, updatedBusinessPlan.businessPlanStatusId, updatedBusinessPlan.businessPartnerId)
    val future = dbConfig.db.run(action)
    updatedBusinessPlan
  }


  override val query = TableQuery[BusinessPlanTable]

}