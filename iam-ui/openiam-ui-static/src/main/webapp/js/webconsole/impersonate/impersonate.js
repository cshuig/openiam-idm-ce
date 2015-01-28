OPENIAM = window.OPENIAM || {};

OPENIAM.User = {
    impersonate : function(id) {
    	console.log(id);
    	$.ajax({
			url : "impersonate.html",
			data : {id : id},
			type: "POST",
			dataType : "json",
			success : function(data, textStatus, jqXHR) {
				if(data.status == 200) {
					OPENIAM.Modal.Success({message : data.successMessage, showInterval : 2000, onIntervalClose : function() {
						parent.window.location.href = data.redirectURL;
					}});
				} else {
					OPENIAM.Modal.Error({errorList : data.errorList});
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
			}
		});
    },
    init : function() {
    	$("#formContainer").userSearchForm({
    		onSubmit : function(json) {
    			$("#resultsArea").userSearchResults({
    				"jsonData" : json,
    				"page" : 0,
    				"size" : 20,
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
    				],
    				onEntityClick : function(bean) {
    					OPENIAM.User.impersonate(bean.id);
    				}
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