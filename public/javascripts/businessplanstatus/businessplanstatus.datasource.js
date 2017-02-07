/**
 * Created by ShchykalauM on 07.02.2017.
 */

var dsBusinessPlanStatus = (function () {

    return {
        getBusinessPlanStatusDataSource: function () {
            return $.ajax({
                type: "GET",
                url: "/businessplanstatus/getbusinessplanstatusinfo",
                async: false
            });
        }
    };

}());