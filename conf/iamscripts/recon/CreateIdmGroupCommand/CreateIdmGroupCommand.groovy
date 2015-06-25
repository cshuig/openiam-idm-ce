import org.openiam.base.AttributeOperationEnum;
import org.openiam.base.ws.Response;
import org.openiam.idm.srvc.auth.dto.IdentityDto;
import org.openiam.idm.srvc.auth.dto.IdentityTypeEnum;
import org.openiam.idm.srvc.auth.dto.LoginStatusEnum;
import org.openiam.idm.srvc.auth.login.IdentityService;
import org.openiam.idm.srvc.grp.dto.Group;
import org.openiam.idm.srvc.grp.ws.GroupDataWebService;
import org.openiam.idm.srvc.recon.command.grp.BaseReconciliationGroupCommand;
import org.openiam.idm.srvc.recon.dto.ReconciliationSituation;
import org.openiam.idm.srvc.user.domain.UserEntity;
import org.openiam.idm.srvc.user.service.UserDataService;
import org.openiam.provision.dto.ProvisionGroup;
import org.openiam.provision.service.ObjectProvisionService;
import org.openiam.provision.type.ExtensibleAttribute;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log
import java.util.List;
import java.util.HashSet;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.BeansException;
import org.apache.commons.collections.CollectionUtils;
import org.openiam.idm.srvc.grp.dto.GroupAttribute

public class CreateIdmGroupCommand  extends BaseReconciliationGroupCommand {
    static final  Log log = LogFactory.getLog(CreateIdmGroupCommand.class);

    protected ApplicationContext context;

    ObjectProvisionService<ProvisionGroup> provisionService;

    GroupDataWebService groupDataWebService;

    IdentityService identityService;

    UserDataService userManager;

    public CreateIdmGroupCustomCommand() {
    }


    public void setApplicationContext(ApplicationContext applicationContext) {
        this.context = applicationContext;
    }

    public boolean execute(ReconciliationSituation config, String principal, String mSysID, Group group, List<ExtensibleAttribute> attributes) {
        if (groupDataWebService == null) {
            groupDataWebService = context.getBean(GroupDataWebService.class);
        }
        if (provisionService == null) {
            provisionService = context.getBean("groupProvision");
        }
        if (provisionService == null) {
            provisionService = context.getBean("groupProvision");
        }
        if (identityService == null) {
            identityService = context.getBean("identityManager");
        }
        if (scriptRunner == null) {
            scriptRunner =  context.getBean("configurableGroovyScriptEngine");
        }

        log.debug("Entering CreateIdmGroupCommand");
        if(attributes == null){
            log.debug("Can't create IDM group without attributes");
        } else {
            try {
                ProvisionGroup pGroup = new ProvisionGroup(group);
                pGroup.setSrcSystemId(mSysID);
                int retval = executeScript(config.getScript(), attributes, pGroup);
                if(retval == 0) {
                    println("  =========== Group = "+pGroup);
                    println("  =========== Group Name = "+pGroup.name);
                    HashSet attrs = new HashSet(pGroup.getAttributes());
                    HashSet<Group> parents = null;
                    if(pGroup.getParentGroups() != null){
                        parents = new HashSet<Group>(pGroup.getParentGroups());
                        pGroup.getParentGroups().clear();
                    }
                    pGroup.getAttributes().clear();
                    Response saveGroupResponse = groupDataWebService.saveGroup(pGroup, DEFAULT_REQUESTER_ID);


                    println("  =========== saveGroupResponse = "+saveGroupResponse);

                    String groupId = (String)saveGroupResponse.getResponseValue();
                    if(parents != null) {
                        for(Group gr : parents) {
                            groupDataWebService.addChildGroup(gr.getId(),groupId,DEFAULT_REQUESTER_ID);
                        }
                    }
                    pGroup.setAttributes(attrs);
                    pGroup.setManagedSysId(mSysID);
                    pGroup.setId(groupId);
                    saveGroupResponse = groupDataWebService.saveGroup(pGroup, DEFAULT_REQUESTER_ID);

                    println("  =========== saveGroupResponse = "+saveGroupResponse);
                    println("  =========== groupId = "+groupId);

                    IdentityDto identity = new IdentityDto();
                    identity.setIdentity(principal);
                    identity.setType(IdentityTypeEnum.GROUP);
                    identity.setManagedSysId(mSysID);
                    identity.setOperation(AttributeOperationEnum.ADD);
                    identity.setStatus(LoginStatusEnum.ACTIVE);
                    identity.setReferredObjectId(groupId);
                    identity.setCreateDate(new Date());
                    identity.setCreatedBy(DEFAULT_REQUESTER_ID);
                    identityService.save(identity);

                    provisionService.add(pGroup);
                    for(String memberPrincipal : pGroup.getMembersIds()) {
                        UserEntity user = userManager.getUserByPrincipal(memberPrincipal, mSysID, false);
                        if(user != null) {
                            Response response = groupDataWebService.addUserToGroup(groupId, user.getId(), DEFAULT_REQUESTER_ID);
                            log.debug("User Member with principal = "+memberPrincipal+" was added to Group = "+identity.getIdentity() + " Managed Sys = "+identity.getManagedSysId() + ". \nResponse = "+response);
                        }
                    }
                }else{
                    log.debug("Couldn't populate ProvisionGroup. Group not added");
                    return false;
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}


