(function( $ ){
	var PAGE_SIZE = 5;
	
	var URLS = {
		"users" : "rest/api/entitlements/getUsersForResource",
		"roles" : "rest/api/entitlements/getRolesForResource",
		"groups" : "rest/api/entitlements/getGroupsForResource"
	};
	
	var ENTITY_URLS = {
		"users" : "editUser.html?id=",
		"roles" : "editRole.html?id=",
		"groups" : "editGroup.html?id="
	};

	var privateMethods = {
		cancel : function() {
			var $this = this;
			var options = $this.data("options");
			if(options.xhr != null) {
				options.xhr.abort();
			}
		},
		removeInteractability : function() {
			this.addClass("disabled");
		},
		makeInteractible : function() {
			this.removeClass("disabled");
		},
		addLoader : function() {
			this.addClass("loading");
		},
		hideLoader : function() {
			this.removeClass("loading");
		},
		request : function() {
			privateMethods.removeInteractability.call(this);
			privateMethods.cancel.call(this);
			
			var $this = this;
			var options = $this.data("options");
			
			privateMethods.addLoader.call($this);
			privateMethods.removeInteractability.call($this);
			var url = URLS[options.type];
			$.ajax({
				"url" : url,
				"data" : {size : PAGE_SIZE, from : options.page * PAGE_SIZE, id : options.resourceId},
				type: "GET",
				dataType : "json",
				contentType: "application/json",
				success : function(data, textStatus, jqXHR) {
					privateMethods.draw.call($this, data);
				},
				error : function(jqXHR, textStatus, errorThrown) {
					privateMethods.removeInteractability.call($this);
					privateMethods.hideLoader.call($this);
				}
			});
		},
		draw : function(data) {
			var $this = this;
			var options = $this.data("options");
			
			$this.empty();
			
			if(options.page == 0) {
				options.size = data.size;
			}
			
			console.log("draw", data);
			
			$this.empty();
				var lbl = document.createElement("label"); lbl.innerHTML = options.title; lbl.className = "title";
			$this.append(lbl);
			
			if(data.beans != null && data.beans != undefined && data.beans.length > 0) {
				$.each(data.beans, function(idx, bean) {
					
					var url = ENTITY_URLS[options.type];
					var record = document.createElement("div"); record.className = "record";
						var a = document.createElement("a"); a.href = url + bean.id; a.innerHTML = bean.name;
					$(record).append(a);
					$this.append(record);
				});
				privateMethods.drawPager.call($this);
			} else {
				var empty = document.createElement("div"); empty.className = "empty"; empty.innerHTML = options.emptyMessage;
				$this.append(empty);
			}
			
			privateMethods.hideLoader.call(this);
			privateMethods.makeInteractible.call(this);
		},
		drawPager : function() {
			var $this = this;
			var options = $this.data("options");
			
			var from = (options.page * PAGE_SIZE);
			var to = from + PAGE_SIZE;
			if(to >= options.size) {
				to = options.size;
			}
			var found = options.size;
			
			var pager = document.createElement("div"); pager.className = "pager";
				var prev = document.createElement("img"); prev.src = "/openiam-ui-static/plugins/tablesorter/img/prev.png"; prev.className = "prev";
				var pageDisplay = document.createElement("input"); $(pageDisplay).attr("type", "text"); pageDisplay.className = "pagedisplay"; $(pageDisplay).val((from + 1) + "-" + to + " of " + found);
				var next = document.createElement("img"); next.src = "/openiam-ui-static/plugins/tablesorter/img/next.png"; next.className = "next";
				
				if(from == 0) {
					$(prev).addClass("disabled");
				} else {
					$(prev).click(function() {
						options.page = options.page - 1;
						privateMethods.request.call($this);
					});
				}
				
				if(to == found) {
					$(next).addClass("disabled");
				} else {
					$(next).click(function() {
						options.page = options.page + 1;
						privateMethods.request.call($this);
					});
				}
			$(pager).append(prev, pageDisplay, next);
			
			$this.append(pager);
		}
	}
	
	var methods = {
		init : function( args ) { 
			var options = $.extend({
     			type : null,
     			title : null,
     			emptyMessage : localeManager["openiam.ui.common.no.data.found"]
    		}, args);
    		
    		options.page = 0;
    		options.xhr = null;
    		
    		if(options.type == null || options.type == undefined) {
    			$.error("type is required");
    		}
    		
    		if(options.title == null || options.title == undefined) {
    			$.error("title is required");
    		}
			
			this.data("options", options);
	    },
	    fire : function(resourceId) {
	    	var $this = this;
			var options = $this.data("options");
			options.page = 0;
	    	options.resourceId = resourceId;
	    	privateMethods.request.call(this);
	    }
	}
	
	$.fn.menuEntitlements = function( method ) {
    	if ( methods[method] ) {
      		return methods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
    	} else if ( typeof method === 'object' || ! method ) {
      		return methods.init.apply( this, arguments );
    	} else {
      		$.error( 'Method ' +  method + ' does not exist on jQuery.menuEntitlements' );
    	}    
  	};
})( jQuery );