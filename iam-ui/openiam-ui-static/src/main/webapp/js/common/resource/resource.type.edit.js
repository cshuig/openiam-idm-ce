OPENIAM = window.OPENIAM || {};
OPENIAM.ResourceType = window.OPENIAM.ResourceType || {};

OPENIAM.ResourceType.Form = {
	populate : function() {
		var obj = OPENIAM.ENV.ResourceType;
		$("#description").val(obj.description);
    	$("#provisionResource").val(obj.provisionResource);
    	$("#processName").val(obj.processName);
    	$("#supportsHierarchy").prop("checked", obj.supportsHierarchy);
    	$("#searchable").prop("checked", obj.searchable);
    	$("#displayNameMap").languageAdmin({ bean : obj, beanKey : "displayNameMap"});
	},
    deleteResource : function() {
        $.ajax({
            url : "deleteResourceType.html",
            data : {
                id : $("#resourceTypeId").val()
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
    saveResourceType : function() {
    	this.toJSON();
        $.ajax({
            url : "editResourceType.html",
            data : JSON.stringify(OPENIAM.ENV.ResourceType),
            cache : false,
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
    toJSON : function() {
    	var obj = OPENIAM.ENV.ResourceType;
    	obj.description = $("#description").val();
    	obj.provisionResource = $("#provisionResource").val();
    	obj.processName = $("#processName").val();
    	obj.supportsHierarchy = $("#supportsHierarchy").is(":checked");
    	obj.searchable = $("#searchable").is(":checked");
    	obj.displayNameMap = $("#displayNameMap").languageAdmin("getMap");
    },
    // using jquery.form.js
    uploadIcon : function() {
        $("#uploadForm").ajaxForm({
            success : function(data) {

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

                // $("#resourceTypeURL").val(data);
                // $("#iconImage").attr('src', '/openiam-ui-static/' + data);
            },
            dataType : "json"
        }).submit();
    }
};

$(document).ready(function() {
	OPENIAM.ResourceType.Form.populate();
    $("#deleteResoruce").click(function() {
        OPENIAM.Modal.Warn({
            message : OPENIAM.ENV.Text.DeleteWarn,
            buttons : true,
            OK : {
                text : localeManager["openiam.common.type.edit.delete.confirm"],
                onClick : function() {
                    OPENIAM.Modal.Close();
                    OPENIAM.ResourceType.Form.deleteResource();
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

    $("#saveButton").on('click', function() {
        OPENIAM.ResourceType.Form.saveResourceType();
        return false;
    });
});

$(window).load(function() {
});