use openiam;

IF OBJECT_ID('AUTH_LEVEL_ATTRIBUTE', 'U') IS NOT NULL
	DROP TABLE AUTH_LEVEL_ATTRIBUTE;

CREATE TABLE AUTH_LEVEL_ATTRIBUTE(
	AUTH_LEVEL_ATTRIBUTE_ID NVARCHAR(32) NOT NULL,
	AUTH_LEVEL_GROUPING_ID NVARCHAR(32) NOT NULL,
	TYPE_ID NVARCHAR(32) NOT NULL,
	NAME NVARCHAR(100) NOT NULL,
	VALUE_AS_BYTE_ARRAY VARBINARY(MAX) NULL,
	VALUE_AS_STRING NVARCHAR(32) NULL,
	PRIMARY KEY(AUTH_LEVEL_ATTRIBUTE_ID),
	CONSTRAINT AUTH_LEVEL_ATTR_GRP_FK FOREIGN KEY (AUTH_LEVEL_GROUPING_ID) REFERENCES AUTH_LEVEL_GROUPING(AUTH_LEVEL_GROUPING_ID),
	CONSTRAINT AUTH_LEVEL_ATTR_TYPE_FK FOREIGN KEY (TYPE_ID) REFERENCES METADATA_TYPE(TYPE_ID)
);


IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_catalog='openiam' AND table_name = 'METADATA_TYPE' AND column_name = 'IS_BINARY')
BEGIN
	ALTER TABLE METADATA_TYPE ADD IS_BINARY CHAR(1) DEFAULT 'N' NOT NULL;
END;
 

DELETE FROM METADATA_TYPE WHERE GROUPING IN('AUTH_LEVEL');
GO
INSERT INTO METADATA_TYPE (TYPE_ID, DESCRIPTION, ACTIVE, GROUPING, IS_BINARY) VALUES('KERBEROS_SERVICE_NAME', 'Kerberos Service Name', 'Y', 'AUTH_LEVEL', 'N');
INSERT INTO METADATA_TYPE (TYPE_ID, DESCRIPTION, ACTIVE, GROUPING, IS_BINARY) VALUES('KERBEROS_KEYTAB_FILE', 'Kerberos Keytab File', 'Y', 'AUTH_LEVEL', 'Y');