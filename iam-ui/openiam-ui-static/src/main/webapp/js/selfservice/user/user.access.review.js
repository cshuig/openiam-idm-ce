OPENIAM = window.OPENIAM || {};

OPENIAM.AccessReview = {
    init : function() {
        OPENIAM.AccessReview.Common.optionMap={
                                                RoleBean: {
                                                    options:{url:"accessReview/getRoleEntitlementsTypes.html",
                                                        emptyMessage:OPENIAM.ENV.Text.NoChildren,
                                                        headerFields:[localeManager["openiam.ui.common.name"]],
                                                        fieldNames:["name"],
                                                        showHeader:false},
                                                    ChildRoles:{options:{url:"accessReview/getChildRoles.html",
                                                        emptyMessage:OPENIAM.ENV.Text.NoChildRole,
                                                        headerFields:[localeManager["openiam.ui.shared.role.name"], localeManager["openiam.ui.shared.managed.system"]],
                                                        fieldNames:["name", "managedSysName"],
                                                        showHeader:true}},
                                                    Groups: {options:{url:"accessReview/getGroupsForRole.html",
                                                        emptyMessage:OPENIAM.ENV.Text.NoGroupForRole,
                                                        headerFields:[localeManager["openiam.ui.shared.group.name"], localeManager["openiam.ui.shared.managed.system"]],
                                                        fieldNames: ["name", "managedSysName"],
                                                        showHeader:true}},
                                                    Resources:{options:{url:"accessReview/getResourcesTypesForRole.html",
                                                        emptyMessage:OPENIAM.ENV.Text.NoResourceForRole,
                                                        headerFields:[localeManager["openiam.ui.common.name"]],
                                                        fieldNames: ["name"],
                                                        showHeader:false}},
                                                    ResourceType:{options:{url:"accessReview/getResourcesForRoleAndType.html",
                                                        emptyMessage:OPENIAM.ENV.Text.NoResource,
                                                        headerFields:[
                                                        	localeManager["openiam.ui.common.name"], localeManager["openiam.ui.common.description"]],
                                                        fieldNames: ["name", "description"],
                                                        showHeader:true}}
                                                },
                                                GroupBean:{
                                                    options:{url:"accessReview/getGroupEntitlementsTypes.html",
                                                        emptyMessage:OPENIAM.ENV.Text.NoChildren,
                                                        headerFields:[localeManager["openiam.ui.common.name"]],
                                                        fieldNames:["name"],
                                                        showHeader:false},
                                                    ChildGroups:{options:{url:"accessReview/ChildGroups.html",
                                                        emptyMessage:OPENIAM.ENV.Text.NoChildGroup,
                                                        headerFields:[[localeManager["openiam.ui.shared.group.name"], localeManager["openiam.ui.shared.managed.system"]],
                                                        fieldNames: ["name", "managedSysName"],
                                                        showHeader:true}},
                                                    Roles:{options:{url:"accessReview/getRolesForGroup.html",
                                                        emptyMessage:OPENIAM.ENV.Text.NoRoleForGroup,
                                                        headerFields:[localeManager["openiam.ui.shared.role.name"], localeManager["openiam.ui.shared.managed.system"]],
                                                        fieldNames: ["name", "managedSysName"],
                                                        showHeader:true}},
                                                    Resources:{options:{url:"accessReview/getResourcesTypesForGroup.html",
                                                        emptyMessage:OPENIAM.ENV.Text.NoResourceForGroup,
                                                        headerFields:[localeManager["openiam.ui.common.name"]],
                                                        fieldNames: ["name"],
                                                        showHeader:false}},
                                                    ResourceType:{options:{url:"accessReview/getResourcesForGroupAndType.html",
                                                        emptyMessage:OPENIAM.ENV.Text.NoResource,
                                                        headerFields:[
                                                        	localeManager["openiam.ui.common.name"], localeManager["openiam.ui.common.description"]],
                                                        fieldNames: ["name", "description"],
                                                        showHeader:true}}
                                                },
                                                ResourceBean:{
                                                    options:{url:"accessReview/getResourcesEntitlementsTypes.html",
                                                        emptyMessage:OPENIAM.ENV.Text.NoChildren,
                                                        headerFields:[localeManager["openiam.ui.common.name"]],
                                                        fieldNames:["name"],
                                                        showHeader:false},
                                                    ChildResources:{options:{url:"accessReview/getResourcesTypesForResource.html",
                                                        emptyMessage:OPENIAM.ENV.Text.NoChildResource,
                                                        headerFields:[localeManager["openiam.ui.shared.role.name"], localeManager["openiam.ui.shared.managed.system"]],
                                                        fieldNames:["name", "managedSysName"],
                                                        showHeader:false}},
                                                    Roles:{options:{url:"accessReview/getRolesForResource.html",
                                                        emptyMessage:OPENIAM.ENV.Text.NoRolesForResource,
                                                        headerFields:[localeManager["openiam.ui.shared.role.name"], localeManager["openiam.ui.shared.managed.system"]],
                                                        fieldNames: ["name", "managedSysName"],
                                                        showHeader:true}},
                                                    Groups: {options:{url:"accessReview/getGroupsForResource.html",
                                                        emptyMessage:OPENIAM.ENV.Text.NoGroupsForResource,
                                                        headerFields:[[localeManager["openiam.ui.shared.group.name"], localeManager["openiam.ui.shared.managed.system"]],
                                                        fieldNames: ["name", "managedSysName"],
                                                        showHeader:true}},
                                                    ResourceType:{options:{url:"accessReview/getResourcesForResourceAndType.html",
                                                        emptyMessage:OPENIAM.ENV.Text.NoResource,
                                                        headerFields:[
                                                        	localeManager["openiam.ui.common.name"], localeManager["openiam.ui.common.description"]],
                                                        fieldNames: ["name", "description"],
                                                        showHeader:true}},
                                                    Identity:{options:{url:"accessReview/getIdentityForResource.html",
                                                        emptyMessage:OPENIAM.ENV.Text.NoResource,
                                                        headerFields:["Identity", "Status", "Last login", "Password Exp."],
                                                        fieldNames: ["login", "status","lastLogin","pwdExpAsStr"],
                                                        showHeader:true}}
                                                },
                                                User:{
                                                    ResourceType:{options:{url:"accessReview/getResourcesForUserAndType.html",
                                                        emptyMessage:OPENIAM.ENV.Text.NoResource,
                                                        headerFields:[
                                                        	localeManager["openiam.ui.common.name"], localeManager["openiam.ui.common.description"]],
                                                        fieldNames: ["name", "description"],
                                                        showHeader:true}}
                                                }
                                              };


        switch(OPENIAM.ENV.EntitlementType) {
            case "groups":
//                OPENIAM.AccessReview.Group.init();
                break;
            case "roles":
               // OPENIAM.AccessReview.Role.init();
                break;
            case "resources":
//                OPENIAM.AccessReview.Resource.init();
                break;
            default:
                break;
        }
    },
    Common:{
        optionMap: {},
        requestData: function(args){
            var $this = this;
            $.ajax({
                url : args.url,
                "data" : args.data,
                type: "GET",
                dataType : "json",
                success : function(data, textStatus, jqXHR) {
                    var beans = data.beans;
                    beans = ($.isArray(beans)) ? beans : null;
                    $this._drawData(args.getSettings(beans));
                },
                error : function(jqXHR, textStatus, errorThrown) {
                    OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
                }
            });
        },
        _drawData : function(args) {
            var target = args.target;
            args.domElement.persistentTable({
                tableTitle : args.tableTitle,
                emptyMessage : args.emptyMessage,
                createEnabled : false,
                editEnabledField : false,
                deleteEnabledField : args.deleteEnabledField,
                objectArray : args.beans,
                headerFields : args.headerFields,
                fieldNames : args.fieldNames,
                greyOutOnDelete : false,
                actionsColumnName : OPENIAM.ENV.Text.ActionsText,
                isExpandable: args.isExpandable,
                showHeader: args.showHeader,

                onDeleteClick : function(obj) {

                },
                equals : function(obj1, obj2) {
                    return obj1.id = obj2.id;
                },
                onExpand : function(row) {
                    target.onExpand(row);
                },
                onCollapse : function(row) {
                    var expandRow = $(row).siblings('#child-'+$(row).attr("id"));
                    expandRow.children("td").empty();
                }
            });
        },
        onExpand: function(row){
            var $this = this;
            var childRowId='child-'+$(row).attr("id");


            var bean = $(row).data("entity");
            var expandRow = $(row).siblings('#'+childRowId);

            var option=null;
            var requestParams=null;
            if(bean.beanType=="EntitlementTypeBean"){
                option = OPENIAM.AccessReview.Common.optionMap[bean.parentBeanType][bean.entitlementType].options;
                requestParams={id : bean.parentId };
                if(bean.parentBeanType=="ResourceBean"){
                    requestParams.userId=OPENIAM.ENV.UserId;
                }
                if(bean.entitlementType=="ResourceType"){
                    requestParams.resourceType=bean.id;
                }
            } else{
                option = OPENIAM.AccessReview.Common.optionMap[bean.beanType].options;
                requestParams={id : bean.id };
            }

            OPENIAM.AccessReview.Common.requestData({target: $this,
                                                     url : option.url,
                                                     data : requestParams,
                                                     getSettings: function(data){
                                                            return {target: $this,
                                                                    domElement: expandRow.find("td"),
                                                                    emptyMessage: option.emptyMessage,
                                                                    deleteEnabledField:true,
                                                                    beans: data,
                                                                    headerFields:option.headerFields,
                                                                    fieldNames: option.fieldNames,
                                                                    isExpandable: true,
                                                                    showHeader:option.showHeader
                                                                }
                                                            }
                                                    });
        }

    },
    Group:{
        init: function(){
            var $this = this;
            OPENIAM.AccessReview.Common.requestData({target: $this,
                                                     url : "rest/api/entitlements/getGroupsForUser",
                                                     data :  { id : OPENIAM.ENV.UserId, from:-1, size:-1, deepFlag:true},
                                                     getSettings: function(data){
                                                        return {target: $this,
                                                                tableTitle: localeManager["openiam.ui.common.groups"],
                                                                domElement: $("#entitlementsContainer"),
                                                                emptyMessage:OPENIAM.ENV.Text.NoGroup,
                                                                deleteEnabledField:true,
                                                                beans: data,
                                                                headerFields:[[localeManager["openiam.ui.shared.group.name"], localeManager["openiam.ui.shared.managed.system"]],
                                                                fieldNames: ["name", "managedSysName"],
                                                                isExpandable: true
                                                               }
                                                        }
                                                    });
        },
        onExpand: function(row){
            var $this = this;
            var bean = $(row).data("entity");
            var expandRow = $(row).siblings('#child-'+$(row).attr("id"));

            switch(bean.beanType) {
                case "EntitlementTypeBean":
                    $this.loadEntitlements(row);
                    break;
                case "GroupBean":
                    OPENIAM.AccessReview.Common.requestData({target: $this,
                                                             url : "accessReview/getGroupEntitlementsTypes.html",
                                                             data :  { id : bean.id },
                                                             getSettings: function(data){
                                                                return {target: $this,
                                                                        domElement: expandRow.find("td"),
                                                                        emptyMessage:OPENIAM.ENV.Text.NoChildren,
                                                                        deleteEnabledField:true,
                                                                        beans: data,
                                                                        headerFields:[localeManager["openiam.ui.common.name"]],
                                                                        fieldNames: ["name"],
                                                                        showHeader: false,
                                                                        isExpandable: true
                                                                       }
                                                                }
                                                            });
                    break;
                default:
                    break;
            }
        },
        loadEntitlements: function(row){
            var $this = this;
            var bean = $(row).data("entity");
            var expandRow = $(row).siblings('#child-'+$(row).attr("id"));

            switch(bean.entitlementType) {
                case "ChildGroups":
                    OPENIAM.AccessReview.Common.requestData({target: $this,
                                                             url : "rest/api/entitlements/getChildGroups",
                                                             data :  { id : bean.id, from:-1, size:-1, deepFlag:true },
                                                             getSettings: function(data){
                                                                return {target: $this,
                                                                        domElement: expandRow.find("td"),
                                                                        emptyMessage:OPENIAM.ENV.Text.NoChildGroup,
                                                                        deleteEnabledField:true,
                                                                        beans: data,
                                                                        headerFields:[[localeManager["openiam.ui.shared.group.name"], localeManager["openiam.ui.shared.managed.system"]],
                                                                        fieldNames: ["name", "managedSysName"],
                                                                        isExpandable: true
                                                                       }
                                                                }
                                                            });
                    break;
                case "Roles":
                    OPENIAM.AccessReview.Common.requestData({target: $this,
                                                             url : "rest/api/entitlements/getRolesForGroup",
                                                             data :  { id : bean.id, from:-1, size:-1 },
                                                             getSettings: function(data){
                                                                return {target: $this,
                                                                        domElement: expandRow.find("td"),
                                                                        emptyMessage:OPENIAM.ENV.Text.NoRoleForGroup,
                                                                        deleteEnabledField:true,
                                                                        beans: data,
                                                                        headerFields:[[localeManager["openiam.ui.shared.role.name"], localeManager["openiam.ui.shared.managed.system"]],
                                                                        fieldNames: ["name", "managedSysName"]
                                                                        }
                                                                }
                                                            });
                    break;
                case "Resources":
                    OPENIAM.AccessReview.Common.requestData({target: $this,
                                                             url : "rest/api/entitlements/getResourcesForGroup",
                                                             data : { id : bean.id, from:-1, size:-1 },
                                                             getSettings: function(data){
                                                                return {target: $this,
                                                                        domElement: expandRow.find("td"),
                                                                        emptyMessage:OPENIAM.ENV.Text.NoResourceForGroup,
                                                                        deleteEnabledField:true,
                                                                        beans: data,
                                                                        headerFields:[
                                                                        	localeManager["openiam.ui.common.name"],
                                                                            localeManager["openiam.ui.common.description"],
                                                                            localeManager["openiam.ui.common.resource.type"]
                                                                        ],
                                                                        fieldNames: ["name", "description","resourceType"]
                                                                        }
                                                                }
                                                            });
                    break;
                default:
                    break;
            }
        }
    },
    Role:{
        init: function(){
            var $this = this;
            OPENIAM.AccessReview.Common.requestData({target: $this,
                                                     url : "accessReview/getRolesForUser.html",
                                                     data :  { id : OPENIAM.ENV.UserId },
                                                     getSettings: function(data){
                                                        return {target: $this,
                                                                tableTitle: "Roles",
                                                                domElement: $("#entitlementsContainer"),
                                                                emptyMessage:OPENIAM.ENV.Text.NoRole,
                                                                deleteEnabledField:true,
                                                                beans: data,
                                                                headerFields:[[localeManager["openiam.ui.shared.role.name"], localeManager["openiam.ui.shared.managed.system"]],
                                                                fieldNames: ["name", "managedSysName"],
                                                                isExpandable: true
                                                               }
                                                        }
                                                    });
        },
        onExpand: function(row){
            OPENIAM.AccessReview.Common.onExpand(row);
        }
    },
    Resource:{
        init: function(){
            var $this = this;
            OPENIAM.AccessReview.Common.requestData({target: $this,
                                                     url : "accessReview/getResourcesTypesForUser.html",
                                                     data :  { id : OPENIAM.ENV.UserId},
                                                     getSettings: function(data){
                                                         return {target: $this,
                                                             tableTitle: localeManager["openiam.ui.common.resource.type"],
                                                             domElement: $("#entitlementsContainer"),
                                                             emptyMessage:OPENIAM.ENV.Text.NoResource,
                                                             deleteEnabledField:true,
                                                             beans: data,
                                                             headerFields:[localeManager["openiam.ui.common.name"]],
                                                             fieldNames: ["name"],
                                                             isExpandable: true
                                                         }
                                                     }});
        },
        onExpand: function(row){
            OPENIAM.AccessReview.Common.onExpand(row);
//            var $this = this;
//            var bean = $(row).data("entity");
//            var expandRow = $(row).siblings('#child-'+$(row).attr("id"));
//            switch(bean.beanType) {
//                case "EntitlementTypeBean":
//                    $this.loadEntitlements(row);
//                    break;
//                case "ResourceBean":
//                    OPENIAM.AccessReview.Common.requestData({target: $this,
//                                                             url : "accessReview/getResourcesEntitlementsTypes.html",
//                                                             data :  { id : bean.id },
//                                                             getSettings: function(data){
//                                                                    return {target: $this,
//                                                                        domElement: expandRow.find("td"),
//                                                                        emptyMessage:OPENIAM.ENV.Text.NoChildren,
//                                                                        deleteEnabledField:true,
//                                                                        beans: data,
//                                                                        headerFields:[localeManager["openiam.ui.common.name"]],
//                                                                        fieldNames: ["name"],
//                                                                        showHeader: false,
//                                                                        isExpandable: true
//                                                                    }
//                                                             }});
//                    break;
//                case "ResourceTypeBean":
//                    OPENIAM.AccessReview.Common.requestData({target: $this,
//                                                             url : "accessReview/getResourcesForUserAndType.html",
//                                                             data :  { id : OPENIAM.ENV.UserId, resourceType: bean.id },
//                                                             getSettings: function(data){
//                                                                return {target: $this,
//                                                                    tableTitle: "Resource",
//                                                                    domElement: expandRow.find("td"),
//                                                                    emptyMessage:OPENIAM.ENV.Text.NoResource,
//                                                                    deleteEnabledField:true,
//                                                                    beans: data,
//                                                                    headerFields:[localeManager["openiam.ui.common.name"], "Description"],
//                                                                    fieldNames: ["name", "description"],
//                                                                    isExpandable: true
//                                                                }
//                                                             }});
//                    break;
//                default:
//                    break;
//            }
        },
        loadEntitlements: function(row){
            var $this = this;
            var bean = $(row).data("entity");
            var expandRow = $(row).siblings('#child-'+$(row).attr("id"));

            switch(bean.entitlementType) {
                case "ChildResources":
                    OPENIAM.AccessReview.Common.requestData({target: $this,
                                                             url : "rest/api/entitlements/getChildResources",
                                                             data :  { id : bean.id, from:-1, size:-1, deepFlag:true },
                                                             getSettings: function(data){
                                                                return {target: $this,
                                                                        domElement: expandRow.find("td"),
                                                                        emptyMessage:OPENIAM.ENV.Text.NoChildResource,
                                                                        deleteEnabledField:true,
                                                                        beans: data,
                                                                        headerFields:[
                                                                        	localeManager["openiam.ui.common.name"],
                                                                            localeManager["openiam.ui.common.description"]
                                                                        ],
                                                                        fieldNames: ["name", "description"],
                                                                        isExpandable: true
                                                                       }
                                                                }
                                                             });
                    break;
                case "Groups":
                    OPENIAM.AccessReview.Common.requestData({target: $this,
                                                             url : "rest/api/entitlements/getGroupsForResource",
                                                             data :  { id : bean.id, from:-1, size:-1 },
                                                             getSettings: function(data){
                                                                return {target: $this,
                                                                        domElement: expandRow.find("td"),
                                                                        emptyMessage:OPENIAM.ENV.Text.NoGroupsForResource,
                                                                        deleteEnabledField:true,
                                                                        beans: data,
                                                                        headerFields:[[localeManager["openiam.ui.shared.group.name"], localeManager["openiam.ui.shared.managed.system"]],
                                                                        fieldNames: ["name", "managedSysName"]
                                                                       }
                                                                }
                                                            });
                    break;
                case "Roles":
                    OPENIAM.AccessReview.Common.requestData({target: $this,
                                                             url : "rest/api/entitlements/getRolesForResource",
                                                             data : { id : bean.id, from:-1, size:-1 },
                                                             getSettings: function(data){
                                                                    return {target: $this,
                                                                            domElement: expandRow.find("td"),
                                                                            emptyMessage:OPENIAM.ENV.Text.NoRolesForResource,
                                                                            deleteEnabledField:true,
                                                                            beans: data,
                                                                            headerFields:[[localeManager["openiam.ui.shared.role.name"], localeManager["openiam.ui.shared.managed.system"]],
                                                                            fieldNames: ["name", "managedSysName"]
                                                                           }
                                                                    }
                                                            });
                    break;
                default:
                    break;
            }
        }
    }
};

$(document).ready(function() {
    OPENIAM.AccessReview.init();
});