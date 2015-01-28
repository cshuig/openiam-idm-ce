console = window.console || {};
console.log = window.console.log || function() {};

OPENIAM = window.OPENIAM || {};
OPENIAM.Policy = window.OPENIAM.Policy || {};
OPENIAM.Policy.Edit = {
	init : function() {
		$("#deletePolicy").click(function() {	
			OPENIAM.Modal.Warn({ 
				message : localeManager["openiam.ui.webconsole.policy.want.delete.policy"],
				buttons : true, 
				OK : {
					text : localeManager["openiam.ui.webconsole.policy.want.delete.yes"],
					onClick : function() {
						OPENIAM.Modal.Close();
						OPENIAM.Policy.Edit.deleteObject();
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
		
		$("#savePolicy").click(function() {
			OPENIAM.Policy.Edit.save();
			return false;
		});
	},
	save : function() {
		$.ajax({
			url : "editAttributePolicy.html",
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
	deleteObject : function() {
		$.ajax({
			url : "deleteAttributePolicy.html",
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
	toJSON : function() {
		var obj = {};
		obj.policyId = OPENIAM.ENV.PolicyId;
		obj.name = $("#policyName").val();
		obj.description = $("#description").val();
		obj.ruleSrcUrl = $("#ruleSrcUrl").val();
		obj.rule = $("#rule").val();
		obj.status = $("#status").val();
		return obj;
	}
};

$(document).ready(function() {
	OPENIAM.Policy.Edit.init();
});