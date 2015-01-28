use openiam;

ALTER TABLE REPORT_INFO ADD COLUMN RESOURCE_ID VARCHAR(32) CHARACTER SET 'utf8' NULL;
ALTER TABLE REPORT_INFO ADD CONSTRAINT REPORT_INFO_RES_FK FOREIGN KEY (RESOURCE_ID) REFERENCES RES(RESOURCE_ID);
