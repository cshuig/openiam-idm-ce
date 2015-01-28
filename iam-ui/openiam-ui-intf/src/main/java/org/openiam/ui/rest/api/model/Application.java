package org.openiam.ui.rest.api.model;

/**
 * @author lbornov2
 * This is a wrapper around a Resource, and represents the concept of an 'Application'.
 * An application can be anything from a Content Provider, a Managed System, or an
 * Authentication Provider.  The id comes from the Resource, while the name comes from
 * the provider it represents (Content Provider, Managed System, AUthentication Provider, etc)
 *
 */
public class Application extends KeyNameBean {

	private String url;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
