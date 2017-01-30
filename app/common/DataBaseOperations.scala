package common

import config.SlickDriver.driver.api._
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.Future
import slick.driver.JdbcProfile
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by ShchykalauM on 19.01.2017.
  */
/**
  *
  * @tparam Q - item table class
  * @tparam E - item class
  */
abstract class Query[Q <: IndexedTable[E], E <: Unique] {
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

  val db = dbConfig.db

  val query: TableQuery[Q]

  val insertQuery = (v: E) => {
    (query += v).flatMap { x =>
      query.sortBy {
        _.id.desc.nullsFirst
      }.map(_.id).result.head
    }
  }

  val detailQuery = (f: Q => Rep[Boolean]) => query.filter(f).result.headOption

}

/**
  *
  * @tparam Q - item table class
  * @tparam E - item class
  */
trait DataBaseOperations[Q <: IndexedTable[E], E <: Unique] extends Query[Q, E] {

  /**
    * find record
    */
  def find(id: Long): Future[Option[E]] = db.run {
    detailQuery(_.id === id)
  }

  /**
    * delete record
    */
  def delete(id: Long): Future[Int] = {
    db.run(query.filter(_.id === id).delete)
  }


  /**
    * insert record
    */
  def insert(item: E): Future[Long] = db.run {
    insertQuery(item)
  }

  /**
    * list or records
    */
  def listAll: Future[Seq[E]] = {
    db.run(query.result)
  }
}

