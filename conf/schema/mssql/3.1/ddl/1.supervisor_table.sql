use openiam;

IF EXISTS (SELECT * FROM information_schema.COLUMNS WHERE TABLE_CATALOG='openiam' and table_name = 'ORG_STRUCTURE' AND column_name  = 'ORG_STRUCTURE_ID') 
BEGIN
	DECLARE @table NVARCHAR(512), @sql NVARCHAR(MAX);
	SELECT @table = 'ORG_STRUCTURE';
	SELECT @sql = 'ALTER TABLE ' + @table 
		+ ' DROP CONSTRAINT ' + name + ';'
		FROM sys.key_constraints
		WHERE [type] = 'PK'
		AND [parent_object_id] = OBJECT_ID(@table);
	EXEC sp_executeSQL @sql;
	
	ALTER TABLE ORG_STRUCTURE DROP COLUMN COMMENTS; 
	ALTER TABLE ORG_STRUCTURE DROP COLUMN STATUS;
	ALTER TABLE ORG_STRUCTURE DROP COLUMN END_DATE; 
	ALTER TABLE ORG_STRUCTURE DROP COLUMN START_DATE; 
	ALTER TABLE ORG_STRUCTURE DROP COLUMN SUPERVISOR_TYPE; 
	ALTER TABLE ORG_STRUCTURE DROP COLUMN ORG_STRUCTURE_ID; 
	
	Declare @v_constraintname NVARCHAR(max)
	set @v_constraintname ='ALTER TABLE ORG_STRUCTURE DROP CONSTRAINT '
	set @v_constraintname = @v_constraintname + (select c_obj.name as CONSTRAINT_NAME
	from sysobjects c_obj
	join syscomments com on c_obj.id = com.id
	join sysobjects t_obj on c_obj.parent_obj = t_obj.id 
	join sysconstraints con on c_obj.id = con.constid
	join syscolumns col on t_obj.id = col.id
	and con.colid = col.colid
	where
	c_obj.uid = user_id()
	and c_obj.xtype = 'D'
	and t_obj.name='ORG_STRUCTURE' and col.name='IS_PRIMARY_SUPER')
	exec(@v_constraintname)

	ALTER TABLE ORG_STRUCTURE ALTER COLUMN IS_PRIMARY_SUPER NVARCHAR(1) NOT NULL;
	ALTER TABLE ORG_STRUCTURE ADD DEFAULT 'N' FOR IS_PRIMARY_SUPER;
	ALTER TABLE ORG_STRUCTURE ADD PRIMARY KEY (SUPERVISOR_ID, STAFF_ID);

	UPDATE ORG_STRUCTURE 
	SET IS_PRIMARY_SUPER='Y'
	WHERE IS_PRIMARY_SUPER='1';

	UPDATE ORG_STRUCTURE 
	SET IS_PRIMARY_SUPER='N'
	WHERE IS_PRIMARY_SUPER='0';
END;
	









