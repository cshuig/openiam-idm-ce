OPENIAM = window.OPENIAM || {};
OPENIAM.CustomPasswordPolicy = {
	deletePolicy : function() {
		$.ajax({
			url : "deletePolicy.html",
			data : {id : OPENIAM.ENV.PolicyId},
			type: "POST",
			dataType : "json",
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
	$("#deletePolicy").click(function() {
		OPENIAM.Modal.Warn({ 
			message : localeManager["openiam.ui.webconsole.policy.want.delete.policy.warn"],
			buttons : true,
			OK : {
				text : localeManager["openiam.ui.webconsole.policy.want.delete.yes"],
				onClick : function() {
					OPENIAM.Modal.Close();
					OPENIAM.CustomPasswordPolicy.deletePolicy();
				}
			},
			Cancel : {
				text : localeManager["openiam.ui.button.cancel"],
				onClick : function() {
					OPENIAM.Modal.Close();
				}
			}
		});
		return false;
	});
});

$(window).load(function() {
	if(OPENIAM.ENV.ErrorMessage != null) {
		OPENIAM.Modal.Error(OPENIAM.ENV.ErrorMessage);
	}
	
	if(OPENIAM.ENV.SuccessMessage != null) {
		OPENIAM.Modal.Success({message : OPENIAM.ENV.SuccessMessage, showInterval : 1000});
	}
});