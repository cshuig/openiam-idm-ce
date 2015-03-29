import java.util.*;

import org.openiam.provision.dto.PasswordSync;
import org.openiam.provision.dto.ProvisionGroup;
import org.openiam.provision.service.ProvisioningConstants;
import org.openiam.provision.service.AbstractProvisionPostProcessor;
/**
* Post-processor script that is used with the Provisioning service.
*/
public class ProvisionServiceGroupProvisionPostProcessor extends AbstractProvisionPostProcessor<ProvisionGroup> {

	public int add(ProvisionGroup group, Map<String, Object> bindingMap) {
		// context to look up spring beans

		println("ProvisionServiceGroupPostProcessor: AddGroup called.");
		println("ProvisionServiceGroupPostProcessor: Group=" + group.toString());


        showBindingMap(bindingMap);

		return ProvisioningConstants.SUCCESS;
	}
	
    public int modify(ProvisionGroup group, Map<String, Object> bindingMap){
    
    	// context to look up spring beans

    	
    	println("ProvisionServiceGroupPostProcessor: ModifyGroup called.");
			println("ProvisionServiceGroupPostProcessor: Group=" + group.toString());
		
	
		showBindingMap(bindingMap);
		
     	return ProvisioningConstants.SUCCESS;
    
	}
	
	
	
    public int delete(ProvisionGroup group, Map<String, Object> bindingMap){
    
    	// context to look up spring beans

      println("ProvisionServiceGroupPostProcessor: DeleteGroup called.");
			println("ProvisionServiceGroupPostProcessor: Group=" + group.toString());
			
			showBindingMap(bindingMap);

    
    	return ProvisioningConstants.SUCCESS;
	}
	
    public int setPassword( PasswordSync passwordSync, Map<String, Object> bindingMap){
    

     	println("ProvisionServiceGroupPostProcessor: SetPassword called.");
     	
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
