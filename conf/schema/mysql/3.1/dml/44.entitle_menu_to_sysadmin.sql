use openiam;

DELIMITER $$

DROP PROCEDURE IF EXISTS entitleMenuToSysAdmin$$

CREATE PROCEDURE entitleMenuToSysAdmin()
	BEGIN
    IF NOT EXISTS (SELECT 1 FROM ROLE WHERE ROLE_ID = '9') THEN
      insert into ROLE(ROLE_ID, ROLE_NAME)
        VALUES ('9','Super Security Admin');
    END IF;

    IF NOT EXISTS (SELECT 1 FROM GRP WHERE GRP_ID = 'SUPER_SEC_ADMIN_GRP') THEN
      insert into GRP(GRP_ID, GRP_NAME)
      VALUES ('SUPER_SEC_ADMIN_GRP','Super Admin Group');
    END IF;

    update ROLE SET DESCRIPTION='Super Security Admin', STATUS='ACTIVE';

    delete from RESOURCE_ROLE where ROLE_ID='9' and RESOURCE_ID in (SELECT RESOURCE_ID FROM RES WHERE RESOURCE_TYPE_ID='MENU_ITEM');

    insert into RESOURCE_ROLE(RESOURCE_ID, ROLE_ID)
      select RESOURCE_ID, '9' FROM RES WHERE RESOURCE_TYPE_ID='MENU_ITEM';

    delete from GRP_ROLE WHERE GRP_ID='SUPER_SEC_ADMIN_GRP' and ROLE_ID='9';

    insert into GRP_ROLE(GRP_ID, ROLE_ID)
      values ('SUPER_SEC_ADMIN_GRP', '9');

    delete from USER_ROLE WHERE USER_ID='3000' and ROLE_ID='9';

    insert into USER_ROLE(USER_ID, ROLE_ID)
      values ('3000', '9');

	END$$
DELIMITER ;

call entitleMenuToSysAdmin();

DROP PROCEDURE entitleMenuToSysAdmin;

