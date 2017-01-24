package models.country

import common.{DataBaseOperations, IndexedTable, Unique}
import config.SlickDriver.driver.api._
import models.region.RegionTable
import slick.lifted.Tag

/**
  * Created by ShchykalauM on 23.01.2017.
  */
case class Country(id: Option[Long], shortName: String, fullName: String, regionId: Long) extends Unique

class CountryTable(tag: Tag) extends IndexedTable[Country](tag, "COUNTRIES") {

  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)

  def shortName = column[String]("SHORT_NAME")

  def fullName = column[String]("FULL_NAME")

  def regionId = column[Long]("REGION_ID")

  def region_fk = foreignKey("country_region_fk", regionId, TableQuery[RegionTable])(_.id, onUpdate = ForeignKeyAction.Cascade, onDelete = ForeignKeyAction.Cascade)

  def * = (id.?, shortName, fullName, regionId) <> ((Country.apply _).tupled, Country.unapply)
}

object CountryDataBaseOperations extends DataBaseOperations[CountryTable, Country] {

  def update(updatedCountry: Country): Country = {
    val action = query.filter(_.id === updatedCountry.id).map(country => (country.shortName, country.fullName, country.regionId))
      .update(updatedCountry.shortName, updatedCountry.fullName, updatedCountry.regionId)
    val future = dbConfig.db.run(action)
    updatedCountry
  }


  override val query = TableQuery[CountryTable]

}