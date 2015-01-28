OPENIAM = window.OPENIAM || {};
OPENIAM.EditProfileWithApprover = {
	init : function() {
		$("#submit").click(function() {
			var inputs = [];
			inputs.push({
				elmtType : "textarea",
				className : "rounded",
				id : "taskDescription",
				label : localeManager["openiam.ui.selfservice.user.edit.profile.enter.optinal.description"]
			});
				
			OPENIAM.Modal.Warn({ 
				inputs : inputs,
				message : localeManager["openiam.ui.selfservice.user.edit.status.warn"],
				buttons : true, 
				OK : {
					text : localeManager["openiam.ui.common.submit"],
					onClick : function() {
						OPENIAM.Modal.Close();
						OPENIAM.EditProfileWithApprover.submit();
					}
				},
				Cancel : {
					text : localeManager["openiam.ui.button.cancel"],
					onClick : function() {
						OPENIAM.Modal.Close();
					}
				}
			});
		});
	},
	submit : function() {
		$.ajax({
			url : "setUserStatus.html",
			data : {userId : OPENIAM.ENV.UserId, status : $("#primaryStatus").val(), secondaryStatus : $("#secondaryStatus").val(), description : $("#taskDescription").val()},
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
	}
};

$(document).ready(function() {
	OPENIAM.EditProfileWithApprover.init();
});

$(window).load(function() {
	
});