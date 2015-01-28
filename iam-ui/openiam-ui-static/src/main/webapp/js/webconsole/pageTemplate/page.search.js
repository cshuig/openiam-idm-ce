OPENIAM = window.OPENIAM || {};
OPENIAM.PageTemplate = {
    initialTemplateName : "",
    size : 10,
    init : function() {
        var that = this;
        this.initialTemplateName = $("#templateName").val();
        this.size = parseInt($("#size").val());
        $("#size").change(function() {
            OPENIAM.PageTemplate.resetPage();
            OPENIAM.PageTemplate.submit();
        });

        $("#filterClearOne").click(function() {
            $("#templateName").val("");
        });

        $("#searchForm").submit(function() {
            if(that.initialTemplateName != $("#templateName").val()) {
                OPENIAM.PageTemplate.resetPage();
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

OPENIAM.PageTemplate.Table = {
    init : function() {
        var offsetPage = OPENIAM.ENV.page;
        $("#tableOne").tablesorter({
            debug: false,
            sortDisabled : true,
            /*sortList: [[0, 0]], */
            widgets: ['zebra'] }).tablesorterPager({
                container: $("#pagerOne"),
                positionFixed: false,
                size : OPENIAM.PageTemplate.getSize(),
                page : offsetPage,
                totalRows : OPENIAM.ENV.count,
                totalPages : OPENIAM.ENV.totalPages,
                onFirstClick : function() {
                    $("#page").val(0);
                    OPENIAM.PageTemplate.submit();
                },
                onLastClick : function() {
                    $("#page").val((OPENIAM.ENV.totalPages - 1));
                    OPENIAM.PageTemplate.submit();
                },
                onPrevClick : function() {
                    if(offsetPage > 0) {
                        $("#page").val((offsetPage - 1));
                    }
                    OPENIAM.PageTemplate.submit();
                },
                onNextClick : function() {
                    if(offsetPage < OPENIAM.ENV.totalPages) {
                        $("#page").val((offsetPage + 1));
                    }
                    OPENIAM.PageTemplate.submit();
                }
            });

        $("#tableOne tbody tr").click(function() {
            window.location.href = "editPageTemplate.html?id=" + $(this).attr("entityId");
        });
    }
};

$(document).ready(function() {
    OPENIAM.PageTemplate.init();
    OPENIAM.PageTemplate.Table.init();
});

$(window).load(function() {

});