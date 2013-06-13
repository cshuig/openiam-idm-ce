import org.openiam.idm.srvc.role.dto.Role
import org.springframework.context.ApplicationContext
import org.openiam.idm.srvc.role.dto.RoleAttribute
import org.openiam.idm.srvc.role.ws.RoleDataWebService



println("Building ou string");

List<String> roleStrList = new ArrayList<String>();
def List<Role> roleList = user.getMemberOfRoles();

ApplicationContext ac =  context;
RoleDataWebService roleDataService = (RoleDataWebService)ac.getBean("roleWS");


println("user roles =" + roleList);
output = matchParam.baseDn;
//reset output from old value
if (roleList != null) {
    // process only the first role otherwise we may end up with multiple OUs
    Role r = roleList.get(0);
    RoleAttribute[] attributeArr = roleDataService.getAllAttributes(r.getId().getServiceId(), r.getId().getRoleId()).getRoleAttrAry();
    for ( RoleAttribute ra : attributeArr ) {

        if ("AD_OU".equalsIgnoreCase(ra.getName())) {
            output = ra.getValue();

            println("Using role OU=" + output);

            return;

        }

    }
}

if (output == null || output.isEmpty()) {
    output = matchParam.baseDn;
}
println("default ou" + output);



