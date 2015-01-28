console = window.console || {};
console.log = window.console.log || function() {};

(function( $ ){	
	
	var CheckboxWidget = function(args) {
		this.id = new Date().getTime();
		this.widget = args.widget;
		this.required = args.widget.required;
		this.disabled = !(args.widget.editable);
		this.displayName = args.widget.displayName || null;
		this.onLabelDraw = args.options.onLabelDraw || function() {return null;};
		this.onCheckboxDraw  = args.options.onCheckboxDraw || this.onCheckboxDraw;
		this.onCheckboxDrawDone = args.options.onCheckboxDrawDone || this.onCheckboxDrawDone;
		this.checkboxes = [];
		this.defaultValue = args.widget.defaultValue || null;
		privateMethods.createUserValuesIfNecessary(this.widget);
		privateMethods.removeInvalidValues(this.widget);
		if(this.widget.userValues.length == 0) {
			this.widget.userValues.push({userAttributeId : null, value : null});
		}
		
		privateMethods.unsetDefaultValueIfNecessary(this, args.options.defaultValuesCache);
	}; 
	
	CheckboxWidget.prototype = {
		drawLabel : function() {
			return this.onLabelDraw(this.id, this.required, this.displayName);
		},
		drawElement : function() {
			var $this = this;
			var div = document.createElement("div");
			div.id = this.id;
			var validValues = this.widget.validValues;
			
			var hasValue = (this.widget.userValues[0].value != null);
			for(var i in validValues) {
				var val = validValues[i];
				var displayName = val.displayName;
				var value = val.value;
				var id = new Date().getTime();
				
				var checkbox = document.createElement("input");
				$(checkbox).attr("type", "checkbox");
				checkbox.id = id;
				if(value) {
					checkbox.value = value;
				}
				if(this.disabled) {
					$(checkbox).attr("disabled", "disabled");
				}
				
				if(hasValue) {
					if(this.widget.userValues[0].value == value) {
						$(checkbox).attr("checked", true);
					}
				} else if(this.defaultValue == value) {
					$(checkbox).attr("checked", true);
				}
				
				var label = document.createElement("label"); label.innerHTML = displayName; $(label).attr("for", id);
				$(div).append(this.onCheckboxDraw(label, checkbox));
				$(checkbox).click(function() {
					$this.onCheck(this);
				});
				this.checkboxes.push(checkbox);
			}
			return div;
		},
		onCheckboxDraw : function(label, element) {
			var div = document.createElement("div");
			$(div).append(element, label);
			$(div).addClass("ui-single-group-container ");
			return div;
		},
		onCheckboxDrawDone : function(label, element) {
			$(element).addClass("ui-group-container");
		},
		onCheck : function(checkbox) {
			if(!this.disabled) {
				var wasChecked = !$(checkbox).is(":checked");
				$.each(this.checkboxes, function(idx, val) {
					$(val).attr("checked", false);
				});
				if(!wasChecked) {
					$(checkbox).attr("checked", true);
					this.widget.userValues[0].value = $(checkbox).val();
				} else {
					this.widget.userValues[0].value = null;
				}
			}
		},
		drawable : function() {
			return (!privateMethods.isNullOrUndefined(this.displayName) && privateMethods.hasValidValues(this.widget));
		},
		onDrawFinished : function(label, element) {
			if($.isFunction(this.onCheckboxDrawDone)) {
				this.onCheckboxDrawDone(label, element);
			}
		},
		onAppendDone : function(label, element) {
			
		}
	};
	
	var SelectWidget = function(args) {
		this.id = new Date().getTime();
		this.widget = args.widget;
		this.required = args.widget.required;
		this.disabled = !(args.widget.editable);
		this.displayName = args.widget.displayName || null;
		this.onLabelDraw = args.options.onLabelDraw || function() {return null;};
		this.defaultValue = args.widget.defaultValue || null;
		privateMethods.createUserValuesIfNecessary(this.widget);
		privateMethods.removeInvalidValues(this.widget);
		if(this.widget.userValues.length == 0) {
			this.widget.userValues.push({userAttributeId : null, value : null});
		}
		
		privateMethods.unsetDefaultValueIfNecessary(this, args.options.defaultValuesCache);
	};
	
	SelectWidget.prototype = {
		drawLabel : function() {
			return this.onLabelDraw(this.id, this.required, this.displayName);
		},
		drawElement : function() {
			var $this = this;
			var select = document.createElement("select");
			select.id = this.id;
			var validValues = this.widget.validValues;
			
			var hasValue = (this.widget.userValues[0].value != null);
			var option = document.createElement("option"); option.value = ""; option.innerHTML = localeManager["openiam.ui.common.value.select"];
			$(select).append(option);
			for(var i in validValues) {
				var val = validValues[i];
				var displayName = val.displayName;
				var value = val.value;
				
				option = document.createElement("option");
				option.value = value;
				option.innerHTML = displayName;
				
				if(hasValue) {
					if(this.widget.userValues[0].value == value) {
						$(option).attr("selected", "selected");
					}
				} else if(this.defaultValue == value) {
					$(option).attr("selected", "selected");
				}
				
				$(select).append(option);
			}
			
			if(this.disabled) {
				$(select).attr("disabled", "disabled");
			}
			
			this.element = select;
			
			return select;
		},
		preSubmit : function() {
			this.widget.userValues[0].value = $(this.element).val();
			//console.log("DateWidget", this.widget.userValues);
		},
		drawable : function() {
			return (!privateMethods.isNullOrUndefined(this.displayName) && privateMethods.hasValidValues(this.widget));
		},
		onDrawFinished : function(label, element) {
			
		},
		onAppendDone : function(label, element) {
			
		}
	};
	
	var MultiSelectWidget = function(args) {
		this.id = new Date().getTime();
		this.widget = args.widget;
		this.required = args.widget.required;
		this.disabled = !(args.widget.editable);
		this.displayName = args.widget.displayName || null;
		this.onLabelDraw = args.options.onLabelDraw || function() {return null;};
		this.defaultValue = args.widget.defaultValue || null;
		privateMethods.createUserValuesIfNecessary(this.widget);
		this._cloneUserValues();
		privateMethods.removeInvalidValues(this.widget);
		
		privateMethods.unsetDefaultValueIfNecessary(this, args.options.defaultValuesCache);
	};
	
	MultiSelectWidget.prototype = {
		drawLabel : function() {
			return this.onLabelDraw(this.id, this.required, this.displayName);
		},
		drawElement : function() {
			var div = document.createElement("div");
			
			var $this = this;
			var select = document.createElement("select");
			select.id = this.id;
			$(select).attr("multiple", "multiple");
			
			var option = null;
			var validValues = this.widget.validValues;
			var hasValues = (this.widget.userValues.length > 0);
			for(var i in validValues) {
				var val = validValues[i];
				var displayName = val.displayName;
				var value = val.value;
				option = document.createElement("option");
				option.value = value;
				option.innerHTML = displayName;
				if(hasValues) {
					$.each(this.widget.userValues, function(idx, userVal) {
						if(userVal.value == value) {
							$(option).attr("selected", "selected");
						}
					});
				} else {
					if(value == this.defaultValue) {
						$(option).attr("selected", "selected");
					}
				}
				
				$(select).append(option);
			}
			
			if(this.disabled) {
				$(select).attr("disabled", "disabled");
			}
			
			if($.isFunction(this.onSelectDraw)) {
				select = this.onSelectDraw(select);
			}
			$(div).append(select);
			return div;
		},
		_valIdx : function(value) {
			var $this = this;
			var contains = null;
			$.each($this.widget.userValues, function(idx, userValue) {
				if(value== userValue.value) {
					contains = idx;
				}
			});
			return contains;
		},
		_cloneUserValues : function() {
			this.oldUserValues = [];
			var $this = this;
			$.each(this.widget.userValues, function(idx, val) {
				var clone = {};
				$.extend(clone, val);
				$this.oldUserValues.push(clone);
			});
		},
		preSubmit : function() {
			var $this = this;
			$this.widget.userValues = $this.oldUserValues;
			this._cloneUserValues();
			
			var checked = $(this.element).multiselect("getChecked");
			var idxToPurge = [];
			$.each($this.widget.userValues, function(idx, userValue) {
				var contains = false;
				$.each(checked, function(idxChecked, val) {
					val = $(val).val();
					if(userValue.value == val) {
						contains = true;
					}
				});
				
				if(!contains) {
					idxToPurge.push(idx);
				}
			});
			for(var i = idxToPurge.length - 1; i >= 0; i--) {
				$this.widget.userValues[idxToPurge[i]].value = null;
			}
			$.each(checked, function(idx, val) {
				val = $(val).val();
				var valIdx = $this._valIdx(val);
				if(valIdx == null) {
					$this.widget.userValues.push({userAttributeId : null, value : val});
				}
			});
			//console.log("DateWidget", this.widget.userValues);
		},
		drawable : function() {
			return (!privateMethods.isNullOrUndefined(this.displayName) && privateMethods.hasValidValues(this.widget));
		},
		onDrawFinished : function(label, element) {
		},
		onAppendDone : function(label, element) {
			var $this = this;
			this.element = element;
			$(element).multiselect({});
		}
	};
	
	var DateWidget = function(args) {
		this.id = new Date().getTime();
		this.widget = args.widget;
		this.required = args.widget.required;
		this.disabled = !(args.widget.editable);
		this.displayName = args.widget.displayName || null;
		this.onLabelDraw = args.options.onLabelDraw || function() {return null;};
		privateMethods.createUserValuesIfNecessary(this.widget);
		if(this.widget.userValues.length == 0) {
			this.widget.userValues.push({userAttributeId : null, value : null});
		}
	};
	
	DateWidget.prototype = {
		drawLabel : function() {
			return this.onLabelDraw(this.id, this.required, this.displayName);
		},
		drawElement : function() {
			var div = document.createElement("div");
			$(div).addClass("ui-datewidget").addClass("ui-group-container")
			
			var element = document.createElement("input"); element.id = this.id;
			$(element).attr("type", "text"); element.className = "full rounded date"; $(element).attr("readonly", "readonly");
			if(this.widget.userValues[0].value) {
				$(element).val(this.widget.userValues[0].value);
			}
			
			if(this.disabled) {
				$(element).attr("disabled", "disabled");
			}
			
			this.element = element;
			$(div).append(element);
			return div;
		},
		drawable : function() {
			return (!privateMethods.isNullOrUndefined(this.displayName));
		},
		onDrawFinished : function(label, element) {
			
		},
		preSubmit : function() {
			this.widget.userValues[0].value = $(this.element).val();
			//console.log("DateWidget", this.widget.userValues);
		},
		onAppendDone : function(label, element) {
			var $this = this;
			var dateCtrl = $(this.element); 
			dateCtrl.datepicker({ dateFormat: OPENIAM.ENV.DateFormatDP,showOn: "button",changeMonth:true, changeYear:true});
			
			setTimeout(function() {
				var btn = dateCtrl.next();
				//btn.css("top", (dateCtrl.height() - btn.height()) / 2)
				btn.css("right", (dateCtrl.parent().width() - dateCtrl.width()));
			}, 500);
		}
	};
	
	var PasswordWidget = function(args) {
		this.id = new Date().getTime();
		this.widget = args.widget;
		this.required = args.widget.required;
		this.disabled = !(args.widget.editable);
		this.displayName = args.widget.displayName || null;
		this.onLabelDraw = args.options.onLabelDraw || function() {return null;};
		privateMethods.createUserValuesIfNecessary(this.widget);
		if(this.widget.userValues.length == 0) {
			this.widget.userValues.push({userAttributeId : null, value : null});
		}
	};
	
	PasswordWidget.prototype = {
		drawLabel : function() {
			return this.onLabelDraw(this.id, this.required, this.displayName);
		},
		drawElement : function() {
			var div = $(document.createElement("div")).addClass("ui-group-container");
			
			var element = document.createElement("input"); element.id = this.id;
			$(element).attr("type", "password"); element.className = "full rounded";
			if(this.widget.userValues[0].value) {
				$(element).val(this.widget.userValues[0].value);
			}
			if(this.disabled) {
				$(element).attr("disabled", "disabled");
			}
			this.element = element;
			div.append(element);
			return div;
		},
		drawable : function() {
			return (!privateMethods.isNullOrUndefined(this.displayName));
		},
		onDrawFinished : function(label, element) {
			
		},
		onAppendDone : function(label, element) {
			
		},
		preSubmit : function() {
			this.widget.userValues[0].value = $(this.element).val();
			//console.log("PasswordWidget", this.widget.userValues);
		}
	};
	
	RadioWidget = function(args) {
		this.id = new Date().getTime();
		this.widget = args.widget;
		this.required = args.widget.required;
		this.disabled = !(args.widget.editable);
		this.displayName = args.widget.displayName || null;
		this.onLabelDraw = args.options.onLabelDraw || function() {return null;};
		this.onRadioDraw = args.options.onRadioDraw || this.onRadioDraw;
		this.onRadioDrawDone = args.options.onRadioDrawDone || this.onRadioDrawDone;
		this.checkboxes = [];
		this.defaultValue = args.widget.defaultValue || null;
		privateMethods.createUserValuesIfNecessary(this.widget);
		privateMethods.removeInvalidValues(this.widget);
		if(this.widget.userValues.length == 0) {
			this.widget.userValues.push({userAttributeId : null, value : null});
		}
		
		privateMethods.unsetDefaultValueIfNecessary(this, args.options.defaultValuesCache);
	};
	
	RadioWidget.prototype = {
		drawLabel : function() {
			return this.onLabelDraw(this.id, this.required, this.displayName);
		},
		drawElement : function() {
			var $this = this;
			var div = document.createElement("div");
			div.id = this.id;
			var validValues = this.widget.validValues;
			
			var hasValue = (this.widget.userValues[0].value != null);
			for(var i in validValues) {
				var val = validValues[i];
				var displayName = val.displayName;
				var value = val.value;
				var id = new Date().getTime();
				
				var checkbox = document.createElement("input");
				$(checkbox).attr("type", "radio");
				checkbox.id = id;
				if(value) {
					checkbox.value = value;
				}
				if(this.disabled) {
					$(checkbox).attr("disabled", "disabled");
				}
				
				if(hasValue) {
					if(this.widget.userValues[0].value == value) {
						$(checkbox).attr("checked", true);
					}
				} else if(this.defaultValue == value) {
					$(checkbox).attr("checked", true);
				}
				
				var label = document.createElement("label"); label.innerHTML = displayName; $(label).attr("for", id);
				$(div).append(this.onRadioDraw(label, checkbox));
				$(checkbox).click(function() {
					$this.onClick(this);
				});
				this.checkboxes.push(checkbox);
			}
			return div;
		},
		onClick : function(radio) {
			if(!this.disabled) {
				$.each(this.checkboxes, function(idx, val) {
					$(val).attr("checked", false);
				});
				$(radio).attr("checked", true);
				this.widget.userValues[0].value = $(radio).val();
			}
		},
		drawable : function() {
			return (!privateMethods.isNullOrUndefined(this.displayName) && privateMethods.hasValidValues(this.widget));
		},
		onDrawFinished : function(label, element) {
			if($.isFunction(this.onRadioDrawDone)) {
				this.onRadioDrawDone(label, element);
			}
		},
		onRadioDraw : function(label, element) {
			var div = document.createElement("div");
			$(div).append(element, label);
			$(div).addClass("ui-single-group-container ");
			return div;
		},
		onRadioDrawDone : function(label, element) {
			$(element).addClass("ui-group-container");
		},
		onAppendDone : function(label, element) {
			
		}
	};
	
	var TextWidget = function(args) {
		this.id = new Date().getTime();
		this.widget = args.widget;
		this.required = args.widget.required;
		this.disabled = !(args.widget.editable);
		this.displayName = args.widget.displayName || null;
		this.onLabelDraw = args.options.onLabelDraw || function() {return null;};
		privateMethods.createUserValuesIfNecessary(this.widget);
		this.defaultValue = args.widget.defaultValue || null;
		if(this.widget.userValues.length == 0) {
			this.widget.userValues.push({userAttributeId : null, value : null});
		}
		
		privateMethods.unsetDefaultValueIfNecessary(this, args.options.defaultValuesCache);
	};
	
	TextWidget.prototype = {
		drawLabel : function() {
			return this.onLabelDraw(this.id, this.required, this.displayName);
		},
		drawElement : function() {
			var div = $(document.createElement("div")).addClass("ui-group-container");
			
			var element = document.createElement("input"); element.id = this.id;
			$(element).attr("type", "text"); element.className = "full rounded";
			if(this.widget.userValues[0].value) {
				$(element).val(this.widget.userValues[0].value);
			} else if(this.defaultValue) {
				$(element).val(this.defaultValue);
			}
			if(this.disabled) {
				$(element).attr("disabled", "disabled");
			}
			this.element = element;
			
			div.append(element);
			return div;
		},
		drawable : function() {
			return (!privateMethods.isNullOrUndefined(this.displayName));
		},
		onDrawFinished : function(label, element) {
			
		},
		onAppendDone : function(label, element) {
			
		},
		preSubmit : function() {
			this.widget.userValues[0].value = $(this.element).val();
			//console.log("TextWidget", this.widget.userValues);
		}
	};
	
	var TextAreaWidget = function(args) {
		this.id = new Date().getTime();
		this.widget = args.widget;
		this.required = args.widget.required;
		this.disabled = !(args.widget.editable);
		this.displayName = args.widget.displayName || null;
		this.onLabelDraw = args.options.onLabelDraw || function() {return null;};
		privateMethods.createUserValuesIfNecessary(this.widget);
		this.defaultValue = args.widget.defaultValue || null;
		if(this.widget.userValues.length == 0) {
			this.widget.userValues.push({userAttributeId : null, value : null});
		}
		
		privateMethods.unsetDefaultValueIfNecessary(this, args.options.defaultValuesCache);
	};
	
	TextAreaWidget.prototype = {
		drawLabel : function() {
			return this.onLabelDraw(this.id, this.required, this.displayName);
		},
		drawElement : function() {
			var div = $(document.createElement("div")).addClass("ui-group-container");
			
			var element = document.createElement("textarea"); element.id = this.id;
			element.className = "full rounded";
			if(this.widget.userValues[0].value) {
				$(element).val(this.widget.userValues[0].value);
			} else if(this.defaultValue) {
				$(element).val(this.defaultValue);
			}
			if(this.disabled) {
				$(element).attr("disabled", "disabled");
			}
			this.element = element;
			div.append(element);
			return div;
		},
		drawable : function() {
			return (!privateMethods.isNullOrUndefined(this.displayName));
		},
		onDrawFinished : function(label, element) {
			
		},
		onAppendDone : function(label, element) {
			
		},
		preSubmit : function() {
			this.widget.userValues[0].value = $(this.element).val();
			//console.log("TextAreaWidget", this.widget.userValues);
		}
	};
	
	var widgetMap = {
		"CHECKBOX" : CheckboxWidget,
		"DATE" : DateWidget,
		"SELECT" : SelectWidget,
		"MULTI_SELECT" : MultiSelectWidget,
		"PASSWORD" : PasswordWidget,
		"RADIO" : RadioWidget,
		"TEXT" : TextWidget,
		"TEXTAREA" : TextAreaWidget
	};
	
	var privateMethods = {
		draw : function() {
			var $this = this;
			var options = $this.data("options");
			var template = options.templateObject; 
			options.elements = [];
			if(privateMethods.isNullOrUndefined(template) || privateMethods.isNullOrUndefined(template.pageElements) || !$.isArray(template.pageElements) || template.pageElements.length == 0) {
				options.onTemplateObjectEmpty.call($this);
			} else {
				for(var i = 0; i < template.pageElements.length; i++) {
					var widget = template.pageElements[i];
					if(widget && widgetMap[widget.typeId]) {
						var uiWidget = new widgetMap[widget.typeId]({widget : widget, options : options});
						if(uiWidget.drawable()) {
							var label = uiWidget.drawLabel();
							var element = uiWidget.drawElement();
							uiWidget.onDrawFinished(label, element);
							options.onAppend.call($this, label, element);
							uiWidget.onAppendDone(label, element);
							options.elements.push(uiWidget);
						}
					}
				}
				options.onFinishedAppending.call($this);
			}
		},
		isNullOrUndefined : function(arg) {
			return (arg == null || arg == undefined)
		},
		hasValidValues : function(widget) {
			var retVal = false;
			if(!this.isNullOrUndefined(widget)) {
				if(!this.isNullOrUndefined(widget.validValues) && widget.validValues.length > 0) {
					retVal = true;
				}
			}
			return retVal;
		},
		createUserValuesIfNecessary : function(widget) {
			if(this.isNullOrUndefined(widget.userValues)) {
				widget.userValues = [];
			}
		},
		removeInvalidValues : function(widget) {
			if(!this.isNullOrUndefined(widget)) {
				if(this.isNullOrUndefined(widget.validValues)) {
					widget.userValues = [];
				} else if(!this.isNullOrUndefined(widget.userValues)) {
					var indexesToRemove = [];
					$.each(widget.userValues, function(idx, userValue) {
						var contains = false;
						$.each(widget.validValues, function(idx2, validValue) {
							if(validValue.value == userValue.value) {
								contains = true;
							}
						});
						if(!contains) {
							indexesToRemove.push(idx);
						}
					});
					
					for(var i = indexesToRemove.length - 1; i >= 0; i--) {
						var idx = indexesToRemove[i];
						widget.userValues.splice(idx, 1);
					}
				}
			}
		},
		unsetDefaultValueIfNecessary : function(uiWidget, cache) {
			if(cache) {
				if(cache[uiWidget.widget.elementId]) {
					uiWidget.defaultValue = null;
				} else {
					cache[uiWidget.widget.elementId] = true;
				}
			}
		}
	};
	
	var methods = {
			
	    init : function( args ) {
	    	var $this = this;
	    	var options = $.extend({
	    		templateObject : null, //required - the JSON Object that represents the Template.  Must be a PageTemplate
	    		onAppend : null, //called when the object is appended to the DOM.
	    		onTemplateObjectEmpty : function() { /* called when there is no templateObject */
	    			
	    		},
	    		onLabelDraw : function(drawFor, required, text) { /* called when drawing the label for each element */
	    			var label = document.createElement("label");
	    			if(required) {
	    				$(label).addClass("required");
	    			}
	    			if(drawFor) {
	    				$(label).attr("for", drawFor);
	    			}
	    			label.innerHTML = text;
	    			return label;
	    		},
	    		onFinishedAppending : function() {}, /* called when all of the objects are done being appended */
	    		onCheckboxDraw : null, /* called after a single checkbox is created */
	    		onCheckboxDrawDone : null, /* called after all of the checkboxes are created */
	    		onRadioDraw : null, /* called after a single radio buttin is created */
	    		onRadioDrawDone : null /* called after all radio buttons are created */
	    	}, args);
	    	options.defaultValuesCache = $.cookies.get("_setDefaultValues");
	    	if(options.defaultValuesCache == null || options.defaultValuesCache == undefined) {
	    		options.defaultValuesCache = {};
	    	}
	    	$this.data("options", options);
	    	privateMethods.draw.call(this);
	    },
	    getObject : function() {
	    	var $this = this;
			var options = $this.data("options");
			var template = options.templateObject; 
			$.each(options.elements, function(idx, element) {
				if($.isFunction(element.preSubmit)) {
					element.preSubmit();
				}
			});
			$.cookies.set("_setDefaultValues", JSON.stringify(options.defaultValuesCache));
			return options.templateObject;
	    }
	}
	
  	$.fn.openiamUITemplate = function( method ) {
    	if ( methods[method] ) {
      		return methods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
    	} else if ( typeof method === 'object' || ! method ) {
      		return methods.init.apply( this, arguments );
    	} else {
      		$.error( 'Method ' +  method + ' does not exist on jQuery.openiamUITemplate' );
    	}    
  	};
})( jQuery );