OPENIAM = window.OPENIAM || {};
OPENIAM.ITPolicy = window.OPENIAM.ITPolicy || {};

OPENIAM.ITPolicy.Form = {
    resetITPolicy : function() {
		$.ajax({
			url : "resetITPolicy.html",
			data : {},
			type: "POST",
			dataType : "json",
			success : function(data, textStatus, jqXHR) {
				if(data.status == 200) {
					OPENIAM.Modal.Success({message : data.successMessage, showInterval : 2000, onIntervalClose : function() {
						window.location.href = data.redirectURL;
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
	saveITPolicy : function() {
		$.ajax({
			url : "saveITPolicy.html",
			data : JSON.stringify(this.toJSON()),
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
	},
	toJSON : function() {
		var obj = {};
        obj.policyId = $("#policyId").val();
		obj.approveType = $("input:radio[name=approveType]:checked").val();
		obj.active = $("#active").val();
		obj.policyContent = $("#policyContent").val();
        obj.confirmation = $("#confirmation").val();
		return obj;
	}
};

$(document).ready(function() {
	$("#resetITPolicy").click(function() {
		OPENIAM.Modal.Warn({ 
			message : OPENIAM.ENV.Text.ResetWarn,
			buttons : true, 
			OK : {
				text : localeManager["openiam.ui.it.policy.reset.confirmation"],
				onClick : function() {
					OPENIAM.Modal.Close();
					OPENIAM.ITPolicy.Form.resetITPolicy();
				}
			},
			Cancel : {
				text : localeManager["openiam.ui.common.cancel"],
				onClick : function() {
					OPENIAM.Modal.Close();
				}
			}
		});
		return false;
	});
	
	$("#itPolicyForm").submit(function() {
		OPENIAM.ITPolicy.Form.saveITPolicy();
		return false;
	});
});

$(window).load(function() {
});