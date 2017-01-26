/**
 * Created by ShchykalauM on 26.01.2017.
 */

var dsBusinessPartnerType = (function () {

    return {
        getBusinessPartnerTypeDataSource: function () {
            return $.ajax({
                type: "GET",
                url: "/businesspartnertype/getbusinesspartnertypeinfo",
                async: false
            });
        }
    };

}());

