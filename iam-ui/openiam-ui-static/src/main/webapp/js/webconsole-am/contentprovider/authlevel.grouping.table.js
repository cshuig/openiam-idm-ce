console = window.console || {};
console.log = window.console.log || function() {
};

(function($) {

    var AJAX_URL = "rest/api/federation/authGroupings";

    var privateMethods = {
        request : function() {
            var $this = this;
            var options = $this.data("options");
            $.ajax({
                "url" : AJAX_URL,
                type : "GET",
                dataType : "json",
                success : function(data, textStatus, jqXHR) {
                    options.data = data.beans;
                    if (options.data != null) {
                        $.each(options.data, function(idx, dataGrouping) {
                            $.each(options.internalGroupings, function(idx2, grouping) {
                                if (grouping.groupingId == dataGrouping.id) {
                                    grouping.name = dataGrouping.name;
                                    grouping.authLevelName = dataGrouping.authLevelName;
                                }
                            });
                        });
                    }
                    privateMethods.draw.call($this);
                },
                error : function(jqXHR, textStatus, errorThrown) {
                    onError(onErrorText);
                }
            });
        },
        draw : function() {
            var $this = this;
            var options = $this.data("options");
            this.persistentTable({
                emptyMessage : options.emptyMessage,
                createEnabled : true,
                objectArray : options.internalGroupings,
                createText : localeManager["authlevel.grouping.table.add.auth.level"],
                headerFields : [ localeManager["openiam.ui.common.name"], localeManager["authlevel.grouping.table.auth.level"] ],
                fieldNames : [ "name", "authLevelName" ],
                deleteEnabledField : true,
                editEnabledField : false,
                actionsColumnName : localeManager["openiam.ui.common.actions"],
                sortEnabled : true,
                equals : function(obj1, obj2) {
                    return (obj1.groupingId == obj2.groupingId);
                },
                onDeleteClick : function(obj) {
                    console.log(obj);
                },
                onEditClick : function(obj) {
                    $.error(localeManager["authlevel.grouping.table.edit.not.enabled"]);
                },
                onCreateClick : function() {

                    var select = $(document.createElement("select"));
                    select.append("<option>" + localeManager["openiam.ui.common.value.select"] + "</option>");
                    $.each(options.data, function(idx, grouping) {
                        var option = $(document.createElement("option")).val(grouping.id).text(grouping.name).data("grouping", grouping);

                        $.each(options.internalGroupings, function(internalIdx, internalGrouping) {
                            if (internalGrouping.groupingId == grouping.id) {
                                option.hide();
                            }
                        });
                        select.append(option);
                    });

                    var dialogBody = $(document.createElement("div"));

                    var buttonBody = $(document.createElement("div"));
                    var saveLevel = $(document.createElement("button")).addClass("redBtn").text("Save");
                    buttonBody.append(saveLevel);

                    dialogBody.append(select, buttonBody);
                    $("#editDialog").html(dialogBody).dialog({
                        autoOpen : true,
                        draggable : false,
                        resizable : false,
                        title : localeManager["authlevel.grouping.table.select.auth.level"],
                        width : "auto",
                        maxWidth : 600
                    });
                    var $this = this;
                    saveLevel.click(function() {
                        var grouping = select.find("option:selected").data("grouping");
                        if (grouping != null && grouping != undefined) {
                            console.log(grouping);
                            var obj = {
                                groupingId : grouping.id,
                                authLevelName : grouping.authLevelName,
                                name : grouping.name
                            };

                            $this.persistentTable("addObject", obj);
                            $this.persistentTable("draw");
                            $this.persistentTable("onOrderEdit");
                            $("#editDialog").dialog("close");
                            return false;
                        }
                    });
                },
                onOrderEdit : function(idx, obj) {
                    obj.order = idx;
                }
            });
        }
    };

    var methods = {
        getValues : function() {
            var $this = this;
            var options = $this.data("authLevelOptions");
            var values = [];
            $.each(options.internalGroupings, function(idx, value) {
                values.push({
                    id : {
                        groupingId : value.groupingId
                    },
                    order : value.order
                });
            })
            return values;
        },
        init : function(args) {
            var $this = this;
            var options = $.extend({
                groupings : [],
                emptyMessage : localeManager["authlevel.grouping.table.level.must.select"]
            }, args);

            if (options.groupings == null) {
                options.groupings = [];
            }

            options.internalGroupings = [];

            $.each(options.groupings, function(idx, grouping) {
                options.internalGroupings.push({
                    groupingId : grouping.id.groupingId,
                    order : grouping.order
                });
            });

            $this.data("options", options);
            $this.data("authLevelOptions", options); // persistent tabl will
            // override 'options'.
            // This is just a copy
            privateMethods.request.call($this);
        }
    };

    $.fn.authenticationLevelTable = function(method) {
        if (methods[method]) {
            return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === 'object' || !method) {
            return methods.init.apply(this, arguments);
        } else {
            $.error(localeManager['openiam.ui.common.method'] + ' ' + method + ' ' + localeManager['authlevel.grouping.table.not.exists.in.authLevelTable']);
        }
    };
})(jQuery);