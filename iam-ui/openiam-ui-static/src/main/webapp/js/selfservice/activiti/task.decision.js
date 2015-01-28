OPENIAM = window.OPENIAM || {};
OPENIAM.TaskDecision = {
    init : function() {
		$("#accept").click(function() {
			OPENIAM.TaskDecision.process(true);
			return false;
		});
		
		$("#reject").click(function() {
			OPENIAM.TaskDecision.process(false);
			return false;
		});
    },
    process : function(accepted) {
    	var obj = this.toJSON();
    	obj.accepted = accepted;
    	this.submit(obj);
    },
    toJSON : function() {
    	var obj = {
    		taskId : OPENIAM.ENV.TaskId,
    		comment : $("#comment").val()
    	};
    	return obj;
    },
    submit : function(obj) {
    	$.ajax({
			url : "rest/api/activiti/task/decision",
			data : JSON.stringify(obj),
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
    OPENIAM.TaskDecision.init();
});
$(window).load(function() {
});