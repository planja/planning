/**
 * Created by ShchykalauM on 02.02.2017.
 */

var businessPartnerTypeDataSource = dsBusinessPartnerType.getBusinessPartnerTypeDataSource();
businessPartnerTypeDataSource = JSON.parse(businessPartnerTypeDataSource.responseText);

var countriesDataSource = dsCountries.getCountriesDataSource();
countriesDataSource = JSON.parse(countriesDataSource.responseText);

planningApp.controller("businessPartnerDetailController",
    function BusinessPartnerController($scope, $http) {

        $scope.businessPartnerTypeDataSource = businessPartnerTypeDataSource;
        $scope.countriesDataSource = countriesDataSource;
        $scope.businessPartner = {};

        $scope.loadData = function () {
            $http({
                method: "GET",
                url: "/businesspartners/loaddataforbusinesspartner/" + businessPartnerId,
                async: false
            }).then(function (response) {
                $scope.businessPartner = response.data;
                $scope.businessPartnerTypeId = response.data.businessPartnerTypeId;
                $scope.countryId = response.data.countryId;
                $scope.businessPartner.startDate = moment(response.data.startDate).format('MM/DD/YYYY');
                $scope.businessPartner.endDate = moment(response.data.endDate).format('MM/DD/YYYY');
            }, function (response) {
                $scope.data = response.statusText;
            });
        };
        $scope.loadData();


        $scope.update = function (businessPartner, businessPartnerTypeId, countryId) {
            $('#eventForm').data('formValidation').validate();
            if ($('#eventForm').data('formValidation').isValid()) {

                var data = {
                    id: null,
                    shortName: businessPartner.shortName,
                    startDate: moment(businessPartner.startDate).format('YYYY-MM-DD'),
                    endDate: moment(businessPartner.endDate).format('YYYY-MM-DD'),
                    address: businessPartner.address,
                    email: businessPartner.email,
                    countryId: countryId,
                    businessPartnerTypeId: businessPartnerTypeId
                };

                $http.post("/businesspartners/savebusinesspartner", data)
                    .then(function (data) {
                        alert("good")
                    }, function (data) {

                    });
                alert(", ваш ответ сохранен");
            }
        };

    });


$(document).ready(function () {



    $('#startDatePicker')
        .datepicker({
            format: 'mm/dd/yyyy',
            autoclose: true
        })
        .on('changeDate', function (e) {
            $('#eventForm').formValidation('revalidateField', 'startDate');
        });

    $('#endDatePicker')
        .datepicker({
            format: 'mm/dd/yyyy',
            autoclose: true
        })
        .on('changeDate', function (e) {
            $('#eventForm').formValidation('revalidateField', 'endDate');
        });

    var form = $('#eventForm');

    form
        .formValidation({
            framework: 'bootstrap',
            icon: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                shortName: {
                    validators: {
                        notEmpty: {
                            message: 'The short name is required'
                        }
                    }
                },
                startDate: {
                    validators: {
                        notEmpty: {
                            message: 'The start date is required'
                        },
                        date: {
                            format: 'MM/DD/YYYY',
                            max: 'endDate',
                            message: 'The start date is not a valid'
                        }
                    }
                },
                endDate: {
                    validators: {
                        notEmpty: {
                            message: 'The end date is required'
                        },
                        date: {
                            format: 'MM/DD/YYYY',
                            min: 'startDate',
                            message: 'The end date is not a valid'
                        }
                    }
                },
                businessPartnerType: {
                    validators: {
                        notEmpty: {
                            message: 'Business partner type is required'
                        }
                    }
                },
                address: {
                    validators: {
                        notEmpty: {
                            message: 'Address is required'
                        }
                    }
                },
                email: {
                    validators: {
                        notEmpty: {
                            message: 'Enter email'
                        },
                        regexp: {
                            regexp: '^[^@\\s]+@([^@\\s]+\\.)+[^@\\s]+$',
                            message: 'The email is not a valid'
                        }
                    }
                },
                country: {
                    validators: {
                        notEmpty: {
                            message: 'Country is required'
                        }
                    }
                }
            }
        })
        .on('success.field.fv', function (e, data) {
            if (data.field === 'startDate' && !data.fv.isValidField('endDate')) {
                data.fv.revalidateField('endDate');
            }

            if (data.field === 'endDate' && !data.fv.isValidField('startDate')) {
                data.fv.revalidateField('startDate');
            }
        });


});
