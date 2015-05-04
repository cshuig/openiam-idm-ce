console = window.console || {};
console.log = window.console.log || function() {};

(function( $ ){
    var TARGET_TYPES_URL = "rest/api/auditlog/targetTypes";

    var privateMethods = {
        request : function() {
            var $this = this;
            var $options = $this.data("options");
            $.ajax({
                url : $options.targetTypeSearch,
                "data" : null,
                type: "GET",
                dataType : "json",
                success : function(data, textStatus, jqXHR) {
                    $options.searchFilterBean = data;
                    privateMethods.detailedDraw.call($this);
                },
                error : function(jqXHR, textStatus, errorThrown) {
                    OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
                }
            });
        },
        detailedDraw : function() {
            var $this = this;
            var $options = $this.data("options");

            var modalFields = [];
            if($options.showStartDate && $options.showStartDate.shown) {
            	modalFields.push({fieldName : "fromDate", type : "datetimepicker", label : localeManager["openiam.ui.audit.log.from.date"], required : true, placeholder : localeManager["openiam.ui.audit.log.select.date"]});
            }
            if($options.showEndDate && $options.showEndDate.shown) {
            	modalFields.push({fieldName : "toDate", type : "datetimepicker", label : localeManager["openiam.ui.audit.log.to.date"], required : true, placeholder : localeManager["openiam.ui.audit.log.select.date"]});
            }

            modalFields.push({fieldName : "requestorId", type : "text", label : localeManager["openiam.ui.audit.log.requestor"], required : false});

            modalFields.push({fieldName : "managedSystem", type : "select", label : localeManager["openiam.ui.idm.prov.mngsys.header"], required : false, items : $options.searchFilterBean.managedSystems});

            modalFields.push({fieldName : "result", type : "select", label : localeManager["openiam.ui.audit.log.result"], required : false, items : $options.searchFilterBean.auditTargetStatus});

            modalFields.push({fieldName : "action", type : "select", label : localeManager["openiam.ui.audit.log.action"], required : false, items : $options.searchFilterBean.auditTargetActions});

            //Target Type input
            modalFields.push({fieldName : "targetType", type : "select", label : localeManager["openiam.ui.audit.log.target.type"], required : false, items : $options.searchFilterBean.auditTargetTypes});

            modalFields.push({fieldName : "targetId", type : "text", label : localeManager["openiam.ui.audit.log.target.id"], required : false});

            modalFields.push({fieldName : "targetLogin", type : "text", label : localeManager["openiam.ui.audit.log.target.login"], required : false});
            this.modalEdit({
                fields: modalFields,
                dialogTitle: $options.dialogTitle,
                saveBtnTxt : $options.searchBtnText,
                onSubmit: function(bean){
                    $this.modalEdit("hide");
                    $options.onSearch(privateMethods.getArguments.call($this));
                },
                onShown : function() {
                	if($options.showStartDate && $options.showStartDate.shown) {
						$("#fromDate").datepicker({ dateFormat: OPENIAM.ENV.DateFormatDP,showOn: "button",changeMonth:true, changeYear:true});
						if($options.showStartDate.value) {
							setTimeout(function() {
								$("#fromDate").datepicker("setDate", new Date($options.showStartDate.value));
							}, 200);
						}
                	}
                	if($options.showEndDate && $options.showEndDate.shown) {
						$("#toDate").datepicker({ dateFormat: OPENIAM.ENV.DateFormatDP,showOn: "button",changeMonth:true, changeYear:true});
						if($options.showEndDate.value) {
							setTimeout(function() {
								$("#toDate").datepicker("setDate", new Date($options.showEndDate.value));
							}, 200);
						}
                	}

                    $("input.date").each(function() {
                        var element = $(this);
                        var icon = element.next();
                        var el_pos = element.position(), el_h = element.outerHeight(false), el_mt = parseInt(element.css('marginTop'), 10) || 0, el_w = element
                                .outerWidth(false), el_ml = parseInt(element.css('marginLeft'), 10) || 0,

                            i_w = icon.outerWidth(true), i_h = icon.outerHeight(true);

                        var new_icon_top = el_pos.top + el_mt + ((el_h - i_h) / 2);
                        var new_icon_left = el_pos.left + el_ml + el_w - i_w;

                        var icon_pos = icon.position();

                        if (icon_pos.top != new_icon_top) {
                            icon.css('top', new_icon_top);
                        }
                        if (icon_pos.left != new_icon_left) {
                            icon.css('left', new_icon_left);
                        }
                        icon.css('float', 'none');
                    });
                }
            });
            this.modalEdit("show");
        },
        getArguments : function() {
        	var obj = {};
			try {
                var fromDate = new Date();
                fromDate.setTime($("#fromDate").datetimepicker('getDate').getTime());
                fromDate.setHours(0,0,0,0);
                obj.fromDate = fromDate.getTime();
			} catch(e) {
				
			}
			try {
                // toDate should contain the last moment of the selected date
                var toDate = new Date();
                toDate.setTime($("#toDate").datetimepicker('getDate').getTime());
                toDate.setHours(23,59,59,999);
				obj.toDate = toDate.getTime();
			} catch(e) {
			
			}
            var requestorIdItem = $('#requestorId');
            if($(requestorIdItem).val() != '') {
                obj.requestorId = $(requestorIdItem).val();
            }
            obj.showChildren = $('#showChild').is(':checked');

            var selectedManagedSystemItem = $('#managedSystem').find(":selected");
            if($(selectedManagedSystemItem).val() != '') {
                obj.managedSystem = $(selectedManagedSystemItem).val();
            }
            var selectedResultItem = $('#result').find(":selected");
            if($(selectedResultItem).val() != '') {
                obj.result = $(selectedResultItem).text();
            }
            var selectedActionItem = $('#action').find(":selected");
            if($(selectedActionItem).val() != ''){
                obj.action = $(selectedActionItem).text();
            }
            var selectedTargetType = $('#targetType').find(":selected");
            if($(selectedTargetType).val() != '') {
                obj.targetType = $(selectedTargetType).text();
            }
			return obj;
        }
    };

    var methods = {
        draw : function() {
            var $this = this;
            var $options = $this.data("options");
            privateMethods.request.call($this);
        },
        init : function( args ) {
            var $this = this;

            var options = $.extend({
                dialogTitle : null,
                restfulURLPrefix : "",
                showStartDate : null,
                showEndDate : null,
                searchBtnText : localeManager["openiam.ui.common.search"],
                onSearch : null
            }, args);

            options.targetTypeSearch = options.restfulURLPrefix + TARGET_TYPES_URL;

            if(options.dialogTitle == null) {
                $.error("'dialogTitle' is a required parameter'");
            }

            $this.data("options", options);
            methods.draw.call(this);
        }
    };

    $.fn.logDialogSearch = function( method ) {
        if(this.length > 0) {
            if ( methods[method] ) {
                return methods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
            } else if ( typeof method === 'object' || ! method ) {
                return methods.init.apply( this, arguments );
            } else {
                $.error( 'Method ' +  method + ' does not exist on jQuery.logDialogSearch' );
            }
        }
    };
})( jQuery );

(function( $ ){

    var COLUMN_HEADERS = [
        localeManager["openiam.ui.audit.log.requestor"],
        localeManager["openiam.ui.audit.log.target"],
        localeManager["openiam.ui.audit.log.action"],
        localeManager["openiam.ui.audit.log.result"],
        localeManager["openiam.ui.common.date"],
    	localeManager["openiam.ui.common.actions"]
    ];
    var COLUMNS_MAP = ["principal", "target", "action", "result", "formattedDate"];
    var SEARCH_URL = "rest/api/metadata/findLogs";

    var privateMethods = {
    	getInputs : function(args) {
			
			var mySearch = document.createElement("input"); $(mySearch).attr("type", "button"); $(mySearch).attr("value", localeManager["openiam.ui.common.search"]); mySearch.className = "redBtn"; mySearch.id = "search";
			
			var inputs = [];
			inputs.push("");
			inputs.push("");
			inputs.push("");
			inputs.push("");
			inputs.push("");
			inputs.push(mySearch);
			return inputs;
		},
        draw : function(json) {
        	var $this = this;
            var $options = $this.data("logOptions");
        	
            var inputelements = privateMethods.getInputs();
            
            $this.entitlemetnsTable({
				columnHeaders : COLUMN_HEADERS,
				columnsMap : COLUMNS_MAP,
				hasEditButton : true,
				onEdit : function(bean, options) {
                    var params = "";
                    $.each(json, function( key, value ) {
                        params += "&"+key+"="+value;
                    });
					var logArguments = $options.getAdditionalEditArgumentsAsString.call($this);
					window.location.href = "viewLogRecord.html?id=" + bean.id +params+"&from="+options.from+"&size="+options.pageSize+"&page="+options.page+"&totalSize="+options.totalSize;
				},
	            theadInputElements : inputelements,
				ajaxURL : $options.searchURL,
				entityUrl : "viewLogRecord.html",
				entityURLIdentifierParamName : "id",
				pageSize : 10,
				emptyResultsText : localeManager["openiam.ui.audit.log.search.empty"],
				onAppendDone : function() {
					$("#search").click(function() {
						$(this).logDialogSearch({
			                dialogTitle : $options.dialogTitle,
			                restfulURLPrefix : $options.restfulURLPrefix,
			                onSearch : function(data) {
			                	privateMethods.draw.call($this, data);
			                },
			                showStartDate : {shown : true, value : json.fromDate},
			                showEndDate : {shown : true, value : json.toDate}
						});
					});
				},
                validate : function(vdata){
                    var obj = $.extend(json, $options.validate(vdata));
                    return obj;
                },
				getAdditionalDataRequestObject : function() {
					var obj = $.extend(json, $options.getStaticArguments());
					return obj;
				}
			});
        }
    };

    var methods = {
    	draw : function(json) {
    		var $this = this;
    		privateMethods.draw.call(this, json);
    	},
        init : function( args ) {
            var $this = this;
            var options = $.extend({
                emptyMessage : null,
                restfulURLPrefix : "",
                onError : OPENIAM.Modal.Error,
                errorText : localeManager["openiam.ui.internal.error"],
                dialogTitle : null,
                getStaticArguments : function() {return {};},
                getAdditionalEditArgumentsAsString : function() {return "";}
            }, args);

            if(options.emptyMessage == null) {
                $.error("'emptyMessage' is a required parameter");
            }

            options.searchURL = options.restfulURLPrefix + SEARCH_URL;

            $this.data("logOptions", options);
            methods.draw.call($this, {});
        }
    };

    $.fn.logSearchResults = function( method ) {
        if(this.length > 0) {
            if ( methods[method] ) {
                return methods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
            } else if ( typeof method === 'object' || ! method ) {
                return methods.init.apply( this, arguments );
            } else {
                $.error( 'Method ' +  method + ' does not exist on jQuery.logSearchResults' );
            }
        }
    };
})( jQuery );