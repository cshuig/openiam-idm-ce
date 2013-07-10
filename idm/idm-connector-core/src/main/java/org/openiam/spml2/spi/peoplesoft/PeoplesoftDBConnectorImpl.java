package org.openiam.spml2.spi.peoplesoft;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openiam.connector.type.SearchRequest;
import org.openiam.connector.type.SearchResponse;
import org.openiam.spml2.spi.common.jdbc.AbstractJDBCConnectorImpl;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Implementation class for the Peoplesoft Connector
 */
@WebService(endpointInterface = "org.openiam.spml2.interf.ConnectorService",
        targetNamespace = "http://www.openiam.org/service/connector",
        portName = "PeoplesoftDbConnectorPort",
        serviceName = "PeoplesoftDbConnector")
public class PeoplesoftDBConnectorImpl  extends AbstractJDBCConnectorImpl {
    private static final Log log = LogFactory.getLog(PeoplesoftDBConnectorImpl.class);

    @Override
    public SearchResponse search(@WebParam(name = "searchRequest", targetNamespace = "") SearchRequest searchRequest) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
