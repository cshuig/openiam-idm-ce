OPENIAM = window.OPENIAM || {};
OPENIAM.Organization = window.OPENIAM.Organization || {};

OPENIAM.Organization.Form = {
	populate : function() {
        var obj = OPENIAM.ENV.Organization;


		$("#abbreviation").val(obj.abbreviation);
        $("#internalOrgId").val(obj.internalOrgId);
		$("#organizationName").val(obj.name);
		$("#symbol").val(obj.symbol);
		$("#description").val(obj.description);
		$("#organizationTypeId").val(obj.organizationTypeId);
		$("#isSelectable").attr("checked", obj.selectable);
		$("#alias").val(obj.alias);
		$("#domainName").val(obj.domainName);
		$("#ldapString").val(obj.ldapStr);
		$("#metadataType").selectableSearchResult({
            initialBeans : [{id : obj.mdTypeId, name : obj.metadataTypeName}],
            singleSearch : true,
            addMoreText : localeManager["openiam.ui.webconsole.meta.type.select.another"],
            noneSelectedText : localeManager["openiam.ui.webconsole.meta.type.select"],
            dialogWarnOnChange : (OPENIAM.ENV.Organization.id != null) ? {
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
        
        OPENIAM.ENV.Organization.attributes = (OPENIAM.ENV.Organization.attributes != null) ? OPENIAM.ENV.Organization.attributes : [];
        
        var modalFields = [
			{fieldName: "name", type:"text",label: localeManager["openiam.ui.common.attribute.name"], required : true},
            {fieldName: "metadataId", type:"select",label: localeManager["openiam.ui.metadata.element"], required:false, items : this._elements},
            {fieldName: "values", type:"multiText",label: localeManager["openiam.ui.common.attribute.values"], required:true}
		];
        
        $("#attributesContainer").attributeTableEdit({
			objectArray : OPENIAM.ENV.Organization.attributes,
			dialogModalFields : modalFields,
			fieldNames : ["name", "metadataName", "values"]
		});
	},
	deleteOrganization : function() {
		$.ajax({
			url : "deleteOrganization.html",
			data : {id : OPENIAM.ENV.Organization.id},
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
	saveOrganization : function() {
		this.updateObject();
		$.ajax({
			url : "editOrganization.html",
			data : JSON.stringify(OPENIAM.ENV.Organization),
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
	addOrRemove : function(args) {
		$.ajax({
			url : args.url,
			"data" : { organizationId : args.entityId, childOrganizationId : OPENIAM.ENV.Organization.id},
			type: "POST",
			dataType : "json",
			success : function(data, textStatus, jqXHR) {
				if(data.status == 200) {
					OPENIAM.Organization.Form.loadAllowedParentTypes($("#organizationTypeId").val());
				} else {
					OPENIAM.Modal.Error({errorList : data.errorList});
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
			}
		});
	},       
	drawParentOrganizations : function(allowedParetnsType) {
		$("#parentOrganizations").entitlemetnsTable({
			columnHeaders : [
				localeManager["openiam.ui.common.organization.name"],
               	localeManager["openiam.ui.common.organization.type"],
               	localeManager["openiam.ui.common.actions"]
			],
            columnsMap : ["name", "type"],
			ajaxURL : "rest/api/entitlements/getParentOrganizations",
			entityUrl : "editOrganization.html",
			entityURLIdentifierParamName : "id",
			requestParamIdName : "id",
			requestParamIdValue : OPENIAM.ENV.Organization.id,
			pageSize : 10,
			deleteOptions : {
				onDelete : function(bean) {
					OPENIAM.Organization.Form.addOrRemove({entityId : bean.id, url : "removeChildOrganization.html"});
				}
			},
			emptyResultsText : localeManager["openiam.ui.selfservice.organization.empty.parent"],
			theadInputElements : [
				"",
				"",
				$(document.createElement("input")).attr("type", "button").attr("value", localeManager["openiam.ui.shared.organization.search"]).addClass("redBtn").attr("id", "parentOrgSearch")
			],
			onAppendDone : function() {
				$("#parentOrgSearch").click(function() {
					$("#editDialog").organizationDialogSearch({
                        searchTargetElmt : "#parentOrganizationSearchResult",
                        organizationTypes: allowedParetnsType,
                        onAdd : function(bean) {
                        	OPENIAM.Organization.Form.addOrRemove({entityId : bean.id, url : "addChildOrganization.html"});
                        },
                        pageSize : 5,
                        showResultsInDialog:true
                    });
				});
			}
		});
	},
    loadAllowedParentTypes: function(typeId){
        if(typeId){
            $.ajax({
                url : "getAllowedParentOrganizationTypes.html",
                data : {orgTypeId: typeId},
                type: "POST",
                dataType : "json",
                success : function(data, textStatus, jqXHR) {
                    if(data !=null && (data.errorList==null || data.errorList.length==0 )) {
                        var allowedParetnsType=[];
                        for(var i=0; i< data.beans.length;i++){
                            var bean = data.beans[i];
                            allowedParetnsType.push(bean.id);
                        }
                        OPENIAM.Organization.Form.drawParentOrganizations(allowedParetnsType);
                    } else {
                        OPENIAM.Modal.Error({errorList : data.errorList});
                    }
                },
                error : function(jqXHR, textStatus, errorThrown) {
                    OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
                }
            });
        }
    },
    getSelectedItemsValues: function(optionList){
        var values = [];
        if(optionList!=null && optionList.length>0){
            for(var i=0; i<optionList.length;i++){
                values.push($(optionList[i]).data("entity").id);
            }
        }else{
            values =null;
        }
        return values;
    },
	updateObject : function() {
		var obj = OPENIAM.ENV.Organization;
		obj.abbreviation = $("#abbreviation").val();
		obj.name = $("#organizationName").val();
        obj.internalOrgId = $("#internalOrgId").val();
        obj.symbol = $("#symbol").val();
		obj.description = $("#description").val();
		obj.organizationTypeId = $("#organizationTypeId").val();
		obj.selectable = $("#isSelectable").is(":checked");
        obj.alias = $("#alias").val();
		obj.domainName = $("#domainName").val();
		obj.ldapStr = $("#ldapString").val();
		obj.mdTypeId = $("#metadataType").selectableSearchResult("getId");
	}
};

$(document).ready(function() {
	$("#deleteOrganization").click(function() {
		OPENIAM.Modal.Warn({ 
			message : localeManager["openiam.ui.shared.organization.delete.warning.message"], 
			buttons : true, 
			OK : {
				text : localeManager["openiam.ui.shared.organization.delete.confirmation"],
				onClick : function() {
					OPENIAM.Modal.Close();
					OPENIAM.Organization.Form.deleteOrganization();
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

	$("#organizationForm").submit(function() {
		OPENIAM.Organization.Form.saveOrganization();
		return false;
	});
	
	if(OPENIAM.ENV.Organization.id != null) {
		$("#organizationTypeId").change(function() {
			OPENIAM.Organization.Form.loadAllowedParentTypes($("#organizationTypeId").val());
		});
	}
	
	OPENIAM.Organization.Form.populate();
	OPENIAM.Organization.Form.loadAllowedParentTypes($("#organizationTypeId").val());
});

$(window).load(function() {
});