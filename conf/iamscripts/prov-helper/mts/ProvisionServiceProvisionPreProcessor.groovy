import org.openiam.base.AttributeOperationEnum
import org.openiam.idm.srvc.res.dto.Resource

import java.util.*;

import org.openiam.provision.dto.PasswordSync;
import org.openiam.provision.dto.ProvisionUser;
import org.openiam.provision.service.ProvisioningConstants;
import org.openiam.provision.service.AbstractProvisionPreProcessor;
import org.openiam.idm.srvc.org.service.OrganizationDataService;

import org.openiam.idm.srvc.role.service.RoleDataService;
import org.openiam.idm.srvc.auth.login.LoginDataService;
import org.openiam.idm.srvc.res.service.ResourceDataService;


/**
 * Pre-processor script that is used with the Provisioning service.
 */
public class ProvisionServiceProvisionPreProcessor extends AbstractProvisionPreProcessor<ProvisionUser> {
    private String ORGANIZATION_ADMIN_ROLEID = "8a4a92c641c017e00141c32e69e002c7";
    private static String RES_AD_ID = "112"
    private static String RES_EXCH_ID = "113"
    def resourceDataService = context.getBean("resourceDataService") as ResourceDataService

    public int add(ProvisionUser provisionUser, Map<String, Object> bindingMap) {

        // context to look up spring beans - commonly used beans. Included to help development

        OrganizationDataService orgManager = (OrganizationDataService)context.getBean("orgManager");
        RoleDataService roleDataService = (RoleDataService)context.getBean("roleDataService");
        LoginDataService loginService = (LoginDataService)context.getBean("loginManager");
        ResourceDataService resourceDataService = (ResourceDataService)context.getBean("resourceDataService");


        println("ProvisionServicePreProcessor: AddUser called.");
        println("ProvisionServicePreProcessor: User=" + user.toString());

        showBindingMap(bindingMap);

        //Add Delegation Filter for selected users org if processed user is in Organization Admin role
 /*       for(Role role : user.getRoles()) {
            if (ORGANIZATION_ADMIN_ROLEID.equals(role.getRoleId())) {
                println("Organization Admin");
                Organization organization = user.getPrimaryOrganization();
                if (organization != null) {
                    UserAttribute attr = new UserAttribute(DelegationFilterHelper.DLG_FLT_ORG, organization.getId());
                    attr.setOperation(AttributeOperationEnum.ADD);
                    attr.setId(null);
                    user.getUserAttributes().put(attr.getName(),attr);
                }
            }
        }
*/
    
        Resource adResource = resourceDataService.getResource("112", null);
        if(adResource != null){
             adResource.setOperation(AttributeOperationEnum.ADD);
             user.addResource(adResource);
        }
        Resource exchResource = resourceDataService.getResource("113", null);
        if(exchResource != null){
             exchResource.setOperation(AttributeOperationEnum.ADD);
             user.addResource(exchResource);
        }
        if(!user.metadataTypeId){
          user.metadataTypeId = "Contractor"
        }           

        return ProvisioningConstants.SUCCESS;
    }

    public int modify(ProvisionUser user, Map<String, Object> bindingMap){
        // context to look up spring beans

        println("ProvisionServicePreProcessor: ModifyUser called.");
        println("ProvisionServicePreProcessor: User=" + user.toString());

        showBindingMap(bindingMap);





        return ProvisioningConstants.SUCCESS;

    }



    public int delete(ProvisionUser user, Map<String, Object> bindingMap){

        // context to look up spring beans

        println("ProvisionServicePreProcessor: DeleteUser called.");
        println("ProvisionServicePreProcessor: User=" + user.toString());

        showBindingMap(bindingMap);



        return ProvisioningConstants.SUCCESS;
    }

    public int setPassword( PasswordSync passwordSync, Map<String, Object> bindingMap){



        println("ProvisionServicePreProcessor: SetPassword called.");

        showBindingMap(bindingMap);


        return ProvisioningConstants.SUCCESS;

    }

    private void showBindingMap( Map<String, Object> bindingMap){
        // context to look up spring beans
        println("Show binding map:");
        for (Map.Entry<String, Object> entry : bindingMap.entrySet()) {
            def key = entry.key
            def val = entry.value as String
            if (key == 'password') {
                val = 'PROTECTED'
            }
            println("- Key=" + key + " value=" + val)
        }
    }
}
