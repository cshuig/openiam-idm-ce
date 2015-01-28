OPENIAM = window.OPENIAM || {};
OPENIAM.SystemList = {
	_getInputs : function(args) {

		var myInput = document.createElement("input"); 
			$(myInput).attr("type", "text"); 
			myInput.className = "full rounded"; 
			$(myInput).attr("placeholder", localeManager["openiam.ui.idm.prov.mngsys.managedSysName"]);
			$(myInput).attr("autocomplete", "off"); 
			myInput.id = "searchInput";
			if(args.initialSearchValue) {
				$(myInput).val(args.initialSearchValue);
			}
		OPENIAM.FN.applyPlaceholder(myInput);
		var mySearch = document.createElement("input"); $(mySearch).attr("type", "button"); $(mySearch).attr("value", localeManager["openiam.ui.common.search"]); mySearch.className = "redBtn"; mySearch.id = "search";
		
		var inputs = [];
		inputs.push("");
        inputs.push("");
		inputs.push(myInput);
		inputs.push(mySearch);
		return inputs;
	},
    init : function(args) {
    	args = (args == null || args == undefined) ? {} : args;
    	var inputelements = this._getInputs(args);
		
		$("#entitlementsContainer").entitlemetnsTable({
			columnHeaders : [
				localeManager["openiam.ui.common.name"],
                localeManager["openiam.ui.common.status"],
                localeManager["openiam.ui.idm.prov.mngsys.field.host_url"],
				localeManager["openiam.ui.common.actions"]
			],
			columnsMap : ["name", "status", "hostURL"],
			hasEditButton : true,
			onEdit : function(bean) {
				window.location.href = "mngsystem.html?id=" + bean.id;
			},
            theadInputElements : inputelements,
			ajaxURL : "searchManagedSystems.html",
			entityUrl : "mngsystem.html",
			entityURLIdentifierParamName : "id",
			pageSize : 10,
			emptyResultsText : OPENIAM.ENV.Text.EmptyText,
			onAppendDone : function() {
				this.find("#search").click(function() {
					OPENIAM.SystemList.reinit();		
				});
			},
			getAdditionalDataRequestObject : function() {
				var obj = {
					name : $("#searchInput").val()
				};
				return obj;
			}
		});
    },
    reinit : function() {
    	OPENIAM.SystemList.init({initialSearchValue:$("#searchInput").val()});
    }
};

$(document).ready(function() {
    OPENIAM.SystemList.init();
});

$(window).load(function() {

});