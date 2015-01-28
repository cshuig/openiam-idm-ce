OPENIAM = window.OPENIAM || {};
OPENIAM.CustomField = {
	request : function(args) {
		var myInput = document.createElement("input"); 
			$(myInput).attr("type", "text"); 
			myInput.className = "full rounded"; 
			$(myInput).attr("placeholder", localeManager["openiam.ui.common.type.field.name"]);
			$(myInput).attr("autocomplete", "off"); 
			myInput.id = "searchInput";
			if(args.initialSearchValue) {
				$(myInput).val(args.initialSearchValue);
			}
		OPENIAM.FN.applyPlaceholder(myInput);
		var mySearch = document.createElement("input"); $(mySearch).attr("type", "button"); $(mySearch).attr("value", localeManager["openiam.ui.idm.prov.connlist.searchBtn"]); mySearch.className = "redBtn"; mySearch.id = "search";
		
		var select = $(document.createElement("select")).attr("id", "type").append("<option value=''>"+localeManager["openiam.ui.common.field.type.select"]+"</option>");
		if($.isArray(OPENIAM.ENV.FieldTypes)) {
			$.each(OPENIAM.ENV.FieldTypes, function(idx, val) { 
				var option = $(document.createElement("option")); option.val(val.id); option.text(val.name);
				select.append(option);
				if(args.initialTypeValue && args.initialTypeValue == val.id) {
					option.attr("selected", "selected");
				}
			});
		}
		
		var inputelements = [];
        inputelements.push(myInput);
		inputelements.push(select);
		inputelements.push(mySearch);
		
		$("#entitlementsContainer").entitlemetnsTable({
			columnHeaders : [
                localeManager["openiam.ui.custom.fields.field.name"],
                localeManager["openiam.ui.custom.fields.field.type"],
				localeManager["openiam.ui.common.actions"]
			],
			hasEditButton : true,
			onEdit : function(bean) {
				window.location.href = "editCustomField.html?id=" + bean.id;
			},
            columnsMap : ["name", "fieldTypeDescription"],
            theadInputElements : inputelements,
			ajaxURL : "rest/api/fields/searchCustomFields",
			entityUrl : "editCustomField.html",
			entityURLIdentifierParamName : "id",
			pageSize : 10,
			emptyResultsText : localeManager["openiam.ui.custom.fields.empty.text"],
			onAppendDone : function() {
				this.find("#search").click(function() {
					OPENIAM.CustomField.init();			
				});
			},
			getAdditionalDataRequestObject : function() {
				var obj = {
					name : $("#searchInput").val(),
					typeId : $("#type").val()
				};
				return obj;
			}
		});
	},
	init : function() {
		OPENIAM.CustomField.request({initialSearchValue : $("#searchInput").val(), initialTypeValue : $("#type").val()});
	}
};

$(document).ready(function() {
	OPENIAM.CustomField.init();
});

$(window).load(function() {
	
});