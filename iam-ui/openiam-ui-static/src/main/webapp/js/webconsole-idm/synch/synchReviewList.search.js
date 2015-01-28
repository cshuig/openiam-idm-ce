OPENIAM = window.OPENIAM || {};
OPENIAM.SynchReviewList = {
    size : 10,
    init : function() {
        var that = this;
        this.size = parseInt($("#size").val());
        $("#size").change(function() {
            OPENIAM.SynchReviewList.resetPage();
            OPENIAM.SynchReviewList.submit();
        });
        $("#searchForm").submit(function() {
        });
        $("#selectAll").click(function() {
            var checked = $(this).is(':checked');
            $("#searchForm [name='entityIds']").prop('checked', checked);
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

OPENIAM.SynchReviewList.Table = {
    init : function() {
        var offsetPage = OPENIAM.ENV.page;
        $("#tableOne").tablesorter({
            debug: false,
            sortDisabled : true,
            /*sortList: [[0, 0]], */
            widgets: ['zebra'] }).tablesorterPager({
                container: $("#pagerOne"),
                positionFixed: false,
                size : OPENIAM.SynchReviewList.getSize(),
                page : offsetPage,
                totalRows : OPENIAM.ENV.count,
                totalPages : OPENIAM.ENV.totalPages,
                onFirstClick : function() {
                    $("#page").val(0);
                    OPENIAM.SynchReviewList.submit();
                },
                onLastClick : function() {
                    $("#page").val((OPENIAM.ENV.totalPages - 1));
                    OPENIAM.SynchReviewList.submit();
                },
                onPrevClick : function() {
                    if(offsetPage > 0) {
                        $("#page").val((offsetPage - 1));
                    }
                    OPENIAM.SynchReviewList.submit();
                },
                onNextClick : function() {
                    if(offsetPage < OPENIAM.ENV.totalPages) {
                        $("#page").val((offsetPage + 1));
                    }
                    OPENIAM.SynchReviewList.submit();
                }
            });

        $("#tableOne tbody tr").click(function() {
//            window.location.href = "synchReview.html?id=" + $(this).attr("entityId");
        });
    }
};

$(document).ready(function() {
    OPENIAM.SynchReviewList.init();
    OPENIAM.SynchReviewList.Table.init();
});

$(window).load(function() {
});