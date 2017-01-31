/**
 * Created by ShchykalauM on 31.01.2017.
 */

var dsBusinessPartners = (function () {
    return {
        getBusinessPartnersDataSource: function () {
            return $.ajax({
                type: "GET",
                url: "/businesspartners/getbusinesspartnersinfo",
                async: false
            });
        }
    }

}());