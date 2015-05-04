import org.openiam.idm.srvc.user.domain.UserEntity
import org.openiam.idm.srvc.user.service.UserDataService
import org.openiam.provision.service.ProvisionService
import org.openiam.provision.type.ExtensibleAttribute


output = null
//Provisioning Members
if (binding.hasVariable("managedSysId")) {

    UserDataService userManager = context.getBean('userManager')
    def members = userManager.getUsersForGroup(group.id, requesterId, 0, Integer.MAX_VALUE)
    def memberDNList = []

    ProvisionService provisionService = context.getBean('defaultProvision')
    for(UserEntity member : members) {
        def principal = member.principalList?.find{l-> l.managedSysId == managedSysId }?.login
        if (principal) {
            def resp = provisionService.getTargetSystemUser(principal, managedSysId, [new ExtensibleAttribute('distinguishedName','')])
            if (resp.success && resp.attrList) {
                def identityDN = resp.attrList.get(0)?.value
                if (identityDN) {
                    memberDNList << identityDN
                }
            }
        }
    }
    if (memberDNList) {
        output = memberDNList
    }
}
