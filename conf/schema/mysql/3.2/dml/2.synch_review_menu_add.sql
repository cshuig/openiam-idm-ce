use openiam;


DELETE FROM RESOURCE_GROUP WHERE RESOURCE_ID = 'SYNCUSER_REVIEW';
DELETE FROM RESOURCE_USER WHERE RESOURCE_ID = 'SYNCUSER_REVIEW';
DELETE FROM RESOURCE_ROLE WHERE RESOURCE_ID = 'SYNCUSER_REVIEW';
DELETE FROM RESOURCE_PROP WHERE RESOURCE_ID = 'SYNCUSER_REVIEW';
DELETE FROM res_to_res_membership WHERE MEMBER_RESOURCE_ID = 'SYNCUSER_REVIEW';
DELETE FROM RES WHERE RESOURCE_ID = 'SYNCUSER_REVIEW';
DELETE FROM LANGUAGE_MAPPING WHERE REFERENCE_ID='SYNCUSER_REVIEW';

INSERT INTO RES (RESOURCE_ID, RESOURCE_TYPE_ID, NAME, DESCRIPTION, DISPLAY_ORDER, IS_PUBLIC, URL) VALUES('SYNCUSER_REVIEW', 'MENU_ITEM', 'SYNCUSER_REVIEW', 'Synchronization Review', 10, 'N', '/webconsole-idm/provisioning/synchReviewList.html');

INSERT INTO RESOURCE_PROP (RESOURCE_PROP_ID, RESOURCE_ID, NAME, ATTR_VALUE) VALUES ('SYNCUSER_REVIEW_PUB', 'SYNCUSER_REVIEW', 'MENU_IS_PUBLIC', 'true');
INSERT INTO RESOURCE_PROP (RESOURCE_PROP_ID, RESOURCE_ID, NAME, ATTR_VALUE) VALUES ('SYNCUSER_REVIEW_DESC', 'SYNCUSER_REVIEW', 'MENU_DISPLAY_NAME', 'Synchronization Review');

INSERT INTO res_to_res_membership (RESOURCE_ID, MEMBER_RESOURCE_ID) VALUES('SYNCUSER_EDIT_MENU_ROOT', 'SYNCUSER_REVIEW');


DELIMITER $$

DROP PROCEDURE IF EXISTS ADD_MENU_ENTITLEMENTS$$
CREATE PROCEDURE ADD_MENU_ENTITLEMENTS()
	BEGIN
 		IF EXISTS (SELECT * FROM ROLE WHERE ROLE_ID='9') THEN
			IF NOT EXISTS (SELECT * FROM RESOURCE_ROLE WHERE ROLE_ID='9' and RESOURCE_ID='SYNCUSER_REVIEW') THEN
				INSERT INTO RESOURCE_ROLE (ROLE_ID, RESOURCE_ID) VALUES('9', 'SYNCUSER_REVIEW');
			END IF;
 		END IF;

	END$$
DELIMITER ;

call ADD_MENU_ENTITLEMENTS();
DROP PROCEDURE ADD_MENU_ENTITLEMENTS;

DELIMITER $$

DROP PROCEDURE IF EXISTS LOCALIZE_MENU_ITEM$$
CREATE PROCEDURE LOCALIZE_MENU_ITEM()
	BEGIN
		IF NOT EXISTS (SELECT * FROM LANGUAGE_MAPPING WHERE REFERENCE_ID='SYNCUSER_REVIEW') THEN
			INSERT INTO LANGUAGE_MAPPING(ID, LANGUAGE_ID, REFERENCE_TYPE, REFERENCE_ID, TEXT_VALUE) VALUES('SYNCUSER_REVIEW_DESC', '1', 'ResourceEntity.displayNameMap', 'SYNCUSER_REVIEW', 'Synchronization Review');
		END IF;
	END$$
DELIMITER ;

call LOCALIZE_MENU_ITEM();
DROP PROCEDURE LOCALIZE_MENU_ITEM;