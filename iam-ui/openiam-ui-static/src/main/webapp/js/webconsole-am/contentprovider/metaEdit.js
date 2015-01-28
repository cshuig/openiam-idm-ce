OPENIAM = window.OPENIAM || {};
OPENIAM.ContentProvider = window.OPENIAM.ContentProvider || {};
OPENIAM.ContentProvider.Meta = {
	_amAttributes : [],
	_bean : null,
	getAmAttributeName : function(id) {
		var name = "";
		$.each(this._amAttributes, function(idx, val) {
			if(val.id == id) {
				name = val.name;
			}
		});
		return name;
	},
	updateJSON : function() {
		var obj = this._bean;
		obj.name = $("#name").val();
		obj.metaType = {id : $("#metaTypeId").val()};
		obj.pattern = {id : OPENIAM.ENV.PatternId};
		obj.uriPatternId = OPENIAM.ENV.PatternId;
	},
	populate : function() {
		var $this = this;
		var dialogFields = [
			{fieldName: "propertyName", type:"text",label:localeManager["contentprovider.provider.meta.data.type.prop.name"], required : true},
			{fieldName: "propertytype", type:"select",label:localeManager["contentprovider.provider.meta.data.type.prop.type"], required:true, items : [
				{id : "groovy", name : localeManager["contentprovider.meta.edit.groovy.script"]},
				{id : "static", name : localeManager["contentprovider.meta.edit.static.value"]},
				{id : "user", name : localeManager["contentprovider.provider.meta.data.type.val.user"]},
				{id : "emptyValue", name : localeManager["contentprovider.meta.edit.empty.value"]}
			]},
			{fieldName: "propertyInputValue", type:"text",label:localeManager["contentprovider.provider.meta.data.type.prop.val"], required:false, hidden : true},
			{fieldName: "propertyUserValue", type:"select",label:localeManager["contentprovider.provider.meta.data.type.prop.val"], required:false, items : $this._amAttributes, hidden : true},
			{fieldName: "propagateThroughProxy", type:"checkbox", label:localeManager["openiam.ui.meta.rule.propagate.through.proxy"]}
		];
		
		var obj = this._bean;
		$("#name").val(obj.name);
		if(obj.metaType != null && obj.metaType != undefined) {
			$("#metaTypeId").val(obj.metaType.id);
		}
		if(!$.isArray(obj.metaValueSet)) {
			obj.metaValueSet = [];
		}
		$("#properties").persistentTable({
			emptyMessage : localeManager["contentprovider.meta.edit.meta.not.assoc"],
			createEnabled : true,
			actionsColumnName : localeManager["openiam.ui.common.actions"],
			objectArray : obj.metaValueSet,
			createBtnId : "newAttribute",
			headerFields : [
				localeManager["contentprovider.provider.meta.data.type.prop.name"],
				localeManager["contentprovider.provider.meta.data.type.prop.type"],
				localeManager["contentprovider.provider.meta.data.type.prop.val"],
				localeManager["openiam.ui.meta.rule.propagate.through.proxy"]
			],
			fieldNames : [
				"name",
				function(bean) {
					var val = "";
					if(bean.emptyValue) {
						val = localeManager["contentprovider.meta.edit.empty.value"];
					} else if(bean.groovyScript != null && bean.groovyScript != undefined && bean.groovyScript != "") {
						val = localeManager["contentprovider.meta.edit.groovy.script"];
					} else if(bean.staticValue != null && bean.staticValue != undefined && bean.staticValue != "") {
						val = localeManager["contentprovider.meta.edit.static.value"];
					} else if(bean.amAttribute != null && bean.amAttribute != undefined && bean.amAttribute.id != null && bean.amAttribute.id != undefined ) {
						val = localeManager["contentprovider.provider.meta.data.type.val.user"];
					}
					return val;
				},
				function(bean) {
					var val = "";
					if(!bean.emptyValue) {
						if(bean.groovyScript != null && bean.groovyScript != undefined && bean.groovyScript != "") {
							val = bean.groovyScript;
						} else if(bean.staticValue != null && bean.staticValue != undefined && bean.staticValue != "") {
							val = bean.staticValue;
						} else if(bean.amAttribute != null && bean.amAttribute != undefined && bean.amAttribute.id != null && bean.amAttribute.id != undefined ) {
							val = bean.amAttribute.attributeName;
						}
					}
					return val;
				},
				"propagateThroughProxy"
			],
			deleteEnabledField : true,
			editEnabledField : true,
			createText : localeManager["openiam.ui.common.add"],
			actionsColumnName : localeManager["openiam.ui.common.actions"],
			onDeleteClick : function(obj) {
				
			},
			equals : function(obj1, obj2) {
				return obj1.name == obj2.name;
			},
			onEditClick : function(obj) {
				var $that = this;
				$("#editDialog").modalEdit({
					fields: dialogFields,
		            dialogTitle: localeManager["openiam.ui.common.attributes.edit"],
		            onSubmit: function(bean){
                         obj.name = $("#propertyName").val();
                         if($("#propertytype").val() == "emptyValue") {
                         	obj.emptyValue = true;
                         	obj.groovyScript = null;
                         	obj.staticValue = null;
                         	obj.amAttribute = null;
                         } else if($("#propertytype").val() == "groovy") {
                         	obj.emptyValue = false;
                         	obj.groovyScript = $("#propertyInputValue").val();
                         	obj.staticValue = null;
                         	obj.amAttribute = null;
                         } else if($("#propertytype").val() == "static") {
                         	obj.emptyValue = false;
                         	obj.groovyScript = null;
                         	obj.staticValue = $("#propertyInputValue").val();
                         	obj.amAttribute = null;
                         } else if($("#propertytype").val() == "user") {
                         	obj.emptyValue = false;
                         	obj.groovyScript = null;
                         	obj.staticValue = null;
                         	obj.amAttribute = {id : $("#propertyUserValue").val()};
                         	obj.amAttribute.attributeName = $this.getAmAttributeName(obj.amAttribute.id);
                         }
                         obj.propagateThroughProxy = bean.propagateThroughProxy;
                         //$("#properties").persistentTable("addObject", bean);
                         $("#properties").persistentTable("draw");
                         $("#editDialog").dialog("close");
		            },
		            onShown : function(bean) {
		            	$("#propertyName").val(bean.name);
		            	if(bean.emptyValue) {
		            		$("#propertytype").val("emptyValue");
		            		$("#propertyInputValue").val("").hide();
		            		$("#propertyUserValue").val("").hide();
		            	} else if(bean.groovyScript != null && bean.groovyScript != undefined && bean.groovyScript != "") {
							$("#propertytype").val("groovy");
							$("#propertyInputValue").val(bean.groovyScript).show();
							$("#propertyUserValue").hide();
						} else if(bean.staticValue != null && bean.staticValue != undefined && bean.staticValue != "") {
							$("#propertytype").val("static");
							$("#propertyInputValue").val(bean.staticValue).show();
							$("#propertyUserValue").hide();
						} else if(bean.amAttribute != null && bean.amAttribute != undefined && bean.amAttribute.id != null && bean.amAttribute.id != undefined ) {
							$("#propertytype").val("user");
							$("#propertyUserValue").val(bean.amAttribute.id).show();
							$("#propertyInputValue").hide();
						}
						
						$("#propertytype").unbind("change").change(function() {
							if($(this).val() == "user") {
								$("#propertyUserValue").show().closest("tr").find("label").show();
								$("#propertyInputValue").hide().closest("tr").find("label").hide();
							} else if($(this).val() == "emptyValue") {
								$("#propertyUserValue").hide().closest("tr").find("label").hide();
								$("#propertyInputValue").hide().closest("tr").find("label").hide();
							} else {
								$("#propertyUserValue").hide().closest("tr").find("label").hide();
								$("#propertyInputValue").show().closest("tr").find("label").show();
							}
						});
		            }
                });
				$("#editDialog").modalEdit("show", obj);
			},
			onCreateClick : function() {
				var $that = this;
				var obj = {};
				obj.propagateThroughProxy = true;
				$("#editDialog").modalEdit({
					fields: dialogFields,
		            dialogTitle: localeManager["openiam.ui.common.attributes.new"],
		            onSubmit: function(bean){
                        bean.name = $("#propertyName").val();
                        if($("#propertytype").val() == "emptyValue") {
                         	bean.emptyValue = true;
                         	bean.groovyScript = null;
                         	bean.staticValue = null;
                         	bean.amAttribute = null;
                        } else if($("#propertytype").val() == "groovy") {
                        	bean.emptyValue = false;
                         	bean.groovyScript = $("#propertyInputValue").val();
                         	bean.staticValue = null;
                         	bean.amAttribute = null;
                         } else if($("#propertytype").val() == "static") {
                         	bean.emptyValue = false;
                         	bean.groovyScript = null;
                         	bean.staticValue = $("#propertyInputValue").val();
                         	bean.amAttribute = null;
                         } else if($("#propertytype").val() == "user") {
                         	bean.emptyValue = false;
                         	bean.groovyScript = null;
                         	bean.staticValue = null;
                         	bean.amAttribute = {id : $("#propertyUserValue").val()};
                         	bean.amAttribute.attributeName = $this.getAmAttributeName(bean.amAttribute.id);
                         }
                         $("#properties").persistentTable("addObject", bean);
                         $("#properties").persistentTable("draw");
                         $("#editDialog").dialog("close");
		            },
		            onShown : function(bean) {
		            	$("#propertytype").unbind("change").change(function() {
							if($(this).val() == "user") {
								$("#propertyUserValue").show().closest("tr").find("label").show();
								$("#propertyInputValue").hide().closest("tr").find("label").hide();
							} else if($(this).val() == "emptyValue") {
								$("#propertyUserValue").hide().closest("tr").find("label").hide();
								$("#propertyInputValue").hide().closest("tr").find("label").hide();
							} else {
								$("#propertyUserValue").hide().closest("tr").find("label").hide();
								$("#propertyInputValue").show().closest("tr").find("label").show();
							}
						});
		            }
                });
				$("#editDialog").modalEdit("show", obj);
			}
		});
	},
	getMetaRule : function() {
		var $this = this;
		$.ajax({
			url : "getPatternMeta.html",
			"data" : {id : OPENIAM.ENV.MetaId},
			type: "GET",
			dataType : "json",
			success : function(data, textStatus, jqXHR) {
				console.log(data);
				$this._bean = data;
				$this.populate();
			},
			error : function(jqXHR, textStatus, errorThrown) {
				OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
			}
		});
	},
	getAmAttributes : function() {
		var $this = this;
		 $.ajax({
			url : "getAMAttributes.html",
			"data" : null,
			type: "GET",
			dataType : "json",
			success : function(data, textStatus, jqXHR) {
				$this._amAttributes = data.beans;
				$this.getMetaRule();
			},
			error : function(jqXHR, textStatus, errorThrown) {
				OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
			}
		});
	},
	remove : function() {
		$.ajax({
            url : "deleteMetaData.html",
            data : {
                id : OPENIAM.ENV.MetaId,
                patternId : OPENIAM.ENV.PatternId
            },
            type : "POST",
            dataType : "json",
            success : function(data, textStatus, jqXHR) {
                if (data.status == 200) {
                    OPENIAM.Modal.Success({
                        message : data.successMessage,
                        showInterval : 1000,
                        onIntervalClose : function() {
                            window.location.href = "editProviderPattern.html?id=" + OPENIAM.ENV.PatternId + "&providerId=" + OPENIAM.ENV.ProviderId;
                        }
                    });
                } else {
                    OPENIAM.Modal.Error({
                        errorList : data.errorList
                    });
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
	},
	save : function() {
		var $this = this;
	 	$this.updateJSON();
		 $.ajax({
            url : "saveMetaData.html",
            data : JSON.stringify($this._bean),
            type : "POST",
            dataType : "json",
            contentType : "application/json",
            success : function(data, textStatus, jqXHR) {
                if (data.status == 200) {
                    OPENIAM.Modal.Success({
                        message : data.successMessage,
                        showInterval : 1000,
                        onIntervalClose : function() {
                            if (data.redirectURL != null && data.redirectURL != undefined && data.redirectURL.length > 0) {
                                window.location.href = data.redirectURL;
                            } else {
                                window.location.reload(true);
                            }
                        }
                    });
                } else {
                    OPENIAM.Modal.Error({
                        errorList : data.errorList
                    });
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });
	},
    init : function() {
    	var $this = this;
        $("#saveBtn").click(function() {
           	$this.save();
        });

        $("#deleteMetaData").click(function() {
           	$this.remove();
        });
        
      	this.getAmAttributes();
    }
};

$(document).ready(function() {
    OPENIAM.ContentProvider.Meta.init();
});

$(window).load(function() {

});