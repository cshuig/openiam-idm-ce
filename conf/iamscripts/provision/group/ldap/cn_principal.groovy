import org.openiam.idm.srvc.auth.login.IdentityService
import org.openiam.provision.service.ObjectProvisionService
import org.openiam.provision.type.ExtensibleAttribute

output = null

ObjectProvisionService groupProvisionService = context.getBean('groupProvision')
IdentityService identityManager = context.getBean('identityManager')

def groupIdentity = group.name
def origGroupIdentity = groupIdentity

//check on uniqueness
def exists
exists = identityManager.getIdentityByManagedSys(groupIdentity, managedSysId)
if (!exists) {
    def resp = groupProvisionService.getTargetSystemObject(groupIdentity, managedSysId, [new ExtensibleAttribute('cn','')])
    exists = resp.success
}

def count = 0
while (exists) {
    count++
    groupIdentity = "${origGroupIdentity}${count}" as String
    exists = identityManager.getIdentityByManagedSys(groupIdentity, managedSysId)
    if (!exists) {
        def resp = groupProvisionService.getTargetSystemObject(groupIdentity, managedSysId, [new ExtensibleAttribute('cn','')])
        exists = resp.success
    }
}
output = groupIdentity

