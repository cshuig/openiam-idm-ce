
if (user?.addresses) {
	def addresses = []
    for (def address : user.addresses) {
        def addressString = new StringBuilder()
        for (int i=1; i<=7; i++) {
            if (address."address${i}") {
                addressString.append(address."address${i}")
                addressString.append(", ");
            }
        }
        if (addressString) {
            addresses.add(addressString as String)
        }
    }
    output = addresses

} else {
    output = null
}
