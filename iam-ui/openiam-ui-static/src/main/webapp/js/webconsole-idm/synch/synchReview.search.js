OPENIAM = window.OPENIAM || {};
OPENIAM.SynchReview = {
    size : 10,
    init : function() {
        var that = this;
        this.size = parseInt($("#size").val());
        $("#size").change(function() {
            OPENIAM.SynchReview.resetPage();
            OPENIAM.SynchReview.submit();
        });
        $("#save").click(function() {
            OPENIAM.SynchReview.execute("updateSynchReview.html");
            return false;
        });
        $("#execute").click(function() {
            OPENIAM.SynchReview.execute("executeSynchReview.html");
            return false;
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
    },
    execute : function(url) {
        $.ajax({
            url : url,
            data : JSON.stringify(this.toJSON()),
            type: "POST",
            dataType : "json",
            contentType: "application/json",
            success : function(data, textStatus, jqXHR) {
                if(data.status == 200) {
                    OPENIAM.Modal.Success({message : data.successMessage, showInterval : 2000, onIntervalClose : function() {
                        OPENIAM.SynchReview.submit();
                    }});
                } else {
                    OPENIAM.Modal.Error({errorList : data.errorList});
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
    },
    toJSON : function() {
        var obj = {};
        obj.synchConfigId = $("#synchConfigId").val();
        obj.synchReviewId = $("#synchReviewId").val();
        obj.skipSourceValid = $("#skipSourceValid").prop("checked");
        obj.skipRecordValid = $("#skipRecordValid").prop("checked");
        obj.sourceRejected = false;
        obj.createTime = null;
        obj.modifyTime = null;
        obj.execTime = null;
        obj.reviewRecords = [];


        $("#tableRecords").find("tr.record").each(function(i, tr) {
            var rec = {};
            rec.synchReviewId = obj.synchReviewId;
            rec.synchReviewRecordId = $(tr).find("input[name='recordId']").val();
            rec.reviewValues = [];
            $(tr).find("td").each(function(j, td) {
                var val = {};
                val.synchReviewRecordId = rec.synchReviewRecordId;
                val.synchReviewRecordValueId = $(td).find("input[name='recordValueId']").val();
                val.value = $(td).find("input[name='recordValue']").val();
                rec.reviewValues.push(val);
            });
            obj.reviewRecords.push(rec);
        });

        return obj;

    }
};

OPENIAM.SynchReview.Table = {
    init : function() {
        var offsetPage = OPENIAM.ENV.page;
        $("#tableRecords").tablesorter({
            debug: false,
            sortDisabled : true
            /*sortList: [[0, 0]], */
            /*widgets: ['zebra']*/ }).tablesorterPager({
                container: $("#pagerOne"),
                positionFixed: false,
                size : OPENIAM.SynchReview.getSize(),
                page : offsetPage,
                totalRows : OPENIAM.ENV.count,
                totalPages : OPENIAM.ENV.totalPages,
                onFirstClick : function() {
                    $("#page").val(0);
                    OPENIAM.SynchReview.submit();
                },
                onLastClick : function() {
                    $("#page").val((OPENIAM.ENV.totalPages - 1));
                    OPENIAM.SynchReview.submit();
                },
                onPrevClick : function() {
                    if(offsetPage > 0) {
                        $("#page").val((offsetPage - 1));
                    }
                    OPENIAM.SynchReview.submit();
                },
                onNextClick : function() {
                    if(offsetPage < OPENIAM.ENV.totalPages) {
                        $("#page").val((offsetPage + 1));
                    }
                    OPENIAM.SynchReview.submit();
                }
            });

        $("#tableOne tbody tr").click(function() {
//            window.location.href = "synchReview.html?id=" + $(this).attr("entityId");
        });
    }
};

$(document).ready(function() {
    OPENIAM.SynchReview.init();
    OPENIAM.SynchReview.Table.init();
});

$(window).load(function() {
});