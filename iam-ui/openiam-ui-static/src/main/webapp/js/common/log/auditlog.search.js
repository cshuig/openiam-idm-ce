OPENIAM = window.OPENIAM || {};
OPENIAM.AuditLog = window.OPENIAM.AuditLog || {};

OPENIAM.AuditLog.COLUMN_HEADERS = [
    localeManager["openiam.ui.audit.log.requestor"],
    localeManager["openiam.ui.audit.log.target"],
    localeManager["openiam.ui.audit.log.action"],
    localeManager["openiam.ui.audit.log.result"],
    localeManager["openiam.ui.common.date"],
    localeManager["openiam.ui.common.actions"]
];

OPENIAM.AuditLog.COLUMNS_MAP = ["principal", "target", "action", "result", "formattedDate"];
OPENIAM.AuditLog.SEARCH_URL = "rest/api/metadata/findLogs";

OPENIAM.AuditLog.Form = {
    init : function() {
        OPENIAM.AuditLog.Form.draw(false);
    },
    search : function() {
        OPENIAM.AuditLog.Form.draw(true);
    },
    toJSON : function() {
        var obj = {};
        try {
            var fromDate = new Date();
            fromDate.setTime($("#fromDate").datetimepicker('getDate').getTime());
            fromDate.setHours(0,0,0,0);
            obj.fromDate = fromDate.getTime();
        } catch(e) {}

        try {
            var toDate = new Date();
            toDate.setTime($("#toDate").datetimepicker('getDate').getTime());
            toDate.setHours(23,59,59,999);
            obj.toDate = toDate.getTime();
        } catch(e) {}

        var action = $('#action').find(":selected");
        if($(action).val() != '') {
            obj.action = $(action).text();
        }
        var result = $('#result').find(":selected");
        if($(result).val() != '') {
            obj.result = $(result).text();
        }

        obj.showChildren = $('#showChild').is(':checked');

        var requestor = $('#requestor');
        if($(requestor).val() != '') {
            obj.requestorLogin = $(requestor).val();
        }
        var managedSystem = $('#managedSystem').find(":selected");
        if($(managedSystem).val() != '') {
            obj.managedSystem = $(managedSystem).val();
        }
        var targetType = $('#targetType').find(":selected");
        if($(targetType).val() != '') {
            obj.targetType = $(targetType).text();
        }
        var targetId = $('#targetId');
        if($(targetId).val() != '') {
            obj.targetId = $(targetId).val();
        }
        var targetLogin = $('#targetLogin');
        if($(targetLogin).val() != '') {
            obj.targetLogin = $(targetLogin).val();
        }
        var secondaryTargetType = $('#secondaryTargetType').find(":selected");
        if($(secondaryTargetType).val() != '') {
            obj.secondaryTargetType = $(secondaryTargetType).text();
        }
        var secondaryTargetId = $('#secondaryTargetId');
        if($(secondaryTargetId).val() != '') {
            obj.secondaryTargetId = $(secondaryTargetId).val();
        }

        return obj;
    },
    draw : function(doRequest) {
        $("#entitlementsContainer").entitlemetnsTable({
            getData : doRequest ? null : function() { return {size : 0, beans : []}; },
            columnHeaders : OPENIAM.AuditLog.COLUMN_HEADERS,
            columnsMap : OPENIAM.AuditLog.COLUMNS_MAP,
            hasEditButton : true,
            onEdit : function(bean, options) {
                var params = "";
                $.each( OPENIAM.AuditLog.Form.toJSON(), function( key, value ) {
                    params += "&"+key+"="+value;
                });

                window.location.href = "viewLogRecord.html?id=" + bean.id+params+"&from="+options.from+"&size="+options.pageSize+"&page="+options.page+"&totalSize="+options.totalSize;
            },
            ajaxURL : OPENIAM.AuditLog.SEARCH_URL,
            entityUrl : "viewLogRecord.html",
            entityURLIdentifierParamName : "id",
            pageSize : 10,
            emptyResultsText : localeManager["openiam.ui.audit.log.search.empty"],
            getAdditionalDataRequestObject : function() {
                return OPENIAM.AuditLog.Form.toJSON();
            }
        });
    }
};


$(document).ready(function() {

    var dateCtrl = $("input.date");
    dateCtrl.datepicker({ dateFormat: OPENIAM.ENV.DateFormatDP,showOn: "button",changeMonth:true, changeYear:true}).attr('readonly','readonly');
    // try to fix icon position. Temporary solution. Still under
    // investigation
    dateCtrl.each(function() {
        var element = $(this);
        var icon = element.next();
        var el_pos = element.position(), el_h = element.outerHeight(false), el_mt = parseInt(element.css('marginTop'), 10) || 0, el_w = element
                .outerWidth(false), el_ml = parseInt(element.css('marginLeft'), 10) || 0,

            i_w = icon.outerWidth(true), i_h = icon.outerHeight(true);

        var new_icon_top = el_pos.top + el_mt + ((el_h - i_h) / 2);
        var new_icon_left = el_pos.left + el_ml + el_w - i_w;

        var icon_pos = icon.position();

        if (icon_pos.top != new_icon_top) {
            icon.css('top', new_icon_top);
        }
        if (icon_pos.left != new_icon_left) {
            icon.css('left', new_icon_left);
        }
        icon.css('float', 'none');
    });

    setTimeout(function() {
        $("#fromDate").datepicker("setDate", new Date());
        $("#toDate").datepicker("setDate", new Date());
    }, 200);

    $("#cleanSearchForm").click(function() {
        $("#userSearchForm").find("input, select").not(":input[type=submit]").val('');
        $("#fromDate").datepicker("setDate", new Date());
        $("#toDate").datepicker("setDate", new Date());
    });

    $("#searchFilter").multiselect({
        header : false,
        click: function(event, ui) {
            var $el = $("#userSearchFormTable #" + ui.value);
            if(ui.checked) {
                $el.show();
            } else {
                $el.find("input, select").val('');
                $el.hide();
            }
        },
        noneSelectedText : localeManager["openiam.ui.user.add.more.search.criteria"]
    });

    $("#searchLogs").click(function() {
        OPENIAM.AuditLog.Form.search();
        return false;
    });

    OPENIAM.AuditLog.Form.init();

});