OPENIAM = window.OPENIAM || {};

OPENIAM.ManagedSys = {
    init: function () {
        $("#deleteBean").click(function () {
            OPENIAM.Modal.Warn({
                message: OPENIAM.ENV.Text.DeleteWarn,
                buttons: true,
                OK: {
                    text: localeManager["openiam.ui.report.mngsys.delete.confirmation"],
                    onClick: function () {
                        OPENIAM.Modal.Close();
                        OPENIAM.ManagedSys.performDelete();
                    }
                },
                Cancel: {
                    text: localeManager["openiam.ui.common.cancel"],
                    onClick: function () {
                        OPENIAM.Modal.Close();
                    }
                }
            });
            return false;
        });

        $("#save").click(function () {
            OPENIAM.ManagedSys.save();
            return false;
        });

        $("#testConnection").click(function () {
            OPENIAM.ManagedSys.testConnection();
        });

        $("#requestSSLCert").click(function () {
            OPENIAM.ManagedSys.certRequest();
        });

        $("#skipGroupProvision").change(function () {
            OPENIAM.ManagedSys.showGroupMatching();
        });

        OPENIAM.ManagedSys.populate();
        OPENIAM.ManagedSys.showGroupMatching();
    },
    populate: function () {
        var fieldName = null;
        if (OPENIAM.ENV.MngSysProps) {
            fieldName = {
                fieldName: "name",
                type: "select",
                label: localeManager["openiam.ui.common.attribute.name"],
                required: true,
                items: OPENIAM.ENV.MngSysProps
            };
        } else {
            fieldName = {
                fieldName: "name",
                type: "text",
                label: localeManager["openiam.ui.common.attribute.name"],
                required: true
            };
        }
        var modalFields = [
            fieldName,
            {
                fieldName: "metadataId",
                type: "select",
                label: localeManager["openiam.ui.metadata.element"],
                required: false
            },
            {
                fieldName: "value",
                type: "text",
                label: localeManager["openiam.ui.common.attribute.value"],
                required: true
            }
        ];

        if (OPENIAM.ENV.Resource != null) {
            OPENIAM.ENV.Resource.resourceProps = (OPENIAM.ENV.Resource.resourceProps != null) ? OPENIAM.ENV.Resource.resourceProps : [];
            $("#attributesContainer").attributeTableEdit({
                objectArray: OPENIAM.ENV.Resource.resourceProps,
                dialogModalFields: modalFields,
                fieldNames: ["name", "metadataName", "value"]
            });
        }
    },
    testConnection: function () {
        $.ajax({
            url: "testManagedSysConnection.html",
            data: {
                id: OPENIAM.ENV.ManagedSysId
            },
            type: "POST",
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                if (data.status == 200) {
                    OPENIAM.Modal.Success({
                        message: data.successMessage,
                        showInterval: 2000,
                        onIntervalClose: function () {

                        }
                    });
                } else {
                    OPENIAM.Modal.Error({
                        errorList: data.errorList
                    });
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
    },
    certRequest: function () {
        $.ajax({
            url: "certRequest.html",
            data: {
                id: OPENIAM.ENV.ManagedSysId
            },
            type: "POST",
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                if (data.status == 200) {
                    OPENIAM.Modal.Success({
                        message: data.successMessage,
                        showInterval: 2000,
                        onIntervalClose: function () {

                        }
                    });
                } else {
                    OPENIAM.Modal.Error({
                        errorList: data.errorList
                    });
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
    },
    showGroupMatching: function () {
        var hide = $("#skipGroupProvision").is(":checked");
        if (hide) {
            $("#keyFieldGroup").closest("tr").hide();
            $("#baseDnGroup").closest("tr").hide();
            $("#searchBaseDnGroup").closest("tr").hide();
            $("#searchFilterGroup").closest("tr").hide();
        } else {
            $("#keyFieldGroup").closest("tr").show();
            $("#baseDnGroup").closest("tr").show();
            $("#searchBaseDnGroup").closest("tr").show();
            $("#searchFilterGroup").closest("tr").show();
        }
    },
    save: function () {
        $.ajax({
            url: "mngsystem.html",
            data: JSON.stringify(this.toJSON()),
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            success: function (data, textStatus, jqXHR) {
                if (data.status == 200) {
                    OPENIAM.Modal.Success({
                        message: data.successMessage,
                        showInterval: 2000,
                        onIntervalClose: function () {
                            if (data.redirectURL != null && data.redirectURL != undefined && data.redirectURL.length > 0) {
                                window.location.href = data.redirectURL;
                            } else {
                                window.location.reload(true);
                            }
                        }
                    });
                } else {
                    OPENIAM.Modal.Error({
                        errorList: data.errorList
                    });
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
    },
    performDelete: function () {
        $.ajax({
            url: "deleteManagedSystem.html",
            data: {
                id: OPENIAM.ENV.ManagedSysId
            },
            type: "POST",
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                if (data.status == 200) {
                    OPENIAM.Modal.Success({
                        message: data.successMessage,
                        showInterval: 2000,
                        onIntervalClose: function () {
                            window.location.href = data.redirectURL;
                        }
                    });
                } else {
                    OPENIAM.Modal.Error({
                        errorList: data.errorList
                    });
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
    },
    toJSON: function () {
        var obj = {};
        obj.objectSearchId = OPENIAM.ENV.ObjectSearchId;
        obj.objectSearchIdGroup = OPENIAM.ENV.ObjectSearchIdGroup;
        obj.resourceId = OPENIAM.ENV.ResourceId;
        obj.id = OPENIAM.ENV.ManagedSysId;
        obj.name = $("#name").val();
        obj.description = $("#description").val();
        obj.status = $("#status").val();
        obj.connectorId = $("#connectorId").val();
        obj.hostUrl = $("#hostUrl").val();
        obj.port = $("#port").val();
        obj.clientCommProtocol = $("#clientCommProtocol").val();
        obj.jdbcDriverUrl = $("#jdbcDriverUrl").val();
        obj.connectionString = $("#connectionString").val();
        obj.login = $("#login").val();
        obj.password = $("#password").val();

        obj.keyField = $("#keyField").val();
        obj.baseDn = $("#baseDn").val();
        obj.searchBaseDn = $("#searchBaseDn").val();
        obj.searchFilter = $("#searchFilter").val();

        obj.keyFieldGroup = $("#keyFieldGroup").val();
        obj.baseDnGroup = $("#baseDnGroup").val();
        obj.searchBaseDnGroup = $("#searchBaseDnGroup").val();
        obj.searchFilterGroup = $("#searchFilterGroup").val();

        obj.attributeNamesLookup = $("#attributeNamesLookup").val();
        obj.searchScope = $("#searchScope").val();
        obj.primaryRepository = $("#primaryRepository").is(":checked") ? 1 : 0;
        obj.secondaryRepositoryId = $("#secondaryRepositoryId").val();
        obj.updateSecondary = $("#updateSecondary").is(":checked") ? 1 : 0;
        obj.handler5 = $("#handler5").val();
        obj.addHandler = $("#addHandler").val();
        obj.modifyHandler = $("#modifyHandler").val();
        obj.deleteHandler = $("#deleteHandler").val();
        obj.passwordHandler = $("#passwordHandler").val();
        obj.suspendHandler = $("#suspendHandler").val();
        obj.resumeHandler = $("#resumeHandler").val();
        obj.searchHandler = $("#searchHandler").val();
        obj.lookupHandler = $("#lookupHandler").val();
        obj.testConnectionHandler = $("#testConnectionHandler").val();
        obj.reconcileResourceHandler = $("#reconcileResourceHandler").val();
        obj.attributeNamesHandler = $("#attributeNamesHandler").val();
        obj.resourceProps = (OPENIAM.ENV.Resource != null) ? OPENIAM.ENV.Resource.resourceProps : [];
        obj.skipGroupProvision = $("#skipGroupProvision").is(":checked");
        obj.changedByEndUser = $("#changedByEndUser").is(":checked");
        return obj;
    }
};

$(document).ready(function () {
    OPENIAM.ManagedSys.init();
});

$(window).load(function () {

});