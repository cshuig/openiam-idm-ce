OPENIAM = window.OPENIAM || {};
OPENIAM.PageTemplate = {
    lastId : 0,
    lastTemplateType:"",
    init : function(){
        lastTemplateType = $("#templateType").val();

        $("#templateType").change(function(){
            var typeId = $(this).val();
            if(lastTemplateType!=""){
                OPENIAM.Modal.Warn({
                    message : localeManager["openiam.ui.page.template.type.change.warn"],
                    buttons : true,
                    OK : {
                        text : localeManager["openiam.ui.page.template.type.change.confirm"],
                        onClick : function() {
                            OPENIAM.Modal.Close();
                            OPENIAM.PageTemplate.Entitlements.TemplateField.reloadTemplateFieldSelect();
                            OPENIAM.PageTemplate.Entitlements.TemplateField.removeAll();

                            lastTemplateType = typeId;
                        }
                    },
                    Cancel : {
                        text : localeManager["openiam.ui.button.cancel"],
                        onClick : function() {
                            OPENIAM.Modal.Close();
                        }
                    }
                });
            } else{
                OPENIAM.PageTemplate.Entitlements.TemplateField.reloadTemplateFieldSelect();
            }
        });
    },

    deletePageTemplate : function() {
        $.ajax({
            url : "deletePageTemplate.html",
            data : {id : OPENIAM.ENV.TemplateId},
            type: "POST",
            dataType : "json",
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
    },
    savePageTemplate : function() {
        $.ajax({
            url : "savePageTemplate.html",
            data : JSON.stringify(this.toJSON()),
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
    },
    toJSON : function() {
        var obj = {};
        obj.id = OPENIAM.ENV.TemplateId;
        obj.resourceId = $("#resourceId").val();
        obj.name = $("#templateName").val();
        obj.templateTypeId = $("#templateType").val();
        obj.isPublic = $("#isPublicOn").is(":checked");
        // fill template fields
        obj.templateFieldList = null;
        var tFieldList = $("#templateFieldContainer table tbody").find("tr:not(tr.empty)");
        if(tFieldList!=null && tFieldList.length>0){
            obj.templateFieldList=[];
            var displayOrder = 1;
            tFieldList.each(function(){
                var bean = $(this).data("entity");

                var fieldXref={};
                /*
                var id = {};
                id.fieldId=bean.id;
                id.templateId=OPENIAM.ENV.TemplateId;

                fieldXref.id=id;
                */
                fieldXref.id = bean.xrefId;
                fieldXref.field = { id : bean.fieldId};
                fieldXref.template = { id : OPENIAM.ENV.TemplateId};
                fieldXref.required=bean.isRequired;
                fieldXref.editable=bean.editable;
                fieldXref.displayOrder=displayOrder;
                fieldXref.languageMap = bean.languageMap;

                obj.templateFieldList.push(fieldXref);
                displayOrder++;
            });
        }
        // fill entitlements
        // fields
        obj.customFieldList = null;
        var fieldList = $("#customFieldContainer table tbody").find("tr:not(tr.empty)");
        if(fieldList!=null && fieldList.length>0){
            obj.customFieldList=[];
            var displayOrder = 1;
            fieldList.each(function(){
                var bean = $(this).data("entity");

                var fieldXref={};
                var id = {};
                    id.metadataElementId=bean.id;
                    id.metadataElementPageTemplateId=OPENIAM.ENV.TemplateId;

                fieldXref.id=id;
                fieldXref.displayOrder=displayOrder;

                obj.customFieldList.push(fieldXref);
                displayOrder++;
            });
        }

        // patterns
        obj.patternList = null;

        var patternList = $("#uriPatternContainer table tbody").find("tr:not(tr.empty)");
        if(patternList!=null && patternList.length>0){
            obj.patternList=[];

            patternList.each(function(){
                var bean = $(this).data("entity");

                var pattern={};
                    pattern.id=bean.id;
                obj.patternList.push(pattern);
            });
        }
        return obj;
    }
};

$(document).ready(function() {
    OPENIAM.PageTemplate.init();

    $("#deleteTemplate").click(function() {
        OPENIAM.Modal.Warn({
            message : localeManager["openiam.ui.page.template.delete.message"],
            buttons : true,
            OK : {
                text : localeManager["openiam.ui.page.template.delete.confirm"],
                onClick : function() {
                    OPENIAM.Modal.Close();
                    OPENIAM.PageTemplate.deletePageTemplate();
                }
            },
            Cancel : {
                text : localeManager["openiam.ui.button.cancel"],
                onClick : function() {
                    OPENIAM.Modal.Close();
                }
            }
        });
        return false;
    });

    $("#pageTemplateForm").submit(function() {
        OPENIAM.PageTemplate.savePageTemplate();
        return false;
    });

});

$(window).load(function() {
});