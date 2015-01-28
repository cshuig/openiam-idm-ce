OPENIAM = window.OPENIAM || {};
OPENIAM.PolicyMap = window.OPENIAM.PolicyMap || {};

OPENIAM.PolicyMap.Form = {
    redirectUrl : null,
    size : 10,
    deleteAttributeMap : function() {
        $.ajax({
            isChanged : false,
            url : "delete-attribute-map.html",
            data : JSON.stringify(this.toAttMapListJSON('delete')),
            type : "POST",
            dataType : "json",
            contentType : "application/json",
            success : function(data, textStatus, jqXHR) {
                if (data.status == 200) {
                    OPENIAM.Modal.Success({
                        message : data.successMessage,
                        showInterval : 1000,
                        onIntervalClose : function() {
                            if (!OPENIAM.PolicyMap.Form.redirectUrl)
                                window.location.reload(true);
                        }
                    });
                } else {
                    OPENIAM.Modal.Error({
                        errorList : data.errorList
                    });
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
    },
    isSomthingChanged : function() {
        isChanged = ($('.for-save').size() > 0);
        return isChanged;
    },
    resetSomthingChanged : function() {
        $('.for-save').removeClass('for-save');
    },
    saveAttributeMap : function() {
        if (this.validate()) {
            $.ajax({
                url : "save-attribute-map.html",
                data : JSON.stringify(this.toAttMapListJSON('save')),
                type : "POST",
                dataType : "json",
                contentType : "application/json",
                success : function(data, textStatus, jqXHR) {
                    if (data.status == 200) {
                        isChanged = false;
                        OPENIAM.Modal.Success({
                            message : data.successMessage,
                            showInterval : 2000,
                            onIntervalClose : function() {
                                if (!OPENIAM.PolicyMap.Form.redirectUrl) {
                                    window.location.reload(true);
                                }
                            }
                        });
                    } else {
                        OPENIAM.Modal.Error({
                            errorList : data.errorList
                        });
                    }
                },
                error : function(jqXHR, textStatus, errorThrown) {
                    OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
                }
            });
        }
    },
    toAttMapListJSON : function(action) {
        var request = {};
        var attrMapArray = [];
        request.resourceId = $('#policyMapTable').attr('resourceId');
        request.resourseName = $('#policyMapTable').attr('resourceName');
        request.mangSysId = $('#policyMapTable').attr('mSysId');
        request.synchConfigId = $('#policyMapTable').attr('synchConfigId');
        request.synchConfigName = $('#policyMapTable').attr('synchConfigName');

        if (action && action == 'save') {
            $("tbody .for-save").each(function(index, value) {
                attrMapArray.push(OPENIAM.PolicyMap.Form.toAttrMapJSON($(value)));
            });
            $("tfoot .attr-map-row").each(function(index, value) {
                if (!$(this).hasClass('empty-row'))
                    attrMapArray.push(OPENIAM.PolicyMap.Form.toAttrMapJSON($(value)));
            });

        } else if (action && action == 'delete') {
            $("tbody .for-delete").each(function(index, value) {
                attrMapArray.push(OPENIAM.PolicyMap.Form.toAttrMapJSON($(value)));
            });
        }
        request.attrMapList = attrMapArray;
        return request;
    },
    toAttrMapJSON : function(row) {
        var obj = {};
        obj.reconResAttribute = {};
        obj.reconResAttribute.defaultAttributePolicy = {};
        obj.reconResAttribute.attributePolicy = {};
        obj.attributeMapId = row.attr("entityId");
        obj.reconResAttribute.reconciliationResourceAttributeMapId = row.attr("reconResId");
        obj.selected = row.find(".attr-map-selected").attr("checked") == "checked";
        obj.mapForObjectType = row.find(".attr-map-obj-type option:selected").val();

        if (row.find(".attr-map-name").size() > 0) {
            obj.attributeName = row.find(".attr-map-name").val();
        } else {
            obj.attributeName = row.find(".attr-map-name-list option:selected").val();
        }
        if (!row.find('.default-policy').hasClass('hide')) {
            obj.reconResAttribute.defaultAttributePolicy.defaultAttributeMapId = row.find('.default-policy option:selected').val();
            obj.reconResAttribute.attributePolicy = null;
        }
        if (!row.find('.custom-policy').hasClass('hide')) {
            obj.reconResAttribute.defaultAttributePolicy = null;
            obj.reconResAttribute.attributePolicy.policyId = row.find('.custom-policy option:selected').val();
        }
        obj.dataType = row.find(".attr-map-date-type option:selected").val();
        obj.defaultValue = row.find(".attr-map-def-value").val();
        obj.status = row.find(".attr-map-status option:selected").val();
        return obj;
    },
    manageVisibility : function(select) {
        var parent = $(select).closest('tr');
        var defPolicy = $(parent).find('.default-policy');
        var cusPolicy = $(parent).find('.custom-policy');
        $(defPolicy).addClass('hide');
        $(cusPolicy).addClass('hide');
        $(defPolicy).removeClass('show');
        $(cusPolicy).removeClass('show');
        $(defPolicy).removeClass('changed-input');
        $(cusPolicy).removeClass('changed-input');
        if ($(select).find('option:selected').val() == 'POLICY') {
            $(cusPolicy).addClass('show');
            $(cusPolicy).removeClass('hide');
            $($(parent).find('.default-policy option')[0]).prop('selected', 'selected');
            $(cusPolicy).change();
        } else {
            $(defPolicy).addClass('show');
            $(defPolicy).removeClass('hide');
            $($(parent).find('.custom-policy option')[0]).prop('selected', 'selected');
            $(defPolicy).change();
        }
    },
    markForSave : function(item) {
        if ($(item).closest('tr').find('.changed-input').size() == 0) {
            $(item).closest('tr').removeClass('for-save');
        } else {
            $(item).closest('tr').addClass('for-save');
        }
    },
    stopEventPropagation : function(event) {
        if (event.stopPropagation) {
            event.stopPropagation(); // W3C model
        } else {
            event.cancelBubble = true; // IE model
        }
        return false;
    },
    confirmationSave : function(event) {
        if (OPENIAM.PolicyMap.Form.isSomthingChanged()) {
            OPENIAM.PolicyMap.Form.redirectUrl = document.location.href;
            OPENIAM.Modal.Warn({
                message : localeManager["openiam.ui.resource.policymap.save.confirmation"],
                buttons : true,
                OK : {
                    text : localeManager["openiam.ui.button.ok"],
                    onClick : function() {
                        OPENIAM.Modal.Close();
                        $("#policyMapForm").submit();
                        window.location.href = href;
                    }
                },
                Cancel : {
                    text : localeManager["openiam.ui.common.cancel"],
                    onClick : function() {
                        OPENIAM.Modal.Close();
                    }
                },
                No : {
                    text : localeManager["openiam.ui.common.no"],
                    onClick : function() {
                        OPENIAM.PolicyMap.Form.resetSomthingChanged();
                        OPENIAM.Modal.Close();
                        window.location.href = href;
                    }
                }
            });
        }
    },
    validate : function() {
        $('.attr-map-row').removeClass('invalid');
        $('.attr-map-row').removeClass('empty-row');
        $('.attr-map-row').each(
                function() {
                    var attrName = '';
                    if ($(this).find(".attr-map-name").size() > 0) {
                        attrName = $(this).find(".attr-map-name").val();
                    } else {
                        attrName = $(this).find(".attr-map-name-list option:selected").val();
                    }
                    attrName = attrName == '' ? null : attrName;
                    var isPolicyCorrect = (!$(this).find('.default-policy').hasClass('hide') && $(this).find('.default-policy option:selected').val() != '')
                            || (!$(this).find('.custom-policy').hasClass('hide') && $(this).find('.custom-policy option:selected').val() != '');
                    if (!((attrName && isPolicyCorrect) || (!attrName && !isPolicyCorrect))) {
                        $(this).addClass('invalid');
                    }
                    if (!attrName && !isPolicyCorrect)
                        $(this).addClass('empty-row');
                });
        return !$('.attr-map-row').hasClass('invalid');
    },
    binding : function() {
        // apply changes for inputs
        $('input').on('blur', function() {
            if ($(this).data('initialValue') == $(this).val()) {
                $(this).removeClass('changed-input');
            } else {
                $(this).addClass('changed-input');
            }
            OPENIAM.PolicyMap.Form.markForSave(this);
            $(this).attr('value', $(this).val());
            $("#policyMapTable").trigger("update");
        });

        $('select').on('change', function() {
            if ($(this).data('initialValue') == $(this).find('option:selected').text()) {
                $(this).removeClass('changed-input');
            } else {
                $(this).addClass('changed-input');
            }
            OPENIAM.PolicyMap.Form.markForSave(this);
            if ($(this).hasClass('attr-map-policy-type')) {
                OPENIAM.PolicyMap.Form.manageVisibility(this);
            }
        });

        $('.attr-map-selected').on('click', function() {
            if ($(this).attr('checked') == 'checked') {
                $(this).closest('tr').addClass('for-delete');
            } else {
                $(this).closest('tr').removeClass('for-delete');
            }
        });
        // init visibility of complex policy fields
        $('tbody .attr-map-policy-type').each(function() {
            var parent = $(this).closest('tr');
            if (!$(parent).find('.custom-policy').hasClass('hide')) {
                $($(this).find('option')[0]).prop('selected', 'selected');
            } else {
                $($(this).find('option')[1]).prop('selected', 'selected');
            }
        });
        // onchange policy type event
        $('.attr-map-policy-type').on('change', function() {
            OPENIAM.PolicyMap.Form.manageVisibility(this);

        });
    }
};
$(window).load(function() {
    OPENIAM.PolicyMap.Form.binding();
    if ($("#policyMapTable tbody tr.attr-map-row").size() > 1)
        $("#policyMapTable").tablesorter({
            sortList : [ [ 0, 0 ] ],
            headers : {
                2 : {
                    sorter : false
                },
                3 : {
                    sorter : false
                },
                4 : {
                    sorter : false
                },
                5 : {
                    sorter : false
                },
                7 : {
                    sorter : false
                }
            }
        });

    // delete selected rows event
    $("#deleteSelected").click(function() {
        OPENIAM.Modal.Warn({
            message : OPENIAM.ENV.Text.DeleteWarn,
            buttons : true,
            OK : {
                text : localeManager["openiam.ui.resource.policymap.delete.attributemap.confirm"],
                onClick : function() {
                    OPENIAM.Modal.Close();
                    OPENIAM.PolicyMap.Form.deleteAttributeMap();
                }
            },
            Cancel : {
                text : localeManager["openiam.ui.common.cancel"],
                onClick : function() {
                    OPENIAM.Modal.Close();
                }
            }
        });
        return false;
    });
    $('#addRow').on('click', function() {
        var $clone = $('tfoot tr.attr-map-row:first').clone();
        $clone.find('input').val('');
        $clone.insertAfter('tfoot tr.attr-map-row:last');
        $('.delete-new-row').on('click', function() {
            if ($('tfoot tr.attr-map-row').size() > 1)
                $(this).closest('tr').remove();
        });
        OPENIAM.PolicyMap.Form.binding();
    });

    $('.delete-new-row').on('click', function() {
        if ($('tfoot tr.attr-map-row').size() > 1)
            $(this).closest('tr').remove();
    });
    // save event
    $("#policyMapForm").submit(function() {
        if ($('.error-input').size() == 0) {
            $(window).unbind("beforeunload");
            OPENIAM.PolicyMap.Form.saveAttributeMap();
        }
        return false;
    });

    // set initialValues for inputs
    $('input').each(function() {
        $(this).data('initialValue', $(this).val());
    });

    // set initialValues for selects
    $('select').each(function() {
        $(this).data('initialValue', $(this).find('option:selected').text());
    });
});
