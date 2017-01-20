/**
 * Created by ShchykalauM on 19.01.2017.
 */

$(document).ready(function () {
    $("#menu-regions").addClass("active")

    var controller = {
        loadData: function (filter) {
            var data = $.ajax({
                type: "GET",
                url: "/regions/getregions",
                async: false
            });
            return $.grep(JSON.parse(data.responseText), function (region) {
                return (!filter.fullName || region.fullName.indexOf(filter.fullName) > -1)
                    && (!filter.shortName || region.shortName.indexOf(filter.shortName) > -1);
            })

        },

        insertItem: function (item) {
            return $.ajax({
                type: "POST",
                url: "/regions/saveregion",
                data: JSON.stringify(item),
                contentType: "application/json; charset=utf-8",
                dataType: "json"
            });
        },

        updateItem: function (item) {
            return $.ajax({
                type: "PUT",
                url: "/regions/updateregion",
                data: JSON.stringify(item),
                contentType: "application/json; charset=utf-8",
                dataType: "json"
            });
        },

        deleteItem: function (item) {
            return $.ajax({
                type: "DELETE",
                url: "/regions/deleteregion/" + item.id,
                data: item
            });
        }
    };

    window.controller = controller;

    controller.regions = [
        {
            "id": 0,
            "shortName": "EU",
            "fullName": "Europe"
        },
        {
            "id": 1,
            "shortName": "NA",
            "fullName": "North America"
        }
    ];

    $("#jsGrid").jsGrid({
        height: 400,
        width: "100%",

        filtering: true,
        editing: true,
        sorting: true,
        paging: true,
        autoload: true,
        inserting: true,

        pageSize: 15,
        pageButtonCount: 5,

        deleteConfirm: "Do you really want to delete the region?",

        controller: controller,

        fields: [
            {name: "id", type: "number", visible: false},
            {name: "shortName", type: "text", title: "Short Name"},
            {name: "fullName", type: "text", title: "Full Name"},
            {type: "control"}
        ]
    });

});

/*planningApp.controller("regionsController",
 function ($scope, $http) {

 $scope.getRegions = function () {
 $http({
 method: "GET",
 url: "/regions/getregions"
 }).then(function mySucces(response) {
 $scope.data = response.data;
 }, function myError(response) {
 $scope.data = response.statusText;
 });
 };

 $scope.getRegions();

 /* $scope.tableParams = new ngTableParams({
 page: 1,
 count: 6
 },{
 total:tableData.length,
 //Returns the data for rendering
 getData : function($defer,params){
 $http.get('data.json').then(function(response) {
 tableData = response.data.person;
 $defer.resolve(tableData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
 params.total(tableData.length);
 });
 }
 });
 });*/