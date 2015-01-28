console = window.console || {};
console.log = window.console.log || function() {};

OPENIAM = window.OPENIAM || {};
OPENIAM.ChallengeResponseQuestion = window.OPENIAM.ChallengeResponseQuestion || {};
OPENIAM.ChallengeResponseQuestion.Search = {
	init : function() {
		$("#searchTable").entitlemetnsTable({
			ajaxURL : "/webconsole/searchChallengeResponseQuestions.html",
			emptyResultsText : localeManager["openiam.ui.webconsole.challenge.response.question.empty.result"],
			columnHeaders : [localeManager["openiam.ui.common.question"], localeManager["openiam.ui.common.status"]],
			columnsMap : ["name", "active"],
			hasEditButton : false,
			entityUrl : OPENIAM.ENV.ChallengeResponseQuestionPageURL
		});
	}
};

$(document).ready(function() {
	OPENIAM.ChallengeResponseQuestion.Search.init();
});

$(window).load(function() {

});