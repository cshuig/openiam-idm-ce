console = window.console || {};
console.log = window.console.log || function() {};

OPENIAM = window.OPENIAM || {};
OPENIAM.SubscribeReportSearch = window.OPENIAM.SubscribeReportSearch || {};
OPENIAM.SubscribeReportSearch.Search = {
	init : function() {
		$("#searchTable").entitlemetnsTable({
			ajaxURL : "/selfservice/subscribeReportSearch.html",
			emptyResultsText : localeManager["openiam.ui.report.view.reports.search.empty"],
			columnHeaders : [
				localeManager["openiam.ui.common.name"], 
				"Status"
			],
			columnsMap : ["reportName", "status"],
			hasEditButton : false,
			entityUrl : OPENIAM.ENV.SubscribeReportPageURL
		});
	}
};

$(document).ready(function() {
	OPENIAM.SubscribeReportSearch.Search.init();
});

$(window).load(function() {

});