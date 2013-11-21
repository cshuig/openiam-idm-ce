set define off;

DELETE FROM openiam.MENU;
delete from openiam.PERMISSIONS;

insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('ROOT', NULL ,'Root','Root', null, 'en',0);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('IDM','ROOT','Identity','IDENTITY MANAGER','security/index.jsp', 'en',1);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('IDMAN','IDM','User Admin','User Admin','idman/index.jsp', 'en',1);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('ACC_CONTROL','IDM','Access Control','Access Control','access/accessIndex.do', 'en',2);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, active, display_order) values('PROVISIONING','IDM','Provisioning','Provisioning','prov/provIndex.do', 'en',1,3);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, active, display_order) values('SECURITY_POLICY','IDM','Policy','Policy','security/policy.do?method=init&nav=reset', 'en',1,4);
insert into openiam.MENU (menu_id, menu_group, menu_name, menu_desc, url, LANGUAGE_CD, DISPLAY_ORDER) values( 'BIRT_REPORT', 'IDM', 'Reports', 'Reports', 'birtReportList.cnt', 'en', '5');
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('ADMIN','IDM','Administration','Administration','admin/index.jsp', 'en',6);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('USER','IDMAN','User','User','menunav.do', 'en',1);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('ORG','IDMAN','Organization','Organization','orglist.cnt', 'en',2);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('USER_BULK','IDMAN','User Bulk Ops','User Bulk Ops','userBulk.cnt', 'en',3);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('QUERYUSER','USER','Query','Query User','userSearch.cnt', 'en',1);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('ADDUSER','USER','Add','Add User','newUser.cnt', 'en',2);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('USERSUMMARY','QUERYUSER','User Details','User Details','editUser.cnt', 'en',1);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('USERATTR','QUERYUSER','Ext Attributes','Ext Attributes','userAttr.cnt', 'en',2);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('USERIDENTITY','QUERYUSER','Identities','User Identities','userIdentity.cnt', 'en',3);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('USERGROUP','QUERYUSER','Group','User Groups','userGroup.cnt', 'en',4);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('USERROLE','QUERYUSER','Role','User Role','userRole.cnt', 'en',5);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('USERORG','QUERYUSER','Affiliations','Affiliations','userOrg.cnt', 'en',6);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('USERHISTORY','QUERYUSER','History','User History','userHistory.cnt', 'en',7);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('USERPSWDRESET','QUERYUSER','Password Reset','Password Reset','resetPassword.cnt', 'en',8);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('DELG_FILTER','QUERYUSER','Delegation Filter','Delegation Filter','userDelegationFilter.cnt', 'en',9);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('USER_COMMENT','QUERYUSER','Comments','Comments','userNotes.cnt', 'en',10);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, active, display_order) values('SECURITY_GROUP','ACC_CONTROL','Group','Group','grouplist.cnt', 'en',1,1);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, active, display_order) values('SECURITY_ROLE','ACC_CONTROL','Role','Role','rolelist.cnt', 'en',1,2);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, active, display_order) values('SECURITY_RES','ACC_CONTROL','Resource','Resource','resourcelist.cnt', 'en',1,3);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('RESSUMMARY','SECURITY_RES','Resource Details','Resource Details','resourceDetail.cnt', 'en',1);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('RESPOLICYMAP','SECURITY_RES','Policy Map','Policy Map','resourceMap.cnt', 'en',2);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('RESAPPROVER','SECURITY_RES','Approval Flow','Approval Flow','resApprovalFlow.cnt', 'en',3);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('RESRECONCILE','SECURITY_RES','Reconciliation','Reconciliation','reconcilConfig.cnt', 'en',4);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('RESENTITLEMENT','SECURITY_RES','Entitlement','Entitlement','resourceEntitlement.cnt', 'en',5);

/* ROLE MENU */

insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('ROLE_SUMMARY','SECURITY_ROLE','Detail','Role Details','roleDetail.cnt', 'en',1);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('ROLE_RESMAP','SECURITY_ROLE','Resource Map','Resource Map','roleResource.cnt', 'en',2);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('ROLE_GRPMAP','SECURITY_ROLE','Group Map','Group Map','roleGroupMap.cnt', 'en',3);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('ROLE_MENUMAP','SECURITY_ROLE','Menu Map','Menu Map','roleMenuMap.cnt', 'en',4);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('ROLE_POLICY','SECURITY_ROLE','Policy','Role Policy','rolePolicy.cnt', 'en',5);



/* Provisioning MENU options */

insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('PROVCONNECT','PROVISIONING','Connectors','Provisioning Connectors','connectorList.cnt', 'en',3);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('MNGSYS','PROVISIONING','Managed Resource','Managed Connections','managedSysList.cnt', 'en',4);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('SYNCUSER','PROVISIONING','Sychronization','Synchronization','syncUser.cnt', 'en',3);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('SYNCLOG','PROVISIONING','LOG Viewer','LOG Viewer','syncExecLog.cnt', 'en',4);


insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('SYNCDETAIL','SYNCUSER','Configuration','Configuration','syncConfiguration.cnt', 'en',1);


/* Admin MENU options */
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('SECDOMAIN','ADMIN','Security Domain','Security Domain','secDomainList.cnt', 'en',1);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('LOCATION','ADMIN','Location','Location','locationList.cnt', 'en',2);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('CHALLENGE','ADMIN','Challenge Quest','Challenge Quest','challengeQuestList.cnt', 'en',3);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('BATCH_PROC','ADMIN','Batch Processes','Batch Processes','batchList.cnt', 'en',4);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('METADATA','ADMIN','Metadata','Metadata','metadataTypeList.cnt', 'en',5);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('BLK_NOTIFICATION','ADMIN','Bulk Notification','Bulk Notification','sysMessageList.cnt', 'en',6);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('MAIL_TEMPLATES','ADMIN','Mail Templates','Mail Templates','mailTmplList.cnt', 'en',7);

/* Self Service MENU options */
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, display_order) values('SELFSERVICE', 'ROOT' ,'SELF SERVICE','SELF SERVICE','', 'en',0);

insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, display_order, PUBLIC_URL) values('ACCESSCENTER','SELFSERVICE', 'Access Management Center', 'Access Management Center', null, 'en', '1',0);

insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, display_order, PUBLIC_URL) values('NEWHIRE','ACCESSCENTER','New User', 'New User', '{SELFSERVICE_EXT}priv/newhire/edit.jsp', 'en', '1',0);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, display_order, PUBLIC_URL) values('REQINBOX', 'ACCESSCENTER' , 'In-Box','In-Box','{SELFSERVICE}myPendingRequest.selfserve', 'en','3',0);
insert into openiam.MENU (MENU_ID,LANGUAGE_CD,MENU_GROUP,MENU_NAME,MENU_DESC,URL,ACTIVE,DISPLAY_ORDER,PUBLIC_URL) VALUES ('CREATEREQ','en','ACCESSCENTER','Create Request','Create Request','{SELFSERVICE}createRequest.selfserve',null,'4',0);

insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, display_order, PUBLIC_URL) values('MANAGEREQ', 'ACCESSCENTER' , 'Request History','Request History','{SELFSERVICE}requestList.selfserve', 'en','5',0);

insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, display_order, PUBLIC_URL) values('SELFCENTER','SELFSERVICE','Self Service Center', 'Self Service Center', null, 'en', '2',0);

insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, display_order, PUBLIC_URL) values('DIRECTORY','SELFCENTER','Directory Lookup', 'Directory Lookup', '{SELFSERVICE}pub/directory.do?method=view', 'en', '1',1);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, display_order, PUBLIC_URL) values('CHNGPSWD','SELFCENTER', 'Change Password', 'Change Password', '{SELFSERVICE}passwordChange.selfserve', 'en', '3',0);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, display_order, PUBLIC_URL) values('IDQUEST','SELFCENTER', 'Challenge Response', 'Challenge Response', '{SELFSERVICE}identityQuestion.selfserve', 'en', '4',0);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, display_order, PUBLIC_URL) values('PROFILE','SELFCENTER', 'Edit Your Profile', 'Edit Your Profile', '{SELFSERVICE_EXT}priv/profile/edit.jsp', 'en', '6',0);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, display_order, PUBLIC_URL) values('MY-ID-LIST','SELFCENTER', 'My Identities', 'My Identities', '{SELFSERVICE}myIdentityList.selfserve', 'en', '7',0);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, display_order, PUBLIC_URL) values('MY-APPS','SELFCENTER', 'Launch Apps', 'Launch Apps', '{SELFSERVICE}launchApp.selfserve', 'en', '8',0);


insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER, PUBLIC_URL) values('SELF_QUERYUSER','ACCESSCENTER','Manage User','Manage User','{SELFSERVICE}idman/userSearch.do?action=view', 'en',1,0);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('SELF_USERSUMMARY','SELF_QUERYUSER','User Details','User Details','{SELFSERVICE}editUser.selfserve', 'en',1);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('SELF_USERIDENTITY','SELF_QUERYUSER','Identities','User Identities','{SELFSERVICE}userIdentity.selfserve', 'en',2);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('SELF_USERGROUP','SELF_QUERYUSER','Group','User Groups','{SELFSERVICE}userGroup.selfserve', 'en',3);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('SELF_USERROLE','SELF_QUERYUSER','Role','User Role','{SELFSERVICE}userRole.selfserve', 'en',4);
insert into openiam.MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('SELF_USERPSWDRESET','SELF_QUERYUSER','Password Reset','Password Reset','{SELFSERVICE}resetPassword.selfserve', 'en',7);




insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) values('BIRT_REPORT','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('BATCH_PROC','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('REPORT','SUPER_SEC_ADMIN','IDM');


/* service admin role */

insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('IDMAN','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('SECURITY_POLICY','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('ACC_CONTROL','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('PROVISIONING','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('ADMIN','SUPER_SEC_ADMIN','IDM');

insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('SECDOMAIN','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('METADATA','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('LOCATION','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('CHALLENGE','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('REFDATA','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('BLK_NOTIFICATION','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('MAIL_TEMPLATES','SUPER_SEC_ADMIN','IDM');

insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('ORG','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('USER','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('USER_BULK','SUPER_SEC_ADMIN','IDM');

insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('SYNCUSER','SUPER_SEC_ADMIN','IDM');

insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('SYNCDETAIL','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('SYNCLOG','SUPER_SEC_ADMIN','IDM');

insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('QUERYUSER','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('ADDUSER','SUPER_SEC_ADMIN','IDM');

insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('USERSUMMARY','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('USERIDENTITY','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('USERGROUP','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('USERROLE','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('USERHISTORY','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('USERPSWDRESET','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('DELG_FILTER','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('USERATTR','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('USERORG','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('USER_COMMENT','SUPER_SEC_ADMIN','IDM');

insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('RESSUMMARY','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('RESPOLICYMAP','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('RESAPPROVER','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('RESRECONCILE','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('RESENTITLEMENT','SUPER_SEC_ADMIN','IDM');

insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('ROLE_SUMMARY','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('ROLE_RESMAP','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('ROLE_POLICY','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('ROLE_GRPMAP','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('ROLE_MENUMAP','SUPER_SEC_ADMIN','IDM');

insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('SECURITY_GROUP','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('SECURITY_ROLE','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('SECURITY_RES','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('PROVCONNECT','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('MNGSYS','SUPER_SEC_ADMIN','IDM');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID,SERVICE_ID) VALUES('IDSYNC','SUPER_SEC_ADMIN','IDM');


insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('SELFSERVICE','END_USER','USR_SEC_DOMAIN');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('ACCESSCENTER','END_USER','USR_SEC_DOMAIN');


insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('SELF_REGISTER','END_USER','USR_SEC_DOMAIN');

insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('REQINBOX','END_USER','USR_SEC_DOMAIN');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('CREATEREQ','END_USER','USR_SEC_DOMAIN');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('MANAGEREQ','END_USER','USR_SEC_DOMAIN');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('SELFCENTER','END_USER','USR_SEC_DOMAIN');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('DIRECTORY','END_USER','USR_SEC_DOMAIN');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('CHNGPSWD','END_USER','USR_SEC_DOMAIN');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('IDQUEST','END_USER','USR_SEC_DOMAIN');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('PROFILE','END_USER','USR_SEC_DOMAIN');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('REPORTINC','END_USER','USR_SEC_DOMAIN');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('CONTADMIN','END_USER','USR_SEC_DOMAIN');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('MY-ID-LIST','END_USER','USR_SEC_DOMAIN');

insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('NEWUSER-NOAPPRV','END_USER','USR_SEC_DOMAIN');

insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('REQINBOX','SUPER_SEC_ADMIN','USR_SEC_DOMAIN');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('MANAGEREQ','SUPER_SEC_ADMIN','USR_SEC_DOMAIN');

insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('SELF_USERSUMMARY','HELPDESK','USR_SEC_DOMAIN');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('SELF_USERIDENTITY','HELPDESK','USR_SEC_DOMAIN');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('SELF_USERGROUP','HELPDESK','USR_SEC_DOMAIN');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('SELF_USERROLE','HELPDESK','USR_SEC_DOMAIN');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('SELF_USERPSWDRESET','HELPDESK','USR_SEC_DOMAIN');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('SELF_QUERYUSER','HELPDESK','USR_SEC_DOMAIN');

insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('SELFSERVICE','HELPDESK','USR_SEC_DOMAIN');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('ACCESSCENTER','HELPDESK','USR_SEC_DOMAIN');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('MANAGEREQ','HELPDESK','USR_SEC_DOMAIN');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('SELFCENTER','HELPDESK','USR_SEC_DOMAIN');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('DIRECTORY','HELPDESK','USR_SEC_DOMAIN');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('CHNGPSWD','HELPDESK','USR_SEC_DOMAIN');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('IDQUEST','HELPDESK','USR_SEC_DOMAIN');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('PROFILE','HELPDESK','USR_SEC_DOMAIN');
insert into openiam.PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('CREATEREQ','HELPDESK','USR_SEC_DOMAIN');

commit;