OPENIAM = window.OPENIAM || {};
OPENIAM.LoginEntity = window.OPENIAM.LoginEntity || {};

OPENIAM.LoginEntity.Form = {
	saveLogin : function() {
		$.ajax({
			url : "saveIdentity.html",
			data : JSON.stringify(this.toLoginJSON()),
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
	deleteLogin : function() {
		$.ajax({
			url : "deleteIdentity.html",
			data : {id : OPENIAM.ENV.LoginId},
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
	toLoginJSON : function() {
		var obj = {};
		obj.loginId = OPENIAM.ENV.LoginId;
		obj.login = $("#principal").val();
		obj.managedSysId = $("#managedSystemId").val();
		obj.userId = OPENIAM.ENV.UserId;
		return obj;
	}
};

$(document).ready(function() {
	$("#deleteLogin").click(function() {
		OPENIAM.Modal.Warn({ 
			message : localeManager["openiam.ui.user.identities.delete.login.question"],
			buttons : true, 
			OK : {
				text : localeManager["openiam.ui.user.identities.delete.login.confirmation"],
				onClick : function() {
					OPENIAM.Modal.Close();
					OPENIAM.LoginEntity.Form.deleteLogin();
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
	
	$("#loginForm").submit(function() {
		OPENIAM.LoginEntity.Form.saveLogin();
		return false;
	});
});

$(window).load(function() {
});