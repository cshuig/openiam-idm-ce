use openiam;

ALTER TABLE RES DROP COLUMN BRANCH_ID;
ALTER TABLE RES DROP FOREIGN KEY FK_RESOURCE_CATEGORY;
ALTER TABLE RES DROP COLUMN CATEGORY_ID;
ALTER TABLE RES DROP COLUMN RES_OWNER_USER_ID;
ALTER TABLE RES DROP COLUMN RES_OWNER_GROUP_ID;

ALTER TABLE RES ADD COLUMN ADMIN_RESOURCE_ID VARCHAR(32) NULL;
ALTER TABLE RES ADD CONSTRAINT RES_ADMIN_RES_FK FOREIGN KEY (ADMIN_RESOURCE_ID) REFERENCES RES(RESOURCE_ID);

ALTER TABLE ROLE ADD COLUMN ADMIN_RESOURCE_ID VARCHAR(32) NULL;
ALTER TABLE ROLE ADD CONSTRAINT ROLE_ADMIN_RES_FK FOREIGN KEY (ADMIN_RESOURCE_ID) REFERENCES RES(RESOURCE_ID);


ALTER TABLE GRP ADD COLUMN ADMIN_RESOURCE_ID VARCHAR(32) NULL;
ALTER TABLE GRP ADD CONSTRAINT GRP_ADMIN_RES_FK FOREIGN KEY (ADMIN_RESOURCE_ID) REFERENCES RES(RESOURCE_ID);

INSERT INTO METADATA_TYPE (TYPE_ID, DESCRIPTION, ACTIVE, SYNC_MANAGED_SYS) VALUES('ADMIN_RESOURCE', 'Admin Resource', 'Y', 'Y');
INSERT INTO RESOURCE_TYPE (RESOURCE_TYPE_ID, DESCRIPTION, METADATA_TYPE_ID) VALUES('ADMIN_RESOURCE', 'Admin Resource', 'ADMIN_RESOURCE');

ALTER TABLE RESOURCE_TYPE ADD COLUMN SUPPORTS_HIERARCHY CHAR(1) NULL DEFAULT 'Y';
UPDATE RESOURCE_TYPE SET SUPPORTS_HIERARCHY='Y';
UPDATE RESOURCE_TYPE SET SUPPORTS_HIERARCHY='N' WHERE RESOURCE_TYPE_ID='ADMIN_RESOURCE';
ALTER TABLE RESOURCE_TYPE MODIFY COLUMN SUPPORTS_HIERARCHY CHAR(1) NOT NULL DEFAULT 'Y';