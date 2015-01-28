use openiam;

DELIMITER $$
DROP PROCEDURE IF EXISTS ALTERNATE_CONFIGS$$
CREATE PROCEDURE ALTERNATE_CONFIGS()
BEGIN

IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.COLUMNS
	WHERE TABLE_NAME='SYNCH_CONFIG' AND column_name='ATTRIBUTE_NAMES_LOOKUP') THEN
	ALTER TABLE SYNCH_CONFIG ADD COLUMN ATTRIBUTE_NAMES_LOOKUP VARCHAR(120) NULL;
END IF;
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.COLUMNS
	WHERE TABLE_NAME='SYNCH_CONFIG' AND column_name='SEARCH_SCOPE') THEN
	ALTER TABLE SYNCH_CONFIG ADD COLUMN SEARCH_SCOPE INTEGER NOT NULL DEFAULT 2;
END IF;
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.COLUMNS
	WHERE TABLE_NAME='MANAGED_SYS' AND column_name='SEARCH_SCOPE') THEN
	ALTER TABLE MANAGED_SYS ADD COLUMN SEARCH_SCOPE INTEGER NOT NULL DEFAULT 2;
END IF;

END$$

DELIMITER ;
call ALTERNATE_CONFIGS();
DROP PROCEDURE ALTERNATE_CONFIGS;