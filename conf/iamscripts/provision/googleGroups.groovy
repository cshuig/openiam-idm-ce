import org.openiam.base.AttributeOperationEnum
import org.openiam.base.BaseAttribute
import org.openiam.base.BaseAttributeContainer
import org.openiam.dozer.converter.GroupDozerConverter
import org.openiam.idm.srvc.grp.dto.Group
import org.openiam.idm.srvc.grp.service.GroupDataService
import org.openiam.idm.srvc.role.dto.Role
import org.openiam.idm.srvc.role.service.RoleDataService


List <String> result = new ArrayList<String>();
def groups = (binding.hasVariable("currentGroupList")) ? currentGroupList : [] as List
groups?.each {  g-> result.add (g.name) ;}
output = result;
