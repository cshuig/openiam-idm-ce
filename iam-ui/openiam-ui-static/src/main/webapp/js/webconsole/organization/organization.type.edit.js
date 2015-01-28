OPENIAM = window.OPENIAM || {};
OPENIAM.OrganizationType = {
	init : function() {
		$("#saveBtn").click(function() {
			OPENIAM.OrganizationType.save();
			return false;
		});
		
		$("#deleteBtn").click(function() {
			OPENIAM.OrganizationType.deleteType();
			return false;
		});
		this.populate();
	},
	populate : function() {
		var obj = OPENIAM.ENV.OrganizationType;
		$("#name").val(obj.name);
		$("#description").val(obj.description);
		$("#displayNameMap").languageAdmin({ bean : obj, beanKey : "displayNameMap"});
	},
	toJSON : function() {
		var obj = OPENIAM.ENV.OrganizationType;
		obj.name = $("#name").val();
		obj.description = $("#description").val();
		obj.displayNameMap = $("#displayNameMap").languageAdmin("getMap");
	},
	save : function() {
		this.toJSON();
		$.ajax({
			url : "organizationTypeEdit.html",
			data : JSON.stringify(OPENIAM.ENV.OrganizationType),
			type: "POST",
			dataType : "json",
			contentType: "application/json",
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
	deleteType : function() {
		$.ajax({
			url : "organizationTypeDelete.html",
			data : {id : OPENIAM.ENV.OrganizationTypeId},
			type: "POST",
			dataType : "json",
			success : function(data, textStatus, jqXHR) {
				if(data.status == 200) {
					OPENIAM.Modal.Success({message : data.successMessage, showInterval : 2000, onIntervalClose : function() {
						window.location.href = "organizationTypeSearch.html";
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
	OPENIAM.OrganizationType.init();
});

$(window).load(function() {

});