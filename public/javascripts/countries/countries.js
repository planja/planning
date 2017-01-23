/**
 * Created by ShchykalauM on 23.01.2017.
 */

var regionsDataSource = dsRegions.getRegionsDataSource();

$(document).ready(function () {
    $("#menu-countries").addClass("active");
    regionsDataSource = JSON.parse(regionsDataSource.responseText);

    var controller = {
        loadData: function (filter) {
            var data = $.ajax({
                type: "GET",
                url: "/countries/getcountries",
                async: false
            });
            return $.grep(JSON.parse(data.responseText), function (region) {
                return (!filter.fullName || region.fullName.indexOf(filter.fullName) > -1)
                    && (!filter.shortName || region.shortName.indexOf(filter.shortName) > -1);
                    //&& (!filter.regionId || region.regionId == filter.regionId);
            })

        },

        insertItem: function (item) {
            return $.ajax({
                type: "POST",
                url: "/countries/savecountry",
                data: JSON.stringify(item),
                contentType: "application/json; charset=utf-8",
                dataType: "json"
            });
        },

        updateItem: function (item) {
            return $.ajax({
                type: "PUT",
                url: "/countries/updatecountry",
                data: JSON.stringify(item),
                contentType: "application/json; charset=utf-8",
                dataType: "json"
            });
        },

        deleteItem: function (item) {
            return $.ajax({
                type: "DELETE",
                url: "/countries/deletecountry/" + item.id,
                data: item
            });
        }
    };

    $("#countriesGrid").jsGrid({
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

        deleteConfirm: "Do you really want to delete the country?",

        controller: controller,

        fields: [
            {name: "id", type: "number", visible: false},
            {
                name: "shortName", type: "text", title: "Short Name", validate: {
                message: "Enter country short name", validator: function (value) {
                    return value.length > 0;
                }
            }
            },
            {
                name: "fullName",
                type: "text",
                title: "Full Name",
                validate: {
                    message: "Enter country full name", validator: function (value) {
                        return value.length > 0;
                    }
                }
            },
            {
                name: "regionId",
                title: "Region",
                type: "select",
                items: regionsDataSource,
                valueField: "id",
                textField: "text"
            },
            {type: "control"}
        ]
    });

});
