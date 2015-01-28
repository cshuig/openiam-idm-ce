OPENIAM = window.OPENIAM || {};
OPENIAM.OrganizationType = {
	init : function() {
		$("#searchTable").entitlemetnsTable({
			ajaxURL : "organization/type/rest/search.html",
			emptyResultsText : OPENIAM.ENV.Text.EmptyResults,
			columnHeaders : [localeManager["openiam.ui.common.name"]],
			columnsMap : ["name"],
			hasEditButton : false,
			entityUrl : "organizationTypeEdit.html"
		});
	}
};

$(document).ready(function() {
	OPENIAM.OrganizationType.init();
});

$(window).load(function() {

});