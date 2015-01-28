use openiam;

DELETE FROM RECONCILIATION_SITUATION WHERE RECON_CONFIG_ID IN ('1011', '4028b881373b90e501373b92ec180001');
DELETE FROM RECONCILIATION_CONFIG WHERE RECON_CONFIG_ID IN ('1011', '4028b881373b90e501373b92ec180001');

INSERT INTO reconciliation_config VALUES 
('1011','101','','ACTIVE',NULL,NULL,'','recon/LDAPSearchQuery.groovy','','PRINCIPAL','uid',NULL,'101','{\"employeeId\":\"not_found\"}',NULL,'(&(objectclass=inetOrgPerson)(uid=*))','recon/UserSearchScript.groovy',NULL,NULL,NULL,'USER',NULL,'','');

INSERT INTO reconciliation_situation VALUES 
('10111','1011','IDM[exists] and Resource[not exists]','DELETE_FROM_IDM','recon/LDAPPopulationScript.groovy',''),
('10112','1011','IDM[not exists] and Resource[exists]','ADD_TO_IDM','recon/LDAPPopulationScript.groovy',''),
('10113','1011','IDM[deleted] and Resource[exists]','NOTHING','recon/LDAPPopulationScript.groovy',''),
('10114','1011','IDM[exists] and Resource[exists]','UPDATE_IDM_FROM_RES','recon/LDAPPopulationScript.groovy','');
