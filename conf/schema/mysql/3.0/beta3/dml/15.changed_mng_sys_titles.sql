use openiam;

UPDATE RESOURCE_PROP SET PROP_VALUE = 'Managed System' WHERE PROP_VALUE = 'Managed Resource'; 
UPDATE RESOURCE_PROP SET PROP_VALUE = 'Create Managed System' WHERE PROP_VALUE = 'Create New Managed System';
UPDATE RESOURCE_PROP SET PROP_VALUE = 'Managed system rules' WHERE PROP_VALUE = 'Manages system rules'; 