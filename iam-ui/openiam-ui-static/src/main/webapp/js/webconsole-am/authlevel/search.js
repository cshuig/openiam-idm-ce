OPENIAM = window.OPENIAM || {};

OPENIAM.Search = {
	request : function() {
		$("#entitlementsContainer").entitlemetnsTable({
			columnHeaders : [
                localeManager["openiam.ui.webconsole.am.authlevel"],
                localeManager["openiam.ui.webconsole.am.authlevel.groupingname"],
				localeManager["openiam.ui.common.actions"]
			],
			hasEditButton : true,
			onEdit : function(bean) {
				window.location.href = "editAuthLevelGrouping.html?id=" + bean.id;
			},
            columnsMap : ["authLevelName", "name"],
			ajaxURL : "rest/api/federation/authGroupings",
			entityUrl : "editAuthLevelGrouping.html",
			entityURLIdentifierParamName : "id",
			pageSize : 10,
			emptyResultsText : localeManager["openiam.ui.webconsole.am.authlevel.search.nogroupfound"],
			onAppendDone : function() {
				
			},
			getAdditionalDataRequestObject : function() {
				var obj = {
				};
				return obj;
			}
		});
	}
};

$(document).ready(function() {
	OPENIAM.Search.request();
});