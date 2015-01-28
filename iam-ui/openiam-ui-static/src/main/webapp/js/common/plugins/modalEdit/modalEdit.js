(function($) {

    var privateMethods = {
        createHtml : function() {
            var $this = this;
            var $options = $this.data("options");

            var html = document.createElement("div");

            var table = document.createElement("table");
            $(table).attr("width", "100%");
            var tbody = document.createElement("tbody");
            var tfoot = document.createElement("tfoot");
            var tr = null;
            var tdLabel = null;
            var tdControl = null;
            $(table).append(tbody, tfoot);
            for (var i = 0; i < $options.fields.length; i++) {
                var field = $options.fields[i];
                tr = document.createElement("tr");
                $(tbody).append(tr);

                tdLabel = document.createElement("td");
                tdControl = document.createElement("td");
                // add label
                var label = document.createElement("label");
                $(label).attr("for", field.fieldName);
                // label.for=field.fieldName;
                label.innerHTML = field.label;
                if (field.required)
                    label.className = "required";

                // add control
                var ctrl = null;
                switch (field.type) {
                case "hidden":
                case "checkbox":
                    ctrl = document.createElement("input");
                    ctrl.type = field.type;
                    ctrl.id = field.fieldName;
                    ctrl.name = field.fieldName;
                    ctrl.value = "";
                    break;
                case "div":
                    ctrl = document.createElement("div");
                    ctrl.id = field.fieldName;
                    if (field.linkLabel) {
                        ctrl.innerHTML = field.linkLabel;
                    }
                    if (field.hidden) {
                        $(ctrl).hide();
                    }
                    break;
                case "link":
                    ctrl = document.createElement("a");
                    ctrl.id = field.fieldName;
                    ctrl.href = field.href || "javascript:void(0);";
                    ctrl.innerHTML = field.linkLabel;
                    if (field.additionaClazz) {
                        $(ctrl).addClass(field.additionaClazz);
                    }
                    if (field.hidden) {
                        $(ctrl).hide();
                    }
                    break;
                case "datetimepicker":
                case "text":
                    ctrl = document.createElement("input");
                    ctrl.type = field.type;
                    ctrl.id = field.fieldName;
                    ctrl.name = field.fieldName;
                    ctrl.value = "";
                    $(ctrl).addClass("full").addClass("rounded");
                    if (field.placeholder) {
                        $(ctrl).attr("placeholder", field.placeholder);
                        OPENIAM.FN.applyPlaceholder(ctrl);
                    }
                    break;
                case "textarea":
                        ctrl = document.createElement("textarea");
                        ctrl.id = field.fieldName;
                        ctrl.name = field.fieldName;
                        ctrl.value = "";
                        $(ctrl).addClass("full").addClass("rounded");
                        if (field.placeholder) {
                            $(ctrl).attr("placeholder", field.placeholder);
                            OPENIAM.FN.applyPlaceholder(ctrl);
                        }
                        break;
                case "select":
                case "multiselect":
                    ctrl = document.createElement("select");
                    ctrl.id = field.fieldName;
	
                     if (field.hidden) {
                     	$(label).hide();
                     	$(ctrl).hide();
                     }
                    
                    var option = document.createElement("option");

                    if(field.type=="multiselect"){
                        $(ctrl).attr("multiple","multiple");
                    } else {
                        option.text = localeManager["openiam.ui.common.please.select"];
                        option.value = "";
                    }
                    ctrl.add(option);
                    if (field.items != null && field.items != undefined && field.items.length > 0) {
                        for (var opt = 0; opt < field.items.length; opt++) {
                            option = document.createElement("option");
                            option.text = (field.items[opt].name) ? field.items[opt].name : field.items[opt].displayName;
                            option.value = field.items[opt].id;
                            ctrl.add(option)
                        }
                    }
                    break;
                case "map":
                    ctrl = document.createElement("div");
                    ctrl.id = field.fieldName;

                    if (field.items != null && field.items != undefined && field.items.length > 0) {
                        for (var opt = 0; opt < field.items.length; opt++) {
                            var option = document.createElement("div");
                            $(option).attr(field.keys, field.items[opt][field.keys]);

                            var spLbl = document.createElement("span");
                            spLbl.className = "mapLabel";
                            spLbl.innerHTML = field.items[opt][field.keyLabel];

                            var spInput = document.createElement("span");
                            spInput.className = "mapInput";

                            var inp = document.createElement("input");
                            inp.type = "text";
                            inp.className = "full rounded";
                            inp.id = field.items[opt][field.keys] + "_optName";
                            inp.name = field.items[opt][field.keys] + "_optName";
                            $(inp).attr(field.keys, field.items[opt][field.keys]);

                            $(spInput).append(inp);

                            $(option).append(spLbl);
                            $(option).append(spInput);
                            $(ctrl).append(option);

                        }
                    }
                    $(ctrl).addClass("mapElement");
                    break;
                case "multiText":
                    var d = document;
                    ctrl = d.createElement("div");
                    ctrl.id = field.fieldName;
                    var mCode = d.createDocumentFragment();
                    var mTable = d.createElement("table");
                    var mTr = d.createElement("tr");
                    var mTd1 = d.createElement("td");
                    var mInput = d.createElement("input");
                    mInput.id = "values[0]";
                    mInput.type = "text";
                    $(mInput).addClass("full rounded");
                    mTd1.appendChild(mInput);
                    mTr.appendChild(mTd1);
                    var mTd2 = d.createElement("td");
                    var multiPlusBtn = d.createElement("div");
                    multiPlusBtn.innerHTML = "[+]";
                    $(multiPlusBtn).addClass("multiPlusBtn");
                    mTd2.appendChild(multiPlusBtn);
                    mTr.appendChild(mTd2);
                    mTable.appendChild(mTr);
                    mCode.appendChild(mTable);
                    ctrl.appendChild(mCode);
                    $(ctrl).addClass("multiElement");
                    $(multiPlusBtn).click(function() {
                        var cTr = d.createElement("tr");
                        var cTd1 = d.createElement("td");
                        var cInput = d.createElement("input");
                        cInput.type = "text";
                        cInput.value = mInput.value;
                        mInput.value = "";
                        $(cInput).addClass("full rounded");
                        cTd1.appendChild(cInput);
                        cTr.appendChild(cTd1);
                        var cTd2 = d.createElement("td");
                        var multiMinusBtn = d.createElement("div");
                        $(multiMinusBtn).addClass("multiMinusBtn");
                        multiMinusBtn.innerHTML = "[-]";
                        cTd2.appendChild(multiMinusBtn);
                        cTr.appendChild(cTd2);
                        mTable.insertBefore(cTr, mTr.nextSibling);
                        $(multiMinusBtn).click(function() {
                            mTable.removeChild(cTr);
                        });
                    });
                    break;
                default:
                    break;
                }
                $(ctrl).addClass("ctrlElement");
                if (typeof (field.readonly) === "boolean" && field.readonly == true) {
                    if (field.type == "map") {
                        $(ctrl).find("input").attr("disabled", "disabled");
                    } else {
                        $(ctrl).attr("disabled", "disabled");
                    }
                }

                $(tdLabel).append(label);
                $(tdControl).append(ctrl);

                $(tr).append(tdLabel, tdControl);

                privateMethods.bindElement.call($this, ctrl, field);
            }
            // create buttons
            var btnContainer = document.createElement("div");
            var ul = document.createElement("ul");
            ul.className = "formControls";
            var li1 = document.createElement('li');
            var btn1 = document.createElement('input');
            btn1.type = "button";
            btn1.id = "saveBtn";
            btn1.value = $options.saveBtnTxt;
            btn1.className = "redBtn";
            var li2 = document.createElement('li');
            var btn2 = document.createElement('input');
            btn2.type = "button";
            btn2.id = "cancelBtn";
            btn2.value = $options.cancelBtnText;
            btn2.className = "whiteBtn";

            $(li1).append(btn1);
            $(li2).append(btn2);
            $(ul).append(li2, li1);
            $(btnContainer).addClass("buttonContainer").append(ul);
            $(html).append(table, btnContainer);

            $options.dialog.html(html).dialog({
                autoOpen : false,
                draggable : false,
                resizable : false,
                title : $options.dialogTitle,
                width : $options.width,
                open:function (event, ui) {
                    $options.dialog.css('overflow', 'visible'); //this line does the actual hiding
                    $options.dialog.parents(".ui-dialog:first").css('overflow', 'visible');
                }
            });
            OPENIAM.FN.turnOffAutocomplete($options.dialog);

            $(btn1).click(function() {
                var json = privateMethods.toJSON.call($this);
                // $options.dialog.dialog("close");
                if (privateMethods.validateRequiredFields.call($this, json)) {
                    $options.onSubmit.call($this, json);
                }
            });
            $(btn2).click(function() {
                $options.dialog.dialog("close");
            });
        },
        bindElement : function(element, field) {
            var $this = this;
            if ($.isFunction(field.onClick)) {
                $(element).click(function() {
                    field.onClick.call($this);
                });
            }

            if ($.isFunction(field.onChange)) {
                $(element).change(function() {
                    field.onChange.call($this);
                });
            }
        },
        bind : function(bean) {
            var $this = $(this);
            var $options = $this.data("options");

            for (var i = 0; i < $options.fields.length; i++) {
                var field = $options.fields[i];

                var ctrl = $this.find("#" + field.fieldName);
                if (field.type == "map") {
                    var dataMap = (bean == null || bean == undefined || bean[field.fieldName] == null || bean[field.fieldName] == undefined) ? null
                            : bean[field.fieldName];
                    if (dataMap) {
                        ctrl.find("div[" + field.keys + "]").each(function() {
                            var divElem = $(this);
                            var val = dataMap[divElem.attr(field.keys)];
                            if ($.isFunction($options.sanitizeMapValue)) {
                                val = $options.sanitizeMapValue(val);
                            }
                            if (val) {
                                divElem.find("span.mapInput input").val(val);
                            } else {
                                divElem.find("span.mapInput input").val("");
                            }
                        });
                    } else {
                        ctrl.find("span.mapInput input").val("");
                    }
                } else if (field.type == "checkbox") {
                    ctrl.prop('checked', (bean != null && bean != undefined && (bean[field.fieldName] == true || bean[field.fieldName] == "true")));

                } else if (field.type == "multiText") {
                    if (bean["isMultivalued"]) {
                        var plusBtn = $(ctrl).find(".multiPlusBtn");
                        $(bean["values"]).each(function(index, element) {
                            if (index) {
                                $(plusBtn).click();
                            }
                        });
                        $(ctrl).find(":input").each(function(index, element) {
                            $(this).val(bean["values"][index]);
                        });
                    } else {
                        $(ctrl).find(":input").val(bean["value"]);
                    }

                } else if(field.type=="multiselect"){
                    if((bean != null && bean != undefined && bean[field.fieldName] != null && bean[field.fieldName] != undefined)){
                        $(ctrl).find("option").each(function(){
                            var opt = $(this);
                            var val = opt.attr("value");
                            if($.inArray(val, bean[field.fieldName])!=-1){
                                opt.prop("selected", true);
                            }
                        });
                    }
                    $(ctrl).chosen({placeholder_text_multiple: localeManager["openiam.ui.common.please.select"]});
                } else {
                    ctrl.val((bean == null || bean == undefined || bean[field.fieldName] == null || bean[field.fieldName] == undefined) ? ""
                            : bean[field.fieldName]);
                }
            }
            $options.dialog.dialog("open");
        },
        validateRequiredFields : function(json) {
            var retVal = true;
            var $this = this;
            var $options = $this.data("options");
            for (var i = 0; i < $options.fields.length; i++) {
                var field = $options.fields[i];
                var fieldName = field.fieldName;
                var required = field.required;
                var jsonFieldValue = json[fieldName];

                if (required === true && privateMethods.isFieldValidatable(field)) {
                    var fieldElem = $("#" + fieldName);
                    if (field.type === "multiText") {
                        if (!json['values'].length && !json['value']) {
                            $(fieldElem).fieldNotification({
                                type : "required"
                            });
                            retVal = false;
                        }
                    } else if(field.type === "datetimepicker") {
                        var fieldValue = $(fieldElem).datetimepicker('getDate').getTime();
                        if(fieldValue === null || fieldValue == undefined || fieldValue == "") {
                            $(fieldElem).fieldNotification({
                                type : "required"
                            });
                            retVal = false;
                        }
                    }  else if (jsonFieldValue == null || jsonFieldValue == undefined || jsonFieldValue == "") {
                        $(fieldElem).fieldNotification({
                            type : "required"
                        });
                        retVal = false;
                    }
                }
            }
            return retVal;
        },
        isFieldValidatable : function(field) {
            var fieldType = field.type;
            return (fieldType === "multiselect" || fieldType === "select" || fieldType === "text" || fieldType === "multiText" || fieldType === "datetimepicker");
        },
        toJSON : function() {
            var $this = this;
            var $options = $this.data("options");
            var obj = {};
            for (var i = 0; i < $options.fields.length; i++) {
                var field = $options.fields[i];

                var ctrl = $this.find("#" + field.fieldName);// $("#"+field.fieldName);

                if (field.type == "map") {
                    var dataMap = {};
                    if (dataMap) {
                        ctrl.find("div[" + field.keys + "]").each(function() {
                            var divElem = $(this);
                            dataMap[divElem.attr(field.keys)] = divElem.find("span.mapInput input").val();
                        });
                    }
                    obj[field.fieldName] = dataMap;
                } else if (field.type == "checkbox") {
                    obj[field.fieldName] = ctrl.is(":checked");
                } else if (field.type == "select") {
                    var v = ctrl.val();
                    if (v == "")
                        v = null;
                    obj[field.fieldName] = v;
                    if (field.itemText) {
                        obj[field.itemText] = ctrl.find("option:selected").text();
                    }
                } else if (field.type == "multiText") {
                    var values = [];
                    var inputs = $(ctrl).find(":input");
                    inputs.each(function(index, element) {
                        var v = $(this).val();
                        if (v) {
                            values.push(v);
                        }
                    });
                    obj['isMultivalued'] = false;
                    if (values.length == 1) {
                        obj['value'] = values[0];
                        obj['values'] = [];
                    } else {
                        if (values.length > 1) {
                            obj['isMultivalued'] = true;
                        }
                        obj['value'] = null;
                        obj['values'] = values;
                    }
                } else {
                    var v = ctrl.val();
                    if (v == "")
                        v = null;
                    obj[field.fieldName] = v;
                }
            }
            return obj;
        }
    };

    var methods = {
        init : function(args) {
            var options = $.extend({
                fields : [], // array of the objects, for each field
                onSubmit : null, // callback when the 'Submit' button is
                dialogHandle : null,
                // pressed
                dialogTitle : null,
                width : 400,
                saveBtnTxt : localeManager["openiam.ui.common.save"],
                cancelBtnText : localeManager["openiam.ui.common.cancel"],
                onShown : null,
                afterFormAppended : function() {
                } // called when the form is fetched and appended
            }, args);
            
            var dialog = null;
            if($(options.dialogHandle).length > 0) {
            	dialog = $(options.dialogHandle);
            } else {
            	if ($("#editDialog").length == 0) {
	                var dialog = document.createElement("div");
	                dialog.id = "editDialog";
	                $(document.body).append(dialog);
	                dialog = $(dialog);
	            } else {
	                dialog = $("#editDialog");
	            }
            }
            options.dialog = dialog;

            this.data("options", options);

            if (options.onSubmit == null && !$.isFunction(options.onSubmit)) {
                $.error("onSubmit option required, but not present");
            }

            if (options.fields == null || options.fields == undefined || options.fields.length == 0) {
                $.error("fields option required, but not present");
            }
            if (options.dialogTitle == null) {
                $.error("No dialogTitle provided");
            }

            privateMethods.createHtml.call(this);
            options.afterFormAppended();
        },
        show : function(bean) { /* will bind bean to form and show it in dialog */
            var $this = $(this);
            var $options = $this.data("options");
            privateMethods.bind.call(this, bean);

            if ($.isFunction($options.onShown)) {
                $options.onShown.call(this, bean);
            }
        },
        hide : function() {
            var $this = $(this);
            var $options = $this.data("options");
            $options.dialog.dialog("close");
        }
    };

    $.fn.modalEdit = function(method) {
        if (methods[method]) {
            return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === 'object' || !method) {
            return methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.modalEdit');
        }
    };
})(jQuery);