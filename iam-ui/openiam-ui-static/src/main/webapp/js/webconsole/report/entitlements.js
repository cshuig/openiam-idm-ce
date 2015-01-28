OPENIAM = window.OPENIAM || {};
OPENIAM.Report = window.OPENIAM.Report || {};
OPENIAM.Report.Entitlements = {
    Load : {
        onReady : function() {
			
        },
        onLoad : function() {
        	if(OPENIAM.ENV.Id != null) {
        		OPENIAM.Report.Entitlements.Params.load();
        	}
        }
    },
    Common : {
        load : function(args) {
            var that = args.target;
            var inputelements = ["","","","",""];
            var addBtn = document.createElement("input"); $(addBtn).attr("type", "submit"); $(addBtn).attr("value", args.buttonTitle); addBtn.className = "redBtn"; addBtn.id = "addBtn";
            switch(that.getEntityType()) {
                case "parameters":
                    inputelements.push("");
                    break;
                default:
                    break;
            }
            inputelements.push(addBtn);
			
            $(that.getRootElement()).entitlemetnsTable({
                columnHeaders : args.columns,
                columnsMap : args.columnsMap,
                ajaxURL : args.ajaxURL,
                entityUrl : "",
                entityType : args.entityType,
                entityURLIdentifierParamName : "id",
                requestParamIdName : "reportId",
                requestParamIdValue : OPENIAM.ENV.Id,
                pageSize : 100,
                deleteOptions : {
                	warningMessage : localeManager["openiam.ui.report.parameter.delete.warning.message"],
                	onDelete : function(bean) {
	                    that.remove(bean);
	                }
                },
                hasEditButton : args.hasEditButton,
                onEdit : function(bean) {
                    that.edit(bean);
                },
                emptyResultsText : localeManager["openiam.ui.report.parameter.search.empty"],
                theadInputElements : inputelements,
                onAppendDone : function() {
                    if(that.getEntityType()=="parameters"){
                        $("#editDialog").modalEdit({
                            fields: args.editFields,
                            dialogTitle: args.dialogTitle,
                            onSubmit: function(bean){
                                that.save(bean);
                            },
                            afterFormAppended : args.afterFormAppended
                        });
                    }

                    var submit = this.find("#addBtn");
                    submit.click(function() {
                        that.create();
                    });
                }
            });
        },
        saveOrRemove : function(args) {
            var data = args.entity;
            var that = args.target;
            data["reportId"] = OPENIAM.ENV.Id;
            $.ajax({
                url : args.url,
                data : JSON.stringify(data),
                type: "POST",
                dataType : "json",
                contentType: "application/json",
                success : function(data, textStatus, jqXHR) {
                    if(data.status == 200) {
                        if(that.getEntityType()=="parameters"){
                            $("#editDialog").modalEdit("hide");
                        }
                        that.load(0);
                    } else {
                        OPENIAM.Modal.Error({errorList : data.errorList});
                    }
                },
                error : function(jqXHR, textStatus, errorThrown) {
                    OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
                }
            });
        }
    },
    Params : {
    	getEntityType : function() {
    		return "parameters";
    	},
    	getRootElement : function() {
    		return "#parameters";
    	},
        load : function() {
            OPENIAM.Report.Entitlements.Common.load({
                columns : [
                    localeManager["openiam.ui.report.parameter.caption"],
                    localeManager["openiam.ui.report.parameter.name"],
                    localeManager["openiam.ui.report.parameter.type"],
                    localeManager["openiam.ui.report.parameter.meta.type"],
                    localeManager["openiam.ui.report.parameter.support.multiple.values"],
                    localeManager["openiam.ui.report.parameter.required"],
                	localeManager["openiam.ui.common.actions"]
                ],
                columnsMap:["paramCaption", "paramName", "paramTypeName", "paramMetaTypeName", "isMultiple", "isRequired"],
                ajaxURL : "getParamsForReport.html",
                buttonTitle : localeManager["openiam.ui.report.parameters.create.new"],
                hasEditButton : true,
                target : this,
                editFields:[{fieldName: "id", type:"hidden",label:""},
                			{fieldName: "reportId", type:"hidden",label:""},
                            {fieldName: "paramCaption", type:"text",label:localeManager["openiam.ui.report.parameter.caption"], required:true},
                            {fieldName: "paramName", type:"text", label:localeManager["openiam.ui.report.parameter.name"], required:true},
							{fieldName: "paramTypeId", type:"select",items:OPENIAM.ENV.ParamTypeNameList, label:localeManager["openiam.ui.report.parameter.type"], required:true},
                            {fieldName: "paramMetaTypeId", type:"select",items:OPENIAM.ENV.ParamMetaTypeNameList, label:localeManager["openiam.ui.report.parameter.meta.type"]},
                            {fieldName: "isMultiple", type:"checkbox",label:localeManager["openiam.ui.report.parameter.support.multiple.values"]},
                            {fieldName: "isRequired", type:"checkbox",label:localeManager["openiam.ui.report.parameter.required"]},
                            {fieldName: "requestParameters", type:"textarea",label:localeManager["openiam.ui.report.parameter.request.param"]},
                            {fieldName: "displayOrder", type:"hidden",label:""}],
                dialogTitle:localeManager["openiam.ui.report.edit.parameter"],

                afterFormAppended : function() {
                    var paramMetaTypeField = this.dialog.find("#paramMetaTypeId");
                    var paramTypeField = this.dialog.find("#paramTypeId");
                    paramMetaTypeField.change(function() {
                        if (paramMetaTypeField.val() != "") {
                            paramTypeField.val(1);
                            paramTypeField.prop('disabled', 'disabled');
                        } else {
                            paramTypeField.prop('disabled', false);
                        }
                    });
                }
            });
        },
        create: function(){
            var bean = {};
            $("#editDialog").modalEdit("show", bean);
        },
        save : function(bean) {
            OPENIAM.Report.Entitlements.Common.saveOrRemove({
                entity : bean,
                url : "saveReportParam.html",
                target : this
            });
        },
        edit : function(bean){
            $("#editDialog").modalEdit("show", bean);
        },
        remove : function(bean) {
            OPENIAM.Report.Entitlements.Common.saveOrRemove({
                entity : bean,
                url : "deleteReportParam.html",
                target : this
            });
        }
    }
};

$(document).ready(function() {
    OPENIAM.Report.Entitlements.Load.onReady();
    OPENIAM.Report.Entitlements.Load.onLoad();
});

$(window).load(function() {
});

function inspect(that) {
    var res = '';
    for(var key in that) {
        if (!$.isFunction(that[key]))  {
            res += key + "=>" + that[key] + "\n";
        }
    }
    //alert(res);
}