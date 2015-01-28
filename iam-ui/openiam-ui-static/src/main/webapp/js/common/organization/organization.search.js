OPENIAM = window.OPENIAM || {};
OPENIAM.Organization = {
	request : function(args) {
		var selectInput = document.createElement("select");
		$(selectInput).append("<option value=\"\">" + localeManager["openiam.ui.common.organization.type.select"] + "</option>");
		selectInput.id = "organizationType";
		var types = OPENIAM.ENV.OrganizationTypes;
		if($.isArray(types)) {
			$.each(types, function(idx, obj) {
				var option = document.createElement("option"); $(option).val(obj.id); $(option).text(obj.name);
				if(args.selectInputValue && obj.id == args.selectInputValue) {
					$(option).attr("selected", "selected");
				}
				$(selectInput).append(option);
			});
		}
		
		var myInput = document.createElement("input"); 
			$(myInput).attr("type", "text"); 
			myInput.className = "full rounded"; 
			$(myInput).attr("placeholder", localeManager["openiam.ui.common.organization.name.type.command"]); 
			$(myInput).attr("autocomplete", "off"); 
			myInput.id = "searchInput";
			if(args.initialSearchValue) {
				$(myInput).val(args.initialSearchValue);
			}
		OPENIAM.FN.applyPlaceholder(myInput);
		var mySearch = document.createElement("input"); $(mySearch).attr("type", "button"); $(mySearch).attr("value", localeManager["openiam.ui.common.search"]); mySearch.className = "redBtn"; mySearch.id = "search";
		
		var inputelements = [];
        inputelements.push(selectInput);
		inputelements.push(myInput);
		inputelements.push(mySearch);
		
		$("#entitlementsContainer").entitlemetnsTable({
			columnHeaders : [
				localeManager["openiam.ui.common.organization.name"],
				localeManager["openiam.ui.common.organization.type"],
				localeManager["openiam.ui.common.actions"]
			],
			hasEditButton : true,
			onEdit : function(bean) {
				window.location.href = "editOrganization.html?id=" + bean.id;
			},
            columnsMap : ["name", "type"],
            theadInputElements : inputelements,
			ajaxURL : "rest/api/entitlements/searchOrganizations",
			entityUrl : "editOrganization.html",
			entityURLIdentifierParamName : "id",
			pageSize : 20,
			emptyResultsText : localeManager["openiam.ui.shared.organization.search.empty"],
            sortEnable:true,
			onAppendDone : function() {
				this.find("#search").click(function() {
					OPENIAM.Organization.init();			
				});
			},
            validate : function(vdata) {
                var obj = {};
                obj.valid = 'true';
                /*
                if((vdata.organizationTypeId == null || vdata.organizationTypeId == undefined || vdata.organizationTypeId == '')
                    && (vdata.name == null || vdata.name == undefined || vdata.name == '')) {
                    obj.valid = 'false';
                    obj.message = localeManager["openiam.ui.search.dialog.searchfilter.request"];
                }
                */
                return obj;
            },
			getAdditionalDataRequestObject : function() {
				var obj = {
					name : $("#searchInput").val(),
					organizationTypeId : [$("#organizationType").val()]
				};
				return obj;
			}
		});
	},
    init : function() {
		OPENIAM.Organization.request({initialSearchValue : $("#searchInput").val(), selectInputValue : $("#organizationType").val()});
	}
};

$(document).ready(function() {
    OPENIAM.Organization.init();
});

$(window).load(function() {

});