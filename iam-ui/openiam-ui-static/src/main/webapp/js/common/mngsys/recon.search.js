OPENIAM = window.OPENIAM || {};
OPENIAM.Reconciliation = {
    request : function(args) {
        var that = args.target;
        var selectInput = document.createElement("select");
        $(selectInput).append("<option value=\"\">" + localeManager["openiam.ui.common.reconciliation.type"] + "</option>");
        selectInput.id = "reconciliationType";
        var types = OPENIAM.ENV.ReconciliationTypes;
        if($.isArray(types)) {
            $.each(types, function(idx, obj) {
                var option = document.createElement("option"); $(option).val(obj.id); $(option).text(obj.name);
                if(args.selectInputValue && obj.id == args.selectInputValue) {
                    $(option).attr("selected", "selected");
                }
                $(selectInput).append(option);
            });
        }

        var mySearch = document.createElement("input");
        $(mySearch).attr("type", "button");
        $(mySearch).attr("value", localeManager["openiam.ui.common.search"]);
        mySearch.className = "redBtn";
        mySearch.id = "search";

        var addNew = document.createElement("input");
        $(addNew).attr("type", "button");
        $(addNew).attr("value", localeManager["openiam.ui.common.add"]);
        addNew.className = "redBtn";
        addNew.id = "add";

        var inputelements = [];
        inputelements.push(selectInput);
        inputelements.push("&nbsp;");
        inputelements.push("&nbsp;");
        inputelements.push(mySearch);
        inputelements.push(addNew);

        $("#entitlementsContainer").entitlemetnsTable({
            columnHeaders : [
                localeManager["openiam.ui.common.reconciliation.type"],
                localeManager["openiam.ui.common.reconciliation.status"],
                localeManager["openiam.ui.common.reconciliation.customscript"],
                localeManager["openiam.ui.common.reconciliation.targetSystemSearchFilter"],
                localeManager["openiam.ui.common.actions"]
            ],
            hasEditButton : true,
            onEdit : function(bean) {
                window.location.href = "reconciliationEdit.html?id=" + bean.id+"&managedSysId="+OPENIAM.ENV.ManagedSysId;
            },
            columnsMap : ["reconType", "execStatusValue", "customProcessorScript","targetSystemSearchFilter"],
            theadInputElements : inputelements,
            ajaxURL : "rest/api/entitlements/searchReconciliations",
            entityUrl : "reconciliationEdit.html",
            entityURLIdentifierParamName : "id",
            pageSize : 10,
            deleteOptions : {
            	onDelete : function(bean) {
	               that.remove(bean.id);
	            }
            },
            hasProvisionButton: false,
            hasDeprovisionButton: false,
            emptyResultsText : localeManager["openiam.ui.common.reconciliation.association.notFound"],
            onAppendDone : function() {
                this.find("#search").click(function() {
                    OPENIAM.Reconciliation.init();
                });
                this.find("#add").click(function() {
                    window.location.href = "reconciliationEdit.html?managedSysId="+OPENIAM.ENV.ManagedSysId;
                });
            },
            validate : function(vdata) {
                var obj = {};
                obj.valid = 'true';
               /* if((vdata.reconciliationType == null || vdata.reconciliationType == undefined || vdata.reconciliationType == '')
                    && (vdata.name == null || vdata.name == undefined || vdata.name == '')) {
                    obj.valid = 'false';
                }*/
                return obj;
            },
            getAdditionalDataRequestObject : function() {
                var obj = {
                    reconciliationType : $("#reconciliationType").find("option:selected").val(),
                    managedSysId : $("#managedSysId").val()
                };
                return obj;
            }
        });
    },
    remove : function(id) {
        OPENIAM.Reconciliation.Common.addOrRemove({
            entityRequestParamName : "id",
            entityId : id,
            url : "removeReconConfig.html",
            target : this
        });
    },

    init : function() {
        OPENIAM.Reconciliation.request(
            {
                selectInputValue : $("#reconciliationType").find("option:selected").val(),
                target : this,
                managedSysId : $("#managedSysId").val()
            }
        );
    },
    Common : {
        addOrRemove : function(args) {
            var data = {};
            data["managedSysId"] = OPENIAM.ENV.ManagedSysId;
            data[args.entityRequestParamName] = args.entityId;
            $.ajax({
                url : args.url,
                "data" : data,
                type: "POST",
                dataType : "json",
                success : function(data, textStatus, jqXHR) {
                    if(data.status == 200) {
                        OPENIAM.Modal.Success({message : data.successMessage, showInterval : 1000, onIntervalClose : function() {
                            OPENIAM.Reconciliation.init();
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
    }
};

$(document).ready(function() {
    OPENIAM.Reconciliation.init();
});

$(window).load(function() {

});