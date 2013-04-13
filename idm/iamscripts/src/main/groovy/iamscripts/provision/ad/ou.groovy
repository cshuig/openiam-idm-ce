import org.openiam.idm.srvc.role.dto.Role
import org.springframework.context.ApplicationContext
import org.openiam.idm.srvc.role.service.RoleDataService
import org.openiam.idm.srvc.role.dto.RoleAttribute



List<String> roleStrList = new ArrayList<String>();
def List<Role> roleList = user.getMemberOfRoles();

ApplicationContext ac =  context;
//RoleDataService roleDataService = (RoleDataService)ac.getBean("roleWS");

println("user roles =" + roleList);

if (roleList != null) {
    // process only the first role otherwise we may end up with multiple OUs
    Role r = roleList.get(0);
    Set<RoleAttribute> attributeSet = r.getRoleAttributes();
    for ( RoleAttribute ra : attributeSet ) {

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



