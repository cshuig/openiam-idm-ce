use openiam;


DELIMITER $$

DROP PROCEDURE IF EXISTS languageLocalization$$

CREATE PROCEDURE languageLocalization()
	BEGIN

	DELETE FROM LANGUAGE_LOCALE where ID='1';
	UPDATE LANGUAGE SET IS_USED='N' WHERE ID='4';

	IF ((SELECT count(*) FROM LANGUAGE WHERE ID='8') = 0) THEN
    		INSERT INTO LANGUAGE (ID, LANGUAGE, IS_USED, LANGUAGE_CODE, IS_DEFAULT) VALUES ('8', 'Russian', 'Y', 'ru', 'N');
    		INSERT INTO LANGUAGE_LOCALE (ID, LANGUAGE_ID, LOCALE) VALUES ('10', '8', 'ru_RU');
	END IF;


  DELETE FROM LANGUAGE_MAPPING WHERE REFERENCE_TYPE='LanguageEntity';

  INSERT INTO LANGUAGE_MAPPING (ID, LANGUAGE_ID, REFERENCE_ID, REFERENCE_TYPE, TEXT_VALUE)
  VALUES ('LanguageEntity1', '1', '1', 'LanguageEntity', 'English'),
	     ('LanguageEntity2', '4', '1', 'LanguageEntity', 'Inglés'),
       ('LanguageEntity3', '8', '1', 'LanguageEntity', 'Английский'),
	     ('LanguageEntity4', '1', '4', 'LanguageEntity', 'Spanish'),
	     ('LanguageEntity5', '4', '4', 'LanguageEntity', 'Español'),
       ('LanguageEntity6', '8', '4', 'LanguageEntity', 'Испанский'),
       ('LanguageEntity7', '1', '8', 'LanguageEntity', 'Russian'),
	     ('LanguageEntity8', '4', '8', 'LanguageEntity', 'Ruso'),
       ('LanguageEntity9', '8', '8', 'LanguageEntity', 'Русский');

	END$$
DELIMITER ;

call languageLocalization();

DROP PROCEDURE languageLocalization;




