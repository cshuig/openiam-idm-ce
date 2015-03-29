package org.openiam.ui.idp.saml.groovy;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.openiam.base.AttributeOperationEnum;
import org.openiam.idm.searchbeans.MetadataTypeSearchBean;
import org.openiam.idm.srvc.auth.dto.Login;
import org.openiam.idm.srvc.continfo.dto.EmailAddress;
import org.openiam.idm.srvc.meta.domain.MetadataTypeGrouping;
import org.openiam.idm.srvc.meta.dto.MetadataType;
import org.openiam.idm.srvc.meta.ws.MetadataWebService;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.ui.idp.saml.provider.SAMLServiceProvider;
import org.openiam.ui.util.SpringContextProvider;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.impl.AttributeImpl;
import org.opensaml.xml.schema.XSInteger;

public abstract class AbstractJustInTimeSAMLAuthenticator {
	
	protected Response response;
	protected NameID nameId;
	private SAMLServiceProvider serviceProvider;
	

    @Resource(name = "metadataServiceClient")
    private MetadataWebService metadataWebService;
	
	public AbstractJustInTimeSAMLAuthenticator() {
		SpringContextProvider.autowire(this);
		SpringContextProvider.resolveProperties(this);
	}
	
	public final void init(final Response response, final NameID nameId, final SAMLServiceProvider serviceProvider) {
		this.response = response;
		this.nameId = nameId;
		this.serviceProvider = serviceProvider;
	}

	public final User createUser() {
		final User user = new User();
		user.setFirstName(getFirstName());
		user.setLastName(getLastName());
		populateLogin(user);
		populateEmailAddresses(user);
		populate(user);
		if(CollectionUtils.isEmpty(user.getResources())) {
			user.setResources( new HashSet<org.openiam.idm.srvc.res.dto.Resource>());
		}
		final org.openiam.idm.srvc.res.dto.Resource spResource = new org.openiam.idm.srvc.res.dto.Resource();
		spResource.setId(serviceProvider.getResource().getId());
		spResource.setName(serviceProvider.getResource().getName());
		spResource.setResourceType(serviceProvider.getResource().getResourceType());
		spResource.setOperation(AttributeOperationEnum.ADD);
		user.getResources().add(spResource);
		return user;
	}
	
	private void populateEmailAddresses(final User user) {
		final Set<EmailAddress> emailAddresses = new HashSet<EmailAddress>();
		final EmailAddress emailAddress = new EmailAddress();
		emailAddress.setEmailAddress(getEmail());
		emailAddress.setMetadataTypeId(getEmailType().getId());
		emailAddresses.add(emailAddress);
		user.setEmailAddresses(emailAddresses);
	}
	
	private void populateLogin(final User user) {
		final List<Login> principalList = new LinkedList<Login>();
		final Login principal = new Login();
		principal.setManagedSysId(serviceProvider.getManagedSysId());
		principal.setLogin(getPrincipal());
		principalList.add(principal);
		user.setPrincipalList(principalList);
	}
	
	private MetadataType getEmailType() {
		final MetadataTypeSearchBean searchBean = new MetadataTypeSearchBean();
    	searchBean.setGrouping(MetadataTypeGrouping.EMAIL);
    	searchBean.setActive(true);
        final List<MetadataType> types = metadataWebService.findTypeBeans(searchBean, 0, Integer.MAX_VALUE, null);
        final MetadataType type = getEmailType(types);
        if(type == null) {
        	throw new RuntimeException("Implementing groovy script must return a non-null value from getEmailType()");
        }
        return type;
	}
	
	/**
	 * The caller must specify the type of email to be stored in the database.  The return value must be non-null.
	 * @param types - all possible email types.  The return value msut be one of these
	 * @return the Email Type
	 */
	protected abstract MetadataType getEmailType(final List<MetadataType> types);
	
	/**
	 * To be implemented by the caller.  Must return the email address of the user in the SAML Response.
	 * @return
	 */
	protected abstract String getFirstName();
	
	/**
	 * To be implemented by the caller.  Must return the email address of the user in the SAML Response.
	 * @return
	 */
	protected abstract String getLastName();
	
	/**
	 * To be implemented by the caller.  Must return the email address of the user in the SAML Response.
	 * @return
	 */
	protected abstract String getEmail();
	
	/**
	 * To be implemented by the caller.  Must return the principal of the user in the SAML Response.
	 * @return
	 */
	protected String getPrincipal() {
		return nameId.getValue();
	}
	
	/**
	 * The caller can optionally add custom code to populate/modify the User object.
	 * The caller can do things like add Roles to the User, give him distinct entitlements, set his middle name, etc.
	 * @param user
	 */
	protected void populate(final User user) {
		
	}
}
