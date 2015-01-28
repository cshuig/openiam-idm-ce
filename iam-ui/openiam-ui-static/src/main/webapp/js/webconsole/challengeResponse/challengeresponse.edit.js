console = window.console || {};
console.log = window.console.log || function() {};

OPENIAM = window.OPENIAM || {};
OPENIAM.ChallengeResponseQuestion = window.OPENIAM.ChallengeResponseQuestion || {};
OPENIAM.ChallengeResponseQuestion.Edit = {
	init : function() {
		$("#deleteChallengeResponse").click(function() {	
			OPENIAM.Modal.Warn({ 
				message : localeManager["openiam.ui.webconsole.challenge.response.question.delete.warn"],
				buttons : true, 
				OK : {
					text : localeManager["openiam.ui.webconsole.challenge.response.question.delete.confirm"],
					onClick : function() {
						OPENIAM.Modal.Close();
						OPENIAM.ChallengeResponseQuestion.Edit.deleteObject();
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
		
		$("#saveChallengeResponse").click(function() {
			OPENIAM.ChallengeResponseQuestion.Edit.save();
			return false;
		});
		this.populatePage();
	},
	save : function() {
		this.toJSON();
		$.ajax({
			url : "saveChallengeResponseQuestion.html",
			data : JSON.stringify(OPENIAM.ENV.Question),
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
			url : "deleteChallengeResponseQuestion.html",
			data : {id : OPENIAM.ENV.Id},
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
	populatePage : function() {
		var obj = OPENIAM.ENV.Question;
		$("#displayNameMap").languageAdmin({ bean : obj, beanKey : "displayNameMap"});
		$("#active").prop("checked", obj.active);
	},
	toJSON : function() {
		var obj = OPENIAM.ENV.Question;
		obj.active = $("#active").is(":checked");
		obj.displayNameMap = $("#displayNameMap").languageAdmin("getMap");
	}
};

$(document).ready(function() {
	OPENIAM.ChallengeResponseQuestion.Edit.init();
});