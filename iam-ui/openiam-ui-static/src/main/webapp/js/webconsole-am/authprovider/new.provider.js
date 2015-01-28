OPENIAM = window.OPENIAM || {};
OPENIAM.AuthProvider = {
	init : function() {
		$("#authProviderType").change(function() {
			window.location.href = "newAuthProviderWithType.html?id=" + $(this).val();
		});
	}
};

$(document).ready(function() {
	OPENIAM.AuthProvider.init();
});

$(window).load(function() {

});