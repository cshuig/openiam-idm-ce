OPENIAM = window.OPENIAM || {};
OPENIAM.ContentProvider = OPENIAM.ContentProvider || {};
OPENIAM.ContentProvider.Pattern = {
    deletePattern : function() {
        $.ajax({
            url : "deleteProviderPattern.html",
            data : JSON.stringify(OPENIAM.ENV.Pattern),
            type : "POST",
            dataType : "json",
            contentType : "application/json",
            success : function(data, textStatus, jqXHR) {
                if (data.status == 200) {
                    OPENIAM.Modal.Success({
                        message : data.successMessage,
                        showInterval : 2000,
                        onIntervalClose : function() {
                            window.location.href = "editContentProvider.html?providerId=" + OPENIAM.ENV.ProviderId;
                        }
                    });
                } else {
                    OPENIAM.Modal.Error({
                        errorList : data.errorList
                    });
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
    },
    savePattern : function() {
        this.updateJSON();
        $.ajax({
            url : "editProviderPattern.html",
            data : JSON.stringify(OPENIAM.ENV.Pattern),
            type : "POST",
            dataType : "json",
            contentType : "application/json",
            success : function(data, textStatus, jqXHR) {
                if (data.status == 200) {
                    OPENIAM.Modal.Success({
                        message : data.successMessage,
                        showInterval : 2000,
                        onIntervalClose : function() {
                            if (data.redirectURL != null && data.redirectURL != undefined && data.redirectURL.length > 0) {
                                window.location.href = data.redirectURL;
                            } else {
                                window.location.reload(true);
                            }
                        }
                    });
                } else {
                    OPENIAM.Modal.Error({
                        errorList : data.errorList
                    });
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
    },
    updateForm : function() {
        var obj = OPENIAM.ENV.Pattern;
        (obj.isPublic) ? $("#isPublicOn").attr("checked", "checked") : $("#isPublicOff").attr("checked", "checked");
        $("#pattern").val(obj.pattern);
        $("#themeId").val(obj.themeId);

    },
    updateJSON : function() {
        var obj = OPENIAM.ENV.Pattern;
        obj.isPublic = $("#isPublicOn").is(":checked");
        obj.pattern = $("#pattern").val();
        obj.themeId = $("#themeId").val();
        obj.groupingXrefs = $("#authenticationLevels").authenticationLevelTable("getValues");
        return obj;
    }
};

$(document).ready(function() {
    OPENIAM.ContentProvider.Pattern.updateForm();
    $("#deletePattern").click(function() {
        OPENIAM.Modal.Warn({
            message : localeManager["contentprovider.meta.delete"],
            buttons : true,
            OK : {
                text : localeManager["contentprovider.entitlements.uri.delete.yes"],
                onClick : function() {
                    OPENIAM.Modal.Close();
                    OPENIAM.ContentProvider.Pattern.deletePattern();
                }
            },
            Cancel : {
                text : localeManager["openiam.ui.common.cancel"],
                onClick : function() {
                    OPENIAM.Modal.Close();
                }
            }
        });
        return false;
    });

    $("#uriPatternForm").submit(function() {
        OPENIAM.ContentProvider.Pattern.savePattern();
        return false;
    });
});

$(window).load(function() {
    if (OPENIAM.ENV.ErrorMessage != null) {
        OPENIAM.Modal.Error(OPENIAM.ENV.ErrorMessage);
    }

    if (OPENIAM.ENV.SuccessMessage != null) {
        OPENIAM.Modal.Success({
            message : OPENIAM.ENV.SuccessMessage,
            showInterval : 1000,
            onIntervalClose : function() {
                window.location.href = "editProviderPattern.html?id=" + OPENIAM.ENV.PatternId + "&providerId=" + OPENIAM.ENV.ProviderId;
            }
        });
    }

    $("#authenticationLevels").authenticationLevelTable({
        groupings : OPENIAM.ENV.Pattern.orderedGroupingXrefs,
        emptyMessage : localeManager["contentprovider.meta.edit.no.auth.level"]
    });
});