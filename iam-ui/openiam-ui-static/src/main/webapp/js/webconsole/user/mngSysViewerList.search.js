OPENIAM = window.OPENIAM || {};
OPENIAM.MngSysViewer = {
    size : 10,
    init : function() {
        var that = this;
        this.size = parseInt($("#size").val());
        $("#size").change(function() {
            OPENIAM.MngSysViewer.resetPage();
            OPENIAM.MngSysViewer.submit();
        });

        $("#searchForm").submit(function() {
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

OPENIAM.MngSysViewer.Table = {
    init : function() {
        var offsetPage = OPENIAM.ENV.page;
        $("#tableOne").tablesorter({
            debug: false,
            sortDisabled : true,
            /*sortList: [[0, 0]], */
            widgets: ['zebra'] }).tablesorterPager({
                container: $("#pagerOne"),
                positionFixed: false,
                size : OPENIAM.MngSysViewer.getSize(),
                page : offsetPage,
                totalRows : OPENIAM.ENV.count,
                totalPages : OPENIAM.ENV.totalPages,
                onFirstClick : function() {
                    $("#page").val(0);
                    OPENIAM.MngSysViewer.submit();
                },
                onLastClick : function() {
                    $("#page").val((OPENIAM.ENV.totalPages - 1));
                    OPENIAM.MngSysViewer.submit();
                },
                onPrevClick : function() {
                    if(offsetPage > 0) {
                        $("#page").val((offsetPage - 1));
                    }
                    OPENIAM.MngSysViewer.submit();
                },
                onNextClick : function() {
                    if(offsetPage < OPENIAM.ENV.totalPages) {
                        $("#page").val((offsetPage + 1));
                    }
                    OPENIAM.MngSysViewer.submit();
                }
            });
    }
};

$(document).ready(function() {
    OPENIAM.MngSysViewer.init();
    OPENIAM.MngSysViewer.Table.init();
});

$(window).load(function() {
});