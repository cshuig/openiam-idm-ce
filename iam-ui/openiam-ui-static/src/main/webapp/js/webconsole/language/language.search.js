OPENIAM = window.OPENIAM || {};
OPENIAM.Languages = {
    _getInputs : function(args) {
        var myInput = document.createElement("input");
        $(myInput).attr("type", "text");
        myInput.className = "full rounded";
        $(myInput).attr("placeholder", localeManager["openiam.ui.webconsole.languages.placeholder.search"]);
        $(myInput).attr("autocomplete", "off");
        $(myInput).attr("width", "15");
        myInput.id = "searchInput";
        if (args && args.initialSearchValue) {
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
        inputs.push("");
        inputs.push("");
        inputs.push(mySearch);
        return inputs;
    },
    init : function(args) {
        var inputelements = this._getInputs(args);
        $("#entitlementsContainer").entitlemetnsTable(
                {
                    ajaxURL : "languages/rest/search.html",
                    emptyResultsText : localeManager[OPENIAM.ENV.Text.EmptyResults],
                    columnHeaders : [ localeManager["openiam.ui.common.display.name"], localeManager["openiam.ui.common.code"],
                            localeManager["openiam.ui.common.is.default"], localeManager["openiam.ui.common.is.active"],
                            localeManager["openiam.ui.common.actions"] ],
                    columnsMap : [ "name", "code", "isDefault", "isUsed" ],
                    hasEditButton : true,
                    onEdit : function(bean) {
                        window.location.href = "languageEdit.html?id=" + bean.id;
                    },
                    theadInputElements : inputelements,
                    getAdditionalDataRequestObject : function() {
                        var obj = {
                            code : $("#searchInput").val()
                        };
                        return obj;
                    },
                    onAppendDone : function() {
                        this.find("#search").click(function() {
                            OPENIAM.Languages.reinit();
                        });
                    }
                });
    },
    reinit : function() {
        OPENIAM.Languages.init({
            initialSearchValue : $("#searchInput").val()
        });
    }
};

$(document).ready(function() {
    OPENIAM.Languages.init();
});

$(window).load(function() {

});