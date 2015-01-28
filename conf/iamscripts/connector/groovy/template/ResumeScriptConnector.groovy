import org.openiam.connector.common.command.AbstractCommand
import org.openiam.connector.type.ConnectorDataException
import org.openiam.connector.type.constant.StatusCodeType
import org.openiam.connector.type.request.CrudRequest
import org.openiam.connector.type.request.RequestType
import org.openiam.connector.type.request.SuspendResumeRequest
import org.openiam.connector.type.response.ObjectResponse
import org.openiam.connector.type.response.ResponseType
import org.openiam.provision.type.ExtensibleAttribute

class ResumeScriptConnector extends AbstractCommand<SuspendResumeRequest, ResponseType>{

    @Override
    public ResponseType execute(SuspendResumeRequest request) throws ConnectorDataException {
         println ("Call ResumeTask");
        ResponseType rt = new ResponseType();
        List<ExtensibleAttribute> fields = request.getExtensibleObject()?.getAttributes();
        String idValue = request.getObjectIdentity();
        String idName = request.getExtensibleObject()?.getPrincipalFieldName();
        String idType = request.getExtensibleObject()?.getPrincipalFieldDataType();
        
	rt.setStatus(StatusCodeType.SUCCESS);
        println (idValue);
        println (idName);
        println (idType);
        println ("Call ResumeTask");
        println ("End ResumeTask");
        return rt;
    }
}

