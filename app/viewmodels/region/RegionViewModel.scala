package viewmodels.region

import models.region.Region
import play.api.libs.json.Json

/**
  * Created by ShchykalauM on 20.01.2017.
  */
case class RegionViewModel(var id: Option[Long], shortName: String, fullName: String) {
  def this(region: Region) {
    this(region.id, region.shortName, region.fullName)
  }
}

object RegionViewModel {

  implicit val regionViewModelFormat = Json.format[RegionViewModel]

  def toRegion(regionViewModel: RegionViewModel): Region = {
    Region(regionViewModel.id, regionViewModel.shortName, regionViewModel.fullName)
  }
}
