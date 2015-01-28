OPENIAM = window.OPENIAM || {};
OPENIAM.Connector = {
    initialSearchValue : "",
    size : 10,
    init : function() {
        var that = this;
        this.initialSearchValue = $("#connectorName").val();
        this.size = parseInt($("#size").val());
        $("#size").change(function() {
            OPENIAM.Connector.resetPage();
            OPENIAM.Connector.submit();
        });

        $("#filterClearOne").click(function() {
            $("#provConnectorTypeId").val("");
            $("#connectorName").val("");
        });

        $("#searchForm").submit(function() {
            if(that.initialSearchValue != $("#connectorName").val()) {
                OPENIAM.Connector.resetPage();
            }
        });
    },
    getSize : function() {
        return this.size;
    },
    submit : function() {
        $("#searchForm").submit();
    },
    resetPage : function() {
        $("#page").val(0);
    }
};

OPENIAM.Connector.Table = {
    init : function() {
        var offsetPage = OPENIAM.ENV.page;
        $("#tableOne").tablesorter({
            debug: false,
            sortDisabled : true,
            /*sortList: [[0, 0]], */
            widgets: ['zebra'] }).tablesorterPager({
                container: $("#pagerOne"),
                positionFixed: false,
                size : OPENIAM.Connector.getSize(),
                page : offsetPage,
                totalRows : OPENIAM.ENV.count,
                totalPages : OPENIAM.ENV.totalPages,
                onFirstClick : function() {
                    $("#page").val(0);
                    OPENIAM.Connector.submit();
                },
                onLastClick : function() {
                    $("#page").val((OPENIAM.ENV.totalPages - 1));
                    OPENIAM.Connector.submit();
                },
                onPrevClick : function() {
                    if(offsetPage > 0) {
                        $("#page").val((offsetPage - 1));
                    }
                    OPENIAM.Connector.submit();
                },
                onNextClick : function() {
                    if(offsetPage < OPENIAM.ENV.totalPages) {
                        $("#page").val((offsetPage + 1));
                    }
                    OPENIAM.Connector.submit();
                }
            });

        $("#tableOne tbody tr").click(function() {
            window.location.href = "connector.html?id=" + $(this).attr("entityId");
        });
    }
};

$(document).ready(function() {
    OPENIAM.Connector.init();
    OPENIAM.Connector.Table.init();
});

$(window).load(function() {

});