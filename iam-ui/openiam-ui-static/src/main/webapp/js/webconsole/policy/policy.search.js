console = window.console || {};
console.log = window.console.log || function() {};

OPENIAM = window.OPENIAM || {};
OPENIAM.Policy = window.OPENIAM.Policy || {};
OPENIAM.Policy.Search = {
	request : function(args) {
		var myInput = document.createElement("input"); 
			$(myInput).attr("type", "text"); 
			myInput.className = "full rounded"; 
			$(myInput).attr("placeholder", localeManager["openiam.ui.policy.name.placeholder"]);
			$(myInput).attr("autocomplete", "off"); 
			myInput.id = "searchInput";
			if(args.initialSearchValue) {
				$(myInput).val(args.initialSearchValue);
			}
		OPENIAM.FN.applyPlaceholder(myInput);
		var mySearch = document.createElement("input"); $(mySearch).attr("type", "button"); $(mySearch).attr("value", localeManager["openiam.ui.common.search"]); mySearch.className = "redBtn"; mySearch.id = "search";
		
		var inputelements = [];
		inputelements.push("");
		inputelements.push(myInput);
		inputelements.push(mySearch);
		
		$("#searchTable").entitlemetnsTable({
			theadInputElements : inputelements,
			ajaxURL : "/webconsole/searchPolicies.html",
			emptyResultsText : OPENIAM.ENV.Text.EmptyResults,
			columnHeaders : [localeManager["openiam.ui.policy.name"], localeManager["openiam.ui.common.description"], localeManager["openiam.ui.common.description"]],
			columnsMap : ["name", "description", "status"],
			hasEditButton : false,
			entityUrl : OPENIAM.ENV.PolicyPageURL,
			onAppendDone : function() {
				this.find("#search").click(function() {
					OPENIAM.Policy.Search.init();			
				});
			},
			getAdditionalDataRequestObject : function() {
				return {
					policyType : OPENIAM.ENV.PolicyDefId,
					name : $("#searchInput").val()
				};
			}
		});
	},
	init : function() {
		OPENIAM.Policy.Search.request({initialSearchValue : $("#searchInput").val()})
	}
};

$(document).ready(function() {
	OPENIAM.Policy.Search.init();
});

$(window).load(function() {

});