import java.util.ArrayList;
import java.util.List; 
import org.openiam.idm.srvc.grp.dto.Group;
import org.openiam.idm.srvc.user.dto.User;
import org.openiam.idm.srvc.grp.ws.GroupDataWebService;
import org.openiam.idm.groovy.helper.ServiceHelper;

import org.openiam.base.BaseAttribute;
import org.openiam.base.BaseAttributeContainer;

def GroupDataWebService groupService = ServiceHelper.groupService();
def Group grp;


def List<Group> groupList = user.getMemberOfGroups();

BaseAttributeContainer attributeContainer = new BaseAttributeContainer();

if (groupList != null) {
	if (groupList.size() > 0)  {
		for (Group r : groupList) {
			String groupName = r.grpName;
			
			if (groupName == null) {
			
				grp =  groupService.getGroup(r.grpId).getGroup();

			}

			attributeContainer.getAttributeList().add(new BaseAttribute(grp.externalGroupName, grp.externalGroupName, r.operation));
			
			
		}

		output = attributeContainer;
	}else {
		output = null;
	}
}else {
	output = null;
}

