import org.openiam.base.AttributeOperationEnum
import org.openiam.idm.searchbeans.IdentitySearchBean
import org.openiam.idm.srvc.auth.dto.IdentityDto
import org.openiam.idm.srvc.auth.dto.IdentityTypeEnum
import org.openiam.idm.srvc.auth.login.IdentityService
import org.openiam.idm.srvc.grp.dto.Group
import org.openiam.idm.srvc.grp.dto.GroupAttribute
import org.openiam.idm.srvc.grp.ws.GroupDataWebService
import org.openiam.idm.srvc.mngsys.dto.ManagedSysDto
import org.openiam.idm.srvc.mngsys.ws.ManagedSystemWebService
import org.openiam.idm.srvc.res.dto.Resource
import org.openiam.idm.srvc.res.service.ResourceDataService
import org.openiam.idm.srvc.synch.dto.Attribute
import org.openiam.idm.srvc.user.domain.UserEntity
import org.openiam.idm.srvc.user.service.UserDataService
import org.openiam.provision.dto.ProvisionGroup
import org.openiam.idm.srvc.user.dto.UserStatusEnum;

public class PowershellGroupPopulationScript extends org.openiam.idm.srvc.recon.service.AbstractPopulationScript<ProvisionGroup> {

    ManagedSystemWebService systemWebService;
    UserDataService userMng;
    ResourceDataService resourceDataService;
    GroupDataWebService groupDataWebService;
    IdentityService identityService;

    public int execute(Map<String, String> line, ProvisionGroup pGroup){
        if (userMng == null) {
            userMng = context.getBean("userManager");
        }
        int retval = 0;
        for(String key: line.keySet()) {
            switch(key) {
                case "CN":
                    String name = line.get("CN");
                    pGroup.setName(name)
                    addAttribute(pGroup, new Attribute("CN",name));
                    break
                case "Description":
                    String description = line.get("Description");
                    pGroup.setDescription(description)
                    break
                case "DistinguishedName":
                    String DistinguishedName= line.get("DistinguishedName");
                    addAttribute(pGroup, new Attribute("DistinguishedName",DistinguishedName));
                    break
                case "MemberOf":
                    String MemberOf = line.get("MemberOf");
                    addAttribute(pGroup, new Attribute("MemberOf",MemberOf));
                    for(String memberDn: MemberOf.split("\\^")) {
                       addParentGroup(pGroup, memberDn);
                    }
                    break
                case "SamAccountName":
                    String samAccountName = line.get("SamAccountName");
                    addAttribute(pGroup, new Attribute("SamAccountName",samAccountName));
                    break
                case "groupType":
                    String groupType= line.get("groupType");
                    addAttribute(pGroup, new Attribute("groupType",groupType));
                    break
                case "ObjectClass":
                    String ObjectClass= line.get("ObjectClass");
                    addAttribute(pGroup, new Attribute("ObjectClass",ObjectClass));
                    break
                case "ObjectGUID":
                    String ObjectGUID = line.get("ObjectGUID");
                    addAttribute(pGroup, new Attribute("ObjectGUID",ObjectGUID));
                    break
                case "Members":
                    // String[] members = line.get("Members").split("\\^");
                    // for(String userPrincipalName: members) {
                    //    addMember(pGroup, userPrincipalName, userMng);
                    // }

                    addAttribute(pGroup, new Attribute("Members", line.get("Members")));
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
        //pGroup.addResource(currentResource);

        //set status to active: IMPORTANT!!!!
        pGroup.setStatus(UserStatusEnum.ACTIVE.value);
        return retval;
    }

    def addParentGroup(ProvisionGroup pGroup, String DN) {
          if (groupDataWebService == null) {
              groupDataWebService = context.getBean("groupWS");
          }
        if (identityService == null) {
            identityService = context.getBean("identityManager");
        }

        IdentitySearchBean searchBean = new IdentitySearchBean();
        searchBean.setManagedSysId(pGroup.getSrcSystemId());
        searchBean.setType(IdentityTypeEnum.GROUP);
        searchBean.setIdentity(DN);
        List<IdentityDto> identityDtos = identityService.findByExample(searchBean,pGroup.getRequestorUserId(), 0,1);
        if (identityDtos != null && identityDtos.size() > 0) {
            IdentityDto parentIdentity = identityDtos.get(0);
            Group parentGroup = groupDataWebService.getGroup(parentIdentity.getReferredObjectId(),pGroup.getRequestorUserId());
            pGroup.addParentGroup(parentGroup);
        }
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