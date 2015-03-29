import org.openiam.provision.dto.ProvisionGroup
import org.openiam.provision.service.AbstractProvisionPreProcessor

import java.util.*;

import org.openiam.provision.dto.PasswordSync;
import org.openiam.provision.service.ProvisioningConstants;


/**
 * Pre-processor script that is used with the Provisioning service.
 */
public class ProvisionServiceGroupPreProcessorProvision extends AbstractProvisionPreProcessor<ProvisionGroup> {

    public int add(ProvisionGroup group, Map<String, Object> bindingMap) {


        println("ProvisionServiceGroupPreProcessor: AddGroup called.");
        println("ProvisionServiceGroupPreProcessor: Group=" + group.toString());

        showBindingMap(bindingMap);

        return ProvisioningConstants.SUCCESS;
    }

    public int modify(ProvisionGroup group, Map<String, Object> bindingMap){
        // context to look up spring beans

        println("ProvisionServiceGroupPreProcessor: ModifyGroup called.");
        println("ProvisionServiceGroupPreProcessor: Group=" + group.toString());

        showBindingMap(bindingMap);


        return ProvisioningConstants.SUCCESS;

    }



    public int delete(ProvisionGroup group, Map<String, Object> bindingMap){

        // context to look up spring beans

        println("ProvisionServiceGroupPreProcessor: DeleteGroup called.");
        println("ProvisionServiceGroupPreProcessor: Group=" + group.toString());

        showBindingMap(bindingMap);



        return ProvisioningConstants.SUCCESS;
    }

    public int setPassword( PasswordSync passwordSync, Map<String, Object> bindingMap){



        println("ProvisionServiceGroupPreProcessor: SetPassword called.");

        showBindingMap(bindingMap);


        return ProvisioningConstants.SUCCESS;

    }

    private void showBindingMap( Map<String, Object> bindingMap){

        // context to look up spring beans


        println("Show binding map");

        for (Map.Entry<String, Object> entry : bindingMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            println("- Key=" + key + "  value=" + value.toString() );
        }


    }





}