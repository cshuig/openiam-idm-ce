package org.openiam.ui.rest.client.factory;

import org.openiam.ui.rest.client.RestAPIClient;
import org.openiam.ui.rest.constant.ClientProvider;

public abstract class AbstractAPIClientFactory<Client extends RestAPIClient> {
    public abstract Client getApiClient(ClientProvider provider) throws Exception;
}
