import org.openiam.connector.common.command.AbstractCommand
import org.openiam.connector.type.ConnectorDataException
import org.openiam.connector.type.constant.StatusCodeType
import org.openiam.connector.type.request.CrudRequest
import org.openiam.connector.type.request.RequestType
import org.openiam.connector.type.response.ObjectResponse
import org.openiam.connector.type.response.ResponseType
import org.openiam.provision.type.ExtensibleAttribute

class DeleteScriptConnector extends AbstractCommand<CrudRequest, ObjectResponse>{

    @Override
    public ObjectResponse execute(CrudRequest request) throws ConnectorDataException {
		println ("Call deleteTask");
        ObjectResponse rt = new ObjectResponse();
        //GET ALL FIELDS FROM OBJECT
        List<ExtensibleAttribute> fields = request.getExtensibleObject()?.getAttributes();
        String idValue = request.getObjectIdentity();
        String idName = request.getExtensibleObject()?.getPrincipalFieldName();
        String idType = request.getExtensibleObject()?.getPrincipalFieldDataType();
        println (idValue);
        println (idName);
        println (idType);
        println ("Call deleteTask");
        //START BODY
        String fileLocation = "/tmp/users/"+idValue;
        File f = new File(fileLocation);
        if (f.delete()){
            rt.setStatus(StatusCodeType.SUCCESS);
        }else {
            rt.setStatus(StatusCodeType.FAILURE);
        }
		println ("End deleteTask");
        //END BODY
        return rt;
    }
}

