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
        

		rt.setStatus(StatusCodeType.SUCCESS);
		println ("end search Task");
        return rt;
    }
}

