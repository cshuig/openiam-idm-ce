OPENIAM = window.OPENIAM || {};
OPENIAM.BulkOperationsSearchResource = {

    MetaData : {},

    init : function() {
        $("#searchResource").click(function() {
            if ($.isEmptyObject(OPENIAM.BulkOperationsSearchResource.MetaData)) {
                OPENIAM.BulkOperationsSearchResource.requestMetadata();
            }
            OPENIAM.BulkOperationsSearchResource.drawDialog();
        });

        $("#clearResource").click(function() {
            OPENIAM.BulkOperationsSearchResource.clear();
        });
    },

    drawDialog: function() {
        var html = document.createElement("div");

        var header = document.createDocumentFragment();
        var headerTable = document.createElement("table");
        headerTable.width = "100%";
        var headerTBody = document.createElement("tbody");
        headerTable.appendChild(headerTBody);

        //row1
        var r1 = headerTBody.insertRow(0);
        var r1cl = r1.insertCell(0);
        var r1c1Label = document.createElement("label");
        r1c1Label.innerHTML = localeManager["openiam.ui.shared.resource.name"] + ": ";
        r1cl.appendChild(r1c1Label);

        var r1c2 = r1.insertCell(1);
        var resourceNameInput = document.createElement("input");
        resourceNameInput.id = "searchName";
        resourceNameInput.type = "text";
        resourceNameInput.className = "full rounded ctrlElement";
        resourceNameInput.autocomplete = "off";
        r1c2.appendChild(resourceNameInput);

        //row2
        var r2 = headerTBody.insertRow(1);
        var r2c1 = r2.insertCell(0);
        var r2c1Label = document.createElement("label");
        r2c1Label.innerHTML = localeManager["openiam.ui.common.resource.type"] + ": ";
        r2c1.appendChild(r2c1Label);

        var r2c2 = r2.insertCell(1);

        var metadata = OPENIAM.BulkOperationsSearchResource.MetaData;
        if (!$.isEmptyObject(metadata)) {
            var metaDataBeans = metadata.resourceTypeList;
            var resourceTypeSelect = document.createElement("select");
//            resourceTypeSelect.multiple="multiple";
//            resourceTypeSelect.size = $(metaDataBeans) ? (Math.min($(metaDataBeans).size(), 5)) : 5;
            resourceTypeSelect.id = "searchResourceType";
            resourceTypeSelect.className = "ctrlElement";
            var option = document.createElement("option");
            option.value = '';
            option.text = localeManager["openiam.ui.common.please.select"];
            resourceTypeSelect.appendChild(option);
            $(metaDataBeans).each(function(i, e) {
                var option = document.createElement("option");
                option.value = e.id;
                option.text = e.name;
                resourceTypeSelect.appendChild(option);
            });
            r2c2.appendChild(resourceTypeSelect);
        }

        //row3
        var r3 = headerTBody.insertRow(2);
        var r3c1 = r3.insertCell(0);
        var r3c1Label = document.createElement("label");
        r3c1Label.innerHTML = localeManager["openiam.ui.common.resource.risk"] + ": ";
        r3c1.appendChild(r3c1Label);

        var r3c2 = r3.insertCell(1);

        if (!$.isEmptyObject(metadata)) {
            var metaDataBeans2 = metadata.resourceRiskList;
            var resourceRiskSelect = document.createElement("select");
//            resourceRiskSelect.multiple="multiple";
//            resourceRiskSelect.size = $(metaDataBeans2) ? (Math.min($(metaDataBeans2).size(), 5)) : 5;
            resourceRiskSelect.id = "searchRiskType";
            resourceRiskSelect.className = "ctrlElement";
            var option = document.createElement("option");
            option.value = '';
            option.text = localeManager["openiam.ui.common.please.select"];
            resourceRiskSelect.appendChild(option);
            $(metaDataBeans2).each(function(i, e) {
                var option = document.createElement("option");
                option.value = e.id;
                option.text = e.name;
                resourceRiskSelect.appendChild(option);
            });
            r3c2.appendChild(resourceRiskSelect);
        }

        //row4
        var r4 = headerTBody.insertRow(3);
        var r4cl = r4.insertCell(0);
        var r4c1Label = document.createElement("label");
        r4c1Label.innerHTML = localeManager["openiam.ui.common.attribute.name"] + ": ";
        r4cl.appendChild(r4c1Label);

        var r4c2 = r4.insertCell(1);
        var attributeNameInput = document.createElement("input");
        attributeNameInput.id = "searchAttrName";
        attributeNameInput.type = "text";
        attributeNameInput.className = "full rounded ctrlElement";
        attributeNameInput.autocomplete = "off";
        r4c2.appendChild(attributeNameInput);

        //row5
        var r5 = headerTBody.insertRow(4);
        var r5cl = r5.insertCell(0);
        var r5c1Label = document.createElement("label");
        r5c1Label.innerHTML = localeManager["openiam.ui.common.attribute.value"] + ": ";
        r5cl.appendChild(r5c1Label);

        var r5c2 = r5.insertCell(1);
        var attributeValueInput = document.createElement("input");
        attributeValueInput.id = "searchAttrValue";
        attributeValueInput.type = "text";
        attributeValueInput.className = "full rounded ctrlElement";
        attributeValueInput.autocomplete = "off";
        r5c2.appendChild(attributeValueInput);

        //footer
        var headerTFoot = headerTable.createTFoot();
        var fr = headerTFoot.insertRow(0);
        var fe = fr.insertCell(0);
        var fc = fr.insertCell(1);

        // create buttons
        var btnContainer = document.createElement("div");
        fc.appendChild(btnContainer);

        var ul = document.createElement("ul");
        btnContainer.appendChild(ul);

        ul.className = "formControls";
        var li1 = document.createElement('li');
        var cancelBtn = document.createElement('input');
        cancelBtn.type = "button";
        cancelBtn.id = "cancelBtn";
        cancelBtn.value = localeManager["openiam.ui.common.cancel"];
        cancelBtn.className = "whiteBtn";
        li1.appendChild(cancelBtn);
        ul.appendChild(li1);

        var li2 = document.createElement('li');
        var searchBtn = document.createElement('input');
        searchBtn.type = "button";
        searchBtn.id = "searchBtn";
        searchBtn.value = localeManager["openiam.ui.button.search"];
        searchBtn.className = "redBtn";
        li2.appendChild(searchBtn);
        ul.appendChild(li2);

        header.appendChild(headerTable);
        html.appendChild(header);

        var body = document.createDocumentFragment();
        var dataContentArea = document.createElement("div");
        body.appendChild(dataContentArea);
        dataContentArea.id = "dataContent";
        dataContentArea.style.paddingTop = "5px";

        html.appendChild(body);

        $(cancelBtn).click(function() {
            $("#dialog").dialog("close");
        });

        $(searchBtn).click(function() {
            var searchReqObj = {};
            searchReqObj.excludeResourceType = "MENU_ITEM";
            searchReqObj.name = $(resourceNameInput).val();
            searchReqObj.resourceTypeId = $(resourceTypeSelect).val();
            searchReqObj.risk = $(resourceRiskSelect).val();
            searchReqObj.attributeName = $(attributeNameInput).val();
            searchReqObj.attributeValue = $(attributeValueInput).val();
            searchReqObj.from = 0;
            searchReqObj.size = 5;

            OPENIAM.BulkOperationsSearchResource.requestData(dataContentArea, searchReqObj);

        });

        $("#dialog").html(html).dialog({
            autoOpen : true,
            draggable : true,
            resizable : false,
            title : localeManager["openiam.ui.shared.resource.search"],
            width : 400,
            maxWidth : 600,
            position: { at: "top" }
        });
    },

    requestData : function(element, searchReqObj) {
        $.ajax({
            "url" : "rest/api/entitlements/searchResources",
            "data" : searchReqObj,
            type: "GET",
            dataType : "json",
            contentType: "application/json",
            success : function(data, textStatus, jqXHR) {
                OPENIAM.BulkOperationsSearchResource.renderData(element, searchReqObj, data);
            },
            error : function(jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
    },

    renderData : function(dataContentArea, searchReqObj, dataBean) {

        var dataTable = document.createDocumentFragment();
        var dt = document.createElement("table");
        dataTable.appendChild(dt);
        dt.width = "100%";
        dt.cellSpacing = 1;
        dt.className = "yui";

        // header
        var dth = dt.createTHead();
        var dthr1 = dth.insertRow(0);

        var dthr1c1 = dthr1.appendChild(document.createElement("th"));
        dthr1c1.className = "header";
        dthr1c1.innerHTML = localeManager["openiam.ui.shared.resource.name"];

        var dthr1c2 = dthr1.appendChild(document.createElement("th"));
        dthr1c2.className = "header";
        dthr1c2.innerHTML = localeManager["openiam.ui.shared.resource.type"];

        var dthr1c3 = dthr1.appendChild(document.createElement("th"));
        dthr1c3.className = "header";
        dthr1c3.innerHTML = localeManager["openiam.ui.common.risk"];

        var dthr1c4 = dthr1.appendChild(document.createElement("th"));
        dthr1c4.className = "header";
        dthr1c4.innerHTML = localeManager["openiam.ui.common.actions"];

        // body
        var dtb = document.createElement("tbody");
        dt.appendChild(dtb);

        if ($.isEmptyObject(dataBean) || $.isEmptyObject(dataBean.beans) || dataBean.size == 0 ) {
            //no data
            $(dataContentArea).data("size", 0);
            var dtbr1 = dtb.insertRow(0);
            $(dtbr1).attr("entityid", "");
            dtbr1.className = "even";

            var dtbr1c1 = dtbr1.insertCell(0);
            dtbr1c1.colSpan = "4";
            dtbr1c1.innerHTML = localeManager["openiam.ui.shared.resource.search.empty"];

        } else {
            if (dataBean.size) {
                $(dataContentArea).data("size", dataBean.size);
            }
            $(dataBean.beans).each(function(i, bean) {
                var dtbr = dtb.insertRow(i);
                $(dtbr).attr("entityid", bean.id);
                dtbr.className = (i%2==0) ? "even" : "odd";

                var dtbrc1 = dtbr.insertCell(0);
                dtbrc1.innerHTML = bean.name? bean.name : '';

                var dtbrc2 = dtbr.insertCell(1);
                dtbrc2.innerHTML = bean.resourceType? bean.resourceType : '';

                var dtbrc3 = dtbr.insertCell(2);
                dtbrc3.innerHTML = bean.risk? bean.risk : '';

                var dtbrc4 = dtbr.insertCell(3);
                dtbrc4.style.textAlign = "center";

                var a = document.createElement('a');
                dtbrc4.appendChild(a);
                a.id = i;
                a.className = "icon-link add16";
                a.href = "javascript:void(0);";

                $(a).click(function() {
                    $("#resourceId").val(bean.id).change();
                    $("#resourceName").val(bean.name).change();
                    $("#dialog").dialog("close");
                });

            });
        }

        // footer (pagination)
        var dtf = dt.createTFoot();
        var dtfr = dtf.insertRow(0);
        dtfr.className = "pager";
        var dtfrt = dtfr.insertCell(0);
        dtfrt.colSpan = "4";

        var first = document.createElement("img");
        first.src = "/openiam-ui-static/plugins/tablesorter/img/first.png";
        first.className = "first disabled";

        var prev = document.createElement("img");
        prev.src = "/openiam-ui-static/plugins/tablesorter/img/prev.png";
        prev.className = "prev disabled";

        var pageInput = document.createElement("input");
        pageInput.className = "pagedisplay";

        var next = document.createElement("img");
        next.src = "/openiam-ui-static/plugins/tablesorter/img/next.png";
        next.className = "next disabled";

        var last = document.createElement("img");
        last.src = "/openiam-ui-static/plugins/tablesorter/img/last.png";
        last.className = "last disabled";

        // pagination
        var start = searchReqObj.from;
        var currentPage = Math.ceil(start / searchReqObj.size);
        var totalPages = Math.ceil($(dataContentArea).data("size") / searchReqObj.size);

        $(pageInput).val(currentPage + (totalPages != 0 ? 1 : 0) + '/' + totalPages);
        if (totalPages > 0 && currentPage < totalPages-1) {
            $(next).removeClass("disabled");
            $(last).removeClass("disabled");

            $(next).click(function() {
                searchReqObj.from += searchReqObj.size;
                OPENIAM.BulkOperationsSearchResource.requestData(dataContentArea, searchReqObj);
            });

            $(last).click(function() {
                searchReqObj.from = (totalPages-1) * searchReqObj.size;
                OPENIAM.BulkOperationsSearchResource.requestData(dataContentArea, searchReqObj);
            });

        }
        if (totalPages > 0 && currentPage > 0) {
            $(first).removeClass("disabled");
            $(prev).removeClass("disabled");

            $(first).click(function() {
                searchReqObj.from = 0;
                OPENIAM.BulkOperationsSearchResource.requestData(dataContentArea, searchReqObj);
            });

            $(prev).click(function() {
                searchReqObj.from -= searchReqObj.size;
                OPENIAM.BulkOperationsSearchResource.requestData(dataContentArea, searchReqObj);
            });
        }

        dtfrt.appendChild(first);
        dtfrt.appendChild(prev);
        dtfrt.appendChild(pageInput);
        dtfrt.appendChild(next);
        dtfrt.appendChild(last);

        if (dataContentArea.firstChild) {
            dataContentArea.replaceChild(dataTable, dataContentArea.firstChild);
        } else {
            dataContentArea.appendChild(dataTable);
        }

    },

    clear : function() {
        $("#resourceId").val('').change();
        $("#resourceName").val('').change();
    },

    requestMetadata : function() {
        $.ajax({
            url : "rest/api/metadata/resourceMetadata",
            "data" : null,
            type: "GET",
            dataType : "json",
            async: false,
            success : function(data, textStatus, jqXHR) {
                OPENIAM.BulkOperationsSearchResource.MetaData = data;
            },
            error : function(jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
                OPENIAM.BulkOperationsSearchResource.MetaData = {};
            }
        });
    }
};

$(document).ready(function() {
    OPENIAM.BulkOperationsSearchResource.init();
});

$(document).load(function() {
});