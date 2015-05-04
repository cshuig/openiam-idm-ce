use openiam;

INSERT INTO AUTH_ATTRIBUTE (AUTH_ATTRIBUTE_ID, ATTRIBUTE_NAME, PROVIDER_TYPE, DESCRIPTION, REQUIRED, DATA_TYPE)
	VALUES('SpNameQualifierForSP', 'SP Name Qualifier', 'SAML_SP_PROVIDER', 'This is the SP Name Qualifier inside of the NameIdPolicy', 'N', 'singleValue');
	
	
INSERT INTO AUTH_ATTRIBUTE (AUTH_ATTRIBUTE_ID, ATTRIBUTE_NAME, PROVIDER_TYPE, DESCRIPTION, REQUIRED, DATA_TYPE)
	VALUES('SpAuthnRequestDestination', 'Destination attribute enabled', 'SAML_SP_PROVIDER', 'Does the AuthnRequest has a Destination attribute?  If true, it will be set to the value of the ACS of this Service Provider', 'N', 'booleanValue');
	
INSERT INTO AUTH_ATTRIBUTE (AUTH_ATTRIBUTE_ID, ATTRIBUTE_NAME, PROVIDER_TYPE, DESCRIPTION, REQUIRED, DATA_TYPE)
	VALUES('SpNameIdAllowCreate', 'AllowCreate on the NameIdPolicy', 'SAML_SP_PROVIDER', 'Sets the value of the AllowCreate attribute on the NameIdPolicy to true, or false', 'N', 'booleanValue');
	
INSERT INTO AUTH_ATTRIBUTE (AUTH_ATTRIBUTE_ID, ATTRIBUTE_NAME, PROVIDER_TYPE, DESCRIPTION, REQUIRED, DATA_TYPE)
	VALUES('SpAuthnContextClassRef', 'AuthnContextClassRef element value', 'SAML_SP_PROVIDER', 'If present, the AuthnRequest will contain a RequestedAuthnContext element with a AuthnContextClassRef', 'N', 'singleValue');
		

INSERT INTO AUTH_ATTRIBUTE (AUTH_ATTRIBUTE_ID, ATTRIBUTE_NAME, PROVIDER_TYPE, DESCRIPTION, REQUIRED, DATA_TYPE)
	VALUES('SamlRequestIssuerFormat', 'Issuer Format', 'SAML_SP_PROVIDER', 'The Format of the AuthnRequest Issuer', 'N', 'singleValue');