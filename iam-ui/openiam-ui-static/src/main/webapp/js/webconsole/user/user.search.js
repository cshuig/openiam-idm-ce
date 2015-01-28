OPENIAM = window.OPENIAM || {};

OPENIAM.User = {
    size : 10,
    init : function() {
    	$("#formContainer").userSearchForm({
    		onSubmit : function(json) {
    			$("#resultsArea").userSearchResults({
    				"jsonData" : json,
    				page : 0,
    				size : 20,
                    initialSortColumn : "name",
                    initialSortOrder : "ASC",
    				url : "rest/api/users/search",
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
    		}
    	});

    }
};

OPENIAM.User.Table = {
    init : function() {

    }
};

$(document).ready(function() {
    OPENIAM.User.init();
});

$(window).load(function() {

});