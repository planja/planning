/**
 * Created by ShchykalauM on 19.01.2017.
 */

var dataForTreeView = null;

planningApp.controller("indexController",
    function indexController($scope, $http) {
        $scope.loadDataForTreeView = function () {
            $http.get("/index/loaddatafortreeview")
                .then(function (response) {
                    dataForTreeView = response.data;
                });
        };
        $scope.loadDataForTreeView();
    });


$(document).ready(function () {
    $("#menu-home").addClass("active");

    var tree = [
        {
            text: "Parent 1",
            nodes: [
                {
                    text: "Child 1",
                    nodes: [
                        {
                            text: "Grandchild 1",
                            id: 123
                        },
                        {
                            text: "Grandchild 2"
                        }
                    ]
                },
                {
                    text: "Child 2"
                }
            ]
        },
        {
            text: "Parent 2"
        },
        {
            text: "Parent 3"
        },
        {
            text: "Parent 4"
        },
        {
            text: "Parent 5"
        }
    ];

    $('#tree').treeview({
        data: tree,
        onNodeSelected: function (event, data) {
            var q = 0;
        }
    });


});