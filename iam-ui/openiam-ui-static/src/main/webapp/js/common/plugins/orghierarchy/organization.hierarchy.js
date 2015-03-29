console = window.console || {};
console.log = window.console.log || function() {
};

(function($) {
    var cssDependencies = [
        "/openiam-ui-static/js/common/plugins/modalsearch/modal.search.css",
    ];

    var javascriptDependencies = [
        "/openiam-ui-static/js/common/search/organization.search.js",
        "/openiam-ui-static/js/common/plugins/modalEdit/modalEdit.js"
    ];

    var privateMethods = {
        draw : function() {
            var $this = this;
            var options = $this.data("options");
            var typeIds = options.typeIds;

            var selectList = {};
            options.selectList = selectList;

            if(("#editDialog").length==0){
                var searchDialog = document.createElement("div");
                searchDialog.id="editDialog";
                $(document.body).append(searchDialog);
            }
            var resultDialog = document.createElement("div");
            resultDialog.id="organizationSearchResult";
            $(document.body).append(resultDialog);

            for (var i = 0; i < typeIds.length; i++) {
                var div = document.createElement("div");
                    div.id=typeIds[i].displayName + "Container";

                var a = document.createElement("a");
                    a.href="javascript:void(0);"
                    a.className="organizationLink entity-link organization";
                    a.innerHTML=localeManager["openiam.ui.common.select"] + " " + OPENIAM.Language.addArticle(typeIds[i].displayName);

                var div1 = document.createElement("ul");
                    div1.className="item multi-choices";

                $(div).append(a, div1);

                selectList[i] = {
                    select : $(div),
                    prevObjects : [],
                    nextObjects : [],
                    orgType : typeIds[i],
                    index : i
                };
            }

            for (var i = 0; i < typeIds.length; i++) {
                for (var j = i + 1; j < typeIds.length; j++) {
                    selectList[i].nextObjects.push(selectList[j]);
                }
            }

            for (var i = typeIds.length - 1; i >= 0; i--) {
                for (var j = i - 1; j >= 0; j--) {
                    selectList[i].prevObjects.push(selectList[j]);
                }
            }

            for (var i = 0; i < typeIds.length; i++) {
                var select = selectList[i].select;
                options.draw.call($this, select, typeIds[i].displayName);
                options.hide.call($this, select);
                privateMethods.bindSelect.call($this, selectList[i]);
            }

            if(options.selectedOrgs.length>0){
                // show already selected values
                for (var i = 0; i < typeIds.length; i++) {
                    var select = selectList[i];

                    for (var j = 0; j < options.selectedOrgs.length; j++) {
                        var bean = options.selectedOrgs[j];
                        if(bean.organizationTypeId==typeIds[i].id){
                            privateMethods.addData.call($this, select, bean);
                            options.show.call($this, select.select);
                            break;
                        }
                    }
                }
            } else {
                options.show.call($this, selectList[0].select);
            }
        },
        bindSelect : function(obj) {
            var $this = this;
            var options = $this.data("options");
            var element = options.selectList[obj.index];
            element.select.find("ul.item").hide();
            element.select.find("a:first").click(function() {
                var parentOrgId=null;
                if(element.prevObjects.length>0){
                    var parentElement = options.selectList[obj.index - 1];
                    var parentOrg = parentElement.select.find("ul.item li").data("entity");
                    if(parentOrg){
                        parentOrgId=parentOrg.id;
                    }
                }
                privateMethods.request.call($this, element, parentOrgId);

            });
        },
        addData : function(element, bean){
            var $this = this;
            var options = $this.data("options");
            if(bean!=null && bean.id!=null && bean.id!=undefined){
                privateMethods.addBean.call($this, element, bean);
                // hide result dialog
                if($("#organizationSearchResult").is(':data(dialog)')) {
                    $("#organizationSearchResult").dialog("close");
                }
                // show next element
                if (element.nextObjects.length > 0) {
                    options.show.call($this, options.selectList[element.index + 1].select);
                }
            }
        },
        addBean : function(element, bean){
            var $this = this;
            var li = document.createElement("li");
            $(li).addClass("choice").data("entity", bean);

            var sp = document.createElement("span");
            sp.innerHTML=bean.name;

            var a = document.createElement("a");
            a.className="choice-close ui-icon ui-icon-closethick";
            a.href="javascript:void(0)";

            $(a).click(function(){
                privateMethods.removeElement.call($this, element);
            });

            $(li).append(sp, a);

            element.select.find("a").hide();
            element.select.find("ul.item").append(li).show();
        },
        removeElement : function(element){
            var $this = this;
            var options = $this.data("options");
            if (element.nextObjects.length > 0) {
                $.each(element.nextObjects, function(idx, val) {
                    val.select.find("ul.item:first").hide().empty();
                    val.select.find("a").show();
                    options.hide.call($this, val.select);
                });
            }
            element.select.find("ul.item:first").hide().empty();
            element.select.find("a").show();
        },
        request : function(currentType, parentOrgId) {
            var $this = this;
            var options = $this.data("options");
            var element = options.selectList[currentType.index];
            var initialValues = options.initialValues;
            var initValue = initialValues[currentType.index];
            var select = element.select;

            var allowedParetnsType=[];
            allowedParetnsType.push(currentType.orgType.id);

            $("#editDialog").organizationDialogSearch({
                searchTargetElmt : "#organizationSearchResult",
                organizationTypes: allowedParetnsType,
                onAdd : function(bean) {
                    privateMethods.addData.call($this, element, bean);
                },
                pageSize : 5,
                showResultsInDialog:true,
                parentId : parentOrgId
            });

        }
    };

    var methods = {
        init : function(args) {
            var $this = this;
            var options = $.extend({
                draw : null,
                hide : null,
                show : null,
                typeIds : [],
                initialValues : [],
                selectedOrgs: []
            }, args);

            if (!$.isFunction(options.draw)) {
                $.error("'draw' is a required parameter on organizationHierarchy");
            }

            if (!$.isFunction(options.hide)) {
                $.error("'hide' is a required parameter on organizationHierarchy");
            }

            if (!$.isFunction(options.show)) {
                $.error("'show' is a required parameter on organizationHierarchy");
            }

            if (options.typeIds == null || options.typeIds == undefined || options.typeIds.length == 0) {
                $.error("'typeIds' is a required parameter on organizationHierarchy, or it's an empty array");
            }

            if (options.initialValues == null || options.initialValues == undefined) {
                options.initialValues = [];
            }
            if (options.selectedOrgs == null || options.selectedOrgs == undefined) {
                options.selectedOrgs = [];
            }



            var $this = this;
            $this.data("options", options);

            $.each(cssDependencies, function(idx, file) {
                OPENIAM.lazyLoadCSS(file);
            });

            OPENIAM.loadScripts(javascriptDependencies, function() {
                privateMethods.draw.call($this);
            });
        },
        getValues : function() {
            var $this = this;
            var options = $this.data("options");
            var retVal = [];
            if (options != null && options != undefined) {
                var selectList = options.selectList;
                $.each(selectList, function(idx, val) {
                    var select = val.select.find("ul.item li");
                    var org = select.data("entity");
                    if (org != undefined && org != null) {
                        retVal.push(org.id);
                    }
                });
            }
            return retVal;
        }
    };

    $.fn.organizationHierarchy = function(method) {
        if (methods[method]) {
            return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === 'object' || !method) {
            return methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.organizationHierarchy');
        }
    };
})(jQuery);

(function($) {
	 var privateMethods = {
	 	
	 }
	 
	 var methods = {
        init : function(args) {
            var $this = this;
            var options = $.extend({
            	hierarchy : null,
            	draw : null,
                hide : null,
                show : null,
                initialValues : [],
                selectedOrgs : []
            }, args);

            options.divs = [];
            
            if (options.initialValues == null || options.initialValues == undefined) {
                options.initialValues = [];
            }
            if (options.selectedOrgs == null || options.selectedOrgs == undefined) {
                options.selectedOrgs = [];
            }
            
            
            if (!$.isFunction(options.draw)) {
                $.error("'draw' is a required parameter on organizationHierarchy");
            }

            if (!$.isFunction(options.hide)) {
                $.error("'hide' is a required parameter on organizationHierarchy");
            }

            if (!$.isFunction(options.show)) {
                $.error("'show' is a required parameter on organizationHierarchy");
            }

            if (options.hierarchy == null || options.hierarchy == undefined || options.hierarchy.length == 0) {
                $.error("'hierarchy' is a required parameter on organizationHierarchy, or it's an empty array");
            }

            if (options.initialValues == null || options.initialValues == undefined) {
                options.initialValues = [];
            }

            var $this = this;
            $this.data("options", options);
            options.divs = [];
            
            $.each(options.hierarchy, function(idx, val) {
            	var table = $(document.createElement("table")).append($(document.createElement("tbody")));
            	$this.append(table);
            	options.divs[idx] = table.find("tbody");
            	options.divs[idx].organizationHierarchy({
            		draw : options.draw,
                	hide : options.hide,
                	show : options.show,
                	typeIds : val,
                	initialValues : options.initialValues,
                    selectedOrgs:options.selectedOrgs
            	});
            });
        },
        getValues : function() {
            var $this = this;
            var options = $this.data("options");
            
            var values = [];
            
            $.each(options.divs, function(idx, val) {
				$.merge(values, val.organizationHierarchy("getValues"));    
            });
            return values;
        }
    };

    $.fn.organizationHierarchyWrapper = function(method) {
        if (methods[method]) {
            return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === 'object' || !method) {
            return methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.organizationHierarchy');
        }
    };
	
})(jQuery);