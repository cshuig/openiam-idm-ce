console = window.console || {};
console.log = window.console.log || function() {};

OPENIAM = window.OPENIAM || {};
OPENIAM.Report = window.OPENIAM.Report || {};
OPENIAM.Report.Edit = {

	init : function() {

        var that = this;

        this.design.init();
        this.dataSource.init();

        $("#deleteReport").click(function() {
            that.onDelete();
			return false;
		});

        $("#saveReport").click(function() {
            that.onSave();
            return false;
        });

        $("#reportDataSourceFile").change(function() {
            that.validateExtension(this, ".groovy");
        });

        $("#reportDesignFile").change(function() {
            that.validateExtension(this, ".rptdesign");
        });
	},

    design : {

        init : function() {
            var that = this;
            $("#newDesignFile").change(function() {
                that.changeView();
            });
            that.changeView();
        },

        changeView : function() {
            if ($("#newDesignFile").is(':checked')) {
                $('#reportDesignName').closest('tr').hide();
                $('#reportDesignFile').closest('tr').show();
            } else {
                $('#reportDesignFile').closest('tr').hide();
                $('#reportDesignName').closest('tr').show();
            }
        }

    },

    dataSource : {
        init : function() {
            var that = this;
            $("#newDataSourceFile").change(function() {
                that.changeView();
            });
            that.changeView();
        },

        changeView : function() {
            if ($("#newDataSourceFile").is(':checked')) {
                $('#reportDataSourceName').closest('tr').hide();
                $('#reportDataSourceFile').closest('tr').show();
            } else {
                $('#reportDataSourceFile').closest('tr').hide();
                $('#reportDataSourceName').closest('tr').show();
            }
        }
    },

    validateExtension : function(target, extension) {
        if(! $(target).val().match(extension+"$")) {
            OPENIAM.Modal.Warn({
                message : localeManager["openiam.ui.report.supported.type.message"].replace('{0}', extension)
            });
            $(target).val('');
        }
    },

    onSave : function() {
        var that = this;
        $.ajax({
            url : "validateReport.html",
            data : JSON.stringify(this.toJSON()),
            type: "POST",
            dataType : "json",
            contentType: "application/json",
            success : function(data, textStatus, jqXHR) {
                if(data.status == 200) {
                    $("#ReportForm").submit();
                } else {
                    if (data.errorList[0].code == OPENIAM.ENV.REPORT_URL_TAKEN ||
                                data.errorList[0].code == OPENIAM.ENV.REPORT_DATASOURCE_TAKEN) {
                        that.confirmOverwrite(data.errorList[0]);
                    } else {
                        OPENIAM.Modal.Error({errorList : data.errorList});
                    }
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });

    },

    toJSON : function() {

        var newDataSourceFile = $("#newDataSourceFile").is(':checked');
        var newDesignFile = $("#newDesignFile").is(':checked');
        return {
            reportId: $("#reportId").val(),
            reportName: $("#reportName").val(),
            resourceId: $("#resourceId").val(),
            reportDataSourceName: newDataSourceFile
                ? $("#reportDataSourceFile").val()
                : $("#reportDataSourceName").val(),
            newDataSourceFile: newDataSourceFile,
            overwriteDataSourceFile: $("#overwriteDataSourceFile").val(),
            reportDesignName: newDesignFile
                ? $("#reportDesignFile").val()
                : $("#reportDesignName").val(),
            newDesignFile: newDesignFile,
            overwriteDesignFile: $("#overwriteDesignFile").val()

        };
    },

    confirmOverwrite : function(error) {
        var that = this;

        OPENIAM.Modal.Warn({
            message : error.message + "<br/>" + localeManager["openiam.ui.report.overwrite.warning.message"],
            buttons : true,
            OK : {
                text : localeManager["openiam.ui.report.overwrite.confirmation"],
                onClick : function() {
                    OPENIAM.Modal.Close();
                    if (error.code == OPENIAM.ENV.REPORT_DATASOURCE_TAKEN) {
                        $("#overwriteDataSourceFile").val("true");
                    } else if (error.code == OPENIAM.ENV.REPORT_URL_TAKEN) {
                        $("#overwriteDesignFile").val("true");
                    } else {
                        OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
                    }
                    that.onSave();
                }
            },
            Cancel : {
                text : localeManager["openiam.ui.common.cancel"],
                onClick : function() {
                    OPENIAM.Modal.Close();
                }
            }
        });
    },

    onDelete : function() {

        var that = this;

        OPENIAM.Modal.Warn({
            message : localeManager["openiam.ui.report.delete.warning.message"],
            buttons : true,
            OK : {
                text : localeManager["openiam.ui.report.delete.confirmation"],
                onClick : function() {
                    OPENIAM.Modal.Close();
                    that.deleteObject();
                }
            },
            Cancel : {
                text : localeManager["openiam.ui.common.cancel"],
                onClick : function() {
                    OPENIAM.Modal.Close();
                }
            }
        });
    },

    deleteObject : function() {
		$.ajax({
			url : "deleteReport.html",
			data : {id : OPENIAM.ENV.Id},
			type: "POST",
			dataType : "json",
			success : function(data, textStatus, jqXHR) {
				if(data.status == 200) {
					OPENIAM.Modal.Success({message : data.successMessage, showInterval : 2000, onIntervalClose : function() {
						window.location.href = data.redirectURL;
					}});
				} else {
					OPENIAM.Modal.Error({errorList : data.errorList});
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
			}
		});
	}
};

$(document).ready(function() {
	OPENIAM.Report.Edit.init();
});


$(window).load(function() {
	if(OPENIAM.ENV.ErrorMessage != null) {
		OPENIAM.Modal.Error(OPENIAM.ENV.ErrorMessage);
	}
	
	if(OPENIAM.ENV.SuccessMessage != null) {
		OPENIAM.Modal.Success({message : OPENIAM.ENV.SuccessMessage, showInterval : 1000});
	}
});