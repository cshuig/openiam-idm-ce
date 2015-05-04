OPENIAM = window.OPENIAM || {};
OPENIAM.AuthDebug = {
	init : function() {
		$("#loginDebug").click(function() {
			OPENIAM.AuthDebug.loginDebug();
			return false;
		});
		
		$("#renewTokenDebug").click(function() {
			OPENIAM.AuthDebug.renewTokenDebug();
			return false;
		});
	},
	loginDebug : function() {
		$("#loginDebugResponse").val();
		$.ajax({
			url : "/idp/login.html",
			data : {login : $("#login").val(), password : $("#password").val()},
			beforeSend: function (request) {
                request.setRequestHeader("x-openiam-test-request", "true");
            },
			type: "POST",
			dataType : "json",
			success : function(data, textStatus, jqXHR) {
				if(data) {
					$("#loginDebugResponse").val(JSON.stringify(data, null, "\t"));
				} else {
					$("#loginDebugResponse").val("Null, undefined, or no data received");
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
			}
		});
	},
	renewTokenDebug : function() {
		$("#renewTokenDebugRespone").val();
		$.ajax({
			url : "/idp/rest/api/auth/renewTokenWithString",
			data : {token : $("#token").val()},
			type: "GET",
			dataType : "json",
			success : function(data, textStatus, jqXHR) {
				if(data) {
					$("#renewTokenDebugRespone").val(JSON.stringify(data, null, "\t"));
				} else {
					$("#renewTokenDebugRespone").val("Null, undefined, or no data received");
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
			}
		});
	}
};

$(document).ready(function() {
	OPENIAM.AuthDebug.init();
});