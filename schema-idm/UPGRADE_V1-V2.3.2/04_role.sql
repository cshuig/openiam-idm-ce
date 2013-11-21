set define off;

INSERT INTO openiam.ROLE (ROLE_ID,SERVICE_ID,ROLE_NAME) VALUES ('SUPER_SEC_ADMIN','IDM','Super Security Admin');
INSERT INTO openiam.ROLE (ROLE_ID,SERVICE_ID,ROLE_NAME) VALUES ('SEC_ADMIN','IDM','Security Admin');
INSERT INTO openiam.ROLE (ROLE_ID,SERVICE_ID,ROLE_NAME) VALUES ('END_USER','USR_SEC_DOMAIN','End User');
INSERT INTO openiam.ROLE (ROLE_ID,SERVICE_ID,ROLE_NAME) VALUES ('HELPDESK','USR_SEC_DOMAIN','Help Desk');
INSERT INTO openiam.ROLE (ROLE_ID,SERVICE_ID,ROLE_NAME) VALUES ('HR','USR_SEC_DOMAIN','Human Resource');
INSERT INTO openiam.ROLE (ROLE_ID,SERVICE_ID,ROLE_NAME) VALUES ('MANAGER','USR_SEC_DOMAIN','Manager');
INSERT INTO openiam.ROLE (ROLE_ID,SERVICE_ID,ROLE_NAME) VALUES ('SECURITY_MANAGER','USR_SEC_DOMAIN','Security Manager');
INSERT INTO openiam.ROLE (ROLE_ID,SERVICE_ID,ROLE_NAME) VALUES ('SEC_ADMIN','USR_SEC_DOMAIN','Security Admin');

INSERT INTO openiam.GRP (GRP_id, grp_name)   values('SUPER_SEC_ADMIN_GRP','Super Admin Group');
INSERT INTO openiam.GRP (grp_id, grp_name)   values('SEC_ADMIN_GRP','Sec Admin Group');
INSERT INTO openiam.GRP (grp_id, grp_name)   values('END_USER_GRP','End User Group');

INSERT INTO openiam.GRP (grp_id, grp_name)   values('HR_GRP','HR Group');
INSERT INTO openiam.GRP (grp_id, grp_name)   values('MNGR_GRP','Manager Group');
INSERT INTO openiam.GRP (grp_id, grp_name)   values('SECURITY_GRP','Security Group');

INSERT INTO openiam.USERS (user_id,first_name, last_name, STATUS, COMPANY_ID ) values('3000','sys','','ACTIVE','100');
INSERT INTO openiam.USERS (user_id,first_name, last_name, STATUS, COMPANY_ID ) values('3001','sys2','','ACTIVE','100');



INSERT INTO openiam.USER_GRP (USER_GRP_ID, grp_id, user_id) 	values('1000','SUPER_SEC_ADMIN_GRP','3000');
INSERT INTO openiam.USER_GRP (USER_GRP_ID,grp_id, user_id) 	values('1001','SUPER_SEC_ADMIN_GRP','3001');

INSERT INTO openiam.GRP_ROLE(ROLE_ID,GRP_ID, SERVICE_ID) VALUES ('SUPER_SEC_ADMIN','SUPER_SEC_ADMIN_GRP', 'IDM');
INSERT INTO openiam.GRP_ROLE(ROLE_ID,GRP_ID, SERVICE_ID) VALUES ('END_USER','END_USER_GRP', 'USR_SEC_DOMAIN');

insert into openiam.LOGIN(SERVICE_ID, LOGIN, MANAGED_SYS_ID, USER_ID, PASSWORD,RESET_PWD, IS_LOCKED, AUTH_FAIL_COUNT) VALUES('IDM','sysadmin','0','3000','b83a81d1b3f5f209a70ec02c3d7f7fc5',0,0,0);
insert into openiam.LOGIN(SERVICE_ID, LOGIN, MANAGED_SYS_ID, USER_ID, PASSWORD,RESET_PWD, IS_LOCKED, AUTH_FAIL_COUNT) VALUES('USR_SEC_DOMAIN','sysadmin','0','3000','b83a81d1b3f5f209a70ec02c3d7f7fc5',0,0,0);
insert into openiam.LOGIN(SERVICE_ID, LOGIN, MANAGED_SYS_ID, USER_ID, PASSWORD,RESET_PWD, IS_LOCKED, AUTH_FAIL_COUNT) VALUES('IDM','sysadmin2','0','3001','b83a81d1b3f5f209a70ec02c3d7f7fc5',0,0,0);

update openiam.LOGIN set reset_pwd = 0, is_locked = 0;

DELETE FROM openiam.AUTH_STATE;

INSERT INTO openiam.AUTH_STATE(AUTH_STATE_ID,USER_ID, TOKEN, AUTH_STATE, AA, EXPIRATION) values('100','3000', NULL,0,'OPENIAM',0);
INSERT INTO openiam.AUTH_STATE(AUTH_STATE_ID,USER_ID, TOKEN, AUTH_STATE, AA, EXPIRATION) values('101','3001', NULL,0,'OPENIAM',0);


/* Sequence Gen*/
insert into openiam.SEQUENCE_GEN (attribute, next_id)	values('CATEGORY_ID','3000');
insert into openiam.SEQUENCE_GEN (attribute, next_id)	values('METADATA_ELEMENT_ID','2000');
insert into openiam.SEQUENCE_GEN (attribute, next_id) values('DOMAIN_ID','1000');


commit;