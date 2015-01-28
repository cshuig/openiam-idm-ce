import org.openiam.connector.common.command.AbstractCommand
import org.openiam.connector.type.ConnectorDataException
import org.openiam.connector.type.constant.StatusCodeType
import org.openiam.connector.type.request.CrudRequest
import org.openiam.connector.type.request.RequestType
import org.openiam.connector.type.response.ObjectResponse
import org.openiam.connector.type.response.ResponseType
import org.openiam.provision.type.ExtensibleAttribute

class AddScriptConnector extends AbstractCommand<CrudRequest, ObjectResponse>{

    @Override
    public ObjectResponse execute(CrudRequest request) throws ConnectorDataException {
		 println ("Call addTask");
        ObjectResponse rt = new ObjectResponse();
        List<ExtensibleAttribute> fields = request.getExtensibleObject()?.getAttributes();
        String idValue = request.getObjectIdentity();
        String idName = request.getExtensibleObject()?.getPrincipalFieldName();
        String idType = request.getExtensibleObject()?.getPrincipalFieldDataType();
        println (idValue);
        println (idName);
        println (idType);

        println ("Call addTask");
     

 		println ("End addTask");
        return rt;
    }
}

