import java.util.*;

import org.openiam.provision.dto.PasswordSync;
import org.openiam.provision.dto.ProvisionUser;
import org.openiam.provision.service.ProvisioningConstants;
import org.openiam.provision.service.AbstractPostProcessor;

/**
* Post-processor script that is used with the Provisioning service.
*/
public class ProvisionServicePostProcessor extends AbstractPostProcessor<ProvisionUser> {

	public int add(ProvisionUser user, Map<String, Object> bindingMap) {
		// context to look up spring beans

		println("ProvisionServicePostProcessor: AddUser called.");
		println("ProvisionServicePostProcessor: User=" + user.toString());

		return ProvisioningConstants.SUCCESS;
	}
	
    public int modify(ProvisionUser user, Map<String, Object> bindingMap){
    
    	// context to look up spring beans

    	println("ProvisionServicePostProcessor: ModifyUser called.");
		println("ProvisionServicePostProcessor: User=" + user.toString());
		
		showBindingMap(bindingMap);
		
     	return ProvisioningConstants.SUCCESS;
    
	}
	
	
	
    public int delete(ProvisionUser user, Map<String, Object> bindingMap){
    
    	// context to look up spring beans

        println("ProvisionServicePostProcessor: DeleteUser called.");
		println("ProvisionServicePostProcessor: User=" + user.toString());
			
		showBindingMap(bindingMap);

    
    	return ProvisioningConstants.SUCCESS;
	}
	
    public int setPassword( PasswordSync passwordSync, Map<String, Object> bindingMap){
    

     	println("ProvisionServicePostProcessor: SetPassword called.");
     	
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
