import org.openiam.idm.groovy.helper.ServiceHelper;
import java.util.List;
import org.openiam.idm.srvc.org.dto.Organization;

def orgService = ServiceHelper.orgService();
output = null
def List<Organization> orgList = orgService.getOrganizationsForUserByTypeLocalized(user.id, null, "ORGANIZATION", null);
if(orgList != null && orgList.size() > 0) {
   	def org = orgList.get(0);
	if (org) {
		output = org.name;
	}
}