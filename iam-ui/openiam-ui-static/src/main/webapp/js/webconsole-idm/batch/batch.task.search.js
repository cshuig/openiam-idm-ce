console = window.console || {};
console.log = window.console.log || function() {};

OPENIAM = window.OPENIAM || {};
OPENIAM.BatchTask = window.OPENIAM.BatchTask || {};
OPENIAM.BatchTask.Search = {
	init : function() {
		$("#searchTable").entitlemetnsTable({
			ajaxURL : "/webconsole-idm/provisioning/batch/task/search.html",
			emptyResultsText : OPENIAM.ENV.Text.EmptyResults,
			columnHeaders : [
				localeManager["openiam.ui.common.name"],
                localeManager["openiam.ui.common.value.enabled"],
                localeManager["openiam.ui.webconsole.idm.batch.task.label.cronExpession"],
                localeManager["openiam.ui.webconsole.idm.batch.task.label.runOn"]
			],
			columnsMap : ["name", "enabled", "cronExpression", "runOn"],
			hasEditButton : false,
			entityUrl : "batchTask.html"
		});
	}
};

$(document).ready(function() {
	OPENIAM.BatchTask.Search.init();
});

$(window).load(function() {

});