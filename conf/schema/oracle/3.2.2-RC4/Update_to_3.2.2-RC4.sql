/********************** v 3.2.2 - RC4***********************/
/**********************dml/1.group_identity_menu.sql*********************************************/
INSERT INTO RES (RESOURCE_ID, RESOURCE_TYPE_ID, NAME, DESCRIPTION, URL, DISPLAY_ORDER) VALUES('GROUP_IDENTITY', 'MENU_ITEM', 'GROUP_IDENTITY', 'Edit Identity Info','/webconsole/editGroupIdentity.html',3);
INSERT INTO RESOURCE_PROP (RESOURCE_PROP_ID, RESOURCE_ID, NAME, ATTR_VALUE) VALUES ('GROUP_IDENTITY_PUB', 'GROUP_IDENTITY', 'MENU_IS_PUBLIC', 'true');
INSERT INTO RESOURCE_PROP (RESOURCE_PROP_ID, RESOURCE_ID, NAME, ATTR_VALUE) VALUES ('GROUP_IDENTITY_DESC', 'GROUP_IDENTITY', 'MENU_DISPLAY_NAME', 'Identities');


INSERT INTO res_to_res_membership (RESOURCE_ID, MEMBER_RESOURCE_ID) VALUES('GROUP_EDIT_MENU', 'GROUP_IDENTITY');

INSERT INTO LANGUAGE_MAPPING (ID, LANGUAGE_ID, REFERENCE_ID, REFERENCE_TYPE, TEXT_VALUE) values ('GROUP_IDENTITY_DESC','1','GROUP_IDENTITY','ResourceEntity.displayNameMap','Identities');
INSERT INTO LANGUAGE_MAPPING (ID, LANGUAGE_ID, REFERENCE_ID, REFERENCE_TYPE, TEXT_VALUE) values ('ESGROUP_IDENTITY_DESC','4','GROUP_IDENTITY','ResourceEntity.displayNameMap','Identidades');
/**********************dml/2.update_policy_attributes.sql*********************************************/
UPDATE POLICY_ATTRIBUTE SET VALUE1='false' WHERE (dbms_lob.compare(VALUE1,'0') = 0 AND NAME IN('REPEAT_SAME_WORD_PASSPHRASE', 'CHNG_PSWD_ON_RESET', 'RESET_BY_USER'));
UPDATE POLICY_ATTRIBUTE SET VALUE1='true' WHERE (dbms_lob.compare(VALUE1,'1') = 0 AND NAME IN('REPEAT_SAME_WORD_PASSPHRASE', 'CHNG_PSWD_ON_RESET', 'RESET_BY_USER'));

commit;