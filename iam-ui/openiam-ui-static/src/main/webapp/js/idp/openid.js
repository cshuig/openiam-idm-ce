OPENIAM = window.OPENIAM || {};

OPENIAM.OpenID = {
	init : function() {
		OPENIAM.FrameBust();
	}
};

$(document).ready(function() {
	OPENIAM.OpenID.init();
});

$(window).load(function() {

});