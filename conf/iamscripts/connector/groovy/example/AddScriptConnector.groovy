import org.openiam.connector.common.command.AbstractCommand
import org.openiam.connector.type.ConnectorDataException
import org.openiam.connector.type.constant.StatusCodeType
import org.openiam.connector.type.request.CrudRequest
import org.openiam.connector.type.response.ObjectResponse
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
        try{
            String fileLocation = "/tmp/users/";
            File f = new File(fileLocation);
            if (!f.isDirectory()){
                f.mkdir();
            }
            fileLocation +=idValue;
            f= new File(fileLocation);
            if (!f.isFile()){
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
            rt.setStatus(StatusCodeType.SUCCESS);
        }catch (Exception e) {
            rt.setStatus(StatusCodeType.FAILURE);
        }
 		println ("End addTask");
        return rt;
    }
}

