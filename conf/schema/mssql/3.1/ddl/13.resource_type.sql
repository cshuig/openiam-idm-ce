use openiam;

ALTER TABLE RESOURCE_TYPE ADD IMAGE_TYPE NVARCHAR(16) NULL DEFAULT NULL;

Declare @v_constraintname varchar(max)
set @v_constraintname ='ALTER TABLE RESOURCE_TYPE DROP CONSTRAINT '
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
and t_obj.name='RESOURCE_TYPE' and col.name='URL')
exec(@v_constraintname)

ALTER TABLE RESOURCE_TYPE ALTER COLUMN URL NVARCHAR(MAX) NULL 
ALTER TABLE RESOURCE_TYPE ADD DEFAULT NULL FOR URL;
