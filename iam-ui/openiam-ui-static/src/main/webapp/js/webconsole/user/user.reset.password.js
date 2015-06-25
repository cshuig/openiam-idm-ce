OPENIAM = window.OPENIAM || {};
OPENIAM.User = window.OPENIAM.User || {};
OPENIAM.activateFlag = false;
OPENIAM.User.ResetPassword = {
    init: function () {

        var $autoGenHidden = $("input:hidden.autoGeneratePassword");
        var $autoGenChbx = $("input:checkbox.autoGeneratePassword");
        if ($autoGenHidden.length > 0 && $autoGenHidden[0].value ||
            $autoGenChbx.length > 0 && $autoGenChbx[0].checked) {
            $(".passwordField").hide();
        }

        $autoGenChbx.change(function () {
            if (this.checked) {
                $(".passwordField").hide();
            } else {
                $(".passwordField").show();
            }
        });

        $("#password").passwordRules({
            forgotPassword: false,
            confirmPasswordSelector: "#confirmPassword", managedSystemId: OPENIAM.ENV.Login.managedSysId,
            useTooltip: false, loginValue: OPENIAM.ENV.Login.login
        });


    },
    resetPassword: function () {
        //this.postJSON(OPENIAM.ENV.ContextPath + "/rest/api/prov/resetPassword", this.toJSON());
        var selectedManagedSystems = $("#managedSystem").multiselect("getChecked").map(function () {
            return this.value;
        }).get();
        if (selectedManagedSystems.length == 0) {
            var data = {"errorList": [{"message": "Please select a managed system."}]}
            OPENIAM.Modal.Error({html: OPENIAM.PasswordPolicy.getFromAjaxResponse(data)});
            return false;
        } else {
            $.ajax({
                url: OPENIAM.ENV.ContextPath + "/rest/api/prov/resetPassword",
                data: JSON.stringify(this.toJSON()),
                type: "POST",
                dataType: "json",
                contentType: "application/json",
                success: function (data, textStatus, jqXHR) {
                    if (data.status == 200) {
                        OPENIAM.Modal.Success({
                            message: data.successMessage, showInterval: 2000, onIntervalClose: function () {
                                if (data.redirectURL) {
                                    if (data.redirectURL != null && data.redirectURL != undefined && data.redirectURL.length > 0) {
                                        window.location.href = data.redirectURL;
                                    } else {
                                        window.location.reload(true);
                                    }
                                }
                            }
                        });
                    } else {
                        OPENIAM.Modal.Error({html: OPENIAM.PasswordPolicy.getFromAjaxResponse(data)});
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
                }
            });
        }
    },

    resyncPassword: function () {
        //this.postJSON(OPENIAM.ENV.ContextPath + "/rest/api/prov/resetPassword", this.toJSON());
        var selectedManagedSystems = $("#managedSystem").multiselect("getChecked").map(function () {
            return this.value;
        }).get();
        if (selectedManagedSystems.length == 0) {
            var data = {"errorList": [{"message": "Please select a managed system."}]}
            OPENIAM.Modal.Error({html: OPENIAM.PasswordPolicy.getFromAjaxResponse(data)});
            return false;
        } else {
            $.ajax({
                url: OPENIAM.ENV.ContextPath + "/rest/api/prov/resyncPassword",
                data: JSON.stringify(this.toJSON()),
                type: "POST",
                dataType: "json",
                contentType: "application/json",
                success: function (data, textStatus, jqXHR) {
                    if (data.status == 200) {
                        OPENIAM.Modal.Success({
                            message: data.successMessage, showInterval: 2000, onIntervalClose: function () {
                                if (data.redirectURL) {
                                    if (data.redirectURL != null && data.redirectURL != undefined && data.redirectURL.length > 0) {
                                        window.location.href = data.redirectURL;
                                    } else {
                                        window.location.reload(true);
                                    }
                                }
                            }
                        });
                    } else {
                        OPENIAM.Modal.Error({html: OPENIAM.PasswordPolicy.getFromAjaxResponse(data)});
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
                }
            });
        }
    },
    toJSON: function () {
        var obj = {};
        var selectedManagedSystems = $("#managedSystem").multiselect("getChecked").map(function () {
            return this.value;
        }).get();
        obj.principal = $("#principal").val();
        obj.userId = OPENIAM.ENV.UserId;
        obj.password = $("#password").val();
        obj.confPassword = $("#confirmPassword").val();
        obj.managedSystem = selectedManagedSystems;
        obj.notifyUserViaEmail = $("input:checkbox.notifyUserViaEmail").is(':checked') || $("input:hidden.notifyUserViaEmail").val();
        obj.autoGeneratePassword = $("input:checkbox.autoGeneratePassword").is(':checked') || $("input:hidden.autoGeneratePassword").val();
        obj.userActivateFlag = OPENIAM.activateFlag;

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
                    OPENIAM.Modal.Success({
                        message: data.successMessage, showInterval: 2000, afterClose: function () {
                            if (callback)
                                callback.call(data);
                            else if (data.redirectURL) {
                                if (data.redirectURL != null && data.redirectURL != undefined && data.redirectURL.length > 0) {
                                    window.location.href = data.redirectURL;
                                } else {
                                    window.location.reload(true);
                                }
                            }
                        }
                    });
                } else {
                    OPENIAM.Modal.Error({errorList: data.errorList});
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
    },

    initManagedSystems: function () {
        var $managedSystem = $("#managedSystem");
        $managedSystem.find('option').remove().end();
        $.each(OPENIAM.ENV.ManagedSystems, function (key, bean) {
            var option = $(document.createElement("option"));
            option.val(key).text(bean.name);
            $managedSystem.append(option);
        });
        if (OPENIAM.ENV.ResetPasswordManagedSystems && OPENIAM.ENV.ResetPasswordManagedSystems.length > 0) {
            $.each(OPENIAM.ENV.ResetPasswordManagedSystems, function (i, item) {
                $managedSystem.multiselect({height: 100}).multiselect("widget").find(":checkbox[value='" + item + "']").each(function(){
                    this.click();
                });
            });
        } else {
            $managedSystem.multiselect({height: 100}).multiselect("checkAll");
        }
    }

};

$(document).ready(function () {
    OPENIAM.User.ResetPassword.init();

    $('.hideShowPassword-field').hidePassword(true);

    $("#resyncPasswordBtn").on('click', function (event) {
        OPENIAM.User.ResetPassword.resyncPassword();
        $(".passwordBlock").hide();
        return false;
    });

    $("#resetPasswordForm").submit(function (event) {
        $(".passwordBlock").hide();
        if (OPENIAM.ENV.UserSecondaryStatus &&
            (OPENIAM.ENV.UserSecondaryStatus == "DISABLED" ||
            OPENIAM.ENV.UserSecondaryStatus == "INACTIVE" ||
            OPENIAM.ENV.UserSecondaryStatus == "LOCKED" )) {
            OPENIAM.Modal.Warn({
                message: localeManager["openiam.ui.webconsole.user.account.reset.warn"],
                buttons: true,
                OK: {
                    text: localeManager["openiam.ui.common.yes"],
                    onClick: function () {
                        OPENIAM.Modal.Close();
                        OPENIAM.activateFlag = true;
                        OPENIAM.User.ResetPassword.resetPassword();
                    }
                },
                No: {
                    text: localeManager["openiam.ui.common.no"],
                    onClick: function () {
                        OPENIAM.Modal.Close();
                        OPENIAM.User.ResetPassword.resetPassword();
                    }
                }
            });
        } else {
            OPENIAM.User.ResetPassword.resetPassword();
        }//
        return false;
    });

    OPENIAM.User.ResetPassword.initManagedSystems();
});

$(window).load(function () {
});