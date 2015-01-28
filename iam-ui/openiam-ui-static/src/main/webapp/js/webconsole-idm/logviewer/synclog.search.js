OPENIAM = window.OPENIAM || {};
OPENIAM.SyncLog = {
    initialSearchValue : "",
    size : 10,
    init : function() {
        var that = this;
        this.initialSearchValue = $("#SyncLogName").val();
        this.size = parseInt($("#size").val());
        $("#size").change(function() {
            OPENIAM.SyncLog.resetPage();
            OPENIAM.SyncLog.submit();
        });

        $("#filterClearOne").click(function() {
            $("#configId").val("");
            $("#startDate").val("");
            $("#endDate").val("");
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

OPENIAM.SyncLog.Table = {
    init : function() {
        var offsetPage = OPENIAM.ENV.page;
        $("#tableOne").tablesorter({
            debug: false,
            sortDisabled : true,
            /*sortList: [[0, 0]], */
            widgets: ['zebra'] }).tablesorterPager({
                container: $("#pagerOne"),
                positionFixed: false,
                size : OPENIAM.SyncLog.getSize(),
                page : offsetPage,
                totalRows : OPENIAM.ENV.count,
                totalPages : OPENIAM.ENV.totalPages,
                onFirstClick : function() {
                    $("#page").val(0);
                    OPENIAM.SyncLog.submit();
                },
                onLastClick : function() {
                    $("#page").val((OPENIAM.ENV.totalPages - 1));
                    OPENIAM.SyncLog.submit();
                },
                onPrevClick : function() {
                    if(offsetPage > 0) {
                        $("#page").val((offsetPage - 1));
                    }
                    OPENIAM.SyncLog.submit();
                },
                onNextClick : function() {
                    if(offsetPage < OPENIAM.ENV.totalPages) {
                        $("#page").val((offsetPage + 1));
                    }
                    OPENIAM.SyncLog.submit();
                }
            });

        $("#tableOne tbody tr").click(function() {
            window.open("synclog.html?id=" + $(this).attr("entityId"), "_blank");
        });
    }
};

$(document).ready(function() {
    OPENIAM.SyncLog.init();
    OPENIAM.SyncLog.Table.init();
});

$(window).load(function() {

});