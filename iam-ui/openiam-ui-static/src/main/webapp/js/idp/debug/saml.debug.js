OPENIAM = window.OPENIAM || {};
OPENIAM.SAML = {
	init : function() {
		var $this = this;
		$("#submit").click(function() {
			if($("#samlResponse").length == 1) {
				$this.debugSAMLResponse();
			} else {
				$this.debugSAMLRequest();
			}
		});
	},
	debugSAMLRequest : function() {
	
	},
	debugSAMLResponse : function(value) {
		$.ajax({
            url : "debugSAMLResponse.html",
            data : {"SAMLResponse" : $("#samlResponse").val()},
            type : "POST",
            dataType : "text",
            success : function(data, textStatus, jqXHR) {
                $("#jsonResponse").val(JSON.stringify(data, null, 2));
            },
            error : function(jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
	}
};

$(document).ready(function() {
	OPENIAM.SAML.init();
});