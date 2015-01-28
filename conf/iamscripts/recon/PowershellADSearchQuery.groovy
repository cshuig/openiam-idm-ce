
def sf = binding.hasVariable("searchFilter")? searchFilter : "{Name -like '*'}"
def us = binding.hasVariable("updatedSince")? updatedSince : ""

//import org.openiam.idm.srvc.role.dto.RoleAttribute
//LDAP query
output = "Get-ADUser -LDAPFilter '${sf}' -Properties *" as String
/*if(role != null) {
    Set<RoleAttribute> attributes =  role.getRoleAttributes();
    for(RoleAttribute attr : attributes) {
        if("AD_OU".equals(attr.getName())) {
            output="Get-ADUser -Filter {Name -like '*'} -SearchBase '"+attr.getValue()+"' -Properties * -searchscope 1";
            break;
        };
    }
} else if(group != null) {
    output="Get-ADGroupMember -Identity "+group.name;
}*/
