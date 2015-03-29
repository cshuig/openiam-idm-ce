use openiam;

INSERT INTO AUTH_ATTRIBUTE (AUTH_ATTRIBUTE_ID, ATTRIBUTE_NAME, PROVIDER_TYPE, DESCRIPTION, REQUIRED, DATA_TYPE)
	VALUES('SamlSpJITGroovyScript', 'Just-in-time provisioning groovy script', 'SAML_SP_PROVIDER', 'This groovy script controls just-in-time provisioning.', 'N', 'singleValue');
	