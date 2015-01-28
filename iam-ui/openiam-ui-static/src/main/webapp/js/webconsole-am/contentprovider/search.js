OPENIAM = window.OPENIAM || {};

OPENIAM.SearchPage = {
    _getInputs : function(args) {
        var myInput = document.createElement("input");
        $(myInput).attr("type", "text");
        myInput.className = "full rounded";
        $(myInput).attr("placeholder", localeManager["contentprovider.search.placeholder"]);
        $(myInput).attr("autocomplete", "off");
        myInput.id = "searchInput";
        if (args.initialSearchValue) {
            $(myInput).val(args.initialSearchValue);
        }
        OPENIAM.FN.applyPlaceholder(myInput);
        var mySearch = document.createElement("input");
        $(mySearch).attr("type", "button");
        $(mySearch).attr("value", localeManager["openiam.ui.idm.prov.connlist.searchBtn"]);
        mySearch.className = "redBtn";
        mySearch.id = "search";

        var inputs = [];
        inputs.push("");
        inputs.push(myInput);
        inputs.push(mySearch);
        return inputs;
        return inputs;
    },
    init : function(args) {
        var inputelements = this._getInputs(args);

        $("#entitlementsContainer").entitlemetnsTable(
                {
                    columnHeaders : [ localeManager["contentprovider.search.cont.prov.name"], localeManager["contentprovider.search.domain.pattern"],
                            localeManager["openiam.ui.common.actions"] ],
                    columnsMap : [ "name", "domainPattern" ],
                    hasEditButton : true,
                    onEdit : function(bean) {
                        window.location.href = "editContentProvider.html?providerId=" + bean.id;
                    },
                    theadInputElements : inputelements,
                    ajaxURL : "searchContentProviders.html",
                    entityUrl : "editContentProvider.html",
                    entityURLIdentifierParamName : "providerId",
                    pageSize : 10,
                    emptyResultsText : localeManager["contentprovider.search.no"],
                    onAppendDone : function() {
                        this.find("#search").click(function() {
                            OPENIAM.SearchPage.reinit();
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
        OPENIAM.SearchPage.init({
            initialSearchValue : $("#searchInput").val()
        });
    }
};

$(document).ready(function() {
    OPENIAM.SearchPage.init({});
});

$(window).load(function() {

});