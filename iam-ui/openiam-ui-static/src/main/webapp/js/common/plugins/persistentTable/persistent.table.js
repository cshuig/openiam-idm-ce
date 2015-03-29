console = window.console || {};
console.log = window.console.log || function() {};

(function( $ ){
	
	var privateMethods = {
		drawEmpty : function(tbody, numHeaderFields) {
			var $this = this;
			var options = $this.data("options");
			
			var tr = document.createElement("tr");
				var td = document.createElement("td"); $(td).attr("colspan", numHeaderFields + 1); td.className = "empty"; td.innerHTML = options.emptyMessage;
				$(tr).append(td);
			$(tbody).append(tr);
		},
		resetColors : function() {
			var $this = this;
			var options = $this.data("options");
			
			$this.find("tbody tr").each(function(idx, val) {
				$(val).removeClass("even").removeClass("odd");
				var isEven = (idx == 0 || idx % 2 == 0);
				$(val).addClass(isEven ? "even" : "odd");
			});
		},
		drawColumn : function(tbody, obj, isEven, idCounter) {
			var $this = this;
			var options = $this.data("options");
            var childTr = null;
			var fieldDisplayers = options.fieldDisplayers;

			
			var tr = document.createElement("tr"); if(obj.isDisableClicked) { $(tr).addClass("disabled");}
                $(tr).addClass(isEven ? "even" : "odd");
			if(options.sortEnabled) {
				$(tr).addClass("openiam-sortable");
			}
			$(tr).data("entity", obj).attr("id","row"+idCounter);

            if(options.isExpandable){
                $(tr).addClass("parentRow");

                var td = document.createElement("td");
                $(td).addClass("openiam-expandable");

                if(obj.hasOwnProperty("hasChild") && obj.hasChild){
                    var childTr = document.createElement("tr");
                        $(childTr).addClass(isEven ? "even" : "odd").attr("id", "child-"+$(tr).attr("id")).css("display","none");
                    var childTd = document.createElement("td");
                      $(childTd).attr("colspan", options.headerFields.length + 2)

                    $(childTr).append(childTd);

                    $(td).addClass("openiam-expand").css("cursor","pointer").attr("title",localeManager["openiam.ui.controls.click.to.expand.or.collapse"]);

                    $(td).click(function(){
                        var pr = $(this).parents("tr.parentRow:first");
                        if($(this).hasClass("openiam-expand")){
                            $(this).removeClass("openiam-expand").addClass("openiam-collapse");
                            options.onExpand.call($this, tr);
                        } else{
                            $(this).removeClass("openiam-collapse").addClass("openiam-expand");
                            options.onCollapse.call($this, tr);
                        }
                        pr.siblings('#child-'+pr.attr("id")).toggle();
                    });

                }
                $(tr).append(td);
            }

			for(var j = 0; j < options.fieldNames.length; j++) {
				var fieldName = options.fieldNames[j];
                var fieldValue = '';
                if($.isFunction(fieldName)) {
                	fieldValue = fieldName(obj)
                } else  if (fieldName == 'values' && obj['isMultivalued'] != undefined) {
                    if (obj['isMultivalued']) {
                        fieldValue = obj['values'].join(', ');
                    } else {
                        fieldValue = obj['value'];
                    }
                } else {
                    fieldValue = obj[fieldName];
                }

				if($.isFunction(fieldDisplayers[fieldName])) {
					fieldValue = fieldDisplayers[fieldName](fieldValue);
				}
				
				var td = document.createElement("td");
				if(fieldValue != null && fieldValue != undefined) {
					td.innerHTML = fieldValue;
				}
				
				$(tr).append(td);
			}
			
			if(options.actionsColumnName) {
				var td = document.createElement("td");
					var editEnabled = (options.editEnabledField === true || obj[options.editEnabledField] || ($.isFunction(options.editEnabledField) && options.editEnabledField.call($this, obj)));
					if(editEnabled) {
						var editA = document.createElement("a"); $(editA).attr("href", "javascript:void(0);");
							var editImg = document.createElement("div"); $(editImg).addClass("openiam-edit-icon").addClass("openiam-image-option");
						$(editA).append(editImg);
						$(td).append(editA);
						$(editA).click(function() {
							options.onEditClick.call($this, obj);
						});
					}
					if(options.deleteEnabledField === true || obj[options.deleteEnabledField]) {
						var deleteA = document.createElement("a"); $(editA).attr("href", "javascript:void(0);");
							var deleteImg = document.createElement("div"); $(deleteImg).addClass("openiam-delete-icon").addClass("openiam-image-option");
						$(deleteA).append(deleteImg);
						$(td).append(deleteA);
						$(deleteA).click(function() {
							if(options.greyOutOnDelete) {
								obj.isDisableClicked = (obj.isDisableClicked === true) ? false : true;
							} else {
								var idxToPurge = null;
								$.each(options.objectArray, function(idx, objAtIdx) {
									if(options.equals(obj, objAtIdx)) {
										idxToPurge = idx;
									}
								});
								if(idxToPurge != null && idxToPurge != undefined) {
									options.objectArray.splice(idxToPurge, 1);
								}
							}
							methods.onOrderEdit.call($this);
							options.onDeleteClick.call($this, obj);
							methods.draw.call($this);
						});
					}
				$(tr).append(td);
			}
			$(tbody).append(tr);
            if(childTr!=null){
                $(tbody).append(childTr);
            }
        }
	};
	
	var methods = {
		onOrderEdit : function() {
			var $this = this;
			var options = $this.data("options");
			
			privateMethods.resetColors.call($this);
			$this.find("tbody tr").each(function(idx, val) {
		    	options.onOrderEdit.call($this, idx, $(this).data("entity"));
			});
		},
		setPropertyOnAll : function(propertyName, propertyValue) {
			var $this = this;
			var options = $this.data("options");
			
			if(options.objectArray == null || options.objectArray == undefined) {
				options.objectArray = [];
			}
			
			$.each(options.objectArray, function(idx, obj) {
				obj[propertyName] = propertyValue;
			});
		},
		addObject : function(obj) {
			var $this = this;
			var options = $this.data("options");
			
			if(options.objectArray == null || options.objectArray == undefined) {
				options.objectArray = [];
			}
			
			var idxOfObj = null;
			$.each(options.objectArray, function(idx, objAtIdx) {
				if(options.equals(obj, objAtIdx)) {
					idxOfObj = idx;
				}
			});
			
			if(idxOfObj == null) {
				options.objectArray.push(obj);
			}
		},
		draw : function() {
			var $this = this;
			var options = $this.data("options");
			
			var numHeaderFields = options.headerFields.length;

            var totalColCount = numHeaderFields;
            if(options.actionsColumnName)
                totalColCount = totalColCount + 1;
            if(options.isExpandable)
                totalColCount = totalColCount + 1;


			var tableTitle = options.tableTitle;
			
			var table = document.createElement("table"); $(table).css("width", "100%"); $(table).attr("cellspacing", "1"); table.className = "yui";
				var thead = document.createElement("thead");

                if(tableTitle != null) {
                    var tr = document.createElement("tr");
                        var td = document.createElement("td"); $(td).attr("colspan", totalColCount); td.className = "yui-title";
                        	var tableLbl = $(document.createElement("label")).text(tableTitle);
                        	if(options.required) {
                        		tableLbl.addClass("required");
                        	}
                        $(td).append(tableLbl);
                    $(tr).append(td);
                    $(thead).append(tr);
                }
				
                if(options.createEnabled) {
                    var tr = document.createElement("tr");

					var td = document.createElement("td"); $(td).attr("colspan", totalColCount);

                    var btn = document.createElement("button"); btn.className = "redBtn"; btn.innerHTML = options.createText; btn.style.cssFloat='right';

					if(options.createBtnId) {
                        btn.id = options.createBtnId;
                    }
                    $(btn).click(function() {
                        options.onCreateClick.call($this);
                        return false;
                    });
                    $(td).append(btn);

					if(options.additionalBtnId || options.additionalBtnText) {
						var btnAdditional = document.createElement("button"); btnAdditional.className = "redBtn"; btnAdditional.innerHTML = options.additionalBtnText;
						btnAdditional.id = options.additionalBtnId;
						btnAdditional.setAttribute('type', 'button');
						btnAdditional.style.marginRight='5px';
						btnAdditional.style.cssFloat='right';
						$(btnAdditional).click(function() {
							options.onAdditionalBtnClick.call($this);
						});
						$(td).append(btnAdditional);
					}
					$(tr).append(td);

                    $(thead).append(tr);
                }

                if(options.showHeader){
                    var tr = document.createElement("tr");
                    if(options.isExpandable){
                        var th = document.createElement("th");
                        $(tr).append(th);
                    }

                    for(var i = 0; i < numHeaderFields; i++) {
                        var headerField = options.headerFields[i];
                        var th = document.createElement("th"); th.innerHTML = headerField;
                        $(tr).append(th);
                    }
                    if(options.actionsColumnName) {
                        var th = document.createElement("th"); th.innerHTML = options.actionsColumnName;
                        $(tr).append(th);
                    }
                    $(thead).append(tr);
                }

				
				var tbody = document.createElement("tbody");
				if(options.objectArray == null || options.objectArray == undefined || options.objectArray.length == 0) {
					privateMethods.drawEmpty.call($this, tbody, numHeaderFields);
				} else {
                    var idCounter = new Date().getTime();
					for(var i = 0; i < options.objectArray.length; i++) {
						var obj = options.objectArray[i];
						privateMethods.drawColumn.call($this, tbody, obj, ((i == 0) || ((i % 2) == 0)), idCounter+i);
					}

                    if(options.isExpandable){
                        $(table).find("tr.parentRow td.openiam-expand, tr.parentRow td.openiam-collapse").click(function(){
                                var pr = $(this).parents("tr.parentRow:first");
                                if($(this).hasClass("openiam-expand")){
                                    $(this).removeClass("openiam-expand").addClass("openiam-collapse");
                                    options.onExpand.call($this, tr);
                                } else{
                                    $(this).removeClass("openiam-collapse").addClass("openiam-expand");
                                    options.onCollapse.call($this, tr);
                                }
                                pr.siblings('#child-'+pr.attr("id")).toggle();
                            });
                        $(table).find('tr[id^=child-]').hide();
                    }


				}
			$(table).append(thead, tbody);
			$(this).html(table);
			   /*
            $(table).tablesorter({
				debug: false 
			});
			  */
			if(options.sortEnabled) {
				$(table).sortable({ axis: "y",
		            delay: 150,
		            items: " tbody tr",
		            containment: "parent",
		            tolerance: "pointer",
		            stop: function(event, ui) {
		            	methods.onOrderEdit.call($this);
		            	/*
		            	privateMethods.resetColors.call($this);
		                $(table).find("tbody tr").each(function(idx, val) {
		                	options.onOrderEdit.call($this, idx, $(this).data("entity"));
		                });
		                */
		            }
		        });
			}
		},
	    init : function( args ) {
	    	var $this = this;
	    	var options = $.extend({
	    		emptyMessage : null, /* message to show when there are no elements */
	    		createEnabled : false, /* is creation of this element enabled? */
	    		objectArray : [], /* the array of entities - JSON object from backend */
	    		headerFields : null, /* array of header fields */
	    		fieldNames : null, /* array of fieldNames in the underlying JSON Object, that are used to display the values in the table */
	    		deleteEnabledField : false, /* the field name that determines if deletion is enabled */
	    		editEnabledField : false, /* the field name that determines if edit is enabled on the JSON object */
	    		createText : "", /* text of the 'create' button */
	    		actionsColumnName : null, /* name of the 'Actions' column */
	    		tableTitle : null, /* title of the table */
	    		greyOutOnDelete : false,
	    		createBtnId : null,
	    		sortEnabled : false,
	    		onOrderEdit : function() {},
	    		fieldDisplayers : {},
                isExpandable : false,
                showHeader: true,
                required : false,
				additionalBtnId : null,
				additionalBtnText : null,

				onAdditionalBtnClick : function(obj) {

				},

	    		equals : function(obj1, obj2) { /* method to determine if two objects are equal - used by delete and create */
	    			
	    		},
	    		onDeleteClick : function(obj) {
	    			
	    		},
	    		onEditClick : function(obj) {
	    			
	    		},
	    		onCreateClick : function() {
	    			
	    		},
                onExpand : function(obj) {

                },
                onCollapse : function(obj) {

                }
	    	}, args);
	    	
	    	if(options.objectArray == null) {
	    		options.objectArray = [];
	    	}
	    	
	    	/*
	    	for(var i in options) {
	    		if(options[i] == null) {
	    			$.error(options[i] + " not passed as an argument for " + i);
	    		}
	    	}
	    	*/
	    	
	    	$this.data("options", options);
	    	methods.draw.call(this);
	    }
	};

  	$.fn.persistentTable = function( method ) {
    	if ( methods[method] ) {
      		return methods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
    	} else if ( typeof method === 'object' || ! method ) {
      		return methods.init.apply( this, arguments );
    	} else {
      		$.error( 'Method ' +  method + ' does not exist on jQuery.persistentTable' );
    	}    
  	};
})( jQuery );