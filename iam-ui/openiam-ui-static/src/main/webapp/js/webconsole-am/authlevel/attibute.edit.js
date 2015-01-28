OPENIAM = window.OPENIAM || {};

OPENIAM.Attribute = {
	deleteAttribute : function() {
	 	$.ajax({
            url : "deleteAuthLevelAttribute.html",
            data : {groupId : OPENIAM.ENV.GroupingId, id : OPENIAM.ENV.AttributeId},
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
	}
};

$(document).ready(function() {
	var removeNewFileElement = false;
	$("#delete").click(function() {
		OPENIAM.Attribute.deleteAttribute();
		return false;
	});
	
	$("#newFile").click(function() {
		$("#bytes").show();
		$("#modifiedFile").val(true);
		$(this).hide();
	});
	
	$("#typeId").change(function() {
		var changed = false;
		var val = $(this).val();
		if(val != null && val != undefined && val != "") {
			var type = OPENIAM.ENV.TypeMap[val];
			if(type != null) {
				if(type.binary) {
					if(removeNewFileElement) {
						$("#newFile").hide();
					}
					$("#bytes").show().closest("tr").show();
					$("#valueAsString").val("").closest("tr").hide();
				} else {
					$("#valueAsString").closest("tr").show();
					$("#bytes").val("").closest("tr").hide();
				}
				changed = true;
			}
		}
		if(!changed) {
			$("#valueAsString").val("").closest("tr").hide();
			$("#bytes").val("").closest("tr").hide();
		}
	});
	
	$("#bytes").click(function() {
		$("#modifiedFile").val(true);
	});
	
	$("#typeId").trigger("change");
	removeNewFileElement = true;
	
	if(OPENIAM.ENV.ErrorMessage != null) {
		OPENIAM.Modal.Error(OPENIAM.ENV.ErrorMessage);
	}
	if(OPENIAM.ENV.SuccessMessage != null) {
		OPENIAM.Modal.Success(OPENIAM.ENV.SuccessMessage);
		setTimeout(function() {
			window.location.href = OPENIAM.ENV.Location;
		}, 2000);
	}
	
	if(OPENIAM.ENV.HasFile) {
		$("#valueAsString").val("").closest("tr").hide();
		$("#bytes").hide().closest("tr").show();
		$("#newFile").show();
	}
});