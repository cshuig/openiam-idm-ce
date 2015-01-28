OPENIAM = window.OPENIAM || {};
OPENIAM.Entity = {
	request : function() {
		$("#entitlementsContainer").entitlemetnsTable({
			columnHeaders : [
				localeManager["openiam.ui.common.name"], 
				localeManager["openiam.ui.common.actions"]
			],
			hasEditButton : true,
			onEdit : function(bean) {
				window.location.href = "editAuthProvider.html?id=" + bean.id;
			},
            columnsMap : ["name"],
			ajaxURL : "getAuthenticationProviders.html",
			entityUrl : "editAuthProvider.html",
			entityURLIdentifierParamName : "id",
			pageSize : 10,
			emptyResultsText : localeManager["openiam.ui.am.auth.provider.search.empty"]
		});
	},
	init : function() {
		OPENIAM.Entity.request();
	}
};

$(document).ready(function() {
	OPENIAM.Entity.init();
});

$(window).load(function() {
	
});