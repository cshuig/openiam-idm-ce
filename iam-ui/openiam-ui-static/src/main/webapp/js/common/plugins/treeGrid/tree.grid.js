console = window.console || {};
console.log = window.console.log || function() {};

(function( $ ){

    var privateMethods = {
        drawEmpty : function(tbody, numHeaderFields) {
            var $this = this;
            var options = $this.data("options");

            var tr = document.createElement("tr");
            var td = document.createElement("td"); $(td).attr("colspan", numHeaderFields + 1); td.className = "empty"; td.innerHTML = options.emptyMessage;
            $(tr).append(td);
            $(tbody).append(tr);
        },
        resetColors : function() {
            var $this = this;
            var options = $this.data("options");

            $this.find("tbody tr:visible").each(function(idx, val) {
                $(val).removeClass("even").removeClass("odd");
                var isEven = (idx == 0 || idx % 2 == 0);
                $(val).addClass(isEven ? "even" : "odd");
            });
        },
        addExpander: function(td){
            var $this = this;
            var options = $this.data("options");

            var sp = document.createElement("span");
            $(sp).addClass("openiam-treegrid-expander").attr("title","Click to expand/collapse");
            $(td).append(sp);

            $(sp).click(function(){
                var pr = $(this).parents("tr:first");
                if($(pr).hasClass("openiam-treegrid-expanded")){
                    options.onCollapse.call($this, pr);
                } else{
                    pr.addClass("openiam-treegrid-expanded");
                    options.onExpand.call($this, pr);
                }
            });

            $(sp).dblclick(function() {
                var pr = $(this).parents("tr:first");
                if(!$(pr).hasClass("openiam-treegrid-expanded")){
                    pr.addClass("openiam-treegrid-expanded");
                    privateMethods.expandRecursive.call($this, pr);
                }
            });
        },
        addIndent: function(td){
            var sp = document.createElement("span");
            sp.className="openiam-treegrid-indent";
            $(td).append(sp);
        },
        getChildNodes:function(row){
            return row.parent().find("tr[parent-row="+row.attr("id")+"]");
        },
        isLeaf:function(row){
            privateMethods.getChildNodes(row).length===0;
        },
        expand:function(row){
            var $this = this;
            var children = privateMethods.getChildNodes.call($this, row);
            children.css("display","table-row");
            privateMethods.resetColors.call($this);
        },
        expandRecursive:function(row){
            var $this = this;
            row.addClass("openiam-treegrid-expanded");
            var children = privateMethods.getChildNodes.call($this, row);
            children.css("display","table-row");
            children.each(function(){
                var tr = $(this);
                if(!privateMethods.isLeaf(tr)){
                    privateMethods.expandRecursive.call($this, tr);
                }
            });
            privateMethods.resetColors.call($this);
        },
        expandAll:function(){
            var $this = this;
            this.find("tr:not([parent-row])").each(function(){
                var tr = $(this);
                if(!privateMethods.isLeaf(tr)){
                    privateMethods.expandRecursive.call($this, tr);
                }
            });
            privateMethods.resetColors.call($this);
        },
        collapse:function(row){
            var $this = this;
            privateMethods.collapseRecursive.call($this, row);
            privateMethods.resetColors.call($this);
        },
        collapseRecursive:function(row){
            var $this = this;
            var children = privateMethods.getChildNodes.call($this, row);
            children.each(function(){
                var tr = $(this);
                if(!privateMethods.isLeaf(tr)){
                    privateMethods.collapseRecursive.call($this, tr);
                }
            });
            row.removeClass("openiam-treegrid-expanded").parent().find("tr[parent-row="+row.attr("id")+"]").css("display","none");
            privateMethods.resetColors.call($this);
        },
        collapseAll:function(){
            var $this = this;
            this.find("tr:not([parent-row])").each(function(){
                var tr = $(this);
                if(!privateMethods.isLeaf(tr)){
                    privateMethods.collapseRecursive.call($this, tr);
                }
            });
            privateMethods.resetColors.call($this);
        },
        drawColumn : function(tbody, obj, isEven, idCounter, parentId, level) {
            var $this = this;
            var options = $this.data("options");
            var fieldDisplayers = options.fieldDisplayers;
            var data = obj.data;


            var tr = document.createElement("tr"); if(obj.isDisableClicked) { $(tr).addClass("disabled");}
            $(tr).addClass(isEven ? "even" : "odd");
            if(obj.isException!=null && obj.isException!=undefined && obj.isException===true){
                $(tr).addClass("openiam-treegrid-exception");
            }


            if(parentId){
                $(tr).attr("parent-row", parentId);
            }
            $(tr).data("entity", obj).attr("id","row"+idCounter);

            if(parentId){
                $(tr).attr("parent-row", parentId).css("display","none");
            }

            if(options.checkboxEnabled){

                var ctd = document.createElement("td");
                if(options.createCheckboxChecker!=null && $.isFunction(options.createCheckboxChecker) && options.createCheckboxChecker(obj)){
                    var chb = document.createElement("input");
                    $(chb).attr("type", "checkbox");
                    if(options.checkedInitialState){
                        $(chb).prop('checked', true);
                    }
                    $(ctd).append(chb)
                }
                $(ctd).addClass("checkCell");
                if(!options.checkboxVisibility){
                    $(ctd).css("display","none");
                }
                $(tr).append(ctd);
            }

            for(var j = 0; j < options.fieldNames.length; j++) {
                var fieldName = options.fieldNames[j];
                var fieldValue = data[fieldName];

                if($.isFunction(fieldDisplayers[fieldName])) {
                    fieldValue = fieldDisplayers[fieldName](data, fieldName);
                }

                var td = document.createElement("td");
                if(j==0){
                    for(var i=0; i<level;i++){
                        privateMethods.addIndent.call($this, td);
                    }
                    if(obj.children && obj.children.length>0){
                        privateMethods.addExpander.call($this, td);
                    } else {
                        privateMethods.addIndent.call($this, td);
                    }
                    if(obj.icon){
                        var sp = document.createElement("span");
                        $(sp).addClass("openiam-treegrid-icon"); // obj.icon

                        if(obj.iconType){
                            $(sp).css("background-image", "url(data:image/"+obj.iconType+";base64,"+obj.icon+")");
                        } else {
                            $(sp).addClass(obj.icon);
                        }

                        if(options.showIconTitle){
                            var iconTitle = (obj.iconDescription)? obj.iconDescription : options.getIconTitle.call($this, obj.icon);
                            $(sp).attr("title",iconTitle);
                        }
                        $(td).append(sp);
                    }

                    $(td).addClass("treegrid-first-column");
                }
                if(fieldValue != null && fieldValue != undefined) {
                    var sp = document.createElement("span");
                    $(sp).append(fieldValue).addClass("openiam-treegrid-value");
                    if(obj.isTerminate!=null && obj.isTerminate!=undefined && obj.isTerminate===true){
                        $(sp).addClass("openiam-treegrid-terminated");
                    }
                    $(td).append(sp);
                }
                $(tr).append(td);
            }

            if(options.actionsColumnName) {
                var td = document.createElement("td");
                if(options.editEnabledField === true || obj[options.editEnabledField]) {
                    var editA = document.createElement("a"); $(editA).attr("href", "javascript:void(0);");
                    var editImg = document.createElement("div"); $(editImg).addClass("openiam-edit-icon").addClass("openiam-image-option");
                    $(editA).append(editImg);
                    $(td).append(editA);
                    $(editA).click(function() {
                        options.onEditClick.call($this, obj);
                    });
                }
                if(options.deleteEnabledField === true || obj[options.deleteEnabledField]) {
                    if(obj.isDeletable!=null && obj.isDeletable!=undefined && obj.isDeletable===true
                        && !(obj.isTerminate!=null && obj.isTerminate!=undefined && obj.isTerminate===true)){
                        var deleteA = document.createElement("a"); $(editA).attr("href", "javascript:void(0);");
                        var deleteImg = document.createElement("div"); $(deleteImg).addClass("openiam-delete-icon").addClass("openiam-image-option");
                        $(deleteA).append(deleteImg);
                        $(td).append(deleteA);
                        $(deleteA).click(function() {
                            options.onDeleteClick.call($this, obj.data);
//                            if(options.greyOutOnDelete) {
//                                obj.isDisableClicked = (obj.isDisableClicked === true) ? false : true;
//                            } else {
//                                var idxToPurge = null;
//                                $.each(options.objectArray, function(idx, objAtIdx) {
//                                    if(options.equals(obj, objAtIdx)) {
//                                        idxToPurge = idx;
//                                    }
//                                });
//                                if(idxToPurge != null && idxToPurge != undefined) {
//                                    options.objectArray.splice(idxToPurge, 1);
//                                }
//                            }
//    //                        methods.onOrderEdit.call($this);
//                            options.onDeleteClick.call($this, obj);
//                            methods.draw.call($this);
                        });
                    }
                }
                $(tr).append(td);

            }
            $(tbody).append(tr);

            if(obj.children && obj.children.length>0){
                for(var i = 0; i < obj.children.length; i++) {
                    var child = obj.children[i];
                    privateMethods.drawColumn.call($this, tbody, child, ((i == 0) || ((i % 2) == 0)), idCounter+""+i, $(tr).attr("id"), level+1);
                }
            }
        }
    };

    var methods = {
        setPropertyOnAll : function(propertyName, propertyValue) {
            var $this = this;
            var options = $this.data("options");

            if(options.objectArray == null || options.objectArray == undefined) {
                options.objectArray = [];
            }

            $.each(options.objectArray, function(idx, obj) {
                obj[propertyName] = propertyValue;
            });
        },
        addObject : function(obj) {
            var $this = this;
            var options = $this.data("options");

            if(options.objectArray == null || options.objectArray == undefined) {
                options.objectArray = [];
            }

            var idxOfObj = null;
            $.each(options.objectArray, function(idx, objAtIdx) {
                if(options.equals(obj, objAtIdx)) {
                    idxOfObj = idx;
                }
            });

            if(idxOfObj == null) {
                options.objectArray.push(obj);
            }
        },
        draw : function() {
            var $this = this;
            var options = $this.data("options");

            var numHeaderFields = options.headerFields.length;

            var totalColCount = numHeaderFields;
            if(options.actionsColumnName)
                totalColCount = totalColCount + 1;
            if(options.checkboxEnabled && options.checkboxVisibility)
                totalColCount = totalColCount + 1;

            var tableTitle = options.tableTitle;

            var table = document.createElement("table"); $(table).css("width", "100%"); $(table).attr("cellspacing", "1"); table.className = "yui";
            var thead = document.createElement("thead");

            if(tableTitle != null) {
                var tr = document.createElement("tr");
                var td = document.createElement("td"); $(td).attr("colspan", totalColCount); td.innerHTML = tableTitle; td.className = "yui-title";
                $(tr).append(td);
                $(thead).append(tr);
            }

            if(options.createEnabled || options.filterEnabled) {
                var tr = document.createElement("tr");

                var td = document.createElement("td");
                $(td).attr("colspan", totalColCount);


                if(options.filterEnabled){
                    var dialog = null;
                    if($("#editDialog").length == 0) {
                        dialog = document.createElement("div"); dialog.id = "editDialog";
                        $(document.body).append(dialog);
                        dialog = $(dialog);
                    } else {
                        dialog = $("#editDialog");
                    }

                    var filterA = document.createElement("a"); $(filterA).attr("href", "javascript:void(0);");
                    var filterImg = document.createElement("div"); $(filterImg).addClass("openiam-filter-icon").addClass("openiam-image-option");
                    $(filterA).append(filterImg).css("float","right").css("margin-right","5px").css("margin-left","5px");
                    $(filterA).click(function() {
                        $(dialog).modalEdit({
                                        fields: options.filterFields,
                                        dialogTitle: localeManager["openiam.ui.common.table.filter.data"],
                                        saveBtnTxt:  localeManager["openiam.ui.coomontable.apply.Filter"],
                                        onSubmit: function(bean){
                                            $(dialog).modalEdit("hide", null);
                                            options.onFilterClick.call($this, bean);
                                        }
                                    });
                        $(dialog).modalEdit("show", options.filterSearchBean);
                    });
                    $(td).append(filterA);
                }
                if(options.createEnabled){
                    var btn = document.createElement("button"); btn.className = "redBtn"; btn.innerHTML = options.createText;
                    if(options.createBtnId) {
                        btn.id = options.createBtnId;
                    }
                    $(btn).css("float","left").css("float","right").css("margin-right","5px").click(function() {
                        options.onCreateClick.call($this);
                        return false;
                    });
                    $(td).append(btn);
                }
                $(tr).append(td);

                $(thead).append(tr);
            }

            if(options.showHeader){
                var tr = document.createElement("tr");

                if(options.checkboxEnabled){

                    var th = document.createElement("th");
                    var chb = document.createElement("input");
                        $(chb).attr("type", "checkbox").attr("id","checkAll");
                        if(options.checkedInitialState){
                            $(chb).prop('checked', true);
                        }
                    $(chb).change(function () {
                        if ($(this).is(":checked")) {
                            //do the stuff that you would do when 'checked'
                            $($this).find("td.checkCell input[type=checkbox]").prop('checked', true);
                        } else {
                            $($this).find("td.checkCell input[type=checkbox]").prop('checked', false);
                        }
                    });
                    $(th).addClass("checkCell").append(chb);
                    if(!options.checkboxVisibility){
                        $(th).css("display","none");
                    }
                    $(tr).append(th);
                }

                for(var i = 0; i < numHeaderFields; i++) {
                    var headerField = options.headerFields[i];
                    var th = document.createElement("th"); th.innerHTML = headerField;
                    $(tr).append(th);
                }
                if(options.actionsColumnName) {
                    var th = document.createElement("th"); th.innerHTML = options.actionsColumnName;
                    $(tr).append(th);
                }
                $(thead).append(tr);
            }


            var tbody = document.createElement("tbody");
            if(options.objectArray == null || options.objectArray == undefined || options.objectArray.length == 0) {
                privateMethods.drawEmpty.call($this, tbody, numHeaderFields);
            } else {
                var idCounter = new Date().getTime();
                for(var i = 0; i < options.objectArray.length; i++) {
                    var obj = options.objectArray[i];
                    privateMethods.drawColumn.call($this, tbody, obj, ((i == 0) || ((i % 2) == 0)), idCounter+""+i, null, 0);
                }
            }
            $(table).append(thead, tbody);
            $(this).html(table);
        },
        showCheckBoxes:function(){
            var $this = this;
            var options = $this.data("options");

            $(this).find(".checkCell").show();
            var title = $(this).find(".yui-title");

            var colspan = title.attr("colspan")*1;
            title.attr("colspan", colspan+1);
            options.checkboxVisibility=true;
            $this.data("options", options);
        },
        hideCheckBoxes:function(){
            var $this = this;
            var options = $this.data("options");

            $(this).find(".checkCell").hide();
            var title = $(this).find(".yui-title");

            var colspan = title.attr("colspan")*1;
            title.attr("colspan", colspan-1);
            options.checkboxVisibility=false;
            $this.data("options", options);
        },
        getSelectedItems:function(){
            var $this = this;
            var options = $this.data("options");
            var selectedElements = [];
            $(this).find("td.checkCell input[type=checkbox]:checked").each(function(){
                var tr = $(this).parents("tr:first");
                var obj = tr.data("entity")
                selectedElements.push(obj.data);
            });
            return selectedElements;
        },
        getBeans: function(){
            var $this = this;
            var options = $this.data("options");
            var beans = [];
            $(this).find("tbody tr").each(function(){
                var tr = $(this);
                var obj = tr.data("entity")
                beans.push(obj.data);
            });
//            if(options.objectArray != null && options.objectArray != undefined && options.objectArray.length > 0) {
//                for(var i = 0; i < options.objectArray.length; i++) {
//                    var obj = options.objectArray[i];
//                    if(obj.data){
//                        beans.push(obj.data);
//                    }
//                }
//            }

            return beans;
        },
        init : function( args ) {
            var $this = this;
            var options = $.extend({
                emptyMessage : null, /* message to show when there are no elements */
                createEnabled : false, /* is creation of this element enabled? */
                objectArray : [], /* the array of entities - JSON object from backend */
                headerFields : null, /* array of header fields */
                fieldNames : null, /* array of fieldNames in the underlying JSON Object, that are used to display the values in the table */
                deleteEnabledField : false, /* the field name that determines if deletion is enabled */
                editEnabledField : false, /* the field name that determines if edit is enabled on the JSON object */
                createText : "", /* text of the 'create' button */
                actionsColumnName : null, /* name of the 'Actions' column */
                tableTitle : null, /* title of the table */
                greyOutOnDelete : false,
                createBtnId : null,
                fieldDisplayers : {},
                showHeader: true,
                filterEnabled: false,
                filterFields: [],
                filterSearchBean:null,
                showIconTitle:false,
                checkboxEnabled:false,
                checkedInitialState:false,
                checkboxVisibility:true,
                createCheckboxChecker:function(obj){
                    return true;
                },
                getIconTitle: function(iconName){
                },
                equals : function(obj1, obj2) { /* method to determine if two objects are equal - used by delete and create */

                },
                onDeleteClick : function(obj) {

                },
                onEditClick : function(obj) {

                },
                onCreateClick : function() {

                },
                onExpand : function(row) {
                    privateMethods.expand.call(this, row);
                },
                onCollapse : function(row) {
                    privateMethods.collapse.call(this, row);
                },
                onFilterClick : function(searchBean){

                }
            }, args);

            if(options.objectArray == null) {
                options.objectArray = [];
            }

            /*
             for(var i in options) {
             if(options[i] == null) {
             $.error(options[i] + " not passed as an argument for " + i);
             }
             }
             */

            $this.data("options", options);
            methods.draw.call(this);
        }
    };

    $.fn.treeGrid = function( method ) {
        if ( methods[method] ) {
            return methods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
        } else if ( typeof method === 'object' || ! method ) {
            return methods.init.apply( this, arguments );
        } else {
            $.error( 'Method ' +  method + ' does not exist on jQuery.treeGrid' );
        }
    };
})( jQuery );