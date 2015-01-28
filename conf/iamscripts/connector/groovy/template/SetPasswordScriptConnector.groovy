import org.openiam.connector.common.command.AbstractCommand
import org.openiam.connector.type.ConnectorDataException
import org.openiam.connector.type.constant.StatusCodeType
import org.openiam.connector.type.request.CrudRequest
import org.openiam.connector.type.request.PasswordRequest
import org.openiam.connector.type.request.RequestType
import org.openiam.connector.type.request.SuspendResumeRequest
import org.openiam.connector.type.response.ObjectResponse
import org.openiam.connector.type.response.ResponseType
import org.openiam.provision.type.ExtensibleAttribute

class SetPasswordScriptConnector extends AbstractCommand<PasswordRequest, ResponseType>{

    @Override
    public ResponseType execute(PasswordRequest request) throws ConnectorDataException {
        println ("Call SetPasswordTask");
        ResponseType rt = new ResponseType();
        List<ExtensibleAttribute> fields = request.getExtensibleObject()?.getAttributes();
        String idValue = request.getObjectIdentity();
        String password = request.getPassword();
        String idName = request.getExtensibleObject()?.getPrincipalFieldName();
        String idType = request.getExtensibleObject()?.getPrincipalFieldDataType();
        
        
        
        rt.setStatus(StatusCodeType.SUCCESS);
        println (idValue);
        println (password);
        println (idName);
        println (idType);
        println ("Call SetPasswordTask");
        println ("End SetPasswordTask");
        return rt;
    }
}

