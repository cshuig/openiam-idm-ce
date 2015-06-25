(function ($) {
    var selectedItems = [];
    var selectedItemsCount = 0;
    var privateMethods = {
        requestMetaData: function () {
            var $this = this;
            var $options = $this.data("options");
            var onError = $options.onError;
            var errorText = $options.errorText;
            if ($options.columnHeaders != null && $options.columnHeaders != undefined && $options.columnHeaders.length > 0) {
                privateMethods.request.call($this, $options.page);
            } else {
                $.ajax({
                    "url": $options.contextPath + "rest/api/users/search/metadata",
                    type: $options.requestType,
                    dataType: "json",
                    contentType: "application/json",
                    success: function (data, textStatus, jqXHR) {
                        var options = $.extend($options, {
                            columnHeaders: data,
                            initialSortColumn: data[0],
                            initialSortOrder: "ASC"
                        });
                        $this.data("options", options);
                        privateMethods.request.call($this, $options.page);
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        onError(errorText);
                    }
                });
            }
        },
        request: function (page) {
            var $this = this;
            var $options = $this.data("options");
            $options.page = page;
            var jsonData = $options.jsonData;
            var url = $options.url;
            var size = $options.size;
            var initialSortColumn = $options.initialSortColumn;
            var initialSortOrder = $options.initialSortOrder;
            var onError = $options.onError;
            var errorText = $options.errorText;
            var emptyText = $options.emptyFormText;
            jsonData.size = size;
            jsonData.from = page * size;
            jsonData.sortBy = initialSortColumn;
            jsonData.orderBy = initialSortOrder;
            var onSuccess = privateMethods.draw;
            var onEmptyForm = $options.onEmptyForm;
            var data = jsonData;
            if ($options.requestType == "POST") {
                data = JSON.stringify(jsonData);
            }

            $this.addClass("loading").show().html("<div class=\"loader\"></div>");
            $.ajax({
                "url": url,
                "data": data,
                type: $options.requestType,
                dataType: "json",
                contentType: "application/json",
                success: function (data, textStatus, jqXHR) {
                    if (data.emptySearchBean) {
                        $this.removeClass("loading").empty();
                        onEmptyForm.call($this, {message: emptyText});
                    } else if (data.error) {
                        onError(data.error);
                        var count = data.size;
                        if (count != null && count != undefined) {
                            $this.data("totalSize", count);
                        }
                        onSuccess.call($this, data);
                    } else {
                        var count = data.size;
                        if (count != null && count != undefined) {
                            $this.data("totalSize", count);
                        }
                        onSuccess.call($this, data);
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    onError(errorText);
                }
            });
        },
        draw: function (data) {
            var $this = this;
            var $options = this.data("options");
            var emptyResultsText = $options.emptyResultsText;
            var pageSize = $options.size;
            var onAppendDone = $options.onAppendDone;
            var columnHeaders = $options.columnHeaders;
            var onEntityClick = $options.onEntityClick;
            var table = document.createElement("table");
            $(table).attr("cellspacing", "1");
            $(table).addClass("yui");
            $(table).attr("width", "100%");
            var thead = document.createElement("thead");
            var tr = document.createElement("tr");

            if ($options.isSelectAllowed) {
                var th = document.createElement("th");
                $(th).addClass("checkCell");
                var chkBox = $(document.createElement("input"))
                    .attr("type", "checkbox").addClass("selectAllBtn");
                chkBox.change(function () {
                    var state = $(this).prop('checked');
                    var chkBoxes = $(this).parents("table:first").find(".selectItemBtn");

                    chkBoxes.each(function () {
                        $(this).prop('checked', state);
                        if (state)
                            privateMethods.selectItem.call($this, $(this));
                        else
                            privateMethods.deselectItem.call($this, $(this));
                    });
                    selectedItemsCount = selectedItems.length;

                    if ($options.onCheckCallback && $.isFunction($options.onCheckCallback)) {
                        $options.onCheckCallback.call($this);
                    }
                });
                $(th).append(chkBox);
                $(tr).append(th);
            }

            $.each(columnHeaders, function (idx, hdr) {
                var th = document.createElement("th");
                var headerText = localeManager["openiam.ui.user.search.result.column." + hdr];
                if ($options.sortEnable) {
                    headerText = "<div>" + headerText + "<span ></span></div>";
                    $(th).addClass("sortable");
                    if ($options.initialSortColumn == hdr
                        || idx == $options.defaultSortColumnIndex) {

                        $(th).addClass(($options.initialSortOrder == "DESC") ? "headerSortUp" : "headerSortDown");
                    }
                    $(th).click(function () {
                        var thisColumn = $(this);
                        var isSortDown = ($options.initialSortOrder == "ASC");

                        thisColumn.parent().children("th.sortable").removeClass("headerSortDown").removeClass("headerSortUp");
                        if (isSortDown) {
                            thisColumn.addClass("headerSortUp");
                            $options.initialSortOrder = "DESC";
                        } else {
                            thisColumn.addClass("headerSortDown");
                            $options.initialSortOrder = "ASC";
                        }
                        $options.initialSortColumn = hdr;

                        $options.jsonData.sortBy = hdr;
                        $options.jsonData.orderBy = (isSortDown) ? "ASC" : "DESC";
                        $options.page = 0;

                        privateMethods.request.call($this, $options.page);
                    });
                }
                $(th).html(headerText);
                $(tr).append(th);
            });
            $(thead).append(tr);
            $(table).append(thead);

            var tbody = document.createElement("tbody");
            if (data.beans != null && data.beans != undefined && data.beans.length > 0) {
                $.each(data.beans, function (idx, bean) {
                    var tr = document.createElement("tr");

                    if ($options.isSelectAllowed) {
                        var td = document.createElement("td");
                        $(td).addClass("checkCell");
                        var chkBox = $(document.createElement("input")).attr("type", "checkbox").addClass("selectItemBtn");
                        chkBox.change(function () {
                            var isChecked = $(this).is(':checked');
                            if (!isChecked) {
                                $(this).parents("table:first").find(".selectAllBtn").prop('checked', false);
                                privateMethods.deselectItem.call($this, $(this));
                            } else {
                                privateMethods.selectItem.call($this, $(this));
                            }
                            selectedItemsCount = selectedItems.length;

                            if ($options.onCheckCallback && $.isFunction($options.onCheckCallback)) {
                                $options.onCheckCallback.call($this);
                            }
                        });
                        $(td).append(chkBox);
                        $(tr).append(td);
                    }

                    $.each(columnHeaders, function (idx, hdr) {
                        if (hdr) {
                            var td = $(document.createElement("td"));
                            if (idx == 0 && $options.entityURL) {
                                td.append($(document.createElement("a")).attr("href", $options.entityURL + "?id=" + bean.id).text(bean[hdr]));
                            } else {
                                td.text((bean[hdr] != null) ? bean[hdr] : "");
                            }
                            $(tr).append(td);
                        }
                    });
//							var td1 = $(document.createElement("td")).append($(document.createElement("a")).attr("href", $options.entityURL + "?id=" + bean.id).text(bean.name));
//							var td2 = $(document.createElement("td")).text(bean.phone);
//							var td3 = $(document.createElement("td")).text(bean.email);
//							var td4 = $(document.createElement("td")).text((bean.userStatus != null) ? bean.userStatus : "");
//							var td5 = $(document.createElement("td")).text((bean.accountStatus != null) ? bean.accountStatus : "");
//							$(tr).append(td1, td2, td3, td4, td5);
                    $(tbody).append(tr);
                    $(tr).attr("entityId", bean.id);
                    if (!$options.isSelectAllowed) {
                        $(tr).click(function () {
                            onEntityClick(bean);
                            return false;
                        });
                    }
                });
            } else {
                var tr = document.createElement("tr");
                var td = $(document.createElement("td")).addClass("empty").attr("colspan", columnHeaders.length).text(emptyResultsText);
                $(tr).append(td);
                $(tbody).append(tr);
            }
            $(table).append(tbody);


            var tfoot = document.createElement("tfoot");
            var footTr = document.createElement("tr");
            footTr.className = "pager";
            var colspan = columnHeaders.length;
            if ($options.isSelectAllowed) {
                colspan = colspan + 1;
            }
            var footTd = document.createElement("td");
            $(footTd).attr("colspan", colspan);
            $(footTd).attr("style", "border-right: solid 3px #7f7f7f;");
            $(footTd).append("<img src=\"/openiam-ui-static/plugins/tablesorter/img/first.png\" class=\"first\"/>");
            $(footTd).append("<img src=\"/openiam-ui-static/plugins/tablesorter/img/prev.png\" class=\"prev\"/>");
            $(footTd).append("<input type=\"text\" class=\"pagedisplay\"/>");
            $(footTd).append("<img src=\"/openiam-ui-static/plugins/tablesorter/img/next.png\" class=\"next\"/>");
            $(footTd).append("<img src=\"/openiam-ui-static/plugins/tablesorter/img/last.png\" class=\"last\"/>");

            var pagesizeCtrl = document.createElement("select");
            pagesizeCtrl.className = "pagesize";

            var opt1 = document.createElement("option");
            opt1.value = "20";
            //opt1.text = "20";
            if (typeof(opt1.innerText) != 'undefined'){
                opt1.innerText = "20";
            } else {
                opt1.text = "20";
            }
            var opt2 = document.createElement("option");
            opt2.value = "50";
            //opt2.text = "50";
            if (typeof(opt2.innerText) != 'undefined'){
                opt2.innerText = "50";
            } else {
                opt2.text = "50";
            }
            var opt3 = document.createElement("option");
            opt3.value = "100";
            //opt3.text = "100";
            if (typeof(opt3.innerText) != 'undefined'){
                opt3.innerText = "100";
            } else {
                opt3.text = "100";
            }
            var opt4 = document.createElement("option");
            opt4.value = "200";
            //opt4.text = "200";
            if (typeof(opt4.innerText) != 'undefined'){
                opt4.innerText = "200";
            } else {
                opt4.text = "200";
            }
            var opt5 = document.createElement("option");
            opt5.value = "500";
            //opt5.text = "500";
            if (typeof(opt5.innerText) != 'undefined'){
                opt5.innerText = "500";
            } else {
                opt5.text = "500";
            }

            $(pagesizeCtrl).append(opt1, opt2, opt3, opt4, opt5).val(pageSize).change(function () {
                $options.size = $(this).val();
                privateMethods.request.call($this, 0);
            });

            $(footTd).append(pagesizeCtrl);

            if ($.isFunction($options.onBack) && $options.backText != null) {
                var backElmt = $(document.createElement("a"));
                $(backElmt).text($options.backText).attr("href", "javascript:void(0)").addClass("ui-dialog-back-btn");
                backElmt.click(function () {
                    $options.onBack.call($this);
                    return false;
                });
                $(footTd).append(backElmt);
            }
            $(footTr).append(footTd);
            $(tfoot).append(footTr);
            $(table).append(tfoot);

            // restore check state
            if ($options.isSelectAllowed) {
                if (selectedItemsCount > 0) {
                    $(table).find("tr").each(function () {
                        var entityId = $(this).attr("entityId");
                        if ($.inArray(entityId, selectedItems) != -1) {
                            $(this).find(".selectItemBtn").prop('checked', true);
                        }
                    });
                }
            }

            this.removeClass("loading").empty().show().html(table);
            privateMethods.tableSort.call($this, table);
            if (onAppendDone) {
                onAppendDone.call(this);
            }
        },
        selectItem: function (checkBox) {
            var idToAdd = checkBox.parents("tr:first").attr("entityId");
            if ($.inArray(idToAdd, selectedItems) == -1) {
                selectedItems.push(idToAdd);
            }
        },
        deselectItem: function (checkBox) {
            var idToRemove = $(checkBox).parents("tr:first").attr("entityId");
            selectedItems = $.grep(selectedItems, function (item) {
                return item != idToRemove;
            });
        },
        tableSort: function (table) {
            var $this = this;
            var $options = this.data("options");

            var size = $options.size;
            var currentPage = $options.page;
            var found = parseInt(this.data("totalSize"));
            var numTotalPages = Math.ceil(found / size);

            $(table).tablesorter({
                debug: true,
                sortDisabled: true,
                widgets: ['zebra']
            }).tablesorterPager({
                container: $(table).find(".pager"),
                positionFixed: false,
                size: size,
                page: currentPage,
                totalRows: found,
                totalPages: numTotalPages,
                onFirstClick: function () {
                    privateMethods.request.call($this, 0);
                },
                onLastClick: function () {
                    privateMethods.request.call($this, numTotalPages - 1);
                },
                onPrevClick: function () {
                    privateMethods.request.call($this, currentPage - 1);
                },
                onNextClick: function () {
                    privateMethods.request.call($this, currentPage + 1);
                }
            });
        }
    };

    var methods = {
        init: function (args) {
            var eUrl = (args.entityURL) ? args.entityURL : "editUser.html";
            var options = $.extend({
                requesterId: null,
                jsonData: null, //json to send for the request
                page: null, //page to request
                size: null, //how many results to fetch
                entityURL: eUrl,
                url: null,
                contextPath: "",
                requestType: "POST",
                errorText: localeManager["openiam.ui.internal.error"],
                emptyFormText: null,
                onError: OPENIAM.Modal.Error,
                onEmptyForm: OPENIAM.Modal.Warn,
                emptyResultsText: null,
                columnHeaders: null,
                sortEnable: true,
                defaultSortColumnIndex: 0,
                onAppendDone: function () {
                },
                onEntityClick: function (bean) {
                    window.location.href = eUrl + "?id=" + bean.id;
                },
                onBack: null,
                backText: localeManager["openiam.ui.common.back.to.search"],
                isSelectAllowed: false,
                onCheckCallback: null
            }, args);
            this.data("options", options);

            if (options.jsonData == null) {
                $.error("jsonData option required, but not present");
            }

            if (options.page == null) {
                $.error("page option required, but not present");
            }

            if (options.size == null) {
                $.error("size option required, but not present");
            }

            if (options.url == null) {
                $.error("url option required, but not present");
            }

            if (options.emptyFormText == null) {
                $.error("emptyFormText option required, but not present");
            }

            if (options.emptyResultsText == null) {
                $.error("emptyResultsText option required, but not present");
            }

            //if(options.columnHeaders == null) {
            //	$.error("columnHeaders option required, but not present");
            //}
            selectedItems = [];
            selectedItemsCount = 0;

            privateMethods.requestMetaData.call(this);
        },
        countSelectedItems: function () {
            return selectedItemsCount;
        },
        getSelectedItems: function () {
            return selectedItems;
        }
    };

    $.fn.userSearchResults = function (method) {
        if (methods[method]) {
            return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === 'object' || !method) {
            return methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.userSearchForm');
        }
    };
})(jQuery);