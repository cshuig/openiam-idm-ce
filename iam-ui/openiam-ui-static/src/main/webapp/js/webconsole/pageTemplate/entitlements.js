OPENIAM = window.OPENIAM || {};
OPENIAM.PageTemplate = window.OPENIAM.PageTemplate || {};
OPENIAM.PageTemplate.Entitlements = {
    Load : {
        onReady : function() {
			
        },
        onLoad : function() {
        	if(OPENIAM.ENV.TemplateId != null) {
        		OPENIAM.PageTemplate.Entitlements.TemplateField.load();
        		OPENIAM.PageTemplate.Entitlements.CustomField.load();
        		OPENIAM.PageTemplate.Entitlements.Patterns.load();
        	}
        }
    },
    Common : {
        load : function(args) {
            var that = args.target;
            var inputelements = [];
            var addBtn = document.createElement("input"); $(addBtn).attr("type", "button"); $(addBtn).attr("value", args.buttonTitle); addBtn.className = "redBtn";
            var myInput = null;
            switch(that.getEntityType()) {
                case "fields":
                    myInput = document.createElement("input"); $(myInput).attr("type", "text");myInput.id = "fieldName"; myInput.className = "full rounded";
                    $(myInput).attr("placeholder", args.placeholder).attr("autocomplete", "off");
                    OPENIAM.FN.applyPlaceholder(myInput);

                    inputelements.push(myInput);
                    var mySelect = document.createElement("select"); mySelect.id = "typeId"; mySelect.className = "full rounded";
                        $(mySelect).attr("autocomplete", "off");
                    var option = document.createElement("option");
                        option.value="";
                        option.innerHTML=localeManager["openiam.ui.page.template.search.custom.field.type.select"]+"...";
                    $(mySelect).append(option);
                    if(OPENIAM.ENV.FieldTypeList!=null && OPENIAM.ENV.FieldTypeList.length>0){
                        for(var opt = 0; opt<OPENIAM.ENV.FieldTypeList.length;opt++){
                            var op = OPENIAM.ENV.FieldTypeList[opt];
                            option = document.createElement("option");
                            option.value=op.id;
                            option.innerHTML=op.name;
                            $(mySelect).append(option);
                        }
                    }
                    inputelements.push(mySelect);

                    addBtn.id = "searchField";
                    break;
                case "patterns":
                    myInput = document.createElement("input"); $(myInput).attr("type", "text");myInput.id = "pattern"; myInput.className = "full rounded";
                    $(myInput).attr("placeholder", args.placeholder).attr("autocomplete", "off");
                    OPENIAM.FN.applyPlaceholder(myInput);

                    inputelements.push(myInput);
                    inputelements.push("");

                    addBtn.id = "searchPattern";
                    break;
                case "templateFields":
                    var mySelect = document.createElement("select"); mySelect.id = "templateField"; mySelect.className = "full rounded";
                    $(mySelect).attr("autocomplete", "off");


                    inputelements.push(mySelect);
                    inputelements.push("");
                    inputelements.push("");

                    addBtn.id = "addTemplateField";
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
                requestParamIdName : "id",
                requestParamIdValue : OPENIAM.ENV.TemplateId,
                pageSize : -1,
                deleteOptions : {
                	preventWarning : true,
                	onDelete : function(bean) {
	                    that.remove(bean);
	                }
                },
                hasEditButton : args.hasEditButton,
                onEdit : function(bean) {
                    that.edit(bean);
                },
                emptyResultsText : args.emptyResultsText,
                theadInputElements : inputelements,
                onAppendDone : function() {
                    var input = null;
                    var submit =null;
                    switch(that.getEntityType()) {
                        case "fields":
                            OPENIAM.PageTemplate.Entitlements.Common.initChangeOrderPlugin({target:that});
                            input = this.find("#fieldName");
                            submit =  this.find("#searchField");
                            OPENIAM.PageTemplate.Entitlements.Common.initSearchPlugin({target:that,
                                                                                        inputElement: input,
                                                                                        submitElement: submit,
                                                                                        getAdditionalDataRequestObject: args.getAdditionalDataRequestObject,
                                                                                        ajaxURL : args.modalAjaxURL,
                                                                                        dialogTitle : args.dialogTitle,
                                                                                        emptyResultsText : args.emptySearchResultsText
                                                                                      });
                            break;
                        case "patterns":
                            input = this.find("#pattern");
                            submit =  this.find("#searchPattern");
                            OPENIAM.PageTemplate.Entitlements.Common.initSearchPlugin({target:that,
                                                                                       inputElement: input,
                                                                                       submitElement: submit,
                                                                                       getAdditionalDataRequestObject: args.getAdditionalDataRequestObject,
                                                                                       ajaxURL : args.modalAjaxURL,
                                                                                       dialogTitle : args.dialogTitle,
                                                                                       emptyResultsText : args.emptySearchResultsText
                                                                                      });
                            break;
                        case "templateFields":
                            OPENIAM.PageTemplate.Entitlements.Common.initChangeOrderPlugin({target:that});
                            $("#editDialog").modalEdit({
                                fields: args.editFields,
                                dialogTitle: args.dialogTitle,
                                sanitizeMapValue : function(val) {
                                	return (val != null) ? val.value : null
                                },
                                onSubmit: function(bean){
                                    that.save(bean);
                                }
                            });
                            that.reloadTemplateFieldSelect();
//                            input = this.find("#templateField");
                            this.find("#addTemplateField").click(function(){
                                var bean = $("#templateField").find("option:selected").data("entity");
                                bean.languageMap = { 1 : {value : bean.name, languageId : 1}};
                                that.addBean(bean);
                            });
                            break;
                        default:
                            break;
                    }
                }
            });
        },
        initSearchPlugin:function(args){
            args.inputElement.keydown(function(e) {
                if(e.keyCode == 13) {
                    return false;
                }
            });
            args.inputElement.modalSearch({
                onElementClick : function(bean) {
                    args.target.addBean(bean);
                },
                ajaxURL : args.ajaxURL,
                dialogTitle : args.dialogTitle,
                emptyResultsText : args.emptyResultsText,
                getAdditionalDataRequestObject : args.getAdditionalDataRequestObject
            });
            args.inputElement.keyup(function(e) {
                if(e.keyCode == 13) {
                    submit.click();
                }
            });

            args.submitElement.click(function() {
                args.inputElement.modalSearch("show");
            });
        },
        addRow:function(args){
            var that = args.target;
            var bean = args.data;
            $(that.getRootElement()).entitlemetnsTable("addRow", bean);
        },
        isAdded:function(args){
            var that = args.target;
            var bean = args.data;
            return $(that.getRootElement()).entitlemetnsTable("isAdded", bean);
        },
        updateRow:function(args){
            var that = args.target;
            var bean = args.data;
            $(that.getRootElement()).entitlemetnsTable("updateRow", bean);
        },
        rowCount: function(args){
            var that = args.target;
            return $(that.getRootElement()).entitlemetnsTable("rowCount");
        },
        remove : function(args) {
            var that = args.target;
            var bean = args.data;

            /*
            OPENIAM.Modal.Warn({
                message : args.deleteMessage,
                buttons : true,
                OK : {
                    text : args.okBtnText,
                    onClick : function() {
                        OPENIAM.Modal.Close();
                        $(that.getRootElement()).entitlemetnsTable("deleteRow", bean);
                    }
                },
                Cancel : {
                    text : "Cancel",
                    onClick : function() {
                        OPENIAM.Modal.Close();
                    }
                }
            });
            */
            $(that.getRootElement()).entitlemetnsTable("deleteRow", bean);
        },
        removeAll : function(args){
            var that = args.target;
            $(that.getRootElement()).entitlemetnsTable("deleteAll");
        },
        initChangeOrderPlugin:function(args){
            var that = args.target;
            $(that.getRootElement()).sortable({ axis: "y",
                delay: 150,
                items: " table tbody tr",
                containment: "parent",
                tolerance: "pointer",
                stop: function(event, ui) {
                    $(that.getRootElement()).entitlemetnsTable("setOddRows");
                }
            });
        }
    },
    TemplateField : {
        getEntityType : function() {
            return "templateFields";
        },
        getRootElement : function() {
            return "#templateFieldContainer";
        },
        load : function() {
            OPENIAM.PageTemplate.Entitlements.Common.load({
                columns : [
                    localeManager["openiam.ui.page.template.field.name"],
                    localeManager["openiam.ui.page.template.field.is.required"],
                    localeManager["openiam.ui.page.template.field.is.editable"],
                	localeManager["openiam.ui.common.actions"]
                ],
                columnsMap:["name", "isRequired", "editable"],
                ajaxURL : "getTemplateFields.html",
                buttonTitle : localeManager["openiam.ui.page.template.field.add"],
                hasEditButton : true,
                target : this,
                dialogTitle:localeManager["openiam.ui.page.template.search.field.dialog.title"],
                emptySearchResultsText:localeManager[""],
                editFields:[{fieldName: "id", type:"hidden",label:""},
                		    {fieldName: "fieldId", type:"hidden",label:""},
                		    {fieldName: "xrefId", type:"hidden",label:""},
                            {fieldName: "name", type:"text",label:localeManager["openiam.ui.page.template.field.name"], readonly:true, required:true},
                            {fieldName: "isRequired", type:"checkbox",label:localeManager["openiam.ui.page.template.field.is.required"]},
                            {fieldName: "editable", type:"checkbox",label:localeManager["openiam.ui.page.template.field.is.editable"]},
                            {fieldName: "languageMap", type:"map",label:localeManager["openiam.ui.common.display.name"], items:OPENIAM.ENV.LanguageList, keys:"languageId",keyLabel:"name",value:"value", required:true}]
            });
        },
        reloadTemplateFieldSelect: function(select){
            var obj = this;
             // get Data for templateTypeId
            var templateTypeId = $("#templateType").val();
            if(select==null){
                select = $("#templateField");
            }
            var requestData = {"templateTypeId" : templateTypeId};
            $.ajax({
                url : "searchTemplateFields.html",
                data : requestData,
                type : "GET",
                dataType : "json",
                contentType: "application/json",
                success : function(data, textStatus, jqXHR) {
                    var newOptionList = [];
                    var opt = document.createElement("option"); opt.value=""; opt.innerHTML="Please select Field...";
                    newOptionList.push(opt);

                    if(data.beans && data.beans.length > 0) {
                        for(var j=0; j<data.beans.length;j++){
                            if(!OPENIAM.PageTemplate.Entitlements.Common.isAdded({target:obj, data:data.beans[j]})){
                                opt = document.createElement("option");
                                opt.value = data.beans[j].id;
                                opt.innerHTML=data.beans[j].name;
                                $(opt).data("entity", data.beans[j]);
                                newOptionList.push(opt);
                            }
                        }
                    }
                    $(select).empty().append(newOptionList);
                },
                error : function(jqXHR, textStatus, errorThrown) {
                    OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
                }
            });


        },
        addBean:function(bean){
            if(bean!=null){
                if(!OPENIAM.PageTemplate.Entitlements.Common.isAdded({target:this, data:bean})){
                    OPENIAM.PageTemplate.Entitlements.Common.addRow({target:this, data:bean});
                    $("#templateField").find("option:selected").remove();
                } else {
                    OPENIAM.Modal.Error("Template field is already added");
                }
            }
        },

        edit : function(bean){
            $("#editDialog").modalEdit("show", bean);
        },
        remove : function(bean) {
            var obj = this;
            OPENIAM.PageTemplate.Entitlements.Common.remove({target:obj, data:bean,
                                                             deleteMessage:localeManager["openiam.ui.page.template.field.delete"],
                                                             okBtnText:localeManager["openiam.ui.page.template.field.delete.confirm"]});

            var opt = document.createElement("option");
            opt.value = bean.id;
            opt.innerHTML=bean.name;
            $(opt).data("entity", bean);

            $("#templateField").append(opt);
        },
        removeAll : function(){
            var obj = this;
            OPENIAM.PageTemplate.Entitlements.Common.removeAll({target:obj});
        },
        save : function(bean){
        	var $that = $("#editDialog")
        	var languageMap=null;
            var liList = $that.find("#languageMap input");
            if(liList!=null && liList.length>0){
                languageMap={};
                liList.each(function(){
                    var lm ={};
                    var langId = $(this).attr("languageId");
                    lm.value=$(this).val();

                    if(lm.value!=null && lm.value!=undefined && lm.value!=''){
                        lm.languageId=langId;
                        lm.referenceId=bean.xrefId;
                        languageMap[langId] = lm;
                    }
                });
            }
            bean.languageMap=languageMap;
            OPENIAM.PageTemplate.Entitlements.Common.updateRow({target:this, data:bean});
            $("#editDialog").modalEdit("hide");
        }
    },
    CustomField : {
    	getEntityType : function() {
    		return "fields";
    	},
    	getRootElement : function() {
    		return "#customFieldContainer";
    	},
        load : function() {
            OPENIAM.PageTemplate.Entitlements.Common.load({
                columns : [
                    localeManager["openiam.ui.page.template.custom.field.name"],
                    localeManager["openiam.ui.page.template.custom.field.type"],
                	localeManager["openiam.ui.common.actions"]
                ],
                columnsMap:["name", "fieldTypeDescription"],
                ajaxURL : "getFieldsForTemplate.html",
                modalAjaxURL : "rest/api/fields/searchCustomFields",
                buttonTitle : localeManager["openiam.ui.button.search"],
                hasEditButton : true,
                target : this,
                dialogTitle:localeManager["openiam.ui.page.template.search.custom.field.dialog.title"],
                emptyResultsText: localeManager["openiam.ui.page.template.search.custom.field.empty"],
                emptySearchResultsText:localeManager["openiam.ui.page.template.search.custom.field.not.found"],
                placeholder: localeManager["openiam.ui.page.template.custom.field.placeholder"]+"...",
                getAdditionalDataRequestObject: function(){
                    var type = $("#typeId").val();
                    var obj = {};
                    if(type!=null && type!=undefined && type!="")
                        obj.typeId = type;
                    return obj;
                }
            });
        },
        addBean:function(bean){
            bean.displayOrder=OPENIAM.PageTemplate.Entitlements.Common.rowCount({target:this})+1;
            OPENIAM.PageTemplate.Entitlements.Common.addRow({target:this, data:bean});
        },
        edit : function(bean){
            window.location.href = "editCustomField.html?id=" + bean.id;
        },
        remove : function(bean) {
            var obj = this;
            OPENIAM.PageTemplate.Entitlements.Common.remove({target:obj, data:bean,
                                                            deleteMessage:localeManager["openiam.ui.page.template.custom.field.delete"],
                                                            okBtnText:localeManager["openiam.ui.page.template.custom.field.delete.confirm"]});
        }
    },
    Patterns:{
    	getEntityType : function() {
    		return "patterns";
    	},
    	getRootElement : function() {
    		return "#uriPatternContainer";
    	},
        load : function() {
            OPENIAM.PageTemplate.Entitlements.Common.load({
                columns : [
                    localeManager["openiam.ui.page.template.uri.pattern.column"],
                    localeManager["openiam.ui.page.template.content.provider.column"],
                	localeManager["openiam.ui.common.actions"]
                ],
                columnsMap:["pattern", "providerName"],
                ajaxURL : "getPatternsForTemplates.html",
                modalAjaxURL : "searchPattens.html",
                buttonTitle : localeManager["openiam.ui.button.search"],
                hasEditButton : true,
                target : this,
                dialogTitle:localeManager["openiam.ui.page.template.uri.pattern"],
                emptyResultsText: localeManager["openiam.ui.page.template.uri.pattern.empty"],
                emptySearchResultsText: localeManager["openiam.ui.page.template.uri.pattern.not.found"],
                placeholder: localeManager["openiam.ui.page.template.uri.pattern.placeholder"]+"..."
            });
        },
        addBean:function(bean){
            OPENIAM.PageTemplate.Entitlements.Common.addRow({target:this, data:bean});
        },
         edit : function(bean){
            window.location.href = "/webconsole-am/editProviderPattern.html?id=" + bean.id+"&providerId="+bean.providerId;
        },
        remove : function(bean) {
            var obj = this;
            OPENIAM.PageTemplate.Entitlements.Common.remove({target:obj, data:bean, deleteMessage:localeManager["openiam.ui.page.template.uri.pattern.delete"], okBtnText:localeManager["openiam.ui.page.template.uri.pattern.delete.confirm"]});
        }
    }
};

$(document).ready(function() {
    OPENIAM.PageTemplate.Entitlements.Load.onReady();
    OPENIAM.PageTemplate.Entitlements.Load.onLoad();
});

$(window).load(function() {
});