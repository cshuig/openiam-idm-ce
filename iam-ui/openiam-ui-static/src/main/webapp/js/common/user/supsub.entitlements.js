OPENIAM = window.OPENIAM || {};
OPENIAM.UserEntitlements = {
    Load : {
        onReady : function() {
            switch(OPENIAM.ENV.EntitlementType) {
                case "superiors":
                    OPENIAM.UserEntitlements.Superiors.load();
                    break;
                case "subordinates":
                    OPENIAM.UserEntitlements.Subordinates.load();
                    break;
                default:
                    break;
            }
        }
    },
    Common : {
        load : function(args) {

            $("#userResultsArea").empty();

            var that = args.target;
            var searchInput = document.createElement("input"); $(searchInput).attr("type", "submit"); searchInput.className = "redBtn"; searchInput.id = "search"; $(searchInput).attr("value", args.buttonTitle);
            var inputelements = ["", "", "", "", "", searchInput];
            $("#entitlementsContainer").entitlemetnsTable({
                columnHeaders : args.columns,
                columnsMap: args.columnsMap,
                ajaxURL : args.ajaxURL,
                entityUrl : args.entityUrl,
                entityType : "USER",
                entityURLIdentifierParamName : "id",
                requestParamIdName : "id",
                requestParamIdValue : OPENIAM.ENV.UserId,
                pageSize : 10,
                deleteOptions : {
                	preventWarning : (OPENIAM.ENV.Text.DeleteWarn != null && OPENIAM.ENV.Text.DeleteWarn != undefined),
                	warningMessage : localeManager["openiam.ui.delete.relationship.confirmation.delete"],
                	onDelete : function(bean) {
	                    that.remove(bean.id);
	                }
                },
                emptyResultsText : args.emptyResultsText,
                theadInputElements : inputelements,
                onAppendDone : function() {
                    this.find("#search").click(function() {
                        $("#dialog").userSearchForm({
                            afterFormAppended : function() {
                                $("#dialog").dialog({autoOpen: false, draggable : false, resizable : false, title : args.buttonTitle, width: "auto", position : "center"});
                                $("#dialog").dialog("open");
                            },
                            onSubmit : function(json) {
                                json.targetUserId = OPENIAM.ENV.UserId;
                                $("#userResultsArea").userSearchResults({
                                    "jsonData" : json,
                                    "page" : 0,
                                    "size" : 20,
                                    initialSortColumn : "name",
                                    initialSortOrder : "ASC",
                                    url : OPENIAM.ENV.ContextPath + "/" + args.modalAjaxURL,
                                    emptyFormText : localeManager["openiam.ui.common.user.search.empty"],
                                    emptyResultsText : args.emptyResultsText,
                                    columnHeaders : [
                                    	localeManager["openiam.ui.common.name"], 
                                    	localeManager["openiam.ui.common.phone.number"], 
                                    	localeManager["openiam.ui.common.email.address"], 
                                    	localeManager["openiam.ui.webconsole.user.status"], 
                                    	localeManager["openiam.ui.webconsole.user.accountStatus"]
                                    ],
                                    onAppendDone : function() {
                                        $("#dialog").dialog("close");
                                        $("#userResultsArea").prepend("<div class=\"\">" + args.userTableDescription + "</div>");
                                    },
                                    onEntityClick : function(bean) {
                                        that.add(bean.id);
                                    }
                                });
                            }
                        });
                    });
                }
            });
        },
        addOrRemove : function(args) {
            var that = args.target;
            var data = {};
            data["userId"] = OPENIAM.ENV.UserId;
            data[args.entityRequestParamName] = args.entityId;
            data["description"] = $("#taskDescription").val();
            $.ajax({
                url : args.url,
                "data" : data,
                type: "POST",
                dataType : "json",
                success : function(data, textStatus, jqXHR) {
                    if(data.status == 200) {
                        OPENIAM.Modal.Success({message : data.successMessage, showInterval : 2000, onIntervalClose : function() {
                            that.load();
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
        showWarningBeforeAction : function(message, callback) {
            $("#dialog").dialog("close");
            var inputs = [];
            inputs.push({
                elmtType : "textarea",
                className : "rounded",
                id : "taskDescription",
                label : "Enter an optional description"
            });
            setTimeout(function() {
                OPENIAM.Modal.Warn({
                    inputs : inputs,
                    message : message,
                    buttons : true,
                    OK : {
                        text : localeManager["openiam.ui.common.submit"],
                        onClick : function() {
                            OPENIAM.Modal.Close();
                            callback();
                        }
                    },
                    Cancel : {
                        text : localeManager["openiam.ui.common.cancel"],
                        onClick : function() {
                            OPENIAM.Modal.Close();
                        }
                    }
                });
            }, 200);
        }
    },
    Superiors : {
        load : function() {
            var $this = this;
            OPENIAM.UserEntitlements.Common.load({
                modalAjaxURL : "rest/api/users/searchSuperiors",
                columns : [
                	localeManager["openiam.ui.common.name"], 
                	localeManager["openiam.ui.common.phone.number"], 
                	localeManager["openiam.ui.common.email.address"], 
                	localeManager["openiam.ui.webconsole.user.status"], 
                	localeManager["openiam.ui.webconsole.user.accountStatus"], 
                	localeManager["openiam.ui.common.actions"]
                ],
                columnsMap:["name","phone","email","userStatus","accountStatus"],
                entityUrl : "supSub.html",
                ajaxURL : "rest/api/users/getSuperiors",
                buttonTitle : localeManager["openiam.ui.user.sup.button.search"],
                emptyResultsText : localeManager["openiam.ui.user.sup.empty.result"],
                userTableDescription:localeManager["openiam.ui.user.sup.table.description"],
                target : $this
            });
        },
        add : function(id) {
            var $this = this;
            var callback = function() {
                OPENIAM.UserEntitlements.Common.addOrRemove({
                    entityRequestParamName : "supervisorId",
                    entityId : id,
                    url : "addSuperior.html",
                    target: $this
                });
            }
            if(OPENIAM.ENV.Text.AddWarn != null && OPENIAM.ENV.Text.AddWarn != undefined) {
                OPENIAM.UserEntitlements.Common.showWarningBeforeAction(OPENIAM.ENV.Text.AddWarn, callback);
            } else {
                callback();
            }
        },
        remove : function(id) {
            var $this = this;
            var callback = function() {
                OPENIAM.UserEntitlements.Common.addOrRemove({
                    entityRequestParamName : "supervisorId",
                    entityId : id,
                    url : "removeSuperior.html",
                    target: $this
                });
            }

            if(OPENIAM.ENV.Text.DeleteWarn != null && OPENIAM.ENV.Text.DeleteWarn != undefined) {
                OPENIAM.UserEntitlements.Common.showWarningBeforeAction(OPENIAM.ENV.Text.DeleteWarn, callback);
            } else {
                callback();
            }
        }
    },
    Subordinates : {
        load : function() {
            var $this = this;
            OPENIAM.UserEntitlements.Common.load({
                modalAjaxURL : "rest/api/users/searchSubordinates",
                columns : [
                	localeManager["openiam.ui.common.name"], 
                	localeManager["openiam.ui.common.phone.number"], 
                	localeManager["openiam.ui.common.email.address"], 
                	localeManager["openiam.ui.webconsole.user.status"], 
                	localeManager["openiam.ui.webconsole.user.accountStatus"], 
                	localeManager["openiam.ui.common.actions"]
                ],
                columnsMap:["name","phone","email","userStatus","accountStatus"],
                entityUrl : "supSub.html",
                ajaxURL : "rest/api/users/getSubordinates",
                buttonTitle : localeManager["openiam.ui.user.sub.button.search"],
                emptyResultsText : localeManager["openiam.ui.user.sub.empty.result"],
                userTableDescription:localeManager["openiam.ui.user.sub.table.description"],
                target : $this
            });
        },
        add : function(id) {
            var $this = this;
            var callback = function() {
                OPENIAM.UserEntitlements.Common.addOrRemove({
                    entityRequestParamName : "subordinateId",
                    entityId : id,
                    url : "addSubordinate.html",
                    target : $this
                });
            }
            if(OPENIAM.ENV.Text.AddWarn != null && OPENIAM.ENV.Text.AddWarn != undefined) {
                OPENIAM.UserEntitlements.Common.showWarningBeforeAction(OPENIAM.ENV.Text.AddWarn, callback);
            } else {
                callback();
            }
        },
        remove : function(id) {
            var $this = this;
            var callback = function() {
                OPENIAM.UserEntitlements.Common.addOrRemove({
                    entityRequestParamName : "subordinateId",
                    entityId : id,
                    url : "removeSubordinate.html",
                    target : $this
                });
            }

            if(OPENIAM.ENV.Text.DeleteWarn != null && OPENIAM.ENV.Text.DeleteWarn != undefined) {
                OPENIAM.UserEntitlements.Common.showWarningBeforeAction(OPENIAM.ENV.Text.DeleteWarn, callback);
            } else {
                callback();
            }
        }
    }
};

$(document).ready(function() {
    OPENIAM.UserEntitlements.Load.onReady();
});

$(document).load(function() {

});