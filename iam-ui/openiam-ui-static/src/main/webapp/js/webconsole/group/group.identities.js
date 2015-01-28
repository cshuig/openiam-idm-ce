OPENIAM = window.OPENIAM || {};
OPENIAM.Group = window.OPENIAM.Group || {};

OPENIAM.Group.Identity = {
    init : function() {
        var mySearch = document.createElement("input");
            $(mySearch).attr("type", "button");
            $(mySearch).attr("value", localeManager["openiam.ui.user.identities.button.new"]);
              mySearch.className = "redBtn";
              mySearch.id = "createNew";

        var inputelements = [];
        inputelements.push("");
        inputelements.push("");
        inputelements.push("");
        inputelements.push("");
        inputelements.push("");
        inputelements.push("");
        var $this = this;
        $("#entitlementsContainer").entitlemetnsTable({
            columnHeaders : [
                localeManager["openiam.ui.common.identity"],
                localeManager["openiam.ui.shared.managed.system"],
                localeManager["openiam.ui.common.last.updated"],
                localeManager["openiam.ui.common.status"],
                localeManager["openiam.ui.user.identities.prov.status"],
                localeManager["openiam.ui.common.actions"]
            ],
            columnsMap : ["identity", "managedSys", "lastUpdateFormatted", "status", "provStatus"],
            hasEditButton : true,
            useTrueFalseStringsOnBoolean : true,
            deleteOptions : {
                warningMessage : localeManager["openiam.ui.user.identities.delete.message"],
                onDelete : function(bean) {
                    OPENIAM.Group.Identity.onDelete(bean);
                },
                isDeletable : function(bean) {
                    return (bean.managedSysId != '0' && bean.status == 'INACTIVE');
                }
            },
            theadInputElements : inputelements,
            onEdit : function(bean) {
                OPENIAM.Group.Identity.onEdit(bean);
            },
            ajaxURL : "getGroupIdentities.html",
            requestParamIdName : "id",
            requestParamIdValue : OPENIAM.ENV.GroupId,
            groupId : OPENIAM.ENV.GroupId,
            pageSize : 10,
            emptyResultsText : localeManager["openiam.ui.group.identities.empty.result"],
            onAppendDone : function() {
                var editFields = [
                    {fieldName: "identity", type:"text",label:localeManager["openiam.ui.common.principal"], required:true},
                    {fieldName: "managedSysId", type:"select", label:localeManager["openiam.ui.shared.managed.system"], readonly:true, items:OPENIAM.ENV.ManagedSystems},
                    {fieldName: "id", type:"hidden", label:""},
                    {fieldName: "status", type:"select",label:localeManager["openiam.ui.common.status"], items: [{id:"ACTIVE", name : localeManager["openiam.ui.common.active"]}, {id:"INACTIVE", name:localeManager["openiam.ui.common.inactive"]}]},
                    {fieldName: "createDateFormatted", type:"text",label:localeManager["openiam.ui.user.identities.created.on"], readonly:true},
                    {fieldName: "lastUpdateFormatted", type:"text",label:localeManager["openiam.ui.common.last.updated"], readonly:true}
                ];

                $("#editDialog").modalEdit({
                    fields: editFields,
                    dialogTitle: localeManager["openiam.ui.group.identities.edit.dialog.title"],
                    onSubmit: function(bean){
                        $this.saveLogin(bean);
                    }
                });
            }
        });
    },
    onDelete : function(bean) {
        $.ajax({
            url : "deleteGroupIdentity.html",
            data : {id : bean.id},
            type: "POST",
            dataType : "json",
            success : function(data, textStatus, jqXHR) {
                if(data.status == 200) {
                    OPENIAM.Modal.Success({message : data.successMessage, showInterval : 2000, onIntervalClose : function() {
                        window.location.reload(true);
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
    onEdit : function(bean) {
        var dateCtrl = $("input.date");
        dateCtrl.datepicker({ dateFormat: OPENIAM.ENV.DateFormatDP , changeMonth:true, changeYear:true}).attr('readonly','readonly');
        $("#editDialog").modalEdit("show", bean);
    },
    saveLogin : function(bean) {
        bean.referredObjectId = OPENIAM.ENV.GroupId;
        $.ajax({
            url : "editGroupIdentity.html",
            data : JSON.stringify(bean),
            type: "POST",
            dataType : "json",
            contentType: "application/json",
            success : function(data, textStatus, jqXHR) {
                if(data.status == 200) {
                    OPENIAM.Modal.Success({message : data.successMessage, showInterval : 2000, onIntervalClose : function() {
                        if(data.redirectURL != null && data.redirectURL != undefined && data.redirectURL.length > 0) {
                            window.location.href = data.redirectURL;
                        } else {
                            window.location.reload(true);
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
    }
};

$(document).ready(function() {
    OPENIAM.Group.Identity.init();
});

$(window).load(function() {

});