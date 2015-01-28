console = window.console || {};
console.log = window.console.log || function() {
};

(function($) {
    var METADATA_URL = "rest/api/metadata/languages";
    window._laq = window._law || [];
    window._langInitialized = window._langInitialized || "NONE";

    var privateMethods = {
        request : function() {
            window._langInitialized = "BUSY";
            var $this = this;
            var $options = $this.data("options");
            $.ajax({
                url : $options.searchURL,
                "data" : null,
                type : "GET",
                dataType : "json",
                success : function(data, textStatus, jqXHR) {
                    OPENIAM.ENV.LanguageList = data;
                    window._langInitialized = "DONE";
                    window._laq.push(function() {
                        privateMethods.draw.call($this);
                    });
                    privateMethods.processQueue.call($this);
                },
                error : function(jqXHR, textStatus, errorThrown) {
                    OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
                }
            });
        },
        draw : function() {
            var $this = this;
            var $options = $this.data("options");
            var ul = document.createElement("ul");
            for (var idx = 0; idx < OPENIAM.ENV.LanguageList.size; idx++) {
                var lang = OPENIAM.ENV.LanguageList.beans[idx];
                var _map = $options.map;
                if (_map == null || _map == undefined) {
                    _map = $options.bean[$options.beanKey];
                }
                var val = (_map != null && _map != undefined) ? _map[lang.id] : null;
                val = (val != null && val != undefined) ? val.value : "";
                var li = $(document.createElement("li")).addClass("languageMapItem");

                var span1 = $(document.createElement("span")).addClass("languageName").text(lang.name);
                var input = $(document.createElement("input")).attr("type", "text").attr("languageId", lang.id).attr("languageCode", lang.code).addClass(
                        "languageValue").addClass("full").addClass("rounded").val(val);

                var div = $(document.createElement("div")).css("clear", "both");

                $(li).append(span1, input, div);
                $(span1).css("display", "inline-block");
                $(span1).css("margin-right", "10px");
                $(ul).css("list-style-type", "none");
                $(ul).css("padding-left", "0px");
                $(ul).append(li);
            }

            $this.append(ul);

            privateMethods.initTranslateAPI($this);
        },
        processQueue : function() {
            $.each(window._laq, function(idx, _f) {
                _f();
            });
        },
        initTranslateAPI : function(parentContainer) {
            // var $this = this;
            parentContainer.find("input[languageCode]").each(function() {
                var input = $(this);

                var div = document.createElement("div");
                div.className = "translateButton";
                div.title = "Auto Translate";

                // var a = document.createElement("a");
                // a.href = "javascript:void(0);";
                // a.title = "Auto Translate";
                // a.innerHTML = "Auto Translate";
                $(div).hide();// .append(a);
                input.after(div);

                input.on("focus", function() {
                    if (privateMethods.checkEnteredValues(parentContainer)) {
                        $(this).parent().find(".translateButton").show();
                    }
                }).on("blur", function() {
                    privateMethods.hideTranslateButton($(this));
                });

                $(div).on('mousedown', function() {
                    // var input =
                    // $(this).parents("span:first").find("input[languageCode]");
                    input.off('blur');
                }).click(function() {
                    // var input =
                    // $(this).parents("span:first").find("input[languageCode]");
                    privateMethods.translateData(input, parentContainer, function(text) {
                        input.val(text);
                    });
                    $(this).hide();

                    input.on("blur", function() {
                        privateMethods.hideTranslateButton($(this));
                    });
                });

            });
        },
        hideTranslateButton : function(input) {
            $(input).parent().find(".translateButton").hide();
        },
        checkEnteredValues : function(parentContainer) {
            var isEntered = false;
            parentContainer.find("input[languageCode]").each(function() {
                var val = $(this).val();
                if (val) {
                    isEntered = true;
                    return false;
                }
            });
            return isEntered;
        },
        translateData : function(input, parentContainer, callback) {
            // get first filled input
            var filledInput = null;
            parentContainer.find("input[languageCode]").each(function() {
                var val = $(this).val();
                if (val) {
                    filledInput = $(this);
                    return false;
                }
            });

            if (filledInput != null) {
                // check source and target languages
                var sourceLang = filledInput.attr("languageCode");
                var targetLang = input.attr("languageCode");
                if (targetLang != sourceLang) {
                    // if source and target languages not equals then try to
                    // translate
                    var val = filledInput.val();
                    if (val) {
                        var obj = {};
                        obj.sourceLang = sourceLang;
                        obj.targetLang = targetLang;
                        obj.text = val;

                        $.ajax({
                            url : "rest/api/language/translate",
                            data : JSON.stringify(obj),
                            type : "POST",
                            dataType : "json",
                            contentType : "application/json",
                            async : false,
                            success : function(data, textStatus, jqXHR) {
                                if (callback) {
                                    callback((data && data.text) ? data.text : "");
                                }
                            },
                            error : function(jqXHR, textStatus, errorThrown) {
                                OPENIAM.Modal.Error(localeManager["openiam.ui.internal.error"]);
                            }
                        });
                    }
                }
            }
        }
    };

    var methods = {
        getMap : function() {
            var $this = this;
            var $options = $this.data("options");
            var map = {};
            $this.find("input").each(function() {
                map[$(this).attr("languageId")] = {
                    value : $(this).val()
                };
            });
            return map;
        },
        draw : function() {
            var $this = this;
            var $options = $this.data("options");
            if (window._langInitialized == "NONE") {
                privateMethods.request.call($this);
            } else if (window._langInitialized == "BUSY") {
                window._laq.push(function() {
                    privateMethods.draw.call($this);
                });
            } else if (window._langInitialized == "DONE") {
                privateMethods.draw.call($this);
            }
        },
        init : function(args) {
            var $this = this;
            var options = $.extend({
                restfulURLPrefix : "",
                bean : null,
                beanKey : null,
                map : null
            }, args);

            if (options.map == null && options.bean == null && options.beanKey == null) {
                $.error("Either the 'map' or 'bean' AND 'beanKey' parameters are required");
            }

            if (options.map == null) {
                if (options.bean == null && options.beanKey == null) {
                    $.error("If 'map' is null, then the 'bean' AND 'beanKey' parameters are required");
                }
            }

            options.searchURL = options.restfulURLPrefix + METADATA_URL;
            $this.data("options", options);
            methods.draw.call(this);
        }
    };

    $.fn.languageAdmin = function(method) {
        if (this.length > 0) {
            if (methods[method]) {
                return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
            } else if (typeof method === 'object' || !method) {
                return methods.init.apply(this, arguments);
            } else {
                $.error('Method ' + method + ' does not exist on jQuery.languageAdmin');
            }
        }
    };
})(jQuery);