OPENIAM = window.OPENIAM || {};
OPENIAM.SynchConfig = {
    initialSearchValue : "",
    size : 10,
    init : function() {
        var that = this;
        this.initialSearchValue = $("#synchConfigName").val();
        this.size = parseInt($("#size").val());
        $("#size").change(function() {
            OPENIAM.SynchConfig.resetPage();
            OPENIAM.SynchConfig.submit();
        });

        $("#filterClearOne").click(function() {
            $("#synchConfigName").val("");
        });

        $("#searchForm").submit(function() {
            if(that.initialSearchValue != $("#synchConfigName").val()) {
                OPENIAM.SynchConfig.resetPage();
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

OPENIAM.SynchConfig.Table = {
    init : function() {
        var offsetPage = OPENIAM.ENV.page;
        $("#tableOne").tablesorter({
            debug: false,
            sortDisabled : true,
            /*sortList: [[0, 0]], */
            widgets: ['zebra'] }).tablesorterPager({
                container: $("#pagerOne"),
                positionFixed: false,
                size : OPENIAM.SynchConfig.getSize(),
                page : offsetPage,
                totalRows : OPENIAM.ENV.count,
                totalPages : OPENIAM.ENV.totalPages,
                onFirstClick : function() {
                    $("#page").val(0);
                    OPENIAM.SynchConfig.submit();
                },
                onLastClick : function() {
                    $("#page").val((OPENIAM.ENV.totalPages - 1));
                    OPENIAM.SynchConfig.submit();
                },
                onPrevClick : function() {
                    if(offsetPage > 0) {
                        $("#page").val((offsetPage - 1));
                    }
                    OPENIAM.SynchConfig.submit();
                },
                onNextClick : function() {
                    if(offsetPage < OPENIAM.ENV.totalPages) {
                        $("#page").val((offsetPage + 1));
                    }
                    OPENIAM.SynchConfig.submit();
                }
            });

        $("#tableOne tbody tr").click(function() {
            window.location.href = "synchronization.html?id=" + $(this).attr("entityId");
        });
    }
};

$(document).ready(function() {
    OPENIAM.SynchConfig.init();
    OPENIAM.SynchConfig.Table.init();
});

$(window).load(function() {

});