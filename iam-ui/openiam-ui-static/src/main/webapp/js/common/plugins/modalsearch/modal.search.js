(function( $ ){
	
	var privateMethods = {
		createHtml : function(data, page) {
			var $this = this;
			var $options = $this.data("modalSearchOptions");
			var found = $this.data("found");
			var size = $options.pageSize;
			var from = (page - 1) * size;
			var to = ((from + size <= found) ? (from + size) : found);
			
			
			var html = document.createElement("div");
			if(data.beans != null && data.beans != undefined && data.beans.length > 0) {
				var table = document.createElement("table"); $(table).attr("width", "100%"); table.className = "search";
					var tbody = document.createElement("tbody");
					var tfoot = document.createElement("tfoot");
				var tr = null;
				var td = null;
				$(table).append(tbody, tfoot);
				for(var i = 0; i < data.beans.length; i++) {
					if(i == 0 || i % 2 == 0) {
						tr = document.createElement("tr");
						$(tbody).append(tr);
					}
					td = document.createElement("td");
					$(tr).append(td);
					var bean = data.beans[i];
					var a = document.createElement("a"); a.href = "javascript:void(0)"; a.innerHTML = bean.name; $(a).data("entity", bean);
					$(a).click(function() {
						$options.onElementClick($(this).data("entity"));
						try {
							$options.dialog.dialog("close");
						} catch(e) {
							
						}
						return false;
					});
					$(td).append(a);
				}
				$(html).append(table);
				
				tr = document.createElement("tr");
					td = document.createElement("td"); $(td).attr("colspan", "2"); td.className = "pager";
						var prev = document.createElement("img"); prev.src = "/openiam-ui-static/plugins/tablesorter/img/prev.png"; prev.className = "prev";
						var pageDisplay = document.createElement("input"); $(pageDisplay).attr("type", "text"); pageDisplay.className = "pagedisplay"; $(pageDisplay).val((from + 1) + "-" + to + " of " + found);
						var next = document.createElement("img"); next.src = "/openiam-ui-static/plugins/tablesorter/img/next.png"; next.className = "next";
						
						if(from == 0) {
							$(prev).addClass("disabled");
						} else {
							$(prev).click(function() {
								privateMethods.request.call($this, page - 1);
							});
						}
						
						if(to == found) {
							$(next).addClass("disabled");
						} else {
							$(next).click(function() {
								privateMethods.request.call($this, page + 1);
							});
						}
					$(td).append(prev, pageDisplay, next);
				$(tr).append(td);
				$(tfoot).append(tr);
			} else {
				html.className = "info"; html.innerHTML = $options.emptyResultsText;
			}
			return html;
		},
		
		request : function(page, $options) {
			var $this = this;
			var $options = $this.data("modalSearchOptions");
			var from = (page - 1) * $options.pageSize;
			var name = $this.val();
			var $found = $this.data("found");
			
			var requestData = {"name" : name, "from" : from, "size" : $options.pageSize};
			if($.isFunction($options.getAdditionalDataRequestObject)) {
				requestData = $.extend(requestData, $options.getAdditionalDataRequestObject());
			}
			
			$.ajax({
				url : $options.ajaxURL,
				data : requestData,
				type : "GET",
				dataType : "json",
				contentType: "application/json",
				success : function(data, textStatus, jqXHR) {
					$found = (data.size != null) ? data.size : $found;
					$this.data("found", $found);
					if(data.beans && data.beans.length == 1 && data.beans[0].name == name) {
						$options.onElementClick(data.beans[0]);
						try {
							$options.dialog.dialog("close");
						} catch(e) {
							
						}
					} else {
						var dialogHtml = privateMethods.createHtml.call($this, data, page);
						$options.dialog.html(dialogHtml).dialog({ autoOpen: true, draggable : false, resizable : false, title : $options.dialogTitle, width: $options.width});
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					$options.onInternalError($options.internalErrorText);
				}
			});
		}
	};
	
  	var methods = {
    	init : function( args ) { 
    		var dialog = null;
    		if($("#dialog").length == 0) {
    			var dialog = document.createElement("div"); dialog.id = "dialog";
    			$(document.body).append(dialog);
    			dialog = $(dialog);
    		} else {
    			dialog = $("#dialog");
    		}
    		
      		var options = $.extend({
      			pageSize : 10, /* number of results per page */
      			onElementClick : null, /* called when an element is clicked */
      			ajaxURL : null, /* the URl to call when searching */
      			dialogTitle : null, /* the Title of the Dialog */
      			width : 400, /* width of the Dialog box */
      			onInternalError : OPENIAM.Modal.Error, /* function called when an error occurs */
      			internalErrorText : localeManager["openiam.ui.internal.error"], /* text to call the error function with */
      			emptyResultsText : null, /* text to display when there are no results */
      			getAdditionalDataRequestObject : null /* function to call that gets the request object to send via ajax */
    		}, args);
    		options.dialog = dialog;
    		
    		if(options.pageSize < 1) {
    			$.errror("Page size < 1");
    		}
    		
    		if(options.onElementClick == null) {
    			$.error("No onElementClick callback provided");
    		}
    		
    		if(options.ajaxURL == null) {
    			$.error("No ajaxURL provided");
    		}
    		
    		if(options.dialogTitle == null) {
    			$.error("No dialogTitle provided");
    		}
    		
    		if(options.emptyResultsText == null) {
    			$.error("No emptyResultsText provided");
    		}
    		
    		this.data("modalSearchOptions", options);
    	},
    	show : function( ) { /* will reset to page 1 */
			privateMethods.request.call(this, 1);
	    },
	    hide : function( ) {
	    	
    	}
  	};

  	$.fn.modalSearch = function( method ) {
  		if(this.length > 0) {
	    	if ( methods[method] ) {
	      		return methods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
	    	} else if ( typeof method === 'object' || ! method ) {
	      		return methods.init.apply( this, arguments );
	    	} else {
	      		$.error( 'Method ' +  method + ' does not exist on jQuery.tooltip' );
	    	}
  		}
  	};
})( jQuery );