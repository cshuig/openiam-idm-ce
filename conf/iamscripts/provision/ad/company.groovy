import org.openiam.idm.srvc.org.service.OrganizationDataService

def orgManager = context.getBean("orgManager") as OrganizationDataService

output = null
def orgList = orgManager.getOrganizationsForUserByTypeLocalized(user.id, null, "ORGANIZATION", null)
if (orgList) {
    output = orgList.get(0).name
}
