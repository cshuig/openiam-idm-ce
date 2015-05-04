import org.openiam.base.AttributeOperationEnum
import org.openiam.base.BaseAttribute
import org.openiam.base.BaseAttributeContainer
import org.openiam.idm.srvc.grp.dto.Group
import org.openiam.idm.srvc.grp.service.GroupDataService


println("================ userGroup.groovy starting..");

output = null
def attributeContainer = new BaseAttributeContainer()
user.groups?.each{Group g->
    if (g.operation == AttributeOperationEnum.ADD || g.operation == AttributeOperationEnum.DELETE) {
        def dn = getGroupAttribute(g, "LDAP_DN")
        if (dn) {
            attributeContainer.attributeList.add(new BaseAttribute(dn, dn, g.operation))
        } else {
            println "Group ${g.name} provisioning will be skipped (LDAP_DN attribute can not be found)"
        }
    }
}

if (attributeContainer.attributeList) {
    output = attributeContainer
}

String getGroupAttribute(Group group, String attrName) {
    def value = group.attributes.find({ it.name.equalsIgnoreCase(attrName) })?.value
    if (!value) {
        def groupManager = context?.getBean("groupManager") as GroupDataService
        def grp = groupManager.getGroup(group.id)
        value = grp.attributes.find({ it.name.equalsIgnoreCase(attrName) })?.value
    }
    return value
}