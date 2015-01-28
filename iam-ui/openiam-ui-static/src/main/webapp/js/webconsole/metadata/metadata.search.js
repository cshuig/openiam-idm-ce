OPENIAM = window.OPENIAM || {};
OPENIAM.Metadata = {
    _getInputs : function(args) {
        var select = $(document.createElement("select"));
        select.attr("id", "metadataType");
        select.append("<option value=\"\">" + localeManager['contentprovider.provider.meta.data.type.search'] + "</option>");
        if (OPENIAM.ENV.metadataTypes != null) {
            for (var i = 0; i < OPENIAM.ENV.metadataTypes.length; i++) {
                var type = OPENIAM.ENV.metadataTypes[i];
                var option = $(document.createElement("option"));
                option.val(type.id);
                option.text(type.name);
                if (args && args.initialMetadataType && args.initialMetadataType == type.id) {
                    option.attr("selected", "selected");
                }
                select.append(option);
            }
        }
        if (OPENIAM.ENV.metadataTypeId) {
            var option = $(document.createElement("option"));
            option.attr("selected", "selected");
            option.val(OPENIAM.ENV.metadataTypeId);
            option.text(OPENIAM.ENV.metadataTypeId);
            select.append(option);
            select.hide();
        }

        var myInput = document.createElement("input");
        $(myInput).attr("type", "text");
        myInput.className = "full rounded";
        $(myInput).attr("placeholder", localeManager["metadata.search.name.placeholder"]);
        $(myInput).attr("autocomplete", "off");
        myInput.id = "searchInput";
        if (args && args.initialSearchValue) {
            $(myInput).val(args.initialSearchValue);
        }
        OPENIAM.FN.applyPlaceholder(myInput);
        var mySearch = document.createElement("input");
        $(mySearch).attr("type", "button");
        $(mySearch).attr("value", localeManager['openiam.ui.idm.synch.synchlist.searchBtn']);
        mySearch.className = "redBtn";
        mySearch.id = "search";

        var inputs = [];
        inputs.push(select);
        inputs.push(myInput);
        inputs.push(mySearch);
        return inputs;
        return inputs;
    },
    init : function(args) {
        var inputelements = this._getInputs(args);

        $("#entitlementsContainer").entitlemetnsTable(
                {
                    columnHeaders : [ localeManager["openiam.ui.common.name"], localeManager["openiam.ui.idm.synch.table.col.type"],
                            localeManager["openiam.ui.common.actions"] ],
                    columnsMap : [ "name", "typeName" ],
                    hasEditButton : true,
                    onEdit : function(bean) {
                        window.location.href = "metaDataEdit.html?id=" + bean.id;
                    },
                    theadInputElements : inputelements,
                    ajaxURL : "rest/api/metadata/element/search",
                    entityUrl : "metaDataEdit.html",
                    entityURLIdentifierParamName : "id",
                    pageSize : 10,
                    emptyResultsText : localeManager["metadata.search.empty"],
                    getAdditionalDataRequestObject : function() {
                        var obj = {
                            name : $("#searchInput").val(),
                            type : (OPENIAM.ENV.metadataTypeId != null) ? OPENIAM.ENV.metadataTypeId : $("#metadataType").val(),
                            returnRootsOnMenuRequest : true,
                        };
                        return obj;
                    },
                    onAppendDone : function() {
                        this.find("#search").click(function() {
                            OPENIAM.Metadata.reinit();
                        });
                    }
                });
    },
    reinit : function() {
        OPENIAM.Metadata.init({
            initialMetadataType : $("#metadataType").val(),
            initialSearchValue : $("#searchInput").val()
        });
    }
};

$(document).ready(function() {
    OPENIAM.Metadata.init();
});

$(window).load(function() {

});