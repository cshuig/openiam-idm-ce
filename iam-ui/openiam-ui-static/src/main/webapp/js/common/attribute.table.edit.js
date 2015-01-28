console = window.console || {};
console.log = window.console.log || function() {};

(function( $ ){
	
	var privateMethods = {
		getElement : function(id) {
			var $this = this;
			var $options = $this.data("attributeTableEdit");
			
			var element = null;
			if(id != null) {
				if($.isArray($options.elements)) {
					$.each($options.elements, function(idx, val) {
						if(val.id == id) {
							element = val;
						}
					});
				}
			}
			return element;
		},
		getElementName : function(id) {
			var $this = this;
			var $options = $this.data("attributeTableEdit");
			
			var element = privateMethods.getElement.call($this, id);
			return (element != null) ? element.name : null;
		},
		onMetadataTypeChange : function() {
			var $this = this;
			var $options = $this.data("attributeTableEdit");
			
			if($.isArray($options.objectArray)) {
				$.each($options.objectArray, function(idx, prop) {
					prop.metadataId = null;
					prop.metadataName = null;
				});
			}
			
			methods.request.call($this);
		},
        updateObject: function(args){
            var $this = args.container;
            var targetBean = args.targetBean;

            targetBean.name = args.sourceBean.name;
            targetBean.value = args.sourceBean.value;
            targetBean.metadataId = args.sourceBean.metadataId;
            targetBean.metadataName = privateMethods.getElementName.call($this, args.sourceBean.metadataId);
            if (!targetBean.metadataName) {
                targetBean.metadataName = args.sourceBean.metadataName;
            }
            targetBean.isMultivalued = args.sourceBean.isMultivalued;
            targetBean.values = args.sourceBean.values;

            $("#editDialog").modalEdit("hide");
            $this.persistentTable("draw");
        },
        createObject: function(args){
            var $this = args.container;
            args.sourceBean.metadataName = privateMethods.getElementName.call($this, args.sourceBean.metadataId);
            $("#editDialog").modalEdit("hide");
            $this.persistentTable("addObject", args.sourceBean);
            $this.persistentTable("draw");
        }
	};
	
	var methods = {
		onChange : function() {
			var $this = this;
			var $options = $this.data("attributeTableEdit");
			privateMethods.onMetadataTypeChange.call($this);
		},
		request : function() {
			var $this = this;
			var $options = $this.data("attributeTableEdit");
			
			var $that = this;
			var id = $options.getMetadataTypeId.call($this);
			if(id != null) {
				$.ajax({
					url : "rest/api/metadata/element/search",
					data : {type : id, from : 0, size : 1000},
					type: "GET",
					dataType : "json",
					success : function(data, textStatus, jqXHR) {
						$options.elements = data.beans;
						if(!$.isArray($options.elements)) {
							$options.elements = [];
						}
						methods.draw.call($this);
					},
					error : function(jqXHR, textStatus, errorThrown) {
						$options.elements = [];
						OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
					}
				});
			} else {
				$options.elements = [];
				methods.draw.call($this);
			}
		},
		draw : function() {
			var $this = this;
			var $options = $this.data("attributeTableEdit");
			
			$.each($options.dialogModalFields, function(idx, field) {
				if(field.fieldName == "metadataId") {
					field.items = $options.elements;
					if($options.elements.length == 0) {
						field.readonly = true;
					} else {
						field.readonly = false
					}
					field.onChange = function() {
						var $dialog = this;
						var selectValue = this.find("#metadataId").val();
						
						if(selectValue != null && selectValue != undefined && selectValue != "") {
							var currentName = this.find("#name").val();
							var currentValue = this.find("#value").val();
							var element = privateMethods.getElement.call($this, selectValue);
							
							//if(currentName == null || currentName == undefined || currentName == "") {
								if($dialog.find("#name").is("input")) {
									$dialog.find("#name").val(element.defaultName).addClass("field-notification").addClass("modified");
								}
							//}
							
							//if(currentValue == null || currentValue == undefined || currentValue == "") {
								$dialog.find("input[id^='values']:first").val(element.defaultValue).addClass("field-notification").addClass("modified");
							//}
								
							setTimeout(function() {
								$dialog.find("#name").removeClass("field-notification").removeClass("modified");
								$dialog.find("input[id^='values']:first").removeClass("field-notification").removeClass("modified");
							}, 1000);
						}
					};
				}
			});
			
			$this.persistentTable({
				emptyMessage : $options.emptyMessage,
				createEnabled : $options.createEnabled,
				objectArray : $options.objectArray,
				createBtnId : "newAttribute",
				headerFields : $options.headerFields,
				fieldNames : $options.fieldNames,
				deleteEnabledField : true,
				editEnabledField : $options.editEnabledField,
				createText : localeManager["openiam.ui.common.attributes.create.new"],
				actionsColumnName : localeManager["openiam.ui.common.actions"],
				tableTitle : $options.tableTitle,
				onDeleteClick : function(obj) {
                    if($options.OnDelete!=null && $.isFunction($options.OnDelete)){
                        $options.OnDelete(obj.id);
                    }
				},
				equals : function(obj1, obj2) {
					return obj1.name == obj2.name;
				},
				onEditClick : function(obj) {
					var $this = this;

                    var tmp = {fieldsArray: $options.dialogModalFields};

                    var dialogFieldsObj = jQuery.extend(true, {}, tmp);

                    if($options.readOnlyFildsOnEdit && $options.readOnlyFildsOnEdit.length >0){
                        $.each($options.readOnlyFildsOnEdit, function(idx, fieldName) {
                            $.each(dialogFieldsObj.fieldsArray, function(idx, field) {
                                if(field.fieldName==fieldName){
                                    field.readonly=true;
                                    return false;
                                }
                            });
                        });
                    }

					$("#editDialog").modalEdit({
						fields: dialogFieldsObj.fieldsArray,
			            dialogTitle: localeManager["openiam.ui.common.attributes.edit"],
			            onSubmit: function(bean){
                             bean.id=obj.id;
                             var args = {sourceBean:bean, targetBean:obj, container:$this};

                            if($options.OnSubmit!=null && $.isFunction($options.OnSubmit)){
                                $options.OnSubmit(args, privateMethods.updateObject);
                            } else {
                                privateMethods.updateObject.call($this, args);
                            }
			            }
	                });
					$("#editDialog").modalEdit("show", obj);
				},
				onCreateClick : function() {
					var $this = this;
					var obj = {};
					$("#editDialog").modalEdit({
						fields: $options.dialogModalFields,
			            dialogTitle: localeManager["openiam.ui.common.attributes.new"],
			            onSubmit: function(bean){
                            var args = {sourceBean:bean, container:$this};
                            if($options.OnSubmit!=null && $.isFunction($options.OnSubmit)){
                                $options.OnSubmit(args, privateMethods.createObject);
                            }else{
                                privateMethods.createObject.call($this, args);
                            }
			            }
	                });
					$("#editDialog").modalEdit("show", obj);
				}
			});
		},
		init : function( args ) {
			var $this = this;
	    	var options = $.extend({
	    		emptyMessage : args.emptyMessage || localeManager["openiam.ui.shared.resource.attributes.empty"],
	    		objectArray : args.objectArray,
	    		headerFields : args.headerFields || [
					localeManager["openiam.ui.common.attribute.name"],
					localeManager["openiam.ui.metadata.element"],
					localeManager["openiam.ui.common.attribute.value"]
				],
				editEnabledField : args.editEnabledField || true,
                readOnlyFildsOnEdit: args.headerFields || null,
				dialogModalFields : args.dialogModalFields || null,
				fieldNames : args.fieldNames || null,
				createText : args.createText || localeManager["openiam.ui.common.attributes.create.new"],
				actionsColumnName : args.actionsColumnName || localeManager["openiam.ui.common.actions"],
				tableTitle : args.tableTitle || localeManager["openiam.ui.common.attributes"],
				getMetadataTypeId : args.getMetadataTypeId || function() {
					return $("#metadataType").selectableSearchResult("getId")
				},
                OnSubmit: null,
                OnDelete: null,
                createEnabled: args.createEnabled || true
	    	}, args);
	    	
	    	options.elements = [];
	    	
	    	if(!$.isArray(options.dialogModalFields)) {
	    		$.error("'dialogModalFields' is required");
	    	}
	    	
	    	if(!$.isArray(options.fieldNames)) {
	    		$.error("'fieldNames' is required");
	    	}

	    	$this.data("attributeTableEdit", options);
            methods.request.call($this);
		}
	};
		
	$.fn.attributeTableEdit = function( method ) {
		if(this.length > 0) {
    		if ( methods[method] ) {
      			return methods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
    		} else if ( typeof method === 'object' || ! method ) {
      			return methods.init.apply( this, arguments );
    		} else {
      			$.error( 'Method ' +  method + ' does not exist on jQuery.attributeTableEdit' );
    		}
		}
  	};
})( jQuery );