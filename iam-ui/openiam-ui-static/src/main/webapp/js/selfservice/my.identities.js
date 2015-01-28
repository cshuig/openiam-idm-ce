$(document).ready(function() {
	$("#tableOne").tablesorter({ 
		debug: false, 
		sortDisabled : true,
		/*sortList: [[0, 0]], */
		widgets: ['zebra'] });
			
		$("#tableOne tbody tr").click(function() {
			window.location.href = "identity.html?loginId=" + $(this).attr("entityId");
		});
});

$(window).load(function() {

});