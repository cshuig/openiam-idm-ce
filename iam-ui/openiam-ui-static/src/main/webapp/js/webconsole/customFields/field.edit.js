OPENIAM = window.OPENIAM || {};
OPENIAM.CustomField = {
    lastId : 0,
    init : function(){
        $("#optionsList").tablesorter({
            debug: false,
            sortDisabled : true,
            widgets: ['zebra'] });



        $("#editDialog").modalEdit({
            width:420,
            fields: [{fieldName: "id", type:"hidden",label:""},
                     {fieldName: "uiValue", type:"text",label:localeManager["openiam.ui.common.value"], required:true},
                     {fieldName: "languageMap", type:"map",label:localeManager["openiam.ui.common.display.name"], items:OPENIAM.ENV.LanguageList, keys:"languageCode",keyLabel:"name",value:"value", required:true},
                     {fieldName: "default", type:"checkbox",label:localeManager["openiam.ui.common.is.default"]}],
            dialogTitle: localeManager["openiam.ui.custom.fields.edit.option.element"],
            onSubmit: function(bean){
                OPENIAM.CustomField.saveValidValue(bean);
            }
        });

        $("#optionsList #addBtn").click(function(){
            var bean = {};
            $("#editDialog").modalEdit("show", bean);
            return false;
        });

        $("#optionsList tbody a.editBtn").click(function(){
            OPENIAM.CustomField.editValidValue(this);
            return false;
        });
        $("#optionsList tbody a.deleteBtn").click(function(){
            OPENIAM.CustomField.deleteValidValue(this);
            return false;
        });

        var parentContainer = $("td.languageMap");
        OPENIAM.CustomField.initTranslateAPI(parentContainer);
        parentContainer = $("div#languageMap");
        OPENIAM.CustomField.initTranslateAPI(parentContainer);
    },
    initTranslateAPI:function(parentContainer){
         parentContainer.find("input[languageCode]").each(function(){
             var curInput = $(this);

             var div = document.createElement("div");
                 div.className = "translateButton";
                 div.title = localeManager["openiam.ui.custom.fields.auto.translate"];

//             var a = document.createElement("a");
//                 a.href = "javascript:void(0);";
//                 a.title = "Auto Translate";
//                 a.innerHTML = "Auto Translate";
             $(div).hide();//.append(a);
             curInput.parent().append(div);

             curInput.on("focus", function(){
                 if(OPENIAM.CustomField.checkEnteredValues(parentContainer)){
                    $(this).parent().find(".translateButton").show();
                 }
             }).on("blur",function(){
                     OPENIAM.CustomField.hideTranslateButton($(this));
                 });

             $(div).on('mousedown', function(){
                // var input = $(this).parents("span:first").find("input[languageCode]");
                 curInput.off('blur');
             }).click(function(){
//                 var input = $(this).parents("span:first").find("input[languageCode]");
                 OPENIAM.CustomField.translateData(curInput, parentContainer);
                 $(this).hide();

                 curInput.on("blur",function(){
                     OPENIAM.CustomField.hideTranslateButton($(this));
                 });
             });

         });
    },
    hideTranslateButton:function(input){
        $(input).parent().find(".translateButton").hide();
    },
    checkEnteredValues:function(parentContainer){
        var isEntered = false;
        parentContainer.find("input[languageCode]").each(function(){
             var val = $(this).val();
             if(val){
                 isEntered = true;
                 return false;
             }
        });
        return isEntered;
    },
    translateData: function(input, parentContainer){
        // get first filled input
        var filledInput=null;
        parentContainer.find("input[languageCode]").each(function(){
            var val = $(this).val();
            if(val){
                filledInput = $(this);
                return false;
            }
        });

        if(filledInput!=null){
            // check source and target languages
            var sourceLang = filledInput.attr("languageCode");
            var targetLang = input.attr("languageCode");
            if(targetLang!=sourceLang){
                // if  source and target languages not equals then try to translate
                var val = filledInput.val();
                if(val){
                    var obj = {};
                        obj.sourceLang=sourceLang;
                        obj.targetLang=targetLang;
                        obj.text = val;

                    $.ajax({
                        url : "translateLabels.html",
                        data : JSON.stringify(obj),
                        type: "POST",
                        dataType : "json",
                        contentType: "application/json",
                        success : function(data, textStatus, jqXHR) {
                            if(data && data.text) {
                                input.val(data.text);
                            }else{
                                input.val("");
                            }
                        },
                        error : function(jqXHR, textStatus, errorThrown) {
                            OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
                        }
                    });
                }
            }
        }
    },
    initChangeOrderPlugin:function(){
        $("#optionsList").sortable({ axis: "y",
            delay: 150,
            items: " tbody tr",
            containment: "parent",
            tolerance: "pointer",
            stop: function(event, ui) {
                OPENIAM.CustomField.setOddRows();
            }
        });
    },
    editValidValue:function(btn){
        var tr = $(btn).parents("tr:first");
        var propId = tr.attr("entityid");
        var isDef = tr.attr("isDefault");

        var bean={};
        bean.id=propId;
        bean.default=false;
        if(isDef){
            bean.default=true;
        }
        bean.uiValue=tr.find("td.uiValue").html();

        var languageMap=null;
        var liList = tr.find("td.displayName ul li");
        if(liList!=null && liList.length>0){
            languageMap={};

            liList.each(function(){
                var languageCode = $(this).attr("languageCode");
                languageMap[languageCode]=$(this).find("span.languageValue").text();
            });
        }
        bean.languageMap=languageMap;
        $("#editDialog").modalEdit("show", bean);
        return false;
    },
    deleteValidValue:function(btn){
        var tr = $(btn).parents("tr:first");
        var isDef = tr.attr("isDefault");
        tr.remove();
        var trList = $("table#optionsList tbody tr[entityId]");
        if(trList==null || trList.length<=0){
            var row = document.createElement("tr");
            row.className="empty";

            var td = document.createElement("td");
            td.className="empty";
            td.colSpan=4;
            td.innerHTML=localeManager["openiam.ui.custom.fields.options.no.found"];

            $(row).append(td);
            $("table#optionsList tbody").append(row);
        } else{
            if(isDef){
                var defRow = $(trList[0]);
                OPENIAM.CustomField.setDefaultValue(defRow, defRow.find("td.uiValue").html());
            }
        }
    },
    setDefaultValue:function(row, value){
        var cell = row.find("td.defaultFlag");
        // find previous default row and reset it
        var defaultRow = $("#optionsList").find("tr[isDefault]");
        if(defaultRow && defaultRow.length>0){
            defaultRow.removeAttr("isDefault").find("td.defaultFlag").html("");
        }
        $(row).attr("isDefault", true);

        var img = document.createElement("div"); $(img).addClass("openiam-check-icon");
        cell.append(img);
        $("#staticDefaultValue").val(value);
    },
    unsetDefaultValue : function(row) {
    	var cell = row.find("td.defaultFlag");
        // find previous default row and reset it
        var isDefault = row.attr("isDefault");

        if(isDefault!=null && isDefault!=undefined && (isDefault==true || isDefault=="true")){
            row.removeAttr("isDefault").find("td.defaultFlag").html("");
            $("#staticDefaultValue").val("");
        }
    },
    saveValidValue:function(bean){
        var isNew=false;

        // get bean Id
        var beanId= bean.id;
        if(!beanId){
            this.lastId+=1;
            beanId = "new_"+this.lastId;
            isNew=true;
        }
        if(isNew){

          // add new row to table
          var tr = document.createElement("tr");
              $(tr).attr("entityId", beanId).attr("displayOrder", $("#optionsList").find("tbody tr:not(tr.empty)").length+1);

          var td1 = document.createElement("td");
              td1.className="uiValue";

          var td2 = document.createElement("td");
              td2.className="displayName";

          var td3 = document.createElement("td");
              td3.className="defaultFlag";

          var td4 = document.createElement("td");
              td4.className="action";

          var a1 = document.createElement("a"); a1.href = "javascript:void(0)";
          var img1 = document.createElement("div"); $(img1).addClass("openiam-edit-icon").addClass("openiam-image-option");
            $(a1).append(img1);
            $(a1).click(function() {
                OPENIAM.CustomField.editValidValue(this);
                return false;
            });
          var a2 = document.createElement("a"); a2.href = "javascript:void(0)";
          var img2 = document.createElement("div"); $(img2).addClass("openiam-delete-icon").addClass("openiam-image-option");
            $(a2).append(img2);
            $(a2).click(function() {
                OPENIAM.CustomField.deleteValidValue(this);
                return false;
            });
            $(td4).append(a1, a2);

          $(tr).append(td1, td2, td3, td4);
          $("table#optionsList tbody").append(tr);
          $("table#optionsList tbody tr.empty").remove();

          OPENIAM.CustomField.initChangeOrderPlugin();
        }
        // find and update existed row
        var row = $("#optionsList").find("tr[entityId="+beanId+"]");
        if(row){
            row.find("td.uiValue").html(bean.uiValue);
            // clean display name
            var cell = row.find("td.displayName");
                cell.html("");

            var ul = document.createElement("ul");

            for(var idx=0; idx<OPENIAM.ENV.LanguageList.length; idx++){
                var lang = OPENIAM.ENV.LanguageList[idx];
                var val = bean.languageMap[lang.languageCode];
                if(val!=null && val!=undefined && val!=''){
                    var li = document.createElement("li");
                        $(li).attr("languageId",lang.id).attr("languageCode",lang.languageCode);

                    var span1 = document.createElement("span");
                        span1.className="languageName";
                        span1.innerHTML=lang.name+":";

                    var span2 = document.createElement("span");
                        span2.className="languageValue";
                        span2.innerHTML=bean.languageMap[lang.languageCode];

                    $(li).append(span1, span2);
                    $(ul).append(li);
                }
            }
            cell.append(ul);

            if(bean.default!=null && bean.default!=undefined && bean.default!=false){
                // try to set default value
                OPENIAM.CustomField.setDefaultValue(row, bean.uiValue);
            } else {
            	OPENIAM.CustomField.unsetDefaultValue(row);
            }
        }
        OPENIAM.CustomField.setOddRows();
        $("#editDialog").modalEdit("hide");
    },
    deleteCustomField : function() {
        $.ajax({
            url : "deleteCustomField.html",
            data : {id : OPENIAM.ENV.FieldId},
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
    saveCustomField : function() {
        $.ajax({
            url : "saveCustomField.html",
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
    switchParamsBox: function(selectedType){
        $("#TEXT_Params").css("display", "none");
        $("#TEXTAREA_Params").css("display", "none");
        $("#SELECT_Params").css("display", "none");
       if(selectedType=="TEXT"){
           $("#TEXT_Params").css("display", "block");
       } else if(selectedType=="TEXTAREA"){
           $("#TEXTAREA_Params").css("display", "block");
       } else if(selectedType=="SELECT"
                    || selectedType=="MULTI_SELECT"
                    || selectedType=="CHECKBOX"
                    || selectedType=="RADIO"){
           $("#SELECT_Params").css("display", "block");
       }
    },
    setOddRows: function(){
        var tbody =  $("#optionsList").find("tbody");
        var trList = tbody.find("tr");
        if(trList.length>0 && !$(trList[0]).hasClass("empty")){
            tbody.find("tr:odd").removeClass("odd").removeClass("even").addClass("odd");
            tbody.find("tr:even").removeClass("odd").removeClass("even").addClass("even");
        }
    },
    toJSON : function() {
        var obj = {};
        obj.id = OPENIAM.ENV.FieldId;
        obj.resourceId = $("#resourceId").val();
        obj.name = $("#name").val();
        obj.isRequired = $("#isRequiredOn").is(":checked");
        obj.isPublic = $("#isPublicOn").is(":checked");
        obj.isEditable=$("#isEditableOn").is(":checked");
        obj.typeId = $("#typeId").val();
        // fill display names in different locales
        obj.displayNameLanguageMap = null;

        $("input[id$=_displayName]").each(function(){

           var lm = {};
               lm.value=$(this).val();
           if(lm.value!=null && lm.value!=undefined && lm.value!=''){
               if(obj.displayNameLanguageMap==null)
                   obj.displayNameLanguageMap={};

                lm.referenceId=OPENIAM.ENV.FieldId;
                lm.languageId=$(this).attr("languageId");
                obj.displayNameLanguageMap[lm.languageId] = lm;
           }
        });


        if(obj.typeId=='TEXT'){
            // fill default values language map
            obj.defaultValueLanguageMap = {};
            obj.staticDefaultValue=null;
            obj.validValues=null;

            $("input[id$=_defaultValue]").each(function(){
                var lm = {};
                lm.value=$(this).val();
                if(lm.value!=null && lm.value!=undefined && lm.value!=''){
                    lm.referenceId=OPENIAM.ENV.FieldId;
                    lm.languageId=$(this).attr("languageId");
                    obj.defaultValueLanguageMap[lm.languageId] = lm;
                }
            });
        } else if(obj.typeId=='TEXTAREA'){
            obj.defaultValueLanguageMap = {};
            obj.staticDefaultValue=null;
            obj.validValues=null;
            $("textarea[id$=_defaultValue]").each(function(){
                var lm = {};
                lm.value=$(this).val();
                if(lm.value!=null && lm.value!=undefined && lm.value!=''){
                    lm.referenceId=OPENIAM.ENV.FieldId;
                    lm.languageId=$(this).attr("languageId");
                    obj.defaultValueLanguageMap[lm.languageId] = lm;
                }
            });
        } else  if(obj.typeId=='SELECT'
                    || obj.typeId=='MULTI_SELECT'
                    || obj.typeId=='CHECKBOX'
                    || obj.typeId=='RADIO') {
            // fill static default value for other field types
            obj.defaultValueLanguageMap=null;
            obj.staticDefaultValue=null;

            // fill validValues elements
            obj.validValues=null;
            var trList = $("table#optionsList tbody tr[entityId]");
            if(trList!=null && trList.length>0){
                obj.validValues=[];
                var displayOrder = 1;
                trList.each(function(){
//                    var displayOrder=$(this).attr("displayOrder");
                    var valueObj = {};
                    var isDef = $(this).attr("isDefault");

                    var id = $(this).attr("entityId");
                    if(id.indexOf("new_") == 0)
                        id=null;
                    var uiValue = $(this).find("td.uiValue").html();

                    var languageMap=null;
                    var liList = $(this).find("td.displayName ul li");
                    if(liList!=null && liList.length>0){
                        languageMap={};
                        liList.each(function(){
                            var lm ={};
                            var langId = $(this).attr("languageId");
                            lm.value=$(this).find("span.languageValue").text();

                            if(lm.value!=null && lm.value!=undefined && lm.value!=''){
                                lm.languageId=langId;
                                lm.referenceId=id;
                                languageMap[langId] = lm;
                            }
                        });
                    }
                    valueObj.id=id;
                    valueObj.uiValue=uiValue;
                    valueObj.displayOrder=displayOrder;
                    valueObj.languageMap=languageMap;
                    valueObj.metadataEntityId=OPENIAM.ENV.FieldId;

                    obj.validValues.push(valueObj);

                    if(isDef){
                        obj.staticDefaultValue=uiValue;
                    }

                    displayOrder++;
                });
            }
        }
        return obj;
    }
};

$(document).ready(function() {
    OPENIAM.CustomField.init();
    OPENIAM.CustomField.initChangeOrderPlugin();

    $("#deleteField").click(function() {
        OPENIAM.Modal.Warn({
            message : localeManager["openiam.ui.custom.fields.delete.warn"],
            buttons : true,
            OK : {
                text : localeManager["openiam.ui.custom.fields.delete.yes"],
                onClick : function() {
                    OPENIAM.Modal.Close();
                    OPENIAM.CustomField.deleteCustomField();
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

    $("#customFieldForm").submit(function() {
        OPENIAM.CustomField.saveCustomField();
        return false;
    });

    $("select#typeId").change(function(){
        OPENIAM.CustomField.switchParamsBox($(this).val());
    });

});

$(window).load(function() {
    /*
     if(OPENIAM.ENV.SuccessMessage != null) {
     OPENIAM.Modal.Success({message : OPENIAM.ENV.SuccessMessage, showInterval : 1000, onIntervalClose : function() {
     window.location.href = "editContentProvider.html?providerId=" + OPENIAM.ENV.ProviderId;
     }});
     }
     */
});