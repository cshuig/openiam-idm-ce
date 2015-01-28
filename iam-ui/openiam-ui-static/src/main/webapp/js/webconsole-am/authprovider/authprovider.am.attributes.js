OPENIAM = window.OPENIAM || {};
OPENIAM.AMProperty = {
	init : function() { 	
		$("#propertySave").click(function() {
			$.ajax({
				url : "saveAMAttributeForProvider.html",
				data : JSON.stringify(OPENIAM.AMProperty.getPropertyJSON()),
				type: "POST",
				dataType : "json",
				contentType: "application/json",
				success : function(data, textStatus, jqXHR) {
					if(data.status == 200) {
						OPENIAM.Modal.Success({message : data.successMessage, showInterval : 1000, onIntervalClose : function() {
							window.location.reload(true);
						}});
					} else {
						OPENIAM.Modal.Error({errorList : data.errorList});
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
				}
			});
		});
		
		$("table td.delete a").click(function() {
			var $this = $(this);
			var propId = $(this).attr("propertyId");
			$.ajax({
				url : "deleteAMAttributeForProvider.html",
				data : { id : propId},
				type: "POST",
				dataType : "json",
				success : function(data, textStatus, jqXHR) {
					if(data.status == 200) {
						$this.closest("tr").remove();
					} else {
						OPENIAM.Modal.Error({errorList : data.errorList});
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
				}
			});
		});
		
		$("#propertyTable").tablesorter({ 
			debug: false, 
			sortDisabled : true,
			widgets: ['zebra'] });
			
	
		$("#valueType").change(function() {
			var val = $(this).val();
			$("#userValue").val("").closest("div").hide();
			$("#staticValue").val("").closest("div").hide();
			$("#groovyScript").val("").closest("div").hide();
			if(val == "userValue") {
				$("#userValue").closest("div").show();
			} else if(val == "staticValue") {
				$("#staticValue").closest("div").show();
			} else if(val == "groovyScript") {
				$("#groovyScript").closest("div").show();
			}
		});
	},
	getPropertyJSON : function() {
		var json = {};
		json.providerId = OPENIAM.ENV.ProviderId;
		json.amResAttributeId = $("#userValue").val();
		json.amResAttributeName = $("#userValue option:selected").text();
		json.targetAttributeName = $("#propertyName").val();
		json.amPolicyUrl = $("#groovyScript").val();
		json.attributeValue = $("#staticValue").val();
		json.attributeType = $("#dataType").val();
		return json;
	}
};

$(document).ready(function() {
	OPENIAM.AMProperty.init();	
});

$(window).load(function() {
	
});