OPENIAM = window.OPENIAM || {};
OPENIAM.Entitlements = {
	getButton : function(args) {
		var mySearch = $(document.createElement("input")); 
		mySearch.attr("type", "submit").attr("value", args.buttonTitle).addClass("redBtn").attr("id", "searchBtn");
		return mySearch;
	},
	Load : {
		onReady : function() {
			switch(OPENIAM.ENV.EntitlementType) {
				case "roles":
					OPENIAM.Entitlements.Role.load();
					break;
				case "users":
					OPENIAM.Entitlements.User.load();
					break;
				case "childresources":
					OPENIAM.Entitlements.Resource.Children.load();
					break;
				case "parentresources":
					OPENIAM.Entitlements.Resource.Parent.load();
					break;
				case "groups":
					OPENIAM.Entitlements.Group.load();
					break;
				default:
					break;
			}
		}
	},
	Common : {
		load : function(args) {
			var that = args.target;			
			var preventInputHeaders = !$.isArray(args.customHeaders);
			$("#entitlementsContainer").entitlemetnsTable({
				columnHeaders : args.columns,
                columnsMap : args.columnsMap,
				ajaxURL : args.ajaxURL,
				entityUrl : args.entityURL,
				entityType : args.entityType,
				entityURLIdentifierParamName : "id",
				requestParamIdName : "id",
				requestParamIdValue : OPENIAM.ENV.ResourceId,
				pageSize : 20,
				deleteOptions : (args.hasDeleteButton) ? {
					warningMessage : localeManager["openiam.ui.delete.relationship.confirmation.delete"],
					onDelete : function(bean) {
						that.remove(bean.id);
					}
				} : null,
				emptyResultsText : OPENIAM.ENV.Text.EmptyChildren,
				theadInputElements : args.customHeaders,
				preventOnclickEvent : OPENIAM.ENV.PreventOnClick,
                showPageSizeSelector:true,
                sortEnable:true,
				onAppendDone : function() {
					if(!preventInputHeaders) {
						var submit = this.find("#searchBtn");
						if($.isFunction(args.onSearchClick)) {
							submit.click(function() {
								args.onSearchClick();
							});
						}
					}
				}
			});
		},
		addOrRemove : function(args) {
			var data = {};
			data["resourceId"] = OPENIAM.ENV.ResourceId;
			data[args.entityRequestParamName] = args.entityId;
			$.ajax({
				url : args.url,
				"data" : data,
				type: "POST",
				dataType : "json",
				success : function(data, textStatus, jqXHR) {
					if(data.status == 200) {
						OPENIAM.Modal.Success({message : data.successMessage, showInterval : 1000, onIntervalClose : function() {
							args.target.load(0);
						}});
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
	Role : {
		load : function() {
			OPENIAM.Entitlements.Common.load({
				customHeaders : [
					"", 
					"", 
					OPENIAM.Entitlements.getButton({buttonTitle : localeManager["openiam.ui.entitlements.role.add"]})
				],
				modalAjaxURL : "rest/api/entitlements/searchRoles",
				columns : [
					localeManager["openiam.ui.shared.role.name"], 
					localeManager["openiam.ui.shared.managed.system"], 
					localeManager["openiam.ui.common.actions"]
				],
                columnsMap:["name", "managedSysName"],
				entityURL : "editRole.html",
				ajaxURL : "rest/api/entitlements/getRolesForResource",
				buttonTitle : localeManager["openiam.ui.shared.role.search"],
                hasDeleteButton : true,
				target : this,
				onSearchClick : function() {
					$("#editDialog").roleDialogSearch({
						searchTargetElmt : "#searchResultsContainer",
						onSearchResultClick : function(bean) {
							OPENIAM.Entitlements.Role.add(bean.id);
							return false;
						}
					});
				}
			});
		},
		add : function(id) {
			OPENIAM.Entitlements.Common.addOrRemove({
				entityRequestParamName : "roleId",
				entityId : id,
				url : "addRoleToResource.html",
				target : this
			});
		},
		remove : function(id) {
			OPENIAM.Entitlements.Common.addOrRemove({
				entityRequestParamName : "roleId",
				entityId : id,
				url : "removeRoleFromResource.html",
				target : this
			});
		}
	},
	Group : {
		load : function() {
			OPENIAM.Entitlements.Common.load({
				customHeaders : [
					"",
					"",
					OPENIAM.Entitlements.getButton({buttonTitle : localeManager["openiam.ui.entitlements.group.add"]})
				],
				onSearchClick : function() {
					$("#editDialog").groupDialogSearch({
						searchTargetElmt : "#searchResultsContainer",
						onSearchResultClick : function(bean) {
							OPENIAM.Entitlements.Group.add(bean.id);
							return false;
						}
					});
				},
				columns : [
					localeManager["openiam.ui.shared.group.name"],
					localeManager["openiam.ui.shared.managed.system"], 
					localeManager["openiam.ui.common.actions"]
				],
                columnsMap:["name", "managedSysName"],
				entityURL : "editGroup.html",
				ajaxURL : "rest/api/entitlements/getGroupsForResource",
				buttonTitle : localeManager["openiam.ui.shared.group.search"],
                hasDeleteButton : true,
				target : this,
				getAdditionalDataRequestObject : function() {
					var obj = {};
					return obj;
				}
			});
		},
		add : function(id) {
			OPENIAM.Entitlements.Common.addOrRemove({
				entityRequestParamName : "groupId",
				entityId : id,
				url : "addGroupToResource.html",
				target : this
			});
		},
		remove : function(id) {
			OPENIAM.Entitlements.Common.addOrRemove({
				entityRequestParamName : "groupId",
				entityId : id,
				url : "deleteGroupFromResource.html",
				target : this
			});
		}
	},
	Resource : {
		Children : {
			load : function() {
				OPENIAM.Entitlements.Common.load({
					customHeaders : [
						"", 
						"", 
						"", 
						OPENIAM.Entitlements.getButton({buttonTitle : localeManager["openiam.ui.entitlements.resource.child.add"]})
					],
					onSearchClick : function() {
						$("#editDialog").resourceDialogSearch({
							excludeMenus : true,
							searchTargetElmt : "#searchResultsContainer",
							//managedSysId : OPENIAM.ENV.ResourceTypeId,
							onSearchResultClick : function(bean) {
								OPENIAM.Entitlements.Resource.Children.add(bean.id);
								return false;
							}
						});
					},
					columns : [
						localeManager["openiam.ui.shared.resource.name"], 
						localeManager["openiam.ui.common.resource.type"], 
						localeManager["openiam.ui.common.risk"], 
						localeManager["openiam.ui.common.actions"]
					],
	                columnsMap:["name", "resourceType", "risk"],
					entityURL : "editResource.html",
					ajaxURL : "rest/api/entitlements/getChildResources",
					buttonTitle : localeManager["openiam.ui.shared.resource.search"],
                    hasDeleteButton : true,
					target : this
//					getAdditionalDataRequestObject : function() {
//						return {
//							resourceTypeId : OPENIAM.ENV.ResourceTypeId
//						};
//					}
				});
			},
			add : function(id) {
				OPENIAM.Entitlements.Common.addOrRemove({
					entityRequestParamName : "memberResourceId",
					entityId : id,
					url : "addChildResource.html",
					target : this
				});
			},
			remove : function(id) {
				OPENIAM.Entitlements.Common.addOrRemove({
					entityRequestParamName : "memberResourceId",
					entityId : id,
					url : "removeChildResource.html",
					target : this
				});
			}
		},
		Parent : {
			load : function() {
				OPENIAM.Entitlements.Common.load({
					columns : [
						localeManager["openiam.ui.shared.resource.name"], 
						localeManager["openiam.ui.common.resource.type"]
					],
	                columnsMap:["name", "resourceType"],
					entityURL : "editResource.html",
					preventInputHeaders : true,
					ajaxURL : "rest/api/entitlements/getParentResources",
                    hasDeleteButton : false
				});
			}
		}
	},
	User : {
		load : function() {
			$("#userResultsArea").empty();
			
			var that = this;
			$("#entitlementsContainer").entitlemetnsTable({
				columnHeaders : [
					localeManager["openiam.ui.common.name"], 
					localeManager["openiam.ui.common.phone.number"], 
					localeManager["openiam.ui.common.email.address"], 
					localeManager["openiam.ui.webconsole.user.status"], 
					localeManager["openiam.ui.webconsole.user.accountStatus"], 
					localeManager["openiam.ui.common.actions"]
				],
                columnsMap:["name","phone","email","userStatus","accountStatus"],
				ajaxURL : "rest/api/entitlements/getUsersForResource",
				entityUrl : "editUser.html",
				entityType : "USER",
				entityURLIdentifierParamName : "id",
				requestParamIdName : "id",
				requestParamIdValue : OPENIAM.ENV.ResourceId,
				pageSize : 20,
				deleteOptions : {
					warningMessage : localeManager["openiam.ui.delete.relationship.confirmation.delete"],
					onDelete : function(bean) {
						that.remove(bean.id);
					}
				},
				emptyResultsText : OPENIAM.ENV.Text.EmptyChildren,
                showPageSizeSelector:true,
                sortEnable:true,
				theadInputElements : ["", "", "", "", "", OPENIAM.Entitlements.getButton({buttonTitle : localeManager["openiam.ui.entitlements.user.add"]})],
				onAppendDone : function() {
					this.find("#searchBtn").click(function() {
						$("#dialog").userSearchForm({
							afterFormAppended : function() {
								$("#dialog").dialog({autoOpen: false, draggable : false, resizable : false, title : localeManager["openiam.ui.common.search.users"], width: "auto", position : "center"});
								$("#dialog").dialog("open");
							},
    						onSubmit : function(json) {
    							$("#searchResultsContainer").userSearchResults({
    								"jsonData" : json,
    								"page" : 0,
    								"size" : 20,
                                    initialSortColumn : "name",
                                    initialSortOrder : "ASC",
    								url : OPENIAM.ENV.ContextPath + "/rest/api/users/search",
    								emptyFormText : localeManager["openiam.ui.common.user.search.empty"],
                					emptyResultsText : localeManager["openiam.ui.common.user.search.no.results"],
    								columnHeaders : [
    									localeManager["openiam.ui.common.name"], 
    									localeManager["openiam.ui.common.phone.number"], 
    									localeManager["openiam.ui.common.email.address"], 
    									localeManager["openiam.ui.webconsole.user.status"], 
    									localeManager["openiam.ui.webconsole.user.accountStatus"]
    								],
    								onAppendDone : function() {
    									$("#dialog").dialog("close");
    									$("#userResultsArea").prepend("<div class=\"\">" + OPENIAM.ENV.Text.UserTableDescription + "</div>");
    								},
    								onEntityClick : function(bean) {
    									OPENIAM.Entitlements.User.add(bean.id);
    								}
    							});
    						}
						});
					});
				}
			});
		},
		add : function(id) {
			OPENIAM.Entitlements.Common.addOrRemove({
				entityRequestParamName : "userId",
				entityId : id,
				url : "addUserToResource.html",
				target : this
			});
		},
		remove : function(id) {
			OPENIAM.Entitlements.Common.addOrRemove({
				entityRequestParamName : "userId",
				entityId : id,
				url : "removeUserFromResource.html",
				target : this
			});
		}
	}
};

$(document).ready(function() {
	OPENIAM.Entitlements.Load.onReady();
});

$(document).load(function() {
	
});