(function( $ ){
	var initialized = false;
	var debug = false;
	var minHeight = 400;
	var subTree = null;
	
	var privateMethods = {
		getIframeWindow : function() {
			var iframe = $("#contentFrame")[0];
			var iframewindow = iframe.contentWindow;
			return iframewindow;
		},
		getIframeJquery : function() {
			var iframe = $("#contentFrame")[0];
			var iframewindow = iframe.contentWindow;
			var _jq = iframewindow.jQuery;
			return _jq;
		},
		getIframeDocument : function() {
			var iframe = $("#contentFrame")[0];
			var iframedoc = (iframe.contentWindow || iframe.contentDocument).document;
			return iframedoc;
		},
		inject : function() {
			var iframedoc = privateMethods.getIframeDocument();
			var api = iframedoc.getElementById("openiam-api");
			if(api == null || api == undefined) {
				var script = iframedoc.createElement("script");
				script.type = "text/javascript";
				script.async = true;
				script.id = "openiam-api";
				script.src = "/openiam-ui-static/js/common/api/openiam.api.client.js";
				iframedoc.getElementsByTagName("head")[0].appendChild(script);
			}
		},
		hideBreadcrumbs : function() {
			$("#breadcrumb").empty();
		},
		unload : function() {
			this.hideBreadcrumbs();
		},
		bindUnload : function() {
			var iframewindow = privateMethods.getIframeWindow();
			if(iframewindow != null && iframewindow != undefined) {
				$(iframewindow).unload(function() {
					privateMethods.unload();
				});
			}
		},
		drawMenusIfPossible : function() {
			$("#submenu").hide();
			var iframewindow = privateMethods.getIframeWindow();
			var tree = null;
			var appendURL = null;
			try {
				tree = iframewindow.OPENIAM.ENV.MenuTree;
			} catch(e) {
				
			}
			
			try {
				appendURL = iframewindow.OPENIAM.ENV.MenuTreeAppendURL;
			} catch(e) {
				
			}
			if(tree != null && tree != undefined) {
				subTree = Object.create(OPENIAM.MenuTree);
				try {
					subTree.initialize({
						"tree" : tree,
						toHTML : function() {
							var ul = document.createElement("ul");
							var root = this.getRoot();
							if(root != null && root.getIsVisible()) {
								var node = this.getRoot().getChild();
								while(node != null) {
									var html = node.toHTML();
									if(html) {
										ul.appendChild(html);
									}
									node = node.getNext();
								}
							}
							return ul;
						},
						onNodeClick : function() {
							OPENIAM.Frame.changeFrame(this);
							setTimeout(function() {
								
							}, 500);
						},
						toNodeHtml : function() {
							var that = this;
							if(appendURL != null && appendURL != undefined) {
								this.setURLParams(appendURL);
							}
							var url = this.getFQURL();
							var li = document.createElement("li");
								var a = document.createElement("a"); a.href = (url != null) ? url : "javascript:void(0);";
									var span = document.createElement("span"); span.innerHTML =  this.getText();
								$(a).append(span);
								$(a).click(function() {
									if($.isFunction(that.onClick)) {
										that.onClick();
									}
									return false;
								});
							$(li).click(function() {
								$(a).click();
							});
							$(li).append(a);
							if(this.getChild() != null) {
								var ul = document.createElement("ul");
								var node = this.getChild();
								while(node != null) {
									var html = node.toHTML();
									if(html) {
										$(ul).append(html);
									}
									node = node.getNext();
								}
								$(li).append(ul);
							}
							return li;
						}
					});
					$("#submenu").show().html(subTree.toHTML());
				} catch(e) {
					
				}
			}
		},
		iframeLoaderShow : function() {
			var framewindow = privateMethods.getIframeWindow();
			framewindow.OPENIAM.AjaxLoader.showOverlay();
			OPENIAM.AjaxLoader.showLoader();
		},
		iframeLoaderHide : function() {
			var framewindow = privateMethods.getIframeWindow();
			framewindow.OPENIAM.AjaxLoader.hideOverlay();
			OPENIAM.AjaxLoader.hideLoader();
		},
		bindAjax : function() {
			var _jq = privateMethods.getIframeJquery();
			if($.isFunction(_jq)) {
				_jq.ajaxSetup({
					beforeSend : function(args) {
						privateMethods.iframeLoaderShow();
					},
					complete : function(args) {
						OPENIAM.AjaxLoader.handleRedirect(args);
						privateMethods.iframeLoaderHide();
					}
				});	
			}
		}
	};
	
	var methods = {
		init : function(args) {
			if(!initialized) {
				debug = args.debug || false;
				$("#contentFrame").load(function() {
					privateMethods.inject();
					privateMethods.drawMenusIfPossible();
					privateMethods.bindAjax();
					privateMethods.bindUnload();
				});
				minHeight = parseInt($("#contentFrame").css("min-height"));
				initialized = true;
			}
		},
		resize : function(offset) {
			if(offset != null && offset != undefined && !isNaN(offset)) {
				$("#contentFrame").css("height", ((offset > minHeight) ? offset : minHeight) + 20);
			}
		},
		goToFirstMenu : function() {
			return OPENIAM.Frame.changeFrameToFirstMenu();
		},
		updateNotifications : function() {
			OPENIAM.NotificationPanel.updateActivitiStatus();
		}
	};
	
	$.fn.OPENIAMAPI = function( method ) {
		if ( methods[method] ) {
			return methods[ method ].apply( this, Array.prototype.slice.call( arguments, 1 ));
	    } else if ( typeof method === 'object' || ! method ) {
	    	return methods.init.apply( this, arguments );
	    } else {
	    	$.error( 'Method ' +  method + ' does not exist on jQuery.tooltip' );
	    }
	};
})( jQuery );

/* public methods to be called by the client */
$.fn.OPENIAMAPI.resize = function(newSize) {$(document).OPENIAMAPI("resize", newSize);};

$.fn.OPENIAMAPI.showLoader = function(text) {
};

$.fn.OPENIAMAPI.hideLoader = function() {
};

$.fn.OPENIAMAPI.goToFirstMenu = function() {$(document).OPENIAMAPI("goToFirstMenu");};

$.fn.OPENIAMAPI.updateNotifications = function() {$(document).OPENIAMAPI("updateNotifications");};

OPENIAM = window.OPENIAM || {};
OPENIAM.API = $.fn.OPENIAMAPI;