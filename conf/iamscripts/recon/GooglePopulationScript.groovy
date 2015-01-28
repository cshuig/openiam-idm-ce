import org.openiam.base.AttributeOperationEnum
import org.openiam.idm.srvc.continfo.dto.EmailAddress
import org.openiam.idm.srvc.mngsys.dto.ManagedSysDto
import org.openiam.idm.srvc.mngsys.ws.ManagedSystemWebService
import org.openiam.idm.srvc.res.dto.Resource
import org.openiam.idm.srvc.res.service.ResourceDataService
import org.openiam.idm.srvc.user.dto.UserStatusEnum
import org.openiam.provision.dto.ProvisionUser

public class GooglePopulationScript extends org.openiam.idm.srvc.recon.service.AbstractPopulationScript<ProvisionUser> {
	public int execute(Map<String, String> line, ProvisionUser pUser){
		int retval = 125;
		System.out.println("In Script");
		for(String key: line.keySet()) {
			switch(key) {
				case "lastName":
					if(pUser.lastName != line.get("lastName")){
						pUser.lastName = line.get("lastName")
						retval = 0
					}
					break
				case "firstName":
					if(pUser.firstName != line.get("firstName")){
						pUser.firstName = line.get("firstName")
						retval = 0
					}
					break
				case "userEmail":
					if (pUser.getEmailAddresses()==null){
						pUser.setEmailAddresses(new HashSet<EmailAddress>());
					}
					EmailAddress newEmail = new EmailAddress();
					newEmail.setEmailAddress( line.get("userEmail"));
					newEmail.setIsActive(true);
					newEmail.setIsDefault(true);
					newEmail.setTypeDescription("PRIMARY_EMAIL");
					newEmail.setOperation(AttributeOperationEnum.ADD);
					pUser.getEmailAddresses().add(newEmail);
					pUser.setEmailCredentialsToNewUsers(true);
					break
			}
		}
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
