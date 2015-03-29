
import org.openiam.provision.dto.ProvisionUser
import org.openiam.provision.service.AbstractPostProcessor
import org.openiam.provision.service.ProvisioningConstants;


public class ExchangePreProcessor extends AbstractPostProcessor<ProvisionUser> {
	
	public int add(ProvisionUser user, Map<String, Object> bindingMap) {
		
		println("Exchange PreProcessor: AddUser called.");
/* //		println("PreProcessor: User=" + user.toString());
	//	println("Show binding map");
		
          //     for (Map.Entry<String, Object> entry : bindingMap.entrySet()) {
            //            String key = entry.getKey();
              //          Object value = entry.getValue();
                //                println("- Key=" + key + "  value=" + value.toString() );
                //}
*/	
	
		return ProvisioningConstants.SUCCESS;
	}
	
    public int modify(ProvisionUser user, Map<String, Object> bindingMap){
    	
   		
    
   
    	return ProvisioningConstants.SUCCESS;
    
	}
	
    public int delete(ProvisionUser user, Map<String, Object> bindingMap){
    
     
    	return ProvisioningConstants.SUCCESS;
	}
	
    public int setPassword( Map<String, Object> bindingMap){
         println("Exchange PreProcessor: Set Password called.");
/* //         println("Exchange PreProcessor: User=" + user.toString());
//         println("Exchange Show binding map");

  //       for (Map.Entry<String, Object> entry : bindingMap.entrySet()) {
    //            String key = entry.getKey();
      //          Object value = entry.getValue();
        //        println("- Key=" + key + "  value=" + value.toString() );
         //}
 		
   */ 
    	return ProvisioningConstants.FAIL;
    
    }
	

    
}

