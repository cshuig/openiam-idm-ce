OPENIAM = window.OPENIAM || {};
OPENIAM.MetadataType = {
    _getInputs : function(args) {
        var myInput = $(document.createElement("input"))
        				.attr("type", "text")
        				.addClass("full").addClass("rounded")
        				.attr("placeholder", localeManager["metadata.type.search.name.placeholder"])
        				.attr("autocomplete", "off")
        				.attr("id", "searchInput");
        OPENIAM.FN.applyPlaceholder(myInput);
        var mySearch = $(document.createElement("input"))
        					.addClass("redBtn")
        					.attr("id", "search")
        					.attr("type", "button")
        					.attr("value", localeManager["openiam.ui.common.search"])

        var select = $(document.createElement("select")).attr("id", "grouping").addClass("rounded").addClass("full");
        select.append($(document.createElement("option")).val("").text(localeManager["openiam.ui.webconsole.meta.type.grouping.select"]));
        $.each(OPENIAM.ENV.Groupings, function(idx, grouping) {
        	select.append($(document.createElement("option")).val(grouping).text(localeManager["openiam.ui.webconsole.meta.type.grouping." + grouping]));
        });
        OPENIAM.FN.applyPlaceholder(select);
        
        if (args) {
        	if(args.initialSearchValue) {
            	$(myInput).val(args.initialSearchValue);
        	}
        	if(args.grouping) {
        		select.val(args.grouping);
        	}
        }
        					
        var inputs = [];
        inputs.push(myInput);
        inputs.push(select);
        inputs.push(mySearch);
        return inputs;
    },
    init : function(args) {
        var inputelements = this._getInputs(args);
        $("#entitlementsContainer").entitlemetnsTable({
            ajaxURL : "rest/api/metadata/type/search",
            emptyResultsText : localeManager["metadata.type.search.empty"],
            columnHeaders : [ 
            	localeManager["openiam.ui.webconsole.meta.type.name"],
            	localeManager["metadata.type.search.edit.grouping"],
            	localeManager["openiam.ui.common.actions"]
            ],
            columnsMap : [ "name", "grouping"],
            hasEditButton : true,
            onEdit : function(bean) {
                window.location.href = "metaDataTypeEdit.html?id=" + bean.id;
            },
            entityUrl : "metaDataTypeEdit.html",
            theadInputElements : inputelements,
            getAdditionalDataRequestObject : function() {
                var obj = {
                    name : $("#searchInput").val(),
                    grouping : $("#grouping").val()
                };
                return obj;
            },
            onAppendDone : function() {
                this.find("#search").click(function() {
                    OPENIAM.MetadataType.reinit();
                });
            }
        });
    },
    reinit : function() {
        OPENIAM.MetadataType.init({
            initialSearchValue : $("#searchInput").val(),
            grouping : $("#grouping").val()
        });
    }
};

$(document).ready(function() {
    OPENIAM.MetadataType.init();
});

$(window).load(function() {

});