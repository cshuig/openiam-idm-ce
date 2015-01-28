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

public class LinuxPopulationScript extends org.openiam.idm.srvc.recon.service.AbstractPopulationScript<ProvisionUser> {
    public int execute(Map<String, String> line, ProvisionUser pUser){
        int retval = 125;
        System.out.println("In Script");
        for(String key: line.keySet()) {
            switch(key) {
                case "login":
                   if (line.get("name")?.size()==0 ){
                   	 pUser.firstName =line.get("login");
                            retval = 0;
                   }
                    break
                case "name":
                        System.out.println("Find Name");
                    String[] parts = line.get("name").split(" ")
                    if(parts.length == 2){
                        if(pUser.firstName != parts[0]){
                            pUser.firstName = parts[0]
                            retval = 0
                        }else {
                         String[] parts = line.get("name").split(".")
                    if(parts.length == 2){
                        if(pUser.firstName != parts[0]){
                            pUser.firstName = parts[0]
                            retval = 0
                        }
                        }
                        if(pUser.lastName != parts[1]){
                            pUser.lastName = parts[1]
                            retval = 0
                        }
                    }
                    else {
                     pUser.firstName = line.get("name");
                    	retval = 0
                    }
                    break
                case "surname":
                    if(pUser.lastName != line.get("surname")){
                        pUser.setLastName(line.get("surname"))
                        retval = 0
                    }
                    break
                case "password":
                    // not supported yet
                    break
                case "homePhone":
                    // not supported yet
                    break
                case "workPhone":
                    // not supported yet
                    break
                case "roomNumber":
                    // not supported yet
                    break
                case "groups":
                    // not supported yet
                    break                                                                            
            }
        }
       /* Set<Role> roleList = pUser.getRoles();
        RoleDataWebService dataService = context.getBean("roleWS");
        Role endUserRole = dataService.getRole("1","3000");
        if (!roleList.contains(endUserRole)) {
            endUserRole.setOperation(AttributeOperationEnum.ADD);
            pUser.getRoles().add(endUserRole);
        }*/
        ManagedSystemWebService systemWebService = context.getBean("managedSysService");
        ResourceDataService  resourceDataService = context.getBean("resourceDataService");
        ManagedSysDto currentManagedSys = systemWebService.getManagedSys(pUser.getSrcSystemId());
        Resource currentResource = resourceDataService.getResource(currentManagedSys.getResourceId(), null);
        currentResource.setOperation(AttributeOperationEnum.ADD);
        pUser.getResources().add(currentResource);
        //set status to active: IMPORTANT!!!!
        pUser.setStatus(UserStatusEnum.PENDING_INITIAL_LOGIN);
                System.out.println("Out Script");
        return retval;
    }
}