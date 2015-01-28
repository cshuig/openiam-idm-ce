OPENIAM = window.OPENIAM || {};
OPENIAM.Groups = {
	request : function(args) {
		var systems = OPENIAM.ENV.ManagedSys;
		var managedSys = $(document.createElement("select")); 
		managedSys.addClass("full").addClass("rounded").attr("autocomplete", "off").attr("id", "managedSysId");
		if(systems != null && systems != undefined) {
			var option = $(document.createElement("option")).val("").text(localeManager["openiam.ui.shared.managed.system.select"]);
			managedSys.append(option);
			$.each(systems, function(idx, bean) {
				option = $(document.createElement("option")); option.val(bean.id).text(bean.name);
				managedSys.append(option);
			});
		}
		if(args.managedSysId) {
			managedSys.val(args.managedSysId);
		}
		
		var myInput = $(document.createElement("input"))
						.attr("type", "text").attr("id", "searchInput").attr("autocomplete", "off")
						.addClass("full").addClass("rounded")
						.attr("placeholder", localeManager["openiam.ui.shared.group.type.name"]); 
			if(args.initialSearchValue) {
				$(myInput).val(args.initialSearchValue);
			}
		OPENIAM.FN.applyPlaceholder(myInput);
		var mySearch = $(document.createElement("input"))
						.attr("type", "button")
						.attr("value", localeManager["openiam.ui.common.search"])
						.addClass("redBtn").attr("id", "search");
		
		var inputelements = [];
		inputelements.push(managedSys);
		inputelements.push(myInput);
		inputelements.push(mySearch);
		
		$("#entitlementsContainer").entitlemetnsTable({
			columnHeaders : [
				localeManager["openiam.ui.shared.group.name"], 
				localeManager["openiam.ui.shared.managed.system"], 
				localeManager["openiam.ui.common.actions"]
			],
			hasEditButton : true,
			onEdit : function(bean) {
				window.location.href = "editGroup.html?id=" + bean.id;
			},
            columnsMap : ["name", "managedSysName"],
            theadInputElements : inputelements,
			ajaxURL : "rest/api/entitlements/searchGroups",
			entityUrl : "editGroup.html",
			entityURLIdentifierParamName : "id",
			pageSize : 20,
			emptyResultsText : localeManager["openiam.ui.shared.group.search.empty"],
            showPageSizeSelector:true,
            sortEnable:true,
			onAppendDone : function() {
				this.find("#search").click(function() {
					OPENIAM.Groups.init();			
				});
			},
            validate : function(vdata) {
                var obj = {};
                obj.valid = 'true';
               // if((vdata.managedSysId == null || vdata.managedSysId == undefined || vdata.managedSysId == '')
               //     && (vdata.name == null || vdata.name == undefined || vdata.name == '')) {
               //     obj.valid = 'false';
               //     obj.message = localeManager["openiam.ui.search.dialog.searchfilter.request"];
               // }
                return obj;
            },
			getAdditionalDataRequestObject : function() {
				var obj = {
					name : $("#searchInput").val(),
					managedSysId : $("#managedSysId").val()
				};
				return obj;
			}
		});
	},
	init : function() {
		OPENIAM.Groups.request({initialSearchValue : $("#searchInput").val(), managedSysId : $("#managedSysId").val()});
	}
};

$(document).ready(function() {
	OPENIAM.Groups.init();
});

$(window).load(function() {
	
});