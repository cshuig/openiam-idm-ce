OPENIAM = window.OPENIAM || {};
OPENIAM.User = window.OPENIAM.User || {};

OPENIAM.User.History = {
   init : function() {
	   	$("#entitlementsContainer").logSearchResults({
			emptyMessage : localeManager["openiam.ui.user.history.empty.result"],
			dialogTitle : localeManager["openiam.ui.user.history.dialog.title"],
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
            },
			getStaticArguments : function() {
				var obj = {};
				obj.userId = OPENIAM.ENV.UserId;
				obj.targetUserId = OPENIAM.ENV.TargetUserId;
				return obj;
			},
			getAdditionalEditArgumentsAsString : function() {
				return "source=user&sourceId=" + OPENIAM.ENV.UserId;
			}
		});
        setTimeout(function() { $("#entitlementsContainer").find("#search").click() }, 500);
   }
};

$(document).ready(function() {
    OPENIAM.User.History.init();
});

$(window).load(function() {

});