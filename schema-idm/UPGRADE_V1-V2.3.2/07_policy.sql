 set define off;

DELETE FROM openiam.POLICY_DEF_PARAM WHERE DEF_PARAM_ID IN ('135','136');

insert into openiam.POLICY_DEF_PARAM (DEF_PARAM_ID, POLICY_DEF_ID, NAME, DESCRIPTION, OPERATION, POLICY_PARAM_HANDLER,PARAM_GROUP) VALUES('171','100','QUEST_ANSWER_CORRECT','Number of answers that are required to be correct', null, null, null);


/* Authentication Policy */

insert into openiam.POLICY_DEF_PARAM (DEF_PARAM_ID, POLICY_DEF_ID, NAME, DESCRIPTION) VALUES('163','103','LOGIN_MOD_TYPE','Type of authentication module');
insert into openiam.POLICY_DEF_PARAM (DEF_PARAM_ID, POLICY_DEF_ID, NAME, DESCRIPTION) VALUES('164','103','AUTH_REPOSITORY','Authentication Repository');
insert into openiam.POLICY_DEF_PARAM (DEF_PARAM_ID, POLICY_DEF_ID, NAME, DESCRIPTION) VALUES('179','103','HOST_URL','URL of the host system');
insert into openiam.POLICY_DEF_PARAM (DEF_PARAM_ID, POLICY_DEF_ID, NAME, DESCRIPTION) VALUES('172','103','HOST_LOGIN','Login to the host');
insert into openiam.POLICY_DEF_PARAM (DEF_PARAM_ID, POLICY_DEF_ID, NAME, DESCRIPTION) VALUES('173','103','HOST_PASSWORD','Password to the host');
insert into openiam.POLICY_DEF_PARAM (DEF_PARAM_ID, POLICY_DEF_ID, NAME, DESCRIPTION) VALUES('174','103','BASEDN','Type of authentication module');
insert into openiam.POLICY_DEF_PARAM (DEF_PARAM_ID, POLICY_DEF_ID, NAME, DESCRIPTION) VALUES('175','103','PROTOCOL','Protocol');
insert into openiam.POLICY_DEF_PARAM (DEF_PARAM_ID, POLICY_DEF_ID, NAME, DESCRIPTION) VALUES('176','103','KEY_ATTRIBUTE','Name of the Primary Key Attribute');
insert into openiam.POLICY_DEF_PARAM (DEF_PARAM_ID, POLICY_DEF_ID, NAME, DESCRIPTION) VALUES('178','103','MANAGED_SYS_ID','Managed system Id');


update openiam.POLICY_DEF_PARAM
	set repeats = 0
where repeats is null;

/* Default Authn policy param */
insert into openiam.POLICY_ATTRIBUTE (POLICY_ATTR_ID, DEF_PARAM_ID, POLICY_ID, NAME, OPERATION,VALUE1)VALUES ('4111','163', '4001', 'LOGIN_MOD_TYPE', '','1');
insert into openiam.POLICY_ATTRIBUTE (POLICY_ATTR_ID, DEF_PARAM_ID, POLICY_ID, NAME, OPERATION,VALUE1)VALUES ('4112','164', '4001', 'AUTH_REPOSITORY', '',null);

/* Default Password policy param */
insert into openiam.POLICY_ATTRIBUTE (POLICY_ATTR_ID, DEF_PARAM_ID, POLICY_ID, NAME, OPERATION, VALUE1) VALUES ('4012', '120', '4000', 'DICTIONARY_CHECK', 'boolean', null);
insert into openiam.POLICY_ATTRIBUTE (POLICY_ATTR_ID, DEF_PARAM_ID, POLICY_ID, NAME, OPERATION, VALUE1) VALUES ('4013', '110', '4000', 'PWD_HIST_VER', null, 6);
insert into openiam.POLICY_ATTRIBUTE (POLICY_ATTR_ID, DEF_PARAM_ID, POLICY_ID, NAME, OPERATION, VALUE1) VALUES ('4015', '132', '4000', 'QUEST_LIST', null, null);
insert into openiam.POLICY_ATTRIBUTE (POLICY_ATTR_ID, DEF_PARAM_ID, POLICY_ID, NAME, OPERATION, VALUE1) VALUES ('4016', '130', '4000', 'QUEST_COUNT', null, 3);
insert into openiam.POLICY_ATTRIBUTE (POLICY_ATTR_ID, DEF_PARAM_ID, POLICY_ID, NAME, OPERATION, VALUE1) VALUES ('4017', '121', '4000', 'PWD_LOGIN', 'boolean', null);
insert into openiam.POLICY_ATTRIBUTE (POLICY_ATTR_ID, DEF_PARAM_ID, POLICY_ID, NAME, OPERATION, VALUE1) VALUES ('4019', '115', '4000', 'LOWERCASE_CHARS', 'RANGE', null);
insert into openiam.POLICY_ATTRIBUTE (POLICY_ATTR_ID, DEF_PARAM_ID, POLICY_ID, NAME, OPERATION, VALUE1) VALUES ('4020', '129', '4000', 'PWD_EXP_WARN', null, 2);
insert into openiam.POLICY_ATTRIBUTE (POLICY_ATTR_ID, DEF_PARAM_ID, POLICY_ID, NAME, OPERATION, VALUE1) VALUES ('4023', '111', '4000', 'PWD_EXPIRATION', null, 90);
insert into openiam.POLICY_ATTRIBUTE (POLICY_ATTR_ID, DEF_PARAM_ID, POLICY_ID, NAME, OPERATION, VALUE1) VALUES ('4054', '141', '4000', 'PWD_EXPIRATION_ON_RESET', null, 2);
insert into openiam.POLICY_ATTRIBUTE (POLICY_ATTR_ID, DEF_PARAM_ID, POLICY_ID, NAME, OPERATION, VALUE1) VALUES ('4050', '133', '4000', 'PWD_EXP_GRACE', null, 1);
insert into openiam.POLICY_ATTRIBUTE (POLICY_ATTR_ID, DEF_PARAM_ID, POLICY_ID, NAME, OPERATION, VALUE1) VALUES ('4051', '134', '4000', 'CHNG_PSWD_ON_RESET', null, 1);
insert into openiam.POLICY_ATTRIBUTE (POLICY_ATTR_ID, DEF_PARAM_ID, POLICY_ID, NAME, OPERATION, VALUE1) VALUES ('4055', '142', '4000', 'PASSWORD_CHANGE_ALLOWED', null, 5);
insert into openiam.POLICY_ATTRIBUTE (POLICY_ATTR_ID, DEF_PARAM_ID, POLICY_ID, NAME, OPERATION, VALUE1) VALUES ('4056', '170', '4000', 'REJECT_CHARS_IN_PSWD', null, '<>');
insert into openiam.POLICY_ATTRIBUTE (POLICY_ATTR_ID, DEF_PARAM_ID, POLICY_ID, NAME, OPERATION, VALUE1) VALUES ('4057', '171', '4000', 'QUEST_ANSWER_CORRECT', null, 3);

insert into openiam.POLICY_OBJECT_ASSOC (POLICY_OBJECT_ID, POLICY_ID, ASSOCIATION_LEVEL, ASSOCIATION_VALUE) VALUES ('1100', '4000', 'GLOBAL', 'GLOBAL');


insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT, RULE_SRC_URL) VALUES ('4501','104', 'cn', 'commonName', 1,sysdate, '3000', '','provision/cn.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT, RULE_SRC_URL) VALUES ('4502','104', 'mail', 'email address', 1,sysdate, '3000', '','provision/mail.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT, RULE_SRC_URL) VALUES ('4540','104', 'userDefineEmail', 'email address', 1,sysdate, '3000', '','provision/emailUserDefined.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4503','104', 'o', 'organization name', 1,sysdate, '3000', '','provision/o.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4504','104', 'ou', 'organizationalUnitName', 1,sysdate, '3000', '','provision/ou.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4505','104', 'postalCode', 'commonName', 1,sysdate, '3000', '','provision/postalCode.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4506','104', 'sn', 'surname', 1,sysdate, '3000', '','provision/sn.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4507','104', 'st', 'stateOrProvinceName', 1,sysdate, '3000', '','provision/st.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4508','104', 'street', 'streetAddress', 1,sysdate, '3000', '','provision/street.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4509','104', 'userPassword', 'password', 1,sysdate, '3000', '','provision/userPassword.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4510','104', 'postalAddress', 'postalAddress', 1,sysdate, '3000', '','provision/postalAddress.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4511','104', 'telephoneNumber', 'Primary Telephone', 1,sysdate, '3000', '','provision/telephone.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4512','104', 'facsimileTelephoneNumber', 'Fax', 1,sysdate, '3000', '','provision/fax.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4513','104', 'mobile', 'mobileTelephoneNumber', 1,sysdate, '3000', '','provision/mobile.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4514','104', 'gn', 'givenName', 1,sysdate, '3000', '','provision/gn.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4515','104', 'uid', 'User Id', 1,sysdate, '3000', '','provision/uid.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4516','104', 'departmentCD', 'Department Code', 1,sysdate, '3000', '','provision/deptcd.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4517','104', 'displayName', 'Display Name', 1,sysdate, '3000', '','provision/displayName.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4518','104', 'employeeType', 'Employee Type', 1,sysdate, '3000', '','provision/employeeType.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4519','104', 'initials', 'Intials', 1,sysdate, '3000', '','provision/initials.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4521','104', 'objectclass', 'Department Number', 1,sysdate, '3000', '','provision/objectclass.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4522','104', 'title', 'Title', 1,sysdate, '3000', '','provision/title.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4541','104', 'dob', 'Date of Birth', 1,sysdate, '3000', '','provision/dob.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4542','104', 'middleInit', 'Middle Initial', 1,sysdate, '3000', '','provision/middleInit.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4543','104', 'employeeId', 'Employee ID', 1,sysdate, '3000', '','provision/employeeId.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4544','104', 'userDefinedPassword', 'Password By User', 1,sysdate, '3000', '','provision/userDefPassword.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4545','104', 'userRole', 'Role Membership', 1,sysdate, '3000', '','provision/userRole.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4546','104', 'userGroup', 'Group Membership', 1,sysdate, '3000', '','provision/userGroup.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4547','104', 'isEnabled', 'Is the User Enabled', 1,sysdate, '3000', '','provision/isEnabled.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4548','104', 'GUID', 'GUID', 1,sysdate, '3000', '','provision/guid.groovy');



insert into openiam.POLICY(POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS,RULE_SRC_URL) VALUES('4562', '104', 'principal', 'PRIMARY_PRINICPAL', '1','provision/primary_principal.groovy');
insert into openiam.POLICY(POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS,RULE_SRC_URL) VALUES('4563', '104', 'password', 'PRIMARY_PASSWORD', '1','provision/primary_pswd.groovy');
insert into openiam.POLICY(POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS,RULE_SRC_URL) VALUES('4564', '104', 'emailAddress', 'PRIMARY_EMAIL', '1','provision/primary_email.groovy');
insert into openiam.POLICY(POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS,RULE_SRC_URL) VALUES('4565', '104', 'domain', 'PRIMARY_DOMAIN', '1','provision/primaryDomain.groovy');

/* Google Apps */
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4600','104', 'userName', 'Google User Name', 1,sysdate, '3000', '','provision/gapps/gappsUid.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4601','104', 'firstName', 'Google Fist Name', 1,sysdate, '3000', '','provision/gapps/gappsFirstName.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4602','104', 'lastName', 'Google Last Name', 1,sysdate, '3000', '','provision/gapps/gappsLastName.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4603','104', 'password', 'Google Password', 1,sysdate, '3000', '','provision/gapps/gappsPassword.groovy');

/* ACTIVE DIR Attributes*/
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS,   CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4632','104', 'ad-CN', 'CN', 1,  '3000', '','provision/ad/adCN.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS,   CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4630','104', 'ad-sAMAccountName', 'sAMAccountName', 1,  '3000', '','provision/ad/sAMAccountName.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS,   CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4631','104', 'ad-givenName', 'givenName', 1,  '3000', '','provision/gn.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS,   CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4633','104', 'ad-ou', 'ou', 1,  '3000', '','provision/ad/ou.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS,   CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4634','104', 'ad-objectclass', 'objectclass', 1,  '3000', '','provision/ad/objectclass.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS,   CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4635','104', 'ad-profilePath', 'profilePath', 1,  '3000', '','provision/ad/profilePath.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS,   CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4636','104', 'ad-homeDirectory', 'homeDirectory', 1,  '3000', '','provision/ad/homeDirectory.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS,   CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4637','104', 'ad-homeDrive', 'homeDrive', 1,  '3000', '','provision/ad/homeDrive.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS,   CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4639','104', 'ad-scriptPath', 'scriptPath', 1,  '3000', '','provision/ad/scriptPath.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS,   CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4640','104', 'ad-company', 'company', 1,  '3000', '','provision/ad/company.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS,   CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4642','104', 'ad-initials', 'initials', 1,  '3000', '','provision/ad/initials.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS,   CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4643','104', 'ad-department', 'department', 1,  '3000', '','provision/ad/department.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS,   CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4644','104', 'ad-telephoneNumber', 'telephoneNumber', 1,  '3000', '','provision/ad/telephoneNumber.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS,   CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4645','104', 'ad-title', 'title', 1,  '3000', '','provision/ad/title.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS,   CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4646','104', 'ad-unicodePwd', 'unicodePwd', 1,  '3000', '','provision/ad/unicodePwd.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS,   CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4650','104', 'ad-userAccountControl', 'userAccountControl', 1,  '3000', '','provision/ad/userAccountControl.groovy');

insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS,   CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4651','104', 'ad-userPrincipalName', 'userPrincipalName', 1,  '3000', '','provision/ad/userPrincipalName.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4652','104', 'ad-userGroup', 'Group Membership', 1,sysdate, '3000', '','provision/ad/userGroup.groovy');

insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4653','104', 'ad-streetAddress', 'Street Address', 1,sysdate, '3000', '','provision/ad/street.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4654','104', 'ad-city', 'City', 1,sysdate, '3000', '','provision/ad/city.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4655','104', 'ad-state', 'State', 1,sysdate, '3000', '','provision/ad/state.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4656','104', 'ad-zip', 'Zip', 1,sysdate, '3000', '','provision/ad/zip.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4657','104', 'manager', 'manager', 1,sysdate, '3000', '','provision/manager.groovy');
insert into openiam.POLICY (POLICY_ID, POLICY_DEF_ID, NAME, DESCRIPTION, STATUS, CREATE_DATE, CREATED_BY, RULE_TEXT,  RULE_SRC_URL) VALUES ('4658','104', 'ad-manager', 'ad-manager', 1,sysdate, '3000', '','provision/ad/manager.groovy');

commit;