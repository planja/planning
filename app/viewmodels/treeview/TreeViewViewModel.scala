package viewmodels.treeview

import models.businesspartner.BusinessPartner
import models.country.Country
import models.region.Region
import play.api.libs.json.Json

import scala.collection.mutable.ListBuffer

/**
  * Created by ShchykalauM on 27.01.2017.
  */
case class TreeViewViewModel(id: Long, text: String, nodes: Seq[TreeViewNodeViewModel])

object TreeViewViewModel {
  implicit val TreeViewViewModelFormat = Json.format[TreeViewViewModel]

  def mapToTreeViewViewModels(regions: Seq[Region], countries: Seq[Country], businessPartners: Seq[BusinessPartner]): Seq[TreeViewViewModel] = {
    var result = new ListBuffer[TreeViewViewModel]()
    for (region <- regions) {
      var nodesForRegion = new ListBuffer[TreeViewNodeViewModel]
      for (country <- countries) {
        if (country.regionId == region.id.get) {
          var nodeForCountry = new ListBuffer[TreeViewNodeViewModel]
          for (businessPartner <- businessPartners) {
            if (businessPartner.countryId == country.id.get) {
              nodeForCountry += TreeViewNodeViewModel(businessPartner.id.get, businessPartner.shortName, new ListBuffer[TreeViewNodeViewModel])
            }
          }
          //if (nodeForCountry.isEmpty) nodeForCountry = null
          nodesForRegion += TreeViewNodeViewModel(country.id.get, country.fullName, nodeForCountry)
        }
      }
      // if (nodesForRegion.isEmpty) nodesForRegion = null
      result += TreeViewViewModel(region.id.get, region.fullName, nodesForRegion)
    }
    result
  }
}