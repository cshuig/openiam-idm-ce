OPENIAM = window.OPENIAM || {};
OPENIAM.BatchTask = window.OPENIAM.BatchTask || {};

OPENIAM.BatchTask.Form = {
	adjustDate : function() {
		setTimeout(function() {
			var dateCtrl = $("#runOnAsString");
			var btn = dateCtrl.next();
			btn.css("right", (dateCtrl.parent().width() - dateCtrl.width()));
		}, 500);
	},
	bind : function() {
		$("#deleteResoruce").click(function() {
			OPENIAM.Modal.Warn({ 
				message : OPENIAM.ENV.Text.DeleteWarn, 
				buttons : true, 
				OK : {
					text : localeManager["openiam.ui.webconsole.idm.batch.task.confirm.delete"],
					onClick : function() {
						OPENIAM.Modal.Close();
						OPENIAM.BatchTask.Form.deleteTask();
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
		$("#execute").click(function() {
			OPENIAM.Modal.Warn({ 
				message : localeManager["openiam.ui.webconsole.idm.batch.task.async.execute.warn"], 
				buttons : true, 
				OK : {
					text : localeManager["openiam.ui.common.yes"],
					onClick : function() {
						OPENIAM.Modal.Close();
						OPENIAM.BatchTask.Form.execute();
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
	
		$("#theForm").submit(function() {
			OPENIAM.BatchTask.Form.save();
			return false;
		});
		
		var dateCtrl = $("#runOnAsString")
		dateCtrl.datepicker({ dateFormat: OPENIAM.ENV.DateFormatDP,showOn: "button",changeMonth:true, changeYear:true}).attr('readonly','readonly');
		
		$("#executionType").change(function() {
			if($(this).val() == "") {
				$("#cronExpression").val("").closest("tr").hide();
				$("#runOnAsString").val("").closest("tr").hide();
			} else if($(this).val() == "cron") {
				$("#cronExpression").closest("tr").show();
				$("#runOnAsString").val("").closest("tr").hide();
			} else if($(this).val() == "date") {
				$("#cronExpression").val("").closest("tr").hide();
				$("#runOnAsString").closest("tr").show();
				OPENIAM.BatchTask.Form.adjustDate();
			}
		});
		
		$("#executionScript").change(function() {
			if($(this).val() == "") {
				$("#taskUrl").val("").closest("tr").hide();
				$("#springBean").val("").closest("tr").hide();
				$("#springBeanMethod").val("").closest("tr").hide();
			} else if($(this).val() == "groovy") {
				$("#taskUrl").closest("tr").show();
				$("#springBean").val("").closest("tr").hide();
				$("#springBeanMethod").val("").closest("tr").hide();
			} else if($(this).val() == "springBean") {
				$("#taskUrl").val("").closest("tr").hide();
				$("#springBean").closest("tr").show();
				$("#springBeanMethod").closest("tr").show();
			}
		});
		
		if($("#taskUrl").val() != "") {
			$("#executionScript").val("groovy");
			$("#executionScript").change();
		} else if($("#springBean").val() != "" && $("#springBeanMethod") != "") {
			$("#executionScript").val("springBean");
			$("#executionScript").change();
		}
		
		if($("#cronExpression").val() != "") {
			$("#executionType").val("cron");
			$("#executionType").change();
		} else if($("#runOnAsString").val() != "") {
			$("#executionType").val("date");
			$("#executionType").change();
		}
		
		OPENIAM.BatchTask.Form.adjustDate();
	},
	execute : function() {
		$.ajax({
			url : "batchtask/execute.html",
			data : {id : OPENIAM.ENV.TaskId},
			type: "POST",
			dataType : "json",
			success : function(data, textStatus, jqXHR) {
				OPENIAM.Modal.Success({message : data.successMessage, showInterval : 2000, onIntervalClose : function() {
					if(data.redirectURL != null && data.redirectURL != undefined && data.redirectURL.length > 0) {
						window.location.href = data.redirectURL;
					} else {
						window.location.reload(true);
					}
				}});
			},
			error : function(jqXHR, textStatus, errorThrown) {
				OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
			}
		});
	},
	deleteTask : function() {
		$.ajax({
			url : "deletBatchTask.html",
			data : {id : OPENIAM.ENV.TaskId},
			type: "POST",
			dataType : "json",
			success : function(data, textStatus, jqXHR) {
				if(data.status == 200) {
					window.location = "batchTaskSearch.html";
				} else {
					OPENIAM.Modal.Error({errorList : data.errorList});
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
			}
		});
	},
	save : function() {
		$.ajax({
			url : "saveBatchTask.html",
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
	toJSON : function() {
		var obj = {};
		obj.id = OPENIAM.ENV.TaskId;
		obj.name = $("#taskName").val();
		obj.enabled = $("#isEnabled").is(":checked");
		obj.runOnAsString = $("#runOnAsString").val();
		obj.cronExpression = $("#cronExpression").val();
		obj.taskUrl = $("#taskUrl").val();
		obj.springBean = $("#springBean").val();
		obj.springBeanMethod = $("#springBeanMethod").val();
		obj.param1 = $("#param1").val();
		obj.param2 = $("#param2").val();
		obj.param3 = $("#param3").val();
		obj.param4 = $("#param4").val();
		return obj;
	}
};

$(document).ready(function() {
	OPENIAM.BatchTask.Form.bind();
});

$(window).load(function() {
});