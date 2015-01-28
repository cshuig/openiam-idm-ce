
def sf = binding.hasVariable("searchFilter")? searchFilter : "(&(objectClass=groupOfUniqueNames)(cn=*))"
def us = binding.hasVariable("updatedSince")? updatedSince : ""

output= sf as String

/*if(role) {
    output="(&(objectCategory=person)(objectClass=user))"
} else if(group) {
    output="(&(&(objectCategory=person)(objectClass=user))(memberOf="+ group.externalGroupName +"))"
}*/
