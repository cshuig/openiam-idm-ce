import org.openiam.base.ws.Response
import org.openiam.base.ws.ResponseStatus
import org.openiam.provision.dto.ProvisionActionEvent
import org.openiam.provision.dto.ProvisionActionTypeEnum
import org.openiam.provision.service.ProvisionServiceEventProcessor

public class DefaultProvisionServiceEventProcessor implements ProvisionServiceEventProcessor {

    def context

    @Override
    Response process(ProvisionActionEvent event, ProvisionActionTypeEnum type) {
        /*
        if (event) {
            println "Executing action '${event.action?.value}' for user with ID '${event.targetUserId}' requested by user with ID '${event.requesterId}'"
        }
        if (ProvisionActionTypeEnum.PRE == type) {
            def response = new Response(ResponseStatus.FAILURE) //Or ResponseStatus.SUCCESS
            response.responseValue = BREAK
            return response
        }
        */

        def response = new Response(ResponseStatus.SUCCESS)
        response.responseValue = CONTINUE
        return response
    }
}