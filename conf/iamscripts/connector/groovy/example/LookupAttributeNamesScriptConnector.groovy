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
                attrNames = [
                        'LAST_NAME': 'EDITABLE',
                        'FIRST_NAME': 'EDITABLE',
                        'DESIG': 'EDITABLE',
                        'DEPT': 'EDITABLE',
                        'EMAIL': 'EDITABLE',
                        'MOBILE': 'EDITABLE',
                        'DOB': 'EDITABLE',
                        'ZONE': 'EDITABLE',
                        'DEPOT': 'EDITABLE',
                        'REGION': 'EDITABLE',
                        'EMPID': 'EDITABLE',
                        'AUTHORITY': 'READ_ONLY',
                        'status': 'READ_ONLY',
                        'password': 'HIDDEN',

                ].sort{a,b-> a.key <=> b.key} as Map
                break
            case "MANAGED_SYSTEM":
                attrNames = [
                        'PRE_PROCESS',
                        'ON_DELETE',
                        'INCLUDE_IN_PASSWORD_SYNC',
                        'GROUP_MEMBERSHIP_ENABLED',
                        'POST_PROCESS',
                ]
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

