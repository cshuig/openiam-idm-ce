console = window.console || {};
console.log = window.console.log || function() {};

OPENIAM = window.OPENIAM || {};

OPENIAM.ReportParameters = window.OPENIAM.ReportParameters || {};

OPENIAM.ReportParameters = {

    onReady : function() {
        OPENIAM.ReportParameters.Dictionary.load();
        OPENIAM.ReportParameters.Group.load();
        OPENIAM.ReportParameters.Roles.load();
        OPENIAM.ReportParameters.Resources.load();
        OPENIAM.ReportParameters.Organizations.load();
        OPENIAM.ReportParameters.Users.load();
        OPENIAM.ReportParameters.Supervisor.load();
        OPENIAM.ReportParameters.Action.load();
        OPENIAM.ReportParameters.BaseInput.load();
        OPENIAM.ReportParameters.DatePicker.load();
        OPENIAM.ReportParameters.ParametersContainer.load();
    },

    onView : function() {
        $.ajax({
            url : "viewReport.html",
            data : JSON.stringify(OPENIAM.ReportParameters.ParametersContainer.toJSON()),
            type : "POST",
            dataType : "json",
            contentType : "application/json",
            success : function(data, textStatus, jqXHR) {
                if (data.status == 200) {
                    var url = data.redirectURL.replace(/&amp;/g, '&');
                    window.open(url, '_blank');
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

    Dictionary : {

        parameterInfo : {},

        load : function() {
            var that = this;
            $.each(OPENIAM.ENV.ReportBean.reportParams, function() {
                that.parameterInfo[this.id] = this;
            });
        },

        get : function(id) {
            return this.parameterInfo[id];
        }
    },

    ParametersContainer : {

        // var parameterBean = {caption : '', id : '', paramName : '', value : '', valueText : ''}
        beans : [],

        getData : function(args) {
            var size = this.beans.length;
            var beans = this.beans.slice(args.from, args.from+args.size);
            return {size : size, beans : beans};
        },

        add : function(id, bean) {
            var parameterInfo = OPENIAM.ReportParameters.Dictionary.get(id);
            var parameterBean = {
                caption : parameterInfo.caption,
                id : id,
                paramName : parameterInfo.name,
                value : bean.id,
                valueText : bean.name
            };

            if (!this.contains(parameterBean, parameterInfo.isMultiple)) {
                this.beans.push(parameterBean);
                this.load();
            } else {
                if (parameterInfo.isMultiple) {
                    setTimeout(function() {
                        OPENIAM.Modal.Error(localeManager["openiam.ui.report.parameter.duplicate"]
                            .replace("{0}", parameterInfo.caption));
                    }, 1);
                } else {
                    setTimeout(function() {
                        OPENIAM.Modal.Error(localeManager["openiam.ui.report.parameter.not.multiple"]
                            .replace("{0}", parameterInfo.caption));
                    }, 1);
                }
            }
        },

        findByName : function(name) {
            for(var i = this.beans.length - 1; i>=0; i--) {
                if(this.beans[i].paramName === name) {
                    return this.beans[i];
                }
            }
            return null;
        },

        contains : function(bean, multiple) {
            for(var i = this.beans.length - 1; i>=0; i--) {
                if(multiple ? this.equals(this.beans[i], bean)
                            : this.beans[i].id == bean.id) {
                    return true;
                }
            }
            return false;
        },

        equals : function(b1, b2) {
            return b1.value == b2.value && b1.id == b2.id;
        },

        remove : function(bean) {
            var position = $.inArray(bean, this.beans);
            if (position >= 0) {
                this.beans.splice(position, 1);
                this.load();
            }
        },

        load : function() {
            var that = this;
            $("#parametersContainer").entitlemetnsTable({
                columnHeaders : [
                    localeManager["openiam.ui.report.parameter.name"],
                    localeManager["openiam.ui.report.parameter.value"],
                    localeManager["openiam.ui.common.actions"]
                ],
                columnsMap : ["caption", "valueText"],
                entityType : "REPORT_PARAMETER",
                deleteOptions : {
                	onDelete : function(bean) {
	                    that.remove(bean);
	                }
                },
                emptyResultsText : localeManager["openiam.ui.report.parameters.not.added"],
                theadInputElements : ["", "", that.getButton()],
                getData : function(args) {
                    return that.getData(args);
                }
            });
            OPENIAM.ReportParameters.DatePicker.fix();
        },

        getButton : function() {
            var mySearch = $(document.createElement("input"));
            mySearch.attr("type", "submit").attr("value", localeManager["openiam.ui.report.view.btn"]).addClass("redBtn").attr("id", "viewBtn");
            mySearch.click(function() {
                OPENIAM.ReportParameters.onView();
            });
            return mySearch;
        },

        toJSON : function() {
            var reportParams = [];
            $.each(this.beans, function() {
                reportParams.push({
                    name : OPENIAM.ReportParameters.Dictionary.get(this.id).name,
                    value : this.value
                });
            });
            return {
                reportName : OPENIAM.ENV.ReportName,
                reportParams : reportParams
            };
        }
    },

    Common : {
        load : function(args) {
            var that = this;
            $(args.buttonSelector).each(function() {
                var submit = $(this);
                var paramId = submit.attr("id");
                var input = $("#input" + paramId);
                var getRequestParameters = null;
                var parameterInfo = OPENIAM.ReportParameters.Dictionary.get(paramId);
                if (parameterInfo && parameterInfo.requestParameters) {
                    getRequestParameters = function() {
                        return that.parse(parameterInfo.requestParameters);
                    }
                }
                input.modalSearch({
                    onElementClick : function(bean) {
                        OPENIAM.ReportParameters.ParametersContainer.add(paramId, bean);
                        input.val("");
                    },
                    ajaxURL : args.modalAjaxURL,
                    dialogTitle : args.dialogTitle,
                    emptyResultsText : args.emptyResultsText,
                    getAdditionalDataRequestObject : getRequestParameters,
                    pageSize : 20,
                    width : 600
                });
                input.keyup(function(e) {
                    if(e.keyCode == 13) {
                        submit.click();
                    }
                });
                if($.isFunction(args.onSearchClick)) {
                    submit.click(function() {
                        args.onSearchClick(paramId);
                    });
                } else {
                    submit.click(function() {
                        input.modalSearch("show");
                    });
                }
            });
        },

        parse : function(params) {
            var obj = JSON.parse(params);
            for(var key in obj) {
                if (obj.hasOwnProperty(key)) {
                    var match = obj[key].match(/\${(.*)}/);
                    if (match) {
                        var bean = OPENIAM.ReportParameters.ParametersContainer.findByName(match[1]);
                        if (bean) {
                            obj[key] = obj[key].replace(match[0], bean.value);
                        } else {
                            // remove attribute if it contains mask which can't be resolved
                            delete obj[key];
                        }
                    }
                }
            }
            return obj;
        }
    },

    Group : {
        load : function() {
            OPENIAM.ReportParameters.Common.load({
                onSearchClick : function(paramId) {
                    $("#dialog").groupDialogSearch({
                        searchTargetElmt : "#searchResultsContainer",
                        onSearchResultClick : function(bean) {
                            OPENIAM.ReportParameters.ParametersContainer.add(paramId, bean);
                            return false;
                        }
                    });
                },
                columns : [
                    localeManager["openiam.ui.report.parameters.group.name"],
                    localeManager["openiam.ui.report.parameters.managed.system"],
                    localeManager["openiam.ui.common.actions"]
                ],
                columnsMap:["name", "managedSysName"],
                target : this,
                buttonSelector : ".searchGroupBtn",
                getAdditionalDataRequestObject : function() {
                    var obj = {};
                    return obj;
                }
            });
        }
    },

    Roles : {
        load : function() {
            OPENIAM.ReportParameters.Common.load({
                modalAjaxURL : "rest/api/entitlements/searchRoles",
                columns : [
                    localeManager["openiam.ui.report.parameters.role.name"],
                    localeManager["openiam.ui.report.parameters.managed.system"],
                    localeManager["openiam.ui.common.actions"]
                ],
                columnsMap:["name", "managedSysName"],
                target : this,
                buttonSelector : ".searchRoleBtn",
                dialogTitle : localeManager["openiam.ui.shared.role.search"],
                emptyResultsText : localeManager["openiam.ui.shared.role.search.empty"]
            });
        }
    },

    Resources : {
        load : function() {
            OPENIAM.ReportParameters.Common.load({
                modalAjaxURL : "rest/api/entitlements/searchResources",
                columns : [
                    localeManager["openiam.ui.report.parameters.resource.name"],
                    localeManager["openiam.ui.report.parameters.managed.system"],
                    localeManager["openiam.ui.common.actions"]
                ],
                columnsMap:["name", "managedSysName"],
                target : this,
                buttonSelector : ".searchResBtn",
                dialogTitle : localeManager["openiam.ui.shared.resource.search"],
                emptyResultsText : localeManager["openiam.ui.shared.resource.search.empty"]
            });
        }
    },

    Organizations : {
        load : function() {
            OPENIAM.ReportParameters.Common.load({
                modalAjaxURL : "rest/api/entitlements/searchOrganizations",
                columns : [
                    localeManager["openiam.ui.report.parameters.organization.name"],
                    localeManager["openiam.ui.report.parameters.classification"],
                    localeManager["openiam.ui.common.actions"]
                ],
                columnsMap:["name", "type"],
                target : this,
                buttonSelector : ".searchOrgBtn",
                dialogTitle : localeManager["openiam.ui.shared.organization.search"],
                emptyResultsText : localeManager["openiam.ui.shared.organization.search.empty"]
            });
        }
    },

    Users : {
        load : function() {
            $(".searchUsersBtn").click(function() {
                var paramId = $(this).attr("id");
                $("#dialog").userSearchForm({
                    afterFormAppended : function() {
                        $("#dialog").dialog({autoOpen: false, draggable : false, resizable : false, title : localeManager["openiam.ui.common.search.users"], width: "auto", position : "center"});
                        $("#dialog").dialog("open");
                    },
                    onSubmit : function(json) {
                        $("#searchResultsContainer").userSearchResults({
                            "jsonData" : json,
                            "page" : 0,
                            "size" : 20,
                            initialSortColumn : "name",
                            initialSortOrder : "ASC",
                            url : "rest/api/users/search",
                            emptyFormText : localeManager["openiam.ui.common.user.search.empty"],
                            emptyResultsText : localeManager["openiam.ui.common.user.search.no.results"],
                            columnHeaders : [
                                localeManager["openiam.ui.common.name"],
                                localeManager["openiam.ui.common.phone.number"],
                                localeManager["openiam.ui.common.email.address"],
                                localeManager["openiam.ui.webconsole.user.status"],
                                localeManager["openiam.ui.webconsole.user.accountStatus"]
                            ],
                            onAppendDone : function() {
                                $("#dialog").dialog("close");
                            },
                            onEntityClick : function(bean) {
                                OPENIAM.ReportParameters.ParametersContainer.add(paramId, bean);
                                $("#searchResultsContainer").empty();
                            }
                        });
                    }
                });
            });
        }
    },

    Supervisor: {
        load : function() {
            $(".searchSupervisorBtn").each(function() {
                var submit = $(this);
                var paramId = submit.attr("id");
                submit.click( function() {
                    $("#searchResultsContainer").userSearchResults({
                        "jsonData" : {},
                        "requestType" : "GET",
                        "page" : 0,
                        "size" : 20,
                        initialSortColumn : "name",
                        initialSortOrder : "ASC",
                        url : "rest/api/users/getAllSuperiors",
                        emptyFormText : localeManager["openiam.ui.common.user.search.empty"],
                        emptyResultsText : localeManager["openiam.ui.shared.supervisor.search.empty"],
                        columnHeaders : [
                            localeManager["openiam.ui.common.name"],
                            localeManager["openiam.ui.common.phone.number"],
                            localeManager["openiam.ui.common.email.address"],
                            localeManager["openiam.ui.webconsole.user.status"],
                            localeManager["openiam.ui.webconsole.user.accountStatus"]
                        ],
                        onEntityClick : function(bean) {
                            OPENIAM.ReportParameters.ParametersContainer.add(paramId, bean);
                            $("#searchResultsContainer").empty();
                        }
                    });
                });
            });
        }
    },

    Action : {
        load : function() {
            OPENIAM.ReportParameters.Common.load({
                modalAjaxURL : "rest/api/metadata/findActions",
                columns : [
                    localeManager["openiam.ui.report.parameters.action.name"],
                    localeManager["openiam.ui.common.actions"]
                ],
                columnsMap:["name"],
                target : this,
                buttonSelector : ".searchActionBtn",
                dialogTitle : localeManager["openiam.ui.shared.log.action.search"],
                emptyResultsText : localeManager["openiam.ui.shared.log.action.search.empty"]
            });
        }
    },

    BaseInput : {
        load : function() {
            $(".addBtn").each(function() {
                var submit = $(this);
                var paramId = submit.attr("id");
                var input = $("#input" + paramId);
                submit.click( function() {
                    var bean, val = input.val();
                    if (input.prop("tagName") == "SELECT") {
                        bean = {id : val, name : input.find("option:selected").text()};
                        input.val("");
                    } else {
                        bean = {id : val, name : val};
                    }
                    if (bean != null && !!val) {
                        OPENIAM.ReportParameters.ParametersContainer.add(paramId, bean);
                    }
                })
            })
        }
    },

    DatePicker : {
        load : function() {
            $("input.date").datepicker({dateFormat: OPENIAM.ENV.DateFormatDP,showOn: "button",changeMonth:true, changeYear:true}).attr('readonly','readonly')
            this.fix();
        },

        fix : function() {
            // try to fix icon position. Temporary solution. Still under investigation
            $("input.date").each(function(){
                var element = $(this);
                var icon = element.next();
                var el_pos = element.position(),
                    el_h = element.outerHeight(false),
                    el_mt = parseInt(element.css('marginTop'), 10) || 0,
                    el_w = element.outerWidth(false),
                    el_ml = parseInt(element.css('marginLeft'), 10) || 0,

                    i_w = icon.outerWidth(true),
                    i_h = icon.outerHeight(true);


                var new_icon_top = el_pos.top + el_mt + ((el_h - i_h) / 2);
                var new_icon_left = el_pos.left + el_ml + el_w - i_w;

                var icon_pos = icon.position();

                if(icon_pos.top!=new_icon_top){
                    icon.css('top', new_icon_top);
                }
                if(icon_pos.left!=new_icon_left){
                    icon.css('left', new_icon_left);
                }
            });
        }
    }
};

$(document).ready(function() {
    OPENIAM.ReportParameters.onReady();
});

$(window).load(function() {
    if(OPENIAM.ENV.ErrorMessage != null) {
        OPENIAM.Modal.Error(OPENIAM.ENV.ErrorMessage);
    }
});
