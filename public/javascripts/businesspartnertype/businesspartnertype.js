/**
 * Created by ShchykalauM on 24.01.2017.
 */


$(document).ready(function () {
    $("#menu-business-partner-type").addClass("active")

    var controller = {
        loadData: function (filter) {
            var data = $.ajax({
                type: "GET",
                url: "/businesspartnertype/getbusinesspartnertype",
                async: false
            });
            return $.grep(JSON.parse(data.responseText), function (businessPartnerType) {
                return (!filter.name || businessPartnerType.name.indexOf(filter.name) > -1);
            })

        },

        insertItem: function (item) {
            return $.ajax({
                type: "POST",
                url: "/businesspartnertype/savebusinesspartnertype",
                data: JSON.stringify(item),
                contentType: "application/json; charset=utf-8",
                dataType: "json"
            });
        },

        updateItem: function (item) {
            return $.ajax({
                type: "PUT",
                url: "/businesspartnertype/updatebusinesspartnertype",
                data: JSON.stringify(item),
                contentType: "application/json; charset=utf-8",
                dataType: "json"
            });
        },

        deleteItem: function (item) {
            return $.ajax({
                type: "DELETE",
                url: "/businesspartnertype/deletebusinesspartnertype/" + item.id,
                data: item
            });
        }
    };

    $("#business-partner-type-grid").jsGrid({
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

        deleteConfirm: "Do you really want to delete the business partner type?",

        controller: controller,

        fields: [
            {name: "id", type: "number", visible: false},
            {
                name: "name", type: "text", title: "Business Partner Type", validate: {
                message: "Enter business partner type", validator: function (value) {
                    return value.length > 0;
                }
            }
            },
            {type: "control"}
        ]
    });

});