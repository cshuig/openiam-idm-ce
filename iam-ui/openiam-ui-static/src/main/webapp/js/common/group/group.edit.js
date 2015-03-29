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
		$("#groupTypeId").val(obj.mdTypeId);
		$("#groupType").val(obj.metadataTypeName);

		$("#groupName").val(obj.name);
		$("#description").val(obj.description);
		$("#managedSysId").val(obj.managedSysId);
		$("#groupMaxUserCount").val(obj.maxUserNumber);
		$("#groupMembershipDuration").val(obj.membershipDuration);

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

		OPENIAM.Group.Form.initMetadataSearchDialog({targetElement:"#groupClassification",
													initialDataId:obj.classificationId,
													initialDataName:obj.classificationName,
													selectAnotherLabel:localeManager["openiam.ui.group.classification.select.another"],
													selectLabel:localeManager["openiam.ui.group.classification.select"],
													metadataGrouping:"GROUP_CLASSIFICATION" });

		OPENIAM.Group.Form.initMetadataSearchDialog({targetElement:"#groupADType",
													initialDataId:obj.adGroupTypeId,
													initialDataName:obj.adGroupTypeName,
													selectAnotherLabel:localeManager["openiam.ui.group.ad.type.select.another"],
													selectLabel:localeManager["openiam.ui.group.ad.type.select"],
													metadataGrouping:"AD_GROUP_TYPE",
													showAllGroupings:true});

		OPENIAM.Group.Form.initMetadataSearchDialog({targetElement:"#groupADScope",
													initialDataId:obj.adGroupScopeId,
													initialDataName:obj.adGroupScopeName,
													selectAnotherLabel:localeManager["openiam.ui.group.ad.scope.select.another"],
													selectLabel:localeManager["openiam.ui.group.ad.scope.select"],
													metadataGrouping:"AD_GROUP_SCOPE",
													showAllGroupings:true });

		OPENIAM.Group.Form.initMetadataSearchDialog({targetElement:"#groupRisk",
													initialDataId:obj.riskId,
													initialDataName:obj.riskName,
													selectAnotherLabel:localeManager["openiam.ui.common.risk.select.another"],
													selectLabel:localeManager["openiam.ui.common.risk.select"],
													metadataGrouping:"RISK",
													showAllGroupings:true });

		OPENIAM.Group.Form.initGroupSearchDialog({targetElement:"#groupParent",
													selectAnotherLabel:localeManager["openiam.ui.common.group.add.another"],
													selectLabel:localeManager["openiam.ui.shared.group.search"]});

		$("#selectGroupOwner").click(function(){
			OPENIAM.Modal.Warn({
				title : localeManager["openiam.ui.group.owner.type.to.select"],
				buttons : true,
				OK : {
					text : localeManager["openiam.ui.common.user"],
					onClick : function() {
						OPENIAM.Modal.Close();
						$("#editDialog").userSearchForm(
							{
								afterFormAppended : function() {
									$("#editDialog").dialog({
										autoOpen : false,
										draggable : false,
										resizable : false,
										title : localeManager["openiam.ui.common.search.users"],
										width : "auto",
										position : "center"
									});
									$("#editDialog").dialog("open");
								},
								onSubmit : function(json) {
									$("#userResultsArea").userSearchResults(
										{
											"jsonData" : json,
											"page" : 0,
											"size" : 20,
											initialSortColumn : "name",
											initialSortOrder : "ASC",
											url : "rest/api/users/search",
											emptyFormText : localeManager["openiam.ui.common.user.search.empty"],
											emptyResultsText : localeManager["openiam.ui.common.user.search.no.results"],
											onAppendDone : function() {
												$("#editDialog").dialog("close");
												$("#userResultsArea").prepend("<div class=\"\">" + localeManager["openiam.ui.user.supervisor.table.description"] + "</div>")
													.dialog({
														autoOpen : true,
														draggable : false,
														resizable : false,
														title : localeManager["openiam.ui.user.search.result.title"],
														width : "auto"
													})
											},
											onEntityClick : function(bean) {
												$("#userResultsArea").dialog("close");
												//$("#groupOwnerId").val(bean.id);
												//$("#groupOwner").val(localeManager["openiam.ui.common.user"] +" - " + bean.name);
												OPENIAM.Group.Form.addOwner("user", bean);
											}
										});
								}
							});
					}
				},
				No : {
					text : localeManager["openiam.ui.common.group"],
					className:"redBtn",
					onClick : function() {
						OPENIAM.Modal.Close();
						$("#editDialog").groupDialogSearch({
							searchTargetElmt : "#editDialog",
							showResultsInDialog : true,
							onSearchResultClick : function(bean) {
								//$("#groupOwnerId").val(bean.id);
								//$("#groupOwner").val(localeManager["openiam.ui.common.group"] +" - " + bean.name);
								OPENIAM.Group.Form.addOwner("group", bean);
								return false;
							}
						});
					}
				}
			});
		});
		//var selectedOrgList = [];
		//if(obj.organizations!=null && obj.organizations!=undefined && obj.organizations.length>0){
		//	for(var i = 0; i<obj.organizations.length;i++){
		//		selectedOrgList.push({id:obj.organizations[i].id,name:obj.organizations[i].name,type:obj.organizations[i].organizationTypeId});
		//	}
		//}

		$("#organizationsTable").organizationHierarchyWrapper({
			hierarchy : OPENIAM.ENV.OrganizationHierarchy,
			selectedOrgs : obj.organizations,
			draw : function(select, labelText) {
				if(this.idx == undefined) {
					this.idx = 0;
					this.tr = null;
				}
				if (this.idx == 0 || this.idx % 3 == 0) {
					this.tr = document.createElement("tr");
					$(this).append(this.tr);
				}
				var label = document.createElement("label");
				$(label).text(labelText);
				var td = document.createElement("td");
				$(td).append(label).append("<br/>");
				$(td).append(select);
				$(this.tr).append(td);
				this.idx++;
			},
			hide : function(select) {
				select.closest("td").hide();
			},
			show : function(select) {
				select.closest("td").show();
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
	addOwner:function(typeName, bean){
		var type = localeManager["openiam.ui.common."+typeName]
		$("#groupOwnerId").val(bean.id);
		$("#groupOwnerType").val(typeName);
		$("#groupOwner").val(type +" - " + bean.name);
		//$("#selectGroupOwner").remove()
	},
	initGroupSearchDialog: function(args){
		args.onClick = function($that) {
			$("#editDialog").groupDialogSearch({
				searchTargetElmt : "#editDialog",
				showResultsInDialog : true,
				onAdd : function(bean) {
					$that.selectableSearchResult("add", bean);
				},
				pageSize : 5
			});
		}
		OPENIAM.Group.Form.initSearchDialog(args);
	},
	initMetadataSearchDialog: function(args){
		args.onClick = function($that) {
			$("#editDialog").metadataTypeDialogSearch({
				initialGrouping:args.metadataGrouping,
				showResultsInDialog : true,
				searchTargetElmt : "#editDialog",
				showAllGroupings: args.showAllGroupings,
				onAdd : function(bean) {
					$that.selectableSearchResult("add", bean);
					$("#editDialog").dialog("close");
				},
				pageSize : 5
			});
		};
		OPENIAM.Group.Form.initSearchDialog(args);
	},
	initSearchDialog: function(args){
		$(args.targetElement).selectableSearchResult({
			initialBeans : [{id : args.initialDataId, name : args.initialDataName}],
			singleSearch : true,
			addMoreText : args.selectAnotherLabel,
			noneSelectedText : args.selectLabel,
			onClick :args.onClick
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
		obj.maxUserNumber = $("#groupMaxUserCount").val();
		obj.membershipDuration = $("#groupMembershipDuration").val();

		obj.companyId = $("#organization").selectableSearchResult("getId");

		obj.classificationId = $("#groupClassification").selectableSearchResult("getId");
		obj.adGroupTypeId = $("#groupADType").selectableSearchResult("getId");
		obj.adGroupScopeId = $("#groupADScope").selectableSearchResult("getId");
		obj.riskId = $("#groupRisk").selectableSearchResult("getId");
        obj.mdTypeId = $("#groupTypeId").val();

		obj.owner={};
		obj.owner.type= $("#groupOwnerType").val();
		obj.owner.id= $("#groupOwnerId").val();

		obj.organizations = [];
		var orgids = $("#organizationsTable").organizationHierarchyWrapper("getValues");
		if(orgids!=null && orgids!=undefined && orgids.length>0){
			for(var i=0; i<orgids.length;i++ ){
				obj.organizations.push({id:orgids[i]});
			}
		}

		var parentId = $("#groupParent").selectableSearchResult("getId");
		if(parentId!=null && parentId!=undefined){
			obj.parentGroups = [];
			obj.parentGroups.push({id:parentId});
		}
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