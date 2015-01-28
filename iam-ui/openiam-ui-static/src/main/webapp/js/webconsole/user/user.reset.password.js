OPENIAM = window.OPENIAM || {};
OPENIAM.User = window.OPENIAM.User || {};

OPENIAM.User.ResetPassword = {
    init: function () {

        $("#autoGeneratePassword").change(function () {
            if (this.checked) {
                $("#passwordLabel, #confirmPasswordLabel").hide();
                $("#password, #confirmPassword").val("").hide();
                $("#passwordRules").val("").hide();
            } else {
                $("#passwordLabel, #confirmPasswordLabel").show();
                $("#password, #confirmPassword").val("").show();
                $("#passwordRules").val("").show();
            }
        });

        $("#password").passwordRules({forgotPassword: false, confirmPasswordSelector: "#confirmPassword", useTooltip: false});
    },
    resetPassword: function () {
        //this.postJSON(OPENIAM.ENV.ContextPath + "/rest/api/prov/resetPassword", this.toJSON());
        $.ajax({
            url: OPENIAM.ENV.ContextPath + "/rest/api/prov/resetPassword",
            data: JSON.stringify(this.toJSON()),
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            success: function (data, textStatus, jqXHR) {
                if (data.status == 200) {
                    OPENIAM.Modal.Success({message: data.successMessage, showInterval: 2000, onIntervalClose: function () {
                        if (data.redirectURL) {
                            if (data.redirectURL != null && data.redirectURL != undefined && data.redirectURL.length > 0) {
                                window.location.href = data.redirectURL;
                            } else {
                                window.location.reload(true);
                            }
                        }
                    }});
                } else {
                    OPENIAM.Modal.Error({html: OPENIAM.PasswordPolicy.getFromAjaxResponse(data)});
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
    },

    resyncPassword: function () {
        //this.postJSON(OPENIAM.ENV.ContextPath + "/rest/api/prov/resetPassword", this.toJSON());
        $.ajax({
            url: OPENIAM.ENV.ContextPath + "/rest/api/prov/resyncPassword",
            data: JSON.stringify(this.toJSON()),
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            success: function (data, textStatus, jqXHR) {
                if (data.status == 200) {
                    OPENIAM.Modal.Success({message: data.successMessage, showInterval: 2000, onIntervalClose: function () {
                        if (data.redirectURL) {
                            if (data.redirectURL != null && data.redirectURL != undefined && data.redirectURL.length > 0) {
                                window.location.href = data.redirectURL;
                            } else {
                                window.location.reload(true);
                            }
                        }
                    }});
                } else {
                    OPENIAM.Modal.Error({html: OPENIAM.PasswordPolicy.getFromAjaxResponse(data)});
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
    },
    toJSON: function () {
        var obj = {};

        obj.userId = OPENIAM.ENV.UserId;
        obj.password = $("#password").val();
        obj.confPassword = $("#confirmPassword").val();
        obj.principal = $("#principal").val();
        obj.managedSystemId = $("#managedSystemId").val();
        obj.notifyUserViaEmail = $("#notifyUserViaEmail").is(':checked');
        obj.autoGeneratePassword = $("#autoGeneratePassword").is(':checked');

        return obj;
    },

    postJSON: function (url, data, callback) {
        $.ajax({
            url: url,
            data: JSON.stringify(data),
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            success: function (data, textStatus, jqXHR) {
                if (data.status == 200) {
                    OPENIAM.Modal.Success({message: data.successMessage, showInterval: 2000, onIntervalClose: function () {
                        if (callback)
                            callback.call(data);
                        else if (data.redirectURL) {
                            if (data.redirectURL != null && data.redirectURL != undefined && data.redirectURL.length > 0) {
                                window.location.href = data.redirectURL;
                            } else {
                                window.location.reload(true);
                            }
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
    }
};

$(document).ready(function () {
    OPENIAM.User.ResetPassword.init();

    $("#resyncPasswordBtn").on('click', function () {
        OPENIAM.User.ResetPassword.resyncPassword();
        return false;
    })

    $("#resetPasswordForm").submit(function () {
        OPENIAM.User.ResetPassword.resetPassword();
        return false;
    });
});

$(window).load(function () {
});