import org.openiam.connector.common.command.AbstractCommand
import org.openiam.connector.type.ConnectorDataException
import org.openiam.connector.type.constant.ErrorCode
import org.openiam.connector.type.constant.StatusCodeType
import org.openiam.connector.type.request.LookupRequest;
import org.openiam.connector.type.request.RequestType
import org.openiam.connector.type.response.ObjectResponse
import org.openiam.connector.type.response.ResponseType
import org.openiam.provision.type.ExtensibleAttribute

class ScriptConnector extends AbstractCommand{

    @Override
    public ResponseType execute(RequestType request) throws ConnectorDataException {
        ResponseType rt = null;
        def command = request.getOperation();
        if (command==null){
            throw new ConnectorDataException(ErrorCode.COMMAND_TYPE_NOT_DEFINED);
        }
        //GET ALL FIELDS FROM OBJECT
        List<ExtensibleAttribute> fields = request.getExtensibleObject()?.getAttributes();
        String idValue = request.getObjectIdentity();
        String idName = request.getExtensibleObject()?.getPrincipalFieldName();
        String idType = request.getExtensibleObject()?.getPrincipalFieldDataType();
        println (idValue);
        println (idName);
        println (idType);
        switch(command){
            case "ADD":
                rt = new ObjectResponse();
                this.addTask(rt, idValue, idName, idType, fields);
                break;
            case "DELETE":
                rt = new ObjectResponse();
                this.deleteTask(rt, idValue, idName, idType);
                break;
            case "MODIFY":
                rt = new ObjectResponse();
                this.modifyTask(rt, idValue, idName, idType, fields);
                break;
            case "LOOKUP_ATTRIBUTE_NAME":
                break;
            case "LOOKUP":
                LookupRequest req = (LookupRequest)request;
                println ("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" + req.getSearchValue());
                break;
            case "SET_PASSWORD":
                break;
            case "EXPIRE_PASSWORD":
                break;
            case "RESET_PASSWORD":
                break;
            case "VALIDATE_PASSWORD":
                break;
            case "SUSPEND":
                break;
            case "RESUME":
                break;
            case "TEST":
                rt = new ResponseType ();
                rt.setStatus(StatusCodeType.SUCCESS);
                break;
            case "SEARCH":
                break;
            default:
                rt.setStatus(StatusCodeType.FAILURE);
                rt.setError(ErrorCode.OPERATION_NOT_SUPPORTED_EXCEPTION);
                break;
        }
        return rt;
    }

    private void addTask(ResponseType response, String idValue, String idName, String idType, List<ExtensibleAttribute> fields){
        println ("Call addTask");
        try{
            //START BODY
            String fileLocation = "/tmp/users/";
            File f = new File(fileLocation);
            if (!f.isDirectory()){
                f.mkdir();
            }
            fileLocation +=idValue;
            f= new File(fileLocation);
            if (f.isFile()){
                // already exist such file;
                response.setStatus(StatusCodeType.SUCCESS);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(idName);
                sb.append("=");
                sb.append(idValue);
                sb.append("\n");
                for (ExtensibleAttribute ea:fields){
                    sb.append(ea.getName());
                    sb.append("=");
                    sb.append(ea.getValue());
                    sb.append("\n");
                }
                FileWriter fw = new FileWriter(f);
                println (sb.toString());
                fw.write(sb.toString());
                fw.flush();
            }
            //END BODY
            response.setStatus(StatusCodeType.SUCCESS);
        }catch (Exception e)
        {
            response.setStatus(StatusCodeType.FAILURE);
        }
    }

    private void deleteTask(ResponseType response, String idValue, String idName, String idType){
        println ("Call deleteTask");

        //START BODY
        String fileLocation = "/tmp/users/"+idValue;
        File f = new File(fileLocation);
        if (f.delete()){
            response.setStatus(StatusCodeType.SUCCESS);
        }else {
            response.setStatus(StatusCodeType.FAILURE);
        }
        //END BODY

    }

    private void modifyTask(ResponseType response, String idValue, String idName, String idType, List<ExtensibleAttribute> fields){
        println ("Call addTask");
        //START BODY
        //END BODY
        response.setStatus(StatusCodeType.SUCCESS);
    }
}

