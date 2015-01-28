import org.openiam.idm.srvc.grp.dto.Group
import org.openiam.idm.srvc.grp.dto.GroupAttribute
import org.openiam.idm.srvc.grp.ws.GroupDataWebService

def groupWS = context?.getBean("groupWS") as GroupDataWebService
def group = binding.hasVariable('group')? group as Group : null

if (groupWS && group?.id) {
    group = groupWS.getGroup(group.id, null)
}

output = group?.attributes?.find {GroupAttribute ga-> ga.name == 'AD_PATH'}?.value

