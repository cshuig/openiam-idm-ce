OPENIAM = window.OPENIAM || {};
OPENIAM.ChangePassword = {
	init : function() {
		/*
		$("._hasPlaceholder").each(function() {
			$(this).val($(this).attr("initialValue"));
			$(this).addClass("placeholderOn");
		});
		
		$("._hasPlaceholder").focusin(function() {
			if($(this).attr("initialized") == "false") {
				$(this).val("");
				$(this).attr("initialized", "true");
				$(this).removeClass("placeholderOn");
			}
		}).focusout(function() {
			if($(this).val() == "") {
				$(this).attr("initialized", "false");
				$(this).val($(this).attr("initialValue"));
				$(this).addClass("placeholderOn");
			}
		})
		*/
		
		$("#currentPassword").keyup(function() {
			$("#currentPasswordHidden").val($(this).val());
		});
		
		$("#newPassword").keyup(function() {
			$("#newPasswordHidden").val($(this).val());
		});
		
		$("#newPasswordConfirm").keyup(function() {
			$("#newPasswordConfirmHidden").val($(this).val());
		});
		
		$("#newPassword").passwordRules();
	}
};


$(document).ready(function() {
	OPENIAM.ChangePassword.init();
});

$(window).load(function() {
	setTimeout(function() {
		if(OPENIAM.ENV.AjaxResponse != null && OPENIAM.ENV.AjaxResponse.status == 500) {
			OPENIAM.Modal.Error({html : OPENIAM.PasswordPolicy.getFromAjaxResponse(OPENIAM.ENV.AjaxResponse)});
		}
	}, 1000);
});