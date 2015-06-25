import org.openiam.idm.srvc.auth.login.LoginDataService
import org.openiam.idm.util.Transliterator

/*
Objects that are passed to the script:
sysId - DefaultManagedSysId
user - New user object that has been submitted to the provisioning service
org - Organization object		
context - Spring application context. Allows you to look up any spring bean
targetSystemIdentityStatus
targetSystemIdentity
targetSystemAttributes = attributes at the target system
*/


def loginManager = context.getBean("loginManager") as LoginDataService

def loginID
if (user.userAttributes.get("userid")?.value) {
	loginID = user.userAttributes.get("userid").value
} else {
	loginID = Transliterator.transliterate(user.firstName + "." + user.lastName, true)
}

ctr = 1
def origLoginID = loginID

while ( loginManager.loginExists( loginID, sysId )) {
	loginID = origLoginID + ctr
	ctr++
}

println("LOGIN="+loginID); 
output = loginID


