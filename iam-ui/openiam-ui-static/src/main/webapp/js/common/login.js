OPENIAM = window.OPENIAM || {};
OPENIAM.Login = {
	init : function() {
		$(function(){
			$('#content').corner("20px");
			$('#nav ul ul a').corner("10px");
			$('#nav ul ul :hover > a').corner("10px");
			$('#nav li:hover > a').corner("10px");
			$('#content').boxShadow( '5px', '10px', '10px' , '#ccc' );
			
		});
		
		OPENIAM.FrameBust();
	}
};

$(document).ready(function() {
	OPENIAM.Login.init();
	$("#principal").focus().click();
});

$(window).load(function() {
	
});