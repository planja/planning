/**
 * Created by ShchykalauM on 02.02.2017.
 */

var businessPartnerTypeDataSource = dsBusinessPartnerType.getBusinessPartnerTypeDataSource();
businessPartnerTypeDataSource = JSON.parse(businessPartnerTypeDataSource.responseText);

var countriesDataSource = dsCountries.getCountriesDataSource();
countriesDataSource = JSON.parse(countriesDataSource.responseText);

var businessPlanStatusDataSource = dsBusinessPlanStatus.getBusinessPlanStatusDataSource();
businessPlanStatusDataSource = JSON.parse(businessPlanStatusDataSource.responseText);

var usersOfBusinessPartnerDataSource = dsUsersOfBusinessPartner.getUsersOfBusinessPartner();
usersOfBusinessPartnerDataSource.read();

planningApp.controller("businessPartnerDetailController",
    function BusinessPartnerController($scope, $http) {

        //DataSources
        $scope.businessPartnerTypeDataSource = businessPartnerTypeDataSource;
        $scope.countriesDataSource = countriesDataSource;
        $scope.usersOfBusinessPartnerDataSource = usersOfBusinessPartnerDataSource;
        $scope.businessPlanStatusDataSource = businessPlanStatusDataSource;

        //BusinessPartner entity
        $scope.businessPartner = {};

        //BusinessPlan entity
        $scope.businessPlan = {
            id: null,
            plan: 0,
            revenue: 0,
            businessPartnerId: parseInt(businessPartnerId),
            visible: false
        };

        //Kendo multiselect
        $scope.selectOptions = {
            placeholder: "Select users...",
            dataTextField: "text",
            dataValueField: "id",
            valuePrimitive: true,
            dataSource: $scope.usersOfBusinessPartnerDataSource
        };

        //Numeric textbox format
        $scope.currencyFormat = "'# $'";

        $scope.loadData = function () {
            $http({
                method: "GET",
                url: "/businesspartners/loaddataforbusinesspartner/" + businessPartnerId,
                async: false
            }).then(function (response) {
                $scope.businessPartner = response.data;
                $scope.businessPartnerTypeId = response.data.businessPartnerTypeId;
                $scope.countryId = response.data.countryId;
                $scope.usersIdOfBusinessPartners = response.data.usersIdOfBusinessPartners;
                $scope.businessPartner.startDate = moment(response.data.startDate).format('MM/DD/YYYY');
                $scope.businessPartner.endDate = moment(response.data.endDate).format('MM/DD/YYYY');
            }, function (response) {
            });
        };

        $scope.loadBusinessPlan = function () {
            $http({
                method: "GET",
                url: "/businesspartners/loadbusinessplan/" + businessPartnerId,
                async: false
            }).then(function (response) {
                if (response.data != "null") {
                    $scope.businessPlan.id = response.data.id;
                    $scope.businessPlan.plan = response.data.plan;
                    $scope.businessPlan.revenue = response.data.revenue;
                    $scope.businessPlan.businessPartnerId = response.data.businessPartnerId;
                    $scope.businessPlanStatusId = response.data.businessPlanStatusId;
                    $scope.businessPlan.visible = true;
                }
            }, function (response) {
            });
        };
        $scope.loadData();
        $scope.loadBusinessPlan();

        $scope.deleteBusinessPlan = function (id) {
            $http({
                method: "DELETE",
                url: "/businesspartners/deletebusinessplan/" + id,
                async: false
            }).then(function (response) {
                $scope.businessPlan.id = null;
                $scope.businessPlan.plan = 0;
                $scope.businessPlan.revenue = 0;
                $scope.businessPlan.businessPartnerId = parseInt(businessPartnerId);
                $scope.businessPlanStatusId = 0;
                $scope.businessPlan.visible = false;
            }, function (response) {
            });
        };

        $scope.deleteBusinessPartner = function () {
            $http({
                method: "DELETE",
                url: "/businesspartners/deletebusinesspartner/" + businessPartnerId,
                async: false
            }).then(function (response) {
                window.location.href = "/";
            }, function (response) {
                window.location.href = "/";
            });
        };


        $scope.updateBusinessPartner = function (businessPartner, businessPartnerTypeId, countryId, usersIdOfBusinessPartners) {
            $('#eventForm').data('formValidation').validate();
            if ($('#eventForm').data('formValidation').isValid()) {

                var data = {
                    id: parseInt(businessPartnerId),
                    shortName: businessPartner.shortName,
                    startDate: moment(businessPartner.startDate).format('YYYY-MM-DD'),
                    endDate: moment(businessPartner.endDate).format('YYYY-MM-DD'),
                    address: businessPartner.address,
                    email: businessPartner.email,
                    countryId: countryId,
                    businessPartnerTypeId: businessPartnerTypeId,
                    usersIdOfBusinessPartners: usersIdOfBusinessPartners
                };

                $http.post("/businesspartners/updatebusinesspartner", data)
                    .then(function (data) {
                        window.location.href = "/";
                    }, function (data) {

                    });
            }
        };

        $scope.saveBusinessPlan = function (businessPlan, businessPlanStatusId) {
            var data = {
                id: businessPlan.id,
                plan: businessPlan.plan,
                revenue: businessPlan.revenue,
                businessPlanStatusId: businessPlanStatusId,
                businessPartnerId: businessPlan.businessPartnerId
            };

            $http.post("/businesspartners/savebusinessplan", data)
                .then(function (data) {
                    $scope.businessPlan.visible = true;
                    alert("Business plan saved")
                }, function (data) {

                });
        }

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
