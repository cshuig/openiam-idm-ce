console = window.console || {};
console.log = window.console.log || function() {};

OPENIAM = window.OPENIAM || {};
OPENIAM.ViewReportSearch = window.OPENIAM.ViewReportSearch || {};
OPENIAM.ViewReportSearch.Search = {
	init : function() {
	

		$("#searchTable").entitlemetnsTable({
			
			ajaxURL : "/selfservice/viewReportSearch.html",
			emptyResultsText : localeManager["openiam.ui.report.view.reports.search.empty"],
			columnHeaders : [localeManager["openiam.ui.common.name"]],
			columnsMap : ["id"],
			hasEditButton : false,
			entityUrl : OPENIAM.ENV.ViewReportPageURL
		});
	}
};

$(document).ready(function() {
	OPENIAM.ViewReportSearch.Search.init();
});

$(window).load(function() {

});