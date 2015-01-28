OPENIAM = window.OPENIAM || {};
OPENIAM.ManagedSysRule = {
    init : function() {

	$("#ruleSave").click(
		function() {
		    $.ajax({
			url : "managed-system-rule-save.html",
			data : JSON.stringify(OPENIAM.ManagedSysRule
				.getPropertyJSON()),
			type : "POST",
			dataType : "json",
			contentType : "application/json",
			success : function(data, textStatus, jqXHR) {
			    if (data.status == 200) {
				OPENIAM.Modal.Success({
				    message : data.successMessage,
				    showInterval : 2000,
				    onIntervalClose : function() {
					window.location.reload(true);
				    }
				});
			    } else {
				OPENIAM.Modal.Error({
				    errorList : data.errorList
				});
			    }
			},
			error : function(jqXHR, textStatus, errorThrown) {
			    OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
			}
		    });
		});

	$("table td.delete a").click(function() {
	    var $this = $(this);
	    var propId = $(this).attr("managedSysRuleId");
	    $.ajax({
		url : "managed-system-role-delete.html",
		data : {
		    id : propId
		},
		type : "POST",
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
		    if (data.status == 200) {
			/*
			 * OPENIAM.Modal.Success({message : data.successMessage,
			 * showInterval : 2000, onIntervalClose : function() {
			 * window.location.reload(true); }});
			 */
			$this.closest("tr").remove();
		    } else {
			OPENIAM.Modal.Error({
			    errorList : data.errorList
			});
		    }
		},
		error : function(jqXHR, textStatus, errorThrown) {
		    OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
		}
	    });
	});

	$("#urlTable").tablesorter({
	    debug : false,
	    sortDisabled : true,
	    widgets : [ 'zebra' ]
	});

	$("#ruleTable").tablesorter({
	    debug : false,
	    sortDisabled : true,
	    widgets : [ 'zebra' ]
	});
    },
    saveURL : function() {

    },
    saveProperty : function() {

    },
    getPropertyJSON : function() {
	var json = {};
	json.managedSysId = $('#ruleTable').attr('manadegSysId');
	json.name = $("#ruleName").val();
	json.value = $("#ruleValue").val();
	return json;
    }
};

$(document).ready(function() {
    OPENIAM.ManagedSysRule.init();
});

$(window).load(function() {

});