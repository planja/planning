/**
 * Created by ShchykalauM on 23.01.2017.
 */

var dsRegions = (function () {

    return {
        getRegionsDataSource: function () {
            return $.ajax({
                type: "GET",
                url: "/regions/getregionsinfo",
                async: false
            });
        }
    };


}());