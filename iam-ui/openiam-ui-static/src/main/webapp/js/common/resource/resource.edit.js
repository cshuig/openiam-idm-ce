OPENIAM = window.OPENIAM || {};
OPENIAM.Resource = window.OPENIAM.Resource || {};

OPENIAM.Resource.Form = {
	populate : function() {
		var obj = OPENIAM.ENV.Resource;
		if(obj.resourceType != null) {
			$("#resourceType").val(obj.resourceType.id).attr("disabled", "disabled");
		}
		$("#resourceName").val(obj.name);
        $("#risk").val(obj.risk);
		$("#description").val(obj.description);
		$("#resourceURL").val(obj.url);
		$("#coorelatedName").val(obj.coorelatedName);
		$("#metadataType").selectableSearchResult({
			initialBeans : [{id : obj.mdTypeId, name : obj.metadataTypeName}],
			singleSearch : true, 
			addMoreText : localeManager["openiam.ui.webconsole.meta.type.select.another"], 
			noneSelectedText : localeManager["openiam.ui.webconsole.meta.type.select"],
			dialogWarnOnChange : (OPENIAM.ENV.ResourceId != null) ? {
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
		var fieldName = null;
		if (OPENIAM.ENV.MngSysProps) {
            fieldName = {fieldName: "name", type:"select", label: localeManager["openiam.ui.common.attribute.name"], required : true, items : OPENIAM.ENV.MngSysProps};
        } else {
            fieldName = {fieldName: "name", type:"text", label: localeManager["openiam.ui.common.attribute.name"], required : true};
        }
		var modalFields = [
            fieldName,
			{fieldName: "metadataId", type:"select",label: localeManager["openiam.ui.metadata.element"], required:false},
		    {fieldName: "value", type:"text",label:localeManager["openiam.ui.common.attribute.value"], required:true}
		];

        if (OPENIAM.ENV.Resource != null) {
		    OPENIAM.ENV.Resource.resourceProps = (OPENIAM.ENV.Resource.resourceProps != null) ? OPENIAM.ENV.Resource.resourceProps : [];
            $("#attributesContainer").attributeTableEdit({
                objectArray : OPENIAM.ENV.Resource.resourceProps,
                dialogModalFields : modalFields,
                fieldNames : ["name", "metadataName", "value"]
            });
        }
	},
	deleteResource : function() {
		$.ajax({
			url : "deleteResource.html",
			data : {id : OPENIAM.ENV.ResourceId},
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
	saveResoruce : function() {
		this.updateObject();
		$.ajax({
			url : "editResource.html",
			data : JSON.stringify(OPENIAM.ENV.Resource),
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
		var obj = OPENIAM.ENV.Resource;
		obj.resourceType = (obj.resourceType == null) ? {} : obj.resourceType;
		obj.resourceType.id = $("#resourceType").val();
		obj.name = $("#resourceName").val();
		obj.description = $("#description").val();
		obj.url = $("#resourceURL").val();
		obj.coorelatedName = $("#coorelatedName").val();
		var val = $("#risk").val();
		obj.risk = (val)?val:null;
		obj.mdTypeId = $("#metadataType").selectableSearchResult("getId");
	}
};

$(document).ready(function() {
	$("#deleteResoruce").click(function() {
		OPENIAM.Modal.Warn({ 
			message : localeManager["openiam.ui.shared.resource.delete.warning"], 
			buttons : true, 
			OK : {
				text : localeManager["openiam.ui.shared.resource.delete.confirmation"],
				onClick : function() {
					OPENIAM.Modal.Close();
					OPENIAM.Resource.Form.deleteResource();
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
	
	$("#resourceForm").submit(function() {
		OPENIAM.Resource.Form.saveResoruce();
		return false;
	});
	
	OPENIAM.Resource.Form.populate();
});

$(window).load(function() {
});