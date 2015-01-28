OPENIAM = window.OPENIAM || {};
OPENIAM.Metadata = {
    init : function() {
        $("#saveBtn").click(function() {
            OPENIAM.Metadata.save();
            return false;
        });

        $("#deleteBtn").click(function() {
            OPENIAM.Metadata.deleteBean();
            return false;
        });
        this.populatePage();
    },
    populatePage : function() {
    	var obj = OPENIAM.ENV.MetadataElement;
    	$("#attributeName").val(obj.attributeName);
    	$("#metadataTypeId").val(obj.metadataTypeId);
    	$("#description").val(obj.description);
    	$("#dataType").val(obj.dataType);
    	$("#staticDefaultValue").val(obj.staticDefaultValue);
    	$("#auditable").prop("checked", obj.auditable);
    	$("#required").prop("checked", obj.required);
    	$("#selfEditable").prop("checked", obj.selfEditable);
    	$("#isPublic").prop("checked", obj.isPublic);
    	if(obj.metadataTypeId != null && obj.metadataTypeId != undefined) {
    		$("#metadataTypeId").prop("disabled", true);	
    	}
    	$("#languageMap").languageAdmin({ bean : OPENIAM.ENV.MetadataElement, beanKey : "languageMap"});
    },
    toJSON : function() {
    	var obj = OPENIAM.ENV.MetadataElement;
    	obj.attributeName = $("#attributeName").val();
    	obj.metadataTypeId = $("#metadataTypeId").val();
    	obj.description = $("#description").val();
    	obj.dataType = $("#dataType").val();
    	obj.staticDefaultValue = $("#staticDefaultValue").val();
    	obj.auditable = $("#auditable").is(":checked");
    	obj.required = $("#required").is(":checked");
    	obj.selfEditable = $("#selfEditable").is(":checked");
    	obj.isPublic = $("#isPublic").is(":checked");
    	obj.languageMap = $("#languageMap").languageAdmin("getMap");
    },
    save : function() {
    	this.toJSON();
        $.ajax({
            url : "metaDataEdit.html",
            data : JSON.stringify(OPENIAM.ENV.MetadataElement),
            type : "POST",
            dataType : "json",
            contentType : "application/json",
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
    deleteBean : function() {
        $.ajax({
            url : "metadataDelete.html",
            data : {
                id : OPENIAM.ENV.MetadataElement.id
            },
            type : "POST",
            dataType : "json",
            success : function(data, textStatus, jqXHR) {
                if (data.status == 200) {
                    OPENIAM.Modal.Success({
                        message : data.successMessage,
                        showInterval : 2000,
                        onIntervalClose : function() {
                            window.location.href = "/webconsole/metaDataTypeEdit.html?OPENIAM_MENU_ID=META_TYPE_EDIT&id=" + data.contextValues.mdTypeId;
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
    }
};

$(document).ready(function() {
    OPENIAM.Metadata.init();
});

$(window).load(function() {

});