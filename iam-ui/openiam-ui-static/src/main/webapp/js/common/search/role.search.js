console = window.console || {};
console.log = window.console.log || function() {};
console.warn = window.console.warn || function() {};

(function( $ ){
    var METADATA_URL = "rest/api/metadata/roleMetadata";
    var SEARCH_URL = "rest/api/entitlements/searchRoles"

    var privateMethods = {
        request : function() {
            var $this = this;
            var $options = $this.data("roleSearchOptions");
            $.ajax({
                url : $options.groupSearchURL,
                "data" : null,
                type: "GET",
                dataType : "json",
                success : function(data, textStatus, jqXHR) {
                    $options.managedSystems = data.managedSystems;
                    $options.organizations = data.organizations
                    privateMethods.detailedDraw.call($this);
                },
                error : function(jqXHR, textStatus, errorThrown) {
                    OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
                }
            });
        },
        detailedDraw : function() {
            var $this = this;
            var $options = $this.data("roleSearchOptions");

            this.modalEdit({
                fields: [
                    {fieldName: "name", type:"text",label:localeManager["openiam.ui.shared.role.name"], required : false},
                    {fieldName: "managedSysId", type:"select",label:localeManager["openiam.ui.shared.managed.system"], required:false, items : $options.managedSystems, readonly: ($options.managedSysId) ? true : false}
                ],
                dialogTitle: $options.dialogTitle || localeManager["openiam.ui.shared.role.search"],
				saveBtnTxt : $options.saveBtnTxt || localeManager["openiam.ui.common.search"],
                position : $options.position,
                onSubmit: function(bean){
                    $this.modalEdit("hide");
                    $($options.searchTargetElmt).entitlemetnsTable({
						columnHeaders : [
							localeManager["openiam.ui.shared.role.name"], 
							localeManager["openiam.ui.shared.managed.system"],
							localeManager["openiam.ui.common.actions"]
						],
		                columnsMap : ["name", "managedSysName"],
						ajaxURL : $options.searchURL,
						entityUrl : "javascript:void(0);",
						getAdditionalDataRequestObject : function() {return bean},
						pageSize : $options.pageSize || 10,
		                preventOnclickEvent : false,
                        sortEnable:true,
						emptyResultsText : localeManager["openiam.ui.shared.role.search.empty"],
						onAdd : $options.onAdd
					});
					
					if($options.showResultsInDialog) {
						$($options.searchTargetElmt).dialog({
					        autoOpen : true,
					        draggable : false,
					        resizable : false,
					        title : localeManager["openiam.ui.shared.role.search"],
					        width : "auto",
					        maxWidth : 600
					    });
					}
                }
            });
            this.modalEdit("show", {managedSysId : $options.managedSysId});
        }
    };

    var methods = {
        draw : function() {
            var $this = this;
            var $options = $this.data("roleSearchOptions");
            privateMethods.request.call($this);
        },
        init : function( args ) {
            var $this = this;
            var options = $.extend({
                dialogTitle : null,
                restfulURLPrefix : "",
                searchTargetElmt : null,
                emptyResultsText : null,
                managedSysId : null,
                position:null,
                onAdd : null,
                pageSize : 10,
                showResultsInDialog : false,
                closedDialogOnSelect : false
            }, args);
            
            if($.isFunction(args.onSearchResultClick)) {
            	console.warn("onSearchResultClick is deprecated - use onAdd");
            	options.onAdd = args.onSearchResultClick;
            }
            
            if(!$.isFunction(options.onAdd)) {
            	$.error("'onAdd' is required");
            }

            options.groupSearchURL = options.restfulURLPrefix + METADATA_URL;
            options.searchURL = options.restfulURLPrefix + SEARCH_URL;

            if(options.searchTargetElmt == null) {
                $.error("'searchTargetElmt' is a required parameter.  The search results are appended here");
            }
            
            
			if(options.showResultsInDialog && options.closedDialogOnSelect) {
            	var oldFunction = options.onAdd;
            	options.onAdd = function(bean) {
            		$(options.searchTargetElmt).dialog("close");
            		oldFunction.call(this, bean);
            	}
            }

            $this.data("roleSearchOptions", options);
            methods.draw.call(this);
        }
    };

    $.fn.roleDialogSearch = function( method ) {
        if(this.length > 0) {
            if ( methods[method] ) {
                return methods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
            } else if ( typeof method === 'object' || ! method ) {
                return methods.init.apply( this, arguments );
            } else {
                $.error( 'Method ' +  method + ' does not exist on jQuery.roleDialogSearch' );
            }
        }
    };
})( jQuery );