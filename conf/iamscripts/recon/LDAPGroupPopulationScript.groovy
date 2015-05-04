import org.openiam.base.AttributeOperationEnum
import org.openiam.idm.srvc.grp.dto.GroupAttribute
import org.openiam.idm.srvc.mngsys.dto.ManagedSysDto
import org.openiam.idm.srvc.mngsys.ws.ManagedSystemWebService
import org.openiam.idm.srvc.res.dto.Resource
import org.openiam.idm.srvc.res.service.ResourceDataService
import org.openiam.idm.srvc.synch.dto.Attribute
import org.openiam.idm.srvc.user.domain.UserEntity
import org.openiam.idm.srvc.user.service.UserDataService
import org.openiam.provision.dto.ProvisionGroup
import org.openiam.idm.srvc.user.dto.UserStatusEnum;

public class LDAPGroupPopulationScript extends org.openiam.idm.srvc.recon.service.AbstractPopulationScript<ProvisionGroup> {

    ManagedSystemWebService systemWebService;
    UserDataService userMng;
    ResourceDataService resourceDataService;

    public int execute(Map<String, String> line, ProvisionGroup pGroup){
        if (userMng == null) {
            userMng = context.getBean("userManager");
        }
        int retval = 0;
        for(String key: line.keySet()) {
            switch(key) {
                case "cn":
                    String name = line.get("cn");
                    pGroup.setName(name)
                    addAttribute(pGroup, new Attribute("cn",name));
                    break
                case "description":
                    String description = line.get("description");
                    pGroup.setDescription(description)
                    break
                case "objectClass":
                    break
                case "uniqueMember":
                    String[] members = line.get("uniqueMember").split("\\^");
                    for(String userPrincipalName: members) {
                       addMember(pGroup, userPrincipalName, userMng);
                    }
                    break
            }
        }
        if (systemWebService == null) {
            systemWebService = context.getBean("managedSysService");
        }
        if (resourceDataService == null) {
            resourceDataService = context.getBean("resourceDataService");
        }
        ManagedSysDto currentManagedSys = systemWebService.getManagedSys(pGroup.getSrcSystemId());
        pGroup.addNotProvisioninResourcesId(currentManagedSys.getResourceId());

        Resource currentResource = resourceDataService.getResource(currentManagedSys.getResourceId(), null);
        currentResource.setOperation(AttributeOperationEnum.ADD);
        pGroup.addResource(currentResource);

        //set status to active: IMPORTANT!!!!
        pGroup.setStatus(UserStatusEnum.ACTIVE.value);
        pGroup.setMdTypeId('GENERAL_GROUP');
        return retval;
    }

    def addMember(ProvisionGroup pGroup, String login, UserDataService userMng) {
        UserEntity member = userMng.getUserByPrincipal(login, pGroup.getSrcSystemId(), false);
        if (member != null) {
            pGroup.addMemberId(member.getId());
        }
    }

    def addAttribute(ProvisionGroup pGroup, Attribute attr) {
        if (attr?.name) {
            def groupAttr = new GroupAttribute(attr.name, attr.value)
            groupAttr.operation = AttributeOperationEnum.ADD
            if (pGroup.getId() != null) {
                for (GroupAttribute a : pGroup.attributes) {
                    if (a.getName().equalsIgnoreCase(attr.name)) {
                        pGroup.attributes.remove(a)
                        groupAttr.operation = AttributeOperationEnum.REPLACE
                        break
                    }
                }
            }
            pGroup.attributes.add(groupAttr)
            println("Attribute '" + attr.name + "' added to the group object.")
        }
    }

}