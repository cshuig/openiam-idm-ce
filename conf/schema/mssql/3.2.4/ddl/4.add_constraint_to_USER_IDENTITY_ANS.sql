use openiam;


DECLARE @u_id VARCHAR(32);
DECLARE @q_id VARCHAR(32);
DECLARE cur1 CURSOR FOR (SELECT IDENTITY_QUESTION_ID, USER_ID FROM USER_IDENTITY_ANS);
OPEN cur1;

FETCH NEXT FROM cur1 INTO @q_id, @u_id;
WHILE @@FETCH_STATUS = 0
BEGIN
	IF ((SELECT count(*) FROM USER_IDENTITY_ANS WHERE IDENTITY_QUESTION_ID=@q_id AND USER_ID=@u_id) > 1) 
	BEGIN
		DELETE FROM USER_IDENTITY_ANS WHERE IDENTITY_QUESTION_ID=@q_id AND USER_ID=@u_id;
	END;
END;
CLOSE cur1;


ALTER TABLE USER_IDENTITY_ANS ADD CONSTRAINT UNQ_ROW UNIQUE(IDENTITY_QUESTION_ID, USER_ID);
