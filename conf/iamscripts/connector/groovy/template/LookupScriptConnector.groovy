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


		rt.setStatus(StatusCodeType.FAILURE);
		 println ("END LOOKUP");
        return rt;
    }
}

