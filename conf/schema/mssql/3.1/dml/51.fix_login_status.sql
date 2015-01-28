use openiam;

update LOGIN SET STATUS=null where STATUS not in ('ACTIVE','INACTIVE');