use openiam;
INSERT INTO RES (RESOURCE_ID, RESOURCE_TYPE_ID, NAME, DESCRIPTION, URL, DISPLAY_ORDER) VALUES('GROUP_IDENTITY', 'MENU_ITEM', 'GROUP_IDENTITY', 'Edit Identity Info','/webconsole/editGroupIdentity.html',3);
INSERT INTO RESOURCE_PROP (RESOURCE_PROP_ID, RESOURCE_ID, NAME, ATTR_VALUE) VALUES ('GROUP_IDENTITY_PUB', 'GROUP_IDENTITY', 'MENU_IS_PUBLIC', 'true');
INSERT INTO RESOURCE_PROP (RESOURCE_PROP_ID, RESOURCE_ID, NAME, ATTR_VALUE) VALUES ('GROUP_IDENTITY_DESC', 'GROUP_IDENTITY', 'MENU_DISPLAY_NAME', 'Identities');


INSERT INTO res_to_res_membership (RESOURCE_ID, MEMBER_RESOURCE_ID) VALUES('GROUP_EDIT_MENU', 'GROUP_IDENTITY');

INSERT INTO LANGUAGE_MAPPING (ID, LANGUAGE_ID, REFERENCE_ID, REFERENCE_TYPE, TEXT_VALUE) 
values ('GROUP_IDENTITY_DESC','1','GROUP_IDENTITY','ResourceEntity.displayNameMap','Identities'),
 ('ESGROUP_IDENTITY_DESC','4','GROUP_IDENTITY','ResourceEntity.displayNameMap','Identidades');
