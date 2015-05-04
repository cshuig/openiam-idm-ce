OPENIAM = window.OPENIAM || {};
OPENIAM.ChangePasswordMsys = {
    init: function () {

        $("#currentPassword").keyup(function () {
            $("#currentPasswordHidden").val($(this).val());
        });

        $("#newPassword").keyup(function () {
            $("#newPasswordHidden").val($(this).val());
        });

        $("#newPasswordConfirm").keyup(function () {
            $("#newPasswordConfirmHidden").val($(this).val());
        });

        $("#managedSystemId").on('change', function () {
            $("#newPassword").passwordRules({
                forgotPassword: false,
                loginSelector: '#managedSystemId', managedSystemId: $('#managedSystemId').find(":selected").val(),
                loginValue: $('#managedSystemId').find(":selected").attr("loginValue")
            });
        });


        $("#newPassword").passwordRules({
            forgotPassword: false,
            loginSelector: '#managedSystemId', managedSystemId: $('#managedSystemId').find(":selected").val(),
            loginValue: $('#managedSystemId').find(":selected").attr("loginValue")
        });
    },
    post: function () {
        $.ajax({
            "url": OPENIAM.ENV.ContextPath + "/rest/api/prov/resetPasswordSelfService",
            "data": JSON.stringify(OPENIAM.ChangePasswordMsys.toJson()),
            contentType: "application/json",
            type: "POST",
            dataType: "json",
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
    },
    toJson: function () {
        var obj = {};
        obj.principal = $('#managedSystemId').find(":selected").attr("loginValue");
        obj.userId = $('#userId').val();
        obj.currentPassword = $('#currentPassword').val();
        obj.password = $('#newPassword').val();
        obj.confPassword = $('#newPasswordConfirm').val();
        var managedSystem = [];
        managedSystem.push($('#managedSystemId').find(":selected").val())
        obj.managedSystem = managedSystem;
        obj.managedSystemId = $('#managedSystemId').find(":selected").val();
        obj.notifyUserViaEmail = false;
        obj.autoGeneratePassword = false;
        return obj;
    }
};


$(document).ready(function () {
    OPENIAM.ChangePasswordMsys.init();
    $("#login-form").submit(function () {
        OPENIAM.ChangePasswordMsys.post();
        return false;
    });
});