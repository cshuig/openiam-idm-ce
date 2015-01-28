package org.openiam.ui.web.model;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.openiam.am.srvc.dto.ContentProvider;
import org.openiam.ui.util.URIUtils;
import org.openid4java.server.InMemoryServerAssociationStore;
import org.openid4java.server.ServerManager;

public class ServerManagerWrapper {
	
	private static Logger LOG = Logger.getLogger(ServerManagerWrapper.class);

	private ServerManager httpManager;
	private ServerManager httpsManager;
	private int contentProviderHashcode;
	
	public ServerManagerWrapper(final ContentProvider cp, final String openidPath) {
		httpManager = createServerManager(cp, "http", openidPath);
		httpsManager = createServerManager(cp, "https", openidPath);
		contentProviderHashcode = cp.hashCode(); //avoid holding a reference to the CP
	}
	
	private ServerManager createServerManager(final ContentProvider cp, final String scheme, final String openidPath) {
		final ServerManager manager = new ServerManager();
		final String domainPattern = cp.getDomainPattern();
		final String endpointURL = new StringBuilder(scheme).append("://").append(domainPattern).append(openidPath).toString();
		manager.setOPEndpointUrl(endpointURL);
		manager.setPrivateAssociations(new InMemoryServerAssociationStore());
		manager.setSharedAssociations(new InMemoryServerAssociationStore());
		return manager;
	}
	
	public ServerManager getServerManager(final HttpServletRequest request) {
		try {
			final String proxySchemeHeader = URIUtils.getProxySchemeHeader(request);
			String scheme = (StringUtils.isNotBlank(proxySchemeHeader)) ? proxySchemeHeader : new URI(request.getRequestURL().toString()).getScheme();
			scheme = (scheme != null) ? scheme.toLowerCase() : null;
			return "https".equals(scheme) ? httpsManager : httpManager;
		} catch(Throwable e) {
			LOG.error("can't get server manager", e);
			return null;
		}
	}
	
	public boolean isModified(final ContentProvider cp) {
		return contentProviderHashcode != cp.hashCode();
	}
}
