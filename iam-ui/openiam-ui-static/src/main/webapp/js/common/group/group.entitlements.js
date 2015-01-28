OPENIAM = window.OPENIAM || {};
OPENIAM.GroupEntitlements = {
	getButton : function(args) {
		var mySearch = $(document.createElement("input")); 
		mySearch.attr("type", "submit").attr("value", args.buttonTitle).addClass("redBtn").attr("id", "searchBtn");
		return mySearch;
	},
	Load : {
		onReady : function() {
			switch(OPENIAM.ENV.EntitlementType) {
				case "childgroups":
					OPENIAM.GroupEntitlements.Group.Children.load();
					break;
				case "parentgroups":
					OPENIAM.GroupEntitlements.Group.Parent.load();
					break;
				case "roles":
					OPENIAM.GroupEntitlements.Roles.load();
					break;
				case "users":
					OPENIAM.GroupEntitlements.User.load();
					break;
				case "resources":
					OPENIAM.GroupEntitlements.Resource.load();
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
				requestParamIdValue : OPENIAM.ENV.GroupId,
				pageSize : 20,
				deleteOptions : (args.hasDeleteButton) ? {
					warningMessage : localeManager["openiam.ui.delete.relationship.confirmation.delete"],
					onDelete : function(bean) {
						that.remove(bean.id);
					}
				} : null,
                hasProvisionButton : args.hasProvisionButton,
                hasDeprovisionButton : args.hasDeprovisionButton,
                onProvision: function(bean) {
                    that.provision(bean.id);
                },
                onDeprovision: function(bean) {
                    that.deprovision(bean.id);
                },
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
			data["groupId"] = OPENIAM.ENV.GroupId;
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
        ,
        onProvision : function(args) {
            var data = {};
            data["groupId"] = OPENIAM.ENV.GroupId;
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
            data["groupId"] = OPENIAM.ENV.GroupId;
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
			OPENIAM.GroupEntitlements.Common.load({
				customHeaders : ["", "", "", OPENIAM.GroupEntitlements.getButton({buttonTitle : localeManager["openiam.ui.entitlements.resource.add"]})],
				onSearchClick : function() {
					$("#editDialog").resourceDialogSearch({
						excludeMenus : true,
						searchTargetElmt : "#searchResultsContainer",
						onSearchResultClick : function(bean) {
							OPENIAM.GroupEntitlements.Resource.add(bean.id);
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
				ajaxURL : "rest/api/entitlements/getResourcesForGroup",
				buttonTitle : localeManager["openiam.ui.shared.resource.search"],
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
			OPENIAM.GroupEntitlements.Common.addOrRemove({
				entityRequestParamName : "resourceId",
				entityId : id,
				url : "addGroupToResource.html",
				target : this
			});
		},
		remove : function(id) {
			OPENIAM.GroupEntitlements.Common.addOrRemove({
				entityRequestParamName : "resourceId",
				entityId : id,
				url : "deleteGroupFromResource.html",
				target : this
			});
		},
        provision : function(id) {
            OPENIAM.GroupEntitlements.Common.onProvision({
                entityRequestParamName : "resourceId",
                entityId : id,
                url : "provisionResourceByGroup.html",
                target : this
            });
        },
        deprovision : function(id) {
            OPENIAM.GroupEntitlements.Common.onDeprovision({
                entityRequestParamName : "resourceId",
                entityId : id,
                url : "deprovisionResourceByGroup.html",
                target : this
            });
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
				ajaxURL : "rest/api/entitlements/getUsersForGroup",
				entityUrl : "editUser.html",
				entityType : "USER",
				entityURLIdentifierParamName : "id",
				requestParamIdName : "id",
				requestParamIdValue : OPENIAM.ENV.GroupId,
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
				theadInputElements : ["", "", "", "", "", OPENIAM.GroupEntitlements.getButton({buttonTitle : localeManager["openiam.ui.entitlements.user.add"]})],
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
    									OPENIAM.GroupEntitlements.User.add(bean.id);
    								}
    							});
    						}
						});
					});
				}
			});
		},
		add : function(id) {
			OPENIAM.GroupEntitlements.Common.addOrRemove({
				entityRequestParamName : "userId",
				entityId : id,
				url : "addUserToGroup.html",
				target : this
			});
		},
		remove : function(id) {
			OPENIAM.GroupEntitlements.Common.addOrRemove({
				entityRequestParamName : "userId",
				entityId : id,
				url : "removeUserFromGroup.html",
				target : this
			});
		}
	},
	Roles : {
		load : function() {
			OPENIAM.GroupEntitlements.Common.load({
				customHeaders : ["", "", OPENIAM.GroupEntitlements.getButton({buttonTitle : localeManager["openiam.ui.entitlements.role.add"]})],
				modalAjaxURL : "rest/api/entitlements/searchRoles",
				columns : [
					localeManager["openiam.ui.shared.role.name"], 
					localeManager["openiam.ui.shared.managed.system"], 
					localeManager["openiam.ui.common.actions"]
				],
                columnsMap:["name", "managedSysName"],
				entityURL : "editRole.html",
				ajaxURL : "rest/api/entitlements/getRolesForGroup",
				buttonTitle : localeManager["openiam.ui.shared.role.search"],
                hasDeleteButton : true,
				target : this,
				onSearchClick : function() {
					$("#editDialog").roleDialogSearch({
						searchTargetElmt : "#searchResultsContainer",
						onSearchResultClick : function(bean) {
							OPENIAM.GroupEntitlements.Roles.add(bean.id);
							return false;
						}
					});
				}
			});
		},
		add : function(id) {
			OPENIAM.GroupEntitlements.Common.addOrRemove({
				entityRequestParamName : "roleId",
				entityId : id,
				url : "addGroupToRole.html",
				target : this
			});
		},
		remove : function(id) {
			OPENIAM.GroupEntitlements.Common.addOrRemove({
				entityRequestParamName : "roleId",
				entityId : id,
				url : "removeGroupFromRole.html",
				target : this
			});
		}
	},
	Group : {
		Children : {
			load : function() {
				OPENIAM.GroupEntitlements.Common.load({
					customHeaders : ["", "", OPENIAM.GroupEntitlements.getButton({buttonTitle : localeManager["openiam.ui.entitlements.group.child.add"]})],
					onSearchClick : function() {
						$("#editDialog").groupDialogSearch({
							searchTargetElmt : "#searchResultsContainer",
							onSearchResultClick : function(bean) {
								OPENIAM.GroupEntitlements.Group.Children.add(bean.id);
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
					ajaxURL : "rest/api/entitlements/getChildGroups",
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
				OPENIAM.GroupEntitlements.Common.addOrRemove({
					entityRequestParamName : "childGroupId",
					entityId : id,
					url : "addChildGroup.html",
					target : this
				});
			},
			remove : function(id) {
				OPENIAM.GroupEntitlements.Common.addOrRemove({
					entityRequestParamName : "childGroupId",
					entityId : id,
					url : "removeChildGroup.html",
					target : this
				});
			}
		},
		Parent : {
			load : function() {
				OPENIAM.GroupEntitlements.Common.load({
					modalAjaxURL : "rest/api/entitlements/searchGroups",
					columns : [
						localeManager["openiam.ui.shared.group.name"],
						localeManager["openiam.ui.shared.managed.system"],
                        localeManager["openiam.ui.common.actions"]
					],
					columnsMap:["name", "managedSysName"],
					entityURL : "editGroup.html",
					preventInputHeaders : true,
					ajaxURL : "rest/api/entitlements/getParentGroups",
                    hasDeleteButton : false
				});
			}
		}
	}
};

$(document).ready(function() {
	OPENIAM.GroupEntitlements.Load.onReady();
});

$(document).load(function() {
	
});