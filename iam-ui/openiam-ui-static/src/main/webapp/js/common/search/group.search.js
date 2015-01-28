console = window.console || {};
console.log = window.console.log || function() {};

(function( $ ){
	var METADATA_URL = "rest/api/metadata/groupMetadata";
	var SEARCH_URL = "rest/api/entitlements/searchGroups"
	
	var privateMethods = {
		request : function() {
			var $this = this;
			var $options = $this.data("groupSearchOptions");
			$.ajax({
				url : $options.groupSearchURL,
				"data" : null,
				type: "GET",
				dataType : "json",
				success : function(data, textStatus, jqXHR) {
					$options.managedSystems = data.managedSystems;
					privateMethods.detailedDraw.call($this);
				},
				error : function(jqXHR, textStatus, errorThrown) {
					OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
				}
			});
		},
		detailedDraw : function() {
			var $this = this;
			var $options = $this.data("groupSearchOptions");
			
			this.modalEdit({
				fields: [
							{fieldName: "name", type:"text",label:localeManager["openiam.ui.shared.group.name"], required : false},
		    				{fieldName: "managedSysId", type:"select",label:localeManager["openiam.ui.shared.managed.system"], required:false, items : $options.managedSystems, readonly: ($options.managedSysId) ? true : false},
		    				{fieldName: "attrName", type:"text",label:localeManager["openiam.ui.common.attribute.name"], required:false},
		    				{fieldName: "attrValue", type:"text",label:localeManager["openiam.ui.common.attribute.value"], required:false}
						],
				dialogTitle: $options.dialogTitle || localeManager["openiam.ui.shared.group.search"],
				saveBtnTxt : $options.saveBtnTxt || localeManager["openiam.ui.common.search"],
				onSubmit: function(bean){
					$this.modalEdit("hide");
					$($options.searchTargetElmt).entitlemetnsTable({
						columnHeaders : [
							localeManager["openiam.ui.shared.group.name"], 
							localeManager["openiam.ui.shared.managed.system"],
							localeManager["openiam.ui.common.actions"]
						],
		                columnsMap : ["name", "managedSysName"],
						ajaxURL : $options.searchURL,
						entityUrl : "javascript:void(0);",
						getAdditionalDataRequestObject : function() {return bean},
						pageSize : $options.pageSize || 10,
		                preventOnclickEvent : false,
						emptyResultsText : localeManager["openiam.ui.shared.group.search.empty"],
						onAdd : $options.onAdd,
                        sortEnable:true
					});
					
					if($options.showResultsInDialog) {
						$($options.searchTargetElmt).dialog({
					        autoOpen : true,
					        draggable : false,
					        resizable : false,
					        title : localeManager["openiam.ui.shared.group.search"],
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
			var $options = $this.data("groupSearchOptions");
			privateMethods.request.call($this);
		},
		init : function( args ) {
			var $this = this;
	    	var options = $.extend({
	    		dialogTitle : null,
	    		restfulURLPrefix : "",
	    		searchTargetElmt : null,
	    		emptyResultsText : null,
	    		onAdd : null,
	    		managedSysId : null,
	    		pageSize : 10,
	    		showResultsInDialog : false,
	    		closedDialogOnSelect : false
	    	}, args);
	    	
	    	options.groupSearchURL = options.restfulURLPrefix + METADATA_URL;
	    	options.searchURL = options.restfulURLPrefix + SEARCH_URL;
	    	
	    	if($.isFunction(args.onSearchResultClick)) {
            	console.warn("onSearchResultClick is deprecated - use onAdd");
            	options.onAdd = args.onSearchResultClick;
            }
	    	
	    	if(!$.isFunction(options.onAdd)) {
            	$.error("'onAdd' is required");
            }
            
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
	    	
	    	$this.data("groupSearchOptions", options);
	    	methods.draw.call(this);
		}
	};
	
	$.fn.groupDialogSearch = function( method ) {
		if(this.length > 0) {
    		if ( methods[method] ) {
      			return methods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
    		} else if ( typeof method === 'object' || ! method ) {
      			return methods.init.apply( this, arguments );
    		} else {
      			$.error( 'Method ' +  method + ' does not exist on jQuery.groupDialogSearch' );
    		}
		}
  	};
})( jQuery );