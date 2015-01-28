OPENIAM = window.OPENIAM || {};
OPENIAM.SubscribeReport = window.OPENIAM.SubscribeReport || {};
OPENIAM.SubscribeReport.Entitlements = {
    Load : {
        onReady : function() {
			
        },
        onLoad : function() {
        	if(OPENIAM.ENV.Id != null) {
        		OPENIAM.SubscribeReport.Entitlements.Params.load();
        	}
        }
    },
    Common : {
        load : function(args) {
            var that = args.target;
            var inputelements = [""];
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
                            }
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
            OPENIAM.SubscribeReport.Entitlements.Common.load({
                columns : [
                	localeManager["openiam.ui.report.parameter.name"],
                    localeManager["openiam.ui.report.parameter.value"],
                	localeManager["openiam.ui.common.actions"]
                ],
                columnsMap:["name","value"],
                ajaxURL : "getSubCriteriaParamsReport.html",
                buttonTitle : localeManager["openiam.ui.report.parameters.create.new"],
                hasEditButton : true,
                target : this,
                editFields:[{fieldName: "rscpId", type:"hidden",label:""},
                            {fieldName: "reportId", type:"hidden",label:""},
                            {fieldName: "reportInfoId", type:"hidden",label:""},
                            {fieldName: "id", type:"select",items:OPENIAM.ENV.GetParamNameInfo,label: localeManager["openiam.ui.report.parameter.name"], required:true},
							{fieldName: "value", type:"text",label: localeManager["openiam.ui.report.parameter.value"], required:true}],
                dialogTitle:localeManager["openiam.ui.report.subscription.parameter.edit"]
            });
        },
        create: function(){
            var bean = {};
            $("#id").attr("disabled", null);
            $("#editDialog").modalEdit("show", bean);
        },
        save : function(bean) {
            OPENIAM.SubscribeReport.Entitlements.Common.saveOrRemove({
                entity : bean,
                url : "saveSubCriteriaParamReport.html",
                target : this
            });
        },
        edit : function(bean){
            $("#id").attr("disabled", "disabled");
            $("#editDialog").modalEdit("show", bean);
        },
        remove : function(bean) {
            OPENIAM.SubscribeReport.Entitlements.Common.saveOrRemove({
                entity : bean,
                url : "deleteSubCriteriaParamReport.html",
                target : this
            });
        }
    }
};

$(document).ready(function() {
    OPENIAM.SubscribeReport.Entitlements.Load.onReady();
    OPENIAM.SubscribeReport.Entitlements.Load.onLoad();
});

$(window).load(function() {
});