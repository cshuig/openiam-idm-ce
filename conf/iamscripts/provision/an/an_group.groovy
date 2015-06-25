import org.openiam.idm.srvc.role.ws.RoleDataWebService

RoleDataWebService roleDataWebService = context.getBean("roleWS") as RoleDataWebService

def ORG_ROLE_TYPE_ID = "2c94b2574a7c3454014a9608add231df"
output = null

def userOrgRole = user.roles.find({ ORG_ROLE_TYPE_ID == it.mdTypeId })
if (user.id && ! userOrgRole) {
    def roles = roleDataWebService.getRolesForUser(user.id, "3000", false, 0, 10)
    userOrgRole = roles.find({ ORG_ROLE_TYPE_ID == it.mdTypeId })
}

println("================ an-group.groovy  UserId=" + user.id)
println("================ an-group.groovy  userADRole =" +  userOrgRole)

if (userOrgRole) {

}