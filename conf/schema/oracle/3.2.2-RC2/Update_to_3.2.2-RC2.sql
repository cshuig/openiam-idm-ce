/********************** v 3.2.2 - RC2***********************/
/**********************ddl/2.extend_columns_length.sql*********************************************/
ALTER TABLE USER_IDENTITY_ANS MODIFY (QUESTION_ANSWER VARCHAR(1024)  DEFAULT NULL);
ALTER TABLE MANAGED_SYS MODIFY (PSWD VARCHAR(512)  DEFAULT NULL);

commit;

