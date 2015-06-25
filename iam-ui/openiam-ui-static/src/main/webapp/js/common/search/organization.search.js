console = window.console || {};
console.log = window.console.log || function() {};

(function( $ ){
	var METADATA_URL = "rest/api/metadata/organizationMetadata";
	var SEARCH_URL = "rest/api/entitlements/searchOrganizations";

    var cssDependencies = [
        "/openiam-ui-static/css/common/entitlements.css",
        "/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.css",
        "/openiam-ui-static/plugins/multiselect/css/multiselect.css"
    ];

    var javascriptDependencies = [
        "/openiam-ui-static/plugins/multiselect/js/multiselect.js",
        "/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.js"
    ];
	
	var privateMethods = {
		request : function() {
			var $this = this;
			var $options = $this.data("orgSearchOptions");
			$.ajax({
				url : $options.metadataURL,
				"data" : null,
				type: "GET",
				dataType : "json",
				success : function(data, textStatus, jqXHR) {
					$options.types = data.types;
					privateMethods.detailedDraw.call($this);
				},
				error : function(jqXHR, textStatus, errorThrown) {
					OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
				}
			});
		},
		detailedDraw : function() {
			var $this = this;
			var $options = $this.data("orgSearchOptions");
			
			this.modalEdit({
				fields: [
							{fieldName: "name", type:"text",label: localeManager["openiam.ui.common.organization.name"], required : false},
		    				{fieldName: "organizationTypeId", type:"multiselect",label: localeManager["openiam.ui.common.organization.type"],
                             required:false, items : $options.types, readonly: ($options.organizationTypes && $options.organizationTypes.length>0) ? true : false}
						],
				dialogTitle: $options.dialogTitle || localeManager["openiam.ui.shared.organization.search"],
				saveBtnTxt : $options.saveBtnTxt || localeManager["openiam.ui.common.search"],
				position: $options.position,
				onSubmit: function(bean){
					$this.modalEdit("hide");
					$($options.searchTargetElmt).entitlemetnsTable({
						columnHeaders : [
							localeManager["openiam.ui.common.organization.name"], 
							localeManager["openiam.ui.common.organization.type"],
							localeManager["openiam.ui.common.actions"]
						],
		                columnsMap : ["name", "type"],
						ajaxURL : $options.searchURL,
						entityUrl : "javascript:void(0);",
						getAdditionalDataRequestObject : function() {
                            if($options.parentId!=null){
                                bean.parentId = $options.parentId;
                            }
                            if($options.validParentTypeId!=null){
                                bean.validParentTypeId = $options.validParentTypeId;
                            }
                            bean.isSelectable = true;
                            return bean;
                        },
						pageSize : $options.pageSize || 10,
		                preventOnclickEvent : false,
						emptyResultsText : localeManager["openiam.ui.shared.organization.search.empty"],
						onAdd : $options.onAdd,
                        sortEnable:true
					});
					if($options.showResultsInDialog) {
						$($options.searchTargetElmt).dialog({
					        autoOpen : true,
					        draggable : false,
					        resizable : false,
					        title : localeManager["openiam.ui.shared.organization.search"],
					        //width : $options.resultsDialogWidth,
					        maxWidth : 600,
							//position: $options.position,
							position: "center"
						});
					}
				}
			});
			this.modalEdit("show", {organizationTypeId: $options.organizationTypes});
		}
	};
	
	var methods = {
		draw : function() {
			var $this = this;
			var $options = $this.data("orgSearchOptions");
			privateMethods.request.call($this);
		},
		init : function( args ) {
			var $this = this;
	    	var options = $.extend({
	    		restfulURLPrefix : "",
	    		searchTargetElmt : null,
	    		onAdd : null,
	    		pageSize : 10,
                organizationTypes:null,
                showResultsInDialog : false,
                parentId:null,
				position:null,
				resultsDialogWidth:"auto",
                validParentTypeId:null
	    	}, args);
	    	
	    	options.metadataURL = options.restfulURLPrefix + METADATA_URL;
	    	options.searchURL = options.restfulURLPrefix + SEARCH_URL;
	    	
	    	if($.isFunction(args.onSearchResultClick)) {
            	console.warn("onSearchResultClick is deprecated - use onAdd");
            	options.onAdd = args.onSearchResultClick;
            }
	    	
	    	if(options.searchTargetElmt == null) {
	    		$.error("'searchTargetElmt' is a required parameter.  The search results are appended here");
	    	}
	    	
	    	$this.data("orgSearchOptions", options);

            $.each(cssDependencies, function(idx, file) {
                OPENIAM.lazyLoadCSS(file);
            });

            OPENIAM.loadScripts(javascriptDependencies, function() {
                methods.draw.call($this);
            });
		}
	};
	
	$.fn.organizationDialogSearch = function( method ) {
		if(this.length > 0) {
    		if ( methods[method] ) {
      			return methods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
    		} else if ( typeof method === 'object' || ! method ) {
      			return methods.init.apply( this, arguments );
    		} else {
      			$.error( 'Method ' +  method + ' does not exist on jQuery.organizationDialogSearch' );
    		}
		}
  	};
})( jQuery );