
def sf = (binding.hasVariable("searchFilter")? searchFilter : null)?: "(&(objectclass=inetOrgPerson)(cn=*))"
// def us = binding.hasVariable("updatedSince")? updatedSince : null

output= sf as String

/*if(role) {
    output="(&(objectCategory=person)(objectClass=user))"
} else if(group) {
    output="(&(&(objectCategory=person)(objectClass=user))(memberOf="+ group.externalGroupName +"))"
}*/
