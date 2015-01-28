(function( $ ){
	
	var privateMethods = {
		drawRules : function(rules) {
			var $this = this;
			var $options = this.data("options");
			var $rulesSelector = $options.rulesSelector;
			var $content = $(document.createElement("div"))
			$.each(rules, function(idx, rule) {
				$content.append(
					$(document.createElement("div")).addClass("error").text(rule.message).attr("id", rule.error).attr("data-role", "password-rule")
				)
			});
			if($options.useTooltip) {
				$rulesSelector.tooltipster({content : $content, position : $options.tooltipPosition, offsetX : 27, offsetY : 10})
				$rulesSelector.tooltipster('show');
			} else {
				$rulesSelector.empty().html($content).show();
			}
		},
		hideRules : function() {
			var $this = this;
			var $options = this.data("options");
			var $rulesSelector = $options.rulesSelector;
			if($options.useTooltip) {
				$rulesSelector.tooltipster('hide');
			} else {
				$rulesSelector.hide();
			}
		},
		getRules : function() {
			var $this = this;
			var $options = this.data("options");
			var $rulesSelector = $options.rulesSelector;
			$.ajax({
                "url" : "rest/api/password/validation/rules",
                "data" : JSON.stringify($options.getRulesData.call($this)),
                contentType: "application/json",
                type: "POST",
                dataType : "text",
                success : function(data, textStatus, jqXHR) {
                	if(data) {
                    	data = JSON.parse(data);
                    	if($.isArray(data.rules)) {
                    		/* show rules */
                    		privateMethods.drawRules.call($this, data.rules);
                    	} else {
                    		/* hide rules - bad login */
                    		privateMethods.hideRules.call($this);
                    	}
                		$options.validate = true;
                	} else {
                		/* hide rules */
                		$options.validate = false;
                		privateMethods.hideRules.call($this);
                	}
                },
                error : function(jqXHR, textStatus, errorThrown) {
                	$options.validate = false;
                	/* hide rules */
                }
            });
		},
		cancelEvent : function() {
			var $this = this;
			var $options = this.data("options");
			if($options.xhr != null) {
				$options.xhr.abort();
			}
		},
		request : function() {
			var $this = this;
			var $options = this.data("options");
			$options.xhr = $.ajax({
                "url" : "rest/api/password/validation/validate",
                "data" : JSON.stringify($options.getValidateionData.call($this)),
                contentType: "application/json",
                type: "POST",
                dataType : "text",
                success : function(data, textStatus, jqXHR) {
                    if(data) {
                    	data = JSON.parse(data);
                    	if($.isArray(data.errorList) && data.errorList.length > 0) {
                    		privateMethods.suceedAll.call($this);
                    		
                    		$.each(data.errorList, function(idx, theError) {
                    			$("#" + theError.error).removeClass("success").addClass("error");
                    		});
                    	} else {
                    		privateMethods.suceedAll.call($this);
                    	}
                    	/* figure out which need to be met */
                    } else {
                    	privateMethods.failAll.call($this);
                    	/* set all to not met */
                    }
                },
                error : function(jqXHR, textStatus, errorThrown) {
                },
                beforeSend : function() {},
                complete : function() {}
            });
		},
		suceedAll : function() {
			$("[data-role='password-rule']").each(function() {
				$(this).removeClass("error").addClass("success");
			});
		},
		failAll : function() {
			$("[data-role='password-rule']").each(function() {
				$(this).removeClass("success").addClass("error");
			});
		},
		bind : function() {
			var $this = this;
			var $options = this.data("options");
			
			$this.keyup(function(event) {
				if($options.validate) {
					if (event.keyCode != 13) {
						privateMethods.cancelEvent.call($this);
						if($(this).val() == "") {
							privateMethods.failAll.call($this);
						} else {
							privateMethods.request.call($this);
						}
					}
				}
			});
			
			$options.confirmPasswordSelector.keyup(function(event) {
				if($options.validate) {
					if (event.keyCode != 13) {
						privateMethods.cancelEvent.call($this);
						if($(this).val() != "") {
							privateMethods.request.call($this);
						}
					}
				}
			});
			
			if(($options.tokenSelector.length > 0)) {
				privateMethods.getRules.call($this);
			} else if(($options.loginSelector.is(":disabled") || $options.loginSelector.is("[readonly]")) && $options.loginSelector.val() != "") {
				privateMethods.getRules.call($this);
			} else  {
				$options.loginSelector.change(function() {
					privateMethods.getRules.call($this);
				});
			}
		}
	};
	
 	var methods = {
    	init : function( args ) {
    		var $this = this;
    		var $options = $.extend({
    			useTooltip : true,
    			tooltipPosition : "right",
    			tokenSelector : "#token",
    			loginSelector : "#principal",
    			rulesSelector : "#passwordRules",
    			confirmPasswordSelector : "#newPasswordConfirm",
    			forgotPassword : true,
    			getValidateionData : function() {
    				return {
    					token : $options.tokenSelector.val(),
    					login : $options.loginSelector.val(),
    					password : $this.val(),
    					confirmPassword : $options.confirmPasswordSelector.val(),
    					forgotPassword : $options.forgotPassword
    				}
    			},
    			getRulesData : function() {
    				return  {
    					token : $options.tokenSelector.val(),
    					login : $options.loginSelector.val(),
    					forgotPassword : $options.forgotPassword
    				}
    			}
    		}, args);
    		
    		if(!this.is(":input")) {
    			$.error("passwordRules is designed to work only on text inputs");
    		}
    		
    		$options.xhr = null;
    		$options.validate = false;
    		
    		$options.rulesSelector = $($options.rulesSelector);
    		if($options.rulesSelector.length == 0) {
    			$.error("passwordRules.rulesSelector not found, or not a valid element");
    		}
    		
    		$options.tokenSelector = $($options.tokenSelector);
    		$options.loginSelector = $($options.loginSelector);
    		if($options.loginSelector.length == 0 && $options.tokenSelector.length == 00) {
    			$.error("passwordRules.loginSelector and passwordRules.tokenSelector not found, or not a valid element");
    		}
    		
    		$options.confirmPasswordSelector = $($options.confirmPasswordSelector);
    		if($options.confirmPasswordSelector.length == 0) {
    			$.error("passwordRules.confirmPasswordSelector not found, or not a valid element");
    		}
    		this.data("options", $options);
    		privateMethods.bind.call(this);
    	}
 	}
 
 	$.fn.passwordRules = function( method ) {
    	if ( methods[method] ) {
      		return methods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
    	} else if ( typeof method === 'object' || ! method ) {
      		return methods.init.apply( this, arguments );
    	} else {
      		$.error( 'Method ' +  method + ' does not exist on jQuery.passwordRules' );
    	}    
  	};
})( jQuery );