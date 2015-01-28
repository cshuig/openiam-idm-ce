use openiam;

DELIMITER $$

DROP PROCEDURE IF EXISTS SUPERVISOR_DATA_TRANSFER$$
CREATE PROCEDURE SUPERVISOR_DATA_TRANSFER()
    BEGIN

        DECLARE counter INT DEFAULT 3000;
        DECLARE finished INTEGER DEFAULT 0;
        DECLARE employee VARCHAR(32) DEFAULT "";
        DECLARE supervisor VARCHAR(32) DEFAULT "";
        DECLARE user_cursor CURSOR FOR SELECT USER_ID, MANAGER_ID FROM USERS;
        DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = 1;

        IF EXISTS (SELECT * FROM information_schema.COLUMNS WHERE TABLE_NAME='USERS' AND COLUMN_NAME='MANAGER_ID') THEN

            OPEN user_cursor;
       
            employee_loop: LOOP
                FETCH user_cursor INTO employee, supervisor;
                IF (supervisor IS NOT NULL) THEN
                    IF NOT EXISTS (SELECT * FROM ORG_STRUCTURE WHERE SUPERVISOR_ID=supervisor AND STAFF_ID=employee) THEN
                        loop_counter: LOOP
                            SET counter=counter+1;
                            IF EXISTS (SELECT * FROM ORG_STRUCTURE WHERE ORG_STRUCTURE_ID=counter) THEN
                                ITERATE loop_counter;
                            ELSE
                                LEAVE loop_counter;
                            END IF;
                        END LOOP loop_counter;
                        INSERT INTO ORG_STRUCTURE (ORG_STRUCTURE_ID, SUPERVISOR_ID, STAFF_ID) VALUES (counter, supervisor, employee);
                    END IF;
                END IF;

                IF finished = 1 THEN
                    LEAVE employee_loop;
                END IF;

            END LOOP employee_loop;

            CLOSE user_cursor;

            ALTER TABLE USERS DROP COLUMN MANAGER_ID;

        END IF;

    END$$
DELIMITER ;

call SUPERVISOR_DATA_TRANSFER();
DROP PROCEDURE SUPERVISOR_DATA_TRANSFER;
