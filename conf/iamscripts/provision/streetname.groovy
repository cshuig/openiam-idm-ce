if (user.address1 != null) {
	output=user.bldgNum + " " + user.streetDirection + " " + (user.addresses != null && user.addresses.size() > 0) ? user.addresses.get(0).address1 : null;
}else {
	output="123 Main"
}