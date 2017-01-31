/**
 * Created by ShchykalauM on 31.01.2017.
 */


var countriesDataSource = dsCountries.getCountriesDataSource();
countriesDataSource = JSON.parse(countriesDataSource.responseText);
countriesDataSource.push({id: -1, text: "Show all"});

var businessPartnersDataSource = dsBusinessPartners.getBusinessPartnersDataSource();
businessPartnersDataSource = JSON.parse(businessPartnersDataSource.responseText);
businessPartnersDataSource.push({id: -1, text: "Show all"});

countriesDataSource.sort(function (a, b) {
    return a.id - b.id;
});

businessPartnersDataSource.sort(function (a, b) {
    return a.id - b.id;
});

$(document).ready(function () {
    $("#menu-users-of-business-partner").addClass("active");

    var controller = {
        loadData: function (filter) {
            var data = $.ajax({
                type: "GET",
                url: "/usersofbusinesspartner/getusersofbusinesspartner",
                async: false
            });
            return $.grep(JSON.parse(data.responseText), function (userOfBusinessPartner) {
                if (filter.countryId == -1 && filter.businessPartnerId == -1) {
                    return (!filter.firstName || userOfBusinessPartner.firstName.indexOf(filter.firstName) > -1)
                        && (!filter.lastName || userOfBusinessPartner.lastName.indexOf(filter.lastName) > -1);
                } else if (filter.countryId == -1) {
                    return (!filter.firstName || userOfBusinessPartner.firstName.indexOf(filter.firstName) > -1)
                        && (!filter.lastName || userOfBusinessPartner.lastName.indexOf(filter.lastName) > -1)
                        && (userOfBusinessPartner.businessPartnerId == filter.businessPartnerId);
                } else return (!filter.firstName || userOfBusinessPartner.firstName.indexOf(filter.firstName) > -1)
                    && (!filter.lastName || userOfBusinessPartner.lastName.indexOf(filter.lastName) > -1)
                    && (userOfBusinessPartner.countryId == filter.countryId);


            })

        },

        insertItem: function (item) {
            if (item.businessPartnerId == -1)
                item.businessPartnerId = null;
            if (item.countryId == -1)
                item.countryId = null;
            return $.ajax({
                type: "POST",
                url: "/usersofbusinesspartner/saveuserofbusinesspartner",
                data: JSON.stringify(item),
                contentType: "application/json; charset=utf-8",
                dataType: "json"
            });
        },

        updateItem: function (item) {
            if (item.businessPartnerId == -1)
                item.businessPartnerId = null;
            if (item.countryId == -1)
                item.countryId = null;
            return $.ajax({
                type: "PUT",
                url: "/usersofbusinesspartner/updateuserofbusinesspartner",
                data: JSON.stringify(item),
                contentType: "application/json; charset=utf-8",
                dataType: "json"
            });
        },

        deleteItem: function (item) {
            return $.ajax({
                type: "DELETE",
                url: "/usersofbusinesspartner/deleteuserofbusinesspartner/" + item.id,
                data: item
            });
        }
    };

    $("#regionsGrid").jsGrid({
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

        deleteConfirm: "Do you really want to delete the user of business partner?",

        controller: controller,

        fields: [
            {name: "id", type: "number", visible: false},
            {
                name: "firstName", type: "text", title: "First Name", validate: {
                message: "Enter first name", validator: function (value) {
                    return value.length > 0;
                }
            }
            },
            {
                name: "lastName",
                type: "text",
                title: "Last Name",
                validate: {
                    message: "Enter last name", validator: function (value) {
                        return value.length > 0;
                    }
                }
            },
            {
                name: "countryId",
                title: "Country",
                type: "select",
                items: countriesDataSource,
                valueField: "id",
                textField: "text"
            },
            {
                name: "businessPartnerId",
                title: "Business Partner",
                type: "select",
                items: businessPartnersDataSource,
                valueField: "id",
                textField: "text"
            },
            {name: "isCertificated", type: "checkbox", title: "Is Certificated", filtering: false},
            {type: "control"}
        ]
    });

});