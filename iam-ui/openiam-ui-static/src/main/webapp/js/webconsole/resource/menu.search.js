OPENIAM = window.OPENIAM || {};

OPENIAM.MenuPage = {
		
};

OPENIAM.MenuPage.Table = {
	init : function() {
		$("#tableOne").tablesorter({ 
			debug: false, 
			sortDisabled : true,
			widgets: ['zebra']});
	}
};

$(document).ready(function() {
	OPENIAM.MenuPage.Table.init();
});

$(window).load(function() {
	
});