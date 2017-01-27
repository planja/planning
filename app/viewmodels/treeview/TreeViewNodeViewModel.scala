package viewmodels.treeview

import play.api.libs.json.Json

/**
  * Created by ShchykalauM on 27.01.2017.
  */
case class TreeViewNodeViewModel(id: Long, text: String, nodes: Seq[TreeViewNodeViewModel])

object TreeViewNodeViewModel{
  implicit val TreeViewNodeViewModelFormat = Json.format[TreeViewNodeViewModel]
}