OPENIAM = window.OPENIAM || {};
OPENIAM.Entity = {
    populate : function() {
        var obj = OPENIAM.ENV.Theme;
        $("#url").val(obj.url);
        $("#name").val(obj.name);
    },
    deleteEntity : function() {
        $.ajax({
            url : "deleteUITheme.html",
            data : {
                id : OPENIAM.ENV.ThemeId
            },
            type : "POST",
            dataType : "json",
            success : function(data, textStatus, jqXHR) {
                if (data.status == 200) {
                    OPENIAM.Modal.Success({
                        message : data.successMessage,
                        showInterval : 2000,
                        onIntervalClose : function() {
                            window.location.href = data.redirectURL;
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
    saveEntity : function() {
        this.updateObject();
        $.ajax({
            url : "editUITheme.html",
            data : JSON.stringify(OPENIAM.ENV.Theme),
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
    updateObject : function() {
        var obj = OPENIAM.ENV.Theme;
        obj.name = $("#name").val();
        obj.url = $("#url").val();
    }
};

$(document).ready(function() {
    $("#deleteEntity").click(function() {
        OPENIAM.Modal.Warn({
            message : localeManager['webconsole.ui.theme.edit.delete'],
            buttons : true,
            OK : {
                text : localeManager["webconsole.ui.theme.edit.delete.yes"],
                onClick : function() {
                    OPENIAM.Modal.Close();
                    OPENIAM.Entity.deleteEntity();
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

    $("#saveEntity").click(function() {
        OPENIAM.Entity.saveEntity();
        return false;
    });

    OPENIAM.Entity.populate();
});

$(window).load(function() {
});