/**
 * Created by ShchykalauM on 25.01.2017.
 */


var now = new Date();

planningApp.controller("businessPartnerController",
    function AnswerController($scope) {

        var stringMonthFromDate = (now.getMonth() + 1).toString().length == 1 ? ("0" + (now.getMonth() + 1).toString()) : ((now.getMonth() + 1).toString());

        $scope.businessPartner = {
            fromDate: (now.getYear() + 1900).toString() + "-" + stringMonthFromDate + "-" + now.getDate().toString(),
            toDate: (now.getYear() + 1901).toString() + "-" + stringMonthFromDate + "-" + now.getDate().toString()
        };

        $scope.save = function (businessPartner, businessPartnerForm) {
            if (businessPartnerForm.$valid) {
                // действия по сохранению
                alert(", ваш ответ сохранен");
            }
        };
    });

var startDate = new Date('01/01/2012');
var FromEndDate = new Date();
var ToEndDate = new Date();

ToEndDate.setDate(ToEndDate.getDate() + 365);

$(document).ready(function () {
    $("#menu-business-partners").addClass("active");

    $("#fromDate").datepicker({
       // startDate: "2010-01-01",
        autoclose: true
    }).on('changeDate', function(e) {
        // Revalidate the start date field
        $('#businessPartnerForm').formValidation('revalidateField', 'startDate');
    });


    $("#toDate").datepicker({
        //startDate: "2010-03-25",
        autoclose: true
    }).on('changeDate', function(e) {
        $('#businessPartnerForm').formValidation('revalidateField', 'endDate');
    });

    $(".datepicker").datetimepicker({
        language: 'fr',
        weekStart: 1,
        todayBtn: true,
        autoclose: true,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0
    });

});