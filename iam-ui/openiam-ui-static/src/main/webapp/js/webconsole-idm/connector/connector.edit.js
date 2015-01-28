OPENIAM = window.OPENIAM || {};
OPENIAM.connectorCommand = window.OPENIAM.Connector || {};

OPENIAM.connectorCommand.Form = {
		deleteProvider : function() {
		$.ajax({
			url : "/webconsole-idm/provisioning/deleteProvider.html",
			data : {id : OPENIAM.ENV.ConnectorId},
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
	
	$("#deleteProvider").click(function() {
		OPENIAM.Modal.Warn({ 
			message : OPENIAM.ENV.Text.DeleteWarn, 
			buttons : true, 
			OK : {
				text : localeManager["openiam.ui.connector.delete.confirm"],
				onClick : function() {
					
					OPENIAM.Modal.Close();
					OPENIAM.connectorCommand.Form.deleteProvider();
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
	
	
});

$(window).load(function() {
});