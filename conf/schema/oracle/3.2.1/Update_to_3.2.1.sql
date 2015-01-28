/**********************dml/1.update_batch_tasks.sql***********************/

UPDATE BATCH_CONFIG SET TASK_NAME='Re-Certification' WHERE TASK_ID='ATTESTATION';

DELETE FROM BATCH_CONFIG WHERE TASK_ID='TASK_EMAIL_NOTIFICATION';

INSERT INTO BATCH_CONFIG (TASK_ID, TASK_NAME, CRON_EXPRESSION, SPRING_BEAN, SPRING_BEAN_METHOD) VALUES('TASK_EMAIL_NOTIFICATION', 'Task Email Notification', '0 0 0 1 1/3 ?', 'taskEmailNotifier', 'sweep');





/**********************ddl/1.modify_uri_meta.sql***********************/

ALTER TABLE URI_PATTERN_META_VALUE ADD (PROPAGETE_THROUGH_PROXY CHAR(1) DEFAULT 'Y' NOT NULL);