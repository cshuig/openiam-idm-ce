import org.openiam.connector.common.command.AbstractCommand
import org.openiam.connector.type.ConnectorDataException
import org.openiam.connector.type.ObjectValue
import org.openiam.connector.type.constant.StatusCodeType
import org.openiam.connector.type.request.RequestType
import org.openiam.connector.type.request.SearchRequest
import org.openiam.connector.type.response.ResponseType
import org.openiam.connector.type.response.SearchResponse
import org.openiam.provision.type.ExtensibleAttribute

class SearchScriptConnector extends AbstractCommand<SearchRequest, SearchResponse>{

    @Override
    public SearchResponse execute(SearchRequest request) throws ConnectorDataException {
		println ("Call search Task");        
		SearchResponse rt = new SearchResponse();
        String fileLocation = "/tmp/users/";
        File f = new File(fileLocation);
        if (f.isDirectory()){
            File[] files = f.listFiles();
            List<ObjectValue> obList = new ArrayList<ObjectValue>();
            for (File f1 : files){
                ObjectValue ob = new ObjectValue();
                List<ExtensibleAttribute> attrList = new ArrayList<ExtensibleAttribute>();
                FileReader fr = new FileReader(f1);
                ob.setObjectIdentity(f1.getName());
                for (String line:   fr?.readLines()){
                    String[] name_val = line.split('=');
                    ExtensibleAttribute a = new ExtensibleAttribute(name_val[0], name_val[1]);
                    attrList.add(a);
                }
                ob.setAttributeList(attrList);

                obList.add(ob);
            }
            rt.setObjectList(obList);
            rt.setStatus(StatusCodeType.SUCCESS);
        }else {
            rt.setStatus(StatusCodeType.FAILURE);
        }
		println ("end search Task");
        return rt;
    }
}

