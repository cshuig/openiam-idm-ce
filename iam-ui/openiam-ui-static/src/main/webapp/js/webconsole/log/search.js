OPENIAM = window.OPENIAM || {};

OPENIAM.SearchPage = {
	init : function(args) {
		$("#entitlementsContainer").logSearchResults({
			emptyMessage : localeManager["openiam.ui.audit.log.search.empty"],
			dialogTitle : localeManager["openiam.ui.audit.log.search"],
            validate : function(vdata) {
                var obj = {};
                obj.valid = 'true';
                if(vdata.fromDate == null || vdata.toDate == null || vdata.fromDate == undefined || vdata.toDate == undefined) {
                    obj.valid = 'false';
                    obj.message = localeManager["openiam.ui.search.dialog.searchfilter.request"];
                    if (!obj.fromDate) {
                        obj.fromDate = new Date();
                    }
                    if (!obj.toDate) {
                        obj.toDate = new Date();
                    }
                }
                return obj;
            }
		});
        setTimeout(function() { $("#entitlementsContainer").find("#search").click() }, 500);
	}
};

$(document).ready(function() {
	OPENIAM.SearchPage.init({});
});
