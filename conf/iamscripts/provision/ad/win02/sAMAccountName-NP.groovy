def attr = user.getUserAttributes().get("sAMAccountName")
if (attr?.value) {
    output = attr.value as String
} else {
    def value = user.employeeId as String
    if (value) {
       output = value
    } else if (binding.hasVariable('targetSystemIdentity') && targetSystemIdentity) {
       output = targetSystemIdentity
    } else {
        output = lg.login
    }
}

