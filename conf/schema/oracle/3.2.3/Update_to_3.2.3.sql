/********************** v 3.2.3***********************/
/**********************dml/1.fix_user_status.sql*********************************************/
UPDATE USERS SET STATUS='TERMINATED' WHERE STATUS='TERMINATE';
UPDATE USERS SET SECONDARY_STATUS='TERMINATED' WHERE SECONDARY_STATUS='TERMINATE';

/**********************ddl/1.add_empty_value.sql*********************************************/
ALTER TABLE URI_PATTERN_META_VALUE ADD (IS_EMPTY_VALUE CHAR(1) DEFAULT 'N'  NOT NULL);
/**********************ddl/2.add_recon_config_activedirectory.sql*********************************************/
-- Reconciliation config
insert into RECONCILIATION_CONFIG(RECON_CONFIG_ID, RESOURCE_ID, STATUS, TARGET_SYS_MATCH_SCRIPT, MANUAL_RECONCILIATION_FLAG, MATCH_FIELD_NAME, CUSTOM_MATCH_ATTR, MANAGED_SYS_ID, SEARCH_FILTER, TARGET_SYS_SEARCH_FILTER, MATCH_SCRIPT, RECON_CUSTOM_PROCESSOR, RECON_TYPE) 
values('110grouptype', '110', 'ACTIVE', 'recon/LDAPSearchQuery.groovy', 'N', 'PRINCIPAL', 'cn', '110', '', '(objectClass=group)', 'recon/GroupSearchScript.groovy', 'recon/GroupProcessor.groovy', 'GROUP');

insert into RECONCILIATION_SITUATION(RECON_SITUATION_ID,RECON_CONFIG_ID,SITUATION,SITUATION_RESP,SCRIPT) 
values('110grouptype0003','110grouptype','IDM[deleted] and Resource[exists]','ADD_TO_IDM','recon/ADPopulationScript.groovy');
insert into RECONCILIATION_SITUATION(RECON_SITUATION_ID,RECON_CONFIG_ID,SITUATION,SITUATION_RESP,SCRIPT) 
values('110grouptype0004','110grouptype','IDM[exists] and Resource[not exists]','DELETE_FROM_IDM','recon/ADPopulationScript.groovy');
insert into RECONCILIATION_SITUATION(RECON_SITUATION_ID,RECON_CONFIG_ID,SITUATION,SITUATION_RESP,SCRIPT) 
values('110grouptype0005','110grouptype','IDM[not exists] and Resource[exists]','ADD_TO_IDM','recon/ADPopulationScript.groovy');
insert into RECONCILIATION_SITUATION(RECON_SITUATION_ID,RECON_CONFIG_ID,SITUATION,SITUATION_RESP,SCRIPT) 
values('110grouptype0006','110grouptype','IDM[exists] and Resource[exists]','UPDATE_IDM_FROM_RES','recon/ADPopulationScript.groovy');

insert into RECONCILIATION_CONFIG(RECON_CONFIG_ID, RESOURCE_ID, STATUS, TARGET_SYS_MATCH_SCRIPT, MANUAL_RECONCILIATION_FLAG, MATCH_FIELD_NAME, CUSTOM_MATCH_ATTR, MANAGED_SYS_ID, SEARCH_FILTER, TARGET_SYS_SEARCH_FILTER, MATCH_SCRIPT, RECON_CUSTOM_PROCESSOR, RECON_TYPE) 
values('110usertype', '110', 'ACTIVE', 'recon/LDAPSearchQuery.groovy', 'N', 'PRINCIPAL', 'sAMAccountName', '110', '{"employeeId": "notexists"}', '(&(objectClass=user)(cn=*))', 'recon/UserSearchScript.groovy', null, 'USER');
insert into RECONCILIATION_SITUATION(RECON_SITUATION_ID,RECON_CONFIG_ID,SITUATION,SITUATION_RESP,SCRIPT) 
values('110usertype290','110usertype','IDM[deleted] and Resource[exists]','ADD_TO_IDM','recon/ADPopulationScript.groovy');
insert into RECONCILIATION_SITUATION(RECON_SITUATION_ID,RECON_CONFIG_ID,SITUATION,SITUATION_RESP,SCRIPT) 
values('110usertype291','110usertype','IDM[exists] and Resource[not exists]','DELETE_FROM_IDM','recon/ADPopulationScript.groovy');
insert into RECONCILIATION_SITUATION(RECON_SITUATION_ID,RECON_CONFIG_ID,SITUATION,SITUATION_RESP,SCRIPT) 
values('110usertype292','110usertype','IDM[not exists] and Resource[exists]','ADD_TO_IDM','recon/ADPopulationScript.groovy');
insert into RECONCILIATION_SITUATION(RECON_SITUATION_ID,RECON_CONFIG_ID,SITUATION,SITUATION_RESP,SCRIPT) 
values('110usertype293','110usertype','IDM[exists] and Resource[exists]','UPDATE_IDM_FROM_RES','recon/ADPopulationScript.groovy');

commit;
