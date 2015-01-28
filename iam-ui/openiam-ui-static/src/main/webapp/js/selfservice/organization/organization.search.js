OPENIAM = window.OPENIAM || {};
OPENIAM.Organization = {
    initialSearchValue : "",
    size : 10,
    init : function() {
        var that = this;
        this.initialSearchValue = $("#organizationName").val();
        this.size = parseInt($("#size").val());
        $("#size").change(function() {
            OPENIAM.Organization.resetPage();
            OPENIAM.Organization.submit();
        });

        $("#filterClearOne").click(function() {
            $("#organizationName").val("");
        });

        $("#classification").change(function() {
            OPENIAM.Organization.resetPage();
            OPENIAM.Organization.submit();
        });

        $("#searchForm").submit(function() {
            if (that.initialSearchValue != $("#organizationName").val()) {
                OPENIAM.Organization.resetPage();
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

OPENIAM.Organization.Table = {
    init : function() {
        var offsetPage = OPENIAM.ENV.page;
        $("#tableOne").tablesorter({
            debug : false,
            sortDisabled : true,
            /* sortList: [[0, 0]], */
            widgets : [ 'zebra' ]
        }).tablesorterPager({
            container : $("#pagerOne"),
            positionFixed : false,
            size : OPENIAM.Organization.getSize(),
            page : offsetPage,
            totalRows : OPENIAM.ENV.count,
            totalPages : OPENIAM.ENV.totalPages,
            onFirstClick : function() {
                $("#page").val(0);
                OPENIAM.Organization.submit();
            },
            onLastClick : function() {
                $("#page").val((OPENIAM.ENV.totalPages - 1));
                OPENIAM.Organization.submit();
            },
            onPrevClick : function() {
                if (offsetPage > 0) {
                    $("#page").val((offsetPage - 1));
                }
                OPENIAM.Organization.submit();
            },
            onNextClick : function() {
                if (offsetPage < OPENIAM.ENV.totalPages) {
                    $("#page").val((offsetPage + 1));
                }
                OPENIAM.Organization.submit();
            }
        });
    },
    onHideBranch : function(id) {
        $("#" + id + " a img").attr('nodeType', '+');
        $("#" + id + " a img").attr('src', '/openiam-ui-static/images/common/icons/expand.png');
        $("[name='" + 'org' + id + "']").hide();
        $("[name='" + 'org' + id + "']").each(function() {
            OPENIAM.Organization.Table.onHideBranch($(this).attr("id"));
        });
    },
    onExpand : function(id) {
        if ($("[name='" + 'org' + id + "']").size() == 0) {
            $.ajax({
                url : "organization/onExpand.html",
                data : id,
                type : "POST",
                dataType : "json",
                contentType : "application/json",
                success : function(data, textStatus, jqXHR) {
                    if (data.beans) {
                        for (var i = 0; i < data.beans.length; i++) {
                            var desc = data.beans[i].describtion ? data.beans[i].describtion : " ";
                            var exp = data.beans[i].hasChild == true ? "<a id='data.beans[i].id' class='expand' href='#'>+</a>" : "";
                            var colorClass = $("tbody tr").size() % 2 == 1 ? "odd" : "even";
                            $("#" + id).closest("tr").after(
                                    "<tr id=" + data.beans[i].id + " class='childOrg " + colorClass + "' name='org" + id + "'><td>" + exp + "</td><td>"
                                            + data.beans[i].name + "</td><td>" + data.beans[i].type + "</td><td>" + desc + "</td><td>"
                                            + data.beans[i].parentName + "</td></tr>");
                        }
                    } else {
                        ;
                    }
                }
            });
        }
        // $("#" + id + " a img").attr('nodeType', '+');
        // $("#" + id + " a img").attr('src', 'images/common/icons/expand.png');
        if ($("#" + id + " a img").attr('nodeType') == "+") {
            $("#" + id + " a img").attr('nodeType', '-');
            $("#" + id + " a img").attr('src', '/openiam-ui-static/images/common/icons/collapse.png');

            $("[name='" + 'org' + id + "']").show();
        } else if ($("#" + id + " a img").attr('nodeType') == "-") {
            OPENIAM.Organization.Table.onHideBranch(id);
        }
    }
};

$(document).ready(function() {
    OPENIAM.Organization.init();
    OPENIAM.Organization.Table.init();
    $(".expand").on('click', function() {
        OPENIAM.Organization.Table.onExpand($(this).attr('id'));
    })
    $(".childOrg").hide();
});

$(window).load(function() {

});