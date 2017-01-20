package models.region

import slick.lifted.Tag
import config.SlickDriver.driver.api._
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import common.{DataBaseOperations, IndexedTable, Unique}
import play.api.libs.json.Json

import scala.concurrent.Future

/**
  * Created by ShchykalauM on 19.01.2017.
  */
case class Region(id: Option[Long], shortName: String, fullName: String) extends Unique

class RegionTable(tag: Tag) extends IndexedTable[Region](tag, "REGIONS") {

  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

  def shortName = column[String]("SHORT_NAME")

  def fullName = column[String]("FULL_NAME")

  def * = (id.?, shortName, fullName) <> ((Region.apply _).tupled, Region.unapply)
}

object RegionDataBaseOperations extends DataBaseOperations[RegionTable, Region] {

  def update(updatedRegion: Region): Region = {
    val action = query.filter(_.id === updatedRegion.id).map(region => (region.shortName, region.fullName))
      .update(updatedRegion.shortName, updatedRegion.fullName)
    val future = dbConfig.db.run(action)
    updatedRegion
  }

  override val query = TableQuery[RegionTable]

}