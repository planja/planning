package common

import slick.lifted.{Rep, Tag}
import config.SlickDriver.driver.api._

/**
  * Created by ShchykalauM on 20.01.2017.
  */
abstract class IndexedTable[T](tag: Tag, name: String) extends Table[T](tag, name) {
  def id: Rep[Long]
}
