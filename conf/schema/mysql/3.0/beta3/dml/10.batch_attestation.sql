use openiam;

INSERT INTO BATCH_CONFIG (TASK_ID, TASK_NAME, CRON_EXPRESSION, SPRING_BEAN, SPRING_BEAN_METHOD) VALUES('ATTESTATION', 'Attestation', '0 0 0 1 1/3 ?', 'attestationInitializer', 'initializeAttestation');