/**
 * Created by ShchykalauM on 24.01.2017.
 */


$(document).ready(function () {
    $("#menu-business-plan-status").addClass("active")

    var controller = {
        loadData: function (filter) {
            var data = $.ajax({
                type: "GET",
                url: "/businessplanstatus/getbusinessplanstatus",
                async: false
            });
            return $.grep(JSON.parse(data.responseText), function (businessPlanStatus) {
                return (!filter.name || businessPlanStatus.name.indexOf(filter.name) > -1);
            })

        },

        insertItem: function (item) {
            return $.ajax({
                type: "POST",
                url: "/businessplanstatus/savebusinessplanstatus",
                data: JSON.stringify(item),
                contentType: "application/json; charset=utf-8",
                dataType: "json"
            });
        },

        updateItem: function (item) {
            return $.ajax({
                type: "PUT",
                url: "/businessplanstatus/updatebusinessplanstatus",
                data: JSON.stringify(item),
                contentType: "application/json; charset=utf-8",
                dataType: "json"
            });
        },

        deleteItem: function (item) {
            return $.ajax({
                type: "DELETE",
                url: "/businessplanstatus/deletebusinessplanstatus/" + item.id,
                data: item
            });
        }
    };

    $("#business-plan-status-grid").jsGrid({
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

        deleteConfirm: "Do you really want to delete the business plan status?",

        controller: controller,

        fields: [
            {name: "id", type: "number", visible: false},
            {
                name: "name", type: "text", title: "Business Plan Status", validate: {
                message: "Enter business plan status short name", validator: function (value) {
                    return value.length > 0;
                }
            }
            },
            {type: "control"}
        ]
    });

});