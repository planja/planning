package common

import config.SlickDriver.driver.api._
import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.Future
import slick.driver.JdbcProfile

/**
  * Created by ShchykalauM on 19.01.2017.
  */

abstract class Query[Q <: Table[E], E] {
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)

  val db = dbConfig.db

  val query: TableQuery[Q]

}

trait DataBaseOperations[Q <: Table[E], E] extends Query[Q, E] {
  /**
    * list or records
    */
  def listAll: Future[Seq[E]] = {
    db.run(query.result)
  }
}

