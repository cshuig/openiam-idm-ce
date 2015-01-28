import org.openiam.base.AttributeOperationEnum
import org.openiam.idm.srvc.audit.service.AuditLogService
import org.openiam.idm.srvc.mngsys.dto.ManagedSysDto
import org.openiam.idm.srvc.mngsys.service.ManagedSystemServiceImpl
import org.openiam.idm.srvc.mngsys.ws.ManagedSystemWebService
import org.openiam.idm.srvc.res.dto.Resource
import org.openiam.idm.srvc.res.service.ResourceDataService
import org.openiam.idm.srvc.role.service.RoleDataService
import org.openiam.idm.srvc.role.ws.RoleDataWebService
import org.openiam.provision.dto.ProvisionUser
import org.openiam.idm.srvc.role.dto.Role;
import org.openiam.idm.srvc.user.dto.UserStatusEnum;

public class OraclePopulationScript extends org.openiam.idm.srvc.recon.service.AbstractPopulationScript<ProvisionUser> {
    public int execute(Map<String, String> line, ProvisionUser pUser){
        int retval = 1;

        ManagedSystemWebService systemWebService = context.getBean("managedSysService");
        ResourceDataService  resourceDataService = context.getBean("resourceDataService");
        ManagedSysDto currentManagedSys = systemWebService.getManagedSys(pUser.getSrcSystemId());
        Resource currentResource = resourceDataService.getResource(currentManagedSys.getResourceId(), null);
        currentResource.setOperation(AttributeOperationEnum.ADD);
        pUser.getResources().add(currentResource);
        //set status to active: IMPORTANT!!!!
        pUser.setStatus(UserStatusEnum.PENDING_INITIAL_LOGIN);
        return retval;
    }
}