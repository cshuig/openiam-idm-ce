use openiam;

INSERT INTO REPORT_PARAMETER_METATYPE (PARAM_METATYPE_ID, PARAM_METATYPE_NAME, IS_MULTIPLE) 
	VALUES  ('ACTION','Audit action','Y'),('GROUP','Group','Y'),('ORGANIZATION','Organization','N'),
		('ROLE','Role','Y'),('USER','User','Y'),('USER_STATUS','User status','Y'),
		('USER_SEC_STATUS','User secondary status','Y');
