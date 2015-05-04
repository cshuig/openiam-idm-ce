OPENIAM = window.OPENIAM || {};
OPENIAM.UserEntitlements = {
    hasAddBtn:false,
    hasEditBtn:false,
    hasDeleteBtn:false,
    hasInfoBtn:false,
    hasProvBtn:false,
    hasDeprovBtn:false,
    getButton : function(args) {
        var mySearch = $(document.createElement("input"));
        mySearch.attr("type", "submit").attr("value", args.buttonTitle).addClass("redBtn").attr("id", "searchBtn");
        return mySearch;
    },
    Load : {
        onReady : function() {
            OPENIAM.UserEntitlements.Buttons.init();
            OPENIAM.UserEntitlements.Organizations.load();
        }
    },
    Buttons: {
        init: function(){
            if (OPENIAM.ENV.buttonsMenu != null && OPENIAM.ENV.buttonsMenu != 'undefined') {
                OPENIAM.UserEntitlements.Buttons = Object.create(OPENIAM.MenuTree);
                OPENIAM.UserEntitlements.Buttons.initialize({
                    tree : OPENIAM.ENV.buttonsMenu,
                    toHTML : function() {
                        if(this.getRoot() != null) {
                            var node = this.getRoot().getChild();
                            while(node != null) {
                                node.toHTML();
                                node = node.getNext();
                            }
                        }
                        return "";
                    },
                    onNodeClick : function() {
                    },
                    toNodeHtml : function() {
                        var btnId = this.getId();
                        if(("ORGANIZATIONS_ADD_BTN")==btnId){
                            OPENIAM.UserEntitlements.hasAddBtn=true;
                        } else if(("ORGANIZATIONS_EDT_BTN")==btnId){
                            OPENIAM.UserEntitlements.hasEditBtn=true;
                        } else if(("ORGANIZATIONS_DEL_BTN")==btnId){
                            OPENIAM.UserEntitlements.hasDeleteBtn=true;
                        } else if(("ORGANIZATIONS_INFO_BTN")==btnId){
                            OPENIAM.UserEntitlements.hasInfoBtn=true;
                        }else if(("ORGANIZATIONS_PROV_BTN")==btnId){
                            OPENIAM.UserEntitlements.hasProvBtn=true;
                        }else if(("ORGANIZATIONS_DEPROV_BTN")==btnId){
                            OPENIAM.UserEntitlements.hasDeprovBtn=true;
                        }
                        return "";
                    }
                });
                OPENIAM.UserEntitlements.Buttons.toHTML()
            }
        }
    },
    Organizations : {
        load : function() {
            var that = this;
            var customHeader = OPENIAM.UserEntitlements.hasAddBtn ?["", "",  OPENIAM.UserEntitlements.getButton({buttonTitle : localeManager["openiam.ui.entitlements.organization.add"]})]:null;
            var preventInputHeaders = !$.isArray(customHeader);
            $("#entitlementsContainer").entitlemetnsTable({
                columnHeaders : [
                                    localeManager["openiam.ui.common.organization.name"],
                                    localeManager["openiam.ui.common.organization.type"],
                                    localeManager["openiam.ui.common.actions"]
                                ],
                columnsMap : ["name", "type"],
                ajaxURL : "rest/api/entitlements/getOrganizationsForUser",
                entityUrl : OPENIAM.UserEntitlements.hasEditBtn ? "editOrganization.html" : "javascript:void(0);",
                entityURLIdentifierParamName : "id",
                requestParamIdName : "id",
                requestParamIdValue : OPENIAM.ENV.UserId,
                pageSize : 20,
                hasProvisionButton : OPENIAM.UserEntitlements.hasProvBtn,
                hasDeprovisionButton : OPENIAM.UserEntitlements.hasDeprovBtn,
                showPageSizeSelector:true,
                deleteOptions : {
                    hasDeleteBtn: OPENIAM.UserEntitlements.hasDeleteBtn,
                    warningMessage : localeManager["openiam.ui.delete.relationship.confirmation.delete"],
                    preventWarning : false,
                    onDelete : function(bean) {
                            that.remove(bean);
                    }
                },
                preventOnclickEvent : OPENIAM.ENV.PreventOnClick,
                sortEnable:true,
                hasEditButton : OPENIAM.UserEntitlements.hasEditBtn,
                onEdit : (OPENIAM.UserEntitlements.hasEditBtn) ? function(bean) {
                    window.location.href = args.entityURL + "?id=" + bean.id;
                } : null,
                onProvision: function(bean) {
                    that.provision(bean.id);
                },
                onDeprovision: function(bean) {
                    that.deprovision(bean.id);
                },
                emptyResultsText : localeManager["openiam.ui.user.entitlement.organization.not.found"],
                theadInputElements : customHeader,
                onAppendDone : function() {
                    if(!preventInputHeaders) {
                        var submit = this.find("#searchBtn");
                        if($.isFunction(OPENIAM.UserEntitlements.Organizations.searchClick)) {
                            submit.click(function() {
                                OPENIAM.UserEntitlements.Organizations.searchClick();
                            });
                        }
                    }
                },
                onInfo : null
            });
        },
        searchClick: function(){
            $("#editDialog").organizationDialogSearch({
                searchTargetElmt : "#searchResultsContainer",
                onAdd : function(bean) {
                    OPENIAM.UserEntitlements.Organizations.add(bean);
                    return false;
                }
            });
        },
        postData: function(args){
            var data = {};
            data["userId"] = OPENIAM.ENV.UserId;
            data[args.entityRequestParamName] = args.entity.id;
            $.ajax({
                url : args.url,
                "data" : data,
                type: "POST",
                dataType : "json",
                success : function(data, textStatus, jqXHR) {
                    if(data.status == 200) {
                        OPENIAM.Modal.Success({message : data.successMessage, showInterval : 1000, onIntervalClose : function() {
                            if($.isFunction(args.postCallback)) {
                                args.postCallback();
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
        add : function(bean) {
            var that=this;
            that.postData({
                entityRequestParamName : "organizationId",
                entity : bean,
                url : "addUserToOrganization.html",
                postCallback : function(){
                    that.load(0);
                }
            });
        },
        remove : function(bean) {
            var that=this;
            that.postData({
                entityRequestParamName : "organizationId",
                entity : bean,
                url : "removeUserFromOrganization.html",
                postCallback : function(){
                    that.load(0);
                }
            });
        },
        provision : function(id) {
            var that=this;
            that.onProvision({
                entityRequestParamName : "orgId",
                entityId : id,
                url : "provisionUserByOrg.html",
                postCallback : function(){
                    that.load(0);
                }
            });
        },
        deprovision : function(id) {
            var that=this;
            that.onDeprovision({
                entityRequestParamName : "orgId",
                entityId : id,
                url : "deprovisionUserByOrg.html",
                postCallback : function(){
                    that.load(0);
                }
            });
        }
        //addOrRemove : function(args) {
        //    var data = {};
        //    data["userId"] = OPENIAM.ENV.UserId;
        //    data[args.entityRequestParamName] = args.entity.id;
        //    $.ajax({
        //        url : args.url,
        //        "data" : data,
        //        type: "POST",
        //        dataType : "json",
        //        success : function(data, textStatus, jqXHR) {
        //            if(data.status == 200) {
        //                OPENIAM.Modal.Success({message : data.successMessage, showInterval : 1000, onIntervalClose : function() {
        //                    //args.target.load(0);
        //                    if(args.isAdd){
        //                        $("#entitlementsContainer").entitlemetnsTable("addRow", args.entity);
        //                    } else {
        //                        $("#entitlementsContainer").entitlemetnsTable("deleteRow", args.entity);
        //                    }
        //
        //                }});
        //            } else {
        //                OPENIAM.Modal.Error({errorList : data.errorList});
        //            }
        //        },
        //        error : function(jqXHR, textStatus, errorThrown) {
        //            OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
        //        }
        //    });
        //},
        //onProvision : function(args) {
        //    var data = {};
        //    data["userId"] = OPENIAM.ENV.UserId;
        //    data[args.entityRequestParamName] = args.entityId;
        //    $.ajax({
        //        url : args.url,
        //        "data" : data,
        //        type: "POST",
        //        dataType : "json",
        //        success : function(data, textStatus, jqXHR) {
        //            if(data.status == 200) {
        //                OPENIAM.Modal.Success({message : data.successMessage, showInterval : 1000, onIntervalClose : function() {
        //                    args.target.load(0);
        //                }});
        //            } else {
        //                OPENIAM.Modal.Error({errorList : data.errorList});
        //            }
        //        },
        //        error : function(jqXHR, textStatus, errorThrown) {
        //            OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
        //        }
        //    });
        //},
        //onDeprovision : function(args) {
        //    var data = {};
        //    data["userId"] = OPENIAM.ENV.UserId;
        //    data[args.entityRequestParamName] = args.entityId;
        //    $.ajax({
        //        url : args.url,
        //        "data" : data,
        //        type: "POST",
        //        dataType : "json",
        //        success : function(data, textStatus, jqXHR) {
        //            if(data.status == 200) {
        //                OPENIAM.Modal.Success({message : data.successMessage, showInterval : 1000, onIntervalClose : function() {
        //                    args.target.load(0);
        //                }});
        //            } else {
        //                OPENIAM.Modal.Error({errorList : data.errorList});
        //            }
        //        },
        //        error : function(jqXHR, textStatus, errorThrown) {
        //            OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
        //        }
        //    });
        //}
    }
    //Organizations : {
    //    load : function() {
    //        OPENIAM.UserEntitlements.Common.load({
    //            //modalAjaxURL : "rest/api/entitlements/searchOrganizations",
    //
    //            //buttonTitle : localeManager["openiam.ui.shared.organization.search"],
    //            //placeholder : localeManager["openiam.ui.shared.organization.type.name"],
    //            //emptyResultsText:localeManager["openiam.ui.user.entitlement.organization.not.found"],
    //            //dialogTitle:localeManager["openiam.ui.shared.organization.search"],
    //            //emptySearchResultsText:localeManager["openiam.ui.shared.organization.search.empty"],
    //            //hasProvisionButton: false,
    //            //hasDeprovisionButton : false,
    //            //target : this
    //        });
    //    },
    //
    //}

};

$(document).ready(function() {
    OPENIAM.UserEntitlements.Load.onReady();
});

$(document).load(function() {

});