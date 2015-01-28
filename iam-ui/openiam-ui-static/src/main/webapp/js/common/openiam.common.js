console = window.console || {};
console.log = window.console.log || function() {
};

Object.create = function(clazz) {
    var F = function() {
    };
    F.prototype = clazz.prototype;
    return new F();
};

Object.size = function(obj) {
    var size = 0, key;
    for (key in obj) {
        if (obj.hasOwnProperty(key)) size++;
    }
    return size;
};


String.prototype.format = function() {
    var s = this,
    i = arguments.length;

    while (i--) {
        s = s.replace(new RegExp('\\{' + i + '\\}', 'gm'), arguments[i]);
    }
    return s;
};

$.ajaxSetup({
    beforeSend : function(args) {
        OPENIAM.AjaxLoader.show(args);
    },
    complete : function(args) {
        OPENIAM.AjaxLoader.handleRedirect(args);
        OPENIAM.AjaxLoader.hide(args);
    }
});

OPENIAM = window.OPENIAM || {};

OPENIAM.lazyLoadCSS = function(file) {
    if (file != null && $("link[href='" + file + "']").length == 0) {
        var head = document.getElementsByTagName('head')[0];
        var link = document.createElement('link');
        link.rel = 'stylesheet';
        link.type = 'text/css';
        link.href = file;
        link.media = 'screen';
        head.appendChild(link);
    }
};

OPENIAM.loadScripts = function(scripts, onAllLoaded) {
	var numLoaded = 0;
	if($.isArray(scripts)) {
		$.each(scripts, function(idx, script) {
			OPENIAM.lazyLoadScript(script, function() {
				if(numLoaded++ == scripts.length - 1) {
					if($.isFunction(onAllLoaded)) {
						onAllLoaded();
					}
				}
			});
		});
	} else {
		OPENIAM.lazyLoadScript(script, onAllLoaded);
	}
};

OPENIAM.lazyLoadScript = function(scriptUrl,postLoadExec,appendTo) {
	if(scriptUrl!=null) {
		if($("script[src='" + scriptUrl + "']").length == 0) {
			if(!$.browser.msie) {
				var sc=document.createElement("script");
				sc.type="text/javascript";
				if(appendTo) {
					$(appendTo).append(sc);
				} else {
					document.getElementsByTagName("head")[0].appendChild(sc);
				}
				if(postLoadExec) {
					$(sc).one("load",postLoadExec);
				}
				sc.src=scriptUrl;
				sc=null;
				delete sc;
			} else {
				$.getScript(scriptUrl,postLoadExec);
			}
		} else {
			if(postLoadExec) {
				postLoadExec();
			}
		}
	}
};

OPENIAM.AjaxLoader = {
    _overlay : null,
    _loader : null,
    initialized : false,
    _init : function() {
        if (!this.initialized) {
            var overlay = document.createElement("div");
            overlay.className = "openiam-loading";
            $(overlay).hide();
            var loader = document.createElement("div");
            loader.className = "openiam-loader";
            $(loader).hide();

            this._overlay = $(overlay);
            this._loader = $(loader);
            $(document.body).append(this._overlay, this._loader);
            this.initialized = true;
        }
    },
    show : function(args) {
        this._init();
        this.showOverlay();
        this.showLoader();
    },
    hide : function(args) {
        this._init();
        this.hideOverlay();
        this.hideLoader();
    },
    showOverlay : function() {
        this._init();
        this._overlay.show();
    },
    hideOverlay : function() {
        this._init();
        this._overlay.hide();
    },
    showLoader : function() {
        this._init();
        this._loader.show();
    },
    hideLoader : function() {
        this._init();
        this._loader.hide();
    },
    handleRedirect : function(args) {
        var isForce = args.getResponseHeader("x-openiam-force-auth");
        if (isForce === "true") {
            var loginURL = args.getResponseHeader("x-openiam-login-uri");
            var outerFrameContextPath = OPENIAM.ENV.OuterFrameContextPath;
            if (outerFrameContextPath != null && outerFrameContextPath != undefined) {
                var postbackURL = outerFrameContextPath + "?frameURL=" + encodeURIComponent(this.getURLOnAuthRedirect());
                loginURL += "?postbackURL=" + encodeURIComponent(postbackURL);
            } else {
                loginURL += "?postbackURL=" + encodeURIComponent(this.getURLOnAuthRedirect());
            }
            window.location = loginURL;
        }
    },
    getURLOnAuthRedirect : function() {
        var loc = window.location;
        var currentURL = loc.pathname;
        if (loc.search != null && loc.search != undefined) {
            currentURL += loc.search;
        }
        return currentURL;
    }
};

OPENIAM.Language = {
    addArticle : function(word) {
        return this._sendRequest("addArticle", word);
    },
    switchLanguage : function(param, locale) {
        var currentUrl = window.location.href;
        var separator = currentUrl.indexOf('?') !== -1 ? "&" : "?";
        window.location = currentUrl + separator + param + "=" + locale;
    },
    _sendRequest : function(command, param) {
        var result = '';
        $.ajax({
            url : "language/languageSupport.html",
            data : {
                methodName: command,
                word : param
            },
            type : "GET",
            dataType : "json",
            async : false,
            success : function(data, textStatus, jqXHR) {
                result = data.resultValue;
            },
            error : function(jqXHR, textStatus, errorThrown) {
                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
            }
        });

        return result;
    },
    getFlagURL : function(locale) {
        if (locale) {
            var imgName = locale.split("_", 2)[1];
            if (imgName) {
                var url = "/openiam-ui-static/images/common/flags/" + imgName.toLowerCase() + ".gif";
                var http = new XMLHttpRequest();
                http.open('HEAD', url, false);
                http.send();
                if (http.status != 404)
                    return url;
            }
        }
        return null;
    }
};

OPENIAM.Modal = window.OPENIAM.Modal || {};
OPENIAM.Modal.init = function(initialize) {
    if ($("#dialog").length == 0) {
        var dialog = document.createElement("div");
        dialog.id = "dialog";
        $("body").append(dialog);
    }

    if (typeof (initialize) === "boolean" && initialize == true) {
        $("#dialog").dialog({
            autoOpen : false,
            draggable : false,
            resizable : false,
            title : "",
            width : "auto"
        });
    }
};

OPENIAM.FrameBust = function() {
    if (self != top) {
        var outerFrameContextPath = parent.OPENIAM.ENV.OuterFrameContextPath;
        if (outerFrameContextPath != null && outerFrameContextPath != undefined) {
            var loginURL = window.location.pathname;
            var postbackURL = outerFrameContextPath + "?frameURL=" + encodeURIComponent(OPENIAM.ENV.PostbackURL);
            loginURL += "?postbackURL=" + encodeURIComponent(postbackURL);
            top.window.location = loginURL;
        } else {
            top.location = self.location;
        }
    } else {
        document.documentElement.style.visibility = 'visible';
    }
};

OPENIAM.Modal.Close = function() {
    OPENIAM.Modal.init();
    $("#dialog").dialog("close");
};

OPENIAM.Modal.Error = function(args) {
    OPENIAM.Modal.init();
    var html = document.createElement("div");
    if (typeof (args) === "string") {
        var err = document.createElement("div");
        err.className = "error";
        err.innerHTML = args;
        $(html).append(err);
    } else if (typeof (args.message) === "string") {
        var err = document.createElement("div");
        err.className = "error";
        err.innerHTML = args.message;
        $(html).append(err);
    } else if (args.messages != null && args.messages != undefined && args.messages.length && args.messages.length > 0) {
        $.each(args.messages, function(idx, val) {
            var err = document.createElement("div");
            err.className = "error";
            err.innerHTML = val;
            $(html).append(err);
        });
    } else if (args.errorList != null && args.errorList != undefined && args.errorList.length && args.errorList.length > 0) {
        var messages = [];
        $.each(args.errorList, function(idx, val) {
            var err = document.createElement("div");
            err.className = "error";
            err.innerHTML = val.message;
            $(html).append(err);
        });
    } else if(args.html != null && args.html != undefined) {
    	$(html).append(args.html);
    }
    $("#dialog").html(html).dialog({
        autoOpen : true,
        draggable : false,
        resizable : false,
        title : args.title || localeManager["openiam.ui.common.error"],
        width : "auto",
        maxWidth : args.width || 600
    });
};

OPENIAM.Modal.Warn = function(args) {
    OPENIAM.Modal.init();
    var html = document.createElement("div");
    if (typeof (args.message) === "string") {
        var msg = document.createElement("div");
        msg.className = "warning";
        msg.innerHTML = args.message;
        $(html).append(msg);
    }

    if (args.inputs && args.inputs.length && args.inputs.length > 0) {
        var modalInputs = document.createElement("div");
        modalInputs.className = "modal-inputs";
        var inputsUL = document.createElement("ul");
        $.each(args.inputs, function(idx, input) {
            var div = document.createElement("div");
            var lbl = document.createElement("label");
            if (input.required) {
                $(lbl).addClass("required");
            }
            $(lbl).text(input.label)

            var inputElmt = document.createElement(input.elmtType);
            inputElmt.className = input.className;
            inputElmt.id = input.id;
            $(div).append(lbl, inputElmt);
            $(inputsUL).append(div);
        });
        $(modalInputs).append(inputsUL);
        $(html).append(modalInputs);
    }

    if (args.buttons) {
        var modalButtons = document.createElement("div");
        modalButtons.className = "modal-buttons";
        var buttonUL = document.createElement("ul");
        if (args.OK && args.OK.text && args.OK.onClick) {
            var li = document.createElement("li");
            var a = document.createElement("a");
            a.href = "javascript:void(0);";
            a.className = args.OK.className || "redBtn";
            a.innerHTML = args.OK.text;
            $(a).click(function() {
                args.OK.onClick();
            });
            $(li).append(a);
            $(buttonUL).append(li);
        }
        if (args.No && args.No.text && args.No.onClick) {
            var li = document.createElement("li");
            var a = document.createElement("a");
            a.href = "javascript:void(0);";
            a.className = args.No.className || "whiteBtn";
            a.innerHTML = args.No.text;
            $(a).click(function() {
                args.No.onClick();
            });
            $(li).append(a);
            $(buttonUL).append(li);
        }
        if (args.Cancel && args.Cancel.text && args.Cancel.onClick) {
            var li = document.createElement("li");
            var a = document.createElement("a");
            a.href = "javascript:void(0);";
            a.className = args.Cancel.className || "whiteBtn";
            a.innerHTML = args.Cancel.text;
            $(a).click(function() {
                args.Cancel.onClick();
            });
            $(li).append(a);
            $(buttonUL).append(li);
        }

        $(modalButtons).append(buttonUL);
        $(html).append(modalButtons);

        $(modalButtons).append(buttonUL);
        $(html).append(modalButtons);
    }
    $("#dialog").html(html).dialog({
        autoOpen : true,
        draggable : false,
        resizable : false,
        title : args.title || localeManager["openiam.ui.common.warning"],
        width : "auto",
        maxWidth : args.width || 600
    });
};

OPENIAM.PasswordPolicy = window.OPENIAM.PasswordPolicy || {};
OPENIAM.PasswordPolicy.getFromAjaxResponse = function(response) {
	var div = $(document.createElement("div"));
	if($.isArray(response.errorList)) {
		for(var i = 0; i < response.errorList.length; i++) {
			var error = response.errorList[i];
			div.append($(document.createElement("div")).addClass("error").text(error.message));
		}
	}
	
	if($.isArray(response.possibleErrors)) {
		div.append($(document.createElement("label")).text(localeManager["openiam.ui.password.rule.title"]));
		var ul = $(document.createElement("ul"));
		for(var i = 0; i < response.possibleErrors.length; i++) {
			var error = response.possibleErrors[i];
			ul.append($(document.createElement("li")).text(error.message));
		}
		div.append(ul);
	}
	return div;
};

OPENIAM.Modal.Success = function(args) {
    OPENIAM.Modal.init();
    var html = document.createElement("div");
    if (typeof (args) === "string") {
        var err = document.createElement("div");
        err.className = "success";
        err.innerHTML = args;
        $(html).append(err);
    } else if (typeof (args.message) === "string") {
        var err = document.createElement("div");
        err.className = "success";
        err.innerHTML = args.message;
        $(html).append(err);
    }
    $("#dialog").html(html).dialog({
        close: function(event, ui) {
            if ($.isFunction(args.afterClose)) {
                args.afterClose()
            }
        },
        autoOpen : true,
        draggable : false,
        resizable : false,
        title : args.title || localeManager["openiam.ui.common.success"],
        width : "auto",
        maxWidth : args.width || 600
    });
    if (!isNaN(args.showInterval) && args.showInterval > 0) {
        setTimeout(function() {
            if($.isFunction(args.onIntervalRun)) {
                args.onIntervalRun();
            } else {
                $("#dialog").dialog("close");
                if ($.isFunction(args.onIntervalClose)) {
                    args.onIntervalClose();
                }
            }

        }, args.showInterval);
    }
};

OPENIAM.FN = window.OPENIAM.FN || {};
OPENIAM.FN.applyPlaceholder = function(elmt) {
    var placeholderSupported = ("placeholder" in document.createElement('input'));
    if (!placeholderSupported) {
        try {
            $(this).placeholder({
                clearOnFocus : true
            });
        } catch (e) {
            console.log("jquery.placeholder not included on the page - can't simulate");
        }
    }
};

OPENIAM.FN.turnOffAutocomplete = function(elmt) {
    if (elmt) {
        $(elmt).find("input[autocomplete!=on]").attr("autocomplete", "off");
        $(elmt).find("select[autocomplete!=on]").attr("autocomplete", "off");
    } else {
        $("input[autocomplete!=on]").attr("autocomplete", "off");
        $("select[autocomplete!=on]").attr("autocomplete", "off");
    }
}

$(document).ready(function() {
    if (!("placeholder" in document.createElement('input'))) {
        $("input[placeholder]").each(function() {
            try {
                $(this).placeholder({
                    clearOnFocus : true
                });
            } catch (e) {
                console.log("jquery.placeholder not included on the page - can't simulate");
            }
        });
    }
    
	if($.browser.msie) {
		$(document.body).addClass("ie").addClass("ie" + parseInt($.browser.version));
	}
    
    OPENIAM.FN.turnOffAutocomplete();
    
    try {
		$("._input_tiptip").tipTip({
            maxWidth : "300px",
            edgeOffset : 10,
            defaultPosition : "right"
        });
    } catch(e) {}
    
    try {
    	$("._tooltip").tooltipster();
    } catch(e) {}
});

(function($) {

    var privateMethods = {
        supportsRequiredNotification : function() {
            var $this = this;
            var $options = this.data("options");
            return ($this.is("input") || $this.is("select") || $this.is("div#values") || $this.is("datetimepicker"));
        },
        draw : function() {
            var $this = this;
            var $options = this.data("options");

            if ($options.type === "required") {
                if (privateMethods.supportsRequiredNotification.call($this)) {
                    var o = $this;
                    if ($this.is("div#values")) {
                        o = $this.find(":input");
                    }
                    o.addClass("ui-required").attr("title", localeManager["openiam.ui.common.value.required"]);
                    try {
                        o.tipTip({
                            maxWidth : "300px",
                            edgeOffset : 10,
                            defaultPosition : "right"
                        });
                    } catch (ex) {

                    }
                }
            }
        }
    };

    var methods = {
        init : function(args) {
            var $this = this;
            var $options = $.extend({
                type : null
            }, args);

            if ($options.type == null) {
                $.error("'type' is required on this plugin");
            }

            this.data("options", $options);
            privateMethods.draw.call($this);
        }
    };

    $.fn.fieldNotification = function(method) {
        if (methods[method]) {
            return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof method === 'object' || !method) {
            return methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.fieldNotification');
        }
    };
})(jQuery);
