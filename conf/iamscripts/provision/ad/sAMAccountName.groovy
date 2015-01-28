
/*
if (binding.hasVariable('targetSystemIdentity') && targetSystemIdentity) {
    output = targetSystemIdentity
    return
}
*/

def loginManager = context.getBean("loginManager")

def ctr = 1

def loginID = user.firstName + "." + user.lastName

if (loginID.length() > 17) {
    loginID = loginID.substring(0,17)

    // add logic to ensure uniqueness
}

if (managedSysId != null) {
    def origLoginID = loginID
    while ( loginManager.loginExists( loginID, managedSysId )) {
        loginID = origLoginID + ctr
        ctr++
    }
}

output = loginID
