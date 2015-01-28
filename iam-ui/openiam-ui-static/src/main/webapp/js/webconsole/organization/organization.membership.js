OPENIAM = window.OPENIAM || {};
OPENIAM.OrganizationEntitlement = {
	Load : {
		onReady : function() {
			switch(OPENIAM.ENV.EntitlementType) {
				case "childorganizations":
					OPENIAM.OrganizationEntitlement.Organization.Children.load();
					break;
				case "parentorganizations":
					OPENIAM.OrganizationEntitlement.Organization.Parent.load();
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
//				var myInput = document.createElement("input"); $(myInput).attr("type", "text"); myInput.className = "full rounded"; $(myInput).attr("placeholder", args.placeholder);
//						 	  $(myInput).attr("autocomplete", "off"); myInput.id = "name";
						 
				var mySearch = document.createElement("input"); $(mySearch).attr("type", "button"); $(mySearch).attr("value", args.buttonTitle); mySearch.className = "redBtn"; mySearch.id = "search";
				inputelements = [];
				inputelements.push("");
				inputelements.push("");
				inputelements.push(mySearch);
			}
			
			$("#entitlementsContainer").entitlemetnsTable({
				columnHeaders : args.columns,
                columnsMap : args.columnsMap,
				ajaxURL : args.ajaxURL,
				entityUrl : args.entityURL,
				entityType : args.entityType,
				entityURLIdentifierParamName : "id",
				requestParamIdName : "id",
				requestParamIdValue : OPENIAM.ENV.OrganizationId,
				pageSize : 20,
                sortEnable:true,
                deleteOptions : (args.hasDeleteButton) ? {
                	warningMessage : localeManager["openiam.ui.delete.relationship.confirmation.delete"],
                	onDelete : function(bean) {
						that.remove(bean.id);
					}
                } : null,
				emptyResultsText : OPENIAM.ENV.Text.EmptyChildren,
				theadInputElements : inputelements,
				onAppendDone : function() {
					if(!preventInputHeaders) {
                        var submit = this.find("#search");
                        if($.isFunction(args.onSearchClick)) {
                            submit.click(function() {
                                args.onSearchClick();
                            });
                        }

//						var input = this.find("#name");
//						var submit = this.find("#search");
//						input.modalSearch({
//							onElementClick : function(bean) {
//								that.add(bean.id);
//							},
//							ajaxURL : args.modalAjaxURL,
//							dialogTitle : OPENIAM.ENV.Text.SearchDialogTitle,
//							emptyResultsText : OPENIAM.ENV.Text.EmptySearch,
//                            getAdditionalDataRequestObject : args.getAdditionalDataRequestObject
//						});
//						input.keyup(function(e) {
//							if(e.keyCode == 13) {
//								submit.click();
//							}
//						});
//
//						submit.click(function() {
//							input.modalSearch("show");
//						});
					}
				}
			});
		},
		addOrRemove : function(args) {
			var data = {};
			data["organizationId"] = OPENIAM.ENV.OrganizationId;
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
	Organization : {
		Children : {
			load : function() {
                var $this = this;
				OPENIAM.OrganizationEntitlement.Common.load({
					modalAjaxURL : "rest/api/entitlements/searchOrganizations",
					columns : [
                        localeManager["openiam.ui.common.organization.name"],
                        localeManager["openiam.ui.common.organization.type"],
						localeManager["openiam.ui.common.actions"]
					],
                    onSearchClick : function() {

                        $("#editDialog").organizationDialogSearch({
                            searchTargetElmt : "#searchResultsContainer",
                            validParentTypeId : OPENIAM.ENV.OrganizationTypeId,
                            onSearchResultClick : function(bean) {
                                $this.add(bean.id);
                                return false;
                            }
                        });
                    },
                    columnsMap : ["name", "type"],
					entityURL : "editOrganization.html",
					ajaxURL : "getChildOrganizations.html",
					buttonTitle : localeManager["openiam.ui.shared.organization.search"],
					placeholder :localeManager["openiam.ui.shared.organization.type.name"],
                    hasDeleteButton : true,
					target : $this
				});
			},
			add : function(id) {
				OPENIAM.OrganizationEntitlement.Common.addOrRemove({
					entityRequestParamName : "childOrganizationId",
					entityId : id,
					url : "addChildOrganization.html",
					target : this
				});
			},
			remove : function(id) {
				OPENIAM.OrganizationEntitlement.Common.addOrRemove({
					entityRequestParamName : "childOrganizationId",
					entityId : id,
					url : "removeChildOrganization.html",
					target : this
				});
			}
		},
		Parent : {
			load : function() {
				OPENIAM.OrganizationEntitlement.Common.load({
					columns : [localeManager["openiam.ui.common.organization.name"],
                               localeManager["openiam.ui.common.organization.type"]],
                    columnsMap : ["name", "type"],
					entityURL : "editOrganization.html",
					preventInputHeaders : true,
					ajaxURL : "rest/api/entitlements/getParentOrganizations",
                    hasDeleteButton : false
				});
			}
		}
	}
};

$(document).ready(function() {
	OPENIAM.OrganizationEntitlement.Load.onReady();
});

$(document).load(function() {
	
});