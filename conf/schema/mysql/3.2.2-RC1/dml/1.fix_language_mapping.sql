use openiam;

delete from LANGUAGE_MAPPING
where ID in ('DEMetadataTypeEntity10','DEMetadataTypeEntity20',
             'DEMetadataTypeEntity59','DEMetadataTypeEntity60',
			 'DEMetadataTypeEntity84',
             'ESMetadataTypeEntity10','ESMetadataTypeEntity20',
             'ESMetadataTypeEntity59','ESMetadataTypeEntity60',
			 'ESMetadataTypeEntity84',
             'RUMetadataTypeEntity82','RUMetadataTypeEntity83',
             'RUMetadataTypeEntity84','RUMetadataTypeEntity85',
             'RUMetadataTypeEntity87');

INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('RUABOUT_DESC','8','ABOUT','ResourceEntity.displayNameMap','Об OpenIAM');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('RUABOUT_ROOT_MENU_DESC','8','ABOUT_ROOT_MENU','ResourceEntity.displayNameMap','Об OpenIAM');
INSERT INTO LANGUAGE_MAPPING (ID,LANGUAGE_ID,REFERENCE_ID,REFERENCE_TYPE,TEXT_VALUE) VALUES ('RUSYNCUSER_REVIEW_DESC','8','SYNCUSER_REVIEW','ResourceEntity.displayNameMap','Проверка сихронизации');
