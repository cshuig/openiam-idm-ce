console = window.console || {};
console.log = window.console.log || function() {};

OPENIAM = window.OPENIAM || {};
OPENIAM.Report = window.OPENIAM.Report || {};
OPENIAM.Report.Search = {
	init : function() {
		$("#searchTable").entitlemetnsTable({
			ajaxURL : "/webconsole/searchReports.html",
			emptyResultsText : localeManager["openiam.ui.report.search.empty"],
			columnHeaders : [
                localeManager["openiam.ui.report.name"],
				localeManager["openiam.ui.common.actions"]
			],
			columnsMap : ["name"],
            hasEditButton : true,
            onEdit : function(bean) {
                window.location.href = "editReport.html?id=" + bean.id;
            },
            onView :  function(bean) {
                if (bean.parameterCount > 0) {
                    window.location.href = "viewReport.html?id=" + bean.id;
                } else {
                    window.open("/reportviewer/frameset?__report=" + bean.reportUrl, '_blank');
                }
            },
            entityURLIdentifierParamName : "id"
		});
	}
};

$(document).ready(function() {
	OPENIAM.Report.Search.init();
});

$(window).load(function() {

});