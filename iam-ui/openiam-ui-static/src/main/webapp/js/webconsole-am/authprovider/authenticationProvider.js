OPENIAM = window.OPENIAM || {};
OPENIAM.AuthProvider = {
	clearKeys : function(arg) {
		this.clearPrivateKey(arg);
		this.clearPublicKey(arg);
	},
	clearPrivateKey : function(arg) {
		$("#clearPrivateKey").val(arg);
	},
	clearPublicKey : function(arg) {
		$("#clearPublicKey").val(arg);
	},
	deleteProvider : function() {
		$.ajax({
			url : "deleteAuthProvider.html",
			data : {id : OPENIAM.ENV.ProviderId},
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
	$("#signResponse").click(function() {
		$(".fileUploadHolder").show();
		OPENIAM.AuthProvider.clearKeys(false);
	});
	$("#dontSignResponse").click(function() {
		$(".fileUploadHolder").hide();
		OPENIAM.AuthProvider.clearKeys(true);
	});
	if($("#signResponse").is(":checked")) {
		$(".fileUploadHolder").show();
		OPENIAM.AuthProvider.clearKeys(false);
	} else{
		$(".fileUploadHolder").hide();
		OPENIAM.AuthProvider.clearKeys(true);
	}
	$("#uploadNewPublicKey").click(function() {
		OPENIAM.AuthProvider.clearPublicKey(true);
		$(this).parent().hide();
		$("#publicKey").show();
	});
	$("#uploadNewPrivateKey").click(function() {
		OPENIAM.AuthProvider.clearPrivateKey(true);
		$(this).parent().hide();
		$("#privateKey").show();
	});
	
	$("#deleteProvider").click(function() {
		OPENIAM.Modal.Warn({ 
			message : localeManager["openiam.ui.am.auth.provider.delete.warn"],
			buttons : true, 
			OK : {
				text : localeManager["openiam.ui.am.auth.provider.delete.confirm"],
				onClick : function() {
					OPENIAM.Modal.Close();
					OPENIAM.AuthProvider.deleteProvider();
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
		OPENIAM.Modal.Success({message : OPENIAM.ENV.SuccessMessage, showInterval : 1000, onIntervalClose : function() {
			window.location.href = "editAuthProvider.html?id=" + OPENIAM.ENV.ProviderId;
		}});
	}
});