OPENIAM = window.OPENIAM || {};
OPENIAM.User = window.OPENIAM.User || {};

OPENIAM.User.Form = {
	submit : function(page) {
        $("#userResultsArea").userSearchResults({
            jsonData : this.toJSON(),
            page : 0,
            size : 20,
            initialSortColumn : "name",
            initialSortOrder : "ASC",
            entityURL: "viewUser.html",
            url : OPENIAM.ENV.ContextPath + "/rest/api/users/search",
            emptyFormText : localeManager["openiam.ui.common.user.search.empty"],
			emptyResultsText : localeManager["openiam.ui.common.user.search.no.results"],
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
        obj.firstName = $("#firstName").val();
        obj.lastName = $("#lastName").val();
        obj.email = $("#email").val();
        obj.phoneCode = $("#phoneCode").val();
        obj.phoneNumber = $("#phoneNumber").val();
        return obj;
    },
    clear : function() {
        $("#firstName").val("");
        $("#lastName").val("");
        $("#email").val("");
        $("#phoneCode").val("");
        $("#phoneNumber").val("");
        return false;
    }
};

$(document).ready(function() {
    $("#userSearchForm").submit(function() {
        OPENIAM.User.Form.submit(0);
        return false;
    });
    $("#cleanUserSearchForm").click(function() {
        OPENIAM.User.Form.clear();
        return false;
    });
});

$(window).load(function() {
});