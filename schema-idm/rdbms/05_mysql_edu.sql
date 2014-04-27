
drop table COURSE_TERM_USER;
drop table COURSE_TERM;
DROP TABLE TERM;
DROP TABLE COURSE_PROGRAM;
DROP TABLE COURSE_ATTRIBUTE;
DROP TABLE COURSE;
DROP TABLE PROGRAM;

CREATE TABLE PROGRAM (
  PROGRAM_ID      VARCHAR(32) NOT NULL,
  NAME    VARCHAR(60) NOT NULL,
  STATUS  VARCHAR(20) NULL DEFAULT 'ACTIVE', /* ACTIVE, INACTIVE */
  PRIMARY KEY (PROGRAM_ID)
)ENGINE=InnoDB;

CREATE TABLE COURSE (
       COURSE_ID            VARCHAR(32) NOT NULL,
       NAME                 varchar(60) NULL,
       COURSE_NUMBER        varchar(30) NULL,
       DISTRICT_ID          varchar(32) NULL,
       SCHOOL_ID            VARCHAR(32) NULL,
       IS_PUBLIC            INTEGER  NULL,
       STATUS               VARCHAR(20) NULL DEFAULT 'ACTIVE', /* ACTIVE, INACTIVE */
       COURSE_FOLDER        VARCHAR(255),
       EXTERNAL_COURSE_ID   VARCHAR(50),
       PRIMARY KEY (COURSE_ID)
) ENGINE=InnoDB;

  ALTER TABLE COURSE
   ADD EXTERNAL_COURSE_ID NVARCHAR(50)

CREATE TABLE COURSE_ATTRIBUTE (
       COURSE_ATTRIBUTE_ID                   varchar(32) NOT NULL,
       NAME                 varchar(60) NULL,
       VALUE                varchar(255) NULL,
       COURSE_ID            varchar(32) NULL,
       PRIMARY KEY (COURSE_ATTRIBUTE_ID),
       CONSTRAINT REF_COURSE_COURSE_ATTR FOREIGN KEY (COURSE_ID) REFERENCES COURSE(COURSE_ID)
) ENGINE=InnoDB;

CREATE TABLE COURSE_PROGRAM(
     COURSE_PROGRAM_ID    varchar(32) NOT NULL,
     COURSE_ID            VARCHAR(32) NOT NULL,
     PROGRAM_ID           VARCHAR(32) NOT NULL,
     PRIMARY KEY (COURSE_PROGRAM_ID),
      CONSTRAINT REF_COURSEPRG_COURSE FOREIGN KEY (COURSE_ID) REFERENCES COURSE(COURSE_ID),
      CONSTRAINT REF_COURSEPRG_PROGRAM FOREIGN KEY (COURSE_ID) REFERENCES COURSE(COURSE_ID)
)ENGINE=InnoDB;


CREATE TABLE TERM (
       TERM_ID            varchar(32) NOT NULL,
       NAME               varchar(60) NULL,
       SCHOOL_YEAR        varchar(30) NULL,
       PERIOD_DESCRIPTION VARCHAR(255) NULL,   /* DESCRIBE WHAT PART OF YEAR  */
       DISTRICT_ID        varchar(32) NULL,
       PRIMARY KEY (TERM_ID),
       CONSTRAINT FK_TERM_COMPANY
       FOREIGN KEY (DISTRICT_ID)
                             REFERENCES COMPANY(COMPANY_ID)
) ENGINE=InnoDB;



CREATE TABLE COURSE_TERM (
       COURSE_TERM_ID                 VARCHAR(32) NOT NULL,
       TERM_ID            varchar(32)  NULL,
       COURSE_ID          varchar(32)  NULL,
       SECTION_NBR        VARCHAR(40)  NULL,
       STATUS             VARCHAR(20) NULL ,
       PRIMARY KEY (COURSE_TERM_ID),
       CONSTRAINT REF_COURSE_TERM_TERM FOREIGN KEY (TERM_ID) REFERENCES TERM(TERM_ID),
       CONSTRAINT REF_COURSE_TERM_COURSE FOREIGN KEY (COURSE_ID) REFERENCES COURSE(COURSE_ID)
) ENGINE=InnoDB;


CREATE TABLE COURSE_TERM_USER (
  COURSE_TERM_USER_ID     VARCHAR(32) NOT NULL,
  COURSE_ID               VARCHAR(32) NOT NULL,
  USER_ID                 VARCHAR(32) NOT NULL,
  USER_TYPE               VARCHAR(30) NULL DEFAULT 'STUDENT',
   ROLE_ID                VARCHAR(32) NULL,
  START_DATE              DATETIME,
  END_DATE                DATETIME,
  PRIMARY KEY (COURSE_TERM_USER_ID)
) ENGINE=InnoDB;


CREATE VIEW `course_summary_vw` AS
select c.COURSE_ID, c.NAME, c.COURSE_NUMBER, district.COMPANY_ID as DISTRICT_ID, district.COMPANY_NAME AS DISTRICT_NAME, c.SCHOOL_ID = school.COMPANY_ID AS SCHOOL_ID,
    school.COMPANY_NAME AS SCHOOL_NAME,
	ct.TERM_ID, t.NAME as TERM_NAME, ct.SECTION_NBR, ct.COURSE_TERM_ID
FROM COURSE c, COMPANY district, COMPANY school, COURSE_TERM ct, TERM t
WHERE c.DISTRICT_ID = district.COMPANY_ID and
	c.SCHOOL_ID = school.COMPANY_ID and
	c.COURSE_ID = ct.COURSE_ID and
    ct.TERM_ID = t.TERM_ID;


insert into METADATA_TYPE(TYPE_ID, DESCRIPTION, SYNC_MANAGED_SYS) values('stateType','State', 0);
insert into METADATA_TYPE(TYPE_ID, DESCRIPTION, SYNC_MANAGED_SYS) values('districtType','District', 0);
insert into METADATA_TYPE(TYPE_ID, DESCRIPTION, SYNC_MANAGED_SYS) values('schoolType','School', 0);

insert into CATEGORY_TYPE (category_id, type_id) values('ORG_TYPE','stateType');
insert into CATEGORY_TYPE (category_id, type_id) values('ORG_TYPE','districtType');
insert into CATEGORY_TYPE (category_id, type_id) values('ORG_TYPE','schoolType');

insert into MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, display_order, PUBLIC_URL) values('COURSECENTER','SELFSERVICE','Course Management Center', 'Course Management Center', null, 'en', '3',0);

insert into MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('COURSE_MANAGEMENT','COURSECENTER','Course Management','Course Management','{SELFSERVICE}courseManagement.selfserve', 'en',1);
insert into MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('PROGRAM_MANAGEMENT','COURSECENTER','Program Management','Program Management','{SELFSERVICE}programManagement.selfserve', 'en',2);
insert into MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('TERM_MANAGEMENT','COURSECENTER','Term Management','Term Management','{SELFSERVICE}programManagement.selfserve', 'en',3);
insert into MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('GROUP_CONTAINER','COURSECENTER','Course Group','Course Group','{SELFSERVICE}programManagement.selfserve', 'en',5);
insert into MENU (menu_id, menu_group, menu_name,menu_desc,url,LANGUAGE_CD, DISPLAY_ORDER) values('SYNC_COURSE','COURSECENTER','Synch Course','Synch Course','{SELFSERVICE}synchCourse.selfserve', 'en',6);



insert into METADATA_TYPE(TYPE_ID, DESCRIPTION, SYNC_MANAGED_SYS) values('EDU_ROLE','EDU ROLE', 0);
insert into CATEGORY_TYPE (category_id, type_id) values('ROLE_TYPE','EDU_ROLE');



INSERT INTO ROLE (ROLE_ID,SERVICE_ID, ROLE_NAME, TYPE_ID) VALUES ('ORG ADMIN','USR_SEC_DOMAIN','ORGANIZATION ADMIN','EDU_ROLE');
INSERT INTO ROLE (ROLE_ID,SERVICE_ID, ROLE_NAME, TYPE_ID) VALUES ('BUILDING ADMIN','USR_SEC_DOMAIN','BUILDING ADMIN','EDU_ROLE');
INSERT INTO ROLE (ROLE_ID,SERVICE_ID, ROLE_NAME, TYPE_ID) VALUES ('TEACHER','USR_SEC_DOMAIN','TEACHER','EDU_ROLE');
INSERT INTO ROLE (ROLE_ID,SERVICE_ID, ROLE_NAME, TYPE_ID) VALUES ('REPORT ONLY','USR_SEC_DOMAIN','REPORT ONLY','EDU_ROLE');
INSERT INTO ROLE (ROLE_ID,SERVICE_ID, ROLE_NAME, TYPE_ID) VALUES ('COURSE ADMIN','USR_SEC_DOMAIN','COURSE ONLY','EDU_ROLE');
INSERT INTO ROLE (ROLE_ID,SERVICE_ID,ROLE_NAME) VALUES ('STUDENT','USR_SEC_DOMAIN','STUDENT');

insert into METADATA_TYPE(TYPE_ID, DESCRIPTION,SYNC_MANAGED_SYS) values('STUDENT','STUDENT',1);
insert into METADATA_ELEMENT(metadata_id, type_id, attribute_name,SELF_EDITABLE, SELF_VIEWABLE, UI_TYPE,UI_OBJECT_SIZE) values ('301','STUDENT', 'STUDENT_ID',1,1,'TEXT','size=20');

insert into METADATA_TYPE(TYPE_ID, DESCRIPTION,SYNC_MANAGED_SYS) values('STAFF','STAFF',1);


insert into CATEGORY_TYPE (category_id, type_ID) values('USER_TYPE','STUDENT');
insert into CATEGORY_TYPE (category_id, type_ID) values('USER_TYPE','STAFF');



INSERT INTO PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('SELF_USERSUMMARY','FACULTY','USR_SEC_DOMAIN');
INSERT INTO PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('SELF_USERIDENTITY','FACULTY','USR_SEC_DOMAIN');
INSERT INTO PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('SELF_USERGROUP','FACULTY','USR_SEC_DOMAIN');
INSERT INTO PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('SELF_USERROLE','FACULTY','USR_SEC_DOMAIN');
INSERT INTO PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('SELF_USERPSWDRESET','FACULTY','USR_SEC_DOMAIN');
INSERT INTO PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('SELF_QUERYUSER','FACULTY','USR_SEC_DOMAIN');

INSERT INTO PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('SELFSERVICE','FACULTY','USR_SEC_DOMAIN');
INSERT INTO PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('ACCESSCENTER','FACULTY','USR_SEC_DOMAIN');
INSERT INTO PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('SELFCENTER','FACULTY','USR_SEC_DOMAIN');
INSERT INTO PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('CHNGPSWD','FACULTY','USR_SEC_DOMAIN');
INSERT INTO PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('IDQUEST','FACULTY','USR_SEC_DOMAIN');
INSERT INTO PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('PROFILE','FACULTY','USR_SEC_DOMAIN');

INSERT INTO PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('COURSECENTER','FACULTY','USR_SEC_DOMAIN');

INSERT INTO PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('COURSE_MANAGEMENT','FACULTY','USR_SEC_DOMAIN');
INSERT INTO PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('PROGRAM_MANAGEMENT','FACULTY','USR_SEC_DOMAIN');
INSERT INTO PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('TERM_MANAGEMENT','FACULTY','USR_SEC_DOMAIN');
INSERT INTO PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('GROUP_CONTAINER','FACULTY','USR_SEC_DOMAIN');
INSERT INTO PERMISSIONS(MENU_ID,ROLE_ID, SERVICE_ID) VALUES('SYNC_COURSE','FACULTY','USR_SEC_DOMAIN');


insert into USERS (user_id,first_name, last_name, STATUS, COMPANY_ID ) values('4000','faculty','','ACTIVE','100');
insert into USERS (user_id,first_name, last_name, STATUS, COMPANY_ID ) values('4001','student','','ACTIVE','100');
insert into LOGIN(SERVICE_ID, LOGIN, MANAGED_SYS_ID, USER_ID, PASSWORD,RESET_PWD, IS_LOCKED, AUTH_FAIL_COUNT) VALUES('USR_SEC_DOMAIN','faculty','0','4000','b83a81d1b3f5f209a70ec02c3d7f7fc5',0,0,0);
insert into LOGIN(SERVICE_ID, LOGIN, MANAGED_SYS_ID, USER_ID, PASSWORD,RESET_PWD, IS_LOCKED, AUTH_FAIL_COUNT) VALUES('USR_SEC_DOMAIN','student','0','4001','b83a81d1b3f5f209a70ec02c3d7f7fc5',0,0,0);

INSERT INTO USER_ROLE (USER_ROLE_ID, SERVICE_ID, ROLE_ID, USER_ID, STATUS) VALUES('102','USR_SEC_DOMAIN','FACULTY','4000','ACTIVE');
INSERT INTO USER_ROLE (USER_ROLE_ID, SERVICE_ID, ROLE_ID, USER_ID, STATUS) VALUES('103','USR_SEC_DOMAIN','STUDENT','4001','ACTIVE');

/*  Test Data for Districts and School */
INSERT INTO `company` (`COMPANY_ID`,`COMPANY_NAME`,`LST_UPDATE`,`LST_UPDATED_BY`,`PARENT_ID`,`STATUS`,`TYPE_ID`,`CREATE_DATE`,`CREATED_BY`,`ALIAS`,`DESCRIPTION`,`DOMAIN_NAME`,`LDAP_STR`,`CLASSIFICATION`,`INTERNAL_COMPANY_ID`,`INTERNAL_COMPANY_NAME`,`ABBREVIATION`,`SYMBOL`) VALUES ('4028818342f4efc10142f4f636c30001','HOLLAND',NULL,NULL,NULL,'ACTIVE','districtType',NULL,NULL,'','','','','DISTRICT','','','','');
INSERT INTO `company` (`COMPANY_ID`,`COMPANY_NAME`,`LST_UPDATE`,`LST_UPDATED_BY`,`PARENT_ID`,`STATUS`,`TYPE_ID`,`CREATE_DATE`,`CREATED_BY`,`ALIAS`,`DESCRIPTION`,`DOMAIN_NAME`,`LDAP_STR`,`CLASSIFICATION`,`INTERNAL_COMPANY_ID`,`INTERNAL_COMPANY_NAME`,`ABBREVIATION`,`SYMBOL`) VALUES ('4028818342f4efc10142f4f703bd0003','HAMILTON',NULL,NULL,NULL,'ACTIVE','districtType',NULL,NULL,'','','','','DISTRICT','','','','');
INSERT INTO `company` (`COMPANY_ID`,`COMPANY_NAME`,`LST_UPDATE`,`LST_UPDATED_BY`,`PARENT_ID`,`STATUS`,`TYPE_ID`,`CREATE_DATE`,`CREATED_BY`,`ALIAS`,`DESCRIPTION`,`DOMAIN_NAME`,`LDAP_STR`,`CLASSIFICATION`,`INTERNAL_COMPANY_ID`,`INTERNAL_COMPANY_NAME`,`ABBREVIATION`,`SYMBOL`) VALUES ('4028818a434c17ff0143502f1920000a','Holland Elementary School',NULL,NULL,'4028818342f4efc10142f4f636c30001','ACTIVE','schoolType',NULL,NULL,'','','','','SCHOOL','','','','');
INSERT INTO `company` (`COMPANY_ID`,`COMPANY_NAME`,`LST_UPDATE`,`LST_UPDATED_BY`,`PARENT_ID`,`STATUS`,`TYPE_ID`,`CREATE_DATE`,`CREATED_BY`,`ALIAS`,`DESCRIPTION`,`DOMAIN_NAME`,`LDAP_STR`,`CLASSIFICATION`,`INTERNAL_COMPANY_ID`,`INTERNAL_COMPANY_NAME`,`ABBREVIATION`,`SYMBOL`) VALUES ('4028818a434c17ff0143502fb828000c','Holland Middle Schoold',NULL,NULL,'4028818342f4efc10142f4f636c30001','ACTIVE','schoolType',NULL,NULL,'','','','','SCHOOL','','','','');

/* Test Data for Users */
INSERT INTO `users` (`USER_ID`,`FIRST_NAME`,`LAST_NAME`,`MIDDLE_INIT`,`TYPE_ID`,`CLASSIFICATION`,`TITLE`,`DEPT_CD`,`DEPT_NAME`,`MAIL_CODE`,`DIVISION`,`COST_CENTER`,`STATUS`,`SECONDARY_STATUS`,`BIRTHDATE`,`SEX`,`CREATE_DATE`,`CREATED_BY`,`LAST_UPDATE`,`LAST_UPDATED_BY`,`PREFIX`,`SUFFIX`,`USER_TYPE_IND`,`EMPLOYEE_ID`,`EMPLOYEE_TYPE`,`LOCATION_CD`,`LOCATION_NAME`,`COMPANY_ID`,`COMPANY_OWNER_ID`,`JOB_CODE`,`MANAGER_ID`,`ALTERNATE_ID`,`START_DATE`,`LAST_DATE`,`MAIDEN_NAME`,`NICKNAME`,`PASSWORD_THEME`,`COUNTRY`,`BLDG_NUM`,`STREET_DIRECTION`,`SUITE`,`ADDRESS1`,`ADDRESS2`,`ADDRESS3`,`ADDRESS4`,`ADDRESS5`,`ADDRESS6`,`ADDRESS7`,`CITY`,`STATE`,`POSTAL_CD`,`EMAIL_ADDRESS`,`AREA_CD`,`COUNTRY_CD`,`PHONE_NBR`,`PHONE_EXT`,`SHOW_IN_SEARCH`,`DEL_ADMIN`,`USER_OWNER_ID`,`DATE_PASSWORD_CHANGED`,`DATE_CHALLENGE_RESP_CHANGED`) VALUES ('4028818445a1becc0145a1c306320001','Test1','Teacher1','','STAFF','','','',NULL,NULL,'','','PENDING_INITIAL_LOGIN',NULL,NULL,'','2014-04-27 01:57:59','3000','2014-04-27 02:00:25',NULL,NULL,'',NULL,'','','-',NULL,'-',NULL,'',NULL,'',NULL,NULL,'','',NULL,NULL,'',NULL,NULL,'','',NULL,NULL,NULL,NULL,NULL,'','','','','',NULL,'','',0,0,NULL,NULL,NULL);
INSERT INTO `users` (`USER_ID`,`FIRST_NAME`,`LAST_NAME`,`MIDDLE_INIT`,`TYPE_ID`,`CLASSIFICATION`,`TITLE`,`DEPT_CD`,`DEPT_NAME`,`MAIL_CODE`,`DIVISION`,`COST_CENTER`,`STATUS`,`SECONDARY_STATUS`,`BIRTHDATE`,`SEX`,`CREATE_DATE`,`CREATED_BY`,`LAST_UPDATE`,`LAST_UPDATED_BY`,`PREFIX`,`SUFFIX`,`USER_TYPE_IND`,`EMPLOYEE_ID`,`EMPLOYEE_TYPE`,`LOCATION_CD`,`LOCATION_NAME`,`COMPANY_ID`,`COMPANY_OWNER_ID`,`JOB_CODE`,`MANAGER_ID`,`ALTERNATE_ID`,`START_DATE`,`LAST_DATE`,`MAIDEN_NAME`,`NICKNAME`,`PASSWORD_THEME`,`COUNTRY`,`BLDG_NUM`,`STREET_DIRECTION`,`SUITE`,`ADDRESS1`,`ADDRESS2`,`ADDRESS3`,`ADDRESS4`,`ADDRESS5`,`ADDRESS6`,`ADDRESS7`,`CITY`,`STATE`,`POSTAL_CD`,`EMAIL_ADDRESS`,`AREA_CD`,`COUNTRY_CD`,`PHONE_NBR`,`PHONE_EXT`,`SHOW_IN_SEARCH`,`DEL_ADMIN`,`USER_OWNER_ID`,`DATE_PASSWORD_CHANGED`,`DATE_CHALLENGE_RESP_CHANGED`) VALUES ('4028818445a1becc0145a1c6ea9d0010','Test2','Teacher2','','STAFF','','','',NULL,NULL,'','','PENDING_INITIAL_LOGIN',NULL,NULL,'','2014-04-27 02:02:18','3000','2014-04-27 02:02:42',NULL,NULL,'',NULL,'','','-',NULL,'-',NULL,'',NULL,'',NULL,NULL,'','',NULL,NULL,'',NULL,NULL,'','',NULL,NULL,NULL,NULL,NULL,'','','','','',NULL,'','',0,0,NULL,NULL,NULL);
INSERT INTO `users` (`USER_ID`,`FIRST_NAME`,`LAST_NAME`,`MIDDLE_INIT`,`TYPE_ID`,`CLASSIFICATION`,`TITLE`,`DEPT_CD`,`DEPT_NAME`,`MAIL_CODE`,`DIVISION`,`COST_CENTER`,`STATUS`,`SECONDARY_STATUS`,`BIRTHDATE`,`SEX`,`CREATE_DATE`,`CREATED_BY`,`LAST_UPDATE`,`LAST_UPDATED_BY`,`PREFIX`,`SUFFIX`,`USER_TYPE_IND`,`EMPLOYEE_ID`,`EMPLOYEE_TYPE`,`LOCATION_CD`,`LOCATION_NAME`,`COMPANY_ID`,`COMPANY_OWNER_ID`,`JOB_CODE`,`MANAGER_ID`,`ALTERNATE_ID`,`START_DATE`,`LAST_DATE`,`MAIDEN_NAME`,`NICKNAME`,`PASSWORD_THEME`,`COUNTRY`,`BLDG_NUM`,`STREET_DIRECTION`,`SUITE`,`ADDRESS1`,`ADDRESS2`,`ADDRESS3`,`ADDRESS4`,`ADDRESS5`,`ADDRESS6`,`ADDRESS7`,`CITY`,`STATE`,`POSTAL_CD`,`EMAIL_ADDRESS`,`AREA_CD`,`COUNTRY_CD`,`PHONE_NBR`,`PHONE_EXT`,`SHOW_IN_SEARCH`,`DEL_ADMIN`,`USER_OWNER_ID`,`DATE_PASSWORD_CHANGED`,`DATE_CHALLENGE_RESP_CHANGED`) VALUES ('4028818445a1becc0145a1c7ab6b001f','Test3','Teacher3','','STAFF','','','',NULL,NULL,'','','PENDING_INITIAL_LOGIN',NULL,NULL,'','2014-04-27 02:03:07','3000','2014-04-27 02:03:23',NULL,NULL,'',NULL,'','','-',NULL,'-',NULL,'',NULL,'',NULL,NULL,'','',NULL,NULL,'',NULL,NULL,'','',NULL,NULL,NULL,NULL,NULL,'','','','','',NULL,'','',0,0,NULL,NULL,NULL);

INSERT INTO `login` (`SERVICE_ID`,`LOGIN`,`MANAGED_SYS_ID`,`IDENTITY_TYPE`,`CANONICAL_NAME`,`USER_ID`,`PASSWORD`,`PWD_EQUIVALENT_TOKEN`,`PWD_CHANGED`,`PWD_EXP`,`RESET_PWD`,`FIRST_TIME_LOGIN`,`IS_LOCKED`,`STATUS`,`GRACE_PERIOD`,`CREATE_DATE`,`CREATED_BY`,`CURRENT_LOGIN_HOST`,`AUTH_FAIL_COUNT`,`LAST_AUTH_ATTEMPT`,`LAST_LOGIN`,`LAST_LOGIN_IP`,`PREV_LOGIN`,`PREV_LOGIN_IP`,`IS_DEFAULT`,`PWD_CHANGE_COUNT`,`PSWD_RESET_TOKEN`,`LOGIN_ATTR_IN_TARGET_SYS`,`PSWD_RESET_TOKEN_EXP`) VALUES ('USR_SEC_DOMAIN','Test1.Teacher1','0',NULL,NULL,'4028818445a1becc0145a1c306320001','85a15e7ae760d7444c80a8a56dd96bfc',NULL,NULL,'2014-07-26 01:58:04',1,1,0,'ACTIVE','2014-07-27 01:58:04','2014-04-27 01:58:04',NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL);
INSERT INTO `login` (`SERVICE_ID`,`LOGIN`,`MANAGED_SYS_ID`,`IDENTITY_TYPE`,`CANONICAL_NAME`,`USER_ID`,`PASSWORD`,`PWD_EQUIVALENT_TOKEN`,`PWD_CHANGED`,`PWD_EXP`,`RESET_PWD`,`FIRST_TIME_LOGIN`,`IS_LOCKED`,`STATUS`,`GRACE_PERIOD`,`CREATE_DATE`,`CREATED_BY`,`CURRENT_LOGIN_HOST`,`AUTH_FAIL_COUNT`,`LAST_AUTH_ATTEMPT`,`LAST_LOGIN`,`LAST_LOGIN_IP`,`PREV_LOGIN`,`PREV_LOGIN_IP`,`IS_DEFAULT`,`PWD_CHANGE_COUNT`,`PSWD_RESET_TOKEN`,`LOGIN_ATTR_IN_TARGET_SYS`,`PSWD_RESET_TOKEN_EXP`) VALUES ('USR_SEC_DOMAIN','Test2.Teacher2','0',NULL,NULL,'4028818445a1becc0145a1c6ea9d0010','85a15e7ae760d7444c80a8a56dd96bfc',NULL,NULL,'2014-07-26 02:02:19',1,1,0,'ACTIVE','2014-07-27 02:02:19','2014-04-27 02:02:19',NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL);
INSERT INTO `login` (`SERVICE_ID`,`LOGIN`,`MANAGED_SYS_ID`,`IDENTITY_TYPE`,`CANONICAL_NAME`,`USER_ID`,`PASSWORD`,`PWD_EQUIVALENT_TOKEN`,`PWD_CHANGED`,`PWD_EXP`,`RESET_PWD`,`FIRST_TIME_LOGIN`,`IS_LOCKED`,`STATUS`,`GRACE_PERIOD`,`CREATE_DATE`,`CREATED_BY`,`CURRENT_LOGIN_HOST`,`AUTH_FAIL_COUNT`,`LAST_AUTH_ATTEMPT`,`LAST_LOGIN`,`LAST_LOGIN_IP`,`PREV_LOGIN`,`PREV_LOGIN_IP`,`IS_DEFAULT`,`PWD_CHANGE_COUNT`,`PSWD_RESET_TOKEN`,`LOGIN_ATTR_IN_TARGET_SYS`,`PSWD_RESET_TOKEN_EXP`) VALUES ('USR_SEC_DOMAIN','Test3.Teacher3','0',NULL,NULL,'4028818445a1becc0145a1c7ab6b001f','85a15e7ae760d7444c80a8a56dd96bfc',NULL,NULL,'2014-07-26 02:03:08',1,1,0,'ACTIVE','2014-07-27 02:03:08','2014-04-27 02:03:08',NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL);


INSERT INTO `USER_AFFILIATION` (`USER_AFFILIATION_ID`,`COMPANY_ID`,`USER_ID`,`STATUS`,`CREATE_DATE`,`START_DATE`,`END_DATE`,`CREATED_BY`) VALUES ('4028818445a1becc0145a1c52d37000e','4028818a434c17ff0143502f1920000a','4028818445a1becc0145a1c306320001','ACTIVE','2014-04-27 02:00:25','2014-04-27 02:00:25',NULL,NULL);
INSERT INTO `USER_AFFILIATION` (`USER_AFFILIATION_ID`,`COMPANY_ID`,`USER_ID`,`STATUS`,`CREATE_DATE`,`START_DATE`,`END_DATE`,`CREATED_BY`) VALUES ('4028818445a1becc0145a1c747b7001d','4028818a434c17ff0143502f1920000a','4028818445a1becc0145a1c6ea9d0010','ACTIVE','2014-04-27 02:02:42','2014-04-27 02:02:42',NULL,NULL);
INSERT INTO `USER_AFFILIATION` (`USER_AFFILIATION_ID`,`COMPANY_ID`,`USER_ID`,`STATUS`,`CREATE_DATE`,`START_DATE`,`END_DATE`,`CREATED_BY`) VALUES ('4028818445a1becc0145a1c7e4df002c','4028818a434c17ff0143502fb828000c','4028818445a1becc0145a1c7ab6b001f','ACTIVE','2014-04-27 02:03:23','2014-04-27 02:03:23',NULL,NULL);


