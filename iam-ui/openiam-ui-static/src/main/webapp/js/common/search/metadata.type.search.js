console = window.console || {};
console.log = window.console.log || function() {};

(function( $ ){
	var METADATA_URL = "rest/api/metadata/type/groupings";
	var SEARCH_URL = "rest/api/metadata/type/search";

    var cssDependencies = [
        "/openiam-ui-static/css/common/entitlements.css",
        "/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.css"
    ];

    var javascriptDependencies = [
        "/openiam-ui-static/js/common/plugins/entitlementstable/entitlements.table.js"
    ];
	
	var privateMethods = {
		request : function() {
			var $this = this;
			var $options = $this.data("metdataTypeOptions");
			$.ajax({
				url : $options.metadataURL,
				"data" : ($options.showAllGroupings) ? {getAll:true} : null,
				type: "GET",
				dataType : "json",
				success : function(data, textStatus, jqXHR) {
					$options.groupings = data.beans;
					privateMethods.detailedDraw.call($this);
				},
				error : function(jqXHR, textStatus, errorThrown) {
					OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
				}
			});
		},
		detailedDraw : function() {
			var $this = this;
			var $options = $this.data("metdataTypeOptions");
			
			this.modalEdit({
				fields: [
							{fieldName: "name", type:"text",label: localeManager["openiam.ui.webconsole.meta.type.name"], required : false},
							{fieldName: "grouping", type:"select",label: localeManager["metadata.type.search.edit.grouping"],
							 required : false, readonly:($options.initialGrouping!=null)?true:false, items : $options.groupings}
						],
				dialogTitle: $options.dialogTitle || localeManager["metadata.type.search.title"],
				saveBtnTxt : $options.saveBtnTxt || localeManager["openiam.ui.common.search"],
				onSubmit: function(bean){
					$this.modalEdit("hide");
					$($options.searchTargetElmt).entitlemetnsTable({
						columnHeaders : [
							localeManager["openiam.ui.webconsole.meta.type.name"], 
							localeManager["metadata.type.search.edit.grouping"],
							localeManager["openiam.ui.common.actions"]
						],
		                columnsMap : ["name", "grouping"],
						ajaxURL : $options.searchURL,
						entityUrl : "javascript:void(0);",
						getAdditionalDataRequestObject : function() {return bean},
						pageSize : $options.pageSize || 10,
		                preventOnclickEvent : false,
						emptyResultsText : localeManager["openiam.ui.webconsole.meta.type.search.empty"],
						onAdd : $options.onAdd
					});
					if($options.showResultsInDialog) {
						$($options.searchTargetElmt).dialog({
					        autoOpen : true,
					        draggable : false,
					        resizable : false,
					        title : localeManager["metadata.type.search.title"],
					        width : "auto",
					        maxWidth : 600
					    });
					}
				}
			});
			this.modalEdit("show", ($options.initialGrouping!=null)?{grouping:$options.initialGrouping}:null);
		}
	};
	
	var methods = {
		draw : function() {
			var $this = this;
			var $options = $this.data("metdataTypeOptions");
			privateMethods.request.call($this);
		},
		init : function( args ) {
			var $this = this;
	    	var options = $.extend({
	    		restfulURLPrefix : "",
	    		searchTargetElmt : null,
	    		onAdd : null,
	    		pageSize : 10,
                showResultsInDialog : false,
				initialGrouping: null,
				showAllGroupings: false
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
	    	
	    	$this.data("metdataTypeOptions", options);

            $.each(cssDependencies, function(idx, file) {
                OPENIAM.lazyLoadCSS(file);
            });

            OPENIAM.loadScripts(javascriptDependencies, function() {
                methods.draw.call($this);
            });
		}
	};
	
	$.fn.metadataTypeDialogSearch = function( method ) {
		if(this.length > 0) {
    		if ( methods[method] ) {
      			return methods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
    		} else if ( typeof method === 'object' || ! method ) {
      			return methods.init.apply( this, arguments );
    		} else {
      			$.error( 'Method ' +  method + ' does not exist on jQuery.metadataTypeDialogSearch' );
    		}
		}
  	};
})( jQuery );