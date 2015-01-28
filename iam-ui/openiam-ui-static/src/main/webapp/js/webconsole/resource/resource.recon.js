OPENIAM = window.OPENIAM || {};
OPENIAM.RECON = window.OPENIAM.RECON || {};

OPENIAM.RECON.Form = {
    deleteReconConfig : function() {
        $.ajax({
            url : "delete-recon-config.html",
            data : $('reconConfig.reconConfigId').val(),
            type : "POST",
            dataType : "json",
            contentType : "application/json",
            success : function(data, textStatus, jqXHR) {
                if (data.status == 200) {
                    OPENIAM.Modal.Success({
                        message : data.successMessage,
                        showInterval : 1000,
                        onIntervalClose : function() {
                            window.location.reload(true);
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
    showUploader : function() {

    }
};

$(document).ready(function() {
    if (OPENIAM.ENV.errorToken) {
        OPENIAM.Modal.Error({
            message : OPENIAM.ENV.errorToken
        });
    } else if (OPENIAM.ENV.successToken) {
        OPENIAM.Modal.Success({
            message : OPENIAM.ENV.successToken,
            showInterval : 1000
        });
    }
    $('#deleteBtn').on('click', function() {
        OPENIAM.RECON.Form.deleteReconConfig();
    });
    $('#reconSave').on('click', function() {
        $('#uploadForm').attr('action', 'save-reconciliation-config.html');
        $('#uploadForm').submit();
    });
    $('#reconNow').on('click', function() {
        $('#uploadForm').attr('action', 'start-reconciliation.html');
        $('#uploadForm').submit();
    });
    $('#reconStop').on('click', function() {
        $('#uploadForm').attr('action', 'stop-reconciliation.html');
        $('#uploadForm').submit();
    });
    $('#reconReport').on('click', function() {
        $('#uploadForm').attr('action', 'get-reconciliation-report.html');
        $('#uploadForm').submit();
    });

    if ($('#csvFileUploadName').size() > 0) {
        $('#csvFileUploadName').find('a').on('click', function() {
            $('#csvFileUploadName').hide();
            $('#csvFileUploader').show();
        });

        $('#csvFileUploader').hide();
    }

    var $dateCtrl = $("input.date");
    $dateCtrl.datepicker({ dateFormat: OPENIAM.ENV.DateFormatDP,showOn: "button", changeMonth:true, changeYear:true}).attr('readonly','readonly');
});

$(window).load(function() {
});