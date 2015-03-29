import java.util.List;

import org.openiam.idm.srvc.meta.dto.MetadataType;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.ui.idp.saml.groovy.AbstractJustInTimeSAMLAuthenticator;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.xml.schema.XSString;
import org.opensaml.xml.schema.XSInteger;
import org.apache.commons.collections.CollectionUtils;

class TestJustInTimeSAMLAuthenticator extends AbstractJustInTimeSAMLAuthenticator {
	
	@Override
	protected MetadataType getEmailType(final List<MetadataType> types) {
		return types.get(0);
	}
	
	@Override
	protected String getFirstName() {
		if(CollectionUtils.isNotEmpty(response.getAssertions()) && CollectionUtils.isNotEmpty(response.getAssertions().get(0).getAttributeStatements())) {
			for(final Attribute attribute : response.getAssertions().get(0).getAttributeStatements().get(0).getAttributes()) {
				if(attribute.getName().equals("urn:openiam:names:attribute:firstName")) {
					return ((XSString)attribute.getAttributeValues().get(0)).getValue();
				}
			}
		}
		return null;
	}
	
	@Override
	protected String getLastName() {
		if(CollectionUtils.isNotEmpty(response.getAssertions()) && CollectionUtils.isNotEmpty(response.getAssertions().get(0).getAttributeStatements())) {
			for(final Attribute attribute : response.getAssertions().get(0).getAttributeStatements().get(0).getAttributes()) {
				if(attribute.getName().equals("urn:openiam:names:attribute:lastName")) {
					return ((XSString)attribute.getAttributeValues().get(0)).getValue();
				}
			}
		}
		return null;
	}
	
	protected String getEmail() {
		if(CollectionUtils.isNotEmpty(response.getAssertions()) && CollectionUtils.isNotEmpty(response.getAssertions().get(0).getAttributeStatements())) {
			for(final Attribute attribute : response.getAssertions().get(0).getAttributeStatements().get(0).getAttributes()) {
				if(attribute.getName().equals("urn:openiam:names:attribute:email")) {
					return ((XSString)attribute.getAttributeValues().get(0)).getValue();
				}
			}
		}
		return null;
	}
	
	protected void populate(final User user) {
			
	}
}