/**
 * Created by ShchykalauM on 06.02.2017.
 */

var dsUsersOfBusinessPartner = (function () {

    return {
        getUsersOfBusinessPartner: function () {
            return new kendo.data.DataSource({
                transport: {
                    read: {
                        url: "/usersofbusinesspartner/getusersofbusinesspartnerinfo",
                        dataType: "json",
                        contentType: "application/json",
                        async: false
                    }
                },
                sort: {field: "text", dir: "asc"}
            });
        }

    };


}());

