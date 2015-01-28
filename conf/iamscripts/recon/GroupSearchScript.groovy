import groovy.json.JsonSlurper
import org.openiam.idm.searchbeans.GroupSearchBean
import org.openiam.idm.srvc.recon.service.AbstractIDMSearchScript

public class GroupSearchScript extends AbstractIDMSearchScript {
    @Override
    public GroupSearchBean createGroupSearchBean(Map<String, Object> bindingMap) {
        def bean = new GroupSearchBean()
        if (updatedSince) {
            bean.updatedSince = updatedSince
        }
        if (searchFilter) {
            def obj = new JsonSlurper().parseText(searchFilter)
            if (obj in Map) {
                obj.keySet().each { key->
                    if (bean.properties.find{key}) {
                        bean."${key}" = obj."${key}"
                    }
                }
            }
        }
        return bean
    }
}