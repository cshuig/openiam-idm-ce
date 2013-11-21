set define off;

insert into openiam.STATUS ( CODE_GROUP, status_cd, LANGUAGE_CD, status_type, description, COMPANY_OWNER_ID, SERVICE_ID) values( 'USER_2ND_STATUS', 'LOCKED','en','String','LOCKED','100', 'IDM');
insert into openiam.STATUS ( CODE_GROUP, status_cd, LANGUAGE_CD, status_type, description, COMPANY_OWNER_ID, SERVICE_ID) values( 'USER_2ND_STATUS', 'LOCKED_ADMIN','en','String','LOCKED_ADMIN','100', 'IDM');
insert into openiam.STATUS ( CODE_GROUP, status_cd, LANGUAGE_CD, status_type, description, COMPANY_OWNER_ID, SERVICE_ID) values( 'USER_2ND_STATUS', 'DISABLED','en','String','DISABLED','100', 'IDM');

/* USER EMPLOYMENT TYPE*/
insert into openiam.STATUS ( CODE_GROUP, status_cd, LANGUAGE_CD, status_type, description, COMPANY_OWNER_ID, SERVICE_ID) values( 'USER_TYPE', 'PERM FULL TIME','en','String','PERMANENT FULL TIME','100', 'USR_SEC_DOMAIN');
insert into openiam.STATUS ( CODE_GROUP, status_cd, LANGUAGE_CD, status_type, description, COMPANY_OWNER_ID, SERVICE_ID) values( 'USER_TYPE', 'PERM PART TIME','en','String','PERMANENT PART TIME','100', 'USR_SEC_DOMAIN');
insert into openiam.STATUS ( CODE_GROUP, status_cd, LANGUAGE_CD, status_type, description, COMPANY_OWNER_ID, SERVICE_ID) values( 'USER_TYPE', 'CONTRACTOR','en','String','CONTRACTOR','100', 'USR_SEC_DOMAIN');
insert into openiam.STATUS ( CODE_GROUP, status_cd, LANGUAGE_CD, status_type, description, COMPANY_OWNER_ID, SERVICE_ID) values( 'USER_TYPE', 'VENDOR','en','String','VENDOR','100', 'USR_SEC_DOMAIN');
insert into openiam.STATUS ( CODE_GROUP, status_cd, LANGUAGE_CD, status_type, description, COMPANY_OWNER_ID, SERVICE_ID) values( 'USER_TYPE', 'AFFILIATE','en','String','AFFILIATE','100', 'USR_SEC_DOMAIN');
insert into openiam.STATUS ( CODE_GROUP, status_cd, LANGUAGE_CD, status_type, description, COMPANY_OWNER_ID, SERVICE_ID) values( 'USER_TYPE', 'SYS ACCOUNT','en','String','SYSTEM ACCOUNT','100', 'USR_SEC_DOMAIN');

insert into openiam.STATUS (CODE_GROUP, status_cd, LANGUAGE_CD, status_type, description, COMPANY_OWNER_ID, SERVICE_ID) values('OBJECT_TYPE', 'ACCNT','en','String','Account','100', 'IDM');
insert into openiam.STATUS (CODE_GROUP, status_cd, LANGUAGE_CD, status_type, description, COMPANY_OWNER_ID, SERVICE_ID) values('OBJECT_TYPE', 'ADMIN','en','String','Administrator','100', 'IDM');
insert into openiam.STATUS (CODE_GROUP, status_cd, LANGUAGE_CD, status_type, description, COMPANY_OWNER_ID, SERVICE_ID) values('OBJECT_TYPE', 'ADMGP','en','String','Admin Group','100', 'IDM');
insert into openiam.STATUS (CODE_GROUP, status_cd, LANGUAGE_CD, status_type, description, COMPANY_OWNER_ID, SERVICE_ID) values('OBJECT_TYPE', 'ATTR','en','String','Attribute','100', 'IDM');


commit;