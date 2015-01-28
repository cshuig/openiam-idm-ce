OPENIAM = window.OPENIAM || {};
OPENIAM.UserEntitlements = {
    getButton : function(args) {
		var mySearch = $(document.createElement("input"));
		mySearch.attr("type", "submit").attr("value", args.buttonTitle).addClass("redBtn").attr("id", "searchBtn");
		return mySearch;
	},
	Load : {
		onReady : function() {
            OPENIAM.UserEntitlements.Menu.draw();
			switch(OPENIAM.ENV.EntitlementType) {
				case "Groups":
					OPENIAM.UserEntitlements.Group.load();
					break;
				case "Roles":
					OPENIAM.UserEntitlements.Roles.load();
					break;
				case "Resources":
					OPENIAM.UserEntitlements.Resource.load();
					break;
				case "Organizations":
					OPENIAM.UserEntitlements.Organizations.load();
					break;
				default:
					break;
			}
		}
	},
    Menu: {
        draw : function() {
            if (OPENIAM.ENV.initialMenu != null && OPENIAM.ENV.initialMenu != 'undefined') {
                OPENIAM.UserEntitlements.Menu = Object.create(OPENIAM.MenuTree);
                OPENIAM.UserEntitlements.Menu.initialize({
                    tree : OPENIAM.ENV.initialMenu,
                    toHTML : function() {
                        var ul = document.createElement("ul");
                        if(this.getRoot() != null) {
                            var node = this.getRoot().getChild();
                            while(node != null) {
                                var html = node.toHTML();
                                if(html) {
                                    ul.appendChild(html);
                                }
                                node = node.getNext();
                            }
                        }
                        return ul;
                    },
                    onNodeClick : function() {
                    },
                    toNodeHtml : function() {
                        var url = this.getURL();
                        var isActive = (window.location.pathname == url)
                            || url.match("/userEntitlements"+OPENIAM.ENV.EntitlementType+"\\.html");
                        if (url != null) {
                            if (OPENIAM.ENV.MenuTreeAppendURL != null) {
                                url = url + ((url.indexOf("?") == -1) ? "?" : "&") + OPENIAM.ENV.MenuTreeAppendURL;
                            }
                        } else {
                            url = "javascript:void(0);";
                        }
                        var li = document.createElement("li");
                        var a = document.createElement("a"); a.href = url;
                        $(a).append(this.getText()); if (isActive) { $(a).addClass("active"); }
                        $(li).append(a);
                        return li;
                    }
                });
                $('#usermenu').append(OPENIAM.UserEntitlements.Menu.toHTML());
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
				entityURLIdentifierParamName : "id",
				requestParamIdName : "id",
				getAdditionalDataRequestObject : args.getAdditionalDataRequestObject,
				requestParamIdValue : OPENIAM.ENV.UserId,
				pageSize : 20,
                hasProvisionButton : args.hasProvisionButton,
                hasDeprovisionButton : args.hasDeprovisionButton,
                showPageSizeSelector:true,
                deleteOptions : {
                	warningMessage : localeManager["openiam.ui.delete.relationship.confirmation.delete"],
					preventWarning : $.isFunction(args.onInfo),
                	onDelete : function(bean) {
						if($.isFunction(args.onInfo)) { /* only if there is an info button */
							$.fn.entitlementsEntityView.hasChildren({entityType : args.entityType, entityId : bean.id, warningOnHasChildren : true, callback : function(result) {
								if(result) {
									that.remove(bean.id);
								} else {
									OPENIAM.Modal.Warn({
			                            message : localeManager["openiam.ui.delete.relationship.confirmation.delete"],
			                            buttons : true,
			                            OK : {
			                                text : localeManager["openiam.ui.common.execute"],
			                                onClick : function() {
			                                    OPENIAM.Modal.Close();
			                                    that.remove(bean.id);
			                                }
			                            },
			                            Cancel : {
			                                text : localeManager["openiam.ui.common.cancel"],
			                                onClick : function() {
			                                    OPENIAM.Modal.Close();
			                                }
			                            }
			                        });
								}
							}});
						} else {
							that.remove(bean.id);
						}
					}
                },
                preventOnclickEvent : OPENIAM.ENV.PreventOnClick,
                sortEnable:true,
				hasEditButton : !(OPENIAM.ENV.PreventOnClick),
				onEdit : function(bean) {
					window.location.href = args.entityURL + "?id=" + bean.id;
				},
                onProvision: function(bean) {
                    that.provision(bean.id);
                },
                onDeprovision: function(bean) {
                    that.deprovision(bean.id);
                },
				emptyResultsText : args.emptyResultsText,
				theadInputElements : args.customHeaders,
				onAppendDone : function() {
					if(!preventInputHeaders) {
						var submit = this.find("#searchBtn");
						if($.isFunction(args.onSearchClick)) {
							submit.click(function() {
								args.onSearchClick();
							});
						}
					}
				},
				onInfo : args.onInfo
            });
		},
		addOrRemove : function(args) {
			var data = {};
			data["userId"] = OPENIAM.ENV.UserId;
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
            data["userId"] = OPENIAM.ENV.UserId;
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
            data["userId"] = OPENIAM.ENV.UserId;
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
			OPENIAM.UserEntitlements.Common.load({
				customHeaders : ["", "", "", OPENIAM.UserEntitlements.getButton({buttonTitle : localeManager["openiam.ui.entitlements.resource.add"]})],
				onSearchClick : function() {
					$("#editDialog").resourceDialogSearch({
						searchTargetElmt : "#searchResultsContainer",
						excludeMenus : true,
						onSearchResultClick : function(bean) {
							OPENIAM.UserEntitlements.Resource.add(bean.id);
							return false;
						}
					});
				},
				entityType : "resource",
				onInfo : function(bean) {
					$("#editDialog").entitlementsEntityView({entityType : "resource", entityId : bean.id, action : "draw"});
				},
				columns : [
                    localeManager["openiam.ui.shared.resource.name"],
                    localeManager["openiam.ui.common.resource.type"],
                    localeManager["openiam.ui.common.risk"],
					localeManager["openiam.ui.common.actions"]
				],
                columnsMap:["name", "resourceType", "risk"],
				entityURL : "editResource.html",
				ajaxURL : "rest/api/entitlements/getResourcesForUser",
				buttonTitle : localeManager["openiam.ui.shared.resource.search"],
                hasProvisionButton : true,
                hasDeprovisionButton : true,
                emptyResultsText:localeManager["openiam.ui.user.entitlement.resource.not.found"],
                dialogTitle:localeManager["openiam.ui.shared.resource.search"],
                emptySearchResultsText:localeManager["openiam.ui.shared.resource.search.empty"],
				target : this,
				getAdditionalDataRequestObject : function() {
					var obj = {};
					obj.ignoreMenus = true;
					return obj;
				}
			});
		},
		add : function(id) {
			OPENIAM.UserEntitlements.Common.addOrRemove({
				entityRequestParamName : "resourceId",
				entityId : id,
				url : "addUserToResource.html",
				target : this
			});
		},
		remove : function(id) {
			OPENIAM.UserEntitlements.Common.addOrRemove({
				entityRequestParamName : "resourceId",
				entityId : id,
				url : "removeUserFromResource.html",
				target : this
			});
		},
        provision : function(id) {
            OPENIAM.UserEntitlements.Common.onProvision({
                entityRequestParamName : "resourceId",
                entityId : id,
                url : "provisionUserByResource.html",
                target : this
            });
        },
        deprovision : function(id) {
            OPENIAM.UserEntitlements.Common.onDeprovision({
                entityRequestParamName : "resourceId",
                entityId : id,
                url : "deprovisionUserByResource.html",
                target : this
            });
        }
	},
	Organizations : {
		load : function() {
			OPENIAM.UserEntitlements.Common.load({
				customHeaders : ["", "", OPENIAM.UserEntitlements.getButton({buttonTitle : localeManager["openiam.ui.entitlements.organization.add"]})],
				modalAjaxURL : "rest/api/entitlements/searchOrganizations",
				columns : [
                    localeManager["openiam.ui.common.organization.name"],
                    localeManager["openiam.ui.common.organization.type"],
					localeManager["openiam.ui.common.actions"]
				],
				onSearchClick : function() {
					$("#editDialog").organizationDialogSearch({
						searchTargetElmt : "#searchResultsContainer",
						onSearchResultClick : function(bean) {
							OPENIAM.UserEntitlements.Organizations.add(bean.id);
							return false;
						}
					});
				},
                columnsMap:["name", "type"],
				entityURL : "editOrganization.html",
				ajaxURL : "rest/api/entitlements/getOrganizationsForUser",
				buttonTitle : localeManager["openiam.ui.shared.organization.search"],
				placeholder : localeManager["openiam.ui.shared.organization.type.name"],
                emptyResultsText:localeManager["openiam.ui.user.entitlement.organization.not.found"],
                dialogTitle:localeManager["openiam.ui.shared.organization.search"],
                emptySearchResultsText:localeManager["openiam.ui.shared.organization.search.empty"],
                hasProvisionButton: false,
                hasDeprovisionButton : false,
				target : this
			});
		},
		add : function(id) {
			OPENIAM.UserEntitlements.Common.addOrRemove({
				entityRequestParamName : "organizationId",
				entityId : id,
				url : "addUserToOrganization.html",
				target : this
			});
		},
		remove : function(id) {
			OPENIAM.UserEntitlements.Common.addOrRemove({
				entityRequestParamName : "organizationId",
				entityId : id,
				url : "removeUserFromOrganization.html",
				target : this
			});
		},
        provision : function(id) {
            OPENIAM.UserEntitlements.Common.onProvision({
                entityRequestParamName : "orgId",
                entityId : id,
                url : "provisionUserByOrg.html",
                target : this
            });
        },
        deprovision : function(id) {
            OPENIAM.UserEntitlements.Common.onDeprovision({
                entityRequestParamName : "orgId",
                entityId : id,
                url : "deprovisionUserByOrg.html",
                target : this
            });
        }
	},
	Roles : {
		load : function() {
			OPENIAM.UserEntitlements.Common.load({
				customHeaders : ["", "", OPENIAM.UserEntitlements.getButton({buttonTitle : localeManager["openiam.ui.entitlements.role.add"]})],
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
							OPENIAM.UserEntitlements.Roles.add(bean.id);
							return false;
						}
					});
				},
				entityType : "role",
				onInfo : function(bean) {
					$("#editDialog").entitlementsEntityView({entityType : "role", entityId : bean.id, action : "draw"});
				},
                columnsMap:["name", "managedSysName"],
				entityURL : "editRole.html",
				ajaxURL : "rest/api/entitlements/getRolesForUser",
				buttonTitle : localeManager["openiam.ui.shared.role.search"],
				placeholder : localeManager["openiam.ui.shared.type.role.name"],
                hasProvisionButton : true,
                hasDeprovisionButton : true,
                emptyResultsText:localeManager["openiam.ui.user.entitlement.role.not.found"],
                dialogTitle:localeManager["openiam.ui.shared.organization.search"],
                emptySearchResultsText:localeManager["openiam.ui.shared.organization.search.empty"],
				target : this
			});
		},
		add : function(id) {
			OPENIAM.UserEntitlements.Common.addOrRemove({
				entityRequestParamName : "roleId",
				entityId : id,
				url : "addUserToRole.html",
				target : this
			});
		},
		remove : function(id) {
			OPENIAM.UserEntitlements.Common.addOrRemove({
				entityRequestParamName : "roleId",
				entityId : id,
				url : "removeUserFromRole.html",
				target : this
			});
		},
        provision : function(id) {
            OPENIAM.UserEntitlements.Common.onProvision({
                entityRequestParamName : "roleId",
                entityId : id,
                url : "provisionUserByRole.html",
                target : this
            });
        },
        deprovision : function(id) {
            OPENIAM.UserEntitlements.Common.onDeprovision({
                entityRequestParamName : "roleId",
                entityId : id,
                url : "deprovisionUserByRole.html",
                target : this
            });
        }
	},
	Group : {
		load : function() {
			OPENIAM.UserEntitlements.Common.load({
				customHeaders : ["", "", OPENIAM.UserEntitlements.getButton({buttonTitle : localeManager["openiam.ui.entitlements.group.add"]})],
				onSearchClick : function() {
					$("#editDialog").groupDialogSearch({
						searchTargetElmt : "#searchResultsContainer",
						onSearchResultClick : function(bean) {
							OPENIAM.UserEntitlements.Group.add(bean.id);
							return false;
						}
					});
				},
				entityType : "group",
				onInfo : function(bean) {
					$("#editDialog").entitlementsEntityView({entityType : "group", entityId : bean.id, action : "draw"});
				},
				columns : [
                    localeManager["openiam.ui.shared.group.name"],
					localeManager["openiam.ui.shared.managed.system"],
					localeManager["openiam.ui.common.actions"]
				],
                columnsMap:["name", "managedSysName"],
				entityURL : "editGroup.html",
				ajaxURL : "rest/api/entitlements/getGroupsForUser",
				buttonTitle : localeManager["openiam.ui.shared.group.search"],
				placeholder : localeManager["openiam.ui.shared.group.type.name"],
                hasProvisionButton : true,
                hasDeprovisionButton : true,
                emptyResultsText:localeManager["openiam.ui.user.entitlement.group.not.found"],
                dialogTitle:localeManager["openiam.ui.shared.group.search"],
                emptySearchResultsText:localeManager["openiam.ui.shared.group.search.empty"],
				target : this,
				getAdditionalDataRequestObject : function() {
					var obj = {};
					return obj;
				}
			});
		},
		add : function(id) {
			OPENIAM.UserEntitlements.Common.addOrRemove({
				entityRequestParamName : "groupId",
				entityId : id,
				url : "addUserToGroup.html",
				target : this
			});
		},
		remove : function(id) {
			OPENIAM.UserEntitlements.Common.addOrRemove({
				entityRequestParamName : "groupId",
				entityId : id,
				url : "removeUserFromGroup.html",
				target : this
			});
		},
        provision : function(id) {
            OPENIAM.UserEntitlements.Common.onProvision({
                entityRequestParamName : "groupId",
                entityId : id,
                url : "provisionUserByGroup.html",
                target : this
            });
        },
        deprovision : function(id) {
            OPENIAM.UserEntitlements.Common.onDeprovision({
                entityRequestParamName : "groupId",
                entityId : id,
                url : "deprovisionUserByGroup.html",
                target : this
            });
        }
	}
};

$(document).ready(function() {
	OPENIAM.UserEntitlements.Load.onReady();
});

$(document).load(function() {
	
});