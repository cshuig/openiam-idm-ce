use openiam;

DELETE FROM RESOURCE_PROP WHERE RESOURCE_PROP_ID = 'MANUALRECON_DESC';
DELETE FROM res_to_res_membership WHERE RESOURCE_ID = 'MNGSYS_EDIT_MENU_ROOT' AND MEMBER_RESOURCE_ID = 'MANUALRECON';
DELETE FROM RES WHERE RESOURCE_ID = 'MANUALRECON';

INSERT INTO RES (RESOURCE_ID, RESOURCE_TYPE_ID, DESCRIPTION, NAME, DISPLAY_ORDER, URL) VALUES ('MANUALRECON', 'MENU_ITEM', 'Manual reconciliation', 'MANUALRECON', 6, '/webconsole/manual-reconciliation.html');
INSERT INTO res_to_res_membership (RESOURCE_ID, MEMBER_RESOURCE_ID) VALUES ('MNGSYS_EDIT_MENU_ROOT', 'MANUALRECON');
INSERT INTO RESOURCE_PROP (RESOURCE_PROP_ID, RESOURCE_ID, NAME, PROP_VALUE) VALUES ('MANUALRECON_DESC', 'MANUALRECON', 'MENU_DISPLAY_NAME', 'Manual reconciliation');
