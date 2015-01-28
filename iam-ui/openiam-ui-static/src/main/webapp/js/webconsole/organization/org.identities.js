OPENIAM = window.OPENIAM || {};
OPENIAM.Org = window.OPENIAM.Org || {};

OPENIAM.Org.Identity = {
    init : function() {
        var mySearch = document.createElement("input");
            $(mySearch).attr("type", "button");
            $(mySearch).attr("value", localeManager["openiam.ui.user.identities.button.new"]);
              mySearch.className = "redBtn";
              mySearch.id = "createNew";

        var inputelements = [];
        inputelements.push("");
        inputelements.push("");
        inputelements.push("");
        inputelements.push("");
       // inputelements.push("");
    //    inputelements.push(mySearch);
        var $this = this;
        $("#entitlementsContainer").entitlemetnsTable({
            columnHeaders : [
                localeManager["openiam.ui.common.identity"],
                localeManager["openiam.ui.shared.managed.system"],
                localeManager["openiam.ui.common.date"],
                localeManager["openiam.ui.common.status"]
            //    localeManager["openiam.ui.common.actions"]
            ],
            columnsMap : ["identity", "managedSys", "createDateFormated", "status"],
            hasEditButton : false,
            useTrueFalseStringsOnBoolean : true,
            deleteOptions : {
                warningMessage : localeManager["openiam.ui.user.identities.delete.message"],
                onDelete : function(bean) {
                    OPENIAM.Org.Identity.onDelete(bean);
                },
                isDeletable : function(bean) {
                    return (bean.managedSysId != '0' && bean.status == 'INACTIVE');
                }
            },
            theadInputElements : inputelements,
            onEdit : function(bean) {
                OPENIAM.Org.Identity.onEdit(bean);
            },
            ajaxURL : "getOrgIdentities.html",
            requestParamIdName : "id",
            requestParamIdValue : OPENIAM.ENV.OrgId,
            orgId : OPENIAM.ENV.OrgId,
            pageSize : 10,
            emptyResultsText : localeManager["openiam.ui.org.identities.empty.result"],
            onAppendDone : function() {
                var editFields = [
                    {fieldName: "identity", type:"text",label:localeManager["openiam.ui.common.principal"], required:true},
                    {fieldName: "managedSysId", type:"select", label:localeManager["openiam.ui.shared.managed.system"], required:true, items:OPENIAM.ENV.ManagedSystems},
                    {fieldName: "orgId", type:"hidden", label:""},
                    {fieldName: "id", type:"hidden", label:""},
                    {fieldName: "status", type:"select",label:localeManager["openiam.ui.common.status"], items: [{id:"ACTIVE", name : localeManager["openiam.ui.common.active"]}, {id:"INACTIVE", name:localeManager["openiam.ui.common.inactive"]}]},
                ];

                $("#editDialog").modalEdit({
                    fields: editFields,
                    dialogTitle: localeManager["openiam.ui.org.identities.edit.dialog.title"],
                    onSubmit: function(bean){
                        $this.saveLogin(bean);
                    },
                    onShown: function(){
                        var dateCtrl = $("input#pwdExpAsStr, input#gracePeriodAsStr");
                        // try to fix icon position. Temporary solution. Still under investigation
                        dateCtrl.each(function(){
                            var element = $(this);
                            var icon = element.next();
                            var el_pos = element.position(),
                                el_h = element.outerHeight(false),
                                el_mt = parseInt(element.css('marginTop'), 10) || 0,
                                el_w = element.outerWidth(false),
                                el_ml = parseInt(element.css('marginLeft'), 10) || 0,

                                i_w = icon.outerWidth(true),
                                i_h = icon.outerHeight(true);


                            var new_icon_top = el_pos.top + el_mt + ((el_h - i_h) / 2);
                            var new_icon_left = el_pos.left + el_ml + el_w - i_w;

                            var icon_pos = icon.position();

                            if(icon_pos.top!=new_icon_top){
                                icon.css('top', new_icon_top);
                            }
                            if(icon_pos.left!=new_icon_left){
                                icon.css('left', new_icon_left);
                            }
                        });
                    }
                });

                $("input#pwdExpAsStr").datepicker({
                    dateFormat: OPENIAM.ENV.DateFormatDP,
                    showOn: "button",
                    minDate: "+0d",
                    changeMonth:true,
                    changeYear:true,
                    onSelect: function(selectedDate){
                        var instance = $( this ).data( "datepicker"),
                            date = $.datepicker.parseDate(
                                instance.settings.dateFormat, selectedDate, instance.settings),
                            datePlus = new Date(date.setDate(date.getDate() + 1));
                        $("input#gracePeriodAsStr").datepicker("option", "minDate", datePlus);
                    }
                }).attr('readonly','readonly');

                $("input#gracePeriodAsStr").datepicker({
                    dateFormat: OPENIAM.ENV.DateFormatDP,
                    showOn: "button",
                    minDate: "+1d",
                    changeMonth:true,
                    changeYear:true,
                    onSelect: function(selectedDate){
                        var instance = $( this ).data( "datepicker"),
                            date = $.datepicker.parseDate(
                                instance.settings.dateFormat, selectedDate, instance.settings);
                        $("input#pwdExpAsStr").datepicker("option", "maxDate", date);
                    }
                }).attr('readonly','readonly');

                $("input#lastUpdateAsStr").datepicker({ dateFormat: OPENIAM.ENV.DateFormatDP,showOn: "button",changeMonth:true, changeYear:true }).attr('readonly','readonly')



                this.find("#createNew").click(function() {
                    OPENIAM.Org.Identity.onEdit({});
                });
            }
        });
    },
    onDelete : function(bean) {
        $.ajax({
            url : "deleteOrgIdentity.html",
            data : {id : bean.id},
            type: "POST",
            dataType : "json",
            success : function(data, textStatus, jqXHR) {
                if(data.status == 200) {
                    OPENIAM.Modal.Success({message : data.successMessage, showInterval : 2000, onIntervalClose : function() {
                        window.location.reload(true);
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
    onEdit : function(bean) {
//    	var editFields = [
//    						{fieldName: "login", type:"text",label:"Principal", required:true},
//    						{fieldName: "managedSysId", type:"select", label:"Managed System", required:true, items:OPENIAM.ENV.ManagedSystems},
//    						{fieldName: "securityDomainid", type:"hidden", label:""},
//                            {fieldName: "userId", type:"hidden", label:""},
//    						{fieldName: "id", type:"hidden", label:""},
//                            {fieldName: "pwdExpAsStr", type:"text",label:"Password Expired", additionaClazz:"date"},
//                            {fieldName: "gracePeriodAsStr", type:"text",label:"Grace Period", additionaClazz:"date"},
//                            {fieldName: "lastUpdateAsStr", type:"text",label:"Last Updated", readonly:true, additionaClazz:"date"},
//                            {fieldName: "status", type:"select",label:"Status", items: [{id:"ACTIVE", name : "Active"}, {id:"INACTIVE", name:"Inactive"}]},
//                            {fieldName: "locked", type:"checkbox",label:"Is Locked?"}
//                         ];
//
//    	var $this = this;
//    	$("#editDialog").modalEdit({
//			fields: editFields,
//			dialogTitle: OPENIAM.ENV.Text.EditIdentity,
//			onSubmit: function(bean){
//				$this.saveLogin(bean);
//			}
//		});
        var dateCtrl = $("input.date");
        dateCtrl.datepicker({dateFormat: OPENIAM.ENV.DateFormatDP,showOn: "button" }).attr('readonly','readonly')

        $("#editDialog").modalEdit("show", bean);
    },
    saveLogin : function(bean) {
        bean.orgId = OPENIAM.ENV.OrgId;
        $.ajax({
            url : "editOrgIdentity.html",
            data : JSON.stringify(bean),
            type: "POST",
            dataType : "json",
            contentType: "application/json",
            success : function(data, textStatus, jqXHR) {
                if(data.status == 200) {
                    OPENIAM.Modal.Success({message : data.successMessage, showInterval : 2000, onIntervalClose : function() {
                        if(data.redirectURL != null && data.redirectURL != undefined && data.redirectURL.length > 0) {
                            window.location.href = data.redirectURL;
                        } else {
                            window.location.reload(true);
                        }
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
    OPENIAM.Org.Identity.init();
});

$(window).load(function() {

});