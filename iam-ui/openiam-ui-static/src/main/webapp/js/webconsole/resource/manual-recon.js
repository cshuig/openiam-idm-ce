OPENIAM = window.OPENIAM || {};
OPENIAM.ManualRecon = window.OPENIAM.ManualRecon || {};

OPENIAM.ManualRecon.Form = {
    redirectUrl : null,
    size : 10,
    saveAttributeMap : function() {
        $.ajax({
            url : "save-manual-recon.html",
            data : JSON.stringify(this.toJSON()),
            type : "POST",
            dataType : "json",
            contentType : "application/json",
            success : function(data, textStatus, jqXHR) {
                if (data.status == 200) {
                    isChanged = false;
                    OPENIAM.Modal.Success({
                        message : data.successMessage,
                        showInterval : 2000,
                        onIntervalClose : function() {
                            if (!OPENIAM.ManualRecon.Form.redirectUrl) {
                                window.location.reload(true);
                            }
                        }
                    });
                } else {
                    OPENIAM.Modal.Error({
                        errorList : data.errorList
                    });
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
    },
    toJSON : function() {
        var request = {};
        request.reconResultBean = {};
        var rowsArray = [];
        $(".tr-disable").each(function(index, value) {
            rowsArray.push(OPENIAM.ManualRecon.Form.rowToJSON(this));
        });
        request.resourceId = OPENIAM.ENV.ResourceId;
        request.reconResultBean.objectType = "USER";
        request.reconResultBean.rows = rowsArray;

        return request;
    },
    rowToJSON : function(tr) {
        var row = {};
        var fieldsArray = [];
        $(tr).find('.field').each(function(index, value) {
            var newVals = [];
            var field = {};
            if ($(value).find('.field-select').size() > 0) {
                newVals.push($(value).find('.field-select option:selected').text());
            } else {
                newVals.push($(value).text());
            }
            field.values = newVals;
            fieldsArray.push(field);
        });
        row.rowId = parseInt($(tr).attr('rowId'));
        row.fields = fieldsArray;
        row.caseReconciliation = $(tr).find(".case").attr('value');
        if ($(tr).find(".action").find(".select-action").size() > 0) {
            row.action = $(tr).find(".action").find(".select-action option:selected").val();
        } else {
            row.action = null;
        }
        return row;
    },
    search : function(options) {
        var o = options || {};
        var resourceId = o.resourceId || OPENIAM.ENV.ResourceId;
        var size = o.size || $('#pageSize option:selected').val();
        var page = o.page || $('#pageNumber').val();
        var searchCase = $('#caseSearch option:selected').val();
        var fieldOrder = $('#fieldOrder option:selected').val();
        var directionOrder = $('#directionOrder option:selected').val();
        var fieldSearch = $('#fieldSearch option:selected').val();
        var querySearch = $('#querySearch').val();
        document.location.href = 'manual-reconciliation.html?id=' + resourceId + '&size=' + size + '&page=' + page + '&searchFieldName=' + fieldSearch
                + '&searchFieldValue=' + querySearch + '&searchCase=' + searchCase + '&orderField=' + fieldOrder + '&orderBy=' + directionOrder;

    }
};
$(window).load(function() {
    $('.ready').on('click', function() {
        if ($(this).prop('checked')) {
            $(this).closest('tr').find('select').prop('disabled', 'disabled');
            $(this).closest('tr').addClass("tr-disable");
        } else {
            $(this).closest('tr').find('select').prop('disabled', false);
            $(this).closest('tr').removeClass("tr-disable");
        }
    });
    $('#saveReconRes').on('click', function() {
        OPENIAM.ManualRecon.Form.saveAttributeMap();
    });
    $('#searchButton').on('click', function() {
        OPENIAM.ManualRecon.Form.search();
    });
    $('#pageSize').on('change', function() {
        OPENIAM.ManualRecon.Form.search();
    });
    $('#pageNext').on('click', function() {
        var options = {};
        var page = parseInt($('#pageNumber').val());
        var pages = parseInt($('#pagesNumber').val());
        if (page < pages) {
            options.page = page + 1;
            OPENIAM.ManualRecon.Form.search(options);
        }
    });
    $('#pagePrev').on('click', function() {
        var options = {};
        var page = parseInt($('#pageNumber').val());
        if (page > 1) {
            options.page = page - 1;
            OPENIAM.ManualRecon.Form.search(options);
        }
    });

    $('#pageLast').on('click', function() {
        var options = {};
        var page = parseInt($('#pageNumber').val());
        var pages = parseInt($('#pagesNumber').val());
        if (page != pages) {
            options.page = pages;
            OPENIAM.ManualRecon.Form.search(options);
        }
    });
    $('#pageFirst').on('click', function() {
        var options = {};
        var page = parseInt($('#pageNumber').val());
        if (page != 1) {
            options.page = 1;
            OPENIAM.ManualRecon.Form.search(options);
        }
    });

});
