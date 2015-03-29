OPENIAM = window.OPENIAM || {};
OPENIAM.Location = {
    request : function(args) {
        var myInput = document.createElement("input");
        $(myInput).attr("type", "text");
        myInput.className = "full rounded";
        $(myInput).attr("placeholder", localeManager["openiam.ui.common.location.name"]);
        $(myInput).attr("autocomplete", "off");
        myInput.id = "searchInput";
        if(args.initialSearchValue) {
            $(myInput).val(args.initialSearchValue);
        }
        OPENIAM.FN.applyPlaceholder(myInput);
        var mySearch = document.createElement("input");
        $(mySearch).attr("type", "button");
        $(mySearch).attr("value", localeManager["openiam.ui.common.search"]);
        mySearch.className = "redBtn"; mySearch.id = "search";

        var inputelements = [];
        inputelements.push(myInput);
        inputelements.push(mySearch);

        $("#entitlementsContainer").entitlemetnsTable({
            columnHeaders : [
                localeManager["openiam.ui.common.location.name"],
                localeManager["openiam.ui.common.location.address"],
                localeManager["openiam.ui.common.is.active"],
                localeManager["openiam.ui.common.actions"]
            ],
            hasEditButton : true,
            onEdit : function(bean) {
                window.location.href = "organizationLocation.html?id=" + bean.id;
            },
            columnsMap : ["name","displayDescription","active"],
            theadInputElements : inputelements,
            ajaxURL : "rest/api/entitlements/searchLocations",
            entityUrl : "organizationLocation.html",
            entityURLIdentifierParamName : "id",
            pageSize : 20,
            emptyResultsText : localeManager["openiam.ui.common.location.empty"],
            sortEnable:true,
            onAppendDone : function() {
                this.find("#search").click(function() {
                    OPENIAM.Location.init();
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
                    organizationId: OPENIAM.ENV.OrganizationId
                };
                return obj;
            }
        });
    },
    init : function() {
        OPENIAM.Location.request({initialSearchValue : $("#searchInput").val()});
    }
};

$(document).ready(function() {
    OPENIAM.Location.init();
});

$(window).load(function() {

});