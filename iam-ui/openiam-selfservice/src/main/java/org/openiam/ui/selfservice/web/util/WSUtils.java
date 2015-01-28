package org.openiam.ui.selfservice.web.util;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

public class WSUtils {
    /**
     * Set timeout for awaiting response from WS service
     *
     * ProvisionService used it
     *
     * @param wsService
     * @param timeout
     */
    public static void setWSClientTimeout(Object wsService, long timeout) {
        Client client = ClientProxy.getClient(wsService);
        if (client != null) {
            HTTPConduit conduit = (HTTPConduit) client.getConduit();
            HTTPClientPolicy policy = new HTTPClientPolicy();
            policy.setConnectionTimeout(timeout);
            policy.setReceiveTimeout(timeout);
            conduit.setClient(policy);
        }

    }
}
