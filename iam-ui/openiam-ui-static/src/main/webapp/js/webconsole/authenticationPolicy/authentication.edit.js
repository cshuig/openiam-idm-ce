OPENIAM = window.OPENIAM || {};
OPENIAM.Policy = window.OPENIAM.Policy || {};

OPENIAM.Policy.Form = {
	deletePolicy : function() {
		$.ajax({
			url : "deleteAuthenticationPolicy.html",
			data : {id : OPENIAM.ENV.PolicyId},
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
	savePolicy : function() {
		this.toPolicyJSON();
		$.ajax({
			url : "editAuthenticationPolicy.html",
			data : JSON.stringify(OPENIAM.ENV.Policy),
			type: "POST",
			dataType : "json",
			contentType: "application/json",
			success : function(data, textStatus, jqXHR) {
				//alert('success');
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
				OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"] + errorThrown);
			}
		});
	},
	toPolicyJSON : function() {
		var obj = OPENIAM.ENV.Policy;
		obj.name = $("#name").val();
		obj.description = $("#description").val();
		obj.policyDefId = $("#policyDefId").val();
		obj.status = $("#status").val();
		for (var i=0; i< obj.policyAttributes.length; i++){
			var policyAttribute=obj.policyAttributes[i];
			var elmt = $("#" + policyAttribute.name);
			if(elmt.attr("value2") == "true") {
				policyAttribute.value2 = elmt.val();
			} else {
				policyAttribute.value1 = elmt.val();
			}
			policyAttribute.required=true;
		}
	},
	populate : function() {
		var obj = OPENIAM.ENV.Policy;
		$("#name").val(obj.name);
		$("#description").val(obj.description);
		$("#status").val(obj.status);
		for (var i=0; i< obj.policyAttributes.length; i++){
			var policyAttribute=obj.policyAttributes[i];
			var elmt = $("#" + policyAttribute.name);
			if(elmt.attr("value2") == "true") {
				elmt.val(policyAttribute.value2);
			} else {
				elmt.val(policyAttribute.value1);
			}
		}
	}
};

$(document).ready(function() {
	$("#deleteAuthenticationPolicy").click(function() {
		OPENIAM.Modal.Warn({ 
			message : localeManager["openiam.ui.webconsole.policy.want.delete.policy.warn"],
			buttons : true,
			OK : {
				text : localeManager["openiam.ui.webconsole.policy.want.delete.yes"],
				onClick : function() {
					OPENIAM.Modal.Close();
					OPENIAM.Policy.Form.deletePolicy();
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
	
	$("#save").click(function() {
		OPENIAM.Policy.Form.savePolicy();
		return false;
	});
	OPENIAM.Policy.Form.populate();
});

$(window).load(function() {
});