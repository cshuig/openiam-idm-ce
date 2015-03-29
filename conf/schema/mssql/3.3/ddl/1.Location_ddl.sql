use openiam;

Declare @v_constraintname NVARCHAR(max)
set @v_constraintname ='ALTER TABLE LOCATION DROP CONSTRAINT '
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
and t_obj.name='LOCATION' and col.name='ACTIVE')
exec(@v_constraintname)

ALTER TABLE LOCATION ALTER COLUMN ACTIVE CHAR(1) NULL;
ALTER TABLE LOCATION ADD DEFAULT 'Y' FOR ACTIVE;
ALTER TABLE LOCATION ALTER COLUMN NAME NVARCHAR(100) NULL;
ALTER TABLE LOCATION ALTER COLUMN COUNTRY NVARCHAR(100) NULL;
ALTER TABLE LOCATION ALTER COLUMN CITY NVARCHAR(100) NULL;
ALTER TABLE LOCATION ALTER COLUMN STATE NVARCHAR(100) NULL;
ALTER TABLE LOCATION ALTER COLUMN POSTAL_CD NVARCHAR(100) NULL;
ALTER TABLE LOCATION ALTER COLUMN BLDG_NUM NVARCHAR(100) NULL;

ALTER TABLE ADDRESS ADD COPY_FROM_LOCATION_ID NVARCHAR(32) NULL;


