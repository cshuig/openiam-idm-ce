OPENIAM = window.OPENIAM || {};
OPENIAM.OrganizationType = {
	Load : {
		onReady : function() {
			switch(OPENIAM.ENV.EntitlementType) {
				case "children":
					OPENIAM.OrganizationType.Type.Children.load();
					break;
				case "parents":
					OPENIAM.OrganizationType.Type.Parent.load();
					break;
				case "organizations":
					OPENIAM.OrganizationType.Organization.load();
					break;
				default:
					break;
			}
		}
	},
	Common : {
		load : function(args) {
			var that = args.target;
			var inputelements = null;
			var preventInputHeaders = (args.preventInputHeaders == null || args.preventInputHeaders == undefined || args.preventInputHeaders == false) ? false : true;
			if(!preventInputHeaders) {
				var myInput = document.createElement("input"); $(myInput).attr("type", "text"); myInput.className = "full rounded"; $(myInput).attr("placeholder", args.placeholder); 
						 	  $(myInput).attr("autocomplete", "off"); myInput.id = "name";
						 
				var mySearch = document.createElement("input"); $(mySearch).attr("type", "button"); $(mySearch).attr("value", args.buttonTitle); mySearch.className = "redBtn"; mySearch.id = "search";
				inputelements = [];
				inputelements.push(myInput);
				inputelements.push(mySearch);
			}
			
			$("#entitlementsContainer").entitlemetnsTable({
				columnHeaders : args.columns,
                columnsMap : args.columnsMap,
				ajaxURL : args.ajaxURL,
				entityUrl : args.entityURL,
				entityType : args.entityType,
				entityURLIdentifierParamName : "id",
				/*
				requestParamIdName : "id",
				requestParamIdValue : OPENIAM.ENV.OrganizationTypeId,
				*/
				pageSize : 10,
                deleteOptions : (args.hasDeleteButton) ? {
                	warningMessage : localeManager["openiam.ui.delete.relationship.confirmation.delete"],
                	onDelete : function(bean) {
						that.remove(bean.id);
					}
                } : null,
				emptyResultsText : OPENIAM.ENV.Text.EmptyChildren,
				theadInputElements : inputelements,
				getAdditionalDataRequestObject : args.getAdditionalDataRequestObject,
				onAppendDone : function() {
					if(!preventInputHeaders) {
						var input = this.find("#name");
						var submit = this.find("#search");
						input.modalSearch({
							onElementClick : function(bean) {
								that.add(bean.id);
							}, 
							ajaxURL : args.modalAjaxURL, 
							dialogTitle : OPENIAM.ENV.Text.SearchDialogTitle,
							emptyResultsText : OPENIAM.ENV.Text.EmptySearch,
                            getAdditionalDataRequestObject : args.getAdditionalModalRequestParams
						});
						input.keyup(function(e) {
							if(e.keyCode == 13) {
								submit.click();
							}
						});
						
						submit.click(function() {
							input.modalSearch("show");
						});
					}
				}
			});
		},
		addOrRemove : function(args) {
			var data = {};
			data["typeId"] = OPENIAM.ENV.OrganizationTypeId;
			data[args.entityRequestParamName] = args.entityId;
			$.ajax({
				url : args.url,
				"data" : data,
				type: "POST",
				dataType : "json",
				success : function(data, textStatus, jqXHR) {
					if(data.status == 200) {
						args.target.load(0);
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
	Type : {
		Children : {
			load : function() {
				OPENIAM.OrganizationType.Common.load({
					modalAjaxURL : "organization/type/rest/search.html",
					columns : [
						localeManager["openiam.ui.common.name"], 
						localeManager["openiam.ui.common.actions"]
					],
                    columnsMap : ["name"],
					entityURL : "organizationTypeEdit.html",
					ajaxURL : "organization/type/rest/search.html",
					buttonTitle : localeManager["openiam.ui.organization.type.search"],
					placeholder : localeManager["openiam.ui.organization.type.name.placeholder"],
                    hasDeleteButton : true,
					target : this,
                    getAdditionalDataRequestObject : function() {
                        return {
                        	parentId: OPENIAM.ENV.OrganizationTypeId
                        };
                    }
				});
			},
			add : function(id) {
				OPENIAM.OrganizationType.Common.addOrRemove({
					entityRequestParamName : "memberTypeId",
					entityId : id,
					url : "organizationTypeChildAdd.html",
					target : this
				});
			},
			remove : function(id) {
				OPENIAM.OrganizationType.Common.addOrRemove({
					entityRequestParamName : "memberTypeId",
					entityId : id,
					url : "organizationTypeChildDelete.html",
					target : this
				});
			}
		},
		Parent : {
			load : function() {
				OPENIAM.OrganizationType.Common.load({
					columns : [localeManager["openiam.ui.common.name"]],
                    columnsMap : ["name"],
					entityURL : "organizationTypeEdit.html",
					preventInputHeaders : true,
					ajaxURL : "organization/type/rest/search.html",
                    hasDeleteButton : false,
                    getAdditionalDataRequestObject : function() {
						return {
							childId: OPENIAM.ENV.OrganizationTypeId
						};
					}
				});
			}
		}
	},
	Organization : {
		load : function() {
			OPENIAM.OrganizationType.Common.load({
				columns : [localeManager["openiam.ui.common.organization.name"], localeManager["openiam.ui.common.organization.type"]],
				columnsMap : ["name", "type"],
				entityURL : "editOrganization.html",
				preventInputHeaders : true,
				ajaxURL : "rest/api/entitlements/searchOrganizations",
				hasDeleteButton : false,
				getAdditionalDataRequestObject : function() {
					return {
						organizationTypeId: [OPENIAM.ENV.OrganizationTypeId]
					};
				}
			});
		}
	}
};

$(document).ready(function() {
	OPENIAM.OrganizationType.Load.onReady();
});

$(window).load(function() {

});