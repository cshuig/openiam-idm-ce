import java.util.*;

import org.openiam.provision.dto.PasswordSync;
import org.openiam.provision.dto.ProvisionUser;
import org.openiam.provision.service.ProvisioningConstants;
import org.openiam.provision.service.AbstractProvisionPostProcessor;
import  org.openiam.idm.srvc.auth.ws.*


import org.openiam.idm.srvc.auth.dto.Login;


/**
* Post-processor script that is used with the Provisioning service.
*/
public class ProvisionServicePostProcessor extends AbstractProvisionPostProcessor<ProvisionUser> {

	public int add(ProvisionUser user, Map<String, Object> bindingMap) {
		// context to look up spring beans

LoginDataWebService loginService = (LoginDataWebService)context.getBean("loginWS");


		println("ProvisionServicePostProcessor: AddUser called.");
		println("ProvisionServicePostProcessor: User=" + user.toString());

        //Try to fix login
        println("-----------------------------------------try fix LOGIN --------------------------------------");
        println ("Login from Primary Principal" + user.getPrimaryPrincipal("0"));
        println ("Login from principalList" + user.principalList);
       Login l = user.getPrimaryPrincipal("0");
        if (l){
                println("-------------------------------------------- fix LOGIN --------------------------------------");
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DAY_OF_YEAR, 120);
                l.pwdExp = c.getTime();
		loginService.saveLogin(l);

        }


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
