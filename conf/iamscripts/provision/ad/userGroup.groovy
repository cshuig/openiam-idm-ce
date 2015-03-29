import org.openiam.base.AttributeOperationEnum
import org.openiam.base.BaseAttribute
import org.openiam.base.BaseAttributeContainer
import org.openiam.dozer.converter.GroupDozerConverter
import org.openiam.idm.srvc.grp.dto.Group
import org.openiam.idm.srvc.grp.dto.GroupAttribute
import org.openiam.idm.srvc.grp.ws.GroupDataWebService
import org.openiam.idm.srvc.role.dto.Role
import org.openiam.idm.srvc.role.service.RoleDataService

def groupBaseDN = "," + matchParam.baseDn

def groupWS = context?.getBean("groupWS") as GroupDataWebService

def oldGroupSet = (binding.hasVariable("userBeforeModify")) ? userBeforeModify.groups : []
def groupSet = user.groups as Set

if (binding.hasVariable("userBeforeModify")) {
    oldGroupSet.addAll(getGroupsFromRoles(userBeforeModify.roles))
}
groupSet.addAll(getGroupsFromRoles(user.roles))

def attributeContainer = new BaseAttributeContainer()
output = null
groupSet?.each { Group g->
    if (!g.managedSysId || (binding.hasVariable("managedSysId") && (g.managedSysId == managedSysId))) { // filter by managed sys
        if (!g.companyId || (binding.hasVariable("org") && (g.companyId == org?.id))) { // filter by organization
            if (!(g in oldGroupSet)) {
                g.operation = AttributeOperationEnum.ADD
            }
            Group group = groupWS.getGroup(g.id, null)
            groupBaseDN = group?.attributes?.find {GroupAttribute ga-> ga.name == 'AD_PATH'}?.value

            println("Adding group id  " + g.id + " --> " + (g.name + "," + groupBaseDN))
            def qualifiedGroupName = "cn=" + g.name +"," + groupBaseDN
            attributeContainer.attributeList.add(new BaseAttribute(qualifiedGroupName, qualifiedGroupName, g.operation))
        }
    }
}
oldGroupSet?.each { Group g->
    if (!g.managedSysId || (binding.hasVariable("managedSysId") && (g.managedSysId == managedSysId))) { // filter by managed sys
        if (!g.companyId || (binding.hasVariable("org") && (g.companyId == org?.id))) { // filter by organization
            if (!(g in groupSet)) {
                g.operation = AttributeOperationEnum.DELETE
                Group group = groupWS.getGroup(g.id, null)
                groupBaseDN = group?.attributes?.find {GroupAttribute ga-> ga.name == 'AD_PATH'}?.value

                println("Deleting group id  " + g.id + " --> " + (g.name + "," + groupBaseDN))
                def qualifiedGroupName = "cn=" + g.name + "," + groupBaseDN
                attributeContainer.attributeList.add(new BaseAttribute(qualifiedGroupName, qualifiedGroupName, g.operation))
            }
        }
    }
}
if (attributeContainer.attributeList) {
    output = attributeContainer
}

Set<Group> getGroupsFromRoles(Set<Role> roles) {
    def roleDataService = context?.getBean("roleDataService") as RoleDataService
    def groupDozerConverter = context?.getBean("groupDozerConverter") as GroupDozerConverter
    def groups = [] as Set
    roles.each {Role r->
        groups.addAll(groupDozerConverter.convertToDTOSet(roleDataService.getRole(r.id).groups, false))
    }
    return groups
}
