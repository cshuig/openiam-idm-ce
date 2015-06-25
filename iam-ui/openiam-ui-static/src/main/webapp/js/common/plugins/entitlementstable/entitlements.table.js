(function ($) {
    var selectedItems = [];
    var selectedItemsCount = 0;
    var privateMethods = {
        request: function (page) {
            var that = this;
            var $options = this.data("options");
            var size = $options.pageSize;
            var from = (page) * size;
            var reqeustIdName = $options.requestParamIdName;
            var requestIdValue = $options.requestParamIdValue;
            var onError = $options.onInternalError;
            var onErrorText = $options.internalErrorText;
            var url = $options.ajaxURL;
            var onSuccess = privateMethods.draw;

            var data = {"from": from, "size": size};
            if (reqeustIdName != null && reqeustIdName != undefined && requestIdValue != null && requestIdValue != undefined) {
                data[reqeustIdName] = requestIdValue;
            }

            if ($.isFunction($options.getAdditionalDataRequestObject)) {
                data = $.extend(data, $options.getAdditionalDataRequestObject());
            }
            data = $.extend(data, {sortBy: $options.initialSortColumn, orderBy: $options.initialSortOrder});

            this.addClass("loading").html("<div class=\"loader\"></div>");
            if ($.isFunction($options.getData)) {
                data = $options.getData.call(this, data);
                size = data.size;
                if (size != null && size != undefined) {
                    that.data("totalSize", size);
                }
                onSuccess.call(that, data, page, from);
            } else {
                var isValid = 'true';
                if ($.isFunction($options.validate)) {
                    var result = $options.validate.call(this, data);
                    isValid = result.valid;
                    $options.message = result.message;
                }
                if (isValid == 'true') {
                    $.ajax({
                        "url": url,
                        "data": data,
                        type: "GET",
                        dataType: "json",
                        contentType: "application/json",
                        success: function (data, textStatus, jqXHR) {
                            var size = data.size;
                            if (size != null && size != undefined) {
                                that.data("totalSize", size);
                            }
                            onSuccess.call(that, data, page, from);
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            onError(onErrorText);
                        }
                    });
                } else {
                    onSuccess.call(that, data, page, from);
                }
            }
        },
        draw: function (data, page, from) {
            var that = this;
            var $options = that.data("options");
            var totalSize = parseInt(this.data("totalSize")) || 0;
            var columnHeaders = $options.columnHeaders;
            var theadInputElements = $options.theadInputElements;
            var onAppendDone = $options.onAppendDone;
            var message = $options.message;
            var columnsMap = $options.columnsMap;
            var colspan = columnHeaders.length;
            if($options.isSelectAllowed)
                colspan = colspan +1;

            $options.page = page;
            $options.from = from;
            $options.totalSize = totalSize;



            var table = document.createElement("table");
            $(table).attr("cellspacing", "1");
            $(table).addClass("yui");
            $(table).attr("width", "100%");
            var thead = document.createElement("thead");
            var tr = document.createElement("tr");
            if (message) {
                var div = document.createElement("div");
                $(div).addClass("info center").css("position", "relative").html(message);
                var td = document.createElement("td");
                $(td).attr("colspan", colspan);
                $(td).append(div);
                $(tr).append(td);
                $(thead).append(tr);
            }
            tr = document.createElement("tr");
            if (theadInputElements != null) {
                if($options.isSelectAllowed){
                    $(tr).append(document.createElement("td"));
                }
                $.each(theadInputElements, function (idx, elmt) {
                    var td = document.createElement("td");
                    $(td).html(elmt);
                    $(tr).append(td);
                });
                $(thead).append(tr);
            }
            tr = document.createElement("tr");

            if($options.isSelectAllowed){
                var th = document.createElement("th");
                $(th).addClass("checkCell");
                var chkBox = $(document.createElement("input")).attr("type", "checkbox").addClass("selectAllBtn");
                chkBox.change(function () {
                    var state = $(this).prop('checked');
                    var chkBoxes = $(this).parents("table:first").find(".selectItemBtn");

                    chkBoxes.each(function(){
                        $(this).prop('checked', state);
                        if(state)
                            privateMethods.selectItem.call(that, $(this));
                        else
                            privateMethods.deselectItem.call(that, $(this));
                    });
                    selectedItemsCount=selectedItems.length;

                    if($options.onCheckCallback && $.isFunction($options.onCheckCallback)){
                        $options.onCheckCallback.call(that);
                    }
                });
                $(th).append(chkBox);
                $(tr).append(th);
            }

            $.each(columnHeaders, function (idx, header) {
                var th = document.createElement("th");
                var headerText = header;
//						$(th).html(header);
//						$(tr).append(th);
                if (idx < columnsMap.length && $options.sortEnable) {
                    headerText = "<div>" + headerText + "<span ></span></div>"
                    $(th).addClass("sortable");

                    if ($options.initialSortColumn == columnsMap[idx]
                        || (!$options.initialSortColumn && idx == $options.defaultSortColumnIndex)) {
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
                        $options.initialSortColumn = columnsMap[idx];
                        $options.page = 0;

                        privateMethods.request.call(that, $options.page);
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
                    privateMethods.drawRow.call(that, bean, tbody);
                });
                $options.onNonEmptyResults.call(that);
            } else {
                var tr = privateMethods.getEmptyRow.call(that);
                $(tbody).append(tr);
                $options.onEmptyResults.call(that);
            }


            $(table).append(tbody);

            var tfoot = document.createElement("tfoot");
            if ($options.pageSize != -1) {
                var footTr = document.createElement("tr");
                footTr.className = "pager";
                var footTd = document.createElement("td");
                $(footTd).attr("colspan", colspan);
                $(footTd).attr("style", "border-right: solid 3px #7f7f7f;");
                $(footTd).append("<img src=\"/openiam-ui-static/plugins/tablesorter/img/first.png\" class=\"first\"/>");
                $(footTd).append("<img src=\"/openiam-ui-static/plugins/tablesorter/img/prev.png\" class=\"prev\"/>");
                $(footTd).append("<input type=\"text\" class=\"pagedisplay\" readonly />");
                $(footTd).append("<img src=\"/openiam-ui-static/plugins/tablesorter/img/next.png\" class=\"next\"/>");
                $(footTd).append("<img src=\"/openiam-ui-static/plugins/tablesorter/img/last.png\" class=\"last\"/>");
                $(footTr).append(footTd);
                $(tfoot).append(footTr);
            }
            $(table).append(tfoot);
            that.removeClass("loading").empty().html(table);
            privateMethods.tableSort.call(that, table, page, from);
            if (onAppendDone) {
                onAppendDone.call(that);
            }
        },
        drawRow: function (bean, tbody) {

            if (tbody == null) {
                tbody = this.find("tbody")[0];
            }

            var tr = document.createElement("tr");
            privateMethods.fillRowWithData.call(this, tr, bean);
            $(tbody).append(tr);
            $(tbody).find("tr.empty").remove();
        },
        removeRow: function (bean) {
            var tbody = this.find("tbody");
            tbody.find("tr[entityId=" + bean.id + "]").remove();

            var trList = tbody.find("tr");
            if (trList.length == 0) {
                var tr = privateMethods.getEmptyRow.call(this);
                $(tbody).append(tr);
            }
            privateMethods.setOddRows.call(this);

        },
        removeAll: function () {
            var tbody = this.find("tbody");
            tbody.empty();
            var tr = privateMethods.getEmptyRow.call(this);
            $(tbody).append(tr);
        },
        updateRow: function (bean) {
            var that = this;
            // find row
            var targetRow = null;
            var trList = this.find("tbody tr[entityId]");

            if (trList != null && trList.length > 0) {
                trList.each(function () {
                    var id = $(this).attr("entityId");
                    if (id && id == bean.id) {
                        targetRow = this;
                        return false;
                    }
                });
            }

            // update it
            if (targetRow != null) {
                privateMethods.fillRowWithData.call(that, targetRow, bean);
            }
        },
        fillRowWithData: function (tr, bean) {
            var that = this;
            var $options = this.data("options");
            var hasEditButton = $options.hasEditButton;
            var hasAddButton = $.isFunction($options.onAdd);
            var hasInfoButton = $.isFunction($options.onInfo);
            var hasProvisionButton = $options.hasProvisionButton;
            var hasDeprovisionButton = $options.hasProvisionButton;

            var deleteOptions = $options.deleteOptions;
            var hasDeleteBtn = (deleteOptions != null && deleteOptions != undefined)? $options.deleteOptions.hasDeleteBtn : true;
            var onDelete = (deleteOptions != null && deleteOptions != undefined) ? $options.deleteOptions.onDelete : null;
            var isDeletable = (deleteOptions != null && deleteOptions != undefined) ? $options.deleteOptions.isDeletable : function () {
                return true;
            };
            var hasDeleteButton = hasDeleteBtn && $.isFunction(onDelete) && isDeletable.call(this, bean);

            var onEdit = $options.onEdit;
            var onView = $options.onView;
            var onAdd = $options.onAdd;
            var onInfo = $options.onInfo;
            var onProvision = $options.onProvision;
            var onDeprovision = $options.onDeprovision;

            var columnsMap = $options.columnsMap;
            var useTrueFalseStringsOnBoolean = $options.useTrueFalseStringsOnBoolean;

            $(tr).empty();
            $(tr).data("entity", bean).attr("entityId", bean.id);

            var beanURL = null;
            if ($.isFunction($options.getEntityURL)) {
                beanURL = $options.getEntityURL(bean);
            } else {
                beanURL = $options.entityUrl;
                if (beanURL != "javascript:void(0);") {
                    beanURL = (beanURL + "?" + $options.entityURLIdentifierParamName + "=" + bean.id);
                }
            }

            if($options.isSelectAllowed){
                var td = document.createElement("td");
                $(td).addClass("checkCell");
                var chkBox = $(document.createElement("input")).attr("type", "checkbox").addClass("selectItemBtn");
                chkBox.change(function () {
                    var isChecked =$(this).is(':checked');
                    if(!isChecked){
                        $(this).parents("table:first").find(".selectAllBtn").prop('checked', false);
                        privateMethods.deselectItem.call(that, $(this));
                    } else {
                        privateMethods.selectItem.call(that, $(this));
                    }
                    selectedItemsCount=selectedItems.length;

                    if($options.onCheckCallback && $.isFunction($options.onCheckCallback)){
                        $options.onCheckCallback.call(that);
                    }
                });
                $(td).append(chkBox);
                $(tr).append(td);
            }

            for (var i = 0; i < columnsMap.length; i++) {
                var fieldName = columnsMap[i];
                var td = document.createElement("td");
                if ($.isFunction(fieldName)) {
                    td.append($(document.createElement("span")).text(fieldName.call(this, bean)));
                    $(tr).append(td);
                }
                else if (bean.hasOwnProperty(fieldName)) {
                    var isDate = ($.inArray(fieldName, $options.dateFields) > -1);
                    if (fieldName == "name") {
                        var beanA = null;
                        if ($options.hideLink) {
                            beanA = $(document.createElement("span")).text(bean.name);
                        } else {
                            beanA = document.createElement("a");
                            beanA.innerHTML = bean.name;
                            if (onView) {
                                beanA.href = "javascript:void(0)";
                                $(beanA).attr("entityId", bean.id);
                                $(beanA).click(function () {
                                    onView($(this).parents("tr:first").data("entity"));
                                    return false;
                                });
                            } else if (onEdit) {
                                beanA.href = "javascript:void(0)";
                                $(beanA).attr("entityId", bean.id);
                                $(beanA).click(function () {
                                    onEdit($(this).parents("tr:first").data("entity"));
                                    return false;
                                });
                            } else {
                                beanA.href = beanURL;
                            }
                        }
                        $(td).append(beanA);
                    } else if (bean[fieldName] == true || bean[fieldName] == "true") {
                        if (useTrueFalseStringsOnBoolean) {
                            td.innerHTML = "true"
                        } else {
                            var checkImg = document.createElement("div");
                            $(checkImg).addClass("openiam-check-icon");
                            $(td).css("text-align", "center").append(checkImg);
                        }
                    } else if (bean[fieldName] == null || bean[fieldName] == undefined || bean[fieldName] == false || bean[fieldName] == "false") {
                        var isBoolean = bean[fieldName] == false || bean[fieldName] == "false";
                        if (isBoolean && useTrueFalseStringsOnBoolean) {
                            td.innerHTML = "false"
                        } else {
                            td.innerHTML = "";
                        }
                    } else if (isDate) {
                        td.innerHTML = new Date(bean[fieldName]).toLocaleDateString()
                    } else {
                        td.innerHTML = bean[fieldName];
                    }
                    $(tr).append(td);
                }

                if (!$options.preventOnclickEvent) {
                    $(td).off("click");
                    $(td).click(function () {
                        if (!onEdit)
                            window.location.href = beanURL;
                        return false;
                    });
                }
            }

            if (hasDeleteButton || hasEditButton || hasAddButton || hasInfoButton || hasProvisionButton || hasDeprovisionButton) {
                var td6 = $(document.createElement("td")).addClass("delete");
                var a, img, alt;
                if (hasEditButton) {
                    a = $(document.createElement("a")).attr("href", "javascript:void(0)").attr("entityId", bean.id);
                    img = $(document.createElement("div")).addClass("openiam-edit-icon").addClass("openiam-image-option").addClass("alt-icon");
                    alt = document.createElement("span");
                    alt.innerHTML = localeManager["openiam.ui.common.edit"];
                    $(img).append(alt);
                    $(a).append(img);
                    $(a).click(function () {
                        if (onEdit)
                            onEdit($(this).parents("tr:first").data("entity"), $options);
                        return false;
                    });
                    $(td6).append(a);
                }
                if (hasDeleteButton) {
                    a = $(document.createElement("a")).attr("href", "javascript:void(0)").attr("entityId", bean.id);
                    img = $(document.createElement("div")).addClass("openiam-delete-icon").addClass("openiam-image-option").addClass("alt-icon");
                    alt = document.createElement("span");
                    alt.innerHTML = localeManager["openiam.ui.common.delete"];
                    $(img).append(alt);
                    $(a).append(img);
                    $(a).click(function () {
                        if (deleteOptions.preventWarning) {
                            onDelete($(a).parents("tr:first").data("entity"));
                        } else {
                            OPENIAM.Modal.Warn({
                                message: deleteOptions.warningMessage,
                                buttons: true,
                                OK: {
                                    text: localeManager["openiam.ui.common.execute"],
                                    onClick: function () {
                                        OPENIAM.Modal.Close();
                                        onDelete($(a).parents("tr:first").data("entity"));
                                    }
                                },
                                Cancel: {
                                    text: localeManager["openiam.ui.common.cancel"],
                                    onClick: function () {
                                        OPENIAM.Modal.Close();
                                    }
                                }
                            });
                        }
                        return false;
                    });
                    $(td6).append(a);
                }
                if (hasAddButton) {
                    a = $(document.createElement("a")).attr("href", "javascript:void(0)").attr("entityId", bean.id);
                    img = $(document.createElement("div")).addClass("openiam-add-icon").addClass("openiam-image-option").addClass("alt-icon");
                    alt = document.createElement("span");
                    alt.innerHTML = localeManager["openiam.ui.common.add"];
                    $(img).append(alt);
                    $(a).append(img);
                    $(a).click(function () {
                        onAdd($(this).parents("tr:first").data("entity"));
                        return false;
                    });
                    $(td6).append(a);
                }
                if (hasInfoButton) {
                    a = $(document.createElement("a")).attr("href", "javascript:void(0)").attr("entityId", bean.id);
                    img = $(document.createElement("div")).addClass("openiam-info-icon").addClass("openiam-image-option").addClass("alt-icon");
                    alt = document.createElement("span");
                    alt.innerHTML = localeManager["openiam.ui.common.information"];
                    $(img).append(alt);
                    $(a).append(img);
                    $(a).click(function () {
                        onInfo($(this).parents("tr:first").data("entity"));
                        return false;
                    });
                    $(td6).append(a);
                }
                if (hasProvisionButton) {
                    a = $(document.createElement("a")).attr("href", "javascript:void(0)").attr("entityId", bean.id);
                    img = $(document.createElement("div")).addClass("openiam-prov-icon").addClass("openiam-image-option").addClass("alt-icon");
                    alt = document.createElement("span");
                    alt.innerHTML = localeManager["openiam.ui.common.provision"];
                    $(img).append(alt);
                    $(a).append(img);
                    $(a).click(function () {
                        OPENIAM.Modal.Warn({
                            message: localeManager["openiam.ui.provision.confirmation.message"],
                            buttons: true,
                            OK: {
                                text: localeManager["openiam.ui.common.execute"],
                                onClick: function () {
                                    OPENIAM.Modal.Close();
                                    onProvision($(a).parents("tr:first").data("entity"));
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
                    $(td6).append(a);
                }
                if (hasDeprovisionButton) {
                    a = $(document.createElement("a")).attr("href", "javascript:void(0)").attr("entityId", bean.id);
                    img = $(document.createElement("div")).addClass("openiam-deprov-icon").addClass("openiam-image-option").addClass("alt-icon");
                    alt = document.createElement("span");
                    alt.innerHTML = localeManager["openiam.ui.common.deprovision"];
                    $(img).append(alt);;
                    $(a).append(img);
                    $(a).click(function () {
                        OPENIAM.Modal.Warn({
                            message: localeManager["openiam.ui.deprovision.confirmation.message"],
                            buttons: true,
                            OK: {
                                text: localeManager["openiam.ui.common.execute"],
                                onClick: function () {
                                    OPENIAM.Modal.Close();
                                    onDeprovision($(a).parents("tr:first").data("entity"));
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
                    $(td6).append(a);
                }
                $(tr).append(td6);
            }
        },
        setOddRows: function () {
            var tbody = this.find("tbody");
            var trList = tbody.find("tr");
            if (trList.length > 0 && !$(trList[0]).hasClass("empty")) {
                tbody.find("tr:odd").removeClass("odd").removeClass("even").addClass("odd");
                tbody.find("tr:even").removeClass("odd").removeClass("even").addClass("even");
            }
        },
        getEmptyRow: function () {
            var $options = this.data("options");
            var columnHeaders = $options.columnHeaders;
            var hasDeleteButton = $options.hasDeleteButton;
            var hasEditButton = $options.hasEditButton;
            var hasInfoButton = $.isFunction($options.onInfo);
            var hasProvisionButton = $.isFunction($options.onProvision);
            var hasDeprovisionButton = $.isFunction($options.onDeprovision);

            var emptyResultsText = $options.emptyResultsText;

            var tr = document.createElement("tr");
            var td = document.createElement("td");
            td.className = "empty";
            $(td).attr("colspan", columnHeaders.length);
            td.innerHTML = emptyResultsText;
            $(tr).addClass("empty").append(td);

            return tr;
        },
        isAdded: function (bean) {
            var tbody = this.find("tbody");
            var found = tbody.find("tr[entityId=" + bean.id + "]");
            return (found != null && found != undefined && found.length > 0);
        },
        rowCount: function () {
            return this.find("tbody tr:not(tr.empty)").length;
        },
        selectItem: function(checkBox){
            var idToAdd =  checkBox.parents("tr:first").attr("entityId");
            if($.inArray(idToAdd, selectedItems)==-1){
                selectedItems.push(idToAdd);
            }
        },
        deselectItem: function(checkBox){
            var idToRemove =  $(checkBox).parents("tr:first").attr("entityId");
            selectedItems = $.grep(selectedItems, function(item){
                return item!=idToRemove;
            });
        },
        tableSort: function (table, page, from) {
            var that = this;
            var $options = this.data("options");
            var pageSize = $options.pageSize;
            var found = parseInt(this.data("totalSize")) || 0;
            var start = from;
            var currentPage = Math.ceil(start / pageSize);
            var numTotalPages = Math.ceil(found / pageSize);

            $(table).tablesorter({
                debug: false,
                sortDisabled: true,
                widgets: ['zebra'] });
            if ($options.pageSize != -1) {
                $(table).tablesorterPager({
                    container: $(table).find(".pager"),
                    positionFixed: false,
                    size: pageSize,
                    page: currentPage,
                    totalRows: found,
                    totalPages: numTotalPages,
                    onFirstClick: function () {
                        privateMethods.request.call(that, 0);
                    },
                    onLastClick: function () {
                        privateMethods.request.call(that, numTotalPages - 1);
                    },
                    onPrevClick: function () {
                        privateMethods.request.call(that, currentPage - 1);
                    },
                    onNextClick: function () {
                        privateMethods.request.call(that, currentPage + 1);
                    }
                });

                if ($options.showPageSizeSelector) {
                    var footTd = $(table).find(".pager td:first");


                    var div1 = document.createElement("div");
                    $(div1).css("float", "left").css("width", "30%").html("&nbsp;");
                    var div2 = document.createElement("div");
                    $(div2).css("float", "left").css("width", "40%");

                    footTd.children().wrapAll(div2);

                    var div3 = document.createElement("div");
                    $(div3).css("float", "left").css("width", "30%");

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
                        $options.pageSize = $(this).val();
                        that.data("options", $options);
                        privateMethods.request.call(that, 0);
                    });

                    $(div3).append(pagesizeCtrl);
                    footTd.prepend(div1).append(div3);

                }
            }
        }
    };

    var methods = {
        init: function (args) {
            var options = $.extend({
                pageSize: 20, /* the number of results to display per page */
                ajaxURL: null, /* the URL to which the request to get the data will be made*/
                onInternalError: OPENIAM.Modal.Error, /* when an error occurs, this function will be called */
                internalErrorText: localeManager["openiam.ui.internal.error"], /* when an error occurs, the onInternalError function will be called with this text as an argument */
                emptyResultsText: null, /* when there are no results, display this text in the table */
                columnHeaders: null, /* an array of column headers */
                entityUrl: "javascript:void(0);", /* the link of the entity being displayed in the table */
                theadInputElements: null, /* an array of additional elements to append to thead */
                entityURLIdentifierParamName: "id", /* when linking to an elements from the ajax call, what is the name of the parameters?  i.e. editResource.html?id= */
                requestParamIdName: "id", /*when making a call to ajaxURL, what is the name of the 'id' parameter to send? */
                requestParamIdValue: null, /* the value of the above parameter */
                hasEditButton: false, /* does this table have a edit button? */
                hasProvisionButton: false, /* does this table have a provision button? */
                hasDeprovisionButton: false, /* does this table have a de-provision button? */
                onEdit: null, /* call this function when 'edit' button clicked */
                onView: null, /* call this function when 'view' */
                onAdd: null,
                onAppendDone: null, /* call this function when the table is appended to the DOM */
                entityType: "BASIC",
                dateFields: {},
                useTrueFalseStringsOnBoolean: false, /* uses the string 'true' or 'false' when finding a boolean, instead of a check */
                columnsMap: null, /*an array to map bean fields to columns. must be in the same order as columnHeaders*/
                getAdditionalDataRequestObject: null, /* function to call that gets the request object to send via ajax */
                preventOnclickEvent: false,
                getEntityURL: null,
                onEmptyResults: function () {
                },
                onNonEmptyResults: function () {
                },
                getData: null, /* when defined, this function is called instead of the default ajax request */
                validate: null, /* validation function befor send ajax request for data*/
                hideLink: false,
                onInfo: null,
                onProvision: null,
                onDeprovision: null,
                showPageSizeSelector: false,
                message: null,
                sortEnable: false,
                defaultSortColumnIndex: 0,
                deleteOptions: null, /* contains deletion options */
                isSelectAllowed:false,
                onCheckCallback: null
            }, args);

            if (options.columnHeaders == null) {
                $.error("columnHeaders is empty");
            }

            if (options.getData == null && options.ajaxURL == null) {
                $.error("ajaxURL and getData is empty");
            }

            if (options.entityUrl == null) {
                $.error("entityUrl is empty");
            }

            if (options.entityURLIdentifierParamName == null) {
                $.error("entityUrl is empty");
            }

            if (args.deleteOptions != null && typeof(args.deleteOptions) === "object") {
                options.deleteOptions = $.extend({
                    hasDeleteBtn:true,
                    onDelete: null,
                    preventWarning: false,
                    warningMessage: localeManager["openiam.ui.delete.confirmation.message"],
                    isDeletable: function () {
                        return true
                    }
                }, args.deleteOptions);

                if (!$.isFunction(options.deleteOptions.onDelete)) {
                    $.error("deleteOptions.onDelete is empty");
                }
            }

            if (options.hasEditButton) {
                if (options.onEdit == null) {
                    $.error("onEdit is empty");
                }
            }

            if (options.hasProvisionButton) {
                if (options.onProvision == null) {
                    $.error("onProvision is empty");
                }
            }

            if (options.hasDeprovisionButton) {
                if (options.onDeprovision == null) {
                    $.error("onDeprovision is empty");
                }
            }

            if (options.entityUrl == "javascript:void(0);" && options.onView == null) {
                options.hideLink = true;
            }
            selectedItems = [];
            selectedItemsCount = 0;

            options = $.extend(options, {initialSortColumn: options.columnsMap[0], initialSortOrder: "ASC"});
            this.data("options", options);
            privateMethods.request.call(this, 0);
            this.show();
        },
        addRow: function (bean) {
            privateMethods.drawRow.call(this, bean, null);
            privateMethods.setOddRows.call(this);
        },
        isAdded: function (bean) {
            return privateMethods.isAdded.call(this, bean);
        },
        deleteRow: function (bean) {
            privateMethods.removeRow.call(this, bean);
        },
        deleteAll: function () {
            privateMethods.removeAll.call(this);
        },
        updateRow: function (bean) {
            privateMethods.updateRow.call(this, bean);
        },
        rowCount: function () {
            return privateMethods.rowCount.call(this);
        },
        setOddRows: function () {
            privateMethods.setOddRows.call(this);
        },
        countSelectedItems:function(){
            return selectedItemsCount;
        },
        getSelectedItems:function(){
            return selectedItems;
        }
    };

    $.fn.entitlemetnsTable = function (method) {
        if (methods[method]) {
            return methods[ method ].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === 'object' || !method) {
            return methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.entitlemetnsTable');
        }
    };
})(jQuery);