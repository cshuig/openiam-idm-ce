OPENIAM = window.OPENIAM || {};
OPENIAM.Login = window.OPENIAM.Login || {};
/* OPENIAM.Login is already defined in another file, so better use another namespace */
OPENIAM.Login.Addon = {
	init : function() {
		$("#login-form").submit(function() {
			OPENIAM.Login.Addon.request();
			//console.log("Submitted");
			return false;
		});

		$("#login-form input").keypress(function(e) {
			if(e.keyCode == 13) {
				OPENIAM.Login.Addon.request();
				return false;
			}
		});
	},
	request : function() {
		$("#unlockURL").hide();
		$("#error").empty().hide();
		
		var obj = {};
		$("#login-form").find("[name]").each(function() {
			var $this = $(this);
			obj[$this.attr("name")] = $this.val();
		});
		$.ajax({
			url : "login.html",
			data : obj,
			type: "POST",
			dataType : "json",
			success : function(data, textStatus, jqXHR) {
				if(data.status == 200) {
					var redirectURL = data.redirectURL;
					if(redirectURL != null && redirectURL != undefined && redirectURL.length > 0) {
						window.location.href = redirectURL;
					} else {
						window.location.href = OPENIAM.ENV.PostbackURL;
					}
					/*
					OPENIAM.Modal.Success({message : data.successMessage, showInterval : 2000, onIntervalClose : function() {
						if(data.redirectURL != null && data.redirectURL != undefined && data.redirectURL.length > 0) {
							window.location.href = data.redirectURL;
						} else {
							window.location.reload(true);
						}
					}});
					*/
					console.log(data);
				} else {
					if(data.unlockURL) {
						$("#unlockURL").show().find("a").attr("href", data.unlockURL);
						$("#error").hide();
					}
						
					$("#error").show();
					$.each(data.errorList, function(idx, val) {
						$("#error").append(
							$(document.createElement("div")).addClass("error").text(val.message)
						);
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
	OPENIAM.Login.Addon.init();
});