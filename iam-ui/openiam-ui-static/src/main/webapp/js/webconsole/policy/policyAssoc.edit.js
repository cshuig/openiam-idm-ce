OPENIAM = window.OPENIAM || {};
OPENIAM.Policy = window.OPENIAM.Policy || {};

OPENIAM.Policy.Form = {
    init: function () {

        $("#userRoleId").selectableSearchResult({
            singleSearch: true,
            noneSelectedText: localeManager["openiam.ui.shared.role.search"],
            addMoreText: localeManager["openiam.ui.common.role.add.another"],
            onClick: function ($that) {
                $("#editDialog").roleDialogSearch({
                    searchTargetElmt: "#editDialog",
                    showResultsInDialog: true,
                    onAdd: function (bean) {
                        OPENIAM.Policy.Form.addBean("#roleContainer", bean);
                        $("#editDialog").dialog("close");
                    },
                    pageSize: 5
                });
            }
        });
        $("#userOrgId").selectableSearchResult({
            singleSearch: true,
            noneSelectedText: localeManager["openiam.ui.shared.organization.search"],
            addMoreText: localeManager["openiam.ui.common.organization.add.another"],
            onClick: function ($that) {
                $("#editDialog").organizationDialogSearch({
                    showResultsInDialog: true,
                    searchTargetElmt: "#editDialog",
                    onAdd: function (bean) {
                        OPENIAM.Policy.Form.addBean("#orgContainer", bean);
                        $("#editDialog").dialog("close");
                    },
                    pageSize: 5
                });
            }
        });
        OPENIAM.Policy.Form.doVisibility();
        OPENIAM.Policy.Form.draw();
    },
    addBean: function (targetContainer, bean) {
        if (bean) {
            $(targetContainer).empty();
            var li = document.createElement("li");
            $(li).addClass("choice").data("entity", bean);

            var sp = document.createElement("span");
            sp.innerHTML = bean.name;

            var a = document.createElement("a");
            a.className = "choice-close ui-icon ui-icon-closethick";
            a.href = "javascript:void(0)";

            $(a).click(function () {
                var container = $(this).parents("ul:first");
                $(this).parent("li:first").remove();

                if (container.children("li").length == 0)
                    container.css("display", "none");
            });

            $(li).append(sp, a);
            $(targetContainer).append(li);

            if (!$(targetContainer).is(':visible')) {
                $(targetContainer).css("display", "block");
            }
        }
    },


    saveAssocPolicy: function () {
        var result = OPENIAM.Policy.Form.validate();
        if (result == null) {
            $.ajax({
                url: "savePolicyAssociation.html",
                data: JSON.stringify(this.toPolicyJSON()),
                type: "POST",
                dataType: "json",
                contentType: "application/json",
                success: function (data, textStatus, jqXHR) {
                    //alert('success');
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
            OPENIAM.Modal.Error(result);
        }
    },

    draw: function () {
        if ($("#associationLevel").val() == "ROLE") {
            OPENIAM.Policy.Form.addBean("#roleContainer", OPENIAM.ENV.ROLE);
        }
        if ($("#associationLevel").val() == "ORGANIZATION") {
            OPENIAM.Policy.Form.addBean("#orgContainer", OPENIAM.ENV.ORGANIZATION);
        }
    },
    doVisibility: function () {
        if ($("#associationLevel").val() == "GLOBAL") {
            $("#assocValueTr").hide();
        } else {
            $("#assocValueTr").show();
            $("#organizationTd").hide();
            $("#roleTd").hide();
            if ($("#associationLevel").val() == "ROLE") {
                $("#roleTd").show();
            }
            if ($("#associationLevel").val() == "ORGANIZATION") {
                $("#organizationTd").show();
            }
        }
    },
    getSelectedItemsValue: function (option) {
        var value = null;
        if (option != null) {
            var bean = $(option).data("entity");
            if (bean != null && bean != undefined)
                value = bean.id;
        }
        return value;
    },
    validate: function () {

        var obj = {};
        obj.associationLevel = $("#associationLevel").val();
        if (obj.associationLevel != "GLOBAL") {
            if (obj.associationLevel == "ROLE") {
                obj.associationValue = OPENIAM.Policy.Form.getSelectedItemsValue("#roleContainer li");
            } else if (obj.associationLevel == "ORGANIZATION") {
                obj.associationValue = OPENIAM.Policy.Form.getSelectedItemsValue("#orgContainer li");
            }
            if (!obj.associationValue) {
                return localeManager["openiam.ui.webconsole.policy.password.association.no.value"];
            } else {
                return null;
            }
        }
        return null;
    },
    toPolicyJSON: function () {

        var obj = {};

        obj.policyId = $("#policyId").val();
        obj.policyObjectId = $("#policyObjectId").val();
        obj.associationLevel = $("#associationLevel").val();
        if (obj.associationLevel == "GLOBAL") {
            obj.associationValue = "GLOBAL";
        } else if (obj.associationLevel == "ROLE") {
            obj.associationValue = OPENIAM.Policy.Form.getSelectedItemsValue("#roleContainer li");
            obj.objectType = "ROLE";
            obj.objectId = OPENIAM.Policy.Form.getSelectedItemsValue("#roleContainer li");
        } else if (obj.associationLevel == "ORGANIZATION") {
            obj.associationValue = OPENIAM.Policy.Form.getSelectedItemsValue("#orgContainer li");
            obj.objectType = "ORGANIZATION";
            obj.objectId = OPENIAM.Policy.Form.getSelectedItemsValue("#orgContainer li");
        }

        return obj;
    }
}
;

$(document).ready(function () {
    OPENIAM.Policy.Form.init();
    $("#associationLevel").on("change", function () {
        OPENIAM.Policy.Form.doVisibility();
    });
    $("#associateform").submit(function () {
        OPENIAM.Policy.Form.saveAssocPolicy();
        return false;
    });

});

$(window).load(function () {
});