console = window.console || {};
console.log = window.console.log || function() {};

(function( $ ){
	
	var PAGE_SIZE = 10;
	
	var privateMethods = {
		hasChildren : function() {
			var $this = this;
			var $options = $this.data("entitlementsEntityViewOptions");
			$.ajax({
				"url" : "rest/api/entitlements/hasChildren/" + $options.entityType + "/" + $options.entityId,
				type: "GET",
				dataType : "json",
				contentType: "application/json",
				success : function(data, textStatus, jqXHR) {
					if($options.warningOnHasChildren) {
						if(data) {
							OPENIAM.Modal.Warn({ 
								message : $options.warningOnHasChildren.message,
								buttons : true, 
								OK : {
									text : $options.warningOnHasChildren.okText,
									onClick : function() {
										OPENIAM.Modal.Close();
										$options.callback.call($this, data);
									}
								},
								Cancel : {
									text : localeManager["openiam.ui.common.cancel"],
									onClick : function() {
										OPENIAM.Modal.Close();
									}
								}
							});
						} else {
							$options.callback.call($this, data);							
						}
					} else {
						$options.callback.call($this, data);
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
				}
			});
		}
	};
	
	var entityMappings = {
		group : {
			hasChildren : function() {
				var $this = this;
				var $options = $this.data("entitlementsEntityViewOptions");
				privateMethods.hasChildren.call($this);
			},
			getChildren : function(args) {
				var $this = this;
				var $options = $this.data("entitlementsEntityViewOptions");
				if($options.dialog) {
					
					var html = "<div>" +
									"<div class=\"openiam-ui-divider\">" +
										"<label class=\"title\">" +
											localeManager["openiam.ui.shared.group.children"] +
										"</label>" + 
										"<div class=\"openiam-member-groups\">" +
										"</div>" + 
									"</div>" +
									"<div class=\"openiam-ui-divider\">" +
										"<label class=\"title\">" +
											localeManager["openiam.ui.shared.group.roles"] +
										"</label>" +
										"<div class=\"openiam-member-roles\">" +
										"</div>" +
									"</div>" +
									"<div class=\"openiam-ui-divider\">" +
										"<label class=\"title\">" +
											localeManager["openiam.ui.shared.group.resources"] + 
										"</label>" +
										"<div class=\"openiam-member-resources\">" +
										"</div>" +
									"</div>" +
								"</div>";
					$this.html(html).dialog({autoOpen : true,
				        draggable : false,
				        resizable : false,
				        title : args.title || localeManager["openiam.ui.entitlements.group.summary.dialog.title"],
				        width : "auto",
				        maxWidth : 600
					});
					$this.find(".openiam-member-groups").entitlementsPagerView({url : "rest/api/entitlements/getChildGroups", entityId : $options.entityId});
					$this.find(".openiam-member-roles").entitlementsPagerView({url : "rest/api/entitlements/getRolesForGroup", entityId : $options.entityId});
					$this.find(".openiam-member-resources").entitlementsPagerView({url : "rest/api/entitlements/getResourcesForGroup", entityId : $options.entityId});
				}
			}
		},
		role : {
			hasChildren : function() {
				var $this = this;
				var $options = $this.data("entitlementsEntityViewOptions");
				privateMethods.hasChildren.call($this);
			},
			getChildren : function(args) {
				var $this = this;
				var $options = $this.data("entitlementsEntityViewOptions");
				if($options.dialog) {
					
					var html = "<div>" +
									"<div class=\"openiam-ui-divider\">" +
										"<label class=\"title\">" +
											localeManager["openiam.ui.shared.group.roles"] +
										"</label>" +
										"<div class=\"openiam-member-roles\">" +
										"</div>" +
									"</div>" +
									"<div class=\"openiam-ui-divider\">" +
										"<label class=\"title\">" +
											localeManager["openiam.ui.shared.group.resources"] + 
										"</label>" +
										"<div class=\"openiam-member-resources\">" +
										"</div>" +
									"</div>" +
								"</div>";
					$this.html(html).dialog({autoOpen : true,
				        draggable : false,
				        resizable : false,
				        title : args.title || localeManager["openiam.ui.entitlements.role.summary.dialog.title"],
				        width : "auto",
				        maxWidth : 600
					});
					$this.find(".openiam-member-roles").entitlementsPagerView({url : "rest/api/entitlements/getChildRoles", entityId : $options.entityId});
					$this.find(".openiam-member-resources").entitlementsPagerView({url : "rest/api/entitlements/getResourcesForRole", entityId : $options.entityId});
				}
			}
		},
		resource : {
			hasChildren : function() {
				var $this = this;
				var $options = $this.data("entitlementsEntityViewOptions");
				privateMethods.hasChildren.call($this);
			},
			getChildren : function(args) {
				var $this = this;
				var $options = $this.data("entitlementsEntityViewOptions");
				if($options.dialog) {
					
					var html = "<div>" +
									"<div class=\"openiam-ui-divider\">" +
										"<label class=\"title\">" +
											localeManager["openiam.ui.shared.group.resources"] + 
										"</label>" +
										"<div class=\"openiam-member-resources\">" +
										"</div>" +
									"</div>" +
								"</div>";
					$this.html(html).dialog({autoOpen : true,
				        draggable : false,
				        resizable : false,
				        title : args.title || localeManager["openiam.ui.entitlements.resource.summary.dialog.title"],
				        width : "auto",
				        maxWidth : 600
					});
					$this.find(".openiam-member-resources").entitlementsPagerView({url : "rest/api/entitlements/getChildResources", entityId : $options.entityId});
				}
			}
		}
	};
	
	var methods = {
		hasChildren : function(args) {
			var $this = this;
			var $options = $this.data("entitlementsEntityViewOptions");
			entityMappings[$options.entityType].hasChildren.call($this, {});
		},
		draw : function() {
			var $this = this;
			var $options = $this.data("entitlementsEntityViewOptions");
			entityMappings[$options.entityType].getChildren.call($this, {});
		},
		init : function( args ) {
			var $this = this;
	    	var options = $.extend({
	    		dialog : true,
	    		entityType : null,
	    		entityId : null,
	    		action : null,
	    		warningOnHasChildren : null
	    	}, args);
	    	
			if(options.entityType == null && options.entityId == null) {
                $.error("'entityType' and 'entityId' are required parameters.");
            }
            
            if(options.action == null && methods[options.action] == undefined) {
            	$.error("'action' is either missing or doesn't resolve to a method");
            }
            
            if(options.warningOnHasChildren != null && options.warningOnHasChildren != undefined) {
            	if(options.warningOnHasChildren === true) {
            		options.warningOnHasChildren = {};
            	}
            	options.warningOnHasChildren.message = options.warningOnHasChildren.message || localeManager["openiam.ui.shared.entitlements.removal.warning"];
            	options.warningOnHasChildren.okText = options.warningOnHasChildren.okText || localeManager["openiam.ui.shared.ui.button.remove.association"];
            }
            
            $this.data("entitlementsEntityViewOptions", options);
            methods[options.action].call(this);
	    	//methods.draw.call(this);
		}
	};
	
	$.fn.entitlementsEntityView = function( method ) {
		if(this.length > 0) {
    		if ( methods[method] ) {
      			return methods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
    		} else if ( typeof method === 'object' || ! method ) {
      			return methods.init.apply( this, arguments );
    		} else {
      			$.error( 'Method ' +  method + ' does not exist on jQuery.entitlementsEntityView' );
    		}
		}
  	};
})( jQuery );

$.fn.entitlementsEntityView.hasChildren = function(args) {
	$.extend(args,{action : "hasChildren"});
	$(document).entitlementsEntityView(args);
};

(function( $ ){
	
	var PAGE_SIZE = 5;
	
	var privateMethods = {
		getRecord : function(bean) {
			return $(document.createElement("div")).addClass("record").append($(document.createElement("a")).attr("href", "javascript:void(0);").text(bean.name));
		},
		getPager : function() {
			var $this = this;
			var $options = $this.data("entitlementsPagerViewOptions");
			
			var from = ($options.page * PAGE_SIZE);
			var to = from + PAGE_SIZE;
			if(to >= $options.size) {
				to = $options.size;
			}
			var found = $options.size;
			
			var pager = $(document.createElement("div")).addClass("pager");
				var prev = $(document.createElement("img")).attr("src", "/openiam-ui-static/plugins/tablesorter/img/prev.png").addClass("prev");
				var pageDisplay = $(document.createElement("input")).attr("type", "text").addClass("pagedisplay").val((from + 1) + "-" + to + " of " + found);
				var next = $(document.createElement("img")).attr("src", "/openiam-ui-static/plugins/tablesorter/img/next.png").addClass("next");
				
				if(from == 0) {
					$(prev).addClass("disabled");
				} else {
					$(prev).click(function() {
						$options.page = $options.page - 1;
						privateMethods.request.call($this);
					});
				}
				
				if(to == found) {
					$(next).addClass("disabled");
				} else {
					$(next).click(function() {
						$options.page = $options.page + 1;
						privateMethods.request.call($this);
					});
				}
			$(pager).append(prev, pageDisplay, next);
			return pager;
		},
		request : function() {
			var $this = this;
			var $options = $this.data("entitlementsPagerViewOptions");
			$.ajax({
				"url" : $options.url,
				"data" : {size : PAGE_SIZE, from : $options.page * PAGE_SIZE, id : $options.entityId},
				type: "GET",
				dataType : "json",
				contentType: "application/json",
				success : function(data, textStatus, jqXHR) {
					if(data.size != null && data.size != undefined) {
						$options.size = data.size;
					}
					privateMethods.draw.call($this, data);
				},
				error : function(jqXHR, textStatus, errorThrown) {
					OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
				}
			});
		},
		draw : function(data) {
			var $this = this;
			var $options = $this.data("entitlementsPagerViewOptions");
			console.log($options.url, data);
			$this.empty();
			if(data.beans.length == 0) {
				$this.append($options.emptyMessage);
			} else {
				$.each(data.beans, function(idx, bean) {
					$this.append(privateMethods.getRecord(bean));
				});
				$this.append(privateMethods.getPager.call($this));
			}
		}
	};
	
	var methods = {
		init : function( args ) {
			var $this = this;
	    	var options = $.extend({
	    		url : null,
	    		page : 0,
	    		entityId : null,
	    		emptyMessage : localeManager["openiam.ui.idm.synch.table.noresults"]
	    	}, args);
	    	
			if(options.url == null) {
                $.error("'url' is required.");
            }
            
            if(options.entityId == null) {
            	$.error("'entityId' is required");
            }
            
            if(options.emptyMessage == null) {
            	$.error("'emptyMessage' is required");
            }
            
            $this.data("entitlementsPagerViewOptions", options);
            privateMethods.request.call($this)
		}
	};
	
	$.fn.entitlementsPagerView = function( method ) {
		if(this.length > 0) {
    		if ( methods[method] ) {
      			return methods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
    		} else if ( typeof method === 'object' || ! method ) {
      			return methods.init.apply( this, arguments );
    		} else {
      			$.error( 'Method ' +  method + ' does not exist on jQuery.entitlementsPagerView' );
    		}
		}
  	};
})( jQuery );