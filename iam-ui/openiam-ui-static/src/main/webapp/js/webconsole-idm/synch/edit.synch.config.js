OPENIAM = window.OPENIAM || {};
OPENIAM.SynchConfigEntity = window.OPENIAM.SynchConfigEntity || {};

OPENIAM.SynchConfigEntity.Form = {
    testConnect : function() {
        $.ajax({
            url : "testConnect.html",
            data : JSON.stringify(this.toJSON()),
            type: "POST",
            dataType : "json",
            contentType: "application/json",
            success : function(data, textStatus, jqXHR) {
                if(data.status == 200) {
                    OPENIAM.Modal.Success({message : data.successMessage, showInterval : 2000, onIntervalClose : function() {
                        if(data.redirectURL != null && data.redirectURL != undefined && data.redirectURL.length > 0) {
                            window.location.href = data.redirectURL;
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
    startSynch : function() {
        $.ajax({
            url : "startSynch.html",
            data : JSON.stringify(this.toJSON()),
            type: "POST",
            dataType : "json",
            contentType: "application/json",
            success : function(data, textStatus, jqXHR) {
                if(data.status == 200) {
                    OPENIAM.Modal.Success({message : data.successMessage, showInterval : 2000, onIntervalClose : function() {
                        if(data.redirectURL != null && data.redirectURL != undefined && data.redirectURL.length > 0) {
                            window.location.href = data.redirectURL;
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
    toJSON : function() {
        var obj = {};
        obj.synchConfigId = OPENIAM.ENV.SynchConfigId;
        return obj;
    }
};

$(document).ready(function() {
    $("#testConnect").click(function() {
        OPENIAM.SynchConfigEntity.Form.testConnect();
        return false;
    });
    $("#startSynch").click(function() {
        OPENIAM.SynchConfigEntity.Form.startSynch();
        return false;
    });
    
    var obj = (OPENIAM.ENV.Organization != null) ? OPENIAM.ENV.Organization : {};
    $("#organization").selectableSearchResult({
			initialBeans : [{id : obj.id, name : obj.name}], 
			singleSearch : true, 
			addMoreText : localeManager["openiam.ui.common.organization.select.another"], 
			noneSelectedText : localeManager["openiam.ui.common.organization.select"],
			onClick : function($that) {
				$("#editDialog").organizationDialogSearch({
					showResultsInDialog : true,
					searchTargetElmt : "#editDialog",
					onAdd : function(bean) {
						$that.selectableSearchResult("add", bean);
						$("#editDialog").dialog("close");
					},
					pageSize : 5,
					restfulURLPrefix : "/webconsole-idm/"
				});
			}
		});
		
	$("#synchCommand").submit(function() {
		$("#companyId").val($("#organization").selectableSearchResult("getId"));
	});
});

$(window).load(function() {
});