use openiam;

update openiam.LOGIN SET STATUS=null where STATUS not in ('ACTIVE','INACTIVE');