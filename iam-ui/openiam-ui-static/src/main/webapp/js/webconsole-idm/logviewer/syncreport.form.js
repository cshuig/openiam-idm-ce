OPENIAM = window.OPENIAM || {};
OPENIAM.ENV = window.OPENIAM.ENV || {};

OPENIAM.SynchReportEntity = window.OPENIAM.SynchReportEntity || {};

OPENIAM.SynchReportEntity.Form = {
    dateControl: null,
    init: function() {
        this.dateControl = $("#auditLogDate");
        this.dateControl.datepicker({
            dateFormat: OPENIAM.ENV.DateFormatDP,
            showOn: "button",
            changeMonth:true,
            changeYear:true,
            onSelect: function(p1, p2, date, p4) {
                OPENIAM.SynchReportEntity.Form.getLogItems();
            }
        }).attr('readonly','readonly');
        $("#runReport").click(function() {
            OPENIAM.SynchReportEntity.Form.runReport();
            return false;
        });
    },
    createLogControls: function (list) {
        var $logsControl = $("#auditLogList");
        $logsControl.empty();
        var items = {};
        var keys = [];
        $.each(list, function (idx, val) {
            items[val] = idx;
            keys.push(val);
        });
        keys.sort();
        var lastKey = keys[keys.length-1];
        keys.map(function (key) {
            var date = new Date(key).toLocaleString();
            var name = 'auditLogId';
            var id = items[key];
            var checked = key==lastKey?'checked':'';
            $logsControl.append($('<input type="radio" id="_' + id + '" name="' + name + '" value="' + id + '" '+checked+'/>'));
            $logsControl.append($('<label for="_' + id + '">' + date + '</label>'));
            $logsControl.append($('<br/>'));
        });
    },
    getLogItems : function() {
        $.ajax({
            url : "synchronization-items.html",
            data : JSON.stringify(this.toJSON()),
            type: "POST",
            dataType : "json",
            contentType: "application/json",
            success : function(data, textStatus, jqXHR) {
                 if(data.status == 200) {
                     OPENIAM.SynchReportEntity.Form.createLogControls(data.contextValues);
                } else {
                    var $logsControl = $("#auditLogList");
                    $logsControl.empty();
                    $logsControl.append(localeManager["openiam.ui.common.no.data.found"]);
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
    },
    runReport : function() {
        $.ajax({
            url : "synchronization-report.html",
            data: JSON.stringify(this.toJSON()),
            type: "POST",
            dataType : "json",
            contentType: "application/json",
            success : function(data, textStatus, jqXHR) {
                if(data.status == 200) {
                    OPENIAM.API.resize(OPENIAM.ENV.ReportHeight);
                    window.location.href = data.redirectURL;
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
        obj.synchConfigId = OPENIAM.ENV.SynchConfigId;
        obj.auditLogDateAsStr = this.dateControl.val();
        obj.auditLogId = $('input[name=auditLogId]:checked', '#reportForm').val();
        obj.reportType = $('#reportType').val();
        return obj;
    }
};

$(document).ready(function() {
    OPENIAM.SynchReportEntity.Form.init();
    OPENIAM.SynchReportEntity.Form.getLogItems();
});