if(targetSystemIdentity != null && targetSystemIdentity.length() >0) {
   name=targetSystemIdentity;
} else {
   name=user.firstName + " " + user.lastName
}


loginId = matchParam.keyField + "=" + name  + "," + matchParam.baseDn;

output=loginId


