OPENIAM = window.OPENIAM || {};
OPENIAM.GroupBulkOP = {
    init : function() {
        OPENIAM.GroupBulkOP.hideConteiner("#usersToAdd");
        OPENIAM.GroupBulkOP.hideConteiner("#usersToDelete");
        $("#applyBtn").hide();


        $("#operation").change(function() {
            var val = $(this).val();

            if(val =='ADD_ENTITLEMENT'){
                OPENIAM.GroupBulkOP.hideConteiner("#usersToDelete");
                OPENIAM.GroupBulkOP.initUserToAdd();
            } else if(val =='DELETE_ENTITLEMENT'){
                OPENIAM.GroupBulkOP.hideConteiner("#usersToAdd");
                OPENIAM.GroupBulkOP.hideConteiner("#searchResultsContainer");
                OPENIAM.GroupBulkOP.initUserToDelete();
            } else {
                OPENIAM.GroupBulkOP.hideConteiner("#usersToDelete");
                OPENIAM.GroupBulkOP.hideConteiner("#usersToAdd");
                OPENIAM.GroupBulkOP.hideConteiner("#searchResultsContainer");
            }
        });

        $("#applyBtn").click(function(){
            OPENIAM.GroupBulkOP.startProvision();
        });
    },
    hideConteiner: function(selectorId){
        $(selectorId).empty().hide();
    },
    initUserToAdd: function(){
        $("#usersToAdd").userSearchForm({
            onSubmit : function(json) {
                $("#searchResultsContainer").userSearchResults({
                    "jsonData" : json,
                    page : 0,
                    size : 20,
                    initialSortColumn : "name",
                    initialSortOrder : "ASC",
                    url : "rest/api/users/search",
                    emptyFormText : localeManager["openiam.ui.common.user.search.empty"],
                    emptyResultsText : localeManager["openiam.ui.common.user.search.no.results"],
                    isSelectAllowed:true,
                    onCheckCallback: function () {
                        var cnt = $("#searchResultsContainer").userSearchResults("countSelectedItems");
                        var text = localeManager["openiam.ui.user.selected"]+": "+cnt;
                        $("#selectedUsers").html(text);
                        if(cnt>0) {
                            $("#applyBtn").show();
                        } else{
                            $("#applyBtn").hide();
                        }
                    }
                });
            }
        });
        $("#usersToAdd").show();
    },
    initUserToDelete: function(){
        $("#usersToDelete").entitlemetnsTable({
            columnHeaders : [
                localeManager["openiam.ui.common.name"],
                localeManager["openiam.ui.common.phone.number"],
                localeManager["openiam.ui.common.email.address"],
                localeManager["openiam.ui.webconsole.user.status"],
                localeManager["openiam.ui.webconsole.user.accountStatus"]
            ],
            columnsMap:["name","phone","email","userStatus","accountStatus"],
            ajaxURL : "rest/api/entitlements/getUsersForGroup",
            entityType : "USER",
            entityURLIdentifierParamName : "id",
            requestParamIdName : "id",
            requestParamIdValue : OPENIAM.ENV.GroupId,
            pageSize : 20,
            emptyResultsText : localeManager["openiam.ui.shared.group.users.empty"],
            showPageSizeSelector:true,
            sortEnable:true,
            isSelectAllowed:true,
            onCheckCallback: function () {
                var cnt = $("#searchResultsContainer").entitlemetnsTable("countSelectedItems");
                var text = localeManager["openiam.ui.user.selected"] + ": " + cnt;
                $("#selectedUsers").html(text);
                if(cnt>0) {
                    $("#applyBtn").show();
                } else{
                    $("#applyBtn").hide();
                }
            }
        });
        $("#usersToDelete").show();
    },
    startProvision:function(){
        var operation = $("#operation").val();
        var bulkOperationBean = {};
        bulkOperationBean.userIds=null;


        if(operation =='ADD_ENTITLEMENT'){
            bulkOperationBean.userIds=$("#usersToDelete").userSearchResults("getSelectedItems");
        } else if(operation =='DELETE_ENTITLEMENT'){
            bulkOperationBean.userIds=$("#usersToDelete").entitlemetnsTable("getSelectedItems");
        }
        bulkOperationBean.operations=[];
        var operationBean = {};
        operationBean.objectId=OPENIAM.ENV.GroupId;
        operationBean.objectName=OPENIAM.ENV.GroupName;
        operationBean.operation=operation;
        operationBean.objectType="GROUP";

        bulkOperationBean.operations.push(operationBean);

        $.ajax({
            url : "startGroupBulkOperation.html",
            data : JSON.stringify(bulkOperationBean),
            type : "POST",
            dataType : "json",
            contentType : "application/json",
            success : function(data, textStatus, jqXHR) {
                if (data.status == 200) {
                    OPENIAM.Modal.Success({message : data.successMessage, showInterval : 2000, onIntervalClose : function() {
                        if(data.redirectURL != null && data.redirectURL != undefined && data.redirectURL.length > 0) {
                            window.location.href = data.redirectURL;
                        } else {
                            window.location.reload(true);
                        }
                    }});
                } else {
                    OPENIAM.Modal.Error({
                        errorList : data.errorList
                    });
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
    }
};

$(document).ready(function() {
    OPENIAM.GroupBulkOP.init();
});