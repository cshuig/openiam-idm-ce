OPENIAM = window.OPENIAM || {};
OPENIAM.Entity = {
    request : function(args) {
        $("#entitlementsContainer").entitlemetnsTable({
            columnHeaders : [ localeManager["webconsole.ui.theme.name"], localeManager["openiam.ui.common.actions"] ],
            hasEditButton : true,
            onEdit : function(bean) {
                window.location.href = "editUITheme.html?id=" + bean.id;
            },
            columnsMap : [ "name" ],
            ajaxURL : "rest/api/metdata/uiTypes",
            entityUrl : "editUITheme.html",
            entityURLIdentifierParamName : "id",
            pageSize : 10,
            emptyResultsText : localeManager['webconsole.ui.theme.no.themes'],
            onAppendDone : function() {

            },
            getAdditionalDataRequestObject : function() {
                var obj = {};
                return obj;
            }
        });
    },
    init : function() {
        OPENIAM.Entity.request();
    }
};

$(document).ready(function() {
    OPENIAM.Entity.init();
});

$(window).load(function() {

});