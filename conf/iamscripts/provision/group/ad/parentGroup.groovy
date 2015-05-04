import org.openiam.base.AttributeOperationEnum
import org.openiam.base.BaseAttribute
import org.openiam.base.BaseAttributeContainer
import org.openiam.idm.srvc.auth.login.IdentityService
import org.openiam.idm.srvc.grp.dto.Group
import org.openiam.provision.service.ObjectProvisionService
import org.openiam.provision.type.ExtensibleAttribute


println("================ parentGroup.groovy starting..")

output = null

ObjectProvisionService groupProvisionService = context.getBean('groupProvision')
IdentityService identityManager = context.getBean('identityManager')

def targetSysParentDNList = []
def gid = identity.identity
def resp = groupProvisionService.getTargetSystemObject(gid, managedSysId, [new ExtensibleAttribute('Group','')])
if (resp.success && resp.attrList) {
    def identityDN = resp.attrList.get(0)?.value
    if (identityDN) {
        targetSysParentDNList << identityDN
    }
}

def parentDNList = []
def group = group as Group
group.parentGroups?.each { g->
    def groupIdentity = identityManager.getIdentityByManagedSys(g.id, managedSysId)?.identity
    if (groupIdentity) {
        resp = groupProvisionService.getTargetSystemObject(groupIdentity, managedSysId, [new ExtensibleAttribute('DistinguishedName','')])
        if (resp.success && resp.attrList) {
            def identityDN = resp.attrList.get(0)?.value
            if (identityDN) {
                parentDNList << identityDN
            }
        }
    }
}

def listToDelete = targetSysParentDNList - parentDNList // remove processed user members
def listToAdd = parentDNList - targetSysParentDNList

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