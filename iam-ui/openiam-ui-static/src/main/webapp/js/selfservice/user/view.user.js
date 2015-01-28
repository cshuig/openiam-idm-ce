OPENIAM = window.OPENIAM || {};
OPENIAM.User = window.OPENIAM.User || {};

OPENIAM.User.Form = {
	load : function(page) {
        $("#userResultsArea").userSearchResults({
            jsonData : this.toJSON(),
            page : 0,
            size : 20,
            initialSortColumn : "name",
            initialSortOrder : "ASC",
            entityURL: "viewUser.html",
            url : OPENIAM.ENV.ContextPath + "/subordinates/search.html",
            emptyResultsText : localeManager["openiam.ui.user.view.user.emptyreports"],
            emptyFormText : localeManager["openiam.ui.user.view.user.emptysearch"],
            columnHeaders : [
            	localeManager["openiam.ui.common.name"], 
            	localeManager["openiam.ui.common.phone.number"], 
            	localeManager["openiam.ui.common.email.address"], 
            	localeManager["openiam.ui.webconsole.user.status"], 
            	localeManager["openiam.ui.webconsole.user.accountStatus"]
            ]
        });
	},
    toJSON : function() {
        var obj = {};
        obj.requesterId =  OPENIAM.ENV.UserId;
        return obj;
    }
};

$(document).ready(function() {
    OPENIAM.User.Form.load(0);
    return false;
});

$(window).load(function() {
});