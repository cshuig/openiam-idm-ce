OPENIAM = window.OPENIAM || {};
OPENIAM.Policy = window.OPENIAM.Policy || {};

OPENIAM.Policy.Form = {
    deletePolicy: function () {
        $.ajax({
            url: "deletePolicy.html",
            data: {id: OPENIAM.ENV.PolicyId},
            type: "POST",
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                if (data.status == 200) {
                    OPENIAM.Modal.Success({message: data.successMessage, showInterval: 2000, onIntervalClose: function () {
                        window.location.href = data.redirectURL;
                    }});
                } else {
                    OPENIAM.Modal.Error({errorList: data.errorList});
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
    },

    savePolicy: function () {
        var errors = OPENIAM.Policy.Form.validate();
        if (!errors || errors.length == 0) {
            $.ajax({
                url: "editPolicy.html",
                data: JSON.stringify(this.toPolicyJSON()),
                type: "POST",
                dataType: "json",
                contentType: "application/json",
                success: function (data, textStatus, jqXHR) {

                    if (data.status == 200) {
                        OPENIAM.Modal.Success({message: data.successMessage, showInterval: 2000, onIntervalClose: function () {
                            if (data.redirectURL != null && data.redirectURL != undefined && data.redirectURL.length > 0) {
                                window.location.href = data.redirectURL;
                            } else {
                                window.location.reload(true);
                            }
                        }});
                    } else {
                        OPENIAM.Modal.Error({errorList: data.errorList});
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"] + errorThrown);
                }
            });
        } else {
            OPENIAM.Modal.Error({messages: errors});
        }
    },
    toPolicyJSON: function () {
        var obj = {};
        obj.policyId = OPENIAM.ENV.PolicyId;
        obj.name = $("#name").val();
        obj.description = $("#description").val();
        obj.policyDefId = $("#policyDefId").val();
        obj.rule = $("#rule").val();
        obj.ruleSrcUrl = $("#ruleSrcUrl").val();
        obj.status = $("#status").val();
        obj.policyAttributes = [];
        /* idx starts at 1, not 0 */
        var policyAttribute = {};
        $("#passwordCompositionAttributes").find("tr").each(function () {
            obj.policyAttributes.push(OPENIAM.Policy.Form.toJsonRow(this));
        });
        $("#passwordForgotAttributes").find("tr").each(function () {
            obj.policyAttributes.push(OPENIAM.Policy.Form.toJsonRow(this));
        });
        $("#changeRuleAttributes").find("tr").each(function () {
            obj.policyAttributes.push(OPENIAM.Policy.Form.toJsonRow(this));
        });

        //alert('obj=' + JSON.stringify(obj));
        return obj;
    },
    toJsonRow: function (row) {
        var attr = {};
        attr.policyAttrId = $(row).find("input[type='hidden'].attribute-id").val();
        attr.required = $(row).find("td.attribute-is-enabled input[type='checkbox']").attr('checked') == 'checked' ? true : false;
        attr.policyId = OPENIAM.ENV.PolicyId;
        attr.defParamId = $(row).find(" input[type='hidden'].attribute-def-param-id").val();
        attr.name = $(row).find(" input[type='hidden'].attribute-name").val();
        attr.operation = $(row).find("td.attribute-values").attr("attr-operation");
        attr.grouping = $(row).attr("attr-grouping");
        attr.description = $(row).find("td.attribute-description").text();

        if (attr.operation == "boolean") {
            attr.value1 = $(row).find(".value1").attr('checked') == 'checked' ? true : false;
        } else if (attr.operation == "select") {
            attr.value1 = $(row).find(".value1 option:selected").val();
        }
        else {
            attr.value1 = $(row).find(".value1").val();
        }
        var val2 = $(row).find(".value2");
        if (val2) {
            attr.value2 = val2.val();
        }

        return attr;
    },
    fillTable: function (beans, targetTableId) {
        $(targetTableId).empty();
        if (beans) {
            beans.forEach(function (data) {
                $(targetTableId).append(OPENIAM.Policy.Form.fillRow(data));
            });
        }
    },
    validate: function () {
        var valid = [];
        var res;
        $("#passwordCompositionAttributes").find("tr").each(function () {
            res = OPENIAM.Policy.Form.validateRow($(this));
            if (res) {
                valid.push(res)
            }
        });
        $("#passwordForgotAttributes").find("tr").each(function () {
            res = OPENIAM.Policy.Form.validateRow($(this));
            if (res) {
                valid.push(res)
            }
        });
        $("#changeRuleAttributes").find("tr").each(function () {
            res = OPENIAM.Policy.Form.validateRow($(this));
            if (res) {
                valid.push(res)
            }
        });
        return valid;
    },
    validateRow: function (row) {
        var errorTextValue = "";
        var fieldName = $(row).find("td.attribute-display-name").text();
        var req = $(row).find("td.attribute-is-enabled input[type='checkbox']").attr('checked') == 'checked' ? true : false;
        var operation = $(row).find("td.attribute-values").attr("attr-operation");
        if ("RANGE" == operation) {
            var value1 = $(row).find(".value1").val();
            var value2 = $(row).find(".value2").val();
            if (req) {
                if (!value1 && !value2) {
                    errorTextValue = localeManager["openiam.ui.password.policy.invalid.range"];
                }

                else {
                    if (value1)
                        var one = parseInt(value1);
                    if (value2)
                        var two = parseInt(value2);
                    if ((value1 && isNaN(one)) || (value2 && isNaN(two))) {
                        errorTextValue = localeManager["openiam.ui.password.policy.invalid.not.int"];
                    } else if (one && two && one != NaN && two != NaN && two < one) {
                        errorTextValue = localeManager["openiam.ui.password.policy.invalid.range.bad"];
                    }
                }
            }
        } else if ("select" == operation) {
            var val = $(row).find(".value1 option:selected").val();
            if (req && !val) {
                errorTextValue = localeManager["openiam.ui.password.policy.invalid.select"];
            }
        }
        if (errorTextValue != "") {
            return fieldName + ":" + errorTextValue;
        }
        return null;
    },
    fillRow: function (data) {
        var row = document.createElement("tr");
        $(row).attr("attr-grouping", data.grouping);
        //Create checkbox
        var tdIsEnabled = document.createElement("td");
        tdIsEnabled.className = "attribute-is-enabled";
        var checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        if (data.required == true) {
            checkbox.checked = true;
        } else {
            checkbox.checked = false;
        }
        $(checkbox).on("change", function () {
            if (this.checked == true) {
                $(this).closest("tr").find(".attribute-values").css("visibility", "visible");
                $(this).closest("tr").find(".attribute-display-name").css("color", "#000");
            }
            else {
                $(this).closest("tr").find(".attribute-values").css("visibility", "hidden");
                $(this).closest("tr").find(".attribute-display-name").css("color", "#aaa");
            }

        });
        tdIsEnabled.appendChild(checkbox);
        row.appendChild(tdIsEnabled);


//Create discription
        var tdDesc = document.createElement("input");
        tdDesc.type = "hidden"
        tdDesc.className = "attribute-description";
        $(tdDesc).val(data.description);
        row.appendChild(tdDesc);

        var tdDisplayName = document.createElement("td");
        tdDisplayName.className = "attribute-display-name";
        $(tdDisplayName).text(localeManager["openiam.ui.password.policy." + data.name]);
        row.appendChild(tdDisplayName);

//Create name
        var tdName = document.createElement("input");
        tdName.type = "hidden"
        tdName.className = "attribute-name";
        $(tdName).val(data.name);
        row.appendChild(tdName);

        var defParamId = document.createElement("input");
        defParamId.type = "hidden"
        defParamId.className = "attribute-def-param-id";
        $(defParamId).val(data.defParamId);
        row.appendChild(defParamId);

        var attrId = document.createElement("input");
        attrId.type = "hidden"
        attrId.className = "attribute-id";
        $(attrId).val(data.id);
        row.appendChild(attrId);
//values
        var tdValues = document.createElement("td");
        tdValues.className = "attribute-values";
        $(tdValues).attr("attr-operation", data.operation);
//simple string
        if (!data.operation || data.operation == "String") {
            var input = document.createElement("input");
            input.type = "text";
            input.className = "value1 rounded";
            input.value = data.value1;
            tdValues.appendChild(input);
        } else if (data.operation == "RANGE") {
            var input1 = document.createElement("input");
            input1.type = "text";
            input1.value = data.value1;
            input1.className = "value1 pair  rounded";
            var from = document.createElement("span");
            from.innerHTML = localeManager["openiam.ui.password.policy.from"] + " ";
            tdValues.appendChild(from);
            tdValues.appendChild(input1);

            var input2 = document.createElement("input");
            input2.type = "text";
            input2.value = data.value2;
            input2.className = "value2 pair  rounded";
            var to = document.createElement("span");
            to.innerHTML = " " + localeManager["openiam.ui.password.policy.to"] + " ";
            tdValues.appendChild(to);
            tdValues.appendChild(input2);
        } else if (data.operation == "boolean") {
            var booleanChk = document.createElement("input");
            booleanChk.type = "checkbox";
            booleanChk.className = "value1";
            if (data.value1 == "true") {
                booleanChk.checked = true;
            } else {
                booleanChk.checked = false;
            }
            tdValues.appendChild(booleanChk);
            $(tdIsEnabled).css("visibility", "hidden");
        }
        else if (data.operation == "select") {
            var select = document.createElement("select");
            select.className = "value1  rounded";
            var defaultOption = document.createElement("option");
            defaultOption.value = "";
            defaultOption.text = localeManager["openiam.ui.common.please.select"];
            select.appendChild(defaultOption);
            if (data.value2) {
                var values = data.value2.split(",");
                if (values) {
                    for (var i = 0; i < values.length; i++) {
                        var opt = document.createElement("option");
                        opt.value = values[i];
                        opt.text = values[i];
                        if (opt.value == data.value1) {
                            opt.setAttribute("selected", "selected");
                        }
                        select.appendChild(opt);
                    }
                }
                var options = document.createElement("input");
                options.type = "hidden"
                options.className = "value2";
                $(options).val(data.value2);
                tdValues.appendChild(options);
            }
            tdValues.appendChild(select);
        }
        row.appendChild(tdValues);
        if (data.required != true) {
            $(tdValues).css("visibility", "hidden");
            $(tdDisplayName).css("color", "#aaa");
        }
        return row;
    },
    fillAttrubutes: function () {
        $.ajax({
            url: "getPasswordAttributes.html",
            data: {
                id: OPENIAM.ENV.PolicyId
            },
            type: "GET",
            dataType: "json",
            async: false,
            success: function (data, textStatus, jqXHR) {
                OPENIAM.Policy.Form.fillTable(data.passwordComposition, "#passwordCompositionAttributes");
                OPENIAM.Policy.Form.fillTable(data.passwordChangeRule, "#changeRuleAttributes");
                OPENIAM.Policy.Form.fillTable(data.forgotPassword, "#passwordForgotAttributes");
            },
            error: function (jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
    }
}
;

$(document).ready(function () {

    OPENIAM.Policy.Form.fillAttrubutes();

    $("#deletePolicy").click(function () {
        OPENIAM.Modal.Warn({
            message: localeManager["openiam.ui.webconsole.policy.want.delete.policy.warn"],
            buttons: true,
            OK: {
                text: localeManager["openiam.ui.webconsole.policy.want.delete.yes"],
                onClick: function () {
                    OPENIAM.Modal.Close();
                    OPENIAM.Policy.Form.deletePolicy();
                }
            },
            Cancel: {
                text: localeManager["openiam.ui.common.cancel"],
                onClick: function () {
                    OPENIAM.Modal.Close();
                }
            }
        });
        return false;
    });

    $("#saveBtn").on("click", function () {
        OPENIAM.Policy.Form.savePolicy();
        return false;
    });
});

$(window).load(function () {
});