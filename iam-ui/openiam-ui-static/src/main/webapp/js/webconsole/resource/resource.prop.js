OPENIAM = window.OPENIAM || {};
OPENIAM.ResourceProperty = {
	init : function() { 
		$("#urlSave").click(function() {
			$.ajax({
				url : "saveURLProp.html",
				data : { url : $("#protectingURL").val(), resourceId : OPENIAM.ENV.ResourceId},
				type: "POST",
				dataType : "json",
				success : function(data, textStatus, jqXHR) {
					if(data.status == 200) {
						OPENIAM.Modal.Success({message : data.successMessage, showInterval : 2000, onIntervalClose : function() {
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
		
		$("#propertySave").click(function() {
			$.ajax({
				url : "saveResourceProp.html",
				data : JSON.stringify(OPENIAM.ResourceProperty.getPropertyJSON()),
				type: "POST",
				dataType : "json",
				contentType: "application/json",
				success : function(data, textStatus, jqXHR) {
					if(data.status == 200) {
						OPENIAM.Modal.Success({message : data.successMessage, showInterval : 2000, onIntervalClose : function() {
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
			var propId = $(this).attr("resourcePropertyId");
			$.ajax({
				url : "deleteResoruceProp.html",
				data : { id : propId},
				type: "POST",
				dataType : "json",
				success : function(data, textStatus, jqXHR) {
					if(data.status == 200) {
						/*OPENIAM.Modal.Success({message : data.successMessage, showInterval : 2000, onIntervalClose : function() {
							window.location.reload(true);
						}});*/
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
		
		$("#urlTable").tablesorter({ 
			debug: false, 
			sortDisabled : true,
			widgets: ['zebra'] });
			
		$("#propertyTable").tablesorter({ 
			debug: false, 
			sortDisabled : true,
			widgets: ['zebra'] });
	},
	saveURL : function() {

	},
	saveProperty : function() {
		
	},
	getPropertyJSON : function() {
		var json = {};
		json.resourceId = OPENIAM.ENV.ResourceId;
		json.name = $("#propertyName").val();
		json.value = $("#propertyValue").val();
		return json;
	}
};

$(document).ready(function() {
	OPENIAM.ResourceProperty.init();	
});

$(window).load(function() {
	
});