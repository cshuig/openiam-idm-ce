OPENIAM = window.OPENIAM || {};
OPENIAM.Groups = {
	request : function(args) {
		var systems = OPENIAM.ENV.ManagedSys;
        var $managedSys;
		if(systems != null && systems != undefined) {
            $managedSys = $(document.createElement("select"));
            $managedSys.addClass("full").addClass("rounded").attr("autocomplete", "off").attr("id", "managedSysId");
			var option = $(document.createElement("option")).val("").text(localeManager["openiam.ui.shared.managed.system.select"]);
			$managedSys.append(option);
			$.each(systems, function(idx, bean) {
				option = $(document.createElement("option")); option.val(bean.id).text(bean.name);
				$managedSys.append(option);
			});
            if(args.managedSysId) {
                $managedSys.val(args.managedSysId);
            }
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
        if ($managedSys != null && $managedSys != undefined) {
            inputelements.push($managedSys);
        } else {
            inputelements.push('');
        }
		inputelements.push(myInput);
		inputelements.push(mySearch);

        var searchUrl = "rest/api/entitlements/searchGroups";
        if (OPENIAM.ENV.ManagedSysId != null && OPENIAM.ENV.ManagedSysId != undefined) {
            searchUrl += "?managedSysId=" + OPENIAM.ENV.ManagedSysId;
        }
        var entityUrl = "editGroup.html";
        if (OPENIAM.ENV.EntityURL != null && OPENIAM.ENV.EntityURL != undefined) {
            entityUrl = OPENIAM.ENV.EntityURL;
        }
        var onAdd = null;
        if (OPENIAM.ENV.onAdd != null && $.isFunction(OPENIAM.ENV.onAdd)) {
            onAdd=OPENIAM.ENV.onAdd;
        }
        var onEdit = function(bean){
            window.location.href = entityUrl + "?id=" + bean.id;
        };
        if (OPENIAM.ENV.onEdit != null && $.isFunction(OPENIAM.ENV.onEdit)) {
            onEdit=OPENIAM.ENV.onEdit;
        }

        var emptyResultsText = localeManager["openiam.ui.shared.group.search.empty"];
        if (OPENIAM.ENV.emptyResultsText != null && OPENIAM.ENV.emptyResultsText != undefined) {
            emptyResultsText=OPENIAM.ENV.emptyResultsText;
        }

		$("#entitlementsContainer").entitlemetnsTable({
			columnHeaders : [
				localeManager["openiam.ui.shared.group.name"], 
				localeManager["openiam.ui.shared.managed.system"], 
				localeManager["openiam.ui.common.actions"]
			],
			hasEditButton : OPENIAM.ENV.hasEditButton,
			onEdit : onEdit,
            columnsMap : ["name", "managedSysName"],
            theadInputElements : inputelements,
			ajaxURL : searchUrl,
			entityUrl : entityUrl,
			entityURLIdentifierParamName : "id",
			pageSize : 20,
			emptyResultsText : emptyResultsText,
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
				var obj = {};
                obj['name'] = $("#searchInput").val();
                var $managedSysId = $("#managedSysId");
                if ($managedSysId) {
                    obj['managedSysId'] = $managedSysId.val();
                }
				if(OPENIAM.ENV.OwnerId!=null && OPENIAM.ENV.OwnerId!=undefined){
					obj.ownerId = OPENIAM.ENV.OwnerId;
				}
				return obj;
			},
            onAdd: onAdd
		});
	},
	init : function() {
        var obj = {};
        obj['initialSearchValue'] = $("#searchInput").val();
        var $managedSysId = $("#managedSysId");
        if ($managedSysId) {
            obj['managedSysId'] = $managedSysId.val();
        }
		OPENIAM.Groups.request(obj);
	}
};

$(document).ready(function() {
	OPENIAM.Groups.init();
});

$(window).load(function() {
	
});