$(document).ready(function() {

    $(".editBtn").click(function() {
        var attrs = OPENIAM.ENV.IDMAttrs;
        var mngSysAttrsMap = OPENIAM.ENV.MngSysAttrs;
        var $idmTD = $(this).parents("tr:first").find('.IDMAttr');
        var name = $(this).attr("entityId");
        var entity = attrs[name];
        var srcEntity = mngSysAttrsMap[name];
        if (entity) {
            var modalFields = [
                {fieldName: "attrValue", type:"text", label: "Value: ", required : false}
//                        {fieldName: "manualUpdate", type:"checkbox", label: "Manual Update: ", required : false}
            ]

            $("#editDialog").modalEdit({
                fields: modalFields,
                dialogTitle: entity.name,
                onSubmit: function(bean){
                    var val = bean["attrValue"];
                    if (val != entity.value) {
                        entity.value = val;
                        $idmTD.text((val!=null)? val : '');
                        if (srcEntity.value != val) {
                            $idmTD.css({'font-weight': 'bold'});
                        } else {
                            $idmTD.css({'font-weight': ''});
                        }
                    }

                    $("#editDialog").modalEdit("hide");
                },
                onShown : function() {
                    if (entity.value) {
                        $("#attrValue").val(entity.value);
                    }
                }
            });
            $("#editDialog").modalEdit("show", entity);

        } else {
            OPENIAM.Modal.Warn({
                message : localeManager["openiam.ui.user.attr.not.support"],
                buttons : true,
                OK : {
                    text : localeManager["openiam.ui.button.ok"],
                    onClick : function() {
                        OPENIAM.Modal.Close();
                    }
                }
            });
        }
    });

    $("#saveBtn").click(function() {

        var data = {};
        data["exist"] = OPENIAM.ENV.MngSysUserExists;
        data["userId"] = OPENIAM.ENV.UserId;
        data["principalId"] = OPENIAM.ENV.PrincipalId;
        data["idmAttrsMap"] = OPENIAM.ENV.IDMAttrs;
        data["mngSysAttrsMap"] = OPENIAM.ENV.MngSysAttrs;

        $.ajax({
            url : "/webconsole/updateMngSysAttrs.html",
            data : JSON.stringify(data),
            type: "POST",
            dataType : "json",
            contentType: "application/json",
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
    });
});