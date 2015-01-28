--------------------------------------------------------
--  File created - Thursday-May-22-2014   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table ADDRESS
--------------------------------------------------------

  CREATE TABLE IAMUSER.ADDRESS 
   (	ADDRESS_ID VARCHAR2(32), 
	NAME VARCHAR2(100), 
	COUNTRY VARCHAR2(100), 
	BLDG_NUM VARCHAR2(100), 
	STREET_DIRECTION VARCHAR2(20), 
	SUITE VARCHAR2(20), 
	ADDRESS1 VARCHAR2(45), 
	ADDRESS2 VARCHAR2(45), 
	ADDRESS3 VARCHAR2(45), 
	ADDRESS4 VARCHAR2(45), 
	ADDRESS5 VARCHAR2(45), 
	ADDRESS6 VARCHAR2(45), 
	ADDRESS7 VARCHAR2(45), 
	CITY VARCHAR2(100), 
	STATE VARCHAR2(100), 
	POSTAL_CD VARCHAR2(100), 
	IS_DEFAULT CHAR(1) DEFAULT ('N'), 
	DESCRIPTION VARCHAR2(100), 
	ACTIVE CHAR(1) DEFAULT ('Y'), 
	PARENT_ID VARCHAR2(32), 
	LAST_UPDATE DATE, 
	CREATE_DATE DATE DEFAULT CURRENT_DATE, 
	TYPE_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table APPROVER_ASSOC
--------------------------------------------------------

  CREATE TABLE IAMUSER.APPROVER_ASSOC 
   (	APPROVER_ASSOC_ID VARCHAR2(32), 
	REQUEST_TYPE VARCHAR2(32), 
	APPROVER_LEVEL NUMBER(4,0) DEFAULT (1), 
	ASSOCIATION_TYPE VARCHAR2(20), 
	ASSOCIATION_ENTITY_ID VARCHAR2(32), 
	ON_APPROVE_ENTITY_ID VARCHAR2(32), 
	ON_REJECT_ENTITY_ID VARCHAR2(32), 
	ON_APPROVE_ENTITY_TYPE VARCHAR2(20), 
	ON_REJECT_ENTITY_TYPE VARCHAR2(20), 
	APPROVER_ENTITY_TYPE VARCHAR2(20), 
	APPROVER_ENTITY_ID VARCHAR2(32), 
	APPLY_DELEGATION_FILTER CHAR(1) DEFAULT 'N'
   ) ;
--------------------------------------------------------
--  DDL for Table ATTRIBUTE_MAP
--------------------------------------------------------

  CREATE TABLE IAMUSER.ATTRIBUTE_MAP 
   (	ATTRIBUTE_MAP_ID VARCHAR2(32), 
	MANAGED_SYS_ID VARCHAR2(32) DEFAULT NULL, 
	RESOURCE_ID VARCHAR2(32), 
	MAP_FOR_OBJECT_TYPE VARCHAR2(20) DEFAULT ('USER'), 
	ATTRIBUTE_NAME VARCHAR2(50), 
	TARGET_ATTRIBUTE_NAME VARCHAR2(50), 
	AUTHORITATIVE_SRC NUMBER(4,0) DEFAULT (0), 
	ATTRIBUTE_POLICY_ID VARCHAR2(32), 
	RULE_TEXT CLOB, 
	STATUS VARCHAR2(20) DEFAULT 'ACTIVE', 
	START_DATE DATE, 
	END_DATE DATE, 
	STORE_IN_IAMDB NUMBER(4,0) DEFAULT (0), 
	DATA_TYPE VARCHAR2(20), 
	DEFAULT_VALUE VARCHAR2(32), 
	SYNCH_CONFIG_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table AUTH_ATTRIBUTE
--------------------------------------------------------

  CREATE TABLE IAMUSER.AUTH_ATTRIBUTE 
   (	AUTH_ATTRIBUTE_ID VARCHAR2(32), 
	ATTRIBUTE_NAME VARCHAR2(100), 
	PROVIDER_TYPE VARCHAR2(32), 
	DESCRIPTION VARCHAR2(255), 
	REQUIRED CHAR(1) DEFAULT ('N'), 
	DATA_TYPE VARCHAR2(20) DEFAULT ('singleValue'), 
	DEFAULT_VALUE VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table AUTH_LEVEL
--------------------------------------------------------

  CREATE TABLE IAMUSER.AUTH_LEVEL 
   (	AUTH_LEVEL_ID VARCHAR2(32), 
	AUTH_LEVEL_NAME VARCHAR2(100), 
	AUTH_LEVEL_DIG NUMBER(3,0)
   ) ;
--------------------------------------------------------
--  DDL for Table AUTH_PROVIDER
--------------------------------------------------------

  CREATE TABLE IAMUSER.AUTH_PROVIDER 
   (	PROVIDER_ID VARCHAR2(32), 
	PROVIDER_TYPE VARCHAR2(32), 
	MANAGED_SYS_ID VARCHAR2(32), 
	RESOURCE_ID VARCHAR2(32), 
	NAME VARCHAR2(50), 
	DESCRIPTION VARCHAR2(255), 
	SIGN_REQUEST CHAR(1) DEFAULT ('N'), 
	PUBLIC_KEY BLOB, 
	PRIVATE_KEY BLOB
   ) ;
--------------------------------------------------------
--  DDL for Table AUTH_PROVIDER_ATTRIBUTE
--------------------------------------------------------

  CREATE TABLE IAMUSER.AUTH_PROVIDER_ATTRIBUTE 
   (	PROVIDER_ATTRIBUTE_ID VARCHAR2(32), 
	PROVIDER_ID VARCHAR2(32), 
	AUTH_ATTRIBUTE_ID VARCHAR2(32), 
	VALUE VARCHAR2(255), 
	DATA_TYPE VARCHAR2(20) DEFAULT ('singleValue')
   ) ;
--------------------------------------------------------
--  DDL for Table AUTH_PROVIDER_TYPE
--------------------------------------------------------

  CREATE TABLE IAMUSER.AUTH_PROVIDER_TYPE 
   (	PROVIDER_TYPE VARCHAR2(32), 
	DESCRIPTION VARCHAR2(40), 
	ACTIVE CHAR(1) DEFAULT ('Y')
   ) ;
--------------------------------------------------------
--  DDL for Table AUTH_RESOURCE_AM_ATTRIBUTE
--------------------------------------------------------

  CREATE TABLE IAMUSER.AUTH_RESOURCE_AM_ATTRIBUTE 
   (	AM_RES_ATTRIBUTE_ID VARCHAR2(32), 
	REFLECTION_KEY VARCHAR2(255), 
	ATTRIBUTE_NAME VARCHAR2(100)
   ) ;
--------------------------------------------------------
--  DDL for Table AUTH_RESOURCE_ATTRIBUTE_MAP
--------------------------------------------------------

  CREATE TABLE IAMUSER.AUTH_RESOURCE_ATTRIBUTE_MAP 
   (	ATTRIBUTE_MAP_ID VARCHAR2(32), 
	PROVIDER_ID VARCHAR2(32), 
	TARGET_ATTRIBUTE_NAME VARCHAR2(100), 
	AM_RES_ATTRIBUTE_ID VARCHAR2(32), 
	AM_POLICY_URL VARCHAR2(100) DEFAULT NULL, 
	ATTRIBUTE_VALUE VARCHAR2(100) DEFAULT NULL, 
	ATTRIBUTE_TYPE VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table AUTH_STATE
--------------------------------------------------------

  CREATE TABLE IAMUSER.AUTH_STATE 
   (	USER_ID VARCHAR2(32), 
	AUTH_STATE NUMBER(5,1), 
	TOKEN VARCHAR2(2000), 
	AA VARCHAR2(20), 
	EXPIRATION NUMBER(18,0), 
	LAST_LOGIN DATE, 
	IP_ADDRESS VARCHAR2(20)
   ) ;
--------------------------------------------------------
--  DDL for Table BATCH_CONFIG
--------------------------------------------------------

  CREATE TABLE IAMUSER.BATCH_CONFIG 
   (	TASK_ID VARCHAR2(32), 
	TASK_NAME VARCHAR2(50), 
	LAST_EXEC_TIME DATE, 
	TASK_URL VARCHAR2(255), 
	EXECUTION_ORDER NUMBER(4,0) DEFAULT (1), 
	STATUS VARCHAR2(20), 
	PARAM1 VARCHAR2(255), 
	PARAM2 VARCHAR2(255), 
	PARAM3 VARCHAR2(255), 
	PARAM4 VARCHAR2(255), 
	RULE_TYPE VARCHAR2(20), 
	ENABLED CHAR(1) DEFAULT 'N', 
	LAST_MODIFIED_DATETIME TIMESTAMP (6), 
	RUN_ON TIMESTAMP (6), 
	CRON_EXPRESSION VARCHAR2(100), 
	SPRING_BEAN VARCHAR2(100), 
	SPRING_BEAN_METHOD VARCHAR2(100)
   ) ;
--------------------------------------------------------
--  DDL for Table CATEGORY
--------------------------------------------------------

  CREATE TABLE IAMUSER.CATEGORY 
   (	CATEGORY_ID VARCHAR2(32), 
	PARENT_ID VARCHAR2(32), 
	CATEGORY_NAME VARCHAR2(40), 
	CATEGORY_DESC VARCHAR2(80), 
	CREATE_DATE DATE, 
	CREATED_BY VARCHAR2(20), 
	SHOW_LIST NUMBER(4,0) DEFAULT (0), 
	DISPLAY_ORDER NUMBER(4,0) DEFAULT (0)
   ) ;
--------------------------------------------------------
--  DDL for Table CATEGORY_LANGUAGE
--------------------------------------------------------

  CREATE TABLE IAMUSER.CATEGORY_LANGUAGE 
   (	CATEGORY_ID VARCHAR2(32), 
	LANGUAGE_ID VARCHAR2(32), 
	CATEGORY_NAME VARCHAR2(40)
   ) ;
--------------------------------------------------------
--  DDL for Table CATEGORY_TYPE
--------------------------------------------------------

  CREATE TABLE IAMUSER.CATEGORY_TYPE 
   (	CATEGORY_ID VARCHAR2(32), 
	TYPE_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table COMPANY
--------------------------------------------------------

  CREATE TABLE IAMUSER.COMPANY 
   (	COMPANY_ID VARCHAR2(32), 
	COMPANY_NAME VARCHAR2(200), 
	LST_UPDATE DATE, 
	LST_UPDATED_BY VARCHAR2(40), 
	PARENT_ID VARCHAR2(32), 
	STATUS VARCHAR2(20), 
	CREATE_DATE DATE, 
	CREATED_BY VARCHAR2(40), 
	ALIAS VARCHAR2(200), 
	DESCRIPTION VARCHAR2(200), 
	DOMAIN_NAME VARCHAR2(250), 
	LDAP_STR VARCHAR2(255), 
	CLASSIFICATION VARCHAR2(40), 
	INTERNAL_COMPANY_ID VARCHAR2(200), 
	ABBREVIATION VARCHAR2(20), 
	SYMBOL VARCHAR2(10), 
	ORG_TYPE_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table COMPANY_ATTRIBUTE
--------------------------------------------------------

  CREATE TABLE IAMUSER.COMPANY_ATTRIBUTE 
   (	COMPANY_ATTR_ID VARCHAR2(32), 
	COMPANY_ID VARCHAR2(32), 
	NAME VARCHAR2(100), 
	VALUE VARCHAR2(255), 
	METADATA_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table COMPANY_TO_COMPANY_MEMBERSHIP
--------------------------------------------------------

  CREATE TABLE IAMUSER.COMPANY_TO_COMPANY_MEMBERSHIP 
   (	COMPANY_ID VARCHAR2(32), 
	MEMBER_COMPANY_ID VARCHAR2(32), 
	CREATE_DATE DATE, 
	UPDATE_DATE DATE, 
	CREATED_BY VARCHAR2(32), 
	UPDATED_BY VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table CONTENT_PROVIDER
--------------------------------------------------------

  CREATE TABLE IAMUSER.CONTENT_PROVIDER 
   (	CONTENT_PROVIDER_ID VARCHAR2(32), 
	CONTENT_PROVIDER_NAME VARCHAR2(100), 
	IS_PUBLIC CHAR(1) DEFAULT ('N'), 
	MIN_AUTH_LEVEL VARCHAR2(32), 
	DOMAIN_PATTERN VARCHAR2(100), 
	IS_SSL CHAR(1), 
	RESOURCE_ID VARCHAR2(32), 
	MANAGED_SYS_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table CONTENT_PROVIDER_SERVER
--------------------------------------------------------

  CREATE TABLE IAMUSER.CONTENT_PROVIDER_SERVER 
   (	CONTENT_PROVIDER_SERVER_ID VARCHAR2(32), 
	CONTENT_PROVIDER_ID VARCHAR2(32), 
	SERVER_URL VARCHAR2(100)
   ) ;
--------------------------------------------------------
--  DDL for Table CREDENTIALS
--------------------------------------------------------

  CREATE TABLE IAMUSER.CREDENTIALS 
   (	USER_ID VARCHAR2(32), 
	CREDENTIAL_TYPE VARCHAR2(20), 
	VALUE VARCHAR2(100), 
	IS_PUBLIC NUMBER(1,0)
   ) ;
--------------------------------------------------------
--  DDL for Table DEF_RECON_ATTR_MAP
--------------------------------------------------------

  CREATE TABLE IAMUSER.DEF_RECON_ATTR_MAP 
   (	DEF_ATTR_MAP_ID VARCHAR2(32), 
	DEF_ATTR_MAP_NAME VARCHAR2(100)
   ) ;
--------------------------------------------------------
--  DDL for Table EMAIL_ADDRESS
--------------------------------------------------------

  CREATE TABLE IAMUSER.EMAIL_ADDRESS 
   (	EMAIL_ID VARCHAR2(32), 
	NAME VARCHAR2(100), 
	DESCRIPTION VARCHAR2(100), 
	EMAIL_ADDRESS VARCHAR2(320), 
	IS_DEFAULT CHAR(1) DEFAULT ('N'), 
	ACTIVE CHAR(1) DEFAULT ('Y'), 
	PARENT_ID VARCHAR2(32), 
	LAST_UPDATE DATE, 
	CREATE_DATE DATE DEFAULT CURRENT_DATE, 
	TYPE_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table ENTITLEMENT
--------------------------------------------------------

  CREATE TABLE IAMUSER.ENTITLEMENT 
   (	ENTITLEMENT_ID VARCHAR2(32), 
	ENTITLEMENT_NAME VARCHAR2(40), 
	ENTITLEMENT_VALUE VARCHAR2(80), 
	DESCRIPTION VARCHAR2(255), 
	CREATE_DATE DATE, 
	CREATED_BY VARCHAR2(20)
   ) ;
--------------------------------------------------------
--  DDL for Table EXCLUDE_WORD_LIST
--------------------------------------------------------

  CREATE TABLE IAMUSER.EXCLUDE_WORD_LIST 
   (	WORD VARCHAR2(30), 
	LANGUAGE_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table GRP
--------------------------------------------------------

  CREATE TABLE IAMUSER.GRP 
   (	GRP_ID VARCHAR2(32), 
	GRP_NAME VARCHAR2(80), 
	CREATE_DATE DATE, 
	CREATED_BY VARCHAR2(20), 
	COMPANY_ID VARCHAR2(32), 
	GROUP_DESC VARCHAR2(80), 
	STATUS VARCHAR2(20), 
	LAST_UPDATE DATE, 
	LAST_UPDATED_BY VARCHAR2(32), 
	MANAGED_SYS_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table GRP_ATTRIBUTES
--------------------------------------------------------

  CREATE TABLE IAMUSER.GRP_ATTRIBUTES 
   (	ID VARCHAR2(32), 
	GRP_ID VARCHAR2(32), 
	METADATA_ID VARCHAR2(20), 
	NAME VARCHAR2(20), 
	VALUE VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table GRP_ROLE
--------------------------------------------------------

  CREATE TABLE IAMUSER.GRP_ROLE 
   (	GRP_ID VARCHAR2(32), 
	ROLE_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table GRP_TO_GRP_MEMBERSHIP
--------------------------------------------------------

  CREATE TABLE IAMUSER.GRP_TO_GRP_MEMBERSHIP 
   (	GROUP_ID VARCHAR2(32), 
	MEMBER_GROUP_ID VARCHAR2(32), 
	CREATE_DATE DATE, 
	UPDATE_DATE DATE, 
	CREATED_BY VARCHAR2(32), 
	UPDATED_BY VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table IDENTITY_QUESTION
--------------------------------------------------------

  CREATE TABLE IAMUSER.IDENTITY_QUESTION 
   (	IDENTITY_QUESTION_ID VARCHAR2(32), 
	IDENTITY_QUEST_GRP_ID VARCHAR2(32), 
	QUESTION_TEXT VARCHAR2(255), 
	ACTIVE CHAR(1) DEFAULT ('Y')
   ) ;
--------------------------------------------------------
--  DDL for Table IDENTITY_QUEST_GRP
--------------------------------------------------------

  CREATE TABLE IAMUSER.IDENTITY_QUEST_GRP 
   (	IDENTITY_QUEST_GRP_ID VARCHAR2(32), 
	NAME VARCHAR2(60), 
	STATUS VARCHAR2(20), 
	COMPANY_OWNER_ID VARCHAR2(32), 
	CREATE_DATE DATE, 
	CREATED_BY VARCHAR2(20), 
	LAST_UPDATE DATE, 
	LAST_UPDATED_BY VARCHAR2(20)
   ) ;
--------------------------------------------------------
--  DDL for Table IMAGE
--------------------------------------------------------

  CREATE TABLE IAMUSER.IMAGE 
   (	IMAGE_ID VARCHAR2(20), 
	IMAGE_FILE VARCHAR2(80), 
	IMAGE_TYPE VARCHAR2(20), 
	DESCRIPTION VARCHAR2(250), 
	MIME_TYPE VARCHAR2(30), 
	URL VARCHAR2(100), 
	FILE_SIZE NUMBER(4,0), 
	IMAGE_BLOB BLOB
   ) ;
--------------------------------------------------------
--  DDL for Table IT_POLICY
--------------------------------------------------------

  CREATE TABLE IAMUSER.IT_POLICY 
   (	IT_POLICY_ID VARCHAR2(32), 
	APPROVE_TYPE VARCHAR2(64) DEFAULT 'Once', 
	ACTIVE CHAR(1) DEFAULT 'N', 
	CREATE_DATE TIMESTAMP (6), 
	UPDATE_DATE TIMESTAMP (6), 
	CREATED_BY VARCHAR2(32), 
	UPDATED_BY VARCHAR2(32), 
	POLICY_CONTENT CLOB, 
	CONFIRMATION VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table LANGUAGE
--------------------------------------------------------

  CREATE TABLE IAMUSER.LANGUAGE 
   (	ID VARCHAR2(32), 
	LANGUAGE VARCHAR2(20), 
	IS_USED VARCHAR2(1) DEFAULT ('N'), 
	LANGUAGE_CODE VARCHAR2(2), 
	IS_DEFAULT CHAR(1) DEFAULT 'N'
   ) ;
--------------------------------------------------------
--  DDL for Table LANGUAGE_LOCALE
--------------------------------------------------------

  CREATE TABLE IAMUSER.LANGUAGE_LOCALE 
   (	ID VARCHAR2(32), 
	LANGUAGE_ID VARCHAR2(32), 
	LOCALE VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table LANGUAGE_MAPPING
--------------------------------------------------------

  CREATE TABLE IAMUSER.LANGUAGE_MAPPING 
   (	ID VARCHAR2(32), 
	LANGUAGE_ID VARCHAR2(32), 
	REFERENCE_ID VARCHAR2(32), 
	REFERENCE_TYPE VARCHAR2(100), 
	TEXT_VALUE VARCHAR2(400)
   ) ;
--------------------------------------------------------
--  DDL for Table LOCATION
--------------------------------------------------------

  CREATE TABLE IAMUSER.LOCATION 
   (	LOCATION_ID VARCHAR2(32), 
	NAME VARCHAR2(80), 
	DESCRIPTION VARCHAR2(255), 
	COUNTRY VARCHAR2(30), 
	BLDG_NUM VARCHAR2(10), 
	STREET_DIRECTION VARCHAR2(20), 
	ADDRESS1 VARCHAR2(45), 
	ADDRESS2 VARCHAR2(45), 
	ADDRESS3 VARCHAR2(45), 
	CITY VARCHAR2(30), 
	STATE VARCHAR2(15), 
	POSTAL_CD VARCHAR2(10), 
	ORGANIZATION_ID VARCHAR2(32), 
	INTERNAL_LOCATION_ID VARCHAR2(32), 
	ACTIVE NUMBER(4,0) DEFAULT (1), 
	SENSITIVE_LOCATION NUMBER(4,0) DEFAULT (0)
   ) ;
--------------------------------------------------------
--  DDL for Table LOGIN
--------------------------------------------------------

  CREATE TABLE IAMUSER.LOGIN 
   (	SERVICE_ID VARCHAR2(20), 
	LOGIN VARCHAR2(320), 
	LOWERCASE_LOGIN VARCHAR2(320), 
	MANAGED_SYS_ID VARCHAR2(32), 
	IDENTITY_TYPE VARCHAR2(20), 
	CANONICAL_NAME VARCHAR2(100), 
	USER_ID VARCHAR2(32), 
	PASSWORD VARCHAR2(255), 
	PWD_EQUIVALENT_TOKEN VARCHAR2(255), 
	PWD_CHANGED DATE, 
	PWD_EXP DATE, 
	RESET_PWD NUMBER(4,0) DEFAULT (0), 
	FIRST_TIME_LOGIN NUMBER(4,0) DEFAULT (1), 
	IS_LOCKED NUMBER(4,0) DEFAULT (0), 
	STATUS VARCHAR2(20), 
	GRACE_PERIOD DATE, 
	CREATE_DATE DATE, 
	CREATED_BY VARCHAR2(32), 
	CURRENT_LOGIN_HOST VARCHAR2(40), 
	AUTH_FAIL_COUNT NUMBER(4,0) DEFAULT (0), 
	LAST_AUTH_ATTEMPT DATE, 
	LAST_LOGIN DATE, 
	LAST_LOGIN_IP VARCHAR2(60), 
	PREV_LOGIN DATE, 
	PREV_LOGIN_IP VARCHAR2(60), 
	IS_DEFAULT NUMBER(4,0) DEFAULT (0), 
	PWD_CHANGE_COUNT NUMBER(4,0) DEFAULT (0), 
	PSWD_RESET_TOKEN VARCHAR2(80), 
	PSWD_RESET_TOKEN_EXP DATE, 
	LOGIN_ID VARCHAR2(32), 
	LAST_UPDATE DATE
   ) ;
--------------------------------------------------------
--  DDL for Table LOGIN_ATTRIBUTE
--------------------------------------------------------

  CREATE TABLE IAMUSER.LOGIN_ATTRIBUTE 
   (	LOGIN_ATTR_ID VARCHAR2(32), 
	NAME VARCHAR2(20), 
	VALUE VARCHAR2(255), 
	METADATA_ID VARCHAR2(20), 
	ATTR_GROUP VARCHAR2(20), 
	LOGIN_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table MANAGED_SYS
--------------------------------------------------------

  CREATE TABLE IAMUSER.MANAGED_SYS 
   (	MANAGED_SYS_ID VARCHAR2(32), 
	NAME VARCHAR2(40), 
	DESCRIPTION VARCHAR2(80), 
	STATUS VARCHAR2(20), 
	CONNECTOR_ID VARCHAR2(32), 
	DOMAIN_ID VARCHAR2(20), 
	HOST_URL VARCHAR2(80), 
	APPL_URL VARCHAR2(80), 
	PORT NUMBER(4,0), 
	COMM_PROTOCOL VARCHAR2(20), 
	USER_ID VARCHAR2(150), 
	PSWD VARCHAR2(255), 
	START_DATE DATE, 
	END_DATE DATE, 
	RESOURCE_ID VARCHAR2(32), 
	PRIMARY_REPOSITORY NUMBER(4,0), 
	SECONDARY_REPOSITORY_ID VARCHAR2(32), 
	ALWAYS_UPDATE_SECONDARY NUMBER(4,0), 
	RES_DEPENDENCY VARCHAR2(32), 
	ADD_HNDLR VARCHAR2(100), 
	MODIFY_HNDLR VARCHAR2(100), 
	DELETE_HNDLR VARCHAR2(100), 
	SETPASS_HNDLR VARCHAR2(100), 
	SUSPEND_HNDLR VARCHAR2(100), 
	SEARCH_HNDLR VARCHAR2(100), 
	LOOKUP_HNDLR VARCHAR2(100), 
	TEST_CONNECTION_HNDLR VARCHAR2(100), 
	RECONCILE_RESOURCE_HNDLR VARCHAR2(100), 
	HNDLR_5 VARCHAR2(100), 
	DRIVER_URL VARCHAR2(100), 
	CONNECTION_STRING VARCHAR2(100), 
	ATTRIBUTE_NAMES_LOOKUP VARCHAR2(120), 
	ATTRIBUTE_NAMES_HNDLR VARCHAR2(100), 
	SEARCH_SCOPE NUMBER(*,0) DEFAULT 2
   ) ;
--------------------------------------------------------
--  DDL for Table MANAGED_SYS_ATTRIBUTE
--------------------------------------------------------

  CREATE TABLE IAMUSER.MANAGED_SYS_ATTRIBUTE 
   (	MNG_SYS_ATTR_ID VARCHAR2(32), 
	NAME VARCHAR2(60), 
	VALUE1 VARCHAR2(255), 
	VALUE2 VARCHAR2(255), 
	MANAGED_SYS_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table MANAGED_SYS_RULE
--------------------------------------------------------

  CREATE TABLE IAMUSER.MANAGED_SYS_RULE 
   (	MANAGED_SYS_RULE_ID VARCHAR2(32), 
	MANAGED_SYS_ID VARCHAR2(32), 
	MANAGED_SYS_RULE_NAME VARCHAR2(45), 
	MANAGED_SYS_RULE_VALUE VARCHAR2(45)
   ) ;
--------------------------------------------------------
--  DDL for Table MD_ELEMENT_VALID_VALUES
--------------------------------------------------------

  CREATE TABLE IAMUSER.MD_ELEMENT_VALID_VALUES 
   (	ID VARCHAR2(32), 
	METADATA_ELEMENT_ID VARCHAR2(32), 
	UI_VALUE VARCHAR2(200), 
	DISPLAY_ORDER NUMBER(4,0)
   ) ;
--------------------------------------------------------
--  DDL for Table METADATA_ELEMENT
--------------------------------------------------------

  CREATE TABLE IAMUSER.METADATA_ELEMENT 
   (	METADATA_ID VARCHAR2(32), 
	TYPE_ID VARCHAR2(32), 
	ATTRIBUTE_NAME VARCHAR2(50), 
	DESCRIPTION VARCHAR2(40), 
	DATA_TYPE VARCHAR2(20), 
	AUDITABLE CHAR(1) DEFAULT ('Y'), 
	REQUIRED CHAR(1) DEFAULT ('N'), 
	SELF_EDITABLE CHAR(1) DEFAULT ('N'), 
	TEMPLATE_ID VARCHAR2(32), 
	RESOURCE_ID VARCHAR2(32), 
	VALIDATOR VARCHAR2(150), 
	STATIC_DEFAULT_VALUE VARCHAR2(400), 
	IS_PUBLIC CHAR(1) DEFAULT ('Y')
   ) ;
--------------------------------------------------------
--  DDL for Table METADATA_ELEMENT_PAGE_TEMPLATE
--------------------------------------------------------

  CREATE TABLE IAMUSER.METADATA_ELEMENT_PAGE_TEMPLATE 
   (	ID VARCHAR2(32), 
	NAME VARCHAR2(40), 
	RESOURCE_ID VARCHAR2(32), 
	IS_PUBLIC CHAR(1) DEFAULT ('Y'), 
	TEMPLATE_TYPE_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table METADATA_TYPE
--------------------------------------------------------

  CREATE TABLE IAMUSER.METADATA_TYPE 
   (	TYPE_ID VARCHAR2(32), 
	DESCRIPTION VARCHAR2(40), 
	ACTIVE CHAR(1) DEFAULT ('N'), 
	SYNC_MANAGED_SYS CHAR(1) DEFAULT ('N'), 
	GROUPING VARCHAR2(100)
   ) ;
--------------------------------------------------------
--  DDL for Table METADATA_URI_XREF
--------------------------------------------------------

  CREATE TABLE IAMUSER.METADATA_URI_XREF 
   (	TEMPLATE_ID VARCHAR2(32), 
	URI_PATTERN_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table MNG_SYS_GROUP
--------------------------------------------------------

  CREATE TABLE IAMUSER.MNG_SYS_GROUP 
   (	MNG_SYS_GROUP_ID VARCHAR2(32), 
	MANAGED_SYS_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table MNG_SYS_LIST
--------------------------------------------------------

  CREATE TABLE IAMUSER.MNG_SYS_LIST 
   (	MANAGED_SYS_ID VARCHAR2(32), 
	REQUEST_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table MNG_SYS_OBJECT_MATCH
--------------------------------------------------------

  CREATE TABLE IAMUSER.MNG_SYS_OBJECT_MATCH 
   (	OBJECT_SEARCH_ID VARCHAR2(32), 
	MANAGED_SYS_ID VARCHAR2(32), 
	OBJECT_TYPE VARCHAR2(20) DEFAULT 'USER', 
	MATCH_METHOD VARCHAR2(20) DEFAULT 'BASE_DN', 
	BASE_DN VARCHAR2(200), 
	SEARCH_BASE_DN VARCHAR2(200), 
	SEARCH_FILTER VARCHAR2(1000), 
	KEY_FIELD VARCHAR2(40)
   ) ;
--------------------------------------------------------
--  DDL for Table NOTIFICATION_CONFIG
--------------------------------------------------------

  CREATE TABLE IAMUSER.NOTIFICATION_CONFIG 
   (	NOTIFICATION_CONFIG_ID VARCHAR2(32), 
	NAME VARCHAR2(40), 
	SELECTION_PARAM_XML CLOB, 
	MSG_SUBJECT VARCHAR2(100), 
	MSG_TEMPLATE_URL VARCHAR2(100), 
	MSG_FROM VARCHAR2(100), 
	MSG_BCC VARCHAR2(100)
   ) ;
--------------------------------------------------------
--  DDL for Table OPENIAM_LOG
--------------------------------------------------------

  CREATE TABLE IAMUSER.OPENIAM_LOG 
   (	OPENIAM_LOG_ID VARCHAR2(32), 
	USER_ID VARCHAR2(32), 
	PRINCIPAL VARCHAR2(320), 
	MANAGED_SYS_ID VARCHAR2(32), 
	CREATED_DATETIME TIMESTAMP (6), 
	SOURCE VARCHAR2(50), 
	CLIENT_IP VARCHAR2(50), 
	NODE_ID VARCHAR2(50), 
	LOG_ACTION VARCHAR2(50), 
	RESULT VARCHAR2(50), 
	SESSION_ID VARCHAR2(100), 
	HASH VARCHAR2(100), 
	CORRELATION_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table OPENIAM_LOG_ATTRIBUTE
--------------------------------------------------------

  CREATE TABLE IAMUSER.OPENIAM_LOG_ATTRIBUTE 
   (	OPENIAM_LOG_ATTRIBUTE_ID VARCHAR2(32), 
	OPENIAM_LOG_ID VARCHAR2(32), 
	NAME VARCHAR2(100), 
	VALUE CLOB, 
	CREATED_TIMESTAMP LONG
   ) ;
--------------------------------------------------------
--  DDL for Table OPENIAM_LOG_LOG_MEMBERSHIP
--------------------------------------------------------

  CREATE TABLE IAMUSER.OPENIAM_LOG_LOG_MEMBERSHIP 
   (	OPENIAM_LOG_ID VARCHAR2(32), 
	OPENIAM_MEMBER_LOG_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table OPENIAM_LOG_TARGET
--------------------------------------------------------

  CREATE TABLE IAMUSER.OPENIAM_LOG_TARGET 
   (	OPENIAM_LOG_TARGET_ID VARCHAR2(32), 
	OPENIAM_LOG_ID VARCHAR2(32), 
	TARGET_ID VARCHAR2(32), 
	TARGET_TYPE VARCHAR2(70)
   ) ;
--------------------------------------------------------
--  DDL for Table ORGANIZATION_TYPE
--------------------------------------------------------

  CREATE TABLE IAMUSER.ORGANIZATION_TYPE 
   (	ORG_TYPE_ID VARCHAR2(32), 
	NAME VARCHAR2(100), 
	DESCRIPTION VARCHAR2(100)
   ) ;
--------------------------------------------------------
--  DDL for Table ORG_POLICY
--------------------------------------------------------

  CREATE TABLE IAMUSER.ORG_POLICY 
   (	ORG_POLICY_ID VARCHAR2(32), 
	NAME VARCHAR2(40), 
	TARGET_AUDIENCE_TYPE VARCHAR2(20), 
	TARGET_AUDIENCE VARCHAR2(255), 
	START_DATE DATE, 
	END_DATE DATE, 
	POLICY_TEXT CLOB
   ) ;
--------------------------------------------------------
--  DDL for Table ORG_POLICY_USER_LOG
--------------------------------------------------------

  CREATE TABLE IAMUSER.ORG_POLICY_USER_LOG 
   (	ORG_POLICY_LOG_ID VARCHAR2(32), 
	ORG_POLICY_ID VARCHAR2(32), 
	USER_ID VARCHAR2(32), 
	TIME_STAMP DATE, 
	RESPONSE VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table ORG_STRUCTURE
--------------------------------------------------------

  CREATE TABLE IAMUSER.ORG_STRUCTURE 
   (	ORG_STRUCTURE_ID VARCHAR2(32), 
	SUPERVISOR_ID VARCHAR2(32), 
	STAFF_ID VARCHAR2(32), 
	SUPERVISOR_TYPE VARCHAR2(20), 
	IS_PRIMARY_SUPER NUMBER(4,0) DEFAULT (0), 
	START_DATE DATE, 
	END_DATE DATE, 
	STATUS VARCHAR2(20), 
	COMMENTS VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table ORG_TYPE_VALID_MEMBERSHIP
--------------------------------------------------------

  CREATE TABLE IAMUSER.ORG_TYPE_VALID_MEMBERSHIP 
   (	ORG_TYPE_ID VARCHAR2(32), 
	MEMBER_ORG_TYPE_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table PAGE_TEMPLATE_XREF
--------------------------------------------------------

  CREATE TABLE IAMUSER.PAGE_TEMPLATE_XREF 
   (	TEMPLATE_ID VARCHAR2(32), 
	METADATA_ELEMENT_ID VARCHAR2(32), 
	DISPLAY_ORDER NUMBER(4,0)
   ) ;
--------------------------------------------------------
--  DDL for Table PHONE
--------------------------------------------------------

  CREATE TABLE IAMUSER.PHONE 
   (	PHONE_ID VARCHAR2(32), 
	NAME VARCHAR2(40), 
	AREA_CD VARCHAR2(10), 
	COUNTRY_CD VARCHAR2(3), 
	DESCRIPTION VARCHAR2(100), 
	PHONE_NBR VARCHAR2(50), 
	PHONE_EXT VARCHAR2(20), 
	IS_DEFAULT CHAR(1) DEFAULT ('N'), 
	ACTIVE CHAR(1) DEFAULT ('Y'), 
	PARENT_ID VARCHAR2(32), 
	PHONE_TYPE VARCHAR2(20), 
	LAST_UPDATE DATE, 
	CREATE_DATE DATE DEFAULT CURRENT_DATE, 
	TYPE_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table POLICY
--------------------------------------------------------

  CREATE TABLE IAMUSER.POLICY 
   (	POLICY_ID VARCHAR2(32), 
	POLICY_DEF_ID VARCHAR2(32), 
	NAME VARCHAR2(60), 
	DESCRIPTION VARCHAR2(255), 
	STATUS NUMBER(4,0), 
	CREATE_DATE DATE, 
	CREATED_BY VARCHAR2(20), 
	LAST_UPDATE DATE, 
	LAST_UPDATED_BY VARCHAR2(20), 
	RULE_SRC_URL VARCHAR2(80), 
	RULE_TEXT CLOB, 
	ENABLEMENT NUMBER(4,0) DEFAULT (1)
   ) ;
--------------------------------------------------------
--  DDL for Table POLICY_ATTRIBUTE
--------------------------------------------------------

  CREATE TABLE IAMUSER.POLICY_ATTRIBUTE 
   (	POLICY_ATTR_ID VARCHAR2(32), 
	DEF_PARAM_ID VARCHAR2(32), 
	POLICY_ID VARCHAR2(32), 
	NAME VARCHAR2(60), 
	OPERATION VARCHAR2(20), 
	VALUE1 VARCHAR2(255), 
	VALUE2 VARCHAR2(255), 
	RULE_TEXT CLOB, 
	REQUIRED CHAR(1) DEFAULT 'Y'
   ) ;
--------------------------------------------------------
--  DDL for Table POLICY_DEF
--------------------------------------------------------

  CREATE TABLE IAMUSER.POLICY_DEF 
   (	POLICY_DEF_ID VARCHAR2(32), 
	NAME VARCHAR2(60), 
	DESCRIPTION VARCHAR2(255), 
	POLICY_TYPE VARCHAR2(20), 
	LOCATION_TYPE VARCHAR2(20), 
	POLICY_RULE VARCHAR2(500), 
	POLICY_HANDLER VARCHAR2(255), 
	POLICY_ADVISE_HANDLER VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table POLICY_DEF_PARAM
--------------------------------------------------------

  CREATE TABLE IAMUSER.POLICY_DEF_PARAM 
   (	DEF_PARAM_ID VARCHAR2(32), 
	POLICY_DEF_ID VARCHAR2(32), 
	NAME VARCHAR2(60), 
	DESCRIPTION VARCHAR2(255), 
	OPERATION VARCHAR2(20), 
	VALUE1 VARCHAR2(255), 
	VALUE2 VARCHAR2(255), 
	REPEATS NUMBER(4,0), 
	PARAM_GROUP VARCHAR2(20), 
	HANDLER_LANGUAGE VARCHAR2(20), 
	POLICY_PARAM_HANDLER VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table POLICY_OBJECT_ASSOC
--------------------------------------------------------

  CREATE TABLE IAMUSER.POLICY_OBJECT_ASSOC 
   (	POLICY_OBJECT_ID VARCHAR2(32), 
	POLICY_ID VARCHAR2(32), 
	ASSOCIATION_LEVEL VARCHAR2(20), 
	ASSOCIATION_VALUE VARCHAR2(255), 
	OBJECT_TYPE VARCHAR2(100), 
	OBJECT_ID VARCHAR2(32), 
	PARENT_ASSOC_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table PRIVILEGE
--------------------------------------------------------

  CREATE TABLE IAMUSER.PRIVILEGE 
   (	PRIVILEGE_ID VARCHAR2(32), 
	RESOURCE_ID VARCHAR2(32), 
	ABBRV VARCHAR2(3), 
	DESCRIPTION VARCHAR2(40)
   ) ;
--------------------------------------------------------
--  DDL for Table PROVISION_CONNECTOR
--------------------------------------------------------

  CREATE TABLE IAMUSER.PROVISION_CONNECTOR 
   (	CONNECTOR_ID VARCHAR2(32), 
	NAME VARCHAR2(40), 
	METADATA_TYPE_ID VARCHAR2(20), 
	STD_COMPLIANCE_LEVEL VARCHAR2(20), 
	CLIENT_COMM_PROTOCOL VARCHAR2(20), 
	SERVICE_URL VARCHAR2(100), 
	SERVICE_NAMESPACE VARCHAR2(100), 
	SERVICE_PORT VARCHAR2(100), 
	SERVICE_WSDL VARCHAR2(100), 
	CLASS_NAME VARCHAR2(60), 
	HOST VARCHAR2(60), 
	PORT VARCHAR2(10), 
	CONNECTOR_INTERFACE VARCHAR2(20)
   ) ;
--------------------------------------------------------
--  DDL for Table PROV_REQUEST
--------------------------------------------------------

  CREATE TABLE IAMUSER.PROV_REQUEST 
   (	REQUEST_ID VARCHAR2(32), 
	REQUESTOR_ID VARCHAR2(100), 
	REQUEST_DATE DATE, 
	STATUS VARCHAR2(20), 
	STATUS_DATE DATE, 
	REQUEST_REASON VARCHAR2(500), 
	REQUEST_TYPE VARCHAR2(20), 
	CHANGE_ACCESS_BY VARCHAR2(32), 
	REQUEST_XML CLOB, 
	NEW_ROLE_ID VARCHAR2(32), 
	NEW_SERVICE_ID VARCHAR2(20), 
	MANAGED_RESOURCE_ID VARCHAR2(32), 
	REQUEST_FOR_ORG_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table PWD_HISTORY
--------------------------------------------------------

  CREATE TABLE IAMUSER.PWD_HISTORY 
   (	PWD_HISTORY_ID VARCHAR2(32), 
	DATE_CREATED DATE, 
	PASSWORD VARCHAR2(255), 
	LOGIN_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table RECONCILIATION_CONFIG
--------------------------------------------------------

  CREATE TABLE IAMUSER.RECONCILIATION_CONFIG 
   (	RECON_CONFIG_ID VARCHAR2(32), 
	RESOURCE_ID VARCHAR2(32), 
	FREQUENCY VARCHAR2(20), 
	STATUS VARCHAR2(20) DEFAULT 'ACTIVE', 
	CSV_LINE_SEPARATOR VARCHAR2(10) DEFAULT 'comma', 
	CSV_END_OF_LINE VARCHAR2(10) DEFAULT 'enter', 
	NOTIFICATION_EMAIL_ADDRESS VARCHAR2(120), 
	TARGET_SYS_MATCH_SCRIPT VARCHAR2(120), 
	CUSTOM_IDENTITY_MATCH_SCRIPT VARCHAR2(120), 
	MATCH_FIELD_NAME VARCHAR2(40), 
	CUSTOM_MATCH_ATTR VARCHAR2(40), 
	CUSTOM_MATCH_SRC_ATTR VARCHAR2(40), 
	MANAGED_SYS_ID VARCHAR2(32), 
	SEARCH_FILTER VARCHAR2(200), 
	UPDATED_SINCE TIMESTAMP (6), 
	TARGET_SYS_SEARCH_FILTER VARCHAR2(200), 
	MATCH_SCRIPT VARCHAR2(120), 
	EXEC_STATUS VARCHAR2(20), 
	LAST_EXEC_TIME DATE
   ) ;
--------------------------------------------------------
--  DDL for Table RECONCILIATION_SITUATION
--------------------------------------------------------

  CREATE TABLE IAMUSER.RECONCILIATION_SITUATION 
   (	RECON_SITUATION_ID VARCHAR2(32), 
	RECON_CONFIG_ID VARCHAR2(32), 
	SITUATION VARCHAR2(40), 
	SITUATION_RESP VARCHAR2(40), 
	SCRIPT VARCHAR2(80)
   ) ;
--------------------------------------------------------
--  DDL for Table RECON_RES_ATTR_MAP
--------------------------------------------------------

  CREATE TABLE IAMUSER.RECON_RES_ATTR_MAP 
   (	ATTR_POLICY_ID VARCHAR2(32), 
	DEF_RECON_ATTR_MAP_ID VARCHAR2(32), 
	RECON_RES_ATTR_MAP_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table RELATIONSHIP
--------------------------------------------------------

  CREATE TABLE IAMUSER.RELATIONSHIP 
   (	RELATIONSHIP_ID VARCHAR2(20), 
	RELATION_SET_ID VARCHAR2(20), 
	ITEM_OBJ VARCHAR2(20), 
	ITEM_ID VARCHAR2(20), 
	RANK NUMBER(4,0), 
	ACTIVE NUMBER(1,0)
   ) ;
--------------------------------------------------------
--  DDL for Table RELATION_CATEGORY
--------------------------------------------------------

  CREATE TABLE IAMUSER.RELATION_CATEGORY 
   (	RELATION_SET_ID VARCHAR2(20), 
	CATEGORY_ID VARCHAR2(20)
   ) ;
--------------------------------------------------------
--  DDL for Table RELATION_SET
--------------------------------------------------------

  CREATE TABLE IAMUSER.RELATION_SET 
   (	RELATION_SET_ID VARCHAR2(20), 
	DESCRIPTION VARCHAR2(80)
   ) ;
--------------------------------------------------------
--  DDL for Table RELATION_SOURCE
--------------------------------------------------------

  CREATE TABLE IAMUSER.RELATION_SOURCE 
   (	RELATION_TYPE_ID VARCHAR2(20), 
	SOURCE_OBJ VARCHAR2(20), 
	SOURCE_ID VARCHAR2(20), 
	RELATION_SET_ID VARCHAR2(20)
   ) ;
--------------------------------------------------------
--  DDL for Table RELATION_TYPE
--------------------------------------------------------

  CREATE TABLE IAMUSER.RELATION_TYPE 
   (	RELATION_TYPE_ID VARCHAR2(20), 
	DESCRIPTION VARCHAR2(80)
   ) ;
--------------------------------------------------------
--  DDL for Table REPORT_CRITERIA_PARAMETER
--------------------------------------------------------

  CREATE TABLE IAMUSER.REPORT_CRITERIA_PARAMETER 
   (	RCP_ID VARCHAR2(32), 
	REPORT_INFO_ID VARCHAR2(32), 
	PARAM_NAME VARCHAR2(64), 
	PARAM_VALUE VARCHAR2(64) DEFAULT NULL, 
	RCPT_ID VARCHAR2(100) DEFAULT NULL
   ) ;
--------------------------------------------------------
--  DDL for Table REPORT_INFO
--------------------------------------------------------

  CREATE TABLE IAMUSER.REPORT_INFO 
   (	REPORT_INFO_ID VARCHAR2(32), 
	REPORT_NAME VARCHAR2(64), 
	DATASOURCE_FILE_PATH VARCHAR2(255), 
	REPORT_FILE_PATH VARCHAR2(255) DEFAULT NULL
   ) ;
--------------------------------------------------------
--  DDL for Table REPORT_PARAMETER_TYPE
--------------------------------------------------------

  CREATE TABLE IAMUSER.REPORT_PARAMETER_TYPE 
   (	RCPT_ID VARCHAR2(100) DEFAULT NULL, 
	TYPE_NAME VARCHAR2(100) DEFAULT NULL, 
	TYPE_DESCRIPTION VARCHAR2(100) DEFAULT NULL
   ) ;
--------------------------------------------------------
--  DDL for Table REPORT_SUBSCRIPTIONS
--------------------------------------------------------

  CREATE TABLE IAMUSER.REPORT_SUBSCRIPTIONS 
   (	REPORT_SUB_ID VARCHAR2(32), 
	REPORT_INFO_ID VARCHAR2(45) DEFAULT NULL, 
	REPORT_NAME VARCHAR2(64), 
	DELIVERY_METHOD VARCHAR2(45) DEFAULT NULL, 
	DELIVERY_FORMAT VARCHAR2(45) DEFAULT NULL, 
	DELIVERY_AUDIENCE VARCHAR2(45) DEFAULT NULL, 
	STATUS VARCHAR2(45) DEFAULT NULL, 
	USERID VARCHAR2(45) DEFAULT NULL
   ) ;
--------------------------------------------------------
--  DDL for Table REPORT_SUB_CRITERIA_PARAM
--------------------------------------------------------

  CREATE TABLE IAMUSER.REPORT_SUB_CRITERIA_PARAM 
   (	RSCP_ID VARCHAR2(45), 
	RCP_ID VARCHAR2(32) DEFAULT NULL, 
	REPORT_SUB_ID VARCHAR2(32) DEFAULT NULL, 
	PARAM_NAME VARCHAR2(64) DEFAULT NULL, 
	PARAM_VALUE VARCHAR2(64) DEFAULT NULL
   ) ;
--------------------------------------------------------
--  DDL for Table REQUEST_ATTACHMENT
--------------------------------------------------------

  CREATE TABLE IAMUSER.REQUEST_ATTACHMENT 
   (	REQUEST_ATTACHMENT_ID VARCHAR2(32), 
	NAME VARCHAR2(20), 
	ATTACHMENT VARCHAR2(20), 
	USER_ID VARCHAR2(32), 
	ATTACHMENT_DATE DATE, 
	REQUEST_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table REQUEST_ATTRIBUTE
--------------------------------------------------------

  CREATE TABLE IAMUSER.REQUEST_ATTRIBUTE 
   (	REQUEST_ATTR_ID VARCHAR2(32), 
	NAME VARCHAR2(40), 
	VALUE VARCHAR2(255), 
	METADATA_ID VARCHAR2(32), 
	ATTR_GROUP VARCHAR2(20), 
	REQUEST_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table REQUEST_USER
--------------------------------------------------------

  CREATE TABLE IAMUSER.REQUEST_USER 
   (	REQ_USER_ID VARCHAR2(32), 
	USER_ID VARCHAR2(32), 
	FIRST_NAME VARCHAR2(20), 
	LAST_NAME VARCHAR2(20), 
	MIDDLE_INIT VARCHAR2(20), 
	DEPT_CD VARCHAR2(20), 
	DIVISION VARCHAR2(20), 
	LOCATION_CD VARCHAR2(20), 
	AFFILIATION VARCHAR2(20), 
	REQUEST_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table REQ_APPROVER
--------------------------------------------------------

  CREATE TABLE IAMUSER.REQ_APPROVER 
   (	REQ_APPROVER_ID VARCHAR2(32), 
	APPROVER_ID VARCHAR2(32), 
	APPROVER_TYPE VARCHAR2(20), 
	APPROVER_LEVEL NUMBER(4,0), 
	REQUEST_ID VARCHAR2(32), 
	ACTION_DATE DATE, 
	STATUS VARCHAR2(20), 
	ACTION VARCHAR2(20), 
	CMT VARCHAR2(1000), 
	MANAGED_SYS_ID VARCHAR2(32), 
	MNG_SYS_GROUP_ID VARCHAR2(32), 
	ROLE_DOMAIN VARCHAR2(20)
   ) ;
--------------------------------------------------------
--  DDL for Table RES
--------------------------------------------------------

  CREATE TABLE IAMUSER.RES 
   (	RESOURCE_ID VARCHAR2(32), 
	RESOURCE_TYPE_ID VARCHAR2(20), 
	DESCRIPTION VARCHAR2(100), 
	NAME VARCHAR2(150), 
	BRANCH_ID VARCHAR2(32), 
	CATEGORY_ID VARCHAR2(20), 
	DISPLAY_ORDER NUMBER(4,0) DEFAULT (1), 
	URL VARCHAR2(255), 
	RES_OWNER_GROUP_ID VARCHAR2(32), 
	RES_OWNER_USER_ID VARCHAR2(32), 
	MIN_AUTH_LEVEL VARCHAR2(32), 
	DOMAIN VARCHAR2(64), 
	IS_PUBLIC CHAR(1) DEFAULT ('Y'), 
	IS_SSL CHAR(1) DEFAULT ('N')
   ) ;
--------------------------------------------------------
--  DDL for Table RESOURCE_GROUP
--------------------------------------------------------

  CREATE TABLE IAMUSER.RESOURCE_GROUP 
   (	RESOURCE_ID VARCHAR2(32), 
	GRP_ID VARCHAR2(32), 
	START_DATE DATE
   ) ;
--------------------------------------------------------
--  DDL for Table RESOURCE_POLICY
--------------------------------------------------------

  CREATE TABLE IAMUSER.RESOURCE_POLICY 
   (	RESOURCE_POLICY_ID VARCHAR2(32), 
	RESOURCE_ID VARCHAR2(32), 
	POLICY_START DATE, 
	POLICY_END DATE, 
	APPLY_TO_CHILDREN NUMBER(4,0), 
	ROLE_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table RESOURCE_PRIVILEGE
--------------------------------------------------------

  CREATE TABLE IAMUSER.RESOURCE_PRIVILEGE 
   (	RESOURCE_PRIVLEGE_ID VARCHAR2(32), 
	RESOURCE_ID VARCHAR2(32), 
	PRIVILEGE_NAME VARCHAR2(32), 
	PRIVILEGE_TYPE VARCHAR2(20), 
	DESCRIPTION VARCHAR2(40)
   ) ;
--------------------------------------------------------
--  DDL for Table RESOURCE_PROP
--------------------------------------------------------

  CREATE TABLE IAMUSER.RESOURCE_PROP 
   (	RESOURCE_PROP_ID VARCHAR2(32), 
	RESOURCE_ID VARCHAR2(32), 
	METADATA_ID VARCHAR2(20), 
	NAME VARCHAR2(40), 
	PROP_VALUE VARCHAR2(500), 
	PROP_GROUP VARCHAR2(20)
   ) ;
--------------------------------------------------------
--  DDL for Table RESOURCE_ROLE
--------------------------------------------------------

  CREATE TABLE IAMUSER.RESOURCE_ROLE 
   (	RESOURCE_ID VARCHAR2(32), 
	START_DATE DATE, 
	ROLE_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table RESOURCE_TYPE
--------------------------------------------------------

  CREATE TABLE IAMUSER.RESOURCE_TYPE 
   (	RESOURCE_TYPE_ID VARCHAR2(20), 
	DESCRIPTION VARCHAR2(100), 
	METADATA_TYPE_ID VARCHAR2(32), 
	PROVISION_RESOURCE NUMBER(4,0), 
	PROCESS_NAME VARCHAR2(80)
   ) ;
--------------------------------------------------------
--  DDL for Table RESOURCE_USER
--------------------------------------------------------

  CREATE TABLE IAMUSER.RESOURCE_USER 
   (	RESOURCE_ID VARCHAR2(32), 
	USER_ID VARCHAR2(32), 
	START_DATE TIMESTAMP (6)
   ) ;
--------------------------------------------------------
--  DDL for Table RES_TO_RES_MEMBERSHIP
--------------------------------------------------------

  CREATE TABLE IAMUSER.RES_TO_RES_MEMBERSHIP 
   (	RESOURCE_ID VARCHAR2(32), 
	MEMBER_RESOURCE_ID VARCHAR2(32), 
	CREATE_DATE DATE, 
	UPDATE_DATE DATE, 
	CREATED_BY VARCHAR2(32), 
	UPDATED_BY VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table ROLE
--------------------------------------------------------

  CREATE TABLE IAMUSER.ROLE 
   (	SERVICE_ID VARCHAR2(20), 
	ROLE_NAME VARCHAR2(80), 
	CREATE_DATE DATE, 
	CREATED_BY VARCHAR2(32), 
	DESCRIPTION VARCHAR2(255), 
	STATUS VARCHAR2(20), 
	ROLE_ID VARCHAR2(32), 
	MANAGED_SYS_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table ROLE_ATTRIBUTE
--------------------------------------------------------

  CREATE TABLE IAMUSER.ROLE_ATTRIBUTE 
   (	ROLE_ATTR_ID VARCHAR2(32), 
	NAME VARCHAR2(40), 
	VALUE VARCHAR2(255), 
	METADATA_ID VARCHAR2(20), 
	ATTR_GROUP VARCHAR2(20), 
	ROLE_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table ROLE_POLICY
--------------------------------------------------------

  CREATE TABLE IAMUSER.ROLE_POLICY 
   (	ROLE_POLICY_ID VARCHAR2(32), 
	ACTION VARCHAR2(20), 
	NAME VARCHAR2(40), 
	VALUE1 VARCHAR2(40), 
	VALUE2 VARCHAR2(40), 
	ACTION_QUALIFIER VARCHAR2(20), 
	EXECUTION_ORDER NUMBER(4,0), 
	POLICY_SCRIPT VARCHAR2(100), 
	ROLE_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table ROLE_TO_ROLE_MEMBERSHIP
--------------------------------------------------------

  CREATE TABLE IAMUSER.ROLE_TO_ROLE_MEMBERSHIP 
   (	ROLE_ID VARCHAR2(32), 
	MEMBER_ROLE_ID VARCHAR2(32), 
	CREATE_DATE DATE, 
	UPDATE_DATE DATE, 
	CREATED_BY VARCHAR2(32), 
	UPDATED_BY VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table SECURITY_DOMAIN
--------------------------------------------------------

  CREATE TABLE IAMUSER.SECURITY_DOMAIN 
   (	DOMAIN_ID VARCHAR2(20), 
	NAME VARCHAR2(40), 
	STATUS VARCHAR2(20), 
	AUTH_SYS_ID VARCHAR2(32), 
	LOGIN_MODULE VARCHAR2(100), 
	PASSWORD_POLICY VARCHAR2(32), 
	AUTHENTICATION_POLICY VARCHAR2(32), 
	AUDIT_POLICY VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table SEQUENCE_GEN
--------------------------------------------------------

  CREATE TABLE IAMUSER.SEQUENCE_GEN 
   (	ATTRIBUTE VARCHAR2(32), 
	NEXT_ID NUMBER(4,0)
   ) ;
--------------------------------------------------------
--  DDL for Table SERVICE
--------------------------------------------------------

  CREATE TABLE IAMUSER.SERVICE 
   (	SERVICE_ID VARCHAR2(20), 
	SERVICE_NAME VARCHAR2(40), 
	STATUS VARCHAR2(20), 
	LOCATION_IP_ADDRESS VARCHAR2(80), 
	COMPANY_OWNER_ID VARCHAR2(20), 
	START_DATE DATE, 
	END_DATE DATE, 
	LICENSE_KEY VARCHAR2(255), 
	SERVICE_TYPE VARCHAR2(20), 
	PARENT_SERVICE_ID VARCHAR2(20), 
	ROOT_RESOURCE_ID VARCHAR2(20), 
	ACCESS_CONTROL_MODEL VARCHAR2(20), 
	PARAM1 VARCHAR2(20), 
	PARAM2 VARCHAR2(20), 
	PARAM3 VARCHAR2(20), 
	PARAM4 VARCHAR2(20), 
	PARAM5 VARCHAR2(20)
   ) ;
--------------------------------------------------------
--  DDL for Table SERVICE_CONFIG
--------------------------------------------------------

  CREATE TABLE IAMUSER.SERVICE_CONFIG 
   (	SERVICE_CONFIG_ID VARCHAR2(20), 
	SERVICE_ID VARCHAR2(20), 
	NAME VARCHAR2(40), 
	VALUE VARCHAR2(40)
   ) ;
--------------------------------------------------------
--  DDL for Table STATUS
--------------------------------------------------------

  CREATE TABLE IAMUSER.STATUS 
   (	STATUS_CD VARCHAR2(40), 
	STATUS_TYPE VARCHAR2(20), 
	DESCRIPTION VARCHAR2(80), 
	CODE_GROUP VARCHAR2(40), 
	LANGUAGE_CD VARCHAR2(2), 
	COMPANY_OWNER_ID VARCHAR2(32), 
	SERVICE_ID VARCHAR2(20), 
	WEIGHT NUMBER(4,0)
   ) ;
--------------------------------------------------------
--  DDL for Table SYNCH_CONFIG
--------------------------------------------------------

  CREATE TABLE IAMUSER.SYNCH_CONFIG 
   (	SYNCH_CONFIG_ID VARCHAR2(32), 
	NAME VARCHAR2(60), 
	STATUS VARCHAR2(20), 
	SYNCH_SRC VARCHAR2(20), 
	FILE_NAME VARCHAR2(80), 
	SRC_LOGIN_ID VARCHAR2(100), 
	SRC_PASSWORD VARCHAR2(100), 
	SRC_HOST VARCHAR2(100), 
	DRIVER VARCHAR2(100), 
	CONNECTION_URL VARCHAR2(100), 
	QUERY VARCHAR2(1000), 
	QUERY_TIME_FIELD VARCHAR2(50), 
	BASE_DN VARCHAR2(50), 
	LAST_EXEC_TIME DATE, 
	LAST_REC_PROCESSED VARCHAR2(32), 
	MANAGED_SYS_ID VARCHAR2(32), 
	LOAD_MATCH_ONLY NUMBER(4,0) DEFAULT (0), 
	UPDATE_ATTRIBUTE NUMBER(4,0) DEFAULT (1), 
	SYNCH_FREQUENCY VARCHAR2(20), 
	SYNCH_TYPE VARCHAR2(20), 
	DELETE_RULE VARCHAR2(80), 
	PROCESS_RULE VARCHAR2(80), 
	VALIDATION_RULE VARCHAR2(80), 
	TRANSFORMATION_RULE VARCHAR2(80), 
	MATCH_FIELD_NAME VARCHAR2(40), 
	MATCH_MANAGED_SYS_ID VARCHAR2(32), 
	MATCH_SRC_FIELD_NAME VARCHAR2(40), 
	CUSTOM_MATCH_RULE VARCHAR2(100), 
	CUSTOM_ADAPTER_SCRIPT VARCHAR2(100), 
	CUSTOM_MATCH_ATTR VARCHAR2(40), 
	WS_URL VARCHAR2(100), 
	WS_SCRIPT VARCHAR2(100), 
	USE_POLICY_MAP CHAR(1) DEFAULT 'Y', 
	USE_TRANSFORM_SCRIPT CHAR(1) DEFAULT 'Y', 
	POLICY_MAP_BEFORE_TRANSFORM CHAR(1) DEFAULT 'Y', 
	USE_SYSTEM_PATH CHAR(1) DEFAULT 'N', 
	PRE_SYNC_SCRIPT VARCHAR2(80), 
	POST_SYNC_SCRIPT VARCHAR2(80), 
	COMPANY_ID VARCHAR2(32), 
	ATTRIBUTE_NAMES_LOOKUP VARCHAR2(120), 
	SEARCH_SCOPE NUMBER(*,0) DEFAULT 2
   ) ;
--------------------------------------------------------
--  DDL for Table SYNCH_CONFIG_DATA_MAPPING
--------------------------------------------------------

  CREATE TABLE IAMUSER.SYNCH_CONFIG_DATA_MAPPING 
   (	MAPPING_ID VARCHAR2(32), 
	SYNCH_CONFIG_ID VARCHAR2(32), 
	IDM_FIELD_NAME VARCHAR2(40), 
	SRC_FIELD_NAME VARCHAR2(40), 
	MULTIVALUED NUMBER(4,0)
   ) ;
--------------------------------------------------------
--  DDL for Table UI_FIELD_TEMPLATE_XREF
--------------------------------------------------------

  CREATE TABLE IAMUSER.UI_FIELD_TEMPLATE_XREF 
   (	UI_FIELD_ID VARCHAR2(32), 
	TEMPLATE_ID VARCHAR2(32), 
	IS_REQUIRED CHAR(1) DEFAULT 'N', 
	IS_EDITABLE CHAR(1) DEFAULT 'Y', 
	DISPLAY_ORDER NUMBER(3,0)
   ) ;
--------------------------------------------------------
--  DDL for Table UI_TEMPLATE_FIELDS
--------------------------------------------------------

  CREATE TABLE IAMUSER.UI_TEMPLATE_FIELDS 
   (	UI_FIELD_ID VARCHAR2(32), 
	NAME VARCHAR2(100), 
	DESCRIPTION VARCHAR2(200), 
	TEMPLATE_TYPE_ID VARCHAR2(32), 
	IS_REQUIRED CHAR(1) DEFAULT 'N'
   ) ;
--------------------------------------------------------
--  DDL for Table UI_TEMPLATE_TYPE
--------------------------------------------------------

  CREATE TABLE IAMUSER.UI_TEMPLATE_TYPE 
   (	TEMPLATE_TYPE_ID VARCHAR2(32), 
	NAME VARCHAR2(100), 
	DESCRIPTION VARCHAR2(200)
   ) ;
--------------------------------------------------------
--  DDL for Table URI_PATTERN
--------------------------------------------------------

  CREATE TABLE IAMUSER.URI_PATTERN 
   (	URI_PATTERN_ID VARCHAR2(32), 
	CONTENT_PROVIDER_ID VARCHAR2(32), 
	MIN_AUTH_LEVEL VARCHAR2(32), 
	PATTERN VARCHAR2(100), 
	IS_PUBLIC CHAR(1) DEFAULT ('N'), 
	RESOURCE_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table URI_PATTERN_META
--------------------------------------------------------

  CREATE TABLE IAMUSER.URI_PATTERN_META 
   (	URI_PATTERN_META_ID VARCHAR2(32), 
	URI_PATTERN_ID VARCHAR2(32), 
	URI_PATTERN_META_TYPE_ID VARCHAR2(32), 
	URI_PATTERN_NAME VARCHAR2(100)
   ) ;
--------------------------------------------------------
--  DDL for Table URI_PATTERN_META_TYPE
--------------------------------------------------------

  CREATE TABLE IAMUSER.URI_PATTERN_META_TYPE 
   (	URI_PATTERN_META_TYPE_ID VARCHAR2(32), 
	METADATA_TYPE_NAME VARCHAR2(100), 
	SPRING_BEAN_NAME VARCHAR2(100)
   ) ;
--------------------------------------------------------
--  DDL for Table URI_PATTERN_META_VALUE
--------------------------------------------------------

  CREATE TABLE IAMUSER.URI_PATTERN_META_VALUE 
   (	URI_PATTERN_META_VALUE_ID VARCHAR2(32), 
	URI_PATTERN_META_ID VARCHAR2(32), 
	META_ATTRIBUTE_NAME VARCHAR2(100), 
	AM_RES_ATTRIBUTE_ID VARCHAR2(32), 
	STATIC_VALUE VARCHAR2(100), 
	GROOVY_SCRIPT VARCHAR2(200)
   ) ;
--------------------------------------------------------
--  DDL for Table USERS
--------------------------------------------------------

  CREATE TABLE IAMUSER.USERS 
   (	USER_ID VARCHAR2(32), 
	FIRST_NAME VARCHAR2(50), 
	LAST_NAME VARCHAR2(50), 
	MIDDLE_INIT VARCHAR2(50), 
	TYPE_ID VARCHAR2(20), 
	CLASSIFICATION VARCHAR2(20), 
	TITLE VARCHAR2(100), 
	MAIL_CODE VARCHAR2(50), 
	COST_CENTER VARCHAR2(20), 
	STATUS VARCHAR2(40), 
	SECONDARY_STATUS VARCHAR2(40), 
	BIRTHDATE DATE, 
	SEX CHAR(1), 
	CREATE_DATE DATE, 
	CREATED_BY VARCHAR2(40), 
	LAST_UPDATE DATE, 
	LAST_UPDATED_BY VARCHAR2(40), 
	PREFIX VARCHAR2(4), 
	SUFFIX VARCHAR2(20), 
	USER_TYPE_IND VARCHAR2(20), 
	EMPLOYEE_ID VARCHAR2(32), 
	EMPLOYEE_TYPE VARCHAR2(20), 
	LOCATION_CD VARCHAR2(50), 
	LOCATION_NAME VARCHAR2(100), 
	COMPANY_OWNER_ID VARCHAR2(32), 
	JOB_CODE VARCHAR2(50), 
	ALTERNATE_ID VARCHAR2(32), 
	START_DATE DATE, 
	LAST_DATE DATE, 
	MAIDEN_NAME VARCHAR2(40), 
	NICKNAME VARCHAR2(40), 
	PASSWORD_THEME VARCHAR2(20), 
	SHOW_IN_SEARCH NUMBER(4,0), 
	USER_OWNER_ID VARCHAR2(32), 
	DATE_PASSWORD_CHANGED DATE, 
	DATE_CHALLENGE_RESP_CHANGED DATE, 
	SYSTEM_FLAG VARCHAR2(1), 
	DATE_IT_POLICY_APPROVED TIMESTAMP (6)
   ) ;
--------------------------------------------------------
--  DDL for Table USER_AFFILIATION
--------------------------------------------------------

  CREATE TABLE IAMUSER.USER_AFFILIATION 
   (	COMPANY_ID VARCHAR2(32), 
	USER_ID VARCHAR2(32), 
	CREATE_DATE TIMESTAMP (6)
   ) ;
--------------------------------------------------------
--  DDL for Table USER_ATTACHMENT_REF
--------------------------------------------------------

  CREATE TABLE IAMUSER.USER_ATTACHMENT_REF 
   (	USER_ATTACH_REF_ID VARCHAR2(20), 
	USER_ID VARCHAR2(20), 
	NAME VARCHAR2(20), 
	VALUE VARCHAR2(50), 
	URL VARCHAR2(50)
   ) ;
--------------------------------------------------------
--  DDL for Table USER_ATTRIBUTES
--------------------------------------------------------

  CREATE TABLE IAMUSER.USER_ATTRIBUTES 
   (	ID VARCHAR2(32), 
	USER_ID VARCHAR2(32), 
	METADATA_ID VARCHAR2(32), 
	NAME VARCHAR2(50), 
	VALUE VARCHAR2(1000), 
	ATTR_GROUP VARCHAR2(20), 
	VALUE_AS_BYTE_ARRAY BLOB
   ) ;
--------------------------------------------------------
--  DDL for Table USER_DELEGATION_ATTRIBUTE
--------------------------------------------------------

  CREATE TABLE IAMUSER.USER_DELEGATION_ATTRIBUTE 
   (	ID VARCHAR2(32), 
	USER_ID VARCHAR2(32), 
	OBJ_TYPE VARCHAR2(50), 
	NAME VARCHAR2(50), 
	VALUE VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table USER_GRP
--------------------------------------------------------

  CREATE TABLE IAMUSER.USER_GRP 
   (	GRP_ID VARCHAR2(32), 
	USER_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table USER_IDENTITY_ANS
--------------------------------------------------------

  CREATE TABLE IAMUSER.USER_IDENTITY_ANS 
   (	IDENTITY_ANS_ID VARCHAR2(32), 
	IDENTITY_QUESTION_ID VARCHAR2(32), 
	USER_ID VARCHAR2(32), 
	QUESTION_ANSWER VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table USER_KEY
--------------------------------------------------------

  CREATE TABLE IAMUSER.USER_KEY 
   (	USER_KEY_ID VARCHAR2(32), 
	USER_ID VARCHAR2(32), 
	NAME VARCHAR2(40), 
	KEY_VALUE VARCHAR2(255)
   ) ;
--------------------------------------------------------
--  DDL for Table USER_NOTE
--------------------------------------------------------

  CREATE TABLE IAMUSER.USER_NOTE 
   (	USER_NOTE_ID VARCHAR2(32), 
	USER_ID VARCHAR2(32), 
	NOTE_TYPE VARCHAR2(20), 
	DESCRIPTION VARCHAR2(2000), 
	CREATE_DATE DATE, 
	CREATED_BY VARCHAR2(20)
   ) ;
--------------------------------------------------------
--  DDL for Table USER_ROLE
--------------------------------------------------------

  CREATE TABLE IAMUSER.USER_ROLE 
   (	USER_ID VARCHAR2(32), 
	ROLE_ID VARCHAR2(32)
   ) ;
--------------------------------------------------------
--  DDL for Table WEB_RESOURCE_ATTRIBUTE
--------------------------------------------------------

  CREATE TABLE IAMUSER.WEB_RESOURCE_ATTRIBUTE 
   (	ATTRIBUTE_MAP_ID VARCHAR2(32), 
	RESOURCE_ID VARCHAR2(32), 
	TARGET_ATTRIBUTE_NAME VARCHAR2(100), 
	AM_ATTRIBUTE_NAME VARCHAR2(100), 
	AM_POLICY_URL VARCHAR2(100)
   ) ;
REM INSERTING into IAMUSER.ADDRESS
SET DEFINE OFF;
REM INSERTING into IAMUSER.APPROVER_ASSOC
SET DEFINE OFF;
Insert into IAMUSER.APPROVER_ASSOC (APPROVER_ASSOC_ID,REQUEST_TYPE,APPROVER_LEVEL,ASSOCIATION_TYPE,ASSOCIATION_ENTITY_ID,ON_APPROVE_ENTITY_ID,ON_REJECT_ENTITY_ID,ON_APPROVE_ENTITY_TYPE,ON_REJECT_ENTITY_TYPE,APPROVER_ENTITY_TYPE,APPROVER_ENTITY_ID,APPLY_DELEGATION_FILTER) values ('100','NEW_HIRE',1,'SUPERVISOR',null,null,null,null,null,null,null,'N');
Insert into IAMUSER.APPROVER_ASSOC (APPROVER_ASSOC_ID,REQUEST_TYPE,APPROVER_LEVEL,ASSOCIATION_TYPE,ASSOCIATION_ENTITY_ID,ON_APPROVE_ENTITY_ID,ON_REJECT_ENTITY_ID,ON_APPROVE_ENTITY_TYPE,ON_REJECT_ENTITY_TYPE,APPROVER_ENTITY_TYPE,APPROVER_ENTITY_ID,APPLY_DELEGATION_FILTER) values ('101','254',1,'RESOURCE','254',null,null,null,null,'USER','3000','N');
REM INSERTING into IAMUSER.ATTRIBUTE_MAP
SET DEFINE OFF;
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('201','101','101','USER','cn',null,0,'1','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('202','101','101','USER','mail',null,0,'2','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('203','101','101','USER','o',null,0,'3','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('204','101','101','USER','ou',null,0,'4','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('205','101','101','USER','postalCode',null,0,'5','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('206','101','101','USER','sn',null,0,'6','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('207','101','101','USER','st',null,0,'7','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('208','101','101','USER','street',null,0,'8','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('209','101','101','USER','userPassword',null,0,'9','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('210','101','101','USER','postalAddress',null,0,'10','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('211','101','101','USER','telephoneNumber',null,0,'11','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('212','101','101','USER','facsimileTelephoneNumber',null,0,'12','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('213','101','101','USER','mobileTelephoneNumber',null,0,'13','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('215','101','101','PRINCIPAL','uid',null,0,'14','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('216','101','101','USER','departmentNumber',null,0,'15','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('217','101','101','USER','displayName',null,0,'16','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('218','101','101','USER','employeeType',null,0,'17','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('221','101','101','USER','objectclass',null,0,'18','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('222','101','101','USER','title',null,0,'19','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('223','101','101','USER','givenName',null,0,'20','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('224','101','101','USER','uniqueMember',null,0,'21','IN-ACTIVE',null,null,0,'MEMBER_OF',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('230','0','0','PRINCIPAL','PRINCIPAL',null,0,'22','ACTIVE',null,null,0,null,null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('231','0','0','PRINCIPAL','PASSWORD',null,0,'23','ACTIVE',null,null,0,null,null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('232','0','0','EMAIL','EMAIL',null,0,'24','ACTIVE',null,null,0,null,null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('233','0','0','PRINCIPAL','DOMAIN',null,0,'25','ACTIVE',null,null,0,null,null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('400','103','103','PRINCIPAL','userName',null,0,'26','ACTIVE',null,null,0,null,null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('401','103','103','USER','firstName',null,0,'27','ACTIVE',null,null,0,null,null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('402','103','103','USER','lastName',null,0,'28','ACTIVE',null,null,0,null,null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('403','103','103','USER','password',null,0,'29','ACTIVE',null,null,0,null,null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('501','110','110','PRINCIPAL','CN',null,0,'30','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('502','110','110','USER','sAMAccountName',null,0,'31','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('503','110','110','USER','sn',null,0,'32','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('504','110','110','USER','givenName',null,0,'33','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('505','110','110','USER','displayName',null,0,'34','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('506','110','110','USER','ou',null,0,'35','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('507','110','110','USER','objectclass',null,0,'36','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('508','110','110','USER','profilePath',null,0,'37','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('509','110','110','USER','homeDirectory',null,0,'38','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('510','110','110','USER','homeDrive',null,0,'39','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('511','110','110','USER','scriptPath',null,0,'40','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('512','110','110','USER','company',null,0,'41','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('513','110','110','USER','initials',null,0,'42','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('514','110','110','USER','department',null,0,'43','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('515','110','110','USER','telephoneNumber',null,0,'44','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('516','110','110','USER','title',null,0,'45','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('517','110','110','USER','unicodePwd',null,0,'46','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('518','110','110','USER','userAccountControl',null,0,'47','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('519','110','110','USER','userPrincipalName',null,0,'48','ACTIVE',null,null,0,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('521','110','110','USER','streetAddress',null,0,'49','ACTIVE',null,null,0,'String',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('522','110','110','USER','l',null,0,'50','ACTIVE',null,null,0,'String',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('523','110','110','USER','st',null,0,'51','ACTIVE',null,null,0,'String',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('524','110','110','USER','postalCode',null,0,'52','ACTIVE',null,null,0,'String',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('550','106','106','PRINCIPAL','userName',null,0,'53','ACTIVE',null,null,0,null,null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('553','106','106','USER','password',null,0,'54','ACTIVE',null,null,0,null,null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('600','105','105','PRINCIPAL','login',null,0,'55','ACTIVE',null,null,0,null,null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('601','105','105','USER','name',null,0,'56','ACTIVE',null,null,0,null,null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('602','105','105','USER','surname',null,0,'57','ACTIVE',null,null,0,null,null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('603','105','105','USER','password',null,0,'58','ACTIVE',null,null,0,null,null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('604','105','105','USER','homePhone',null,0,'59','ACTIVE',null,null,0,null,null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('605','105','105','USER','workPhone',null,0,'60','ACTIVE',null,null,0,null,null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('606','105','105','USER','roomNumber',null,0,'61','ACTIVE',null,null,0,null,null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('607','105','105','USER','groups',null,0,'62','ACTIVE',null,null,0,null,null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('612','113','113','PRINCIPAL','LOGIN_ID',null,null,'65','ACTIVE',null,null,null,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('613','113','113','USER','USER_PASSWORD',null,null,'66','ACTIVE',null,null,null,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('614','113','113','USER','FIRST_NAME',null,null,'67','ACTIVE',null,null,null,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('615','113','113','USER','LAST_NAME',null,null,'68','ACTIVE',null,null,null,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('616','113','113','USER','EMPLOYEE_ID',null,null,'69','ACTIVE',null,null,null,'STRING',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('617','113','113','USER','DOB',null,null,'70','ACTIVE',null,null,null,'DATE',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('618','113','113','USER','CREATE_DATE',null,null,'71','ACTIVE',null,null,null,'DATE',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('619','113','113','USER','PHONE_EXT',null,null,'72','ACTIVE',null,null,null,'INT',null,null);
Insert into IAMUSER.ATTRIBUTE_MAP (ATTRIBUTE_MAP_ID,MANAGED_SYS_ID,RESOURCE_ID,MAP_FOR_OBJECT_TYPE,ATTRIBUTE_NAME,TARGET_ATTRIBUTE_NAME,AUTHORITATIVE_SRC,ATTRIBUTE_POLICY_ID,STATUS,START_DATE,END_DATE,STORE_IN_IAMDB,DATA_TYPE,DEFAULT_VALUE,SYNCH_CONFIG_ID) values ('620','113','113','USER','PHONE_NBR',null,null,'73','ACTIVE',null,null,null,'STRING',null,null);
REM INSERTING into IAMUSER.AUTH_ATTRIBUTE
SET DEFINE OFF;
Insert into IAMUSER.AUTH_ATTRIBUTE (AUTH_ATTRIBUTE_ID,ATTRIBUTE_NAME,PROVIDER_TYPE,DESCRIPTION,REQUIRED,DATA_TYPE,DEFAULT_VALUE) values ('1','Request Issuer','SAML_PROVIDER','The Issuer element to look for in the SAMLRequest','Y','singleValue',null);
Insert into IAMUSER.AUTH_ATTRIBUTE (AUTH_ATTRIBUTE_ID,ATTRIBUTE_NAME,PROVIDER_TYPE,DESCRIPTION,REQUIRED,DATA_TYPE,DEFAULT_VALUE) values ('2','Response Issuer','SAML_PROVIDER','The Issuer element to send in the SAMLResponse.  Default is the SAML Login Page URL','N','singleValue',null);
Insert into IAMUSER.AUTH_ATTRIBUTE (AUTH_ATTRIBUTE_ID,ATTRIBUTE_NAME,PROVIDER_TYPE,DESCRIPTION,REQUIRED,DATA_TYPE,DEFAULT_VALUE) values ('3','Assertion Consumer URL','SAML_PROVIDER','Where the SAMLResponse will be POSTed to','Y','singleValue',null);
Insert into IAMUSER.AUTH_ATTRIBUTE (AUTH_ATTRIBUTE_ID,ATTRIBUTE_NAME,PROVIDER_TYPE,DESCRIPTION,REQUIRED,DATA_TYPE,DEFAULT_VALUE) values ('4','Audiences','SAML_PROVIDER','The Audience value(s) to send in the SAMLResponse','N','listValue',null);
Insert into IAMUSER.AUTH_ATTRIBUTE (AUTH_ATTRIBUTE_ID,ATTRIBUTE_NAME,PROVIDER_TYPE,DESCRIPTION,REQUIRED,DATA_TYPE,DEFAULT_VALUE) values ('AuthnContextClassRef','Authentication Context Class','SAML_PROVIDER','The Value of the AuthnContextClassRef attribute','N','singleValue','urn:oasis:names:tc:SAML:2.0:ac:classes:Password');
Insert into IAMUSER.AUTH_ATTRIBUTE (AUTH_ATTRIBUTE_ID,ATTRIBUTE_NAME,PROVIDER_TYPE,DESCRIPTION,REQUIRED,DATA_TYPE,DEFAULT_VALUE) values ('METADATA_EXPOSED','Metdata Exposed','SAML_PROVIDER','Is the metadata for this SAML Provider exposed?  Some Service Providers require a URL that defines the Identity Provider Metadata','N','booleanValue',null);
Insert into IAMUSER.AUTH_ATTRIBUTE (AUTH_ATTRIBUTE_ID,ATTRIBUTE_NAME,PROVIDER_TYPE,DESCRIPTION,REQUIRED,DATA_TYPE,DEFAULT_VALUE) values ('NameIdFormat','Name ID Format','SAML_PROVIDER','The Value of the Format attribute in the NameID element','N','singleValue','urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress');
Insert into IAMUSER.AUTH_ATTRIBUTE (AUTH_ATTRIBUTE_ID,ATTRIBUTE_NAME,PROVIDER_TYPE,DESCRIPTION,REQUIRED,DATA_TYPE,DEFAULT_VALUE) values ('NameQualifier','Name Qualifier','SAML_PROVIDER','Value of the NameQualifier attribute in the NameID element','N','singleValue',null);
Insert into IAMUSER.AUTH_ATTRIBUTE (AUTH_ATTRIBUTE_ID,ATTRIBUTE_NAME,PROVIDER_TYPE,DESCRIPTION,REQUIRED,DATA_TYPE,DEFAULT_VALUE) values ('SPNameQualifier','SP Name Qualifier','SAML_PROVIDER','Value of the SPNameQualifier attribute in the NameID element','N','singleValue',null);
REM INSERTING into IAMUSER.AUTH_LEVEL
SET DEFINE OFF;
Insert into IAMUSER.AUTH_LEVEL (AUTH_LEVEL_ID,AUTH_LEVEL_NAME,AUTH_LEVEL_DIG) values ('CERT_AUTH','Certification Authentication',2);
Insert into IAMUSER.AUTH_LEVEL (AUTH_LEVEL_ID,AUTH_LEVEL_NAME,AUTH_LEVEL_DIG) values ('PASSWORD_AUTH','Password Authentication',1);
REM INSERTING into IAMUSER.AUTH_PROVIDER
SET DEFINE OFF;
REM INSERTING into IAMUSER.AUTH_PROVIDER_ATTRIBUTE
SET DEFINE OFF;
REM INSERTING into IAMUSER.AUTH_PROVIDER_TYPE
SET DEFINE OFF;
Insert into IAMUSER.AUTH_PROVIDER_TYPE (PROVIDER_TYPE,DESCRIPTION,ACTIVE) values ('SAML_PROVIDER','SAML Provider','Y');
REM INSERTING into IAMUSER.AUTH_RESOURCE_AM_ATTRIBUTE
SET DEFINE OFF;
Insert into IAMUSER.AUTH_RESOURCE_AM_ATTRIBUTE (AM_RES_ATTRIBUTE_ID,REFLECTION_KEY,ATTRIBUTE_NAME) values ('1,','Login.login','Principal');
Insert into IAMUSER.AUTH_RESOURCE_AM_ATTRIBUTE (AM_RES_ATTRIBUTE_ID,REFLECTION_KEY,ATTRIBUTE_NAME) values ('2','User.title','Functional Title');
Insert into IAMUSER.AUTH_RESOURCE_AM_ATTRIBUTE (AM_RES_ATTRIBUTE_ID,REFLECTION_KEY,ATTRIBUTE_NAME) values ('3','Login.password','Password');
REM INSERTING into IAMUSER.AUTH_RESOURCE_ATTRIBUTE_MAP
SET DEFINE OFF;
REM INSERTING into IAMUSER.AUTH_STATE
SET DEFINE OFF;
Insert into IAMUSER.AUTH_STATE (USER_ID,AUTH_STATE,TOKEN,AA,EXPIRATION,LAST_LOGIN,IP_ADDRESS) values ('3000',0,null,'OPENIAM',0,null,null);
Insert into IAMUSER.AUTH_STATE (USER_ID,AUTH_STATE,TOKEN,AA,EXPIRATION,LAST_LOGIN,IP_ADDRESS) values ('3001',0,null,'OPENIAM',0,null,null);
Insert into IAMUSER.AUTH_STATE (USER_ID,AUTH_STATE,TOKEN,AA,EXPIRATION,LAST_LOGIN,IP_ADDRESS) values ('3006',0,null,'OPENIAM',0,null,null);
Insert into IAMUSER.AUTH_STATE (USER_ID,AUTH_STATE,TOKEN,AA,EXPIRATION,LAST_LOGIN,IP_ADDRESS) values ('3007',0,null,'OPENIAM',0,null,null);
Insert into IAMUSER.AUTH_STATE (USER_ID,AUTH_STATE,TOKEN,AA,EXPIRATION,LAST_LOGIN,IP_ADDRESS) values ('3008',0,null,'OPENIAM',0,null,null);
Insert into IAMUSER.AUTH_STATE (USER_ID,AUTH_STATE,TOKEN,AA,EXPIRATION,LAST_LOGIN,IP_ADDRESS) values ('3009',0,null,'OPENIAM',0,null,null);
REM INSERTING into IAMUSER.BATCH_CONFIG
SET DEFINE OFF;
Insert into IAMUSER.BATCH_CONFIG (TASK_ID,TASK_NAME,LAST_EXEC_TIME,TASK_URL,EXECUTION_ORDER,STATUS,PARAM1,PARAM2,PARAM3,PARAM4,RULE_TYPE,ENABLED,LAST_MODIFIED_DATETIME,RUN_ON,CRON_EXPRESSION,SPRING_BEAN,SPRING_BEAN_METHOD) values ('100','ACCOUNT_LOCKED_NOTIFICATION',null,'batch/accountLockedNotification.groovy',2,null,null,null,null,null,null,'N',null,null,'0 0/1 * * * ?',null,null);
Insert into IAMUSER.BATCH_CONFIG (TASK_ID,TASK_NAME,LAST_EXEC_TIME,TASK_URL,EXECUTION_ORDER,STATUS,PARAM1,PARAM2,PARAM3,PARAM4,RULE_TYPE,ENABLED,LAST_MODIFIED_DATETIME,RUN_ON,CRON_EXPRESSION,SPRING_BEAN,SPRING_BEAN_METHOD) values ('101','INACTIVE_USER',null,'batch/inactiveUser.groovy',1,null,null,null,null,null,null,'N',null,null,'0 0 0 1/1 * ?',null,null);
Insert into IAMUSER.BATCH_CONFIG (TASK_ID,TASK_NAME,LAST_EXEC_TIME,TASK_URL,EXECUTION_ORDER,STATUS,PARAM1,PARAM2,PARAM3,PARAM4,RULE_TYPE,ENABLED,LAST_MODIFIED_DATETIME,RUN_ON,CRON_EXPRESSION,SPRING_BEAN,SPRING_BEAN_METHOD) values ('102','PASSWORD_NEAR_EXP',null,'batch/passwordNearExpNotification.groovy',2,null,null,null,null,null,null,'N',null,null,'0 0 0 1/1 * ?',null,null);
Insert into IAMUSER.BATCH_CONFIG (TASK_ID,TASK_NAME,LAST_EXEC_TIME,TASK_URL,EXECUTION_ORDER,STATUS,PARAM1,PARAM2,PARAM3,PARAM4,RULE_TYPE,ENABLED,LAST_MODIFIED_DATETIME,RUN_ON,CRON_EXPRESSION,SPRING_BEAN,SPRING_BEAN_METHOD) values ('103','TERMINATE_EXP_ACCT',null,'batch/terminateOnExpiration.groovy',3,null,null,null,null,null,null,'N',null,null,'0 0 0 1/1 * ?',null,null);
Insert into IAMUSER.BATCH_CONFIG (TASK_ID,TASK_NAME,LAST_EXEC_TIME,TASK_URL,EXECUTION_ORDER,STATUS,PARAM1,PARAM2,PARAM3,PARAM4,RULE_TYPE,ENABLED,LAST_MODIFIED_DATETIME,RUN_ON,CRON_EXPRESSION,SPRING_BEAN,SPRING_BEAN_METHOD) values ('104','AUTO_UNLOCK',null,'batch/autoUnlock.groovy',1,null,null,null,null,null,null,'N',null,null,'0 0/1 * * * ?',null,null);
Insert into IAMUSER.BATCH_CONFIG (TASK_ID,TASK_NAME,LAST_EXEC_TIME,TASK_URL,EXECUTION_ORDER,STATUS,PARAM1,PARAM2,PARAM3,PARAM4,RULE_TYPE,ENABLED,LAST_MODIFIED_DATETIME,RUN_ON,CRON_EXPRESSION,SPRING_BEAN,SPRING_BEAN_METHOD) values ('105','RESET_PSWD_CHNG_COUNT',null,'batch/resetPasswordChangeCount.groovy',2,null,null,null,null,null,null,'N',null,null,'0 0/1 * * * ?',null,null);
Insert into IAMUSER.BATCH_CONFIG (TASK_ID,TASK_NAME,LAST_EXEC_TIME,TASK_URL,EXECUTION_ORDER,STATUS,PARAM1,PARAM2,PARAM3,PARAM4,RULE_TYPE,ENABLED,LAST_MODIFIED_DATETIME,RUN_ON,CRON_EXPRESSION,SPRING_BEAN,SPRING_BEAN_METHOD) values ('106','PUBLISH_AUDIT_EVENT',null,'batch/publishAuditEvent.groovy',3,null,null,null,null,null,null,'N',null,null,'0 0 0 1/1 * ?',null,null);
Insert into IAMUSER.BATCH_CONFIG (TASK_ID,TASK_NAME,LAST_EXEC_TIME,TASK_URL,EXECUTION_ORDER,STATUS,PARAM1,PARAM2,PARAM3,PARAM4,RULE_TYPE,ENABLED,LAST_MODIFIED_DATETIME,RUN_ON,CRON_EXPRESSION,SPRING_BEAN,SPRING_BEAN_METHOD) values ('107','PASSWORD_EXPIRED',null,'batch/passwordExpiredNotification.groovy',4,null,null,null,null,null,null,'N',null,null,'0 0 0 1/1 * ?',null,null);
Insert into IAMUSER.BATCH_CONFIG (TASK_ID,TASK_NAME,LAST_EXEC_TIME,TASK_URL,EXECUTION_ORDER,STATUS,PARAM1,PARAM2,PARAM3,PARAM4,RULE_TYPE,ENABLED,LAST_MODIFIED_DATETIME,RUN_ON,CRON_EXPRESSION,SPRING_BEAN,SPRING_BEAN_METHOD) values ('108','PROV_ON_STARTDATE',null,'batch/provisionOnStartDate.groovy',4,null,null,null,null,null,null,'N',null,null,'0 0 0 1/1 * ?',null,null);
Insert into IAMUSER.BATCH_CONFIG (TASK_ID,TASK_NAME,LAST_EXEC_TIME,TASK_URL,EXECUTION_ORDER,STATUS,PARAM1,PARAM2,PARAM3,PARAM4,RULE_TYPE,ENABLED,LAST_MODIFIED_DATETIME,RUN_ON,CRON_EXPRESSION,SPRING_BEAN,SPRING_BEAN_METHOD) values ('ATTESTATION','Attestation',null,null,1,null,null,null,null,null,null,'N',to_timestamp('28-MAR-14 09.26.36.315191000 PM','DD-MON-RR HH.MI.SS.FF AM'),null,'0 0 0 1 1/3 ?','attestationInitializer','initializeAttestation');
Insert into IAMUSER.BATCH_CONFIG (TASK_ID,TASK_NAME,LAST_EXEC_TIME,TASK_URL,EXECUTION_ORDER,STATUS,PARAM1,PARAM2,PARAM3,PARAM4,RULE_TYPE,ENABLED,LAST_MODIFIED_DATETIME,RUN_ON,CRON_EXPRESSION,SPRING_BEAN,SPRING_BEAN_METHOD) values ('109','GENERATE_REPORTS',null,'batch/reportsGeneration.groovy',5,null,null,null,null,null,null,'N',to_timestamp('28-MAR-14 09.32.07.641947000 PM','DD-MON-RR HH.MI.SS.FF AM'),null,'0 15 0 * * ?',null,null);
REM INSERTING into IAMUSER.CATEGORY
SET DEFINE OFF;
Insert into IAMUSER.CATEGORY (CATEGORY_ID,PARENT_ID,CATEGORY_NAME,CATEGORY_DESC,CREATE_DATE,CREATED_BY,SHOW_LIST,DISPLAY_ORDER) values ('ACL',null,'ACL Root',null,null,null,0,0);
Insert into IAMUSER.CATEGORY (CATEGORY_ID,PARENT_ID,CATEGORY_NAME,CATEGORY_DESC,CREATE_DATE,CREATED_BY,SHOW_LIST,DISPLAY_ORDER) values ('Application',null,'ACL',null,null,null,0,0);
Insert into IAMUSER.CATEGORY (CATEGORY_ID,PARENT_ID,CATEGORY_NAME,CATEGORY_DESC,CREATE_DATE,CREATED_BY,SHOW_LIST,DISPLAY_ORDER) values ('CONNECTOR_TYPE','ROOT','Provisioning Connectors',null,null,null,0,0);
Insert into IAMUSER.CATEGORY (CATEGORY_ID,PARENT_ID,CATEGORY_NAME,CATEGORY_DESC,CREATE_DATE,CREATED_BY,SHOW_LIST,DISPLAY_ORDER) values ('GROUP_TYPE','ROOT','Group Type',null,null,null,0,0);
Insert into IAMUSER.CATEGORY (CATEGORY_ID,PARENT_ID,CATEGORY_NAME,CATEGORY_DESC,CREATE_DATE,CREATED_BY,SHOW_LIST,DISPLAY_ORDER) values ('MANAGED_SYS_TYPE','ROOT','Managed System Types',null,null,null,0,0);
Insert into IAMUSER.CATEGORY (CATEGORY_ID,PARENT_ID,CATEGORY_NAME,CATEGORY_DESC,CREATE_DATE,CREATED_BY,SHOW_LIST,DISPLAY_ORDER) values ('ORG_TYPE','ROOT','ORGANIZATION Types',null,null,null,0,0);
Insert into IAMUSER.CATEGORY (CATEGORY_ID,PARENT_ID,CATEGORY_NAME,CATEGORY_DESC,CREATE_DATE,CREATED_BY,SHOW_LIST,DISPLAY_ORDER) values ('ROOT',null,'ROOT',null,null,null,0,0);
Insert into IAMUSER.CATEGORY (CATEGORY_ID,PARENT_ID,CATEGORY_NAME,CATEGORY_DESC,CREATE_DATE,CREATED_BY,SHOW_LIST,DISPLAY_ORDER) values ('SECURITY',null,'SECURITY',null,null,null,0,0);
Insert into IAMUSER.CATEGORY (CATEGORY_ID,PARENT_ID,CATEGORY_NAME,CATEGORY_DESC,CREATE_DATE,CREATED_BY,SHOW_LIST,DISPLAY_ORDER) values ('USER_TYPE','ROOT','User Types',null,null,null,0,0);
Insert into IAMUSER.CATEGORY (CATEGORY_ID,PARENT_ID,CATEGORY_NAME,CATEGORY_DESC,CREATE_DATE,CREATED_BY,SHOW_LIST,DISPLAY_ORDER) values ('WebSite',null,'ACL',null,null,null,0,0);
REM INSERTING into IAMUSER.CATEGORY_LANGUAGE
SET DEFINE OFF;
REM INSERTING into IAMUSER.CATEGORY_TYPE
SET DEFINE OFF;
Insert into IAMUSER.CATEGORY_TYPE (CATEGORY_ID,TYPE_ID) values ('MANAGED_SYS_TYPE','ADGroup');
Insert into IAMUSER.CATEGORY_TYPE (CATEGORY_ID,TYPE_ID) values ('MANAGED_SYS_TYPE','ADUser');
Insert into IAMUSER.CATEGORY_TYPE (CATEGORY_ID,TYPE_ID) values ('CONNECTOR_TYPE','AD_Connector');
Insert into IAMUSER.CATEGORY_TYPE (CATEGORY_ID,TYPE_ID) values ('GROUP_TYPE','AD_GROUP_TYPE');
Insert into IAMUSER.CATEGORY_TYPE (CATEGORY_ID,TYPE_ID) values ('USER_TYPE','Contractor');
Insert into IAMUSER.CATEGORY_TYPE (CATEGORY_ID,TYPE_ID) values ('GROUP_TYPE','DEFAULT_GROUP');
Insert into IAMUSER.CATEGORY_TYPE (CATEGORY_ID,TYPE_ID) values ('ACL','DIRECTORY');
Insert into IAMUSER.CATEGORY_TYPE (CATEGORY_ID,TYPE_ID) values ('ACL','FILE');
Insert into IAMUSER.CATEGORY_TYPE (CATEGORY_ID,TYPE_ID) values ('USER_TYPE','InetOrgPerson');
Insert into IAMUSER.CATEGORY_TYPE (CATEGORY_ID,TYPE_ID) values ('CONNECTOR_TYPE','LDAP_Connector');
Insert into IAMUSER.CATEGORY_TYPE (CATEGORY_ID,TYPE_ID) values ('GROUP_TYPE','LDAP_GROUP_TYPE');
Insert into IAMUSER.CATEGORY_TYPE (CATEGORY_ID,TYPE_ID) values ('CONNECTOR_TYPE','LINUX_Connector');
Insert into IAMUSER.CATEGORY_TYPE (CATEGORY_ID,TYPE_ID) values ('MANAGED_SYS_TYPE','LdapOrgPerson');
Insert into IAMUSER.CATEGORY_TYPE (CATEGORY_ID,TYPE_ID) values ('CONNECTOR_TYPE','RDBMS_Connector');
Insert into IAMUSER.CATEGORY_TYPE (CATEGORY_ID,TYPE_ID) values ('CONNECTOR_TYPE','SCRIPT_Connector');
Insert into IAMUSER.CATEGORY_TYPE (CATEGORY_ID,TYPE_ID) values ('CONNECTOR_TYPE','SOAP_Connector');
Insert into IAMUSER.CATEGORY_TYPE (CATEGORY_ID,TYPE_ID) values ('USER_TYPE','SystemAccount');
Insert into IAMUSER.CATEGORY_TYPE (CATEGORY_ID,TYPE_ID) values ('ACL','URL');
Insert into IAMUSER.CATEGORY_TYPE (CATEGORY_ID,TYPE_ID) values ('ORG_TYPE','departmentType');
Insert into IAMUSER.CATEGORY_TYPE (CATEGORY_ID,TYPE_ID) values ('ORG_TYPE','divisionType');
REM INSERTING into IAMUSER.COMPANY
SET DEFINE OFF;
Insert into IAMUSER.COMPANY (COMPANY_ID,COMPANY_NAME,LST_UPDATE,LST_UPDATED_BY,PARENT_ID,STATUS,CREATE_DATE,CREATED_BY,ALIAS,DESCRIPTION,DOMAIN_NAME,LDAP_STR,CLASSIFICATION,INTERNAL_COMPANY_ID,ABBREVIATION,SYMBOL,ORG_TYPE_ID) values ('100','OpenIAM',null,null,null,'ACTIVE',null,null,null,null,'openiam.com',null,'ORGANIZATION',null,null,null,'ORGANIZATION');
Insert into IAMUSER.COMPANY (COMPANY_ID,COMPANY_NAME,LST_UPDATE,LST_UPDATED_BY,PARENT_ID,STATUS,CREATE_DATE,CREATED_BY,ALIAS,DESCRIPTION,DOMAIN_NAME,LDAP_STR,CLASSIFICATION,INTERNAL_COMPANY_ID,ABBREVIATION,SYMBOL,ORG_TYPE_ID) values ('101','Sales',null,null,'100','ACTIVE',null,null,null,null,null,null,'DEPARTMENT',null,null,null,'DEPARTMENT');
Insert into IAMUSER.COMPANY (COMPANY_ID,COMPANY_NAME,LST_UPDATE,LST_UPDATED_BY,PARENT_ID,STATUS,CREATE_DATE,CREATED_BY,ALIAS,DESCRIPTION,DOMAIN_NAME,LDAP_STR,CLASSIFICATION,INTERNAL_COMPANY_ID,ABBREVIATION,SYMBOL,ORG_TYPE_ID) values ('102','Finance',null,null,'100','ACTIVE',null,null,null,null,null,null,'DEPARTMENT',null,null,null,'DEPARTMENT');
Insert into IAMUSER.COMPANY (COMPANY_ID,COMPANY_NAME,LST_UPDATE,LST_UPDATED_BY,PARENT_ID,STATUS,CREATE_DATE,CREATED_BY,ALIAS,DESCRIPTION,DOMAIN_NAME,LDAP_STR,CLASSIFICATION,INTERNAL_COMPANY_ID,ABBREVIATION,SYMBOL,ORG_TYPE_ID) values ('103','Customer Service',null,null,'100','ACTIVE',null,null,null,null,null,null,'DEPARTMENT',null,null,null,'DEPARTMENT');
Insert into IAMUSER.COMPANY (COMPANY_ID,COMPANY_NAME,LST_UPDATE,LST_UPDATED_BY,PARENT_ID,STATUS,CREATE_DATE,CREATED_BY,ALIAS,DESCRIPTION,DOMAIN_NAME,LDAP_STR,CLASSIFICATION,INTERNAL_COMPANY_ID,ABBREVIATION,SYMBOL,ORG_TYPE_ID) values ('104','IT',null,null,'100','ACTIVE',null,null,null,null,null,null,'DEPARTMENT',null,null,null,'DEPARTMENT');
REM INSERTING into IAMUSER.COMPANY_ATTRIBUTE
SET DEFINE OFF;
REM INSERTING into IAMUSER.COMPANY_TO_COMPANY_MEMBERSHIP
SET DEFINE OFF;
Insert into IAMUSER.COMPANY_TO_COMPANY_MEMBERSHIP (COMPANY_ID,MEMBER_COMPANY_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('100','101',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.COMPANY_TO_COMPANY_MEMBERSHIP (COMPANY_ID,MEMBER_COMPANY_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('100','102',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.COMPANY_TO_COMPANY_MEMBERSHIP (COMPANY_ID,MEMBER_COMPANY_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('100','103',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.COMPANY_TO_COMPANY_MEMBERSHIP (COMPANY_ID,MEMBER_COMPANY_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('100','104',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
REM INSERTING into IAMUSER.CONTENT_PROVIDER
SET DEFINE OFF;
REM INSERTING into IAMUSER.CONTENT_PROVIDER_SERVER
SET DEFINE OFF;
REM INSERTING into IAMUSER.CREDENTIALS
SET DEFINE OFF;
REM INSERTING into IAMUSER.DEF_RECON_ATTR_MAP
SET DEFINE OFF;
Insert into IAMUSER.DEF_RECON_ATTR_MAP (DEF_ATTR_MAP_ID,DEF_ATTR_MAP_NAME) values ('Login.login','Principal');
Insert into IAMUSER.DEF_RECON_ATTR_MAP (DEF_ATTR_MAP_ID,DEF_ATTR_MAP_NAME) values ('User.title','Functional Title');
REM INSERTING into IAMUSER.EMAIL_ADDRESS
SET DEFINE OFF;
REM INSERTING into IAMUSER.ENTITLEMENT
SET DEFINE OFF;
REM INSERTING into IAMUSER.EXCLUDE_WORD_LIST
SET DEFINE OFF;
REM INSERTING into IAMUSER.GRP
SET DEFINE OFF;
Insert into IAMUSER.GRP (GRP_ID,GRP_NAME,CREATE_DATE,CREATED_BY,COMPANY_ID,GROUP_DESC,STATUS,LAST_UPDATE,LAST_UPDATED_BY,MANAGED_SYS_ID) values ('END_USER_GRP','End User Group',null,null,null,null,null,null,null,null);
Insert into IAMUSER.GRP (GRP_ID,GRP_NAME,CREATE_DATE,CREATED_BY,COMPANY_ID,GROUP_DESC,STATUS,LAST_UPDATE,LAST_UPDATED_BY,MANAGED_SYS_ID) values ('HR_GRP','HR Group',null,null,null,null,null,null,null,null);
Insert into IAMUSER.GRP (GRP_ID,GRP_NAME,CREATE_DATE,CREATED_BY,COMPANY_ID,GROUP_DESC,STATUS,LAST_UPDATE,LAST_UPDATED_BY,MANAGED_SYS_ID) values ('MNGR_GRP','Manager Group',null,null,null,null,null,null,null,null);
Insert into IAMUSER.GRP (GRP_ID,GRP_NAME,CREATE_DATE,CREATED_BY,COMPANY_ID,GROUP_DESC,STATUS,LAST_UPDATE,LAST_UPDATED_BY,MANAGED_SYS_ID) values ('SECURITY_GRP','Security Group',null,null,null,null,null,null,null,null);
Insert into IAMUSER.GRP (GRP_ID,GRP_NAME,CREATE_DATE,CREATED_BY,COMPANY_ID,GROUP_DESC,STATUS,LAST_UPDATE,LAST_UPDATED_BY,MANAGED_SYS_ID) values ('SEC_ADMIN_GRP','Sec Admin Group',null,null,null,null,null,null,null,null);
Insert into IAMUSER.GRP (GRP_ID,GRP_NAME,CREATE_DATE,CREATED_BY,COMPANY_ID,GROUP_DESC,STATUS,LAST_UPDATE,LAST_UPDATED_BY,MANAGED_SYS_ID) values ('SUPER_SEC_ADMIN_GRP','Super Admin Group',null,null,null,null,null,null,null,null);
REM INSERTING into IAMUSER.GRP_ATTRIBUTES
SET DEFINE OFF;
REM INSERTING into IAMUSER.GRP_ROLE
SET DEFINE OFF;
Insert into IAMUSER.GRP_ROLE (GRP_ID,ROLE_ID) values ('END_USER_GRP','1');
Insert into IAMUSER.GRP_ROLE (GRP_ID,ROLE_ID) values ('HR_GRP','3');
Insert into IAMUSER.GRP_ROLE (GRP_ID,ROLE_ID) values ('MNGR_GRP','4');
Insert into IAMUSER.GRP_ROLE (GRP_ID,ROLE_ID) values ('SECURITY_GRP','5');
Insert into IAMUSER.GRP_ROLE (GRP_ID,ROLE_ID) values ('SUPER_SEC_ADMIN_GRP','9');
REM INSERTING into IAMUSER.GRP_TO_GRP_MEMBERSHIP
SET DEFINE OFF;
REM INSERTING into IAMUSER.IDENTITY_QUESTION
SET DEFINE OFF;
Insert into IAMUSER.IDENTITY_QUESTION (IDENTITY_QUESTION_ID,IDENTITY_QUEST_GRP_ID,QUESTION_TEXT,ACTIVE) values ('200','GLOBAL','What are the last four digits of your social security number?','Y');
Insert into IAMUSER.IDENTITY_QUESTION (IDENTITY_QUESTION_ID,IDENTITY_QUEST_GRP_ID,QUESTION_TEXT,ACTIVE) values ('201','GLOBAL','What is your mothers maiden name?','Y');
Insert into IAMUSER.IDENTITY_QUESTION (IDENTITY_QUESTION_ID,IDENTITY_QUEST_GRP_ID,QUESTION_TEXT,ACTIVE) values ('202','GLOBAL','Where did you go to school?','Y');
Insert into IAMUSER.IDENTITY_QUESTION (IDENTITY_QUESTION_ID,IDENTITY_QUEST_GRP_ID,QUESTION_TEXT,ACTIVE) values ('203','GLOBAL','What is your pets name?','Y');
Insert into IAMUSER.IDENTITY_QUESTION (IDENTITY_QUESTION_ID,IDENTITY_QUEST_GRP_ID,QUESTION_TEXT,ACTIVE) values ('204','GLOBAL','What is your favorite food?','Y');
Insert into IAMUSER.IDENTITY_QUESTION (IDENTITY_QUESTION_ID,IDENTITY_QUEST_GRP_ID,QUESTION_TEXT,ACTIVE) values ('205','GLOBAL','What is your favorite color?','Y');
Insert into IAMUSER.IDENTITY_QUESTION (IDENTITY_QUESTION_ID,IDENTITY_QUEST_GRP_ID,QUESTION_TEXT,ACTIVE) values ('206','GLOBAL','Which city were you born in?','Y');
Insert into IAMUSER.IDENTITY_QUESTION (IDENTITY_QUESTION_ID,IDENTITY_QUEST_GRP_ID,QUESTION_TEXT,ACTIVE) values ('207','GLOBAL','What is your favorite sport?','Y');
Insert into IAMUSER.IDENTITY_QUESTION (IDENTITY_QUESTION_ID,IDENTITY_QUEST_GRP_ID,QUESTION_TEXT,ACTIVE) values ('209','GLOBAL','What are the last four digits of your drivers license?','Y');
Insert into IAMUSER.IDENTITY_QUESTION (IDENTITY_QUESTION_ID,IDENTITY_QUEST_GRP_ID,QUESTION_TEXT,ACTIVE) values ('210','GLOBAL','What is the name of your favorite school?','Y');
Insert into IAMUSER.IDENTITY_QUESTION (IDENTITY_QUESTION_ID,IDENTITY_QUEST_GRP_ID,QUESTION_TEXT,ACTIVE) values ('211','GLOBAL','What is the name of your first pet','Y');
Insert into IAMUSER.IDENTITY_QUESTION (IDENTITY_QUESTION_ID,IDENTITY_QUEST_GRP_ID,QUESTION_TEXT,ACTIVE) values ('212','GLOBAL','What is the name of your favorite movie, play or song?','Y');
Insert into IAMUSER.IDENTITY_QUESTION (IDENTITY_QUESTION_ID,IDENTITY_QUEST_GRP_ID,QUESTION_TEXT,ACTIVE) values ('213','GLOBAL','What is the title of your favorite book?','Y');
Insert into IAMUSER.IDENTITY_QUESTION (IDENTITY_QUESTION_ID,IDENTITY_QUEST_GRP_ID,QUESTION_TEXT,ACTIVE) values ('214','GLOBAL','What is the name of your first boy or girl friend?','Y');
Insert into IAMUSER.IDENTITY_QUESTION (IDENTITY_QUESTION_ID,IDENTITY_QUEST_GRP_ID,QUESTION_TEXT,ACTIVE) values ('215','GLOBAL','What is the name of your favorite school teacher?','Y');
Insert into IAMUSER.IDENTITY_QUESTION (IDENTITY_QUESTION_ID,IDENTITY_QUEST_GRP_ID,QUESTION_TEXT,ACTIVE) values ('216','GLOBAL','What is the year and nick name of your first car (Year-Name)?','Y');
Insert into IAMUSER.IDENTITY_QUESTION (IDENTITY_QUESTION_ID,IDENTITY_QUEST_GRP_ID,QUESTION_TEXT,ACTIVE) values ('217','GLOBAL','Where did you meet your significant other?','Y');
Insert into IAMUSER.IDENTITY_QUESTION (IDENTITY_QUESTION_ID,IDENTITY_QUEST_GRP_ID,QUESTION_TEXT,ACTIVE) values ('218','GLOBAL','What is the type and name of your first pet (Type-Name)?','Y');
REM INSERTING into IAMUSER.IDENTITY_QUEST_GRP
SET DEFINE OFF;
Insert into IAMUSER.IDENTITY_QUEST_GRP (IDENTITY_QUEST_GRP_ID,NAME,STATUS,COMPANY_OWNER_ID,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY) values ('GLOBAL','GLOBAL IDENTITY QUESTIONS','ACTIVE','GLOBAL',null,null,null,null);
REM INSERTING into IAMUSER.IMAGE
SET DEFINE OFF;
REM INSERTING into IAMUSER.IT_POLICY
SET DEFINE OFF;
REM INSERTING into IAMUSER.LANGUAGE
SET DEFINE OFF;
Insert into IAMUSER.LANGUAGE (ID,LANGUAGE,IS_USED,LANGUAGE_CODE,IS_DEFAULT) values ('1','English','Y','en','Y');
Insert into IAMUSER.LANGUAGE (ID,LANGUAGE,IS_USED,LANGUAGE_CODE,IS_DEFAULT) values ('2','German','N','de','N');
Insert into IAMUSER.LANGUAGE (ID,LANGUAGE,IS_USED,LANGUAGE_CODE,IS_DEFAULT) values ('3','French','N','fr','N');
Insert into IAMUSER.LANGUAGE (ID,LANGUAGE,IS_USED,LANGUAGE_CODE,IS_DEFAULT) values ('4','Spanish','N','es','N');
Insert into IAMUSER.LANGUAGE (ID,LANGUAGE,IS_USED,LANGUAGE_CODE,IS_DEFAULT) values ('5','Italian','N','it','N');
Insert into IAMUSER.LANGUAGE (ID,LANGUAGE,IS_USED,LANGUAGE_CODE,IS_DEFAULT) values ('6','Dutch','N','nl','N');
Insert into IAMUSER.LANGUAGE (ID,LANGUAGE,IS_USED,LANGUAGE_CODE,IS_DEFAULT) values ('7','Portugese','N','pt','N');
REM INSERTING into IAMUSER.LANGUAGE_LOCALE
SET DEFINE OFF;
Insert into IAMUSER.LANGUAGE_LOCALE (ID,LANGUAGE_ID,LOCALE) values ('4','2','de_DE');
Insert into IAMUSER.LANGUAGE_LOCALE (ID,LANGUAGE_ID,LOCALE) values ('1','1','en_EN');
Insert into IAMUSER.LANGUAGE_LOCALE (ID,LANGUAGE_ID,LOCALE) values ('3','1','en_GB');
Insert into IAMUSER.LANGUAGE_LOCALE (ID,LANGUAGE_ID,LOCALE) values ('2','1','en_US');
Insert into IAMUSER.LANGUAGE_LOCALE (ID,LANGUAGE_ID,LOCALE) values ('6','4','es_ES');
Insert into IAMUSER.LANGUAGE_LOCALE (ID,LANGUAGE_ID,LOCALE) values ('5','3','fr_FR');
Insert into IAMUSER.LANGUAGE_LOCALE (ID,LANGUAGE_ID,LOCALE) values ('7','5','it_IT');
Insert into IAMUSER.LANGUAGE_LOCALE (ID,LANGUAGE_ID,LOCALE) values ('8','6','nl_NL');
Insert into IAMUSER.LANGUAGE_LOCALE (ID,LANGUAGE_ID,LOCALE) values ('9','7','pt_PT');
REM INSERTING into IAMUSER.LANGUAGE_MAPPING
SET DEFINE OFF;
REM INSERTING into IAMUSER.LOCATION
SET DEFINE OFF;
Insert into IAMUSER.LOCATION (LOCATION_ID,NAME,DESCRIPTION,COUNTRY,BLDG_NUM,STREET_DIRECTION,ADDRESS1,ADDRESS2,ADDRESS3,CITY,STATE,POSTAL_CD,ORGANIZATION_ID,INTERNAL_LOCATION_ID,ACTIVE,SENSITIVE_LOCATION) values ('100','HQ',null,'US','111',null,'MAIN ST',null,null,'MY TOWN','NY','12345',null,null,1,0);
Insert into IAMUSER.LOCATION (LOCATION_ID,NAME,DESCRIPTION,COUNTRY,BLDG_NUM,STREET_DIRECTION,ADDRESS1,ADDRESS2,ADDRESS3,CITY,STATE,POSTAL_CD,ORGANIZATION_ID,INTERNAL_LOCATION_ID,ACTIVE,SENSITIVE_LOCATION) values ('101','BRANCH',null,'US','112',null,'Next ST',null,null,'MY TOWN','CT','67890',null,null,1,0);
REM INSERTING into IAMUSER.LOGIN
SET DEFINE OFF;
Insert into IAMUSER.LOGIN (SERVICE_ID,LOGIN,LOWERCASE_LOGIN,MANAGED_SYS_ID,IDENTITY_TYPE,CANONICAL_NAME,USER_ID,PASSWORD,PWD_EQUIVALENT_TOKEN,PWD_CHANGED,PWD_EXP,RESET_PWD,FIRST_TIME_LOGIN,IS_LOCKED,STATUS,GRACE_PERIOD,CREATE_DATE,CREATED_BY,CURRENT_LOGIN_HOST,AUTH_FAIL_COUNT,LAST_AUTH_ATTEMPT,LAST_LOGIN,LAST_LOGIN_IP,PREV_LOGIN,PREV_LOGIN_IP,IS_DEFAULT,PWD_CHANGE_COUNT,PSWD_RESET_TOKEN,PSWD_RESET_TOKEN_EXP,LOGIN_ID,LAST_UPDATE) values ('IDM','system','system','0',null,null,'0001','passwd00',null,null,null,0,1,0,null,null,to_date('28-MAR-14','DD-MON-RR'),null,null,0,null,null,null,null,null,0,0,null,null,'0',to_date('28-MAR-14','DD-MON-RR'));
Insert into IAMUSER.LOGIN (SERVICE_ID,LOGIN,LOWERCASE_LOGIN,MANAGED_SYS_ID,IDENTITY_TYPE,CANONICAL_NAME,USER_ID,PASSWORD,PWD_EQUIVALENT_TOKEN,PWD_CHANGED,PWD_EXP,RESET_PWD,FIRST_TIME_LOGIN,IS_LOCKED,STATUS,GRACE_PERIOD,CREATE_DATE,CREATED_BY,CURRENT_LOGIN_HOST,AUTH_FAIL_COUNT,LAST_AUTH_ATTEMPT,LAST_LOGIN,LAST_LOGIN_IP,PREV_LOGIN,PREV_LOGIN_IP,IS_DEFAULT,PWD_CHANGE_COUNT,PSWD_RESET_TOKEN,PSWD_RESET_TOKEN_EXP,LOGIN_ID,LAST_UPDATE) values ('IDM','sysadmin','sysadmin','0',null,null,'3000','passwd00',null,null,null,0,1,0,null,null,to_date('28-MAR-14','DD-MON-RR'),null,null,0,null,null,null,null,null,0,0,null,null,'1',to_date('28-MAR-14','DD-MON-RR'));
Insert into IAMUSER.LOGIN (SERVICE_ID,LOGIN,LOWERCASE_LOGIN,MANAGED_SYS_ID,IDENTITY_TYPE,CANONICAL_NAME,USER_ID,PASSWORD,PWD_EQUIVALENT_TOKEN,PWD_CHANGED,PWD_EXP,RESET_PWD,FIRST_TIME_LOGIN,IS_LOCKED,STATUS,GRACE_PERIOD,CREATE_DATE,CREATED_BY,CURRENT_LOGIN_HOST,AUTH_FAIL_COUNT,LAST_AUTH_ATTEMPT,LAST_LOGIN,LAST_LOGIN_IP,PREV_LOGIN,PREV_LOGIN_IP,IS_DEFAULT,PWD_CHANGE_COUNT,PSWD_RESET_TOKEN,PSWD_RESET_TOKEN_EXP,LOGIN_ID,LAST_UPDATE) values ('IDM','sysadmin2','sysadmin2','0',null,null,'3001','passwd00',null,null,null,0,1,0,null,null,to_date('28-MAR-14','DD-MON-RR'),null,null,0,null,null,null,null,null,0,0,null,null,'2',to_date('28-MAR-14','DD-MON-RR'));
Insert into IAMUSER.LOGIN (SERVICE_ID,LOGIN,LOWERCASE_LOGIN,MANAGED_SYS_ID,IDENTITY_TYPE,CANONICAL_NAME,USER_ID,PASSWORD,PWD_EQUIVALENT_TOKEN,PWD_CHANGED,PWD_EXP,RESET_PWD,FIRST_TIME_LOGIN,IS_LOCKED,STATUS,GRACE_PERIOD,CREATE_DATE,CREATED_BY,CURRENT_LOGIN_HOST,AUTH_FAIL_COUNT,LAST_AUTH_ATTEMPT,LAST_LOGIN,LAST_LOGIN_IP,PREV_LOGIN,PREV_LOGIN_IP,IS_DEFAULT,PWD_CHANGE_COUNT,PSWD_RESET_TOKEN,PSWD_RESET_TOKEN_EXP,LOGIN_ID,LAST_UPDATE) values ('USR_SEC_DOMAIN','helpdesk','helpdesk','0',null,null,'3010','passwd00',null,null,null,0,1,0,null,null,to_date('28-MAR-14','DD-MON-RR'),null,null,0,null,null,null,null,null,0,0,null,null,'3',to_date('28-MAR-14','DD-MON-RR'));
Insert into IAMUSER.LOGIN (SERVICE_ID,LOGIN,LOWERCASE_LOGIN,MANAGED_SYS_ID,IDENTITY_TYPE,CANONICAL_NAME,USER_ID,PASSWORD,PWD_EQUIVALENT_TOKEN,PWD_CHANGED,PWD_EXP,RESET_PWD,FIRST_TIME_LOGIN,IS_LOCKED,STATUS,GRACE_PERIOD,CREATE_DATE,CREATED_BY,CURRENT_LOGIN_HOST,AUTH_FAIL_COUNT,LAST_AUTH_ATTEMPT,LAST_LOGIN,LAST_LOGIN_IP,PREV_LOGIN,PREV_LOGIN_IP,IS_DEFAULT,PWD_CHANGE_COUNT,PSWD_RESET_TOKEN,PSWD_RESET_TOKEN_EXP,LOGIN_ID,LAST_UPDATE) values ('USR_SEC_DOMAIN','hrmanager','hrmanager','0',null,null,'3007','passwd00',null,null,null,0,1,0,null,null,to_date('28-MAR-14','DD-MON-RR'),null,null,0,null,null,null,null,null,0,0,null,null,'4',to_date('28-MAR-14','DD-MON-RR'));
Insert into IAMUSER.LOGIN (SERVICE_ID,LOGIN,LOWERCASE_LOGIN,MANAGED_SYS_ID,IDENTITY_TYPE,CANONICAL_NAME,USER_ID,PASSWORD,PWD_EQUIVALENT_TOKEN,PWD_CHANGED,PWD_EXP,RESET_PWD,FIRST_TIME_LOGIN,IS_LOCKED,STATUS,GRACE_PERIOD,CREATE_DATE,CREATED_BY,CURRENT_LOGIN_HOST,AUTH_FAIL_COUNT,LAST_AUTH_ATTEMPT,LAST_LOGIN,LAST_LOGIN_IP,PREV_LOGIN,PREV_LOGIN_IP,IS_DEFAULT,PWD_CHANGE_COUNT,PSWD_RESET_TOKEN,PSWD_RESET_TOKEN_EXP,LOGIN_ID,LAST_UPDATE) values ('USR_SEC_DOMAIN','manager','manager','0',null,null,'3008','passwd00',null,null,null,0,1,0,null,null,to_date('28-MAR-14','DD-MON-RR'),null,null,0,null,null,null,null,null,0,0,null,null,'5',to_date('28-MAR-14','DD-MON-RR'));
Insert into IAMUSER.LOGIN (SERVICE_ID,LOGIN,LOWERCASE_LOGIN,MANAGED_SYS_ID,IDENTITY_TYPE,CANONICAL_NAME,USER_ID,PASSWORD,PWD_EQUIVALENT_TOKEN,PWD_CHANGED,PWD_EXP,RESET_PWD,FIRST_TIME_LOGIN,IS_LOCKED,STATUS,GRACE_PERIOD,CREATE_DATE,CREATED_BY,CURRENT_LOGIN_HOST,AUTH_FAIL_COUNT,LAST_AUTH_ATTEMPT,LAST_LOGIN,LAST_LOGIN_IP,PREV_LOGIN,PREV_LOGIN_IP,IS_DEFAULT,PWD_CHANGE_COUNT,PSWD_RESET_TOKEN,PSWD_RESET_TOKEN_EXP,LOGIN_ID,LAST_UPDATE) values ('USR_SEC_DOMAIN','secmanager','secmanager','0',null,null,'3009','passwd00',null,null,null,0,1,0,null,null,to_date('28-MAR-14','DD-MON-RR'),null,null,0,null,null,null,null,null,0,0,null,null,'6',to_date('28-MAR-14','DD-MON-RR'));
Insert into IAMUSER.LOGIN (SERVICE_ID,LOGIN,LOWERCASE_LOGIN,MANAGED_SYS_ID,IDENTITY_TYPE,CANONICAL_NAME,USER_ID,PASSWORD,PWD_EQUIVALENT_TOKEN,PWD_CHANGED,PWD_EXP,RESET_PWD,FIRST_TIME_LOGIN,IS_LOCKED,STATUS,GRACE_PERIOD,CREATE_DATE,CREATED_BY,CURRENT_LOGIN_HOST,AUTH_FAIL_COUNT,LAST_AUTH_ATTEMPT,LAST_LOGIN,LAST_LOGIN_IP,PREV_LOGIN,PREV_LOGIN_IP,IS_DEFAULT,PWD_CHANGE_COUNT,PSWD_RESET_TOKEN,PSWD_RESET_TOKEN_EXP,LOGIN_ID,LAST_UPDATE) values ('USR_SEC_DOMAIN','snelson','snelson','0',null,null,'3006','passwd00',null,null,null,0,1,0,null,null,to_date('28-MAR-14','DD-MON-RR'),null,null,0,null,null,null,null,null,0,0,null,null,'7',to_date('28-MAR-14','DD-MON-RR'));
Insert into IAMUSER.LOGIN (SERVICE_ID,LOGIN,LOWERCASE_LOGIN,MANAGED_SYS_ID,IDENTITY_TYPE,CANONICAL_NAME,USER_ID,PASSWORD,PWD_EQUIVALENT_TOKEN,PWD_CHANGED,PWD_EXP,RESET_PWD,FIRST_TIME_LOGIN,IS_LOCKED,STATUS,GRACE_PERIOD,CREATE_DATE,CREATED_BY,CURRENT_LOGIN_HOST,AUTH_FAIL_COUNT,LAST_AUTH_ATTEMPT,LAST_LOGIN,LAST_LOGIN_IP,PREV_LOGIN,PREV_LOGIN_IP,IS_DEFAULT,PWD_CHANGE_COUNT,PSWD_RESET_TOKEN,PSWD_RESET_TOKEN_EXP,LOGIN_ID,LAST_UPDATE) values ('USR_SEC_DOMAIN','sysadmin','sysadmin','0',null,null,'3000','passwd00',null,null,null,0,1,0,null,null,to_date('28-MAR-14','DD-MON-RR'),null,null,0,null,null,null,null,null,0,0,null,null,'9',to_date('28-MAR-14','DD-MON-RR'));
REM INSERTING into IAMUSER.LOGIN_ATTRIBUTE
SET DEFINE OFF;
REM INSERTING into IAMUSER.MANAGED_SYS
SET DEFINE OFF;
Insert into IAMUSER.MANAGED_SYS (MANAGED_SYS_ID,NAME,DESCRIPTION,STATUS,CONNECTOR_ID,DOMAIN_ID,HOST_URL,APPL_URL,PORT,COMM_PROTOCOL,USER_ID,PSWD,START_DATE,END_DATE,RESOURCE_ID,PRIMARY_REPOSITORY,SECONDARY_REPOSITORY_ID,ALWAYS_UPDATE_SECONDARY,RES_DEPENDENCY,ADD_HNDLR,MODIFY_HNDLR,DELETE_HNDLR,SETPASS_HNDLR,SUSPEND_HNDLR,SEARCH_HNDLR,LOOKUP_HNDLR,TEST_CONNECTION_HNDLR,RECONCILE_RESOURCE_HNDLR,HNDLR_5,DRIVER_URL,CONNECTION_STRING,ATTRIBUTE_NAMES_LOOKUP,ATTRIBUTE_NAMES_HNDLR,SEARCH_SCOPE) values ('0','OPENIAM','Primary IDENTITY','ACTIVE',null,'USR_SEC_DOMAIN',null,null,null,null,'0001',null,null,null,'0',null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,2);
Insert into IAMUSER.MANAGED_SYS (MANAGED_SYS_ID,NAME,DESCRIPTION,STATUS,CONNECTOR_ID,DOMAIN_ID,HOST_URL,APPL_URL,PORT,COMM_PROTOCOL,USER_ID,PSWD,START_DATE,END_DATE,RESOURCE_ID,PRIMARY_REPOSITORY,SECONDARY_REPOSITORY_ID,ALWAYS_UPDATE_SECONDARY,RES_DEPENDENCY,ADD_HNDLR,MODIFY_HNDLR,DELETE_HNDLR,SETPASS_HNDLR,SUSPEND_HNDLR,SEARCH_HNDLR,LOOKUP_HNDLR,TEST_CONNECTION_HNDLR,RECONCILE_RESOURCE_HNDLR,HNDLR_5,DRIVER_URL,CONNECTION_STRING,ATTRIBUTE_NAMES_LOOKUP,ATTRIBUTE_NAMES_HNDLR,SEARCH_SCOPE) values ('101','LDAP','Primary Directory','ACTIVE','51','USR_SEC_DOMAIN','ldap://',null,389,'CLEAR','0001',null,null,null,'101',null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,2);
Insert into IAMUSER.MANAGED_SYS (MANAGED_SYS_ID,NAME,DESCRIPTION,STATUS,CONNECTOR_ID,DOMAIN_ID,HOST_URL,APPL_URL,PORT,COMM_PROTOCOL,USER_ID,PSWD,START_DATE,END_DATE,RESOURCE_ID,PRIMARY_REPOSITORY,SECONDARY_REPOSITORY_ID,ALWAYS_UPDATE_SECONDARY,RES_DEPENDENCY,ADD_HNDLR,MODIFY_HNDLR,DELETE_HNDLR,SETPASS_HNDLR,SUSPEND_HNDLR,SEARCH_HNDLR,LOOKUP_HNDLR,TEST_CONNECTION_HNDLR,RECONCILE_RESOURCE_HNDLR,HNDLR_5,DRIVER_URL,CONNECTION_STRING,ATTRIBUTE_NAMES_LOOKUP,ATTRIBUTE_NAMES_HNDLR,SEARCH_SCOPE) values ('103','Google Apps Connector','Google Apps Connector','ACTIVE','54','USR_SEC_DOMAIN',null,null,null,'CLEAR','0001',null,null,null,'103',null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,2);
Insert into IAMUSER.MANAGED_SYS (MANAGED_SYS_ID,NAME,DESCRIPTION,STATUS,CONNECTOR_ID,DOMAIN_ID,HOST_URL,APPL_URL,PORT,COMM_PROTOCOL,USER_ID,PSWD,START_DATE,END_DATE,RESOURCE_ID,PRIMARY_REPOSITORY,SECONDARY_REPOSITORY_ID,ALWAYS_UPDATE_SECONDARY,RES_DEPENDENCY,ADD_HNDLR,MODIFY_HNDLR,DELETE_HNDLR,SETPASS_HNDLR,SUSPEND_HNDLR,SEARCH_HNDLR,LOOKUP_HNDLR,TEST_CONNECTION_HNDLR,RECONCILE_RESOURCE_HNDLR,HNDLR_5,DRIVER_URL,CONNECTION_STRING,ATTRIBUTE_NAMES_LOOKUP,ATTRIBUTE_NAMES_HNDLR,SEARCH_SCOPE) values ('104','Test_Script_CONNECTOR','Test Script CONNECTOR','ACTIVE','62','USR_SEC_DOMAIN',null,null,null,'CLEAR','0001',null,null,null,'104',null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,2);
Insert into IAMUSER.MANAGED_SYS (MANAGED_SYS_ID,NAME,DESCRIPTION,STATUS,CONNECTOR_ID,DOMAIN_ID,HOST_URL,APPL_URL,PORT,COMM_PROTOCOL,USER_ID,PSWD,START_DATE,END_DATE,RESOURCE_ID,PRIMARY_REPOSITORY,SECONDARY_REPOSITORY_ID,ALWAYS_UPDATE_SECONDARY,RES_DEPENDENCY,ADD_HNDLR,MODIFY_HNDLR,DELETE_HNDLR,SETPASS_HNDLR,SUSPEND_HNDLR,SEARCH_HNDLR,LOOKUP_HNDLR,TEST_CONNECTION_HNDLR,RECONCILE_RESOURCE_HNDLR,HNDLR_5,DRIVER_URL,CONNECTION_STRING,ATTRIBUTE_NAMES_LOOKUP,ATTRIBUTE_NAMES_HNDLR,SEARCH_SCOPE) values ('105','Linux CONNECTOR','Linux CONNECTOR','ACTIVE','63','USR_SEC_DOMAIN',null,null,null,'CLEAR','0001',null,null,null,'105',null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,2);
Insert into IAMUSER.MANAGED_SYS (MANAGED_SYS_ID,NAME,DESCRIPTION,STATUS,CONNECTOR_ID,DOMAIN_ID,HOST_URL,APPL_URL,PORT,COMM_PROTOCOL,USER_ID,PSWD,START_DATE,END_DATE,RESOURCE_ID,PRIMARY_REPOSITORY,SECONDARY_REPOSITORY_ID,ALWAYS_UPDATE_SECONDARY,RES_DEPENDENCY,ADD_HNDLR,MODIFY_HNDLR,DELETE_HNDLR,SETPASS_HNDLR,SUSPEND_HNDLR,SEARCH_HNDLR,LOOKUP_HNDLR,TEST_CONNECTION_HNDLR,RECONCILE_RESOURCE_HNDLR,HNDLR_5,DRIVER_URL,CONNECTION_STRING,ATTRIBUTE_NAMES_LOOKUP,ATTRIBUTE_NAMES_HNDLR,SEARCH_SCOPE) values ('106','MySQL CONNECTOR','MySQL CONNECTOR','ACTIVE','64','USR_SEC_DOMAIN',null,null,null,'CLEAR','0001',null,null,null,'106',null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,2);
Insert into IAMUSER.MANAGED_SYS (MANAGED_SYS_ID,NAME,DESCRIPTION,STATUS,CONNECTOR_ID,DOMAIN_ID,HOST_URL,APPL_URL,PORT,COMM_PROTOCOL,USER_ID,PSWD,START_DATE,END_DATE,RESOURCE_ID,PRIMARY_REPOSITORY,SECONDARY_REPOSITORY_ID,ALWAYS_UPDATE_SECONDARY,RES_DEPENDENCY,ADD_HNDLR,MODIFY_HNDLR,DELETE_HNDLR,SETPASS_HNDLR,SUSPEND_HNDLR,SEARCH_HNDLR,LOOKUP_HNDLR,TEST_CONNECTION_HNDLR,RECONCILE_RESOURCE_HNDLR,HNDLR_5,DRIVER_URL,CONNECTION_STRING,ATTRIBUTE_NAMES_LOOKUP,ATTRIBUTE_NAMES_HNDLR,SEARCH_SCOPE) values ('110','ACTIVE DIRECTORY','ACTIVE DIRECTORY','ACTIVE','51','USR_SEC_DOMAIN','ldaps://',null,636,'CLEAR','0001',null,null,null,'110',null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,2);
Insert into IAMUSER.MANAGED_SYS (MANAGED_SYS_ID,NAME,DESCRIPTION,STATUS,CONNECTOR_ID,DOMAIN_ID,HOST_URL,APPL_URL,PORT,COMM_PROTOCOL,USER_ID,PSWD,START_DATE,END_DATE,RESOURCE_ID,PRIMARY_REPOSITORY,SECONDARY_REPOSITORY_ID,ALWAYS_UPDATE_SECONDARY,RES_DEPENDENCY,ADD_HNDLR,MODIFY_HNDLR,DELETE_HNDLR,SETPASS_HNDLR,SUSPEND_HNDLR,SEARCH_HNDLR,LOOKUP_HNDLR,TEST_CONNECTION_HNDLR,RECONCILE_RESOURCE_HNDLR,HNDLR_5,DRIVER_URL,CONNECTION_STRING,ATTRIBUTE_NAMES_LOOKUP,ATTRIBUTE_NAMES_HNDLR,SEARCH_SCOPE) values ('111','Oracle Connector','Oracle Connector','ACTIVE','65','USR_SEC_DOMAIN',null,null,null,'CLEAR','0001',null,null,null,'111',null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,2);
Insert into IAMUSER.MANAGED_SYS (MANAGED_SYS_ID,NAME,DESCRIPTION,STATUS,CONNECTOR_ID,DOMAIN_ID,HOST_URL,APPL_URL,PORT,COMM_PROTOCOL,USER_ID,PSWD,START_DATE,END_DATE,RESOURCE_ID,PRIMARY_REPOSITORY,SECONDARY_REPOSITORY_ID,ALWAYS_UPDATE_SECONDARY,RES_DEPENDENCY,ADD_HNDLR,MODIFY_HNDLR,DELETE_HNDLR,SETPASS_HNDLR,SUSPEND_HNDLR,SEARCH_HNDLR,LOOKUP_HNDLR,TEST_CONNECTION_HNDLR,RECONCILE_RESOURCE_HNDLR,HNDLR_5,DRIVER_URL,CONNECTION_STRING,ATTRIBUTE_NAMES_LOOKUP,ATTRIBUTE_NAMES_HNDLR,SEARCH_SCOPE) values ('113','AppTableMSys',null,'ACTIVE','61',null,null,null,null,'CLEAR','root',null,null,null,'113',0,null,0,null,null,null,null,null,null,null,null,null,null,null,'com.mysql.jdbc.Driver','jdbc:mysql://localhost/test',null,null,2);
REM INSERTING into IAMUSER.MANAGED_SYS_ATTRIBUTE
SET DEFINE OFF;
REM INSERTING into IAMUSER.MANAGED_SYS_RULE
SET DEFINE OFF;
REM INSERTING into IAMUSER.MD_ELEMENT_VALID_VALUES
SET DEFINE OFF;
REM INSERTING into IAMUSER.METADATA_ELEMENT
SET DEFINE OFF;
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('101','InetOrgPerson','Display Name',null,null,'Y','N','Y',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('104','InetOrgPerson','Preferred Language',null,null,'Y','N','Y',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('105','InetOrgPerson','VehicleLicense',null,null,'Y','N','Y',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('106','InetOrgPerson','Given Name',null,null,'Y','N','Y',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('107','InetOrgPerson','LabeledURI',null,null,'Y','N','Y',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('108','InetOrgPerson','Initials',null,null,'Y','N','Y',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('110','InetOrgPerson','BusinessCategory',null,null,'Y','N','Y',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('131','Contractor','Display Name',null,null,'Y','N','Y',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('134','Contractor','Preferred Language',null,null,'Y','N','Y',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('135','Contractor','VehicleLicense',null,null,'Y','N','Y',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('136','Contractor','Given Name',null,null,'Y','N','Y',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('137','Contractor','LabeledURI',null,null,'Y','N','Y',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('138','Contractor','Initials',null,null,'Y','N','Y',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('140','DIRECTORY','Display Name',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('150','FILE','Display Name',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('160','URL','Display Name',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('260','Contractor','BusinessCategory',null,null,'Y','N','Y',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('261','Contractor','StartDate',null,null,'Y','N','Y',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('262','Contractor','EndDate',null,null,'Y','N','Y',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('300','LdapOrgPerson','name',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('301','LdapOrgPerson','distinguishedName',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('302','LdapOrgPerson','objectclass',null,null,'Y','Y','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('303','LdapOrgPerson','aliasedObjectName',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('304','LdapOrgPerson','cn',null,null,'Y','Y','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('305','LdapOrgPerson','sn',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('306','LdapOrgPerson','serialNumber',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('307','LdapOrgPerson','c',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('308','LdapOrgPerson','l',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('309','LdapOrgPerson','st',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('310','LdapOrgPerson','street',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('311','LdapOrgPerson','o',null,null,'Y','Y','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('312','LdapOrgPerson','ou',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('313','LdapOrgPerson','title',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('314','LdapOrgPerson','description',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('315','LdapOrgPerson','businessCategory',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('316','LdapOrgPerson','postalAddress',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('317','LdapOrgPerson','postalCode',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('318','LdapOrgPerson','postOfficeBox',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('319','LdapOrgPerson','physicalDeliveryOfficeName',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('320','LdapOrgPerson','telephoneNumber',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('321','LdapOrgPerson','telexNumber',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('322','LdapOrgPerson','teletexTerminalIdentifier',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('323','LdapOrgPerson','facsimileTelephoneNumber',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('324','LdapOrgPerson','x121Address',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('325','LdapOrgPerson','internationaliSDNNumber',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('326','LdapOrgPerson','registeredAddress',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('327','LdapOrgPerson','destinationIndicator',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('328','LdapOrgPerson','preferredDeliveryMethod',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('329','LdapOrgPerson','presentationAddress',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('330','LdapOrgPerson','supportedApplicationContext',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('331','LdapOrgPerson','member',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('332','LdapOrgPerson','owner',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('333','LdapOrgPerson','roleOccupant',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('334','LdapOrgPerson','seeAlso',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('335','LdapOrgPerson','userPassword',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('336','LdapOrgPerson','userCertificate',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('337','LdapOrgPerson','cACertificate',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('338','LdapOrgPerson','authorityRevocationList',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('339','LdapOrgPerson','certificateRevocationList',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('340','LdapOrgPerson','crossCertificatePair',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('341','LdapOrgPerson','givenName',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('342','LdapOrgPerson','initials',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('343','LdapOrgPerson','generationQualifier',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('344','LdapOrgPerson','x500UniqueIdentifier',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('346','LdapOrgPerson','uniqueMember',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('347','LdapOrgPerson','houseIdentifier',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('348','LdapOrgPerson','uid',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('349','LdapOrgPerson','mail',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('350','LdapOrgPerson','ref',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('351','LdapOrgPerson','referral',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('352','LdapOrgPerson','krbName',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('401','MANAGED_SYS','SUBMIT_USER_TO_CONNECTOR',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('402','MANAGED_SYS','INCLUDE_IN_PASSWORD_SYNC',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('405','MANAGED_SYS','INCLUDE_IN_SYNC',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('406','MANAGED_SYS','TABLE_NAME',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('407','MANAGED_SYS','GROUP_MEMBERSHIP_ENABLED',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('408','MANAGED_SYS','ON_DELETE',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('409','MANAGED_SYS','PRE_PROCESS',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('410','MANAGED_SYS','POST_PROCESS',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('411','AUTH_REPO','HOST_URL',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('412','AUTH_REPO','BASE_DN',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('413','AUTH_REPO','HOST_LOGIN ID',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('415','AUTH_REPO','COMMUNICATION_PROTOCOL',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('416','AUTH_REPO','OBJECT_CLASS',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('418','AUTH_REPO','SEARCH_ATTRIBUTE',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('419','AUTH_REPO','MANAGED_SYS_ID',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('420','AUTH_REPO','DN_ATTRIBUTE',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('421','AUTH_REPO','PASSWORD',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('430','WEBSERVICE','END_POINT',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('440','WEBSERVICE_OP','OPERATION_NAME',null,null,'Y','N','N',null,null,null,null,'Y');
Insert into IAMUSER.METADATA_ELEMENT (METADATA_ID,TYPE_ID,ATTRIBUTE_NAME,DESCRIPTION,DATA_TYPE,AUDITABLE,REQUIRED,SELF_EDITABLE,TEMPLATE_ID,RESOURCE_ID,VALIDATOR,STATIC_DEFAULT_VALUE,IS_PUBLIC) values ('450','NO-PROVISION-APP','URL',null,null,'Y','N','N',null,null,null,null,'Y');
REM INSERTING into IAMUSER.METADATA_ELEMENT_PAGE_TEMPLATE
SET DEFINE OFF;
REM INSERTING into IAMUSER.METADATA_TYPE
SET DEFINE OFF;
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('ADGroup','AD User type','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('ADUser','AD User type','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('AD_Connector','Active Directory Connector','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('AD_GROUP_TYPE','AD GROUP TYPE','N','N',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('AUTHENTICATION_PROVIDER','Authentication Provider','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('AUTH_REPO','Authentication Repository','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('CHECKBOX','Checkbox','Y','N','UI_WIDGET');
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('CONTENT_PROVIDER','Content Provider','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('Contractor','Contractor user type','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('DATE','Date field','Y','N','UI_WIDGET');
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('DEFAULT_GROUP','DEFAULT GROUP TYPE','N','N',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('departmentType','Department','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('DIRECTORY','Directory','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('divisionType','Division','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('FILE','File','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('GOOGLE_Connector','GOOGLE APPS Connector','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('InetOrgPerson','InetOrgPerson user type','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('LdapOrgPerson','LdapOrgPerson User type','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('LDAP_Connector','LDAP Connector','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('LDAP_GROUP_TYPE','LDAP GROUP TYPE','N','N',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('LINUX_Connector','Linux Connector','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('MANAGED_SYS','Managed System','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('MENU_ITEM','Menu Item Type','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('MULTI_SELECT','MultiSelect list','Y','N','UI_WIDGET');
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('NO-PROVISION-APP','Un-Provisionable Apps','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('OrgOpenIAM','OpenIAM','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('PASSWORD','Password field','Y','N','UI_WIDGET');
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('RADIO','Radio button','Y','N','UI_WIDGET');
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('RDBMS_Connector','RDBMS Connector','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('SCRIPT_Connector','Script Connector','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('SELECT','Combo box','Y','N','UI_WIDGET');
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('SOAP_Connector','SOAP Connector','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('SystemAccount','System Account type','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('SYS_ACTION','System Actions','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('TEXT','Text field','Y','N','UI_WIDGET');
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('TEXTAREA','Multiline text field','Y','N','UI_WIDGET');
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('UI_TEMPLATE','UI_TEMPLATE','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('UI_WIDGET','UI_WIDGET','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('URL','URL','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('URL_PATTERN','Url Pattern','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('WEBSERVICE','Web Service','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('WEBSERVICE_OP','Web Service Operation','Y','Y',null);
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('WORK_EMAIL','Work Email','Y','N','EMAIL');
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('PRIMARY_EMAIL','Primary Email','Y','N','EMAIL');
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('HOME_EMAIL','Home Email','Y','N','EMAIL');
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('OFFICE_PHONE','Office phone','Y','N','PHONE');
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('CELL_PHONE','Cell phone','Y','N','PHONE');
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('HOME_PHONE','Home phone','Y','N','PHONE');
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('OFFICE_ADDRESS','Office Address','Y','N','ADDRESS');
Insert into IAMUSER.METADATA_TYPE (TYPE_ID,DESCRIPTION,ACTIVE,SYNC_MANAGED_SYS,GROUPING) values ('HOME_ADDRESS','Home Address','Y','N','ADDRESS');
REM INSERTING into IAMUSER.METADATA_URI_XREF
SET DEFINE OFF;
REM INSERTING into IAMUSER.MNG_SYS_GROUP
SET DEFINE OFF;
REM INSERTING into IAMUSER.MNG_SYS_LIST
SET DEFINE OFF;
REM INSERTING into IAMUSER.MNG_SYS_OBJECT_MATCH
SET DEFINE OFF;
Insert into IAMUSER.MNG_SYS_OBJECT_MATCH (OBJECT_SEARCH_ID,MANAGED_SYS_ID,OBJECT_TYPE,MATCH_METHOD,BASE_DN,SEARCH_BASE_DN,SEARCH_FILTER,KEY_FIELD) values ('101','101','USER','BASE_DN',null,null,'(&(objectclass=inetOrgPerson)(?))','uid');
Insert into IAMUSER.MNG_SYS_OBJECT_MATCH (OBJECT_SEARCH_ID,MANAGED_SYS_ID,OBJECT_TYPE,MATCH_METHOD,BASE_DN,SEARCH_BASE_DN,SEARCH_FILTER,KEY_FIELD) values ('110','110','USER','BASE_DN',null,null,'(&(objectclass=user)(?))','CN');
REM INSERTING into IAMUSER.NOTIFICATION_CONFIG
SET DEFINE OFF;
REM INSERTING into IAMUSER.OPENIAM_LOG
SET DEFINE OFF;
REM INSERTING into IAMUSER.OPENIAM_LOG_ATTRIBUTE
SET DEFINE OFF;
REM INSERTING into IAMUSER.OPENIAM_LOG_LOG_MEMBERSHIP
SET DEFINE OFF;
REM INSERTING into IAMUSER.OPENIAM_LOG_TARGET
SET DEFINE OFF;
REM INSERTING into IAMUSER.ORGANIZATION_TYPE
SET DEFINE OFF;
Insert into IAMUSER.ORGANIZATION_TYPE (ORG_TYPE_ID,NAME,DESCRIPTION) values ('ORGANIZATION','Organization',null);
Insert into IAMUSER.ORGANIZATION_TYPE (ORG_TYPE_ID,NAME,DESCRIPTION) values ('DEPARTMENT','Department',null);
Insert into IAMUSER.ORGANIZATION_TYPE (ORG_TYPE_ID,NAME,DESCRIPTION) values ('DIVISION','Division',null);
Insert into IAMUSER.ORGANIZATION_TYPE (ORG_TYPE_ID,NAME,DESCRIPTION) values ('SUBSIDIARY','Subsidiary',null);
REM INSERTING into IAMUSER.ORG_POLICY
SET DEFINE OFF;
REM INSERTING into IAMUSER.ORG_POLICY_USER_LOG
SET DEFINE OFF;
REM INSERTING into IAMUSER.ORG_STRUCTURE
SET DEFINE OFF;
REM INSERTING into IAMUSER.ORG_TYPE_VALID_MEMBERSHIP
SET DEFINE OFF;
Insert into IAMUSER.ORG_TYPE_VALID_MEMBERSHIP (ORG_TYPE_ID,MEMBER_ORG_TYPE_ID) values ('DIVISION','DEPARTMENT');
Insert into IAMUSER.ORG_TYPE_VALID_MEMBERSHIP (ORG_TYPE_ID,MEMBER_ORG_TYPE_ID) values ('ORGANIZATION','DEPARTMENT');
Insert into IAMUSER.ORG_TYPE_VALID_MEMBERSHIP (ORG_TYPE_ID,MEMBER_ORG_TYPE_ID) values ('ORGANIZATION','DIVISION');
Insert into IAMUSER.ORG_TYPE_VALID_MEMBERSHIP (ORG_TYPE_ID,MEMBER_ORG_TYPE_ID) values ('ORGANIZATION','SUBSIDIARY');
Insert into IAMUSER.ORG_TYPE_VALID_MEMBERSHIP (ORG_TYPE_ID,MEMBER_ORG_TYPE_ID) values ('SUBSIDIARY','DIVISION');
REM INSERTING into IAMUSER.PAGE_TEMPLATE_XREF
SET DEFINE OFF;
REM INSERTING into IAMUSER.PHONE
SET DEFINE OFF;
REM INSERTING into IAMUSER.POLICY
SET DEFINE OFF;
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4000','100','Default Pswd Policy','Default Password Policy',1,null,null,null,null,null,1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4001','103','Default Authn Policy','Default Authentication Policy',1,null,null,null,null,null,1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4501','104','cn','commonName',1,null,null,null,null,'provision/cn.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4502','104','mail','email address',1,null,null,null,null,'provision/mail.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4503','104','o','organization name',1,null,null,null,null,'provision/o.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4504','104','ou','organizationalUnitName',1,null,null,null,null,'provision/ou.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4505','104','postalCode','commonName',1,null,null,null,null,'provision/postalCode.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4506','104','sn','surname',1,null,null,null,null,'provision/sn.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4507','104','st','stateOrProvinceName',1,null,null,null,null,'provision/st.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4508','104','street','streetAddress',1,null,null,null,null,'provision/street.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4509','104','userPassword','password',1,null,null,null,null,'provision/userPassword.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4510','104','postalAddress','postalAddress',1,null,null,null,null,'provision/postalAddress.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4511','104','telephoneNumber','Primary Telephone',1,null,null,null,null,'provision/telephone.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4512','104','facsimileTelephoneNumber','Fax',1,null,null,null,null,'provision/fax.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4513','104','mobile','mobileTelephoneNumber',1,null,null,null,null,'provision/mobile.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4514','104','gn','givenName',1,null,null,null,null,'provision/gn.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4515','104','uid','User Id',1,null,null,null,null,'provision/uid.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4516','104','departmentCD','Department Code',1,null,null,null,null,'provision/deptcd.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4517','104','displayName','Display Name',1,null,null,null,null,'provision/displayName.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4518','104','employeeType','Employee Type',1,null,null,null,null,'provision/employeeType.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4519','104','initials','Intials',1,null,null,null,null,'provision/initials.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4521','104','objectclass','Department Number',1,null,null,null,null,'provision/objectclass.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4522','104','title','Title',1,null,null,null,null,'provision/title.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4540','104','userDefineEmail','email address',1,null,null,null,null,'provision/emailUserDefined.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4541','104','dob','Date of Birth',1,null,null,null,null,'provision/dob.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4542','104','middleInit','Middle Initial',1,null,null,null,null,'provision/middleInit.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4543','104','employeeId','Employee ID',1,null,null,null,null,'provision/employeeId.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4544','104','userDefinedPassword','Password By User',1,null,null,null,null,'provision/userDefPassword.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4545','104','userRole','Role Membership',1,null,null,null,null,'provision/userRole.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4546','104','userGroup','Group Membership',1,null,null,null,null,'provision/userGroup.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4547','104','isEnabled','Is the User Enabled',1,null,null,null,null,'provision/isEnabled.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4548','104','GUID','GUID',1,null,null,null,null,'provision/guid.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4562','104','principal','PRIMARY_PRINICPAL',1,null,null,null,null,'provision/primary_principal.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4563','104','password','PRIMARY_PASSWORD',1,null,null,null,null,'provision/primary_pswd.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4564','104','emailAddress','PRIMARY_EMAIL',1,null,null,null,null,'provision/primary_email.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4565','104','domain','PRIMARY_DOMAIN',1,null,null,null,null,'provision/primaryDomain.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4600','104','userName','Google User Name',1,null,null,null,null,'provision/gapps/gappsUid.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4601','104','firstName','Google Fist Name',1,null,null,null,null,'provision/gapps/gappsFirstName.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4602','104','lastName','Google Last Name',1,null,null,null,null,'provision/gapps/gappsLastName.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4603','104','password','Google Password',1,null,null,null,null,'provision/gapps/gappsPassword.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4630','104','ad-sAMAccountName','sAMAccountName',1,null,null,null,null,'provision/ad/sAMAccountName.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4631','104','ad-givenName','givenName',1,null,null,null,null,'provision/gn.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4632','104','ad-CN','CN',1,null,null,null,null,'provision/ad/adCN.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4633','104','ad-ou','ou',1,null,null,null,null,'provision/ad/ou.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4634','104','ad-objectclass','objectclass',1,null,null,null,null,'provision/ad/objectclass.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4635','104','ad-profilePath','profilePath',1,null,null,null,null,'provision/ad/profilePath.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4636','104','ad-homeDirectory','homeDirectory',1,null,null,null,null,'provision/ad/homeDirectory.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4637','104','ad-homeDrive','homeDrive',1,null,null,null,null,'provision/ad/homeDrive.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4639','104','ad-scriptPath','scriptPath',1,null,null,null,null,'provision/ad/scriptPath.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4640','104','ad-company','company',1,null,null,null,null,'provision/ad/company.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4642','104','ad-initials','initials',1,null,null,null,null,'provision/ad/initials.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4643','104','ad-department','department',1,null,null,null,null,'provision/ad/department.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4644','104','ad-telephoneNumber','telephoneNumber',1,null,null,null,null,'provision/ad/telephoneNumber.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4645','104','ad-title','title',1,null,null,null,null,'provision/ad/title.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4646','104','ad-unicodePwd','unicodePwd',1,null,null,null,null,'provision/ad/unicodePwd.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4650','104','ad-userAccountControl','userAccountControl',1,null,null,null,null,'provision/ad/userAccountControl.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4651','104','ad-userPrincipalName','userPrincipalName',1,null,null,null,null,'provision/ad/userPrincipalName.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4652','104','ad-userGroup','Group Membership',1,null,null,null,null,'provision/ad/userGroup.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4653','104','ad-streetAddress','Street Address',1,null,null,null,null,'provision/ad/street.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4654','104','ad-city','City',1,null,null,null,null,'provision/ad/city.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4655','104','ad-state','State',1,null,null,null,null,'provision/ad/state.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4656','104','ad-zip','Zip',1,null,null,null,null,'provision/ad/zip.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4657','104','sf-profile-grp','Sales Force Profile Group',1,null,null,null,null,'salesforce/salesforce-profile-group.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4660','104','userCreateDate','userCreateDate',1,to_date('28-MAR-14','DD-MON-RR'),'3000',null,null,'provision/userCreateDate.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4661','104','userCreateDate','userCreateDate',1,to_date('28-MAR-14','DD-MON-RR'),'3000',null,null,'provision/ext_phone.groovy',1);
Insert into IAMUSER.POLICY (POLICY_ID,POLICY_DEF_ID,NAME,DESCRIPTION,STATUS,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,RULE_SRC_URL,ENABLEMENT) values ('4662','104','userCreateDate','userCreateDate',1,to_date('28-MAR-14','DD-MON-RR'),'3000',null,null,'provision/nmb_phone.groovy',1);
REM INSERTING into IAMUSER.POLICY_ATTRIBUTE
SET DEFINE OFF;
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4000','116','4000','NON_ALPHA_CHARS','RANGE',null,null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4004','117','4000','ALPHA_CHARS','RANGE',null,null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4007','114','4000','UPPERCASE_CHARS','RANGE',null,null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4008','113','4000','NUMERIC_CHARS','RANGE','1',null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4010','122','4000','PWD_NAME','boolean',null,null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4011','112','4000','PWD_LEN','RANGE','8','12','Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4013','110','4000','PWD_HIST_VER',null,'6',null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4015','132','4000','QUEST_LIST',null,null,null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4016','130','4000','QUEST_COUNT',null,'3',null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4017','121','4000','PWD_LOGIN','boolean',null,null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4019','115','4000','LOWERCASE_CHARS','RANGE',null,null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4020','129','4000','PWD_EXP_WARN',null,'2',null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4023','111','4000','PWD_EXPIRATION',null,'90',null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4050','133','4000','PWD_EXP_GRACE',null,'1',null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4051','134','4000','CHNG_PSWD_ON_RESET',null,'1',null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4055','142','4000','PASSWORD_CHANGE_ALLOWED',null,'1',null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4056','170','4000','REJECT_CHARS_IN_PSWD',null,'<>',null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4057','171','4000','QUEST_ANSWER_CORRECT',null,'3',null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4101','151','4001','AUTO_UNLOCK_TIME',null,'30',null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4102','152','4001','TOKEN_TYPE',null,'OPENIAM_TOKEN',null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4103','153','4001','LOGIN_MODULE_SEL_POLCY',null,null,null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4104','154','4001','SUCCESS_URL',null,null,null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4105','155','4001','FAIL_URL',null,null,null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4107','157','4001','FAILED_AUTH_COUNT',null,'3',null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4108','158','4001','DEFAULT_LOGIN_MOD',null,'defaultLoginModule',null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4109','159','4001','TOKEN_LIFE',null,'30',null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4110','162','4001','TOKEN_ISSUER',null,'openiam',null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4111','163','4001','LOGIN_MOD_TYPE',null,'1',null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4112','164','4001','AUTH_REPOSITORY',null,null,null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4052','180','4000','MIN_WORDS_PASSPHRASE',null,null,null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4053','181','4000','REPEAT_SAME_WORD_PASSPHRASE',null,null,null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4054','182','4000','LIMIT_NUM_REPEAT_CHAR',null,null,null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('4200','183','4000','PWD_PASSWORD',null,null,null,'Y');
Insert into IAMUSER.POLICY_ATTRIBUTE (POLICY_ATTR_ID,DEF_PARAM_ID,POLICY_ID,NAME,OPERATION,VALUE1,VALUE2,REQUIRED) values ('NUM_DAYS_FRGT_PWD_TOKEN_VALID','NUM_DAYS_FRGT_PWD_TOKEN_VALID','4000','NUM_DAYS_FORGET_PWD_TOKEN_VALID',null,null,null,'Y');
REM INSERTING into IAMUSER.POLICY_DEF
SET DEFINE OFF;
Insert into IAMUSER.POLICY_DEF (POLICY_DEF_ID,NAME,DESCRIPTION,POLICY_TYPE,LOCATION_TYPE,POLICY_RULE,POLICY_HANDLER,POLICY_ADVISE_HANDLER) values ('100','PASSWORD POLICY','Out of the box Password Policy','2','DB',null,null,null);
Insert into IAMUSER.POLICY_DEF (POLICY_DEF_ID,NAME,DESCRIPTION,POLICY_TYPE,LOCATION_TYPE,POLICY_RULE,POLICY_HANDLER,POLICY_ADVISE_HANDLER) values ('103','AUTHENTICATION POLICY','Out of the box Authentication Policy','4','DB',null,null,null);
Insert into IAMUSER.POLICY_DEF (POLICY_DEF_ID,NAME,DESCRIPTION,POLICY_TYPE,LOCATION_TYPE,POLICY_RULE,POLICY_HANDLER,POLICY_ADVISE_HANDLER) values ('104','ATTRIBUTE POLICY','Attribute value policies.','5','DB',null,null,null);
REM INSERTING into IAMUSER.POLICY_DEF_PARAM
SET DEFINE OFF;
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('110','100','PWD_HIST_VER','Password history versions',null,null,null,0,'PSWD_COMPOSITION',null,'org.openiam.idm.srvc.pswd.rule.PasswordHistoryRule');
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('111','100','PWD_EXPIRATION','Password expiration',null,null,null,0,'PSWD_CHANGE_RULE',null,null);
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('112','100','PWD_LEN','Password length','RANGE',null,null,0,'PSWD_COMPOSITION',null,'org.openiam.idm.srvc.pswd.rule.PasswordLengthRule');
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('113','100','NUMERIC_CHARS','Numeric characters(Min-Max)','RANGE',null,null,0,'PSWD_COMPOSITION',null,'org.openiam.idm.srvc.pswd.rule.NumericCharRule');
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('114','100','UPPERCASE_CHARS','Uppercase characters(Min-Max)','RANGE',null,null,0,'PSWD_COMPOSITION',null,'org.openiam.idm.srvc.pswd.rule.UpperCaseRule');
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('115','100','LOWERCASE_CHARS','Lowercase characters(Min-Max)','RANGE',null,null,0,'PSWD_COMPOSITION',null,'org.openiam.idm.srvc.pswd.rule.LowerCaseRule');
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('116','100','NON_ALPHA_CHARS','Non-alpha numeric symbols(Min-Max)','RANGE',null,null,0,'PSWD_COMPOSITION',null,'org.openiam.idm.srvc.pswd.rule.NonAlphaNumericRule');
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('117','100','ALPHA_CHARS','Alpha character(Min-Max)','RANGE',null,null,0,'PSWD_COMPOSITION',null,'org.openiam.idm.srvc.pswd.rule.AlphaCharRule');
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('182','100','LIMIT_NUM_REPEAT_CHAR','Limit the repetition of same character',null,null,null,null,'PSWD_COMPOSITION',null,'org.openiam.idm.srvc.pswd.rule.LimitNumberRepeatCharRule');
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('121','100','PWD_LOGIN','Reject password = Login Id','boolean',null,null,0,'PSWD_COMPOSITION',null,'org.openiam.idm.srvc.pswd.rule.PasswordNotPrincipalRule');
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('122','100','PWD_NAME','Reject password = First / Last name','boolean',null,null,0,'PSWD_COMPOSITION',null,'org.openiam.idm.srvc.pswd.rule.PasswordNotNameRule');
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('183','100','PWD_PASSWORD','Cannot repeat same Password',null,null,null,null,'PSWD_COMPOSITION',null,'org.openiam.idm.srvc.pswd.rule.PasswordNotPasswordRule');
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('NUM_DAYS_FRGT_PWD_TOKEN_VALID','100','NUM_DAYS_FORGET_PWD_TOKEN_VALID','Number of days the forgot password token is valid',null,null,null,null,'FORGET_PSWD',null,null);
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('129','100','PWD_EXP_WARN','Days to password expiration warning',null,null,null,0,'PSWD_CHANGE_RULE',null,null);
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('130','100','QUEST_COUNT','Number of questions to display',null,null,null,0,'FORGET_PSWD',null,null);
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('132','100','QUEST_LIST','Question list',null,null,null,0,'FORGET_PSWD',null,null);
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('133','100','PWD_EXP_GRACE','Password expiration grace period',null,null,null,0,'PSWD_CHANGE_RULE',null,null);
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('134','100','CHNG_PSWD_ON_RESET','Change Password after reset',null,null,null,0,'PSWD_CHANGE_RULE',null,null);
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('138','100','PWD_EQ_PWD','RejectPassword Equals password',null,null,null,0,'PSWD_COMPOSITION',null,'org.openiam.idm.srvc.pswd.rule.PasswordNotPasswordRule');
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('139','100','RESET_BY_USER','Reject reset by user',null,null,null,0,'PSWD_CHANGE_RULE',null,'org.openiam.idm.srvc.pswd.rule.ChangePasswordByUserRule');
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('142','100','PASSWORD_CHANGE_ALLOWED','Determines how many times you are allowed to change your password',null,null,null,0,'PSWD_CHANGE_RULE',null,'org.openiam.idm.srvc.pswd.rule.PasswordChangeAllowedRule');
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('151','103','AUTO_UNLOCK_TIME','UnLock account in n minutes',null,null,null,0,null,null,null);
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('152','103','TOKEN_TYPE','SSO Token Type',null,null,null,0,null,null,null);
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('153','103','LOGIN_MODULE_SEL_POLCY','Policy to select the login module',null,null,null,0,null,null,null);
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('154','103','SUCCESS_URL','URL to forward on authn success',null,null,null,0,null,null,null);
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('155','103','FAIL_URL','URL to forward on authn fail',null,null,null,0,null,null,null);
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('157','103','FAILED_AUTH_COUNT','Failed Authentication Attempts',null,null,null,0,null,null,null);
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('158','103','DEFAULT_LOGIN_MOD','Default Login Module',null,null,null,0,null,null,null);
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('159','103','TOKEN_LIFE','SSO Token Type Life',null,null,null,0,null,null,null);
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('162','103','TOKEN_ISSUER','Issuer of the SSO Token',null,null,null,0,null,null,null);
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('163','103','LOGIN_MOD_TYPE','Type of authentication module',null,null,null,0,null,null,null);
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('164','103','AUTH_REPOSITORY','Authentication Repository',null,null,null,0,null,null,null);
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('170','100','REJECT_CHARS_IN_PSWD','Characters not allowed in a password',null,null,null,0,'PSWD_COMPOSITION',null,'org.openiam.idm.srvc.pswd.rule.RejectCharactersRule');
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('171','100','QUEST_ANSWER_CORRECT','Number of answers that are required to be correct',null,null,null,0,'FORGET_PSWD',null,null);
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('172','103','HOST_LOGIN','Login to the host',null,null,null,0,null,null,null);
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('173','103','HOST_PASSWORD','Password to the host',null,null,null,0,null,null,null);
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('174','103','BASEDN','Type of authentication module',null,null,null,0,null,null,null);
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('175','103','PROTOCOL','Protocol',null,null,null,0,null,null,null);
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('176','103','KEY_ATTRIBUTE','Name of the Primary Key Attribute',null,null,null,0,null,null,null);
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('178','103','MANAGED_SYS_ID','Managed system Id',null,null,null,0,null,null,null);
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('179','103','HOST_URL','URL of the host system',null,null,null,0,null,null,null);
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('180','100','MIN_WORDS_PASSPHRASE','Minimum number of words in the phrase',null,null,null,null,'PSWD_COMPOSITION',null,'org.openiam.idm.srvc.pswd.rule.PassphraseMinWordsRule');
Insert into IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID,POLICY_DEF_ID,NAME,DESCRIPTION,OPERATION,VALUE1,VALUE2,REPEATS,PARAM_GROUP,HANDLER_LANGUAGE,POLICY_PARAM_HANDLER) values ('181','100','REPEAT_SAME_WORD_PASSPHRASE','Repetition of the same word in the phrase',null,null,null,null,'PSWD_COMPOSITION',null,'org.openiam.idm.srvc.pswd.rule.PassphraseRepeatWordRule');
REM INSERTING into IAMUSER.POLICY_OBJECT_ASSOC
SET DEFINE OFF;
Insert into IAMUSER.POLICY_OBJECT_ASSOC (POLICY_OBJECT_ID,POLICY_ID,ASSOCIATION_LEVEL,ASSOCIATION_VALUE,OBJECT_TYPE,OBJECT_ID,PARENT_ASSOC_ID) values ('1100','4000','GLOBAL','GLOBAL',null,null,null);
REM INSERTING into IAMUSER.PRIVILEGE
SET DEFINE OFF;
REM INSERTING into IAMUSER.PROVISION_CONNECTOR
SET DEFINE OFF;
Insert into IAMUSER.PROVISION_CONNECTOR (CONNECTOR_ID,NAME,METADATA_TYPE_ID,STD_COMPLIANCE_LEVEL,CLIENT_COMM_PROTOCOL,SERVICE_URL,SERVICE_NAMESPACE,SERVICE_PORT,SERVICE_WSDL,CLASS_NAME,HOST,PORT,CONNECTOR_INTERFACE) values ('51','LDAP CONNECTOR','LDAP_Connector',null,null,'localhost:8080/openiam-idm-esb/idmsrvc/LDAPConnectorService','http://www.openiam.org/service/connector','LDAPConnectorServicePort',null,null,null,null,null);
Insert into IAMUSER.PROVISION_CONNECTOR (CONNECTOR_ID,NAME,METADATA_TYPE_ID,STD_COMPLIANCE_LEVEL,CLIENT_COMM_PROTOCOL,SERVICE_URL,SERVICE_NAMESPACE,SERVICE_PORT,SERVICE_WSDL,CLASS_NAME,HOST,PORT,CONNECTOR_INTERFACE) values ('54','Google Apps CONNECTOR','GOOGLE_Connector',null,null,'localhost:8080/openiam-idm-esb/idmsrvc/GoogleAppsConnectorService','http://www.openiam.org/service/connector','GoogleAppsConnectorServicePort',null,null,null,null,null);
Insert into IAMUSER.PROVISION_CONNECTOR (CONNECTOR_ID,NAME,METADATA_TYPE_ID,STD_COMPLIANCE_LEVEL,CLIENT_COMM_PROTOCOL,SERVICE_URL,SERVICE_NAMESPACE,SERVICE_PORT,SERVICE_WSDL,CLASS_NAME,HOST,PORT,CONNECTOR_INTERFACE) values ('62','Groovy Script CONNECTOR','SCRIPT_Connector',null,null,'localhost:8080/openiam-idm-esb/idmsrvc/ScriptConnectorService','http://www.openiam.org/service/connector','NA',null,null,null,null,null);
Insert into IAMUSER.PROVISION_CONNECTOR (CONNECTOR_ID,NAME,METADATA_TYPE_ID,STD_COMPLIANCE_LEVEL,CLIENT_COMM_PROTOCOL,SERVICE_URL,SERVICE_NAMESPACE,SERVICE_PORT,SERVICE_WSDL,CLASS_NAME,HOST,PORT,CONNECTOR_INTERFACE) values ('63','Linux CONNECTOR','LINUX_Connector',null,null,'localhost:8080/openiam-idm-esb/idmsrvc/LinuxConnectorService','http://www.openiam.org/service/connector','NA',null,null,null,null,null);
Insert into IAMUSER.PROVISION_CONNECTOR (CONNECTOR_ID,NAME,METADATA_TYPE_ID,STD_COMPLIANCE_LEVEL,CLIENT_COMM_PROTOCOL,SERVICE_URL,SERVICE_NAMESPACE,SERVICE_PORT,SERVICE_WSDL,CLASS_NAME,HOST,PORT,CONNECTOR_INTERFACE) values ('64','MySQL CONNECTOR','MySQL Connector',null,null,'localhost:8080/openiam-idm-esb/idmsrvc/MySQLConnector','http://www.openiam.org/service/connector','NA',null,null,null,null,null);
Insert into IAMUSER.PROVISION_CONNECTOR (CONNECTOR_ID,NAME,METADATA_TYPE_ID,STD_COMPLIANCE_LEVEL,CLIENT_COMM_PROTOCOL,SERVICE_URL,SERVICE_NAMESPACE,SERVICE_PORT,SERVICE_WSDL,CLASS_NAME,HOST,PORT,CONNECTOR_INTERFACE) values ('65','Oracle CONNECTOR','DB_Connector',null,null,'localhost:8080/openiam-idm-esb/idmsrvc/OracleConnector','http://www.openiam.org/service/connector','NA',null,null,null,null,null);
Insert into IAMUSER.PROVISION_CONNECTOR (CONNECTOR_ID,NAME,METADATA_TYPE_ID,STD_COMPLIANCE_LEVEL,CLIENT_COMM_PROTOCOL,SERVICE_URL,SERVICE_NAMESPACE,SERVICE_PORT,SERVICE_WSDL,CLASS_NAME,HOST,PORT,CONNECTOR_INTERFACE) values ('61','AppTableConnector',null,null,'CLEAR','localhost:9080/openiam-esb/idmsrvc/ApplicationTablesConnector','http://www.openiam.org/service/connector','NA',null,null,null,null,'LOCAL');
REM INSERTING into IAMUSER.PROV_REQUEST
SET DEFINE OFF;
REM INSERTING into IAMUSER.PWD_HISTORY
SET DEFINE OFF;
REM INSERTING into IAMUSER.RECONCILIATION_CONFIG
SET DEFINE OFF;
Insert into IAMUSER.RECONCILIATION_CONFIG (RECON_CONFIG_ID,RESOURCE_ID,FREQUENCY,STATUS,CSV_LINE_SEPARATOR,CSV_END_OF_LINE,NOTIFICATION_EMAIL_ADDRESS,TARGET_SYS_MATCH_SCRIPT,CUSTOM_IDENTITY_MATCH_SCRIPT,MATCH_FIELD_NAME,CUSTOM_MATCH_ATTR,CUSTOM_MATCH_SRC_ATTR,MANAGED_SYS_ID,SEARCH_FILTER,UPDATED_SINCE,TARGET_SYS_SEARCH_FILTER,MATCH_SCRIPT,EXEC_STATUS,LAST_EXEC_TIME) values ('4028b881373b90e501373b92ec180001','101','NIGHTLY','ACTIVE','comma','enter',null,null,null,null,null,null,null,null,null,null,null,null,null);
REM INSERTING into IAMUSER.RECONCILIATION_SITUATION
SET DEFINE OFF;
Insert into IAMUSER.RECONCILIATION_SITUATION (RECON_SITUATION_ID,RECON_CONFIG_ID,SITUATION,SITUATION_RESP,SCRIPT) values ('4028b881373b90e501373b92ec1f0002','4028b881373b90e501373b92ec180001','Match Found','UPD_IDM_USER','/recon/LDAPPopulationScript.groovy');
Insert into IAMUSER.RECONCILIATION_SITUATION (RECON_SITUATION_ID,RECON_CONFIG_ID,SITUATION,SITUATION_RESP,SCRIPT) values ('4028b881373b90e501373b92ec1f0003','4028b881373b90e501373b92ec180001','Login Not Found','CREATE_RES_ACCOUNT',null);
Insert into IAMUSER.RECONCILIATION_SITUATION (RECON_SITUATION_ID,RECON_CONFIG_ID,SITUATION,SITUATION_RESP,SCRIPT) values ('4028b881373b90e501373b92ec1f0004','4028b881373b90e501373b92ec180001','IDM Delete','DEL_RES_ACCOUNT',null);
Insert into IAMUSER.RECONCILIATION_SITUATION (RECON_SITUATION_ID,RECON_CONFIG_ID,SITUATION,SITUATION_RESP,SCRIPT) values ('4028b881373b90e501373b92ec1f0005','4028b881373b90e501373b92ec180001','Resource Delete','CREATE_RES_ACCOUNT',null);
Insert into IAMUSER.RECONCILIATION_SITUATION (RECON_SITUATION_ID,RECON_CONFIG_ID,SITUATION,SITUATION_RESP,SCRIPT) values ('4028b881373b90e501373b92ec200006','4028b881373b90e501373b92ec180001','IDM Not Found','CREATE_IDM_ACCOUNT','/recon/LDAPPopulationScript.groovy');
REM INSERTING into IAMUSER.RECON_RES_ATTR_MAP
SET DEFINE OFF;
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4501',null,'1');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4502',null,'2');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4503',null,'3');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4504',null,'4');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4505',null,'5');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4506',null,'6');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4507',null,'7');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4508',null,'8');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4509',null,'9');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4510',null,'10');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4511',null,'11');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4512',null,'12');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4513',null,'13');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4515',null,'14');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4516',null,'15');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4517',null,'16');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4518',null,'17');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4521',null,'18');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4522',null,'19');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4514',null,'20');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4546',null,'21');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4562',null,'22');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4563',null,'23');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4564',null,'24');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4565',null,'25');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4600',null,'26');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4601',null,'27');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4602',null,'28');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4603',null,'29');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4632',null,'30');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4630',null,'31');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4506',null,'32');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4631',null,'33');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4517',null,'34');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4633',null,'35');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4634',null,'36');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4635',null,'37');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4636',null,'38');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4637',null,'39');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4639',null,'40');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4640',null,'41');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4642',null,'42');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4643',null,'43');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4644',null,'44');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4645',null,'45');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4646',null,'46');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4650',null,'47');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4651',null,'48');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4653',null,'49');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4654',null,'50');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4655',null,'51');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4656',null,'52');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4600',null,'53');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4603',null,'54');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4562',null,'55');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4514',null,'56');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4506',null,'57');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4509',null,'58');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4511',null,'59');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4513',null,'60');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4516',null,'61');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4546',null,'62');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4562',null,'65');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4509',null,'66');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4601',null,'67');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4602',null,'68');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4543',null,'69');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4541',null,'70');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4660',null,'71');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4661',null,'72');
Insert into IAMUSER.RECON_RES_ATTR_MAP (ATTR_POLICY_ID,DEF_RECON_ATTR_MAP_ID,RECON_RES_ATTR_MAP_ID) values ('4662',null,'73');
REM INSERTING into IAMUSER.RELATIONSHIP
SET DEFINE OFF;
REM INSERTING into IAMUSER.RELATION_CATEGORY
SET DEFINE OFF;
REM INSERTING into IAMUSER.RELATION_SET
SET DEFINE OFF;
REM INSERTING into IAMUSER.RELATION_SOURCE
SET DEFINE OFF;
REM INSERTING into IAMUSER.RELATION_TYPE
SET DEFINE OFF;
REM INSERTING into IAMUSER.REPORT_CRITERIA_PARAMETER
SET DEFINE OFF;
REM INSERTING into IAMUSER.REPORT_INFO
SET DEFINE OFF;
REM INSERTING into IAMUSER.REPORT_PARAMETER_TYPE
SET DEFINE OFF;
Insert into IAMUSER.REPORT_PARAMETER_TYPE (RCPT_ID,TYPE_NAME,TYPE_DESCRIPTION) values ('1','STRING','String');
Insert into IAMUSER.REPORT_PARAMETER_TYPE (RCPT_ID,TYPE_NAME,TYPE_DESCRIPTION) values ('2','DATE','Date');
Insert into IAMUSER.REPORT_PARAMETER_TYPE (RCPT_ID,TYPE_NAME,TYPE_DESCRIPTION) values ('3','INTEGER','Integer');
REM INSERTING into IAMUSER.REPORT_SUBSCRIPTIONS
SET DEFINE OFF;
REM INSERTING into IAMUSER.REPORT_SUB_CRITERIA_PARAM
SET DEFINE OFF;
REM INSERTING into IAMUSER.REQUEST_ATTACHMENT
SET DEFINE OFF;
REM INSERTING into IAMUSER.REQUEST_ATTRIBUTE
SET DEFINE OFF;
REM INSERTING into IAMUSER.REQUEST_USER
SET DEFINE OFF;
REM INSERTING into IAMUSER.REQ_APPROVER
SET DEFINE OFF;
REM INSERTING into IAMUSER.RES
SET DEFINE OFF;
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SELF_SUB_ORGS_EDIT','MENU_ITEM','add organization','SELF_SUB_ORGS_EDIT',null,null,1,'/selfservice/editOrganization.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SELF_SUB_ORGS_MEMEBERSHIP','MENU_ITEM','add organization','SELF_SUB_ORGS_MEMEBERSHIP',null,null,1,'/selfservice/membership-organizations.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SYNCLOG','MENU_ITEM','Log Viewer','SYNCLOG',null,null,1,'/webconsole/viewAuditLog.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('113','MANAGED_SYS',null,'AppTableMSys',null,null,null,null,null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('0','MANAGED_SYS',null,'OPENIAM',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('101','MANAGED_SYS',null,'LDAP',null,null,2,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('103','MANAGED_SYS',null,'Google App Con',null,null,3,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('104','MANAGED_SYS',null,'Groovy Script Con',null,null,4,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('105','MANAGED_SYS',null,'Linux Con',null,null,5,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('106','MANAGED_SYS',null,'MySQL Con',null,null,6,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('110','MANAGED_SYS',null,'ACTIVE DIRECTORY',null,null,3,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('111','MANAGED_SYS',null,'Oracle Connector',null,null,4,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('ACCESSCENTER','MENU_ITEM','Access Management Center','ACCESSCENTER',null,null,1,null,null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('ACC_CONTROL','MENU_ITEM','Access Control','ACC_CONTROL',null,null,2,'javascript:void(0);',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('ADMIN','MENU_ITEM','Administration','ADMIN',null,null,6,'javascript:void(0);',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('AM_PROV_AM_ATTR','MENU_ITEM','Create Authentication Provider','AM_PROV_AM_ATTR',null,null,1,'/webconsole-am/authProviderAMAttributes.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('AM_PROV_EDIT','MENU_ITEM','Authentication Provider Edit Menu','AM_PROV_EDIT',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('AM_PROV_EDIT_CURRENT','MENU_ITEM','Back to Search','AM_PROV_EDIT_CURRENT',null,null,1,'/webconsole-am/editAuthProvider.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('AM_PROV_EDIT_SEARCH','MENU_ITEM','Back to Search','AM_PROV_EDIT_SEARCH',null,null,1,'/webconsole-am/authenticationProviders.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('AM_PROV_NEW','MENU_ITEM','Create Authentication Provider','AM_PROV_NEW',null,null,1,'/webconsole-am/newAuthProvider.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('AM_PROV_NEW_EDIT','MENU_ITEM','Create Authentication Provider','AM_PROV_NEW_EDIT',null,null,1,'/webconsole-am/newAuthProvider.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('AM_PROV_ROOT','MENU_ITEM','Authentication Provider Root Menu','AM_PROV_ROOT',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('AM_PROV_SEARCH','MENU_ITEM','Search Authentication Providers','AM_PROV_SEARCH',null,null,1,'/webconsole-am/authenticationProviders.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('AM_PROV_SEARCH_CHILD','MENU_ITEM','Authentication Provider Search','AM_PROV_SEARCH_CHILD',null,null,1,'/webconsole-am/authenticationProviders.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('AUDITREPORT','MENU_ITEM','Audit Information Reports','AUDITREPORT',null,null,2,null,null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('AUDIT_RPT','MENU_ITEM','Audit Report','AUDIT_RPT',null,null,1,'auditReport.cnt',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('BACK_TO_CP','MENU_ITEM','Back To Content Provider','BACK_TO_CP',null,null,1,'/webconsole-am/editContentProvider.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('BATCH_PROC','MENU_ITEM','Batch Processes','BATCH_PROC',null,null,4,'batchList.cnt',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('CHALLENGE','MENU_ITEM','Challenge Quest','CHALLENGE',null,null,3,'challengeQuestList.cnt',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('CHNGPSWD','MENU_ITEM','Change Password','CHNGPSWD',null,null,3,'/idp/changePassword.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('CONTENT_PROV_EDIT','MENU_ITEM','Content Provider Edit Menu','CONTENT_PROV_EDIT',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('CONTENT_PROV_EDIT_CURRENT','MENU_ITEM','Edit Current Provider','CONTENT_PROV_EDIT_CURRENT',null,null,1,'/webconsole-am/editContentProvider.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('CONTENT_PROV_EDIT_SEARCH','MENU_ITEM','Back to Search','CONTENT_PROV_EDIT_SEARCH',null,null,1,'/webconsole-am/contentProviders.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('CONTENT_PROV_NEW','MENU_ITEM','Create Content Provider','CONTENT_PROV_NEW',null,null,1,'/webconsole-am/newContentProvider.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('CONTENT_PROV_NEW_EDIT','MENU_ITEM','Create Content Provider','CONTENT_PROV_NEW_EDIT',null,null,1,'/webconsole-am/newContentProvider.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('CONTENT_PROV_ROOT','MENU_ITEM','Content Provider Root Menu','CONTENT_PROV_ROOT',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('CONTENT_PROV_SEARCH','MENU_ITEM','Search Content Providers','CONTENT_PROV_SEARCH',null,null,1,'/webconsole-am/contentProviders.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('CONTENT_PROV_SEARCH_CHILD','MENU_ITEM','Content Provider Search','CONTENT_PROV_SEARCH_CHILD',null,null,1,'/webconsole-am/contentProviders.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('CREATEREQ','MENU_ITEM','Change Access','CREATEREQ',null,null,4,'/selfservice/users.html',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('CREATE_RESOURCE_MENU','MENU_ITEM','Resource for Menu Creation','CREATE_RESOURCE_MENU',null,null,1,'/webconsole/editResource.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('CUSTOM_FIELD_EDIT','MENU_ITEM','Custom Field Edit Menu','CUSTOM_FIELD_EDIT',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('CUSTOM_FIELD_EDIT_NEW','MENU_ITEM','Create New Custom Field','CUSTOM_FIELD_EDIT_NEW',null,null,1,'/webconsole/newCustomField.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('CUSTOM_FIELD_EDIT_SEARCH','MENU_ITEM','Back to Search','CUSTOM_FIELD_EDIT_SEARCH',null,null,1,'/webconsole/customFields.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('CUSTOM_FIELD_NEW','MENU_ITEM','Create New Custom Field','CUSTOM_FIELD_NEW',null,null,1,'/webconsole/newCustomField.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('CUSTOM_FIELD_ROOT','MENU_ITEM','Custom Field Root Menu','CUSTOM_FIELD_ROOT',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('CUSTOM_FIELD_SEARCH','MENU_ITEM','Search Custom Fields','CUSTOM_FIELD_SEARCH',null,null,1,'/webconsole/customFields.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('CUSTOM_FIELD_SEARCH_CHILD','MENU_ITEM','Custom Field Search','CUSTOM_FIELD_SEARCH_CHILD',null,null,1,'/webconsole/customFields.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('DELG_FILTER','MENU_ITEM','Delegation Filter','DELG_FILTER',null,null,8,'userDelegationFilter.cnt',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('DIRECTORY','MENU_ITEM','Directory Lookup','DIRECTORY',null,null,1,'pub/directory.do?method=view',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('EDIT_GROUP','MENU_ITEM','Edit Group','EDIT_GROUP',null,null,1,'/webconsole/editGroup.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('EDIT_ORG','MENU_ITEM','Edit Organization','EDIT_ORG',null,null,1,'/webconsole/editOrganization.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('EDIT_RESOURCE','MENU_ITEM','Edit Resource','EDIT_RESOURCE',null,null,1,'/webconsole/editResource.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('EDIT_ROLE','MENU_ITEM','Role Menus','EDIT_ROLE',null,null,1,'/webconsole/editRole.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('GROUP_EDIT_MENU','MENU_ITEM','Group Edit Menus','GROUP_EDIT_MENU',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('GROUP_ENTITLEMENTS','MENU_ITEM','Group Entitlements','GROUP_ENTITLEMENTS',null,null,1,'/webconsole/groupEntitlements.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('GROUP_MENUS','MENU_ITEM','Group Menus','GROUP_MENUS',null,null,1,'/webconsole/groupMenuTree.html',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('GROUP_ROOT_MENU','MENU_ITEM','Group Menus','GROUP_ROOT_MENU',null,null,1,'/webconsole/groups.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('GROUP_SEARCH','MENU_ITEM','Group Search','GROUP_SEARCH',null,null,1,'/webconsole/groups.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('IDM','MENU_ITEM','IDENTITY MANAGER','IDM',null,null,1,'javascript:void(0);',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('IDMAN','MENU_ITEM','User Admin','IDMAN',null,null,1,'/webconsole/users.html',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('IDQUEST','MENU_ITEM','Challenge Response','IDQUEST',null,null,4,'/selfservice/challengeResponse.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('LOCATION','MENU_ITEM','Location','LOCATION',null,null,2,'locationList.cnt',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('MANAGEREQ','MENU_ITEM','Request History','MANAGEREQ',null,null,5,'/selfservice/taskHistory.html',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('METADATA','MENU_ITEM','Metadata','METADATA',null,null,5,'metadataTypeList.cnt',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('MNGSYS','MENU_ITEM','Managed Connections','MNGSYS',null,null,4,'/webconsole-idm/provisioning/mngsystemlist.html',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('MNGSYS_MENU_ITEM','MENU_ITEM','Managed Systems','MNGSYS_MENU_ITEM',null,null,1,'/webconsole-idm/provisioning/mngsystemlist.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('MNGSYS_NEW','MENU_ITEM','Create Managed System','MNGSYS_NEW',null,null,1,'/webconsole-idm/provisioning/mngsystem.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('NEWUSER','MENU_ITEM','New User','NEWUSER',null,null,6,'/selfservice/newUserWithApprover.html',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('NEWUSER-NOAPPRV','MENU_ITEM','New User-No Approver','NEWUSER-NOAPPRV',null,null,7,'/selfservice/newUser.html',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('NEW_GROUP','MENU_ITEM','Create New Group','NEW_GROUP',null,null,1,'/webconsole/editGroup.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('NEW_ORG','MENU_ITEM','New Organization','NEW_ORG',null,null,1,'/webconsole/editOrganization.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('NEW_ROLE','MENU_ITEM','New Role','NEW_ROLE',null,null,1,'/webconsole/editRole.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('NEW_USER','MENU_ITEM','Create New User','NEW_USER',null,null,1,'/webconsole/editUser.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('ORG','MENU_ITEM','Organization','ORG',null,null,2,'/webconsole/organizations.html',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('ORGANIZATION_EDIT_ID','MENU_ITEM','Organization Edit Menus','ORGANIZATION_EDIT_ID',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('ORGANIZATION_ROOT_ID','MENU_ITEM','Organization Root Menus','ORGANIZATION_ROOT_ID',null,null,1,'/webconsole/organizations.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('ORG_MEMBERSHIP','MENU_ITEM','Organization Membership','ORG_MEMBERSHIP',null,null,1,'/webconsole/organizationMembership.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('ORG_SEARCH','MENU_ITEM','Organization Search','ORG_SEARCH',null,null,1,'/webconsole/organizations.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('PAGE_TEMPLATE_EDIT','MENU_ITEM','Page Template Edit Menu','PAGE_TEMPLATE_EDIT',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('PAGE_TEMPLATE_EDIT_NEW','MENU_ITEM','Create New Template','PAGE_TEMPLATE_EDIT_NEW',null,null,1,'/webconsole/newPageTemplate.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('PAGE_TEMPLATE_EDIT_SEARCH','MENU_ITEM','Back to Search','PAGE_TEMPLATE_EDIT_SEARCH',null,null,1,'/webconsole/pageTemplates.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('PAGE_TEMPLATE_NEW','MENU_ITEM','Create Content Provider','PAGE_TEMPLATE_NEW',null,null,1,'/webconsole/newPageTemplate.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('PAGE_TEMPLATE_ROOT','MENU_ITEM','Page Template Root Menu','PAGE_TEMPLATE_ROOT',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('PAGE_TEMPLATE_SEARCH','MENU_ITEM','Search Page Templates','PAGE_TEMPLATE_SEARCH',null,null,1,'/webconsole/pageTemplates.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('PROFILE','MENU_ITEM','Edit Your Profile','PROFILE',null,null,6,'/selfservice/editProfile.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('PROVCONNECT','MENU_ITEM','Provisioning Connectors','PROVCONNECT',null,null,3,'/webconsole-idm/provisioning/connectorlist.html',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('PROVCONNECT_MENU_ITEM','MENU_ITEM','Provisioning Connectors','PROVCONNECT_MENU_ITEM',null,null,1,'/webconsole-idm/provisioning/connectorlist.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('PROVISIONING','MENU_ITEM','Provisioning','PROVISIONING',null,null,3,'prov/provIndex.do',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('PROV_CONNECTOR_NEW','MENU_ITEM','Create Provisioning Connector','PROV_CONNECTOR_NEW',null,null,1,'/webconsole-idm/provisioning/connector.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('REPORT','MENU_ITEM','Report','REPORT',null,null,5,'security/reportIndex.do',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('REQINBOX','MENU_ITEM','In-Box','REQINBOX',null,null,3,'/selfservice/myTasks.html',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('RESOURCE_EDIT_PAGE_ROOT','MENU_ITEM',null,'RESOURCE_EDIT_PAGE_ROOT',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('RESOURCE_MENU_ROOT','MENU_ITEM','Root for Resource Context Menu','RESOURCE_MENU_ROOT',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('RESPOLICYMAP','MENU_ITEM','Policy Map','RESPOLICYMAP',null,null,2,'/webconsole/policyMap.html',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('RESRECONCILE','MENU_ITEM','Reconciliation','RESRECONCILE',null,null,4,'/webconsole/reconciliationEdit.html',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('RESSUMMARY','MENU_ITEM','Resource Details','RESSUMMARY',null,null,1,'/webconsole/resources.html',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('RES_ENT_SUB','MENU_ITEM','Resouce Entitlements','RES_ENT_SUB',null,null,1,'/webconsole/resourceEntitlements.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('ROLE_EDIT_MENU','MENU_ITEM','Role Edit Menus','ROLE_EDIT_MENU',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('ROLE_ENTITLEMENTS','MENU_ITEM','Role Entitlements','ROLE_ENTITLEMENTS',null,null,1,'/webconsole/roleEntitlements.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('ROLE_MENUS','MENU_ITEM','Role Menus','ROLE_MENUS',null,null,1,'/webconsole/roleMenuTree.html',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('ROLE_RESMAP','MENU_ITEM','Resource Map','ROLE_RESMAP',null,null,2,'roleResource.cnt',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('ROLE_ROOT_MENU','MENU_ITEM','Role Root Menus','ROLE_ROOT_MENU',null,null,1,'/webconsole/roles.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('ROLE_SEARCH','MENU_ITEM','Role Search','ROLE_SEARCH',null,null,1,'/webconsole/roles.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('ROLE_SUMMARY','MENU_ITEM','Role Details','ROLE_SUMMARY',null,null,1,'roleDetail.cnt',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SECDOMAIN','MENU_ITEM','Security Domain','SECDOMAIN',null,null,1,'secDomainList.cnt',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SECURITY_GROUP','MENU_ITEM','Group','SECURITY_GROUP',null,null,1,'/webconsole/groups.html',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SECURITY_POLICY','MENU_ITEM','Policy','SECURITY_POLICY',null,null,4,'security/policy.do?method=init',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SECURITY_RES','MENU_ITEM','Resource','SECURITY_RES',null,null,3,'/webconsole/resources.html',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SECURITY_ROLE','MENU_ITEM','Role','SECURITY_ROLE',null,null,2,'/webconsole/roles.html',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SELFCENTER','MENU_ITEM','Self Service Center','SELFCENTER',null,null,2,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SELFSERVICE','MENU_ITEM','SELF SERVICE','SELFSERVICE',null,null,0,'javascript:void(0);',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SELFSERVICE_MYAPPS','MENU_ITEM','My Applications','SELFSERVICE_MYAPPS',null,null,1,'/selfservice/myApplications.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SELFSERVICE_MYINFO','MENU_ITEM','My Info','SELFSERVICE_MYINFO',null,null,1,'/selfservice/myInfo.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SELF_QUERYUSER','MENU_ITEM','Manage User','SELF_QUERYUSER',null,null,1,'/webconsole/users.html',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SYNCUSER','MENU_ITEM','Synchronization','SYNCUSER',null,null,3,'/webconsole-idm/provisioning/synchronizationlist.html',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('TEMPLATE_SEARCH_CHILD','MENU_ITEM','Page Template Search','TEMPLATE_SEARCH_CHILD',null,null,1,'/webconsole/pageTemplates.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('URI_META_BACK','MENU_ITEM','Back To Pattern','URI_META_BACK',null,null,1,'/webconsole-am/editProviderPattern.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('URI_META_EDIT','MENU_ITEM','URI Meta Root','URI_META_EDIT',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('URI_PATTERN_EDIT','MENU_ITEM','URI Pattern Edit Menu','URI_PATTERN_EDIT',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('URI_PATTERN_EDIT_NEW_PATTERN','MENU_ITEM','Create URI Pattern','URI_PATTERN_EDIT_NEW_PATTERN',null,null,1,'/webconsole-am/newProviderPattern.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('URI_PATTERN_EDIT_PATTERN','MENU_ITEM','Edit URI Pattern','URI_PATTERN_EDIT_PATTERN',null,null,1,'/webconsole-am/editProviderPattern.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('URI_PATTERN_EDT_PROV_SEARCH','MENU_ITEM','Context Provider Search','URI_PATTERN_EDT_PROV_SEARCH',null,null,1,'/webconsole-am/contentProviders.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('USER','MENU_ITEM','User','USER',null,null,1,'/webconsole/users.html',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('USER_ATTRIBUTES','MENU_ITEM','Edit User Attributes','USER_ATTRIBUTES',null,null,4,'/webconsole/userAttributes.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('USER_BULK','MENU_ITEM','User Bulk Ops','USER_BULK',null,null,3,'userBulk.cnt',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('USER_DELEGATION','MENU_ITEM','User Delegation Filter','USER_DELEGATION',null,null,9,'/webconsole/userDelegation.html',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('USER_EDIT_CONTACT_INFO','MENU_ITEM','Edit User Contact Info','USER_EDIT_CONTACT_INFO',null,null,3,'/webconsole/editUserContact.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('USER_EDIT_INFO','MENU_ITEM','Edit User Primary Info','USER_EDIT_INFO',null,null,2,'/webconsole/editUser.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('USER_EDIT_MENU','MENU_ITEM','User Edit Menus','USER_EDIT_MENU',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('USER_ENTITLEMENTS','MENU_ITEM','User Entitlements','USER_ENTITLEMENTS',null,null,7,'/webconsole/userEntitlements.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('USER_HISTORY','MENU_ITEM','User History','USER_HISTORY',null,null,8,'/webconsole/userHistory.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('USER_IDENTITY','MENU_ITEM','Edit User Identity Info','USER_IDENTITY',null,null,6,'/webconsole/editUserIdentity.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('USER_MENUS','MENU_ITEM','User Menus','USER_MENUS',null,null,1,'/webconsole/userMenuTree.html',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('USER_RESET_PASSWORD','MENU_ITEM','Reset Password','USER_RESET_PASSWORD',null,null,10,'/webconsole/resetPassword.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('USER_ROOT_MENU','MENU_ITEM','Root for User Context Menu','USER_ROOT_MENU',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('USER_SEARCH','MENU_ITEM','User Search','USER_SEARCH',null,null,1,'/webconsole/users.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SYNCUSER_MENU_ITEM','MENU_ITEM','Synchronization','SYNCUSER_MENU_ITEM',null,null,1,'/webconsole-idm/provisioning/synchronizationlist.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SYNCUSER_NEW','MENU_ITEM','Create Synchronization','SYNCUSER_NEW',null,null,1,'/webconsole-idm/provisioning/synchronization.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('MNGSYS_EDIT_MENU_ROOT','MENU_ITEM',null,'MNGSYS_EDIT_MENU_ROOT',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('MNGSYS_EDIT','MENU_ITEM','Edit managed system','MNGSYS_EDIT',null,null,1,'/webconsole-idm/provisioning/mngsystem.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('PASSWORD_POLICY','MENU_ITEM','Password policy','PASSWORD_POLICY',null,null,1,'/webconsole/passwordPolicy.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('PASSWORD_POLICY_ROOT_MENU','MENU_ITEM','Root for Password policy Context Menu','PASSWORD_POLICY_ROOT_MENU',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('NEW_PASSWORD_POLICY','MENU_ITEM','Create New Password Policy ','NEW_PASSWORD_POLICY',null,null,1,'/webconsole/editPolicy.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('AUTHENTICATION_POLICY','MENU_ITEM','Authentication policy','AUTHENTICATION_POLICY',null,null,2,'/webconsole/authenticationPolicy.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('AUTHENTICATION_POLICY_ROOT_MENU','MENU_ITEM','Root for Authentication policy Context Menu','AUTHENTICATION_POLICY_ROOT_MENU',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('NEW_AUTHENTICATION_POLICY','MENU_ITEM','Create New Authentication Policy ','NEW_AUTHENTICATION_POLICY',null,null,1,'/webconsole/editAuthenticationPolicy.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('ASSOC_POLICY','MENU_ITEM','Associate Policy','ASSOC_POLICY',null,null,1,'/webconsole/assocPasswordPolicy.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('PASSWORD_POLICY_EDIT','MENU_ITEM','Root for edit policy Context Menu','PASSWORD_POLICY_EDIT',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('UPLOAD_CUST_PSWD_POLICY','MENU_ITEM','Upload Custom Password Policy','UPLOAD_CUST_PSWD_POLICY',null,null,2,'/webconsole/uploadCustomPswdPolicy.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('ATTRIBUTE_POLICY','MENU_ITEM','Attribute policy','ATTRIBUTE_POLICY',null,null,2,'/webconsole/attributePolicy.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('POLICY_ATTR_ROOT','MENU_ITEM','Root for Attribute policy Context Menu','POLICY_ATTR_ROOT',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('NEW_POLICY_ATTR','MENU_ITEM','New Attribute policy','NEW_POLICY_ATTR',null,null,2,'/webconsole/editAttributePolicy.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('POLICY_ATTR_EDIT_ROOT','MENU_ITEM','Root for Edit Attribute policy Context Menu','POLICY_ATTR_EDIT_ROOT',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('EDIT_POLICY_ATTR_SRCH_BCK','MENU_ITEM','Back to Search','EDIT_POLICY_ATTR_SRCH_BCK',null,null,2,'/webconsole/attributePolicy.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('RES_APROVER_ASSOC','MENU_ITEM','Approver Associations','RES_APROVER_ASSOC',null,null,1,'/webconsole-idm/provisioning/resourceApproverAssociations.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('GRP_APROVER_ASSOC','MENU_ITEM','Approver Associations','GRP_APROVER_ASSOC',null,null,1,'/webconsole-idm/provisioning/groupApproverAssociations.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('ROLE_APROVER_ASSOC','MENU_ITEM','Approver Associations','ROLE_APROVER_ASSOC',null,null,1,'/webconsole-idm/provisioning/roleApproverAssociations.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SYNCLOG_MENU_ITEM','MENU_ITEM','LOG Viewer','SYNCLOG_MENU_ITEM',null,null,1,'/webconsole-idm/provisioning/syncloglist.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('MNGSYSRULES','MENU_ITEM','Managed system roles','MNGSYSRULES',null,null,3,'/webconsole-idm/provisioning/mngsystemrules.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SELFSERV_USER_EDIT_ROOT','MENU_ITEM','SelfService Edit User Root','SELFSERV_USER_EDIT_ROOT',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SELFSERV_USER_EDIT','MENU_ITEM','Edit User','SELFSERV_USER_EDIT',null,null,1,'/selfservice/editUser.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SELFSERV_USER_MEMSHP','MENU_ITEM','User Entitlements','SELFSERV_USER_MEMSHP',null,null,1,'/selfservice/userEntitlements.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('CHALLENGE_RESPONSE_QUESTION','MENU_ITEM','Challenge Response Question','CHALLENGE_RESPONSE_QUESTION',null,null,3,'/webconsole/challengeResponseQuestion.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('CHALLENGE_RESPONSE_QUESTION_ROOT','MENU_ITEM','Challenge Response Question Root Menu','CHALLENGE_RESPONSE_QUESTION_ROOT',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('CHALLENGE_RESPONSE_QUESTION_NEW','MENU_ITEM','New Question','CHALLENGE_RESPONSE_QUESTION_NEW',null,null,1,'/webconsole/editChallengeResponseQuestion.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SYNCUSER_EDIT','MENU_ITEM','Edit sync','SYNCUSER_EDIT',null,null,1,'/webconsole-idm/provisioning/synchronization.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SYNCUSER_POLICYMAP','MENU_ITEM','Policy map','SYNCUSER_POLICYMAP',null,null,1,'/webconsole/synchPolicyMap.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SYNCUSER_EDIT_MENU_ROOT','MENU_ITEM','Edit sync','SYNCUSER_EDIT_MENU_ROOT',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('MNGSYS_EDIT_NO_RES_MENU_ROOT','MENU_ITEM',null,'MNGSYS_EDIT_NO_RES_MENU_ROOT',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('WEB_REPORT','MENU_ITEM','Report','WEB_REPORT',null,null,6,'/webconsole/report.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('WEB_REPORT_ROOT','MENU_ITEM','Report Root Menu','WEB_REPORT_ROOT',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('WEB_REPORT_NEW','MENU_ITEM','New Report','WEB_REPORT_NEW',null,null,1,'/webconsole/editReport.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('CONF_POLICY_PAGE','MENU_ITEM','Configure IT Policy','IT_POLICY_PAGE',null,null,1,'/webconsole/configurePolicy.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('ORG_APROVER_ASSOC','MENU_ITEM','Approver Associations','ORG_APROVER_ASSOC',null,null,1,'/webconsole-idm/provisioning/organizationApproverAssociations.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('ORG_TYPE_SUBMENU_ROOT','MENU_ITEM','Organization Type Root','ORG_TYPE_SUBMENU_ROOT',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('ORG_TYPE_SUBMENU_EDIT','MENU_ITEM','Organization Type Edit','ORG_TYPE_SUBMENU_EDIT',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('ORG_TYPE_SEARCH','MENU_ITEM','Organization Type Search','ORG_TYPE_SEARCH',null,null,1,'/webconsole/organizationTypeSearch.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('NEW_ORG_TYPE','MENU_ITEM','Edit Organization Type','NEW_ORG_TYPE',null,null,1,'/webconsole/organizationTypeEdit.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('EDIT_ORG_TYPE','MENU_ITEM','Edit Organization Type','EDIT_ORG_TYPE',null,null,1,'/webconsole/organizationTypeEdit.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('EDIT_ORG_MEMBERSHIP','MENU_ITEM','Membership','EDIT_ORG_MEMBERSHIP',null,null,1,'/webconsole/organizationTypeMembership.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('ORG_TYPE_EDIT_SEARCH','MENU_ITEM','Organization Type Search','ORG_TYPE_EDIT_SEARCH',null,null,1,'/webconsole/organizationTypeSearch.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('EDIT_ORG_TYPE_IDMAN','MENU_ITEM','Membership','EDIT_ORG_TYPE_IDMAN',null,null,1,'/webconsole/organizationTypeSearch.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SUP_SUB_PAGE','MENU_ITEM','Configure IT Policy','SUP_SUB_PAGE',null,null,11,'/webconsole/supSub.html',null,null,null,null,'N','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('BATCH_TASK_ROOT','MENU_ITEM','Batch Task Root','BATCH_TASK_ROOT',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SEARCH_BATCH_NEW','MENU_ITEM','Search Batch Tasks','SEARCH_BATCH_NEW',null,null,1,'/webconsole-idm/provisioning/batchTaskSearch.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('NEW_BATCH_TASK','MENU_ITEM','New Batch Task','NEW_BATCH_TASK',null,null,1,'/webconsole-idm/provisioning/batchTask.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('BATCH_TASK_EDIT','MENU_ITEM','Batch Task Edit','BATCH_TASK_EDIT',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SEARCH_BATCH_EDIT','MENU_ITEM','Search Batch Tasks','SEARCH_BATCH_EDIT',null,null,1,'/webconsole-idm/provisioning/batchTaskSearch.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('EDIT_BATCH_TASK','MENU_ITEM','Edit Batch Task','EDIT_BATCH_TASK',null,null,1,'/webconsole-idm/provisioning/batchTask.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('ADMIN_BATCH_TASKS','MENU_ITEM','Batch Tasks','ADMIN_BATCH_TASKS',null,null,1,'/webconsole-idm/provisioning/batchTaskSearch.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('DIR_LOOKUP_PAGE','MENU_ITEM','Directory Lookup','DIR_LOOKUP_PAGE',null,null,5,'/selfservice/dirLookup.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('MANUALRECON','MENU_ITEM','Manual reconciliation','MANUALRECON',null,null,6,'/webconsole/manual-reconciliation.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SELFSERVICE_REPORT','MENU_ITEM','Report','SELFSERVICE_REPORT',null,null,5,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SUBSCRIBE','MENU_ITEM','Subscribe','SUBSCRIBE',null,null,1,'/selfservice/subscribe.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('VIEW','MENU_ITEM','View','VIEW',null,null,2,'/selfservice/view.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SELF_SUB_REPORT_ROOT','MENU_ITEM','Subscribe Report Root Menu','SELF_SUB_REPORT_ROOT',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SELF_SUB_REPORT_NEW','MENU_ITEM','New Subscribe Report','SELF_SUB_REPORT_NEW',null,null,1,'/selfservice/editSubscribeReport.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SELF_VIEW_REPORT_ROOT','MENU_ITEM','View Report Root Menu','SELF_VIEW_REPORT_ROOT',null,null,1,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SELF_VIEW_REPORT_NEW','MENU_ITEM','New Subscribe Report','SELF_VIEW_REPORT_NEW',null,null,1,'/selfservice/editViewReport.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SELFSERVE_SUP_SUB','MENU_ITEM','Superiors and Subordinates','SELFSERVE_SUP_SUB',null,null,1,'/selfservice/supSub.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SELFSER_USER_LOGIN','MENU_ITEM','Identities','SELFSER_USER_LOGIN',null,null,1,'/selfservice/userIdenties.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SELFSER_USER_NEW_LOGIN','MENU_ITEM','Create New Identity','SELFSER_USER_NEW_LOGIN',null,null,1,'/selfservice/identity.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SELFSRVS_ACCESS_MNGMNT_ORG','MENU_ITEM','Selfservice organization','SELFSRVS_ACCESS_MNGMNT_ORG',null,null,1,'/selfservice/organizations.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SELF_SUB_ORGS_ROOT','MENU_ITEM','organizations menu','SELF_SUB_ORGS_ROOT',null,null,2,null,null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SELF_SUB_ORGS_NEW','MENU_ITEM','add organization','SELF_SUB_ORGS_NEW',null,null,1,'/selfservice/editOrganization.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SELF_SUB_ORGS_SEARCH','MENU_ITEM','add organization','SELF_SUB_ORGS_SEARCH',null,null,1,'/selfservice/organizations.html',null,null,null,null,'Y','N');
Insert into IAMUSER.RES (RESOURCE_ID,RESOURCE_TYPE_ID,DESCRIPTION,NAME,BRANCH_ID,CATEGORY_ID,DISPLAY_ORDER,URL,RES_OWNER_GROUP_ID,RES_OWNER_USER_ID,MIN_AUTH_LEVEL,DOMAIN,IS_PUBLIC,IS_SSL) values ('SELF_SUB_ORGS_EDIT_ROOT','MENU_ITEM','organizations menu','SELF_SUB_ORGS_EDIT_ROOT',null,null,2,null,null,null,null,null,'Y','N');
REM INSERTING into IAMUSER.RESOURCE_GROUP
SET DEFINE OFF;
REM INSERTING into IAMUSER.RESOURCE_POLICY
SET DEFINE OFF;
REM INSERTING into IAMUSER.RESOURCE_PRIVILEGE
SET DEFINE OFF;
REM INSERTING into IAMUSER.RESOURCE_PROP
SET DEFINE OFF;
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CHALLENGE_RESP_QUESTION_NEW_PUB','CHALLENGE_RESPONSE_QUESTION_NEW',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CHALLENGE_RESP_QUESTION_NEW_DSC','CHALLENGE_RESPONSE_QUESTION_NEW',null,'MENU_DISPLAY_NAME','Create New Question',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SYNCUSER_EDIT_PUB','SYNCUSER_EDIT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SYNCUSER_EDIT_DESC','SYNCUSER_EDIT',null,'MENU_DISPLAY_NAME','Edit Synchronization',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SYNCUSER_POLICYMAP_PUB','SYNCUSER_POLICYMAP',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SYNCUSER_POLICYMAP_DESC','SYNCUSER_POLICYMAP',null,'MENU_DISPLAY_NAME','Policy Map',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('WEB_REPORT_ROOT_PUB','WEB_REPORT_ROOT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('WEB_REPORT_ROOT_DESC','WEB_REPORT_ROOT',null,'MENU_DISPLAY_NAME','Report Root Menu',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('WEB_REPORT_NEW_PUB','WEB_REPORT_NEW',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('WEB_REPORT_NEW_DSC','WEB_REPORT_NEW',null,'MENU_DISPLAY_NAME','Create New Report',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CONF_POLICY_PAGE_PUB','CONF_POLICY_PAGE',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CONF_POLICY_PAGE_DIS','CONF_POLICY_PAGE',null,'MENU_DISPLAY_NAME','Configure IT Policy',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ORG_APROVER_ASSOC_DN','ORG_APROVER_ASSOC',null,'MENU_DISPLAY_NAME','Approver Associations',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ORG_APROVER_ASSOC_PUB','ORG_APROVER_ASSOC',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ORG_TYPE_SUBMENU_ROOT_PUB','ORG_TYPE_SUBMENU_ROOT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ORG_TYPE_SUBMENU_ROOT_DESC','ORG_TYPE_SUBMENU_ROOT',null,'MENU_DISPLAY_NAME','Organization Type Root',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ORG_TYPE_SUBMENU_EDIT_PUB','ORG_TYPE_SUBMENU_EDIT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ORG_TYPE_SUBMENU_EDIT_DESC','ORG_TYPE_SUBMENU_EDIT',null,'MENU_DISPLAY_NAME','Organization Type Edit',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ORG_TYPE_SEARCH_PUB','ORG_TYPE_SEARCH',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ORG_TYPE_SEARCH_DESC','ORG_TYPE_SEARCH',null,'MENU_DISPLAY_NAME','Search Organization Types',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('NEW_ORG_TYPE_PUB','NEW_ORG_TYPE',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('NEW_ORG_TYPE_DESC','NEW_ORG_TYPE',null,'MENU_DISPLAY_NAME','New Organization Type',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('EDIT_ORG_TYPE_PUB','EDIT_ORG_TYPE',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('EDIT_ORG_TYPE_DESC','EDIT_ORG_TYPE',null,'MENU_DISPLAY_NAME','Edit Organization Type',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('EDIT_ORG_MEMBERSHIP_PUB','EDIT_ORG_MEMBERSHIP',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('EDIT_ORG_MEMBERSHIP_DESC','EDIT_ORG_MEMBERSHIP',null,'MENU_DISPLAY_NAME','Membership',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ORG_TYPE_EDIT_SEARCH_PUB','ORG_TYPE_EDIT_SEARCH',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ORG_TYPE_EDIT_SEARCH_DESC','ORG_TYPE_EDIT_SEARCH',null,'MENU_DISPLAY_NAME','Search Organization Types',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('EDIT_ORG_TYPE_IDMAN_PUB','EDIT_ORG_TYPE_IDMAN',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('EDIT_ORG_TYPE_IDMAN_DESC','EDIT_ORG_TYPE_IDMAN',null,'MENU_DISPLAY_NAME','Organization Types',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SUP_SUB_PAGE_PUB','SUP_SUB_PAGE',null,'MENU_IS_PUBLIC','false',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SUP_SUB_PAGE_DIS','SUP_SUB_PAGE',null,'MENU_DISPLAY_NAME','Superiors and Subordinates',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('BATCH_TASK_ROOT_PUB','BATCH_TASK_ROOT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('BATCH_TASK_ROOT_DESC','BATCH_TASK_ROOT',null,'MENU_DISPLAY_NAME','Batch Task Root',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SEARCH_BATCH_NEW_PUB','SEARCH_BATCH_NEW',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SEARCH_BATCH_NEW_DESC','SEARCH_BATCH_NEW',null,'MENU_DISPLAY_NAME','Search Batch Tasks',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('NEW_BATCH_TASK_PUB','NEW_BATCH_TASK',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('NEW_BATCH_TASK_DESC','NEW_BATCH_TASK',null,'MENU_DISPLAY_NAME','New Batch Task',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('BATCH_TASK_EDIT_PUB','BATCH_TASK_EDIT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('BATCH_TASK_EDIT_DESC','BATCH_TASK_EDIT',null,'MENU_DISPLAY_NAME','Batch Task Edit',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SEARCH_BATCH_EDIT_PUB','SEARCH_BATCH_EDIT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SEARCH_BATCH_EDIT_DESC','SEARCH_BATCH_EDIT',null,'MENU_DISPLAY_NAME','Search Batch Tasks',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('EDIT_BATCH_TASK_PUB','EDIT_BATCH_TASK',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('EDIT_BATCH_TASK_DESC','EDIT_BATCH_TASK',null,'MENU_DISPLAY_NAME','Edit Batch Task',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ADMIN_BATCH_TASKS_PUB','ADMIN_BATCH_TASKS',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ADMIN_BATCH_TASKS_DESC','ADMIN_BATCH_TASKS',null,'MENU_DISPLAY_NAME','Batch Tasks',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('DIR_LOOKUP_PAGE_PUB','DIR_LOOKUP_PAGE',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('DIR_LOOKUP_PAGE_DIS','DIR_LOOKUP_PAGE',null,'MENU_DISPLAY_NAME','Directory Lookup',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('MANAGEREQ_VISIBLE','MANAGEREQ',null,'IS_VISIBLE','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('MANUALRECON_DESC','MANUALRECON',null,'MENU_DISPLAY_NAME','Manual reconciliation',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SUBSCRIBE_MENU_PUB','SUBSCRIBE',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SUBSCRIBE_MENU_DESC','SUBSCRIBE',null,'MENU_DISPLAY_NAME','Subscribe',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('VIEW_MENU_PUB','VIEW',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('VIEW_MENU_DESC','VIEW',null,'MENU_DISPLAY_NAME','View',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELF_SUB_REPORT_ROOT_PUB','SELF_SUB_REPORT_ROOT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELF_SUB_REPORT_ROOT_DESC','SELF_SUB_REPORT_ROOT',null,'MENU_DISPLAY_NAME','Subscribe Report Root Menu',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELF_SUB_REPORT_NEW_PUB','SELF_SUB_REPORT_NEW',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELF_SUB_REPORT_NEW_DSC','SELF_SUB_REPORT_NEW',null,'MENU_DISPLAY_NAME','Subscribe Report',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELF_VIEW_REPORT_ROOT_PUB','SELF_VIEW_REPORT_ROOT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELF_VIEW_REPORT_ROOT_DESC','SELF_VIEW_REPORT_ROOT',null,'MENU_DISPLAY_NAME','View Report Root Menu',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELF_VIEW_REPORT_NEW_PUB','SELF_VIEW_REPORT_NEW',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELF_VIEW_REPORT_NEW_DSC','SELF_VIEW_REPORT_NEW',null,'MENU_DISPLAY_NAME','Create New View Report',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELFSERVE_SUP_SUB_PUB','SELFSERVE_SUP_SUB',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELFSERVE_SUP_SUB_DESC','SELFSERVE_SUP_SUB',null,'MENU_DISPLAY_NAME','Superiors and Subordinates',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELFSER_USER_LOGIN_PUB','SELFSER_USER_LOGIN',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELFSER_USER_LOGIN_DESC','SELFSER_USER_LOGIN',null,'MENU_DISPLAY_NAME','Identities',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELFSER_USER_NEW_LOGIN_PUB','SELFSER_USER_NEW_LOGIN',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELFSER_USER_NEW_LOGIN_DESC','SELFSER_USER_NEW_LOGIN',null,'MENU_DISPLAY_NAME','Create New Identity',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ACCESSCENTER_MENU_DISPLAY','ACCESSCENTER',null,'MENU_DISPLAY_NAME','Access Management',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ACCESSCENTER_MENU_ICON','ACCESSCENTER',null,'MENU_ICON','/openiam-ui-static/images/common/icons/access_control.png',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('WEB_REPORT_DESC','WEB_REPORT',null,'MENU_DISPLAY_NAME','Report',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('WEB_REPORT_PUB','WEB_REPORT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('WEB_REPORT_DISPLAY','WEB_REPORT',null,'MENU_ICON','/openiam-ui-static/images/common/icons/reports.png',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELFSERVICE_REPORT_DESC','SELFSERVICE_REPORT',null,'MENU_DISPLAY_NAME','Report',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELFSERVICE_REPORT_PUB','SELFSERVICE_REPORT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELFSERVICE_REPORT_DISPLAY','SELFSERVICE_REPORT',null,'MENU_ICON','/openiam-ui-static/images/common/icons/reports.png',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELFSRVS_ACCESS_MNGMNT_ORG_DESC','SELFSRVS_ACCESS_MNGMNT_ORG',null,'MENU_DISPLAY_NAME','Organizations',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SS_ACC_MNGMNT_ORG_NEW_DESC','SELF_SUB_ORGS_NEW',null,'MENU_DISPLAY_NAME','New Organization',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SS_ACC_MG_ORG_SEARCH_DESC','SELF_SUB_ORGS_SEARCH',null,'MENU_DISPLAY_NAME','Search Organization',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SS_ACC_MNGMNT_ORG_EDIT_DESC','SELF_SUB_ORGS_EDIT',null,'MENU_DISPLAY_NAME','Edit Organization',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SS_ACC_ORG_MEM_DESC','SELF_SUB_ORGS_MEMEBERSHIP',null,'MENU_DISPLAY_NAME','Organization Membership',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SYNCLOG_ADMIN_DESC','SYNCLOG',null,'MENU_DISPLAY_NAME','Log Viewer',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SYNCLOG_ADMIN_PUB','SYNCLOG',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('113','113',null,'TABLE_NAME','MY_USR_TABLE',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('200','101',null,'INCLUDE_USER_OBJECT','N',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('202','101',null,'PRINCIPAL_NAME','uid',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('203','101',null,'PRINCIPAL_PASSWORD','userPassword',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('204','101',null,'INCLUDE_IN_PASSWORD_SYNC','Y',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('205','101',null,'ON_DELETE','DELETE',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('206','101',null,'GROUP_MEMBERSHIP_ENABLED','Y',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('207','101',null,'PRE_PROCESS','/prov-helper/LDAPPreProcessor.groovy',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('208','101',null,'POST_PROCESS','/prov-helper/LDAPPostProcessor.groovy',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('210','105',null,'INCLUDE_USER_OBJECT','Y',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('211','105',null,'PRINCIPAL_NAME','login',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('212','105',null,'PRINCIPAL_PASSWORD','userPassword',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('213','105',null,'INCLUDE_IN_PASSWORD_SYNC','Y',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('214','105',null,'ON_DELETE','DELETE',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('215','105',null,'GROUP_MEMBERSHIP_ENABLED','Y',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('216','105',null,'PRE_PROCESS',null,null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('217','105',null,'POST_PROCESS',null,null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('220','110',null,'ON_DELETE','DELETE',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('221','110',null,'INCLUDE_IN_PASSWORD_SYNC','Y',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('222','110',null,'GROUP_MEMBERSHIP_ENABLED','Y',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('223','101',null,'PRE_PROCESS',null,null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('224','101',null,'POST_PROCESS',null,null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ACC_CONTROL_MENU_DISPLAY','ACC_CONTROL',null,'MENU_DISPLAY_NAME','Access Control',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ACC_CONTROL_MENU_ICON','ACC_CONTROL',null,'MENU_ICON','/openiam-ui-static/images/common/icons/access_control.png',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ACONTENT_PROV_NEW_EDIT_PUB_DESC','CONTENT_PROV_NEW_EDIT',null,'MENU_DISPLAY_NAME','Create New Provider',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ADMIN_MENU_DISPLAY','ADMIN',null,'MENU_DISPLAY_NAME','Administration',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ADMIN_MENU_ICON','ADMIN',null,'MENU_ICON','/openiam-ui-static/images/common/icons/admin.png',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('AM_PROV_AM_ATTR_DESC','AM_PROV_AM_ATTR',null,'MENU_DISPLAY_NAME','Request Attributes',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('AM_PROV_AM_ATTR_PUB','AM_PROV_AM_ATTR',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('AM_PROV_EDIT_CURRENT_DESC','AM_PROV_EDIT_CURRENT',null,'MENU_DISPLAY_NAME','Edit Provider',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('AM_PROV_EDIT_CURRENT_PUB','AM_PROV_EDIT_CURRENT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('AM_PROV_EDIT_DESC','AM_PROV_EDIT',null,'MENU_DISPLAY_NAME','Authentication Provider Edit Menu',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('AM_PROV_EDIT_PUB','AM_PROV_EDIT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('AM_PROV_EDIT_SEARCH_DESC','AM_PROV_EDIT_SEARCH',null,'MENU_DISPLAY_NAME','Back to Search',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('AM_PROV_EDIT_SEARCH_PUB','AM_PROV_EDIT_SEARCH',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('AM_PROV_NEW_DSC','AM_PROV_NEW',null,'MENU_DISPLAY_NAME','Create New Provider',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('AM_PROV_NEW_EDIT_PUB','AM_PROV_NEW_EDIT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('AM_PROV_NEW_EDIT_PUB_DESC','AM_PROV_NEW_EDIT',null,'MENU_DISPLAY_NAME','Create New Provider',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('AM_PROV_NEW_PUB','AM_PROV_NEW',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('AM_PROV_ROOT_DESC','AM_PROV_ROOT',null,'MENU_DISPLAY_NAME','Authentication Provider Root Menu',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('AM_PROV_ROOT_PUB','AM_PROV_ROOT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('AM_PROV_SEARCH_CHILD_PUB','AM_PROV_SEARCH_CHILD',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('AM_PROV_SEARCH_CHILD_PUB_DESC','AM_PROV_SEARCH_CHILD',null,'MENU_DISPLAY_NAME','Authentication Providers',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('AM_PROV_SEARCH_DESC','AM_PROV_SEARCH',null,'MENU_DISPLAY_NAME','Search Providers',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('AM_PROV_SEARCH_PUB','AM_PROV_SEARCH',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('AUDITREPORT_MENU_DISPLAY','AUDITREPORT',null,'MENU_DISPLAY_NAME','Audit Reports',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('AUDIT_RPT_MENU_DISPLAY','AUDIT_RPT',null,'MENU_DISPLAY_NAME','Audit Report',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('BACK_TO_CP_DESC','BACK_TO_CP',null,'MENU_DISPLAY_NAME','Back To Content Provider',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('BACK_TO_CP_PUB','BACK_TO_CP',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('BATCH_PROC_MENU_DISPLAY','BATCH_PROC',null,'MENU_DISPLAY_NAME','Batch Processes',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('BATCH_PROC_VISIBLE','BATCH_PROC',null,'IS_VISIBLE','false',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CHALLENGE_MENU_DISPLAY','CHALLENGE',null,'MENU_DISPLAY_NAME','Challenge Quest',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CHALLENGE_VISIBLE','CHALLENGE',null,'IS_VISIBLE','false',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CHNGPSWD_MENU_DISPLAY','CHNGPSWD',null,'MENU_DISPLAY_NAME','Change Password',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CONTENT_PROV_EDIT_CURRENT_DESC','CONTENT_PROV_EDIT_CURRENT',null,'MENU_DISPLAY_NAME','Edit Provider',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CONTENT_PROV_EDIT_CURRENT_PUB','CONTENT_PROV_EDIT_CURRENT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CONTENT_PROV_EDIT_DESC','CONTENT_PROV_EDIT',null,'MENU_DISPLAY_NAME','Content Provider Edit Menu',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CONTENT_PROV_EDIT_PUB','CONTENT_PROV_EDIT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CONTENT_PROV_EDIT_SEARCH_DESC','CONTENT_PROV_EDIT_SEARCH',null,'MENU_DISPLAY_NAME','Back to Search',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CONTENT_PROV_EDIT_SEARCH_PUB','CONTENT_PROV_EDIT_SEARCH',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CONTENT_PROV_NEW_DSC','CONTENT_PROV_NEW',null,'MENU_DISPLAY_NAME','Create New Provider',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CONTENT_PROV_NEW_EDIT_PUB','CONTENT_PROV_NEW_EDIT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CONTENT_PROV_NEW_PUB','CONTENT_PROV_NEW',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CONTENT_PROV_ROOT_DESC','CONTENT_PROV_ROOT',null,'MENU_DISPLAY_NAME','Content Provider Root Menu',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CONTENT_PROV_ROOT_PUB','CONTENT_PROV_ROOT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CONTENT_PROV_SEARCH_CHILD_PUB','CONTENT_PROV_SEARCH_CHILD',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CONTENT_PROV_SEARCH_CHILD_PUB_DE','CONTENT_PROV_SEARCH_CHILD',null,'MENU_DISPLAY_NAME','Content Providers',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CONTENT_PROV_SEARCH_DESC','CONTENT_PROV_SEARCH',null,'MENU_DISPLAY_NAME','Search Content Providers',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CONTENT_PROV_SEARCH_PUB','CONTENT_PROV_SEARCH',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CREATEREQ_MENU_DISPLAY','CREATEREQ',null,'MENU_DISPLAY_NAME','Create Request',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CR_RES_DISP_NAME','CREATE_RESOURCE_MENU',null,'MENU_DISPLAY_NAME','Create New Resource',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CR_RES_MENU_PUB','CREATE_RESOURCE_MENU',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CUSTOM_FIELD_EDIT_DESC','CUSTOM_FIELD_EDIT',null,'MENU_DISPLAY_NAME','Custom Field Edit Menu',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CUSTOM_FIELD_EDIT_NEW_DESC','CUSTOM_FIELD_EDIT_NEW',null,'MENU_DISPLAY_NAME','Create New Custom Field',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CUSTOM_FIELD_EDIT_NEW_PUB','CUSTOM_FIELD_EDIT_NEW',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CUSTOM_FIELD_EDIT_PUB','CUSTOM_FIELD_EDIT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CUSTOM_FIELD_EDIT_SEARCH_DESC','CUSTOM_FIELD_EDIT_SEARCH',null,'MENU_DISPLAY_NAME','Back to Search',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CUSTOM_FIELD_EDIT_SEARCH_PUB','CUSTOM_FIELD_EDIT_SEARCH',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CUSTOM_FIELD_NEW_DSC','CUSTOM_FIELD_NEW',null,'MENU_DISPLAY_NAME','Create New Custom Field',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CUSTOM_FIELD_NEW_PUB','CUSTOM_FIELD_NEW',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CUSTOM_FIELD_ROOT_DESC','CUSTOM_FIELD_ROOT',null,'MENU_DISPLAY_NAME','Custom Field Root Menu',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CUSTOM_FIELD_ROOT_PUB','CUSTOM_FIELD_ROOT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CUSTOM_FIELD_SEARCH_CHILD_DESC','CUSTOM_FIELD_SEARCH_CHILD',null,'MENU_DISPLAY_NAME','Custom Fields',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CUSTOM_FIELD_SEARCH_CHILD_PUB','CUSTOM_FIELD_SEARCH_CHILD',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CUSTOM_FIELD_SEARCH_DESC','CUSTOM_FIELD_SEARCH',null,'MENU_DISPLAY_NAME','Search Custom Fields',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CUSTOM_FIELD_SEARCH_PUB','CUSTOM_FIELD_SEARCH',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('DELG_FILTER_MENU_DISPLAY','DELG_FILTER',null,'MENU_DISPLAY_NAME','Delegation Filter',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('DIRECTORY_MENU_DISPLAY','DIRECTORY',null,'MENU_DISPLAY_NAME','Directory Lookup',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('DIRECTORY_VISIBLE','DIRECTORY',null,'IS_VISIBLE','false',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('EDIT_GROUP_DESC','EDIT_GROUP',null,'MENU_DISPLAY_NAME','Edit Group',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('EDIT_GROUP_PUB','EDIT_GROUP',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('EDIT_ORG_DESC','EDIT_ORG',null,'MENU_DISPLAY_NAME','Edit Organization',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('EDIT_ORG_PUB','EDIT_ORG',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('EDIT_RESOURCE_DIS','EDIT_RESOURCE',null,'MENU_DISPLAY_NAME','Edit Resource',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('EDIT_RESOURCE_PUB','EDIT_RESOURCE',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('EDIT_ROLE_DESC','EDIT_ROLE',null,'MENU_DISPLAY_NAME','Edit Role',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('EDIT_ROLE_PUB','EDIT_ROLE',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('GROUP_EDIT_MENU_DESC','GROUP_EDIT_MENU',null,'MENU_DISPLAY_NAME','Group Root Edit Menu',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('GROUP_EDIT_MENU_PUB','GROUP_EDIT_MENU',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('GROUP_ENTITLEMENTS_DESC','GROUP_ENTITLEMENTS',null,'MENU_DISPLAY_NAME','Group Entitlements',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('GROUP_ENTITLEMENTS_PUB','GROUP_ENTITLEMENTS',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('GROUP_MENUS_DESC','GROUP_MENUS',null,'MENU_DISPLAY_NAME','Menus',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('GROUP_MENUS_PUB','GROUP_MENUS',null,'MENU_IS_PUBLIC','false',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('GROUP_ROOT_MENU_DESC','GROUP_ROOT_MENU',null,'MENU_DISPLAY_NAME','Group Root Menu',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('GROUP_ROOT_MENU_PUB','GROUP_ROOT_MENU',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('GROUP_SEARCH_DESC','GROUP_SEARCH',null,'MENU_DISPLAY_NAME','Group Search',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('GROUP_SEARCH_PUB','GROUP_SEARCH',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('IDMAN_MENU_DISPLAY','IDMAN',null,'MENU_DISPLAY_NAME','User Admin',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('IDMAN_MENU_ICON','IDMAN',null,'MENU_ICON','/openiam-ui-static/images/common/icons/user_manager.png',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('IDM_MENU_DISPLAY','IDM',null,'MENU_DISPLAY_NAME','Identity',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('IDM_MENU_PUBLIC','IDM',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('IDQUEST_MENU_DISPLAY','IDQUEST',null,'MENU_DISPLAY_NAME','Challenge Response',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('LOCATION_MENU_DISPLAY','LOCATION',null,'MENU_DISPLAY_NAME','Location',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('LOCATION_VISIBLE','LOCATION',null,'IS_VISIBLE','false',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('MANAGEREQ_MENU_DISPLAY','MANAGEREQ',null,'MENU_DISPLAY_NAME','Request History',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('METADATA_MENU_DISPLAY','METADATA',null,'MENU_DISPLAY_NAME','Metadata',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('METADATA_VISIBLE','METADATA',null,'IS_VISIBLE','false',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('MNGSYS_DESC','MNGSYS_MENU_ITEM',null,'MENU_DISPLAY_NAME','Managed Systems',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('MNGSYS_DSC','MNGSYS_NEW',null,'MENU_DISPLAY_NAME','Create Managed System',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('MNGSYS_MENU_DISPLAY','MNGSYS',null,'MENU_DISPLAY_NAME','Managed System',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('MNGSYS_NEW_PUB','MNGSYS_NEW',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('MNGSYS_PUB','MNGSYS_MENU_ITEM',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('NEWUSER-NOAPPRV_MENU_DISPLAY','NEWUSER-NOAPPRV',null,'MENU_DISPLAY_NAME','New User-NO Approver',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('NEWUSER_MENU_DISPLAY','NEWUSER',null,'MENU_DISPLAY_NAME','New User',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('NEW_GROUP_DESC','NEW_GROUP',null,'MENU_DISPLAY_NAME','Create New Group',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('NEW_GROUP_PUB','NEW_GROUP',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('NEW_ORG_DESC','NEW_ORG',null,'MENU_DISPLAY_NAME','Create New Organization',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('NEW_ORG_PUB','NEW_ORG',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('NEW_ROLE_DESC','NEW_ROLE',null,'MENU_DISPLAY_NAME','Create New Role',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('NEW_ROLE_PUB','NEW_ROLE',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('NEW_USER_DESC','NEW_USER',null,'MENU_DISPLAY_NAME','Create New User',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('NEW_USER_PUB','NEW_USER',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ORGANIZATION_EDIT_ID_DESC','ORGANIZATION_EDIT_ID',null,'MENU_DISPLAY_NAME','Organization Edit Menus',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ORGANIZATION_EDIT_ID_PUB','ORGANIZATION_EDIT_ID',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ORGANIZATION_ROOT_ID_DESC','ORGANIZATION_ROOT_ID',null,'MENU_DISPLAY_NAME','Organization Root Menus',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ORGANIZATION_ROOT_ID_PUB','ORGANIZATION_ROOT_ID',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ORG_MEMBERSHIP_DESC','ORG_MEMBERSHIP',null,'MENU_DISPLAY_NAME','Organization Membership',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ORG_MEMBERSHIP_PUB','ORG_MEMBERSHIP',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ORG_MENU_DISPLAY','ORG',null,'MENU_DISPLAY_NAME','Organization',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ORG_MENU_PUBLIC','ORG',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ORG_SEARCH_DESC','ORG_SEARCH',null,'MENU_DISPLAY_NAME','Organization Search',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ORG_SEARCH_PUB','ORG_SEARCH',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('PAGE_TEMPLATE_EDIT_DESC','PAGE_TEMPLATE_EDIT',null,'MENU_DISPLAY_NAME','Page Template Edit Menu',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('PAGE_TEMPLATE_EDIT_NEW_DESC','PAGE_TEMPLATE_EDIT_NEW',null,'MENU_DISPLAY_NAME','Create New Template',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('PAGE_TEMPLATE_EDIT_NEW_PUB','PAGE_TEMPLATE_EDIT_NEW',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('PAGE_TEMPLATE_EDIT_PUB','PAGE_TEMPLATE_EDIT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('PAGE_TEMPLATE_EDIT_SEARCH_DESC','PAGE_TEMPLATE_EDIT_SEARCH',null,'MENU_DISPLAY_NAME','Back to Search',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('PAGE_TEMPLATE_EDIT_SEARCH_PUB','PAGE_TEMPLATE_EDIT_SEARCH',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('PAGE_TEMPLATE_NEW_DSC','PAGE_TEMPLATE_NEW',null,'MENU_DISPLAY_NAME','Create New Template',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('PAGE_TEMPLATE_NEW_PUB','PAGE_TEMPLATE_NEW',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('PAGE_TEMPLATE_ROOT_DESC','PAGE_TEMPLATE_ROOT',null,'MENU_DISPLAY_NAME','Page Template Root Menu',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('PAGE_TEMPLATE_ROOT_PUB','PAGE_TEMPLATE_ROOT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('PAGE_TEMPLATE_SEARCH_DESC','PAGE_TEMPLATE_SEARCH',null,'MENU_DISPLAY_NAME','Search Page Templates',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('PAGE_TEMPLATE_SEARCH_PUB','PAGE_TEMPLATE_SEARCH',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('PROFILE_MENU_DISPLAY','PROFILE',null,'MENU_DISPLAY_NAME','Edit Your Profile',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('PROFILE_VISIBLE','PROFILE',null,'IS_VISIBLE','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('PROVCONNECT_DESC','PROVCONNECT_MENU_ITEM',null,'MENU_DISPLAY_NAME','Provisioning Connectors',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('PROVCONNECT_MENU_DISPLAY','PROVCONNECT',null,'MENU_DISPLAY_NAME','Connectors',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('PROVCONNECT_PUB','PROVCONNECT_MENU_ITEM',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('PROVISIONING_MENU_DISPLAY','PROVISIONING',null,'MENU_DISPLAY_NAME','Provisioning',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('PROVISIONING_MENU_ICON','PROVISIONING',null,'MENU_ICON','/openiam-ui-static/images/common/icons/menu7.png',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('PROV_CONNECTOR_DSC','PROV_CONNECTOR_NEW',null,'MENU_DISPLAY_NAME','Create New Connector',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('PROV_CONNECTOR_NEW_PUB','PROV_CONNECTOR_NEW',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('REPORT_MENU_DISPLAY','REPORT',null,'MENU_DISPLAY_NAME','Manage Reports',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('REPORT_VISIBLE','REPORT',null,'IS_VISIBLE','false',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('REQINBOX_MENU_DISPLAY','REQINBOX',null,'MENU_DISPLAY_NAME','In-Box',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('RESOURCE_EDIT_PAGE_ROOT_DIS','RESOURCE_EDIT_PAGE_ROOT',null,'MENU_DISPLAY_NAME','Resource Edit Page Root',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('RESOURCE_EDIT_PAGE_ROOT_PUB','RESOURCE_EDIT_PAGE_ROOT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('RESPOLICYMAP_MENU_DISPLAY','RESPOLICYMAP',null,'MENU_DISPLAY_NAME','Policy Map',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('RESRECONCILE_MENU_DISPLAY','RESRECONCILE',null,'MENU_DISPLAY_NAME','Reconciliation',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('RESSUMMARY_MENU_DISPLAY','RESSUMMARY',null,'MENU_DISPLAY_NAME','Resource Details',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('RES_ENT_SUB_DIS','RES_ENT_SUB',null,'MENU_DISPLAY_NAME','Entitlements',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('RES_ENT_SUB_PUB','RES_ENT_SUB',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ROLE_EDIT_MENU_DESC','ROLE_EDIT_MENU',null,'MENU_DISPLAY_NAME','Role Root Edit Menu',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ROLE_EDIT_MENU_PUB','ROLE_EDIT_MENU',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ROLE_ENTITLEMENTS_DESC','ROLE_ENTITLEMENTS',null,'MENU_DISPLAY_NAME','Role Entitlements',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ROLE_ENTITLEMENTS_PUB','ROLE_ENTITLEMENTS',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ROLE_MENUS_DESC','ROLE_MENUS',null,'MENU_DISPLAY_NAME','Menus',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ROLE_MENUS_PUB','ROLE_MENUS',null,'MENU_IS_PUBLIC','false',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ROLE_ROOT_MENU_DESC','ROLE_ROOT_MENU',null,'MENU_DISPLAY_NAME','Role Root Menu',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ROLE_ROOT_MENU_PUB','ROLE_ROOT_MENU',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ROLE_SEARCH_DESC','ROLE_SEARCH',null,'MENU_DISPLAY_NAME','Role Search',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ROLE_SEARCH_PUB','ROLE_SEARCH',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SECDOMAIN_MENU_DISPLAY','SECDOMAIN',null,'MENU_DISPLAY_NAME','Security Domain',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SECDOMAIN_VISIBLE','SECDOMAIN',null,'IS_VISIBLE','false',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SECURITY_GROUP_MENU_DISPLAY','SECURITY_GROUP',null,'MENU_DISPLAY_NAME','Group',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SECURITY_GROUP_PUB','SECURITY_GROUP',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SECURITY_POLICY_MENU_DISPLAY','SECURITY_POLICY',null,'MENU_DISPLAY_NAME','Policy',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SECURITY_POLICY_MENU_ICON','SECURITY_POLICY',null,'MENU_ICON','/openiam-ui-static/images/common/icons/sso.png',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SECURITY_RES_MENU_DISPLAY','SECURITY_RES',null,'MENU_DISPLAY_NAME','Resource',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SECURITY_ROLE_MENU_DISPLAY','SECURITY_ROLE',null,'MENU_DISPLAY_NAME','Role',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SECURITY_ROLE_MENU_PUBLIC','SECURITY_ROLE',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELFCENTER_MENU_DISPLAY','SELFCENTER',null,'MENU_DISPLAY_NAME','Self Service Center',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELFCENTER_MENU_ICON','SELFCENTER',null,'MENU_ICON','/openiam-ui-static/images/common/icons/user_manager.png',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELFSERVICE_MENU_DISPLAY','SELFSERVICE',null,'MENU_DISPLAY_NAME','SELF SERVICE',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELFSERVICE_MENU_PUBLIC','SELFSERVICE',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELFSERVICE_MYAPPS_DESC','SELFSERVICE_MYAPPS',null,'MENU_DISPLAY_NAME','My Applications',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELFSERVICE_MYAPPS_MENU_ICON','SELFSERVICE_MYAPPS',null,'MENU_ICON','/openiam-ui-static/images/common/icons/sso.png',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELFSERVICE_MYAPPS_PUB','SELFSERVICE_MYAPPS',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELFSERVICE_MYINFO_DESC','SELFSERVICE_MYINFO',null,'MENU_DISPLAY_NAME','My Info',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELFSERVICE_MYINFO_MENU_ICON','SELFSERVICE_MYINFO',null,'MENU_ICON','/openiam-ui-static/images/common/icons/menu7.png',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELFSERVICE_MYINFO_PUB','SELFSERVICE_MYINFO',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELF_QUERYUSER_MENU_DISPLAY','SELF_QUERYUSER',null,'MENU_DISPLAY_NAME','Manage User',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SYNCUSER_MENU_DISPLAY','SYNCUSER',null,'MENU_DISPLAY_NAME','Synchronization',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('TEMPLATE_SEARCH_CHILD_DESC','TEMPLATE_SEARCH_CHILD',null,'MENU_DISPLAY_NAME','Page Template',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('TEMPLATE_SEARCH_CHILD_PUB','TEMPLATE_SEARCH_CHILD',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('URI_META_BACK_DESC','URI_META_BACK',null,'MENU_DISPLAY_NAME','Back To Pattern',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('URI_META_BACK_PUB','URI_META_BACK',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('URI_META_EDIT_DESC','URI_META_EDIT',null,'MENU_DISPLAY_NAME','URI Meta Root Item',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('URI_META_EDIT_PUB','URI_META_EDIT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('URI_PATTERN_EDIT_DESC','URI_PATTERN_EDIT',null,'MENU_DISPLAY_NAME','URI Pattern Edit Menu',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('URI_PATTERN_EDIT_NEW_PATTERN_DES','URI_PATTERN_EDIT_NEW_PATTERN',null,'MENU_DISPLAY_NAME','Create URI Pattern',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('URI_PATTERN_EDIT_NEW_PATTERN_PUB','URI_PATTERN_EDIT_NEW_PATTERN',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('URI_PATTERN_EDIT_PATTERN_DESC','URI_PATTERN_EDIT_PATTERN',null,'MENU_DISPLAY_NAME','Edit URI Pattern',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('URI_PATTERN_EDIT_PATTERN_PUB','URI_PATTERN_EDIT_PATTERN',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('URI_PATTERN_EDIT_PUB','URI_PATTERN_EDIT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('URI_PATTERN_EDT_PROV_SEARCH_DESC','URI_PATTERN_EDT_PROV_SEARCH',null,'MENU_DISPLAY_NAME','Content Provider Search',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('URI_PATTERN_EDT_PROV_SEARCH_PUB','URI_PATTERN_EDT_PROV_SEARCH',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('USER_ATTRIBUTES_DESC','USER_ATTRIBUTES',null,'MENU_DISPLAY_NAME','User Attributes',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('USER_ATTRIBUTES_PUB','USER_ATTRIBUTES',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('USER_BULK_MENU_DISPLAY','USER_BULK',null,'MENU_DISPLAY_NAME','User Bulk Ops',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('USER_BULK_VISIBLE','USER_BULK',null,'IS_VISIBLE','false',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('USER_DELEGATION_DESC','USER_DELEGATION',null,'MENU_DISPLAY_NAME','User Delegation Filter',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('USER_DELEGATION_PUB','USER_DELEGATION',null,'MENU_IS_PUBLIC','false',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('USER_EDIT_CONTACT_INFO_DESC','USER_EDIT_CONTACT_INFO',null,'MENU_DISPLAY_NAME','Contact Info',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('USER_EDIT_CONTACT_INFO_PUB','USER_EDIT_CONTACT_INFO',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('USER_EDIT_INFO_DESC','USER_EDIT_INFO',null,'MENU_DISPLAY_NAME','User Info',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('USER_EDIT_INFO_PUB','USER_EDIT_INFO',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('USER_EDIT_MENU_DESC','USER_EDIT_MENU',null,'MENU_DISPLAY_NAME','User Root Edit Menu',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('USER_EDIT_MENU_PUB','USER_EDIT_MENU',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('USER_ENTITLEMENTS_DESC','USER_ENTITLEMENTS',null,'MENU_DISPLAY_NAME','User Entitlements',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('USER_ENTITLEMENTS_PUB','USER_ENTITLEMENTS',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('USER_HISTORY_DESC','USER_HISTORY',null,'MENU_DISPLAY_NAME','User History',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('USER_HISTORY_PUB','USER_HISTORY',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('USER_IDENTITY_DESC','USER_IDENTITY',null,'MENU_DISPLAY_NAME','User Identities',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('USER_IDENTITY_PUB','USER_IDENTITY',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('USER_MENUS_DESC','USER_MENUS',null,'MENU_DISPLAY_NAME','Menus',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('USER_MENUS_PUB','USER_MENUS',null,'MENU_IS_PUBLIC','false',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('USER_MENU_DESC','USER',null,'MENU_DISPLAY_NAME','User',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('USER_MENU_PUB','USER',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('USER_RESET_PASSWORD_DESC','USER_RESET_PASSWORD',null,'MENU_DISPLAY_NAME','Reset Password',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('USER_RESET_PASSWORD_PUB','USER_RESET_PASSWORD',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('USER_ROOT_MENU_DESC','USER_ROOT_MENU',null,'MENU_DISPLAY_NAME','User Root Menu',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('USER_ROOT_MENU_PUB','USER_ROOT_MENU',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('USER_SEARCH_DESC','USER_SEARCH',null,'MENU_DISPLAY_NAME','User Search',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('USER_SEARCH_PUB','USER_SEARCH',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SYNCUSER_DESC','SYNCUSER_MENU_ITEM',null,'MENU_DISPLAY_NAME','Synchronization',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SYNCUSER_PUB','SYNCUSER_MENU_ITEM',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SYNCUSER_NEW_DESC','SYNCUSER_NEW',null,'MENU_DISPLAY_NAME','Create Synchronization',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SYNCUSER_NEW_PUB','SYNCUSER_NEW',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('MNGSYS_EDIT_DESC','MNGSYS_EDIT',null,'MENU_DISPLAY_NAME','Edit managed system',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('POLICY_MENU_PUB','PASSWORD_POLICY',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('POLICY_MENU_DESC','PASSWORD_POLICY',null,'MENU_DISPLAY_NAME','Password Policy',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('POLICY_ROOT_MENU_PUB','PASSWORD_POLICY_ROOT_MENU',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('POLICY_ROOT_MENU_DESC','PASSWORD_POLICY_ROOT_MENU',null,'MENU_DISPLAY_NAME','Password Policy Root Menu',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('NEW_PASSWORD_POLICY_PUB','NEW_PASSWORD_POLICY',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('NEW_PASSWORD_POLICY_DESC','NEW_PASSWORD_POLICY',null,'MENU_DISPLAY_NAME','Create New Pswd Policy',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('AUTHENTICATION_POLICY_MENU_PUB','AUTHENTICATION_POLICY',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('AUTHENTICATION_POLICY_MENU_DESC','AUTHENTICATION_POLICY',null,'MENU_DISPLAY_NAME','Authenticaton Policy',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('AUTH_POLICY_ROOT_MENU_PUB','AUTHENTICATION_POLICY_ROOT_MENU',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('AUTH_POLICY_ROOT_MENU_DESC','AUTHENTICATION_POLICY_ROOT_MENU',null,'MENU_DISPLAY_NAME','Authentication Policy Root Menu',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('NEW_AUTHENTICATION_POLICY_PUB','NEW_AUTHENTICATION_POLICY',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('NEW_AUTHENTICATION_POLICY_DESC','NEW_AUTHENTICATION_POLICY',null,'MENU_DISPLAY_NAME','Create New Auth Policy',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ASSOC_POLICY_PUB','ASSOC_POLICY',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ASSOC_POLICY_DESC','ASSOC_POLICY',null,'MENU_DISPLAY_NAME','Associate Policy',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('PASSWORD_POLICY_EDIT_PUB','PASSWORD_POLICY_EDIT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('PASSWORD_POLICY_EDIT_DESC','PASSWORD_POLICY_EDIT',null,'MENU_DISPLAY_NAME','Associate Policy Root Menu',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('UPLOAD_CUST_PSWD_POLICY_PUB','UPLOAD_CUST_PSWD_POLICY',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('UPLOAD_CUST_PSWD_POLICY_DESC','UPLOAD_CUST_PSWD_POLICY',null,'MENU_DISPLAY_NAME','Custom Pswd Policy',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ATTRIBUTE_POLICY_PUB','ATTRIBUTE_POLICY',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ATTRIBUTE_POLICY_DESC','ATTRIBUTE_POLICY',null,'MENU_DISPLAY_NAME','Attribute Policy',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('POLICY_ATTR_ROOT_PUB','POLICY_ATTR_ROOT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('POLICY_ATTR_ROOT_DESC','POLICY_ATTR_ROOT',null,'MENU_DISPLAY_NAME','Attribute Policy Root Menu',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('NEW_POLICY_ATTR_PUB','NEW_POLICY_ATTR',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('NEW_POLICY_ATTR_DESC','NEW_POLICY_ATTR',null,'MENU_DISPLAY_NAME','New Attribute Policy',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('POLICY_ATTR_EDIT_ROOT_PUB','POLICY_ATTR_EDIT_ROOT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('POLICY_ATTR_EDIT_ROOTT_DESC','POLICY_ATTR_EDIT_ROOT',null,'MENU_DISPLAY_NAME','Attribute Policy Edit Root Menu',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('EDIT_POLICY_ATTR_SRCH_BCK_PUB','EDIT_POLICY_ATTR_SRCH_BCK',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('EDIT_POLICY_ATTR_SRCH_BCK_DESC','EDIT_POLICY_ATTR_SRCH_BCK',null,'MENU_DISPLAY_NAME','Back to Search',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('RES_APROVER_ASSOC_DN','RES_APROVER_ASSOC',null,'MENU_DISPLAY_NAME','Approver Associations',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('RES_APROVER_ASSOC_PUB','RES_APROVER_ASSOC',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('GRP_APROVER_ASSOC_DN','GRP_APROVER_ASSOC',null,'MENU_DISPLAY_NAME','Approver Associations',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('GRP_APROVER_ASSOC_PUB','GRP_APROVER_ASSOC',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ROLE_APROVER_ASSOC_DN','ROLE_APROVER_ASSOC',null,'MENU_DISPLAY_NAME','Approver Associations',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('ROLE_APROVER_ASSOC_PUB','ROLE_APROVER_ASSOC',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SYNCLOG_PUB','SYNCLOG_MENU_ITEM',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SYNCLOG_DESC','SYNCLOG_MENU_ITEM',null,'MENU_DISPLAY_NAME','Synchronization Transaction Log Viewer',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('MNGSYSRULES_DESC','MNGSYSRULES',null,'MENU_DISPLAY_NAME','Managed system rules',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('REQINBOX_VISIBLE','REQINBOX',null,'IS_VISIBLE','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('NEWUSER_VISIBLE','NEWUSER',null,'IS_VISIBLE','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CREATEREQ_VISIBLE','CREATEREQ',null,'IS_VISIBLE','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SS_USR_EDIT_RT_PUB','SELFSERV_USER_EDIT_ROOT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SS_USR_EDIT_RT_DESC','SELFSERV_USER_EDIT_ROOT',null,'MENU_DISPLAY_NAME','SelfService Edit User Root',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELFSERV_USER_EDIT_PUB','SELFSERV_USER_EDIT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELFSERV_USER_EDIT_DESC','SELFSERV_USER_EDIT',null,'MENU_DISPLAY_NAME','Edit User',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELFSERV_USER_MEMSHP_PUB','SELFSERV_USER_MEMSHP',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('SELFSERV_USER_MEMSHP_DES','SELFSERV_USER_MEMSHP',null,'MENU_DISPLAY_NAME','User Entitlements',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CHALLENGE_RESPONSE_QUESTION_PUB','CHALLENGE_RESPONSE_QUESTION',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CHALLENGE_RESPONSE_QUESTION_DESC','CHALLENGE_RESPONSE_QUESTION',null,'MENU_DISPLAY_NAME','Challenge Response Question',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CHALLENGE_RESP_QUESTION_ROOT_PUB','CHALLENGE_RESPONSE_QUESTION_ROOT',null,'MENU_IS_PUBLIC','true',null);
Insert into IAMUSER.RESOURCE_PROP (RESOURCE_PROP_ID,RESOURCE_ID,METADATA_ID,NAME,PROP_VALUE,PROP_GROUP) values ('CHALLENGE_RESP_QUEST_ROOT_DESC','CHALLENGE_RESPONSE_QUESTION_ROOT',null,'MENU_DISPLAY_NAME','Challenge Response Question Root Menu',null);
REM INSERTING into IAMUSER.RESOURCE_ROLE
SET DEFINE OFF;
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('ACCESSCENTER',null,'2');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('ACCESSCENTER',null,'4');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('ACC_CONTROL',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('ADMIN',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('BATCH_PROC',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('CHALLENGE',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('CHNGPSWD',null,'1');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('CHNGPSWD',null,'2');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('DELG_FILTER',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('DIRECTORY',null,'1');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('DIRECTORY',null,'2');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('IDMAN',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('IDQUEST',null,'1');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('IDQUEST',null,'2');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('LOCATION',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('MANAGEREQ',null,'4');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('METADATA',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('MNGSYS',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('NEWUSER',null,'4');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('NEWUSER-NOAPPRV',null,'4');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('NEW_USER',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('ORG',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('PROFILE',null,'1');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('PROFILE',null,'2');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('PROVCONNECT',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('PROVISIONING',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('REPORT',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('REQINBOX',null,'4');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('RESPOLICYMAP',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('RESRECONCILE',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('RESSUMMARY',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('SECDOMAIN',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('SECURITY_GROUP',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('SECURITY_POLICY',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('SECURITY_RES',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('SECURITY_ROLE',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('SELFCENTER',null,'1');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('SELFCENTER',null,'2');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('SELFSERVICE',null,'1');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('SELFSERVICE',null,'2');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('SELF_QUERYUSER',null,'2');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('SYNCUSER',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('USER',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('USER_ATTRIBUTES',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('USER_BULK',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('USER_DELEGATION',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('USER_EDIT_CONTACT_INFO',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('USER_EDIT_INFO',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('USER_ENTITLEMENTS',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('USER_HISTORY',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('USER_RESET_PASSWORD',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('USER_SEARCH',null,'9');
Insert into IAMUSER.RESOURCE_ROLE (RESOURCE_ID,START_DATE,ROLE_ID) values ('CREATEREQ',null,'1');
REM INSERTING into IAMUSER.RESOURCE_TYPE
SET DEFINE OFF;
Insert into IAMUSER.RESOURCE_TYPE (RESOURCE_TYPE_ID,DESCRIPTION,METADATA_TYPE_ID,PROVISION_RESOURCE,PROCESS_NAME) values ('AUTH_PROVIDER','Authentication Provider','AUTHENTICATION_PROVIDER',null,null);
Insert into IAMUSER.RESOURCE_TYPE (RESOURCE_TYPE_ID,DESCRIPTION,METADATA_TYPE_ID,PROVISION_RESOURCE,PROCESS_NAME) values ('AUTH_REPO','Authentication Repository','AUTH_REPO',null,null);
Insert into IAMUSER.RESOURCE_TYPE (RESOURCE_TYPE_ID,DESCRIPTION,METADATA_TYPE_ID,PROVISION_RESOURCE,PROCESS_NAME) values ('CONTENT_PROVIDER','Content Provider','CONTENT_PROVIDER',null,null);
Insert into IAMUSER.RESOURCE_TYPE (RESOURCE_TYPE_ID,DESCRIPTION,METADATA_TYPE_ID,PROVISION_RESOURCE,PROCESS_NAME) values ('MANAGED_SYS','Managed Systems','MANAGED_SYSTEM',1,null);
Insert into IAMUSER.RESOURCE_TYPE (RESOURCE_TYPE_ID,DESCRIPTION,METADATA_TYPE_ID,PROVISION_RESOURCE,PROCESS_NAME) values ('MENU_ITEM','Menus','MENU_ITEM',null,null);
Insert into IAMUSER.RESOURCE_TYPE (RESOURCE_TYPE_ID,DESCRIPTION,METADATA_TYPE_ID,PROVISION_RESOURCE,PROCESS_NAME) values ('SYS_ACTION','System Action','SYS_ACTION',0,null);
Insert into IAMUSER.RESOURCE_TYPE (RESOURCE_TYPE_ID,DESCRIPTION,METADATA_TYPE_ID,PROVISION_RESOURCE,PROCESS_NAME) values ('UI_TEMPLATE','UI Template','UI_TEMPLATE',null,null);
Insert into IAMUSER.RESOURCE_TYPE (RESOURCE_TYPE_ID,DESCRIPTION,METADATA_TYPE_ID,PROVISION_RESOURCE,PROCESS_NAME) values ('UI_WIDGET','UI Widget','UI_WIDGET',null,null);
Insert into IAMUSER.RESOURCE_TYPE (RESOURCE_TYPE_ID,DESCRIPTION,METADATA_TYPE_ID,PROVISION_RESOURCE,PROCESS_NAME) values ('URL_PATTERN','Url Pattern','URL_PATTERN',null,null);
REM INSERTING into IAMUSER.RESOURCE_USER
SET DEFINE OFF;
Insert into IAMUSER.RESOURCE_USER (RESOURCE_ID,USER_ID,START_DATE) values ('USER_MENUS','3000',null);
Insert into IAMUSER.RESOURCE_USER (RESOURCE_ID,USER_ID,START_DATE) values ('USER_DELEGATION','3000',null);
Insert into IAMUSER.RESOURCE_USER (RESOURCE_ID,USER_ID,START_DATE) values ('ROLE_MENUS','3000',null);
Insert into IAMUSER.RESOURCE_USER (RESOURCE_ID,USER_ID,START_DATE) values ('GROUP_MENUS','3000',null);
Insert into IAMUSER.RESOURCE_USER (RESOURCE_ID,USER_ID,START_DATE) values ('SUP_SUB_PAGE','3000',null);
REM INSERTING into IAMUSER.RES_TO_RES_MEMBERSHIP
SET DEFINE OFF;
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('BATCH_TASK_ROOT','SEARCH_BATCH_NEW',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('BATCH_TASK_ROOT','NEW_BATCH_TASK',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('BATCH_TASK_EDIT','SEARCH_BATCH_EDIT',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('BATCH_TASK_EDIT','EDIT_BATCH_TASK',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ADMIN','ADMIN_BATCH_TASKS',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('SELFCENTER','DIR_LOOKUP_PAGE',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('MNGSYS_EDIT_MENU_ROOT','MANUALRECON',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('SELFSERVICE','SELFSERVICE_REPORT',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('SELFSERVICE_REPORT','SUBSCRIBE',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('SELFSERVICE_REPORT','VIEW',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('SELF_SUB_REPORT_ROOT','SELF_SUB_REPORT_NEW',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('SELF_VIEW_REPORT_ROOT','SELF_VIEW_REPORT_NEW',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('SELFSERV_USER_EDIT_ROOT','SELFSERVE_SUP_SUB',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('SELFSERV_USER_EDIT_ROOT','SELFSER_USER_LOGIN',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('SELFSERV_USER_EDIT_ROOT','SELFSER_USER_NEW_LOGIN',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('SELF_SUB_ORGS_ROOT','SELF_SUB_ORGS_NEW',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('SELF_SUB_ORGS_ROOT','SELF_SUB_ORGS_SEARCH',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('SELF_SUB_ORGS_EDIT_ROOT','SELF_SUB_ORGS_EDIT',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('SELF_SUB_ORGS_EDIT_ROOT','SELF_SUB_ORGS_MEMEBERSHIP',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ADMIN','SYNCLOG',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ACCESSCENTER','SELFSRVS_ACCESS_MNGMNT_ORG',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ACCESSCENTER','CREATEREQ',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ACCESSCENTER','MANAGEREQ',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ACCESSCENTER','NEWUSER',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ACCESSCENTER','NEWUSER-NOAPPRV',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ACCESSCENTER','REQINBOX',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ACCESSCENTER','SELF_QUERYUSER',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ACC_CONTROL','AM_PROV_SEARCH_CHILD',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ACC_CONTROL','CONTENT_PROV_SEARCH_CHILD',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ACC_CONTROL','SECURITY_GROUP',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ACC_CONTROL','SECURITY_RES',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ACC_CONTROL','SECURITY_ROLE',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ADMIN','BATCH_PROC',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ADMIN','CHALLENGE',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ADMIN','CUSTOM_FIELD_SEARCH_CHILD',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ADMIN','LOCATION',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ADMIN','METADATA',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ADMIN','SECDOMAIN',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ADMIN','TEMPLATE_SEARCH_CHILD',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('AM_PROV_EDIT','AM_PROV_AM_ATTR',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('AM_PROV_EDIT','AM_PROV_EDIT_CURRENT',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('AM_PROV_EDIT','AM_PROV_EDIT_SEARCH',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('AM_PROV_EDIT','AM_PROV_NEW_EDIT',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('AM_PROV_ROOT','AM_PROV_NEW',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('AM_PROV_ROOT','AM_PROV_SEARCH',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('AUDITREPORT','AUDIT_RPT',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('CONTENT_PROV_EDIT','CONTENT_PROV_EDIT_CURRENT',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('CONTENT_PROV_EDIT','CONTENT_PROV_EDIT_SEARCH',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('CONTENT_PROV_EDIT','CONTENT_PROV_NEW_EDIT',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('CONTENT_PROV_ROOT','CONTENT_PROV_NEW',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('CONTENT_PROV_ROOT','CONTENT_PROV_SEARCH',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('CUSTOM_FIELD_EDIT','CUSTOM_FIELD_EDIT_NEW',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('CUSTOM_FIELD_EDIT','CUSTOM_FIELD_EDIT_SEARCH',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('CUSTOM_FIELD_ROOT','CUSTOM_FIELD_NEW',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('CUSTOM_FIELD_ROOT','CUSTOM_FIELD_SEARCH',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('GROUP_EDIT_MENU','EDIT_GROUP',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('GROUP_EDIT_MENU','GROUP_ENTITLEMENTS',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('GROUP_EDIT_MENU','GROUP_MENUS',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('GROUP_ROOT_MENU','GROUP_SEARCH',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('GROUP_ROOT_MENU','NEW_GROUP',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('IDM','ACC_CONTROL',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('IDM','ADMIN',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('IDM','IDMAN',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('IDM','PROVISIONING',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('IDM','REPORT',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('IDM','SECURITY_POLICY',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('IDMAN','ORG',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('IDMAN','USER',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('IDMAN','USER_BULK',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('MNGSYS_MENU_ITEM','MNGSYS_NEW',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ORGANIZATION_EDIT_ID','EDIT_ORG',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ORGANIZATION_EDIT_ID','ORG_MEMBERSHIP',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ORGANIZATION_ROOT_ID','NEW_ORG',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ORGANIZATION_ROOT_ID','ORG_SEARCH',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('PAGE_TEMPLATE_EDIT','PAGE_TEMPLATE_EDIT_NEW',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('PAGE_TEMPLATE_EDIT','PAGE_TEMPLATE_EDIT_SEARCH',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('PAGE_TEMPLATE_ROOT','PAGE_TEMPLATE_NEW',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('PAGE_TEMPLATE_ROOT','PAGE_TEMPLATE_SEARCH',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('PROVCONNECT_MENU_ITEM','PROV_CONNECTOR_NEW',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('PROVISIONING','MNGSYS',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('PROVISIONING','PROVCONNECT',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('PROVISIONING','SYNCUSER',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('REPORT','AUDITREPORT',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('RESOURCE_EDIT_PAGE_ROOT','EDIT_RESOURCE',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('MNGSYS_EDIT_MENU_ROOT','RESPOLICYMAP',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('RESOURCE_EDIT_PAGE_ROOT','RES_ENT_SUB',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('RESOURCE_MENU_ROOT','CREATE_RESOURCE_MENU',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('MNGSYS_EDIT_MENU_ROOT','RESRECONCILE',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('RESOURCE_MENU_ROOT','RESSUMMARY',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ROLE_EDIT_MENU','EDIT_ROLE',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ROLE_EDIT_MENU','ROLE_ENTITLEMENTS',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ROLE_EDIT_MENU','ROLE_MENUS',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ROLE_ROOT_MENU','NEW_ROLE',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ROLE_ROOT_MENU','ROLE_SEARCH',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('SELFCENTER','CHNGPSWD',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('SELFCENTER','DIRECTORY',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('SELFCENTER','IDQUEST',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('SELFCENTER','PROFILE',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('SELFSERVICE','ACCESSCENTER',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('SELFSERVICE','SELFCENTER',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('SELFSERVICE','SELFSERVICE_MYAPPS',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('SELFSERVICE','SELFSERVICE_MYINFO',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('USER_EDIT_MENU','SUP_SUB_PAGE',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('URI_META_EDIT','URI_META_BACK',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('URI_PATTERN_EDIT','BACK_TO_CP',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('URI_PATTERN_EDIT','URI_PATTERN_EDIT_NEW_PATTERN',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('URI_PATTERN_EDIT','URI_PATTERN_EDIT_PATTERN',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('URI_PATTERN_EDIT','URI_PATTERN_EDT_PROV_SEARCH',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('USER_EDIT_MENU','USER_ATTRIBUTES',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('USER_EDIT_MENU','USER_DELEGATION',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('USER_EDIT_MENU','USER_EDIT_CONTACT_INFO',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('USER_EDIT_MENU','USER_EDIT_INFO',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('USER_EDIT_MENU','USER_ENTITLEMENTS',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('USER_EDIT_MENU','USER_HISTORY',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('USER_EDIT_MENU','USER_IDENTITY',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('USER_EDIT_MENU','USER_MENUS',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('USER_EDIT_MENU','USER_RESET_PASSWORD',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('USER_EDIT_MENU','USER_SEARCH',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('USER_ROOT_MENU','NEW_USER',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('SYNCUSER_MENU_ITEM','SYNCUSER_NEW',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('MNGSYS_EDIT_MENU_ROOT','MNGSYS_EDIT',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('SECURITY_POLICY','PASSWORD_POLICY',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('PASSWORD_POLICY_ROOT_MENU','NEW_PASSWORD_POLICY',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('SECURITY_POLICY','AUTHENTICATION_POLICY',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('AUTHENTICATION_POLICY_ROOT_MENU','NEW_AUTHENTICATION_POLICY',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('PASSWORD_POLICY_EDIT','ASSOC_POLICY',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('PASSWORD_POLICY_ROOT_MENU','UPLOAD_CUST_PSWD_POLICY',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('SECURITY_POLICY','ATTRIBUTE_POLICY',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('POLICY_ATTR_ROOT','NEW_POLICY_ATTR',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('POLICY_ATTR_EDIT_ROOT','EDIT_POLICY_ATTR_SRCH_BCK',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('RESOURCE_EDIT_PAGE_ROOT','RES_APROVER_ASSOC',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('GROUP_EDIT_MENU','GRP_APROVER_ASSOC',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ROLE_EDIT_MENU','ROLE_APROVER_ASSOC',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('MNGSYS_EDIT_MENU_ROOT','MNGSYSRULES',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('SELFSERV_USER_EDIT_ROOT','SELFSERV_USER_EDIT',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('SELFSERV_USER_EDIT_ROOT','SELFSERV_USER_MEMSHP',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ADMIN','CHALLENGE_RESPONSE_QUESTION',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('CHALLENGE_RESPONSE_QUESTION_ROOT','CHALLENGE_RESPONSE_QUESTION_NEW',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('SYNCUSER_EDIT_MENU_ROOT','SYNCUSER_EDIT',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('SYNCUSER_EDIT_MENU_ROOT','SYNCUSER_POLICYMAP',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('IDM','WEB_REPORT',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('WEB_REPORT_ROOT','WEB_REPORT_NEW',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ADMIN','CONF_POLICY_PAGE',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ORGANIZATION_EDIT_ID','ORG_APROVER_ASSOC',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ORG_TYPE_SUBMENU_ROOT','ORG_TYPE_SEARCH',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ORG_TYPE_SUBMENU_ROOT','NEW_ORG_TYPE',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ORG_TYPE_SUBMENU_EDIT','ORG_TYPE_EDIT_SEARCH',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ORG_TYPE_SUBMENU_EDIT','EDIT_ORG_TYPE',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('ORG_TYPE_SUBMENU_EDIT','EDIT_ORG_MEMBERSHIP',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
Insert into IAMUSER.RES_TO_RES_MEMBERSHIP (RESOURCE_ID,MEMBER_RESOURCE_ID,CREATE_DATE,UPDATE_DATE,CREATED_BY,UPDATED_BY) values ('IDMAN','EDIT_ORG_TYPE_IDMAN',to_date('28-MAR-14','DD-MON-RR'),to_date('28-MAR-14','DD-MON-RR'),null,null);
REM INSERTING into IAMUSER.ROLE
SET DEFINE OFF;
Insert into IAMUSER.ROLE (SERVICE_ID,ROLE_NAME,CREATE_DATE,CREATED_BY,DESCRIPTION,STATUS,ROLE_ID,MANAGED_SYS_ID) values ('USR_SEC_DOMAIN','End User',null,null,null,null,'1',null);
Insert into IAMUSER.ROLE (SERVICE_ID,ROLE_NAME,CREATE_DATE,CREATED_BY,DESCRIPTION,STATUS,ROLE_ID,MANAGED_SYS_ID) values ('USR_SEC_DOMAIN','Help Desk',null,null,null,null,'2',null);
Insert into IAMUSER.ROLE (SERVICE_ID,ROLE_NAME,CREATE_DATE,CREATED_BY,DESCRIPTION,STATUS,ROLE_ID,MANAGED_SYS_ID) values ('USR_SEC_DOMAIN','Human Resource',null,null,null,null,'3',null);
Insert into IAMUSER.ROLE (SERVICE_ID,ROLE_NAME,CREATE_DATE,CREATED_BY,DESCRIPTION,STATUS,ROLE_ID,MANAGED_SYS_ID) values ('USR_SEC_DOMAIN','Manager',null,null,null,null,'4',null);
Insert into IAMUSER.ROLE (SERVICE_ID,ROLE_NAME,CREATE_DATE,CREATED_BY,DESCRIPTION,STATUS,ROLE_ID,MANAGED_SYS_ID) values ('USR_SEC_DOMAIN','Security Manager',null,null,null,null,'5',null);
Insert into IAMUSER.ROLE (SERVICE_ID,ROLE_NAME,CREATE_DATE,CREATED_BY,DESCRIPTION,STATUS,ROLE_ID,MANAGED_SYS_ID) values ('IDM','Security Admin_IDM',null,null,null,null,'6',null);
Insert into IAMUSER.ROLE (SERVICE_ID,ROLE_NAME,CREATE_DATE,CREATED_BY,DESCRIPTION,STATUS,ROLE_ID,MANAGED_SYS_ID) values ('USR_SEC_DOMAIN','Security Admin',null,null,null,null,'7',null);
Insert into IAMUSER.ROLE (SERVICE_ID,ROLE_NAME,CREATE_DATE,CREATED_BY,DESCRIPTION,STATUS,ROLE_ID,MANAGED_SYS_ID) values ('IDM','Super Security Admin',null,null,null,null,'9',null);
REM INSERTING into IAMUSER.ROLE_ATTRIBUTE
SET DEFINE OFF;
REM INSERTING into IAMUSER.ROLE_POLICY
SET DEFINE OFF;
REM INSERTING into IAMUSER.ROLE_TO_ROLE_MEMBERSHIP
SET DEFINE OFF;
REM INSERTING into IAMUSER.SECURITY_DOMAIN
SET DEFINE OFF;
Insert into IAMUSER.SECURITY_DOMAIN (DOMAIN_ID,NAME,STATUS,AUTH_SYS_ID,LOGIN_MODULE,PASSWORD_POLICY,AUTHENTICATION_POLICY,AUDIT_POLICY) values ('IDM','IDM','ON-LINE','0',null,'4000','4001','4002');
Insert into IAMUSER.SECURITY_DOMAIN (DOMAIN_ID,NAME,STATUS,AUTH_SYS_ID,LOGIN_MODULE,PASSWORD_POLICY,AUTHENTICATION_POLICY,AUDIT_POLICY) values ('USR_SEC_DOMAIN','DEFAULT DOMAIN','ON-LINE','0',null,'4000','4001','4002');
REM INSERTING into IAMUSER.SEQUENCE_GEN
SET DEFINE OFF;
Insert into IAMUSER.SEQUENCE_GEN (ATTRIBUTE,NEXT_ID) values ('CATEGORY_ID',3000);
Insert into IAMUSER.SEQUENCE_GEN (ATTRIBUTE,NEXT_ID) values ('DOMAIN_ID',1000);
Insert into IAMUSER.SEQUENCE_GEN (ATTRIBUTE,NEXT_ID) values ('MANAGED_SYS_ID',100);
Insert into IAMUSER.SEQUENCE_GEN (ATTRIBUTE,NEXT_ID) values ('METADATA_ELEMENT_ID',2000);
Insert into IAMUSER.SEQUENCE_GEN (ATTRIBUTE,NEXT_ID) values ('METADATA_ID',3000);
Insert into IAMUSER.SEQUENCE_GEN (ATTRIBUTE,NEXT_ID) values ('METADATA_VALUE_ID',2000);
Insert into IAMUSER.SEQUENCE_GEN (ATTRIBUTE,NEXT_ID) values ('ORG_STRUCTURE_ID',200);
Insert into IAMUSER.SEQUENCE_GEN (ATTRIBUTE,NEXT_ID) values ('PRIVILEGE_ID',1001);
Insert into IAMUSER.SEQUENCE_GEN (ATTRIBUTE,NEXT_ID) values ('SERVICE_ID',1000);
Insert into IAMUSER.SEQUENCE_GEN (ATTRIBUTE,NEXT_ID) values ('TYPE_ID',1013);
REM INSERTING into IAMUSER.SERVICE
SET DEFINE OFF;
Insert into IAMUSER.SERVICE (SERVICE_ID,SERVICE_NAME,STATUS,LOCATION_IP_ADDRESS,COMPANY_OWNER_ID,START_DATE,END_DATE,LICENSE_KEY,SERVICE_TYPE,PARENT_SERVICE_ID,ROOT_RESOURCE_ID,ACCESS_CONTROL_MODEL,PARAM1,PARAM2,PARAM3,PARAM4,PARAM5) values ('USR_SEC_DOMAIN','USER SECURITY DOMAIN','ON-LINE',null,null,null,null,null,null,null,null,null,null,null,null,null,null);
REM INSERTING into IAMUSER.SERVICE_CONFIG
SET DEFINE OFF;
REM INSERTING into IAMUSER.STATUS
SET DEFINE OFF;
Insert into IAMUSER.STATUS (STATUS_CD,STATUS_TYPE,DESCRIPTION,CODE_GROUP,LANGUAGE_CD,COMPANY_OWNER_ID,SERVICE_ID,WEIGHT) values ('MANAGER','String','MANAGER','JOB_CODE','en','100','USR_SEC_DOMAIN',null);
Insert into IAMUSER.STATUS (STATUS_CD,STATUS_TYPE,DESCRIPTION,CODE_GROUP,LANGUAGE_CD,COMPANY_OWNER_ID,SERVICE_ID,WEIGHT) values ('SALES REP','String','SALES REP','JOB_CODE','en','100','USR_SEC_DOMAIN',null);
Insert into IAMUSER.STATUS (STATUS_CD,STATUS_TYPE,DESCRIPTION,CODE_GROUP,LANGUAGE_CD,COMPANY_OWNER_ID,SERVICE_ID,WEIGHT) values ('SECURITY MANAGER','String','SECURITY MANAGER','JOB_CODE','en','100','USR_SEC_DOMAIN',null);
Insert into IAMUSER.STATUS (STATUS_CD,STATUS_TYPE,DESCRIPTION,CODE_GROUP,LANGUAGE_CD,COMPANY_OWNER_ID,SERVICE_ID,WEIGHT) values ('SERVICE REP','String','SERVICE REP','JOB_CODE','en','100','USR_SEC_DOMAIN',null);
Insert into IAMUSER.STATUS (STATUS_CD,STATUS_TYPE,DESCRIPTION,CODE_GROUP,LANGUAGE_CD,COMPANY_OWNER_ID,SERVICE_ID,WEIGHT) values ('ACCNT','String','Account','OBJECT_TYPE','en','100','IDM',null);
Insert into IAMUSER.STATUS (STATUS_CD,STATUS_TYPE,DESCRIPTION,CODE_GROUP,LANGUAGE_CD,COMPANY_OWNER_ID,SERVICE_ID,WEIGHT) values ('ADMGP','String','Admin Group','OBJECT_TYPE','en','100','IDM',null);
Insert into IAMUSER.STATUS (STATUS_CD,STATUS_TYPE,DESCRIPTION,CODE_GROUP,LANGUAGE_CD,COMPANY_OWNER_ID,SERVICE_ID,WEIGHT) values ('ADMIN','String','Administrator','OBJECT_TYPE','en','100','IDM',null);
Insert into IAMUSER.STATUS (STATUS_CD,STATUS_TYPE,DESCRIPTION,CODE_GROUP,LANGUAGE_CD,COMPANY_OWNER_ID,SERVICE_ID,WEIGHT) values ('ATTR','String','Attribute','OBJECT_TYPE','en','100','IDM',null);
Insert into IAMUSER.STATUS (STATUS_CD,STATUS_TYPE,DESCRIPTION,CODE_GROUP,LANGUAGE_CD,COMPANY_OWNER_ID,SERVICE_ID,WEIGHT) values ('BL','String','DISABLE','OPERATION','en','100','IDM',null);
Insert into IAMUSER.STATUS (STATUS_CD,STATUS_TYPE,DESCRIPTION,CODE_GROUP,LANGUAGE_CD,COMPANY_OWNER_ID,SERVICE_ID,WEIGHT) values ('DL','String','DELETE','OPERATION','en','100','IDM',null);
Insert into IAMUSER.STATUS (STATUS_CD,STATUS_TYPE,DESCRIPTION,CODE_GROUP,LANGUAGE_CD,COMPANY_OWNER_ID,SERVICE_ID,WEIGHT) values ('RJ','String','REJECT','OPERATION','en','100','IDM',null);
Insert into IAMUSER.STATUS (STATUS_CD,STATUS_TYPE,DESCRIPTION,CODE_GROUP,LANGUAGE_CD,COMPANY_OWNER_ID,SERVICE_ID,WEIGHT) values ('UB','String','UN-DISABLE','OPERATION','en','100','IDM',null);
Insert into IAMUSER.STATUS (STATUS_CD,STATUS_TYPE,DESCRIPTION,CODE_GROUP,LANGUAGE_CD,COMPANY_OWNER_ID,SERVICE_ID,WEIGHT) values ('OFF-LINE','String','OFF-LINE','SERVICE_STATUS','en','100','IDM',null);
Insert into IAMUSER.STATUS (STATUS_CD,STATUS_TYPE,DESCRIPTION,CODE_GROUP,LANGUAGE_CD,COMPANY_OWNER_ID,SERVICE_ID,WEIGHT) values ('READY','String','READY','SERVICE_STATUS','en','100','IDM',null);
Insert into IAMUSER.STATUS (STATUS_CD,STATUS_TYPE,DESCRIPTION,CODE_GROUP,LANGUAGE_CD,COMPANY_OWNER_ID,SERVICE_ID,WEIGHT) values ('ACTIVE','String','ACTIVE','USER','en','100','IDM',null);
Insert into IAMUSER.STATUS (STATUS_CD,STATUS_TYPE,DESCRIPTION,CODE_GROUP,LANGUAGE_CD,COMPANY_OWNER_ID,SERVICE_ID,WEIGHT) values ('APPROVAL_DECLINED','String','APPROVAL_DECLINED','USER','en','100','IDM',null);
Insert into IAMUSER.STATUS (STATUS_CD,STATUS_TYPE,DESCRIPTION,CODE_GROUP,LANGUAGE_CD,COMPANY_OWNER_ID,SERVICE_ID,WEIGHT) values ('DELETED','String','DELETED','USER','en','100','IDM',null);
Insert into IAMUSER.STATUS (STATUS_CD,STATUS_TYPE,DESCRIPTION,CODE_GROUP,LANGUAGE_CD,COMPANY_OWNER_ID,SERVICE_ID,WEIGHT) values ('INACTIVE','String','INACTIVE','USER','en','100','IDM',null);
Insert into IAMUSER.STATUS (STATUS_CD,STATUS_TYPE,DESCRIPTION,CODE_GROUP,LANGUAGE_CD,COMPANY_OWNER_ID,SERVICE_ID,WEIGHT) values ('LEAVE','String','LEAVE','USER','en','100','IDM',null);
Insert into IAMUSER.STATUS (STATUS_CD,STATUS_TYPE,DESCRIPTION,CODE_GROUP,LANGUAGE_CD,COMPANY_OWNER_ID,SERVICE_ID,WEIGHT) values ('PENDING_APPROVAL','String','PENDING_APPROVAL','USER','en','100','IDM',null);
Insert into IAMUSER.STATUS (STATUS_CD,STATUS_TYPE,DESCRIPTION,CODE_GROUP,LANGUAGE_CD,COMPANY_OWNER_ID,SERVICE_ID,WEIGHT) values ('PENDING_INITIAL_LOGIN','String','PENDING_INITIAL_LOGIN','USER','en','100','IDM',null);
Insert into IAMUSER.STATUS (STATUS_CD,STATUS_TYPE,DESCRIPTION,CODE_GROUP,LANGUAGE_CD,COMPANY_OWNER_ID,SERVICE_ID,WEIGHT) values ('PENDING_START_DATE','String','PENDING_START_DATE','USER','en','100','IDM',null);
Insert into IAMUSER.STATUS (STATUS_CD,STATUS_TYPE,DESCRIPTION,CODE_GROUP,LANGUAGE_CD,COMPANY_OWNER_ID,SERVICE_ID,WEIGHT) values ('PENDING_USER_VALIDATION','String','PENDING_USER_VALIDATION','USER','en','100','IDM',null);
Insert into IAMUSER.STATUS (STATUS_CD,STATUS_TYPE,DESCRIPTION,CODE_GROUP,LANGUAGE_CD,COMPANY_OWNER_ID,SERVICE_ID,WEIGHT) values ('RETIRED','String','RETIRED','USER','en','100','IDM',null);
Insert into IAMUSER.STATUS (STATUS_CD,STATUS_TYPE,DESCRIPTION,CODE_GROUP,LANGUAGE_CD,COMPANY_OWNER_ID,SERVICE_ID,WEIGHT) values ('TERMINATE','String','TERMINATE','USER','en','100','IDM',null);
Insert into IAMUSER.STATUS (STATUS_CD,STATUS_TYPE,DESCRIPTION,CODE_GROUP,LANGUAGE_CD,COMPANY_OWNER_ID,SERVICE_ID,WEIGHT) values ('DISABLED','String','DISABLED','USER_2ND_STATUS','en','100','IDM',null);
Insert into IAMUSER.STATUS (STATUS_CD,STATUS_TYPE,DESCRIPTION,CODE_GROUP,LANGUAGE_CD,COMPANY_OWNER_ID,SERVICE_ID,WEIGHT) values ('LOCKED','String','LOCKED','USER_2ND_STATUS','en','100','IDM',null);
Insert into IAMUSER.STATUS (STATUS_CD,STATUS_TYPE,DESCRIPTION,CODE_GROUP,LANGUAGE_CD,COMPANY_OWNER_ID,SERVICE_ID,WEIGHT) values ('LOCKED_ADMIN','String','LOCKED_ADMIN','USER_2ND_STATUS','en','100','IDM',null);
Insert into IAMUSER.STATUS (STATUS_CD,STATUS_TYPE,DESCRIPTION,CODE_GROUP,LANGUAGE_CD,COMPANY_OWNER_ID,SERVICE_ID,WEIGHT) values ('SYS ACCOUNT','String','SYSTEM ACCOUNT','USER_TYPE','en','100','USR_SEC_DOMAIN',null);
REM INSERTING into IAMUSER.SYNCH_CONFIG
SET DEFINE OFF;
REM INSERTING into IAMUSER.SYNCH_CONFIG_DATA_MAPPING
SET DEFINE OFF;
REM INSERTING into IAMUSER.UI_FIELD_TEMPLATE_XREF
SET DEFINE OFF;
REM INSERTING into IAMUSER.UI_TEMPLATE_FIELDS
SET DEFINE OFF;
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_FIRST_NAME','First Name',null,'USER_TEMPLATE','Y');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_LAST_NAME','Last Name',null,'USER_TEMPLATE','Y');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_MIDDLE_INIT','Middle Initial',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_MAIDEN_NAME','Maiden Name',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_NICKNAME','User NickName',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_DOB','Date of Birth',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_TITLE','User Title',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_GENDER','Gender',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_EMPLOYEE_ID','Employee ID',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_EMPLOYEE_TYPE','Employee Type',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_LOCATION_CODE','Location Code',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_LOCATION_NAME','Location Name',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_SELECT_SUPERVISOR','Select Supervisor',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_CLASSICIATION','User Classification',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_PREFIX','Prefix',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_STATUS','User Status',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_SECONDARY_STATUS','Account Status',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_SUFFIX','User Suffix',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_ALTERNATE_CONTACT','Alternate Contact',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_MAILCODE','Mail Code',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_COST_CENTER','Cost Center',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_JOB_CODE','Job Code',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_USER_TYPE','User Type',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_OBJECT_TYPE','User Type',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_START_DATE','Start Date',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_END_DATE','End Date',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_ADDRESS_REQUIRED','User is required to input at least one address',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_PHONES_REQUIRED','User is required to input at least one phone number',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_PHONES_CREATABLE','User can input phone numbers',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_ADDRESSES_CREATABLE','User can input addresses',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_SHOW_ROLES','User can select a Role',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_SHOW_GROUPS','User can select a Group',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_SHOW_ORGANIZATIONS','User can define his organizational structure',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_CREATE_LOGIN','User can create a custom login',null,'USER_TEMPLATE','Y');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_EMAIL_CREATABLE','User can create email addresses',null,'USER_TEMPLATE','N');
Insert into IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID,NAME,DESCRIPTION,TEMPLATE_TYPE_ID,IS_REQUIRED) values ('USER_EMAILS_REQUIRED','User is required to input at least one email address',null,'USER_TEMPLATE','N');
REM INSERTING into IAMUSER.UI_TEMPLATE_TYPE
SET DEFINE OFF;
Insert into IAMUSER.UI_TEMPLATE_TYPE (TEMPLATE_TYPE_ID,NAME,DESCRIPTION) values ('USER_TEMPLATE','User Page Tempate','Template for SelfService User-centric pages');
REM INSERTING into IAMUSER.URI_PATTERN
SET DEFINE OFF;
REM INSERTING into IAMUSER.URI_PATTERN_META
SET DEFINE OFF;
REM INSERTING into IAMUSER.URI_PATTERN_META_TYPE
SET DEFINE OFF;
Insert into IAMUSER.URI_PATTERN_META_TYPE (URI_PATTERN_META_TYPE_ID,METADATA_TYPE_NAME,SPRING_BEAN_NAME) values ('cookieURIPatternRule','Set Cookie','cookieURIPatternRule');
Insert into IAMUSER.URI_PATTERN_META_TYPE (URI_PATTERN_META_TYPE_ID,METADATA_TYPE_NAME,SPRING_BEAN_NAME) values ('formPostURIPatternRule','Form Post Processor','formPostURIPatternRule');
Insert into IAMUSER.URI_PATTERN_META_TYPE (URI_PATTERN_META_TYPE_ID,METADATA_TYPE_NAME,SPRING_BEAN_NAME) values ('headerURIPatternRule','Set Header','headerURIPatternRule');
Insert into IAMUSER.URI_PATTERN_META_TYPE (URI_PATTERN_META_TYPE_ID,METADATA_TYPE_NAME,SPRING_BEAN_NAME) values ('requestParamURIPatternRule','Set URI String Parameter','requestParamURIPatternRule');
REM INSERTING into IAMUSER.URI_PATTERN_META_VALUE
SET DEFINE OFF;
REM INSERTING into IAMUSER.USERS
SET DEFINE OFF;
Insert into IAMUSER.USERS (USER_ID,FIRST_NAME,LAST_NAME,MIDDLE_INIT,TYPE_ID,CLASSIFICATION,TITLE,MAIL_CODE,COST_CENTER,STATUS,SECONDARY_STATUS,BIRTHDATE,SEX,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,PREFIX,SUFFIX,USER_TYPE_IND,EMPLOYEE_ID,EMPLOYEE_TYPE,LOCATION_CD,LOCATION_NAME,COMPANY_OWNER_ID,JOB_CODE,ALTERNATE_ID,START_DATE,LAST_DATE,MAIDEN_NAME,NICKNAME,PASSWORD_THEME,SHOW_IN_SEARCH,USER_OWNER_ID,DATE_PASSWORD_CHANGED,DATE_CHALLENGE_RESP_CHANGED,SYSTEM_FLAG,DATE_IT_POLICY_APPROVED) values ('0001','system','system',null,null,null,null,null,null,'ACTIVE',null,null,null,to_date('28-MAR-14','DD-MON-RR'),null,to_date('28-MAR-14','DD-MON-RR'),null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,'1',null);
Insert into IAMUSER.USERS (USER_ID,FIRST_NAME,LAST_NAME,MIDDLE_INIT,TYPE_ID,CLASSIFICATION,TITLE,MAIL_CODE,COST_CENTER,STATUS,SECONDARY_STATUS,BIRTHDATE,SEX,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,PREFIX,SUFFIX,USER_TYPE_IND,EMPLOYEE_ID,EMPLOYEE_TYPE,LOCATION_CD,LOCATION_NAME,COMPANY_OWNER_ID,JOB_CODE,ALTERNATE_ID,START_DATE,LAST_DATE,MAIDEN_NAME,NICKNAME,PASSWORD_THEME,SHOW_IN_SEARCH,USER_OWNER_ID,DATE_PASSWORD_CHANGED,DATE_CHALLENGE_RESP_CHANGED,SYSTEM_FLAG,DATE_IT_POLICY_APPROVED) values ('3000','sys',null,null,null,null,null,null,null,'ACTIVE',null,null,null,to_date('28-MAR-14','DD-MON-RR'),null,to_date('28-MAR-14','DD-MON-RR'),null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
Insert into IAMUSER.USERS (USER_ID,FIRST_NAME,LAST_NAME,MIDDLE_INIT,TYPE_ID,CLASSIFICATION,TITLE,MAIL_CODE,COST_CENTER,STATUS,SECONDARY_STATUS,BIRTHDATE,SEX,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,PREFIX,SUFFIX,USER_TYPE_IND,EMPLOYEE_ID,EMPLOYEE_TYPE,LOCATION_CD,LOCATION_NAME,COMPANY_OWNER_ID,JOB_CODE,ALTERNATE_ID,START_DATE,LAST_DATE,MAIDEN_NAME,NICKNAME,PASSWORD_THEME,SHOW_IN_SEARCH,USER_OWNER_ID,DATE_PASSWORD_CHANGED,DATE_CHALLENGE_RESP_CHANGED,SYSTEM_FLAG,DATE_IT_POLICY_APPROVED) values ('3001','sys2',null,null,null,null,null,null,null,'ACTIVE',null,null,null,to_date('28-MAR-14','DD-MON-RR'),null,to_date('28-MAR-14','DD-MON-RR'),null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
Insert into IAMUSER.USERS (USER_ID,FIRST_NAME,LAST_NAME,MIDDLE_INIT,TYPE_ID,CLASSIFICATION,TITLE,MAIL_CODE,COST_CENTER,STATUS,SECONDARY_STATUS,BIRTHDATE,SEX,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,PREFIX,SUFFIX,USER_TYPE_IND,EMPLOYEE_ID,EMPLOYEE_TYPE,LOCATION_CD,LOCATION_NAME,COMPANY_OWNER_ID,JOB_CODE,ALTERNATE_ID,START_DATE,LAST_DATE,MAIDEN_NAME,NICKNAME,PASSWORD_THEME,SHOW_IN_SEARCH,USER_OWNER_ID,DATE_PASSWORD_CHANGED,DATE_CHALLENGE_RESP_CHANGED,SYSTEM_FLAG,DATE_IT_POLICY_APPROVED) values ('3006','Scott','Nelson',null,null,null,null,null,null,'ACTIVE',null,null,null,to_date('28-MAR-14','DD-MON-RR'),null,to_date('28-MAR-14','DD-MON-RR'),null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
Insert into IAMUSER.USERS (USER_ID,FIRST_NAME,LAST_NAME,MIDDLE_INIT,TYPE_ID,CLASSIFICATION,TITLE,MAIL_CODE,COST_CENTER,STATUS,SECONDARY_STATUS,BIRTHDATE,SEX,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,PREFIX,SUFFIX,USER_TYPE_IND,EMPLOYEE_ID,EMPLOYEE_TYPE,LOCATION_CD,LOCATION_NAME,COMPANY_OWNER_ID,JOB_CODE,ALTERNATE_ID,START_DATE,LAST_DATE,MAIDEN_NAME,NICKNAME,PASSWORD_THEME,SHOW_IN_SEARCH,USER_OWNER_ID,DATE_PASSWORD_CHANGED,DATE_CHALLENGE_RESP_CHANGED,SYSTEM_FLAG,DATE_IT_POLICY_APPROVED) values ('3007','HR','User',null,null,null,null,null,null,'ACTIVE',null,null,null,to_date('28-MAR-14','DD-MON-RR'),null,to_date('28-MAR-14','DD-MON-RR'),null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
Insert into IAMUSER.USERS (USER_ID,FIRST_NAME,LAST_NAME,MIDDLE_INIT,TYPE_ID,CLASSIFICATION,TITLE,MAIL_CODE,COST_CENTER,STATUS,SECONDARY_STATUS,BIRTHDATE,SEX,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,PREFIX,SUFFIX,USER_TYPE_IND,EMPLOYEE_ID,EMPLOYEE_TYPE,LOCATION_CD,LOCATION_NAME,COMPANY_OWNER_ID,JOB_CODE,ALTERNATE_ID,START_DATE,LAST_DATE,MAIDEN_NAME,NICKNAME,PASSWORD_THEME,SHOW_IN_SEARCH,USER_OWNER_ID,DATE_PASSWORD_CHANGED,DATE_CHALLENGE_RESP_CHANGED,SYSTEM_FLAG,DATE_IT_POLICY_APPROVED) values ('3008','Hiring','Manager',null,null,null,null,null,null,'ACTIVE',null,null,null,to_date('28-MAR-14','DD-MON-RR'),null,to_date('28-MAR-14','DD-MON-RR'),null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
Insert into IAMUSER.USERS (USER_ID,FIRST_NAME,LAST_NAME,MIDDLE_INIT,TYPE_ID,CLASSIFICATION,TITLE,MAIL_CODE,COST_CENTER,STATUS,SECONDARY_STATUS,BIRTHDATE,SEX,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,PREFIX,SUFFIX,USER_TYPE_IND,EMPLOYEE_ID,EMPLOYEE_TYPE,LOCATION_CD,LOCATION_NAME,COMPANY_OWNER_ID,JOB_CODE,ALTERNATE_ID,START_DATE,LAST_DATE,MAIDEN_NAME,NICKNAME,PASSWORD_THEME,SHOW_IN_SEARCH,USER_OWNER_ID,DATE_PASSWORD_CHANGED,DATE_CHALLENGE_RESP_CHANGED,SYSTEM_FLAG,DATE_IT_POLICY_APPROVED) values ('3009','Security','Manager',null,null,null,null,null,null,'ACTIVE',null,null,null,to_date('28-MAR-14','DD-MON-RR'),null,to_date('28-MAR-14','DD-MON-RR'),null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
Insert into IAMUSER.USERS (USER_ID,FIRST_NAME,LAST_NAME,MIDDLE_INIT,TYPE_ID,CLASSIFICATION,TITLE,MAIL_CODE,COST_CENTER,STATUS,SECONDARY_STATUS,BIRTHDATE,SEX,CREATE_DATE,CREATED_BY,LAST_UPDATE,LAST_UPDATED_BY,PREFIX,SUFFIX,USER_TYPE_IND,EMPLOYEE_ID,EMPLOYEE_TYPE,LOCATION_CD,LOCATION_NAME,COMPANY_OWNER_ID,JOB_CODE,ALTERNATE_ID,START_DATE,LAST_DATE,MAIDEN_NAME,NICKNAME,PASSWORD_THEME,SHOW_IN_SEARCH,USER_OWNER_ID,DATE_PASSWORD_CHANGED,DATE_CHALLENGE_RESP_CHANGED,SYSTEM_FLAG,DATE_IT_POLICY_APPROVED) values ('3010','Help','Desk',null,null,null,null,null,null,'ACTIVE',null,null,null,to_date('28-MAR-14','DD-MON-RR'),null,to_date('28-MAR-14','DD-MON-RR'),null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
REM INSERTING into IAMUSER.USER_AFFILIATION
SET DEFINE OFF;
Insert into IAMUSER.USER_AFFILIATION (COMPANY_ID,USER_ID,CREATE_DATE) values ('100','0001',null);
Insert into IAMUSER.USER_AFFILIATION (COMPANY_ID,USER_ID,CREATE_DATE) values ('100','3000',null);
Insert into IAMUSER.USER_AFFILIATION (COMPANY_ID,USER_ID,CREATE_DATE) values ('100','3001',null);
Insert into IAMUSER.USER_AFFILIATION (COMPANY_ID,USER_ID,CREATE_DATE) values ('100','3006',null);
Insert into IAMUSER.USER_AFFILIATION (COMPANY_ID,USER_ID,CREATE_DATE) values ('100','3007',null);
Insert into IAMUSER.USER_AFFILIATION (COMPANY_ID,USER_ID,CREATE_DATE) values ('100','3008',null);
Insert into IAMUSER.USER_AFFILIATION (COMPANY_ID,USER_ID,CREATE_DATE) values ('100','3009',null);
Insert into IAMUSER.USER_AFFILIATION (COMPANY_ID,USER_ID,CREATE_DATE) values ('100','3010',null);
REM INSERTING into IAMUSER.USER_ATTACHMENT_REF
SET DEFINE OFF;
REM INSERTING into IAMUSER.USER_ATTRIBUTES
SET DEFINE OFF;
REM INSERTING into IAMUSER.USER_DELEGATION_ATTRIBUTE
SET DEFINE OFF;
REM INSERTING into IAMUSER.USER_GRP
SET DEFINE OFF;
Insert into IAMUSER.USER_GRP (GRP_ID,USER_ID) values ('SUPER_SEC_ADMIN_GRP','3000');
Insert into IAMUSER.USER_GRP (GRP_ID,USER_ID) values ('SUPER_SEC_ADMIN_GRP','3001');
Insert into IAMUSER.USER_GRP (GRP_ID,USER_ID) values ('END_USER_GRP','3006');
Insert into IAMUSER.USER_GRP (GRP_ID,USER_ID) values ('HR_GRP','3007');
Insert into IAMUSER.USER_GRP (GRP_ID,USER_ID) values ('MNGR_GRP','3008');
Insert into IAMUSER.USER_GRP (GRP_ID,USER_ID) values ('SECURITY_GRP','3009');
REM INSERTING into IAMUSER.USER_IDENTITY_ANS
SET DEFINE OFF;
REM INSERTING into IAMUSER.USER_KEY
SET DEFINE OFF;
REM INSERTING into IAMUSER.USER_NOTE
SET DEFINE OFF;
REM INSERTING into IAMUSER.USER_ROLE
SET DEFINE OFF;
Insert into IAMUSER.USER_ROLE (USER_ID,ROLE_ID) values ('3010','2');
REM INSERTING into IAMUSER.WEB_RESOURCE_ATTRIBUTE
SET DEFINE OFF;
--------------------------------------------------------
--  DDL for Index FK_PROVIDER_ATTRIBUTE
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_PROVIDER_ATTRIBUTE ON IAMUSER.AUTH_PROVIDER_ATTRIBUTE (AUTH_ATTRIBUTE_ID);
--------------------------------------------------------
--  DDL for Index LANGUAGE_LOCALE_UNIQUE
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.LANGUAGE_LOCALE_UNIQUE ON IAMUSER.LANGUAGE_LOCALE (LOCALE);
--------------------------------------------------------
--  DDL for Index USER_GRP_UNIQUE_RECORD
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.USER_GRP_UNIQUE_RECORD ON IAMUSER.USER_GRP (USER_ID, GRP_ID);
--------------------------------------------------------
--  DDL for Index PAGE_TEMPLATE_UNIQUE
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.PAGE_TEMPLATE_UNIQUE ON IAMUSER.METADATA_ELEMENT_PAGE_TEMPLATE (NAME);
--------------------------------------------------------
--  DDL for Index FK_USER_KEY_USER_ID
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_USER_KEY_USER_ID ON IAMUSER.USER_KEY (USER_ID);
--------------------------------------------------------
--  DDL for Index LANGUAGE_LOCALE_LANGUAGE_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.LANGUAGE_LOCALE_LANGUAGE_FK ON IAMUSER.LANGUAGE_LOCALE (LANGUAGE_ID);
--------------------------------------------------------
--  DDL for Index FK_RESOURCE_RESOURCE_TYPE
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_RESOURCE_RESOURCE_TYPE ON IAMUSER.RES (RESOURCE_TYPE_ID);
--------------------------------------------------------
--  DDL for Index FK_USR_ORG_AFFL
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_USR_ORG_AFFL ON IAMUSER.USER_AFFILIATION (COMPANY_ID);
--------------------------------------------------------
--  DDL for Index RESOURCE_ID
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.RESOURCE_ID ON IAMUSER.WEB_RESOURCE_ATTRIBUTE (RESOURCE_ID, TARGET_ATTRIBUTE_NAME);
--------------------------------------------------------
--  DDL for Index FK_MNG_AUTH_PROVIDER
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_MNG_AUTH_PROVIDER ON IAMUSER.AUTH_PROVIDER (MANAGED_SYS_ID);
--------------------------------------------------------
--  DDL for Index TEMPLATE_XREF_UI_FIELD_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.TEMPLATE_XREF_UI_FIELD_FK ON IAMUSER.UI_FIELD_TEMPLATE_XREF (UI_FIELD_ID);
--------------------------------------------------------
--  DDL for Index COMPANY_ORG_TYPE_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.COMPANY_ORG_TYPE_FK ON IAMUSER.COMPANY (ORG_TYPE_ID);
--------------------------------------------------------
--  DDL for Index VALID_VALUES_ELEMENT_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.VALID_VALUES_ELEMENT_FK ON IAMUSER.MD_ELEMENT_VALID_VALUES (METADATA_ELEMENT_ID);
--------------------------------------------------------
--  DDL for Index UNIQUE_ORG_TYPE
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.UNIQUE_ORG_TYPE ON IAMUSER.ORGANIZATION_TYPE (NAME);
--------------------------------------------------------
--  DDL for Index FK_ROLE_SERVICE
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_ROLE_SERVICE ON IAMUSER.ROLE (SERVICE_ID);
--------------------------------------------------------
--  DDL for Index EMAIL_ADDRESS_TYPE_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.EMAIL_ADDRESS_TYPE_FK ON IAMUSER.EMAIL_ADDRESS (TYPE_ID);
--------------------------------------------------------
--  DDL for Index REQUEST_USER_PROV_REQUEST
--------------------------------------------------------

  CREATE INDEX IAMUSER.REQUEST_USER_PROV_REQUEST ON IAMUSER.REQUEST_USER (REQUEST_ID);
--------------------------------------------------------
--  DDL for Index RESOURCE_USER_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.RESOURCE_USER_PK ON IAMUSER.RESOURCE_USER (RESOURCE_ID, USER_ID);
--------------------------------------------------------
--  DDL for Index GRP_NAME
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.GRP_NAME ON IAMUSER.GRP (GRP_NAME);
--------------------------------------------------------
--  DDL for Index UNIQUE_LOGIN
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.UNIQUE_LOGIN ON IAMUSER.LOGIN (LOGIN, MANAGED_SYS_ID, SERVICE_ID);
--------------------------------------------------------
--  DDL for Index ADDRESS_USER_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.ADDRESS_USER_FK ON IAMUSER.ADDRESS (PARENT_ID);
--------------------------------------------------------
--  DDL for Index URI_META_TYPE_NAME_UNIQUE
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.URI_META_TYPE_NAME_UNIQUE ON IAMUSER.URI_PATTERN_META_TYPE (METADATA_TYPE_NAME);
--------------------------------------------------------
--  DDL for Index GRP_ATTR_META_ELMT_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.GRP_ATTR_META_ELMT_FK ON IAMUSER.GRP_ATTRIBUTES (METADATA_ID);
--------------------------------------------------------
--  DDL for Index FK_CATEGORY_TYPE_CATEGORY
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_CATEGORY_TYPE_CATEGORY ON IAMUSER.CATEGORY_TYPE (CATEGORY_ID);
--------------------------------------------------------
--  DDL for Index UNIQUE_AUTH_LEVEL_LEVEL
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.UNIQUE_AUTH_LEVEL_LEVEL ON IAMUSER.AUTH_LEVEL (AUTH_LEVEL_DIG);
--------------------------------------------------------
--  DDL for Index URI_PATTERN_UNIQUE
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.URI_PATTERN_UNIQUE ON IAMUSER.URI_PATTERN (CONTENT_PROVIDER_ID, PATTERN);
--------------------------------------------------------
--  DDL for Index RS_PL_RS_RSID
--------------------------------------------------------

  CREATE INDEX IAMUSER.RS_PL_RS_RSID ON IAMUSER.RESOURCE_POLICY (RESOURCE_ID);
--------------------------------------------------------
--  DDL for Index FK_MNG_SYS_PROV_CON
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_MNG_SYS_PROV_CON ON IAMUSER.MANAGED_SYS (CONNECTOR_ID);
--------------------------------------------------------
--  DDL for Index COMPANY_COMPANY_PARENT
--------------------------------------------------------

  CREATE INDEX IAMUSER.COMPANY_COMPANY_PARENT ON IAMUSER.COMPANY_TO_COMPANY_MEMBERSHIP (MEMBER_COMPANY_ID);
--------------------------------------------------------
--  DDL for Index URI_PATTERN_META_VALUE_META_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.URI_PATTERN_META_VALUE_META_FK ON IAMUSER.URI_PATTERN_META_VALUE (URI_PATTERN_META_ID);
--------------------------------------------------------
--  DDL for Index FK_LOGIN_SERVICE
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_LOGIN_SERVICE ON IAMUSER.LOGIN (SERVICE_ID);
--------------------------------------------------------
--  DDL for Index PHONE_USER_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.PHONE_USER_FK ON IAMUSER.PHONE (AREA_CD, PARENT_ID);
--------------------------------------------------------
--  DDL for Index URI_PATTERN_META_VALUE_AM_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.URI_PATTERN_META_VALUE_AM_FK ON IAMUSER.URI_PATTERN_META_VALUE (AM_RES_ATTRIBUTE_ID);
--------------------------------------------------------
--  DDL for Index FK_PROVIDER_PROVIDER_ATTRIBUTE
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_PROVIDER_PROVIDER_ATTRIBUTE ON IAMUSER.AUTH_PROVIDER_ATTRIBUTE (PROVIDER_ID);
--------------------------------------------------------
--  DDL for Index TEMPLATE_XREF_UNIQUE
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.TEMPLATE_XREF_UNIQUE ON IAMUSER.UI_FIELD_TEMPLATE_XREF (TEMPLATE_ID, UI_FIELD_ID);
--------------------------------------------------------
--  DDL for Index LOGIN_ID_PWD_HIST_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.LOGIN_ID_PWD_HIST_FK ON IAMUSER.PWD_HISTORY (LOGIN_ID);
--------------------------------------------------------
--  DDL for Index MEMBER_RESOURCE_ID
--------------------------------------------------------

  CREATE INDEX IAMUSER.MEMBER_RESOURCE_ID ON IAMUSER.RES_TO_RES_MEMBERSHIP (MEMBER_RESOURCE_ID);
--------------------------------------------------------
--  DDL for Index URI_PATTERN_AUTH_LEVEL_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.URI_PATTERN_AUTH_LEVEL_FK ON IAMUSER.URI_PATTERN (MIN_AUTH_LEVEL);
--------------------------------------------------------
--  DDL for Index FK_USR_ROLE_USR
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_USR_ROLE_USR ON IAMUSER.USER_ROLE (USER_ID);
--------------------------------------------------------
--  DDL for Index FK_COMPANY_ATTRIBUTE_COMPANY
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_COMPANY_ATTRIBUTE_COMPANY ON IAMUSER.COMPANY_ATTRIBUTE (COMPANY_ID);
--------------------------------------------------------
--  DDL for Index POLI_ATTR_POL_DEF_PARAM
--------------------------------------------------------

  CREATE INDEX IAMUSER.POLI_ATTR_POL_DEF_PARAM ON IAMUSER.POLICY_ATTRIBUTE (DEF_PARAM_ID);
--------------------------------------------------------
--  DDL for Index URI_PATTERN_RES_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.URI_PATTERN_RES_FK ON IAMUSER.URI_PATTERN (RESOURCE_ID);
--------------------------------------------------------
--  DDL for Index ORG_T_VAL_MEM_TARGET_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.ORG_T_VAL_MEM_TARGET_FK ON IAMUSER.ORG_TYPE_VALID_MEMBERSHIP (MEMBER_ORG_TYPE_ID);
--------------------------------------------------------
--  DDL for Index FK_USR_USER_AFFIL
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_USR_USER_AFFIL ON IAMUSER.USER_AFFILIATION (USER_ID);
--------------------------------------------------------
--  DDL for Index UNIQUE_RESOURCE_GROUP
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.UNIQUE_RESOURCE_GROUP ON IAMUSER.RESOURCE_GROUP (GRP_ID, RESOURCE_ID);
--------------------------------------------------------
--  DDL for Index FK_USER_IDENTITY_ANS_USERS
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_USER_IDENTITY_ANS_USERS ON IAMUSER.USER_IDENTITY_ANS (USER_ID);
--------------------------------------------------------
--  DDL for Index OPENIAM_LOG_TARGET_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.OPENIAM_LOG_TARGET_PK ON IAMUSER.OPENIAM_LOG_TARGET (OPENIAM_LOG_TARGET_ID);
--------------------------------------------------------
--  DDL for Index URI_PATTERN_META_META_TYPE_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.URI_PATTERN_META_META_TYPE_FK ON IAMUSER.URI_PATTERN_META (URI_PATTERN_META_TYPE_ID);
--------------------------------------------------------
--  DDL for Index LAST_NAME
--------------------------------------------------------

  CREATE INDEX IAMUSER.LAST_NAME ON IAMUSER.USERS (LAST_NAME);
--------------------------------------------------------
--  DDL for Index FK_RESOURCE_PRIVILEGE_RES
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_RESOURCE_PRIVILEGE_RES ON IAMUSER.RESOURCE_PRIVILEGE (RESOURCE_ID);
--------------------------------------------------------
--  DDL for Index TEMPLATE_XREF_TEMPLATE_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.TEMPLATE_XREF_TEMPLATE_FK ON IAMUSER.UI_FIELD_TEMPLATE_XREF (TEMPLATE_ID);
--------------------------------------------------------
--  DDL for Index MEMBER_GROUP_ID
--------------------------------------------------------

  CREATE INDEX IAMUSER.MEMBER_GROUP_ID ON IAMUSER.GRP_TO_GRP_MEMBERSHIP (MEMBER_GROUP_ID);
--------------------------------------------------------
--  DDL for Index CP_RES_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.CP_RES_FK ON IAMUSER.CONTENT_PROVIDER (RESOURCE_ID);
--------------------------------------------------------
--  DDL for Index FK_MNG_MNG_ATTR
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_MNG_MNG_ATTR ON IAMUSER.MANAGED_SYS_ATTRIBUTE (MANAGED_SYS_ID);
--------------------------------------------------------
--  DDL for Index UNIQUE_CP_NAME
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.UNIQUE_CP_NAME ON IAMUSER.CONTENT_PROVIDER (CONTENT_PROVIDER_NAME);
--------------------------------------------------------
--  DDL for Index LANGUAGE_MAPPING_UNIQUE
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.LANGUAGE_MAPPING_UNIQUE ON IAMUSER.LANGUAGE_MAPPING (LANGUAGE_ID, REFERENCE_ID, REFERENCE_TYPE);
--------------------------------------------------------
--  DDL for Index ORG_T_VAL_MEM_SOURCE_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.ORG_T_VAL_MEM_SOURCE_FK ON IAMUSER.ORG_TYPE_VALID_MEMBERSHIP (ORG_TYPE_ID);
--------------------------------------------------------
--  DDL for Index UNIQUE_AUTH_LEVEL_NAME
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.UNIQUE_AUTH_LEVEL_NAME ON IAMUSER.AUTH_LEVEL (AUTH_LEVEL_NAME);
--------------------------------------------------------
--  DDL for Index FK_USERS_METADATA_TYPE
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_USERS_METADATA_TYPE ON IAMUSER.USERS (TYPE_ID);
--------------------------------------------------------
--  DDL for Index USER_ID
--------------------------------------------------------

  CREATE INDEX IAMUSER.USER_ID ON IAMUSER.USER_ATTRIBUTES (USER_ID);
--------------------------------------------------------
--  DDL for Index RECON_SITUATION
--------------------------------------------------------

  CREATE INDEX IAMUSER.RECON_SITUATION ON IAMUSER.RECONCILIATION_SITUATION (RECON_CONFIG_ID);
--------------------------------------------------------
--  DDL for Index FK_RELATIONSHIP_RELATION_SET
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_RELATIONSHIP_RELATION_SET ON IAMUSER.RELATIONSHIP (RELATION_SET_ID);
--------------------------------------------------------
--  DDL for Index USER_DELEG_USER_ID
--------------------------------------------------------

  CREATE INDEX IAMUSER.USER_DELEG_USER_ID ON IAMUSER.USER_DELEGATION_ATTRIBUTE (USER_ID);
--------------------------------------------------------
--  DDL for Index REFPROV_REQUEST1001
--------------------------------------------------------

  CREATE INDEX IAMUSER.REFPROV_REQUEST1001 ON IAMUSER.REQUEST_ATTACHMENT (REQUEST_ID);
--------------------------------------------------------
--  DDL for Index REFPROV_REQUEST951
--------------------------------------------------------

  CREATE INDEX IAMUSER.REFPROV_REQUEST951 ON IAMUSER.MNG_SYS_LIST (REQUEST_ID);
--------------------------------------------------------
--  DDL for Index FK_RELAT_SOURCE_RELATION_SET
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_RELAT_SOURCE_RELATION_SET ON IAMUSER.RELATION_SOURCE (RELATION_SET_ID);
--------------------------------------------------------
--  DDL for Index FK_PROVIDER_TYPE_PROVIDER
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_PROVIDER_TYPE_PROVIDER ON IAMUSER.AUTH_PROVIDER (PROVIDER_TYPE);
--------------------------------------------------------
--  DDL for Index FK_PROVIDER_AUTH_RES_ATTR_MAP
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_PROVIDER_AUTH_RES_ATTR_MAP ON IAMUSER.AUTH_RESOURCE_ATTRIBUTE_MAP (PROVIDER_ID);
--------------------------------------------------------
--  DDL for Index IDX_ATTRIBUTE_PT
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.IDX_ATTRIBUTE_PT ON IAMUSER.AUTH_ATTRIBUTE (ATTRIBUTE_NAME, PROVIDER_TYPE);
--------------------------------------------------------
--  DDL for Index FK_AUTH_AM_ATTR
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_AUTH_AM_ATTR ON IAMUSER.AUTH_RESOURCE_ATTRIBUTE_MAP (AM_RES_ATTRIBUTE_ID);
--------------------------------------------------------
--  DDL for Index FK_POLICY_DEF_PARAM_POLICY_DEF
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_POLICY_DEF_PARAM_POLICY_DEF ON IAMUSER.POLICY_DEF_PARAM (POLICY_DEF_ID);
--------------------------------------------------------
--  DDL for Index REPORT_NAME
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.REPORT_NAME ON IAMUSER.REPORT_INFO (REPORT_NAME);
--------------------------------------------------------
--  DDL for Index FK_POLICY_ATTRIBUTE_POLICY
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_POLICY_ATTRIBUTE_POLICY ON IAMUSER.POLICY_ATTRIBUTE (POLICY_ID);
--------------------------------------------------------
--  DDL for Index CP_AUTH_LEVEL_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.CP_AUTH_LEVEL_FK ON IAMUSER.CONTENT_PROVIDER (MIN_AUTH_LEVEL);
--------------------------------------------------------
--  DDL for Index FK_USER_NOTE_USERS
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_USER_NOTE_USERS ON IAMUSER.USER_NOTE (USER_ID);
--------------------------------------------------------
--  DDL for Index UNIQUE_CP_SERVER
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.UNIQUE_CP_SERVER ON IAMUSER.CONTENT_PROVIDER_SERVER (CONTENT_PROVIDER_ID, SERVER_URL);
--------------------------------------------------------
--  DDL for Index REFLECTION_KEY_IDX
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.REFLECTION_KEY_IDX ON IAMUSER.AUTH_RESOURCE_AM_ATTRIBUTE (REFLECTION_KEY);
--------------------------------------------------------
--  DDL for Index USER_EMPLOYEE_INDX
--------------------------------------------------------

  CREATE INDEX IAMUSER.USER_EMPLOYEE_INDX ON IAMUSER.USERS (EMPLOYEE_ID);
--------------------------------------------------------
--  DDL for Index FK_EXCLUDE_WORD_LIST_LANGUAGE
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_EXCLUDE_WORD_LIST_LANGUAGE ON IAMUSER.EXCLUDE_WORD_LIST (LANGUAGE_ID);
--------------------------------------------------------
--  DDL for Index LOGIN_ID_LOGIN_ATTR_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.LOGIN_ID_LOGIN_ATTR_FK ON IAMUSER.LOGIN_ATTRIBUTE (LOGIN_ID);
--------------------------------------------------------
--  DDL for Index FK_USER_ATTACHMENT_REF_USERS
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_USER_ATTACHMENT_REF_USERS ON IAMUSER.USER_ATTACHMENT_REF (USER_ID);
--------------------------------------------------------
--  DDL for Index FK_RESOURCE_POLICY_ROLE
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_RESOURCE_POLICY_ROLE ON IAMUSER.RESOURCE_POLICY (ROLE_ID);
--------------------------------------------------------
--  DDL for Index ROLE_ROLE_MMSP_MEMBER_ROLE_ID
--------------------------------------------------------

  CREATE INDEX IAMUSER.ROLE_ROLE_MMSP_MEMBER_ROLE_ID ON IAMUSER.ROLE_TO_ROLE_MEMBERSHIP (MEMBER_ROLE_ID);
--------------------------------------------------------
--  DDL for Index OPENIAM_LOG_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.OPENIAM_LOG_PK ON IAMUSER.OPENIAM_LOG (OPENIAM_LOG_ID);
--------------------------------------------------------
--  DDL for Index REQUEST_ATTRIBUTE_PROV_REQUEST
--------------------------------------------------------

  CREATE INDEX IAMUSER.REQUEST_ATTRIBUTE_PROV_REQUEST ON IAMUSER.REQUEST_ATTRIBUTE (REQUEST_ID);
--------------------------------------------------------
--  DDL for Index IDX_PROVIDER_ATTRIBUTE
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.IDX_PROVIDER_ATTRIBUTE ON IAMUSER.AUTH_PROVIDER_ATTRIBUTE (PROVIDER_ID, AUTH_ATTRIBUTE_ID);
--------------------------------------------------------
--  DDL for Index METADATA_PAGE_TEMPLATE_RES_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.METADATA_PAGE_TEMPLATE_RES_FK ON IAMUSER.METADATA_ELEMENT_PAGE_TEMPLATE (RESOURCE_ID);
--------------------------------------------------------
--  DDL for Index FK_PROVIDER_TYPE_ATTRIBUTE
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_PROVIDER_TYPE_ATTRIBUTE ON IAMUSER.AUTH_ATTRIBUTE (PROVIDER_TYPE);
--------------------------------------------------------
--  DDL for Index ADDRESS_TYPE_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.ADDRESS_TYPE_FK ON IAMUSER.ADDRESS (TYPE_ID);
--------------------------------------------------------
--  DDL for Index FK_MNG_SYS_OBJ_MATC
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_MNG_SYS_OBJ_MATC ON IAMUSER.MNG_SYS_OBJECT_MATCH (MANAGED_SYS_ID);
--------------------------------------------------------
--  DDL for Index SYNCH_DATA_MAP
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.SYNCH_DATA_MAP ON IAMUSER.SYNCH_CONFIG_DATA_MAPPING (SYNCH_CONFIG_ID);
--------------------------------------------------------
--  DDL for Index ROLE_NAME
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.ROLE_NAME ON IAMUSER.ROLE (ROLE_NAME);
--------------------------------------------------------
--  DDL for Index FK_SRV_SRV_CONF
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_SRV_SRV_CONF ON IAMUSER.SERVICE_CONFIG (SERVICE_ID);
--------------------------------------------------------
--  DDL for Index FK_SUPR_USER
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_SUPR_USER ON IAMUSER.ORG_STRUCTURE (SUPERVISOR_ID);
--------------------------------------------------------
--  DDL for Index TEMPLETE_PATTERN_XREF_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.TEMPLETE_PATTERN_XREF_FK ON IAMUSER.METADATA_URI_XREF (TEMPLATE_ID);
--------------------------------------------------------
--  DDL for Index TEMPLATE_TYPE_UI_TEMPL_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.TEMPLATE_TYPE_UI_TEMPL_FK ON IAMUSER.UI_TEMPLATE_FIELDS (TEMPLATE_TYPE_ID);
--------------------------------------------------------
--  DDL for Index URI_PATTERN_META_URI_PATRN_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.URI_PATTERN_META_URI_PATRN_FK ON IAMUSER.URI_PATTERN_META (URI_PATTERN_ID);
--------------------------------------------------------
--  DDL for Index FK_GRP_ROLE_ROLE
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_GRP_ROLE_ROLE ON IAMUSER.GRP_ROLE (ROLE_ID);
--------------------------------------------------------
--  DDL for Index FK_RES_AUTH_PROVIDER
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_RES_AUTH_PROVIDER ON IAMUSER.AUTH_PROVIDER (RESOURCE_ID);
--------------------------------------------------------
--  DDL for Index FK_CATEGORY_LANGUAGE_LANGUAGE
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_CATEGORY_LANGUAGE_LANGUAGE ON IAMUSER.CATEGORY_LANGUAGE (LANGUAGE_ID);
--------------------------------------------------------
--  DDL for Index LOGIN_USER_INDX
--------------------------------------------------------

  CREATE INDEX IAMUSER.LOGIN_USER_INDX ON IAMUSER.LOGIN (USER_ID);
--------------------------------------------------------
--  DDL for Index TEMPLATE_TYPE_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.TEMPLATE_TYPE_FK ON IAMUSER.METADATA_ELEMENT_PAGE_TEMPLATE (TEMPLATE_TYPE_ID);
--------------------------------------------------------
--  DDL for Index FK_RESOURCE_GRP_RESOURCE
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_RESOURCE_GRP_RESOURCE ON IAMUSER.RESOURCE_GROUP (RESOURCE_ID);
--------------------------------------------------------
--  DDL for Index UNIQUE_USER_ORG_TYPE
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.UNIQUE_USER_ORG_TYPE ON IAMUSER.ORG_TYPE_VALID_MEMBERSHIP (ORG_TYPE_ID, MEMBER_ORG_TYPE_ID);
--------------------------------------------------------
--  DDL for Index FK_RELATION_CATEGORY_CATEGORY
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_RELATION_CATEGORY_CATEGORY ON IAMUSER.RELATION_CATEGORY (CATEGORY_ID);
--------------------------------------------------------
--  DDL for Index USER_AFFILIATION_UNIQUE
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.USER_AFFILIATION_UNIQUE ON IAMUSER.USER_AFFILIATION (USER_ID, COMPANY_ID);
--------------------------------------------------------
--  DDL for Index TEMPLATE_PATRN_XREF_PATRN_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.TEMPLATE_PATRN_XREF_PATRN_FK ON IAMUSER.METADATA_URI_XREF (URI_PATTERN_ID);
--------------------------------------------------------
--  DDL for Index RESOURCE_USER_UNIQUE
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.RESOURCE_USER_UNIQUE ON IAMUSER.RESOURCE_USER (USER_ID, RESOURCE_ID);
--------------------------------------------------------
--  DDL for Index FK_ID_AND_QUEST_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_ID_AND_QUEST_FK ON IAMUSER.USER_IDENTITY_ANS (IDENTITY_QUESTION_ID);
--------------------------------------------------------
--  DDL for Index META_SPRING_BEAN_UNIQUE
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.META_SPRING_BEAN_UNIQUE ON IAMUSER.URI_PATTERN_META_TYPE (SPRING_BEAN_NAME);
--------------------------------------------------------
--  DDL for Index METADATA_ELEMENT_RES_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.METADATA_ELEMENT_RES_FK ON IAMUSER.METADATA_ELEMENT (RESOURCE_ID);
--------------------------------------------------------
--  DDL for Index FK_USR_GRP_GPR
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_USR_GRP_GPR ON IAMUSER.USER_GRP (GRP_ID);
--------------------------------------------------------
--  DDL for Index OPENIAM_LOG_ATTRIBUTE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.OPENIAM_LOG_ATTRIBUTE_PK ON IAMUSER.OPENIAM_LOG_ATTRIBUTE (OPENIAM_LOG_ATTRIBUTE_ID);
--------------------------------------------------------
--  DDL for Index EMAIL_ADDRESS_USER_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.EMAIL_ADDRESS_USER_FK ON IAMUSER.EMAIL_ADDRESS (PARENT_ID);
--------------------------------------------------------
--  DDL for Index AREA_CD
--------------------------------------------------------

  CREATE INDEX IAMUSER.AREA_CD ON IAMUSER.PHONE (AREA_CD, PHONE_NBR);
--------------------------------------------------------
--  DDL for Index FK_ROLE_POLICY_ROLE
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_ROLE_POLICY_ROLE ON IAMUSER.ROLE_POLICY (ROLE_ID);
--------------------------------------------------------
--  DDL for Index PHONE_TYPE_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.PHONE_TYPE_FK ON IAMUSER.PHONE (TYPE_ID);
--------------------------------------------------------
--  DDL for Index NAME
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.NAME ON IAMUSER.RES (NAME);
--------------------------------------------------------
--  DDL for Index FK_POLICY_POLICY_DEF
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_POLICY_POLICY_DEF ON IAMUSER.POLICY (POLICY_DEF_ID);
--------------------------------------------------------
--  DDL for Index FK_RESOURCE_PROP_RESOURCE
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_RESOURCE_PROP_RESOURCE ON IAMUSER.RESOURCE_PROP (RESOURCE_ID);
--------------------------------------------------------
--  DDL for Index FK_STAFF_USER
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_STAFF_USER ON IAMUSER.ORG_STRUCTURE (STAFF_ID);
--------------------------------------------------------
--  DDL for Index GRP_ID
--------------------------------------------------------

  CREATE INDEX IAMUSER.GRP_ID ON IAMUSER.GRP_ATTRIBUTES (GRP_ID);
--------------------------------------------------------
--  DDL for Index ID_QU_IDEN_QUEST_GRP_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.ID_QU_IDEN_QUEST_GRP_FK ON IAMUSER.IDENTITY_QUESTION (IDENTITY_QUEST_GRP_ID);
--------------------------------------------------------
--  DDL for Index FK_COMPANY_METADATA_ELEMENT
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_COMPANY_METADATA_ELEMENT ON IAMUSER.COMPANY_ATTRIBUTE (METADATA_ID);
--------------------------------------------------------
--  DDL for Index METADATA_ELEMENT_TEMPLATE_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.METADATA_ELEMENT_TEMPLATE_FK ON IAMUSER.METADATA_ELEMENT (TEMPLATE_ID);
--------------------------------------------------------
--  DDL for Index UNIQUE_CP_PATTERN
--------------------------------------------------------

  CREATE UNIQUE INDEX IAMUSER.UNIQUE_CP_PATTERN ON IAMUSER.CONTENT_PROVIDER (DOMAIN_PATTERN, IS_SSL);
--------------------------------------------------------
--  DDL for Index FK_RESOURCE_CATEGORY
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_RESOURCE_CATEGORY ON IAMUSER.RES (CATEGORY_ID);
--------------------------------------------------------
--  DDL for Index FK_USR_ATTR_ELMT_FK
--------------------------------------------------------

  CREATE INDEX IAMUSER.FK_USR_ATTR_ELMT_FK ON IAMUSER.USER_ATTRIBUTES (METADATA_ID);
--------------------------------------------------------
--  Constraints for Table REQ_APPROVER
--------------------------------------------------------

  ALTER TABLE IAMUSER.REQ_APPROVER MODIFY (REQ_APPROVER_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.REQ_APPROVER ADD PRIMARY KEY (REQ_APPROVER_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table PAGE_TEMPLATE_XREF
--------------------------------------------------------

  ALTER TABLE IAMUSER.PAGE_TEMPLATE_XREF MODIFY (TEMPLATE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.PAGE_TEMPLATE_XREF MODIFY (METADATA_ELEMENT_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.PAGE_TEMPLATE_XREF MODIFY (DISPLAY_ORDER NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.PAGE_TEMPLATE_XREF ADD PRIMARY KEY (TEMPLATE_ID, METADATA_ELEMENT_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table ORGANIZATION_TYPE
--------------------------------------------------------

  ALTER TABLE IAMUSER.ORGANIZATION_TYPE MODIFY (ORG_TYPE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.ORGANIZATION_TYPE MODIFY (NAME NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.ORGANIZATION_TYPE ADD PRIMARY KEY (ORG_TYPE_ID) ENABLE;
 
  ALTER TABLE IAMUSER.ORGANIZATION_TYPE ADD CONSTRAINT UNIQUE_ORG_TYPE UNIQUE (NAME) ENABLE;
--------------------------------------------------------
--  Constraints for Table REPORT_INFO
--------------------------------------------------------

  ALTER TABLE IAMUSER.REPORT_INFO ADD CONSTRAINT REPORT_NAME UNIQUE (REPORT_NAME) ENABLE;
 
  ALTER TABLE IAMUSER.REPORT_INFO MODIFY (REPORT_INFO_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.REPORT_INFO MODIFY (REPORT_NAME NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.REPORT_INFO MODIFY (DATASOURCE_FILE_PATH NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.REPORT_INFO ADD PRIMARY KEY (REPORT_INFO_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table COMPANY
--------------------------------------------------------

  ALTER TABLE IAMUSER.COMPANY MODIFY (COMPANY_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.COMPANY ADD PRIMARY KEY (COMPANY_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table GRP_ROLE
--------------------------------------------------------

  ALTER TABLE IAMUSER.GRP_ROLE MODIFY (GRP_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.GRP_ROLE MODIFY (ROLE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.GRP_ROLE ADD PRIMARY KEY (GRP_ID, ROLE_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table POLICY_OBJECT_ASSOC
--------------------------------------------------------

  ALTER TABLE IAMUSER.POLICY_OBJECT_ASSOC MODIFY (POLICY_OBJECT_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.POLICY_OBJECT_ASSOC ADD PRIMARY KEY (POLICY_OBJECT_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table ROLE_ATTRIBUTE
--------------------------------------------------------

  ALTER TABLE IAMUSER.ROLE_ATTRIBUTE MODIFY (ROLE_ATTR_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.ROLE_ATTRIBUTE MODIFY (ROLE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.ROLE_ATTRIBUTE ADD PRIMARY KEY (ROLE_ATTR_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table USER_DELEGATION_ATTRIBUTE
--------------------------------------------------------

  ALTER TABLE IAMUSER.USER_DELEGATION_ATTRIBUTE MODIFY (ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.USER_DELEGATION_ATTRIBUTE ADD PRIMARY KEY (ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table MNG_SYS_OBJECT_MATCH
--------------------------------------------------------

  ALTER TABLE IAMUSER.MNG_SYS_OBJECT_MATCH MODIFY (OBJECT_SEARCH_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.MNG_SYS_OBJECT_MATCH MODIFY (MANAGED_SYS_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.MNG_SYS_OBJECT_MATCH ADD PRIMARY KEY (OBJECT_SEARCH_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table IMAGE
--------------------------------------------------------

  ALTER TABLE IAMUSER.IMAGE MODIFY (IMAGE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.IMAGE ADD PRIMARY KEY (IMAGE_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table METADATA_ELEMENT_PAGE_TEMPLATE
--------------------------------------------------------

  ALTER TABLE IAMUSER.METADATA_ELEMENT_PAGE_TEMPLATE MODIFY (ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.METADATA_ELEMENT_PAGE_TEMPLATE MODIFY (NAME NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.METADATA_ELEMENT_PAGE_TEMPLATE MODIFY (RESOURCE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.METADATA_ELEMENT_PAGE_TEMPLATE MODIFY (IS_PUBLIC NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.METADATA_ELEMENT_PAGE_TEMPLATE ADD PRIMARY KEY (ID) ENABLE;
 
  ALTER TABLE IAMUSER.METADATA_ELEMENT_PAGE_TEMPLATE MODIFY (TEMPLATE_TYPE_ID NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table STATUS
--------------------------------------------------------

  ALTER TABLE IAMUSER.STATUS MODIFY (STATUS_CD NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.STATUS MODIFY (CODE_GROUP NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.STATUS MODIFY (LANGUAGE_CD NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.STATUS MODIFY (COMPANY_OWNER_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.STATUS MODIFY (SERVICE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.STATUS ADD PRIMARY KEY (CODE_GROUP, STATUS_CD, LANGUAGE_CD) ENABLE;
--------------------------------------------------------
--  Constraints for Table GRP
--------------------------------------------------------

  ALTER TABLE IAMUSER.GRP MODIFY (GRP_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.GRP MODIFY (GRP_NAME NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.GRP ADD PRIMARY KEY (GRP_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table LANGUAGE_LOCALE
--------------------------------------------------------

  ALTER TABLE IAMUSER.LANGUAGE_LOCALE MODIFY (ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.LANGUAGE_LOCALE MODIFY (LANGUAGE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.LANGUAGE_LOCALE MODIFY (LOCALE NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CATEGORY_TYPE
--------------------------------------------------------

  ALTER TABLE IAMUSER.CATEGORY_TYPE MODIFY (CATEGORY_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.CATEGORY_TYPE MODIFY (TYPE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.CATEGORY_TYPE ADD PRIMARY KEY (TYPE_ID, CATEGORY_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table LANGUAGE
--------------------------------------------------------

  ALTER TABLE IAMUSER.LANGUAGE MODIFY (ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.LANGUAGE MODIFY (IS_USED NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.LANGUAGE MODIFY (LANGUAGE_CODE NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.LANGUAGE ADD PRIMARY KEY (ID) ENABLE;
 
  ALTER TABLE IAMUSER.LANGUAGE MODIFY (IS_DEFAULT NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LOGIN
--------------------------------------------------------

  ALTER TABLE IAMUSER.LOGIN MODIFY (SERVICE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.LOGIN MODIFY (LOGIN NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.LOGIN MODIFY (LOWERCASE_LOGIN NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.LOGIN MODIFY (MANAGED_SYS_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.LOGIN MODIFY (RESET_PWD NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.LOGIN MODIFY (FIRST_TIME_LOGIN NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.LOGIN MODIFY (IS_LOCKED NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.LOGIN MODIFY (AUTH_FAIL_COUNT NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.LOGIN MODIFY (LOGIN_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.LOGIN ADD PRIMARY KEY (LOGIN_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table REPORT_CRITERIA_PARAMETER
--------------------------------------------------------

  ALTER TABLE IAMUSER.REPORT_CRITERIA_PARAMETER MODIFY (RCP_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.REPORT_CRITERIA_PARAMETER MODIFY (REPORT_INFO_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.REPORT_CRITERIA_PARAMETER MODIFY (PARAM_NAME NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.REPORT_CRITERIA_PARAMETER ADD PRIMARY KEY (RCP_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table ROLE_POLICY
--------------------------------------------------------

  ALTER TABLE IAMUSER.ROLE_POLICY MODIFY (ROLE_POLICY_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.ROLE_POLICY MODIFY (ROLE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.ROLE_POLICY ADD PRIMARY KEY (ROLE_POLICY_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table SYNCH_CONFIG_DATA_MAPPING
--------------------------------------------------------

  ALTER TABLE IAMUSER.SYNCH_CONFIG_DATA_MAPPING MODIFY (MAPPING_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.SYNCH_CONFIG_DATA_MAPPING ADD PRIMARY KEY (MAPPING_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table RESOURCE_PROP
--------------------------------------------------------

  ALTER TABLE IAMUSER.RESOURCE_PROP MODIFY (RESOURCE_PROP_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.RESOURCE_PROP MODIFY (NAME NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.RESOURCE_PROP ADD PRIMARY KEY (RESOURCE_PROP_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table EXCLUDE_WORD_LIST
--------------------------------------------------------

  ALTER TABLE IAMUSER.EXCLUDE_WORD_LIST MODIFY (WORD NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.EXCLUDE_WORD_LIST MODIFY (LANGUAGE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.EXCLUDE_WORD_LIST ADD PRIMARY KEY (WORD, LANGUAGE_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table SECURITY_DOMAIN
--------------------------------------------------------

  ALTER TABLE IAMUSER.SECURITY_DOMAIN MODIFY (DOMAIN_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.SECURITY_DOMAIN ADD PRIMARY KEY (DOMAIN_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table AUTH_LEVEL
--------------------------------------------------------

  ALTER TABLE IAMUSER.AUTH_LEVEL MODIFY (AUTH_LEVEL_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.AUTH_LEVEL MODIFY (AUTH_LEVEL_NAME NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.AUTH_LEVEL MODIFY (AUTH_LEVEL_DIG NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.AUTH_LEVEL ADD PRIMARY KEY (AUTH_LEVEL_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table COMPANY_ATTRIBUTE
--------------------------------------------------------

  ALTER TABLE IAMUSER.COMPANY_ATTRIBUTE MODIFY (COMPANY_ATTR_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.COMPANY_ATTRIBUTE MODIFY (COMPANY_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.COMPANY_ATTRIBUTE ADD PRIMARY KEY (COMPANY_ATTR_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table RESOURCE_TYPE
--------------------------------------------------------

  ALTER TABLE IAMUSER.RESOURCE_TYPE MODIFY (RESOURCE_TYPE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.RESOURCE_TYPE ADD PRIMARY KEY (RESOURCE_TYPE_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table RELATION_SET
--------------------------------------------------------

  ALTER TABLE IAMUSER.RELATION_SET MODIFY (RELATION_SET_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.RELATION_SET ADD PRIMARY KEY (RELATION_SET_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table REPORT_SUB_CRITERIA_PARAM
--------------------------------------------------------

  ALTER TABLE IAMUSER.REPORT_SUB_CRITERIA_PARAM MODIFY (RSCP_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.REPORT_SUB_CRITERIA_PARAM ADD PRIMARY KEY (RSCP_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table GRP_TO_GRP_MEMBERSHIP
--------------------------------------------------------

  ALTER TABLE IAMUSER.GRP_TO_GRP_MEMBERSHIP MODIFY (GROUP_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.GRP_TO_GRP_MEMBERSHIP MODIFY (MEMBER_GROUP_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.GRP_TO_GRP_MEMBERSHIP ADD PRIMARY KEY (GROUP_ID, MEMBER_GROUP_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table OPENIAM_LOG_ATTRIBUTE
--------------------------------------------------------

  ALTER TABLE IAMUSER.OPENIAM_LOG_ATTRIBUTE ADD CONSTRAINT OPENIAM_LOG_ATTRIBUTE_PK PRIMARY KEY (OPENIAM_LOG_ATTRIBUTE_ID) ENABLE;
 
  ALTER TABLE IAMUSER.OPENIAM_LOG_ATTRIBUTE MODIFY (OPENIAM_LOG_ATTRIBUTE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.OPENIAM_LOG_ATTRIBUTE MODIFY (OPENIAM_LOG_ID NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table MANAGED_SYS_RULE
--------------------------------------------------------

  ALTER TABLE IAMUSER.MANAGED_SYS_RULE MODIFY (MANAGED_SYS_RULE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.MANAGED_SYS_RULE MODIFY (MANAGED_SYS_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.MANAGED_SYS_RULE ADD PRIMARY KEY (MANAGED_SYS_RULE_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table AUTH_PROVIDER_ATTRIBUTE
--------------------------------------------------------

  ALTER TABLE IAMUSER.AUTH_PROVIDER_ATTRIBUTE MODIFY (PROVIDER_ATTRIBUTE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.AUTH_PROVIDER_ATTRIBUTE MODIFY (PROVIDER_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.AUTH_PROVIDER_ATTRIBUTE MODIFY (AUTH_ATTRIBUTE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.AUTH_PROVIDER_ATTRIBUTE MODIFY (VALUE NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.AUTH_PROVIDER_ATTRIBUTE MODIFY (DATA_TYPE NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.AUTH_PROVIDER_ATTRIBUTE ADD PRIMARY KEY (PROVIDER_ATTRIBUTE_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table USER_ROLE
--------------------------------------------------------

  ALTER TABLE IAMUSER.USER_ROLE MODIFY (USER_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.USER_ROLE MODIFY (ROLE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.USER_ROLE ADD PRIMARY KEY (ROLE_ID, USER_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table PROVISION_CONNECTOR
--------------------------------------------------------

  ALTER TABLE IAMUSER.PROVISION_CONNECTOR MODIFY (CONNECTOR_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.PROVISION_CONNECTOR ADD PRIMARY KEY (CONNECTOR_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table USER_AFFILIATION
--------------------------------------------------------

  ALTER TABLE IAMUSER.USER_AFFILIATION MODIFY (COMPANY_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.USER_AFFILIATION MODIFY (USER_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.USER_AFFILIATION ADD CONSTRAINT USER_AFFILIATION_PK PRIMARY KEY (COMPANY_ID, USER_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table ORG_POLICY_USER_LOG
--------------------------------------------------------

  ALTER TABLE IAMUSER.ORG_POLICY_USER_LOG MODIFY (ORG_POLICY_LOG_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.ORG_POLICY_USER_LOG MODIFY (ORG_POLICY_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.ORG_POLICY_USER_LOG MODIFY (USER_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.ORG_POLICY_USER_LOG MODIFY (TIME_STAMP NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.ORG_POLICY_USER_LOG ADD PRIMARY KEY (ORG_POLICY_LOG_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table SERVICE_CONFIG
--------------------------------------------------------

  ALTER TABLE IAMUSER.SERVICE_CONFIG MODIFY (SERVICE_CONFIG_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.SERVICE_CONFIG ADD PRIMARY KEY (SERVICE_CONFIG_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table GRP_ATTRIBUTES
--------------------------------------------------------

  ALTER TABLE IAMUSER.GRP_ATTRIBUTES MODIFY (ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.GRP_ATTRIBUTES MODIFY (GRP_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.GRP_ATTRIBUTES ADD PRIMARY KEY (ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table USERS
--------------------------------------------------------

  ALTER TABLE IAMUSER.USERS MODIFY (USER_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.USERS ADD PRIMARY KEY (USER_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table EMAIL_ADDRESS
--------------------------------------------------------

  ALTER TABLE IAMUSER.EMAIL_ADDRESS MODIFY (EMAIL_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.EMAIL_ADDRESS ADD PRIMARY KEY (EMAIL_ID) ENABLE;
 
  ALTER TABLE IAMUSER.EMAIL_ADDRESS MODIFY (TYPE_ID NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table URI_PATTERN_META_VALUE
--------------------------------------------------------

  ALTER TABLE IAMUSER.URI_PATTERN_META_VALUE MODIFY (URI_PATTERN_META_VALUE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.URI_PATTERN_META_VALUE MODIFY (URI_PATTERN_META_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.URI_PATTERN_META_VALUE MODIFY (META_ATTRIBUTE_NAME NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.URI_PATTERN_META_VALUE ADD PRIMARY KEY (URI_PATTERN_META_VALUE_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table CONTENT_PROVIDER_SERVER
--------------------------------------------------------

  ALTER TABLE IAMUSER.CONTENT_PROVIDER_SERVER MODIFY (CONTENT_PROVIDER_SERVER_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.CONTENT_PROVIDER_SERVER MODIFY (CONTENT_PROVIDER_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.CONTENT_PROVIDER_SERVER MODIFY (SERVER_URL NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.CONTENT_PROVIDER_SERVER ADD PRIMARY KEY (CONTENT_PROVIDER_SERVER_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table ATTRIBUTE_MAP
--------------------------------------------------------

  ALTER TABLE IAMUSER.ATTRIBUTE_MAP MODIFY (ATTRIBUTE_MAP_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.ATTRIBUTE_MAP MODIFY (MANAGED_SYS_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.ATTRIBUTE_MAP ADD PRIMARY KEY (ATTRIBUTE_MAP_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table METADATA_ELEMENT
--------------------------------------------------------

  ALTER TABLE IAMUSER.METADATA_ELEMENT MODIFY (METADATA_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.METADATA_ELEMENT MODIFY (TYPE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.METADATA_ELEMENT MODIFY (AUDITABLE NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.METADATA_ELEMENT MODIFY (REQUIRED NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.METADATA_ELEMENT MODIFY (SELF_EDITABLE NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.METADATA_ELEMENT MODIFY (IS_PUBLIC NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.METADATA_ELEMENT ADD PRIMARY KEY (METADATA_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table AUTH_RESOURCE_ATTRIBUTE_MAP
--------------------------------------------------------

  ALTER TABLE IAMUSER.AUTH_RESOURCE_ATTRIBUTE_MAP MODIFY (ATTRIBUTE_MAP_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.AUTH_RESOURCE_ATTRIBUTE_MAP MODIFY (PROVIDER_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.AUTH_RESOURCE_ATTRIBUTE_MAP MODIFY (TARGET_ATTRIBUTE_NAME NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.AUTH_RESOURCE_ATTRIBUTE_MAP MODIFY (AM_RES_ATTRIBUTE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.AUTH_RESOURCE_ATTRIBUTE_MAP MODIFY (ATTRIBUTE_TYPE NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.AUTH_RESOURCE_ATTRIBUTE_MAP ADD PRIMARY KEY (ATTRIBUTE_MAP_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table REQUEST_ATTRIBUTE
--------------------------------------------------------

  ALTER TABLE IAMUSER.REQUEST_ATTRIBUTE MODIFY (REQUEST_ATTR_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.REQUEST_ATTRIBUTE ADD PRIMARY KEY (REQUEST_ATTR_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table RESOURCE_GROUP
--------------------------------------------------------

  ALTER TABLE IAMUSER.RESOURCE_GROUP ADD CONSTRAINT RESOURCE_GROUP_PK PRIMARY KEY (RESOURCE_ID, GRP_ID) ENABLE;
 
  ALTER TABLE IAMUSER.RESOURCE_GROUP MODIFY (RESOURCE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.RESOURCE_GROUP MODIFY (GRP_ID NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table MNG_SYS_LIST
--------------------------------------------------------

  ALTER TABLE IAMUSER.MNG_SYS_LIST MODIFY (MANAGED_SYS_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.MNG_SYS_LIST MODIFY (REQUEST_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.MNG_SYS_LIST ADD PRIMARY KEY (MANAGED_SYS_ID, REQUEST_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table UI_TEMPLATE_FIELDS
--------------------------------------------------------

  ALTER TABLE IAMUSER.UI_TEMPLATE_FIELDS MODIFY (UI_FIELD_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.UI_TEMPLATE_FIELDS MODIFY (NAME NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.UI_TEMPLATE_FIELDS MODIFY (TEMPLATE_TYPE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.UI_TEMPLATE_FIELDS MODIFY (IS_REQUIRED NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.UI_TEMPLATE_FIELDS ADD PRIMARY KEY (UI_FIELD_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table LOCATION
--------------------------------------------------------

  ALTER TABLE IAMUSER.LOCATION MODIFY (LOCATION_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.LOCATION ADD PRIMARY KEY (LOCATION_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table REQUEST_USER
--------------------------------------------------------

  ALTER TABLE IAMUSER.REQUEST_USER MODIFY (REQ_USER_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.REQUEST_USER ADD PRIMARY KEY (REQ_USER_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table MANAGED_SYS
--------------------------------------------------------

  ALTER TABLE IAMUSER.MANAGED_SYS MODIFY (MANAGED_SYS_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.MANAGED_SYS ADD PRIMARY KEY (MANAGED_SYS_ID) ENABLE;
 
  ALTER TABLE IAMUSER.MANAGED_SYS MODIFY (SEARCH_SCOPE NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table APPROVER_ASSOC
--------------------------------------------------------

  ALTER TABLE IAMUSER.APPROVER_ASSOC MODIFY (APPROVER_ASSOC_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.APPROVER_ASSOC ADD PRIMARY KEY (APPROVER_ASSOC_ID) ENABLE;
 
  ALTER TABLE IAMUSER.APPROVER_ASSOC MODIFY (APPLY_DELEGATION_FILTER NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table RESOURCE_POLICY
--------------------------------------------------------

  ALTER TABLE IAMUSER.RESOURCE_POLICY MODIFY (RESOURCE_POLICY_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.RESOURCE_POLICY MODIFY (ROLE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.RESOURCE_POLICY ADD PRIMARY KEY (RESOURCE_POLICY_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table AUTH_RESOURCE_AM_ATTRIBUTE
--------------------------------------------------------

  ALTER TABLE IAMUSER.AUTH_RESOURCE_AM_ATTRIBUTE ADD CONSTRAINT REFLECTION_KEY_IDX UNIQUE (REFLECTION_KEY) ENABLE;
 
  ALTER TABLE IAMUSER.AUTH_RESOURCE_AM_ATTRIBUTE MODIFY (AM_RES_ATTRIBUTE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.AUTH_RESOURCE_AM_ATTRIBUTE MODIFY (REFLECTION_KEY NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.AUTH_RESOURCE_AM_ATTRIBUTE MODIFY (ATTRIBUTE_NAME NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.AUTH_RESOURCE_AM_ATTRIBUTE ADD PRIMARY KEY (AM_RES_ATTRIBUTE_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table AUTH_ATTRIBUTE
--------------------------------------------------------

  ALTER TABLE IAMUSER.AUTH_ATTRIBUTE MODIFY (AUTH_ATTRIBUTE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.AUTH_ATTRIBUTE MODIFY (ATTRIBUTE_NAME NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.AUTH_ATTRIBUTE MODIFY (PROVIDER_TYPE NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.AUTH_ATTRIBUTE MODIFY (DATA_TYPE NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.AUTH_ATTRIBUTE ADD PRIMARY KEY (AUTH_ATTRIBUTE_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table POLICY_ATTRIBUTE
--------------------------------------------------------

  ALTER TABLE IAMUSER.POLICY_ATTRIBUTE MODIFY (POLICY_ATTR_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.POLICY_ATTRIBUTE ADD PRIMARY KEY (POLICY_ATTR_ID) ENABLE;
 
  ALTER TABLE IAMUSER.POLICY_ATTRIBUTE MODIFY (REQUIRED NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ROLE
--------------------------------------------------------

  ALTER TABLE IAMUSER.ROLE MODIFY (SERVICE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.ROLE MODIFY (ROLE_NAME NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.ROLE MODIFY (ROLE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.ROLE ADD PRIMARY KEY (ROLE_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table OPENIAM_LOG
--------------------------------------------------------

  ALTER TABLE IAMUSER.OPENIAM_LOG ADD CONSTRAINT OPENIAM_LOG_PK PRIMARY KEY (OPENIAM_LOG_ID) ENABLE;
 
  ALTER TABLE IAMUSER.OPENIAM_LOG MODIFY (OPENIAM_LOG_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.OPENIAM_LOG MODIFY (HASH NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table RECONCILIATION_CONFIG
--------------------------------------------------------

  ALTER TABLE IAMUSER.RECONCILIATION_CONFIG MODIFY (RECON_CONFIG_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.RECONCILIATION_CONFIG ADD PRIMARY KEY (RECON_CONFIG_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table CONTENT_PROVIDER
--------------------------------------------------------

  ALTER TABLE IAMUSER.CONTENT_PROVIDER MODIFY (CONTENT_PROVIDER_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.CONTENT_PROVIDER MODIFY (CONTENT_PROVIDER_NAME NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.CONTENT_PROVIDER MODIFY (IS_PUBLIC NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.CONTENT_PROVIDER MODIFY (MIN_AUTH_LEVEL NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.CONTENT_PROVIDER MODIFY (DOMAIN_PATTERN NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.CONTENT_PROVIDER MODIFY (RESOURCE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.CONTENT_PROVIDER ADD PRIMARY KEY (CONTENT_PROVIDER_ID) ENABLE;
 
  ALTER TABLE IAMUSER.CONTENT_PROVIDER MODIFY (MANAGED_SYS_ID NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table USER_KEY
--------------------------------------------------------

  ALTER TABLE IAMUSER.USER_KEY MODIFY (USER_KEY_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.USER_KEY MODIFY (USER_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.USER_KEY MODIFY (NAME NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.USER_KEY MODIFY (KEY_VALUE NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.USER_KEY ADD PRIMARY KEY (USER_KEY_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table USER_NOTE
--------------------------------------------------------

  ALTER TABLE IAMUSER.USER_NOTE MODIFY (USER_NOTE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.USER_NOTE ADD PRIMARY KEY (USER_NOTE_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table PWD_HISTORY
--------------------------------------------------------

  ALTER TABLE IAMUSER.PWD_HISTORY MODIFY (PWD_HISTORY_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.PWD_HISTORY MODIFY (DATE_CREATED NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.PWD_HISTORY MODIFY (LOGIN_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.PWD_HISTORY ADD PRIMARY KEY (PWD_HISTORY_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table SERVICE
--------------------------------------------------------

  ALTER TABLE IAMUSER.SERVICE MODIFY (SERVICE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.SERVICE ADD PRIMARY KEY (SERVICE_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table RESOURCE_USER
--------------------------------------------------------

  ALTER TABLE IAMUSER.RESOURCE_USER ADD CONSTRAINT RESOURCE_USER_PK PRIMARY KEY (RESOURCE_ID, USER_ID) ENABLE;
 
  ALTER TABLE IAMUSER.RESOURCE_USER MODIFY (RESOURCE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.RESOURCE_USER MODIFY (USER_ID NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ENTITLEMENT
--------------------------------------------------------

  ALTER TABLE IAMUSER.ENTITLEMENT MODIFY (ENTITLEMENT_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.ENTITLEMENT ADD PRIMARY KEY (ENTITLEMENT_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table RELATION_CATEGORY
--------------------------------------------------------

  ALTER TABLE IAMUSER.RELATION_CATEGORY MODIFY (RELATION_SET_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.RELATION_CATEGORY MODIFY (CATEGORY_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.RELATION_CATEGORY ADD PRIMARY KEY (RELATION_SET_ID, CATEGORY_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table DEF_RECON_ATTR_MAP
--------------------------------------------------------

  ALTER TABLE IAMUSER.DEF_RECON_ATTR_MAP MODIFY (DEF_ATTR_MAP_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.DEF_RECON_ATTR_MAP MODIFY (DEF_ATTR_MAP_NAME NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.DEF_RECON_ATTR_MAP ADD PRIMARY KEY (DEF_ATTR_MAP_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table COMPANY_TO_COMPANY_MEMBERSHIP
--------------------------------------------------------

  ALTER TABLE IAMUSER.COMPANY_TO_COMPANY_MEMBERSHIP MODIFY (COMPANY_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.COMPANY_TO_COMPANY_MEMBERSHIP MODIFY (MEMBER_COMPANY_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.COMPANY_TO_COMPANY_MEMBERSHIP ADD PRIMARY KEY (COMPANY_ID, MEMBER_COMPANY_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table RECON_RES_ATTR_MAP
--------------------------------------------------------

  ALTER TABLE IAMUSER.RECON_RES_ATTR_MAP MODIFY (RECON_RES_ATTR_MAP_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.RECON_RES_ATTR_MAP ADD PRIMARY KEY (RECON_RES_ATTR_MAP_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table MANAGED_SYS_ATTRIBUTE
--------------------------------------------------------

  ALTER TABLE IAMUSER.MANAGED_SYS_ATTRIBUTE MODIFY (MNG_SYS_ATTR_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.MANAGED_SYS_ATTRIBUTE ADD PRIMARY KEY (MNG_SYS_ATTR_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table SEQUENCE_GEN
--------------------------------------------------------

  ALTER TABLE IAMUSER.SEQUENCE_GEN MODIFY (ATTRIBUTE NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.SEQUENCE_GEN ADD PRIMARY KEY (ATTRIBUTE) ENABLE;
--------------------------------------------------------
--  Constraints for Table POLICY_DEF
--------------------------------------------------------

  ALTER TABLE IAMUSER.POLICY_DEF MODIFY (POLICY_DEF_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.POLICY_DEF ADD PRIMARY KEY (POLICY_DEF_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table IDENTITY_QUESTION
--------------------------------------------------------

  ALTER TABLE IAMUSER.IDENTITY_QUESTION MODIFY (IDENTITY_QUESTION_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.IDENTITY_QUESTION ADD PRIMARY KEY (IDENTITY_QUESTION_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table UI_TEMPLATE_TYPE
--------------------------------------------------------

  ALTER TABLE IAMUSER.UI_TEMPLATE_TYPE MODIFY (TEMPLATE_TYPE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.UI_TEMPLATE_TYPE MODIFY (NAME NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.UI_TEMPLATE_TYPE ADD PRIMARY KEY (TEMPLATE_TYPE_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table PHONE
--------------------------------------------------------

  ALTER TABLE IAMUSER.PHONE MODIFY (PHONE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.PHONE ADD PRIMARY KEY (PHONE_ID) ENABLE;
 
  ALTER TABLE IAMUSER.PHONE MODIFY (TYPE_ID NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table SYNCH_CONFIG
--------------------------------------------------------

  ALTER TABLE IAMUSER.SYNCH_CONFIG MODIFY (SYNCH_CONFIG_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.SYNCH_CONFIG ADD PRIMARY KEY (SYNCH_CONFIG_ID) ENABLE;
 
  ALTER TABLE IAMUSER.SYNCH_CONFIG MODIFY (SEARCH_SCOPE NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table LOGIN_ATTRIBUTE
--------------------------------------------------------

  ALTER TABLE IAMUSER.LOGIN_ATTRIBUTE MODIFY (LOGIN_ATTR_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.LOGIN_ATTRIBUTE MODIFY (LOGIN_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.LOGIN_ATTRIBUTE ADD PRIMARY KEY (LOGIN_ATTR_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table OPENIAM_LOG_LOG_MEMBERSHIP
--------------------------------------------------------

  ALTER TABLE IAMUSER.OPENIAM_LOG_LOG_MEMBERSHIP MODIFY (OPENIAM_LOG_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.OPENIAM_LOG_LOG_MEMBERSHIP MODIFY (OPENIAM_MEMBER_LOG_ID NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table IDENTITY_QUEST_GRP
--------------------------------------------------------

  ALTER TABLE IAMUSER.IDENTITY_QUEST_GRP MODIFY (IDENTITY_QUEST_GRP_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.IDENTITY_QUEST_GRP ADD PRIMARY KEY (IDENTITY_QUEST_GRP_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table UI_FIELD_TEMPLATE_XREF
--------------------------------------------------------

  ALTER TABLE IAMUSER.UI_FIELD_TEMPLATE_XREF MODIFY (UI_FIELD_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.UI_FIELD_TEMPLATE_XREF MODIFY (TEMPLATE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.UI_FIELD_TEMPLATE_XREF MODIFY (IS_REQUIRED NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.UI_FIELD_TEMPLATE_XREF MODIFY (IS_EDITABLE NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.UI_FIELD_TEMPLATE_XREF MODIFY (DISPLAY_ORDER NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.UI_FIELD_TEMPLATE_XREF ADD CONSTRAINT TEMPLATE_XREF_UNIQUE UNIQUE (TEMPLATE_ID, UI_FIELD_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table RES
--------------------------------------------------------

  ALTER TABLE IAMUSER.RES MODIFY (RESOURCE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.RES MODIFY (RESOURCE_TYPE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.RES MODIFY (NAME NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.RES ADD PRIMARY KEY (RESOURCE_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table WEB_RESOURCE_ATTRIBUTE
--------------------------------------------------------

  ALTER TABLE IAMUSER.WEB_RESOURCE_ATTRIBUTE MODIFY (ATTRIBUTE_MAP_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.WEB_RESOURCE_ATTRIBUTE MODIFY (RESOURCE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.WEB_RESOURCE_ATTRIBUTE MODIFY (TARGET_ATTRIBUTE_NAME NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.WEB_RESOURCE_ATTRIBUTE ADD PRIMARY KEY (ATTRIBUTE_MAP_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table USER_ATTACHMENT_REF
--------------------------------------------------------

  ALTER TABLE IAMUSER.USER_ATTACHMENT_REF MODIFY (USER_ATTACH_REF_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.USER_ATTACHMENT_REF ADD PRIMARY KEY (USER_ATTACH_REF_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table RELATION_TYPE
--------------------------------------------------------

  ALTER TABLE IAMUSER.RELATION_TYPE MODIFY (RELATION_TYPE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.RELATION_TYPE MODIFY (DESCRIPTION NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.RELATION_TYPE ADD PRIMARY KEY (RELATION_TYPE_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table RELATION_SOURCE
--------------------------------------------------------

  ALTER TABLE IAMUSER.RELATION_SOURCE MODIFY (RELATION_TYPE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.RELATION_SOURCE MODIFY (SOURCE_OBJ NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.RELATION_SOURCE MODIFY (SOURCE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.RELATION_SOURCE ADD PRIMARY KEY (RELATION_TYPE_ID, SOURCE_OBJ, SOURCE_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table ORG_TYPE_VALID_MEMBERSHIP
--------------------------------------------------------

  ALTER TABLE IAMUSER.ORG_TYPE_VALID_MEMBERSHIP MODIFY (ORG_TYPE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.ORG_TYPE_VALID_MEMBERSHIP MODIFY (MEMBER_ORG_TYPE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.ORG_TYPE_VALID_MEMBERSHIP ADD CONSTRAINT UNIQUE_USER_ORG_TYPE UNIQUE (ORG_TYPE_ID, MEMBER_ORG_TYPE_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table RECONCILIATION_SITUATION
--------------------------------------------------------

  ALTER TABLE IAMUSER.RECONCILIATION_SITUATION MODIFY (RECON_SITUATION_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.RECONCILIATION_SITUATION ADD PRIMARY KEY (RECON_SITUATION_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table RESOURCE_ROLE
--------------------------------------------------------

  ALTER TABLE IAMUSER.RESOURCE_ROLE MODIFY (RESOURCE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.RESOURCE_ROLE MODIFY (ROLE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.RESOURCE_ROLE ADD PRIMARY KEY (RESOURCE_ID, ROLE_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table USER_ATTRIBUTES
--------------------------------------------------------

  ALTER TABLE IAMUSER.USER_ATTRIBUTES MODIFY (ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.USER_ATTRIBUTES ADD PRIMARY KEY (ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table ROLE_TO_ROLE_MEMBERSHIP
--------------------------------------------------------

  ALTER TABLE IAMUSER.ROLE_TO_ROLE_MEMBERSHIP MODIFY (ROLE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.ROLE_TO_ROLE_MEMBERSHIP MODIFY (MEMBER_ROLE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.ROLE_TO_ROLE_MEMBERSHIP ADD PRIMARY KEY (ROLE_ID, MEMBER_ROLE_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table MNG_SYS_GROUP
--------------------------------------------------------

  ALTER TABLE IAMUSER.MNG_SYS_GROUP MODIFY (MNG_SYS_GROUP_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.MNG_SYS_GROUP MODIFY (MANAGED_SYS_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.MNG_SYS_GROUP ADD PRIMARY KEY (MANAGED_SYS_ID, MNG_SYS_GROUP_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table REPORT_SUBSCRIPTIONS
--------------------------------------------------------

  ALTER TABLE IAMUSER.REPORT_SUBSCRIPTIONS MODIFY (REPORT_SUB_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.REPORT_SUBSCRIPTIONS MODIFY (REPORT_NAME NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.REPORT_SUBSCRIPTIONS ADD PRIMARY KEY (REPORT_SUB_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table NOTIFICATION_CONFIG
--------------------------------------------------------

  ALTER TABLE IAMUSER.NOTIFICATION_CONFIG MODIFY (NOTIFICATION_CONFIG_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.NOTIFICATION_CONFIG ADD PRIMARY KEY (NOTIFICATION_CONFIG_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table CATEGORY_LANGUAGE
--------------------------------------------------------

  ALTER TABLE IAMUSER.CATEGORY_LANGUAGE MODIFY (CATEGORY_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.CATEGORY_LANGUAGE MODIFY (LANGUAGE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.CATEGORY_LANGUAGE ADD PRIMARY KEY (CATEGORY_ID, LANGUAGE_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table MD_ELEMENT_VALID_VALUES
--------------------------------------------------------

  ALTER TABLE IAMUSER.MD_ELEMENT_VALID_VALUES MODIFY (ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.MD_ELEMENT_VALID_VALUES MODIFY (METADATA_ELEMENT_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.MD_ELEMENT_VALID_VALUES MODIFY (UI_VALUE NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.MD_ELEMENT_VALID_VALUES MODIFY (DISPLAY_ORDER NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.MD_ELEMENT_VALID_VALUES ADD PRIMARY KEY (ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table URI_PATTERN_META
--------------------------------------------------------

  ALTER TABLE IAMUSER.URI_PATTERN_META MODIFY (URI_PATTERN_META_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.URI_PATTERN_META MODIFY (URI_PATTERN_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.URI_PATTERN_META MODIFY (URI_PATTERN_META_TYPE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.URI_PATTERN_META MODIFY (URI_PATTERN_NAME NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.URI_PATTERN_META ADD PRIMARY KEY (URI_PATTERN_META_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table AUTH_STATE
--------------------------------------------------------

  ALTER TABLE IAMUSER.AUTH_STATE MODIFY (USER_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.AUTH_STATE ADD PRIMARY KEY (USER_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table RESOURCE_PRIVILEGE
--------------------------------------------------------

  ALTER TABLE IAMUSER.RESOURCE_PRIVILEGE MODIFY (RESOURCE_PRIVLEGE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.RESOURCE_PRIVILEGE ADD PRIMARY KEY (RESOURCE_PRIVLEGE_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table USER_GRP
--------------------------------------------------------

  ALTER TABLE IAMUSER.USER_GRP MODIFY (GRP_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.USER_GRP MODIFY (USER_ID NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CREDENTIALS
--------------------------------------------------------

  ALTER TABLE IAMUSER.CREDENTIALS MODIFY (USER_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.CREDENTIALS MODIFY (CREDENTIAL_TYPE NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.CREDENTIALS ADD PRIMARY KEY (USER_ID, CREDENTIAL_TYPE) ENABLE;
--------------------------------------------------------
--  Constraints for Table ADDRESS
--------------------------------------------------------

  ALTER TABLE IAMUSER.ADDRESS MODIFY (ADDRESS_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.ADDRESS ADD PRIMARY KEY (ADDRESS_ID) ENABLE;
 
  ALTER TABLE IAMUSER.ADDRESS MODIFY (TYPE_ID NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table AUTH_PROVIDER
--------------------------------------------------------

  ALTER TABLE IAMUSER.AUTH_PROVIDER MODIFY (PROVIDER_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.AUTH_PROVIDER MODIFY (PROVIDER_TYPE NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.AUTH_PROVIDER MODIFY (MANAGED_SYS_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.AUTH_PROVIDER MODIFY (RESOURCE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.AUTH_PROVIDER MODIFY (NAME NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.AUTH_PROVIDER MODIFY (SIGN_REQUEST NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.AUTH_PROVIDER ADD PRIMARY KEY (PROVIDER_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table USER_IDENTITY_ANS
--------------------------------------------------------

  ALTER TABLE IAMUSER.USER_IDENTITY_ANS MODIFY (IDENTITY_ANS_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.USER_IDENTITY_ANS MODIFY (IDENTITY_QUESTION_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.USER_IDENTITY_ANS ADD PRIMARY KEY (IDENTITY_ANS_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table METADATA_URI_XREF
--------------------------------------------------------

  ALTER TABLE IAMUSER.METADATA_URI_XREF MODIFY (TEMPLATE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.METADATA_URI_XREF MODIFY (URI_PATTERN_ID NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PROV_REQUEST
--------------------------------------------------------

  ALTER TABLE IAMUSER.PROV_REQUEST MODIFY (REQUEST_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.PROV_REQUEST ADD PRIMARY KEY (REQUEST_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table CATEGORY
--------------------------------------------------------

  ALTER TABLE IAMUSER.CATEGORY MODIFY (CATEGORY_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.CATEGORY ADD PRIMARY KEY (CATEGORY_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table POLICY
--------------------------------------------------------

  ALTER TABLE IAMUSER.POLICY MODIFY (POLICY_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.POLICY ADD PRIMARY KEY (POLICY_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table ORG_STRUCTURE
--------------------------------------------------------

  ALTER TABLE IAMUSER.ORG_STRUCTURE MODIFY (ORG_STRUCTURE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.ORG_STRUCTURE MODIFY (SUPERVISOR_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.ORG_STRUCTURE MODIFY (STAFF_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.ORG_STRUCTURE ADD PRIMARY KEY (ORG_STRUCTURE_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table LANGUAGE_MAPPING
--------------------------------------------------------

  ALTER TABLE IAMUSER.LANGUAGE_MAPPING MODIFY (ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.LANGUAGE_MAPPING MODIFY (LANGUAGE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.LANGUAGE_MAPPING MODIFY (REFERENCE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.LANGUAGE_MAPPING MODIFY (REFERENCE_TYPE NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.LANGUAGE_MAPPING MODIFY (TEXT_VALUE NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.LANGUAGE_MAPPING ADD PRIMARY KEY (ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table AUTH_PROVIDER_TYPE
--------------------------------------------------------

  ALTER TABLE IAMUSER.AUTH_PROVIDER_TYPE MODIFY (PROVIDER_TYPE NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.AUTH_PROVIDER_TYPE ADD PRIMARY KEY (PROVIDER_TYPE) ENABLE;
--------------------------------------------------------
--  Constraints for Table RELATIONSHIP
--------------------------------------------------------

  ALTER TABLE IAMUSER.RELATIONSHIP MODIFY (RELATIONSHIP_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.RELATIONSHIP ADD PRIMARY KEY (RELATIONSHIP_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table ORG_POLICY
--------------------------------------------------------

  ALTER TABLE IAMUSER.ORG_POLICY MODIFY (ORG_POLICY_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.ORG_POLICY ADD PRIMARY KEY (ORG_POLICY_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table IT_POLICY
--------------------------------------------------------

  ALTER TABLE IAMUSER.IT_POLICY MODIFY (IT_POLICY_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.IT_POLICY MODIFY (APPROVE_TYPE NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.IT_POLICY MODIFY (ACTIVE NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.IT_POLICY ADD PRIMARY KEY (IT_POLICY_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table BATCH_CONFIG
--------------------------------------------------------

  ALTER TABLE IAMUSER.BATCH_CONFIG MODIFY (TASK_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.BATCH_CONFIG ADD PRIMARY KEY (TASK_ID) ENABLE;
 
  ALTER TABLE IAMUSER.BATCH_CONFIG MODIFY (ENABLED NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PRIVILEGE
--------------------------------------------------------

  ALTER TABLE IAMUSER.PRIVILEGE MODIFY (PRIVILEGE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.PRIVILEGE MODIFY (RESOURCE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.PRIVILEGE MODIFY (ABBRV NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.PRIVILEGE ADD PRIMARY KEY (PRIVILEGE_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table RES_TO_RES_MEMBERSHIP
--------------------------------------------------------

  ALTER TABLE IAMUSER.RES_TO_RES_MEMBERSHIP MODIFY (RESOURCE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.RES_TO_RES_MEMBERSHIP MODIFY (MEMBER_RESOURCE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.RES_TO_RES_MEMBERSHIP ADD PRIMARY KEY (RESOURCE_ID, MEMBER_RESOURCE_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table URI_PATTERN_META_TYPE
--------------------------------------------------------

  ALTER TABLE IAMUSER.URI_PATTERN_META_TYPE MODIFY (URI_PATTERN_META_TYPE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.URI_PATTERN_META_TYPE MODIFY (METADATA_TYPE_NAME NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.URI_PATTERN_META_TYPE MODIFY (SPRING_BEAN_NAME NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.URI_PATTERN_META_TYPE ADD PRIMARY KEY (URI_PATTERN_META_TYPE_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table POLICY_DEF_PARAM
--------------------------------------------------------

  ALTER TABLE IAMUSER.POLICY_DEF_PARAM MODIFY (DEF_PARAM_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.POLICY_DEF_PARAM ADD PRIMARY KEY (DEF_PARAM_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table METADATA_TYPE
--------------------------------------------------------

  ALTER TABLE IAMUSER.METADATA_TYPE MODIFY (TYPE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.METADATA_TYPE MODIFY (ACTIVE NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.METADATA_TYPE MODIFY (SYNC_MANAGED_SYS NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.METADATA_TYPE ADD PRIMARY KEY (TYPE_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table URI_PATTERN
--------------------------------------------------------

  ALTER TABLE IAMUSER.URI_PATTERN MODIFY (URI_PATTERN_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.URI_PATTERN MODIFY (CONTENT_PROVIDER_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.URI_PATTERN MODIFY (MIN_AUTH_LEVEL NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.URI_PATTERN MODIFY (PATTERN NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.URI_PATTERN MODIFY (IS_PUBLIC NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.URI_PATTERN MODIFY (RESOURCE_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.URI_PATTERN ADD PRIMARY KEY (URI_PATTERN_ID) ENABLE;
--------------------------------------------------------
--  Constraints for Table OPENIAM_LOG_TARGET
--------------------------------------------------------

  ALTER TABLE IAMUSER.OPENIAM_LOG_TARGET ADD CONSTRAINT OPENIAM_LOG_TARGET_PK PRIMARY KEY (OPENIAM_LOG_TARGET_ID) ENABLE;
 
  ALTER TABLE IAMUSER.OPENIAM_LOG_TARGET MODIFY (OPENIAM_LOG_TARGET_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.OPENIAM_LOG_TARGET MODIFY (OPENIAM_LOG_ID NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table REQUEST_ATTACHMENT
--------------------------------------------------------

  ALTER TABLE IAMUSER.REQUEST_ATTACHMENT MODIFY (REQUEST_ATTACHMENT_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.REQUEST_ATTACHMENT MODIFY (REQUEST_ID NOT NULL ENABLE);
 
  ALTER TABLE IAMUSER.REQUEST_ATTACHMENT ADD PRIMARY KEY (REQUEST_ATTACHMENT_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ADDRESS
--------------------------------------------------------

  ALTER TABLE IAMUSER.ADDRESS ADD CONSTRAINT ADDRESS_TYPE_FK FOREIGN KEY (TYPE_ID)
	  REFERENCES IAMUSER.METADATA_TYPE (TYPE_ID) ENABLE;
 
  ALTER TABLE IAMUSER.ADDRESS ADD CONSTRAINT ADDRESS_USER_FK FOREIGN KEY (PARENT_ID)
	  REFERENCES IAMUSER.USERS (USER_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table AUTH_ATTRIBUTE
--------------------------------------------------------

  ALTER TABLE IAMUSER.AUTH_ATTRIBUTE ADD CONSTRAINT FK_PROVIDER_TYPE_ATTRIBUTE FOREIGN KEY (PROVIDER_TYPE)
	  REFERENCES IAMUSER.AUTH_PROVIDER_TYPE (PROVIDER_TYPE) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table AUTH_PROVIDER
--------------------------------------------------------

  ALTER TABLE IAMUSER.AUTH_PROVIDER ADD CONSTRAINT FK_MNG_AUTH_PROVIDER FOREIGN KEY (MANAGED_SYS_ID)
	  REFERENCES IAMUSER.MANAGED_SYS (MANAGED_SYS_ID) ENABLE;
 
  ALTER TABLE IAMUSER.AUTH_PROVIDER ADD CONSTRAINT FK_PROVIDER_TYPE_PROVIDER FOREIGN KEY (PROVIDER_TYPE)
	  REFERENCES IAMUSER.AUTH_PROVIDER_TYPE (PROVIDER_TYPE) ENABLE;
 
  ALTER TABLE IAMUSER.AUTH_PROVIDER ADD CONSTRAINT FK_RES_AUTH_PROVIDER FOREIGN KEY (RESOURCE_ID)
	  REFERENCES IAMUSER.RES (RESOURCE_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table AUTH_PROVIDER_ATTRIBUTE
--------------------------------------------------------

  ALTER TABLE IAMUSER.AUTH_PROVIDER_ATTRIBUTE ADD CONSTRAINT FK_PROVIDER_ATTRIBUTE FOREIGN KEY (AUTH_ATTRIBUTE_ID)
	  REFERENCES IAMUSER.AUTH_ATTRIBUTE (AUTH_ATTRIBUTE_ID) ENABLE;
 
  ALTER TABLE IAMUSER.AUTH_PROVIDER_ATTRIBUTE ADD CONSTRAINT FK_PROVIDER_PROVIDER_ATTRIBUTE FOREIGN KEY (PROVIDER_ID)
	  REFERENCES IAMUSER.AUTH_PROVIDER (PROVIDER_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table AUTH_RESOURCE_ATTRIBUTE_MAP
--------------------------------------------------------

  ALTER TABLE IAMUSER.AUTH_RESOURCE_ATTRIBUTE_MAP ADD CONSTRAINT FK_AUTH_AM_ATTR FOREIGN KEY (AM_RES_ATTRIBUTE_ID)
	  REFERENCES IAMUSER.AUTH_RESOURCE_AM_ATTRIBUTE (AM_RES_ATTRIBUTE_ID) ENABLE;
 
  ALTER TABLE IAMUSER.AUTH_RESOURCE_ATTRIBUTE_MAP ADD CONSTRAINT FK_PROVIDER_AUTH_RES_ATTR_MAP FOREIGN KEY (PROVIDER_ID)
	  REFERENCES IAMUSER.AUTH_PROVIDER (PROVIDER_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table AUTH_STATE
--------------------------------------------------------

  ALTER TABLE IAMUSER.AUTH_STATE ADD CONSTRAINT FK_AUTH_STATE_USERS FOREIGN KEY (USER_ID)
	  REFERENCES IAMUSER.USERS (USER_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table CATEGORY_LANGUAGE
--------------------------------------------------------

  ALTER TABLE IAMUSER.CATEGORY_LANGUAGE ADD CONSTRAINT FK_CATEGORY_LANGUAGE_CATEGORY FOREIGN KEY (CATEGORY_ID)
	  REFERENCES IAMUSER.CATEGORY (CATEGORY_ID) ON DELETE CASCADE ENABLE;
 
  ALTER TABLE IAMUSER.CATEGORY_LANGUAGE ADD CONSTRAINT FK_CATEGORY_LANGUAGE_LANGUAGE FOREIGN KEY (LANGUAGE_ID)
	  REFERENCES IAMUSER.LANGUAGE (ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table CATEGORY_TYPE
--------------------------------------------------------

  ALTER TABLE IAMUSER.CATEGORY_TYPE ADD CONSTRAINT FK_CATEGORY_METADATA_TYPE FOREIGN KEY (TYPE_ID)
	  REFERENCES IAMUSER.METADATA_TYPE (TYPE_ID) ENABLE;
 
  ALTER TABLE IAMUSER.CATEGORY_TYPE ADD CONSTRAINT FK_CATEGORY_TYPE_CATEGORY FOREIGN KEY (CATEGORY_ID)
	  REFERENCES IAMUSER.CATEGORY (CATEGORY_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table COMPANY
--------------------------------------------------------

  ALTER TABLE IAMUSER.COMPANY ADD CONSTRAINT COMPANY_ORG_TYPE_FK FOREIGN KEY (ORG_TYPE_ID)
	  REFERENCES IAMUSER.ORGANIZATION_TYPE (ORG_TYPE_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table COMPANY_ATTRIBUTE
--------------------------------------------------------

  ALTER TABLE IAMUSER.COMPANY_ATTRIBUTE ADD CONSTRAINT FK_COMPANY_ATTRIBUTE_COMPANY FOREIGN KEY (COMPANY_ID)
	  REFERENCES IAMUSER.COMPANY (COMPANY_ID) ENABLE;
 
  ALTER TABLE IAMUSER.COMPANY_ATTRIBUTE ADD CONSTRAINT FK_COMPANY_METADATA_ELEMENT FOREIGN KEY (METADATA_ID)
	  REFERENCES IAMUSER.METADATA_ELEMENT (METADATA_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table COMPANY_TO_COMPANY_MEMBERSHIP
--------------------------------------------------------

  ALTER TABLE IAMUSER.COMPANY_TO_COMPANY_MEMBERSHIP ADD CONSTRAINT COMPANY_COMPANY_CHILD FOREIGN KEY (COMPANY_ID)
	  REFERENCES IAMUSER.COMPANY (COMPANY_ID) ENABLE;
 
  ALTER TABLE IAMUSER.COMPANY_TO_COMPANY_MEMBERSHIP ADD CONSTRAINT COMPANY_COMPANY_PARENT FOREIGN KEY (MEMBER_COMPANY_ID)
	  REFERENCES IAMUSER.COMPANY (COMPANY_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table CONTENT_PROVIDER
--------------------------------------------------------

  ALTER TABLE IAMUSER.CONTENT_PROVIDER ADD CONSTRAINT CP_AUTH_LEVEL_FK FOREIGN KEY (MIN_AUTH_LEVEL)
	  REFERENCES IAMUSER.AUTH_LEVEL (AUTH_LEVEL_ID) ENABLE;
 
  ALTER TABLE IAMUSER.CONTENT_PROVIDER ADD CONSTRAINT CP_RES_FK FOREIGN KEY (RESOURCE_ID)
	  REFERENCES IAMUSER.RES (RESOURCE_ID) ENABLE;
 
  ALTER TABLE IAMUSER.CONTENT_PROVIDER ADD CONSTRAINT FK_CP_MANAGED_SYS FOREIGN KEY (MANAGED_SYS_ID)
	  REFERENCES IAMUSER.MANAGED_SYS (MANAGED_SYS_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table CONTENT_PROVIDER_SERVER
--------------------------------------------------------

  ALTER TABLE IAMUSER.CONTENT_PROVIDER_SERVER ADD CONSTRAINT CP_SERVER_CP_FK FOREIGN KEY (CONTENT_PROVIDER_ID)
	  REFERENCES IAMUSER.CONTENT_PROVIDER (CONTENT_PROVIDER_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table CREDENTIALS
--------------------------------------------------------

  ALTER TABLE IAMUSER.CREDENTIALS ADD CONSTRAINT FK_CREDENTIALS_USERS FOREIGN KEY (USER_ID)
	  REFERENCES IAMUSER.USERS (USER_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table EMAIL_ADDRESS
--------------------------------------------------------

  ALTER TABLE IAMUSER.EMAIL_ADDRESS ADD CONSTRAINT EMAIL_ADDRESS_TYPE_FK FOREIGN KEY (TYPE_ID)
	  REFERENCES IAMUSER.METADATA_TYPE (TYPE_ID) ENABLE;
 
  ALTER TABLE IAMUSER.EMAIL_ADDRESS ADD CONSTRAINT EMAIL_ADDRESS_USER_FK FOREIGN KEY (PARENT_ID)
	  REFERENCES IAMUSER.USERS (USER_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table EXCLUDE_WORD_LIST
--------------------------------------------------------

  ALTER TABLE IAMUSER.EXCLUDE_WORD_LIST ADD CONSTRAINT FK_EXCLUDE_WORD_LIST_LANGUAGE FOREIGN KEY (LANGUAGE_ID)
	  REFERENCES IAMUSER.LANGUAGE (ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table GRP
--------------------------------------------------------

  ALTER TABLE IAMUSER.GRP ADD CONSTRAINT GRP_MNG_SYS_FK FOREIGN KEY (MANAGED_SYS_ID)
	  REFERENCES IAMUSER.MANAGED_SYS (MANAGED_SYS_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table GRP_ATTRIBUTES
--------------------------------------------------------

  ALTER TABLE IAMUSER.GRP_ATTRIBUTES ADD CONSTRAINT GRP_ATTRIBUTES_IBFK_1 FOREIGN KEY (GRP_ID)
	  REFERENCES IAMUSER.GRP (GRP_ID) ENABLE;
 
  ALTER TABLE IAMUSER.GRP_ATTRIBUTES ADD CONSTRAINT GRP_ATTR_META_ELMT_FK FOREIGN KEY (METADATA_ID)
	  REFERENCES IAMUSER.METADATA_ELEMENT (METADATA_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table GRP_ROLE
--------------------------------------------------------

  ALTER TABLE IAMUSER.GRP_ROLE ADD CONSTRAINT FK_GRP_ROLE_GRP FOREIGN KEY (GRP_ID)
	  REFERENCES IAMUSER.GRP (GRP_ID) ENABLE;
 
  ALTER TABLE IAMUSER.GRP_ROLE ADD CONSTRAINT FK_GRP_ROLE_ROLE FOREIGN KEY (ROLE_ID)
	  REFERENCES IAMUSER.ROLE (ROLE_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table GRP_TO_GRP_MEMBERSHIP
--------------------------------------------------------

  ALTER TABLE IAMUSER.GRP_TO_GRP_MEMBERSHIP ADD CONSTRAINT GRP_TO_GRP_MEMBERSHIP_IBFK_1 FOREIGN KEY (GROUP_ID)
	  REFERENCES IAMUSER.GRP (GRP_ID) ENABLE;
 
  ALTER TABLE IAMUSER.GRP_TO_GRP_MEMBERSHIP ADD CONSTRAINT GRP_TO_GRP_MEMBERSHIP_IBFK_2 FOREIGN KEY (MEMBER_GROUP_ID)
	  REFERENCES IAMUSER.GRP (GRP_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table IDENTITY_QUESTION
--------------------------------------------------------

  ALTER TABLE IAMUSER.IDENTITY_QUESTION ADD CONSTRAINT ID_QU_IDEN_QUEST_GRP_FK FOREIGN KEY (IDENTITY_QUEST_GRP_ID)
	  REFERENCES IAMUSER.IDENTITY_QUEST_GRP (IDENTITY_QUEST_GRP_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table LANGUAGE_LOCALE
--------------------------------------------------------

  ALTER TABLE IAMUSER.LANGUAGE_LOCALE ADD CONSTRAINT LANGUAGE_LOCALE_LANGUAGE_FK FOREIGN KEY (LANGUAGE_ID)
	  REFERENCES IAMUSER.LANGUAGE (ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table LANGUAGE_MAPPING
--------------------------------------------------------

  ALTER TABLE IAMUSER.LANGUAGE_MAPPING ADD CONSTRAINT LANGUAGE_MAPPING_LANGUAGE_FK FOREIGN KEY (LANGUAGE_ID)
	  REFERENCES IAMUSER.LANGUAGE (ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table LOGIN
--------------------------------------------------------

  ALTER TABLE IAMUSER.LOGIN ADD CONSTRAINT FK_LOGIN_SERVICE FOREIGN KEY (SERVICE_ID)
	  REFERENCES IAMUSER.SECURITY_DOMAIN (DOMAIN_ID) ENABLE;
 
  ALTER TABLE IAMUSER.LOGIN ADD CONSTRAINT FK_LOGIN_USERS FOREIGN KEY (USER_ID)
	  REFERENCES IAMUSER.USERS (USER_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table LOGIN_ATTRIBUTE
--------------------------------------------------------

  ALTER TABLE IAMUSER.LOGIN_ATTRIBUTE ADD CONSTRAINT LOGIN_ID_LOGIN_ATTR_FK FOREIGN KEY (LOGIN_ID)
	  REFERENCES IAMUSER.LOGIN (LOGIN_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table MANAGED_SYS
--------------------------------------------------------

  ALTER TABLE IAMUSER.MANAGED_SYS ADD CONSTRAINT FK_MNG_SYS_PROV_CON FOREIGN KEY (CONNECTOR_ID)
	  REFERENCES IAMUSER.PROVISION_CONNECTOR (CONNECTOR_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table MANAGED_SYS_ATTRIBUTE
--------------------------------------------------------

  ALTER TABLE IAMUSER.MANAGED_SYS_ATTRIBUTE ADD CONSTRAINT FK_MNG_MNG_ATTR FOREIGN KEY (MANAGED_SYS_ID)
	  REFERENCES IAMUSER.MANAGED_SYS (MANAGED_SYS_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table MD_ELEMENT_VALID_VALUES
--------------------------------------------------------

  ALTER TABLE IAMUSER.MD_ELEMENT_VALID_VALUES ADD CONSTRAINT VALID_VALUES_ELEMENT_FK FOREIGN KEY (METADATA_ELEMENT_ID)
	  REFERENCES IAMUSER.METADATA_ELEMENT (METADATA_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table METADATA_ELEMENT
--------------------------------------------------------

  ALTER TABLE IAMUSER.METADATA_ELEMENT ADD CONSTRAINT FK_MD_ELMT_TYPE_FK FOREIGN KEY (TYPE_ID)
	  REFERENCES IAMUSER.METADATA_TYPE (TYPE_ID) ENABLE;
 
  ALTER TABLE IAMUSER.METADATA_ELEMENT ADD CONSTRAINT METADATA_ELEMENT_RES_FK FOREIGN KEY (RESOURCE_ID)
	  REFERENCES IAMUSER.RES (RESOURCE_ID) ENABLE;
 
  ALTER TABLE IAMUSER.METADATA_ELEMENT ADD CONSTRAINT METADATA_ELEMENT_TEMPLATE_FK FOREIGN KEY (TEMPLATE_ID)
	  REFERENCES IAMUSER.METADATA_ELEMENT_PAGE_TEMPLATE (ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table METADATA_ELEMENT_PAGE_TEMPLATE
--------------------------------------------------------

  ALTER TABLE IAMUSER.METADATA_ELEMENT_PAGE_TEMPLATE ADD CONSTRAINT METADATA_PAGE_TEMPLATE_RES_FK FOREIGN KEY (RESOURCE_ID)
	  REFERENCES IAMUSER.RES (RESOURCE_ID) ENABLE;
 
  ALTER TABLE IAMUSER.METADATA_ELEMENT_PAGE_TEMPLATE ADD CONSTRAINT TEMPLATE_TYPE_FK FOREIGN KEY (TEMPLATE_TYPE_ID)
	  REFERENCES IAMUSER.UI_TEMPLATE_TYPE (TEMPLATE_TYPE_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table METADATA_URI_XREF
--------------------------------------------------------

  ALTER TABLE IAMUSER.METADATA_URI_XREF ADD CONSTRAINT TEMPLATE_PATRN_XREF_PATRN_FK FOREIGN KEY (URI_PATTERN_ID)
	  REFERENCES IAMUSER.URI_PATTERN (URI_PATTERN_ID) ENABLE;
 
  ALTER TABLE IAMUSER.METADATA_URI_XREF ADD CONSTRAINT TEMPLETE_PATTERN_XREF_FK FOREIGN KEY (TEMPLATE_ID)
	  REFERENCES IAMUSER.METADATA_ELEMENT_PAGE_TEMPLATE (ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table MNG_SYS_GROUP
--------------------------------------------------------

  ALTER TABLE IAMUSER.MNG_SYS_GROUP ADD CONSTRAINT REFMANAGED_SYS831 FOREIGN KEY (MANAGED_SYS_ID)
	  REFERENCES IAMUSER.MANAGED_SYS (MANAGED_SYS_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table MNG_SYS_LIST
--------------------------------------------------------

  ALTER TABLE IAMUSER.MNG_SYS_LIST ADD CONSTRAINT REFMANAGED_SYS941 FOREIGN KEY (MANAGED_SYS_ID)
	  REFERENCES IAMUSER.MANAGED_SYS (MANAGED_SYS_ID) ENABLE;
 
  ALTER TABLE IAMUSER.MNG_SYS_LIST ADD CONSTRAINT REFPROV_REQUEST951 FOREIGN KEY (REQUEST_ID)
	  REFERENCES IAMUSER.PROV_REQUEST (REQUEST_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table MNG_SYS_OBJECT_MATCH
--------------------------------------------------------

  ALTER TABLE IAMUSER.MNG_SYS_OBJECT_MATCH ADD CONSTRAINT FK_MNG_SYS_OBJ_MATC FOREIGN KEY (MANAGED_SYS_ID)
	  REFERENCES IAMUSER.MANAGED_SYS (MANAGED_SYS_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ORG_STRUCTURE
--------------------------------------------------------

  ALTER TABLE IAMUSER.ORG_STRUCTURE ADD CONSTRAINT FK_STAFF_USER FOREIGN KEY (STAFF_ID)
	  REFERENCES IAMUSER.USERS (USER_ID) ENABLE;
 
  ALTER TABLE IAMUSER.ORG_STRUCTURE ADD CONSTRAINT FK_SUPR_USER FOREIGN KEY (SUPERVISOR_ID)
	  REFERENCES IAMUSER.USERS (USER_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ORG_TYPE_VALID_MEMBERSHIP
--------------------------------------------------------

  ALTER TABLE IAMUSER.ORG_TYPE_VALID_MEMBERSHIP ADD CONSTRAINT ORG_T_VAL_MEM_SOURCE_FK FOREIGN KEY (ORG_TYPE_ID)
	  REFERENCES IAMUSER.ORGANIZATION_TYPE (ORG_TYPE_ID) ENABLE;
 
  ALTER TABLE IAMUSER.ORG_TYPE_VALID_MEMBERSHIP ADD CONSTRAINT ORG_T_VAL_MEM_TARGET_FK FOREIGN KEY (MEMBER_ORG_TYPE_ID)
	  REFERENCES IAMUSER.ORGANIZATION_TYPE (ORG_TYPE_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table PAGE_TEMPLATE_XREF
--------------------------------------------------------

  ALTER TABLE IAMUSER.PAGE_TEMPLATE_XREF ADD CONSTRAINT MD_ELMT_TEMPLATE_FK FOREIGN KEY (METADATA_ELEMENT_ID)
	  REFERENCES IAMUSER.METADATA_ELEMENT (METADATA_ID) ENABLE;
 
  ALTER TABLE IAMUSER.PAGE_TEMPLATE_XREF ADD CONSTRAINT META_PG_TEMPLATE_FK FOREIGN KEY (TEMPLATE_ID)
	  REFERENCES IAMUSER.METADATA_ELEMENT_PAGE_TEMPLATE (ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table PHONE
--------------------------------------------------------

  ALTER TABLE IAMUSER.PHONE ADD CONSTRAINT PHONE_TYPE_FK FOREIGN KEY (TYPE_ID)
	  REFERENCES IAMUSER.METADATA_TYPE (TYPE_ID) ENABLE;
 
  ALTER TABLE IAMUSER.PHONE ADD CONSTRAINT PHONE_USER_FK FOREIGN KEY (PARENT_ID)
	  REFERENCES IAMUSER.USERS (USER_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table POLICY_ATTRIBUTE
--------------------------------------------------------

  ALTER TABLE IAMUSER.POLICY_ATTRIBUTE ADD CONSTRAINT FK_POLICY_ATTRIBUTE_POLICY FOREIGN KEY (POLICY_ID)
	  REFERENCES IAMUSER.POLICY (POLICY_ID) ENABLE;
 
  ALTER TABLE IAMUSER.POLICY_ATTRIBUTE ADD CONSTRAINT POLI_ATTR_POL_DEF_PARAM FOREIGN KEY (DEF_PARAM_ID)
	  REFERENCES IAMUSER.POLICY_DEF_PARAM (DEF_PARAM_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table POLICY_DEF_PARAM
--------------------------------------------------------

  ALTER TABLE IAMUSER.POLICY_DEF_PARAM ADD CONSTRAINT FK_POLICY_DEF_PARAM_POLICY_DEF FOREIGN KEY (POLICY_DEF_ID)
	  REFERENCES IAMUSER.POLICY_DEF (POLICY_DEF_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table PWD_HISTORY
--------------------------------------------------------

  ALTER TABLE IAMUSER.PWD_HISTORY ADD CONSTRAINT LOGIN_ID_PWD_HIST_FK FOREIGN KEY (LOGIN_ID)
	  REFERENCES IAMUSER.LOGIN (LOGIN_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table RECONCILIATION_SITUATION
--------------------------------------------------------

  ALTER TABLE IAMUSER.RECONCILIATION_SITUATION ADD CONSTRAINT RECON_SITUATION FOREIGN KEY (RECON_CONFIG_ID)
	  REFERENCES IAMUSER.RECONCILIATION_CONFIG (RECON_CONFIG_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table RELATIONSHIP
--------------------------------------------------------

  ALTER TABLE IAMUSER.RELATIONSHIP ADD CONSTRAINT FK_RELATIONSHIP_RELATION_SET FOREIGN KEY (RELATION_SET_ID)
	  REFERENCES IAMUSER.RELATION_SET (RELATION_SET_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table RELATION_CATEGORY
--------------------------------------------------------

  ALTER TABLE IAMUSER.RELATION_CATEGORY ADD CONSTRAINT FK_RELATION_CATEGORY_CATEGORY FOREIGN KEY (CATEGORY_ID)
	  REFERENCES IAMUSER.CATEGORY (CATEGORY_ID) ENABLE;
 
  ALTER TABLE IAMUSER.RELATION_CATEGORY ADD CONSTRAINT REL_CAT_REL_SET_FK FOREIGN KEY (RELATION_SET_ID)
	  REFERENCES IAMUSER.RELATION_SET (RELATION_SET_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table RELATION_SOURCE
--------------------------------------------------------

  ALTER TABLE IAMUSER.RELATION_SOURCE ADD CONSTRAINT FK_RELAT_SOURCE_RELATION_SET FOREIGN KEY (RELATION_SET_ID)
	  REFERENCES IAMUSER.RELATION_SET (RELATION_SET_ID) ENABLE;
 
  ALTER TABLE IAMUSER.RELATION_SOURCE ADD CONSTRAINT FK_RELAT_SOURCE_RELATION_TYPE FOREIGN KEY (RELATION_TYPE_ID)
	  REFERENCES IAMUSER.RELATION_TYPE (RELATION_TYPE_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table REQUEST_ATTACHMENT
--------------------------------------------------------

  ALTER TABLE IAMUSER.REQUEST_ATTACHMENT ADD CONSTRAINT REFPROV_REQUEST1001 FOREIGN KEY (REQUEST_ID)
	  REFERENCES IAMUSER.PROV_REQUEST (REQUEST_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table REQUEST_ATTRIBUTE
--------------------------------------------------------

  ALTER TABLE IAMUSER.REQUEST_ATTRIBUTE ADD CONSTRAINT REFPROV_REQUEST1011 FOREIGN KEY (REQUEST_ID)
	  REFERENCES IAMUSER.PROV_REQUEST (REQUEST_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table REQUEST_USER
--------------------------------------------------------

  ALTER TABLE IAMUSER.REQUEST_USER ADD CONSTRAINT REFPROV_REQUEST931 FOREIGN KEY (REQUEST_ID)
	  REFERENCES IAMUSER.PROV_REQUEST (REQUEST_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table RES
--------------------------------------------------------

  ALTER TABLE IAMUSER.RES ADD CONSTRAINT FK_RESOURCE_CATEGORY FOREIGN KEY (CATEGORY_ID)
	  REFERENCES IAMUSER.CATEGORY (CATEGORY_ID) ENABLE;
 
  ALTER TABLE IAMUSER.RES ADD CONSTRAINT FK_RESOURCE_RESOURCE_TYPE FOREIGN KEY (RESOURCE_TYPE_ID)
	  REFERENCES IAMUSER.RESOURCE_TYPE (RESOURCE_TYPE_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table RESOURCE_GROUP
--------------------------------------------------------

  ALTER TABLE IAMUSER.RESOURCE_GROUP ADD CONSTRAINT FK_RESOURCE_GRP FOREIGN KEY (GRP_ID)
	  REFERENCES IAMUSER.GRP (GRP_ID) ENABLE;
 
  ALTER TABLE IAMUSER.RESOURCE_GROUP ADD CONSTRAINT FK_RESOURCE_GRP_RESOURCE FOREIGN KEY (RESOURCE_ID)
	  REFERENCES IAMUSER.RES (RESOURCE_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table RESOURCE_POLICY
--------------------------------------------------------

  ALTER TABLE IAMUSER.RESOURCE_POLICY ADD CONSTRAINT FK_RESOURCE_POLICY_ROLE FOREIGN KEY (ROLE_ID)
	  REFERENCES IAMUSER.ROLE (ROLE_ID) ENABLE;
 
  ALTER TABLE IAMUSER.RESOURCE_POLICY ADD CONSTRAINT RS_PL_RS_RSID FOREIGN KEY (RESOURCE_ID)
	  REFERENCES IAMUSER.RES (RESOURCE_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table RESOURCE_PRIVILEGE
--------------------------------------------------------

  ALTER TABLE IAMUSER.RESOURCE_PRIVILEGE ADD CONSTRAINT FK_RESOURCE_PRIVILEGE_RES FOREIGN KEY (RESOURCE_ID)
	  REFERENCES IAMUSER.RES (RESOURCE_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table RESOURCE_PROP
--------------------------------------------------------

  ALTER TABLE IAMUSER.RESOURCE_PROP ADD CONSTRAINT FK_RESOURCE_PROP_RESOURCE FOREIGN KEY (RESOURCE_ID)
	  REFERENCES IAMUSER.RES (RESOURCE_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table RESOURCE_ROLE
--------------------------------------------------------

  ALTER TABLE IAMUSER.RESOURCE_ROLE ADD CONSTRAINT FK_RESOURCE_ROLE_RESOURCE FOREIGN KEY (RESOURCE_ID)
	  REFERENCES IAMUSER.RES (RESOURCE_ID) ENABLE;
 
  ALTER TABLE IAMUSER.RESOURCE_ROLE ADD CONSTRAINT FK_RESOURCE_ROLE_ROLE FOREIGN KEY (ROLE_ID)
	  REFERENCES IAMUSER.ROLE (ROLE_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table RESOURCE_USER
--------------------------------------------------------

  ALTER TABLE IAMUSER.RESOURCE_USER ADD CONSTRAINT FK_RESOURCE_USER_USERS FOREIGN KEY (USER_ID)
	  REFERENCES IAMUSER.USERS (USER_ID) ENABLE;
 
  ALTER TABLE IAMUSER.RESOURCE_USER ADD CONSTRAINT RESOURCE_USER_IBFK_1 FOREIGN KEY (RESOURCE_ID)
	  REFERENCES IAMUSER.RES (RESOURCE_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table RES_TO_RES_MEMBERSHIP
--------------------------------------------------------

  ALTER TABLE IAMUSER.RES_TO_RES_MEMBERSHIP ADD CONSTRAINT RES_TO_RES_MEMBERSHIP_IBFK_1 FOREIGN KEY (RESOURCE_ID)
	  REFERENCES IAMUSER.RES (RESOURCE_ID) ENABLE;
 
  ALTER TABLE IAMUSER.RES_TO_RES_MEMBERSHIP ADD CONSTRAINT RES_TO_RES_MEMBERSHIP_IBFK_2 FOREIGN KEY (MEMBER_RESOURCE_ID)
	  REFERENCES IAMUSER.RES (RESOURCE_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ROLE
--------------------------------------------------------

  ALTER TABLE IAMUSER.ROLE ADD CONSTRAINT FK_ROLE_SERVICE FOREIGN KEY (SERVICE_ID)
	  REFERENCES IAMUSER.SECURITY_DOMAIN (DOMAIN_ID) ENABLE;
 
  ALTER TABLE IAMUSER.ROLE ADD CONSTRAINT ROLE_MNG_SYS_FK FOREIGN KEY (MANAGED_SYS_ID)
	  REFERENCES IAMUSER.MANAGED_SYS (MANAGED_SYS_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ROLE_ATTRIBUTE
--------------------------------------------------------

  ALTER TABLE IAMUSER.ROLE_ATTRIBUTE ADD CONSTRAINT FK_ROLE_ROLE_ATTRIBUTE FOREIGN KEY (ROLE_ID)
	  REFERENCES IAMUSER.ROLE (ROLE_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ROLE_POLICY
--------------------------------------------------------

  ALTER TABLE IAMUSER.ROLE_POLICY ADD CONSTRAINT FK_ROLE_POLICY_ROLE FOREIGN KEY (ROLE_ID)
	  REFERENCES IAMUSER.ROLE (ROLE_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ROLE_TO_ROLE_MEMBERSHIP
--------------------------------------------------------

  ALTER TABLE IAMUSER.ROLE_TO_ROLE_MEMBERSHIP ADD CONSTRAINT ROLE_TO_ROLE_MEMBERSHIP_IBFK_1 FOREIGN KEY (ROLE_ID)
	  REFERENCES IAMUSER.ROLE (ROLE_ID) ENABLE;
 
  ALTER TABLE IAMUSER.ROLE_TO_ROLE_MEMBERSHIP ADD CONSTRAINT ROLE_TO_ROLE_MEMBERSHIP_IBFK_2 FOREIGN KEY (MEMBER_ROLE_ID)
	  REFERENCES IAMUSER.ROLE (ROLE_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table SERVICE_CONFIG
--------------------------------------------------------

  ALTER TABLE IAMUSER.SERVICE_CONFIG ADD CONSTRAINT FK_SRV_SRV_CONF FOREIGN KEY (SERVICE_ID)
	  REFERENCES IAMUSER.SERVICE (SERVICE_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table SYNCH_CONFIG_DATA_MAPPING
--------------------------------------------------------

  ALTER TABLE IAMUSER.SYNCH_CONFIG_DATA_MAPPING ADD CONSTRAINT SYNCH_DATA_MAP FOREIGN KEY (SYNCH_CONFIG_ID)
	  REFERENCES IAMUSER.SYNCH_CONFIG (SYNCH_CONFIG_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table UI_FIELD_TEMPLATE_XREF
--------------------------------------------------------

  ALTER TABLE IAMUSER.UI_FIELD_TEMPLATE_XREF ADD CONSTRAINT TEMPLATE_XREF_TEMPLATE_FK FOREIGN KEY (TEMPLATE_ID)
	  REFERENCES IAMUSER.METADATA_ELEMENT_PAGE_TEMPLATE (ID) ENABLE;
 
  ALTER TABLE IAMUSER.UI_FIELD_TEMPLATE_XREF ADD CONSTRAINT TEMPLATE_XREF_UI_FIELD_FK FOREIGN KEY (UI_FIELD_ID)
	  REFERENCES IAMUSER.UI_TEMPLATE_FIELDS (UI_FIELD_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table UI_TEMPLATE_FIELDS
--------------------------------------------------------

  ALTER TABLE IAMUSER.UI_TEMPLATE_FIELDS ADD CONSTRAINT TEMPLATE_TYPE_UI_TEMPL_FK FOREIGN KEY (TEMPLATE_TYPE_ID)
	  REFERENCES IAMUSER.UI_TEMPLATE_TYPE (TEMPLATE_TYPE_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table URI_PATTERN
--------------------------------------------------------

  ALTER TABLE IAMUSER.URI_PATTERN ADD CONSTRAINT URI_PATTERN_AUTH_LEVEL_FK FOREIGN KEY (MIN_AUTH_LEVEL)
	  REFERENCES IAMUSER.AUTH_LEVEL (AUTH_LEVEL_ID) ENABLE;
 
  ALTER TABLE IAMUSER.URI_PATTERN ADD CONSTRAINT URI_PATTERN_CP_FK FOREIGN KEY (CONTENT_PROVIDER_ID)
	  REFERENCES IAMUSER.CONTENT_PROVIDER (CONTENT_PROVIDER_ID) ENABLE;
 
  ALTER TABLE IAMUSER.URI_PATTERN ADD CONSTRAINT URI_PATTERN_RES_FK FOREIGN KEY (RESOURCE_ID)
	  REFERENCES IAMUSER.RES (RESOURCE_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table URI_PATTERN_META
--------------------------------------------------------

  ALTER TABLE IAMUSER.URI_PATTERN_META ADD CONSTRAINT URI_PATTERN_META_META_TYPE_FK FOREIGN KEY (URI_PATTERN_META_TYPE_ID)
	  REFERENCES IAMUSER.URI_PATTERN_META_TYPE (URI_PATTERN_META_TYPE_ID) ENABLE;
 
  ALTER TABLE IAMUSER.URI_PATTERN_META ADD CONSTRAINT URI_PATTERN_META_URI_PATRN_FK FOREIGN KEY (URI_PATTERN_ID)
	  REFERENCES IAMUSER.URI_PATTERN (URI_PATTERN_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table URI_PATTERN_META_VALUE
--------------------------------------------------------

  ALTER TABLE IAMUSER.URI_PATTERN_META_VALUE ADD CONSTRAINT URI_PATTERN_META_VALUE_AM_FK FOREIGN KEY (AM_RES_ATTRIBUTE_ID)
	  REFERENCES IAMUSER.AUTH_RESOURCE_AM_ATTRIBUTE (AM_RES_ATTRIBUTE_ID) ENABLE;
 
  ALTER TABLE IAMUSER.URI_PATTERN_META_VALUE ADD CONSTRAINT URI_PATTERN_META_VALUE_META_FK FOREIGN KEY (URI_PATTERN_META_ID)
	  REFERENCES IAMUSER.URI_PATTERN_META (URI_PATTERN_META_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table USERS
--------------------------------------------------------

  ALTER TABLE IAMUSER.USERS ADD CONSTRAINT FK_USERS_METADATA_TYPE FOREIGN KEY (TYPE_ID)
	  REFERENCES IAMUSER.METADATA_TYPE (TYPE_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table USER_AFFILIATION
--------------------------------------------------------

  ALTER TABLE IAMUSER.USER_AFFILIATION ADD CONSTRAINT FK_USR_ORG_AFFL FOREIGN KEY (COMPANY_ID)
	  REFERENCES IAMUSER.COMPANY (COMPANY_ID) ENABLE;
 
  ALTER TABLE IAMUSER.USER_AFFILIATION ADD CONSTRAINT FK_USR_USER_AFFIL FOREIGN KEY (USER_ID)
	  REFERENCES IAMUSER.USERS (USER_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table USER_ATTACHMENT_REF
--------------------------------------------------------

  ALTER TABLE IAMUSER.USER_ATTACHMENT_REF ADD CONSTRAINT FK_USER_ATTACHMENT_REF_USERS FOREIGN KEY (USER_ID)
	  REFERENCES IAMUSER.USERS (USER_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table USER_ATTRIBUTES
--------------------------------------------------------

  ALTER TABLE IAMUSER.USER_ATTRIBUTES ADD CONSTRAINT FK_USR_ATTR_ELMT_FK FOREIGN KEY (METADATA_ID)
	  REFERENCES IAMUSER.METADATA_ELEMENT (METADATA_ID) ENABLE;
 
  ALTER TABLE IAMUSER.USER_ATTRIBUTES ADD CONSTRAINT USER_ATTRIBUTES_IBFK_1 FOREIGN KEY (USER_ID)
	  REFERENCES IAMUSER.USERS (USER_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table USER_DELEGATION_ATTRIBUTE
--------------------------------------------------------

  ALTER TABLE IAMUSER.USER_DELEGATION_ATTRIBUTE ADD CONSTRAINT USER_DELEG_ATTRIBUTE_IBFK_1 FOREIGN KEY (USER_ID)
	  REFERENCES IAMUSER.USERS (USER_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table USER_GRP
--------------------------------------------------------

  ALTER TABLE IAMUSER.USER_GRP ADD CONSTRAINT FK_USR_GRP_GPR FOREIGN KEY (GRP_ID)
	  REFERENCES IAMUSER.GRP (GRP_ID) ENABLE;
 
  ALTER TABLE IAMUSER.USER_GRP ADD CONSTRAINT FK_USR_GRP_USR FOREIGN KEY (USER_ID)
	  REFERENCES IAMUSER.USERS (USER_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table USER_IDENTITY_ANS
--------------------------------------------------------

  ALTER TABLE IAMUSER.USER_IDENTITY_ANS ADD CONSTRAINT FK_ID_AND_QUEST_FK FOREIGN KEY (IDENTITY_QUESTION_ID)
	  REFERENCES IAMUSER.IDENTITY_QUESTION (IDENTITY_QUESTION_ID) ENABLE;
 
  ALTER TABLE IAMUSER.USER_IDENTITY_ANS ADD CONSTRAINT FK_USER_IDENTITY_ANS_USERS FOREIGN KEY (USER_ID)
	  REFERENCES IAMUSER.USERS (USER_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table USER_KEY
--------------------------------------------------------

  ALTER TABLE IAMUSER.USER_KEY ADD CONSTRAINT FK_USER_KEY_USER_ID FOREIGN KEY (USER_ID)
	  REFERENCES IAMUSER.USERS (USER_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table USER_NOTE
--------------------------------------------------------

  ALTER TABLE IAMUSER.USER_NOTE ADD CONSTRAINT FK_USER_NOTE_USERS FOREIGN KEY (USER_ID)
	  REFERENCES IAMUSER.USERS (USER_ID) ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table USER_ROLE
--------------------------------------------------------

  ALTER TABLE IAMUSER.USER_ROLE ADD CONSTRAINT FK_USR_ROLE_ROLE FOREIGN KEY (ROLE_ID)
	  REFERENCES IAMUSER.ROLE (ROLE_ID) ENABLE;
 
  ALTER TABLE IAMUSER.USER_ROLE ADD CONSTRAINT FK_USR_ROLE_USR FOREIGN KEY (USER_ID)
	  REFERENCES IAMUSER.USERS (USER_ID) ENABLE;
--------------------------------------------------------
--  DDL for Trigger GRP_GRP_MEM_INSERT
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER IAMUSER.GRP_GRP_MEM_INSERT 
BEFORE 
INSERT ON grp_to_grp_membership 
	FOR EACH ROW
	BEGIN
		:NEW.CREATE_DATE := SYSDATE;
		:NEW.UPDATE_DATE := SYSDATE;
	END;
	
/
ALTER TRIGGER IAMUSER.GRP_GRP_MEM_INSERT ENABLE;
--------------------------------------------------------
--  DDL for Trigger ON_ADDRESS_INSERT
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER IAMUSER.ON_ADDRESS_INSERT 
BEFORE 
INSERT ON ADDRESS 
	FOR EACH ROW
	BEGIN
		:NEW.CREATE_DATE := SYSDATE;
		:NEW.LAST_UPDATE := SYSDATE;
	END;
/
ALTER TRIGGER IAMUSER.ON_ADDRESS_INSERT ENABLE;
--------------------------------------------------------
--  DDL for Trigger ON_ADDRESS_UPDATE
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER IAMUSER.ON_ADDRESS_UPDATE 
BEFORE 
UPDATE ON ADDRESS 
	FOR EACH ROW
	BEGIN
		:NEW.LAST_UPDATE := SYSDATE;
	END;
/
ALTER TRIGGER IAMUSER.ON_ADDRESS_UPDATE ENABLE;
--------------------------------------------------------
--  DDL for Trigger ON_BATCH_TASK_INSERT
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER IAMUSER.ON_BATCH_TASK_INSERT 
BEFORE 
INSERT ON BATCH_CONFIG 
	FOR EACH ROW
	BEGIN
    	:NEW.LAST_MODIFIED_DATETIME := CURRENT_TIMESTAMP;
	END;	
/
ALTER TRIGGER IAMUSER.ON_BATCH_TASK_INSERT ENABLE;
--------------------------------------------------------
--  DDL for Trigger ON_BATCH_TASK_UPDATE
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER IAMUSER.ON_BATCH_TASK_UPDATE 
BEFORE 
UPDATE ON BATCH_CONFIG 
	FOR EACH ROW
	BEGIN
    	:NEW.LAST_MODIFIED_DATETIME := CURRENT_TIMESTAMP;
	END;	
/
ALTER TRIGGER IAMUSER.ON_BATCH_TASK_UPDATE ENABLE;
--------------------------------------------------------
--  DDL for Trigger ON_COMPANY_XREF_INSERT
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER IAMUSER.ON_COMPANY_XREF_INSERT 
BEFORE
INSERT ON COMPANY_TO_COMPANY_MEMBERSHIP
	FOR EACH ROW
	BEGIN
		:NEW.CREATE_DATE := SYSDATE;
		:NEW.UPDATE_DATE := SYSDATE;
	END;
/
ALTER TRIGGER IAMUSER.ON_COMPANY_XREF_INSERT ENABLE;
--------------------------------------------------------
--  DDL for Trigger ON_EMAIL_INSERT
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER IAMUSER.ON_EMAIL_INSERT 
BEFORE 
INSERT ON EMAIL_ADDRESS 
	FOR EACH ROW
	BEGIN
		:NEW.CREATE_DATE := SYSDATE;
		:NEW.LAST_UPDATE := SYSDATE;
	END;
/
ALTER TRIGGER IAMUSER.ON_EMAIL_INSERT ENABLE;
--------------------------------------------------------
--  DDL for Trigger ON_EMAIL_UPDATE
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER IAMUSER.ON_EMAIL_UPDATE 
BEFORE 
UPDATE ON EMAIL_ADDRESS 
	FOR EACH ROW
	BEGIN
		:NEW.LAST_UPDATE := SYSDATE;
	END;
/
ALTER TRIGGER IAMUSER.ON_EMAIL_UPDATE ENABLE;
--------------------------------------------------------
--  DDL for Trigger ON_LOGIN_INSERT
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER IAMUSER.ON_LOGIN_INSERT 
BEFORE 
INSERT ON LOGIN 
	FOR EACH ROW
	BEGIN
		:NEW.CREATE_DATE := SYSDATE;
		:NEW.LAST_UPDATE := SYSDATE;
	END;
/
ALTER TRIGGER IAMUSER.ON_LOGIN_INSERT ENABLE;
--------------------------------------------------------
--  DDL for Trigger ON_LOGIN_UPDATE
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER IAMUSER.ON_LOGIN_UPDATE 
BEFORE 
UPDATE ON LOGIN 
	FOR EACH ROW
	BEGIN
		:NEW.LAST_UPDATE := SYSDATE;
	END;
/
ALTER TRIGGER IAMUSER.ON_LOGIN_UPDATE ENABLE;
--------------------------------------------------------
--  DDL for Trigger ON_PHONE_INSERT
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER IAMUSER.ON_PHONE_INSERT 
BEFORE 
INSERT ON PHONE 
	FOR EACH ROW
	BEGIN
		:NEW.CREATE_DATE := SYSDATE;
		:NEW.LAST_UPDATE := SYSDATE;
	END;
/
ALTER TRIGGER IAMUSER.ON_PHONE_INSERT ENABLE;
--------------------------------------------------------
--  DDL for Trigger ON_PHONE_UPDATE
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER IAMUSER.ON_PHONE_UPDATE 
BEFORE 
UPDATE ON PHONE 
	FOR EACH ROW
	BEGIN
		:NEW.LAST_UPDATE := SYSDATE;
	END;
/
ALTER TRIGGER IAMUSER.ON_PHONE_UPDATE ENABLE;
--------------------------------------------------------
--  DDL for Trigger ON_USER_INSERT
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER IAMUSER.ON_USER_INSERT 
BEFORE 
INSERT ON USERS 
	FOR EACH ROW
	BEGIN
		:NEW.CREATE_DATE := SYSDATE;
		:NEW.LAST_UPDATE := SYSDATE;
	END;
/
ALTER TRIGGER IAMUSER.ON_USER_INSERT ENABLE;
--------------------------------------------------------
--  DDL for Trigger ON_USER_UPDATE
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER IAMUSER.ON_USER_UPDATE 
BEFORE 
UPDATE ON USERS 
	FOR EACH ROW
	BEGIN
		:NEW.LAST_UPDATE := SYSDATE;
	END;
/
ALTER TRIGGER IAMUSER.ON_USER_UPDATE ENABLE;
--------------------------------------------------------
--  DDL for Trigger RES_RES_MEM_INSERT
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER IAMUSER.RES_RES_MEM_INSERT 
BEFORE
INSERT ON res_to_res_membership
	FOR EACH ROW
	BEGIN
		:NEW.CREATE_DATE := SYSDATE;
		:NEW.UPDATE_DATE := SYSDATE;
	END;
/
ALTER TRIGGER IAMUSER.RES_RES_MEM_INSERT ENABLE;
--------------------------------------------------------
--  DDL for Trigger ROLE_ROLE_MEM_INSERT
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER IAMUSER.ROLE_ROLE_MEM_INSERT 
BEFORE 
INSERT ON role_to_role_membership 
	FOR EACH ROW
	BEGIN
		:NEW.CREATE_DATE := SYSDATE;
		:NEW.UPDATE_DATE := SYSDATE;
	END;
/
ALTER TRIGGER IAMUSER.ROLE_ROLE_MEM_INSERT ENABLE;
