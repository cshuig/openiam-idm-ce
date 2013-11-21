set define off;

insert into openiam.SERVICE (SERVICE_ID, SERVICE_NAME, STATUS) values('USR_SEC_DOMAIN','USER SECURITY DOMAIN','ON-LINE');

insert into openiam.LANGUAGE (LANGUAGE_CD, LANGUAGE) VALUES ('en','English');


insert into openiam.METADATA_TYPE(TYPE_ID, DESCRIPTION) values('OrgOpenIAM','OpenIAM');
insert into openiam.METADATA_TYPE(TYPE_ID, DESCRIPTION, SYNC_MANAGED_SYS) values('MANAGED_SYS','Managed System', 0);
insert into openiam.METADATA_TYPE(TYPE_ID, DESCRIPTION, SYNC_MANAGED_SYS) values('SYS_ACTION','System Actions', 0);
insert into openiam.METADATA_TYPE(TYPE_ID, DESCRIPTION, SYNC_MANAGED_SYS) values('WORKFLOW','Workflow', 0);
insert into openiam.METADATA_TYPE(TYPE_ID, DESCRIPTION, SYNC_MANAGED_SYS) values('AUTH_REPO','Authentication Repository', 0);

insert into openiam.METADATA_TYPE(TYPE_ID, DESCRIPTION, SYNC_MANAGED_SYS) values('AD_GROUP_TYPE','AD GROUP TYPE', 0);
insert into openiam.METADATA_TYPE(TYPE_ID, DESCRIPTION, SYNC_MANAGED_SYS) values('LDAP_GROUP_TYPE','LDAP GROUP TYPE', 0);
insert into openiam.METADATA_TYPE(TYPE_ID, DESCRIPTION, SYNC_MANAGED_SYS) values('DEFAULT_GROUP','DEFAULT GROUP TYPE', 0);

insert into openiam.METADATA_TYPE(TYPE_ID, DESCRIPTION, SYNC_MANAGED_SYS) values('IDM_GOBAL_ROLE','DEFAULT IDM ROLES', 0);
insert into openiam.METADATA_TYPE(TYPE_ID, DESCRIPTION, SYNC_MANAGED_SYS) values('ORA_ROLE','ORACLE DB ROLE', 0);
insert into openiam.METADATA_TYPE(TYPE_ID, DESCRIPTION, SYNC_MANAGED_SYS) values('PEOPLESOFT_ROLE','PEOPLESOFT ROLE', 0);

insert into openiam.METADATA_TYPE(TYPE_ID, DESCRIPTION, SYNC_MANAGED_SYS) values('APP_MENU','MENU', 0);


insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name) values ('4011','MANAGED_SYS','SUBMIT_USER_TO_CONNECTOR');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name) values ('4021','MANAGED_SYS','INCLUDE_IN_PASSWORD_SYNC');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name) values ('406','MANAGED_SYS','TABLE_NAME');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name) values ('405','MANAGED_SYS','INCLUDE_IN_SYNC');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name) values ('408','MANAGED_SYS','ON_DELETE');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name) values ('407','MANAGED_SYS','GROUP_MEMBERSHIP_ENABLED');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name) values ('409','MANAGED_SYS','PRE_PROCESS');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name) values ('410','MANAGED_SYS','POST_PROCESS');

insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name) values ('411','AUTH_REPO','HOST_URL');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name) values ('412','AUTH_REPO','BASE_DN');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name) values ('413','AUTH_REPO','HOST_LOGIN ID');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name) values ('421','AUTH_REPO','PASSWORD');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name) values ('415','AUTH_REPO','COMMUNICATION_PROTOCOL');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name) values ('416','AUTH_REPO','OBJECT_CLASS');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name) values ('418','AUTH_REPO','SEARCH_ATTRIBUTE');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name) values ('419','AUTH_REPO','MANAGED_SYS_ID');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name) values ('420','AUTH_REPO','DN_ATTRIBUTE');

/* AD GROUP DEFINITION */

insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name) values ('450','AD_GROUP_TYPE','DN');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name) values ('451','AD_GROUP_TYPE','CN');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name) values ('452','AD_GROUP_TYPE','sAMAccountName');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name) values ('453','AD_GROUP_TYPE','objectClass');

/* LDAP GROUP DEFINITION */

insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name) values ('470','LDAP_GROUP_TYPE','dn');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name) values ('471','LDAP_GROUP_TYPE','uid');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name) values ('472','LDAP_GROUP_TYPE','objectClass');


insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name) values ('430','WORKFLOW','LAUNCH_URL');



insert into openiam.CATEGORY_TYPE (category_id, type_id) values('GROUP_TYPE','AD_GROUP_TYPE');
insert into openiam.CATEGORY_TYPE (category_id, type_id) values('GROUP_TYPE','LDAP_GROUP_TYPE');
insert into openiam.CATEGORY_TYPE (category_id, type_id) values('GROUP_TYPE','DEFAULT_GROUP');


insert into openiam.CATEGORY_TYPE (category_id, type_id) values('ROLE_TYPE','IDM_GOBAL_ROLE');
insert into openiam.CATEGORY_TYPE (category_id, type_id) values('ROLE_TYPE','ORA_ROLE');
insert into openiam.CATEGORY_TYPE (category_id, type_id) values('ROLE_TYPE','PEOPLESOFT_ROLE');



insert into openiam.METADATA_TYPE(TYPE_ID, DESCRIPTION,SYNC_MANAGED_SYS) values('SystemAccount','System Account type',1);
insert into openiam.METADATA_TYPE(TYPE_ID, DESCRIPTION,SYNC_MANAGED_SYS) values('InetOrgPerson','InetOrgPerson user type',1);
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name,SELF_EDITABLE, SELF_VIEWABLE, UI_TYPE,UI_OBJECT_SIZE) values ('101','InetOrgPerson', 'Display Name',1,1,'TEXT','size=20');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name,SELF_EDITABLE, SELF_VIEWABLE, UI_TYPE,UI_OBJECT_SIZE) values ('104','InetOrgPerson', 'Preferred Language',1,1,'TEXT','size=20');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name,SELF_EDITABLE, SELF_VIEWABLE, UI_TYPE,UI_OBJECT_SIZE) values ('105','InetOrgPerson', 'VehicleLicense',1,1,'TEXT','size=20');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name,SELF_EDITABLE, SELF_VIEWABLE, UI_TYPE,UI_OBJECT_SIZE) values ('106','InetOrgPerson', 'Given Name',1,1,'TEXT','size=20');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name,SELF_EDITABLE, SELF_VIEWABLE, UI_TYPE,UI_OBJECT_SIZE) values ('107','InetOrgPerson', 'LabeledURI',1,1,'TEXT','size=20');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name,SELF_EDITABLE, SELF_VIEWABLE, UI_TYPE,UI_OBJECT_SIZE) values ('108','InetOrgPerson', 'Initials',1,1,'TEXT','size=20');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name,SELF_EDITABLE, SELF_VIEWABLE, UI_TYPE,UI_OBJECT_SIZE) values ('110','InetOrgPerson', 'BusinessCategory',1,1,'TEXT','size=20');

insert into openiam.METADATA_TYPE(TYPE_ID, DESCRIPTION,SYNC_MANAGED_SYS) values('Contractor','Contractor user type',1);
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name,SELF_EDITABLE, SELF_VIEWABLE, UI_TYPE,UI_OBJECT_SIZE) values ('131','Contractor', 'Display Name',1,1,'TEXT','size=20');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name,SELF_EDITABLE, SELF_VIEWABLE, UI_TYPE,UI_OBJECT_SIZE) values ('134','Contractor', 'Preferred Language',1,1,'TEXT','size=20');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name,SELF_EDITABLE, SELF_VIEWABLE, UI_TYPE,UI_OBJECT_SIZE) values ('135','Contractor', 'VehicleLicense',1,1,'TEXT','size=20');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name,SELF_EDITABLE, SELF_VIEWABLE, UI_TYPE,UI_OBJECT_SIZE) values ('136','Contractor', 'Given Name',1,1,'TEXT','size=20');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name,SELF_EDITABLE, SELF_VIEWABLE, UI_TYPE,UI_OBJECT_SIZE) values ('137','Contractor', 'LabeledURI',1,1,'TEXT','size=20');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name,SELF_EDITABLE, SELF_VIEWABLE, UI_TYPE,UI_OBJECT_SIZE) values ('138','Contractor', 'Initials',1,1,'TEXT','size=20');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name,SELF_EDITABLE, SELF_VIEWABLE, UI_TYPE,UI_OBJECT_SIZE) values ('260','Contractor', 'BusinessCategory',1,1,'TEXT','size=20');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name,SELF_EDITABLE, SELF_VIEWABLE, UI_TYPE,UI_OBJECT_SIZE) values ('261','Contractor', 'StartDate',1,1,'TEXT','size=20');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name,SELF_EDITABLE, SELF_VIEWABLE, UI_TYPE,UI_OBJECT_SIZE) values ('262','Contractor', 'EndDate',1,1,'TEXT','size=20');


insert into openiam.METADATA_TYPE(TYPE_ID, DESCRIPTION) values('DIRECTORY','Directory');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name) values ('140','DIRECTORY', 'Display Name');


insert into openiam.METADATA_TYPE(TYPE_ID, DESCRIPTION) values('FILE','File');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name) values ('150','FILE', 'Display Name');


insert into openiam.METADATA_TYPE(TYPE_ID, DESCRIPTION) values('URL','URL');
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name) values ('160','URL', 'Display Name');



insert into openiam.METADATA_TYPE(TYPE_ID, DESCRIPTION) values('SCRIPT_Connector','Script Connector');
insert into openiam.METADATA_TYPE(TYPE_ID, DESCRIPTION) values('GOOGLE_Connector','GOOGLE APPS Connector');
insert into openiam.METADATA_TYPE(TYPE_ID, DESCRIPTION) values('LINUX_Connector','Linux Connector');


insert into openiam.METADATA_TYPE(TYPE_ID, DESCRIPTION, SYNC_MANAGED_SYS) values('WEB-APP','WEB Apps', 0);
insert into openiam.METADATA_ELEMENT(metadata_id, type_id, attribute_name) values ('WEB-APP1','WEB-APP','PROXY_URL');

INSERT INTO openiam.RESOURCE_TYPE (RESOURCE_TYPE_ID, DESCRIPTION, METADATA_TYPE_ID, PROVISION_RESOURCE) VALUES('WEB-APP', 'Web Application', 'WEB-APP', 0);


UPDATE openiam.METADATA_TYPE
SET ACTIVE = 1, SYNC_MANAGED_SYS = 1;

insert into openiam.COMPANY(company_id, company_name, status, type_ID, DOMAIN_NAME, CLASSIFICATION) values('100','OpenIAM', 'ACTIVE', 'OrgOpenIAM','openiam.com', 'ORGANIZATION');

insert into openiam.CATEGORY (category_id, parent_id, category_name, show_list) values ('RESOURCE_TYPE', 'ROOT', 'Resource Types',0);



insert into openiam.CATEGORY_TYPE (category_id, type_ID) values('USER_TYPE','InetOrgPerson');
insert into openiam.CATEGORY_TYPE (category_id, type_ID) values('USER_TYPE','Contractor');
insert into openiam.CATEGORY_TYPE (category_id, type_ID) values('USER_TYPE','SystemAccount');

insert into openiam.CATEGORY_TYPE (category_id, type_ID) values('CONNECTOR_TYPE','SCRIPT_Connector');
insert into openiam.CATEGORY_TYPE (category_id, type_ID) values('CONNECTOR_TYPE','LINUX_Connector');

insert into openiam.CATEGORY_TYPE (category_id, type_ID) values('ACL','DIRECTORY');
insert into openiam.CATEGORY_TYPE (category_id, type_id) values('ACL','FILE');
insert into openiam.CATEGORY_TYPE (category_id, type_id) values('ACL','URL');

insert into openiam.CATEGORY_TYPE (category_id, type_ID) values('RESOURCE_TYPE','AUTH_REPO');
insert into openiam.CATEGORY_TYPE (category_id, type_ID) values('RESOURCE_TYPE','MANAGED_SYS');
insert into openiam.CATEGORY_TYPE (category_id, type_ID) values('RESOURCE_TYPE','SYS_ACTION');



update openiam.CATEGORY
SET DISPLAY_ORDER = 0, SHOW_LIST = 0
WHERE DISPLAY_ORDER IS NULL OR SHOW_LIST IS NULL;

commit;
