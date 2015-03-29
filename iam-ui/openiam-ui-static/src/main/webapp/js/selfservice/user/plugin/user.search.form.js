(function ($) {

    var cssDependencies = [
        "/openiam-ui-static/css/common/entitlements.css",
        "/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.css",
        "/openiam-ui-static/js/common/plugins/modalsearch/modal.search.css",
        "/openiam-ui-static/js/common/plugins/multiselect/jquery.multiselect.css"
    ];

    var javascriptDependencies = [
        "/openiam-ui-static/js/common/plugins/multiselect/jquery.multiselect.js",
        "/openiam-ui-static/js/common/search/organization.search.js",
        "/openiam-ui-static/js/common/search/search.result.js",
        "/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.js",
        "/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.js"
    ];

    var restCache = {};
    var extraOptions = {
        "organizationId": {
            html: "<td class=\"filter reordable\" colspan=\"3\">" +
            "<label for=\"organizationId\">" +
            localeManager["openiam.ui.common.organization"] + ":" +
            "</label>" +
            "<a href=\"javascript:void(0);\" class=\"organizationLink entity-link organization\"></a>" +
            "<div id=\"organizationContainer\" class=\"ui-selected-container\"></div>" +
            "<input type=\"hidden\" id=\"organizationId\" />" +
            "</td>"
        },
        "employeeType": {
            html: "<td class=\"filter reordable\" colspan=\"3\">" +
            "<label for=\"employeeType\">" +
            localeManager["openiam.ui.common.employee.type"] + ":" +
            "</label>" +
            "<select id=\"employeeType\" name=\"employeeType\" autocomplete=\"off\">" +
            "<option value=\"\">" +
            localeManager["openiam.ui.common.employee.type.select"] + "..." +
            "</option>" +
            "</select>" +
            "</td>", restURL: "rest/api/users/employeeTypes"
        }
    };

    var privateMethods = {
        getInitialFormHTML: function () {
            var form = $(document.createElement("form"));
            form.attr("id", "userSearchForm");
            form.attr("name", "userSearchForm");
            form.append("<select id=\"searchFilter\" multiple=\"multiple\">" +
            "<option value=\"organizationId\">" + localeManager["openiam.ui.common.organization"] + "</option>" +
            "<option value=\"employeeType\">" + localeManager["openiam.ui.common.employee.type"] + "</option>" +
            "</select>");
            var table = $(document.createElement("table")).attr("cellspacing", "1").addClass("yui").attr("width", "100%").attr("id", "userSearchFormTable");
            var thead = $(document.createElement("thead"));
            thead.append("<tr>" +
                "<td colspan=\"6\">" +
                "<div class=\"info center\" style=\"position:relative;\">" +
                localeManager["openiam.ui.common.user.search.algorithm.explanation"] +
                "</div>" +
                "</td>" +
                "</tr>" +
                "<tr>" +
                "<td class=\"filter\" colspan=\"3\">" +
                "<label for=\"lastName\">" +
                localeManager["openiam.ui.webconsole.user.lastName"] + ":" +
                "</label>" +
                "<input id=\"lastName\" name=\"lastName\" maxlength=\"30\" size=\"23\" type=\"text\" autocomplete=\"off\" />" +
                "</td>" +
                "<td class=\"filter\" colspan=\"3\">" +
                "<label for=\"emailAddress\">" +
                localeManager["openiam.ui.common.email.address"] + ":" +
                "</label>" +
                "<input id=\"emailAddress\" name=\"emailAddress\" maxlength=\"50\" size=\"23\" type=\"text\" autocomplete=\"off\" />" +
                "</td>" +
                "</tr>" +
                "<tr>" +
                "<td class=\"filter\" colspan=\"3\">" +
                "<label for=\"principal\">" +
                localeManager["openiam.ui.webconsole.user.principal"] + ":" +
                "</label>" +
                "<input id=\"principal\" name=\"principal\" maxlength=\"1000\" size=\"23\" type=\"text\" autocomplete=\"off\" />" +
                "</td>" +
                "<td class=\"filter\" colspan=\"3\">" +
                "<label for=\"employeeId\">" +
                localeManager["openiam.ui.webconsole.user.employeeId"] + ":" +
                "</label>" +
                "<input id=\"employeeId\" name=\"employeeId\" maxlength=\"32\" size=\"23\" type=\"text\" autocomplete=\"off\" />" +
                "</td>" +
                "</tr>"
            );
            var tfoot = $(document.createElement("tfoot"));
            tfoot.append("<tr>" +
            "<td class=\"filter\" colspan=\"6\">" +
            "<div id=\"humanReadable\"></div>" +
            "</td>" +
            "</tr>" +
            "<tr>" +
            "<td class=\"filter\" colspan=\"6\">" +
            "<input type=\"submit\" value=\"" + localeManager["openiam.ui.common.search"] + "\" class=\"redBtn\" />" +
            "<a id=\"cleanUserSearchForm\" class=\"whiteBtn\" href=\"javascript:void(0);\">" +
            localeManager["openiam.ui.common.clear"] +
            "</a>" +
            "</td>" +
            "</tr>");
            table.append(thead, tfoot);
            form.append(table, $(document.createElement("div")).attr("id", "userSearchFormResultsArea"));
            return form;
        },
        request: function () {
            var $this = this;
            var $options = $this.data("options");
            var afterFormAppended = $options.afterFormAppended;
            $.ajax({
                "url": $options.url,
                type: "GET",
                dataType: "json",
                contentType: "application/json",
                success: function (data, textStatus, jqXHR) {
                    $this.html($options.form);
                    privateMethods.postProcessHtml.call($this, data);
                    afterFormAppended();
                    privateMethods.bind.call($this);
                },
                error: function (jqXHR, textStatus, errorThrown) {

                }
            });
        },
        restfulCall: function (url, target) {
            var $this = this;
            var $options = $this.data("options");
            var form = $options.form;

            var postRest = function (data) {
                for (var i = 0; i < data.beans.length; i++) {
                    var bean = data.beans[i];
                    var option = $(document.createElement("option"));
                    option.attr("value", bean.id);
                    option.text(bean.name);
                    form.find("#" + target).append(option);
                }
            }

            if (restCache[url]) {
                postRest(restCache[url]);
            } else {
                $.ajax({
                    "url": url,
                    type: "GET",
                    dataType: "json",
                    contentType: "application/json",
                    success: function (data, textStatus, jqXHR) {
                        restCache[url] = data;
                        postRest(data);
                    },
                    error: function (jqXHR, textStatus, errorThrown) {

                    }
                });
            }
        },
        postProcessHtml: function (data) {
            var $this = this;
            var $options = $this.data("options");

            if (data && data.additionalSearchCriteria != null && data.additionalSearchCriteria.length > 0) {
                for (var i = 0; i < data.additionalSearchCriteria.length; i++) {
                    var criteria = data.additionalSearchCriteria[i];
                    privateMethods.selectAdditionalFormAttribute.call($this, criteria, true);
                }

                $("#searchFilter").val(data.additionalSearchCriteria);
            }
        },
        reorder: function (args) {
            var tds = [];
            $("#userSearchFormTable thead .reordable").each(function () {
                tds.push($(this).detach());

            });
            if (args.add) {
                tds.push(args.html)
            }

            $("#userSearchFormTable thead").find("tr").each(function () {
                if (!$(this).is(":first-child")) {
                    if ($(this).children().length == 0) { /* remove empty tr */
                        $(this).remove();
                    } else {
                        $(this).children().each(function () {
                            if ($(this).children().length == 0) { /* remove empty td */
                                $(this).remove();
                            }
                        });
                    }
                }
            });

            $.each(tds, function (idx, td) {
                var added = false;
                $("#userSearchFormTable thead").find("tr").each(function () {
                    if (!added) {
                        if (!$(this).is(":first-child")) {
                            var len = $(this).children().length;
                            if (len == 1) {
                                $(this).append(td);
                                added = true;
                            }
                        }
                    }
                });

                if (!added) {
                    var theTr = $(document.createElement("tr")).append(td);
                    $("#userSearchFormTable thead").append(theTr);
                }
            });

            /* add emtpy td */
            $("#userSearchFormTable thead").find("tr").each(function () {
                if (!$(this).is(":first-child")) {
                    if ($(this).children().length == 1) {
                        $(this).append("<td colspan=\"3\"></td>");
                    }
                }
            });

            //reordable
        },
        onSearchParameterAdd: function (bean, entityContainer, linkContainer, emptyText, addAnotherText) {
            var $this = this;
            var $options = $this.data("options");
            if ($this.find(entityContainer).find("#entity" + bean.id).length == 0) {
                var div = $(document.createElement("div")).data("dataId", bean.id).data("entity", bean).attr("id", "entity" + bean.id).addClass("ui-removable");
                var a = $(document.createElement("div")).addClass("ui-icon").addClass("ui-icon-closethick");
                a.click(function () {
                    $(this).closest(".ui-removable").remove();
                    if ($this.find(entityContainer).children().length == 0) {
                        $this.find(linkContainer).text(emptyText);
                    }
                });
                var lbl = $(document.createElement("label")).text(bean.name).addClass("ui-removeable-text");
                div.append(a, lbl);
                $this.find(entityContainer).append(div);
            }
            $this.find(linkContainer).text(addAnotherText);
        },
        onFormChange: function () {
            var $this = this;
            var $options = $this.data("options");

            var validString = function (arg) {
                return (arg != null && arg != undefined && $.trim(arg) != "")
            };

            var lastName = $this.find("#lastName").val();
            var emailAddress = $this.find("#emailAddress").val();
            var principal = $this.find("#principal").val();
            var employeeId = $this.find("#employeeId").val();
            var employeeType = ($("#employeeType").val() != "") ? $this.find("#employeeType option:selected").text() : null;

            var humanReadable = "";
            if (validString(lastName)) {
                humanReadable += "(Last Name Starts With '{0}')".format(lastName);
            }

            if (validString(emailAddress)) {
                humanReadable += (humanReadable != "") ? (" AND ") : humanReadable;
                humanReadable += "(Email Address Starts with '{0}')".format(emailAddress);
            }
            if (validString(principal)) {
                humanReadable += (humanReadable != "") ? (" AND ") : humanReadable;
                humanReadable += "(Principal Starts with '{0}')".format(principal);
            }

            if (validString(employeeId)) {
                humanReadable += (humanReadable != "") ? (" AND ") : humanReadable;
                humanReadable += "(Employee ID Starts With '{0}')".format(employeeId);
            }
            if (validString(employeeType)) {
                humanReadable += (humanReadable != "") ? (" AND ") : humanReadable;
                humanReadable += "(Employee Type equals '{0}')".format(employeeType);
            }

            var orgNames = [];
            $.each(privateMethods.getOrganizations.call($this), function (idx, bean) {
                orgNames.push(bean.name);
            });

            if (orgNames.length > 0) {
                humanReadable += (humanReadable != "") ? (" AND ") : humanReadable;
                humanReadable += "(User is one of the following Organizations '{0}')".format(JSON.stringify(orgNames));
            }

            $this.find("#humanReadable").text(humanReadable);
        },

        getOrganizations: function () {
            var $this = this;
            var $options = $this.data("options");
            return $this.find("#userSearchForm a.organizationLink").selectableSearchResult("getValues") || [];
        },
        getOrganizationIds: function () {
            var $this = this;
            var $options = $this.data("options");
            return $this.find("#userSearchForm a.organizationLink").selectableSearchResult("getIds") || [];
        },
        bind: function () {
            var $this = this;
            var $options = $this.data("options");
            $this.find("#cleanUserSearchForm").click(function () {
                $this.find("#lastName").val("");
                $this.find("#humanReadable").text("");
                $this.find("#userSearchForm #userSearchFormResultsArea").empty();
                $this.find("#userSearchForm a.organizationLink").selectableSearchResult("clear");
                $this.find("#emailAddress").val("");
                $this.find("#principal").val("");
                $this.find("#employeeId").val("");
                $this.find("#employeeType").val("");
                return false;
            });

            $("#searchFilter").multiselect({
                header: false,
                height: 62,
                click: function (event, ui) {
                    privateMethods.selectAdditionalFormAttribute.call($this, ui.value, ui.checked);
                },
                noneSelectedText: localeManager["openiam.ui.user.add.more.search.criteria"]
            });

            $this.find("#userSearchForm").submit(function () {
                $this.find("#userSearchForm #userSearchFormResultsArea").empty();
                privateMethods.onFormChange.call($this);
                var json = privateMethods.toJSON.call($this);
                $options.onSubmit.call($this, json);
                return false;
            });
        },
        selectAdditionalFormAttribute: function (selectedValue, isChecked) {
            var $this = this;
            var $options = $this.data("options");
            var info = extraOptions[selectedValue];
            var html = $(info.html);
            if (isChecked) {
                if (html.is("tr")) {
                    $("#userSearchFormTable thead").append(html);
                } else {
                    privateMethods.reorder.call($this, {html: html, add: true});
                }

                if (info.restURL) {
                    privateMethods.restfulCall.call($this, $options.restfulURLPrefix + info.restURL, selectedValue)
                }

                if (selectedValue == "organizationId") {
                    $("#userSearchForm a.organizationLink").selectableSearchResult({
                        noneSelectedText: localeManager["openiam.ui.shared.organization.search"],
                        addMoreText: localeManager["openiam.ui.common.organization.add.another"],
                        onClick: function ($that) {
                            $("#editDialog").organizationDialogSearch({
                                restfulURLPrefix: $options.restfulURLPrefix,
                                searchTargetElmt: "#userSearchForm #userSearchFormResultsArea",
                                onAdd: function (bean) {
                                    $that.selectableSearchResult("add", bean);
                                },
                                pageSize: 5
                            });
                        }
                    });
                } else {

                }
            } else {
                if (html.is("tr")) {
                    $("#" + selectedValue).closest("tr").remove();
                } else {
                    $("#" + selectedValue).closest("td").remove();
                    privateMethods.reorder.call($this, {html: html})
                }
            }
        },

        toJSON: function () {
            var $this = this;
            var obj = {};
            obj.requesterId = $this.find("#requesterId").val();
            obj.lastName = $this.find("#lastName").val();
            obj.principal = $this.find("#principal").val();
            obj.email = $this.find("#emailAddress").val();
            obj.organizationIds = privateMethods.getOrganizationIds.call($this);
            obj.employeeId = $this.find("#employeeId").val();
            obj.employeeType = $this.find("#employeeType").val();
            return obj;
        }
    };

    var methods = {
        init: function (args) {
            var options = $.extend({
                restfulURLPrefix: "",
                onSubmit: null, //callback when the 'Submit' button is pressed
                url: "rest/api/users/getUserFormAttributes", //URL of the search form
                afterFormAppended: function () {
                } //called when the form is fetched and appended
            }, args);
            this.data("options", options);

            if (options.onSubmit == null && !$.isFunction(options.onSubmit)) {
                $.error("onSubmit option required, but not present");
            }

            if (options.url == null) {
                $.error("url option required, but not present");
            }

            if ($("#editDialog").length == 0) {
                $(document.body).append($(document.createElement("div")).attr("id", "editDialog"));
            }

            options.form = privateMethods.getInitialFormHTML();

            $.each(cssDependencies, function (idx, file) {
                OPENIAM.lazyLoadCSS(file);
            });

            var $this = this;
            OPENIAM.loadScripts(javascriptDependencies, function () {
                privateMethods.request.call($this);
            });
        }
    };

    $.fn.userSearchForm = function (method) {
        if (methods[method]) {
            return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === 'object' || !method) {
            return methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.userSearchForm');
        }
    };
})(jQuery);