use openiam;


IF NOT EXISTS (SELECT * FROM information_schema.columns WHERE table_catalog='openiam' AND table_name = 'REPORT_INFO' AND column_name = 'BUILT_IN') 
BEGIN
	ALTER TABLE REPORT_INFO ADD BUILT_IN CHAR(1) DEFAULT 'N' NOT NULL;
END;
