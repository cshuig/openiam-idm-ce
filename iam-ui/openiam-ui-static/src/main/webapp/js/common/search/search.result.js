console = window.console || {};
console.log = window.console.log || function() {};
console.warn = window.console.warn || function() {};

(function( $ ){
	
	var privateMethods = {
		boostrap : function() {
			var $this = this;
			var $options = $this.data("searchResultOptions");
			
			if($options.container != null && $options.container != undefined) {
				$options.container.remove();
			}
			
			$options.container = $(document.createElement("div")).addClass("ui-selected-container");			
			$this.text($options.noneSelectedText);
			$this.after($options.container);
			privateMethods.bind.call($this);
		},
		bind : function() {
			var $this = this;
			var $options = $this.data("searchResultOptions");
			$this.off("click").click(function() {
				$options.onClick($this);
			});
		},
		unbind : function() {
			var $this = this;
			var $options = $this.data("searchResultOptions");
			$this.off("click");
		},
		remove : function(bean) {
			var $this = this;
			var $options = $this.data("searchResultOptions");
			$options.container.find("[entityId='" + bean.id + "']").remove();
			delete $options.beans[bean.id];
			privateMethods.bind.call($this);
			if(methods.empty.call($this)) {
				$this.text($options.noneSelectedText);
			}
			
			if($.isFunction($options.onRemove)) {
				$options.onRemove.call($this);
			}
		},
		add : function(bean, initializing) {
			var $this = this;
			var $options = $this.data("searchResultOptions");
			
			if($options.beans[bean.id] == null || $options.beans[bean.id] == undefined) {
				var elmt = $(document.createElement("div")).addClass("ui-removable").attr("entityId", bean.id);
					var del = $(document.createElement("div")).addClass("ui-icon").addClass("ui-icon-closethick").data("bean", bean);
					var lbl = $(document.createElement("label")).text(bean.name).addClass("ui-removeable-text");
				elmt.append(del, lbl);
				
				if(!methods.empty.call($this) && $options.singleSearch) {
					methods.clear.call($this);
				}
				
				$options.container.append(elmt);
				
				del.click(function() {
					methods.remove.call($this, $(this).data("bean"));	
				});
				
				$options.beans[bean.id] = bean;
				$this.text($options.addMoreText);
				
				if($.isFunction($options.onAdd)) {
					$options.onAdd.call($this, initializing);
				}
			}
		}
	};
	var methods = {
		empty : function() {
			var $this = this;
			var $options = $this.data("searchResultOptions");
			return (Object.size($options.beans) == 0);
		},
		add : function(bean, initializing) {
			var $this = this;
			var $options = $this.data("searchResultOptions");
			
			initializing = (typeof(initializing === "boolean")) && initializing;
			
			if($options.dialogWarnOnChange && !initializing && !methods.empty.call($this)) {
				OPENIAM.Modal.Warn({ 
					message : $options.dialogWarnOnChange.addWarning,
					buttons : true, 
					OK : {
						text : $options.dialogWarnOnChange.okText,
						onClick : function() {
							OPENIAM.Modal.Close();
							privateMethods.add.call($this, bean, initializing);
						}
					},
					Cancel : {
						text : $options.dialogWarnOnChange.cancelText,
						onClick : function() {
							OPENIAM.Modal.Close();
						}
					}
				});
			} else {
				privateMethods.add.call($this, bean, initializing);
			}
		},
		remove : function(bean) {
			var $this = this;
			var $options = $this.data("searchResultOptions");
			
			if($options.dialogWarnOnChange) {
				OPENIAM.Modal.Warn({ 
					message : $options.dialogWarnOnChange.deleteWarning,
					buttons : true, 
					OK : {
						text : $options.dialogWarnOnChange.okText,
						onClick : function() {
							OPENIAM.Modal.Close();
							privateMethods.remove.call($this, bean);
						}
					},
					Cancel : {
						text : $options.dialogWarnOnChange.cancelText,
						onClick : function() {
							OPENIAM.Modal.Close();
						}
					}
				});
			} else {
				privateMethods.remove.call($this, bean);
			}
		},
		clear : function() {
			var $this = this;
			var $options = $this.data("searchResultOptions");
			$options.beans = {};
			privateMethods.boostrap.call($this);
			privateMethods.bind.call($this);
		},
		size : function() {
			var $this = this;
			var $options = $this.data("searchResultOptions");
			return Object.size($options.beans);
		},
		getValue : function() {
			var $this = this;
			var $options = $this.data("searchResultOptions");
			
			var beans = methods.getValues.call($this);
			return (beans.length > 0) ? beans[0] : null;
		},
		getValues : function() {
			var $this = this;
			var $options = $this.data("searchResultOptions");
			
			return $options.beans;
			
		},
		getIds : function() {
			var $this = this;
			var $options = $this.data("searchResultOptions");
			
			var ids = [];
			for(var key in $options.beans) {
				ids.push(key);	
			}
			return ids;
		},
		getId : function() {
			var $this = this;
			var $options = $this.data("searchResultOptions");
			var ids = methods.getIds.call($this);
			return (ids.length > 0) ? ids[0] : null;
		},
		init : function( args ) {
			var $this = this;
	    	var options = $.extend({
	    		removable : true,
	    		singleSearch : false,
	    		noneSelectedText : null,
	    		addMoreText : null,
	    		onClick : null,
	    		initialBeans : null,
	    		onAdd : null,
	    		onRemove : null
	    	}, args);
	    	
	    	if(options.noneSelectedText == null) {
	    		$.error("'noneSelectedText' is a required parameter");
	    	}
	    	
	    	if(options.addMoreText == null) {
	    		$.error("'addMoreText' is a required parameter");
	    	}
	    	
	    	if($this.is("a")) {
	    		console.warn("selectableSearchResult is designed to work with hyperlinks.")
	    	} else {
	    		$this.addClass("searchable-link");
	    	}
	    	
	    	if(!$.isFunction(options.onClick)) {
	    		$.error("'onClick' is a required parameter");
	    	}
	    	
	    	if(options.dialogWarnOnChange != null && options.dialogWarnOnChange != undefined && typeof(options.dialogWarnOnChange) == "object") {
	    		if(options.dialogWarnOnChange.addWarning == null || options.dialogWarnOnChange.addWarning == undefined) {
	    			$.error("'dialogWarnOnChange.addWarning' is a required parameter");
	    		}
	    		
	    		if(options.dialogWarnOnChange.deleteWarning == null || options.dialogWarnOnChange.deleteWarning == undefined) {
	    			$.error("'dialogWarnOnChange.deleteWarning' is a required parameter");
	    		}
	    		
	    		if(options.dialogWarnOnChange.okText == null || options.dialogWarnOnChange.okText == undefined) {
	    			$.error("'dialogWarnOnChange.okText' is a required parameter");
	    		}
	    		
	    		if(options.dialogWarnOnChange.cancelText == null || options.dialogWarnOnChange.cancelText == undefined) {
	    			$.error("'dialogWarnOnChange.cancelText' is a required parameter");
	    		}
	    	} else {
	    		options.dialogWarnOnChange = null;
	    	}
	    	
	    	options.beans = {};
	    	
	    	$this.data("searchResultOptions", options);
	    	privateMethods.boostrap.call($this);
	    	if(options.initialBeans != null && options.initialBeans != undefined) {
	    		$.each(options.initialBeans, function(idx, bean) {
	    			if(bean.id != null && bean.id != undefined) {
	    				methods.add.call($this, bean, true);
	    			}
	    		});
	    	}
		}
	};
	
	$.fn.selectableSearchResult = function( method ) {
		if(this.length > 0) {
    		if ( methods[method] ) {
      			return methods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
    		} else if ( typeof method === 'object' || ! method ) {
      			return methods.init.apply( this, arguments );
    		} else {
      			$.error( 'Method ' +  method + ' does not exist on jQuery.selectableSearchResult' );
    		}
		}
  	};
})( jQuery );