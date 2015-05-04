console = window.console || {};
console.log = window.console.log || function() {};

(function( $ ){
	var RESOURCE_TYPE_SEARCH_URL = "rest/api/metadata/resoruceTypes";
    var RESOURCE_METADATA_SEARCH_URL = "rest/api/metadata/resourceMetadata";
    var RESOURCE_SEARCH_URL = "rest/api/entitlements/searchResources"

	var privateMethods = {
		request : function() {
			var $this = this;
			var $options = $this.data("resourceSearchOptions");
			$.ajax({
				url : $options.resourceTypeSearch,
				"data" : null,
				type: "GET",
				dataType : "json",
				success : function(data, textStatus, jqXHR) {
					$options.resourceMetadata = data;
					privateMethods.detailedDraw.call($this);
				},
				error : function(jqXHR, textStatus, errorThrown) {
					OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
				}
			});
		},
		detailedDraw : function() {
			var $this = this;
			var $options = $this.data("resourceSearchOptions");
			
			this.modalEdit({
				fields: [
							{fieldName: "name", type:"text",label: localeManager["openiam.ui.shared.resource.name"], required : false},
		    				{fieldName: "type", type:"select",label: localeManager["openiam.ui.common.resource.type"], required:false, items : $options.resourceMetadata.resourceTypeList, readonly: ($options.managedSysId) ? true : false},
                            {fieldName: "risk", type:"select",label: localeManager["openiam.ui.common.resource.risk"], required:false, items : $options.resourceMetadata.resourceRiskList},
		    				{fieldName: "attrName", type:"text",label: localeManager["openiam.ui.common.attribute.name"], required:false},
		    				{fieldName: "attrValue", type:"text",label: localeManager["openiam.ui.common.attribute.value"], required:false}
						],
				dialogTitle: $options.dialogTitle || localeManager["openiam.ui.shared.resource.search"],
				saveBtnTxt : $options.saveBtnTxt || localeManager["openiam.ui.common.search"],
				position : $options.position,
				onSubmit: function(bean){
					$this.modalEdit("hide");
					$($options.searchTargetElmt).entitlemetnsTable({
						columnHeaders : [
							localeManager["openiam.ui.shared.resource.name"], 
							localeManager["openiam.ui.common.description"],
							localeManager["openiam.ui.common.resource.type"], 
							localeManager["openiam.ui.common.resource.risk"],
							localeManager["openiam.ui.common.actions"]
						],
		                columnsMap : ["name", "description", "resourceType", "risk"],
						ajaxURL : $options.searchURL,
						entityUrl : "javascript:void(0);",
						getAdditionalDataRequestObject : function() {
							return {
								resourceTypeId : bean.type,
								attributeName : bean.attrName,
								attributeValue : bean.attrValue,
								name : bean.name,
								risk : bean.risk,
								excludeResourceType : ($options.excludeMenus) ? "MENU_ITEM" : null
							}
						},
						pageSize : $options.pageSize || 10,
		                preventOnclickEvent : false,
						emptyResultsText : localeManager["openiam.ui.shared.resource.search.empty"],
						onAdd : $options.onAdd,
                        sortEnable:true
					});
				}
			});
			this.modalEdit("show", {type : $options.managedSysId});
		}
	};
	
	var methods = {
		draw : function() {
			var $this = this;
			var $options = $this.data("resourceSearchOptions");
			privateMethods.request.call($this);
		},
		init : function( args ) {
			var $this = this;
	    	var options = $.extend({
	    		restfulURLPrefix : "",
	    		searchTargetElmt : null,
	    		onSearchResultClick : null,
	    		managedSysId : null,
				position:null,
				pageSize : 10,
	    		onAdd : null,
	    		excludeMenus : false
	    	}, args);
	    	
	    	options.resourceTypeSearch = options.restfulURLPrefix + RESOURCE_METADATA_SEARCH_URL;
	    	options.searchURL = options.restfulURLPrefix + RESOURCE_SEARCH_URL;
	    	
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
	    	
	    	$this.data("resourceSearchOptions", options);
	    	methods.draw.call(this);
		}
	};
	
	$.fn.resourceDialogSearch = function( method ) {
		if(this.length > 0) {
    		if ( methods[method] ) {
      			return methods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
    		} else if ( typeof method === 'object' || ! method ) {
      			return methods.init.apply( this, arguments );
    		} else {
      			$.error( 'Method ' +  method + ' does not exist on jQuery.resourceDialogSearch' );
    		}
		}
  	};
})( jQuery );