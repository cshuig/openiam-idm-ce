import org.openiam.idm.srvc.res.dto.Resource
import org.openiam.provision.dto.ProvisionUser
import org.openiam.provision.service.AbstractResourceOrderProcessor

public class ProvisionServiceResourceOrderProcessor extends AbstractResourceOrderProcessor {

    def priorDelResNames = ['POWERSHELL-EXCHANGE', 'POWERSHELL-AD']
    def priorAddResNames = ['POWERSHELL-AD', 'POWERSHELL-EXCHANGE']

    @Override
    public List<Resource> orderDeprovisionResources(ProvisionUser pUser, Set<Resource> resources, Map<String, Object> bindingMap) {
        orderResources(resources, priorDelResNames)
    }

    @Override
    public List<Resource> orderProvisionResources(ProvisionUser pUser, Set<Resource> resources, Map<String, Object> bindingMap) {
        orderResources(resources, priorAddResNames)
    }

    def orderResources(Set<Resource> resources, List<String> priorities) {
        if (resources) {
            def col = resources as List
            Collections.sort(col, new OrderComparator(priorities))
            return col
        }
        return null
    }

    private class OrderComparator implements Comparator {

        List<String> priorities

        public OrderComparator(List<String> priorities) {
            this.priorities = priorities
        }

        @Override
        int compare(Object o1, Object o2) {
            def res1 = o1 as Resource
            def res2 = o2 as Resource
            def priorCnt = 0
            def prorRes1 = Integer.MAX_VALUE
            def prorRes2 = Integer.MAX_VALUE
            priorities.each { name->
                if (res1.name?.startsWith(name)) {
                    prorRes1 = priorCnt
                }
                if (res2.name?.startsWith(name)) {
                    prorRes2 = priorCnt
                }
                priorCnt++
            }
            return prorRes1.compareTo(prorRes2)
        }
    }

}