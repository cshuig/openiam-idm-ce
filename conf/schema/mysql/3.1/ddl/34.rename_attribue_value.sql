use openiam;

ALTER TABLE RESOURCE_PROP CHANGE VALUE _VALUE VARCHAR(4096);
ALTER TABLE GRP_ATTRIBUTES CHANGE VALUE _VALUE VARCHAR(4096);
ALTER TABLE ROLE_ATTRIBUTE CHANGE VALUE _VALUE VARCHAR(4096);
ALTER TABLE COMPANY_ATTRIBUTE CHANGE VALUE _VALUE VARCHAR(4096);