import java.util.Map;

import org.openiam.provision.dto.PasswordSync;
import org.openiam.provision.dto.ProvisionUser;
import org.openiam.provision.service.PostProcessor;
import org.openiam.provision.service.ProvisioningConstants;


public class LDAPPostProcessor implements PostProcessor<ProvisionUser> {
	
	public int add(ProvisionUser user, Map<String, Object> bindingMap, boolean success) {
	
		println("LDAP Postprocessor: AddUser called.");
		println("PostProcessor: Provisioning to resource was successful?" + success)
		println("PostProcessor: User=" + user.toString());
		println("Show binding map");

        showBindingMap(bindingMap)
		
	
		return ProvisioningConstants.SUCCESS;
	}
	
    public int modify(ProvisionUser user, Map<String, Object> bindingMap, boolean success){
    
    
    	return ProvisioningConstants.SUCCESS;
    
	}
	
    public int delete(ProvisionUser user, Map<String, Object> bindingMap, boolean success){
    
     
    	return ProvisioningConstants.SUCCESS;
	}
	
    public int setPassword( Map<String, Object> bindingMap, boolean success){
    			
    
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
