use openiam;


IF EXISTS (SELECT * FROM information_schema.columns WHERE table_catalog='openiam' AND table_name = 'CONTENT_PROVIDER' AND column_name = 'MIN_AUTH_LEVEL')
BEGIN
	ALTER TABLE CONTENT_PROVIDER DROP CP_AUTH_LEVEL_FK;
	DROP INDEX CP_AUTH_LEVEL_FK ON CONTENT_PROVIDER;
	ALTER TABLE CONTENT_PROVIDER DROP COLUMN MIN_AUTH_LEVEL;
END;

IF EXISTS (SELECT * FROM information_schema.columns WHERE table_catalog='openiam' AND table_name = 'URI_PATTERN' AND column_name = 'MIN_AUTH_LEVEL')
BEGIN
	ALTER TABLE URI_PATTERN DROP URI_PATTERN_AUTH_LEVEL_FK;
	DROP INDEX URI_PATTERN_AUTH_LEVEL_FK ON URI_PATTERN;
	ALTER TABLE URI_PATTERN DROP COLUMN MIN_AUTH_LEVEL;
END;
