OPENIAM = window.OPENIAM || {};
OPENIAM.BulkChooseOperations = {

    MetaData : {},

    init : function() {

        var chooseObjectRow = $("#formContainer #chooseObjectRow")[0];
        $("#objectType").change(function() {
            OPENIAM.BulkChooseOperations.showOperation('', false);
            $(chooseObjectRow).nextAll().remove();

            var objType = $(this).val();
            if (objType && objType != 'USER') {
                var objectIdRow = document.createElement("tr");
                objectIdRow.id="objectIdRow";
                var objectIdRowC1 = objectIdRow.insertCell(0);
                objectIdRowC1.innerHTML = '<label for="objectName">Choose Object Name</label>';
                var objectIdRowC2 = objectIdRow.insertCell(1);

                if (objType == 'GROUP') {
                    objectIdRowC2.innerHTML = ''
                        +'<input id="groupId" type="hidden" value="" name="objectId" autocomplete="off">'
                        +'<input id="groupName" type="text" value="" readonly="readonly" name="objectName" autocomplete="off">'
                        +'<a id="searchGroup" class="icon-link search16" href="javascript:void(0);"></a>'
                        +'<a id="clearGroup" class="icon-link delete16" href="javascript:void(0);"></a>';

                } else if (objType == 'ROLE') {
                    objectIdRowC2.innerHTML = ''
                        +'<input id="roleId" type="hidden" value="" name="objectId" autocomplete="off">'
                        +'<input id="roleName" type="text" value="" readonly="readonly" name="objectName" autocomplete="off">'
                        +'<a id="searchRole" class="icon-link search16" href="javascript:void(0);"></a>'
                        +'<a id="clearRole" class="icon-link delete16" href="javascript:void(0);"></a>';

                } else if (objType == 'RESOURCE') {
                    objectIdRowC2.innerHTML = ''
                        +'<input id="resourceId" type="hidden" value="" name="objectId" autocomplete="off">'
                        +'<input id="resourceName" type="text" value="" readonly="readonly" name="objectName" autocomplete="off">'
                        +'<a id="searchResource" class="icon-link search16" href="javascript:void(0);"></a>'
                        +'<a id="clearResource" class="icon-link delete16" href="javascript:void(0);"></a>';

                } else if (objType == 'ORGANIZATION') {
                    objectIdRowC2.innerHTML = ''
                        +'<input id="organizationId" type="hidden" value="" name="objectId" autocomplete="off">'
                        +'<input id="organizationName" type="text" value="" readonly="readonly" name="objectName" autocomplete="off">'
                        +'<a id="searchOrganization" class="icon-link search16" href="javascript:void(0);"></a>'
                        +'<a id="clearOrganization" class="icon-link delete16" href="javascript:void(0);"></a>';
                }

                $(objectIdRow).insertAfter(chooseObjectRow);

                if (objType == 'GROUP') {
                    OPENIAM.BulkOperationsSearchGroup.init();
                } else if (objType == 'ROLE') {
                    OPENIAM.BulkOperationsSearchRole.init();
                } else if (objType == 'RESOURCE') {
                    OPENIAM.BulkOperationsSearchResource.init();
                } else if (objType == 'ORGANIZATION') {
                    OPENIAM.BulkOperationsSearchOrganization.init();
                }

                $("[name='objectId']").on('change', function() {
                    OPENIAM.BulkChooseOperations.showOperation('', false);
                    var isDelete = ($(this).val() === "");
                    OPENIAM.BulkChooseOperations.updateOperations(objType, isDelete);
                });

            } else {
                OPENIAM.BulkChooseOperations.updateOperations(objType, false);
            }

        });
    },

    updateOperations : function(objType, isDelete) {
        var baseRow = (objType == 'USER') ? $("#formContainer #chooseObjectRow")[0] : $("#formContainer #objectIdRow")[0];
        $(baseRow).nextAll().remove();
        if (!isDelete) {
            var operationRow = document.createElement("tr");
            operationRow.id="operationRow";
            var operationRowC1 = operationRow.insertCell(0);
            operationRowC1.innerHTML = '<label for="operation">'+localeManager["openiam.ui.challenge.response.choose.operation"]+'</label>';
            var operationRowC2 = operationRow.insertCell(1);

            if (OPENIAM.ENV.ObjectOperations) {

                var operationBeans = OPENIAM.ENV.ObjectOperations;
                var operationSelect = document.createElement("select");
                operationSelect.id = "operation";
                operationSelect.name = "operation";
                operationSelect.className = "ctrlElement";
                var option = document.createElement("option");
                option.value = '';
                option.text = localeManager["openiam.ui.common.please.select"];
                operationSelect.appendChild(option);

                var operations = operationBeans[objType];
                $.each(operations, function(k, v) {
                    var option = document.createElement("option");
                    option.value = k;
                    option.text = v;
                    operationSelect.appendChild(option);
                });

                operationRowC2.appendChild(operationSelect);

                $(operationSelect).change(function() {
                    if ($(this).val()) {
                        OPENIAM.BulkChooseOperations.showOperation($(this).val(), true);
                    } else {
                        OPENIAM.BulkChooseOperations.showOperation($(this).val(), false);
                    }
                });

            }

            $(operationRow).insertAfter(baseRow);
        }
    },

    showOperation : function(op, isShow) {
        var baseRow = $("#formContainer #operationRow")[0];
        $(baseRow).nextAll().remove();
        if (isShow) {
            if (op === 'RESET_USER_PASSWORD') {

                // password
                var bulkOpPasswordRow = document.createElement("tr");
                bulkOpPasswordRow.className ="bulkOpPasswordRow";
                var bulkOpPasswordRowC1 = bulkOpPasswordRow.insertCell(0);
                bulkOpPasswordRowC1.innerHTML = '<label class="required" for="password">'+localeManager["openiam.ui.user.password"]+'</label>';
                var bulkOpPasswordRowC2 = bulkOpPasswordRow.insertCell(1);
                bulkOpPasswordRowC2.innerHTML = '<input type="password" autocomplete="off" class="full rounded" name="password" id="password" />';

                // confirm password
                var bulkOpConfirmPasswordRow = document.createElement("tr");
                bulkOpConfirmPasswordRow.className ="bulkOpPasswordRow";
                var bulkOpConfirmPasswordRowC1 = bulkOpConfirmPasswordRow.insertCell(0);
                bulkOpConfirmPasswordRowC1.innerHTML = '<label class="required" for="confirmPassword">'+localeManager["openiam.ui.user.password.confirm"]+'</label>';
                var bulkOpConfirmPasswordRowC2 = bulkOpConfirmPasswordRow.insertCell(1);
                bulkOpConfirmPasswordRowC2.innerHTML = '<input type="password" autocomplete="off" class="full rounded" name="confirmPassword" id="confirmPassword" />';

                // send password
                var bulkOpSendPassRow = document.createElement("tr");
                var bulkOpSendPassRowC1 = bulkOpSendPassRow.insertCell(0);
                bulkOpSendPassRowC1.colSpan = 2;
                bulkOpSendPassRowC1.innerHTML = '<input type="checkbox" name="notifyUserViaEmail" id="notifyUserViaEmail" checked="checked" value="true" />';
                bulkOpSendPassRowC1.innerHTML += '<label for="notifyUserViaEmail"><i>'+localeManager["openiam.ui.user.password.send.to.user"]+'</i></label>';

                // autogen password
                var bulkOpAutoPassRow = document.createElement("tr");
                var bulkOpAutoPassRowC1 = bulkOpAutoPassRow.insertCell(0);
                bulkOpAutoPassRowC1.colSpan = 2;
                var autoGeneratePassword = document.createElement("input");
                autoGeneratePassword.value = "true";
                autoGeneratePassword.id = "autoGeneratePassword";
                autoGeneratePassword.name = "autoGeneratePassword";
                autoGeneratePassword.type = "checkbox";
                // autoGeneratePassword.onClick = OPENIAM.BulkOperationsSearchResource.showHidePasswordLines;

                bulkOpAutoPassRowC1.appendChild(autoGeneratePassword);
                bulkOpAutoPassRowC1.innerHTML += '<label for="autoGeneratePassword"><i>'+localeManager["openiam.ui.user.password.auto.generate"]+'</i></label>';

                $(bulkOpAutoPassRow).insertAfter(baseRow);
                $(bulkOpSendPassRow).insertAfter(baseRow);
                $(bulkOpConfirmPasswordRow).insertAfter(baseRow);
                $(bulkOpPasswordRow).insertAfter(baseRow);

                $("#formContainer #autoGeneratePassword").click(function() {
                    var checked = $(this).is(':checked');
                    var passwordRows = $("#formContainer .bulkOpPasswordRow");
                    $("#formContainer #password").val('');
                    $("#formContainer #confirmPassword").val('');
                    checked ? $(passwordRows).hide() : $(passwordRows).show();
                });

            } else if (op === 'NOTIFY_USER') {
                // subject
                var bulkOpSubjectRow = document.createElement("tr");
                var bulkOpSubjectRowC1 = bulkOpSubjectRow.insertCell(0);
                bulkOpSubjectRowC1.innerHTML = '<label for="subject">'+localeManager["openiam.ui.user.notification.subject"]+'</label>';
                var bulkOpSubjectRowC2 = bulkOpSubjectRow.insertCell(1);
                bulkOpSubjectRowC2.innerHTML = '<input type="text" autocomplete="off" class="full rounded" name="subject" id="subject" />';

                //text
                var bulkOpTextRow = document.createElement("tr");
                var bulkOpTextRowC1 = bulkOpTextRow.insertCell(0);
                bulkOpTextRowC1.colSpan = 2;
                bulkOpTextRowC1.innerHTML = '<textarea placeholder="' + localeManager["openiam.ui.user.notification.text.placeholder"] + '" style="width: 400px; height: 200px;" class="full rounded _input_tiptip" name="text" id="text" />';

                $(bulkOpTextRow).insertAfter(baseRow);
                $(bulkOpSubjectRow).insertAfter(baseRow);

            }
            $("#addOperation").show();
        } else {
            $("#addOperation").hide();
        }
    }

};

$(document).ready(function() {
    OPENIAM.BulkChooseOperations.init();

    $("#selectAll").click(function() {
        var checked = $(this).is(':checked');
        $("#resultsArea [name='operationIds']").prop('checked', checked);
    });

});

$(document).load(function() {
});
