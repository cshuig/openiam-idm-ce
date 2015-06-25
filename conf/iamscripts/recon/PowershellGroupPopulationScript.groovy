import org.openiam.base.AttributeOperationEnum
import org.openiam.idm.searchbeans.IdentitySearchBean
import org.openiam.idm.srvc.auth.dto.IdentityDto
import org.openiam.idm.srvc.auth.dto.IdentityTypeEnum
import org.openiam.idm.srvc.auth.login.IdentityService
import org.openiam.idm.srvc.grp.domain.GroupEntity
import org.openiam.idm.srvc.grp.dto.Group
import org.openiam.idm.srvc.grp.dto.GroupAttribute
import org.openiam.idm.srvc.grp.ws.GroupDataWebService
import org.openiam.idm.srvc.mngsys.dto.ManagedSysDto
import org.openiam.idm.srvc.mngsys.ws.ManagedSystemWebService
import org.openiam.idm.srvc.recon.service.AbstractPopulationScript
import org.openiam.idm.srvc.res.dto.Resource
import org.openiam.idm.srvc.res.service.ResourceDataService
import org.openiam.idm.srvc.role.domain.RoleEntity
import org.openiam.idm.srvc.role.dto.Role
import org.openiam.idm.srvc.role.service.RoleDataService
import org.openiam.idm.srvc.role.ws.RoleDataWebService
import org.openiam.idm.srvc.synch.dto.Attribute
import org.openiam.idm.srvc.user.domain.UserEntity
import org.openiam.idm.srvc.user.service.UserDataService
import org.openiam.provision.dto.ProvisionGroup
import org.openiam.idm.srvc.user.dto.UserStatusEnum

import javax.naming.InvalidNameException
import javax.naming.ldap.LdapName;

public class PowershellGroupPopulationScript extends AbstractPopulationScript<ProvisionGroup> {

    ManagedSystemWebService systemWebService;
    UserDataService userMng;
    ResourceDataService resourceDataService;
    GroupDataWebService groupDataWebService;
    IdentityService identityService;
    RoleDataService roleDataService;
    public int execute(Map<String, String> line, ProvisionGroup pGroup){
        println("======================= PowershellGroupPopulationScript.groovy =============== processing..");
        if (userMng == null) {
            userMng = context.getBean("userManager");
        }
        if(roleDataService == null) {
            roleDataService = context.getBean("roleDataService");
        }
        if (groupDataWebService == null) {
            groupDataWebService = context.getBean("groupWS");
        }
        if (identityService == null) {
            identityService = context.getBean("identityManager");
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
                case "sAMAccountType":
                    String sAMAccountType= line.get("sAMAccountType");
                    addAttribute(pGroup, new Attribute("sAMAccountType",sAMAccountType));
                    break
                case "DistinguishedName":
                    String DistinguishedName= line.get("DistinguishedName");
                    addAttribute(pGroup, new Attribute("DistinguishedName",DistinguishedName));
                    break
                case "MemberOf":
                    println("==================== PowershellGroupPopulationScript.groovy=== MemberOf"+line.get("MemberOf"))
                    String MemberOf = line.get("MemberOf");
                    // addAttribute(pGroup, new Attribute("MemberOf",MemberOf));
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
                case "GroupScope":
                    String GroupScope= line.get("GroupScope");
                    addAttribute(pGroup, new Attribute("GroupScope",GroupScope));
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
                    String[] members = line.get("Members").split("\\^");
                    for(String userPrincipalName: members) {
                        addMember(pGroup, userPrincipalName, userMng);
                    }

                    // addAttribute(pGroup, new Attribute("Members", line.get("Members")));
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
        println("======================= PowershellGroupPopulationScript.groovy =============== finished..");
        return retval;
    }



    def addParentGroup(ProvisionGroup pGroup, String DN) {
        IdentitySearchBean searchBean = new IdentitySearchBean();
        searchBean.setManagedSysId(pGroup.getSrcSystemId());
        searchBean.setType(IdentityTypeEnum.GROUP);
        searchBean.setIdentity(DN);
        List<IdentityDto> identityDtos = identityService.findByExample(searchBean,pGroup.getRequestorUserId(), 0,1);
        if (identityDtos != null && identityDtos.size() > 0) {
            IdentityDto parentIdentity = identityDtos.get(0);
            Group parentGroup = groupDataWebService.getGroup(parentIdentity.getReferredObjectId(),pGroup.getRequestorUserId());
            pGroup.addParentGroup(parentGroup);
            println("======================= PowershellGroupPopulationScript.groovy =============== parent group= "+parentIdentity+" was assigned to group="+pGroup.getName());
        }
    }

    def addMember(ProvisionGroup pGroup, String login, UserDataService userMng) {
        UserEntity member = userMng.getUserByPrincipal(login, pGroup.getSrcSystemId(), false);
        if (member != null) {
            pGroup.addMemberId(member.getId());
            println("======================= PowershellGroupPopulationScript.groovy =============== child user= "+login+" was assigned to group="+pGroup.getName());
        } else {
            IdentitySearchBean searchBean = new IdentitySearchBean();
            searchBean.setDeepCopy(false);
            searchBean.setIdentity(login);
            searchBean.setManagedSysId(pGroup.getSrcSystemId());
            searchBean.setType(IdentityTypeEnum.GROUP);
            List<IdentityDto> identities = identityService.findByExample(searchBean, "3000", 0, 1);
            if(identities) {
                IdentityDto childGroupIdenity = identities.get(0);
                Group child = groupDataWebService.getGroup(childGroupIdenity.getReferredObjectId(), "3000");
                child.setOperation(AttributeOperationEnum.ADD);
                pGroup.addChildGroup(child);
                println("======================= PowershellGroupPopulationScript.groovy =============== child group= "+childGroupIdenity+" was assigned to group="+pGroup.getName());
            }
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
            println("======================= PowershellGroupPopulationScript.groovy ===== Attribute '" + attr.name+ "="+ attr.value + "' added to the group object.")
        }
    }


}