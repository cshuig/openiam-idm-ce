package org.openiam.ui.idp.saml.boostrap;

import org.opensaml.DefaultBootstrap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class SAMLBootstrap implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		DefaultBootstrap.bootstrap();
	}

}
