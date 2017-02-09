package viewmodels.country

import models.country.Country
import play.api.libs.json.Json

/**
  * Created by ShchykalauM on 23.01.2017.
  */
case class CountryViewModel(var id: Option[Long], shortName: String, var fullName: String, regionId: Long) {

  def this(country: Country) {
    this(country.id, country.shortName, country.fullName, country.regionId)
  }

}

object CountryViewModel {

  implicit val countryViewModelFormat = Json.format[CountryViewModel]

  def toCountry(countryViewModel: CountryViewModel): Country = {
    Country(countryViewModel.id, countryViewModel.shortName, countryViewModel.fullName, countryViewModel.regionId)
  }
}
