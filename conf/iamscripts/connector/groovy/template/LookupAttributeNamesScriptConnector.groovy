import org.openiam.connector.common.command.AbstractCommand
import org.openiam.connector.type.ConnectorDataException
import org.openiam.connector.type.constant.StatusCodeType
import org.openiam.connector.type.request.LookupRequest
import org.openiam.connector.type.response.LookupAttributeResponse
import org.openiam.provision.type.ExtensibleAttribute
import org.openiam.provision.type.ExtensibleObject

class LookupAttributeNamesScriptConnector<ExtObject extends ExtensibleObject> extends AbstractCommand<LookupRequest<ExtObject>, LookupAttributeResponse> {

    @Override
    public LookupAttributeResponse execute(LookupRequest<ExtObject> lookupRequest) throws ConnectorDataException {
        println "call ATTRIBUTE NAMES LOOKUP start"

        def respType = new LookupAttributeResponse()

        def attrNames = null
        switch (lookupRequest.executionMode) {
            case "POLICY_MAP":
                attrNames = [:]
                break
            case "MANAGED_SYSTEM":
                attrNames = []
                break
        }

        def attributes = new ArrayList<ExtensibleAttribute>()
        if (attrNames in List) {
            def attrNamesList = attrNames as List
            for (String name : attrNamesList) {
                attributes << new ExtensibleAttribute(name, "")
            }
        } else if (attrNames in Map) {
            def attrNamesMap = attrNames as Map
            for (String name : attrNamesMap.keySet()) {
                attributes << new ExtensibleAttribute(name, "", attrNamesMap.get(name) as String)
            }
        }

        respType.status = StatusCodeType.SUCCESS
        respType.attributes = attributes

        println "call ATTRIBUTE NAMES LOOKUP end"
        return respType

    }

}

