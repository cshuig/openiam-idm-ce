import org.openiam.idm.srvc.role.dto.RoleAttribute
if(role != null) {
    Set<RoleAttribute> attributes =  role.getRoleAttributes();
    for(RoleAttribute attr : attributes) {
        if("AD_OU".equals(attr.getName())) {
            output="(&(&(objectCategory=person)(objectClass=user))(memberOf="+ attr.getValue() +"))";
        };
    }
} else if(group != null) {
    output="(&(&(objectCategory=person)(objectClass=user))(memberOf="+ gr.externalGroupName +"))";
}
