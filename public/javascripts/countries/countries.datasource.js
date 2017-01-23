/**
 * Created by ShchykalauM on 23.01.2017.
 */


var dsCountries = (function () {

    return {
        getCountriesDataSource: function () {
            return $.ajax({
                type: "GET",
                url: "/countries/getcountriesinfo",
                async: false
            });
        }
    };

}());
