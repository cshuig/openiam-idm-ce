import org.openiam.base.AttributeOperationEnum
import org.openiam.base.BaseAttribute
import org.openiam.base.BaseAttributeContainer
import org.openiam.idm.srvc.auth.login.IdentityService
import org.openiam.idm.srvc.user.domain.UserEntity
import org.openiam.idm.srvc.user.service.UserDataService
import org.openiam.provision.service.ObjectProvisionService
import org.openiam.provision.service.ProvisionService
import org.openiam.provision.type.ExtensibleAttribute

output = null
//Provisioning Members
if (binding.hasVariable("managedSysId")) {

    ObjectProvisionService groupProvisionService = context.getBean("groupProvision")
    UserDataService userManager = context.getBean('userManager')
    IdentityService identityManager = context.getBean('identityManager')

    def groupIdentity = identityManager.getIdentityByManagedSys(group.id, managedSysId)
    def targetSystemMemberDNList = []

    //Lookup for current group members
    def lookupUserResponse = groupProvisionService.getTargetSystemObject(groupIdentity.identity, managedSysId, [new ExtensibleAttribute('Members', null)])
    if (lookupUserResponse.success && lookupUserResponse.attrList) {
        for(ExtensibleAttribute attribute : lookupUserResponse.attrList){
            if (attribute.multivalued) {
                attribute.valueList?.each {v-> targetSystemMemberDNList << v }
            } else {
                if (attribute.value) {
                    targetSystemMemberDNList << attribute.value
                }
            }
        }
    }

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

    def listToDelete = targetSystemMemberDNList - memberDNList // remove processed user members
    def listToAdd = memberDNList - targetSystemMemberDNList

    def attributeContainer = new BaseAttributeContainer()
    listToDelete?.each{dn->
        attributeContainer.attributeList.add(new BaseAttribute(dn, dn, AttributeOperationEnum.DELETE))
    }
    listToAdd?.each{dn->
        attributeContainer.attributeList.add(new BaseAttribute(dn, dn, AttributeOperationEnum.ADD))
    }
    if (attributeContainer.attributeList) {
        output = attributeContainer
    }
}

