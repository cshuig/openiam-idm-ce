OPENIAM = window.OPENIAM || {};

OPENIAM.Entity = {
	saveEntity : function() {
		this.updateJSON();
    	$.ajax({
			url : "editAuthLevelGrouping.html",
			data : JSON.stringify(OPENIAM.ENV.Grouping),
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
	deleteEntity : function() {
		 $.ajax({
            url : "deleteAuthLevelGrouping.html",
            data : {id : OPENIAM.ENV.GroupingId},
            type: "POST",
            dataType : "json",
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
	updateJSON : function() {
		var obj = OPENIAM.ENV.Grouping;
		obj.name = $("#name").val();
		obj.authLevel = { id : $("#authLevel").val()}
	},
	updateForm : function() {
		var obj = OPENIAM.ENV.Grouping;
		$("#name").val(obj.name);
		$("#authLevel").val((obj.authLevel) ? obj.authLevel.id : null);
		if(obj.authLevel && obj.authLevel.id) {
			$("#authLevel").attr("disabled", "disabled");	
		}
		
		if($("#attributesContainer").length > 0) {
			$("#attributesContainer").persistentTable({
				emptyMessage : localeManager["openiam.ui.webconsole.am.authlevel.create.newattribute"],
				createEnabled : true,
				objectArray : OPENIAM.ENV.Attributes,
				createBtnId : "newAttribute",
				headerFields : [localeManager["openiam.ui.common.attribute.name"], localeManager["openiam.ui.common.type"]],
				fieldNames : ["name", "metaTypeName"],
				deleteEnabledField : false,
				editEnabledField : true,
				createText : localeManager["openiam.ui.webconsole.am.authlevel.create.newattribute"],
				actionsColumnName : localeManager["openiam.ui.webconsole.am.authlevel.actions"],
				onDeleteClick : function(obj) {
					
				},
				equals : function(obj1, obj2) {
					return obj1.id == obj2.id;
				},
				onEditClick : function(obj) {
					window.location = "editAuthLevelGroupingAttibute.html?id=" + obj.id + "&groupingId=" + OPENIAM.ENV.GroupingId;
				},
				onCreateClick : function() {
					window.location = "editAuthLevelGroupingAttibute.html?groupingId=" + OPENIAM.ENV.GroupingId;
				}
			});
		}
	}
};

$(document).ready(function() {
	OPENIAM.Entity.updateForm();
	
	$("#delete").click(function() {
		OPENIAM.Entity.deleteEntity();
		return false;
	});
	
	$("#save").click(function() {
		OPENIAM.Entity.saveEntity();
		return false;
	});
});