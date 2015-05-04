import org.openiam.idm.srvc.grp.dto.Group
import org.openiam.idm.srvc.grp.ws.GroupDataWebService

println("================ an_group_scope.groovy starting..");

output = null

def groupWS = context?.getBean('groupWS') as GroupDataWebService
def group = binding.hasVariable('group')? group as Group : null

if (groupWS && group?.id) {
    group = groupWS.getGroup(group.id, null)
}

def scope = group?.adGroupScopeId?: 'Domain_Local'
println("=========================== scope.groovy===== scope="+scope +", group="+group)
if ('Domain_Local'.equalsIgnoreCase(scope)) {
    output = 'DomainLocal'
} else if ('Global'.equalsIgnoreCase(scope)) {
    output = 'Global'
} else if ('Universal'.equalsIgnoreCase(scope)) {
    output = 'Universal'
}

println("================ scope.groovy output="+output);