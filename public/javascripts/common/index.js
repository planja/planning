/**
 * Created by ShchykalauM on 19.01.2017.
 */

var dataForTreeView = null;

function changeNode(node) {
    if (node.length == 0)
        return null;
    $.each(node, function (index, value) {
        if (!value.isBusinessPartner)
            value.selectable = false;
        if (value.nodes.length == 0)
            value.nodes = null;
        else value.nodes = changeNode(value.nodes)
    });
    return node;
}

function changeDataForTreeView(data) {
    $.each(data, function (index, value) {
        if (!value.isBusinessPartner)
            value.selectable = false;
        value.nodes = changeNode(value.nodes);
    });
    return data;
}

planningApp.controller("indexController",
    function indexController($scope, $http) {
        $scope.loadDataForTreeView = function () {
            $http.get("/index/loaddatafortreeview")
                .then(function (response) {
                    dataForTreeView = response.data;
                    dataForTreeView = changeDataForTreeView(dataForTreeView);
                    $('#tree').treeview({
                        data: dataForTreeView,
                        onNodeSelected: function (event, data) {
                            window.location.href = "/businesspartners/editbusinesspartner/" + data.id
                        }
                    });
                });
        };
        $scope.loadDataForTreeView();
    });


$(document).ready(function () {
    $("#menu-home").addClass("active");
});