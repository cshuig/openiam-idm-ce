import org.openiam.connector.common.command.AbstractCommand
import org.openiam.connector.type.ConnectorDataException
import org.openiam.connector.type.ObjectValue
import org.openiam.connector.type.constant.StatusCodeType
import org.openiam.connector.type.request.LookupRequest
import org.openiam.connector.type.request.RequestType
import org.openiam.connector.type.response.ResponseType
import org.openiam.connector.type.response.SearchResponse
import org.openiam.provision.type.ExtensibleAttribute

class LookupScriptConnector extends AbstractCommand<LookupRequest, SearchResponse>{

    @Override
    public SearchResponse execute(LookupRequest request) throws ConnectorDataException {
        println ("call LOOKUP");
        SearchResponse rt = new SearchResponse();
        //GET ALL FIELDS FROM OBJECT
        String search = request?.searchValue;
        println (search);

        try{
            //START BODY
            String fileLocation = "/tmp/users/"+search;
            File f = new File(fileLocation);
            if (f.exists()){
                List<ObjectValue> obList = new ArrayList<ObjectValue>();
                ObjectValue ob = new ObjectValue();
                FileReader reader = new FileReader(f);
                ob.setObjectIdentity(f.getName());
                List<ExtensibleAttribute> attrList = new ArrayList<ExtensibleAttribute>();
                for (String line:   reader?.readLines()){
                    String[] name_val = line.split('=');
                    ExtensibleAttribute a = new ExtensibleAttribute(name_val[0], name_val[1]);
                    attrList.add(a);
                }
                ob.setAttributeList(attrList);
                obList.add(ob);
                rt.setObjectList(obList);
                rt.setStatus(StatusCodeType.SUCCESS);
            }else {
                rt.setStatus(StatusCodeType.FAILURE);
            }
        }catch (Exception e)
        {
            rt.setStatus(StatusCodeType.FAILURE);
        }

		 println ("END LOOKUP");
        return rt;
    }
}

