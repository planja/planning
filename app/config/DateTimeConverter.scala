package config

import java.sql.Timestamp

import org.joda.time.DateTime

import config.SlickDriver.driver.api._

/**
  * Created by ShchykalauM on 27.01.2017.
  */
object DateTimeConverter {

  implicit val JodaDateTimeMapper = MappedColumnType.base[DateTime, Timestamp](
    dt => new Timestamp(dt.getMillis),
    ts => new DateTime(ts.getTime())
  )

}
