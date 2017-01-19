package models.region

import slick.lifted.Tag
import config.SlickDriver.driver.api._
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import common.DataBaseOperations

import scala.concurrent.Future

/**
  * Created by ShchykalauM on 19.01.2017.
  */
case class Region(id: Option[Long], shortName: String, fullName: String)

class RegionTable(tag: Tag) extends Table[Region](tag, "REGIONS") {

  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

  def shortName = column[String]("SHORT_NAME")

  def fullName = column[String]("FULL_NAME")

  def * = (id.?, shortName, fullName) <> ((Region.apply _).tupled, Region.unapply)
}

object RegionDataBaseOperations extends DataBaseOperations[RegionTable, Region] {

  override val query = TableQuery[RegionTable]

}