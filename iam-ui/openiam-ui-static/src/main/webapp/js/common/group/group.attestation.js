OPENIAM = window.OPENIAM || {};
OPENIAM.GroupAttestation = {
    removedUsers:[],
    Load : {
        onReady : function() {
            OPENIAM.GroupAttestation.User.load();

            $("#certifyBtn").click(function(){
                OPENIAM.GroupAttestation.User.certify();
            });
        }
    },
    Common : {
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
                entityUrl : "javascript:void(0)",
                entityType : "USER",
                entityURLIdentifierParamName : "id",
                requestParamIdName : "id",
                requestParamIdValue : OPENIAM.ENV.GroupId,
                pageSize : 20,
                deleteOptions : {
                    warningMessage : localeManager["openiam.ui.delete.relationship.confirmation.delete"],
                    onDelete : function(bean) {
                        that.remove(bean);
                    }
                },
                emptyResultsText : OPENIAM.ENV.Text.EmptyChildren,
                showPageSizeSelector:true,
                sortEnable:true,
                theadInputElements : ["", "", "", "", "", ""],
                onAppendDone : function() {
                }
            });
        },
        remove : function(bean) {
            var target = this;
            var data = {};
            data["groupId"] = OPENIAM.ENV.GroupId;
            data["userId"] = bean.id;
            $.ajax({
                url : "removeUserFromGroup.html",
                "data" : data,
                type: "POST",
                dataType : "json",
                success : function(data, textStatus, jqXHR) {
                    if(data.status == 200) {
                        OPENIAM.GroupAttestation.removedUsers.push(bean);
                        OPENIAM.Modal.Success({message : data.successMessage, showInterval : 1000, onIntervalClose : function() {
                            target.load(0);
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
        certify: function(){
            var data = {};
            data["taskId"] = OPENIAM.ENV.ATTESTATION_TASK_ID;
            data["groupId"] = OPENIAM.ENV.GroupId;
            data["groupName"]= OPENIAM.ENV.GroupName;
            data["removedUsers"] = (OPENIAM.GroupAttestation.removedUsers.length>0)?OPENIAM.GroupAttestation.removedUsers:null;

            $.ajax({
                url : "groupCertify.html",
                data : JSON.stringify(data),
                type : "POST",
                dataType : "json",
                contentType : "application/json",
                success : function(data, textStatus, jqXHR) {
                    if(data.status == 200) {
                        OPENIAM.Modal.Success({message : data.successMessage, showInterval : 1000, onIntervalClose : function() {
                            if(data.redirectURL)
                                window.location.href=data.redirectURL;
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
    }
};

$(document).ready(function() {
    OPENIAM.GroupAttestation.Load.onReady();
});

$(document).load(function() {

});