--------------------------------------------------------
--  File created - Wednesday-May-21-2014   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table ACT_GE_BYTEARRAY
--------------------------------------------------------

  CREATE TABLE ACTIVITI.ACT_GE_BYTEARRAY 
   (	ID_ NVARCHAR2(64), 
	REV_ NUMBER(*,0), 
	NAME_ NVARCHAR2(255), 
	DEPLOYMENT_ID_ NVARCHAR2(64), 
	BYTES_ BLOB, 
	GENERATED_ NUMBER(1,0)
   ) ;
--------------------------------------------------------
--  DDL for Table ACT_GE_PROPERTY
--------------------------------------------------------

  CREATE TABLE ACTIVITI.ACT_GE_PROPERTY 
   (	NAME_ NVARCHAR2(64), 
	VALUE_ NVARCHAR2(300), 
	REV_ NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table ACT_HI_ACTINST
--------------------------------------------------------

  CREATE TABLE ACTIVITI.ACT_HI_ACTINST 
   (	ID_ NVARCHAR2(64), 
	PROC_DEF_ID_ NVARCHAR2(64), 
	PROC_INST_ID_ NVARCHAR2(64), 
	EXECUTION_ID_ NVARCHAR2(64), 
	ACT_ID_ NVARCHAR2(255), 
	ACT_NAME_ NVARCHAR2(255), 
	ACT_TYPE_ NVARCHAR2(255), 
	ASSIGNEE_ NVARCHAR2(64), 
	START_TIME_ TIMESTAMP (6), 
	END_TIME_ TIMESTAMP (6), 
	DURATION_ NUMBER(19,0)
   ) ;
--------------------------------------------------------
--  DDL for Table ACT_HI_ATTACHMENT
--------------------------------------------------------

  CREATE TABLE ACTIVITI.ACT_HI_ATTACHMENT 
   (	ID_ NVARCHAR2(64), 
	REV_ NUMBER(*,0), 
	USER_ID_ NVARCHAR2(255), 
	NAME_ NVARCHAR2(255), 
	DESCRIPTION_ NVARCHAR2(2000), 
	TYPE_ NVARCHAR2(255), 
	TASK_ID_ NVARCHAR2(64), 
	PROC_INST_ID_ NVARCHAR2(64), 
	URL_ NVARCHAR2(2000), 
	CONTENT_ID_ NVARCHAR2(64)
   ) ;
--------------------------------------------------------
--  DDL for Table ACT_HI_COMMENT
--------------------------------------------------------

  CREATE TABLE ACTIVITI.ACT_HI_COMMENT 
   (	ID_ NVARCHAR2(64), 
	TYPE_ NVARCHAR2(255), 
	TIME_ TIMESTAMP (6), 
	USER_ID_ NVARCHAR2(255), 
	TASK_ID_ NVARCHAR2(64), 
	PROC_INST_ID_ NVARCHAR2(64), 
	ACTION_ NVARCHAR2(255), 
	MESSAGE_ NVARCHAR2(2000), 
	FULL_MSG_ BLOB
   ) ;
--------------------------------------------------------
--  DDL for Table ACT_HI_DETAIL
--------------------------------------------------------

  CREATE TABLE ACTIVITI.ACT_HI_DETAIL 
   (	ID_ VARCHAR2(64), 
	TYPE_ NVARCHAR2(255), 
	PROC_INST_ID_ NVARCHAR2(64), 
	EXECUTION_ID_ NVARCHAR2(64), 
	TASK_ID_ NVARCHAR2(64), 
	ACT_INST_ID_ NVARCHAR2(64), 
	NAME_ NVARCHAR2(255), 
	VAR_TYPE_ NVARCHAR2(64), 
	REV_ NUMBER(*,0), 
	TIME_ TIMESTAMP (6), 
	BYTEARRAY_ID_ NVARCHAR2(64), 
	DOUBLE_ NUMBER(*,10), 
	LONG_ NUMBER(19,0), 
	TEXT_ NVARCHAR2(2000), 
	TEXT2_ NVARCHAR2(2000)
   ) ;
--------------------------------------------------------
--  DDL for Table ACT_HI_PROCINST
--------------------------------------------------------

  CREATE TABLE ACTIVITI.ACT_HI_PROCINST 
   (	ID_ NVARCHAR2(64), 
	PROC_INST_ID_ NVARCHAR2(64), 
	BUSINESS_KEY_ NVARCHAR2(255), 
	PROC_DEF_ID_ NVARCHAR2(64), 
	START_TIME_ TIMESTAMP (6), 
	END_TIME_ TIMESTAMP (6), 
	DURATION_ NUMBER(19,0), 
	START_USER_ID_ NVARCHAR2(255), 
	START_ACT_ID_ NVARCHAR2(255), 
	END_ACT_ID_ NVARCHAR2(255), 
	SUPER_PROCESS_INSTANCE_ID_ NVARCHAR2(64), 
	DELETE_REASON_ NVARCHAR2(2000)
   ) ;
--------------------------------------------------------
--  DDL for Table ACT_HI_TASKINST
--------------------------------------------------------

  CREATE TABLE ACTIVITI.ACT_HI_TASKINST 
   (	ID_ NVARCHAR2(64), 
	PROC_DEF_ID_ NVARCHAR2(64), 
	TASK_DEF_KEY_ NVARCHAR2(255), 
	PROC_INST_ID_ NVARCHAR2(64), 
	EXECUTION_ID_ NVARCHAR2(64), 
	PARENT_TASK_ID_ NVARCHAR2(64), 
	NAME_ NVARCHAR2(255), 
	DESCRIPTION_ NVARCHAR2(2000), 
	OWNER_ NVARCHAR2(64), 
	ASSIGNEE_ NVARCHAR2(64), 
	START_TIME_ TIMESTAMP (6), 
	END_TIME_ TIMESTAMP (6), 
	DURATION_ NUMBER(19,0), 
	DELETE_REASON_ NVARCHAR2(2000), 
	PRIORITY_ NUMBER(*,0), 
	DUE_DATE_ TIMESTAMP (6)
   ) ;
--------------------------------------------------------
--  DDL for Table ACT_ID_GROUP
--------------------------------------------------------

  CREATE TABLE ACTIVITI.ACT_ID_GROUP 
   (	ID_ NVARCHAR2(64), 
	REV_ NUMBER(*,0), 
	NAME_ NVARCHAR2(255), 
	TYPE_ NVARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table ACT_ID_INFO
--------------------------------------------------------

  CREATE TABLE ACTIVITI.ACT_ID_INFO 
   (	ID_ NVARCHAR2(64), 
	REV_ NUMBER(*,0), 
	USER_ID_ NVARCHAR2(64), 
	TYPE_ NVARCHAR2(64), 
	KEY_ NVARCHAR2(255), 
	VALUE_ NVARCHAR2(255), 
	PASSWORD_ BLOB, 
	PARENT_ID_ NVARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table ACT_ID_MEMBERSHIP
--------------------------------------------------------

  CREATE TABLE ACTIVITI.ACT_ID_MEMBERSHIP 
   (	USER_ID_ NVARCHAR2(64), 
	GROUP_ID_ NVARCHAR2(64)
   ) ;
--------------------------------------------------------
--  DDL for Table ACT_ID_USER
--------------------------------------------------------

  CREATE TABLE ACTIVITI.ACT_ID_USER 
   (	ID_ NVARCHAR2(64), 
	REV_ NUMBER(*,0), 
	FIRST_ NVARCHAR2(255), 
	LAST_ NVARCHAR2(255), 
	EMAIL_ NVARCHAR2(255), 
	PWD_ NVARCHAR2(255), 
	PICTURE_ID_ NVARCHAR2(64)
   ) ;
--------------------------------------------------------
--  DDL for Table ACT_RE_DEPLOYMENT
--------------------------------------------------------

  CREATE TABLE ACTIVITI.ACT_RE_DEPLOYMENT 
   (	ID_ NVARCHAR2(64), 
	NAME_ NVARCHAR2(255), 
	DEPLOY_TIME_ TIMESTAMP (6)
   ) ;
--------------------------------------------------------
--  DDL for Table ACT_RE_PROCDEF
--------------------------------------------------------

  CREATE TABLE ACTIVITI.ACT_RE_PROCDEF 
   (	ID_ NVARCHAR2(64), 
	REV_ NUMBER(*,0), 
	CATEGORY_ NVARCHAR2(255), 
	NAME_ NVARCHAR2(255), 
	KEY_ NVARCHAR2(255), 
	VERSION_ NUMBER(*,0), 
	DEPLOYMENT_ID_ NVARCHAR2(64), 
	RESOURCE_NAME_ NVARCHAR2(2000), 
	DGRM_RESOURCE_NAME_ VARCHAR2(4000), 
	HAS_START_FORM_KEY_ NUMBER(1,0), 
	SUSPENSION_STATE_ NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table ACT_RU_EVENT_SUBSCR
--------------------------------------------------------

  CREATE TABLE ACTIVITI.ACT_RU_EVENT_SUBSCR 
   (	ID_ NVARCHAR2(64), 
	REV_ NUMBER(*,0), 
	EVENT_TYPE_ NVARCHAR2(255), 
	EVENT_NAME_ NVARCHAR2(255), 
	EXECUTION_ID_ NVARCHAR2(64), 
	PROC_INST_ID_ NVARCHAR2(64), 
	ACTIVITY_ID_ NVARCHAR2(64), 
	CONFIGURATION_ NVARCHAR2(255), 
	CREATED_ TIMESTAMP (6)
   ) ;
--------------------------------------------------------
--  DDL for Table ACT_RU_EXECUTION
--------------------------------------------------------

  CREATE TABLE ACTIVITI.ACT_RU_EXECUTION 
   (	ID_ NVARCHAR2(64), 
	REV_ NUMBER(*,0), 
	PROC_INST_ID_ NVARCHAR2(64), 
	BUSINESS_KEY_ NVARCHAR2(255), 
	PARENT_ID_ NVARCHAR2(64), 
	PROC_DEF_ID_ NVARCHAR2(64), 
	SUPER_EXEC_ NVARCHAR2(64), 
	ACT_ID_ NVARCHAR2(255), 
	IS_ACTIVE_ NUMBER(1,0), 
	IS_CONCURRENT_ NUMBER(1,0), 
	IS_SCOPE_ NUMBER(1,0), 
	IS_EVENT_SCOPE_ NUMBER(1,0), 
	SUSPENSION_STATE_ NUMBER(*,0), 
	CACHED_ENT_STATE_ NUMBER(*,0)
   ) ;
--------------------------------------------------------
--  DDL for Table ACT_RU_IDENTITYLINK
--------------------------------------------------------

  CREATE TABLE ACTIVITI.ACT_RU_IDENTITYLINK 
   (	ID_ NVARCHAR2(64), 
	REV_ NUMBER(*,0), 
	GROUP_ID_ NVARCHAR2(64), 
	TYPE_ NVARCHAR2(255), 
	USER_ID_ NVARCHAR2(64), 
	TASK_ID_ NVARCHAR2(64), 
	PROC_DEF_ID_ NVARCHAR2(64)
   ) ;
--------------------------------------------------------
--  DDL for Table ACT_RU_JOB
--------------------------------------------------------

  CREATE TABLE ACTIVITI.ACT_RU_JOB 
   (	ID_ NVARCHAR2(64), 
	REV_ NUMBER(*,0), 
	TYPE_ NVARCHAR2(255), 
	LOCK_EXP_TIME_ TIMESTAMP (6), 
	LOCK_OWNER_ NVARCHAR2(255), 
	EXCLUSIVE_ NUMBER(1,0), 
	EXECUTION_ID_ NVARCHAR2(64), 
	PROCESS_INSTANCE_ID_ NVARCHAR2(64), 
	RETRIES_ NUMBER(*,0), 
	EXCEPTION_STACK_ID_ NVARCHAR2(64), 
	EXCEPTION_MSG_ NVARCHAR2(2000), 
	DUEDATE_ TIMESTAMP (6), 
	REPEAT_ NVARCHAR2(255), 
	HANDLER_TYPE_ NVARCHAR2(255), 
	HANDLER_CFG_ NVARCHAR2(2000)
   ) ;
--------------------------------------------------------
--  DDL for Table ACT_RU_TASK
--------------------------------------------------------

  CREATE TABLE ACTIVITI.ACT_RU_TASK 
   (	ID_ NVARCHAR2(64), 
	REV_ NUMBER(*,0), 
	EXECUTION_ID_ NVARCHAR2(64), 
	PROC_INST_ID_ NVARCHAR2(64), 
	PROC_DEF_ID_ NVARCHAR2(64), 
	NAME_ NVARCHAR2(255), 
	PARENT_TASK_ID_ NVARCHAR2(64), 
	DESCRIPTION_ NVARCHAR2(2000), 
	TASK_DEF_KEY_ NVARCHAR2(255), 
	OWNER_ NVARCHAR2(64), 
	ASSIGNEE_ NVARCHAR2(64), 
	DELEGATION_ NVARCHAR2(64), 
	PRIORITY_ NUMBER(*,0), 
	CREATE_TIME_ TIMESTAMP (6), 
	DUE_DATE_ TIMESTAMP (6)
   ) ;
--------------------------------------------------------
--  DDL for Table ACT_RU_VARIABLE
--------------------------------------------------------

  CREATE TABLE ACTIVITI.ACT_RU_VARIABLE 
   (	ID_ NVARCHAR2(64), 
	REV_ NUMBER(*,0), 
	TYPE_ NVARCHAR2(255), 
	NAME_ NVARCHAR2(255), 
	EXECUTION_ID_ NVARCHAR2(64), 
	PROC_INST_ID_ NVARCHAR2(64), 
	TASK_ID_ NVARCHAR2(64), 
	BYTEARRAY_ID_ NVARCHAR2(64), 
	DOUBLE_ NUMBER(*,10), 
	LONG_ NUMBER(19,0), 
	TEXT_ NVARCHAR2(2000), 
	TEXT2_ NVARCHAR2(2000)
   ) ;
REM INSERTING into ACTIVITI.ACT_GE_BYTEARRAY
SET DEFINE OFF;
REM INSERTING into ACTIVITI.ACT_GE_PROPERTY
SET DEFINE OFF;
Insert into ACTIVITI.ACT_GE_PROPERTY (NAME_,VALUE_,REV_) values ('historyLevel','2',1);
Insert into ACTIVITI.ACT_GE_PROPERTY (NAME_,VALUE_,REV_) values ('schema.version','5.10',1);
Insert into ACTIVITI.ACT_GE_PROPERTY (NAME_,VALUE_,REV_) values ('schema.history','create(5.10)',1);
Insert into ACTIVITI.ACT_GE_PROPERTY (NAME_,VALUE_,REV_) values ('next.dbid','1',1);
REM INSERTING into ACTIVITI.ACT_HI_ACTINST
SET DEFINE OFF;
REM INSERTING into ACTIVITI.ACT_HI_ATTACHMENT
SET DEFINE OFF;
REM INSERTING into ACTIVITI.ACT_HI_COMMENT
SET DEFINE OFF;
REM INSERTING into ACTIVITI.ACT_HI_DETAIL
SET DEFINE OFF;
REM INSERTING into ACTIVITI.ACT_HI_PROCINST
SET DEFINE OFF;
REM INSERTING into ACTIVITI.ACT_HI_TASKINST
SET DEFINE OFF;
REM INSERTING into ACTIVITI.ACT_ID_GROUP
SET DEFINE OFF;
REM INSERTING into ACTIVITI.ACT_ID_INFO
SET DEFINE OFF;
REM INSERTING into ACTIVITI.ACT_ID_MEMBERSHIP
SET DEFINE OFF;
REM INSERTING into ACTIVITI.ACT_ID_USER
SET DEFINE OFF;
REM INSERTING into ACTIVITI.ACT_RE_DEPLOYMENT
SET DEFINE OFF;
REM INSERTING into ACTIVITI.ACT_RE_PROCDEF
SET DEFINE OFF;
REM INSERTING into ACTIVITI.ACT_RU_EVENT_SUBSCR
SET DEFINE OFF;
REM INSERTING into ACTIVITI.ACT_RU_EXECUTION
SET DEFINE OFF;
REM INSERTING into ACTIVITI.ACT_RU_IDENTITYLINK
SET DEFINE OFF;
REM INSERTING into ACTIVITI.ACT_RU_JOB
SET DEFINE OFF;
REM INSERTING into ACTIVITI.ACT_RU_TASK
SET DEFINE OFF;
REM INSERTING into ACTIVITI.ACT_RU_VARIABLE
SET DEFINE OFF;
--------------------------------------------------------
--  DDL for Index ACT_IDX_VARIABLE_TASK_ID
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_VARIABLE_TASK_ID ON ACTIVITI.ACT_RU_VARIABLE (TASK_ID_);
--------------------------------------------------------
--  DDL for Index ACT_IDX_EXE_SUPER
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_EXE_SUPER ON ACTIVITI.ACT_RU_EXECUTION (SUPER_EXEC_);
--------------------------------------------------------
--  DDL for Index ACT_IDX_EXE_PARENT
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_EXE_PARENT ON ACTIVITI.ACT_RU_EXECUTION (PARENT_ID_);
--------------------------------------------------------
--  DDL for Index ACT_IDX_MEMB_GROUP
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_MEMB_GROUP ON ACTIVITI.ACT_ID_MEMBERSHIP (GROUP_ID_);
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_DETAIL_NAME
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_HI_DETAIL_NAME ON ACTIVITI.ACT_HI_DETAIL (NAME_);
--------------------------------------------------------
--  DDL for Index ACT_IDX_EVENT_SUBSCR
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_EVENT_SUBSCR ON ACTIVITI.ACT_RU_EVENT_SUBSCR (EXECUTION_ID_);
--------------------------------------------------------
--  DDL for Index ACT_UNIQ_RU_BUS_KEY
--------------------------------------------------------

  CREATE UNIQUE INDEX ACTIVITI.ACT_UNIQ_RU_BUS_KEY ON ACTIVITI.ACT_RU_EXECUTION (CASE  WHEN BUSINESS_KEY_ IS NULL THEN NULL ELSE PROC_DEF_ID_ END , CASE  WHEN BUSINESS_KEY_ IS NULL THEN NULL ELSE BUSINESS_KEY_ END );
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_DETAIL_ACT_INST
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_HI_DETAIL_ACT_INST ON ACTIVITI.ACT_HI_DETAIL (ACT_INST_ID_);
--------------------------------------------------------
--  DDL for Index ACT_IDX_MEMB_USER
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_MEMB_USER ON ACTIVITI.ACT_ID_MEMBERSHIP (USER_ID_);
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_DETAIL_TIME
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_HI_DETAIL_TIME ON ACTIVITI.ACT_HI_DETAIL (TIME_);
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_PRO_I_BUSKEY
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_HI_PRO_I_BUSKEY ON ACTIVITI.ACT_HI_PROCINST (BUSINESS_KEY_);
--------------------------------------------------------
--  DDL for Index ACT_IDX_EVENT_SUBSCR_CONFIG_
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_EVENT_SUBSCR_CONFIG_ ON ACTIVITI.ACT_RU_EVENT_SUBSCR (CONFIGURATION_);
--------------------------------------------------------
--  DDL for Index ACT_IDX_IDENT_LNK_USER
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_IDENT_LNK_USER ON ACTIVITI.ACT_RU_IDENTITYLINK (USER_ID_);
--------------------------------------------------------
--  DDL for Index ACT_IDX_TASK_PROCDEF
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_TASK_PROCDEF ON ACTIVITI.ACT_RU_TASK (PROC_DEF_ID_);
--------------------------------------------------------
--  DDL for Index ACT_IDX_VAR_PROCINST
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_VAR_PROCINST ON ACTIVITI.ACT_RU_VARIABLE (PROC_INST_ID_);
--------------------------------------------------------
--  DDL for Index ACT_IDX_BYTEAR_DEPL
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_BYTEAR_DEPL ON ACTIVITI.ACT_GE_BYTEARRAY (DEPLOYMENT_ID_);
--------------------------------------------------------
--  DDL for Index ACT_IDX_JOB_EXCEPTION
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_JOB_EXCEPTION ON ACTIVITI.ACT_RU_JOB (EXCEPTION_STACK_ID_);
--------------------------------------------------------
--  DDL for Index ACT_IDX_TASK_CREATE
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_TASK_CREATE ON ACTIVITI.ACT_RU_TASK (CREATE_TIME_);
--------------------------------------------------------
--  DDL for Index ACT_IDX_EXEC_BUSKEY
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_EXEC_BUSKEY ON ACTIVITI.ACT_RU_EXECUTION (BUSINESS_KEY_);
--------------------------------------------------------
--  DDL for Index ACT_IDX_IDENT_LNK_GROUP
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_IDENT_LNK_GROUP ON ACTIVITI.ACT_RU_IDENTITYLINK (GROUP_ID_);
--------------------------------------------------------
--  DDL for Index ACT_IDX_EXE_PROCINST
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_EXE_PROCINST ON ACTIVITI.ACT_RU_EXECUTION (PROC_INST_ID_);
--------------------------------------------------------
--  DDL for Index ACT_IDX_TASK_EXEC
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_TASK_EXEC ON ACTIVITI.ACT_RU_TASK (EXECUTION_ID_);
--------------------------------------------------------
--  DDL for Index ACT_UNIQ_HI_BUS_KEY
--------------------------------------------------------

  CREATE UNIQUE INDEX ACTIVITI.ACT_UNIQ_HI_BUS_KEY ON ACTIVITI.ACT_HI_PROCINST (CASE  WHEN BUSINESS_KEY_ IS NULL THEN NULL ELSE PROC_DEF_ID_ END , CASE  WHEN BUSINESS_KEY_ IS NULL THEN NULL ELSE BUSINESS_KEY_ END );
--------------------------------------------------------
--  DDL for Index ACT_IDX_TASK_PROCINST
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_TASK_PROCINST ON ACTIVITI.ACT_RU_TASK (PROC_INST_ID_);
--------------------------------------------------------
--  DDL for Index ACT_UNIQ_PROCDEF
--------------------------------------------------------

  CREATE UNIQUE INDEX ACTIVITI.ACT_UNIQ_PROCDEF ON ACTIVITI.ACT_RE_PROCDEF (KEY_, VERSION_);
--------------------------------------------------------
--  DDL for Index ACT_IDX_ATHRZ_PROCEDEF
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_ATHRZ_PROCEDEF ON ACTIVITI.ACT_RU_IDENTITYLINK (PROC_DEF_ID_);
--------------------------------------------------------
--  DDL for Index ACT_IDX_TSKASS_TASK
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_TSKASS_TASK ON ACTIVITI.ACT_RU_IDENTITYLINK (TASK_ID_);
--------------------------------------------------------
--  DDL for Index ACT_IDX_VAR_BYTEARRAY
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_VAR_BYTEARRAY ON ACTIVITI.ACT_RU_VARIABLE (BYTEARRAY_ID_);
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_DETAIL_TASK_ID
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_HI_DETAIL_TASK_ID ON ACTIVITI.ACT_HI_DETAIL (TASK_ID_);
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_PRO_INST_END
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_HI_PRO_INST_END ON ACTIVITI.ACT_HI_PROCINST (END_TIME_);
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_ACT_INST_START
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_HI_ACT_INST_START ON ACTIVITI.ACT_HI_ACTINST (START_TIME_);
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_DETAIL_PROC_INST
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_HI_DETAIL_PROC_INST ON ACTIVITI.ACT_HI_DETAIL (PROC_INST_ID_);
--------------------------------------------------------
--  DDL for Index ACT_IDX_VAR_EXE
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_VAR_EXE ON ACTIVITI.ACT_RU_VARIABLE (EXECUTION_ID_);
--------------------------------------------------------
--  DDL for Index ACT_IDX_HI_ACT_INST_END
--------------------------------------------------------

  CREATE INDEX ACTIVITI.ACT_IDX_HI_ACT_INST_END ON ACTIVITI.ACT_HI_ACTINST (END_TIME_);
--------------------------------------------------------
--  Constraints for Table ACT_ID_GROUP
--------------------------------------------------------

  ALTER TABLE ACTIVITI.ACT_ID_GROUP ADD PRIMARY KEY (ID_) ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_GE_PROPERTY
--------------------------------------------------------

  ALTER TABLE ACTIVITI.ACT_GE_PROPERTY ADD PRIMARY KEY (NAME_) ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_RU_JOB
--------------------------------------------------------

  ALTER TABLE ACTIVITI.ACT_RU_JOB MODIFY (ID_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_RU_JOB MODIFY (TYPE_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_RU_JOB ADD CHECK (EXCLUSIVE_ IN (1,0)) ENABLE;
 
  ALTER TABLE ACTIVITI.ACT_RU_JOB ADD PRIMARY KEY (ID_) ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_ID_MEMBERSHIP
--------------------------------------------------------

  ALTER TABLE ACTIVITI.ACT_ID_MEMBERSHIP ADD PRIMARY KEY (USER_ID_, GROUP_ID_) ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_RU_EXECUTION
--------------------------------------------------------

  ALTER TABLE ACTIVITI.ACT_RU_EXECUTION ADD CHECK (IS_ACTIVE_ IN (1,0)) ENABLE;
 
  ALTER TABLE ACTIVITI.ACT_RU_EXECUTION ADD CHECK (IS_CONCURRENT_ IN (1,0)) ENABLE;
 
  ALTER TABLE ACTIVITI.ACT_RU_EXECUTION ADD CHECK (IS_SCOPE_ IN (1,0)) ENABLE;
 
  ALTER TABLE ACTIVITI.ACT_RU_EXECUTION ADD CHECK (IS_EVENT_SCOPE_ IN (1,0)) ENABLE;
 
  ALTER TABLE ACTIVITI.ACT_RU_EXECUTION ADD PRIMARY KEY (ID_) ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_HI_DETAIL
--------------------------------------------------------

  ALTER TABLE ACTIVITI.ACT_HI_DETAIL MODIFY (ID_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_HI_DETAIL MODIFY (TYPE_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_HI_DETAIL MODIFY (PROC_INST_ID_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_HI_DETAIL MODIFY (EXECUTION_ID_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_HI_DETAIL MODIFY (NAME_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_HI_DETAIL MODIFY (TIME_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_HI_DETAIL ADD PRIMARY KEY (ID_) ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_RU_IDENTITYLINK
--------------------------------------------------------

  ALTER TABLE ACTIVITI.ACT_RU_IDENTITYLINK ADD PRIMARY KEY (ID_) ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_HI_COMMENT
--------------------------------------------------------

  ALTER TABLE ACTIVITI.ACT_HI_COMMENT MODIFY (ID_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_HI_COMMENT MODIFY (TIME_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_HI_COMMENT ADD PRIMARY KEY (ID_) ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_ID_INFO
--------------------------------------------------------

  ALTER TABLE ACTIVITI.ACT_ID_INFO ADD PRIMARY KEY (ID_) ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_HI_PROCINST
--------------------------------------------------------

  ALTER TABLE ACTIVITI.ACT_HI_PROCINST MODIFY (ID_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_HI_PROCINST MODIFY (PROC_INST_ID_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_HI_PROCINST MODIFY (PROC_DEF_ID_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_HI_PROCINST MODIFY (START_TIME_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_HI_PROCINST ADD PRIMARY KEY (ID_) ENABLE;
 
  ALTER TABLE ACTIVITI.ACT_HI_PROCINST ADD UNIQUE (PROC_INST_ID_) ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_HI_ACTINST
--------------------------------------------------------

  ALTER TABLE ACTIVITI.ACT_HI_ACTINST MODIFY (ID_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_HI_ACTINST MODIFY (PROC_DEF_ID_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_HI_ACTINST MODIFY (PROC_INST_ID_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_HI_ACTINST MODIFY (EXECUTION_ID_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_HI_ACTINST MODIFY (ACT_ID_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_HI_ACTINST MODIFY (ACT_TYPE_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_HI_ACTINST MODIFY (START_TIME_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_HI_ACTINST ADD PRIMARY KEY (ID_) ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_RU_TASK
--------------------------------------------------------

  ALTER TABLE ACTIVITI.ACT_RU_TASK ADD PRIMARY KEY (ID_) ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_HI_ATTACHMENT
--------------------------------------------------------

  ALTER TABLE ACTIVITI.ACT_HI_ATTACHMENT MODIFY (ID_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_HI_ATTACHMENT ADD PRIMARY KEY (ID_) ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_RU_EVENT_SUBSCR
--------------------------------------------------------

  ALTER TABLE ACTIVITI.ACT_RU_EVENT_SUBSCR MODIFY (ID_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_RU_EVENT_SUBSCR MODIFY (EVENT_TYPE_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_RU_EVENT_SUBSCR MODIFY (CREATED_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_RU_EVENT_SUBSCR ADD PRIMARY KEY (ID_) ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_ID_USER
--------------------------------------------------------

  ALTER TABLE ACTIVITI.ACT_ID_USER ADD PRIMARY KEY (ID_) ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_GE_BYTEARRAY
--------------------------------------------------------

  ALTER TABLE ACTIVITI.ACT_GE_BYTEARRAY ADD CHECK (GENERATED_ IN (1,0)) ENABLE;
 
  ALTER TABLE ACTIVITI.ACT_GE_BYTEARRAY ADD PRIMARY KEY (ID_) ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_HI_TASKINST
--------------------------------------------------------

  ALTER TABLE ACTIVITI.ACT_HI_TASKINST MODIFY (ID_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_HI_TASKINST MODIFY (START_TIME_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_HI_TASKINST ADD PRIMARY KEY (ID_) ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_RE_DEPLOYMENT
--------------------------------------------------------

  ALTER TABLE ACTIVITI.ACT_RE_DEPLOYMENT ADD PRIMARY KEY (ID_) ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_RE_PROCDEF
--------------------------------------------------------

  ALTER TABLE ACTIVITI.ACT_RE_PROCDEF ADD CONSTRAINT ACT_UNIQ_PROCDEF UNIQUE (KEY_, VERSION_) ENABLE;
 
  ALTER TABLE ACTIVITI.ACT_RE_PROCDEF ADD CHECK (HAS_START_FORM_KEY_ IN (1,0)) ENABLE;
 
  ALTER TABLE ACTIVITI.ACT_RE_PROCDEF ADD PRIMARY KEY (ID_) ENABLE;
--------------------------------------------------------
--  Constraints for Table ACT_RU_VARIABLE
--------------------------------------------------------

  ALTER TABLE ACTIVITI.ACT_RU_VARIABLE MODIFY (ID_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_RU_VARIABLE MODIFY (TYPE_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_RU_VARIABLE MODIFY (NAME_ NOT NULL ENABLE);
 
  ALTER TABLE ACTIVITI.ACT_RU_VARIABLE ADD PRIMARY KEY (ID_) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ACT_GE_BYTEARRAY
--------------------------------------------------------

  ALTER TABLE ACTIVITI.ACT_GE_BYTEARRAY ADD CONSTRAINT ACT_FK_BYTEARR_DEPL FOREIGN KEY (DEPLOYMENT_ID_)
	  REFERENCES ACTIVITI.ACT_RE_DEPLOYMENT (ID_) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ACT_ID_MEMBERSHIP
--------------------------------------------------------

  ALTER TABLE ACTIVITI.ACT_ID_MEMBERSHIP ADD CONSTRAINT ACT_FK_MEMB_GROUP FOREIGN KEY (GROUP_ID_)
	  REFERENCES ACTIVITI.ACT_ID_GROUP (ID_) ENABLE;
 
  ALTER TABLE ACTIVITI.ACT_ID_MEMBERSHIP ADD CONSTRAINT ACT_FK_MEMB_USER FOREIGN KEY (USER_ID_)
	  REFERENCES ACTIVITI.ACT_ID_USER (ID_) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ACT_RU_EVENT_SUBSCR
--------------------------------------------------------

  ALTER TABLE ACTIVITI.ACT_RU_EVENT_SUBSCR ADD CONSTRAINT ACT_FK_EVENT_EXEC FOREIGN KEY (EXECUTION_ID_)
	  REFERENCES ACTIVITI.ACT_RU_EXECUTION (ID_) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ACT_RU_EXECUTION
--------------------------------------------------------

  ALTER TABLE ACTIVITI.ACT_RU_EXECUTION ADD CONSTRAINT ACT_FK_EXE_PARENT FOREIGN KEY (PARENT_ID_)
	  REFERENCES ACTIVITI.ACT_RU_EXECUTION (ID_) ENABLE;
 
  ALTER TABLE ACTIVITI.ACT_RU_EXECUTION ADD CONSTRAINT ACT_FK_EXE_PROCINST FOREIGN KEY (PROC_INST_ID_)
	  REFERENCES ACTIVITI.ACT_RU_EXECUTION (ID_) ENABLE;
 
  ALTER TABLE ACTIVITI.ACT_RU_EXECUTION ADD CONSTRAINT ACT_FK_EXE_SUPER FOREIGN KEY (SUPER_EXEC_)
	  REFERENCES ACTIVITI.ACT_RU_EXECUTION (ID_) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ACT_RU_IDENTITYLINK
--------------------------------------------------------

  ALTER TABLE ACTIVITI.ACT_RU_IDENTITYLINK ADD CONSTRAINT ACT_FK_ATHRZ_PROCEDEF FOREIGN KEY (PROC_DEF_ID_)
	  REFERENCES ACTIVITI.ACT_RE_PROCDEF (ID_) ENABLE;
 
  ALTER TABLE ACTIVITI.ACT_RU_IDENTITYLINK ADD CONSTRAINT ACT_FK_TSKASS_TASK FOREIGN KEY (TASK_ID_)
	  REFERENCES ACTIVITI.ACT_RU_TASK (ID_) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ACT_RU_JOB
--------------------------------------------------------

  ALTER TABLE ACTIVITI.ACT_RU_JOB ADD CONSTRAINT ACT_FK_JOB_EXCEPTION FOREIGN KEY (EXCEPTION_STACK_ID_)
	  REFERENCES ACTIVITI.ACT_GE_BYTEARRAY (ID_) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ACT_RU_TASK
--------------------------------------------------------

  ALTER TABLE ACTIVITI.ACT_RU_TASK ADD CONSTRAINT ACT_FK_TASK_EXE FOREIGN KEY (EXECUTION_ID_)
	  REFERENCES ACTIVITI.ACT_RU_EXECUTION (ID_) ENABLE;
 
  ALTER TABLE ACTIVITI.ACT_RU_TASK ADD CONSTRAINT ACT_FK_TASK_PROCDEF FOREIGN KEY (PROC_DEF_ID_)
	  REFERENCES ACTIVITI.ACT_RE_PROCDEF (ID_) ENABLE;
 
  ALTER TABLE ACTIVITI.ACT_RU_TASK ADD CONSTRAINT ACT_FK_TASK_PROCINST FOREIGN KEY (PROC_INST_ID_)
	  REFERENCES ACTIVITI.ACT_RU_EXECUTION (ID_) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ACT_RU_VARIABLE
--------------------------------------------------------

  ALTER TABLE ACTIVITI.ACT_RU_VARIABLE ADD CONSTRAINT ACT_FK_VAR_BYTEARRAY FOREIGN KEY (BYTEARRAY_ID_)
	  REFERENCES ACTIVITI.ACT_GE_BYTEARRAY (ID_) ENABLE;
 
  ALTER TABLE ACTIVITI.ACT_RU_VARIABLE ADD CONSTRAINT ACT_FK_VAR_EXE FOREIGN KEY (EXECUTION_ID_)
	  REFERENCES ACTIVITI.ACT_RU_EXECUTION (ID_) ENABLE;
 
  ALTER TABLE ACTIVITI.ACT_RU_VARIABLE ADD CONSTRAINT ACT_FK_VAR_PROCINST FOREIGN KEY (PROC_INST_ID_)
	  REFERENCES ACTIVITI.ACT_RU_EXECUTION (ID_) ENABLE;
