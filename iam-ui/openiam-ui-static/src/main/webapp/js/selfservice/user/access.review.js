OPENIAM = window.OPENIAM || {};
OPENIAM.ENV = window.OPENIAM.ENV || {};
OPENIAM.NewAccessReview = {
    init : function() {
        $("#identityInformation .identityInfoTitle .openiam-close-icon").click(function(){
            $("#identityInformation").hide();
        });

        if(OPENIAM.ENV.RECERTIFICATION_TASK_ID){
            OPENIAM.NewAccessReview.Recetrification.init();
        } else {
            switch(OPENIAM.ENV.EntitlementType) {
                case "groups":
                    OPENIAM.NewAccessReview.Group.init();
                    break;
                case "roles":
                    OPENIAM.NewAccessReview.Role.init();
                    break;
                case "resources":
                    OPENIAM.NewAccessReview.Resource.init();
                    break;
                default:
                    break;
            }
        }
    },
    Common:{
        initialSearchBean:{
            name:null,
            description:null,
            showExceptionsFlag:null,
            showRolesFlag:null,
            showGroupsFlag:null,
            showManagesSysFlag:null
        },
        showIdentityInfo:function(a, loginId){
            $.ajax({
                    url : "accessreview/logininfo.html",
                    "data" : {loginId: loginId},
                    type: "GET",
                    dataType : "json",
                    success : function(data, textStatus, jqXHR) {
                        $("#identityMetadata .name").html(data.login);
                        $("#identityMetadata .mngsys").html(data.managedSys);
                        $("#identityMetadata .status").html(data.status);
                        $("#identityMetadata .lastLogin").html(data.lastLogin);
                        $("#identityMetadata .pwdExp").html(data.pwdExpAsStr);
                        $("#identityInformation").css("top", $(a).offset().top);
                        $("#identityInformation").show();
                    },
                    error : function(jqXHR, textStatus, errorThrown) {
                        OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
                    }
            });
        },
        requestData: function(args){
            var $this = this;
            var requestData = {userId : OPENIAM.ENV.UserId };
            if(args.searchFilter){
                requestData = $.extend(requestData, args.searchFilter);
                OPENIAM.NewAccessReview.Common.initialSearchBean=args.searchFilter;
            }

            $.ajax({
                    url : args.url,
                    "data" : JSON.stringify(requestData),
                    type: "POST",
                    dataType : "json",
                    contentType: "application/json",
                    success : function(data, textStatus, jqXHR) {
                        var beans = data.beans;
                        beans = ($.isArray(beans)) ? beans : null;
                        $this._drawTree(args.getSettings(beans));

                        var exceptions = data.exceptions;
                        exceptions = ($.isArray(exceptions)) ? exceptions : null;
                        $this._drawExceptions({target:args.target,
                                               beans: exceptions
                                              });
                    },
                    error : function(jqXHR, textStatus, errorThrown) {
                        OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
                    }
            });
        },
        _drawTree : function(args){
            var treeArgs = {elementSelector: "#entitlementsContainer",
                            createEnabled : true,
                            createBtnId : "addObjectBtn",
                            createText : localeManager["openiam.ui.common.add"],
                            filterEnabled: true,
                            filterFields: OPENIAM.NewAccessReview.Common._getFilterDialogFields(),
                            filterSearchBean: OPENIAM.NewAccessReview.Common.initialSearchBean,
                            onDeleteClick:function(bean) {
                                if(bean.beanType=="role"){
                                    OPENIAM.NewAccessReview.Role.remove(bean.id);
                                } else if(bean.beanType=="group"){
                                    OPENIAM.NewAccessReview.Group.remove(bean.id);
                                }else if(bean.beanType=="resource"){
                                    OPENIAM.NewAccessReview.Resource.remove(bean.id);
                                }
                            },
                            onCreateClick:function() {
                                OPENIAM.NewAccessReview.Common.onAddBtnClick();
                                },
                            onFilterClick : function(searchBean){
                                args.target.init(searchBean);
                                }
                            };
            treeArgs = $.extend(treeArgs, args);
            this._drawData(treeArgs);
        },
        _drawExceptions : function(args){
            if(args.beans!=null && args.beans.length>0){
                $("#exceptionsTitle").show();
                $("#exceptionsContainer").show();

                var excArgs = {target: args.target,
                               elementSelector: "#exceptionsContainer",
                               emptyMessage:localeManager["openiam.ui.selfservice.user.access.review.noresource"],
                               deleteEnabledField:true,
                               beans: args.beans
                              }
                this._drawData(excArgs);
            } else {
                $("#exceptionsTitle").hide();
                $("#exceptionsContainer").hide();
            }
        },
        _drawData : function(args) {
            var target = args.target;

            $(args.elementSelector).treeGrid({
                                    tableTitle : args.tableTitle,
                                    emptyMessage : args.emptyMessage,
                                    createEnabled : args.createEnabled,
                                    createBtnId : args.createBtnId,
                                    createText : args.createText,
                                    editEnabledField : false,
                                    deleteEnabledField : args.deleteEnabledField,
                                    objectArray : args.beans,
                                    headerFields:[
                                    	localeManager["openiam.ui.common.name"],
                                        localeManager["openiam.ui.common.description"],
                                        localeManager["openiam.ui.common.risk"],
                                        localeManager["openiam.ui.common.status"],
                                        localeManager["openiam.ui.selfservice.user.access.review.identity"]
                                    ],
                                    fieldNames: ["name", "description", "risk", "status", "identity"],
                                    greyOutOnDelete : false,
                                    actionsColumnName : args.actionsColumnName,
                                    filterEnabled: args.filterEnabled,
                                    filterFields: args.filterFields,
                                    filterSearchBean: args.filterSearchBean,
                                    fieldDisplayers : {
                                        identity : function(bean, fieldName) {
                                            var idA="";
                                            if(bean.identity){
                                                idA = document.createElement("a"); $(idA).attr("href", "javascript:void(0);");
                                                $(idA).append(bean.identity).attr("id",bean.loginId);
                                                $(idA).click(function() {
                                                    OPENIAM.NewAccessReview.Common.showIdentityInfo(this, bean.loginId);
                                                });
                                            }
                                            return idA;
                                        }
                                    },
                                    showIconTitle:true,
                                   // getIconTitle: OPENIAM.NewAccessReview.Common.getIconTitle,
                                    onDeleteClick : args.onDeleteClick,
                                    onCreateClick : args.onCreateClick,
                                    equals : function(obj1, obj2) {
                                        return obj1.id = obj2.id;
                                    },
                                    onFilterClick : args.onFilterClick,
                                    checkboxEnabled:args.checkboxEnabled,
                                    checkedInitialState:args.checkedInitialState,
                                    checkboxVisibility:args.checkboxVisibility,
                                    createCheckboxChecker:args.createCheckboxChecker
            });
        },
        _getFilterDialogFields: function(){
            return [
                    {fieldName: "name", type:"text",label:localeManager["openiam.ui.common.name"]},
                    {fieldName: "description", type:"text", label:localeManager["openiam.ui.common.description"]},
                    {fieldName: "risk", type:"select",label:localeManager["openiam.ui.common.resource.risk"], items : OPENIAM.ENV.RiskList},
                    {fieldName: "showExceptionsFlag", type:"checkbox", label:localeManager["openiam.ui.selfservice.user.access.review.show.exceptions"]},
                    {fieldName: "showRolesFlag", type:"checkbox", label:localeManager["openiam.ui.selfservice.user.access.review.show.roles"]},
                    {fieldName: "showGroupsFlag", type:"checkbox", label:localeManager["openiam.ui.selfservice.user.access.review.show.groups"]},
                    {fieldName: "showManagesSysFlag", type:"checkbox", label:localeManager["openiam.ui.selfservice.user.access.review.show.managed.systems"]}
                 ];
        },
        onAddBtnClick:function(){
            OPENIAM.Modal.Warn({
                title : localeManager["openiam.ui.selfservice.user.access.review.select.object.type.to.add"],
                buttons : true,
                OK : {
                    text : localeManager["openiam.ui.common.role"],
                    onClick : function() {
                        OPENIAM.Modal.Close();
                        $("#editDialog").roleDialogSearch({
                            emptyResultsText : localeManager["openiam.ui.selfservice.user.access.review.role.empty.search"],
                            dialogTitle : localeManager["openiam.ui.selfservice.user.access.review.role.dialog.title"],
                            searchTargetElmt : "#searchResultsContainer",
                            saveBtnTxt : localeManager["openiam.ui.common.search"],
                            onSearchResultClick : function(bean) {
                                OPENIAM.NewAccessReview.Role.add(bean.id);
                                return false;
                            }
                        });
                    }
                },
                No : {
                    text : localeManager["openiam.ui.selfservice.user.access.review.resource"],
                    className:"redBtn",
                    onClick : function() {
                        OPENIAM.Modal.Close();
                        $("#editDialog").resourceDialogSearch({
                            searchTargetElmt : "#searchResultsContainer",
                            onSearchResultClick : function(bean) {
                                OPENIAM.NewAccessReview.Resource.add(bean.id);
                                return false;
                            }
                        });
                    }
                },
                Cancel : {
                    text : localeManager["openiam.ui.common.group"],
                    className:"redBtn",
                    onClick : function() {
                        OPENIAM.Modal.Close();
                        $("#editDialog").groupDialogSearch({
                            searchTargetElmt : "#searchResultsContainer",
                            onSearchResultClick : function(bean) {
                                OPENIAM.NewAccessReview.Group.add(bean.id);
                                return false;
                            }
                        });
                    }
                }
            });
        },
        addOrRemove : function(args){
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
                           args.target.init(0);
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
    Role:{
        init: function(searchBean){
            var $this = this;
            OPENIAM.NewAccessReview.Common.requestData({target: $this,
                                                        url : "accessReview/getRolesView.html",
                                                        searchFilter: searchBean,
                                                        getSettings: function(data){
                                                            return {target: $this,
                                                                    tableTitle: "Roles View",
                                                                    emptyMessage:localeManager["openiam.ui.selfservice.user.access.review.norole"],
                                                                    actionsColumnName :localeManager["openiam.ui.selfservice.user.access.review.action"],
                                                                    deleteEnabledField:true,
                                                                    beans: data
                                                                }
                                                         }
            });
        },
        add : function(id){
            OPENIAM.NewAccessReview.Common.addOrRemove({
                entityRequestParamName : "roleId",
                entityId : id,
                url : "addUserToRole.html",
                target : this
            });
        },
        remove : function(id){
            OPENIAM.NewAccessReview.Common.addOrRemove({
                entityRequestParamName : "roleId",
                entityId : id,
                url : "removeUserFromRole.html",
                target : this
            });
        }
    },
    Group:{
        init: function(searchBean){
            var $this = this;
            OPENIAM.NewAccessReview.Common.requestData({target: $this,
                                                        url : "accessReview/getGroupView.html",
                                                        searchFilter: searchBean,
                                                        getSettings: function(data){
                                                            return {target: $this,
                                                                    tableTitle: localeManager["openiam.ui.selfservice.user.access.review.group.view"],
                                                                    emptyMessage:localeManager["openiam.ui.selfservice.user.access.review.nogroup"],
                                                                    actionsColumnName :localeManager["openiam.ui.selfservice.user.access.review.action"],
                                                                    deleteEnabledField:true,
                                                                    beans: data,
                                                                    searchFilter: searchBean
                                                                }
                                                        }
            });
        },
        add : function(id){
            OPENIAM.NewAccessReview.Common.addOrRemove({
                entityRequestParamName : "groupId",
                entityId : id,
                url : "addUserToGroup.html",
                target : this
            });
        },
        remove : function(id){
            OPENIAM.NewAccessReview.Common.addOrRemove({
                entityRequestParamName : "groupId",
                entityId : id,
                url : "removeUserFromGroup.html",
                target : this
            });
        }
    },
    Resource:{
        init: function(searchBean){
            var $this = this;
            OPENIAM.NewAccessReview.Common.requestData({target: $this,
                                                        url : "accessReview/getResourceView.html",
                                                        searchFilter: searchBean,
                                                        getSettings: function(data){
                                                            return {target: $this,
                                                                tableTitle: localeManager["openiam.ui.selfservice.user.access.review.resource.view"],
                                                                emptyMessage:localeManager["openiam.ui.selfservice.user.access.review.noresource"],
                                                                actionsColumnName :localeManager["openiam.ui.selfservice.user.access.review.action"],
                                                                deleteEnabledField:true,
                                                                beans: data,
                                                                searchFilter: searchBean
                                                            }
                                                        }
            });
        },
        add : function(id){
            OPENIAM.NewAccessReview.Common.addOrRemove({
                entityRequestParamName : "resourceId",
                entityId : id,
                url : "addUserToResource.html",
                target : this
            });
        },
        remove : function(id){
            OPENIAM.NewAccessReview.Common.addOrRemove({
                entityRequestParamName : "resourceId",
                entityId : id,
                url : "removeUserFromResource.html",
                target : this
            });
        }
    },
    Recetrification:{
        init: function(searchBean){
            var $this = this;

            $("#dontCertifyBtn").click(function(){
                OPENIAM.NewAccessReview.Recetrification.dontCertifyAccess();
                $(this).hide();
            });

            $("#certifyBtn").click(function(){
                OPENIAM.NewAccessReview.Recetrification.certifyAccess();
            });

            OPENIAM.NewAccessReview.Common.requestData({target: $this,
                                                        url : "accessReview/getResourceView.html",
                                                        searchFilter: searchBean,
                                                        getSettings: function(data){
                                                            return {target: $this,
                                                                tableTitle: localeManager["openiam.ui.selfservice.user.access.review.resource.view"],
                                                                emptyMessage:localeManager["openiam.ui.selfservice.user.access.review.noresource"],
                                                                deleteEnabledField:true,
                                                                beans: data,
                                                                searchFilter: searchBean,
                                                                createEnabled:false,
                                                                filterEnabled: false,
                                                                actionsColumnName:localeManager["openiam.ui.selfservice.user.access.review.action"],
                                                                checkboxEnabled:true,
                                                                checkedInitialState:true,
                                                                checkboxVisibility:false,
                                                                createCheckboxChecker:function(bean){
                                                                    return bean && bean.data
                                                                        && (bean.data.beanType=="resource" || bean.data.beanType=="role");
                                                               }
                                                            }
                                                        }
            });
        },
        certifyAccess: function(){

            var data = {};
            var beans = $("#entitlementsContainer").treeGrid("getBeans");


            data["userId"] = OPENIAM.ENV.UserId;
            data["userName"] = OPENIAM.ENV.UserName;
            data["taskId"] = OPENIAM.ENV.RECERTIFICATION_TASK_ID;
            data["selectedItems"]=$("#entitlementsContainer").treeGrid("getSelectedItems");
            data["unSelectedItems"]=[];

            var selectedIds = {};

            for(var i=0; i< data["selectedItems"].length;i++){
                selectedIds[data["selectedItems"][i].id] = data["selectedItems"][i];
            }

//            $.each(data["selectedItems"], function(bean){
//                selectedIds[bean.id] = bean;
//            });
            for(var i=0; i< beans.length;i++){
                if(!selectedIds.hasOwnProperty(beans[i].id)){
                    data["unSelectedItems"].push(beans[i]);
                }
            }
//            $.each(beans, function(bean){
//                if(!selectedIds.hasOwnProperty(bean.id)){
//                    data["unSelectedItems"].push(bean);
//                }
//            });

            $.ajax({
                url : "accessReview/certify.html",
                data : JSON.stringify(data),
                type : "POST",
                dataType : "json",
                contentType : "application/json",
                success : function(data, textStatus, jqXHR) {
                    if(data.status == 200) {
                        OPENIAM.Modal.Success({message : data.successMessage, showInterval : 1000, onIntervalClose : function() {
                            if(data.redirectURL)
                                window.location.href=data.redirectURL;
    //                            args.target.load(0);
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
        dontCertifyAccess: function(){
            $("#entitlementsContainer").treeGrid("showCheckBoxes");
        }
    }
};

$(document).ready(function() {
    OPENIAM.NewAccessReview.init();
});