package org.openiam.ui.rest.client.factory;

import org.openiam.ui.rest.client.TranslateAPIClient;
import org.openiam.ui.rest.constant.ClientProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TranslateApiClientFactory extends AbstractAPIClientFactory<TranslateAPIClient>{
    @Autowired
    @Qualifier("googleTranslateClient")
    private TranslateAPIClient googleApiClient;

    @Override
    public TranslateAPIClient getApiClient(ClientProvider provider) throws Exception {
        switch (provider)   {
            case Google:
                return googleApiClient;
            default:
                throw new UnsupportedOperationException("Unsupported API provider: " + provider);
        }
    }
}
