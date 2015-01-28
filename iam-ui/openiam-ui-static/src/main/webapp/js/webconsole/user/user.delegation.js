OPENIAM = window.OPENIAM || {};
OPENIAM.User = window.OPENIAM.User || {};

OPENIAM.User.Delegation = {
    initialSelectedDiv:null,
    initialSelectedDep:null,

    init: function(){
        var $this = this;
        $("#orgFilterLink").click(function(){
            OPENIAM.User.Delegation.orgSearchClick("#orgFilterContainer",OPENIAM.ENV.DEL_FILTER.organizationTypeId);
        });
        $("#divFilterLink").click(function(){
            OPENIAM.User.Delegation.orgSearchClick("#divFilterContainer",OPENIAM.ENV.DEL_FILTER.divisionTypeId);
        });
        $("#deptFilterLink").click(function(){
            OPENIAM.User.Delegation.orgSearchClick("#deptFilterContainer",OPENIAM.ENV.DEL_FILTER.departmentTypeId);
        });

        $("#groupListLink").click(function(){
            $("#editDialog").groupDialogSearch({
                searchTargetElmt : "#searchResultsContainer",
                showResultsInDialog : true,
                pageSize : 5,
                onSearchResultClick : function(bean) {
                    $this.addBean("#groupListContainer", bean);
                    return false;
                }
            });
        });
        $("#roleListLink").click(function(){
            $("#editDialog").roleDialogSearch({
                searchTargetElmt : "#searchResultsContainer",
                showResultsInDialog : true,
                pageSize : 5,
                onSearchResultClick : function(bean) {
                    $this.addBean("#roleListContainer", bean);
                    return false;
                }
            });
        });

        $("#appListLink").click(function(){
            $("#editDialog").resourceDialogSearch({
            	excludeMenus : true,
                searchTargetElmt : "#searchResultsContainer",
                showResultsInDialog : true,
                pageSize : 5,
                onSearchResultClick : function(bean) {
                    $this.addBean("#appListContainer", bean);
                    return false;
                }
            });
        });

        if(OPENIAM.ENV.DEL_FILTER){
            OPENIAM.User.Delegation.fillInData("#orgFilterContainer",OPENIAM.ENV.DEL_FILTER.orgFilter);
            OPENIAM.User.Delegation.fillInData("#divFilterContainer",OPENIAM.ENV.DEL_FILTER.divFilter);
            OPENIAM.User.Delegation.fillInData("#deptFilterContainer",OPENIAM.ENV.DEL_FILTER.deptFilter);
            OPENIAM.User.Delegation.fillInData("#groupListContainer",OPENIAM.ENV.DEL_FILTER.groupList);
            OPENIAM.User.Delegation.fillInData("#roleListContainer",OPENIAM.ENV.DEL_FILTER.roleList);
//            OPENIAM.User.Delegation.fillInData("#appListContainer",OPENIAM.ENV.DEL_FILTER.appList);


            if(OPENIAM.ENV.DEL_FILTER.mngRptFlag!=null && OPENIAM.ENV.DEL_FILTER.mngRptFlag==true){
                $("#mngRptFlag").attr("checked", "checked");
            }
            if(OPENIAM.ENV.DEL_FILTER.useOrgInhFlag!=null && OPENIAM.ENV.DEL_FILTER.useOrgInhFlag==true){
                $("#useOrgInhFlag").attr("checked", "checked");
            }
        }
    },
    fillInData : function(targetContainer, dataList){
        if(dataList && dataList.length>0){
            for(var i=0; i< dataList.length; i++){
                OPENIAM.User.Delegation.addBean(targetContainer, dataList[i]);
            }
        }
    },
    orgSearchClick: function(targetContainer, orgType){
        var $this = this;
        var types = [];
        types.push(orgType);

        $("#editDialog").organizationDialogSearch({
            searchTargetElmt : "#searchResultsContainer",
            organizationTypes : types,
            showResultsInDialog : true,
            pageSize : 5,
            onSearchResultClick : function(bean) {
                $this.addBean(targetContainer, bean);
                return false;
            }
        });
    },

    addBean : function (targetContainer, bean){
        var li = document.createElement("li");
        $(li).addClass("choice").data("entity", bean);

        var sp = document.createElement("span");
        sp.innerHTML=bean.name;

        var a = document.createElement("a");
        a.className="choice-close ui-icon ui-icon-closethick";
        a.href="javascript:void(0)";

        $(a).click(function(){
            var container = $(this).parents("ul:first");
            $(this).parent("li:first").remove();

            if(container.children("li").length==0)
                container.css("display", "none");
        });

        $(li).append(sp, a);
        $(targetContainer).append(li);

        if(!$(targetContainer).is(':visible')){
            $(targetContainer).css("display","block");
        }
    },

    saveDelegation : function() {
        this.postJSON("userDelegation.html", this.toJSON());
    },
    getSelectedItemsValues: function(optionList){
        var values = [];
        if(optionList!=null && optionList.length>0){
            for(var i=0; i<optionList.length;i++){
                var bean = $(optionList[i]).data("entity");
                if(bean!=null && bean != undefined)
                    values.push({id: bean.id});
            }
        }else{
            values =null;
        }
        return values;
    },
    toJSON : function() {
        var obj = {};

        obj.userId=OPENIAM.ENV.UserId;
        obj.orgFilter = OPENIAM.User.Delegation.getSelectedItemsValues($("#orgFilterContainer li"));
        obj.divFilter = OPENIAM.User.Delegation.getSelectedItemsValues($("#divFilterContainer li"));
        obj.deptFilter = OPENIAM.User.Delegation.getSelectedItemsValues($("#deptFilterContainer li"));

        obj.groupList = OPENIAM.User.Delegation.getSelectedItemsValues($("#groupListContainer li"));
        obj.roleList = OPENIAM.User.Delegation.getSelectedItemsValues($("#roleListContainer li"));
//        obj.appList = OPENIAM.User.Delegation.getSelectedItemsValues($("#appList option:selected"));
        obj.mngRptFlag = $("#mngRptFlag").is(":checked");
        obj.useOrgInhFlag = $("#useOrgInhFlag").is(":checked");

        if(obj.mngRptFlag==null || obj.mngRptFlag==undefined)
            obj.mngRptFlag=false;

        if(obj.useOrgInhFlag==null || obj.useOrgInhFlag==undefined)
            obj.useOrgInhFlag=false;
        return obj;
    },

    postJSON: function(url, data, callback){
        $.ajax({
            url : url,
            data : JSON.stringify(data),
            type: "POST",
            dataType : "json",
            contentType: "application/json",
            success : function(data, textStatus, jqXHR) {
                if(data.status == 200) {
                    OPENIAM.Modal.Success({message : data.successMessage, showInterval : 2000, onIntervalClose : function() {
                        if(callback)
                            callback.call(data);
                        else if(data.redirectURL) {
                            if(data.redirectURL != null && data.redirectURL != undefined && data.redirectURL.length > 0) {
                                window.location.href = data.redirectURL;
                            } else {
                                window.location.reload(true);
                            }
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
    OPENIAM.User.Delegation.init();

    $("#delegationForm").submit(function() {
        OPENIAM.User.Delegation.saveDelegation();
        return false;
    });
});

$(window).load(function() {
});