OPENIAM = window.OPENIAM || {};
OPENIAM.Group = window.OPENIAM.Group || {};

OPENIAM.Group.Form = {
	/*
	_initAttributes : function() {
        var $that = this;
		var emailModalFields = [
			{fieldName: "name", type:"text",label: localeManager["openiam.ui.common.attribute.name"], required : true},
            {fieldName: "metadataId", type:"select",label: localeManager["openiam.ui.metadata.element"], required:false, items : this._elements},
            {fieldName: "values", type:"multiText",label: localeManager["openiam.ui.common.attribute.values"], required:true}
		];
		
		if($("#attributesContainer").length > 0) {
			$("#attributesContainer").persistentTable({
				emptyMessage : localeManager["openiam.ui.shared.group.attributes.empty"],
				createEnabled : true,
				objectArray : OPENIAM.ENV.Group.attributes,
				createBtnId : "newAttribute",
                headerFields : [
                	localeManager["openiam.ui.common.attribute.name"],
                    localeManager["openiam.ui.metadata.element"],
                	localeManager["openiam.ui.common.attribute.values"]
                ],
                fieldNames : ["name", "metadataName", "values"],
				deleteEnabledField : true,
				editEnabledField : true,
				createText : localeManager["openiam.ui.common.attributes.create.new"],
				actionsColumnName : localeManager["openiam.ui.common.actions"],
				tableTitle : localeManager["openiam.ui.common.attributes"],
				onDeleteClick : function(obj) {
					
				},
				equals : function(obj1, obj2) {
					return obj1.name == obj2.name;
				},
				onEditClick : function(obj) {
					var $this = this;
					$("#editDialog").modalEdit({
						fields: emailModalFields,
			            dialogTitle: localeManager["openiam.ui.common.attributes.edit"],
			            onSubmit: function(bean){
			            	obj.name = bean.name;
			            	obj.value = bean.value;
                            obj.metadataId = bean.metadataId;
                            obj.metadataName = $that.getElementName(bean.metadataId);
                            obj.isMultivalued = bean.isMultivalued;
                            obj.values = bean.values;
			            	$("#editDialog").modalEdit("hide");
			            	$this.persistentTable("draw");
			            }
	                });
					$("#editDialog").modalEdit("show", obj);
				},
				onCreateClick : function() {
					var $this = this;
					var obj = {};
					$("#editDialog").modalEdit({
						fields: emailModalFields,
			            dialogTitle: localeManager["openiam.ui.common.attributes.new"],
			            onSubmit: function(bean){
                            bean.metadataName = $that.getElementName(bean.metadataId);
			            	$("#editDialog").modalEdit("hide");
			            	$this.persistentTable("addObject", bean)
			            	$this.persistentTable("draw");
			            }
	                });
					$("#editDialog").modalEdit("show", obj);
				}
			});
		}
	},
	*/
	populate : function() {
		var obj = OPENIAM.ENV.Group;
		$("#groupName").val(obj.name);
		$("#description").val(obj.description);
		$("#managedSysId").val(obj.managedSysId);
		//$("#organization").val(obj.companyId);
		$("#organization").selectableSearchResult({
			initialBeans : [{id : obj.companyId, name : obj.companyName}], 
			singleSearch : true, 
			addMoreText : localeManager["openiam.ui.common.organization.select.another"], 
			noneSelectedText : localeManager["openiam.ui.common.organization.select"],
			onClick : function($that) {
				$("#editDialog").organizationDialogSearch({
					showResultsInDialog : true,
					searchTargetElmt : "#editDialog",
					onAdd : function(bean) {
						$that.selectableSearchResult("add", bean);
						$("#editDialog").dialog("close");
					},
					pageSize : 5
				});
			}
		});
        $("#metadataType").selectableSearchResult({
            initialBeans : [{id : obj.mdTypeId, name : obj.metadataTypeName}],
            singleSearch : true,
            addMoreText : localeManager["openiam.ui.webconsole.meta.type.select.another"],
            noneSelectedText : localeManager["openiam.ui.webconsole.meta.type.select"],
            dialogWarnOnChange : (OPENIAM.ENV.GroupId != null) ? {
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
        
        OPENIAM.ENV.Group.attributes = (OPENIAM.ENV.Group.attributes != null) ? OPENIAM.ENV.Group.attributes : [];
        
        var modalFields = [
			{fieldName: "name", type:"text",label: localeManager["openiam.ui.common.attribute.name"], required : true},
            {fieldName: "metadataId", type:"select",label: localeManager["openiam.ui.metadata.element"], required:false, items : this._elements},
            {fieldName: "values", type:"multiText",label: localeManager["openiam.ui.common.attribute.values"], required:true}
		];
        
        $("#attributesContainer").attributeTableEdit({
			objectArray : OPENIAM.ENV.Group.attributes,
			dialogModalFields : modalFields,
			fieldNames : ["name", "metadataName", "values"]
		});
	},
	deleteGroup : function() {
		$.ajax({
			url : "deleteGroup.html",
			data : {id : OPENIAM.ENV.Group.id},
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
	saveGroup : function() {
		this.updateObject();
		$.ajax({
			url : "saveGroup.html",
			data : JSON.stringify(OPENIAM.ENV.Group),
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
		var obj = OPENIAM.ENV.Group;
		obj.name = $("#groupName").val();
		obj.description = $("#description").val();
		obj.managedSysId = $("#managedSysId").val();
		obj.companyId = $("#organization").selectableSearchResult("getId");
        obj.mdTypeId = $("#metadataType").selectableSearchResult("getId");
	}
};

$(document).ready(function() {
	$("#deleteGroup").click(function() {
		OPENIAM.Modal.Warn({ 
			message : localeManager["openiam.ui.shared.group.delete.warning"], 
			buttons : true, 
			OK : {
				text : localeManager["openiam.ui.shared.group.delete.confirmation"],
				onClick : function() {
					OPENIAM.Modal.Close();
					OPENIAM.Group.Form.deleteGroup();
				}
			},
			Cancel : {
				text : localeManager["openiam.ui.button.cancel"],
				onClick : function() {
					OPENIAM.Modal.Close();
				}
			}
		});
		return false;
	});
	
	$("#groupForm").submit(function() {
		OPENIAM.Group.Form.saveGroup();
		return false;
	});
	
	OPENIAM.Group.Form.populate();
});

$(window).load(function() {
});