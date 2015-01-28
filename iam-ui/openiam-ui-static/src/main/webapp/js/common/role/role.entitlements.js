OPENIAM = window.OPENIAM || {};
OPENIAM.Roles = {
	getButton : function(args) {
		var mySearch = $(document.createElement("input")); 
		mySearch.attr("type", "submit").attr("value", args.buttonTitle).addClass("redBtn").attr("id", "searchBtn");
		return mySearch;
	},
	Load : {
		onReady : function() {
			switch(OPENIAM.ENV.EntitlementType) {
				case "childroles":
					OPENIAM.Roles.Role.Children.load();
					break;
				case "parentroles":
					OPENIAM.Roles.Role.Parent.load();
					break;
				case "groups":
					OPENIAM.Roles.Group.load();
					break;
				case "users":
					OPENIAM.Roles.User.load();
					break;
				case "resources":
					OPENIAM.Roles.Resource.load();
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
                getAdditionalDataRequestObject : args.getAdditionalDataRequestObject,
				ajaxURL : args.ajaxURL,
				entityUrl : args.entityURL,
				entityType : args.entityType,
				entityURLIdentifierParamName : "id",
				requestParamIdName : "id",
				requestParamIdValue : OPENIAM.ENV.RoleId,
				pageSize : 20,
                hasProvisionButton : args.hasProvisionButton,
                hasDeprovisionButton : args.hasDeprovisionButton,
                deleteOptions : (args.hasDeleteButton) ? {
                	warningMessage : localeManager["openiam.ui.delete.relationship.confirmation.delete"],
                	onDelete : function(bean) {
						that.remove(bean.id);
					}
                } : null,
                preventOnclickEvent : OPENIAM.ENV.PreventOnClick,
                onProvision: function(bean) {
                    that.provision(bean.id);
                },
                onDeprovision: function(bean) {
                    that.deprovision(bean.id);
                },
				emptyResultsText : OPENIAM.ENV.Text.EmptyChildren,
				theadInputElements : args.customHeaders,
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
			data["roleId"] = OPENIAM.ENV.RoleId;
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
		},
        onProvision : function(args) {
            var data = {};
            data["roleId"] = OPENIAM.ENV.RoleId;
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
        },
        onDeprovision : function(args) {
            var data = {};
            data["roleId"] = OPENIAM.ENV.RoleId;
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
	Resource : {
		load : function() {
			OPENIAM.Roles.Common.load({
				customHeaders : ["", "", "", OPENIAM.Roles.getButton({buttonTitle : localeManager["openiam.ui.entitlements.resource.add"]})],
				onSearchClick : function() {
					$("#editDialog").resourceDialogSearch({
						excludeMenus : true,
						searchTargetElmt : "#searchResultsContainer",
						onSearchResultClick : function(bean) {
							OPENIAM.Roles.Resource.add(bean.id);
							return false;
						}
					});
				},
				columns : [
					localeManager["openiam.ui.shared.resource.name"], 
					localeManager["openiam.ui.common.resource.type"], 
					localeManager["openiam.ui.common.resource.risk"], 
					localeManager["openiam.ui.common.actions"]
				],
                columnsMap:["name", "resourceType", "risk"],
				entityURL : "editResource.html",
				ajaxURL : "rest/api/entitlements/getResourcesForRole",
				buttonTitle : localeManager["openiam.ui.shared.resource.search"],
				placeholder : localeManager["openiam.ui.shared.resource.name.type"],
                hasDeleteButton : true,
                hasProvisionButton : true,
                hasDeprovisionButton : true,
				target : this,
				getAdditionalDataRequestObject : function() {
					var obj = {};
					obj.ignoreMenus = true;
					return obj;
				}
			});
		},
		add : function(id) {
			OPENIAM.Roles.Common.addOrRemove({
				entityRequestParamName : "resourceId",
				entityId : id,
				url : "addRoleToResource.html",
				target : this
			});
		},
		remove : function(id) {
			OPENIAM.Roles.Common.addOrRemove({
				entityRequestParamName : "resourceId",
				entityId : id,
				url : "removeRoleFromResource.html",
				target : this
			});
		},
        provision : function(id) {
            OPENIAM.Roles.Common.onProvision({
                entityRequestParamName : "resourceId",
                entityId : id,
                url : "provisionResourceByRole.html",
                target : this
            });
        },
        deprovision : function(id) {
            OPENIAM.Roles.Common.onDeprovision({
                entityRequestParamName : "resourceId",
                entityId : id,
                url : "deprovisionResourceByRole.html",
                target : this
            });
        }
	},
	User : {
		load : function() {
			$("#searchResultsContainer").empty();
			
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
				ajaxURL : "rest/api/entitlements/getUsersForRole",
				entityUrl : "editUser.html",
				entityType : "USER",
				entityURLIdentifierParamName : "id",
				requestParamIdName : "id",
				requestParamIdValue : OPENIAM.ENV.RoleId,
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
				theadInputElements : ["", "", "", "", "", OPENIAM.Roles.getButton({buttonTitle : localeManager["openiam.ui.entitlements.user.add"]})],
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
    									$("#searchResultsContainer").prepend("<div class=\"\">" + OPENIAM.ENV.Text.UserTableDescription + "</div>");
    								},
    								onEntityClick : function(bean) {
    									OPENIAM.Roles.User.add(bean.id);
    								}
    							});
    						}
						});
					});
				}
			});
		},
		add : function(id) {
			OPENIAM.Roles.Common.addOrRemove({
				entityRequestParamName : "userId",
				entityId : id,
				url : "addUserToRole.html",
				target : this
			});
		},
		remove : function(id) {
			OPENIAM.Roles.Common.addOrRemove({
				entityRequestParamName : "userId",
				entityId : id,
				url : "removeUserFromRole.html",
				target : this
			});
		}
	},
	Group : {
		load : function() {
			OPENIAM.Roles.Common.load({
				modalAjaxURL : "rest/api/entitlements/searchGroups",
				columns : [
					localeManager["openiam.ui.shared.group.name"], 
					localeManager["openiam.ui.shared.managed.system"],
                    localeManager["openiam.ui.common.actions"]
				],
				columnsMap:["name", "managedSysName"],
				entityURL : "editGroup.html",
				preventInputHeaders : true,
				ajaxURL : "rest/api/entitlements/getGroupsForRole",
                hasDeleteButton : false
			});
		}
	},
	Role : {
		Children : {
			load : function() {
				OPENIAM.Roles.Common.load({
					customHeaders : ["", "", OPENIAM.Roles.getButton({buttonTitle : localeManager["openiam.ui.entitlements.role.child.add"]})],
					modalAjaxURL : "rest/api/entitlements/searchRoles",
					columns : [
						localeManager["openiam.ui.shared.role.name"], 
						localeManager["openiam.ui.shared.managed.system"], 
						localeManager["openiam.ui.common.actions"]
					],
					onSearchClick : function() {
						$("#editDialog").roleDialogSearch({
							searchTargetElmt : "#searchResultsContainer",
							onSearchResultClick : function(bean) {
								OPENIAM.Roles.Role.Children.add(bean.id);
								return false;
							}
						});
					},
                    columnsMap:["name", "managedSysName"],
					entityURL : "editRole.html",
					ajaxURL : "rest/api/entitlements/getChildRoles",
					buttonTitle : localeManager["openiam.ui.shared.role.search"],
					placeholder : localeManager["openiam.ui.shared.type.role.name"],
                    hasDeleteButton : true,
					target : this
				});
			},
			add : function(id) {
				OPENIAM.Roles.Common.addOrRemove({
					entityRequestParamName : "childRoleId",
					entityId : id,
					url : "addChildRole.html",
					target : this
				});
			},
			remove : function(id) {
				OPENIAM.Roles.Common.addOrRemove({
					entityRequestParamName : "childRoleId",
					entityId : id,
					url : "removeChildRole.html",
					target : this
				});
			}
		},
		Parent : {
			load : function() {
				OPENIAM.Roles.Common.load({
					modalAjaxURL : "rest/api/entitlements/searchRoles",
					columns : [
						localeManager["openiam.ui.shared.role.name"], 
						localeManager["openiam.ui.shared.managed.system"],
                        localeManager["openiam.ui.common.actions"]
					],
					columnsMap:["name", "managedSysName"],
					entityURL : "editRole.html",
					preventInputHeaders : true,
					ajaxURL : "rest/api/entitlements/getParentRoles",
                    hasDeleteButton : false
				});
			}
		}
	}
};

$(document).ready(function() {
	OPENIAM.Roles.Load.onReady();
});

$(document).load(function() {
	
});