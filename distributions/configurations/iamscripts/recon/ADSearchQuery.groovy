import org.openiam.idm.srvc.role.dto.RoleAttribute
def output="";
if(role != null){
    Set<RoleAttribute> attributes =  role.getRoleAttributes();
    for(RoleAttribute attr : attributes) {
        if("AD_OU".equals(attr.getName())) {
            output="Get-ADUser -Filter {Name -like '*'} -SearchBase '"+attr.getValue()+"'";
        };
    }
} else if(group != null) {
    output="Get-ADGroupMember -Identity "+group.grpName;
}