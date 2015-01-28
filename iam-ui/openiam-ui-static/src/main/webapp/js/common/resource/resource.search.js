OPENIAM = window.OPENIAM || {};

OPENIAM.Resource = {
	_getInputs : function(args) {
		var select = $(document.createElement("select")); select.attr("id", "resourceType");
			select.append("<option value=\"\">"+localeManager["openiam.ui.common.resource.type.select"]+"</option>");
			if(OPENIAM.ENV.ResourceTypes != null) {
				for(var i = 0; i < OPENIAM.ENV.ResourceTypes.length; i++) {
					var type = OPENIAM.ENV.ResourceTypes[i];
					var option = $(document.createElement("option")); option.val(type.id); option.text(type.name);
					if(args.initialResourceType && args.initialResourceType == type.id) {
						option.attr("selected", "selected");
					}
					select.append(option);
				}
			}
		if(OPENIAM.ENV.TargetResourceType) {
			var option = $(document.createElement("option")); option.attr("selected", "selected"); 
			option.val(OPENIAM.ENV.TargetResourceType); option.text(OPENIAM.ENV.TargetResourceType);
			select.append(option);
			select.hide();
		}
			
		var myInput = document.createElement("input"); 
			$(myInput).attr("type", "text"); 
			myInput.className = "full rounded"; 
			$(myInput).attr("placeholder", localeManager["openiam.ui.shared.resource.name.type"]);
			$(myInput).attr("autocomplete", "off"); 
			myInput.id = "searchInput";
			if(args.initialSearchValue) {
				$(myInput).val(args.initialSearchValue);
			}
		OPENIAM.FN.applyPlaceholder(myInput);
		var mySearch = document.createElement("input"); $(mySearch).attr("type", "button"); $(mySearch).attr("value", localeManager["openiam.ui.idm.prov.connlist.searchBtn"]); mySearch.className = "redBtn"; mySearch.id = "search";
		
		var inputs = [];
		inputs.push("");
		inputs.push(select);
		inputs.push(myInput);
        inputs.push("");
        inputs.push(mySearch);
		return inputs;
		return inputs;
	},
	init : function(args) {
		var inputelements = this._getInputs(args);
		
		$("#entitlementsContainer").entitlemetnsTable({
			columnHeaders : [
                localeManager["openiam.ui.shared.resource.name"],
                localeManager["openiam.ui.common.type"],
                localeManager["openiam.ui.common.description"],
                localeManager["openiam.ui.common.risk"],
				localeManager["openiam.ui.common.actions"]
			],
			columnsMap : ["name", "resourceType", "description", "risk"],
			hasEditButton : true,
			onEdit : function(bean) {
				window.location.href = "editResource.html?id=" + bean.id;
			},
            theadInputElements : inputelements,
			ajaxURL : "rest/api/entitlements/searchResources",
			entityUrl : "editResource.html",
			entityURLIdentifierParamName : "id",
			pageSize : 20,
			emptyResultsText : localeManager["openiam.ui.shared.resource.search.empty"],
            showPageSizeSelector:true,
            sortEnable:true,
			onAppendDone : function() {
				this.find("#search").click(function() {
					OPENIAM.Resource.reinit();		
				});
			},
			getAdditionalDataRequestObject : function() {
				var obj = {
					name : $("#searchInput").val(),
					resourceTypeId : (OPENIAM.ENV.TargetResourceType != null) ? OPENIAM.ENV.TargetResourceType : $("#resourceType").val(),
					returnRootsOnMenuRequest : true,
					excludeResourceType : OPENIAM.ENV.ExcludeResourceType
				};
				return obj;
			}
		});
	},
	reinit : function() {
		OPENIAM.Resource.init({initialResourceType:$("#resourceType").val(), initialSearchValue:$("#searchInput").val()});
	}
};

$(document).ready(function() {
	OPENIAM.Resource.init({});
});

$(window).load(function() {
	
});