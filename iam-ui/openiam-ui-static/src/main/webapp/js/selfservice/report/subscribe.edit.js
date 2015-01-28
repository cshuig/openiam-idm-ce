console = window.console || {};
console.log = window.console.log || function () {
};

OPENIAM = window.OPENIAM || {};
OPENIAM.SubscribeReport = window.OPENIAM.SubscribeReport || {};
OPENIAM.SubscribeReport.Edit = {
    init: function () {
        $("#deleteSubscribeReport").click(function () {
            OPENIAM.Modal.Warn({
                message: localeManager["openiam.ui.report.subscription.delete.warning.message"],
                buttons: true,
                OK: {
                    text: localeManager["openiam.ui.report.subscription.delete.confirmation"],
                    onClick: function () {
                        OPENIAM.Modal.Close();
                        OPENIAM.SubscribeReport.Edit.deleteObject();
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

        $("#testSubscription").click(function () {
            OPENIAM.Modal.Warn({
                message: localeManager["openiam.ui.report.subscription.test.warning.message"],
                buttons: true,
                OK: {
                    text: localeManager["openiam.ui.report.subscription.test.confirmation"],
                    onClick: function () {
                        OPENIAM.Modal.Close();
                        OPENIAM.SubscribeReport.Edit.testSubscription();
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

        $("#saveSubscribeReport").click(function () {
            OPENIAM.SubscribeReport.Edit.save();
            return false;
        });
    },
    save: function () {
        $.ajax({
            url: "saveSubscribeReport.html",
            data: JSON.stringify(this.toJSON()),
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            success: function (data, textStatus, jqXHR) {
                if (data.status == 200) {
                    OPENIAM.Modal.Success({message: data.successMessage, showInterval: 2000, onIntervalClose: function () {
                        if (data.redirectURL != null && data.redirectURL != undefined && data.redirectURL.length > 0) {
                            window.location.href = data.redirectURL;
                        } else {
                            window.location.reload(true);
                        }
                    }});
                } else {
                    OPENIAM.Modal.Error({errorList: data.errorList});
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
    },
    deleteObject: function () {
        $.ajax({
            url: "deleteSubscribeReport.html",
            data: {reportId: OPENIAM.ENV.Id},
            type: "POST",
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                if (data.status == 200) {
                    OPENIAM.Modal.Success({message: data.successMessage, showInterval: 2000, onIntervalClose: function () {
                        window.location.href = data.redirectURL;
                    }});
                } else {
                    OPENIAM.Modal.Error({errorList: data.errorList});
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
    },
    testSubscription: function () {
        $.ajax({
            url: "testSubscription.html",
            data: {reportId: OPENIAM.ENV.Id},
            type: "POST",
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                if (data.status == 200) {
                    OPENIAM.Modal.Success({message: data.successMessage, showInterval: 2000});
                } else {
                    OPENIAM.Modal.Error({errorList: data.errorList});
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
    },
    toJSON: function () {
        var obj = {};
        obj.reportId = OPENIAM.ENV.Id;
        obj.reportInfoId = $("#reportInfoId").val();
        if ($("#reportName").val()) {
            obj.reportName = $("#reportName").val();
        } else {
            obj.reportName = $("#name option:selected").val();
        }
        obj.deliveryMethod = $("#dmethod").val();
        obj.deliveryFormat = $("#dformat").val();
        obj.deliveryAudience = $("#daudience").val();
        obj.status = $("#status").val();
        return obj;
    }
};

$(document).ready(function () {
    OPENIAM.SubscribeReport.Edit.init();
});