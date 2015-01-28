import org.openiam.idm.srvc.role.dto.Role;
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.openiam.idm.srvc.role.ws.RoleDataWebService
import org.openiam.idm.srvc.role.dto.RoleAttribute;


def RoleDataWebService roleWS = (RoleDataWebService)context.getBean("roleWS")


List<String> roleStrList = new ArrayList<String>();
def List<Role> roleList = user.getMemberOfRoles();
println("user roles =" + roleList);

if (roleList != null) {
	if (roleList.size() > 0)  {
		Role r = roleList.get(0);

            // GET ORACLE ROLE NAME
            Role roleObj = roleWS.getRole( r.id.serviceId, r.id.roleId).role;
            Set<RoleAttribute> attrSet =  roleObj.getRoleAttributes();
            if (!attrSet.isEmpty()) {
                for (RoleAttribute atr : attrSet) {
                    if ("PEOPLESOFT_ROLE".equalsIgnoreCase( atr.getName())) {
                        output = atr.value;
                        return;

                    }

                }
            }

		output = null;
	}else {
		output = null;
	}
}else {
	output = null;
}

