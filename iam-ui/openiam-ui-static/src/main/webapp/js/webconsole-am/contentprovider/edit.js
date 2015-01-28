OPENIAM = window.OPENIAM || {};
OPENIAM.ContentProvider = {
    deleteProvider : function() {
        $.ajax({
            url : "deleteContentProvider.html",
            data : {
                id : OPENIAM.ENV.ProviderId
            },
            type : "POST",
            dataType : "json",
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
    applyDefaultPatterns : function() {
		$.ajax({
            url : "createDefaultURIPatterns.html",
            data : {id : OPENIAM.ENV.ContentProvider.id},
            type : "POST",
            dataType : "json",
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
    saveContentProvider : function() {
        this.updateJSON();
        $.ajax({
            url : "editContentProvider.html",
            data : JSON.stringify(OPENIAM.ENV.ContentProvider),
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
        var obj = OPENIAM.ENV.ContentProvider;
        $("#managedSysId").val(obj.managedSysId);
        $("#name").val(obj.name);
        $("#url").val(obj.url);
        $("#domainPattern").val(obj.domainPattern);
        $("#themeId").val(obj.themeId);
        $("#isSSL").val(obj.isSSL + "");
        if (obj.isPublic) {
            $("#cpIsPublicOn").attr("checked", "checked");
        } else {
            $("#cpIsPublicOff").attr("checked", "checked");
        }
        if(obj.showOnApplicationPage) {
        	$("#showOnApplicationPageOn").attr("checked", "checked");
        } else {
        	$("#showOnApplicationPageOff").attr("checked", "checked");
        }
    },
    updateJSON : function() {
        var obj = OPENIAM.ENV.ContentProvider;
        obj.managedSysId = $("#managedSysId").val();
        obj.name = $("#name").val();
        obj.url = $("#url").val();
        obj.domainPattern = $("#domainPattern").val();
        obj.themeId = $("#themeId").val();
        obj.isSSL = $("#isSSL").val();
        obj.isPublic = $("#cpIsPublicOn").is(":checked") ? true : false;
        obj.showOnApplicationPage = $("#showOnApplicationPageOn").is(":checked") ? true : false;
        obj.groupingXrefs = $("#authenticationLevels").authenticationLevelTable("getValues");
    }
};

$(document).ready(function() {
    OPENIAM.ContentProvider.updateForm();
    $("#deleteProvider").click(function() {
        OPENIAM.Modal.Warn({
            message : localeManager["contentprovider.edit.delete"],
            buttons : true,
            OK : {
                text : localeManager["contentprovider.edit.yes.delete"],
                onClick : function() {
                    OPENIAM.Modal.Close();
                    OPENIAM.ContentProvider.deleteProvider();
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

    $("#contentProviderForm").submit(function() {
        OPENIAM.ContentProvider.saveContentProvider();
        return false;
    });

    $("#authenticationLevels").authenticationLevelTable({
        groupings : OPENIAM.ENV.ContentProvider.orderedGroupingXrefs
    });
});

$(window).load(function() {
    /*
     * if(OPENIAM.ENV.SuccessMessage != null) { OPENIAM.Modal.Success({message :
     * OPENIAM.ENV.SuccessMessage, showInterval : 1000, onIntervalClose :
     * function() { window.location.href =
     * "editContentProvider.html?providerId=" + OPENIAM.ENV.ProviderId; }}); }
     */
});