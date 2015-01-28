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
        


		rt.setStatus(StatusCodeType.SUCCESS);
		println ("End deleteTask");
        //END BODY
        return rt;
    }
}

