(function() {
	var loadInterval = null;
    var resizeTimeout = null;
    var lastHeight = -1;
    var ruler = null;
	
	if(self != top && self != parent) {
		OPENIAM = window.OPENIAM || {};
		console = window.console || {};
		console.log = window.console.log || function() {};
		var windowHost = window.location.host;
		/* some URLs are blank, so this protects against this */
		if(windowHost != null && windowHost != undefined && windowHost != "") {
			try {
				OPENIAM.API = parent.OPENIAM.API;
				loadInterval = setInterval(function() {
					var rs = document.readyState;
					if(rs == "complete" || rs == "interactive") {
						try {
							var _ruler = document.createElement("div"); _ruler.style.height = "1px";
							document.body.appendChild(_ruler);
							jQuery(document.body).addClass("openiam-client-frame");
							ruler = _ruler;
							clearInterval(loadInterval);
							startResizeInterval();
						} catch(e) {
							
						}
					}
				}, 100);
			} catch(e) {
				console.log("Can't talk to the API", e);
			}
		}
	}
	var getMaxDialogHeight = function() {
		var height = null;
		if(typeof(jQuery) !== "undefined") {
			jQuery(".ui-dialog").each(function() {
				var h = jQuery(this).height();
				if(height == null) {
					height = h;
				} else {
					if(h > height) {
						height = h;
					}
				}
			});
		}
		var altHeight = ruler.offsetTop;
		if(height == null || height < altHeight) {
			height = altHeight;
		}
		return height;
    };
    var resizeFrame = function() {
        var height = getMaxDialogHeight();
        if (lastHeight != height) {
            clearTimeout(resizeTimeout);
            lastHeight = height;
            OPENIAM.API.resize(height);
            /*
            resizeTimeout = setTimeout(
                function() {
                    lastHeight = getMaxDialogHeight();
                }, 100);
			*/
        }
    };
	var startResizeInterval = function() {
        setInterval(resizeFrame, 300);
	};
})();