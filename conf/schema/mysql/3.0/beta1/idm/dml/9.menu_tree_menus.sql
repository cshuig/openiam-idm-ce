use openiam;

DELETE FROM res_to_res_membership WHERE MEMBER_RESOURCE_ID IN('USER_MENUS', 'GROUP_MENUS', 'ROLE_MENUS');
DELETE FROM RESOURCE_PROP WHERE RESOURCE_ID IN('USER_MENUS', 'GROUP_MENUS', 'ROLE_MENUS');
DELETE FROM RES WHERE RESOURCE_ID IN('USER_MENUS', 'GROUP_MENUS', 'ROLE_MENUS');

INSERT INTO RES (RESOURCE_ID, RESOURCE_TYPE_ID, NAME, DESCRIPTION, URL) VALUES('USER_MENUS', 'MENU_ITEM', 'USER_MENUS', 'User Menus', '/webconsole/userMenuTree.html');
INSERT INTO RESOURCE_PROP (RESOURCE_PROP_ID, RESOURCE_ID, NAME, PROP_VALUE) VALUES ('USER_MENUS_PUB', 'USER_MENUS', 'MENU_IS_PUBLIC', 'true');
INSERT INTO RESOURCE_PROP (RESOURCE_PROP_ID, RESOURCE_ID, NAME, PROP_VALUE) VALUES ('USER_MENUS_DESC', 'USER_MENUS', 'MENU_DISPLAY_NAME', 'Menus');

INSERT INTO RES (RESOURCE_ID, RESOURCE_TYPE_ID, NAME, DESCRIPTION, URL) VALUES('GROUP_MENUS', 'MENU_ITEM', 'GROUP_MENUS', 'Group Menus', '/webconsole/groupMenuTree.html');
INSERT INTO RESOURCE_PROP (RESOURCE_PROP_ID, RESOURCE_ID, NAME, PROP_VALUE) VALUES ('GROUP_MENUS_PUB', 'GROUP_MENUS', 'MENU_IS_PUBLIC', 'true');
INSERT INTO RESOURCE_PROP (RESOURCE_PROP_ID, RESOURCE_ID, NAME, PROP_VALUE) VALUES ('GROUP_MENUS_DESC', 'GROUP_MENUS', 'MENU_DISPLAY_NAME', 'Menus');

INSERT INTO RES (RESOURCE_ID, RESOURCE_TYPE_ID, NAME, DESCRIPTION, URL) VALUES('ROLE_MENUS', 'MENU_ITEM', 'ROLE_MENUS', 'Role Menus', '/webconsole/roleMenuTree.html');
INSERT INTO RESOURCE_PROP (RESOURCE_PROP_ID, RESOURCE_ID, NAME, PROP_VALUE) VALUES ('ROLE_MENUS_PUB', 'ROLE_MENUS', 'MENU_IS_PUBLIC', 'true');
INSERT INTO RESOURCE_PROP (RESOURCE_PROP_ID, RESOURCE_ID, NAME, PROP_VALUE) VALUES ('ROLE_MENUS_DESC', 'ROLE_MENUS', 'MENU_DISPLAY_NAME', 'Menus');

INSERT INTO res_to_res_membership (RESOURCE_ID, MEMBER_RESOURCE_ID) VALUES('USER_EDIT_MENU', 'USER_MENUS');
INSERT INTO res_to_res_membership (RESOURCE_ID, MEMBER_RESOURCE_ID) VALUES('GROUP_EDIT_MENU', 'GROUP_MENUS');
INSERT INTO res_to_res_membership (RESOURCE_ID, MEMBER_RESOURCE_ID) VALUES('ROLE_EDIT_MENU', 'ROLE_MENUS');