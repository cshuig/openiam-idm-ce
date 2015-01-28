use openiam;

IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_catalog='openiam' AND table_name = 'USERS' AND column_name = 'CLAIM_DATE') 
BEGIN
	ALTER TABLE USERS ADD CLAIM_DATE DATE NULL;
END;

 		

