$(document).ready(function() {
	OPENIAM.EditProfileBootstrap.onReady({
		postURL : "editProfile.html",
		toJSON : function() {
			var obj = {};
			obj.user = {
				firstName : $("#firstName").val(),
				lastName : $("#lastName").val(),
				middleInit : $("#middleInit").val(),
				nickname : $("#nickname").val()
			};
			obj.pageTemplate = $("#uiTemplate").openiamUITemplate("getObject");
			obj.emails = OPENIAM.ENV.emailList,
			obj.phones = OPENIAM.ENV.phoneList,
			obj.addresses = OPENIAM.ENV.addressList
			return obj;
		}
	});
});

$(window).load(function() {

});