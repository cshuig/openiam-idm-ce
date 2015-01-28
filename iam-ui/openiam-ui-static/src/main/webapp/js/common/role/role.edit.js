OPENIAM = window.OPENIAM || {};
OPENIAM.Role = window.OPENIAM.Role || {};

OPENIAM.Role.Form = {
	populate : function() {
		var obj = OPENIAM.ENV.Role;
		$("#roleName").val(obj.name );
		$("#description").val(obj.description);
		$("#managedSysId").val(obj.managedSysId);
		$("#metadataType").selectableSearchResult({
            initialBeans : [{id : obj.mdTypeId, name : obj.metadataTypeName}],
            singleSearch : true,
            addMoreText : localeManager["openiam.ui.webconsole.meta.type.select.another"],
            noneSelectedText : localeManager["openiam.ui.webconsole.meta.type.select"],
            dialogWarnOnChange : (OPENIAM.ENV.Role.id != null) ? {
                addWarning : localeManager["openiam.ui.webconsole.meta.type.change.warn"],
                deleteWarning : localeManager["openiam.ui.webconsole.meta.type.delete.warn"],
                okText : localeManager["openiam.ui.common.yes"],
                cancelText : localeManager["openiam.ui.common.cancel"]
            } : null,
            onClick : function($that) {
                $("#editDialog").metadataTypeDialogSearch({
                    showResultsInDialog : true,
                    searchTargetElmt : "#editDialog",
                    onAdd : function(bean) {
                        $that.selectableSearchResult("add", bean);
                        $("#editDialog").dialog("close");
                    },
                    pageSize : 5
                });
            },
            onAdd : function(initializing) {
                if(!initializing) {
                    $("#attributesContainer").attributeTableEdit("onChange");
                }
            },
            onRemove : function() {
                $("#attributesContainer").attributeTableEdit("onChange");
            }
        });
        
        OPENIAM.ENV.Role.roleAttributes = (OPENIAM.ENV.Role.roleAttributes != null) ? OPENIAM.ENV.Role.roleAttributes : [];
        
        var modalFields = [
			{fieldName: "name", type:"text",label: localeManager["openiam.ui.common.attribute.name"], required : true},
            {fieldName: "metadataId", type:"select",label: localeManager["openiam.ui.metadata.element"], required:false, items : this._elements},
            {fieldName: "values", type:"multiText",label: localeManager["openiam.ui.common.attribute.values"], required:true}
		];
        
        $("#attributesContainer").attributeTableEdit({
			objectArray : OPENIAM.ENV.Role.roleAttributes,
			dialogModalFields : modalFields,
			fieldNames : ["name", "metadataName", "values"]
		});
	},
	deleteRole : function() {
		$.ajax({
			url : "deleteRole.html",
			data : {id : OPENIAM.ENV.Role.id},
			type: "POST",
			dataType : "json",
			success : function(data, textStatus, jqXHR) {
				if(data.status == 200) {
					OPENIAM.Modal.Success({message : data.successMessage, showInterval : 2000, onIntervalClose : function() {
						window.location.href = data.redirectURL;
					}});
				} else {
					OPENIAM.Modal.Error({errorList : data.errorList});
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
			}
		});
	},
	saveRole : function() {
		this.updateObject();
		$.ajax({
			url : "saveRole.html",
			data : JSON.stringify(OPENIAM.ENV.Role),
			type: "POST",
			dataType : "json",
			contentType: "application/json",
			success : function(data, textStatus, jqXHR) {
				if(data.status == 200) {
					OPENIAM.Modal.Success({message : data.successMessage, showInterval : 2000, onIntervalClose : function() {
						if(data.redirectURL != null && data.redirectURL != undefined && data.redirectURL.length > 0) {
							window.location.href = data.redirectURL;
						} else {
							window.location.reload(true);
						}
					}});
				} else {
					OPENIAM.Modal.Error({errorList : data.errorList});
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
			}
		});
	},
	updateObject : function() {
		var obj = OPENIAM.ENV.Role;
		obj.name = $("#roleName").val();
		obj.description = $("#description").val();
		obj.managedSysId = $("#managedSysId").val();
		obj.mdTypeId = $("#metadataType").selectableSearchResult("getId");
	}
};

$(document).ready(function() {
	$("#deleteRole").click(function() {
		OPENIAM.Modal.Warn({ 
			message : localeManager["openiam.ui.shared.role.delete.warning.message"], 
			buttons : true, 
			OK : {
				text : localeManager["openiam.ui.shared.role.delete.confirmation"],
				onClick : function() {
					OPENIAM.Modal.Close();
					OPENIAM.Role.Form.deleteRole();
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
	
	$("#roleForm").submit(function() {
		OPENIAM.Role.Form.saveRole();
		return false;
	});
	
	OPENIAM.Role.Form.populate();
});

$(window).load(function() {
});