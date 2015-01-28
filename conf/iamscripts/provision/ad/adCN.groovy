
def loginManager = context.getBean("loginManager")

def ctr = 1

def loginId = user.firstName + "." + user.lastName

def origLoginId = loginId

if (managedSysId != null) {
    loginId = matchParam.keyField + "=" + origLoginId + "," + matchParam.baseDn
    while ( loginManager.loginExists( loginId, managedSysId )) {
        loginId = matchParam.keyField + "=" + origLoginId + ctr + "," + matchParam.baseDn
        ctr++
    }
}

output = loginId


