import org.openiam.idm.srvc.auth.login.LoginDataService

def loginManager = context.getBean("loginManager") as LoginDataService

output = null

if(binding.hasVariable('targetSystemIdentityStatus') && "EXIST" == targetSystemIdentityStatus) {
    return
}
def attr = user.getUserAttributes().get("sAMAccountName")
if (attr?.value) {
    output = attr.value as String
} else {
    def value = user.employeeId as String
    if (value) {
       output = value
    } else {
       output = lg.login
    }
}
// Increment if exists
ctr = 1;
loginID = output
origLoginID = output

if (binding.hasVariable("managedSysId")) {
   while ( loginManager.loginExists( loginID, managedSysId )) {
     strCtrSize = String.valueOf(ctr)
        loginID=origLoginID + ctr
        ctr++
   }
}
output = loginID
