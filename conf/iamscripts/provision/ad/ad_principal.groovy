import org.openiam.idm.srvc.auth.login.LoginDataService
import org.openiam.idm.util.Transliterator
import org.openiam.provision.resp.LookupUserResponse
import org.openiam.provision.service.ProvisionService
import org.openiam.provision.type.ExtensibleAttribute


output = null

if (managedSysId) {

    loginId = Transliterator.transliterate(user.firstName + "." + user.lastName, true)

    //check on length
    if (loginId.length() > 17) {
        loginId = loginId.substring(0,17)
    }
    def orgLoginId = loginId

    //check on uniqueness
    def exists
    LoginDataService loginManager = context.getBean("loginManager")
    exists = loginManager.loginExists(loginId, managedSysId)
    if (!exists) {
        ProvisionService provisionService = context.getBean('defaultProvision')
        LookupUserResponse resp = provisionService.getTargetSystemUser(loginId, managedSysId, [new ExtensibleAttribute("uid","")])
        exists = resp.success
    }

    def count = 0
    while (exists) {
        count++
        loginId = "${orgLoginId}${count}" as String
        exists = loginManager.loginExists(loginId, managedSysId)
        if (!exists) {
            ProvisionService provisionService = context.getBean('defaultProvision')
            LookupUserResponse resp = provisionService.getTargetSystemUser(loginId, managedSysId, [new ExtensibleAttribute("uid","")])
            exists = resp.success
        }
    }
    output = loginId
}