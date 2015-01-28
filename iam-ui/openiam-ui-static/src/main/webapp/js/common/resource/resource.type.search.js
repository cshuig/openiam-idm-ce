OPENIAM = window.OPENIAM || {};

OPENIAM.ResourceType = {
    _getInputs : function(args) {
        var myInput = document.createElement("input");
        $(myInput).attr("type", "text");
        myInput.className = "full rounded";
        $(myInput).attr("placeholder", localeManager["openiam.ui.common.type.resource.type.select"]);
        $(myInput).attr("autocomplete", "off");
        myInput.id = "searchInput";
        if (args.initialSearchValue) {
            $(myInput).val(args.initialSearchValue);
        }
        OPENIAM.FN.applyPlaceholder(myInput);
        var mySearch = document.createElement("input");
        $(mySearch).attr("type", "button");
        $(mySearch).attr("value", localeManager["openiam.ui.button.search"]);
        mySearch.className = "redBtn";
        mySearch.id = "search";

        var inputs = [];
        inputs.push("");
        inputs.push("");
        inputs.push(myInput);
        inputs.push("");
        inputs.push(mySearch);
        inputs.push("");
        inputs.push("");
        return inputs;
        return inputs;
    },
    init : function(args) {
        var inputelements = this._getInputs(args);

        $("#entitlementsContainer").entitlemetnsTable({
            columnHeaders : [ localeManager["openiam.ui.common.name"], localeManager["openiam.common.type.search.process.name"], localeManager["openiam.common.type.search.support.hierarhy"], localeManager["openiam.common.type.search.is.searchable"], localeManager["openiam.common.type.search.provision.resource"], localeManager["openiam.ui.common.action"] ],
            columnsMap : [ "name", "processName", "supportsHierarchy", "searchable", "provisionResource"],
            hasEditButton : true,
            onEdit : function(bean) {
                window.location.href = "editResourceType.html?id=" + bean.id;
            },
            theadInputElements : inputelements,
            ajaxURL : "rest/api/entitlements/searchResourceTypes",
            entityUrl : "editResourceType.html",
            entityURLIdentifierParamName : "id",
            pageSize : 10,
            emptyResultsText : OPENIAM.ENV.Text.EmptyText,
            onAppendDone : function() {
                this.find("#search").click(function() {
                    OPENIAM.ResourceType.reinit();
                });
            },
            getAdditionalDataRequestObject : function() {
                var obj = {
                    decs : $("#searchInput").val(),
                    returnRootsOnMenuRequest : true,
                };
                return obj;
            }
        });
    },
    reinit : function() {
        OPENIAM.ResourceType.init({
            initialSearchValue : $("#searchInput").val()
        });
    }
};

$(document).ready(function() {
    OPENIAM.ResourceType.init({});
});

$(window).load(function() {

});