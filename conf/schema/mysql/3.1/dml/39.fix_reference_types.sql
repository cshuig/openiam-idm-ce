use openiam;

UPDATE LANGUAGE_MAPPING SET REFERENCE_TYPE='MetadataTypeEntity.displayNameMap' WHERE REFERENCE_TYPE='MetadataTypeEntity';
UPDATE LANGUAGE_MAPPING SET REFERENCE_TYPE='LanguageEntity.displayNameMap' WHERE REFERENCE_TYPE='LanguageEntity';
UPDATE LANGUAGE_MAPPING SET REFERENCE_TYPE='MetadataElementEntity.languageMap' WHERE REFERENCE_TYPE='MetadataElementEntity';
UPDATE LANGUAGE_MAPPING SET REFERENCE_TYPE='MetadataElementEntity.defaultValueLanguageMap' WHERE REFERENCE_TYPE='MetadataElementDefaultValues';
UPDATE LANGUAGE_MAPPING SET REFERENCE_TYPE='MetadataFieldTemplateXrefEntity.languageMap' WHERE REFERENCE_TYPE='MetadataFieldTemplateXrefEntity';
UPDATE LANGUAGE_MAPPING SET REFERENCE_TYPE='MetadataValidValueEntity.languageMap' WHERE REFERENCE_TYPE='MetadataValidValueEntity';
UPDATE LANGUAGE_MAPPING SET REFERENCE_TYPE='OrganizationTypeEntity.displayNameMap' WHERE REFERENCE_TYPE='OrganizationTypeEntity';
UPDATE LANGUAGE_MAPPING SET REFERENCE_TYPE='IdentityQuestionEntity.displayNameMap' WHERE REFERENCE_TYPE='IdentityQuestionEntity';
UPDATE LANGUAGE_MAPPING SET REFERENCE_TYPE='ResourceEntity.displayNameMap' WHERE REFERENCE_TYPE='ResourceEntity';
UPDATE LANGUAGE_MAPPING SET REFERENCE_TYPE='ResourceTypeEntity.displayNameMap' WHERE REFERENCE_TYPE='ResourceTypeEntity';